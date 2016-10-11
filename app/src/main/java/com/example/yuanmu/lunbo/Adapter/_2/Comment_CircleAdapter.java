package com.example.yuanmu.lunbo.Adapter._2;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuanmu.lunbo.Application.MyApplication;
import com.example.yuanmu.lunbo.BmobBean.Reply;
import com.example.yuanmu.lunbo.BmobBean.User;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.Adaptation;
import com.example.yuanmu.lunbo.Util.ImgUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by yuanmu on 2016/8/29.
 */
public class Comment_CircleAdapter extends BaseAdapter{
    private Context mcontext;
    private List<Map<String, Object>> mlist;
    private CommentClick friclick;
    private List<User> mUserList;
    private LinearLayout mLinearLayoutCopy;
    private Map<String,List> mMap;
    private ReplyClick  mReplyListener;
    private List<Reply> list;
    private int a;
    public Comment_CircleAdapter(Context context, List<Map<String, Object>> list,List<User> users,Map<String,List> map) {
        this.mcontext = context;
        this.mlist = list;
        this.mUserList = users;
        mMap = map;
    }
    public Comment_CircleAdapter(Context context, List<Map<String, Object>> list) {
        this.mcontext = context;
        this.mlist = list;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mlist.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return mlist.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        final String id = (String) mlist.get(position).get("id");
        final String nickname = (String) mlist.get(position).get("nickname");
        String targetuser = (String) mlist.get(position).get("targetuser");
        String content = (String) mlist.get(position).get("content");
        String createdAt = (String) mlist.get(position).get("createdAt");
        String headPortraitAddress = (String) mlist.get(position).get("headportrait");
        convertView = LayoutInflater.from(mcontext).inflate(
                R.layout.item_comment_circle, parent, false);
        holder = new ViewHolder();
        holder.commentLayout = (LinearLayout) convertView.findViewById(R.id.commentLayout);
        mLinearLayoutCopy = (LinearLayout) convertView.findViewById(R.id.mReplyLayout);
        //HeadPortrait
        holder.img_headPortrait = (ImageView) convertView.findViewById(R.id.img_headPortrait);
        holder.tv_nickname = (TextView) convertView
                .findViewById(R.id.tv_nickname);
        holder.tv_createdate = (TextView) convertView
                .findViewById(R.id.tv_createdate);
        holder.tv_content = (TextView) convertView
                .findViewById(R.id.tv_content);
        convertView.setTag(holder);

        holder.tv_content.setText(content);
        holder.tv_createdate.setText(createdAt);
        //设置层主
        holder.tv_nickname.setText(nickname);

        ImgUtil.setImg(holder.img_headPortrait,headPortraitAddress,80,80);
        holder.commentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyApplication.isLogin) {
                    //Content_Circle活动类调用此接口
                    Toast.makeText(MyApplication.getContext(), "点击!", Toast.LENGTH_SHORT).show();
                    friclick.onItemClick(nickname,id);
                }else{
                    Toast.makeText(MyApplication.getContext(), "未登录,请先登录!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //获取评论的回复
        list = mMap.get(id);
        if(list != null) {
            for(a = 0;a<list.size();a++) {
                LinearLayout childLinearLayout = new LinearLayout(MyApplication.getContext());
                childLinearLayout.setOrientation(childLinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                layoutParams.setMargins(Adaptation.dp2px(mcontext,54),0,0,0);
                childLinearLayout.setLayoutParams(layoutParams);
                //回复人
                TextView replyTextView = new TextView(MyApplication.getContext());
                ViewGroup.MarginLayoutParams replyMarginLayoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT);
                replyTextView.setLayoutParams(replyMarginLayoutParams);
                replyTextView.setTextColor(Color.parseColor("#4F77AB"));
                replyTextView.setText(list.get(a).getNickNameArray()[0]);
                //回复文本
                TextView replyText = new TextView(MyApplication.getContext());
                ViewGroup.MarginLayoutParams replyTextMarginLayoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT);
                replyText.setLayoutParams(replyTextMarginLayoutParams);
                replyText.setTextColor(Color.BLACK);
                replyText.setText("回复");
                //被回复人
                TextView targetTextView = new TextView(MyApplication.getContext());
                ViewGroup.MarginLayoutParams targetMarginLayoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT);
                targetTextView.setLayoutParams(targetMarginLayoutParams);
                targetTextView.setTextColor(Color.parseColor("#4F77AB"));
                targetTextView.setText(list.get(a).getNickNameArray()[1]);
                //回复内容
                TextView contentTextView = new TextView(MyApplication.getContext());
                ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT);
                contentTextView.setLayoutParams(marginLayoutParams);
                contentTextView.setTextColor(Color.BLACK);
                contentTextView.setText(":"+list.get(a).getContent());

                childLinearLayout.addView(replyTextView);
                childLinearLayout.addView(replyText);
                childLinearLayout.addView(targetTextView);
                childLinearLayout.addView(contentTextView);
                childLinearLayout.setBackgroundResource(R.drawable.press_change_color_reply);
                childLinearLayout.setOnClickListener(new View.OnClickListener() {
                    //获得即将被回复的用户
                    //获得即将被回复的用户的昵称
                    String targetUserName = list.get(a).getNickNameArray()[0];
                    String commentId = list.get(a).getBelong();
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MyApplication.getContext(), "Click!Success!", Toast.LENGTH_SHORT).show();
                        mReplyListener.onItemClick(targetUserName,commentId);
                    }
                });
                mLinearLayoutCopy.addView(childLinearLayout);
            }
        }
        return convertView;
    }

    public interface CommentClick {
        void onItemClick(String targetusername,String id);
    }
    public interface ReplyClick{
        void onItemClick(String targetusername,String id);
    }

    public void setfriinterface(CommentClick friclick) {
        this.friclick = friclick;
    }
    public void setmReplyListener(ReplyClick listener){
        mReplyListener = listener;
    }
    class ViewHolder {
        LinearLayout commentLayout;
        ImageView img_headPortrait;
        TextView tv_reply;
        TextView tv_nickname;
        TextView tv_target;
        TextView tv_createdate;
        TextView tv_content;
    }

}
