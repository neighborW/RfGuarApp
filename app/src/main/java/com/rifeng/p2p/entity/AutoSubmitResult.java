package com.rifeng.p2p.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by caixiangyu on 2018/6/20.
 */
@Entity
public class AutoSubmitResult {
    @Id(autoincrement = true)
    private Long id;
    private String url;
    private String body;
    @Generated(hash = 2084542258)
    public AutoSubmitResult(Long id, String url, String body) {
        this.id = id;
        this.url = url;
        this.body = body;
    }
    @Generated(hash = 1705712230)
    public AutoSubmitResult() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getBody() {
        return this.body;
    }
    public void setBody(String body) {
        this.body = body;
    }
}
