package com.example.yuanmu.lunbo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.yuanmu.lunbo.Adapter.TestFragPagerAdapter;
import com.example.yuanmu.lunbo.BmobBean.User;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.StatusBarColorUtil;
import com.example.yuanmu.lunbo.widget.jellyviewpager_widget.JellyViewPager;

import java.util.List;

/**
 * Created by Administrator on 2016/9/2 0002.
 */
public class Lovematch extends AppCompatActivity {
    JellyViewPager pager;
    public int Indexes;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_love_match_layout);
        new StatusBarColorUtil(this,R.color.StatusBarColor);
        Intent intent = getIntent();
        List<User> user = (List<User>) intent.getSerializableExtra("list");
        initView(user);
    }


    private void initView(List<User> users) {
        pager = (JellyViewPager) findViewById(R.id.myViewPager1);
        //pager.setAdapter(new TestPagerAdapter(this));
        pager.setAdapter(new TestFragPagerAdapter(getSupportFragmentManager(),users));
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int state) {
                switch(state){
                    case 1: //正在滑动
                        break;
                    case 2: //滑动结束
                        break;
                }
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageSelected(int arg0) {
                Indexes = arg0;
            }
        });
    }
}
