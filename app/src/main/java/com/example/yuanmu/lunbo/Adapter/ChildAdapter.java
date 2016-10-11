package com.example.yuanmu.lunbo.Adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.yuanmu.lunbo.Activity.LocalImageActivity;
import com.example.yuanmu.lunbo.Custom.MyImageView;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.LocalImageBean;
import com.example.yuanmu.lunbo.Util.NativeImageLoader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint("NewApi") public class ChildAdapter extends BaseAdapter {
	private Point mPoint = new Point(0, 0);// 用来封装ImageView的宽和高的对象
	private GridView mGridView;
	private List<String> list;
	protected LayoutInflater mInflater;
	private Context mcontext;
	private int mneedcount;
    private LocalImageOnClick localimageonclick;
	public ChildAdapter(Context context, List<String> list, int needcount,
			GridView mGridView) {
		this.list = list;
		this.mcontext = context;
		this.mGridView = mGridView;
		this.mneedcount = needcount;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		final String path = list.get(position);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.localimage_child_item, null);
			viewHolder = new ViewHolder();
			viewHolder.mImageView = (MyImageView) convertView
					.findViewById(R.id.child_image);
			viewHolder.mCheckBox = (CheckBox) convertView
					.findViewById(R.id.child_checkbox);

			// 用来监听ImageView的宽和高
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
		viewHolder.mImageView.setTag(path);
		viewHolder.mCheckBox
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// 如果是未选中的CheckBox,则添加动画
						if (!LocalImageBean.mSelectMap.containsKey(path)
								|| !LocalImageBean.mSelectMap.get(path)) {
							addAnimation(viewHolder.mCheckBox);
							int pos = 0;
							for (Boolean v : LocalImageBean.mSelectMap.values()) {
								if (v == true) {
									pos++;
									if (pos >= mneedcount) {
										viewHolder.mCheckBox.setChecked(false);
										Toast.makeText(mcontext,
												"最多只能选择" + mneedcount + "张图片",
												Toast.LENGTH_SHORT).show();
										return;
									}
								}

							}

						}
						LocalImageBean.mSelectMap.put(path, isChecked);
						localimageonclick.onclick();
					}
				});

		viewHolder.mCheckBox
				.setChecked(LocalImageBean.mSelectMap.containsKey(path) ? LocalImageBean.mSelectMap
						.get(path) : false);

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

		if (bitmap != null) {
			viewHolder.mImageView.setImageBitmap(bitmap);
		} else {
			viewHolder.mImageView
					.setImageResource(R.mipmap.background_no);
		}
		viewHolder.mImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mcontext, LocalImageActivity.class);
				intent.putExtra("path",path);
				mcontext.startActivity(intent);
			}
		});

		return convertView;
	}

	/**
	 * 给CheckBox加点击动画，利用开源库nineoldandroids设置动画
	 *
	 * @param view
	 */
	@SuppressLint("NewApi") private void addAnimation(View view) {
		float[] vaules = new float[] { 0.5f, 0.6f, 0.7f, 0.8f, 0.9f, 1.0f,
				1.1f, 1.2f, 1.3f, 1.25f, 1.2f, 1.15f, 1.1f, 1.0f };
		AnimatorSet set = new AnimatorSet();
		set.playTogether(ObjectAnimator.ofFloat(view, "scaleX", vaules),
				ObjectAnimator.ofFloat(view, "scaleY", vaules));
		set.setDuration(150);
		set.start();
	}

	/**
	 * 获取选中的Item的position
	 *
	 * @return
	 */
	public List<String> getSelectItems() {
		List<String> list = new ArrayList<String>();
		for (Iterator<Map.Entry<String, Boolean>> it = LocalImageBean.mSelectMap
				.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, Boolean> entry = it.next();
			if (entry.getValue()) {
				list.add(entry.getKey());
			}
		}

		return list;
	}

	public static class ViewHolder {
		public MyImageView mImageView;
		public CheckBox mCheckBox;
	}
    public interface LocalImageOnClick {
        public void onclick();
    }

    public void setLocalImageOnClick(LocalImageOnClick localimageonclick) {
        this.localimageonclick = localimageonclick;
    }
}
