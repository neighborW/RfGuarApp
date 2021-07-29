package com.rifeng.p2p.entity;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

/**
 * 测试记录
 */
@Entity
public class HistoryRecordBean {

    @Id(autoincrement = true)
    private long id;
    private String plumberName;
    private String plumbingCompany;
    private String mobilePhone;
    private String email;
    private String projectAddress;
    private Date createDate;
    private double latitude;
    private double longitude;
    private String result;


    @Generated(hash = 1256849153)
    public HistoryRecordBean(long id, String plumberName, String plumbingCompany,
            String mobilePhone, String email, String projectAddress,
            Date createDate, double latitude, double longitude, String result) {
        this.id = id;
        this.plumberName = plumberName;
        this.plumbingCompany = plumbingCompany;
        this.mobilePhone = mobilePhone;
        this.email = email;
        this.projectAddress = projectAddress;
        this.createDate = createDate;
        this.latitude = latitude;
        this.longitude = longitude;
        this.result = result;
    }

    @Generated(hash = 1791356846)
    public HistoryRecordBean() {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getProjectAddress() {
        return projectAddress;
    }

    public void setProjectAddress(String projectAddress) {
        this.projectAddress = projectAddress;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
