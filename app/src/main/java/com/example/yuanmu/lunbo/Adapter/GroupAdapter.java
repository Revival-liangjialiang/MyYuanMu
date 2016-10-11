package com.example.yuanmu.lunbo.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yuanmu.lunbo.Custom.MyImageView;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.LocalImageListBean;
import com.example.yuanmu.lunbo.Util.NativeImageLoader;
import com.example.yuanmu.lunbo.Util.ScreenUtil;

import java.util.List;


public class GroupAdapter extends BaseAdapter {
    private List<LocalImageListBean> list;
    private Point mPoint = new Point(0, 0);// 用来封装ImageView的宽和高的对象
    private GridView mGridView;
    protected LayoutInflater mInflater;
    private int widhei = ScreenUtil.getScreenwidth() / 2 - 25;

    @Override
    public int getCount() {
        return list.size() <= 0 ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public GroupAdapter(Context context, List<LocalImageListBean> list,
                        GridView mGridView) {
        this.list = list;
        this.mGridView = mGridView;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        LocalImageListBean mImageBean = list.get(position);
        String path = mImageBean.getTopImagePath();
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.localimage_group_item, null);
            viewHolder.mImageView = (MyImageView) convertView
                    .findViewById(R.id.group_image);
            viewHolder.mTextViewTitle = (TextView) convertView
                    .findViewById(R.id.group_title);
            viewHolder.mTextViewCounts = (TextView) convertView
                    .findViewById(R.id.group_count);

            // 用来监听ImageView的宽和高，没这个滑动起来会很卡
            viewHolder.mImageView.setOnMeasureListener(new MyImageView.OnMeasureListener() {

                @Override
                public void onMeasureSize(int width, int height) {
                    mPoint.set(width, height);
                }
            });

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.mImageView
                    .setImageResource(R.mipmap.background_no);
        }

        viewHolder.mTextViewTitle.setText(mImageBean.getFolderName());
        viewHolder.mTextViewCounts.setText( "(" + Integer.toString(mImageBean
                .getImageCounts()) + ")");
        // 给ImageView设置路径Tag,这是异步加载图片的小技巧
        viewHolder.mImageView.setTag(path);

        // 利用NativeImageLoader类加载本地图片
        Bitmap bitmap = NativeImageLoader.getInstance().loadNativeImage(path,
                mPoint, new NativeImageLoader.NativeImageCallBack() {

                    @Override
                    public void onImageLoader(Bitmap bitmap, String path) {
                        ImageView mImageView = (ImageView) mGridView
                                .findViewWithTag(path);
                        if (bitmap != null && mImageView != null) {
                            mImageView.setImageBitmap(bitmap);
                        }
                    }
                });

        ViewGroup.LayoutParams para1 = viewHolder.mImageView.getLayoutParams();
        para1.height = widhei;
        para1.width = widhei;
        viewHolder.mImageView.setLayoutParams(para1);
        if (bitmap != null) {
            viewHolder.mImageView.setImageBitmap(bitmap);
        } else {
            viewHolder.mImageView
                    .setImageResource(R.mipmap.background_no);
        }

        return convertView;
    }

    public static class ViewHolder {
        public MyImageView mImageView;
        public TextView mTextViewTitle;
        public TextView mTextViewCounts;
    }

}
