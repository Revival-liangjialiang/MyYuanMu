package com.example.yuanmu.lunbo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuanmu.lunbo.BmobBean.Condition;
import com.example.yuanmu.lunbo.BmobBean.User;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.MyMap;
import com.example.yuanmu.lunbo.Util.StatusBarColorUtil;

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
 * Created by Administrator on 2016/9/13 0013.
 */
public class ConditionalSelectionActivity extends AppCompatActivity implements View.OnClickListener{
    User user;
    private Map<String, String[]> cityMap = new HashMap<String, String[]>();//key:省p---value:市n  value是一个集合
    private Map<String, String[]> areaMap = new HashMap<String, String[]>();//key:市n---value:区s    区也是一个集合
    //全部省
    private String[] allProv;
    private JSONObject jsonObject;
    //启动活动值常量
    public static final int AGE = 0,HEIGHT = 1,MONTHLY_INCOME = 2,EDUCATION = 3,MARITAL_STATUS = 4,SHAPE = 5,WORKINGAREA = 6,HAVE_NO_CHILDREN = 7,
            WHETHER_YOU_WANT_A_CHILD = 8,SMOKING_STATUS = 9,WHETHER_TO_DRINK = 10,HOUSING_SITUATION = 11,BUYING_A_CAR = 12,NICKNAME = 13,OCCUPATION = 14,PLACE_OF_ORIGIN = 15,
    WEIGHT = 16,CONSTELLATION = 17,NATION = 18,WHEN_TO_GET_MARRIED = 19,GENGER = 20,BIRTHDAY = 21;
    //获取常量
    public static final String TITLE = "title",LIST = "list",FROM = "from",TO = "to",VALUE = "value",PROVINCE = "province",CITY = "city",AREA = "area";
    RelativeLayout mAge_ReLayout,mHeight_ReLayout,mMonthlyIncomeRelayout/* 月收入*/,mEducationReLayout/*学历*/,mMaritalStatusReLayout/*婚姻状况*/,
            mShapeReLayout,mWorkingAreaReLayout/*工作地区*/,mHave_no_childrenReLayout,mWhether_you_want_a_childReLayout,mSmokingStatusReLayout,mWhether_to_drinkReLayout;
    //TODO
    TextView save_TV,mAge_TV,mHeight_TV,mMonthlyIncome_TV,mMaritalStatus_TV,mEducation_TV,mShape_TV,mWorkingArea_TV,mHave_no_children_TV,mWhether_you_want_a_child_TV,mSmokingStatus_TV,mWhether_to_drink_TV;
    //选择器
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_conditional_selection_layout);
        new StatusBarColorUtil(this,R.color.StatusBarColor);
        initView();
        user = BmobUser.getCurrentUser(User.class);
        getData(user);
    }

    private void getData(User user) {
        BmobQuery<Condition> query = new BmobQuery<>();
        query.getObject(user.getConditionId(), new QueryListener<Condition>() {

            @Override
            public void done(Condition object, BmobException e) {
                if(e==null){
                    mAge_TV.setText(object.getAge());
                    mHeight_TV.setText(object.getHeight());
                    mMonthlyIncome_TV.setText(object.getIncome());
                    mEducation_TV.setText(object.getEducation());
                    mShape_TV.setText(object.getShape());
                    mWorkingArea_TV.setText(object.getWorking_area());
                    mHave_no_children_TV.setText(object.getThere_is_no_child());
                    mWhether_you_want_a_child_TV.setText(object.getDo_you_want_a_baby());
                    mSmokingStatus_TV.setText(object.getSmokingStatus());
                    mWhether_to_drink_TV.setText(object.getWhether_to_drink());
                }else{

                }
            }

        });
    }

    private void initView() {
        save_TV = (TextView) findViewById(R.id.save_TV);
        mWhether_to_drink_TV = (TextView) findViewById(R.id.mWhether_to_drink_TV);
        mWhether_to_drinkReLayout = (RelativeLayout) findViewById(R.id.mWhether_to_drinkReLayout);
        mSmokingStatus_TV = (TextView) findViewById(R.id.mSmokingStatus_TV);
        mSmokingStatusReLayout = (RelativeLayout) findViewById(R.id.mSmokingStatusReLayout);
        mWhether_you_want_a_child_TV = (TextView) findViewById(R.id.mWhether_you_want_a_child_TV);
        mWhether_you_want_a_childReLayout = (RelativeLayout) findViewById(R.id.mWhether_you_want_a_childReLayout);
        mHave_no_children_TV = (TextView) findViewById(R.id.mHave_no_children_TV);
        mHave_no_childrenReLayout = (RelativeLayout) findViewById(R.id.mHave_no_childrenReLayout);
        mWorkingAreaReLayout = (RelativeLayout) findViewById(R.id.mWorkingAreaReLayout);
        mWorkingArea_TV = (TextView) findViewById(R.id.mWorkingArea_TV);
        mShapeReLayout = (RelativeLayout) findViewById(R.id.mShapeReLayout);
        mShape_TV = (TextView) findViewById(R.id.mShape_TV);
        mEducation_TV = (TextView) findViewById(R.id.mEducation_TV);
        mMaritalStatus_TV = (TextView) findViewById(R.id.mMaritalStatus_TV);
        mMaritalStatusReLayout = (RelativeLayout) findViewById(R.id.mMaritalStatusReLayout);
        mEducationReLayout = (RelativeLayout) findViewById(R.id.mEducationReLayout);
        mMonthlyIncomeRelayout = (RelativeLayout) findViewById(R.id.mMonthlyIncomeRelayout);
        mMonthlyIncome_TV = (TextView) findViewById(R.id.mMonthlyIncome_TV);
        mHeight_TV = (TextView) findViewById(R.id.mHeight_TV);
        mHeight_ReLayout = (RelativeLayout) findViewById(R.id.mHeight_ReLayout);
        mAge_ReLayout = (RelativeLayout) findViewById(R.id.mAge_ReLayout);
        mAge_TV = (TextView) findViewById(R.id.mAge_TV);
        mAge_ReLayout.setOnClickListener(this);
        mHeight_ReLayout.setOnClickListener(this);
        mMonthlyIncomeRelayout.setOnClickListener(this);
        mEducationReLayout.setOnClickListener(this);
        mMaritalStatusReLayout.setOnClickListener(this);
        mShapeReLayout.setOnClickListener(this);
        mWorkingAreaReLayout.setOnClickListener(this);
        mHave_no_childrenReLayout.setOnClickListener(this);
        mWhether_you_want_a_childReLayout.setOnClickListener(this);
        mSmokingStatusReLayout.setOnClickListener(this);
        mWhether_to_drinkReLayout.setOnClickListener(this);
        save_TV.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mAge_ReLayout:
                Intent ageIntent = new Intent(this,DataSelectActivityTwo.class);
                ageIntent.putIntegerArrayListExtra(LIST,(ArrayList)initAgeWheelviewData());
                ageIntent.putExtra(TITLE,"年龄");
                startActivityForResult(ageIntent,AGE);
                break;
            case R.id.mHeight_ReLayout:
                Intent heightIntent = new Intent(this,DataSelectActivityTwo.class);
                heightIntent.putExtra(TITLE,"身高");
                heightIntent.putStringArrayListExtra(LIST,(ArrayList<String>) initHeightWheelviewData());
                startActivityForResult(heightIntent,HEIGHT);
                break;
            case R.id.mMonthlyIncomeRelayout:
                Intent monthlyIncomeIntent = new Intent(this,DataSelectActivityTwo.class);
                monthlyIncomeIntent.putExtra(TITLE,"月收入");
                monthlyIncomeIntent.putStringArrayListExtra(LIST,(ArrayList<String>) initMonthlyIncomeWheelviewData());
                startActivityForResult(monthlyIncomeIntent,MONTHLY_INCOME);
                break;
            case R.id.mEducationReLayout:
                Intent educationIntent = new Intent(this,DataSelectActivityOne.class);
                educationIntent.putExtra(TITLE,"学历");
                educationIntent.putStringArrayListExtra(LIST,(ArrayList<String>) initEducationWheelviewData());
                startActivityForResult(educationIntent,EDUCATION);
                break;
            case R.id.mMaritalStatusReLayout:
                Intent maritalStatusIntent = new Intent(this,DataSelectActivityOne.class);
                maritalStatusIntent.putExtra(TITLE,"婚姻状况");
                maritalStatusIntent.putStringArrayListExtra(LIST,(ArrayList<String>) initMaritalStatusWheelviewData());
                startActivityForResult(maritalStatusIntent,MARITAL_STATUS);
                break;
            case R.id.mShapeReLayout:
                Intent shapeIntent = new Intent(this,DataSelectActivityOne.class);
                shapeIntent.putExtra(TITLE,"体型");
                shapeIntent.putStringArrayListExtra(LIST,(ArrayList<String>) initShapeWheelviewData());
                startActivityForResult(shapeIntent,SHAPE);
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
                workingAreaIntent.putExtra(TITLE,"工作地区");
                workingAreaIntent.putExtra(PROVINCE,allProv);
                workingAreaIntent.putExtras(bundle);
                startActivityForResult(workingAreaIntent,WORKINGAREA);
                break;
            case R.id.mHave_no_childrenReLayout:
                Intent have_no_childrenIntent = new Intent(this,DataSelectActivityOne.class);
                have_no_childrenIntent.putExtra(TITLE,"有无孩子");
                have_no_childrenIntent.putStringArrayListExtra(LIST,(ArrayList<String>) initHave_no_childrenWheelviewData());
                startActivityForResult(have_no_childrenIntent,HAVE_NO_CHILDREN);
            break;
            case R.id.mWhether_you_want_a_childReLayout:
                Intent whether_you_want_a_childIntent = new Intent(this,DataSelectActivityOne.class);
                whether_you_want_a_childIntent.putExtra(TITLE,"是否想要孩子");
                whether_you_want_a_childIntent.putStringArrayListExtra(LIST,(ArrayList<String>) initWhether_you_want_a_childWheelviewData());
                startActivityForResult(whether_you_want_a_childIntent,WHETHER_YOU_WANT_A_CHILD);
                break;
            case R.id.mSmokingStatusReLayout:
                //SMOKING_STATUS
                Intent smokingStatusIntent = new Intent(this,DataSelectActivityOne.class);
                smokingStatusIntent.putExtra(TITLE,"是否抽烟");
                smokingStatusIntent.putStringArrayListExtra(LIST,(ArrayList<String>) initSmokingStatusWheelviewData());
                startActivityForResult(smokingStatusIntent,SMOKING_STATUS);
                break;
            case R.id.mWhether_to_drinkReLayout:
                Intent whether_to_drinkIntent = new Intent(this,DataSelectActivityOne.class);
                whether_to_drinkIntent.putExtra(TITLE,"是否喝酒");
                whether_to_drinkIntent.putStringArrayListExtra(LIST,(ArrayList<String>) initWhether_to_drinkWheelviewData());
                startActivityForResult(whether_to_drinkIntent,WHETHER_TO_DRINK);
                break;
            case R.id.save_TV:
                user = BmobUser.getCurrentUser(User.class);
                final Condition conditionalSD = new Condition();
                //TODO
                conditionalSD.setObjectId(user.getConditionId());
                conditionalSD.setAge(mAge_TV.getText().toString());
                conditionalSD.setHeight(mHeight_TV.getText().toString());
                conditionalSD.setIncome(mMonthlyIncome_TV.getText().toString());
                conditionalSD.setMarital_status(mMaritalStatus_TV.getText().toString());
                conditionalSD.setEducation(mEducation_TV.getText().toString());
                conditionalSD.setShape(mShape_TV.getText().toString());
                conditionalSD.setWorking_area(mWorkingArea_TV.getText().toString());
                conditionalSD.setThere_is_no_child(mHave_no_children_TV.getText().toString());
                conditionalSD.setDo_you_want_a_baby(mWhether_you_want_a_child_TV.getText().toString());
                conditionalSD.setSmokingStatus(mSmokingStatus_TV.getText().toString());
                conditionalSD.setWhether_to_drink(mWhether_to_drink_TV.getText().toString());
                conditionalSD.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                   if(e == null){
                       //todo
                       Intent intent = new Intent();
                       Bundle mBundle = new Bundle();
                       mBundle.putSerializable("Condition",conditionalSD);
                       intent.putExtras(mBundle);
                       setResult(ConditionalSelectionActivity.RESULT_OK,intent);
                       finish();
                       Toast.makeText(ConditionalSelectionActivity.this, "更新成功!", Toast.LENGTH_SHORT).show();
                   }else {
                       Toast.makeText(ConditionalSelectionActivity.this, "更新失败!", Toast.LENGTH_SHORT).show();
                   }
                    }
                });
                break;
            default:break;
        }
    }

    private List<String> initWhether_to_drinkWheelviewData() {
        List<String> list = new ArrayList<>();
        list.add("不限");
        list.add("不喝");
        list.add("喝");
        return list;
    }

    private List<String> initSmokingStatusWheelviewData() {
        List<String> list = new ArrayList<>();
        list.add("不限");
        list.add("是");
        list.add("否");
        return list;
    }

    private  List<String> initWhether_you_want_a_childWheelviewData() {
        List list = new ArrayList<>();
        list.add("不限");
        list.add("想要孩子");
        list.add("不想要孩子");
        list.add("看情况而定");
        return list;
    }

    private List<String> initHave_no_childrenWheelviewData() {
        List list = new ArrayList<>();
        list.add("不限");
        list.add("没有");
        list.add("有，我们住在一起");
        list.add("有，我们偶尔一起住");
        list.add("有，但不在身边");
        return list;
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
    private List<String> initShapeWheelviewData() {
        List<String> list = new ArrayList<>();
        list.add("不限");
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

    private List<String> initMaritalStatusWheelviewData() {
        List<String> list = new ArrayList<>();
        list.add("不限");
        list.add("未婚");
        list.add("已婚");
        list.add("离异");
        list.add("丧偶");
        return list;
    }

    private List<String> initEducationWheelviewData() {
        List list = new ArrayList<>();
        list.add("不限");
        list.add("中专");
        list.add("高中及以下");
        list.add("大专");
        list.add("大学本科");
        list.add("硕士");
        list.add("博士");
        return list;
    }

    public List<String> initMonthlyIncomeWheelviewData() {
        List list = new ArrayList<>();
        list.add("不限");
        for(int a = 1000;a<50000;a+=2000){
            list.add(""+a);
        }
        return list;
    }

    private List<String> initHeightWheelviewData() {
        List list = new ArrayList<>();
        list.add("不限");
        for(int a = 110;a<212;a++){
            list.add(""+a);
        }
        return list;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case AGE:
                if(resultCode == RESULT_OK) {
                    String leftVlaue = data.getStringExtra(FROM);
                    String rightValue = data.getStringExtra(TO);
                    if (leftVlaue.equals("不限") && rightValue.equals("不限")) {
                        mAge_TV.setText("不限");
                    } else {
                        mAge_TV.setText(leftVlaue + " - " + rightValue + " 岁");
                    }
                }
                break;
            case HEIGHT:
                if(resultCode == RESULT_OK){
                    String leftVlaue = data.getStringExtra(FROM);
                    String rightValue = data.getStringExtra(TO);
                    if (leftVlaue.equals("不限") && rightValue.equals("不限")) {
                        mHeight_TV.setText("不限");
                    } else {
                        mHeight_TV.setText(leftVlaue + " - " + rightValue + " 厘米");
                    }
                }
                break;
            case MONTHLY_INCOME:
                if(resultCode == RESULT_OK){
                    String leftVlaue = data.getStringExtra(FROM);
                    String rightValue = data.getStringExtra(TO);
                    if (leftVlaue.equals("不限") && rightValue.equals("不限")) {
                        mMonthlyIncome_TV.setText("不限");
                    } else {
                        mMonthlyIncome_TV.setText(leftVlaue + " - " + rightValue + " 元");
                    }
                }
            case EDUCATION:
                if(resultCode == RESULT_OK) {
                    String value = data.getStringExtra(VALUE);
                    mEducation_TV.setText(value);
                }
                break;
            case MARITAL_STATUS:
                if(resultCode == RESULT_OK) {
                    String value = data.getStringExtra(VALUE);
                    mMaritalStatus_TV.setText(value);
                }
                break;
            case SHAPE:
                if(resultCode == RESULT_OK) {
                    String value = data.getStringExtra(VALUE);
                    mShape_TV.setText(value);
                }
                break;
            case WORKINGAREA:
                if(resultCode == RESULT_OK) {
                    String provinceStr = data.getStringExtra(PROVINCE);
                    String cityStr = data.getStringExtra(CITY);
                    String areaStr = data.getStringExtra(AREA);
                    //等于空或者等于不限都赋值为空
                    if(provinceStr ==null||provinceStr.equals("不限")){
                        provinceStr = "";
                    }
                    if(cityStr == null||cityStr.equals("不限")){
                        cityStr = "";
                    }
                    if(areaStr == null||areaStr.equals("不限")){
                        areaStr = "";
                    }
                    if(!provinceStr.equals("")&&!cityStr.equals("")&&!areaStr.equals("")) {
                        mWorkingArea_TV.setText(provinceStr + "-" + cityStr + "-" + areaStr);
                    }else if(!provinceStr.equals("")&&!cityStr.equals("")){
                        mWorkingArea_TV.setText(provinceStr + "-" + cityStr);
                    }else if(!provinceStr.equals("")){
                        mWorkingArea_TV.setText(provinceStr);
                    }else{
                        mWorkingArea_TV.setText("不限");
                    }
                }
                break;
            case HAVE_NO_CHILDREN:
                if(resultCode == RESULT_OK) {
                    String value = data.getStringExtra(VALUE);
                    mHave_no_children_TV.setText(value);
                }
                break;
            case WHETHER_YOU_WANT_A_CHILD:
                if(resultCode == RESULT_OK) {
                    String value = data.getStringExtra(VALUE);
                    mWhether_you_want_a_child_TV.setText(value);
                }
                break;
            case SMOKING_STATUS:
                if(resultCode == RESULT_OK) {
                    String value = data.getStringExtra(VALUE);
                    mSmokingStatus_TV.setText(value);
                }
                break;
            case WHETHER_TO_DRINK:
                if(resultCode == RESULT_OK) {
                    String value = data.getStringExtra(VALUE);
                    mWhether_to_drink_TV.setText(value);
                }
                break;
            default:break;
        }
    }
    private List<String> initAgeWheelviewData() {
        List list = new ArrayList<>();
        list.add("不限");
        for(int a = 0;a<100;a++){
            list.add(""+a);
        }
        return list;
    }
}
