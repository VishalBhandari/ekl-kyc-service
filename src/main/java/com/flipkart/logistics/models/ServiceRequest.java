package com.flipkart.logistics.models;

import java.io.Serializable;

/**
 * Created by vishal.bhandari on 24/08/15.
 */
public class ServiceRequest {

    private long id;
    private long merchantRefId;
    private long requestRefId;
    private String customerJson;
    private String service;
    private String category;
    private String eta;
    private String docJson;


    public ServiceRequest()
    {

    }

    public ServiceRequest(long merchant_ref_id,String customer_json, String service_json, String category_json,
                          String eta_json, String doc_json) {

        this.merchantRefId = merchant_ref_id;
        this.customerJson = customer_json;
        this.service = service_json;
        this.category = category_json;
        this.eta = eta_json;
        this.docJson = doc_json;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public long getMerchantRefId() {
        return merchantRefId;
    }

    public void setMerchantRefId(long merchant_ref_id) {
        this.merchantRefId = merchant_ref_id;
    }


    public long getRequestRefId() {
        return requestRefId;
    }

    public void setRequestRefId(long request_ref_id) {
        this.requestRefId = request_ref_id;
    }

    public String getCustomerJson() {
        return customerJson;
    }

    public void setCustomerJson(String customer_json) {
        this.customerJson = customer_json;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getEta() {
        return eta;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }

    public String getDocJson() {
        return docJson;
    }

    public void setDocJson(String doc_json) {
        this.docJson = doc_json;
    }

}
