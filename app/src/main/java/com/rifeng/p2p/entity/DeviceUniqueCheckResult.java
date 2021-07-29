package com.rifeng.p2p.entity;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

@Entity(indexes = {@Index(value = "deviceCode DESC,  userId DESC", unique = true)})
public class DeviceUniqueCheckResult {

    @Id(autoincrement = true)
    Long resultId;

    String deviceCode;

    String userId;

    String result;

    @Generated(hash = 871323561)
    public DeviceUniqueCheckResult(Long resultId, String deviceCode, String userId,
            String result) {
        this.resultId = resultId;
        this.deviceCode = deviceCode;
        this.userId = userId;
        this.result = result;
    }

    @Generated(hash = 1444534374)
    public DeviceUniqueCheckResult() {
    }

    public Long getResultId() {
        return this.resultId;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }

    public String getDeviceCode() {
        return this.deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }


}
