package com.mtechyard.newpizzayum.api;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderInfoResponse {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("orderId")
    @Expose
    private String orderId;
    @SerializedName("orderFor")
    @Expose
    private String orderFor;
    @SerializedName("d_name")
    @Expose
    private String dName;
    @SerializedName("d_mobile")
    @Expose
    private String dMobile;
    @SerializedName("d_address")
    @Expose
    private String dAddress;
    @SerializedName("d_loaclity")
    @Expose
    private String dLoaclity;
    @SerializedName("isMapAvailable")
    @Expose
    private Boolean isMapAvailable;
    @SerializedName("status_light_color")
    @Expose
    private Integer statusLightColor;
    @SerializedName("status_massage")
    @Expose
    private String statusMassage;
    @SerializedName("paymentMode")
    @Expose
    private String paymentMode;
    @SerializedName("paymentTransactionId")
    @Expose
    private Object paymentTransactionId;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("item_count")
    @Expose
    private String itemCount;
    @SerializedName("sub_total")
    @Expose
    private String subTotal;
    @SerializedName("gst")
    @Expose
    private String gst;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("productList")
    @Expose
    private List<OrderInfoResponseProductList> productList;

    public String getResult() {
        return result;
    }

    public String getDate() {
        return date;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getOrderFor() {
        return orderFor;
    }

    public String getdName() {
        return dName;
    }

    public String getdMobile() {
        return dMobile;
    }

    public String getdAddress() {
        return dAddress;
    }

    public String getdLoaclity() {
        return dLoaclity;
    }

    public Boolean getMapAvailable() {
        return isMapAvailable;
    }

    public Integer getStatusLightColor() {
        return statusLightColor;
    }

    public String getStatusMassage() {
        return statusMassage;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public Object getPaymentTransactionId() {
        return paymentTransactionId;
    }

    public String getDiscount() {
        return discount;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getItemCount() {
        return itemCount;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public String getGst() {
        return gst;
    }

    public String getTotal() {
        return total;
    }

    public List<OrderInfoResponseProductList> getProductList() {
        return productList;
    }
}