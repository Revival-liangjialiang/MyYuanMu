package com.example.yuanmu.lunbo.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yuanmu.lunbo.BmobBean.User;
import com.example.yuanmu.lunbo.Custom.MyView;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.ImgUtil;

import java.util.List;

import io.rong.imkit.RongIM;

/**
 * Created by Administrator on 2016/10/20 0020.
 */
public class ConcernRvAdapter extends RecyclerView.Adapter<ConcernRvAdapter.MyViewHolder>{
    List<User> mUserList;
    Context context;

public ConcernRvAdapter(Context context,List<User> list){
    mUserList = list;
    this.context = context;
}
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.personal_follow_item_layout,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
       final User user = mUserList.get(position);
        holder.userName.setText(user.getNickname());
        holder.userData_tv.setText(user.getGender()+" "+user.getAge()+" "+user.getConstellation());
        //设置头像
        ImgUtil.setImg(user.getImg(), 100, new ImgUtil.PictureListener() {
            @Override
            public void loadFinish(Bitmap bitmap) {
                holder.headportrait.bitmap = bitmap;
                holder.headportrait.postInvalidate();
            }
        });
        holder.view.setOnClickListener(new View.OnClickListener() {
            User userChild = user;
            @Override
            public void onClick(View view) {
                if (RongIM.getInstance() != null) {
                    RongIM.getInstance().startPrivateChat(context, userChild.getUsername(), userChild.getNickname());
                }
                }
        });
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        View view;
        MyView headportrait;
        TextView userName;
        TextView userData_tv;
        // TODO: 2016/10/20 0020
        //vip字段
        TextView vip_tv;
        //vip图标
        ImageView vip_iv;
        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            headportrait = (MyView) itemView.findViewById(R.id.headportrait);
            userName = (TextView) itemView.findViewById(R.id.userName);
            userData_tv = (TextView) itemView.findViewById(R.id.userData_tv);
            vip_tv = (TextView) itemView.findViewById(R.id.vip_tv);
            vip_iv = (ImageView) itemView.findViewById(R.id.vip_iv);
        }
    }
}
