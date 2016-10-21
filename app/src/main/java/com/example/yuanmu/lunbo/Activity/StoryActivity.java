package com.example.yuanmu.lunbo.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.yuanmu.lunbo.Adapter.StoryAdapter;
import com.example.yuanmu.lunbo.BmobBean.Story;
import com.example.yuanmu.lunbo.Custom.StoryListView;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.StatusBarColorUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by yuanmu on 2016/9/2.
 */
public class StoryActivity extends AppCompatActivity implements View.OnClickListener{
    private Context context;
    private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    private Map<String, Object> map;
    private StoryListView lv_top;
    private StoryAdapter adapter;
    private ImageView iv_edit;
private static final int POS_INTENT_1 = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        new StatusBarColorUtil(this,R.color.StyleColor);
        context = StoryActivity.this;
        initView();
        initData();
        getData();
        initEvent();
    }


    protected void getData() {
        BmobQuery<Story> query = new BmobQuery<Story>();
        query.setLimit(10);
        query.include("user");
//执行查询方法
        query.findObjects(new FindListener<Story>() {
            @Override
            public void done(List<Story> object, BmobException e) {
                if (e == null) {
                    list.clear();
                    for (Story story : object) {
                        String title = story.getTitle();
                        String img = story.getUser().getImg();
                        String nickname = story.getUser().getNickname();
                        String createdAt = story.getCreatedAt();
                        List<String>  imgarray = story.getImg();
                        String objectId = story.getObjectId();
                        map = new HashMap<String, Object>();
                        map.put("title", title);
                        map.put("img", img);
                        map.put("nickname", nickname);
                        map.put("createdAt", createdAt);
                        map.put("imgarray", imgarray);
                        map.put("objectId", objectId);
                        list.add(map);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    private void initEvent() {
        iv_edit.setOnClickListener(this);
    }

    private void initData() {
        adapter = new StoryAdapter(context, list);
        lv_top.setAdapter(adapter);

    }

    private void initView() {
        lv_top = (StoryListView) findViewById(R.id.lv_top);
        iv_edit = (ImageView) findViewById(R.id.iv_edit);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //编辑故事
            case R.id.iv_edit:
                Intent intent = new Intent(context,Release_StoryActivity.class);
                startActivityForResult(intent,POS_INTENT_1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case POS_INTENT_1:
                getData();
                break;
        }
    }
}
