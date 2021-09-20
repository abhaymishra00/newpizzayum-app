package com.mtechyard.newpizzayum.server;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderInfoResponseProductList {

    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("topping")
    @Expose
    private String topping;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("rate")
    @Expose
    private String rate;

    public String getProductName() {
        return productName;
    }

    public String getSize() {
        return size;
    }

    public String getImage() {
        return image;
    }

    public String getTopping() {
        return topping;
    }

    public String getQty() {
        return qty;
    }

    public String getRate() {
        return rate;
    }
}