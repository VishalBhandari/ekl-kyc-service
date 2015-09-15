package com.flipkart.logistics.models;

import java.util.Date;

/**
 * Created by vishal.bhandari on 28/08/15.
 */
public class ShipmentFormDetail {

    private Long id;
    private String shipmentId;
    private String name;
    private String value;
    private Date createdAt;
    private Date updatedAt;
    private Long shipmentItemSeqId;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getShipmentId() {
        return shipmentId;
    }
    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public Date getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getShipmentItemSeqId() {
        return shipmentItemSeqId;
    }
    public void setShipmentItemSeqId(Long shipmentItemSeqId) {
        this.shipmentItemSeqId = shipmentItemSeqId;
    }
    @Override
    public String toString() {
        return "ShipmentFormDetail [id=" + id + ", shipmentId=" + shipmentId + ", name=" + name + ", value=" + value
                + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", shipmentItemSeqId=" + shipmentItemSeqId
                + "]";
    }
}
