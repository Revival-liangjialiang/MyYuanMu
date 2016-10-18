package com.example.yuanmu.lunbo.Util;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * Created by Administrator on 2016/10/6 0006.
 */
public class getLocationInfo {
    private LocationListener LocationListener;
    //经纬度
    public double mLongitude;
    public double mLatitude;
    public String mCity;
    public String mDistrict;
    public boolean mUploadSwitch = false;
    private Context context;
    public getLocationInfo(Context context){
        this.context = context;
        initLocation();
        startLocation();
    }
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = new AMapLocationClientOption();
    //以下是高德地图
    private void initLocation(){
        //初始化client
        locationClient = new AMapLocationClient(context.getApplicationContext());
        //设置定位参数
        locationClient.setLocationOption(getDefaultOption());
        // 设置定位监听
        locationClient.setLocationListener(locationListener);

        if (null == locationOption) {
            locationOption = new AMapLocationClientOption();
            locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
            locationOption.setNeedAddress(true);
            locationOption.setLocationCacheEnable(true);
            locationClient.setLocationOption(locationOption);
        }
    }
    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation loc) {
            if (null != loc) {
                //todo
                mUploadSwitch = true;
                mCity = loc.getCity();
                mDistrict = loc.getDistrict();
                mLongitude = loc.getLongitude();
                mLatitude = loc.getLatitude();
                if(LocationListener!=null) {
                    LocationListener.finish(mLongitude, mLatitude, mCity,mDistrict);
                }
            } else {
                MyLog.i("kkk","Fail!");
            }
        }
    };
    private AMapLocationClientOption getDefaultOption(){
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(1800000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是ture
        mOption.setOnceLocation(true);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        return mOption;
    }
    private void startLocation(){
        //根据控件的选择，重新设置定位参数
        resetOption();
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }
    // 根据控件的选择，重新设置定位参数
    private void resetOption() {
        // 设置是否需要显示地址信息
        locationOption.setNeedAddress(true);
        /**
         * 设置是否优先返回GPS定位结果，如果30秒内GPS没有返回定位结果则进行网络定位
         * 注意：只有在高精度模式下的单次定位有效，其他方式无效
         */
//		locationOption.setGpsFirst(true);===========================================================
        // 设置是否开启缓存
        locationOption.setLocationCacheEnable(true);
        //设置是否等待设备wifi刷新，如果设置为true,会自动变为单次定位，持续定位时不要使用
        locationOption.setOnceLocationLatest(true);
    }

    public void setLocationListener(getLocationInfo.LocationListener locationListener) {
        LocationListener = locationListener;
    }

    public String getmDistrict() {
        return mDistrict;
    }

    public void setmDistrict(String mDistrict) {
        this.mDistrict = mDistrict;
    }


    public interface LocationListener{
        void finish(Double longitude,Double latitude,String city,String district);
    }
}
