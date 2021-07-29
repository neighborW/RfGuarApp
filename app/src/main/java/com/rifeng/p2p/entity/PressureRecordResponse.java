package com.rifeng.p2p.entity;

import java.util.List;

public class PressureRecordResponse {

    //{"records":[],"total":0,"size":10,"current":1,"orders":[],"hitCount":false,"searchCount":true,"pages":0}
    private List<PressureRecord> records;
    private int total;
    private  int size;
    private  int current;
    private List<String> orders;
    private  boolean hitCount;
    private boolean searchCount;
    private  int pages;

    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public List<PressureRecord> getRecords() {
        return records;
    }

    public int getTotal() {
        return total;
    }

    public int getSize() {
        return size;
    }

    public int getCurrent() {
        return current;
    }

    public List<String> getOrders() {
        return orders;
    }

    public boolean isHitCount() {
        return hitCount;
    }

    public boolean isSearchCount() {
        return searchCount;
    }

    public int getPages() {
        return pages;
    }
    public void setRecords(List<PressureRecord> records) {
        this.records = records;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public void setOrders(List<String> orders) {
        this.orders = orders;
    }

    public void setHitCount(boolean hitCount) {
        this.hitCount = hitCount;
    }

    public void setSearchCount(boolean searchCount) {
        this.searchCount = searchCount;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    @Override
    public String toString() {
        return "PressureRecordResponse{" +
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
