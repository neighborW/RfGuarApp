package com.rifeng.p2p.entity;

import com.google.gson.annotations.Expose;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by caixiangyu on 2018/6/5.
 */

@Entity
public class Point {

    @Override
    public String toString() {
        return "Point{" +
                "id='" + id + '\'' +
                ", orderNum=" + orderNum +
                ", groupId=" + groupId +
                ", roundId=" + roundId +
                ", testId=" + testId +
                ", startPressure=" + startPressure +
                ", point='" + point + '\'' +
                ", pressure=" + pressure +
                '}';
    }

    public String toJson() {
        return "[\"point\":" + point + "\"pressure\"" + pressure + "]";
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNum() {
        return this.orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getRoundId() {
        return this.roundId;
    }

    public void setRoundId(String roundId) {
        this.roundId = roundId;
    }

    public String getTestId() {
        return this.testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getStartPressure() {
        return this.startPressure;
    }

    public void setStartPressure(String startPressure) {
        this.startPressure = startPressure;
    }

    public String getPoint() {
        return this.point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getPressure() {
        return this.pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    /**
     * point : 96s
     * pressure : 940
     * id:testId_groupId_roundId_orderNum
     */
    @Expose(serialize = false)
    @Id
    private String id;
    @Expose(serialize = false)
    private String orderNum;
    private String groupId;
    private String roundId;
    private String testId;
    private String startPressure;
    private String point;
    private String pressure;
    @Generated(hash = 1091429235)
    public Point(String id, String orderNum, String groupId, String roundId,
            String testId, String startPressure, String point, String pressure) {
        this.id = id;
        this.orderNum = orderNum;
        this.groupId = groupId;
        this.roundId = roundId;
        this.testId = testId;
        this.startPressure = startPressure;
        this.point = point;
        this.pressure = pressure;
    }

    @Generated(hash = 1977038299)
    public Point() {
    }



    

}
