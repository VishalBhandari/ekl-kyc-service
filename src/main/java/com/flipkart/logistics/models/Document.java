package com.flipkart.logistics.models;

/**
 * Created by vishal.bhandari on 19/08/15.
 */
public class Document {
    private Long id;
    private String doc_type;
    private String name;

    public String getDocType() {
        return doc_type;
    }

    public void setDocType(String doc_type) {
        this.doc_type = doc_type;
    }



    public Document()
    {

    }
    public Document(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
