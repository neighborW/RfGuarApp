package com.rifeng.p2p.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by caixiangyu on 2018/5/16.
 */

public class Agreement implements Serializable {

    private String agreement;
    private String companyDeviceOptions;
    private String companyId;
    private String createdBy;
    private String createdTime;
    private String groupName;
    private String id;
    private String testType;
    private String updatedBy;
    private String updatedTime;


    private String remark;

    public void setAgreement(String agreement){
        this.agreement = agreement;
    }
    public String getAgreement(){
        return this.agreement;
    }
    public void setCompanyDeviceOptions(String companyDeviceOptions){
        this.companyDeviceOptions = companyDeviceOptions;
    }
    public String getCompanyDeviceOptions(){
        return this.companyDeviceOptions;
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
    public void setGroupName(String groupName){
        this.groupName = groupName;
    }
    public String getGroupName(){
        return this.groupName;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
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

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    /**
     * Instantiate the instance using the passed jsonObject to set the properties values
     */
    public Agreement(JSONObject jsonObject){
        if(jsonObject == null){
            return;
        }
        agreement = jsonObject.optString("agreement");
        companyDeviceOptions = jsonObject.optString("companyDeviceOptions");
        companyId = jsonObject.optString("companyId");
        createdBy = jsonObject.optString("createdBy");
        createdTime = jsonObject.optString("createdTime");
        groupName = jsonObject.optString("groupName");
        id = jsonObject.optString("id");
        testType = jsonObject.optString("testType");
        updatedBy = jsonObject.optString("updatedBy");
        updatedTime = jsonObject.optString("updatedTime");
        remark = jsonObject.optString("remark");
    }

    /**
     * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
     */
    public JSONObject toJsonObject()
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("agreement", agreement);
            jsonObject.put("companyDeviceOptions", companyDeviceOptions);
            jsonObject.put("companyId", companyId);
            jsonObject.put("createdBy", createdBy);
            jsonObject.put("createdTime", createdTime);
            jsonObject.put("groupName", groupName);
            jsonObject.put("id", id);
            jsonObject.put("testType", testType);
            jsonObject.put("updatedBy", updatedBy);
            jsonObject.put("updatedTime", updatedTime);
            jsonObject.put("remark", remark);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonObject;
    }
}
