package com.rifeng.p2p.entity;

import java.io.Serializable;
import java.util.Date;

public class Notify implements Serializable {



    public String id;
    private String title;
    private String startTime;
    private String isRead;
    private String content;
    private String status;
    private String isTop;
    public Long    createdBy;
    public String    createdTime;
    public Long    updatedBy;
    public String    updatedTime;

    public String updated;


    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getIsTop() {
        return isTop;
    }

    public void setIsTop(String isTop) {
        this.isTop = isTop;
    }
    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public String getIsRead() {
        return isRead;
    }
    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Notify{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", startTime='" + startTime + '\'' +
                ", isRead='" + isRead + '\'' +
                ", content='" + content + '\'' +
                ", status='" + status + '\'' +
                ", createdBy=" + createdBy +
                ", createdTime='" + createdTime + '\'' +
                ", updatedBy=" + updatedBy +
                ", updatedTime='" + updatedTime + '\'' +
                '}';
    }
}
