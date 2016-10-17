package com.example.yuanmu.lunbo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuanmu.lunbo.Application.MyApplication;
import com.example.yuanmu.lunbo.BmobBean.Focus;
import com.example.yuanmu.lunbo.BmobBean.User;
import com.example.yuanmu.lunbo.Custom.CircleImageView;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.ImgUtil;
import com.example.yuanmu.lunbo.Util.MyLog;
import com.example.yuanmu.lunbo.Util.StatusBarColorUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2016/10/6 0006.
 */
public class UserDataActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView mBack_tv;
   public static final String USER_NAME = "USER_NAME",HEAD_PORTRAIT_ADDRESS = "HEAD_PORTRAIT_ADDRESS";
    TextView mUserName,monologue_tv,mEducation_tv,mHeight_tv,mWeight_tv,mIncome_tv,mAddressAndBirthday_tv,mFollow;
    CircleImageView mCiv_img;
    User targetUser,currentUser;
    boolean mFollowSwitch = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup mContentView = (ViewGroup) this.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 使其不为系统 View 预留空间.
            //设置为true表示为状态栏预留空间。
            ViewCompat.setFitsSystemWindows(mChildView, false);
        }
        new StatusBarColorUtil(this,android.R.color.transparent);
        setContentView(R.layout.activity_otherdata);
        Intent intent = getIntent();
        targetUser = (User) intent.getSerializableExtra("user");
            currentUser = BmobUser.getCurrentUser(User.class);
        initView();
        if(targetUser!=null) {
            setData();
        }
        //如果关注过此人，就把控件设置为不可点击
        queryWhetherFollw();
    }

    private void queryWhetherFollw() {
        BmobQuery<Focus> query = new BmobQuery();
        query.include("target");
        query.addWhereEqualTo("remark", currentUser.getNickname());
        query.findObjects(new FindListener<Focus>() {
            @Override
            public void done(List<Focus> list, BmobException e) {
           if(e == null){
               if(list.size()>0){
                   for(int a = 0;a<list.size();a++){
                       Focus focus = list.get(a);
                       //如果关注过此人，则直接退出，不做任何逻辑处理
                       if(targetUser.getNickname().equals(focus.getTarget().getNickname())) {
                           mFollow.setEnabled(false);
                           mFollow.setText("已关注!");
                           return;
                       }
                   }
               }
           }else {

           }
            }
        });
    }

    private void setData() {
        mUserName.setText(targetUser.getNickname());
        ImgUtil.setImg(mCiv_img,targetUser.getImg(),150,150);
        monologue_tv.setText(targetUser.getMonologue());
        mEducation_tv.setText(targetUser.getEducation());
        mHeight_tv.setText(targetUser.getHeight());
        mWeight_tv.setText(targetUser.getWeight());
        mIncome_tv.setText(targetUser.getIncome());
        mAddressAndBirthday_tv.setText(targetUser.getWorking_area()+"\n\n"+targetUser.getBirthday());
    }

    private void initView() {
        mBack_tv = (ImageView) findViewById(R.id.mBack_tv);
        mFollow = (TextView) findViewById(R.id.mFollow);
        monologue_tv = (TextView) findViewById(R.id.monologue_tv);
        mEducation_tv = (TextView) findViewById(R.id.mEducation_tv);
        mHeight_tv = (TextView) findViewById(R.id.mHeight_tv);
        mWeight_tv = (TextView) findViewById(R.id.mWeight_tv);
        mIncome_tv = (TextView) findViewById(R.id.mIncome_tv);
        mAddressAndBirthday_tv = (TextView) findViewById(R.id.mAddressAndBirthday_tv);
        mUserName = (TextView) findViewById(R.id.mUserName);
        mCiv_img = (CircleImageView) findViewById(R.id.mCiv_img);
        mFollow.setOnClickListener(this);
        mBack_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.mFollow:
                MyLog.i("pppp","mFollowSwitch = "+mFollowSwitch);
                if(mFollowSwitch) {
                    mFollowSwitch = false;
                    if (targetUser.getNickname().equals(currentUser.getNickname())) {
                        Toast.makeText(UserDataActivity.this, "抱歉！自己不能关注自己。", Toast.LENGTH_SHORT).show();
                        mFollowSwitch = true;
                        return;
                    } else {
                        BmobQuery<Focus> query = new BmobQuery();
                        query.include("target");
                        query.addWhereEqualTo("remark", currentUser.getNickname());
                        query.findObjects(new FindListener<Focus>() {
                            @Override
                            public void done(List<Focus> list, BmobException e) {
                                if (e == null) {
                                    //有好友就需要判断有没重复关注好友
                                    if (list.size() > 0) {
                                    for(int a = 0;a<list.size();a++){
                                        Focus focus = list.get(a);
                                        //如果关注过此人，则直接退出，不做任何逻辑处理
                                        if(targetUser.getNickname().equals(focus.getTarget().getNickname())) {
                                            Toast.makeText(UserDataActivity.this, "你已经关注过此人!", Toast.LENGTH_SHORT).show();
                                            mFollowSwitch = true;
                                            return;
                                        }
                                    }
                                        Focus one = new Focus();
                                        one.setMyusername(currentUser.getUsername());
                                        one.setTarget(targetUser);
                                        //设置备注
                                        one.setRemark(currentUser.getNickname());
                                        one.save(new SaveListener<String>() {
                                            @Override
                                            public void done(String objectId, BmobException e) {
                                                if (e == null) {
                                                    Toast.makeText(MyApplication.getContext(), "关注成功，返回objectId为：" + objectId,
                                                            Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(MyApplication.getContext(), "关注失败：" + e.getMessage(),
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else {
                                        //没有好友就直接关注
                                        Focus one = new Focus();
                                        one.setMyusername(currentUser.getUsername());
                                        one.setTarget(targetUser);
                                        //设置备注
                                        one.setRemark(currentUser.getNickname());
                                        one.save(new SaveListener<String>() {
                                            @Override
                                            public void done(String objectId, BmobException e) {
                                                if (e == null) {
                                                    Toast.makeText(MyApplication.getContext(), "关注成功，返回objectId为：" + objectId,
                                                            Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(MyApplication.getContext(), "关注失败：" + e.getMessage(),
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                } else {
                                    Toast.makeText(UserDataActivity.this, "错误!", Toast.LENGTH_SHORT).show();
                                }
                                mFollowSwitch = true;
                            }
                        });
                    }
                }else{
                    Toast.makeText(UserDataActivity.this, "您的操作过于频繁,请稍后再试!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.mBack_tv:
                finish();
                break;
            default:break;
        }
    }
}
