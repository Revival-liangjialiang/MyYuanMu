package com.example.yuanmu.lunbo.Fragment.Personal;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.yuanmu.lunbo.Activity.DataActivity;
import com.example.yuanmu.lunbo.Activity.LoginActivity;
import com.example.yuanmu.lunbo.Activity.MainActivity;
import com.example.yuanmu.lunbo.Activity.PersonalSettingActivity.SettingActivity;
import com.example.yuanmu.lunbo.Application.MyApplication;
import com.example.yuanmu.lunbo.Custom.MyView;
import com.example.yuanmu.lunbo.R;

/**
 * Created by Administrator on 2016/8/25 0025.
 */
public class Personal_Fragment extends Fragment implements View.OnClickListener{
    MyView mMyView;
    ImageView mSetting_img;
    MainActivity m;
    RelativeLayout data_layout;
    public LinearLayout mNo_sign_in_layout;
    public LinearLayout sign_in_success_Layout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.from(container.getContext()).inflate(R.layout.personal_fragment_layout,container,false);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        m = (MainActivity) getActivity();
        m.setFragment(this);
        initView();
        if(MyApplication.isLogin){
            mNo_sign_in_layout.setVisibility(View.GONE);
            sign_in_success_Layout.setVisibility(View.VISIBLE);
        }
    }

    private void initView() {
        mSetting_img = (ImageView) m.findViewById(R.id.mSetting_img);
        sign_in_success_Layout = (LinearLayout) m.findViewById(R.id.sign_in_success_layout);
        mNo_sign_in_layout = (LinearLayout)m.findViewById(R.id.no_sign_in_layout);
        mNo_sign_in_layout.setOnClickListener(this);
        data_layout = (RelativeLayout) m.findViewById(R.id.data_layout);
        mMyView = (MyView)m.findViewById(R.id.personal_MyView);
        mMyView.bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.myviewtest);
        mMyView.postInvalidate();
        data_layout.setOnClickListener(this);
        mSetting_img.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.data_layout:
                Intent startActivityIntent = new Intent(m, DataActivity.class);
                startActivity(startActivityIntent);
                m.overridePendingTransition(R.anim.start_left,R.anim.start_right);
            break;
            case R.id.no_sign_in_layout:
                startActivity(new Intent(m,LoginActivity.class));
                break;
            case R.id.mSetting_img:
                startActivityForResult(new Intent(m, SettingActivity.class),0);
                break;
            default:break;
        }
    }

}
