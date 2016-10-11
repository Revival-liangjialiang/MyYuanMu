package com.example.yuanmu.lunbo.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;


/**
 * Created by Administrator on 2016/8/25 0025.
 */
public class VolleyRequest {
    public RequestQueue mRequestQueue;
    PictureRequestListener mListener;
    Context mContext;
    String[] mAddress;
    int a = 0;
    //判断是否加载完成
    int b = 0;
    public VolleyRequest(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
        mContext = context;
    }

    //开始请求图片
    public void startRequest(String[] address) {
        if(address == null){
            return;
        }
        mAddress = address;
        for (; a < mAddress.length; a++) {
            Log.i("ok"," mAddress[a] = "+ mAddress.length);
            mRequestQueue.add(new ImageRequest(mAddress[a], new Response.Listener<Bitmap>() {
              String str = mAddress[a];
                @Override
                public void onResponse(Bitmap bitmap) {
                    mListener.onRessponse(bitmap,str);
                    b++;
                    Log.i("ok","返回的图片数为 = "+b);
                    if(b == mAddress.length){
                        MyLog.i("ok","全部图片请求完成！");
                        MyLog.i("ok","mListener = "+mListener);
                        mListener.onFinish();
                        b=0;
                    }
                }
            }, 720, 0, ImageView.ScaleType.CENTER_CROP, null, null));
        }
        a = 0;
    }

    public interface PictureRequestListener {
        void onRessponse(Bitmap bitmap,String str);
        void onFinish();
    }

    public void PictureRequestListener(PictureRequestListener listener) {
        this.mListener = listener;
    }
}
