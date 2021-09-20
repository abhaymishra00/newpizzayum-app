package com.mtechyard.newpizzayum.project_rec;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserOrderData {



    @SerializedName("orderName")
    @Expose
    private String orderName;

    @SerializedName("orderDate")
    @Expose
    private String orderDate;
    @SerializedName("colorCode")
    @Expose
    private Integer colorCode;
    @SerializedName("statusMessage")
    @Expose
    private String statusMessage;
    @SerializedName("orderRate")
    @Expose
    private Integer orderRate;
    @SerializedName("paymentMode")
    @Expose
    private String paymentMode;
    @SerializedName("itemCount")
    @Expose
    private Integer itemCount;
    @SerializedName("imageUrl1")
    @Expose
    private String imageUrl1;
    @SerializedName("imageUrl2")
    @Expose
    private String imageUrl2;
    @SerializedName("orderId")
    @Expose
    private String orderId;

    public UserOrderData(String orderId,
                         String orderName,
                         String orderDate,
                         Integer orderRate,
                         Integer colorCode,
                         String statusMessage,
                         String paymentMode,
                         Integer itemCount,
                         String imageUrl1,
                         String imageUrl2) {
        this.orderName = orderName;
        this.orderDate = orderDate;
        this.colorCode = colorCode;
        this.statusMessage = statusMessage;
        this.orderRate = orderRate;
        this.paymentMode = paymentMode;
        this.itemCount = itemCount;
        this.imageUrl1 = imageUrl1;
        this.imageUrl2 = imageUrl2;
        this.orderId = orderId;
    }


    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }
    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getColorCode() {
        return colorCode;
    }

    public void setColorCode(Integer statusCode) {
        this.colorCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Integer getOrderRate() {
        return orderRate;
    }

    public void setOrderRate(Integer orderRate) {
        this.orderRate = orderRate;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public String getImageUrl1() {
        return imageUrl1;
    }

    public void setImageUrl1(String imageUrl1) {
        this.imageUrl1 = imageUrl1;
    }

    public String getImageUrl2() {
        return imageUrl2;
    }

    public void setImageUrl2(String imageUrl2) {
        this.imageUrl2 = imageUrl2;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

}