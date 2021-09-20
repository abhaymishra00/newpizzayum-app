package com.mtechyard.newpizzayum.project_rec;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.mtechyard.newpizzayum.home;

import java.util.ArrayList;
import java.util.List;


public class TinyDB extends Application {

    //Store Data
    @SuppressLint("StaticFieldLeak")
    public static Context myContext;


    public TinyDB(Context context) {
        myContext = context;
    }

    public void StoreString(String Tag, String Value) {

        SharedPreferences Store1 = myContext.getSharedPreferences("DB1", Context.MODE_PRIVATE);
        SharedPreferences.Editor Store = Store1.edit();
        Store.putString(Tag, Value);
        Store.apply();

    }

    public static void StoreInt(String Tag, int Value) {

        SharedPreferences Store1 = myContext.getSharedPreferences("DB1", Context.MODE_PRIVATE);
        SharedPreferences.Editor Store = Store1.edit();
        Store.putInt(Tag, Value);
        Store.apply();

    }

    public static void StoreFloat(String Tag, float Value) {

        SharedPreferences Store1 = myContext.getSharedPreferences("DB1", Context.MODE_PRIVATE);
        SharedPreferences.Editor Store = Store1.edit();
        Store.putFloat(Tag, Value);
        Store.apply();

    }

    public void StoreBoolean(String Tag, boolean Value) {

        SharedPreferences Store1 = myContext.getSharedPreferences("DB1", Context.MODE_PRIVATE);
        SharedPreferences.Editor Store = Store1.edit();
        Store.putBoolean(Tag, Value);
        Store.apply();
    }

    public static void StoreLong(String Tag, long Value) {

        SharedPreferences Store1 = myContext.getSharedPreferences("DB1", Context.MODE_PRIVATE);
        SharedPreferences.Editor Store = Store1.edit();
        Store.putLong(Tag, Value);
        Store.apply();

    }

    //Get Data
    public static int GetInt(String Tag, int DefValue) {

        SharedPreferences Store1 = myContext.getSharedPreferences("DB1", Context.MODE_PRIVATE);
        return Store1.getInt(Tag, DefValue);
    }

    public static String GetString(String Tag, String DefValue) {

        SharedPreferences Store1 = myContext.getSharedPreferences("DB1", Context.MODE_PRIVATE);
        return Store1.getString(Tag, DefValue);
    }

    public static float GetFloat(String Tag, float DefValue) {

        SharedPreferences Store1 = myContext.getSharedPreferences("DB1", Context.MODE_PRIVATE);
        return Store1.getFloat(Tag, DefValue);
    }

    public boolean GetBoolean(String Tag, boolean DefValue) {

        SharedPreferences Store1 = myContext.getSharedPreferences("DB1", Context.MODE_PRIVATE);
        return Store1.getBoolean(Tag, DefValue);
    }

    public static long GetLong(String Tag, long DefValue) {

        SharedPreferences Store1 = myContext.getSharedPreferences("DB1", Context.MODE_PRIVATE);
        return Store1.getLong(Tag, DefValue);
    }

    //Clare Data
    public void ClearDB1() {
        SharedPreferences Store1 = myContext.getSharedPreferences("DB1", Context.MODE_PRIVATE);
        SharedPreferences.Editor Store = Store1.edit();
        Store.clear();
        Store.apply();
    }


    public String getUserMobileNo() {
        SharedPreferences Store1 = myContext.getSharedPreferences("DB1", Context.MODE_PRIVATE);
        return Store1.getString("userMobileNo", " ");

    }

    public String getUserName() {
        SharedPreferences Store1 = myContext.getSharedPreferences("DB1", Context.MODE_PRIVATE);
        return Store1.getString("userName", " ");

    }

    public void saveUserMobileNo(String MobileNo) {
        StoreString("userMobileNo", MobileNo);
    }

    public void saveUserName(String Name) {
        StoreString("userName", Name);
    }

    public String getUserLocality() {
        return GetString("locality", "");
    }

    public void saveUserLocality(String Locality) {
        StoreString("locality", Locality);
    }

    public void setBucketItemCount(int Count) {
        StoreInt("userBucketCount", Count);
    }

    public int getBucketItemCount() {
        return GetInt("userBucketCount", 0);
    }

