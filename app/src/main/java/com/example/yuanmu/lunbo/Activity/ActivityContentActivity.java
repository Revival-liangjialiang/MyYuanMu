package com.example.yuanmu.lunbo.Activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuanmu.lunbo.Adapter.LoveActivityReAdapter;
import com.example.yuanmu.lunbo.Adapter.NOA_Re_Adapter;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.SystemBarTintManager;

/**
 * Created by Administrator on 2016/9/9 0009.
 */
public class ActivityContentActivity extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView mNOA_re;
    TextView mBack;
    RelativeLayout mActivity_position;
    private int mValue = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_content);
        initView();
        mValue = getIntent().getIntExtra(LoveActivityReAdapter.INDEX,0);
        Toast.makeText(ActivityContentActivity.this, "start value = "+mValue, Toast.LENGTH_SHORT).show();
        setStatusBarColor();
    }
    private void initView() {
        mBack = (TextView) findViewById(R.id.content_activity_back);
        mNOA_re = (RecyclerView)findViewById(R.id.c_re);
        mActivity_position = (RelativeLayout) findViewById(R.id.activity_position);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(linearLayoutManager.HORIZONTAL);
        mNOA_re.setLayoutManager(linearLayoutManager);
        mNOA_re.setAdapter(new NOA_Re_Adapter());
        mBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.content_activity_back:
                finish();
                break;
            default:
                break;
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
}
