package com.example.yuanmu.lunbo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.yuanmu.lunbo.Activity.Content_Circle;
import com.example.yuanmu.lunbo.Activity.Gally;
import com.example.yuanmu.lunbo.Application.MyApplication;
import com.example.yuanmu.lunbo.BmobBean.CircleComment;
import com.example.yuanmu.lunbo.BmobBean.Reply;
import com.example.yuanmu.lunbo.BmobBean.User;
import com.example.yuanmu.lunbo.Custom.CircleImageView;
import com.example.yuanmu.lunbo.Custom.MyCircleitemGridView;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.DateUtil;
import com.example.yuanmu.lunbo.Util.DateUtils;
import com.example.yuanmu.lunbo.Util.ImgUtil;
import com.example.yuanmu.lunbo.Util.MyLog;
import com.example.yuanmu.lunbo.Util.ScreenUtil;
import com.example.yuanmu.lunbo.interfaces.ListItemBtnClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuanmu on 2016/8/24.
 */
public class LifecircleAdapter extends BaseAdapter {
    private Reply reply;
//    private CircleComment circleComment;
    private CommentLsiener CommentListener;
    //CommentClickListener
    private CommentReplyClickListener CommentReplyListener;
    //ReplyListener
    private ReplyClickListener ReplyListener;
    private List<Map<String, Object>> mlist;
    private Context mcontext;
    private MyCricleitemGridAdapter adapter;
    private ListItemBtnClickListener listItemBtnClickListener = null;
    //评论集合
    private Map<String,List> mCommentListMap = new HashMap<>();
    //回复集合
    private Map<String,List> mReplyListMap = new HashMap<>();
    private String mArticleId;

    //
//   private List<CircleComment> mCommentCopyList;
    private List<Reply> mReplyCopyList;

    public LifecircleAdapter(Context context, List<Map<String, Object>> list,Map<String,List> comments,Map<String,List> replys) {
        this.mcontext = context;
        this.mlist = list;
        mCommentListMap = comments;
        mReplyListMap = replys;
    }

    /**
     * @return the listItemBtnClickListener
     */
    public ListItemBtnClickListener getListItemBtnClickListener() {
        return listItemBtnClickListener;
    }

