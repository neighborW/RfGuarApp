package com.rifeng.p2p.entity;

import java.io.Serializable;

/**
 * Created by caixiangyu on 2018/5/16.
 */

public class Agent implements Serializable{


    /**
     * agency_id : 7
     * agency_name : couta
     * user_id : 141
     * email : couta@google.com
     * agency_address : burnieTest
     * country_id : 10013
     * country : Australia
     * state_id : 10017
     * state : Tasmania
     * city_id : 10091
     * city : Burnie
     * create_date : 2018-05-10
     * creator : 41
     * update_date : 2018-05-15
     * updater : 10086
     * max_group : 2
     * max_round : 3
     */

    private int agency_id;
    private String agency_name;
    private int user_id;
    private String email;
    private String agency_address;
    private int country_id;
    private String country;
    private int state_id;
    private String state;
    private int city_id;
    private String city;
    private String create_date;
    private int creator;
    private String update_date;
    private int updater;
    private int max_group;
    private int max_round;
    private String testMethod;
    private String company_id;
    public int getAgency_id() {
        return agency_id;
    }

    public void setAgency_id(int agency_id) {
        this.agency_id = agency_id;
    }

    public String getAgency_name() {
        return agency_name;
    }

    public void setTestMethod(String testMethod) {
        this.testMethod = testMethod;
    }



    public void setAgency_name(String agency_name) {
        this.agency_name = agency_name;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAgency_address() {
        return agency_address;
    }

    public void setAgency_address(String agency_address) {
        this.agency_address = agency_address;
    }

    public int getCountry_id() {
        return country_id;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getState_id() {
        return state_id;
    }

    public void setState_id(int state_id) {
        this.state_id = state_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public String getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }

    public int getUpdater() {
        return updater;
    }

    public void setUpdater(int updater) {
        this.updater = updater;
    }

    public int getMax_group() {
        return max_group;
    }

    public void setMax_group(int max_group) {
        this.max_group = max_group;
    }

    public int getMax_round() {
        return max_round;
    }

    public void setMax_round(int max_round) {
        this.max_round = max_round;
    }

    public String getTestMethod() {
        return testMethod;
    }


    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }
}
