package com.rifeng.p2p.entity;

public class ReportData {

    private Double reportNum;
    private Double reportFailNum;
    private Double passNum;
    private Double  rate;

    public Double getReportNum() {
        return reportNum;
    }

    public void setReportNum(Double reportNum) {
        this.reportNum = reportNum;
    }

    public Double getReportFailNum() {
        return reportFailNum;
    }

    public void setReportFailNum(Double reportFailNum) {
        this.reportFailNum = reportFailNum;
    }

    public Double getPassNum() {
        return passNum;
    }

    public void setPassNum(Double passNum) {
        this.passNum = passNum;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
