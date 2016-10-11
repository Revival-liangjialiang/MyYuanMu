package com.example.yuanmu.lunbo.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.ImgUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yuanmu on 2016/9/10.
 */
public class MarriageListAdapter extends RecyclerView.Adapter<MarriageListAdapter.MyViewHolder> {
    private LayoutInflater mlayoutinflater;
    private Context mcontext;
    private List<Map<String, Object>> mlist;
    private List<Integer> mheight;

    public MarriageListAdapter(Context context, List<Map<String, Object>> list) {
        this.mcontext = context;
        this.mlist = list;
        mlayoutinflater = LayoutInflater.from(mcontext);

        mheight = new ArrayList<Integer>();
        for (int i = 0; i < mlist.size(); i++) {
            mheight.add((int) (100 + Math.random() * 300));
        }
    }

    public int getItemCount() {
        // TODO Auto-generated method stub
        return mlist.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder,
                                 int position) {
        // TODO Auto-generated method stub
        String img = (String) mlist.get(position).get("img");
        String content = (String) mlist.get(position).get("content");
        final String objectId = (String) mlist.get(position).get("objectId");
        if (!TextUtils.isEmpty(img)) {
            ImgUtil.setImg(holder.iv, img, 400, 400);
        }
        holder.tv_content.setText(content);
        holder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mcontext,Content_Cabinet.class);
//                intent.putExtra("objectId", objectId);
//                mcontext.startActivity(intent);

            }});
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup arg0,
                                                          int arg1) {
        // TODO Auto-generated method stub
        View view = mlayoutinflater.inflate(R.layout.item_marriagelist, arg0, false);
        MyViewHolder viewholder = new MyViewHolder(view);

        return viewholder;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_content;
        ImageView iv;

        public MyViewHolder(View view) {
            super(view);
            iv = (ImageView) view.findViewById(R.id.iv_item_cabinet);
            tv_content = (TextView) view.findViewById(R.id.tv_content);
        }

    }

}