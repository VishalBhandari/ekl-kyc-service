package com.flipkart.logistics.models;

import java.util.Set;

/**
 * Created by vishal.bhandari on 18/08/15.
 */

public class Merchant {

    public static final String NAME = "name";
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String country;
    private String pinCode;
    private String createDateTime;
    private String updateDateTime;
    private String merchantReferenceId;
    private int active;
    private Set<Category> category;
    private Set<Service>  service;

    public Merchant()
    {

    }

    public Merchant(String name, String phone, String email, String address1, String address2, String city, String state, String country, String pinCode, String createDateTime, String updateDateTime) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pinCode = pinCode;
        this.createDateTime = createDateTime;
        this.updateDateTime = updateDateTime;
    }

    public String getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(String updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public String getMerchantReferenceId() {
        return merchantReferenceId;
    }

    public void setMerchantReferenceId(String merchantReferenceId) {
        this.merchantReferenceId = merchantReferenceId;
    }

    public int getactive() {
        return active;
    }

    public void setactive(int active) {
        this.active = active;
    }

    public Set<Service> getService() {
        return service;
    }

    public void setService(Set<Service> service) {
        this.service = service;
    }

    public Set getCategory() {
        return category;
    }

    public void setCategory(Set category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }

}
