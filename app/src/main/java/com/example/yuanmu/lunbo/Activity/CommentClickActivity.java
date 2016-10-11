package com.example.yuanmu.lunbo.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.StatusBarColorUtil;

/**
 * Created by k on 2016/9/28.
 */
public class CommentClickActivity extends AppCompatActivity{
    public static final String CONTENT = "CONTENT",CLICK_TYPE = "CLICK_TYPE",INPUT_TEXT = "INTPUT_TEXT";
    private RelativeLayout mMainLayout;
    private EditText mInput_et;
    private TextView mCommentAndReply_tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lifecircle_comment_click_activity_layout);
        //设置一下状态栏颜色
        new StatusBarColorUtil(this,R.color.StatusBarColor);
        initView();
        Intent intent = getIntent();
        String clickType = intent.getStringExtra(CLICK_TYPE);
        String inputText = intent.getStringExtra(INPUT_TEXT);
        mCommentAndReply_tv.setText(clickType);
        mInput_et.setHint(inputText);
        //弹出输入法
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
    private void initView() {
        mMainLayout = (RelativeLayout) findViewById(R.id.mMainLayout);
        mCommentAndReply_tv = (TextView) findViewById(R.id.mCommentAndReply_tv);
        mInput_et = (EditText) findViewById(R.id.mInput_et);
        mCommentAndReply_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(CONTENT,mInput_et.getText().toString());
                setResult(RESULT_OK,intent);
                //关闭输入法
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                finish();
            }
        });
        mMainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
