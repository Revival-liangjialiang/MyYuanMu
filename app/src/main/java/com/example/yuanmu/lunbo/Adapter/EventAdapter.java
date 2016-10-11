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
import java.util.Map;

/**
 * Created by yuanmu on 2016/9/2.
 */
public class EventAdapter extends BaseAdapter {
    private Context mcontext;
    private List<Map<String, Object>> mlist;

    public EventAdapter(Context context, List<Map<String, Object>> list) {
        mcontext = context;
        mlist = list;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        String img = (String) mlist.get(position).get("img");
        if (convertView == null) {
            convertView = LayoutInflater.from(mcontext).inflate(
                    R.layout.item_event, parent, false);
            holder = new ViewHolder();
            holder.iv_img = (NetworkImageView) convertView
                    .findViewById(R.id.iv_img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ViewGroup.LayoutParams para2 = holder.iv_img.getLayoutParams();
        int width = ScreenUtil.getScreenwidth();
        para2.height = width / 2;
        para2.width = width;
        holder.iv_img.setLayoutParams(para2);
        ImgUtil.setImg(holder.iv_img, img);
        return convertView;
    }

    class ViewHolder {
        NetworkImageView iv_img;
    }
}
