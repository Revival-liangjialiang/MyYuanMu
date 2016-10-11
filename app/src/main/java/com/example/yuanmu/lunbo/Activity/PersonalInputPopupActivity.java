package com.example.yuanmu.lunbo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuanmu.lunbo.R;

/**
 * Created by Administrator on 2016/9/19 0019.
 */
public class PersonalInputPopupActivity extends AppCompatActivity implements View.OnClickListener{
    TextView mPopupTitle_TV;
    Button mCancel_bt,mDetermine_bt;
    RelativeLayout mInputPopup_ReLayout;
    EditText mInput_et;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_input_popup_layout);
        initView();
        Intent intent = getIntent();
        mPopupTitle_TV.setText(intent.getStringExtra(ConditionalSelectionActivity.TITLE));
    }

    private void initView() {
        mInput_et = (EditText) findViewById(R.id.mInput_et);
        mInputPopup_ReLayout = (RelativeLayout) findViewById(R.id.mInputPopup_ReLayout);
        mInputPopup_ReLayout = (RelativeLayout) findViewById(R.id.mInputPopup_ReLayout);
        mPopupTitle_TV = (TextView) findViewById(R.id.mPopupTitle_TV);
        mCancel_bt = (Button) findViewById(R.id.mCancel_bt);
        mDetermine_bt = (Button) findViewById(R.id.mDetermine_bt);
        mCancel_bt.setOnClickListener(this);
        mDetermine_bt.setOnClickListener(this);
        mInputPopup_ReLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mCancel_bt:
                finish();
                break;
            case R.id.mDetermine_bt:
                String str = mInput_et.getText().toString();
                if(!str.equals("")) {
                    Intent intent = new Intent();
                    intent.putExtra(ConditionalSelectionActivity.VALUE,mInput_et.getText().toString());
                    setResult(RESULT_OK,intent);
                    finish();
                }else{
                    Toast.makeText(PersonalInputPopupActivity.this, "内容不能为空!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.mInputPopup_ReLayout:
                mInputPopup_ReLayout.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }
}
