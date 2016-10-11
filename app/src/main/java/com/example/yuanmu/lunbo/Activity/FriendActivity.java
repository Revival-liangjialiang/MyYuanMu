package com.example.yuanmu.lunbo.Activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yuanmu.lunbo.Adapter.FriendAdapter;
import com.example.yuanmu.lunbo.BmobBean.Focus;
import com.example.yuanmu.lunbo.BmobBean.User;
import com.example.yuanmu.lunbo.Custom.LetterRetrievalRightTool;
import com.example.yuanmu.lunbo.Data.FriendData;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.MixComparator;
import com.example.yuanmu.lunbo.Util.PinYinUtil;
import com.example.yuanmu.lunbo.Util.UserCacheUtil;
import com.example.yuanmu.lunbo.interfaces.KeywithValueUtil;
import com.example.yuanmu.lunbo.interfaces.PublicInterface;

import java.util.Collections;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import io.rong.imkit.RongIM;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class FriendActivity extends Activity  implements
        ListView.OnScrollListener, FriendAdapter.MessageFriClick {

    private ListView listview_friend;
    private FriendAdapter adapter;
    private LetterRetrievalRightTool letterListView;
    private Handler handler;
    private DisapearThread disapearThread;
    private int scrollState;
    private TextView txtOverlay;
    private WindowManager windowManager;
    private RelativeLayout rl_fri;
    private Context context;
    private String targetobject;

        @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        context = FriendActivity.this;
        initView();
        textOverlayout();
        getData();
        initData();
        initEvent();
        initChange();
    }

        /**
         * 好友列表发生改变时调用
         */

    private void initChange() {
        KeywithValueUtil.setKeywithValueInterface(new PublicInterface() {

            @Override
            public void getkeywithvalue(String key, String value) {
                if (key.equals(FriendData.Status_Change)) {
                    if (value.equals(FriendData.Status_IsChange)) {
                        adapter.notifyDataSetChanged();
                        FriendData.Status_Change = FriendData.Status_NoChange;
                        rl_fri.setVisibility(View.GONE);
                    }
                }

            }
        });
    }
