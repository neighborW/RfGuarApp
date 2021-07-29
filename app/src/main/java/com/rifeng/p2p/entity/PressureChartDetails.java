package com.rifeng.p2p.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by caixiangyu on 2018/5/29.
 */

public class PressureChartDetails {


    /**
     * round : 1
     * history : [{"point":"0s","pressure":950},{"point":"12s","pressure":955},{"point":"24s","pressure":948},{"point":"36s","pressure":null},{"point":"48s","pressure":945},{"point":"60s","pressure":940},{"point":"72s","pressure":942},{"point":"84s","pressure":940},{"point":"96s","pressure":940},{"point":"108s","pressure":945},{"point":"120s","pressure":945}]
     * <p>
     * id:testid_groupId_roundId
     */
    private String id;

    private String testId;

    private long groupId;


    private int round;
    @SerializedName(value = "start_pressure")
    private Integer startPressure;
    private String decision_standard;

    private List<Point> history;

    public String getDecision_standard() {
        return decision_standard;
    }

    public void setDecision_standard(String decision_standard) {
        this.decision_standard = decision_standard;
    }

    public Integer getStartPressure() {
        return startPressure;
    }

    public void setStartPressure(Integer startPressure) {
        this.startPressure = startPressure;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getTestId() {
        return testId;
    }

    public long getGroupId() {
        return this.groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public int getRound() {
        return this.round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public List<Point> getHistory() {
        return history;
    }

    public void setHistory(List<Point> history) {
        this.history = history;
    }
}

