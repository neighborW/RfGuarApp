package com.rifeng.p2p.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * 试压记录
 */
@Entity
public class BleRecords implements Serializable {

    public final static long serialVersionUID = 1L;
    @Id
    private String testId;
    private String groupId;
    private String testRoundTime;//每次试压时间
    private String testRoundStartPressure;//每次试压标准压力

    private String memo;
    private String result;
    private String testRoundCount;//试压次数
    private String groupName;//组名
    private Date lastUpdateDate;

    //新增字段
    private String test_method;
    private String post_code;
    private String test_method_code;
    @Generated(hash = 1624092726)
    public BleRecords(String testId, String groupId, String testRoundTime,
            String testRoundStartPressure, String memo, String result,
            String testRoundCount, String groupName, Date lastUpdateDate,
            String test_method, String post_code, String test_method_code) {
        this.testId = testId;
        this.groupId = groupId;
        this.testRoundTime = testRoundTime;
        this.testRoundStartPressure = testRoundStartPressure;
        this.memo = memo;
        this.result = result;
        this.testRoundCount = testRoundCount;
        this.groupName = groupName;
        this.lastUpdateDate = lastUpdateDate;
        this.test_method = test_method;
        this.post_code = post_code;
        this.test_method_code = test_method_code;
    }
    @Generated(hash = 2144903304)
    public BleRecords() {
    }
    public String getTestId() {
        return this.testId;
    }
    public void setTestId(String testId) {
        this.testId = testId;
    }
    public String getGroupId() {
        return this.groupId;
    }
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    public String getTestRoundTime() {
        return this.testRoundTime;
    }
    public void setTestRoundTime(String testRoundTime) {
        this.testRoundTime = testRoundTime;
    }
    public String getTestRoundStartPressure() {
        return this.testRoundStartPressure;
    }
    public void setTestRoundStartPressure(String testRoundStartPressure) {
        this.testRoundStartPressure = testRoundStartPressure;
    }
    public String getMemo() {
        return this.memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }
    public String getResult() {
        return this.result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public String getTestRoundCount() {
        return this.testRoundCount;
    }
    public void setTestRoundCount(String testRoundCount) {
        this.testRoundCount = testRoundCount;
    }
    public String getGroupName() {
        return this.groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public Date getLastUpdateDate() {
        return this.lastUpdateDate;
    }
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
    public String getTest_method() {
        return this.test_method;
    }
    public void setTest_method(String test_method) {
        this.test_method = test_method;
    }
    public String getPost_code() {
        return this.post_code;
    }
    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }
    public String getTest_method_code() {
        return this.test_method_code;
    }
    public void setTest_method_code(String test_method_code) {
        this.test_method_code = test_method_code;
    }




}