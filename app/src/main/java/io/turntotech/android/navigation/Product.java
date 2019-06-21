package io.turntotech.android.navigation;


public class Product {

    String [] productNameArray =
            {

            };

    String productName;
    String productUrl;
    String productImageUrl;

    public Product (String companyName, String companyStock, String companyLogoUrl) {
        this.productName = productName;
        this.productUrl = productUrl;
        this.productImageUrl = productImageUrl;

    }

    //Product setter and getters:
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
        this.productImageUrl = productImageUrl;
    }
}
