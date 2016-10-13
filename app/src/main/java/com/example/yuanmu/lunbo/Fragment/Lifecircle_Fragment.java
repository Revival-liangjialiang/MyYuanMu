package com.example.yuanmu.lunbo.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.yuanmu.lunbo.Activity.CommentClickActivity;
import com.example.yuanmu.lunbo.Activity.Release_LifeActivity;
import com.example.yuanmu.lunbo.Adapter.LifecircleAdapter;
import com.example.yuanmu.lunbo.Application.MyApplication;
import com.example.yuanmu.lunbo.BmobBean.CircleComment;
import com.example.yuanmu.lunbo.BmobBean.Lifecircle;
import com.example.yuanmu.lunbo.BmobBean.Reply;
import com.example.yuanmu.lunbo.BmobBean.User;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.MyLog;
import com.yalantis.phoenix.PullToRefreshView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;


/**
 * Created by Administrator on 2016/8/25 0025.
 */
public class Lifecircle_Fragment extends Fragment implements View.OnClickListener{
    String mTargetUserNickName;
    //文章Id
    private String mArticleId;
    private String mContent;
    private String mCommentId;
    private User mCurrentUser;
    private AppCompatActivity mActivity;
    private boolean mCommentAndReplySwitch;
    private ViewGroup view;
    private String articleId;
    private ListView lv_1;

    private LifecircleAdapter adapter;
    private Map<String, Object> map;
    private Context context;
    private List<List> mCircleCommentList = new ArrayList<>();
    private PullToRefreshView mPullToRefreshView;
    public static final int REFRESH_DELAY = 2000;
    private ImageView iv_edit;

