package com.rifeng.p2p.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by keguihong on 2017/11/15.
 */

@Entity
public class BleDataBean {
    @Id
    private String clientId;

    private String card;

    private String phone;

    private int groupId;

    private float pressureStart;

    private float pressureEnd;

    private int totalTime;

    private String result;

    private Date lastUpdateDate;

    private Date createDate;

    private int times;//第几次测试

    public BleDataBean() {
    }


    @Keep
    public BleDataBean(String clientId, String card, String phone, int groupId, float pressureStart, float pressureEnd, int totalTime, String result, Date lastUpdateDate, Date createDate, int times) {
        this.clientId = clientId;
        this.card = card;
        this.phone = phone;
        this.groupId = groupId;
        this.pressureStart = pressureStart;
        this.pressureEnd = pressureEnd;
        this.totalTime = totalTime;
        this.result = result;
        this.lastUpdateDate = lastUpdateDate;
        this.createDate = createDate;
        this.times = times;
    }


    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public float getPressureStart() {
        return pressureStart;
    }

    public void setPressureStart(float pressureStart) {
        this.pressureStart = pressureStart;
    }

    public float getPressureEnd() {
        return pressureEnd;
    }

    public void setPressureEnd(float pressureEnd) {
        this.pressureEnd = pressureEnd;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }
}