    public int getBucketTotal() {

        List<UserOrderList> orderList = getOrderList();

        int bucketTotal = 0;

        for (int i = 0; i < orderList.size(); i++) {
            UserOrderList d = orderList.get(i);
            if (d.getHaveToppings()){
                bucketTotal = bucketTotal + d.getRate() + d.getToppingRate() ;
            }else{
                bucketTotal = bucketTotal + d.getRate() ;
            }
        }
        return bucketTotal;
    }

    public int getBucketTax() {
        List<UserOrderList> orderList = getOrderList();

        int bucketTotalTax = 0;

        int productRate = 0;

        for (int i = 0; i < orderList.size(); i++) {
            UserOrderList d = orderList.get(i);
            productRate = productRate + d.getRate();
            bucketTotalTax = bucketTotalTax + (productRate * d.getTax() / 100);
        }
        return bucketTotalTax;
    }


    public void addInBucket(UserOrderList newOrderItem) {

        StoreInt("userBucketCount", getBucketItemCount() + 1);

        List<UserOrderList> o = getOrderList();
        o.add(newOrderItem);
        Gson gson = new Gson();
        String newOrderListString = gson.toJson(o, ArrayList.class);
        StoreString("UserOrderList", newOrderListString);

    }


    public void removeFromBucket(int position) {
        List<UserOrderList> list = getOrderList();

        StoreInt("userBucketCount", getBucketItemCount() - 1);
        home.changeBucketCountInUi();
        list.remove(position);
        Gson gson = new Gson();
        String newOrderListString = gson.toJson(list, ArrayList.class);
        StoreString("UserOrderList", newOrderListString);
    }

    public void addQuantity(int position) {

        List<UserOrderList> list = getOrderList();
        UserOrderList l = list.get(position);

        int newQty = l.getQty() + 1;
        int rate = l.getRate();
        int qty = l.getQty();

        int aRate = rate / qty;

        int currentRate = aRate * newQty;

        if (l.getHaveToppings()) {
            int ToppingRate = l.getToppingRate();
            int aToppingRate = ToppingRate / qty;
            int currentToppingRate = aToppingRate * newQty;

            l.setToppingRate(currentToppingRate);

        }

        l.setRate(currentRate);
        l.setQty(newQty);

        list.set(position, l);
        Gson gson = new Gson();
        String newOrderListString = gson.toJson(list, ArrayList.class);
        StoreString("UserOrderList", newOrderListString);

    }

    public void removeQuantity(int position) {

        List<UserOrderList> list = getOrderList();
        UserOrderList l = list.get(position);

        int newQty = l.getQty() - 1;
        if (newQty == 0) {
            removeFromBucket(position);
            return;
        }
        int rate = l.getRate();
        int qty = l.getQty();

        int aRate = rate / qty;

        int currentRate = aRate * newQty;

        int aaRate = currentRate - aRate;

        if (l.getHaveToppings()) {
            int ToppingRate = l.getToppingRate();
            int aToppingRate = ToppingRate / qty;
            int currentToppingRate = aToppingRate * newQty;
            int aaToppingRate = currentToppingRate - aRate;
            l.setToppingRate(currentToppingRate);
        }
        l.setRate(currentRate);
        l.setQty(newQty);
        list.set(position, l);
        Gson gson = new Gson();
        String newOrderListString = gson.toJson(list, ArrayList.class);
        StoreString("UserOrderList", newOrderListString);


    }

    public List<UserOrderList> getOrderList() {
        String orderListJsonString = GetString("UserOrderList", "");
        List<UserOrderList> oldOrderList = new ArrayList<>();
        Gson gson = new Gson();
        if (!orderListJsonString.isEmpty()) {

            UserOrderList[] oldUserOrderList = gson.fromJson(orderListJsonString, UserOrderList[].class);
            for (UserOrderList userOrderList : oldUserOrderList) {
                UserOrderList orderListItem;
                if (userOrderList.getHaveToppings()) {
                    orderListItem = new UserOrderList(
                            userOrderList.getProductName(),
                            userOrderList.getProductImage(),
                            userOrderList.getSize(),
                            userOrderList.getQty(),
                            userOrderList.getRate(),
                            userOrderList.getTax(),
                            userOrderList.getProductId(),
                            userOrderList.getToppingList(),
                            userOrderList.getToppingRate()
                    );
                } else {
                    orderListItem = new UserOrderList(
                            userOrderList.getProductName(),
                            userOrderList.getProductImage(),
                            userOrderList.getSize(),
                            userOrderList.getQty(),
                            userOrderList.getRate(),
                            userOrderList.getTax(),
                            userOrderList.getProductId()
                    );
                }

                oldOrderList.add(orderListItem);
            }
        }
        return oldOrderList;
    }

