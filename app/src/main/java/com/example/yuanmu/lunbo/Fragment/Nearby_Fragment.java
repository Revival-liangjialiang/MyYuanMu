package com.example.yuanmu.lunbo.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yuanmu.lunbo.Adapter.Near_nearAdapter;
import com.example.yuanmu.lunbo.BmobBean.User;
import com.example.yuanmu.lunbo.Custom.NearListView;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.CalculationUtil_2;
import com.example.yuanmu.lunbo.Util.MyLog;
import com.example.yuanmu.lunbo.Util.UserInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2016/8/25 0025.
 */
public class Nearby_Fragment extends Fragment{
    //附近人集合
    List<User> mUserList = null;
    //计算距离后的附近人集合
    List<UserInfo> mUserInfoList = new ArrayList<>();
    private View view;
    private NearListView rv_2;
    public static final int REFRESH_DELAY = 2000;
    private Near_nearAdapter nearadapter;
    private List<Map<String, Object>> nlist = new ArrayList<Map<String, Object>>();
    private Map<String, Object> nmap;
    private Context context;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.two, null);
        context = getActivity();
        initView();
        //查询附近人
        queryNearbyPeople();
        initData();
        getData();
        return view;
    }
    //查询附近人
    private void queryNearbyPeople() {
        BmobQuery<User> query = new BmobQuery<User>();
//查询playerName叫“比目”的数据
        query.addWhereEqualTo("city", "广州市");
//返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(50);
//执行查询方法
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if(e==null){
                    mUserList = object;
                    //计算附近人与本人的距离
                    calculationDistance();
                    MyLog.i("kkk","mUserList = "+mUserList.size());
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }
    private void calculationDistance() {
        User currentUser = BmobUser.getCurrentUser(User.class);
        Double longitude = Double.valueOf(currentUser.getLongitute());
        Double latitude = Double.valueOf(currentUser.getLatitude());
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
    }

    private void initView() {
        rv_2 = (NearListView) view.findViewById(R.id.rv_2);
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (this.getView() != null) {
            this.getView()
                    .setVisibility(menuVisible ? View.VISIBLE : View.GONE);
        }
    }

    public void getData() {
        nmap = new HashMap<String, Object>();
        nmap.put("img", "http://e.hiphotos.baidu.com/image/h%3D360/sign=8edabc3c6963f624035d3f05b745eb32/203fb80e7bec54e74a142d1bbb389b504fc26a3e.jpg");
        nmap.put("txt", "林妹妹");
        nlist.add(nmap);
        nmap = new HashMap<String, Object>();
        nmap.put("img", "http://b.hiphotos.baidu.com/image/h%3D360/sign=a0273f604dc2d562ed08d6ebd71190f3/7e3e6709c93d70cf22d83b0dfadcd100baa12b8b.jpg");
        nmap.put("txt", "王小花");
        nlist.add(nmap);
        nmap = new HashMap<String, Object>();
        nmap.put("img", "http://g.hiphotos.baidu.com/image/h%3D360/sign=4f62888e6e81800a71e58f08813533d6/d50735fae6cd7b89006b4b820d2442a7d9330eaf.jpg");
        nmap.put("txt", "小姨子");
        nlist.add(nmap);
        nmap = new HashMap<String, Object>();
        nmap.put("img", "http://g.hiphotos.baidu.com/image/h%3D360/sign=31c5a0bf37fae6cd13b4ad673fb10f9e/29381f30e924b8990fef38b06c061d950b7bf663.jpg");
        nmap.put("txt", "二狗子他妈");
        nlist.add(nmap);
        nmap = new HashMap<String, Object>();
        nmap.put("img", "http://b.hiphotos.baidu.com/image/h%3D360/sign=8e4ce5ba85d6277ff612343e18381f63/1b4c510fd9f9d72a6914611cd62a2834349bbbba.jpg");
        nmap.put("txt", "铁哥哥");
        nlist.add(nmap);
        nmap = new HashMap<String, Object>();
        nmap.put("img", "http://g.hiphotos.baidu.com/image/h%3D360/sign=67ed572fb251f819ee25054ceab54a76/d6ca7bcb0a46f21fe0f68f57f4246b600d33aef0.jpg");
        nmap.put("txt", "马大姐");
        nlist.add(nmap);
        nearadapter.notifyDataSetChanged();
    }
}
