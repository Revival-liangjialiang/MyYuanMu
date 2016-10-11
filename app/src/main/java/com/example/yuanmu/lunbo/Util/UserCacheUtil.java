package com.example.yuanmu.lunbo.Util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.yuanmu.lunbo.Application.MyApplication;

public class UserCacheUtil {
    public static final String PREF_NAME_AVATOR = MyApplication.username + "avator";
    public static final String PREF_NAME_REMARK = MyApplication.username + "remark";


    public static String getAvator(Context ctx, String username) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME_AVATOR,
                Context.MODE_PRIVATE);
        return sp
                .getString(
                        username,
                        "");
    }


    public static void setAvator(Context ctx, String username, String img) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME_AVATOR,
                Context.MODE_PRIVATE);
        sp.edit().putString(username, img).commit();
    }


    public static String getRemark(Context ctx, String username) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME_REMARK,
                Context.MODE_PRIVATE);
        return sp.getString(username, " ");
    }


    public static void setRemark(Context ctx, String username, String remark) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME_REMARK,
                Context.MODE_PRIVATE);
        sp.edit().putString(username, remark).commit();
    }
}
