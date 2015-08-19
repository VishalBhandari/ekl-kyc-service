package com.flipkart.logistics.models;

/**
 * Created by vishal.bhandari on 18/08/15.
 */
public class MerchantDetails {

    String name;
    int id;
    int age;

    public int getId() {
        return id;
    }
    public int getAge() {
        return age;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
