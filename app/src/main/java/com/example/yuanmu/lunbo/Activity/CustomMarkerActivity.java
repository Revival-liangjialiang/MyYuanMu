package com.example.yuanmu.lunbo.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMap.OnMapLoadedListener;
import com.amap.api.maps2d.AMap.OnMarkerClickListener;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.example.yuanmu.lunbo.BmobBean.User;
import com.example.yuanmu.lunbo.Custom.CircleImageView;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.VolleyRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.rong.message.LocationMessage;

/**
 * AMapV1地图中简单介绍一些Marker的用法.
 */
public class CustomMarkerActivity extends Activity implements OnMarkerClickListener,OnMapLoadedListener,LocationSource,AMapLocationListener,
        OnClickListener{
    Map<String,Bitmap> map = new HashMap();
    List<User> mUsers;
    private TextView markerText;
    private Button markerButton;// 获取屏幕内所有marker的button
    private AMap aMap;
    private MapView mapView;
    private LatLng latlng = new LatLng(36.061, 103.834);
    private Context context;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = new AMapLocationClientOption();
    private OnLocationChangedListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custommarker_activity);
        Intent intent = getIntent();
        mUsers = (List<User>) intent.getSerializableExtra("list");
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState); // 此方法必须重写
        context = CustomMarkerActivity.this;
        init(mUsers);
        loadHeadportrait(mUsers);

    }
//加载头像
    private void loadHeadportrait(final List<User> mUsers) {
        String[] str = new String[mUsers.size()];
        for(int a = 0;a<mUsers.size();a++){
            User user = mUsers.get(a);
            str[a] = user.getImg();
        }
        VolleyRequest volleyRequest = new VolleyRequest(this);
        volleyRequest.PictureRequestListener(new VolleyRequest.PictureRequestListener() {
            @Override
            public void onRessponse(Bitmap bitmap, String str) {
                map.put(str,bitmap);
            }

            @Override
            public void onFinish() {
                addMarkersToMap();
            }
        });
        volleyRequest.startRequest(str);
    }

    /**
     * 初始化AMap对象
     */
    private void init(List<User> users) {
        markerText = (TextView) findViewById(R.id.mark_listenter_text);
        markerButton = (Button) findViewById(R.id.marker_button);
        markerButton.setOnClickListener(this);
        Button clearMap = (Button) findViewById(R.id.clearMap);
        clearMap.setOnClickListener(this);
        Button resetMap = (Button) findViewById(R.id.resetMap);
        resetMap.setOnClickListener(this);
        if (aMap == null) {
            aMap = mapView.getMap();
            aMap.setLocationSource(this);// 设置定位监听
            aMap.setMyLocationEnabled(true);
            aMap.getUiSettings().setZoomControlsEnabled(false);
            aMap.getUiSettings().setMyLocationButtonEnabled(false);
            setUpMap(users);
        }
    }

    private void setUpMap(List<User> users) {
        aMap.setOnMapLoadedListener(this);// 设置amap加载成功事件监听器
        aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
       /* addMarkersToMap(users);// 往地图上添加marker*/
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    /**
     * 在地图上添加marker
     */
    private void addMarkersToMap() {
        for ( int i = 0; i < mUsers.size(); i++){
            User user = mUsers.get(i);
            View view = View.inflate(context, R.layout.item_market, null);
            CircleImageView civ = (CircleImageView) view.findViewById(R.id.civ);
            civ.setImageBitmap(map.get(user.getImg()));
            Bitmap bitmap = this.convertViewToBitmap(view);
            drawMarkerOnMap(new LatLng(Double.parseDouble(user.getLatitude())
                    , Double.parseDouble(user.getLongitute())), bitmap, "大神");
        }

    }

    //view 转bitmap
    public static Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    /**
     * 在地图上画marker
     *
     * @param point      marker坐标点位置（example:LatLng point = new LatLng(39.963175,
     *                   116.400244); ）
     * @param markerIcon 图标
     * @return Marker对象
     */
    private Marker drawMarkerOnMap(LatLng point, Bitmap markerIcon, String id) {
        if (aMap != null && point != null) {
            Marker marker = aMap.addMarker(new MarkerOptions().anchor(0.5f, 1)
                    .position(point)
                    .title(id)
                    .icon(BitmapDescriptorFactory.fromBitmap(markerIcon)));
            return marker;
        }
        return null;

    }
    /**
     * 对marker标注点点击响应事件
     */
    @Override
    public boolean onMarkerClick(final Marker marker) {
        markerText.setText("你点击的是" + marker.getTitle());
        return true;
    }
    /**
     * 监听amap地图加载成功事件回调
     */
    @Override
    public void onMapLoaded() {

//        CameraUpdate cameraUpdate = CameraUpdateFactory.zoomTo(11);//设置缩放监听

        LatLngBounds bounds = new LatLngBounds.Builder().build();// 设置所有maker显示在当前可视区域地图中
//        for(int a = 0;a<mUsers.size();a++) {
//            User user = mUsers.get(a);
            bounds.including(new LatLng(23.013692, 113.297943));
//        }
        aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 13));

//        aMap.moveCamera(cameraUpdate);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /**
             * 清空地图上所有已经标注的marker
             */
            case R.id.clearMap:
                if (aMap != null) {
                    aMap.clear();
                }
                break;
            /**
             * 重新标注所有的marker
             */
            case R.id.resetMap:
                if (aMap != null) {
                    aMap.clear();
                    /*addMarkersToMap(mUsers);*/
                }
                break;
            // 获取屏幕所有marker
            case R.id.marker_button:
                if (aMap != null) {
                    List<Marker> markers = aMap.getMapScreenMarkers();
                    if (markers == null || markers.size() == 0) {
                        return;
                    }
                    String tile = "屏幕内有：";
                    for (Marker marker : markers) {
                        tile = tile + " " + marker.getTitle();

                    }


                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
            if (listener != null) {
                listener.onLocationChanged(aMapLocation);// 显示系统小蓝点
            }
            double latitude = aMapLocation.getLatitude();
            double longitude = aMapLocation.getLongitude();
            LocationMessage.obtain(latitude, longitude, aMapLocation.getRoad() + aMapLocation.getStreet() + aMapLocation.getPoiName(), getMapUrl(latitude, longitude));


        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        listener = onLocationChangedListener;
        if (locationClient == null) {
            locationClient = new AMapLocationClient(this);
            locationClient.setLocationListener(this);
            locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            locationClient.setLocationOption(locationOption);
            locationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        listener = null;
        if (locationClient != null) {
            locationClient.stopLocation();
            locationClient.onDestroy();
        }
        locationClient = null;
        locationOption = null;
    }

    private Uri getMapUrl(double x, double y) {
        String url = "http://restapi.amap.com/v3/staticmap?location=" + y + "," + x +
                "&zoom=16&scale=2&size=408*240&markers=mid,,A:" + y + ","
                + x + "&key=" + "ee95e52bf08006f63fd29bcfbcf21df0";

        return Uri.parse(url);
    }
}
