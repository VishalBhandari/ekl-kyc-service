package com.flipkart.logistics.models;

/**
 * Created by vishal.bhandari on 19/08/15.
 */
public class Document {

    private Long id;
    private String docType;
    private String docName;


    public Document()
    {

    }
    public Document(String docType, String docName) {

        this.docType = docType;
        this.docName = docName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }
}
