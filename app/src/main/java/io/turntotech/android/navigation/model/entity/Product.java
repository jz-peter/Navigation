package io.turntotech.android.navigation.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Product {
    @PrimaryKey(autoGenerate = true)
    int id;

    String productName;
    String productUrl;
    String productImgUrl;
    String productPrice;
    String companyLogoUrl;
    int companyId;


    public Product(String productName, String productImgUrl,String productUrl) {
        this.productName = productName;
        this.productUrl = productUrl;
        this.productImgUrl = productImgUrl;
        this.productPrice = "0";
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

    public void setProductUrl(String productUrl) { this.productUrl = productUrl; }

    public String getProductImgUrl() {
        return productImgUrl;
    }

    public void setProductImgUrl(String productImgUrl) { this.productImgUrl = productImgUrl; }

    public String getProductPrice() { return productPrice; }

    public void setProductPrice(String productPrice) { this.productPrice = productPrice; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
}