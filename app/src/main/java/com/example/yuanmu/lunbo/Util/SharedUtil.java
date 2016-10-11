package com.example.yuanmu.lunbo.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedUtil {

	/**
	 * 存放帐号密码的sharedpreferences
	 */
	public static final String USER_NAME = "userinfo";
	public static final String NOTIFI_NAME = "nitifisetting";
	public static final String FIND_NAME = "findsetting";

	/**
	 * @param ctx
	 *            上下文
	 * @return 帐号 得到用户名
	 */
	public static String getUserName(Context ctx) {
		SharedPreferences sp = ctx.getSharedPreferences(USER_NAME,
				Context.MODE_PRIVATE);
		return sp.getString("username", "");
	}

	/**
	 * @param ctx
	 *            上下文
	 * @return 密码 得到密码
	 */
	public static String getPassword(Context ctx) {
		SharedPreferences sp = ctx.getSharedPreferences(USER_NAME,
				Context.MODE_PRIVATE);
		return sp.getString("password", "");
	}

	/**
	 * @param ctx
	 *            上下文
	 * @param username
	 *            帐号
	 * @param password
	 *            密码 存储用户信息
	 */
	public static void setUserinfo(Context ctx, String username, String password) {
		SharedPreferences sp = ctx.getSharedPreferences(USER_NAME,
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("username", username);
		editor.putString("password", password);
		editor.commit();
	}

}
