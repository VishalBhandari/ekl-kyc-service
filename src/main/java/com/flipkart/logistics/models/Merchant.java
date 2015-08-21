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
    private String create_datetime;
    private String update_datetime;
    private Set<MerchantCategoryMapping> merchantCategoryMappingSet;
    private Set<MerchantServiceMapping>  merchantServiceMappingSet;
    private Set<MerchantDocumentMapping> merchantDocumentMappingSet;

    public Merchant()
    {

    }

    public Merchant(String name, String phone, String email, String address1, String address2, String city, String state, String country, String pinCode, String create_datetime, String update_datetime, Set<MerchantCategoryMapping> merchantCategoryMappingSet, Set<MerchantServiceMapping> merchantservicemappingSet, Set<MerchantDocumentMapping> merchantdocumentmappingSet) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pinCode = pinCode;
        this.create_datetime = create_datetime;
        this.update_datetime = update_datetime;
        this.merchantCategoryMappingSet = merchantCategoryMappingSet;
        this.merchantServiceMappingSet = merchantservicemappingSet;
        this.merchantDocumentMappingSet = merchantdocumentmappingSet;
    }

    public Set<MerchantCategoryMapping> getMerchantCategoryMappingSet() {
        return merchantCategoryMappingSet;
    }

    public void setMerchantCategoryMappingSet(Set<MerchantCategoryMapping> merchant_category_mappingSet) {
        this.merchantCategoryMappingSet = merchant_category_mappingSet;
    }

    public Set<MerchantServiceMapping> getMerchant_service_mappingSet() {
        return merchantServiceMappingSet;
    }

    public void setMerchant_service_mappingSet(Set<MerchantServiceMapping> merchant_service_mappingSet) {
        this.merchantServiceMappingSet = merchant_service_mappingSet;
    }

    public Set<MerchantDocumentMapping> getMerchant_document_mappingSet() {
        return merchantDocumentMappingSet;
    }

    public void setMerchant_document_mappingSet(Set<MerchantDocumentMapping> merchant_document_mappingSet) {
        this.merchantDocumentMappingSet = merchant_document_mappingSet;
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

    public String getCreate_datetime() {
        return create_datetime;
    }

    public void setCreate_datetime(String create_datetime) {
        this.create_datetime = create_datetime;
    }

    public String getUpdate_datetime() {
        return update_datetime;
    }

    public void setUpdate_datetime(String update_datetime) {
        this.update_datetime = update_datetime;
    }


}
