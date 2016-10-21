package com.example.yuanmu.lunbo.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuanmu.lunbo.Activity.MainActivity;
import com.example.yuanmu.lunbo.Adapter.Near_nearAdapter;
import com.example.yuanmu.lunbo.BmobBean.User;
import com.example.yuanmu.lunbo.Custom.NearListView;
import com.example.yuanmu.lunbo.Custom.SelectTopPopupWindow;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.CalculationUtil_2;
import com.example.yuanmu.lunbo.Util.MyLog;
import com.example.yuanmu.lunbo.Util.UserInfo;
import com.example.yuanmu.lunbo.Util.getLocationInfo;
import com.example.yuanmu.lunbo.interfaces.KeywithValueUtil;
import com.example.yuanmu.lunbo.interfaces.PublicInterface;
import com.victor.loading.rotate.RotateLoading;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2016/8/25 0025.
 */
public class Nearby_Fragment extends Fragment implements View.OnClickListener{
    String mCity;
    boolean mLocationSwitch = false,mDelaySwitch = false;
   public RelativeLayout loading_layout;
   public RotateLoading rotateLoading;
    MainActivity mainActivity;
    TextView mSelect_tv;
    TextView mPrompt_tv;
    //附近人集合
    public List<User> mUserList = null;
    public List<User> mUserListCopy = new ArrayList<>();
    //计算距离后的附近人集合
   public List<UserInfo> mUserInfoList = new ArrayList<>();
    private View view;
    private NearListView rv_2;
    public static final int REFRESH_DELAY = 2000;
    private Near_nearAdapter nearadapter;
    private List<Map<String, Object>> nlist = new ArrayList<Map<String, Object>>();
    private Map<String, Object> nmap;
    private Context context;
    private LinearLayout ll_1;
    private TextView tv_sex;
    private TextView tv_age;
    private TextView mPlace_of_origin_tv;
    private SelectTopPopupWindow menuWindow; // 自定义的头像编辑弹出框
    private String[] array_sex = {"性别","男", "女"};
    private String COMMAND_SEX = "性别";
    private String[] array_age = {"年龄","0-20", "21-25", "26-30", "31-35", "36-40", "41-45", "46-50", "51-60"};
    private String COMMAND_AGE = "年龄";
    private String[] array_province = {"籍贯","北京","天津","重庆","上海","河北","山西","辽宁","吉林","黑龙江","江苏","浙江","安徽","福建","江西","山东","河南","湖北","湖南","广东","海南","四川","贵州","云南","陕西","甘肃","青海","台湾","内蒙古自治区","广西壮族自治区","西藏自治区","宁夏回族自治区","新疆维吾尔自治区","香港特别行政区","澳门特别行政区"};
    private String COMMAND_PROVINCE = "籍贯";
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.two, null);
        context = getActivity();
        initView();
        initData();
        getPositionInfo();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        mainActivity.setmNearby_fragment(this);
        //如果时间超过5秒，即开通网络异常提醒开关
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    mDelaySwitch = true;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


