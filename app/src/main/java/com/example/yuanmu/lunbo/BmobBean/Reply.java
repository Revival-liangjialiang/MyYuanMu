package com.example.yuanmu.lunbo.BmobBean;

import cn.bmob.v3.BmobObject;

/**
 * Created by k on 2016/9/25.
 */
public class Reply extends BmobObject{
    //index = 0 为回复用户
    //index = 1 为被回复用户
    private String [] nickNameArray;
    private String content;
    private String belong;
    public Reply(){
        nickNameArray = new String[2];
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBelong() {
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }


    public String[] getNickNameArray() {
        return nickNameArray;
    }

    public void setNickNameArray(String[] nickNameArray) {
        this.nickNameArray = nickNameArray;
    }
}
