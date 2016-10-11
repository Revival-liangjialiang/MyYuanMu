
package com.example.yuanmu.lunbo.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yuanmu.lunbo.Adapter.GroupAdapter;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.CommenUtil;
import com.example.yuanmu.lunbo.Util.LocalImageBean;
import com.example.yuanmu.lunbo.Util.LocalImageListBean;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * 本地图片列表
 */
public class LocalImageList extends Activity implements View.OnClickListener {
    private HashMap<String, List<String>> mGruopMap = new HashMap<String, List<String>>();
    private List<LocalImageListBean> list = new ArrayList<LocalImageListBean>();
    private final static int SCAN_OK = 1;
    private ProgressDialog mProgressDialog;
    private GroupAdapter adapter;
    private GridView mGroupGridView;
    private int needcount;
    private TextView tv_count;
    private ImageView iv_back;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SCAN_OK:
                    // 关闭进度条
                    mProgressDialog.dismiss();
                    adapter = new GroupAdapter(LocalImageList.this,
                            list = subGroupOfImage(mGruopMap), mGroupGridView);
                    mGroupGridView.setAdapter(adapter);
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.localimagelist);
        Intent intent = getIntent();
        needcount = intent.getIntExtra("count", 0);
        mGroupGridView = (GridView) findViewById(R.id.main_grid);
        tv_count = (TextView) findViewById(R.id.tv_count);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        getImages();
        tv_count.setText("完成(" + LocalImageBean.Count + "/" + needcount + ")");

        mGroupGridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                List<String> childList = mGruopMap.get(list.get(position)
                        .getFolderName());

                Intent mIntent = new Intent(LocalImageList.this,
                        LocalImage.class);
                mIntent.putExtra("count", needcount);
                mIntent.putStringArrayListExtra("data",
                        (ArrayList<String>) childList);
                startActivityForResult(mIntent, 2);

            }
        });
        iv_back.setOnClickListener(this);
        tv_count.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        tv_count.setText("完成(" + LocalImageBean.Count + "/" + needcount + ")");
        String posback = data.getExtras().getString("pos_back");
        if (posback.equals("ok")) {
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中
     */
    private void getImages() {
        // 判断是否有外置存储卡
        boolean sd = CommenUtil.hasSDCard();
        if (sd == false) {
            return;
        }

        // 显示进度条
        mProgressDialog = ProgressDialog.show(this, null, "正在加载...");

        new Thread(new Runnable() {

            @Override
            public void run() {
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = LocalImageList.this
                        .getContentResolver();

                // 只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png"},
                        MediaStore.Images.Media.DATE_MODIFIED);

                while (mCursor.moveToNext()) {
                    // 获取图片的路径
                    String path = mCursor.getString(mCursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));

                    // 获取该图片的父路径名
                    String parentName = new File(path).getParentFile()
                            .getName();

                    // 根据父路径名将图片放入到mGruopMap中
                    if (!mGruopMap.containsKey(parentName)) {
                        List<String> chileList = new ArrayList<String>();
                        chileList.add(path);
                        mGruopMap.put(parentName, chileList);
                    } else {
                        mGruopMap.get(parentName).add(path);
                    }
                }

                mCursor.close();

                // 通知Handler扫描图片完成
                mHandler.sendEmptyMessage(SCAN_OK);

            }
        }).start();

    }

    /**
     * 组装分组界面GridView的数据源，因为我们扫描手机的时候将图片信息放在HashMap中 所以需要遍历HashMap将数据组装成List
     *
     * @param mGruopMap
     * @return
     */
    private List<LocalImageListBean> subGroupOfImage(
            HashMap<String, List<String>> mGruopMap) {
        if (mGruopMap.size() == 0) {
            return null;
        }
        List<LocalImageListBean> list = new ArrayList<LocalImageListBean>();

        Iterator<Map.Entry<String, List<String>>> it = mGruopMap.entrySet()
                .iterator();
        while (it.hasNext()) {
            Map.Entry<String, List<String>> entry = it.next();
            LocalImageListBean mImageBean = new LocalImageListBean();
            String key = entry.getKey();
            List<String> value = entry.getValue();

            mImageBean.setFolderName(key);// 设置文件名
            mImageBean.setImageCounts(value.size());// 设置文件大小
            mImageBean.setTopImagePath(value.get(0));// 获取该组的第一张图片

            list.add(mImageBean);
        }

        return list;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_count:
                finish();
                break;
        }
    }
}
