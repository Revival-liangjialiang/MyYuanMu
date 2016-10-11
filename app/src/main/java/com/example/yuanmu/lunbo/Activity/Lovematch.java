package com.example.yuanmu.lunbo.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.yuanmu.lunbo.Adapter.TestFragPagerAdapter;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.widget.jellyviewpager_widget.JellyViewPager;

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
        initView();
    }

    private void initView() {
        pager = (JellyViewPager) findViewById(R.id.myViewPager1);
        //pager.setAdapter(new TestPagerAdapter(this));
        pager.setAdapter(new TestFragPagerAdapter(getSupportFragmentManager()));
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
