package com.example.yuanmu.lunbo.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.ImgUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by yuanmu on 2016/8/26.
 */
public class Near_personalsAdapter extends
        RecyclerView.Adapter<Near_personalsAdapter.ViewHolder>
{

    private LayoutInflater mInflater;
    private List<Map<String, Object>> mDatas;

    public Near_personalsAdapter(Context context, List<Map<String, Object>> datats)
    {
        mInflater = LayoutInflater.from(context);
        mDatas = datats;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public ViewHolder(View arg0)
        {
            super(arg0);
        }

        NetworkImageView mImg;
        TextView mTxt;
    }

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View view = mInflater.inflate(R.layout.item_near_personals,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.mImg = (NetworkImageView) view
                .findViewById(R.id.id_index_gallery_item_image);
        viewHolder.mTxt = (TextView) view.findViewById(R.id.id_index_gallery_item_text);
        return viewHolder;
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i)
    {
        ImgUtil.setImg(viewHolder.mImg,mDatas.get(i).get("img") + "");
        viewHolder.mTxt.setText(mDatas.get(i).get("txt") + "");
    }

}