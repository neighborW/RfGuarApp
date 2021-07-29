package com.rifeng.p2p.entity;

import androidx.annotation.NonNull;

import java.util.stream.IntStream;

public class NewsEntity implements CharSequence {

    private CharSequence title;
    private String currentDate;
    private String isRead;
    private String isTop;
    public NewsEntity() {

    }

    public NewsEntity(CharSequence title, String currentDate, String isRead, String isTop) {
        this.title = title;
        this.currentDate = currentDate;
        this.isRead = isRead;
        this.isTop = isTop;
    }

    public String getIsTop() {
        return isTop;
    }

    public void setIsTop(String isTop) {
        this.isTop = isTop;
    }

    public CharSequence getTitle() {
        return title;
    }
    public void setTitle(CharSequence title) {
        this.title = title;
    }
    public String getCurrentDate() {
        return currentDate;
    }
    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }
    public String getIsRead() {
        return isRead;
    }
    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    @Override
    public int length() {
        return 0;
    }

    @Override
    public char charAt(int i) {
        return 0;
    }

    @NonNull
    @Override
    public CharSequence subSequence(int i, int i1) {
        return null;
    }

    @NonNull
    @Override
    public IntStream chars() {
        return null;
    }

    @NonNull
    @Override
    public IntStream codePoints() {
        return null;
    }
}
