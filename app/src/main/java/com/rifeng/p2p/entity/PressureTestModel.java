package com.rifeng.p2p.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Unique;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Entity
public class PressureTestModel {

    @Id
    private String tempTestId = "";
    private String testId = "";
    private String projectName = "";
    private String address = "";
    private String postCode = "";
    private String testTimeStr = "";
    private long testTime;
    private String company = "";
    private String pipeBrandAndType = "";
    private String testMethod = "";
    private String testMethodCode = "";
    private String ccEmail = "";

    //当前状态 0 空闲， 1 测试中， 2， fail 3. pass
    private String currentState = "0";


    private String deviceUniqueId = "";
    private String email = "";
    private String mobile = "";
    private String userId = "";
    //选择的组别
    private String groupId = "";
    private String testType = "";

    @Transient
    private ArrayList<PipeDiagramModel> pipeDiagramList = new ArrayList<PipeDiagramModel>();
    @Transient
    private ArrayList<String> pipeCodeList = new ArrayList<String>();

    //组别名称
    @Transient
    private List<Agreement> agreementList = new ArrayList<Agreement>();

    @Transient
    public List<Option> optionList = new ArrayList<Option>();

    @Transient
    public List<List<Option>> allOptionList = new ArrayList<List<Option>>();



    @Transient
    public List<PressureResultBean> resultList = new ArrayList<PressureResultBean>();

    public void setPipeDiagramList(ArrayList<PipeDiagramModel> pipeDiagramList) {
        this.pipeDiagramList = pipeDiagramList;
    }

    public void setPipeCodeList(ArrayList<String> pipeCodeList) {
        this.pipeCodeList = pipeCodeList;
    }

    public void setAgreementList(List<Agreement> agreementList) {
        this.agreementList = agreementList;
    }

    public void setOptionList(List<Option> optionList) {
        this.optionList = optionList;
    }

    public void setAllOptionList(List<List<Option>> allOptionList) {
        this.allOptionList = allOptionList;
    }

    public ArrayList<PipeDiagramModel> getPipeDiagramList() {
        return pipeDiagramList;
    }

    public ArrayList<String> getPipeCodeList() {
        return pipeCodeList;
    }

    public List<Agreement> getAgreementList() {
        return agreementList;
    }

    public List<Option> getOptionList() {
        return optionList;
    }

    public List<List<Option>> getAllOptionList() {
        return allOptionList;
    }



    @Generated(hash = 1650259442)
    public PressureTestModel(String tempTestId, String testId, String projectName,
            String address, String postCode, String testTimeStr, long testTime,
            String company, String pipeBrandAndType, String testMethod, String testMethodCode,
            String ccEmail, String currentState, String deviceUniqueId, String email,
            String mobile, String userId, String groupId, String testType) {
        this.tempTestId = tempTestId;
        this.testId = testId;
        this.projectName = projectName;
        this.address = address;
        this.postCode = postCode;
        this.testTimeStr = testTimeStr;
        this.testTime = testTime;
        this.company = company;
        this.pipeBrandAndType = pipeBrandAndType;
        this.testMethod = testMethod;
        this.testMethodCode = testMethodCode;
        this.ccEmail = ccEmail;
        this.currentState = currentState;
        this.deviceUniqueId = deviceUniqueId;
        this.email = email;
        this.mobile = mobile;
        this.userId = userId;
        this.groupId = groupId;
        this.testType = testType;
    }

    @Generated(hash = 337027208)
    public PressureTestModel() {
    }

    public String getTempTestId() {
        return this.tempTestId;
    }

    public void setTempTestId(String tempTestId) {
        this.tempTestId = tempTestId;
    }

    public String getTestId() {
        return this.testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return this.postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getTestTimeStr() {
        return this.testTimeStr;
    }

    public void setTestTimeStr(String testTimeStr) {
        this.testTimeStr = testTimeStr;
    }

    public long getTestTime() {
        return this.testTime;
    }

    public void setTestTime(long testTime) {
        this.testTime = testTime;
    }

    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPipeBrandAndType() {
        return this.pipeBrandAndType;
    }

    public void setPipeBrandAndType(String pipeBrandAndType) {
        this.pipeBrandAndType = pipeBrandAndType;
    }

    public String getTestMethod() {
        return this.testMethod;
    }

    public void setTestMethod(String testMethod) {
        this.testMethod = testMethod;
    }

    public String getCcEmail() {
        return this.ccEmail;
    }

    public void setCcEmail(String ccEmail) {
        this.ccEmail = ccEmail;
    }

    public String getDeviceUniqueId() {
        return this.deviceUniqueId;
    }

    public void setDeviceUniqueId(String deviceUniqueId) {
        this.deviceUniqueId = deviceUniqueId;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getCurrentState() {
        return this.currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }


    public String getTestMethodCode() {
        return testMethodCode;
    }

    public void setTestMethodCode(String testMethodCode) {
        this.testMethodCode = testMethodCode;
    }

    public String getTestType() {
        return this.testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }
}
