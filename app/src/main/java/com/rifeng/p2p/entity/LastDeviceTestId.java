package com.rifeng.p2p.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;


//保存设备最后测试ID
@Entity
public class LastDeviceTestId {

    @Id
    String deviceUniqueId;

    String tempTestId;

    @Generated(hash = 1544320540)
    public LastDeviceTestId(String deviceUniqueId, String tempTestId) {
        this.deviceUniqueId = deviceUniqueId;
        this.tempTestId = tempTestId;
    }

    @Generated(hash = 723884403)
    public LastDeviceTestId() {
    }

    public String getDeviceUniqueId() {
        return this.deviceUniqueId;
    }

    public void setDeviceUniqueId(String deviceUniqueId) {
        this.deviceUniqueId = deviceUniqueId;
    }

    public String getTempTestId() {
        return this.tempTestId;
    }

    public void setTempTestId(String tempTestId) {
        this.tempTestId = tempTestId;
    }

}

