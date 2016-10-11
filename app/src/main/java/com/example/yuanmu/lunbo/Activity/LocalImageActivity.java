package com.example.yuanmu.lunbo.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.example.yuanmu.lunbo.Application.MyApplication;
import com.example.yuanmu.lunbo.Custom.ZoomImageView;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.BitmapCompressUtil;

import java.io.IOException;


/**
 * Created by yuanmu on 2016/9/19.
 */
public class LocalImageActivity extends Activity{
    private ZoomImageView ziv_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localimage);
        Intent intent = getIntent();
        String path = intent.getStringExtra("path");
        ziv_1 = (ZoomImageView) findViewById(R.id.ziv_1);
        try {
            Bitmap addbmps = BitmapCompressUtil.revitionImageSize(
                    MyApplication.getContext(),
                    path, 800);
            ziv_1.setImageBitmap(addbmps);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
