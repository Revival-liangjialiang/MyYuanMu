package com.example.yuanmu.lunbo.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.example.yuanmu.lunbo.Custom.ZoomImageView;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.ImgUtil;
import com.example.yuanmu.lunbo.Util.StatusBarColorUtil;

import java.util.List;

/**
 * Created by yuanmu on 2016/9/11.
 */
public class Gally extends Activity implements
        ViewPager.OnPageChangeListener {
    private ViewPager vp;
    private TextView page;
    private List<String> imgarray;
    private ZoomImageView[] mImageViews;
    private Context context;
    private int imgsize;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gally);
        new StatusBarColorUtil(this,R.color.StyleColor);
        context = Gally.this;
        vp = (ViewPager) findViewById(R.id.vp);
        page = (TextView) findViewById(R.id.page);
        getLunbo();
        initImg();
    }

    private void getLunbo() {
        Intent intent = getIntent();
        imgarray = intent.getStringArrayListExtra("imgarray");
        pos = intent.getIntExtra("pos", 0);
    }


    /**
     * 初始化图片资源 为数组里的每一个Imageview分配内存
     */
    public void initImg() {
        // 设置轮播图信息
        int length = imgarray.size();
        mImageViews = new ZoomImageView[length];
        for (int i = 0; i < length; i++) {
            ZoomImageView imageView = new ZoomImageView(context);
            mImageViews[i] = imageView;
        }
        setImg(length);
        vp.setAdapter(new HomePageViewpagerAdapter());
        vp.setCurrentItem(pos);
        page.setText(pos + 1 + "/" + imgsize);
        vp.setOnPageChangeListener(this);

    }

    public void setImg(int length) {
        imgsize = length;
        for (int i = 0; i < length; i++) {
            ImgUtil.setImg(mImageViews[i], imgarray.get(i), 1000, 1000);
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    /**
     * 显示滑到了第几张（不是必须的）
     */
    @Override
    public void onPageSelected(int arg0) {
        page.setText(arg0 + 1 + "/" + imgsize);
    }

    private class HomePageViewpagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImageViews.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);

        }

        @Override
        public Object instantiateItem(View container, int position) {
            ((ViewPager) container).addView(mImageViews[position]);
            return mImageViews[position];
        }
    }

}
