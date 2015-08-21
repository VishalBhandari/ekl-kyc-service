package com.flipkart.logistics.models;

/**
 * Created by vishal.bhandari on 21/08/15.
 */
public class MerchantCategoryMapping {
    private Long id;
    private Long merchantId;
    private Category category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
