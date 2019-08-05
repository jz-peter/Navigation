package io.turntotech.android.navigation.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Company {
    @PrimaryKey(autoGenerate = true)
    int id;

    String companyName;
    String companyStock;
    String companyLogoUrl;
    String stockPrice;


    public Company(String companyName, String companyStock,String companyLogoUrl) {
        this.companyName = companyName;
        this.companyStock = companyStock;
        this.companyLogoUrl = companyLogoUrl;
        this.stockPrice = "0";
    }

    //Company setters and getters:
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyStock() {
        return companyStock;
    }

    public void setCompanyStock(String companyStock) {
        this.companyStock = companyStock;
    }

    public String getCompanyLogoUrl() { return companyLogoUrl;}

    public void setCompanyLogoUrl(String companyLogoUrl) {
        this.companyLogoUrl = companyLogoUrl;
    }

    public String getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(String stockPrice) {
        this.stockPrice = stockPrice;
    }
}