    /**
     * @param listItemBtnClickListener the listItemBtnClickListener to set
     */
    public void setListItemBtnClickListener(
            ListItemBtnClickListener listItemBtnClickListener) {
        this.listItemBtnClickListener = listItemBtnClickListener;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        List<String> imgarray = (List<String>) mlist.get(position).get(
                "imgarray");
        if (imgarray == null) {
            return 0;
        }
        if (imgarray.size() == 0) {
            return 0;
        }
        if (imgarray.size() == 1) {
            return 1;
        }
        if (imgarray.size() == 4) {
            return 2;
        }

        return 3;
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        MyLog.i("ko","getView（）");
        ViewHolder1 holder1 = null;
        ViewHolder2 holder2 = null;
        ViewHolder3 holder3 = null;
        ViewHolder4 holder4 = null;
        //获取文章ID
        final String articleId = (String) mlist.get(position).get("id");
        final String content = (String) mlist.get(position).get("content");
        final String nickname = (String) mlist.get(position).get("nickname");
        final String img = (String) mlist.get(position).get("img");
        final String objectId = (String) mlist.get(position).get("objectId");
        final String createdAt = (String) mlist.get(position).get("createdAt");
        String datestr = DateUtils.data(createdAt);
        String ptime = DateUtil.getStandardDate(datestr);
        final List<String> imgarray = (List<String>) mlist.get(position).get(
                "imgarray");
        int type = getItemViewType(position);
        if (convertView == null)
            switch (type) {
            case 0://无图
                convertView = LayoutInflater.from(mcontext).inflate(
                        R.layout.lifecircle_item_cricle1, parent, false);
                holder1 = new ViewHolder1();
                holder1.comment_img = (ImageView) convertView.findViewById(R.id.comment_img);
                holder1.ll_2 = (LinearLayout) convertView.findViewById(R.id.ll_2);
                holder1.civ_img = (CircleImageView) convertView
                        .findViewById(R.id.civ_img);
                holder1.tv_content = (TextView) convertView
                        .findViewById(R.id.tv_content);
                holder1.tv_nickname = (TextView) convertView
                        .findViewById(R.id.tv_nickname);
                holder1.tv_createdAt = (TextView) convertView
                        .findViewById(R.id.tv_createdAt);
                convertView.setTag(holder1);
                holder1.ll_2.setTag(articleId);
                break;
            case 1://一张图
                convertView = LayoutInflater.from(mcontext).inflate(
                        R.layout.lifecircle_item_cricle2, parent, false);
                holder2 = new ViewHolder2();
                holder2.comment_img = (ImageView) convertView.findViewById(R.id.comment_img);
                holder2.civ_img = (CircleImageView) convertView
                        .findViewById(R.id.civ_img);
                holder2.tv_nickname = (TextView) convertView
                        .findViewById(R.id.tv_nickname);
                holder2.tv_createdAt = (TextView) convertView
                        .findViewById(R.id.tv_createdAt);
                holder2.iv_img = (NetworkImageView) convertView.findViewById(R.id.iv_img);
                holder2.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
                holder2.ll_2 = (LinearLayout) convertView.findViewById(R.id.ll_2);
                convertView.setTag(holder2);
                holder2.ll_2.setTag(articleId);
                break;
            case 2://四张图
                convertView = LayoutInflater.from(mcontext).inflate(
                        R.layout.lifecircle_item_cricle3, parent, false);
                holder3 = new ViewHolder3();
                holder3.comment_img = (ImageView) convertView.findViewById(R.id.comment_img);
                holder3.civ_img = (CircleImageView) convertView
                        .findViewById(R.id.civ_img);
                holder3.tv_content = (TextView) convertView
                        .findViewById(R.id.tv_content);
                holder3.tv_nickname = (TextView) convertView
                        .findViewById(R.id.tv_nickname);
                holder3.tv_createdAt = (TextView) convertView
                        .findViewById(R.id.tv_createdAt);
                holder3.gv_img = (MyCircleitemGridView) convertView.findViewById(R.id.gv_img);
                holder3.ll_2 = (LinearLayout) convertView.findViewById(R.id.ll_2);
                convertView.setTag(holder3);
                holder3.ll_2.setTag(articleId);
                break;
            case 3://多张图
                convertView = LayoutInflater.from(mcontext).inflate(
                        R.layout.lifecircle__item_cricle4, parent, false);
                holder4 = new ViewHolder4();
                holder4.comment_img = (ImageView) convertView.findViewById(R.id.comment_img);
                holder4.civ_img = (CircleImageView) convertView
                        .findViewById(R.id.civ_img);
                holder4.tv_content = (TextView) convertView
                        .findViewById(R.id.tv_content);
                holder4.tv_nickname = (TextView) convertView
                        .findViewById(R.id.tv_nickname);
                holder4.tv_createdAt = (TextView) convertView
                        .findViewById(R.id.tv_createdAt);
                holder4.gv_img = (MyCircleitemGridView) convertView.findViewById(R.id.gv_img);
                holder4.ll_2 = (LinearLayout) convertView.findViewById(R.id.ll_2);
                convertView.setTag(holder4);
                holder4.ll_2.setTag(articleId);
                break;

        }else {
            switch (type) {
                case 0:
                    holder1 = (ViewHolder1) convertView.getTag();
                    holder1.ll_2.setTag(articleId);
                    break;
                case 1:
                    holder2 = (ViewHolder2) convertView.getTag();
                    holder2.ll_2.setTag(articleId);
                    break;
                case 2:
                    holder3 = (ViewHolder3) convertView.getTag();
                    holder3.ll_2.setTag(articleId);
                    break;
                case 3:
                    holder4 = (ViewHolder4) convertView.getTag();
                    holder4.ll_2.setTag(articleId);
                    break;
            }
        }

        switch (type) {
            case 0://无图
                ImgUtil.setImg(holder1.civ_img, img, 150, 150);
                holder1.tv_content.setText(content);
                holder1.tv_nickname.setText(nickname);
                holder1.tv_createdAt.setText(ptime);
                holder1.ll_2.removeAllViews();
                //评论图标点击事件
                holder1.comment_img.setOnClickListener(new View.OnClickListener() {
                    String articleIdCopy = articleId;
                    @Override
                    public void onClick(View view) {
                        CommentListener.commentClick(articleIdCopy);
                    }
                });
                List<CircleComment> mCommentCopyList0 = mCommentListMap.get(holder1.ll_2.getTag() + "");
                    //如果此文章的评论不为空就进入
                    if(mCommentCopyList0 != null){
                        for(int a = 0;a < mCommentCopyList0.size();a ++){
                            //获得某条评论
                            final CircleComment circleComment0 = mCommentCopyList0.get(a);
                            //评论用户的昵称
                            TextView userNameTextView = new TextView(MyApplication.getContext());
                            userNameTextView.setTextColor(Color.parseColor("#4F77AB"));
                            userNameTextView.setText(circleComment0.getUser().getNickname());
                            //评论内容
                            TextView contentTextView = new TextView(MyApplication.getContext());
                            contentTextView.setTextColor(Color.BLACK);
                            contentTextView.setText(":"+circleComment0.getContent());

                            LinearLayout commentLayout = new LinearLayout(MyApplication.getContext());
                            commentLayout.setBackgroundResource(R.drawable.press_change_color_reply);
                            commentLayout.setOrientation(commentLayout.HORIZONTAL);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            commentLayout.setLayoutParams(layoutParams);
                            //添加用户名和内容
                            commentLayout.addView(userNameTextView);
                            commentLayout.addView(contentTextView);
                            commentLayout.setOnClickListener(new View.OnClickListener() {
                                CircleComment circleCommentCopy = circleComment0;
                                @Override
                                public void onClick(View view) {
                                    //传入即将被回复的用户和用户昵称还有评论的ID
                                    CommentReplyListener.commentClick(circleCommentCopy.getUser(),circleCommentCopy.getUser().getNickname(),circleCommentCopy.getObjectId());
                                    MyLog.i("iii","");
                                }
                            });
                            //添加到评论区
                            holder1.ll_2.addView(commentLayout);
                            mReplyCopyList = mReplyListMap.get(circleComment0.getObjectId());
                            //如果评论有回复就进入
                            if(mReplyCopyList !=null){
                                for(int b = 0;b<mReplyCopyList.size();b++){
                                    reply = mReplyCopyList.get(b);
                                    //SettingReplyUserName
                                    TextView userName = new TextView(MyApplication.getContext());
                                    userName.setTextColor(Color.parseColor("#4F77AB"));
                                    userName.setText(reply.getNickNameArray()[0]);
                                    //SettingText
                                    TextView text = new TextView(MyApplication.getContext());
                                    text.setTextColor(Color.BLACK);
                                    text.setText("回复");
                                    //SettingTargetUserName
                                    TextView targetUserName = new TextView(MyApplication.getContext());
                                    targetUserName.setTextColor(Color.parseColor("#4F77AB"));
                                    targetUserName.setText(reply.getNickNameArray()[1]);
                                    //SettingReplyContent
                                    TextView replyContent = new TextView(MyApplication.getContext());
                                    replyContent.setTextColor(Color.BLACK);
                                    replyContent.setText(":"+reply.getContent());
                                    //SettingReplylayout
                                    LinearLayout replyLayout = new LinearLayout(MyApplication.getContext());
                                    replyLayout.setBackgroundResource(R.drawable.press_change_color_reply);
                                    replyLayout.setOrientation(replyLayout.HORIZONTAL);
                                    LinearLayout.LayoutParams replyLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                                    replyLayout.setLayoutParams(replyLayoutParams);
                                    replyLayout.addView(userName);
                                    replyLayout.addView(text);
                                    replyLayout.addView(targetUserName);
                                    replyLayout.addView(replyContent);
                                    //每条回复监听
                                    replyLayout.setOnClickListener(new View.OnClickListener() {
                                        Reply replyCopy = reply;
                                        @Override
                                        public void onClick(View view) {
                                            ReplyListener.replyClick(replyCopy.getNickNameArray()[0],replyCopy.getBelong());
                                        }
                                    });
                                    holder1.ll_2.addView(replyLayout);
                                }
                            }
                        }
                    }
                    holder1.ll_2.setPadding(5, 5, 5, 5);
                //------------------------
                break;
            case 1://一张图
                ViewGroup.LayoutParams para1 = holder2.iv_img.getLayoutParams();
                para1.height = ScreenUtil.getScreenwidth() / 2;
                para1.width = ScreenUtil.getScreenwidth() / 2;
                holder2.iv_img.setLayoutParams(para1);
                ImgUtil.setImg(holder2.iv_img, imgarray.get(0));
                ImgUtil.setImg(holder2.civ_img, img,100,100);
                holder2.tv_content.setText(content);
                holder2.tv_nickname.setText(nickname);
                holder2.tv_createdAt.setText(ptime);
                holder2.ll_2.removeAllViews();
                holder2.comment_img.setOnClickListener(new View.OnClickListener() {
                    String articleIdCopy = articleId;
                    @Override
                    public void onClick(View view) {
                        CommentListener.commentClick(articleIdCopy);
                    }
                });
                List<CircleComment> mCommentCopyList1 = mCommentListMap.get(holder2.ll_2.getTag() + "");
                    if(mCommentCopyList1 != null){
                        for(int a = 0;a<mCommentCopyList1.size();a++){
                            //获得某条评论
                            final CircleComment circleComment1 = mCommentCopyList1.get(a);
                            //评论用户的昵称
                            TextView userNameTextView = new TextView(MyApplication.getContext());
                            userNameTextView.setTextColor(Color.parseColor("#4F77AB"));
                            userNameTextView.setText(circleComment1.getUser().getNickname());
                            //评论内容
                            TextView contentTextView = new TextView(MyApplication.getContext());
                            contentTextView.setTextColor(Color.BLACK);
                            contentTextView.setText(":"+circleComment1.getContent());

                            LinearLayout commentLayout = new LinearLayout(MyApplication.getContext());
                            commentLayout.setBackgroundResource(R.drawable.press_change_color_reply);
                            commentLayout.setOrientation(commentLayout.HORIZONTAL);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            commentLayout.setLayoutParams(layoutParams);
                            commentLayout.setOnClickListener(new View.OnClickListener() {
                                CircleComment circleCommentCopy = circleComment1;
                                @Override
                                public void onClick(View view) {
                                    //传入即将被回复的用户和用户昵称还有评论的ID
                                    CommentReplyListener.commentClick(circleCommentCopy.getUser(),circleCommentCopy.getUser().getNickname(),circleCommentCopy.getObjectId());
                                }
                            });
                            //添加用户名和内容
                            commentLayout.addView(userNameTextView);
                            commentLayout.addView(contentTextView);
                            //添加到评论区
                            holder2.ll_2.addView(commentLayout);
                            mReplyCopyList = mReplyListMap.get(circleComment1.getObjectId());
                            //如果评论有回复就进入
                            if(mReplyCopyList !=null){
                                for(int b = 0;b<mReplyCopyList.size();b++){
                                    reply = mReplyCopyList.get(b);
                                    //SettingReplyUserName
                                    TextView userName = new TextView(MyApplication.getContext());
                                    userName.setTextColor(Color.parseColor("#4F77AB"));
                                    userName.setText(reply.getNickNameArray()[0]);
                                    //SettingText
                                    TextView text = new TextView(MyApplication.getContext());
                                    text.setTextColor(Color.BLACK);
                                    text.setText("回复");
                                    //SettingTargetUserName
                                    TextView targetUserName = new TextView(MyApplication.getContext());
                                    targetUserName.setTextColor(Color.parseColor("#4F77AB"));
                                    targetUserName.setText(reply.getNickNameArray()[1]);
                                    //SettingReplyContent
                                    TextView replyContent = new TextView(MyApplication.getContext());
                                    replyContent.setTextColor(Color.BLACK);
                                    replyContent.setText(":"+reply.getContent());
                                    //SettingReplylayout
                                    LinearLayout replyLayout = new LinearLayout(MyApplication.getContext());
                                    replyLayout.setBackgroundResource(R.drawable.press_change_color_reply);
                                    replyLayout.setOrientation(replyLayout.HORIZONTAL);
                                    LinearLayout.LayoutParams replyLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                                    replyLayout.setLayoutParams(replyLayoutParams);
                                    replyLayout.addView(userName);
                                    replyLayout.addView(text);
                                    replyLayout.addView(targetUserName);
                                    replyLayout.addView(replyContent);
                                    //每条回复监听
                                    replyLayout.setOnClickListener(new View.OnClickListener() {
                                        Reply replyCopy = reply;
                                        @Override
                                        public void onClick(View view) {
                                            ReplyListener.replyClick(replyCopy.getNickNameArray()[0],replyCopy.getBelong());
                                        }
                                    });
                                    holder2.ll_2.addView(replyLayout);
                                }
                            }
                        }
                    }
                    holder2.ll_2.setPadding(5, 5, 5, 5);
                holder2.iv_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mcontext, Gally.class);
                        intent.putStringArrayListExtra("imgarray",(ArrayList<String>)imgarray);
                        intent.putExtra("pos",0);
                        mcontext.startActivity(intent);
                    }
                });
                break;
            case 2://四张图
                ImgUtil.setImg(holder3.civ_img, img, 150, 150);
                holder3.tv_content.setText(content);
                holder3.tv_nickname.setText(nickname);
                holder3.tv_createdAt.setText(ptime);
                adapter = new MyCricleitemGridAdapter(mcontext, imgarray);
                holder3.gv_img.setAdapter(adapter);
                holder3.ll_2.removeAllViews();
                holder3.comment_img.setOnClickListener(new View.OnClickListener() {
                    String articleIdCopy = articleId;
                    @Override
                    public void onClick(View view) {
                        CommentListener.commentClick(articleIdCopy);
                    }
                });
