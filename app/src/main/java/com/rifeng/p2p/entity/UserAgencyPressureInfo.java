package com.rifeng.p2p.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UserAgencyPressureInfo {

    @Id
    private String userId;

    private String pressureInfoJsonStr;

    @Generated(hash = 346896688)
    public UserAgencyPressureInfo(String userId, String pressureInfoJsonStr) {
        this.userId = userId;
        this.pressureInfoJsonStr = pressureInfoJsonStr;
    }

    @Generated(hash = 651798942)
    public UserAgencyPressureInfo() {
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPressureInfoJsonStr() {
        return this.pressureInfoJsonStr;
    }

    public void setPressureInfoJsonStr(String pressureInfoJsonStr) {
        this.pressureInfoJsonStr = pressureInfoJsonStr;
    }
}
