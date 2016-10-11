package com.example.yuanmu.lunbo.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuanmu.lunbo.R;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.util.List;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class BirthdayDataSelectActivity extends AppCompatActivity implements View.OnClickListener{
    String mTitleStr="";
    Button mDetermineButton,mCancelButton;
    public static final String YEAR_LIST = "year",MONTH_LIST = "month",DAY_LIST = "day",YEAR="year",MONTH = "month",DAY = "day";
    List<String> mYearList,mMonthList,mDayList;
    WheelView hourWheelViewYear,hourWheelViewMonth,hourWheelViewDay;
    TextView mTitle_TV;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_birthday_select_layout);
        initView();
        Intent intent = getIntent();
        mTitleStr = intent.getStringExtra(ConditionalSelectionActivity.TITLE);
        mTitle_TV.setText(mTitleStr);
        mYearList = intent.getStringArrayListExtra(YEAR_LIST);
        mMonthList = intent.getStringArrayListExtra(MONTH_LIST);
        mDayList = intent.getStringArrayListExtra(DAY_LIST);
        if(mYearList!=null&&mMonthList!=null&&mDayList!=null) {
            initYearWheelview();
            initMonthWheelview();
            initDayWheelview();
        }
    }

    private void initView() {
        mTitle_TV = (TextView) findViewById(R.id.mTitle_TV);
        hourWheelViewYear = (WheelView) findViewById(R.id.hour_wheelviewYear);
        hourWheelViewMonth  = (WheelView) findViewById(R.id.hour_wheelviewMonth);
        hourWheelViewDay = (WheelView) findViewById(R.id.hour_wheelviewDay);
        mDetermineButton = (Button) findViewById(R.id.mDetermineButton);
        mCancelButton = (Button) findViewById(R.id.mCancelButton);
        mCancelButton.setOnClickListener(this);
        mDetermineButton.setOnClickListener(this);
    }
    private void initYearWheelview() {
        hourWheelViewYear.setWheelAdapter(new ArrayWheelAdapter(this));
        hourWheelViewYear.setSkin(WheelView.Skin.Holo);
        hourWheelViewYear.setWheelData(mYearList);
        //设置数据
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.selectedTextColor = Color.parseColor("#0288ce");
        style.textColor = Color.GRAY;
        style.selectedTextSize = 20;
        hourWheelViewYear.setStyle(style);
        hourWheelViewYear.setExtraText("", Color.parseColor("#0288ce"), 40, 70);
        hourWheelViewYear.setSelection(65);
    }
    private void initMonthWheelview() {
        hourWheelViewMonth.setWheelAdapter(new ArrayWheelAdapter(this));
        hourWheelViewMonth.setSkin(WheelView.Skin.Holo);
        hourWheelViewMonth.setWheelData(mMonthList);
        //设置数据
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.selectedTextColor = Color.parseColor("#0288ce");
        style.textColor = Color.GRAY;
        style.selectedTextSize = 20;
        hourWheelViewMonth.setStyle(style);
        hourWheelViewMonth.setExtraText("", Color.parseColor("#0288ce"), 40, 70);
        //TODO
    }
    private void initDayWheelview() {
        hourWheelViewDay.setWheelAdapter(new ArrayWheelAdapter(this));
        hourWheelViewDay.setSkin(WheelView.Skin.Holo);
        hourWheelViewDay.setWheelData(mDayList);
        //设置数据
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.selectedTextColor = Color.parseColor("#0288ce");
        style.textColor = Color.GRAY;
        style.selectedTextSize = 20;
        hourWheelViewDay.setStyle(style);
        hourWheelViewDay.setExtraText("", Color.parseColor("#0288ce"), 40, 70);
        //TODO
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
                String yearStr = (String) hourWheelViewYear.getSelectionItem();
                String monthStr = (String) hourWheelViewMonth.getSelectionItem();
                String dayStr = (String) hourWheelViewDay.getSelectionItem();
                if(!yearStr.equals("请选择")&&!monthStr.equals("请选择")&&!dayStr.equals("请选择")) {
                    Intent determineIntent = new Intent();
                    determineIntent.putExtra(YEAR, yearStr + ".");
                    determineIntent.putExtra(MONTH, monthStr + ".");
                    determineIntent.putExtra(DAY, dayStr);
                    setResult(RESULT_OK, determineIntent);
                    finish();
                }else{
                    Toast.makeText(BirthdayDataSelectActivity.this, "错误,有的项未选择!", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
