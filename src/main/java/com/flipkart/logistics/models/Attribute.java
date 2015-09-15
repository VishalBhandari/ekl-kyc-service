package com.flipkart.logistics.models;

/**
 * Created by vishal.bhandari on 01/09/15.
 */
public class Attribute {

    long id;
    String name;
    String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
