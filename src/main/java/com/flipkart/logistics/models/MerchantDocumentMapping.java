package com.flipkart.logistics.models;

/**
 * Created by vishal.bhandari on 19/08/15.
 */
public class MerchantDocumentMapping {

    private Long id;
    private Long merchant_id;
    private Long document_id;
    private Long document_category_code;

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

    public Long getDocument_id() {
        return document_id;
    }

    public void setDocument_id(Long document_id) {
        this.document_id = document_id;
    }

    public Long getDocument_category_code() {
        return document_category_code;
    }

    public void setDocument_category_code(Long document_category_code) {
        this.document_category_code = document_category_code;
    }
}
