package com.rifeng.p2p.entity;

import java.io.Serializable;

/**
 * Created by caizhiming on 2017/4/13.
 */

public class TabItem implements Serializable{
    private String itemName;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
