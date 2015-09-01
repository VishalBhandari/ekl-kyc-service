package com.flipkart.logistics.models;

/**
 * Created by vishal.bhandari on 28/08/15.
 */
public class ShipmentAttributeJsonModel {

    private String shipmentId;
    private String name;
    private String value;

    public ShipmentAttributeJsonModel(String name, String value) {
        this.name = name;
        this.value = value;
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

}
