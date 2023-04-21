package com.mtechyard.newpizzayum.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddressList {

    @SerializedName("full-name")
    @Expose
    private String fullName;
    @SerializedName("mobile-number")
    @Expose
    private String mobileNumber;
    @SerializedName("pinCode")
    @Expose
    private String pinCode;
    @SerializedName("full-address")
    @Expose
    private String fullAddress;
    @SerializedName("locality")
    @Expose
    private String locality;
    @SerializedName("isPrimary")
    @Expose
    private Boolean isPrimary;

    public AddressList(String fullName, String mobileNumber, String pinCode, String fullAddress, String locality, Boolean isPrimary) {
        this.fullName = fullName;
        this.mobileNumber = mobileNumber;
        this.pinCode = pinCode;
        this.fullAddress = fullAddress;
        this.locality = locality;
        this.isPrimary = isPrimary;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public Boolean getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(Boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

}