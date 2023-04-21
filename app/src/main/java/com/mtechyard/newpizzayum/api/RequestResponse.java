package com.mtechyard.newpizzayum.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


   public class RequestResponse {
        @SerializedName("result")
        @Expose
        private String result;
        @SerializedName("response")
        @Expose
        private String response;
        @SerializedName("responseCode")
        @Expose
        private String responseCode;
        @SerializedName("responseMessage")
        @Expose
        private String responseMessage;

        @SerializedName("walletAmount")
        @Expose
        private String walletAmount;
        @SerializedName("userAvailable")
        @Expose
        private boolean userAvailable;
        @SerializedName("firebasePass")
        @Expose
        private String firebasePass;
        @SerializedName("customerID")
        @Expose
        private String customerID;

        @SerializedName("unReadMessageCount")
        @Expose
        private String unReadMessageCount;
        @SerializedName("unReadNotificationCount")
        @Expose
        private String unReadNotificationCount;
        @SerializedName("profileImageLink")
        @Expose
        private String profileImageLink;
        @SerializedName("accountStatus")
        @Expose
        private String accountStatus;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("orderId")
        @Expose
        private String orderId;
        @SerializedName("payAmount")
        @Expose
        private String payAmount;


        @SerializedName("localityCheck")
        @Expose
        private boolean localityCheck;

        @SerializedName("productId")
        @Expose
        private final int productId;
        @SerializedName("productName")
        @Expose
        private final String productName;
        @SerializedName("productImage")
        @Expose
        private final String productImage;

        @SerializedName("category")
        @Expose
        private final String category;
        @SerializedName("dPrice")
        @Expose
        private final Integer dPrice;
        @SerializedName("customizable")
        @Expose
        private final Boolean customizable;
        @SerializedName("sPrice")
        @Expose
        private final Integer sPrice;
        @SerializedName("mPrice")
        @Expose
        private final Integer mPrice;
        @SerializedName("lPrice")
        @Expose
        private final Integer lPrice;
        @SerializedName("productTax")
        @Expose
        private final Integer productTax;

        public RequestResponse(int productId,String productName, String productImage, String category, Integer dPrice, Boolean customizable, Integer sPrice, Integer mPrice, Integer lPrice, Integer productTax) {
             this.productId = productId;
             this.productName = productName;
             this.productImage = productImage;
             this.category = category;
             this.dPrice = dPrice;
             this.customizable = customizable;
             this.sPrice = sPrice;
             this.mPrice = mPrice;
             this.lPrice = lPrice;
             this.productTax = productTax;
        }



        // return data

        public String getResult(){
            return result;
        }
        public String getResponse(){
            return response;
        }
        public String getResponseCode(){
            return responseCode;
        }
        // for check user area in
        public boolean getLocalityCheckResponse(){
             return localityCheck;
        }

        public String getWalletAmount(){
            return walletAmount;
        }
        public String getResponseMessage(){
            return responseMessage;
        }
        public boolean getUserAvailable(){
            return userAvailable;
        }
        public String getFirebasePass(){
            return firebasePass;
        }
        public String getCustomerID(){
            return customerID;
        }
        public String getUnReadMessageCount(){
            return unReadMessageCount;
        }
        public String getUnReadNotificationCount(){
            return unReadNotificationCount;
        }
        public String getProfileImageLink(){
            return profileImageLink;
        }
        public String getAccountStatus(){
            return accountStatus;
        }
        public String getUserName(){
            return name;
        }

        public int getProductId() {
             return productId;
        }
        public String getProductName() {
             return productName;
        }
        public String getProductImage() {
             return productImage;
        }
        public String getCategory() {
             return category;
        }
        public Integer getDisplayPrice() {
             return dPrice;
        }
        public Boolean getCustomizable() {
             return customizable;
        }
        public Integer getsPrice() {
             return sPrice;
        }
        public Integer getmPrice() {
             return mPrice;
        }
        public Integer getlPrice() {
             return lPrice;
        }
        public Integer getProductTax() {
             return productTax;
        }

        public String getOrderId() {
             return orderId;
        }

        public String getPayAmount() {
             return payAmount;
        }
   }

