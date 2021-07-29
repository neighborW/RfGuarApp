package com.rifeng.p2p.entity;

/**
 * Created by caixiangyu on 2018/5/16.
 */

public class RFResponse<T> {
   // @SerializedName(value = "data", alternate = {"pressureRecordChart"})
    private T data;
//    private String errCode;
//    private String errMsg;



    private Page page;
    private int code;
    private String msg;
    private boolean ok;

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isOk() {
        return ok;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

//    public String getErrCode() {
//        return errCode;
//    }
//
//    public void setErrCode(String errCode) {
//        this.errCode = errCode;
//    }
//
//    public String getErrMsg() {
//        return errMsg;
//    }
//
//    public void setErrMsg(String errMsg) {
//        this.errMsg = errMsg;
//    }
}
