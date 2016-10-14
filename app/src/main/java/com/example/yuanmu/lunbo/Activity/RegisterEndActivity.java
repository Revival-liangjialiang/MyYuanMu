package com.example.yuanmu.lunbo.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuanmu.lunbo.BmobBean.Condition;
import com.example.yuanmu.lunbo.BmobBean.User;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.RongUtil.GetToken;
import com.example.yuanmu.lunbo.Util.getLocationInfo;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


/**
 * Created by yuanmu on 2016/8/31.
 */
public class RegisterEndActivity extends AppCompatActivity implements View.OnClickListener{
    getLocationInfo mLocationInfo = null;

    //NetWorkListener
    private IntentFilter intentfilter;// IntentFilter：意图过滤器。
    private NetworkChangeReceier networkchangereceier;

    static final int REGISTER_SUCCESS = 0,REGISTER_FAIL = 1;
    EditText mUserNickNameET,mPassWordET_1,mPassWordET_2;
    String mTelephoneStr;
    //完成
    TextView mComplete;
//    File mFile;
    String mToken;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REGISTER_SUCCESS:
                    if(mLocationInfo.mUploadSwitch) {
                        Condition condition = new Condition();
                        condition.setUserNickName(mUserNickNameET.getText().toString());
                        condition.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if(e ==null){
                                    User user = new User();
                                    user.setConditionId(s);
                                    user.setUsername(mTelephoneStr);
                                    user.setPassword(mPassWordET_1.getText().toString());
                                    user.setNickname(mUserNickNameET.getText().toString());
                                    user.setLongitute(String.valueOf(mLocationInfo.mLongitude));
                                    user.setLatitude(String.valueOf(mLocationInfo.mLatitude));
                                    user.setCity(mLocationInfo.mCity);
                                    user.setToken(mToken);
                                    user.setImg("https://ss1.baidu.com/9vo3dSag_xI4khGko9WTAnF6hhy/image/h%3D360/sign=51650b06b83eb1355bc7b1bd961fa8cb/7a899e510fb30f2493c8cbedcc95d143ac4b0389.jpg");
                                    user.signUp(new SaveListener<BmobUser>() {
                                        @Override
                                        public void done(BmobUser s, BmobException e) {
                                            if (e == null) {
                                                //初始化选偶条件数据
                                                Toast.makeText(RegisterEndActivity.this, "注册成功" + s.toString(),
                                                        Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(RegisterEndActivity.this, LoginActivity.class);
                                                intent.putExtra(LoginActivity.USER_NAME, mUserNickNameET.getText().toString());
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(RegisterEndActivity.this, "注册失败：" + e.getMessage(),
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }else{
                        Toast.makeText(RegisterEndActivity.this, "注册失败!请检查你的GPS或网络是否打开!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case REGISTER_FAIL:
                    Toast.makeText(RegisterEndActivity.this, "融云注册失败!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerend);
        mLocationInfo = new getLocationInfo(this);
        initBroadcastReceiver();
        Intent intent = getIntent();
        mTelephoneStr = intent.getStringExtra("username");
        initView();
    }

    private void initBroadcastReceiver() {
        intentfilter = new IntentFilter();
        intentfilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");// 想监听什么广播在这修改值即可。
        networkchangereceier = new NetworkChangeReceier();
        registerReceiver(networkchangereceier, intentfilter);
    }


    private void initView() {
        mUserNickNameET = (EditText)findViewById(R.id.et_nickname) ;
        mPassWordET_1 = (EditText) findViewById(R.id.et_password);
        mPassWordET_2 = (EditText) findViewById(R.id.et_confirm);
        mComplete = (TextView)findViewById(R.id.tv_enter);
        mComplete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()) {
           //完成事件
           case R.id.tv_enter:
               if (mPassWordET_1.getText().toString().equals(mPassWordET_2.getText().toString())) {
                   new Thread(new Runnable() {

                       @Override
                       public void run() {
                           String response = GetToken.GetRongCloudToken(mTelephoneStr, mUserNickNameET.getText().toString(),
                                   "https://ss1.baidu.com/-4o3dSag_xI4khGko9WTAnF6hhy/image/h%3D200/sign=5f6ab3f5d00735fa8ef049b9ae500f9f/29381f30e924b8995d7368d66a061d950b7bf695.jpg");
                           JSONObject object = null;

                           try {
                               object = new JSONObject(response);
                               String status = object.getString("code");
                               if (status.equals("200")) {
                                   mToken = object.getString("token");
                                   Log.i("token", mToken);
                                   Message message = new Message();
                                   message.what = REGISTER_SUCCESS;
                                   handler.sendMessage(message);
                               } else {
                                   Message message_1 = new Message();
                                   message_1.what = REGISTER_FAIL;
                                   handler.sendMessage(message_1);
                               }
                           } catch (JSONException e) {
                               e.printStackTrace();
                           }
                       }
                   }).start();

                   break;
               }else{
                   Toast.makeText(RegisterEndActivity.this, "两次密码输入不一致!", Toast.LENGTH_SHORT).show();
               }
                   default:
                       break;

               }

    }

    //广播接收机
    class NetworkChangeReceier extends BroadcastReceiver {// BroadcastReceiver：广播接收机。
        @Override
        public void onReceive(Context context, Intent intent) {// 网络发生变化时就会调用这个方法。
            ConnectivityManager connectivitymanager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);// ConnectivityManager:连接管理器。
            NetworkInfo networkinfo = connectivitymanager
                    .getActiveNetworkInfo(); // NetworkInfo：网络状态。
            if (networkinfo != null && networkinfo.isAvailable()) {// isAvailable：是可用的，若连接则返回true。
                mComplete.setEnabled(true);
                Toast.makeText(context, "网络已连接", Toast.LENGTH_SHORT).show();
            } else {
                mComplete.setEnabled(false);
                Toast.makeText(context, "网络已断开", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销广播机
        unregisterReceiver(networkchangereceier);
    }
}
