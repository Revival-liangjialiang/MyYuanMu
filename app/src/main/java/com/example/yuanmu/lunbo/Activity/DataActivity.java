package com.example.yuanmu.lunbo.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuanmu.lunbo.Adapter.DataViewPagerAdapter;
import com.example.yuanmu.lunbo.BmobBean.User;
import com.example.yuanmu.lunbo.Custom.MyCircularView;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.ImgUtil;
import com.example.yuanmu.lunbo.Util.SystemBarTintManager;
import com.example.yuanmu.lunbo.Util.TabEntity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2016/8/29 0029.
 */
public class DataActivity extends FragmentActivity implements View.OnClickListener{
   private Fragment mPersonalDataFragment,mConditionalSelectionFeagment;
    //头像控件
    MyCircularView mHeadPortrait_cu;
    TextView edit_condition_TV,mEditPersonalData_TV,mUserName,mEditMonologue_tv;
    ImageView mBack_IV,mUserDataSetup;
    public RelativeLayout mPopup_layout;
    CommonTabLayout mTab;
    ViewPager mViewPager;
    private String[] mTitles = {"个人资料", "选偶条件"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_data_layout);
        setStatusBarColor();
        initView();
        setListener();
        initTabData();
        User user = BmobUser.getCurrentUser(User.class);
        //SettingUserName
        mUserName.setText(user.getNickname());
        //SettingUserHeadPortrait
        ImgUtil.setImg(user.getImg(), 100, new ImgUtil.PictureListener() {
            @Override
            public void loadFinish(Bitmap bitmap) {
                mHeadPortrait_cu.bitmap = bitmap;
                mHeadPortrait_cu.postInvalidate();
            }
        });
    }

    private void setListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            mTab.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
        mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    private void initTabData() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], 0, 0));
        }
        mTab.setTabData(mTabEntities);
    }

    private void initView() {
        mEditMonologue_tv = (TextView) findViewById(R.id.mEditMonologue_tv);
        mHeadPortrait_cu = (MyCircularView) findViewById(R.id.mHeadPortrait_cu);
        mUserName = (TextView) findViewById(R.id.mUserName);
        mEditPersonalData_TV = (TextView) findViewById(R.id.mEditPersonalData_TV);
        edit_condition_TV = (TextView) findViewById(R.id.edit_condition_TV);
        mPopup_layout = (RelativeLayout) findViewById(R.id.popup_layout);
        mUserDataSetup = (ImageView) findViewById(R.id.userDataSetup);
        mViewPager = (ViewPager)findViewById(R.id.data_ViewPager);
        mBack_IV = (ImageView)findViewById(R.id.mBack_IV);
        mTab = (CommonTabLayout)findViewById(R.id.data_tab);
        mViewPager.setAdapter(new DataViewPagerAdapter(getSupportFragmentManager()));
        mBack_IV.setOnClickListener(this);
        mUserDataSetup.setOnClickListener(this);
        mPopup_layout.setOnClickListener(this);
        edit_condition_TV.setOnClickListener(this);
        mEditPersonalData_TV.setOnClickListener(this);
        mEditMonologue_tv.setOnClickListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
        finish();
            overridePendingTransition(R.anim.exit_left,R.anim.exit_right);
        }
        return super.onKeyDown(keyCode, event);
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.userDataSetup:
                mPopup_layout.setVisibility(View.VISIBLE);
                break;
            case R.id.popup_layout:
                mPopup_layout.setVisibility(View.GONE);
                break;
            //编辑选偶条件
            case R.id.edit_condition_TV:
                startActivityForResult(new Intent(this,ConditionalSelectionActivity.class),1);
                break;
            //启动个人数据编辑活动
            case R.id.mEditPersonalData_TV:
                    startActivityForResult(new Intent(this, EditPersonalDataActivity.class),30);
                    mPopup_layout.setVisibility(View.GONE);
                   break;
            case R.id.mBack_IV:
                finish();
                overridePendingTransition(R.anim.exit_left,R.anim.exit_right);
                break;
            case R.id.mEditMonologue_tv:
                startActivityForResult(new Intent(DataActivity.this, EditPersonalDataActivity.class),30);
                mPopup_layout.setVisibility(View.GONE);
                break;
            default:break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(DataActivity.this, "requestCode = "+requestCode, Toast.LENGTH_SHORT).show();
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
            if (mPersonalDataFragment != null) {
                mPersonalDataFragment.onActivityResult(requestCode, resultCode, data);
            }
                break;
            case 1:
                if(mConditionalSelectionFeagment!=null) {
                    mConditionalSelectionFeagment.onActivityResult(requestCode, resultCode, data);
                }
                break;
            case 30:
                if (mPersonalDataFragment != null) {
                    mPersonalDataFragment.onActivityResult(requestCode, resultCode, data);
                }
                break;
            default:
                break;
        }
    }

    public void setmPersonalDataFragment(Fragment mPersonalDataFragment) {
        this.mPersonalDataFragment = mPersonalDataFragment;
    }

    public void setmConditionalSelectionFeagment(Fragment mConditionalSelectionFeagment) {
        this.mConditionalSelectionFeagment = mConditionalSelectionFeagment;
    }
}
