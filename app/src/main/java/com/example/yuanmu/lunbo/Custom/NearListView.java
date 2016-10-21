package com.example.yuanmu.lunbo.Custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yuanmu.lunbo.Adapter.Near_personalsAdapter;
import com.example.yuanmu.lunbo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yuanmu on 2016/8/26.
 */
public class NearListView extends ListView {
    //高调征婚控件
    private TextView hpm_TV;
//    private RecyclerView rv_1;
    private List<Map<String, Object>> plist = new ArrayList<Map<String, Object>>();
    private Map<String, Object> pmap;
    private Near_personalsAdapter personalsadapter;
    private Context mcontext;
    private View header;
    public NearListView(Context context) {
        super(context);
        init(context);
    }

    public NearListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NearListView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        this.mcontext = context;
         header = LayoutInflater.from(context).inflate(
                R.layout.header_near, null);
        initView();
        this.addHeaderView(header);
    }


    private void initView() {

//        rv_1 = (RecyclerView) header.findViewById(R.id.rv_1);

    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, expandSpec);
    }
}