    public String getOrderListAsString() {
        Gson gson = new Gson();
        List<UserOrderList> oldOrderList = getOrderList();
        return gson.toJson(oldOrderList, ArrayList.class);
    }

    // ADDRESS FUNCTIONS
    public List<AddressList> getAddressList() {
        String addressListString = GetString("UserAddressList", "");
        List<AddressList> addressList = new ArrayList<>();
        Gson gson = new Gson();
        if (!addressListString.isEmpty()) {

            AddressList[] addressLists_1 = gson.fromJson(addressListString, AddressList[].class);
            for (AddressList aList : addressLists_1) {
                AddressList addressObject = new AddressList(
                        aList.getFullName(),
                        aList.getMobileNumber(),
                        aList.getPinCode(),
                        aList.getFullAddress(),
                        aList.getLocality(),
                        aList.getIsPrimary()
                );

                addressList.add(addressObject);
            }
        }


        return addressList;
    }

    public void addNewAddress(AddressList addressObject) {
        List<AddressList> addressList = getAddressList();
        Gson gson = new Gson();
        addressList.add(addressObject);
        String newAddressList = gson.toJson(addressList, ArrayList.class);
        StoreString("UserAddressList", newAddressList);
    }

    public int getDefaultAddressPosition() {
        String addressListString = GetString("UserAddressList", "");
        int returnPosition = -1;
        if (!addressListString.isEmpty()) {

            Gson gson = new Gson();
            AddressList[] addressLists_1 = gson.fromJson(addressListString, AddressList[].class);

            for (int i = 0; i < addressLists_1.length; i++) {

                if (addressLists_1[i].getIsPrimary()) {
                    returnPosition = i;
                }
            }
        }
        return returnPosition;
    }

    public AddressList getDefaultAddress() {
        List<AddressList> list = getAddressList();
        return list.get(getDefaultAddressPosition());
    }

    public void changeDefaultAddress(int position) {

        String addressListString = GetString("UserAddressList", "");
        List<AddressList> addressList = new ArrayList<>();
        Gson gson = new Gson();
        if (!addressListString.isEmpty()) {

            AddressList[] addressLists_1 = gson.fromJson(addressListString, AddressList[].class);

            for (int i = 0; i < addressLists_1.length; i++) {
                AddressList aO;
                if (i == position) {

                    aO = new AddressList(
                            addressLists_1[i].getFullName(),
                            addressLists_1[i].getMobileNumber(),
                            addressLists_1[i].getPinCode(),
                            addressLists_1[i].getFullAddress(),
                            addressLists_1[i].getLocality(),
                            true
                    );
                } else {
                    aO = new AddressList(
                            addressLists_1[i].getFullName(),
                            addressLists_1[i].getMobileNumber(),
                            addressLists_1[i].getPinCode(),
                            addressLists_1[i].getFullAddress(),
                            addressLists_1[i].getLocality(),
                            false
                    );
                }

                addressList.add(aO);

            }

            String newAddressList = gson.toJson(addressList, ArrayList.class);
            StoreString("UserAddressList", newAddressList);

        }

    }

    public void removeDefaultAddress() {
        String addressListString = GetString("UserAddressList", "");
        List<AddressList> addressList = new ArrayList<>();
        Gson gson = new Gson();
        if (!addressListString.isEmpty()) {

            AddressList[] addressLists_1 = gson.fromJson(addressListString, AddressList[].class);
            for (AddressList aList : addressLists_1) {
                AddressList addressObject = new AddressList(
                        aList.getFullName(),
                        aList.getMobileNumber(),
                        aList.getPinCode(),
                        aList.getFullAddress(),
                        aList.getLocality(),
                        false
                );

                addressList.add(addressObject);
            }
        }
        String newAddressList = gson.toJson(addressList, ArrayList.class);
        StoreString("UserAddressList", newAddressList);

    }


    public boolean isTaxApplied() {
        return GetBoolean("taxApplied",false);
    }

    public void removeOrderList() {
        StoreString("UserOrderList", "");
        StoreInt("userBucketCount", 0);
        home.changeBucketCountInUi();
    }
}





