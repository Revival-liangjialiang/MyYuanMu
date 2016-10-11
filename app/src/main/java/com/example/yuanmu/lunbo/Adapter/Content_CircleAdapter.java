package com.example.yuanmu.lunbo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.volley.toolbox.NetworkImageView;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.ImgUtil;
import com.example.yuanmu.lunbo.Util.ScreenUtil;

import java.util.List;


/**
 * Created by yuanmu on 2016/8/29.
 */
public class Content_CircleAdapter extends BaseAdapter {
    private Context mcontext;
    private List<String> mlist;

    public Content_CircleAdapter(Context context, List<String> imgarray) {
        this.mcontext = context;
        this.mlist = imgarray;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int arg0) {
        return mlist.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public int getItemViewType(int position) {
        int imgsize = mlist.size();
        switch (imgsize) {
            case 1:
                return 2;
            case 2:
                return 0;
            case 3:
                return 0;
            case 4:
                return 0;
            default:
                return 1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder1 holder1 = null;
        ViewHolder2 holder2 = null;
        ViewHolder3 holder3 = null;
        String item_imgarray = mlist.get(position);
        int type = getItemViewType(position);
        if (convertView == null) {
            switch (type) {
                case 0:
                    convertView = LayoutInflater.from(mcontext).inflate(
                            R.layout.item_grid_img, parent, false);
                    holder1 = new ViewHolder1();
                    holder1.iv_grid_img = (NetworkImageView) convertView
                            .findViewById(R.id.iv_grid_img);
                    convertView.setTag(holder1);
                    break;
                case 1:
                    convertView = LayoutInflater.from(mcontext).inflate(
                            R.layout.item_grid_img, parent, false);
                    holder2 = new ViewHolder2();
                    holder2.iv_grid_img = (NetworkImageView) convertView
                            .findViewById(R.id.iv_grid_img);
                    convertView.setTag(holder2);
                    break;
                case 2:
                    convertView = LayoutInflater.from(mcontext).inflate(
                            R.layout.item_grid_img, parent, false);
                    holder3 = new ViewHolder3();
                    holder3.iv_grid_img = (NetworkImageView) convertView
                            .findViewById(R.id.iv_grid_img);
                    convertView.setTag(holder3);
                    break;
            }

        } else {
            switch (type) {
                case 0:
                    holder1 = (ViewHolder1) convertView.getTag();
                    break;
                case 1:
                    holder2 = (ViewHolder2) convertView.getTag();
                    break;
                case 2:
                    holder3 = (ViewHolder3) convertView.getTag();
                    break;
            }

        }
        switch (type) {
            case 0:
                ViewGroup.LayoutParams para1 = holder1.iv_grid_img.getLayoutParams();
                para1.height = ScreenUtil.getScreenwidth() / 2;
                para1.width = ScreenUtil.getScreenwidth() / 2;
                holder1.iv_grid_img.setLayoutParams(para1);
                ImgUtil.setImg(holder1.iv_grid_img, item_imgarray);
                break;
            case 1:
                ViewGroup.LayoutParams para2 = holder2.iv_grid_img.getLayoutParams();
                para2.height = ScreenUtil.getScreenwidth() / 3;
                para2.width = ScreenUtil.getScreenwidth() / 3;
                holder2.iv_grid_img.setLayoutParams(para2);
                ImgUtil.setImg(holder2.iv_grid_img, item_imgarray);
                break;
            case 2:
//                int screenWidth = ScreenUtil.getScreenwidth();
//                ViewGroup.LayoutParams lp = holder3.iv_grid_img.getLayoutParams();
//                lp.width = screenWidth;
//                lp.height = lp.WRAP_CONTENT;
//                holder3.iv_grid_img.setMaxWidth(screenWidth);
//                holder3.iv_grid_img.setMaxHeight(screenWidth * 3); //这里其实可以根据需求而定，我这里测试为最大宽度的5倍
//                holder3.iv_grid_img.setLayoutParams(lp);
//                ImgUtil.setImg(holder3.iv_grid_img, item_imgarray);
                break;
        }

        return convertView;
    }

    class ViewHolder1 {
        NetworkImageView iv_grid_img;
    }

    class ViewHolder2 {
        NetworkImageView iv_grid_img;
    }

    class ViewHolder3 {
        NetworkImageView iv_grid_img;
    }
}
