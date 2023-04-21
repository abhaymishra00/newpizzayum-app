package com.mtechyard.newpizzayum.api;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserOrderList {

    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("productImage")
    @Expose
    private String productImage;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("rate")
    @Expose
    private Integer rate;
    @SerializedName("tax")
    @Expose
    private Integer tax;
    @SerializedName("productId")
    @Expose
    private Integer productId;
    @SerializedName("haveToppings")
    @Expose
    private Boolean haveToppings;
    @SerializedName("toppingList")
    @Expose
    private List<Toppings> toppingList = null;
    @SerializedName("toppingRate")
    @Expose
    private Integer toppingRate;

    public UserOrderList(String productName,String productImage, String size, Integer qty, Integer rate, Integer tax, Integer productId, List<Toppings> toppingList, Integer toppingRate) {
        this.productName = productName;
        this.productImage = productImage;
        this.size = size;
        this.qty = qty;
        this.rate = rate;
        this.tax = tax;
        this.productId = productId;
        this.haveToppings = true;
        this.toppingList = toppingList;
        this.toppingRate = toppingRate;
    }


    public UserOrderList(String productName,String productImage, String size, Integer qty, Integer rate, Integer tax, Integer productId) {
        this.productName = productName;
        this.productImage = productImage;
        this.size = size;
        this.qty = qty;
        this.rate = rate;
        this.tax = tax;
        this.productId = productId;
        this.haveToppings = false;

    }

    public UserOrderList() {

    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductImage() {
        return productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Integer getTax() {
        return tax;
    }

    public void setTax(Integer tax) {
        this.tax = tax;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Boolean getHaveToppings() {
        return haveToppings;
    }

    public void setHaveToppings(Boolean haveToppings) {
        this.haveToppings = haveToppings;
    }

    public List<Toppings> getToppingList() {
        return toppingList;
    }

    public void setToppingList(List<Toppings> toppingList) {
        this.toppingList = toppingList;
    }

    public Integer getToppingRate() {
        return toppingRate;
    }

    public void setToppingRate(Integer toppingRate) {
        this.toppingRate = toppingRate;
    }

}