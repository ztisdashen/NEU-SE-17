package com.uestc.www.servlets;

/**
 * @创建人 贾敬哲
 * @创建时间 2018/10/11
 * @描述 电商平台客户管理模块
 */
public class Customer {
    //客户的属性
    private String customerID;
    private String customerName;
    private String gender;
    private String professional;
    private String levelOfEducation;
    private String address;

    //无参数构造器和全参数构造器

    public Customer() {
    }

    public Customer(String customerID, String customerName, String gender, String professional, String levelOfEducation, String address) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.gender = gender;
        this.professional = professional;
        this.levelOfEducation = levelOfEducation;
        this.address = address;
    }
    //set和get方法


    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    public String getLevelOfEducation() {
        return levelOfEducation;
    }

    public void setLevelOfEducation(String levelOfEducation) {
        this.levelOfEducation = levelOfEducation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
