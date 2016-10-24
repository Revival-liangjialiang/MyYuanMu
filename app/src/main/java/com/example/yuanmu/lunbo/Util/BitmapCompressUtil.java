package com.example.yuanmu.lunbo.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.yuanmu.lunbo.Application.MyApplication;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;



public class BitmapCompressUtil {
	/**
	 * 将Bitmap转换成文件
	 * 保存文件
	 *
	 * @param bm
	 * @throws IOException
	 */
	public static File saveFile(Bitmap bm/*, String path, String fileName*/) throws IOException {
			String path = MyApplication.getContext().getExternalCacheDir().getPath();
			String fileName = DateUtils.getUnixStamp() + ".jpg";
			File dirFile = new File(path);
			if (!dirFile.exists()) {
				dirFile.mkdir();
		}
		File myCaptureFile = new File(path, fileName);
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
		bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
		bos.flush();
		bos.close();
		return myCaptureFile;
	}

	/**
	 * @param url url转bitmap
	 * @return bitmap图片   用于显示自定义的相册中
	 */
	public static Bitmap getLoacalBitmap(String url) {
		try {
			Bitmap addbmps = BitmapCompressUtil.revitionImageSize(
					MyApplication.getContext(),
					url, 200);
			return addbmps;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param filePath url转bitmap
	 * @return bitmap图片   用于图片压缩上传
	 */
	public static Bitmap getSmallBitmap(String filePath) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, 800, 480);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeFile(filePath, options);
	}

	//计算图片的缩放值，用于上传
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}
	//计算图片的缩放值并且返回缩放结果，用于自定义相册显示
	public static Bitmap revitionImageSize(Context context, String path, int size) throws IOException {
		// 取得图片
		//InputStream temp = context.getAssets().open(path);
		File file = new File(path);
		InputStream temp = new FileInputStream(file);
		BitmapFactory.Options options = new BitmapFactory.Options();
		// 这个参数代表，不为bitmap分配内存空间，只记录一些该图片的信息（例如图片大小），说白了就是为了内存优化
		options.inJustDecodeBounds = true;
		// 通过创建图片的方式，取得options的内容（这里就是利用了java的地址传递来赋值）
		BitmapFactory.decodeStream(temp, null, options);
		// 关闭流
		temp.close();

		// 生成压缩的图片
		int i = 0;
		Bitmap bitmap = null;
		while (true) {
			// 这一步是根据要设置的大小，使宽和高都能满足
			if ((options.outWidth >> i <= size)
					&& (options.outHeight >> i <= size)) {
				// 重新取得流，注意：这里一定要再次加载，不能二次使用之前的流！
				//temp = context.getAssets().open(path);
				temp = new FileInputStream(file);
				// 这个参数表示 新生成的图片为原始图片的几分之一。
				options.inSampleSize = (int) Math.pow(2.0D, i);
				// 这里之前设置为了true，所以要改为false，否则就创建不出图片
				options.inJustDecodeBounds = false;

				bitmap = BitmapFactory.decodeStream(temp, null, options);
				break;
			}
			i += 1;
		}
		return bitmap;
	}


}
