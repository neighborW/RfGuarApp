package com.rifeng.p2p.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caixiangyu on 2018/5/16.
 */

public class AgentInfo implements Serializable {
    Agent agent;
    List<Agreement> agreements;
    List<List<Option>> options;


    public List<List<Option>> getOptions() {
        return options;
    }

    public void setOptions(List<List<Option>> options) {
        this.options = options;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public List<Agreement> getAgreements() {
        return agreements;
    }

    public void setAgreements(List<Agreement> agreements) {
        this.agreements = agreements;
    }
}
