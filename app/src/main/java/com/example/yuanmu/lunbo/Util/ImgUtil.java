package com.example.yuanmu.lunbo.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.example.yuanmu.lunbo.Application.MyApplication;
import com.example.yuanmu.lunbo.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ImgUtil {
	public static void setImg(final ImageView imageview, String headUrl, int i,
			int j) {
		Log.i("test","调用加载头像方法！");
		ImageRequest request = new ImageRequest(headUrl,
				new Listener<Bitmap>() {
					@Override
					public void onResponse(Bitmap bitmap) {
						Log.i("test","开始加载头像！");
						if (imageview != null) {
							Log.i("test","加载头像完成！");
							imageview.setImageBitmap(bitmap);
						}else{
							imageview
							.setImageResource(R.mipmap.ic_launcher);
						}
					}
				}, 100, j, Config.RGB_565, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						imageview
								.setImageResource(R.mipmap.ic_launcher);
					}
				});
		request.setTag("loadImage");
		MyApplication.getHttpQueues().add(request);
	}
	public static void setBackground(final ImageView imageview, String headUrl,
									 int i, int j) {

		ImageRequest request = new ImageRequest(headUrl,
				new Listener<Bitmap>() {
					@Override
					public void onResponse(Bitmap bitmap) {
						// Bitmap bitmapbackground =
						// BlurBitmapUtil.doBlur(bitmap,
						// 25, false);
						Drawable drawable = new BitmapDrawable(bitmap);
						imageview.setBackgroundDrawable(drawable);
					}
				}, 100, j, Config.RGB_565, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				imageview
						.setBackgroundResource(R.mipmap.ic_launcher);
			}
		});
		request.setTag("loadBackground");
		MyApplication.getHttpQueues().add(request);
	}
	public static void setImg(final NetworkImageView imageview, String headUrl){
		if(imageview != null&&!headUrl.equals("")){
			imageview.setDefaultImageResId(R.mipmap.ic_launcher);
			imageview.setErrorImageResId(R.mipmap.ic_launcher);
			imageview.setImageUrl(headUrl, MyApplication.getImageLoader());
		}
	}
	/**
	 * @param context
	 *            锟斤拷锟斤拷锟斤拷
	 * @param urlpath
	 *            图片url
	 * @return bitmap锟斤拷式图片
	 */
	public static Bitmap getBitMBitmap(Context context, String urlpath) {
		Bitmap map = null;
		try {
			URL url = new URL(urlpath);
			URLConnection conn = url.openConnection();
			conn.connect();
			InputStream in;
			in = conn.getInputStream();
			map = BitmapFactory.decodeStream(in);
			// TODO Auto-generated catch block
		} catch (IOException e) {
			e.printStackTrace();
		}
		return zoomImage(context, map, 100, 100);
	}
	/**
	 * @param context
	 *            锟斤拷锟斤拷锟斤拷
	 * @param bgimage
	 *            bitmap锟斤拷式图片
	 * @param newWidth
	 *            锟斤拷锟斤拷锟斤拷
	 * @param newHeight
	 *            锟斤拷锟斤拷叨锟?
	 * @return bitmap图片
	 */
	public static Bitmap zoomImage(Context context, Bitmap bgimage,
				       double newWidth, double newHeight) {
		// // 锟斤拷取锟斤拷锟酵计拷目锟酵革拷
		// float width = bgimage.getWidth();
		// float height = bgimage.getHeight();
		// // 锟斤拷锟斤拷锟斤拷锟斤拷图片锟矫碉拷matrix锟斤拷锟斤拷
		// Matrix matrix = new Matrix();
		// // 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
		// float scaleWidth = ((float) newWidth) / width;
		// float scaleHeight = ((float) newHeight) / height;
		// // 锟斤拷锟斤拷图片锟斤拷锟斤拷
		// matrix.postScale(scaleWidth, scaleHeight);
		// Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
		// (int) height, matrix, true);
		LayoutInflater factory = LayoutInflater.from(context);
		View textEntryView = factory.inflate(R.layout.item_map_marker, null); // //锟斤拷锟斤拷图转锟斤拷锟斤拷Bitma
		// 锟斤拷转锟斤拷锟斤拷Drawable
		ImageView imageview = (ImageView) textEntryView.findViewById(R.id.iv);
		imageview.setImageBitmap(bgimage);
		textEntryView.setDrawingCacheEnabled(true);
		textEntryView.measure(
				View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
				View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
		textEntryView.layout(0, 0, textEntryView.getMeasuredWidth(),
				textEntryView.getMeasuredHeight());
		textEntryView.buildDrawingCache();
		Bitmap newbmp = textEntryView.getDrawingCache();

		return newbmp;
	}
	public static void setImg(String headUrl,
				  int j,PictureListener listener) {
		 final PictureListener mListener = listener;
		Log.i("test","调用加载头像方法！");
		ImageRequest request = new ImageRequest(headUrl,
				new Listener<Bitmap>() {
					@Override
					public void onResponse(Bitmap bitmap) {
						Log.i("test","开始加载头像！");
						if(mListener!=null) {
							mListener.loadFinish(bitmap);
						}
					}
				}, 100, j, Config.RGB_565, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {

			}
		});
		request.setTag("loadImage");
		MyApplication.getHttpQueues().add(request);
	}

	public interface PictureListener{
		void loadFinish(Bitmap bitmap);
	}
}
