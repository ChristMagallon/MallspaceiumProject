package com.example.mallspaceium.getproducts;

public class gettersetter {
    String ProductName,ProductPrice,image;

    public gettersetter(){}

    public gettersetter(String productName, String productPrice, String image) {
        this.ProductName = productName;
        this.ProductPrice = productPrice;
        this.image = image;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
