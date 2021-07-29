package com.rifeng.p2p.entity;

public class PressureParameterEntity {


    private String pressureData;
    private String remark;
    public PressureParameterEntity() {

    }
    public PressureParameterEntity(String pressureData, String remark) {
        this.pressureData = pressureData;
        this.remark = remark;
    }
    public String getPressureData() {
        return pressureData;
    }
    public void setPressureData(String pressureData) {
        this.pressureData = pressureData;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
