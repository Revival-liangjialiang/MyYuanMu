package com.example.yuanmu.lunbo.BmobBean;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/9/7 0007.
 */
public class Story extends BmobObject {
    private String[][] storycomment = new String[1][1];
    private String title;
    private List<String> img;
    private String content;
    private User user;
    public Story(){
        String[] str = new String[1];
        str[0] = "";
        storycomment[0] = str;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String[][] getStorycomment() {
        return storycomment;
    }

    public void setStorycomment(String[][] storycomment) {
        this.storycomment = storycomment;
    }
}
