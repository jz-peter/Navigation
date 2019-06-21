package io.turntotech.android.navigation;


public class Company {


    public static String[][] CompanyNameArray =
            {
                    {
                            "Apple",
                            "(APPL)",
                            "img-companyLogo_Apple.png"
                    },
                    {
                            "Google",
                            "(GOOG)",
                            "img-companyLogo_Google.png"
                    },
                    {
                            "Twitter",
                            "(TWTR)",
                            "img-companyLogo_Twitter.png"
                    },
                    {
                            "Tesla",
                            "(TSLA)",
                            "img-companyLogo_Tesla.png"

                    }
            };


    String companyName;
    String companyStock;
    String companyLogoUrl;


    public Company(String companyName, String companyStock, String companyLogoUrl) {
        this.companyName = companyName;
        this.companyStock = companyStock;
        this.companyLogoUrl = companyLogoUrl;

    }

    //Company setters and getters:
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

    public String getCompanyLogoUrl() {
        return companyLogoUrl;
    }

    public void setCompanyLogoUrl(String companyLogoUrl) {
        this.companyLogoUrl = companyLogoUrl;
    }


}

