package com.example.yuanmu.lunbo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.yuanmu.lunbo.Activity.UserDataActivity;
import com.example.yuanmu.lunbo.BmobBean.User;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.ImgUtil;
import com.example.yuanmu.lunbo.Util.UserInfo;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by yuanmu on 2016/8/26.
 */
public class Near_nearAdapter extends BaseAdapter{
    User mUser;
    private Context mcontext;
    private List<UserInfo> mlist;
        public Near_nearAdapter(Context context, List<UserInfo> list) {
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
        mUser = mlist.get(position).getUser();
        UserInfo userInfo = mlist.get(position);
        ViewHolder holder = null;
        if(convertView == null){
             convertView = LayoutInflater.from(mcontext).inflate(R.layout.item_near_list,
                    parent, false);
            holder = new ViewHolder();
            holder.sex_tv = (TextView) convertView.findViewById(R.id.sex_tv);
            holder.age_tv = (TextView) convertView.findViewById(R.id.age_tv);
            holder.address_tv = (TextView) convertView.findViewById(R.id.address_tv);
            holder.position_tv = (TextView) convertView.findViewById(R.id.position_tv);
            holder.education_tv = (TextView) convertView.findViewById(R.id.education_tv);
            holder.height_tv = (TextView) convertView.findViewById(R.id.height_tv);
            holder.monologue_tv = (TextView) convertView.findViewById(R.id.monologue_tv);

            holder.distance_tv = (TextView) convertView.findViewById(R.id.distance_tv);
            holder.userName_tv = (TextView) convertView.findViewById(R.id.userName);
            holder.img_iv = (NetworkImageView) convertView.findViewById(R.id.img_iv);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        //设置附近人的个人资料
        DecimalFormat df = new DecimalFormat("0.00");
        holder.sex_tv.setText(mUser.getGender());
        holder.distance_tv.setText(df.format(userInfo.getDistance())+"km");
        holder.userName_tv.setText(mUser.getNickname());
        holder.age_tv.setText(mUser.getAge());
        holder.position_tv.setText(mUser.getWorking_area());
        holder.address_tv.setText(mUser.getCity()+"-"+mUser.getDistrict());
        holder.education_tv.setText(mUser.getEducation());
        holder.height_tv.setText(mUser.getHeight());
        holder.monologue_tv.setText(mUser.getMonologue());

        ImgUtil.setImg(holder.img_iv, mUser.getImg());
        convertView.setOnClickListener(new View.OnClickListener() {
            User user = mUser;
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext,UserDataActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user",user);
                intent.putExtras(bundle);
                mcontext.startActivity(intent);
            }
        });
        return convertView;
    }
    class ViewHolder{
        TextView age_tv;
        TextView sex_tv;
        TextView address_tv;
        TextView position_tv;
        TextView education_tv;
        TextView height_tv;
        TextView monologue_tv;
        TextView distance_tv;
        TextView userName_tv;
        NetworkImageView img_iv;
    }
}
