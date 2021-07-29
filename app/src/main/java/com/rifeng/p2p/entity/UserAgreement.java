package com.rifeng.p2p.entity;

import org.json.JSONException;
import org.json.JSONObject;

public class UserAgreement {

    private String context;
    private String createdBy;
    private String createdTime;
    private String id;
    private String status;
    private String title;
    private String type;
    private String updatedBy;
    private String updatedTime;

    public void setContext(String context) {
        this.context = context;
    }

    public String getContext() {
        return this.context;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedTime() {
        return this.createdTime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getUpdatedTime() {
        return this.updatedTime;
    }


    /**
     * Instantiate the instance using the passed jsonObject to set the properties values
     */
    public UserAgreement(JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        }
        context = jsonObject.optString("context");
        createdBy = jsonObject.optString("createdBy");
        createdTime = jsonObject.optString("createdTime");
        id = jsonObject.optString("id");
        status = jsonObject.optString("status");
        title = jsonObject.optString("title");
        type = jsonObject.optString("type");
        updatedBy = jsonObject.optString("updatedBy");
        updatedTime = jsonObject.optString("updatedTime");
    }

    /**
     * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
     */
    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("context", context);
            jsonObject.put("createdBy", createdBy);
            jsonObject.put("createdTime", createdTime);
            jsonObject.put("id", id);
            jsonObject.put("status", status);
            jsonObject.put("title", title);
            jsonObject.put("type", type);
            jsonObject.put("updatedBy", updatedBy);
            jsonObject.put("updatedTime", updatedTime);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonObject;
    }

}