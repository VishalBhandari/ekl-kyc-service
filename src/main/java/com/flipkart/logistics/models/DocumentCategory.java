package com.flipkart.logistics.models;

/**
 * Created by vishal.bhandari on 19/08/15.
 */
public class DocumentCategory {
    private Long document_category_code;
    private String document_category;

    public Long getDocument_category_code() {
        return document_category_code;
    }

    public void setDocument_category_code(Long document_category_code) {
        this.document_category_code = document_category_code;
    }

    public String getDocument_category() {
        return document_category;
    }

    public void setDocument_category(String document_category) {
        this.document_category = document_category;
    }
}
