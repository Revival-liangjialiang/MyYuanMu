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
 * Created by k on 2016/9/17.
 */
public class DataSelectActivityTwo extends AppCompatActivity implements View.OnClickListener{
    List<String> mList = new ArrayList<>();
    String mTitleStr;
    TextView mTitle_TV;
    Button mDetermineButton,mCancelButton;
    WheelView hourWheelViewLeft,hourWheelViewRight;
    RelativeLayout mAge_Select_Popup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_data_select_activity_two_layout);
        new StatusBarColorUtil(this,R.color.StyleColor);
        initView();
        Intent intent = getIntent();
        mList = intent.getStringArrayListExtra(ConditionalSelectionActivity.LIST);
        mTitleStr = intent.getStringExtra(ConditionalSelectionActivity.TITLE);
        mTitle_TV.setText(mTitleStr);
        initLeftWheelview();
        initRightWheelview();
    }
    private void initView() {
        mTitle_TV = (TextView) findViewById(R.id.mTitle_TV);
        mDetermineButton = (Button) findViewById(R.id.mDetermineButton);
        mCancelButton = (Button) findViewById(R.id.mCancelButton);
        hourWheelViewLeft = (WheelView) findViewById(R.id.hour_wheelviewLeft);
        hourWheelViewRight = (WheelView) findViewById(R.id.hour_wheelviewRight);
        mAge_Select_Popup = (RelativeLayout) findViewById(R.id.mAge_Select_Popup);
        mAge_Select_Popup.setOnClickListener(this);
        mCancelButton.setOnClickListener(this);
        mDetermineButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mCancelButton:
            case R.id.mAge_Select_Popup:
                finish();
                break;
            //确认按钮，确认之后返回数据
            case R.id.mDetermineButton:
                Intent determineIntent = new Intent();
                determineIntent.putExtra(ConditionalSelectionActivity.FROM, "" + hourWheelViewLeft.getSelectionItem());
                determineIntent.putExtra(ConditionalSelectionActivity.TO, "" + hourWheelViewRight.getSelectionItem());
                setResult(RESULT_OK, determineIntent);
                finish();
                break;
            default:
                break;
        }
    }
    //初始化左边选择器
    private void initLeftWheelview() {
        hourWheelViewLeft.setWheelAdapter(new ArrayWheelAdapter(this));
        hourWheelViewLeft.setSkin(WheelView.Skin.Holo);
        //设置数据
        hourWheelViewLeft.setWheelData(mList);
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.selectedTextColor = Color.parseColor("#0288ce");
        style.textColor = Color.GRAY;
        style.selectedTextSize = 20;
        hourWheelViewLeft.setStyle(style);
        hourWheelViewLeft.setExtraText("", Color.parseColor("#0288ce"), 40, 70);
    }
    //初始化右边选择器
    private void initRightWheelview() {
        hourWheelViewRight.setWheelAdapter(new ArrayWheelAdapter(this));
        //设置皮肤
        hourWheelViewRight.setSkin(WheelView.Skin.Holo);
        //设置数据
        hourWheelViewRight.setWheelData(mList);
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.selectedTextColor = Color.parseColor("#0288ce");
        style.textColor = Color.GRAY;
        style.selectedTextSize = 20;

        hourWheelViewRight.setStyle(style);

        hourWheelViewRight.setExtraText("", Color.parseColor("#0288ce"), 40, 70);
    }
}
