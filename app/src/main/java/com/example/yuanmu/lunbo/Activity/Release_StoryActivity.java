package com.example.yuanmu.lunbo.Activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yuanmu.lunbo.Custom.RichTextEditor;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.FileUtils;
import com.example.yuanmu.lunbo.Application.MyApplication;
import com.example.yuanmu.lunbo.BmobBean.Story;
import com.example.yuanmu.lunbo.BmobBean.User;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;


/**
 * Created by yuanmu on 2016/9/6.
 */
public class Release_StoryActivity extends Activity implements View.OnClickListener {
    private final int REQUEST_CODE_CAPTURE_CAMEIA = 100;
    private final int REQUEST_CODE_PICK_IMAGE = 200;
    private Context context;
    String[] path;
    String mPic_uri;
    boolean mSwitch = true;
    private LinearLayout line_rootView;
    //	private InterceptLinearLayout line_intercept;
    private RichTextEditor richText;
    private EditText et_name;
    private TextView tv_ok;
    private boolean isKeyBoardUp, isEditTouch;// 判断软键盘的显示与隐藏
    private File mCameraImageFile;// 照相机拍照得到的图片
    private FileUtils mFileUtils;
    List<String> list_path  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                        | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_release_story);
        context = this;
        init();
    }

    private void init() {

        mFileUtils = new FileUtils(context);
        line_rootView = (LinearLayout) findViewById(R.id.line_rootView);
//		line_intercept = (InterceptLinearLayout) findViewById(R.id.line_intercept);
        tv_ok = (TextView) findViewById(R.id.tv_ok);
        tv_ok.setOnClickListener(this);
        et_name = (EditText) findViewById(R.id.et_name);
        richText = (RichTextEditor) findViewById(R.id.richText);
        initRichEdit();
        tv_ok.setText("提交");
    }

    private void initRichEdit() {
        ImageView img_addPicture, img_takePicture;
        img_addPicture = (ImageView) findViewById(R.id.img_addPicture);
        img_addPicture.setOnClickListener(this);
        img_takePicture = (ImageView) findViewById(R.id.img_takePicture);
        img_takePicture.setOnClickListener(this);

    }

    private void openCamera() {
        try {
            File PHOTO_DIR = new File(mFileUtils.getStorageDirectory());
            if (!PHOTO_DIR.exists())
                PHOTO_DIR.mkdirs();// 创建照片的存储目录

            mCameraImageFile = new File(PHOTO_DIR, getPhotoFileName());// 给新照的照片文件命名
            final Intent intent = getTakePickIntent(mCameraImageFile);
            startActivityForResult(intent, REQUEST_CODE_CAPTURE_CAMEIA);
        } catch (ActivityNotFoundException e) {
        }
    }

    private Intent getTakePickIntent(File f) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        return intent;
    }

    /**
     * 用当前时间给取得的图片命名
     */
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyy_MM_dd_HH_mm_ss");
        return dateFormat.format(date) + ".jpg";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_PICK_IMAGE) {
            Uri uri = data.getData();
            richText.insertImage(mFileUtils.getFilePathFromUri(uri));
        } else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA
                && resultCode == Activity.RESULT_OK) {
            richText.insertImage(mCameraImageFile.getAbsolutePath());
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFileUtils.deleteRichTextImage();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_ok:
                if(mSwitch) {
                    mSwitch = false;
                    Log.i("打印信息", richText.getRichEditData().get("content") + "");
                    path = new String[MyApplication.value];
                    for (int a = 0; a < MyApplication.value; a++) {
                        path[a] = MyApplication.img_path[a];
                    }
                    BmobFile.uploadBatch(path, new UploadBatchListener() {
                        @Override
                        public void onError(int statuscode, String errormsg) {
                            mSwitch = true;
                        }

                        @Override
                        public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {
                            Log.i("进度", curPercent + "");
                            mSwitch = true;
                        }

                        @Override
                        public void onSuccess(List<BmobFile> files, List<String> urls) {
                            if (urls.size() == MyApplication.value) {//如果数量相等，则代表文件全部上传完成
                                next(urls);
                                MyApplication.value = 0;
                                mSwitch = true;
                            }
                        }
                    });
                }
                break;
            case R.id.img_addPicture:
                // 打开系统相册
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");// 相片类型
                startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
                break;
            case R.id.img_takePicture:
                // 打开相机
                openCamera();
                break;
        }
    }
    void next(List<String> url) {
        for (int a = 0; a < MyApplication.value; a++) {
            list_path.add(url.get(a));
        }
        User user = BmobUser.getCurrentUser(User.class);
        Log.i("ok","user = "+user.getNickname());
        Story story = new Story();
        story.setTitle(et_name.getText().toString());
        /*Log.i("ok","---------------------------Content = "+richText.getRichEditData().get("content") + "");*/
        mPic_uri = richText.getRichEditData().get("content") + "";
        for(int a = 0;a<MyApplication.value;a++){
            /*Log.i("ok","MyApplication.img_path[a] = "+MyApplication.img_path[a]+"      url.get(a)"+url.get(a));*/
        mPic_uri = mPic_uri.replaceAll(MyApplication.img_path[a],url.get(a));
        }
//        Log.i("ok", "-----------------------mPic_uri等于 = " +mPic_uri);
        story.setContent(mPic_uri);
        story.setImg(list_path);
        story.setUser(user);
        story.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Log.i("ok", "Story上传成功！");
                    finish();
                } else {
                    Log.i("ok", "Story上传失败！");
                }
            }
        });
    }
}
