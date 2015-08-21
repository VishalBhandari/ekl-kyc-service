package com.flipkart.logistics.models;

/**
 * Created by vishal.bhandari on 19/08/15.
 */
public class Service {
    private Long id;
    private String service_type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }
}
