package com.example.yuanmu.lunbo.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuanmu.lunbo.BmobBean.User;
import com.example.yuanmu.lunbo.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


/**
 * Created by yuanmu on 2016/8/31.
 */
public class RegisterActivity extends Activity implements View.OnClickListener {
    TextView mTv_register;
    private EditText mTelephone_ET;
    String mTelephoneStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        mTelephone_ET = (EditText) findViewById(R.id.et_username);
        mTv_register = (TextView) findViewById(R.id.tv_register);
        mTv_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register:
                mTelephoneStr = mTelephone_ET.getText().toString();
                if(mTelephoneStr.length() == 11){
                    Log.i("号码",mTelephoneStr);
                    BmobQuery<User> querysum = new BmobQuery<User>();
                    querysum.addWhereEqualTo("username", mTelephoneStr);
                    querysum.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> list, BmobException e) {
                            if (e == null) {
                                if (list.size() == 0) {
                                    Intent intent = new Intent(RegisterActivity.this,RegisterEndActivity.class);
                                    intent.putExtra("username", mTelephoneStr);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(RegisterActivity.this, "帐号已经被注册",
                                            Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(RegisterActivity.this, "服务器错误",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }else{
                    Toast.makeText(RegisterActivity.this, "格式错误!", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