    //文章集合
    private List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
    //评论集合
    private Map<String,List> mCommentListMap = new HashMap<>();
    //回复集合
    private Map<String,List> mReplyListMap = new HashMap<>();
    private String mQueryArticleId;
    private String mStartCommentId;
    private int mCommentValue = 0;
    //用来判断是否全部回复都请求完毕!
    private int mCommentCount = 0,mCommentCountCopy = 0;
    public Lifecircle_Fragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = (ViewGroup) inflater.from(container.getContext()).inflate(R.layout.fragment_lifecircle, container, false);
        context = getActivity();
        mCurrentUser = BmobUser.getCurrentUser(User.class);
        initView();
        initData();
        getData();
        initEvent();
        return view;
    }

    private void initEvent() {
        iv_edit.setOnClickListener(this);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getData();
                        mPullToRefreshView.setRefreshing(false);
                    }
                }, REFRESH_DELAY);
            }
        });
        adapter.setCommentReplyListener(new LifecircleAdapter.CommentReplyClickListener() {
            @Override
            public void commentClick(User user, String nickName, String commentId) {
                //为真即开启评论，反之开启回复
                mCommentAndReplySwitch = false;
                mTargetUserNickName = nickName;
                Intent intent = new Intent(mActivity,CommentClickActivity.class);
                intent.putExtra(CommentClickActivity.CLICK_TYPE,"回复");
                intent.putExtra(CommentClickActivity.INPUT_TEXT,"回复:"+nickName);
                startActivityForResult(intent,0);
                mCommentId = commentId;
            }
        });
        adapter.setReplyListener(new LifecircleAdapter.ReplyClickListener() {
            @Override
            public void replyClick(String targetUserNickName, String commentId) {
                mCommentAndReplySwitch = false;
                mTargetUserNickName = targetUserNickName;
                MyLog.i("ko","commentId = "+commentId);
                mCommentId = commentId;
                Intent intent = new Intent(mActivity,CommentClickActivity.class);
                intent.putExtra(CommentClickActivity.CLICK_TYPE,"回复");
                intent.putExtra(CommentClickActivity.INPUT_TEXT,"回复:"+targetUserNickName);
                startActivityForResult(intent,0);
            }
        });
        adapter.setCommentListener(new LifecircleAdapter.CommentLsiener() {
            @Override
            public void commentClick(String articleId) {
                //为真即开启评论，反之开启回复
                mCommentAndReplySwitch = true;
                mArticleId = articleId;
                Intent intent = new Intent(mActivity,CommentClickActivity.class);
                intent.putExtra(CommentClickActivity.CLICK_TYPE,"评论");
                intent.putExtra(CommentClickActivity.INPUT_TEXT,"说点什么吧");
                startActivityForResult(intent,0);
            }
        });
    }

    private void initData() {
        adapter = new LifecircleAdapter(getActivity(), mList,mCommentListMap,mReplyListMap);
        lv_1.setAdapter(adapter);
    }
    private void initView() {
        //监听global layout的大小变化
        lv_1 = (ListView) view.findViewById(R.id.lv_1);
        iv_edit = (ImageView) view.findViewById(R.id.iv_edit);
        mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.pull_to_refresh);
    }
    public void getData() {
        mCircleCommentList.clear();
        mReplyListMap.clear();
        mCommentListMap.clear();
        mList.clear();
        //查询文章
        BmobQuery<Lifecircle> query = new BmobQuery<Lifecircle>();
        query.order("-createdAt");
        query.setLimit(10);
        query.include("user");// 希望在查询帖子信息的同时也把发布人的信息查询出来
        query.findObjects(new FindListener<Lifecircle>() {
            @Override
            public void done(List<Lifecircle> object, BmobException e) {
                //2
                if (e == null) {
                    Log.i("数量", object.size() + "");
                    for (int i = 0; i < object.size(); i++) {
                        //获取文章ID
                        articleId = object.get(i).getObjectId();
                        String nickname = object.get(i).getUser()
                                .getNickname();
                        String img = object.get(i).getUser().getImg();
                        String content = object.get(i).getContent();
                        String objectId = object.get(i).getObjectId();
                        String createdAt = object.get(i).getCreatedAt();
                        Date date = null;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            date = sdf.parse(createdAt);
                            MyLog.i("value","date = "+date);
                        } catch (ParseException el) {
                            el.printStackTrace();
                        }
                        List<String> fabulousList = object.get(i).getFabulous();
                        List<String> imgarray = object.get(i).getImgarray();
                        List<String> commentarray = object.get(i)
                                .getCommentarray();
                        map = new HashMap<String, Object>();
                        map.put("fabulous",fabulousList);
                        map.put("id",articleId);
                        map.put("nickname", nickname);
                        map.put("img", img);
                        map.put("content", content);
                        map.put("objectId", objectId);
                        map.put("createdAt", createdAt);
                        map.put("imgarray", imgarray);
                        map.put("commentarray", commentarray);
                        Toast.makeText(MyApplication.getContext(), "加载文章成功!", Toast.LENGTH_SHORT).show();
                        mList.add(map);
                    }
                    //查询文章的评论
                    startCommentQuery();
                    adapter.notifyDataSetChanged();
                } else {
                    Log.i("问题", e + "");
                }
            }

        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_edit:
                startActivity(new Intent(context, Release_LifeActivity.class));
                break;
            default:break;
        }
    }
    public void startCommentQuery(){
        adapter.notifyDataSetChanged();
        Toast.makeText(MyApplication.getContext(), "启动文章评论请求!", Toast.LENGTH_SHORT).show();
        //查询文章的评论
        for(int a = 0;a<mList.size();a++) {
            mQueryArticleId = (String) mList.get(a).get("id");
            BmobQuery<CircleComment> query = new BmobQuery<CircleComment>();
            query.include("user");
            query.addWhereEqualTo("belong", mQueryArticleId);
            query.findObjects(new FindListener<CircleComment>() {
                //文章ID
                String articleIdCopy = mQueryArticleId;
                @Override
                public void done(final List<CircleComment> list, BmobException e) {
                    //有多少文章就会加多少次
                    if (e == null) {
                        mCommentValue++;
                        //将一篇文章的所有的评论放进去，待请求回复用
                        mCircleCommentList.add(list);
                        //存放文章的评论,索引是文章的objectId
                        MyLog.i("value","存放评论信息数量为："+list.size());
                        mCommentListMap.put(articleIdCopy, list);
                        if(mCommentValue == mList.size()){
                            startReplyQuery();
                        }
                    } else {
                        Log.i("ppp","请求文章错误信息为:"+e);
                    }
                }
            });
        }
        //------------------
    }
    public void startReplyQuery(){
        //继续进行查询评论回复
        for(int a = 0;a<mCircleCommentList.size();a++) {
            //取出某个文章的评论进行查询
            List<CircleComment> list = mCircleCommentList.get(a);
            for(int b = 0;b<list.size();b++) {
                mStartCommentId = list.get(b).getObjectId();
                BmobQuery<Reply> query = new BmobQuery<>();
                query.addWhereEqualTo("belong",mStartCommentId);
                query.findObjects(new FindListener<Reply>() {
                    String id = mStartCommentId;
                    @Override
                    public void done(List<Reply> list, BmobException e) {
                        if(e == null) {
                            mCommentCountCopy++;
                            mReplyListMap.put(id, list);
                            //当相等时，可以进行适配器刷新操作!
                            if(mCommentCount==mCommentCountCopy){
                                adapter.notifyDataSetChanged();
                                mCommentCountCopy = 0;
                                mCommentCount = 0;
                                mCommentValue = 0;
                            }
                        }else{
                            Toast.makeText(MyApplication.getContext(), "QueryReplyFail!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                mCommentCount++;
            }
        }
        //----------------------
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (AppCompatActivity) getActivity();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 0:
                if(resultCode == mActivity.RESULT_OK){
                    mContent = data.getStringExtra(CommentClickActivity.CONTENT);
                    if(mCommentAndReplySwitch){
                        //处理评论逻辑
                        if (TextUtils.isEmpty(mContent)) {
                            return;
                        }
                        if (!MyApplication.isLogin) {
                            Toast.makeText(MyApplication.getContext(), "未登录!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        final User user = BmobUser.getCurrentUser(User.class);
                        // 创建要发表的评论信息
                        CircleComment cc = new CircleComment();
                        cc.setContent(mContent);
                        //文章的id
                        cc.setBelong(mArticleId);
                        cc.setTargetuser(mCurrentUser.getNickname());
                        //层主
                        cc.setUser(user);
                        cc.save(new SaveListener<String>() {
                            @Override
                            public void done(String object, BmobException e) {
                                if (e == null) {
                                    mCommentAndReplySwitch = true;
                                    Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT)
                                            .show();
                                } else {
                                    Toast.makeText(context, "保存失败", Toast.LENGTH_SHORT)
                                            .show();
                                }
                            }
                            //object为 评论信息ID
                        });
                    }else{
                        //处理回复逻辑
                        Reply reply = new Reply();
                        String[] nickName = new String[2];
                        //回复用户
                        nickName[0] = mCurrentUser.getNickname();
                        //被回复用户
                        nickName[1] = mTargetUserNickName;
                        reply.setNickNameArray(nickName);
                        reply.setBelong(mCommentId);
                        reply.setContent(mContent);
                        reply.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if(e == null){
                                    Toast.makeText(mActivity, "回复保存成功!", Toast.LENGTH_SHORT).show();
                                    //todo
                                    getData();
                                    CircleComment circleComment = new CircleComment();
                                    circleComment.setObjectId(mCommentId);
                                    BmobRelation bmobRelation = new BmobRelation();
                                    Reply reply = new Reply();
                                    reply.setObjectId(s);
                                    bmobRelation.add(reply);
                                    circleComment.setReply(bmobRelation);
                                    circleComment.update(new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if(e == null){
                                                Toast.makeText(mActivity, "回复更新成功!", Toast.LENGTH_SHORT).show();
                                                mCommentAndReplySwitch = true;
                                            }else{
                                                Toast.makeText(mActivity, "回复更新失败!", Toast.LENGTH_SHORT).show();
                                                MyLog.i("ok","回复错误信息为："+e);
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
                break;
            default:break;
        }
    }

}
