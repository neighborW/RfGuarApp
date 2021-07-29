package com.rifeng.p2p.entity;

import java.io.Serializable;

/**
 * Created by caixiangyu on 2018/5/22.
 */

public class InvitationCode implements Serializable {

    /**
     * invitation_id : 10002
     * invitation_code : 00002
     * status : Y
     * register_person : null
     * register_email : null
     * agency_id : 8
     */

    private int invitation_id;
    private String invitation_code;
    private String status;
    private String register_person;
    private String register_email;
    private int agency_id;
    private String org_id;

    public int getInvitation_id() {
        return invitation_id;
    }

    public void setInvitation_id(int invitation_id) {
        this.invitation_id = invitation_id;
    }

    public String getInvitation_code() {
        return invitation_code;
    }

    public void setInvitation_code(String invitation_code) {
        this.invitation_code = invitation_code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRegister_person() {
        return register_person;
    }

    public void setRegister_person(String register_person) {
        this.register_person = register_person;
    }

    public String getRegister_email() {
        return register_email;
    }

    public void setRegister_email(String register_email) {
        this.register_email = register_email;
    }

    public int getAgency_id() {
        return agency_id;
    }

    public void setAgency_id(int agency_id) {
        this.agency_id = agency_id;
    }

    public String getOrg_id() {
        return org_id;
    }

    public void setOrg_id(String org_id) {
        this.org_id = org_id;
    }
}
