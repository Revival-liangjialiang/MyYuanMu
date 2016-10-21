package com.example.yuanmu.lunbo.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.yuanmu.lunbo.Adapter.ConcernRvAdapter;
import com.example.yuanmu.lunbo.BmobBean.Focus;
import com.example.yuanmu.lunbo.BmobBean.User;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.MyLog;
import com.example.yuanmu.lunbo.Util.StatusBarColorUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2016/10/20 0020.
 */
public class MyConcern extends AppCompatActivity{
    RecyclerView mConcern_rv;
    List<User> mUserList = new ArrayList<>();
    User mUser;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_my_concern);
        new StatusBarColorUtil(this,R.color.StyleColor);
        mUser = BmobUser.getCurrentUser(User.class);
        getData();
    }

    private void initView() {
        mConcern_rv = (RecyclerView) findViewById(R.id.mConcern_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(layoutManager.VERTICAL);
        mConcern_rv.setLayoutManager(layoutManager);
        mConcern_rv.setAdapter(new ConcernRvAdapter(this,mUserList));
    }

    public void getData() {
        BmobQuery<Focus> query = new BmobQuery<Focus>();
        query.include("target");
//查询playerName叫“比目”的数据
        query.addWhereEqualTo("myusername", mUser.getUsername());
//返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(20);
//执行查询方法
        query.findObjects(new FindListener<Focus>() {
            @Override
            public void done(List<Focus> object, BmobException e) {
                if(e==null){
                    for(int a = 0;a<object.size();a++) {
                        Focus focus = object.get(a);
                        mUserList.add(focus.getTarget());
                    }
                    initView();
                    //计算附近人与本人的距离
                    MyLog.i("kkk","mUserList = "+mUserList.size());
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }
}
