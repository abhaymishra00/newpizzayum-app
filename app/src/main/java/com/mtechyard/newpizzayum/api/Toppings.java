package com.mtechyard.newpizzayum.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Toppings {
    @SerializedName("toppingPrice")
    @Expose
    private final Integer ToppingPrice;
    @SerializedName("toppingName")
    @Expose
    private final String ToppingName;

    public Toppings(Integer toppingPrice, String toppingName) {
        ToppingPrice = toppingPrice;
        ToppingName = toppingName;
    }

    public Integer getToppingPrice() {
        return ToppingPrice;
    }

    public String getToppingName() {
        return ToppingName;
    }
}
