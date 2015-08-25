package com.flipkart.logistics.models;

import java.util.Set;

/**
 * Created by vishal.bhandari on 24/08/15.
 */
public class Request {
    private long id;
    private ServiceRequest sr;
    private Service service;
    private Category category;
    private String eta;
    private Merchant merchant;
    private Set<Document> document;

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

    public String getEta() {
        return eta;
    }

    public void setEta(String eta) {
        this.eta = eta;
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
