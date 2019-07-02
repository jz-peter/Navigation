package io.turntotech.android.navigation.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Product {
    @PrimaryKey(autoGenerate = true)
    public int id;

    String productName;
    String productUrl;
    String productImageUrl;

    public Product (String productName, String productUrl, String productImageUrl) {
        this.productName = productName;
        this.productUrl = productUrl;
        this.productImageUrl = productImageUrl;
    }

    //Product setters and getters:
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
    }
}
