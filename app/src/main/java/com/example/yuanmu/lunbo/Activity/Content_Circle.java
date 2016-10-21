package com.example.yuanmu.lunbo.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.example.yuanmu.lunbo.Adapter.Content_CircleAdapter;
import com.example.yuanmu.lunbo.Adapter._2.Comment_CircleAdapter;
import com.example.yuanmu.lunbo.Application.MyApplication;
import com.example.yuanmu.lunbo.BmobBean.CircleComment;
import com.example.yuanmu.lunbo.BmobBean.Lifecircle;
import com.example.yuanmu.lunbo.BmobBean.Reply;
import com.example.yuanmu.lunbo.BmobBean.User;
import com.example.yuanmu.lunbo.Custom.CircleImageView;
import com.example.yuanmu.lunbo.Custom.Comment_CircleListView;
import com.example.yuanmu.lunbo.Custom.Content_CircleGridView;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.ImgUtil;
import com.example.yuanmu.lunbo.Util.MyLog;
import com.example.yuanmu.lunbo.Util.ScreenUtil;
import com.example.yuanmu.lunbo.Util.StatusBarColorUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
 * Created by yuanmu on 2016/8/29.
 */
public class Content_Circle extends AppCompatActivity {
    TextView mAddress_tv;
    String mTargetUserNickName;
    //判断是回复还是评论
    private boolean mReplyAndCommentSwitch = true;
    private Comment_CircleAdapter adapter;
    private Context context;
    private List<String> imglist = new ArrayList<String>();
    private Content_CircleAdapter imgadapter;
    private Content_CircleGridView gv_1;
    private NetworkImageView iv_img;
    private String objectId;
    private TextView tv_nickname;
    private TextView tv_createdAt;
    private TextView tv_content;
    private EditText et_reply;
    /* 回复控件*/
    private TextView tv_enter;
    private String targetnickname;
    private String oneuser;
    private CircleImageView civ_img;
    //文章里面的评论List控件
    private Comment_CircleListView lv_comment;

    private List<Map<String, Object>> commentlist = new ArrayList<Map<String, Object>>();
    private Map<String, Object> commentmap;
    private List<String> mcommentarray = new ArrayList<String>();

