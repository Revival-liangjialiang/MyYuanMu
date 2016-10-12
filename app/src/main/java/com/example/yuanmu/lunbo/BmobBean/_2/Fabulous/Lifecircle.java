package com.example.yuanmu.lunbo.BmobBean._2.Fabulous;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/10/12 0012.
 */
public class Lifecircle extends BmobObject{
    private List<String> fabulous = new ArrayList<>();

    public List<String> getFabulous() {
        return fabulous;
    }

    public void setFabulous(List<String> fabulous) {
        this.fabulous = fabulous;
    }
}
