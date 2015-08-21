package com.flipkart.logistics.models;

/**
 * Created by vishal.bhandari on 19/08/15.
 */
public class MerchantServiceMapping {
    private Long id;
    private Long merchant_id;
    private Long service_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(Long merchant_id) {
        this.merchant_id = merchant_id;
    }

    public Long getService_id() {
        return service_id;
    }

    public void setService_id(Long service_id) {
        this.service_id = service_id;
    }
}
