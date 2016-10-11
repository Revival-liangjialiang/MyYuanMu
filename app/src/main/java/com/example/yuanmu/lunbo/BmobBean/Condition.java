package com.example.yuanmu.lunbo.BmobBean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/10/11 0011.
 */
public class Condition extends BmobObject{

    private String userNickName;
    //年龄
    private String age = "不限";
    //月收入
    private String income = "不限";
    //工作地区
    private String working_area = "不限";
    //有没有小孩
    private String there_is_no_child = "不限";
    //是否想要小孩
    private String do_you_want_a_baby = "不限";
    //体重
    private String weight = "不限";
    // 体型
    private String shape = "不限";
    // 是否喝酒
    private String whether_to_drink = "不限";
    //是否抽烟
    private String smokingStatus = "不限";
    // 身高
    private String height = "不限";
    //学历
    private String education = "不限";
    //婚姻状况
    private String marital_status = "不限";

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getWorking_area() {
        return working_area;
    }

    public void setWorking_area(String working_area) {
        this.working_area = working_area;
    }

    public String getThere_is_no_child() {
        return there_is_no_child;
    }

    public void setThere_is_no_child(String there_is_no_child) {
        this.there_is_no_child = there_is_no_child;
    }

    public String getDo_you_want_a_baby() {
        return do_you_want_a_baby;
    }

    public void setDo_you_want_a_baby(String do_you_want_a_baby) {
        this.do_you_want_a_baby = do_you_want_a_baby;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getWhether_to_drink() {
        return whether_to_drink;
    }

    public void setWhether_to_drink(String whether_to_drink) {
        this.whether_to_drink = whether_to_drink;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getMarital_status() {
        return marital_status;
    }

    public void setMarital_status(String marital_status) {
        this.marital_status = marital_status;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getSmokingStatus() {
        return smokingStatus;
    }

    public void setSmokingStatus(String smokingStatus) {
        this.smokingStatus = smokingStatus;
    }
}