    private List<User> mUserList = new ArrayList<>();
    //评论发表人
    public User mCurrentUser;
    public String mReplyId;
    public Map<String,List> mListMap = new HashMap<>();
    private int mValue = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_circle);
        new StatusBarColorUtil(this,R.color.StyleColor);
        context = Content_Circle.this;
        Intent intent = getIntent();
        //文章的ID
        objectId = intent.getStringExtra("objectId");
        initView();
        tv_enter.setText("评论");
        initData();
        getData(objectId);
        initEvent();
    }

    private void initEvent() {
        //回复和评论控件
        tv_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //值为true时即为评论，反之为回复
                if(mReplyAndCommentSwitch){
                    final String content = et_reply.getText().toString().trim();
                    if (TextUtils.isEmpty(content)) {
                        return;
                    }
                    if (!MyApplication.isLogin) {
                        return;
                    }
                    final User user = BmobUser.getCurrentUser(User.class);
                    mAddress_tv.setText(user.getCity()+"-"+user.getDistrict());
                    // 创建要发表的评论信息
                    CircleComment cc = new CircleComment();
                    cc.setContent(content);
                    //文章的id
                    cc.setBelong(objectId);
                    cc.setTargetuser(targetnickname);
                    //层主
                    cc.setUser(user);
                    cc.save(new SaveListener<String>() {
                        @Override
                        public void done(String object, BmobException e) {
                            if (e == null) {
                                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                inputMethodManager.hideSoftInputFromWindow(Content_Circle.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                tv_enter.setText("评论");
                                et_reply.setText("");
                                et_reply.setHint("说点什么吧");
                                mReplyAndCommentSwitch = true;
                                getData(objectId);
                                Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT)
                                        .show();
                                //一条评论信息的ID
                                relative(object);
                                et_reply.setText("");
                                getData(objectId);
                            } else {
                                Toast.makeText(context, "保存失败", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }
                        //object为 评论信息ID
                        private void relative(String object) {
                            Lifecircle post = new Lifecircle();
                            post.setObjectId(objectId);
                            if (mcommentarray.size() < 3) {
                                if (targetnickname.equals(oneuser)) {
                                    mcommentarray.add(user.getNickname() + "：" + content);
                                } else {
                                    mcommentarray.add(user.getNickname() + " 回复 "
                                            + targetnickname + "：" + content);
                                }
                                post.setCommentarray(mcommentarray);
                            }
                            // 将用户B添加到Post表中的likes字段值中，表明用户B喜欢该帖子
                            BmobRelation relation = new BmobRelation();
                            // 构造用户B
                            CircleComment user = new CircleComment();
                            user.setObjectId(object);
                            // 将用户B添加到多对多关联中
                            relation.add(user);
                            // 多对多关联指向`post`的`likes`字段
                            post.setComment(relation);
                            post.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        Log.i("bmob", "用户B和该帖子关联成功");
                                    } else {
                                        Log.i("bmob", "失败：" + e.getMessage());
                                    }
                                }
                            });

                        }
                    });
                }else{
                    Reply reply = new Reply();
                    String[] userNickNameArray = new String[2];
                    //回复用户
                    userNickNameArray[0] = mCurrentUser.getNickname();
                    //被回复用户
                    userNickNameArray[1] = mTargetUserNickName;
                    reply.setNickNameArray(userNickNameArray);
                    //todo
                    reply.setBelong(mReplyId);
                    reply.setContent(et_reply.getText().toString());
                    reply.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e == null){
                                Toast.makeText(Content_Circle.this, "回复保存成功!", Toast.LENGTH_SHORT).show();
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
                                            Toast.makeText(Content_Circle.this, "回复更新成功!", Toast.LENGTH_SHORT).show();
                                            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                            inputMethodManager.hideSoftInputFromWindow(Content_Circle.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                            mReplyAndCommentSwitch = true;
                                            tv_enter.setText("评论");
                                            et_reply.setText("");
                                            et_reply.setHint("说点什么吧");
                                            getData(objectId);
                                        }else{
                                            Toast.makeText(Content_Circle.this, "回复更新失败!", Toast.LENGTH_SHORT).show();
                                            MyLog.i("ok","回复错误信息为："+e);
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }

        });
    }



    //传入文章的ID,获取文章的内容
    private void getData(final String objectId) {
        BmobQuery<Lifecircle> bmobQuery = new BmobQuery<Lifecircle>();
        bmobQuery.order("-createdAt");
        bmobQuery.include("user");// 希望在查询帖子信息的同时也把发布人的信息查询出来
        bmobQuery.getObject(objectId, new QueryListener<Lifecircle>() {
            @Override
            public void done(Lifecircle objects, BmobException e) {
                if (e == null) {
                    //返回文章内容
                    String img = objects.getUser().getImg();
                    String nickname = objects.getUser().getNickname();
                    String createdAt = objects.getCreatedAt();
                    String content = objects.getContent();
                    List<String> imgarray = objects.getImgarray();
                    //文章的主人昵称
                    targetnickname = nickname;
                    oneuser = nickname;
                    //输入框的内容
                    et_reply.setHint("说点什么吧");
                    tv_nickname.setText(nickname);
                    tv_createdAt.setText(createdAt);
                    tv_content.setText(content);
                    ImgUtil.setImg(civ_img, img, 150, 150);
                    int imgsize = imgarray.size();
                    switch (imgsize) {
                        case 0:
                            break;
                        case 1:
                            iv_img.setVisibility(View.VISIBLE);
                            gv_1.setVisibility(View.GONE);
                            int screenWidth = ScreenUtil.getScreenwidth();
                            ViewGroup.LayoutParams lp = iv_img.getLayoutParams();
                            lp.width = screenWidth;
                            lp.height = lp.WRAP_CONTENT;
                            iv_img.setLayoutParams(lp);
                            iv_img.setMaxWidth(screenWidth);
                            iv_img.setMaxHeight(screenWidth * 3); //这里其实可以根据需求而定，我这里测试为最大宽度的5倍
                            ImgUtil.setImg(iv_img, imglist.get(0) + "");
                            break;
                        default:
                            iv_img.setVisibility(View.GONE);
                            gv_1.setVisibility(View.VISIBLE);
                            if (imgsize == 2 || imgsize == 3 || imgsize == 4) {
                                gv_1.setNumColumns(2);
                            } else {
                                gv_1.setNumColumns(3);
                            }
                            imgadapter.notifyDataSetChanged();
                            break;
                    }
                }
            }
        });
        BmobQuery<CircleComment> query = new BmobQuery<CircleComment>();
        query.addWhereEqualTo("belong", objectId); // 查询当前用户的所有帖子
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
        context = Content_Circle.this;
        adapter = new Comment_CircleAdapter(context, commentlist,mUserList,mListMap);
        //文章里面的评论List控件
        lv_comment.setAdapter(adapter);
        imgadapter = new Content_CircleAdapter(context, imglist);
        //文章里面的图片
        gv_1.setAdapter(imgadapter);
        //文章里面的评论item点击事件
        adapter.setfriinterface(new Comment_CircleAdapter.CommentClick() {
            @Override
            public void onItemClick(String targetusername,String id) {
                mTargetUserNickName = targetusername;
                tv_enter.setText("回复");
                mReplyId = id;
                //赋值为false，即开启回复
                mReplyAndCommentSwitch = false;
                //被回复的用户
                mCurrentUser = BmobUser.getCurrentUser(User.class);
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.RESULT_UNCHANGED_HIDDEN);
                et_reply.setHint("回复:" + targetusername);
            }
        });
        //回复项监听
        adapter.setmReplyListener(new Comment_CircleAdapter.ReplyClick() {
            @Override
            public void onItemClick( String targetusername, String id) {
                tv_enter.setText("回复");
                mReplyId = id;
                //赋值为false，即开启回复
                mReplyAndCommentSwitch = false;
                //被回复的用户
                mTargetUserNickName = targetusername;
                mCurrentUser = BmobUser.getCurrentUser(User.class);
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                et_reply.setHint("回复:" + targetusername);
            }
        });

    }

    private void initView() {
        mAddress_tv = (TextView) findViewById(R.id.mAddress_tv);
        lv_comment = (Comment_CircleListView) findViewById(R.id.lv_comment);
        gv_1 = (Content_CircleGridView) findViewById(R.id.gv_1);
        iv_img = (NetworkImageView) findViewById(R.id.iv_img);
        et_reply = (EditText) findViewById(R.id.et_reply);
        tv_enter = (TextView) findViewById(R.id.tv_enter);
        tv_createdAt = (TextView) findViewById(R.id.tv_createdAt);
        tv_nickname = (TextView) findViewById(R.id.tv_nickname);
        tv_content = (TextView) findViewById(R.id.tv_content);
        civ_img = (CircleImageView) findViewById(R.id.civ_img);
    }
}
