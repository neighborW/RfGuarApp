package com.rifeng.p2p.entity;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

@Entity(indexes = {@Index(value = "tempTestId DESC,  round DESC", unique = true)})
public class PressureResultBean {

    @Id(autoincrement = true)
    Long resultId;

    String tempTestId;

     //轮次
    String round;
    //57504705010106010104240430
    String result;

    //组别压力
    String groupPressure;

    //开始压力
    String startPressure;

    //结束压力
    String endPressure;

    //是否作为判断标准
    String decisionStandard;

    @Generated(hash = 1391692897)
    public PressureResultBean(Long resultId, String tempTestId, String round,
            String result, String groupPressure, String startPressure,
            String endPressure, String decisionStandard) {
        this.resultId = resultId;
        this.tempTestId = tempTestId;
        this.round = round;
        this.result = result;
        this.groupPressure = groupPressure;
        this.startPressure = startPressure;
        this.endPressure = endPressure;
        this.decisionStandard = decisionStandard;
    }

    @Generated(hash = 911231676)
    public PressureResultBean() {
    }

    public Long getResultId() {
        return this.resultId;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }

    public String getTempTestId() {
        return this.tempTestId;
    }

    public void setTempTestId(String tempTestId) {
        this.tempTestId = tempTestId;
    }

    public String getRound() {
        return this.round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStartPressure() {
        return this.startPressure;
    }

    public void setStartPressure(String startPressure) {
        this.startPressure = startPressure;
    }

    public String getEndPressure() {
        return this.endPressure;
    }

    public void setEndPressure(String endPressure) {
        this.endPressure = endPressure;
    }

    public String getDecisionStandard() {
        return this.decisionStandard;
    }

    public void setDecisionStandard(String decisionStandard) {
        this.decisionStandard = decisionStandard;
    }

    public void setGroupPressure(String groupPressure) {
        this.groupPressure = groupPressure;
    }

    public String getGroupPressure() {
        return groupPressure;
    }
}
