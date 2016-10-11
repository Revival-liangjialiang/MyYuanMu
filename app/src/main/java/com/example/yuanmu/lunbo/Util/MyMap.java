package com.example.yuanmu.lunbo.Util;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by k on 2016/9/18.
 */
public class MyMap implements Serializable {

    private Map<String, String[]> cityMap;
    private Map<String, String[]> areaMap;

    public MyMap() {
    }

    public Map<String, String[]> getCityMap() {
        return cityMap;
    }

    public void setCityMap(Map<String, String[]> cityMap) {
        this.cityMap = cityMap;
    }

    public Map<String, String[]> getAreaMap() {
        return areaMap;
    }

    public void setAreaMap(Map<String, String[]> areaMap) {
        this.areaMap = areaMap;
    }
}
