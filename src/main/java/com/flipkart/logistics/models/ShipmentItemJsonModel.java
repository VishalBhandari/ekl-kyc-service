package com.flipkart.logistics.models;

/**
 * Created by vishal.bhandari on 28/08/15.
 */
public class ShipmentItemJsonModel {
    public ShipmentItemJsonModel(String description, String type) {
        this.description = description;
        this.type = type;
    }

    private String description;
    private String type;
    private String instruction;




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

    /**
     * @return the instruction
     */
    public String getInstruction() {
        return instruction;
    }
    /**
     * @param instruction the instruction to set
     */
    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }


}
