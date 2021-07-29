package com.rifeng.p2p.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

import java.io.Serializable;
import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UserForeManBean {


    private static final long serialVersionUID = 7992227446249320445L;

    @Id(autoincrement = true)
    private Long fId;

    @Index(unique = true)//设置唯一性
    private String email;

    @Generated(hash = 126622687)
    public UserForeManBean(Long fId, String email) {
        this.fId = fId;
        this.email = email;
    }

    @Generated(hash = 215604750)
    public UserForeManBean() {
    }

    public Long getFId() {
        return this.fId;
    }

    public void setFId(Long fId) {
        this.fId = fId;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
