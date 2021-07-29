package com.rifeng.p2p.entity;

/**
 * 用户个人信息
 */
public class UserBean {

    private String country;
    private String state;
    private String city;
    private String plumberName;
    private String plumbingCompany;
    private String mobilePhone;
    private String email;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPlumberName() {
        return plumberName;
    }

    public void setPlumberName(String plumberName) {
        this.plumberName = plumberName;
    }

    public String getPlumbingCompany() {
        return plumbingCompany;
    }

    public void setPlumbingCompany(String plumbingCompany) {
        this.plumbingCompany = plumbingCompany;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