//TODO
    /**
     * 获取数据
     */
    private void getData() {
        User u = BmobUser.getCurrentUser(User.class);
        Log.i("======================", u.getUsername() + "账号");
        BmobQuery<Focus> query = new BmobQuery<Focus>();
        query.addWhereEqualTo("myusername", u.getUsername());
        query.include("target");
        query.findObjects(new FindListener<Focus>() {

            @Override
            public void done(List<Focus> list, BmobException e) {
                if (e == null) {
                    Log.i("======================",list.size() + "数量");
                    FriendData.pinyinList.clear();
                    FriendData.firstLetterList.clear();
                    FriendData.firstLetterList.clear();
                    FriendData.usernamemap.clear();
                    FriendData.imgmap.clear();
                    FriendData.remarkmap.clear();
                    for (int i = 0; i < list.size(); i++) {
                        String remark = list.get(i).getRemark();
                        String targetusername = list.get(i).getTarget()
                                .getUsername();
                        Log.i("======================","备注" + remark);
                        Log.i("======================","对方账号" + targetusername);
                        String targetimg = list.get(i).getTarget().getImg();
//                        targetobject = list.get(i).getTarget().getObjectId();
                        UserCacheUtil
                                .setRemark(context, targetusername, remark);
                        UserCacheUtil.setAvator(context, targetusername,
                                targetimg);
                        String pinyin = PinYinUtil.converterToPinYin(remark
                                + targetusername);
                        String firstLetter = pinyin.substring(0, 1);
                        FriendData.pinyinList.add(pinyin); // 此列表增加拼音
                        Collections.sort(FriendData.pinyinList,
                                new MixComparator());
                        if (!FriendData.firstLetterList.contains(firstLetter)
                                && PinYinUtil.isLetter(firstLetter)) {
                            FriendData.firstLetterList.add(firstLetter); // 此列表添加拼音首字母
                            Collections.sort(FriendData.firstLetterList,
                                    new MixComparator());
                        }
                        FriendData.usernamemap.put(pinyin, targetusername);
                        FriendData.imgmap.put(targetusername, targetimg);
                        FriendData.remarkmap.put(targetusername, remark);

                    }
                    adapter.notifyDataSetChanged();
                    listview_friend.setOnScrollListener(FriendActivity.this);
                    disapearThread = new DisapearThread();
                } else {
                    nopeople();
                }
            }

        });
    }

    protected void nopeople() {
        rl_fri.setVisibility(View.VISIBLE);
    }

    /**
     * 初始化数据、填充数据
     */
    private void initData() {
        adapter = new FriendAdapter(context, FriendData.pinyinList,
                FriendData.usernamemap, FriendData.imgmap, FriendData.remarkmap);
        adapter.setfriinterface(FriendActivity.this);
        listview_friend.setAdapter(adapter);
    }

    /**
     * 初始化控件的监听事件
     */
    private void initEvent() {
        letterListView
                .setOnTouchingLetterChangedListener(new LetterListViewListener());
    }

    /**
     * 初始化控件
     */
    private void initView() {
        rl_fri = (RelativeLayout) findViewById(R.id.rl_fri);
        letterListView = (LetterRetrievalRightTool) findViewById(R.id.rightCharacterListView);
        listview_friend = (ListView) findViewById(R.id.listview_friend);
    }


    // 点击进入好友资料
    @Override
    public void onfriclick(String username, String remark, String img) {
        Log.i("账号",username + "");
//        Intent intent = new Intent();
//        intent.putExtra("targetid", username);
//        intent.putExtra("remark", remark);
//        intent.putExtra("targetobject", targetobject);
//        intent.setClass(context, ConversationActivity.class);
//        startActivity(intent);
        if (RongIM.getInstance() != null) {
            RongIM.getInstance().startPrivateChat(context, username,
                    "聊天");
        }
    }

    /**
     * 滚到悬浮字母
     */
    public void textOverlayout() {
        handler = new Handler();
        // 初始化首字母悬浮提示框
        txtOverlay = (TextView) LayoutInflater.from(context).inflate(
                R.layout.popup_char, null);
        txtOverlay.setVisibility(View.INVISIBLE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        windowManager = (WindowManager) context.getSystemService(
                Context.WINDOW_SERVICE);
        windowManager.addView(txtOverlay, lp);

    }

    /**
     * 右侧导航条点击列表滚动指定位置
     */
    public class LetterListViewListener implements
            LetterRetrievalRightTool.OnTouchingLetterChangedListener {
        public void onTouchingLetterChanged(final String s) {
            int num = 0;
            for (int i = 0; i < FriendData.pinyinList.size(); i++) {
                if ("#".equals(s)) { // 底部
                    num = FriendData.pinyinList.size() - 1;
                } else if (PinYinUtil.isLetter(FriendData.pinyinList.get(i)
                        .substring(0, 1))
                        && (PinYinUtil.character2ASCII(FriendData.pinyinList
                        .get(i).substring(0, 1)) < (PinYinUtil
                        .character2ASCII(s) + 32))) {
                    num += 1; // 首先判断是字母，字母的ascll值小于s是，滚动位置+1；如果有10个数据小于s，就滚到10处
                }
            }
            listview_friend.setSelection(num); // 移动到指定
        }
    }

    /**
     * 滚动处理
     *
     * @param view
     * @param firstVisibleItem
     * @param visibleItemCount
     * @param totalItemCount
     */
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        if (FriendData.pinyinList.size() > 0) {
            String firstLetter = String.valueOf(
                    FriendData.pinyinList.get(firstVisibleItem).charAt(0))
                    .toUpperCase();
            if (PinYinUtil.isLetter(firstLetter)) {
                txtOverlay.setText(firstLetter);
            } else {
                txtOverlay.setText("#");
            }
        }

    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        this.scrollState = scrollState;
        if (scrollState == ListView.OnScrollListener.SCROLL_STATE_IDLE) {
            handler.removeCallbacks(disapearThread);
            // 提示延迟1.0s再消失
            handler.postDelayed(disapearThread, 1000);
        } else {
            txtOverlay.setVisibility(View.VISIBLE);
        }
    }

    private class DisapearThread implements Runnable {
        public void run() {
            // 避免在1.5s内，用户再次拖动时提示框又执行隐藏命令。
            if (scrollState == ListView.OnScrollListener.SCROLL_STATE_IDLE) {
                txtOverlay.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void onDestroy() {
        super.onDestroy();
        txtOverlay.setVisibility(View.INVISIBLE);
        windowManager.removeView(txtOverlay);
    }
}
