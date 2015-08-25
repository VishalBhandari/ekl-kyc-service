package com.flipkart.logistics.models;

/**
 * Created by vishal.bhandari on 24/08/15.
 */
public class ServiceRequest {

    private long id;
    private long merchantRefId;
    private long requestRefId;
    private String customerJson;
    private String serviceJson;
    private String categoryJson;
    private String etaJson;
    private String docJson;


    public ServiceRequest()
    {

    }

    public ServiceRequest(long merchant_ref_id,String customer_json, String service_json, String category_json,
                          String eta_json, String doc_json) {

        this.merchantRefId = merchant_ref_id;
        this.customerJson = customer_json;
        this.serviceJson = service_json;
        this.categoryJson = category_json;
        this.etaJson = eta_json;
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

    public String getServiceJson() {
        return serviceJson;
    }

    public void setServiceJson(String service_json) {
        this.serviceJson = service_json;
    }

    public String getCategoryJson() {
        return categoryJson;
    }

    public void setCategoryJson(String category_json) {
        this.categoryJson = category_json;
    }

    public String getEtaJson() {
        return etaJson;
    }

    public void setEtaJson(String eta_json) {
        this.etaJson = eta_json;
    }

    public String getDocJson() {
        return docJson;
    }

    public void setDocJson(String doc_json) {
        this.docJson = doc_json;
    }

}
