package com.rifeng.p2p.entity;

import java.util.ArrayList;

/**
 * Created by caixiangyu on 2018/5/21.
 */

public class AccountInfo {

    private ArrayList<Agent> agent;

    private User user;
    private int loginCount;

    private String token;

    public ArrayList<Agent> getAgent() {
        return agent;
    }

    public void setAgent(ArrayList<Agent> agent) {
        agent = agent;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        user = user;
    }

    public int getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}

