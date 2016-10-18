package com.example.yuanmu.lunbo.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuanmu.lunbo.Application.MyApplication;
import com.example.yuanmu.lunbo.BmobBean.User;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.MyLog;
import com.example.yuanmu.lunbo.Util.getLocationInfo;
import com.example.yuanmu.lunbo.interfaces.LoginInterface;
import com.example.yuanmu.lunbo.interfaces.LoginUtil;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by yuanmu on 2016/8/31.
 */
public class LoginActivity extends Activity implements View.OnClickListener
{
    static final String USER_NAME = "USER_NAME";
    EditText mUserNameET,mPassword;
    TextView mRegister_TV,sign_in_TV;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = LoginActivity.this;
        initView();
        getUserData();
        //注册好之后，把用户账号赋值到登录界面的输入框里
        Intent intent = getIntent();
        String userName = intent.getStringExtra(USER_NAME);
        if(userName!=null) {
            mUserNameET.setText(userName);
        }
    }

    private void initView() {
        mUserNameET = (EditText) findViewById(R.id.et_username);
        mPassword = (EditText) findViewById(R.id.et_password);
        mRegister_TV = (TextView) findViewById(R.id.tv_forgetpassword);
        sign_in_TV = (TextView) findViewById(R.id.tv_login);
        sign_in_TV.setOnClickListener(this);
        mRegister_TV.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_forgetpassword:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            case R.id.tv_login:
                LoginUtil.login(context, mUserNameET.getText().toString().trim(), mPassword.getText().toString().trim(),
                        new LoginInterface() {
                            @Override
                            public void LoginResult(String result) {
                                if (result.equals("success")) {
                                    MyApplication.userID = mUserNameET.getText().toString();
                                    Toast.makeText(context, "登录成功:",
                                            Toast.LENGTH_SHORT).show();
                                    //登录的时候获取位置信息
                                    getLocationInfo locationInfo = new getLocationInfo(LoginActivity.this);
                                    locationInfo.setLocationListener(new getLocationInfo.LocationListener() {
                                        @Override
                                        public void finish(Double longitude, Double latitude, String city,String district) {
                                            User user = BmobUser.getCurrentUser(User.class);
                                            user.setLongitute(String.valueOf(longitude));
                                            user.setLatitude(String.valueOf(latitude));
                                            user.setCity(city);
                                            user.setDistrict(district);
                                            user.setDistrict(district);
                                            user.update(new UpdateListener() {
                                                @Override
                                                public void done(BmobException e) {
                                                if(e==null){
                                                    MyLog.i("kkk","UpdateLocationSuccess!");
                                                }else{
                                                    MyLog.i("kkk","UpdateLocationSuccess!");
                                                }
                                                }
                                            });
                                            }
                                    });
                                    SharedPreferences.Editor editor = getSharedPreferences("data", Context.MODE_PRIVATE).edit();
                                    editor.putString("user_name", mUserNameET.getText().toString());
                                    editor.putString("user_password", mPassword.getText().toString());
                                    editor.commit();
                                    startActivity(new Intent(context,
                                            MainActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(context, "登录失败",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                break;
            default:break;
        }
    }

    public void getUserData() {
        SharedPreferences pref = getSharedPreferences("data",Context.MODE_PRIVATE);
        mUserNameET.setText(pref.getString("user_name",""));
        mPassword.setText(pref.getString("user_password",""));
    }
}
