package com.example.yuanmu.lunbo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.yuanmu.lunbo.Custom.CircleImageView;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.ImgUtil;

/**
 * Created by Administrator on 2016/10/6 0006.
 */
public class UserDataActivity extends AppCompatActivity{
   public static final String USER_NAME = "USER_NAME",HEAD_PORTRAIT_ADDRESS = "HEAD_PORTRAIT_ADDRESS";
    TextView mUserName;
    CircleImageView mCiv_img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otherdata);
        Intent intent = getIntent();
        initView();
        mUserName.setText(intent.getStringExtra(USER_NAME));
        ImgUtil.setImg(mCiv_img,intent.getStringExtra(HEAD_PORTRAIT_ADDRESS),150,150);
    }

    private void initView() {
        mUserName = (TextView) findViewById(R.id.mUserName);
        mCiv_img = (CircleImageView) findViewById(R.id.mCiv_img);
    }
}
