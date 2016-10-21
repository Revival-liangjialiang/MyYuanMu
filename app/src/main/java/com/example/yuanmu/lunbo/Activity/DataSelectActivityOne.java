package com.example.yuanmu.lunbo.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.StatusBarColorUtil;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/17 0017.
 */
public class DataSelectActivityOne extends AppCompatActivity implements View.OnClickListener{
        List<String> mList = new ArrayList<>();
        String mTitleStr;
        TextView mTitle_TV;
        Button mDetermineButton,mCancelButton;
        WheelView hourWheelView;
        RelativeLayout mAge_Select_Popup;

@Override
protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_data_select_activity_one_layout);
        new StatusBarColorUtil(this,R.color.StyleColor);
        initView();
        Intent intent = getIntent();
        mList = intent.getStringArrayListExtra(ConditionalSelectionActivity.LIST);
        mTitleStr = intent.getStringExtra(ConditionalSelectionActivity.TITLE);
        mTitle_TV.setText(mTitleStr);
        initWheelview();
        }
private void initView() {
        mTitle_TV = (TextView) findViewById(R.id.mTitle_TV);
        mDetermineButton = (Button) findViewById(R.id.mDetermineButton);
        mCancelButton = (Button) findViewById(R.id.mCancelButton);
        hourWheelView = (WheelView) findViewById(R.id.hour_wheelview);
        mCancelButton.setOnClickListener(this);
        mDetermineButton.setOnClickListener(this);
        }
@Override
public void onClick(View view) {
        switch (view.getId()) {
        case R.id.mCancelButton:
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
        finish();
        break;
        //确认按钮，确认之后返回数据
        case R.id.mDetermineButton:
        Intent determineIntent = new Intent();
        determineIntent.putExtra(ConditionalSelectionActivity.VALUE, "" + hourWheelView.getSelectionItem());
        setResult(RESULT_OK, determineIntent);
        finish();
        break;
default:
        break;
        }
        }
//初始化左边选择器
private void initWheelview() {
        hourWheelView.setWheelAdapter(new ArrayWheelAdapter(this));
        hourWheelView.setSkin(WheelView.Skin.Holo);
        //设置数据
        hourWheelView.setWheelData(mList);
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.selectedTextColor = Color.parseColor("#0288ce");
        style.textColor = Color.GRAY;
        style.selectedTextSize = 20;
        hourWheelView.setStyle(style);
        hourWheelView.setExtraText("", Color.parseColor("#0288ce"), 40, 70);
        }
        }

