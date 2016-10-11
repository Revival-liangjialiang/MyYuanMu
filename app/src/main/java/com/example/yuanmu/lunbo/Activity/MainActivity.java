package com.example.yuanmu.lunbo.Activity;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.yuanmu.lunbo.Adapter.Main_ViewPager_Adapter;
import com.example.yuanmu.lunbo.Application.MyApplication;
import com.example.yuanmu.lunbo.Fragment.Personal.Personal_Fragment;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.SystemBarTintManager;
import com.example.yuanmu.lunbo.Util.TabEntity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;


public class MainActivity extends AppCompatActivity {
    ViewPager mViewPager;
    public LruCache<String, Bitmap> mLruCache;
    Personal_Fragment personal_fragment;


    private String[] mTitles = {"首页", "生活圈", "附近", "消息", "个人"};
    //导航主控件
    CommonTabLayout mCommonTabLayout;
    //未选中的导航图
    private int[] mIconUnselectIds = {
            R.drawable.home_ic, R.drawable.lifecircle_ic,
            R.drawable.nearby_ic, R.drawable.news_ic, R.drawable.personal_ic};
    //选中后的图片
    private int[] mIconSelectIds = {
            R.drawable.home_ic_2, R.drawable.lifecircle_ic_2,
            R.drawable.nearby_ic_2, R.drawable.news_ic_2, R.drawable.personal_ic_2};

    //CustomTabEntity为接口，实现类是TabEntity，用来存储每个Tab的标题，和点击前和点击后的两张图
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        //加入导航所需的的数据
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        initView();
        setCache();
        setStatusBarColor();
        // 轮播图
        rong();
    }

    private void rong() {
        /**
         * 建立与服务器的连接
         */
        if (MyApplication.isLogin) {
//            User u = User.getInstance();
//            Log.i("融云帐号", u.getUsername());
//            Log.i("融云token", u.getToken() + "");
            RongIM.connect(MyApplication.token,// 登录
                    new RongIMClient.ConnectCallback() {
                        @Override
                        public void onSuccess(String userId) {
                            Log.e("MainActivity", "------onSuccess----"
                                    + userId);
                            /**
                             * 设置接收 push 消息的监听器。
                             */
                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {
                            Log.e("MainActivity", "------onError----"
                                    + errorCode);
                        }

                        @Override
                        public void onTokenIncorrect() {
                            Log.d("LoginActivity", "--onTokenIncorrect");
                        }
                    });
        }
    }

    private void setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.StatusBarColor);// 通知栏颜色
        }
        ViewGroup mContentView = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 使其不为系统 View 预留空间.
            //设置为true表示为状态栏预留空间。
            ViewCompat.setFitsSystemWindows(mChildView, true);
        }
    }

    private void setCache() {
        //得到进程最大可使用内存
        int maxCacheSize = (int) Runtime.getRuntime().maxMemory();
//使用五分之一的内存设置
        int cacheSize = maxCacheSize / 5;
        mLruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
//返回单位也是字节（B）
                return value.getByteCount();
            }
        };
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.main_viewPager);
        //TODO
        mViewPager.setOffscreenPageLimit(5);
        mCommonTabLayout = (CommonTabLayout) findViewById(R.id.tl_2);
        mCommonTabLayout.setTabData(mTabEntities);
        mCommonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mViewPager.setAdapter(new Main_ViewPager_Adapter(getSupportFragmentManager()));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCommonTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }



    @Override
    protected void onRestart() {
        super.onRestart();
        //登录成功之后隐藏登录界面
        if(MyApplication.isLogin) {
            personal_fragment.mNo_sign_in_layout.setVisibility(View.GONE);
            personal_fragment.sign_in_success_Layout.setVisibility(View.VISIBLE);
        }
    }
    //活动和碎片关联起来的时候把碎片传进来，用来处理登录界面的隐藏
    public void setFragment(Personal_Fragment fragment) {
        personal_fragment = fragment;
    }

}
