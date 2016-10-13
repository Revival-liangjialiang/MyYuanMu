package com.example.yuanmu.lunbo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.yuanmu.lunbo.BmobBean.User;
import com.example.yuanmu.lunbo.Custom.CircleImageView;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.ImgUtil;
import com.example.yuanmu.lunbo.Util.MyLog;
import com.example.yuanmu.lunbo.Util.StatusBarColorUtil;

/**
 * Created by Administrator on 2016/10/6 0006.
 */
public class UserDataActivity extends AppCompatActivity{
   public static final String USER_NAME = "USER_NAME",HEAD_PORTRAIT_ADDRESS = "HEAD_PORTRAIT_ADDRESS";
    TextView mUserName,monologue_tv,mEducation_tv,mHeight_tv,mWeight_tv,mIncome_tv,mAddressAndBirthday_tv,occupation_tv;
    CircleImageView mCiv_img;
    User user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otherdata);
        new StatusBarColorUtil(this,R.color.StatusBarColor);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        initView();
        MyLog.i("dd",""+user);
        if(user!=null) {
            setData();
        }
    }

    private void setData() {
        mUserName.setText(user.getUsername());
        ImgUtil.setImg(mCiv_img,user.getImg(),150,150);
        monologue_tv.setText(user.getMonologue());
        mEducation_tv.setText(user.getEducation());
        mHeight_tv.setText(user.getHeight());
        mWeight_tv.setText(user.getWeight());
        mIncome_tv.setText(user.getIncome());
        mAddressAndBirthday_tv.setText(user.getWorking_area()+"\n\n"+user.getBirthday());
        occupation_tv.setText(user.getOccupation());
    }

    private void initView() {
        occupation_tv = (TextView) findViewById(R.id.occupation_tv);
        monologue_tv = (TextView) findViewById(R.id.monologue_tv);
        mEducation_tv = (TextView) findViewById(R.id.mEducation_tv);
        mHeight_tv = (TextView) findViewById(R.id.mHeight_tv);
        mWeight_tv = (TextView) findViewById(R.id.mWeight_tv);
        mIncome_tv = (TextView) findViewById(R.id.mIncome_tv);
        mAddressAndBirthday_tv = (TextView) findViewById(R.id.mAddressAndBirthday_tv);
        mUserName = (TextView) findViewById(R.id.mUserName);
        mCiv_img = (CircleImageView) findViewById(R.id.mCiv_img);
    }
}
