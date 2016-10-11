package com.example.yuanmu.lunbo.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuanmu.lunbo.Adapter.GridImageAdapter;
import com.example.yuanmu.lunbo.BmobBean.Lifecircle;
import com.example.yuanmu.lunbo.BmobBean.User;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.LocalImageBean;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

/**
 * Created by yuanmu on 2016/9/7.
 */
public class Release_LifeActivity extends Activity implements View.OnClickListener {
    private Context context;
    private GridView gv_myimg;
    private ImageView iv_addphoto;
    private GridImageAdapter adapter;
    private static final int STATUS_INTENT_NUMBER = 0;
    private TextView tv_ok;
    private EditText et_content;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_circle);
        context = Release_LifeActivity.this;

        tv_ok = (TextView) findViewById(R.id.tv_ok);
        gv_myimg = (GridView) findViewById(R.id.gv_myimg);
        iv_addphoto = (ImageView) findViewById(R.id.iv_addphoto);
        et_content = (EditText) findViewById(R.id.et_content);
        initData();
        iv_addphoto.setOnClickListener(this);
        tv_ok.setOnClickListener(this);
    }

    private void initData() {
        LocalImageBean.init();
        adapter = new GridImageAdapter(this, LocalImageBean.list);
        gv_myimg.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_addphoto:
                Intent intent2 = new Intent();
                intent2.putExtra("count", 9);
                intent2.setClass(context, LocalImageList.class);
                startActivityForResult(intent2, STATUS_INTENT_NUMBER);
                break;
            case R.id.tv_ok:
                Toast.makeText(context, "开始上传", Toast.LENGTH_SHORT).show();
                content = et_content.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    return;
                }
                final String[] imgarry = new String[LocalImageBean.list.size()];
                LocalImageBean.list.toArray(imgarry);
                if(imgarry.length == 0){
                    List<String> urls = null;
                    next(urls);
                    return;
                }
                BmobFile.uploadBatch(imgarry, new UploadBatchListener() {
                    @Override
                    public void onError(int statuscode, String errormsg) {
//			        ShowToast("错误码"+statuscode +",错误描述："+errormsg);
                        Log.i("错误", statuscode + errormsg);
                        Toast.makeText(context, statuscode + errormsg, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onProgress(int curIndex, int curPercent, int total,int totalPercent) {
                        Log.i("进度", curPercent + "");
                        //1、curIndex--表示当前第几个文件正在上传
                        //2、curPercent--表示当前上传文件的进度值（百分比）
                        //3、total--表示总的上传文件数
                        //4、totalPercent--表示总的上传进度（百分比）
                    }

                    @Override
                    public void onSuccess(List<BmobFile> files, List<String> urls) {
                        //1、files-上传完成后的BmobFile集合，是为了方便大家对其上传后的数据进行操作，例如你可以将该文件保存到表中
                        //2、urls-上传文件的完整url地址
                        Toast.makeText(context, "上传完成！", Toast.LENGTH_LONG).show();
                        if(urls.size()==imgarry.length){//如果数量相等，则代表文件全部上传完成
                            next(urls);
                        }

                    }
                });



                break;
        }
    }

    protected void next(List<String> urls) {

        User user = BmobUser.getCurrentUser(User.class);
        // 创建帖子信息
        Lifecircle post = new Lifecircle();
        post.setContent(content);
        post.setImgarray(urls);
        // 添加一对一关联
        post.setUser(user);
        post.save(new SaveListener<String>() {
            @Override
            public void done(String object, BmobException e) {
                if (e == null) {
                    Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT)
                            .show();
                    Log.i("bmob", "保存成功");
                    finish();
                } else {
                    Toast.makeText(context, "保存失败", Toast.LENGTH_SHORT)
                            .show();
                    Log.i("bmob", "保存失败：" + e.getMessage());
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case STATUS_INTENT_NUMBER:
                adapter.notifyDataSetChanged();
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}