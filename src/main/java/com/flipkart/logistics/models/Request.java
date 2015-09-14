package com.flipkart.logistics.models;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by vishal.bhandari on 24/08/15.
 */
public class Request {
     private long id;
     private ServiceRequest sr;
     private Service service;
     private Category category;
     private String expectedBy;
     private Merchant merchant;
     private Set<Document> document;
     private String status;
     private Customer customer;
     private long retryCount;
     private String requestReferenceId;
     private int active;

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getRequestReferenceId() {
        return requestReferenceId;
    }

    public void setRequestReferenceId(String requestReferenceId) {
        this.requestReferenceId = requestReferenceId;
    }

    public long getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(long retryCount) {
        this.retryCount = retryCount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ServiceRequest getSr() {
        return sr;
    }

    public void setSr(ServiceRequest sr) {
        this.sr = sr;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    public String getExpectedBy() {
        return expectedBy;
    }

    public void setExpectedBy(String expectedBy) {
        this.expectedBy = expectedBy;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public Set<Document> getDocument() {
        return document;
    }

    public void setDocument(Set<Document> document) {
        this.document = document;
    }

}
