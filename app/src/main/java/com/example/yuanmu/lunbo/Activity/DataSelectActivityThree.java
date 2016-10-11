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
import com.example.yuanmu.lunbo.Util.MyMap;
import com.example.yuanmu.lunbo.Util.StatusBarColorUtil;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/17 0017.
 */
public class DataSelectActivityThree extends AppCompatActivity implements View.OnClickListener{
    List<String> mListLift = new ArrayList<>();
    List<String> mListMiddle = new ArrayList<>();
    List<String> mListRight = new ArrayList<>();
    private Map<String, String[]> cityMap = new HashMap<String, String[]>();//key:省p---value:市n  value是一个集合
    private Map<String, String[]> areaMap = new HashMap<String, String[]>();//key:市n---value:区s    区也是一个集合
    //全部省
    private String[] allProv,allCity,allArea;
    String mTitleStr;
    TextView mTitle_TV;
    Button mDetermineButton,mCancelButton;
    WheelView hourWheelViewLeft,hourWheelViewRight,hourWheelViewMiddle;
    RelativeLayout mAge_Select_Popup;
    String mListHeadValue = "不限";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_data_select_activity_middle_layout);
        new StatusBarColorUtil(this,R.color.StyleColor);
        initView();
        Intent intent = getIntent();
        if(intent.getStringExtra("mListHeadValue")!=null) {
            mListHeadValue = intent.getStringExtra("mListHeadValue");
        }
        mTitleStr = intent.getStringExtra(ConditionalSelectionActivity.TITLE);
        mTitle_TV.setText(mTitleStr);
        allProv = intent.getStringArrayExtra(ConditionalSelectionActivity.PROVINCE);
        Bundle bundle = intent.getExtras();
        MyMap myMap = (MyMap) bundle.get("map");
        cityMap = myMap.getCityMap();
        areaMap = myMap.getAreaMap();
        //初始化左中右选择器的数据
        initMiddleWheelview();
        initRightWheelview();
        initLeftWheelview();
    }

    private void initMiddleWheelview() {
        hourWheelViewMiddle.setWheelAdapter(new ArrayWheelAdapter(this));
        hourWheelViewMiddle.setSkin(WheelView.Skin.Holo);
        mListMiddle.add(mListHeadValue);
        hourWheelViewMiddle.setWheelData(mListMiddle);
        //设置数据
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.selectedTextColor = Color.parseColor("#0288ce");
        style.textColor = Color.GRAY;
        style.selectedTextSize = 20;
        hourWheelViewMiddle.setStyle(style);
        hourWheelViewMiddle.setExtraText("", Color.parseColor("#0288ce"), 40, 70);
        //TODO
    }

    private void initView() {
        hourWheelViewMiddle  = (WheelView) findViewById(R.id.hour_wheelviewMiddle);
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
                determineIntent.putExtra(ConditionalSelectionActivity.PROVINCE,(String)hourWheelViewLeft.getSelectionItem());
                determineIntent.putExtra(ConditionalSelectionActivity.CITY,(String)hourWheelViewMiddle.getSelectionItem());
                determineIntent.putExtra(ConditionalSelectionActivity.AREA,(String)hourWheelViewRight.getSelectionItem());
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
        mListLift.add(mListHeadValue);
        for(String str:allProv){
            mListLift.add(str);
            //TODO
        }
        //设置数据
        hourWheelViewLeft.setWheelData(mListLift);
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.selectedTextColor = Color.parseColor("#0288ce");
        style.textColor = Color.GRAY;
        style.selectedTextSize = 20;
        hourWheelViewLeft.setStyle(style);
        hourWheelViewLeft.setExtraText("", Color.parseColor("#0288ce"), 40, 70);
        hourWheelViewLeft.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                mListMiddle.clear();
                allCity = null;
                allCity = cityMap.get(o.toString());
                if(allCity !=null) {
                    mListMiddle.add(mListHeadValue);
                    for (String str : allCity) {
                        mListMiddle.add(str);
                    }
                }else {
                    mListMiddle.add(mListHeadValue);
                }
                hourWheelViewMiddle.setWheelData(mListMiddle);
                mListRight.clear();
                allArea =null;
                if (areaMap.get(hourWheelViewMiddle.getSelectionItem()) != null) {
                    allArea = areaMap.get(hourWheelViewMiddle.getSelectionItem());
                    for (String str : allArea) {
                        mListRight.add(str);
                    }
                    hourWheelViewRight.setWheelData(mListRight);
                }else{
                    mListRight.add(mListHeadValue);
                    hourWheelViewRight.setWheelData(mListRight);
                }
                hourWheelViewMiddle.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
                    @Override
                    public void onItemSelected(int position, Object o) {
                        mListRight.clear();
                        allArea = null;
                        if (areaMap.get(o.toString()) != null) {
                            allArea = areaMap.get(o.toString());
                            mListRight.add(mListHeadValue);
                            for (String str : allArea) {
                                mListRight.add(str);
                            }
                            hourWheelViewRight.setWheelData(mListRight);
                        }else{
                            mListRight.add(mListHeadValue);
                            hourWheelViewRight.setWheelData(mListRight);
                        }
                    }
                });
            }
        });
    }
    //初始化右边选择器
    private void initRightWheelview() {
        hourWheelViewRight.setWheelAdapter(new ArrayWheelAdapter(this));
        //设置皮肤
        hourWheelViewRight.setSkin(WheelView.Skin.Holo);
        mListRight.add(mListHeadValue);
        hourWheelViewRight.setWheelData(mListRight);
        //设置数据
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.selectedTextColor = Color.parseColor("#0288ce");
        style.textColor = Color.GRAY;
        style.selectedTextSize = 20;
        hourWheelViewRight.setStyle(style);
        hourWheelViewRight.setExtraText("", Color.parseColor("#0288ce"), 40, 70);
    }
}

