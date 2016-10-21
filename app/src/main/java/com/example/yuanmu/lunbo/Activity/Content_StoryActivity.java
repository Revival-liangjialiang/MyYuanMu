package com.example.yuanmu.lunbo.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuanmu.lunbo.Adapter._2.Comment_CircleAdapter;
import com.example.yuanmu.lunbo.Application.MyApplication;
import com.example.yuanmu.lunbo.BmobBean.CircleComment;
import com.example.yuanmu.lunbo.BmobBean.Reply;
import com.example.yuanmu.lunbo.BmobBean.Story;
import com.example.yuanmu.lunbo.BmobBean.User;
import com.example.yuanmu.lunbo.Custom.Comment_CircleListView;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.MyLog;
import com.example.yuanmu.lunbo.Util.ScreenUtil;
import com.example.yuanmu.lunbo.Util.StatusBarColorUtil;

import org.xml.sax.XMLReader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by yuanmu on 2016/9/6.
 */
public class Content_StoryActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView mBack_iv;
    TextView mComment_tv;
    public Map<String,List> mListMap = new HashMap<>();
    int mValue;
    private String oneuser;
    private List<String> mcommentarray = new ArrayList<String>();
    private List<User> mUserList = new ArrayList<>();
    private List<Map<String, Object>> commentlist = new ArrayList<Map<String, Object>>();
    private Map<String, Object> commentmap;
    public String mReplyId;
    String mTargetUserNickName;
    private User mCurrentUser;
    //判断是回复还是评论
    private boolean mReplyAndCommentSwitch = true;

    private Comment_CircleListView lv_comment;
    private Comment_CircleAdapter adapter;
    private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    private EditText input_et;
    private String targetnickname;
    private TextView tv_content,submit_tv;
    private Context context;
    private String content, createdAt, title;
    private String mobjectId;
    private TextView tv_createdAt, tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_story);
        new StatusBarColorUtil(this,R.color.StyleColor);
        context = Content_StoryActivity.this;
        mCurrentUser = BmobUser.getCurrentUser(User.class);
        Intent intent = getIntent();
        //故事ID
        mobjectId = intent.getStringExtra("objectId");
        initView();
        input_et.setHint("说点什么吧!");
        submit_tv.setText("评论");
        initData();
        getData();
    }

    private void getData() {
        //查找Person表里面id为6b6c11c537的数据
        BmobQuery<Story> bmobQuery = new BmobQuery<Story>();
        bmobQuery.include("user");
        bmobQuery.getObject(mobjectId, new QueryListener<Story>() {
            @Override
            public void done(Story story, BmobException e) {
                if (e == null) {
                    User user = story.getUser();
                    oneuser = user.getNickname();
                    title = story.getTitle();
                    createdAt = story.getCreatedAt();
                    content = story.getContent();
                    tv_createdAt.setText(createdAt);
                    tv_title.setText(title);
                    htmlWebPic(content);
                } else {
                 Log.i("错误",e + "");
                }
            }
        });
        //加载评论和回复
        BmobQuery<CircleComment> query = new BmobQuery<CircleComment>();
        query.addWhereEqualTo("belong", mobjectId); // 查询当前用户的所有帖子
        query.include("user");// 希望在查询帖子信息的同时也把发布人的信息查询出来
        query.findObjects(new FindListener<CircleComment>()
        {
            @Override
            public void done(List<CircleComment> object, BmobException e) {
                if (e == null) {
                    commentlist.clear();
                    for (int i = 0; i < object.size(); i++) {
                        mUserList.add(object.get(i).getUser());
                        String nickname = object.get(i).getUser().getNickname();
                        String headPortrait = object.get(i).getUser().getImg();
                        String content = object.get(i).getContent();
                        String createdAt = object.get(i).getCreatedAt();
                        String targetuser = object.get(i).getTargetuser();
                        //获取评论信息的ID
                        String id = object.get(i).getObjectId();
                        commentmap = new HashMap<String, Object>();
                        commentmap.put("id",id);
                        commentmap.put("user",object.get(i).getUser());
                        commentmap.put("headportrait",headPortrait);
                        commentmap.put("nickname", nickname);
                        commentmap.put("content", content);
                        commentmap.put("createdAt", createdAt);
                        commentmap.put("targetuser", targetuser);
                        commentlist.add(commentmap);
                        if (targetuser != null && !targetuser.equals(oneuser)) {
                            mcommentarray.add(nickname + " 回复 " + targetuser
                                    + "：" + content);
                        } else {
                            mcommentarray.add(nickname + "：" + content);
                        }
                    }
                    //刷新文章里面的评论List
                    //查询回复信息
                    for(int a = 0;a<commentlist.size();a++) {
                        final String strId = (String) commentlist.get(a).get("id");
                        BmobQuery<Reply> query = new BmobQuery<Reply>();
                        query.addWhereEqualTo("belong", strId); // 查询当前用户的所有帖子
                        query.include("user");
                        query.findObjects(new FindListener<Reply>() {
                            String id = strId;
                            @Override
                            public void done(List<Reply> list, BmobException e) {
                                mValue++;
                                if (e == null) {
                                    //将查询回来的Reply集合直接赋值给mReplyList
                                    List<Reply> mReplyList = list;
                                    mListMap.put(id,mReplyList);
                                } else {
                                    Log.i("test", "查询回复信息的错误信息为: " + e);
                                }
                                //查询回复完毕，刷新List控件
                                if(mValue ==  commentlist.size()){
                                    adapter.notifyDataSetChanged();
                                }
//           query.include();
                            }
                        });
                        //等待回复信息加载完毕才刷新List控件
                    }
                } else {
                    Log.i("test", "有错误！" + e);
                    Toast.makeText(context, e + "", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initData() {
        tv_content.setMovementMethod(ScrollingMovementMethod.getInstance());// 设置可滚动
        adapter = new Comment_CircleAdapter(context, commentlist,mUserList,mListMap);
        lv_comment.setAdapter(adapter);
        //文章里面的评论item点击事件
        adapter.setfriinterface(new Comment_CircleAdapter.CommentClick() {
            @Override
            public void onItemClick(String targetusername,String id) {
                mTargetUserNickName = targetusername;
                submit_tv.setText("回复");
                mReplyId = id;
                //赋值为false，即开启回复
                mReplyAndCommentSwitch = false;
                //被回复的用户
                mCurrentUser = BmobUser.getCurrentUser(User.class);
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.RESULT_UNCHANGED_HIDDEN);
                input_et.setHint("回复:" + targetusername);
            }
        });
        adapter.setmReplyListener(new Comment_CircleAdapter.ReplyClick() {
            @Override
            public void onItemClick(String targetusername, String id) {
                submit_tv.setText("回复");
                mReplyId = id;
                //赋值为false，即开启回复
                mReplyAndCommentSwitch = false;
                //被回复的用户
                mTargetUserNickName = targetusername;
                mCurrentUser = BmobUser.getCurrentUser(User.class);
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                input_et.setHint("回复:" + targetusername);
            }
        });
    }

    private void initView() {
        mBack_iv = (ImageView) findViewById(R.id.mBack_iv);
        mComment_tv = (TextView) findViewById(R.id.mComment_tv);
        input_et = (EditText) findViewById(R.id.input_et);
        submit_tv = (TextView) findViewById(R.id.submit_tv);
        tv_createdAt = (TextView) findViewById(R.id.tv_createdAt);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_content = (TextView) findViewById(R.id.tv_content);
        lv_comment = (Comment_CircleListView) findViewById(R.id.lv_comment);
        submit_tv.setOnClickListener(this);
        mComment_tv.setOnClickListener(this);
        mBack_iv.setOnClickListener(this);
    }

    public void htmlWebPic(final String htmlContent) {
        Thread t = new Thread(new Runnable() {
            Message msg = handler.obtainMessage();
            @Override
            public void run() {
                Html.ImageGetter imageGetter = new Html.ImageGetter() {
                    @Override
                    public Drawable getDrawable(String source) {
                        URL url = null;
                        Drawable drawable = null;
                        try {
                            url = new URL(source);
                            drawable = Drawable.createFromStream(
                                    url.openStream(), null);
                            int spe = dip2px(context, 10);
                            int width = ScreenUtil.getScreenwidth() - spe;
                            int height = width * drawable.getIntrinsicHeight() / drawable.getIntrinsicWidth();
                            drawable.setBounds(0, 0,
                                    width,
                                    height);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return drawable;
                    }
                };
                CharSequence result = Html.fromHtml(htmlContent, imageGetter, new GameTagHandler());
                msg.what = 0x12;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        });
        t.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //提交按钮事件，提交评论或者回复！
            case R.id.submit_tv:
                if(mReplyAndCommentSwitch){
                    Toast.makeText(Content_StoryActivity.this, "此次点击为评论!", Toast.LENGTH_SHORT).show();
                    final String content = input_et.getText().toString().trim();
                    if (TextUtils.isEmpty(content)) {
                        return;
                    }
                    if (!MyApplication.isLogin) {
                        return;
                    }
                    final User user = BmobUser.getCurrentUser(User.class);
                    // 创建要发表的评论信息
                    CircleComment cc = new CircleComment();
                    cc.setContent(content);
                    //文章的id
                    cc.setBelong(mobjectId);
                    targetnickname = mCurrentUser.getNickname();
                    cc.setTargetuser(targetnickname);
                    //层主
                    cc.setUser(user);
                    cc.save(new SaveListener<String>() {
                        @Override
                        public void done(String object, BmobException e) {
                            if (e == null) {
                                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                inputMethodManager.hideSoftInputFromWindow(Content_StoryActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                submit_tv.setText("评论");
                                input_et.setText("");
                                input_et.setHint("说点什么吧");
                                mReplyAndCommentSwitch = true;
                                getData();
                                Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT)
                                        .show();
                                input_et.setText("");
                                getData();
                            } else {
                                Toast.makeText(context, "保存失败", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }

                    });
                }else{
                    Toast.makeText(Content_StoryActivity.this, "此次点击为回复!", Toast.LENGTH_SHORT).show();
                    Reply reply = new Reply();
                    String[] userNickNameArray = new String[2];
                    //回复用户
                    userNickNameArray[0] = mCurrentUser.getNickname();
                    //被回复用户
                    userNickNameArray[1] = mTargetUserNickName;
                    reply.setNickNameArray(userNickNameArray);
                    //todo
                    reply.setBelong(mReplyId);
                    reply.setContent(input_et.getText().toString());
                    reply.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e == null){
                                Toast.makeText(Content_StoryActivity.this, "回复保存成功!", Toast.LENGTH_SHORT).show();
                                CircleComment circleComment = new CircleComment();
                                circleComment.setObjectId(mReplyId);
                                BmobRelation bmobRelation = new BmobRelation();
                                Reply reply = new Reply();
                                reply.setObjectId(s);
                                bmobRelation.add(reply);
                                circleComment.setReply(bmobRelation);
                                circleComment.update(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if(e == null){
                                            Toast.makeText(Content_StoryActivity.this, "回复更新成功!", Toast.LENGTH_SHORT).show();
                                            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                            inputMethodManager.hideSoftInputFromWindow(Content_StoryActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                            mReplyAndCommentSwitch = true;
                                            submit_tv.setText("评论");
                                            input_et.setText("");
                                            input_et.setHint("说点什么吧");
                                            getData();
                                        }else{
                                            Toast.makeText(Content_StoryActivity.this, "回复更新失败!", Toast.LENGTH_SHORT).show();
                                            MyLog.i("ok","回复错误信息为："+e);
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
                break;
            case R.id.mComment_tv:
                input_et.setHint("说点什么吧!");
                submit_tv.setText("评论");
                mReplyAndCommentSwitch = true;
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.RESULT_UNCHANGED_HIDDEN);
                break;
            case R.id.mBack_iv:
                finish();
           break;
            default:break;
        }
    }

    public class GameTagHandler implements Html.TagHandler {

        @Override
        public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
            if (tag.toLowerCase(Locale.getDefault()).equals("img")) {
                // 获取长度
                int len = output.length();
                // 获取图片地址
                ImageSpan[] images = output.getSpans(len - 1, len, ImageSpan.class);
                String imgURL = images[0].getSource();

                // 使图片可点击并监听点击事件
                output.setSpan(new GameSpan(context, imgURL), len - 1, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

    private class GameSpan extends ClickableSpan implements View.OnClickListener {
        private String url;
        private Context context;

        public GameSpan(Context context, String url) {
            this.context = context;
            this.url = url;
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(context, "图片点击" + url, Toast.LENGTH_SHORT).show();
            Log.i("图片", url);

        }
    }

    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            if (msg.what == 0x12) {
                tv_content.setText((CharSequence) msg.obj);
                tv_content.setMovementMethod(LinkMovementMethod.getInstance());
            }
            return false;
        }
    });

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
