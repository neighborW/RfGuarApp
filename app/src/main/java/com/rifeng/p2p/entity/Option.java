package com.rifeng.p2p.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by caixiangyu on 2018/5/31.
 */
public class Option {
    private String cannotpressureNum;
    private String companyId;
    private String createdBy;
    private String createdTime;
    private String decisionStandard;
    private String groupId;
    private String group;
    private String id;
    private String overpressure;
    private String pressureDecRange;
    private String pressureRiseRange;
    private String roundId;
    private String startPressure;
    private String testTime;
    private String testType;
    private String updatedBy;
    private String updatedTime;
    public void setCannotpressureNum(String cannotpressureNum){
        this.cannotpressureNum = cannotpressureNum;
    }
    public String getCannotpressureNum(){
        return this.cannotpressureNum;
    }
    public void setCompanyId(String companyId){
        this.companyId = companyId;
    }
    public String getCompanyId(){
        return this.companyId;
    }
    public void setCreatedBy(String createdBy){
        this.createdBy = createdBy;
    }
    public String getCreatedBy(){
        return this.createdBy;
    }
    public void setCreatedTime(String createdTime){
        this.createdTime = createdTime;
    }
    public String getCreatedTime(){
        return this.createdTime;
    }
    public void setDecisionStandard(String decisionStandard){
        this.decisionStandard = decisionStandard;
    }
    public String getDecisionStandard(){
        return this.decisionStandard;
    }
    public void setGroupId(String groupId){
        this.groupId = groupId;
    }
    public String getGroupId(){
        return this.groupId;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setOverpressure(String overpressure){
        this.overpressure = overpressure;
    }
    public String getOverpressure(){
        return this.overpressure;
    }
    public void setPressureDecRange(String pressureDecRange){
        this.pressureDecRange = pressureDecRange;
    }
    public String getPressureDecRange(){
        return this.pressureDecRange;
    }
    public void setPressureRiseRange(String pressureRiseRange){
        this.pressureRiseRange = pressureRiseRange;
    }
    public String getPressureRiseRange(){
        return this.pressureRiseRange;
    }
    public void setRoundId(String roundId){
        this.roundId = roundId;
    }
    public String getRoundId(){
        return this.roundId;
    }
    public void setStartPressure(String startPressure){
        this.startPressure = startPressure;
    }
    public String getStartPressure(){
        return this.startPressure;
    }
    public void setTestTime(String testTime){
        this.testTime = testTime;
    }
    public String getTestTime(){
        return this.testTime;
    }
    public void setTestType(String testType){
        this.testType = testType;
    }
    public String getTestType(){
        return this.testType;
    }
    public void setUpdatedBy(String updatedBy){
        this.updatedBy = updatedBy;
    }
    public String getUpdatedBy(){
        return this.updatedBy;
    }
    public void setUpdatedTime(String updatedTime){
        this.updatedTime = updatedTime;
    }
    public String getUpdatedTime(){
        return this.updatedTime;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroup() {
        return group;
    }

    /**
     * Instantiate the instance using the passed jsonObject to set the properties values
     */
    public Option(JSONObject jsonObject){
        if(jsonObject == null){
            return;
        }
        cannotpressureNum = jsonObject.optString("cannotpressureNum");
        companyId = jsonObject.optString("companyId");
        createdBy = jsonObject.optString("createdBy");
        createdTime = jsonObject.optString("createdTime");
        decisionStandard = jsonObject.optString("decisionStandard");
        groupId = jsonObject.optString("groupId");
        group = jsonObject.optString("group");
        id = jsonObject.optString("id");
        overpressure = jsonObject.optString("overpressure");
        pressureDecRange = jsonObject.optString("pressureDecRange");
        pressureRiseRange = jsonObject.optString("pressureRiseRange");
        roundId = jsonObject.optString("roundId");
        startPressure = jsonObject.optString("startPressure");
        testTime = jsonObject.optString("testTime");
        testType = jsonObject.optString("testType");
        updatedBy = jsonObject.optString("updatedBy");
        updatedTime = jsonObject.optString("updatedTime");
    }

    /**
     * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
     */
    public JSONObject toJsonObject()
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cannotpressureNum", cannotpressureNum);
            jsonObject.put("companyId", companyId);
            jsonObject.put("createdBy", createdBy);
            jsonObject.put("createdTime", createdTime);
            jsonObject.put("decisionStandard", decisionStandard);
            jsonObject.put("groupId", groupId);
            jsonObject.put("id", id);
            jsonObject.put("overpressure", overpressure);
            jsonObject.put("pressureDecRange", pressureDecRange);
            jsonObject.put("pressureRiseRange", pressureRiseRange);
            jsonObject.put("roundId", roundId);
            jsonObject.put("startPressure", startPressure);
            jsonObject.put("testTime", testTime);
            jsonObject.put("testType", testType);
            jsonObject.put("updatedBy", updatedBy);
            jsonObject.put("updatedTime", updatedTime);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonObject;
    }
}
