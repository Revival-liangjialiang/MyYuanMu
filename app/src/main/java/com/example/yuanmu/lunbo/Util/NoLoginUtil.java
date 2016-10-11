package com.example.yuanmu.lunbo.Util;

import android.content.Context;
import android.content.Intent;

import com.example.yuanmu.lunbo.Activity.LoginActivity;

/**
 * Created by k on 2016/9/25.
 */
public class NoLoginUtil {
    public static void login(Context context){
        context.startActivity(new Intent(context,LoginActivity.class));
    }
}
