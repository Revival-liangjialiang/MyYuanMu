package com.example.yuanmu.lunbo.Custom;

/**
 * Created by Administrator on 2016/8/26 0026.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import com.example.yuanmu.lunbo.R;

/**
 * Created by k on 2016/6/7.
 */
public class MyView extends View {
    Context context;
    int mHeight = 0,mWidth = 0;
    public Bitmap bitmap = null;
    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.myviewtest);
        this.context = context;
    }
    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int f = measuredWidth(widthMeasureSpec);
        int g = measuredHeight(heightMeasureSpec);
        setMeasuredDimension(f, g);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int min = Math.min(mWidth, mHeight);
        /**
         * 长度如果不一致，按小的值进行压缩
         */
        bitmap = Bitmap.createScaledBitmap(bitmap, min, min, false);
        canvas.drawBitmap(createCircleImage(bitmap, min), 0, 0, null);
    }
    //************************************************************************************************
    private int measuredWidth(int widthMeasureSpec) {
        int Mode = MeasureSpec.getMode(widthMeasureSpec);
        int Size = MeasureSpec.getSize(widthMeasureSpec);
        if (Mode == MeasureSpec.EXACTLY) {
            mWidth = Size;
        } else {
            //由图片决定大小
            int value = getPaddingLeft()+getPaddingRight()+bitmap.getWidth();
            if (Mode == MeasureSpec.AT_MOST) {
                //由图片和Padding决定宽度，但是不能超过View的宽
                mWidth = Math.min(value,Size);
            }
        }
        return mWidth;
    }
    //**********************************************************************************************
    private int measuredHeight(int heightMeasureSpec) {
        int Mode = MeasureSpec.getMode(heightMeasureSpec);
        int Size = MeasureSpec.getSize(heightMeasureSpec);
        if (Mode == MeasureSpec.EXACTLY) {
            mHeight = Size;
        } else {
            //由图片决定高度
            int intvalur1 = getPaddingTop()+getPaddingBottom()+bitmap.getHeight();
            if (Mode == MeasureSpec.AT_MOST) {
                //由图片和Padding决定大小，但是不能超过View的高
                mHeight = Math.min(intvalur1,Size);
            }
        }
        return mHeight;
    }
    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    private Bitmap createCircleImage(Bitmap source, int min)
    {
        final Paint paint = new Paint();
        final Paint paint1 = new Paint();
        final Paint paint2 = new Paint();
        //消除锯齿，让绘制出来的画更清晰
        paint.setAntiAlias(true);
        //创建一个Bitmap
        Bitmap target = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);

        /**
         * 产生一个同样大小的画布
         */
        //以后再Canvas上的画图操作都会作用在target上
        Canvas canvas = new Canvas(target);

        /**
         * 首先绘制圆形
         */
        //第一个参数为圆心的x坐标，第二个参数为圆心的y坐标，第三个为圆心的半径
        canvas.drawCircle(min / 2, min / 2, min / 2, paint);
        /**
         * 使用SRC_IN模式显示后画图的交集处
         */
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //这种模式是前图和后图一起显示，如果有相交的部分只显示后图的
        paint1.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        /**
         * 绘制图片
         */
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }

}