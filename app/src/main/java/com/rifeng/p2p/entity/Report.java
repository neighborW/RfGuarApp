package com.rifeng.p2p.entity;

import java.io.Serializable;

public class Report implements Serializable {

    private String currentDay;
    private String status;
    private String company;
    private String email;
    private String mobilePhone;
    private String projectName;
    private String testingMethod;

    private String reportType;




    public Report() {

    }

    public Report(String currentDay ,String status, String company, String email, String mobilePhone, String projectName, String testingMethod , String reportType) {

        this.currentDay = currentDay;
        this.status = status;
        this.company = company;
        this.email = email;
        this.mobilePhone = mobilePhone;
        this.projectName = projectName;
        this.testingMethod = testingMethod;
        this.reportType = reportType;
    }

    public String getReportType() {
        return reportType;
    }
    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getCurrentDay() {
        return currentDay;
    }
    public void setCurrentDay(String currentDay) {
        this.currentDay = currentDay;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getMobilePhone() {
        return mobilePhone;
    }
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getProjectName() {
        return projectName;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public String getTestingMethod() {
        return testingMethod;
    }
    public void setTestingMethod(String testingMethod) {
        this.testingMethod = testingMethod;
    }
}
