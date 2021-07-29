package com.rifeng.p2p.entity;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caixiangyu on 2018/5/17.
 */

public class PressureRecordChart {

    /**
     * agency_id : 8
     * engineer_id : 2
     * test_id : 1
     * group_id : 1
     * record : [{"round": 1,"history": [{"point": "0s","pressure": 950}, {"point": "12s","pressure": 955}, {"point": "24s","pressure": 948}, {"point": "36s","pressure": null}, {"point": "48s","pressure": 945}, {"point": "60s","pressure": 940}, {"point": "72s","pressure": 942}, {"point": "84s","pressure": 940}, {"point": "96s","pressure": 940}, {"point": "108s","pressure": 945}, {"point": "120s","pressure": 945}]},{"round": 2,"history": [{"point": "0s","pressure": 545},{"point": "2s","pressure": 540},{"point": "4s","pressure": 538},{"point": "6s","pressure": 535},{"point": "8s","pressure": null},{"point": "10s","pressure": 520},{"point": "12s","pressure": 510},{"point": "14s","pressure": 509},{"point": "14s","pressure": 510},{"point": "16s","pressure": 507},{"point": "18s","pressure": 505},{"point": "20s","pressure": 500}]}]
     */

    private String agencyId;
    private String engineerId;
    private String testId;
    private int groupId;
    private String record;

    private List<PressureChartDetails> charts;

    public List<PressureChartDetails> getCharts() {
        return charts;
    }

    public void setCharts(List<PressureChartDetails> charts) {
        this.charts = charts;
    }

    public void parseDetails() {
        Gson gson = new Gson();
//        record="[{\"round\":1,\"decision_standard\":\"Y\",\"start_pressure\":800,\"history\":[{\"point\":\"0s\",\"pressure\":872},{\"point\":\"3s\",\"pressure\":872},{\"point\":\"10s\",\"pressure\":872},{\"point\":\"18s\",\"pressure\":872},{\"point\":\"25s\",\"pressure\":872},{\"point\":\"32s\",\"pressure\":872},{\"point\":\"39s\",\"pressure\":872},{\"point\":\"47s\",\"pressure\":872},{\"point\":\"55s\",\"pressure\":872},{\"point\":\"60s\",\"pressure\":872}]},{\"round\":2,\"decision_standard\":\"Y\",\"start_pressure\":800,\"history\":[{\"point\":\"0s\",\"pressure\":872},{\"point\":\"10s\",\"pressure\":872},{\"point\":\"21s\",\"pressure\":872},{\"point\":\"33s\",\"pressure\":872},{\"point\":\"45s\",\"pressure\":872},{\"point\":\"57s\",\"pressure\":872},{\"point\":\"68s\",\"pressure\":872},{\"point\":\"80s\",\"pressure\":872},{\"point\":\"93s\",\"pressure\":872},{\"point\":\"100s\",\"pressure\":872}]},{\"round\":3,\"decision_standard\":\"N\",\"start_pressure\":800,\"history\":[{\"point\":\"0s\",\"pressure\":872},{\"point\":\"6s\",\"pressure\":872},{\"point\":\"11s\",\"pressure\":872},{\"point\":\"12s\",\"pressure\":872},{\"point\":\"13s\",\"pressure\":872},{\"point\":\"14s\",\"pressure\":872},{\"point\":\"18s\",\"pressure\":872},{\"point\":\"19s\",\"pressure\":872},{\"point\":\"20s\",\"pressure\":872}]},{\"round\":4,\"decision_standard\":\"N\",\"start_pressure\":800,\"history\":[{\"point\":\"0s\",\"pressure\":872},{\"point\":\"6s\",\"pressure\":872},{\"point\":\"11s\",\"pressure\":870},{\"point\":\"12s\",\"pressure\":870},{\"point\":\"13s\",\"pressure\":872},{\"point\":\"14s\",\"pressure\":872},{\"point\":\"18s\",\"pressure\":872},{\"point\":\"19s\",\"pressure\":872},{\"point\":\"20s\",\"pressure\":872}]},{\"round\":5,\"decision_standard\":\"Y\",\"start_pressure\":800,\"history\":[{\"point\":\"0s\",\"pressure\":872},{\"point\":\"8s\",\"pressure\":872},{\"point\":\"14s\",\"pressure\":872},{\"point\":\"20s\",\"pressure\":872},{\"point\":\"27s\",\"pressure\":872},{\"point\":\"34s\",\"pressure\":872},{\"point\":\"41s\",\"pressure\":872},{\"point\":\"48s\",\"pressure\":872},{\"point\":\"55s\",\"pressure\":872},{\"point\":\"60s\",\"pressure\":872}]}]";
        charts = gson.fromJson(record, new TypeToken<List<PressureChartDetails>>() {
        }.getType());
    }

    public List<PressureChartDetails> getDecisionStandardYesChart(){
        List<PressureChartDetails> details=new ArrayList<>();
        if(charts!=null){
            for(PressureChartDetails details1:charts){
                if("Y".equalsIgnoreCase(details1.getDecision_standard())){
                    details.add(details1);
                }
            }
        }
        return details;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public void setEngineerId(String engineerId) {
        this.engineerId = engineerId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public String getEngineerId() {
        return engineerId;
    }

    public String getTestId() {
        return testId;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        Log.e("cxy","setRecord");
        this.record = record;
    }
}
