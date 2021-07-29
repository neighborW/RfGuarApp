package com.rifeng.p2p.http;

/**
 * Created by caixiangyu on 2018/5/16.
 */

public class RFException extends Exception {

    int code;
    public RFException(int code,String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
