package com.flipkart.logistics.models;

/**
 * Created by vishal.bhandari on 01/09/15.
 */
public class Attribute {

    long id;
    String key;
    long value;

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
