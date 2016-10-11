package com.example.yuanmu.lunbo.Util;

import android.graphics.Bitmap;
import android.os.Environment;
import android.widget.Toast;

import com.example.yuanmu.lunbo.Application.MyApplication;

import java.io.IOException;


public class CommenUtil {
	/**
	 * 判断手机是否有SD卡。
	 * 
	 * @return 有SD卡返回true，没有返回false。
	 */
	public static boolean hasSDCard() {
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			Toast.makeText(MyApplication.getContext(), "暂无外置内存卡", Toast.LENGTH_SHORT).show();
			return false;
		} else {
			return true;
		}
	}
	/**
	 * @param url
	 *            url转bitmap
	 * @return bitmap图片
	 */
	public static Bitmap getLoacalBitmap(String url) {

//		try {
//			FileInputStream fis = new FileInputStream(url);
//			return BitmapFactory.decodeStream(fis); // /把流转化为Bitmap图片
		try {
			Bitmap addbmps = BitmapCompressUtil.revitionImageSize(
					MyApplication.getContext(),
					url, 300);
			return addbmps;
		} catch (IOException e) {
			e.printStackTrace();
		}
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//			return null;
//		}
		return null;
	}
}
