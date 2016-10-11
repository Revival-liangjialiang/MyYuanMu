package com.example.yuanmu.lunbo.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.yuanmu.lunbo.Activity.ActivityContentActivity;
import com.example.yuanmu.lunbo.Activity.LoveActivity;
import com.example.yuanmu.lunbo.R;

/**
 * Created by Administrator on 2016/9/9 0009.
 */
public class LoveActivityReAdapter extends RecyclerView.Adapter<LoveActivityReAdapter.MyViewHolder> {
    public static final String INDEX = "start_value";
    LoveActivity mActivity;
    public LoveActivityReAdapter(LoveActivity mActivity){
        this.mActivity = mActivity;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_love_activity_re_item_layout,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
    holder.linearLayout.setOnClickListener(new View.OnClickListener() {
        int item_position = position;
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(mActivity,ActivityContentActivity.class);
            intent.putExtra(INDEX,position);
            mActivity.startActivity(intent);
        }
    });
    }

    @Override
    public int getItemCount() {
        return 10;
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
LinearLayout linearLayout ;
        public MyViewHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView;
        }
    }
}
