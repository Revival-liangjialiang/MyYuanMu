package com.example.yuanmu.lunbo.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.yuanmu.lunbo.Adapter.LoveActivityReAdapter;
import com.example.yuanmu.lunbo.R;

/**
 * Created by Administrator on 2016/9/2 0002.
 */
public class LoveActivity extends AppCompatActivity implements View.OnClickListener{
    RecyclerView mRe;
    TextView mBack_TV,mCreate_TV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_love_activity_layout);
        initView();
    }

    private void initView() {
        mCreate_TV = (TextView) findViewById(R.id.love_activity_create_TV);
        mBack_TV = (TextView) findViewById(R.id.love_activity_back_TV);
        mRe = (RecyclerView) findViewById(R.id.love_activity_RE);
        LinearLayoutManager layout_manager = new LinearLayoutManager(this);
        layout_manager.setOrientation(layout_manager.VERTICAL);
        mRe.setLayoutManager(layout_manager);
        mRe.setAdapter(new LoveActivityReAdapter(this));
        mBack_TV.setOnClickListener(this);
        mCreate_TV.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.love_activity_back_TV:
                finish();
                break;
            case R.id.love_activity_create_TV:
                //TODO 创建活动 待办事项
                break;
            default:break;
        }
    }
}
