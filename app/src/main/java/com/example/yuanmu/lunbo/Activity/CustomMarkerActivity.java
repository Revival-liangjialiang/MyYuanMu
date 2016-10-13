/*
package com.example.yuanmu.lunbo.Activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMap.OnMapLoadedListener;
import com.amap.api.maps2d.AMap.OnMarkerClickListener;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.example.yuanmu.lunbo.Custom.CircleImageView;
import com.example.yuanmu.lunbo.R;

import java.util.List;

*/
/**
 * AMapV1地图中简单介绍一些Marker的用法.
 *//*

public class CustomMarkerActivity extends Activity implements OnMarkerClickListener,OnMapLoadedListener,
        OnClickListener{
    private TextView markerText;
    private Button markerButton;// 获取屏幕内所有marker的button
    private AMap aMap;
    private MapView mapView;
    private LatLng latlng = new LatLng(36.061, 103.834);
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custommarker_activity);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState); // 此方法必须重写
        context = CustomMarkerActivity.this;
        init();
    }

    */
/**
     * 初始化AMap对象
     *//*

    private void init() {
        markerText = (TextView) findViewById(R.id.mark_listenter_text);
        markerButton = (Button) findViewById(R.id.marker_button);
        markerButton.setOnClickListener(this);
        Button clearMap = (Button) findViewById(R.id.clearMap);
        clearMap.setOnClickListener(this);
        Button resetMap = (Button) findViewById(R.id.resetMap);
        resetMap.setOnClickListener(this);
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
    }

    private void setUpMap() {
        aMap.setOnMapLoadedListener(this);// 设置amap加载成功事件监听器
        aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
        addMarkersToMap();// 往地图上添加marker
    }

    */
/**
     * 方法必须重写
     *//*

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    */
/**
     * 方法必须重写
     *//*

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    */
/**
     * 方法必须重写
     *//*

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    */
/**
     * 方法必须重写
     *//*

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    */
/**
     * 在地图上添加marker
     *//*

    private void addMarkersToMap() {

        int[] imgarray = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e,R.drawable.g, R.drawable.h, R.drawable.i, R.drawable.j, R.drawable.k,
                R.drawable.l, R.drawable.m, R.drawable.n, R.drawable.o, R.drawable.p,R.drawable.q, R.drawable.r, R.drawable.s, R.drawable.t, R.drawable.u
        ,R.drawable.v, R.drawable.w, R.drawable.x, R.drawable.y, R.drawable.z,R.drawable.s27, R.drawable.s28, R.drawable.s29, R.drawable.s30, R.drawable.s31};
        String[] lat = {"36.830","36.730","36.780","36.800","36.740","36.837","36.739","36.782","36.818","36.760","36.834","36.790","36.788","36.801","36.749",
                "36.830","36.760","36.790","36.810","36.813","36.820","36.788","36.780","36.840","36.785","36.8240","36.767","36.777","36.805","36.797"};
        String[] lng = {"104.829","104.729","104.839","104.809","104.770","104.779","104.769","104.739","104.819","104.770","104.769","104.749","104.818","104.799","104.750",
                "104.819","104.732","104.830","104.800","104.776","104.822","104.735","104.789","104.799","104.780","104.826","104.755","104.753","104.801","104.763"};
        for ( int i = 0; i < imgarray.length; i++){
            View view = View.inflate(context, R.layout.item_market, null);
            CircleImageView civ = (CircleImageView) view.findViewById(R.id.civ);
            civ.setImageResource(imgarray[i]);
            Bitmap bitmap = this.convertViewToBitmap(view);
            drawMarkerOnMap(new LatLng(Double.parseDouble(lat[i])
                    , Double.parseDouble(lng[i])), bitmap, "大神");
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

    */
/**
     * 在地图上画marker
     *
     * @param point      marker坐标点位置（example:LatLng point = new LatLng(39.963175,
     *                   116.400244); ）
     * @param markerIcon 图标
     * @return Marker对象
     *//*

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


    */
/**
     * 对marker标注点点击响应事件
     *//*

    @Override
    public boolean onMarkerClick(final Marker marker) {
        markerText.setText("你点击的是" + marker.getTitle());
        return true;
    }


    */
/**
     * 监听amap地图加载成功事件回调
     *//*

    @Override
    public void onMapLoaded() {
        // 设置所有maker显示在当前可视区域地图中
        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(Constants.XIAN).include(Constants.CHENGDU)
                .include(latlng).include(Constants.ZHENGZHOU).include(Constants.BEIJING).build();
        aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 10));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            */
/**
             * 清空地图上所有已经标注的marker
             *//*

            case R.id.clearMap:
                if (aMap != null) {
                    aMap.clear();
                }
                break;
            */
/**
             * 重新标注所有的marker
             *//*

            case R.id.resetMap:
                if (aMap != null) {
                    aMap.clear();
                    addMarkersToMap();
                }
                break;
            // 获取屏幕所有marker
            case R.id.marker_button:
                if (aMap != null) {
                    List<Marker> markers = aMap.getMapScreenMarkers();
                    if (markers == null || markers.size() == 0) {
                        ToastUtil.show(this, "当前屏幕内没有Marker");
                        return;
                    }
                    String tile = "屏幕内有：";
                    for (Marker marker : markers) {
                        tile = tile + " " + marker.getTitle();

                    }
                    ToastUtil.show(this, tile);
                }
                break;
            default:
                break;
        }
    }
}
*/
