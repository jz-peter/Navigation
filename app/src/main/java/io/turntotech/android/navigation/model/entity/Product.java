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
    String productPrice;
    String companyLogoUrl;


    public Product(String productName, String productImageUrl, String productPrice,String productUrl) {
        this.productName = productName;
        this.productUrl = productUrl;
        this.productImageUrl = productImageUrl;
        this.productPrice = productPrice;
        this.companyLogoUrl = companyLogoUrl;
    }

    //Product setters and getters:
    public String getCompanyLogoUrl() {return companyLogoUrl; }

    public void setCompanyLogoUrl(String companyLogoUrl) {this.companyLogoUrl = companyLogoUrl; }

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

    public String getProductPrice() { return productPrice; }

    public void setProductPrice(String productPrice) { this.productPrice = productPrice; }
}