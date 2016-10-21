package com.example.yuanmu.lunbo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.yuanmu.lunbo.Activity.DataActivity;
import com.example.yuanmu.lunbo.Activity.LocalImageList;
import com.example.yuanmu.lunbo.Custom.SquareBlockView;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.ImgUtil;
import com.example.yuanmu.lunbo.Util.LocalImageBean;
import com.example.yuanmu.lunbo.Util.MyLog;

/**
 * Created by Administrator on 2016/8/29 0029.
 */
public class PersonalDataReAdapter extends RecyclerView.Adapter<PersonalDataReAdapter.MyViewHolder>{
    String[] mPictureAddressArray;
    Context mContext;
    DataActivity mDataActivity;
    public PersonalDataReAdapter(DataActivity dataActivity,String[] strings){
        mDataActivity = dataActivity;
        mPictureAddressArray = strings;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.personal_data_re_item_layout,parent,false);
        mContext = parent.getContext();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if(position!=0) {
            MyLog.i("cccc","mPictureAddressArray[position-1] = "+mPictureAddressArray[position-1]);
            ImgUtil.setImg(mPictureAddressArray[position-1], 200, new ImgUtil.PictureListener() {
                @Override
                public void loadFinish(Bitmap bitmap) {
                    holder.imageView.bitmap = bitmap;
                    holder.imageView.postInvalidate();
                }
            });
        }
        else{
            holder.imageView.bitmap = BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.add);
            holder.imageView.postInvalidate();
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "Add", Toast.LENGTH_SHORT).show();
                    // TODO: 2016/10/21 0021
                    Intent intent = new Intent(mContext, LocalImageList.class);
                    intent.putExtra("count", 9);
                    //初始化装图片地址的类
                    LocalImageBean.init();
                    mDataActivity.startActivityForResult(intent,33);
                }
            });
        }
    }
//
    @Override
    public int getItemCount() {
        return mPictureAddressArray.length+1;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
public SquareBlockView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (SquareBlockView) itemView.findViewById(R.id.PD_RV_image);
        }
    }
}
