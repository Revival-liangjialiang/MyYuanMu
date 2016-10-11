package com.example.yuanmu.lunbo.Util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.example.yuanmu.lunbo.Application.MyApplication;

public class ScreenUtil {
	static int screenwidth;
	static int screenheight;
	static DisplayMetrics dm;
	private static Context context = MyApplication.getContext();

	public static int getScreenwidth() {
		screenmanager();
		screenwidth = dm.widthPixels;
		return screenwidth;
	}

	private static void screenmanager() {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
	}

	public static int getScreenheight() {
		screenmanager();
		screenheight = dm.heightPixels;
		return screenheight;
	}

}
