package com.rifeng.p2p.entity;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class LookupCode {


    @Id
    private String item_code;
    private int sort_no;
    private String item_name;
    private int id;
    private String status;
    @Generated(hash = 1250453153)
    public LookupCode(String item_code, int sort_no, String item_name, int id,
            String status) {
        this.item_code = item_code;
        this.sort_no = sort_no;
        this.item_name = item_name;
        this.id = id;
        this.status = status;
    }
    @Generated(hash = 400110714)
    public LookupCode() {
    }
    public String getItem_code() {
        return this.item_code;
    }
    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }
    public int getSort_no() {
        return this.sort_no;
    }
    public void setSort_no(int sort_no) {
        this.sort_no = sort_no;
    }
    public String getItem_name() {
        return this.item_name;
    }
    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getStatus() {
        return this.status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
   

}