//得到位置信息
    private void getPositionInfo() {
        getLocationInfo getLocationInfo = new getLocationInfo(getContext());
        getLocationInfo.setLocationListener(new getLocationInfo.LocationListener() {
            @Override
                public void finish(Double longitude, Double latitude, String city, String district) {
                Nearby_Fragment.this.mCity = city;
                mLocationSwitch = true;
                //查询附近人
                queryNearbyPeople();
            }
        });
    }

    //查询附近人
    private void queryNearbyPeople() {
        BmobQuery<User> query = new BmobQuery<User>();
//查询playerName叫“比目”的数据
        query.addWhereEqualTo("city", mCity);
//返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(20);
//执行查询方法
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if(e==null){
                    mUserListCopy = mUserList = object;
                    //计算附近人与本人的距离
                    calculationDistance();
                    MyLog.i("kkk","mUserList = "+mUserList.size());
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }
    public void calculationDistance() {
        User currentUser = BmobUser.getCurrentUser(User.class);
        Double longitude = Double.valueOf(currentUser.getLongitute());
        Double latitude = Double.valueOf(currentUser.getLatitude());
        if(mUserList.size()==0){
            mPrompt_tv.setVisibility(View.VISIBLE);
        }else {
            mPrompt_tv.setVisibility(View.GONE);
        }
        for(int a = 0;a<mUserList.size();a++){
            UserInfo userInfo = new UserInfo();
            User user = mUserList.get(a);
            //计算距离
            Double distance = CalculationUtil_2.getDistatce(latitude,Double.valueOf(user.getLatitude()),longitude,Double.valueOf(user.getLongitute()));
            //把用户和该用户和当前用户的距离传进去
            userInfo.setDistance(distance);
            userInfo.setUser(user);
            mUserInfoList.add(userInfo);
        }
        //将UserInfoList里面的数据按距离从小到大排序
        Collections.sort(mUserInfoList);
        rv_2.setAdapter(nearadapter);
    }

    private void initData() {
        //设置布局管理器
        nearadapter = new Near_nearAdapter(context,mUserInfoList);
        tv_sex.setOnClickListener(this);
        tv_age.setOnClickListener(this);
        mPlace_of_origin_tv.setOnClickListener(this);
        KeywithValueUtil.setKeywithValueInterface(new PublicInterface() {

            @Override
            public void getkeywithvalue(String key, String value) {
                if (key.equals(COMMAND_SEX)) {
                    tv_sex.setText(value);
                }
                if (key.equals(COMMAND_AGE)) {
                    tv_age.setText(value);
                }
                if (key.equals(COMMAND_PROVINCE)) {
                    mPlace_of_origin_tv.setText(value);
                }
                initColor();
                menuWindow.dismiss();

            }


        });
    }

    private void initView() {
        mPrompt_tv = (TextView) view.findViewById(R.id.mPrompt_tv);
        loading_layout = (RelativeLayout) view.findViewById(R.id.loading_layout);
        rotateLoading = (RotateLoading) view.findViewById(R.id.rotateloading);
        mSelect_tv = (TextView) view.findViewById(R.id.mSelect_tv);
        rv_2 = (NearListView) view.findViewById(R.id.rv_2);
        ll_1 = (LinearLayout) view.findViewById(R.id.ll_1);
        tv_sex = (TextView) view.findViewById(R.id.tv_sex);
        tv_age = (TextView) view.findViewById(R.id.tv_age);
        mPlace_of_origin_tv = (TextView) view.findViewById(R.id.mPlace_of_origin_tv);
        mSelect_tv.setOnClickListener(this);
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (this.getView() != null) {
            this.getView()
                    .setVisibility(menuVisible ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        initColor();
        switch (view.getId()){
            case R.id.mSelect_tv:
                //网络定位成功值即为true!
                if(mLocationSwitch) {
                    // TODO: 2016/10/20 0020
                    String sex = tv_sex.getText().toString();
                    String age = tv_age.getText().toString();
                    String place_of_origin = mPlace_of_origin_tv.getText().toString();
                    //不进行网络请求,用回原来的数据
                    if (sex.equals("性别") && age.equals("年龄") && place_of_origin.equals("籍贯")) {
                        mUserInfoList.clear();
                        mUserList = mUserListCopy;
                        calculationDistance();
                    }
                    //按性别查询
                    if (sex.equals("性别") != true && age.equals("年龄") && place_of_origin.equals("籍贯")) {
                        queryUser("gender", sex);
                    }
                    //按年龄查询
                    if (sex.equals("性别") && age.equals("年龄") != true && place_of_origin.equals("籍贯")) {
                        String[] ageStrArray = age.split("-");
                        int minAge = Integer.valueOf(ageStrArray[0]);
                        int maxAge = Integer.valueOf(ageStrArray[1]);
                        BmobQuery<User> eq1 = new BmobQuery<>();
                        eq1.addWhereLessThanOrEqualTo("age", maxAge + "");//年龄<=maxAge
                        BmobQuery<User> eq2 = new BmobQuery<>();
                        eq2.addWhereGreaterThanOrEqualTo("age", minAge + "");//年龄>=minAge
                        BmobQuery<User> qu3 = new BmobQuery<User>();
                        qu3.addWhereEqualTo("city",mCity);
                        List<BmobQuery<User>> andQuerys = new ArrayList<>();
                        andQuerys.add(eq1);
                        andQuerys.add(eq2);
                        andQuerys.add(qu3);
                        BmobQuery<User> query = new BmobQuery<>();
                        query.and(andQuerys);
                        query.findObjects(new FindListener<User>() {
                            @Override
                            public void done(List<User> object, BmobException e) {
                                if (e == null) {
                                    mUserInfoList.clear();
                                    mUserList = object;
                                    calculationDistance();
                                } else {
                                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                                }
                            }
                        });
                    }
                    //按籍贯查询
                    if (sex.equals("性别") && age.equals("年龄") && place_of_origin.equals("籍贯") != true) {
                        queryUser("province", place_of_origin);
                    }
                    //按性别和年龄查询
                    if (sex.equals("性别") != true && age.equals("年龄") != true && place_of_origin.equals("籍贯")) {
                        String[] ageStrArray = age.split("-");
                        int minAge = Integer.valueOf(ageStrArray[0]);
                        int maxAge = Integer.valueOf(ageStrArray[1]);
                        BmobQuery<User> eq1 = new BmobQuery<>();
                        eq1.addWhereLessThanOrEqualTo("age", maxAge + "");//年龄<=maxAge
                        BmobQuery<User> eq2 = new BmobQuery<>();
                        eq2.addWhereGreaterThanOrEqualTo("age", minAge + "");//年龄>=minAge
                        BmobQuery<User> eq3 = new BmobQuery<>();
                        eq3.addWhereEqualTo("gender", sex);
                        BmobQuery<User> qu2 = new BmobQuery<User>();
                        qu2.addWhereEqualTo("city",mCity);
                        List<BmobQuery<User>> andQuerys = new ArrayList<>();
                        andQuerys.add(eq1);
                        andQuerys.add(eq2);
                        andQuerys.add(eq3);
                        andQuerys.add(qu2);
                        BmobQuery<User> query = new BmobQuery<>();
                        query.and(andQuerys);
                        query.findObjects(new FindListener<User>() {
                            @Override
                            public void done(List<User> object, BmobException e) {
                                if (e == null) {
                                    mUserInfoList.clear();
                                    mUserList = object;
                                    calculationDistance();
                                } else {
                                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                                }
                            }
                        });
                    }
                    //按性别和籍贯查询
                    if (sex.equals("性别") != true && age.equals("年龄") && place_of_origin.equals("籍贯") != true) {
                        BmobQuery<User> eq1 = new BmobQuery<>();
                        eq1.addWhereEqualTo("province", place_of_origin);
                        BmobQuery<User> eq2 = new BmobQuery<>();
                        eq2.addWhereEqualTo("gender", sex);
                        List<BmobQuery<User>> andQuerys = new ArrayList<>();
                        andQuerys.add(eq1);
                        andQuerys.add(eq2);
                        BmobQuery<User> query = new BmobQuery<>();
                        query.and(andQuerys);
                        query.findObjects(new FindListener<User>() {
                            @Override
                            public void done(List<User> object, BmobException e) {
                                if (e == null) {
                                    mUserInfoList.clear();
                                    mUserList = object;
                                    calculationDistance();
                                } else {
                                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                                }
                            }
                        });
                    }
                    //按年龄和籍贯
                    if (sex.equals("性别") && age.equals("年龄") != true && place_of_origin.equals("籍贯") != true) {
                        String[] ageStrArray = age.split("-");
                        int minAge = Integer.valueOf(ageStrArray[0]);
                        int maxAge = Integer.valueOf(ageStrArray[1]);
                        BmobQuery<User> eq1 = new BmobQuery<>();
                        eq1.addWhereLessThanOrEqualTo("age", maxAge + "");//年龄<=maxAge
                        BmobQuery<User> eq2 = new BmobQuery<>();
                        eq2.addWhereGreaterThanOrEqualTo("age", minAge + "");//年龄>=minAge
                        BmobQuery<User> eq3 = new BmobQuery<>();
                        eq3.addWhereEqualTo("province", place_of_origin);
                        List<BmobQuery<User>> andQuerys = new ArrayList<>();
                        andQuerys.add(eq1);
                        andQuerys.add(eq2);
                        andQuerys.add(eq3);
                        BmobQuery<User> query = new BmobQuery<>();
                        query.and(andQuerys);
                        query.findObjects(new FindListener<User>() {
                            @Override
                            public void done(List<User> object, BmobException e) {
                                if (e == null) {
                                    mUserInfoList.clear();
                                    mUserList = object;
                                    calculationDistance();
                                } else {
                                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                                }
                            }
                        });
                    }
                    //查询三个条件
                    if (sex.equals("性别") != true && age.equals("年龄") != true && place_of_origin.equals("籍贯") != true) {
                        String[] ageStrArray = age.split("-");
                        int minAge = Integer.valueOf(ageStrArray[0]);
                        int maxAge = Integer.valueOf(ageStrArray[1]);
                        BmobQuery<User> eq1 = new BmobQuery<>();
                        eq1.addWhereLessThanOrEqualTo("age", maxAge + "");//年龄<=maxAge
                        BmobQuery<User> eq2 = new BmobQuery<>();
                        eq2.addWhereGreaterThanOrEqualTo("age", minAge + "");//年龄>=minAge
                        BmobQuery<User> eq3 = new BmobQuery<>();
                        eq3.addWhereEqualTo("gender", sex);
                        BmobQuery<User> eq4 = new BmobQuery<>();
                        eq4.addWhereEqualTo("province", place_of_origin);
                        List<BmobQuery<User>> andQuerys = new ArrayList<>();
                        andQuerys.add(eq1);
                        andQuerys.add(eq2);
                        andQuerys.add(eq3);
                        andQuerys.add(eq4);
                        BmobQuery<User> query = new BmobQuery<>();
                        query.and(andQuerys);
                        query.findObjects(new FindListener<User>() {
                            @Override
                            public void done(List<User> object, BmobException e) {
                                if (e == null) {
                                    mUserInfoList.clear();
                                    mUserList = object;
                                    calculationDistance();
                                } else {
                                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                                }
                            }
                        });
                    }
                }else{
                    Toast.makeText(getContext(), "定位失败,请检查网络连接!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_sex:
                tv_sex.setTextColor(context.getResources().getColor(R.color.sky_blue));
                menuWindow = new SelectTopPopupWindow(context, COMMAND_SEX,
                        array_sex);
                menuWindow.showAsDropDown(ll_1);
                break;
            case R.id.tv_age:
                tv_age.setTextColor(context.getResources().getColor(R.color.sky_blue));
                menuWindow = new SelectTopPopupWindow(context, COMMAND_AGE,
                        array_age);
                menuWindow.showAsDropDown(ll_1);
                break;
            case R.id.mPlace_of_origin_tv:
                mPlace_of_origin_tv.setTextColor(context.getResources().getColor(R.color.sky_blue));
                menuWindow = new SelectTopPopupWindow(context, COMMAND_PROVINCE,
                        array_province);
                menuWindow.showAsDropDown(ll_1);
                break;
        }
    }
    protected void initColor() {
        tv_sex.setTextColor(context.getResources().getColor(R.color.toptextcolor));
        tv_age.setTextColor(context.getResources().getColor(R.color.toptextcolor));
        mPlace_of_origin_tv.setTextColor(context.getResources().getColor(R.color.toptextcolor));
    }
public void queryUser(String key,String value){
    BmobQuery<User> qu1 = new BmobQuery<User>();
//查询playerName叫“比目”的数据
    qu1.addWhereEqualTo(key, value);
//返回50条数据，如果不加上这条语句，默认返回10条数据
    qu1.setLimit(20);
    BmobQuery<User> qu2 = new BmobQuery<User>();
    qu2.addWhereEqualTo("city",mCity);
    List<BmobQuery<User>> queryList = new ArrayList<>();
    queryList.add(qu1);
    queryList.add(qu2);
    BmobQuery<User> query = new BmobQuery<User>();
    query.and(queryList);
    query.findObjects(new FindListener<User>() {
        @Override
        public void done(List<User> object, BmobException e) {
            if(e==null){
                mUserInfoList.clear();
                mUserList = object;
                //计算附近人与本人的距离
                calculationDistance();
            }else{
                Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
            }
        }
    });
}

}
