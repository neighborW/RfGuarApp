package com.rifeng.p2p.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;
//联合主键
@Entity(indexes = {@Index(value = "pipeCode DESC,  tempTestId DESC", unique = true)})
public class PipeCodeModel {

    @Id(autoincrement = true)
    public Long id;

    String pipeCode;
    String tempTestId;
    @Generated(hash = 1899701099)
    public PipeCodeModel(Long id, String pipeCode, String tempTestId) {
        this.id = id;
        this.pipeCode = pipeCode;
        this.tempTestId = tempTestId;
    }
    @Generated(hash = 1086231026)
    public PipeCodeModel() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPipeCode() {
        return this.pipeCode;
    }
    public void setPipeCode(String pipeCode) {
        this.pipeCode = pipeCode;
    }
    public String getTempTestId() {
        return this.tempTestId;
    }
    public void setTempTestId(String tempTestId) {
        this.tempTestId = tempTestId;
    }

}
