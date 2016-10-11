package com.example.yuanmu.lunbo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.volley.toolbox.NetworkImageView;
import com.example.yuanmu.lunbo.Activity.Gally;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.ImgUtil;
import com.example.yuanmu.lunbo.Util.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuanmu on 2016/8/24.
 */
public class MyCricleitemGridAdapter extends BaseAdapter {
    private Context mcontext;
    private List<String> mlist;

    public MyCricleitemGridAdapter(Context context, List<String> list) {
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
        int type = getItemViewType(position);
        String img = mlist.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(mcontext).inflate(
                    R.layout.item_grid_img, parent, false);
            holder = new ViewHolder();
            holder.iv_grid_img = (NetworkImageView) convertView.findViewById(R.id.iv_grid_img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }
        if(mlist.size() == 4){
            ViewGroup.LayoutParams para = holder.iv_grid_img.getLayoutParams();
            para.height = (ScreenUtil.getScreenwidth() - 70) / 2 - 30;
            para.width = (ScreenUtil.getScreenwidth() - 70) / 2 - 30;
            holder.iv_grid_img.setLayoutParams(para);
            ImgUtil.setImg(holder.iv_grid_img, img);

        }else{
            ViewGroup.LayoutParams para = holder.iv_grid_img.getLayoutParams();
            para.height = (ScreenUtil.getScreenwidth() - 75) / 3 - 20;
            para.width = (ScreenUtil.getScreenwidth() - 75) / 3 - 20;
            holder.iv_grid_img.setLayoutParams(para);
            ImgUtil.setImg(holder.iv_grid_img, img);
        }
        holder.iv_grid_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, Gally.class);
                intent.putStringArrayListExtra("imgarray",(ArrayList<String>)mlist);
                intent.putExtra("pos",position);
                mcontext.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        NetworkImageView iv_grid_img;
    }

}