//                mArticleId = (String) mlist.get(position).get("id");
                List<CircleComment> mCommentCopyList2 = mCommentListMap.get(holder3.ll_2.getTag() + "");
                    if(mCommentCopyList2 != null){
                        for(int a = 0;a<mCommentCopyList2.size();a++){
                            //获得某条评论
                            final CircleComment circleComment2 = mCommentCopyList2.get(a);
                            //评论用户的昵称
                            TextView userNameTextView = new TextView(MyApplication.getContext());
                            userNameTextView.setTextColor(Color.parseColor("#4F77AB"));
                            userNameTextView.setText(circleComment2.getUser().getNickname());
                            //评论内容
                            TextView contentTextView = new TextView(MyApplication.getContext());
                            contentTextView.setTextColor(Color.BLACK);
                            contentTextView.setText(":"+circleComment2.getContent());

                            LinearLayout commentLayout = new LinearLayout(MyApplication.getContext());
                            commentLayout.setBackgroundResource(R.drawable.press_change_color_reply);
                            commentLayout.setOrientation(commentLayout.HORIZONTAL);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            commentLayout.setLayoutParams(layoutParams);
                            commentLayout.setOnClickListener(new View.OnClickListener() {
                                CircleComment circleCommentCopy = circleComment2;
                                @Override
                                public void onClick(View view) {
                                    //传入即将被回复的用户和用户昵称还有评论的ID
                                    CommentReplyListener.commentClick(circleCommentCopy.getUser(),circleCommentCopy.getUser().getNickname(),circleCommentCopy.getObjectId());
                                }
                            });
                            //添加用户名和内容
                            commentLayout.addView(userNameTextView);
                            commentLayout.addView(contentTextView);
                            //添加到评论区
                            holder3.ll_2.addView(commentLayout);
                            mReplyCopyList = mReplyListMap.get(circleComment2.getObjectId());
                            //如果评论有回复就进入
                            if(mReplyCopyList !=null){
                                for(int b = 0;b<mReplyCopyList.size();b++){
                                    reply = mReplyCopyList.get(b);
                                    //SettingReplyUserName
                                    TextView userName = new TextView(MyApplication.getContext());
                                    userName.setTextColor(Color.parseColor("#4F77AB"));
                                    userName.setText(reply.getNickNameArray()[0]);
                                    //SettingText
                                    TextView text = new TextView(MyApplication.getContext());
                                    text.setTextColor(Color.BLACK);
                                    text.setText("回复");
                                    //SettingTargetUserName
                                    TextView targetUserName = new TextView(MyApplication.getContext());
                                    targetUserName.setTextColor(Color.parseColor("#4F77AB"));
                                    targetUserName.setText(reply.getNickNameArray()[1]);
                                    //SettingReplyContent
                                    TextView replyContent = new TextView(MyApplication.getContext());
                                    replyContent.setTextColor(Color.BLACK);
                                    replyContent.setText(":"+reply.getContent());
                                    //SettingReplylayout
                                    LinearLayout replyLayout = new LinearLayout(MyApplication.getContext());
                                    replyLayout.setBackgroundResource(R.drawable.press_change_color_reply);
                                    replyLayout.setOrientation(replyLayout.HORIZONTAL);
                                    LinearLayout.LayoutParams replyLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                                    replyLayout.setLayoutParams(replyLayoutParams);
                                    replyLayout.addView(userName);
                                    replyLayout.addView(text);
                                    replyLayout.addView(targetUserName);
                                    replyLayout.addView(replyContent);
                                    //每条回复监听
                                    replyLayout.setOnClickListener(new View.OnClickListener() {
                                        Reply replyCopy = reply;
                                        @Override
                                        public void onClick(View view) {
                                            ReplyListener.replyClick(replyCopy.getNickNameArray()[0],replyCopy.getBelong());
                                        }
                                    });
                                    holder3.ll_2.addView(replyLayout);
                                }
                            }
                        }
                    }
                    holder3.ll_2.setPadding(5, 5, 5, 5);
                break;
            case 3://多张图
                ImgUtil.setImg(holder4.civ_img, img, 150, 150);
                holder4.tv_content.setText(content);
                holder4.tv_nickname.setText(nickname);
                holder4.tv_createdAt.setText(ptime);
                adapter = new MyCricleitemGridAdapter(mcontext, imgarray);
                holder4.gv_img.setAdapter(adapter);
                holder4.ll_2.removeAllViews();
                holder4.comment_img.setOnClickListener(new View.OnClickListener() {
                    String articleIdCopy = articleId;
                    @Override
                    public void onClick(View view) {
                        CommentListener.commentClick(articleIdCopy);
                    }
                });
                List<CircleComment> mCommentCopyList3 = mCommentListMap.get(holder4.ll_2.getTag() + "");
                    if(mCommentCopyList3 != null){
                        for(int a = 0;a<mCommentCopyList3.size();a++){
                            //获得某条评论
                           final CircleComment circleComment3 = mCommentCopyList3.get(a);
                            //评论用户的昵称
                            TextView userNameTextView = new TextView(MyApplication.getContext());
                            userNameTextView.setTextColor(Color.parseColor("#4F77AB"));
                            userNameTextView.setText(circleComment3.getUser().getNickname());
                            //评论内容
                            TextView contentTextView = new TextView(MyApplication.getContext());
                            contentTextView.setTextColor(Color.BLACK);
                            contentTextView.setText(":"+circleComment3.getContent());

                            LinearLayout commentLayout = new LinearLayout(MyApplication.getContext());
                            commentLayout.setBackgroundResource(R.drawable.press_change_color_reply);
                            commentLayout.setOrientation(commentLayout.HORIZONTAL);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            commentLayout.setLayoutParams(layoutParams);
                            commentLayout.setOnClickListener(new View.OnClickListener() {
                                CircleComment circleCommentCopy = circleComment3;
                                @Override
                                public void onClick(View view) {
                                    //传入即将被回复的用户和用户昵称还有评论的ID
                                    CommentReplyListener.commentClick(circleCommentCopy.getUser(),circleCommentCopy.getUser().getNickname(),circleCommentCopy.getObjectId());
                                }
                            });
                            //添加用户名和内容
                            commentLayout.addView(userNameTextView);
                            commentLayout.addView(contentTextView);
                            //添加到评论区
                            holder4.ll_2.addView(commentLayout);
                            mReplyCopyList = mReplyListMap.get(circleComment3.getObjectId());
                            //如果评论有回复就进入
                            if(mReplyCopyList !=null){
                                for(int b = 0;b<mReplyCopyList.size();b++){
                                    reply = mReplyCopyList.get(b);
                                    //SettingReplyUserName
                                    TextView userName = new TextView(MyApplication.getContext());
                                    userName.setTextColor(Color.parseColor("#4F77AB"));
                                    userName.setText(reply.getNickNameArray()[0]);
                                    //SettingText
                                    TextView text = new TextView(MyApplication.getContext());
                                    text.setTextColor(Color.BLACK);
                                    text.setText("回复");
                                    //SettingTargetUserName
                                    TextView targetUserName = new TextView(MyApplication.getContext());
                                    targetUserName.setTextColor(Color.parseColor("#4F77AB"));
                                    targetUserName.setText(reply.getNickNameArray()[1]);
                                    //SettingReplyContent
                                    TextView replyContent = new TextView(MyApplication.getContext());
                                    replyContent.setTextColor(Color.BLACK);
                                    replyContent.setText(":"+reply.getContent());
                                    //SettingReplylayout
                                    LinearLayout replyLayout = new LinearLayout(MyApplication.getContext());
                                    replyLayout.setBackgroundResource(R.drawable.press_change_color_reply);
                                    replyLayout.setOrientation(replyLayout.HORIZONTAL);
                                    LinearLayout.LayoutParams replyLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                                    replyLayout.setLayoutParams(replyLayoutParams);
                                    replyLayout.addView(userName);
                                    replyLayout.addView(text);
                                    replyLayout.addView(targetUserName);
                                    replyLayout.addView(replyContent);
                                    //每条回复监听
                                    replyLayout.setOnClickListener(new View.OnClickListener() {
                                        Reply replyCopy = reply;
                                        @Override
                                        public void onClick(View view) {
                                            ReplyListener.replyClick(replyCopy.getNickNameArray()[0],replyCopy.getBelong());
                                        }
                                    });
                                    holder4.ll_2.addView(replyLayout);
                                }
                            }
                        }
                    }
                    holder4.ll_2.setPadding(5, 5, 5, 5);
                break;
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, Content_Circle.class);
                intent.putExtra("objectId",objectId);
                mcontext.startActivity(intent);
            }
        });
        return convertView;
    }


    class ViewHolder1 {
        ImageView comment_img;
        CircleImageView civ_img;
        TextView tv_nickname;
        TextView tv_content;
        TextView tv_createdAt;
        LinearLayout ll_2;
    }

    class ViewHolder2 {
        ImageView comment_img;
        CircleImageView civ_img;
        TextView tv_nickname;
        TextView tv_createdAt;
        NetworkImageView iv_img;
        TextView tv_content;
        LinearLayout ll_2;
    }

    class ViewHolder3 {
        ImageView comment_img;
        CircleImageView civ_img;
        TextView tv_nickname;
        TextView tv_content;
        TextView tv_createdAt;
        MyCircleitemGridView gv_img;
        LinearLayout ll_2;
    }

    class ViewHolder4 {
        ImageView comment_img;
        CircleImageView civ_img;
        TextView tv_nickname;
        TextView tv_content;
        TextView tv_createdAt;
        MyCircleitemGridView gv_img;
        LinearLayout ll_2;
    }

    public interface CommentReplyClickListener{
        void commentClick(User targetUser,String nickName,String commentId);
    }
    public interface ReplyClickListener{
        void replyClick(String targetUserNickName,String commentId);
    }
    public interface CommentLsiener{
        void commentClick(String articleId);
    }
    public void setReplyListener(ReplyClickListener replyListener) {
        ReplyListener = replyListener;
    }

    public void setCommentReplyListener(CommentReplyClickListener commentReplyListener) {
        CommentReplyListener = commentReplyListener;
    }

    public void setCommentListener(CommentLsiener commentListener) {
        CommentListener = commentListener;
    }
}
