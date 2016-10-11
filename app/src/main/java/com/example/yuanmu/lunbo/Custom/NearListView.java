package com.example.yuanmu.lunbo.Custom;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yuanmu.lunbo.Activity.MarriageListActivity;
import com.example.yuanmu.lunbo.Adapter.Near_personalsAdapter;
import com.example.yuanmu.lunbo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuanmu on 2016/8/26.
 */
public class NearListView extends ListView {
    //高调征婚控件
    private TextView hpm_TV;
    private RecyclerView rv_1;
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
        initData();
        getData();
        this.addHeaderView(header);
    }

    private void getData() {

        pmap = new HashMap<String, Object>();
        pmap.put("img", "http://c.hiphotos.baidu.com/image/h%3D360/sign=cd77242f0e46f21fd6345855c6266b31/8c1001e93901213fa2b3240850e736d12e2e9565.jpg");
        pmap.put("txt", "林妹妹");
        plist.add(pmap);
        pmap = new HashMap<String, Object>();
        pmap.put("img", "http://b.hiphotos.baidu.com/image/h%3D360/sign=c57205b730a85edfe58cf825795509d8/d31b0ef41bd5ad6ea487080d85cb39dbb7fd3cde.jpg");
        pmap.put("txt", "王小花");
        plist.add(pmap);
        pmap = new HashMap<String, Object>();
        pmap.put("img", "http://h.hiphotos.baidu.com/image/h%3D360/sign=20c0fc45ba99a90124355d302d940a58/2934349b033b5bb5f61a00af34d3d539b700bc95.jpg");
        pmap.put("txt", "小姨子");
        plist.add(pmap);
        pmap = new HashMap<String, Object>();
        pmap.put("img", "http://f.hiphotos.baidu.com/image/h%3D360/sign=ea34bba27b310a55db24d8f287454387/09fa513d269759ee206f419ab0fb43166d22dfa7.jpg");
        pmap.put("txt", "二狗子他妈");
        plist.add(pmap);
        pmap = new HashMap<String, Object>();
        pmap.put("img", "http://a.hiphotos.baidu.com/image/h%3D360/sign=328d23fe7ff40ad10ae4c1e5672e1151/d439b6003af33a87d1a614dcc25c10385243b528.jpg");
        pmap.put("txt", "铁哥哥");
        plist.add(pmap);
        pmap = new HashMap<String, Object>();
        pmap.put("img", "http://h.hiphotos.baidu.com/image/h%3D360/sign=da9656df84cb39dbdec06150e01609a7/2f738bd4b31c8701a6d980d3227f9e2f0708ff93.jpg");
        pmap.put("txt", "马大姐");
        plist.add(pmap);
        personalsadapter.notifyDataSetChanged();
    }

    private void initData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mcontext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_1.setLayoutManager(linearLayoutManager);
        personalsadapter = new Near_personalsAdapter(mcontext,plist);
        rv_1.setAdapter(personalsadapter);
    }

    private void initView() {
        hpm_TV = (TextView) header.findViewById(R.id.hpm_TV);
        rv_1 = (RecyclerView) header.findViewById(R.id.rv_1);
        hpm_TV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mcontext.startActivity(new Intent(mcontext, MarriageListActivity.class));
            }
        });
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, expandSpec);
    }
}
