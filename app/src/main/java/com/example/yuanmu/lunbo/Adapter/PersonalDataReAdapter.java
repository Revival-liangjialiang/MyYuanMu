package com.example.yuanmu.lunbo.Adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.yuanmu.lunbo.Custom.SquareBlockView;
import com.example.yuanmu.lunbo.R;

/**
 * Created by Administrator on 2016/8/29 0029.
 */
public class PersonalDataReAdapter extends RecyclerView.Adapter<PersonalDataReAdapter.MyViewHolder>{
    Context mContext;
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.personal_data_re_item_layout,parent,false);
        mContext = parent.getContext();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if(position!=0) {
            holder.imageView.bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.myviewtest);
            holder.imageView.postInvalidate();
        }
        else{
            holder.imageView.bitmap = BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.add);
            holder.imageView.postInvalidate();
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "Add", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 9;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
public SquareBlockView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (SquareBlockView) itemView.findViewById(R.id.PD_RV_image);
        }
    }
}
