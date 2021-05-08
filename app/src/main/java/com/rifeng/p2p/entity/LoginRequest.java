package com.rifeng.p2p.entity;

import java.io.Serializable;

public class LoginRequest implements Serializable {

    private String username;
    private String password;
    private String captcha;
    private String captcheKey;
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getCaptcheKey() {
        return captcheKey;
    }

    public void setCaptcheKey(String captcheKey) {
        this.captcheKey = captcheKey;
    }





}
