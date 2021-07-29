package com.rifeng.p2p.entity;

import androidx.annotation.NonNull;

public class AddressItem {

    private String id;
    private String label;

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getParentId() {
        return parentId;
    }

    private String parentId;


    public void setId(String id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "AddressItem{" +
                "id='" + id + '\'' +
                ", label='" + label + '\'' +
                ", parentId='" + parentId + '\'' +
                '}';
    }
}
