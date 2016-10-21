package com.example.yuanmu.lunbo.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuanmu.lunbo.Adapter.AlbumRVAdapter;
import com.example.yuanmu.lunbo.Adapter.HeadPortraitReAdapter;
import com.example.yuanmu.lunbo.BmobBean.User;
import com.example.yuanmu.lunbo.Fragment.Personal.PersonalDataFragment;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.MyLog;
import com.example.yuanmu.lunbo.Util.MyMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2016/9/18 0018.
 * 资料修改活动
 */
public class EditPersonalDataActivity extends AppCompatActivity implements View.OnClickListener{
    private PersonalDataFragment personalDataFragment;
    //网络监听字段
    private IntentFilter intentfilter;// IntentFilter：意图过滤器。
    private NetworkChangeReceier networkchangereceier;
    LinearLayout mListener_network_layout;
    private String[] allProv;
    private JSONObject jsonObject;
    private Map<String, String[]> cityMap = new HashMap<String, String[]>();//key:省p---value:市n  value是一个集合
    private Map<String, String[]> areaMap = new HashMap<String, String[]>();//key:市n---value:区s    区也是一个集合
    // 布局
    RelativeLayout mMonthlyIncome_ReLayout,mWorkingAreaReLayout,mHave_no_childrenReLayout,mWhether_you_want_a_childReLayout,mHousing_situationReLayout,
            mBuying_a_carReLayout,mOccupationReLayout,mPlace_of_originReLayout,mWeightReLayout,mShapeReLayout,mSmoking_StatusReLayout,mWhether_to_drinkReLayout,
            mConstellationReLayout,mNationReLayout,mWhen_to_get_marriedReLayout,mGenderReLayout,mBirthdayReLayout,mHieghtReLayout,mEducationReLayout,mMarital_statusReLayout;
    //todo
    TextView mMonthlyIncome_TV,mWorkingArea_TV,mHave_no_children_TV,mWhether_you_want_a_child_TV,mHousing_situation_TV,mBuying_a_car_TV,mOccupation_TV,mBirthday_TV,
            mPlace_of_origin_TV,mWeight_TV,mShape_TV,mSmoking_Status_TV,mWhether_to_drink_TV,mConstellation_TV,mNation_TV,mWhen_to_get_married_TV,mGender_TV,mHeight_TV,mEducation_TV,
            mMaritalStatus_TV,mSave_tv;
    RecyclerView mHeadPortrait_RV,mAlbum_RV;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_edit_personal_data_layout);
        initView();
        initBrocast();
        getData();
    }
    //查询个人数据并显示
    private void getData() {
        BmobQuery<User> query = new BmobQuery<>();
        query.getObject(BmobUser.getCurrentUser(User.class).getObjectId(), new QueryListener<User>() {
            @Override
            public void done(User object, BmobException e) {
                if(e==null){
                    mMonthlyIncome_TV.setText(object.getIncome());
                    mWorkingArea_TV.setText(object.getWorking_area());
                    mHave_no_children_TV.setText(object.getThere_is_no_child());
                    mWhether_you_want_a_child_TV.setText(object.getDo_you_want_a_baby());
                    mHousing_situation_TV.setText(object.getHousing_situation());
                    mBuying_a_car_TV.setText(object.getBuying_a_car());
                    mOccupation_TV.setText(object.getOccupation());
                    mBirthday_TV.setText(object.getBirthday());
                    mPlace_of_origin_TV.setText(object.getPlace_of_origin());
                    mWeight_TV.setText(object.getWeight());
                    mShape_TV.setText(object.getShape());
                    mSmoking_Status_TV.setText(object.getSmoking_status());
                    mWhether_to_drink_TV.setText(object.getWhether_to_drink());
                    mConstellation_TV.setText(object.getConstellation());
                    mNation_TV.setText(object.getNation());
                    mWhen_to_get_married_TV.setText(object.getWhen_to_get_married());
                    mGender_TV.setText(object.getGender());
                    mHeight_TV.setText(object.getHeight());
                    mEducation_TV.setText(object.getEducation());
                    mMaritalStatus_TV.setText(object.getMarital_status());

                }else{
                    MyLog.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }

        });
    }

    private void initBrocast() {
        intentfilter = new IntentFilter();
        intentfilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");// 想监听什么广播在这修改值即可。
        networkchangereceier = new NetworkChangeReceier();
        registerReceiver(networkchangereceier, intentfilter);
    }

    private void initView() {
        mListener_network_layout = (LinearLayout) findViewById(R.id.mListener_network_layout);
        mSave_tv = (TextView) findViewById(R.id.mSave_tv);
        mMaritalStatus_TV = (TextView) findViewById(R.id.mMarital_status_TV);
        mMarital_statusReLayout = (RelativeLayout) findViewById(R.id.mMarital_statusReLayout);
        mEducation_TV = (TextView) findViewById(R.id.mEducation_TV);
        mEducationReLayout = (RelativeLayout) findViewById(R.id.mEducationReLayout);
        mHeight_TV = (TextView) findViewById(R.id.mHeight_TV);
        mHieghtReLayout = (RelativeLayout) findViewById(R.id.mHieghtReLayout);
        mBirthday_TV = (TextView) findViewById(R.id.mBirthday_TV);
        mBirthdayReLayout = (RelativeLayout) findViewById(R.id.mBirthdayReLayout);
        mGender_TV = (TextView) findViewById(R.id.mGender_TV);
        mGenderReLayout = (RelativeLayout) findViewById(R.id.mGenderReLayout);
        mWhen_to_get_married_TV = (TextView) findViewById(R.id.mWhen_to_get_married_TV);
        mWhen_to_get_marriedReLayout = (RelativeLayout) findViewById(R.id.mWhen_to_get_marriedReLayout);
        mNation_TV = (TextView) findViewById(R.id.mNation_TV);
        mNationReLayout = (RelativeLayout) findViewById(R.id.mNationReLayout);
        mConstellation_TV = (TextView) findViewById(R.id.mConstellation_TV);
        mConstellationReLayout = (RelativeLayout) findViewById(R.id.mConstellationReLayout);
        mWhether_to_drink_TV = (TextView) findViewById(R.id.mWhether_to_drink_TV);
        mWhether_to_drinkReLayout = (RelativeLayout) findViewById(R.id.mWhether_to_drinkReLayout);
        mSmoking_Status_TV = (TextView) findViewById(R.id.mSmoking_Status_TV);
        mSmoking_StatusReLayout = (RelativeLayout) findViewById(R.id.mSmoking_StatusReLayout);
        mShape_TV = (TextView) findViewById(R.id.mShape_TV);
        mShapeReLayout = (RelativeLayout) findViewById(R.id.mShapeReLayout);
        mWeight_TV = (TextView) findViewById(R.id.mWeight_TV);
        mWeightReLayout = (RelativeLayout) findViewById(R.id.mWeightReLayout);
        mPlace_of_origin_TV = (TextView) findViewById(R.id.mPlace_of_origin_TV);
        mPlace_of_originReLayout = (RelativeLayout) findViewById(R.id.mPlace_of_originReLayout);
        mOccupation_TV = (TextView) findViewById(R.id.mOccupation_TV);
        mOccupationReLayout = (RelativeLayout) findViewById(R.id.mOccupationReLayout);
        mBuying_a_car_TV = (TextView) findViewById(R.id.mBuying_a_car_TV);
        mBuying_a_carReLayout = (RelativeLayout) findViewById(R.id.mBuying_a_carReLayout);
        mHousing_situation_TV = (TextView) findViewById(R.id.mHousing_situation_TV);
        mHousing_situationReLayout = (RelativeLayout) findViewById(R.id.mHousing_situationReLayout);
        mHave_no_childrenReLayout = (RelativeLayout) findViewById(R.id.mHave_no_childrenReLayout);
        mHave_no_children_TV = (TextView) findViewById(R.id.mHave_no_children_TV);
        mWhether_you_want_a_child_TV = (TextView) findViewById(R.id.mWhether_you_want_a_child_TV);
        mWhether_you_want_a_childReLayout = (RelativeLayout) findViewById(R.id.mWhether_you_want_a_childReLayout);
        mWorkingArea_TV = (TextView) findViewById(R.id.mWorkingArea_TV);
        mWorkingAreaReLayout = (RelativeLayout) findViewById(R.id.mWorkingAreaReLayout);
        mMonthlyIncome_TV = (TextView) findViewById(R.id.mMonthlyIncome_TV);
        mMonthlyIncome_ReLayout = (RelativeLayout) findViewById(R.id.mMonthlyIncome_ReLayout);
        mAlbum_RV = (RecyclerView) findViewById(R.id.mAlbum_RV);
        mHeadPortrait_RV = (RecyclerView) findViewById(R.id.mHeadPortrait_RV);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(linearLayoutManager.HORIZONTAL);
        mAlbum_RV.setLayoutManager(linearLayoutManager);
        LinearLayoutManager linearLayoutManager_2 = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(linearLayoutManager.HORIZONTAL);
        mHeadPortrait_RV.setLayoutManager(linearLayoutManager_2);
        mHeadPortrait_RV.setAdapter(new HeadPortraitReAdapter(this));
        mAlbum_RV.setAdapter(new AlbumRVAdapter(this));
        mMonthlyIncome_ReLayout.setOnClickListener(this);
        mWorkingAreaReLayout.setOnClickListener(this);
        mWhether_you_want_a_childReLayout.setOnClickListener(this);
        mHave_no_childrenReLayout.setOnClickListener(this);
        mHousing_situationReLayout.setOnClickListener(this);
        mBuying_a_carReLayout.setOnClickListener(this);
        mOccupationReLayout.setOnClickListener(this);
        mPlace_of_originReLayout.setOnClickListener(this);
        mWeightReLayout.setOnClickListener(this);
        mShapeReLayout.setOnClickListener(this);
        mSmoking_StatusReLayout.setOnClickListener(this);
        mWhether_to_drinkReLayout.setOnClickListener(this);
        mConstellationReLayout.setOnClickListener(this);
        mNationReLayout.setOnClickListener(this);
        mWhen_to_get_marriedReLayout.setOnClickListener(this);
        mGenderReLayout.setOnClickListener(this);
        mBirthdayReLayout.setOnClickListener(this);
        mHieghtReLayout.setOnClickListener(this);
        mEducationReLayout.setOnClickListener(this);
        mMarital_statusReLayout.setOnClickListener(this);
        mSave_tv.setOnClickListener(this);
        mListener_network_layout.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mMonthlyIncome_ReLayout:
                Intent monthlyIncomeIntent = new Intent(this,DataSelectActivityTwo.class);
                monthlyIncomeIntent.putExtra(ConditionalSelectionActivity.TITLE,"月收入");
                monthlyIncomeIntent.putStringArrayListExtra(ConditionalSelectionActivity.LIST,(ArrayList<String>) initMonthlyIncomeWheelviewData());
                startActivityForResult(monthlyIncomeIntent,ConditionalSelectionActivity.MONTHLY_INCOME);
                break;
            case R.id.mWorkingAreaReLayout:
                Bundle bundle = new Bundle();
                MyMap myMap = new MyMap();
                myMap.setCityMap(cityMap);
                myMap.setAreaMap(areaMap);
                bundle.putSerializable("map",myMap);
                initJsonData();
                initWorkingAreaData();
                Intent workingAreaIntent = new Intent(this,DataSelectActivityThree.class);
                workingAreaIntent.putExtra(ConditionalSelectionActivity.TITLE,"工作地区");
                workingAreaIntent.putExtra("mListHeadValue","请选择");
                workingAreaIntent.putExtra(ConditionalSelectionActivity.PROVINCE,allProv);
                workingAreaIntent.putExtras(bundle);
                startActivityForResult(workingAreaIntent,ConditionalSelectionActivity.WORKINGAREA);
                break;
            case R.id.mHave_no_childrenReLayout:
                Intent have_no_childrenIntent = new Intent(this,DataSelectActivityOne.class);
                have_no_childrenIntent.putExtra(ConditionalSelectionActivity.TITLE,"有没有孩子");
                have_no_childrenIntent.putStringArrayListExtra(ConditionalSelectionActivity.LIST,(ArrayList<String>) initHave_no_childrenWheelviewData());
                startActivityForResult(have_no_childrenIntent,ConditionalSelectionActivity.HAVE_NO_CHILDREN);
                break;
            case R.id.mWhether_you_want_a_childReLayout:
                Intent whether_you_want_a_childIntent = new Intent(this,DataSelectActivityOne.class);
                whether_you_want_a_childIntent.putExtra(ConditionalSelectionActivity.TITLE,"是否想要孩子");
                whether_you_want_a_childIntent.putStringArrayListExtra(ConditionalSelectionActivity.LIST,(ArrayList<String>) initWhether_you_want_a_childWheelviewData());
                startActivityForResult(whether_you_want_a_childIntent,ConditionalSelectionActivity.WHETHER_YOU_WANT_A_CHILD);
                break;
            case R.id.mHousing_situationReLayout:
                Intent housing_situationIntent = new Intent(this,DataSelectActivityOne.class);
                housing_situationIntent.putExtra(ConditionalSelectionActivity.TITLE,"住房情况");
                housing_situationIntent.putStringArrayListExtra(ConditionalSelectionActivity.LIST,(ArrayList<String>) initHousing_situationWheelviewData());
                startActivityForResult(housing_situationIntent,ConditionalSelectionActivity.HOUSING_SITUATION);
                break;
            case R.id.mBuying_a_carReLayout:
                Intent buying_a_carIntent = new Intent(this,DataSelectActivityOne.class);
                buying_a_carIntent.putExtra(ConditionalSelectionActivity.TITLE,"买车情况");
                buying_a_carIntent.putStringArrayListExtra(ConditionalSelectionActivity.LIST,(ArrayList<String>) initBuying_a_carWheelviewData());
                startActivityForResult(buying_a_carIntent,ConditionalSelectionActivity.BUYING_A_CAR);
                break;
            case R.id.mOccupationReLayout:
                Intent occupationIntent = new Intent(this,PersonalInputPopupActivity.class);
                occupationIntent.putExtra(ConditionalSelectionActivity.TITLE,"职业");
                startActivityForResult(occupationIntent,ConditionalSelectionActivity.OCCUPATION);
                break;
            case R.id.mPlace_of_originReLayout:
                Bundle p_bundle = new Bundle();
                MyMap p_myMap = new MyMap();
                p_myMap.setCityMap(cityMap);
                p_myMap.setAreaMap(areaMap);
                p_bundle.putSerializable("map",p_myMap);
                initJsonData();
                initWorkingAreaData();
                Intent p_workingAreaIntent = new Intent(this,DataSelectActivityThree.class);
                p_workingAreaIntent.putExtra(ConditionalSelectionActivity.TITLE,"籍贯");
                p_workingAreaIntent.putExtra("mListHeadValue","请选择");
                p_workingAreaIntent.putExtra(ConditionalSelectionActivity.PROVINCE,allProv);
                p_workingAreaIntent.putExtras(p_bundle);
                startActivityForResult(p_workingAreaIntent,ConditionalSelectionActivity.PLACE_OF_ORIGIN);
                break;
            case R.id.mWeightReLayout:
                Intent weightIntent = new Intent(this,DataSelectActivityOne.class);
                weightIntent.putExtra(ConditionalSelectionActivity.TITLE,"体重");
                weightIntent.putStringArrayListExtra(ConditionalSelectionActivity.LIST,(ArrayList<String>) initWeightWheelviewData());
                startActivityForResult(weightIntent,ConditionalSelectionActivity.WEIGHT);
                break;
            case R.id.mShapeReLayout:
                Intent shapeIntent = new Intent(this,DataSelectActivityOne.class);
                shapeIntent.putExtra(ConditionalSelectionActivity.TITLE,"体型");
                shapeIntent.putStringArrayListExtra(ConditionalSelectionActivity.LIST,(ArrayList<String>) initShapeWheelviewData());
                startActivityForResult(shapeIntent,ConditionalSelectionActivity.SHAPE);
                break;
            //是否抽烟
            case R.id.mSmoking_StatusReLayout:
                Intent smokingStatusIntent = new Intent(this,DataSelectActivityOne.class);
                smokingStatusIntent.putExtra(ConditionalSelectionActivity.TITLE,"是否抽烟");
                smokingStatusIntent.putStringArrayListExtra(ConditionalSelectionActivity.LIST,(ArrayList<String>) initSmokingStatusWheelviewData());
                startActivityForResult(smokingStatusIntent,ConditionalSelectionActivity.SMOKING_STATUS);
                break;
            case R.id.mWhether_to_drinkReLayout:
                Intent whether_to_drinkIntent = new Intent(this,DataSelectActivityOne.class);
                whether_to_drinkIntent.putExtra(ConditionalSelectionActivity.TITLE,"是否喝酒");
                whether_to_drinkIntent.putStringArrayListExtra(ConditionalSelectionActivity.LIST,(ArrayList<String>) initWhether_to_drinkWheelviewData());
                startActivityForResult(whether_to_drinkIntent,ConditionalSelectionActivity.WHETHER_TO_DRINK);
                break;
            case R.id.mConstellationReLayout:
                Intent constellationIntent = new Intent(this,DataSelectActivityOne.class);
                constellationIntent.putExtra(ConditionalSelectionActivity.TITLE,"星座");
                constellationIntent.putStringArrayListExtra(ConditionalSelectionActivity.LIST,(ArrayList<String>) initConstellationWheelviewData());
                startActivityForResult(constellationIntent,ConditionalSelectionActivity.CONSTELLATION);
                break;
            case R.id.mNationReLayout:
                Intent nationIntent = new Intent(this,DataSelectActivityOne.class);
                nationIntent.putExtra(ConditionalSelectionActivity.TITLE,"民族");
                nationIntent.putStringArrayListExtra(ConditionalSelectionActivity.LIST,(ArrayList<String>) initNationWheelviewData());
                startActivityForResult(nationIntent,ConditionalSelectionActivity.NATION);
                break;
            case R.id.mWhen_to_get_marriedReLayout:
                Intent when_to_get_marriedIntent = new Intent(this,DataSelectActivityOne.class);
                when_to_get_marriedIntent.putExtra(ConditionalSelectionActivity.TITLE,"何时结婚");
                when_to_get_marriedIntent.putStringArrayListExtra(ConditionalSelectionActivity.LIST,(ArrayList<String>) initWhen_to_get_marriedWheelviewData());
                startActivityForResult(when_to_get_marriedIntent,ConditionalSelectionActivity.WHEN_TO_GET_MARRIED);
                break;
            case R.id.mGenderReLayout:
                Intent genderIntent = new Intent(this,DataSelectActivityOne.class);
                genderIntent.putExtra(ConditionalSelectionActivity.TITLE,"性别");
                genderIntent.putStringArrayListExtra(ConditionalSelectionActivity.LIST,(ArrayList<String>) initGenderWheelviewData());
                startActivityForResult(genderIntent,ConditionalSelectionActivity.GENGER);
                break;
            case R.id.mBirthdayReLayout:
                Intent birthdayIntent = new Intent(this,BirthdayDataSelectActivity.class);
                birthdayIntent.putExtra(ConditionalSelectionActivity.TITLE,"生日");
                birthdayIntent.putStringArrayListExtra(BirthdayDataSelectActivity.YEAR_LIST,(ArrayList<String>) initYearWheelviewData());
                birthdayIntent.putStringArrayListExtra(BirthdayDataSelectActivity.MONTH_LIST,(ArrayList<String>) initMonthWheelviewData());
                birthdayIntent.putStringArrayListExtra(BirthdayDataSelectActivity.DAY_LIST,(ArrayList<String>) initDayWheelviewData());
                startActivityForResult(birthdayIntent,ConditionalSelectionActivity.BIRTHDAY);
                break;
            case R.id.mHieghtReLayout:
                Intent heightIntent = new Intent(this,DataSelectActivityTwo.class);
                heightIntent.putExtra(ConditionalSelectionActivity.TITLE,"身高");
                heightIntent.putStringArrayListExtra(ConditionalSelectionActivity.LIST,(ArrayList<String>) initHeightWheelviewData());
                startActivityForResult(heightIntent,ConditionalSelectionActivity.HEIGHT);
                break;
            case R.id.mEducationReLayout:
                Intent educationIntent = new Intent(this,DataSelectActivityOne.class);
                educationIntent.putExtra(ConditionalSelectionActivity.TITLE,"学历");
                educationIntent.putStringArrayListExtra(ConditionalSelectionActivity.LIST,(ArrayList<String>) initEducationWheelviewData());
                startActivityForResult(educationIntent,ConditionalSelectionActivity.EDUCATION);
                break;
            case R.id.mMarital_statusReLayout:
                Intent maritalStatusIntent = new Intent(this,DataSelectActivityOne.class);
                maritalStatusIntent.putExtra(ConditionalSelectionActivity.TITLE,"婚姻状况");
                maritalStatusIntent.putStringArrayListExtra(ConditionalSelectionActivity.LIST,(ArrayList<String>) initMaritalStatusWheelviewData());
                startActivityForResult(maritalStatusIntent,ConditionalSelectionActivity.MARITAL_STATUS);
                break;
            //todo SaveButton
            //保存用户资料到后台
            case R.id.mSave_tv:
                    final User data = BmobUser.getCurrentUser(User.class);
                    data.setIncome(mMonthlyIncome_TV.getText().toString());
                    data.setWorking_area(mWorkingArea_TV.getText().toString());
                    data.setThere_is_no_child(mHave_no_children_TV.getText().toString());
                    data.setDo_you_want_a_baby(mWhether_you_want_a_child_TV.getText().toString());
                    data.setHousing_situation(mHousing_situation_TV.getText().toString());
                    data.setBuying_a_car(mBuying_a_car_TV.getText().toString());
                    data.setOccupation(mOccupation_TV.getText().toString());
                    data.setPlace_of_origin(mPlace_of_origin_TV.getText().toString());
                    if(!mPlace_of_origin_TV.getText().toString().equals("未知")){
                        data.setProvince(mPlace_of_origin_TV.getText().toString().split("-")[0]);
                    }
                    data.setWeight(mWeight_TV.getText().toString());
                    data.setShape(mShape_TV.getText().toString());
                    data.setSmoking_status(mSmoking_Status_TV.getText().toString());
                    data.setWhether_to_drink(mWhether_to_drink_TV.getText().toString());
                    data.setConstellation(mConstellation_TV.getText().toString());
                    data.setNation(mNation_TV.getText().toString());
                    data.setWhen_to_get_married(mWhen_to_get_married_TV.getText().toString());
                    data.setGender(mGender_TV.getText().toString());
                    data.setBirthday(mBirthday_TV.getText().toString());
                if(mBirthday_TV.getText().toString().equals("未知")!=true){
                        //计算年龄然后上传
                    String str = mBirthday_TV.getText().toString();
                    str = str.substring(0, str.indexOf("."));
                    int value = 2016 - Integer.valueOf(str);
                    data.setAge(value + "");
                    }
                    data.setHeight(mHeight_TV.getText().toString());
                    data.setEducation(mEducation_TV.getText().toString());
                    data.setMarital_status(mMaritalStatus_TV.getText().toString());
                    data.update(data.getObjectId(),new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                       if(e == null){
                           Toast.makeText(EditPersonalDataActivity.this, "保存成功!", Toast.LENGTH_SHORT).show();
                           Intent intent = new Intent();
                           Bundle bundle1 = new Bundle();
                           bundle1.putSerializable("user",data);
                           intent.putExtras(bundle1);
                           setResult(EditPersonalDataActivity.RESULT_OK,intent);
                           finish();
                       }else{
                           Toast.makeText(EditPersonalDataActivity.this, "保存失败!", Toast.LENGTH_SHORT).show();
                           MyLog.i("kkk","e = "+e);
                       }
                        }
                    });
            break;
            case R.id.mListener_network_layout:
                Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                startActivity(intent);
                break;
            default:break;
        }
    }

    private List<String> initYearWheelviewData() {
        List<String> list = new ArrayList<>();
        list.add("请选择");
        for(int a = 1916;a<2016;a++) {
            list.add(a+"");
        }
        return list;
    }
    private List<String> initMonthWheelviewData() {
        List<String> list = new ArrayList<>();
        list.add("请选择");
        for(int a = 1;a<13;a++) {
            list.add(a+"");
        }
        return list;
    }
    private List<String> initDayWheelviewData() {
        List<String> list = new ArrayList<>();
        list.add("请选择");
        for(int a = 1;a<32;a++) {
            list.add(a+"");
        }
        return list;
    }

    private List<String> initGenderWheelviewData() {
        List<String> list = new ArrayList<>();
        list.add("请选择");
        list.add("男");
        list.add("女");
        return list;
    }

    private List<String> initWhen_to_get_marriedWheelviewData() {
        List<String> list = new ArrayList<>();
        list.add("请选择");
        list.add("认同闪婚");
        list.add("一年内");
        list.add("两年内");
        list.add("三年内");
        list.add("时机成熟就结婚");
        return list;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case ConditionalSelectionActivity.MONTHLY_INCOME:
                if(resultCode == RESULT_OK) {
                    String leftVlaue = data.getStringExtra(ConditionalSelectionActivity.FROM);
                    String rightValue = data.getStringExtra(ConditionalSelectionActivity.TO);
                    if (leftVlaue.equals("不限") && rightValue.equals("不限")) {
                        mMonthlyIncome_TV.setText("不限");
                    } else {
                        mMonthlyIncome_TV.setText(leftVlaue + " - " + rightValue + " 元");
                    }
                }
                    break;
            case ConditionalSelectionActivity.WORKINGAREA:
                if(resultCode == RESULT_OK) {
                    String provinceStr = data.getStringExtra(ConditionalSelectionActivity.PROVINCE);
                    String cityStr = data.getStringExtra(ConditionalSelectionActivity.CITY);
                    String areaStr = data.getStringExtra(ConditionalSelectionActivity.AREA);
                    //等于空或者等于不限都赋值为空
                    if(provinceStr ==null||provinceStr.equals("请选择")){
                        provinceStr = "";
                    }
                    if(cityStr == null||cityStr.equals("请选择")){
                        cityStr = "";
                    }
                    if(areaStr == null||areaStr.equals("请选择")){
                        areaStr = "";
                    }
                    if(!provinceStr.equals("")&&!cityStr.equals("")&&!areaStr.equals("")) {
                        mWorkingArea_TV.setText(provinceStr + "-" + cityStr + "-" + areaStr);
                    }else if(!provinceStr.equals("")&&!cityStr.equals("")){
                        mWorkingArea_TV.setText(provinceStr + "-" + cityStr);
                    }else if(!provinceStr.equals("")){
                        mWorkingArea_TV.setText(provinceStr);
                    }else{
                        mWorkingArea_TV.setText("请选择");
                    }
                }
                break;
            case ConditionalSelectionActivity.HAVE_NO_CHILDREN:
                if(resultCode == RESULT_OK) {
                    String value = data.getStringExtra(ConditionalSelectionActivity.VALUE);
                    mHave_no_children_TV.setText(value);
                }
                break;
            case ConditionalSelectionActivity.WHETHER_YOU_WANT_A_CHILD:
                if(resultCode == RESULT_OK) {
                    String value = data.getStringExtra(ConditionalSelectionActivity.VALUE);
                    mWhether_you_want_a_child_TV.setText(value);
                }
                break;
            case ConditionalSelectionActivity.HOUSING_SITUATION:
                if(resultCode == RESULT_OK) {
                    String value = data.getStringExtra(ConditionalSelectionActivity.VALUE);
                    mHousing_situation_TV.setText(value);
                }
                break;
            case ConditionalSelectionActivity.BUYING_A_CAR:
                if(resultCode == RESULT_OK) {
                    String value = data.getStringExtra(ConditionalSelectionActivity.VALUE);
                    mBuying_a_car_TV.setText(value);
                }
                break;
            case ConditionalSelectionActivity.OCCUPATION:
                if(resultCode == RESULT_OK) {
                    String value = data.getStringExtra(ConditionalSelectionActivity.VALUE);
                    mOccupation_TV.setText(value);
                }
                break;
            //籍贯
            case ConditionalSelectionActivity.PLACE_OF_ORIGIN:
                if(resultCode == RESULT_OK) {
                    String provinceStr = data.getStringExtra(ConditionalSelectionActivity.PROVINCE);
                    String cityStr = data.getStringExtra(ConditionalSelectionActivity.CITY);
                    String areaStr = data.getStringExtra(ConditionalSelectionActivity.AREA);
                    //等于空或者等于不限都赋值为空
                    if(provinceStr ==null||provinceStr.equals("请选择")){
                        provinceStr = "";
                    }
                    if(cityStr == null||cityStr.equals("请选择")){
                        cityStr = "";
                    }
                    if(areaStr == null||areaStr.equals("请选择")){
                        areaStr = "";
                    }
                    if(!provinceStr.equals("")&&!cityStr.equals("")&&!areaStr.equals("")) {
                        mPlace_of_origin_TV.setText(provinceStr + "-" + cityStr + "-" + areaStr);
                    }else if(!provinceStr.equals("")&&!cityStr.equals("")){
                        mPlace_of_origin_TV.setText(provinceStr + "-" + cityStr);
                    }else if(!provinceStr.equals("")){
                        mPlace_of_origin_TV.setText(provinceStr);
                    }else{
                        mPlace_of_origin_TV.setText("请选择");
                    }
                }
                break;
            case ConditionalSelectionActivity.WEIGHT:
                if(resultCode == RESULT_OK) {
                    String value = data.getStringExtra(ConditionalSelectionActivity.VALUE);
                    mWeight_TV.setText(value);
                }
                break;
            case ConditionalSelectionActivity.SHAPE:
                if(resultCode == RESULT_OK) {
                    String value = data.getStringExtra(ConditionalSelectionActivity.VALUE);
                    mShape_TV.setText(value);
                }
                break;
            case ConditionalSelectionActivity.SMOKING_STATUS:
                if(resultCode == RESULT_OK) {
                    String value = data.getStringExtra(ConditionalSelectionActivity.VALUE);
                    mSmoking_Status_TV.setText(value);
                }
                break;
            case ConditionalSelectionActivity.WHETHER_TO_DRINK:
                if(resultCode == RESULT_OK) {
                    String value = data.getStringExtra(ConditionalSelectionActivity.VALUE);
                    mWhether_to_drink_TV.setText(value);
                }
                break;
            case ConditionalSelectionActivity.CONSTELLATION:
                if(resultCode == RESULT_OK) {
                    String value = data.getStringExtra(ConditionalSelectionActivity.VALUE);
                    mConstellation_TV.setText(value);
                }
                break;
            case ConditionalSelectionActivity.NATION:
                if(resultCode == RESULT_OK) {
                    String value = data.getStringExtra(ConditionalSelectionActivity.VALUE);
                    mNation_TV.setText(value);
                }
                break;
            case ConditionalSelectionActivity.WHEN_TO_GET_MARRIED:
                if(resultCode == RESULT_OK) {
                    String value = data.getStringExtra(ConditionalSelectionActivity.VALUE);
                    mWhen_to_get_married_TV.setText(value);
                }
                break;
            case ConditionalSelectionActivity.GENGER:
                if(resultCode == RESULT_OK) {
                    String value = data.getStringExtra(ConditionalSelectionActivity.VALUE);
                    mGender_TV.setText(value);
                }
                break;
            case ConditionalSelectionActivity.BIRTHDAY:
                if(resultCode == RESULT_OK) {
                    String yearStr = data.getStringExtra(BirthdayDataSelectActivity.YEAR);
                    String monthStr = data.getStringExtra(BirthdayDataSelectActivity.MONTH);
                    String dayStr = data.getStringExtra(BirthdayDataSelectActivity.DAY);
                    mBirthday_TV.setText(yearStr+monthStr+dayStr);
                }
                break;
            case ConditionalSelectionActivity.HEIGHT:
                if(resultCode == RESULT_OK) {
                    String leftVlaue = data.getStringExtra(ConditionalSelectionActivity.FROM);
                    String rightValue = data.getStringExtra(ConditionalSelectionActivity.TO);
                    if (leftVlaue.equals("不限") && rightValue.equals("不限")) {
                        mHeight_TV.setText("不限");
                    } else {
                        mHeight_TV.setText(leftVlaue + " - " + rightValue + " 厘米");
                    }
                }
                break;
            case ConditionalSelectionActivity.EDUCATION:
                if(resultCode == RESULT_OK) {
                    String value = data.getStringExtra(ConditionalSelectionActivity.VALUE);
                    mEducation_TV.setText(value);
                }
                break;
            case ConditionalSelectionActivity.MARITAL_STATUS:
                if(resultCode == RESULT_OK) {
                    String value = data.getStringExtra(ConditionalSelectionActivity.VALUE);
                    mMaritalStatus_TV.setText(value);
                }
                break;
            default:break;
        }
    }

    public List<String> initMonthlyIncomeWheelviewData() {
        List list = new ArrayList<>();
        list.add("请选择");
        for(int a = 1000;a<50000;a+=2000){
            list.add(""+a);
        }
        return list;
    }
    private void initJsonData() {
        try {
            StringBuffer sb = new StringBuffer();
            InputStream is = getAssets().open("city.json");//打开json数据
            byte[] by = new byte[is.available()];//转字节
            int len = -1;
            while ((len = is.read(by)) != -1) {
                sb.append(new String(by, 0, len, "gb2312"));//根据字节长度设置编码
            }
            is.close();//关闭流
            jsonObject = new JSONObject(sb.toString());//为json赋值
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void initWorkingAreaData() {
        //初始化省市区数据
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("citylist");//获取整个json数据
            //所有省的长度 = jsonArray.length();
            allProv = new String[jsonArray.length()];//封装数据
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonP = jsonArray.getJSONObject(i);//jsonArray转jsonObject
                //循环一次获取一个省
                String provStr = jsonP.getString("p");//获取所有的省
                allProv[i] = provStr;
                JSONArray jsonCity = null;

                try {
                    jsonCity = jsonP.getJSONArray("c");//在所有的省中取出所有的市，转jsonArray
                } catch (Exception e) {
                    continue;
                }
                //一个省中的所有市
                String[] allCity = new String[jsonCity.length()];//所有市的长度
                for (int c = 0; c < jsonCity.length(); c++) {
                    JSONObject jsonCy = jsonCity.getJSONObject(c);//转jsonObject
                    String cityStr = jsonCy.getString("n");//取出所有的市
                    allCity[c] = cityStr;//封装市集合
                    JSONArray jsonArea = null;
                    try {
                        jsonArea = jsonCy.getJSONArray("a");//在从所有的市里面取出所有的区,转jsonArray
                    } catch (Exception e) {
                        continue;
                    }
                    //一个市中的所有区
                    String[] allArea = new String[jsonArea.length()];//所有的区
                    for (int a = 0; a < jsonArea.length(); a++) {
                        JSONObject jsonAa = jsonArea.getJSONObject(a);
                        String areaStr = jsonAa.getString("s");//获取所有的区
                        allArea[a] = areaStr;//封装起来
                    }
                    areaMap.put(cityStr, allArea);//某个市取出所有的区集合
                }
                cityMap.put(provStr, allCity);//某个省取出所有的市,
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonObject = null;//清空所有的数据
    }
    private List<String> initHave_no_childrenWheelviewData() {
        List list = new ArrayList<>();
        list.add("请选择");
        list.add("没有");
        list.add("有，我们住在一起");
        list.add("有，我们偶尔一起住");
        list.add("有，但不在身边");
        return list;
    }
    private  List<String> initWhether_you_want_a_childWheelviewData() {
        List list = new ArrayList<>();
        list.add("请选择");
        list.add("想要孩子");
        list.add("不想要孩子");
        list.add("看情况而定");
        return list;
    }
    private List<String> initShapeWheelviewData() {
        List<String> list = new ArrayList<>();
        list.add("请选择");
        list.add("一般");
        list.add("瘦长");
        list.add("运动员型");
        list.add("比较胖");
        list.add("体格魁梧");
        list.add("苗条");
        list.add("高大美丽");
        list.add("丰满");
        list.add("富线条美");
        list.add("壮实");
        return list;
    }

    private List<String> initSmokingStatusWheelviewData() {
        List<String> list = new ArrayList<>();
        list.add("请选择");
        list.add("是");
        list.add("否");
        return list;
    }
    private List<String> initWeightWheelviewData() {
        List<String> list = new ArrayList<>();
        list.add("请选择");
        for(int a = 35;a<100;a++) {
            list.add(a+"");
        }
        return list;
    }

    private List<String> initBuying_a_carWheelviewData() {
        List<String> list = new ArrayList<>();
        list.add("请选择");
        list.add("已购车");
        list.add("未购车");
        return list;
    }

    private List<String> initHousing_situationWheelviewData() {
        List<String> list = new ArrayList<>();
        list.add("请选择");
        list.add("和家人同住");
        list.add("已购房");
        list.add("租房");
        list.add("打算婚后购房");
        list.add("单位宿舍");
        return list;
    }
    private List<String> initWhether_to_drinkWheelviewData() {
        List<String> list = new ArrayList<>();
        list.add("请选择");
        list.add("不喝");
        list.add("喝");
        return list;
    }
    private List<String> initConstellationWheelviewData() {
        List<String> list = new ArrayList<>();
        list.add("请选择");
        list.add("白羊座(3.21-4.19)");
        list.add("金牛座(4.20-5.20)");
        list.add("双子座(5.21-6.21)");
        list.add("巨蟹座(6.22-7.22)");
        list.add("狮子座(7.23-8.22)");
        list.add("处女座(8.23-9.22)");
        list.add("天秤座(9.23-10.23)");
        list.add("天蝎座(10.24-11.22)");
        list.add("射手座(11.23-12.21)");
        list.add("魔蝎座(12.21-1.19)");
        list.add("水瓶座(1.20-2.18)");
        list.add("双鱼座(2.19-3.20)");
        return list;
    }
    private List<String> initNationWheelviewData() {
        List<String> list = new ArrayList<>();
        String str = "汉族、满族、朝鲜族、蒙古族、达斡尔族、鄂温克族、鄂伦春族、赫哲族、回族、维吾尔族、哈萨克族、东乡族、土族、锡伯族、柯尔克孜族、撒拉族、"
                +"塔吉克族、乌孜别克族、俄罗斯族、裕固族、保安族、塔塔尔族、苗族、彝族、藏族、布依族、侗族、白族、哈尼族、傣族、僳僳族、仡佬族、" +
                "拉祜族、佤族、水族、纳西族、羌族、景颇族、布朗族、普米族、阿昌族、怒族、基诺族、德昂族、独龙族、珞巴族、壮族、土家族、瑶族、黎族、畲族、高山族、仫佬族、毛南族、京族";
        String[] strArray = str.split("、");
        for(int a = 0;a<strArray.length;a++) {
            list.add(strArray[a]);
        }
        return list;
    }
    private List<String> initHeightWheelviewData() {
        List list = new ArrayList<>();
        list.add("请选择");
        for(int a = 110;a<212;a++){
            list.add(""+a);
        }
        return list;
    }
    private List<String> initEducationWheelviewData() {
        List list = new ArrayList<>();
        list.add("请选择");
        list.add("中专");
        list.add("高中及以下");
        list.add("大专");
        list.add("大学本科");
        list.add("硕士");
        list.add("博士");
        return list;
    }
    private List<String> initMaritalStatusWheelviewData() {
        List<String> list = new ArrayList<>();
        list.add("请选择");
        list.add("未婚");
        list.add("已婚");
        list.add("离异");
        list.add("丧偶");
        return list;
    }

    public void setPersonalDataFragment(PersonalDataFragment personalDataFragment) {
        this.personalDataFragment = personalDataFragment;
    }

    class NetworkChangeReceier extends BroadcastReceiver {// BroadcastReceiver：广播接收机。
        @Override
        public void onReceive(Context context, Intent intent) {// 网络发生变化时就会调用这个方法。
            ConnectivityManager connectivitymanager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);// ConnectivityManager:连接管理器。
            NetworkInfo networkinfo = connectivitymanager
                    .getActiveNetworkInfo(); // NetworkInfo：网络状态。
            if (networkinfo != null && networkinfo.isAvailable()) {// isAvailable：是可用的，若连接则返回true。
                mListener_network_layout.setVisibility(View.GONE);
                mSave_tv.setEnabled(true);
            } else {
                mListener_network_layout.setVisibility(View.VISIBLE);
                mSave_tv.setEnabled(false);
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkchangereceier);
    }
}
