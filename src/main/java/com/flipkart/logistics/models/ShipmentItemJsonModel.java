package com.flipkart.logistics.models;

/**
 * Created by vishal.bhandari on 28/08/15.
 */
public class ShipmentItemJsonModel {

    private String description;
    private String type;
    private String instruction;

    public ShipmentItemJsonModel(String description, String type) {
        this.description = description;
        this.type = type;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }


    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }


}
