package com.fiveup.core.questionnaire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


public class ModifyPwdData {
    private String checkPass;
    private String newpass;
    private String oldpass;
    private String username;

    public ModifyPwdData(String checkPass, String newpass, String oldPass, String username) {
        this.checkPass = checkPass;
        this.newpass = newpass;
        this.oldpass = oldPass;
        this.username = username;
    }
    public ModifyPwdData() {}

    public String getCheckPass() {
        return checkPass;
    }

    public void setCheckPass(String checkPass) {
        this.checkPass = checkPass;
    }

    public String getNewpass() {
        return newpass;
    }

    public void setNewpass(String newpass) {
        this.newpass = newpass;
    }

    public String getOldPass() {
        return oldpass;
    }

    public void setOldPass(String oldPass) {
        this.oldpass = oldPass;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
