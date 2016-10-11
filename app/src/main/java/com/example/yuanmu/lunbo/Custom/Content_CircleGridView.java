package com.example.yuanmu.lunbo.Custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by yuanmu on 2016/8/29.
 */
public class Content_CircleGridView extends GridView{
    public Content_CircleGridView(Context context) {
        super(context);
    }

    public Content_CircleGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Content_CircleGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
