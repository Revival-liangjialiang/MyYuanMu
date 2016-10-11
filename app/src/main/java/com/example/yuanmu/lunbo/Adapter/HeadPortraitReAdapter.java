package com.example.yuanmu.lunbo.Adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yuanmu.lunbo.Custom.SquareBlockView;
import com.example.yuanmu.lunbo.R;

/**
 * Created by Administrator on 2016/9/18 0018.
 */
public class HeadPortraitReAdapter extends RecyclerView.Adapter<HeadPortraitReAdapter.MyViewHolder>{
    Context mContext;
    public HeadPortraitReAdapter(Context context){
        mContext = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.personal_head_portrait_re_adapter_layout,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
    holder.img.bitmap = BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.add);
        holder.img.postInvalidate();
    }

    @Override
    public int getItemCount() {
        return 1;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        SquareBlockView img;
        public MyViewHolder(View itemView) {
            super(itemView);
            img = (SquareBlockView) itemView.findViewById(R.id.img);
        }
    }
}
