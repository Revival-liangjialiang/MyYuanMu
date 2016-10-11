package com.example.yuanmu.lunbo.Application;


import android.app.Application;
import android.content.Context;
import android.net.Uri;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.yuanmu.lunbo.Other.SealAppContext;
import com.example.yuanmu.lunbo.Util.BitmapCache;
import com.example.yuanmu.lunbo.Util.UserCacheUtil;

import io.rong.imkit.RongIM;
import io.rong.imkit.widget.provider.FileMessageItemProvider;
import io.rong.imlib.model.UserInfo;
import io.rong.message.FileMessage;
import io.rong.message.GroupNotificationMessage;

public class MyApplication extends Application {
    // 登录所需要的token
    public static String token = "";
    // 帐号名字
    public static String username = "";
    // 是否登陆
    public static boolean isLogin = false;
    public static String[] img_path  = new String[50];
    public static int value = 0;
    // bmob appid
    public static String userID = "";
    private static RequestQueue queues;
    private static Context context;
    private static ImageLoader imageLoader;
    // bmob appid
    public static String APPID = "d6aba0f132596eb927dcde680f807bec";

    @Override
    public void onCreate() {
        super.onCreate();
/**
 * 初始化融云
 */
        RongIM.init(this);
        // 获取全局context
        context = getApplicationContext();
        // 获取请求队列
        queues = Volley.newRequestQueue(getApplicationContext());
        imageLoader = new ImageLoader(queues, new BitmapCache());
        // 设置消息或者会话页面的头像
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {

            @Override
            public UserInfo getUserInfo(String rongid) {
                return new UserInfo(rongid, UserCacheUtil.getRemark(
                        context, rongid), Uri.parse(UserCacheUtil
                        .getAvator(context, rongid)));
            }
        }, true);
        SealAppContext.init(this);
        try {
            RongIM.registerMessageType(GroupNotificationMessage.class);
            RongIM.registerMessageType(FileMessage.class);
            RongIM.registerMessageTemplate(new FileMessageItemProvider());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Context getContext() {
        return context;

    }

    public static RequestQueue getHttpQueues() {
        return queues;

    }

    public static ImageLoader getImageLoader() {
        return imageLoader;

    }
}