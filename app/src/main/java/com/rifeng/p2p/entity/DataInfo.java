package com.rifeng.p2p.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by caixiangyu on 2018/5/16.
 */

public class DataInfo<T> {
    @SerializedName(value = "pressureRecord", alternate = {"pressureRecordChart", "InvitationCode","user"})
    T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
