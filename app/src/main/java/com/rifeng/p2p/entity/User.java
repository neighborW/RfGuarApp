package com.rifeng.p2p.entity;

/**
 * Created by caixiangyu on 2018/5/21.
 */

public class User {

    /**
     * user_id : 164
     * user_code : test004
     * user_name : test004-willia
     * user_status : A
     * email : test004@163.com
     * mobile_number : 15389507564
     * engineer_id : 41
     * company_name : siemens-aus
     */

    private String address;
    private String avatarUrl;
    private String companyName;
    private String country;
    private String countryId;
    private String email;
    private String engineerId;
    private String firstname;
    private String lastName;
    private String loginAccount;
    private String loginSource;
    private String mobile;
    private String postcode;
    private String state;
    private String suburb;
    private String userId;
    private String stateId;
    private String suburbId;
    private String city;
    private String cityId;
    private String userName;
    private String companyId;
    private String agentId;
    private String userType;





    public void setAddress(String address){
        this.address = address;
    }
    public String getAddress(){
        return this.address;
    }
    public void setAvatarUrl(String avatarUrl){
        this.avatarUrl = avatarUrl;
    }
    public String getAvatarUrl(){
        return this.avatarUrl;
    }
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }
    public String getCompanyName(){
        return this.companyName;
    }
    public void setCountry(String country){
        this.country = country;
    }
    public String getCountry(){
        return this.country;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return this.email;
    }
    public void setEngineerId(String engineerId){
        this.engineerId = engineerId;
    }
    public String getEngineerId(){
        return this.engineerId;
    }
    public void setFirstname(String firstname){
        this.firstname = firstname;
    }
    public String getFirstname(){
        return this.firstname;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public String getLastName(){
        return this.lastName;
    }
    public void setLoginAccount(String loginAccount){
        this.loginAccount = loginAccount;
    }
    public String getLoginAccount(){
        return this.loginAccount;
    }
    public void setLoginSource(String loginSource){
        this.loginSource = loginSource;
    }
    public String getLoginSource(){
        return this.loginSource;
    }
    public void setMobile(String mobile){
        this.mobile = mobile;
    }
    public String getMobile(){
        return this.mobile;
    }
    public void setPostcode(String postcode){
        this.postcode = postcode;
    }
    public String getPostcode(){
        return this.postcode;
    }
    public void setState(String state){
        this.state = state;
    }
    public String getState(){
        return this.state;
    }
    public void setSuburb(String suburb){
        this.suburb = suburb;
    }
    public String getSuburb(){
        return this.suburb;
    }
    public void setUserId(String userId){
        this.userId = userId;
    }
    public String getUserId(){
        return this.userId;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }
    public String getUserName(){
        return this.userName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getAgentId() {
        return agentId;
    }

    public String getUserType() {
        return userType;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getCountryId() {
        return countryId;
    }

    public String getStateId() {
        return stateId;
    }

    public String getSuburbId() {
        return suburbId;
    }

    public String getCity() {
        return city;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public void setSuburbId(String suburbId) {
        this.suburbId = suburbId;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
}
