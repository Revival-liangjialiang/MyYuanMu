package com.example.yuanmu.lunbo.Util;

import com.example.yuanmu.lunbo.BmobBean.User;

/**
 * Created by Administrator on 2016/10/6 0006.
 */
public class UserInfo implements Comparable<UserInfo>{
    private User user;
    public Double distance;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
    @Override
    public int compareTo(UserInfo user) {
        //这里进行排序
        return this.getDistance().compareTo(user.getDistance());
    }
}
