package com.example.yuanmu.lunbo.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yuanmu.lunbo.Adapter.ChildAdapter;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.LocalImageBean;

import java.util.List;


public class LocalImage extends Activity implements ChildAdapter.LocalImageOnClick, View.OnClickListener {
    private GridView mGridView;
    private List<String> list;
    private ChildAdapter adapter;
    private int needcount;
    private TextView tv_count;
    private ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.localimage);
        Intent intent = getIntent();
        needcount = intent.getIntExtra("count", 0);
        mGridView = (GridView) findViewById(R.id.child_grid);
        tv_count = (TextView) findViewById(R.id.tv_count);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        list = getIntent().getStringArrayListExtra("data");
        adapter = new ChildAdapter(this, list, needcount, mGridView);
        adapter.setLocalImageOnClick(this);
        mGridView.setAdapter(adapter);
        tv_count.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        tv_count.setText("完成(" + LocalImageBean.Count + "/" + needcount + ")");

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

//	@Override
	public void onBackPressed() {
		Intent intent = new Intent();// 数据是使用Intent返回
        intent.putExtra("pos_back", "no");
		this.setResult(RESULT_OK, intent);// 设置返回数据
		super.onBackPressed();
	}

    @Override
    public void onclick() {
        LocalImageBean.clearlist();
        for (String key : LocalImageBean.mSelectMap.keySet()) {

            if (LocalImageBean.mSelectMap.get(key) == true) {
                LocalImageBean.addlist(key);
            }
        }
        LocalImageBean.Count = LocalImageBean.list.size();
        tv_count.setText("完成(" + LocalImageBean.Count + "/" + needcount + ")");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                Intent intent1 = new Intent();// 数据是使用Intent返回
                intent1.putExtra("pos_back", "no");
                this.setResult(RESULT_OK, intent1);// 设置返回数据
                finish();
                break;
            case R.id.tv_count:
                Intent intent2 = new Intent();// 数据是使用Intent返回
                intent2.putExtra("pos_back", "ok");
                this.setResult(RESULT_OK, intent2);// 设置返回数据
                finish();
                break;
        }
    }
}
