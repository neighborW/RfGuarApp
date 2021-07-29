package com.rifeng.p2p.entity;

import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.List;

public class NotifyResponse implements Serializable {


    //{"records":[],"total":0,"size":10,"current":1,"orders":[],"hitCount":false,"searchCount":true,"pages":0}
    private List<Notify> records;
    private int total;
    private  int size;
    private  int current;
    private List<JsonObject> orders;
    private  boolean hitCount;
    private boolean searchCount;
    private  int pages;
    private int code;

    public void setRecords(List<Notify> records) {
        this.records = records;
    }

    public List<Notify> getRecords() {
        return records;
    }
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public List<JsonObject> getOrders() {
        return orders;
    }

    public void setOrders(List<JsonObject> orders) {
        this.orders = orders;
    }

    public boolean isHitCount() {
        return hitCount;
    }

    public void setHitCount(boolean hitCount) {
        this.hitCount = hitCount;
    }

    public boolean isSearchCount() {
        return searchCount;
    }

    public void setSearchCount(boolean searchCount) {
        this.searchCount = searchCount;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    @Override
    public String toString() {
        return "NotifyResponse{" +
                "records=" + records +
                ", total=" + total +
                ", size=" + size +
                ", current=" + current +
                ", orders=" + orders +
                ", hitCount=" + hitCount +
                ", searchCount=" + searchCount +
                ", pages=" + pages +
                ", code=" + code +
                '}';
    }
}
