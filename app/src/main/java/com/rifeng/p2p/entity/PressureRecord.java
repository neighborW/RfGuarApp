package com.rifeng.p2p.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caixiangyu on 2018/5/16.
 */

public class PressureRecord implements Serializable {

    /**
     * agency_id : 8
     * state : New South Wales
     * city : Lismore
     * project_address : bcdiuflanfssdnufie
     * test_date : 2018-05-15
     * memo : test01
     * test_result : pass
     * engineer_id : 2
     * test_id : 48
     * test_count : 3
     * longitude : null
     * latitude : null
     * company_name : siemens-aus
     * user_id : 163
     * e_mail : test003@163.com
     * plumber_name : test003-willia
     * mobile_number : 15219930001
     * group_id : 1
     * round_id : 1
     * point1 : 463.2
     * point2 : 463.2
     * point3 : 463.2
     * point4 : 463.2
     * point5 : 463.2
     * point6 : 463.2
     * point7 : 463.2
     * point8 : 463.2
     * point9 : 463.2
     * point10 : 463.2
     * point11 : 463.2
     * point12 : 463.2
     * point13 : 463.2
     * point14 : 463.2
     * point15 : 463.2
     * point16 : 463.2
     * point17 : 463.2
     * point18 : 463.2
     * point19 : 463.2
     * point20 : 463.2
     * point21 : 463.2
     * point22 : 463.2
     * point23 : 463.2
     * point24 : 463.2
     * point25 : 463.2
     * point26 : 463.2
     * point27 : 463.2
     * point28 : 463.2
     * point29 : 463.2
     * point30 : 463.2
     * point0 : 463.2
     * start_pressure : 943.32
     * end_pressure : 453.23
     * pressure_dec : 473.22
     */

    public String    agencyId;
    public String    attribute3;
    public String    city;
    public String    companyName;
    public String    createdBy;
    public String    createdTime;
    public String    email;
    public String    engineerId;
    public String    id;
    public String    latitude;
    public String    longitude;
    public String    memo;
    public String    mobileNumber;
    public String    plumberName;
    public String    postCode;
    public String    projectName;
    public String    projectAddress;
    public String    state;
    public String    testCount;
    public String    testDate;
    public String    testId;
    public String    testMethod;
    public String    testMethodCode;
    public String    testResult;
    public String    updatedBy;
    public String    updatedTime;
    public String    userId;
    private boolean isShowSubmit;


    //add
    private String groupId;
    List<PressureRecordChart> pressureRecordChart;




    public boolean isShowSubmit() {
        return isShowSubmit;
    }

    public void setShowSubmit(boolean showSubmit) {
        isShowSubmit = showSubmit;
    }



    public List<PressureRecordChart> getPressureRecordChart() {
        return pressureRecordChart;
    }

    public void setPressureRecordChart(List<PressureRecordChart> pressureRecordChart) {
        this.pressureRecordChart = pressureRecordChart;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public String getAttribute3() {
        return attribute3;
    }

    public String getCity() {
        return city;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public String getEmail() {
        return email;
    }

    public String getEngineerId() {
        return engineerId;
    }

    public String getId() {
        return id;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getMemo() {
        return memo;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getPlumberName() {
        return plumberName;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getProjectAddress() {
        return projectAddress;
    }

    public String getState() {
        return state;
    }

    public String getTestCount() {
        return testCount;
    }

    public String getTestDate() {
        return testDate;
    }

    public String getTestId() {
        return testId;
    }

    public String getTestMethod() {
        return testMethod;
    }

    public String getTestMethodCode() {
        return testMethodCode;
    }

    public String getTestResult() {
        return testResult;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public String getUserId() {
        return userId;
    }


    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public void setAttribute3(String attribute3) {
        this.attribute3 = attribute3;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEngineerId(String engineerId) {
        this.engineerId = engineerId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setPlumberName(String plumberName) {
        this.plumberName = plumberName;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setProjectAddress(String projectAddress) {
        this.projectAddress = projectAddress;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setTestCount(String testCount) {
        this.testCount = testCount;
    }

    public void setTestDate(String testDate) {
        this.testDate = testDate;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public void setTestMethod(String testMethod) {
        this.testMethod = testMethod;
    }

    public void setTestMethodCode(String testMethodCode) {
        this.testMethodCode = testMethodCode;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }


    @Override
    public String toString() {
        return "PressureRecord{" +
                "agencyId='" + agencyId + '\'' +
                ", attribute3='" + attribute3 + '\'' +
                ", city='" + city + '\'' +
                ", companyName='" + companyName + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", email='" + email + '\'' +
                ", engineerId='" + engineerId + '\'' +
                ", id='" + id + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", memo='" + memo + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", plumberName='" + plumberName + '\'' +
                ", postCode='" + postCode + '\'' +
                ", projectName='" + projectName + '\'' +
                ", projectAddress='" + projectAddress + '\'' +
                ", state='" + state + '\'' +
                ", testCount='" + testCount + '\'' +
                ", testDate='" + testDate + '\'' +
                ", testId='" + testId + '\'' +
                ", testMethod='" + testMethod + '\'' +
                ", testMethodCode='" + testMethodCode + '\'' +
                ", testResult='" + testResult + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedTime='" + updatedTime + '\'' +
                ", userId='" + userId + '\'' +
                ", isShowSubmit=" + isShowSubmit +
                ", groupId='" + groupId + '\'' +
                ", pressureRecordChart=" + pressureRecordChart +
                '}';
    }
}
