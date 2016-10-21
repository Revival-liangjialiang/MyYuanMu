package com.example.yuanmu.lunbo.BmobBean.Album;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2016/10/21 0021.
 */
public class User extends BmobUser{
    private String[] album;

    public String[] getAlbum() {
        return album;
    }

    public void setAlbum(String[] album) {
        this.album = album;
    }
}
