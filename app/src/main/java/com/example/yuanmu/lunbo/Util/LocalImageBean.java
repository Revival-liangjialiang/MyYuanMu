package com.example.yuanmu.lunbo.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LocalImageBean {
    /**
     * 用来存储图片的选中情况
     */
    public static HashMap<String, Boolean> mSelectMap;


    /**
     * 被选择的图片的数量
     */
    public static int Count;
    /**
     * 存放图片地址
     */
    public static List list;

    /**
     * list清空
     */
    public static void clearlist() {
        if (list != null) {
            list.clear();
        }
    }


    /**
     * @param path 图片路径
     *             向list中添加图片路径
     */
    public static void addlist(String path) {
        list.add(path);
    }

    public static void init() {
        list = new ArrayList();
        mSelectMap = new HashMap<String, Boolean>();
        Count = 0;
    }
}
