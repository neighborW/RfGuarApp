package com.rifeng.p2p.entity;

/**
 * Created by caixiangyu on 2018/5/16.
 */

public class Page {

    /**
     * begin : 3
     * length : 3
     * count : 21
     * totalPage : 7
     * currentPage : 2
     * isCount : true
     * isFirst : false
     * isLast : false
     * size : 3
     */

    private int begin;
    private int length;
    private int count;
    private int totalPage;
    private int currentPage;
    private boolean isCount;
    private boolean isFirst;
    private boolean isLast;
    private int size;

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public boolean isIsCount() {
        return isCount;
    }

    public void setIsCount(boolean isCount) {
        this.isCount = isCount;
    }

    public boolean isIsFirst() {
        return isFirst;
    }

    public void setIsFirst(boolean isFirst) {
        this.isFirst = isFirst;
    }

    public boolean isIsLast() {
        return isLast;
    }

    public void setIsLast(boolean isLast) {
        this.isLast = isLast;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
