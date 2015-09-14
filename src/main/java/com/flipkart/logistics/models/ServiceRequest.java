package com.flipkart.logistics.models;

import java.io.Serializable;

/**
 * Created by vishal.bhandari on 24/08/15.
 */
public class ServiceRequest {

    private long id;
    private String merchantReferenceId;
    private String category;
    private String request;

    public void setMerchantReferenceId(String merchantReferenceId) {
        this.merchantReferenceId = merchantReferenceId;
    }

    public  ServiceRequest()
    {

    }
    public ServiceRequest(String merchantReferenceId, String category, String request) {
        this.merchantReferenceId = merchantReferenceId;
        this.category = category;
        this.request = request;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMerchantReferenceId() {
        return merchantReferenceId;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}
