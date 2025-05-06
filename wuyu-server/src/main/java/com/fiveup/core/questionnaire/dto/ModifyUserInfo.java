package com.fiveup.core.questionnaire.dto;

public class ModifyUserInfo {
    private String phone;
    private String nickname;
    private int gender;
    private String username;
    public ModifyUserInfo(String phone, String nickname, int gender,String username) {
        this.phone = phone;
        this.nickname = nickname;
        this.gender = gender;
        this.username = username;
    }
    public ModifyUserInfo() {}

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if (gender.equals("male")){
            this.gender = 1;
        }else{
            this.gender = 0;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
