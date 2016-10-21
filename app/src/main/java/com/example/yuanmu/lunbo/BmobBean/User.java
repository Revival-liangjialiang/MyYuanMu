package com.example.yuanmu.lunbo.BmobBean;


import java.util.ArrayList;

import cn.bmob.v3.BmobUser;
public class User extends BmobUser {
    private ArrayList<String> fabulous = new ArrayList();
    private String conditionId;
    //独白
    private String monologue = "这家伙很懒，什么也没留下!";
    private String token;
    private String city;
    private String longitute;
    private String latitude;
    private String nickname;
    private String img;
    //相册
    private String[] album;
    //省份
    private String province = "未知";
    //区
    private String district = "未知";
    //手机识别码
    private String installationId = "未知";
    //月收入
    private String income = "未知";
    //工作地区
    private String working_area = "未知";
    //有没有小孩
    private String there_is_no_child = "未知";
    //是否想要小孩
    private String do_you_want_a_baby = "未知";
    //住房情况
    private String housing_situation = "未知";
    //买车情况
    private String buying_a_car = "未知";
    //职业
    private String occupation = "未知";
    //籍贯
    private String place_of_origin = "未知";
    //体重
    private String weight = "未知";
    // 体型
    private String shape = "未知";
    //是否抽烟
    private String smoking_status = "未知";
    // 是否喝酒
    private String whether_to_drink = "未知";
    // 星座
    private String constellation = "未知";
    //民族
    private String nation = "未知";
    //何时结婚
    private String when_to_get_married = "未知";
    //性别
    private String gender = "未知";
    //生日
    private String birthday = "未知";
    // 身高
    private String height = "未知";
    //学历
    private String education = "未知";
    //婚姻状况
    private String marital_status = "未知";
    //年龄
    private String age = "未知";
    public User(){
        fabulous.add(" ");
        album = new String [1];
        album[0] = "";
    }

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

    public String getHousing_situation() {
        return housing_situation;
    }

    public void setHousing_situation(String housing_situation) {
        this.housing_situation = housing_situation;
    }

    public String getBuying_a_car() {
        return buying_a_car;
    }

    public void setBuying_a_car(String buying_a_car) {
        this.buying_a_car = buying_a_car;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getPlace_of_origin() {
        return place_of_origin;
    }

    public void setPlace_of_origin(String place_of_origin) {
        this.place_of_origin = place_of_origin;
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

    public String getSmoking_status() {
        return smoking_status;
    }

    public void setSmoking_status(String smoking_status) {
        this.smoking_status = smoking_status;
    }

    public String getWhether_to_drink() {
        return whether_to_drink;
    }

    public void setWhether_to_drink(String whether_to_drink) {
        this.whether_to_drink = whether_to_drink;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getWhen_to_get_married() {
        return when_to_get_married;
    }

    public void setWhen_to_get_married(String when_to_get_married) {
        this.when_to_get_married = when_to_get_married;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLongitute() {
        return longitute;
    }

    public void setLongitute(String longitute) {
        this.longitute = longitute;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getInstallationId() {
        return installationId;
    }

    public void setInstallationId(String installationId) {
        this.installationId = installationId;
    }

    public String getMonologue() {
        return monologue;
    }

    public void setMonologue(String monologue) {
        this.monologue = monologue;
    }

    public String getConditionId() {
        return conditionId;
    }

    public void setConditionId(String conditionId) {
        this.conditionId = conditionId;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String[] getAlbum() {
        return album;
    }

    public void setAlbum(String[] album) {
        this.album = album;
    }
}
