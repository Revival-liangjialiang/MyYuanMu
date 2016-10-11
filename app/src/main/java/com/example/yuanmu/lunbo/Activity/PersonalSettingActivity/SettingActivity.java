package com.example.yuanmu.lunbo.Activity.PersonalSettingActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuanmu.lunbo.Activity.LoginActivity;
import com.example.yuanmu.lunbo.Application.MyApplication;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.StatusBarColorUtil;

import cn.bmob.v3.BmobUser;


/**
 * Created by yuanmu on 2016/8/29.
 */
public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    TextView mPalaceTV,mFeedbackTV,mPushTV,mTermTV;
    Button mCloseApp_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_activity_setting);
        new StatusBarColorUtil(this,R.color.StatusBarColor);
        initView();
    }

    private void initView() {
        mCloseApp_bt = (Button) findViewById(R.id.mCloseApp_bt);
        mPalaceTV = (TextView) findViewById(R.id.palaceTV);
        mFeedbackTV = (TextView) findViewById(R.id.tv_feedback);
        mPushTV = (TextView) findViewById(R.id.tv_push);
        mTermTV = (TextView) findViewById(R.id.tv_term);
        mPalaceTV.setOnClickListener(this);
        mFeedbackTV.setOnClickListener(this);
        mPushTV.setOnClickListener(this);
        mTermTV.setOnClickListener(this);
        mCloseApp_bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //殿堂
            case R.id.palaceTV:
                startActivity(new Intent(this,AboutActivity.class));
                break;
            //意见反馈
            case R.id.tv_feedback:
                startActivity(new Intent(this,FeedbackActivity.class));
                break;
            //推送设置
            case R.id.tv_push:
                startActivity(new Intent(this,PushActivity.class));
                break;
            //协议
            case R.id.tv_term:
                startActivity(new Intent(this,TermActivity.class));
                break;
            //退出应用
            case R.id.mCloseApp_bt:
                if(MyApplication.isLogin){
                    BmobUser.logOut();
                    MyApplication.isLogin = false;
                    Toast.makeText(SettingActivity.this, "退出成功!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                }else{
                    Toast.makeText(SettingActivity.this, "未登录!", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
