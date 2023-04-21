package com.mtechyard.newpizzayum;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.mtechyard.newpizzayum.api.AddressList;
import com.mtechyard.newpizzayum.app.EditComponents;
import com.mtechyard.newpizzayum.app.GlobalFunctions;
import com.mtechyard.newpizzayum.app.MyDialog;
import com.mtechyard.newpizzayum.api.RequestResponse;
import com.mtechyard.newpizzayum.api.Url;
import com.mtechyard.newpizzayum.app_src.AppDB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderDetailsActivity extends AppCompatActivity {

    TextView itemCount, subTotal, gst, discount, deliveryCharge, gTotal;
    @SuppressLint("StaticFieldLeak")
    private static AppDB appDB;
    private int gDiscount;
    String iCount, sTotal, Gst, Discount, dCharge, total;
    private String orderFor;
    private String selectedRestaurant;

    private MyDialog myD;
    private String OrderId;
    private String payingPayment;

    private  int paymentOption;

    private  BottomSheetDialog bsd;


    @SuppressLint("StaticFieldLeak")
    private static Activity thisActivity;
    private String discountCoupon;
    final int UPI_PAYMENT = 0;

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        
        //INIT
        appDB = new AppDB(this);
        gDiscount = 0;
        Discount = "0";
        thisActivity = this;
        discountCoupon = "noCoupon";
        selectedRestaurant = GlobalFunctions.restaurant1;

        myD = new MyDialog(this);

        ImageView bBtn = findViewById(R.id.backButton);
        bBtn.setOnClickListener(v -> {
            finish();
        });

        if (getIntent().getStringExtra("orderId")!=null){
            OrderId = getIntent().getStringExtra("orderId");
            payingPayment = getIntent().getStringExtra("payingPayment");
            showPaymentOption();

        }else{

        iCount = String.valueOf(appDB.getBucketItemCount());
        sTotal = String.valueOf(appDB.getBucketTotal());
        Gst = String.valueOf(appDB.getBucketTax());
        total = String.valueOf((appDB.getBucketTotal() + appDB.getBucketTax() - gDiscount));


        itemCount = findViewById(R.id.itemId20);
        subTotal = findViewById(R.id.itemId21);
        gst = findViewById(R.id.itemId23);
        discount = findViewById(R.id.itemId24);
        deliveryCharge = findViewById(R.id.itemId25);
        gTotal = findViewById(R.id.orderTotal);

        Button b = findViewById(R.id.changeAddress);
        b.setOnClickListener(v -> {
            if (b.getText().equals("Add")) {
                addNewAddress(v);
            } else {
                Intent intent = new Intent(this, AddressActivity.class);
                intent.putExtra("requestCode", 1);
                startActivityForResult(intent, 2);
            }
        });


        // DO NOT REMOVE THIS CODE FROM HERE
        findViewById(R.id.addDiscountText).setOnClickListener(v -> {
            Intent intent = new Intent(this, SalesActivity.class);
            intent.putExtra("totalAmount", appDB.getBucketTotal());
            startActivityForResult(intent, 1);
        });

        //SETTING PAYMENT INFORMATION ON SCREEN
        setAmounts();

        TextView deliveryText, dineInText, pickUpText;
        LinearLayout deliveryLayout, dineInLayout, pickUpLayout, toppingParentLayout, sizeParentLayout;

        orderFor = "delivery";

        deliveryText = findViewById(R.id.dText);
        dineInText = findViewById(R.id.diText);
        pickUpText = findViewById(R.id.pText);
        deliveryLayout = findViewById(R.id.dLayout);
        dineInLayout = findViewById(R.id.diLayout);
        pickUpLayout = findViewById(R.id.pLayout);


        deliveryText.setOnClickListener(v -> {
            setOrderFor("delivery", deliveryText, deliveryLayout);
        });
        dineInText.setOnClickListener(v -> {
            setOrderFor("dine in", dineInText, dineInLayout);
        });
        pickUpText.setOnClickListener(v -> {
            setOrderFor("pick up", pickUpText, pickUpLayout);
        });

        deliveryLayout.setOnClickListener(v -> {
            setOrderFor("delivery", deliveryText, deliveryLayout);
        });
        dineInLayout.setOnClickListener(v -> {
            setOrderFor("dine in", dineInText, dineInLayout);
        });
        pickUpLayout.setOnClickListener(v -> {
            setOrderFor("pick up", pickUpText, pickUpLayout);
        });

        showAddress();


        findViewById(R.id.orderPlaceButton).setOnClickListener(v -> {

            myD.showLoadingDialog();
            if (!orderFor.isEmpty()) {
                switch (orderFor) {
                    case "delivery":
                        if (appDB.getDefaultAddressPosition() != -1) {
                            checkOrderAndCreateRequest();
                        } else {
                            myD.dismissLoadingDialog(100);
                            Toast.makeText(this, "Please select or add a address for delivery.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case "dine in":
                    case "pick up":

                        if (!selectedRestaurant.isEmpty()) {
                            checkOrderAndCreateRequest();
                        } else {
                            myD.dismissLoadingDialog(100);
                            Toast.makeText(this, "Please select or add a address for delivery.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            } else {
                myD.dismissLoadingDialog(100);
            }
        });


        //RESTAURANT SELECTION

        LinearLayout restaurant_01, restaurant_02;

        restaurant_01 = findViewById(R.id.restaurant_01);
        restaurant_02 = findViewById(R.id.restaurant_02);

        restaurant_02.setOnClickListener(v -> {
            selectedRestaurant = GlobalFunctions.restaurant2;
            restaurant_01.setBackground(this.getResources().getDrawable(R.drawable.round_corner_layout));
            restaurant_02.setBackground(this.getResources().getDrawable(R.drawable.round_corner_layout_with_main_color_stoke));
        });

        restaurant_01.setOnClickListener(v -> {
            selectedRestaurant = GlobalFunctions.restaurant1;
            restaurant_02.setBackground(this.getResources().getDrawable(R.drawable.round_corner_layout));
            restaurant_01.setBackground(this.getResources().getDrawable(R.drawable.round_corner_layout_with_main_color_stoke));
        });
        }


    }

    private void checkOrderAndCreateRequest() {


        AddressList userAddress = appDB.getDefaultAddress();

        StringRequest createOrderRequest = new StringRequest(Request.Method.POST, Url.CREATE_ORDER, response -> {
            if (GlobalFunctions.isJSONValid(response)){
                Gson gson = new Gson();
                RequestResponse requestResponse = gson.fromJson(response, RequestResponse.class);
                if (requestResponse.getResult().equals("success")) {
                    //payUsingUpi("New Pizza Num","9044982994@okbizaxis","Pay for order: 20120" ,"10");

                    appDB.removeOrderList();
                    OrderId = requestResponse.getOrderId();
                    payingPayment = requestResponse.getPayAmount();

                    showPaymentOption();


                } else {
                    myD.dismissLoadingDialog(100);
                    Toast.makeText(this, "Some thing going wrong. please try in some time.", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Some internal issue occurred", Toast.LENGTH_SHORT).show();
            }
            myD.dismissLoadingDialog(100);

        }, error -> {
            myD.dismissLoadingDialog(100);
            Toast.makeText(this, "Something wrong with this error " + error.getMessage(), Toast.LENGTH_SHORT).show();
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                if (orderFor.equals("delivery")) {
                    params.put("fullAddress", userAddress.getFullAddress());
                    params.put("locality", userAddress.getLocality());
                    params.put("uName", userAddress.getFullName());
                    params.put("uMobile", userAddress.getMobileNumber());
                }

                params.put("orderFor", orderFor.toLowerCase());
                params.put("orderFrom", "app");
                params.put("orderProductList", appDB.getOrderListAsString());
                params.put("userPrimaryMobile", appDB.getUserMobileNo());
                params.put("userPrimaryName", appDB.getUserName());
                if (!discountCoupon.equals("noCoupon")) {
                    params.put("discountApplied", "yes");
                    params.put("discountCoupon", discountCoupon);
                } else {
                    params.put("discountApplied", "no");
                }
                if (appDB.isTaxApplied()) {
                    params.put("taxApplied", "yes");
                } else {
                    params.put("taxApplied", "no");
                }
                params.put("restaurantId", selectedRestaurant);

                return params;
            }

        };

//        Toast.makeText(this, db.getOrderListAsString(), Toast.LENGTH_SHORT).show();
//        Log.e("weeoe",db.getOrderListAsString());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(createOrderRequest);

    }

    @SuppressLint({"ResourceAsColor", "UseCompatLoadingForDrawables"})
    private void setOrderFor(String clickFor, TextView textView, LinearLayout linearLayout) {

        switch (clickFor) {
            case "delivery":
                EditComponents.setLinearLayoutVisibility(this, R.id.address_layout, View.VISIBLE);
                EditComponents.setLinearLayoutVisibility(this, R.id.restaurantLayout, View.GONE);
                showAddress();
                break;
            case "dine in":
            case "pick up":
                selectedRestaurant = GlobalFunctions.restaurant1;
                EditComponents.setLinearLayoutVisibility(this, R.id.restaurantLayout, View.VISIBLE);
                EditComponents.setLinearLayoutVisibility(this, R.id.address_layout, View.GONE);
                break;
        }

        TextView ts = findViewById(R.id.dText);
        LinearLayout ls = findViewById(R.id.dLayout);
        switch (orderFor) {
            case "delivery":
                ts = findViewById(R.id.dText);
                ls = findViewById(R.id.dLayout);
                break;
            case "dine in":
                ts = findViewById(R.id.diText);
                ls = findViewById(R.id.diLayout);
                break;
            case "pick up":
                ts = findViewById(R.id.pText);
                ls = findViewById(R.id.pLayout);
                break;
        }

        orderFor = clickFor;
        ts.setTextColor(this.getResources().getColor(R.color.light_gray));
        ls.setBackground(this.getResources().getDrawable(R.drawable.round_corner_layout));
        textView.setTextColor(this.getResources().getColor(R.color.white));
        linearLayout.setBackground(this.getResources().getDrawable(R.drawable.round_corner_layout_with_main));

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                gDiscount = data.getIntExtra("result", 0);
                if (gDiscount > 0) {
                    discountCoupon = data.getStringExtra("backResultCoupon");
                } else {
                    Discount = String.valueOf(gDiscount);
                    setAmounts();
                }
            }
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(OrderDetailsActivity.this, "Activity Closed", Toast.LENGTH_SHORT).show();
            }
        }


        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                showAddress();
            }
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(OrderDetailsActivity.this, "Activity Closed", Toast.LENGTH_SHORT).show();
            }

        }

        if (requestCode == -202 && data != null) {
            Toast.makeText(this, data.getStringExtra("nativeSdkForMerchantMessage") + data.getStringExtra("response"), Toast.LENGTH_SHORT).show();
        }


        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        //Log.e("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        //Log.e("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    //when user simply back without payment
                    //Log.e("UPI", "onActivityResult: " + "Return data is null");
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }


    @SuppressLint("SetTextI18n")
    private void setAmounts() {
        total = String.valueOf((appDB.getBucketTotal() + appDB.getBucketTax() - gDiscount));
        itemCount.setText(iCount);
        subTotal.setText("₹ " + sTotal);
        gst.setText("₹ " + Gst);
        discount.setText("₹ " + Discount);
        deliveryCharge.setText("₹ 0");
        gTotal.setText("₹" + total);
    }


    private void showAddress() {
        EditComponents ed = new EditComponents(this);

        if (appDB.getDefaultAddressPosition() != -1) {

            AddressList address = appDB.getDefaultAddress();

            if (appDB.getAddressList().size() == 1) {
                ed.setButtonVisibility(R.id.changeAddress, View.VISIBLE);
                ed.setButtonText(R.id.changeAddress, "Add");
            }
            if (appDB.getAddressList().size() >= 2) {
                ed.setButtonVisibility(R.id.changeAddress, View.VISIBLE);
                ed.setButtonText(R.id.changeAddress, "Change Address");
            }
            ed.setLinearLayoutVisibility(R.id.addressView, View.VISIBLE);
            ed.setLinearLayoutVisibility(R.id.noAddressView, View.GONE);
            ed.setTextViewText(R.id.addressViewName, address.getFullName());
            ed.setTextViewText(R.id.addressViewMobile, address.getMobileNumber());
            ed.setTextViewText(R.id.addressViewAddress, address.getFullAddress());

        } else {
            if (appDB.getAddressList().size() == 1) {
                appDB.changeDefaultAddress(0);
                showAddress();
            } else {
                ed.setLinearLayoutVisibility(R.id.addressView, View.GONE);
                ed.setLinearLayoutVisibility(R.id.noAddressView, View.VISIBLE);
                ed.setButtonVisibility(R.id.changeAddress, View.GONE);
            }

        }


    }

    public void addNewAddress(View view) {
        Intent intent = new Intent(this, UserDetailsActivity.class);
        intent.putExtra("requestCode", 2);
        startActivityForResult(intent, 2);
    }


    void payUsingUpi(String name, String upiId, String note, String amount) {
        //Log.e("main ", "name "+name +"--up--"+upiId+"--"+ note+"--"+amount);
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                //.appendQueryParameter("mc", "")
                //.appendQueryParameter("tid", "02125412")
                //.appendQueryParameter("tr", "25584584")
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                //.appendQueryParameter("refUrl", "blueapp")
                .build();
        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);
        // will always show a dialog to user to choose an app
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");
        // check if intent resolves
        if (null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(OrderDetailsActivity.this, "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show();
        }
    }


    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(OrderDetailsActivity.this)) {
            String str = data.get(0);
            //Log.e("UPIPAY", "upiPaymentDataOperation: " + str);
            String paymentCancel = "";
            if (str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";

            String[] response = str.split("&");
            for (String s : response) {
                String[] equalStr = s.split("=");
                if (equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    } else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                        //txtRef = equalStr[2];
                        //Toast.makeText(this, txtRef, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }
            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(OrderDetailsActivity.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                setOrderPayed(approvalRefNo);
                //Log.e("UPI", "payment successfull: " + approvalRefNo);
            } else if ("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(OrderDetailsActivity.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
                //Log.e("UPI", "Cancelled by user: " + approvalRefNo);
            } else {
                Toast.makeText(OrderDetailsActivity.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
                //Log.e("UPI", "failed payment: " + approvalRefNo);
            }

            startActivity(new Intent(this, HomeActivity.class));
        } else {
            Log.e("UPI", "Internet issue: ");
            Toast.makeText(OrderDetailsActivity.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }

    private void setOrderPayed(String approvalRefNo) {
        StringRequest changeStatusRequest = new StringRequest(Request.Method.POST, Url.CHANGE_ORDER_STATUS, response -> {

            if (response.trim().equals("success")) {

                bsd.dismiss();
                if(getIntent().getStringExtra("orderId")!=null){
                    Toast.makeText(this, "Payment Completed", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, UserOrderActivity.class));
                }else{
                    Toast.makeText(this, "Your Order Has Been Placed.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, HomeActivity.class));
                }
            }
        }, error -> {
            Toast.makeText(this, "Something going wrong please try in some time", Toast.LENGTH_SHORT).show();
            bsd.dismiss();
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("orderId", OrderId);
                params.put("statusCode", "1");
                if (!approvalRefNo.isEmpty()){
                    params.put("transactionId", approvalRefNo);
                }
                params.put("doneBy", "user");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(changeStatusRequest);
    }

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private void showPaymentOption(){
        bsd = new BottomSheetDialog(this, R.style.bottom_sheet_with_round_corner_theme);
        View view = LayoutInflater.from(this.getApplicationContext())
                .inflate(R.layout.payment_options,
                        this.findViewById(R.id.order_details_payment_option));

        ImageView cBtn = view.findViewById(R.id.closeBtn);
        cBtn.setOnClickListener(v1 -> {
            if(getIntent().getStringExtra("orderId")!=null){
                Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show();
                bsd.dismiss();
                startActivity(new Intent(this, UserOrderActivity.class));
            }else{
                bsd.dismiss();
            }
        });
        LinearLayout btn1,btn2;
        TextView text1,text2;

        text1 = view.findViewById(R.id.pOptionCash);
        text2 = view.findViewById(R.id.pOptionOnline);
         btn2 = view.findViewById(R.id.onlinePaymentBtn);
         btn1 = view.findViewById(R.id.cashPaymentBtn);

          paymentOption = 1;


         btn1.setOnClickListener(v->{
             text2.setTextColor(this.getResources().getColor(R.color.text_black));
             btn2.setBackground(this.getResources().getDrawable(R.drawable.round_corner_layout));
             text1.setTextColor(this.getResources().getColor(R.color.white));
             btn1.setBackground(this.getResources().getDrawable(R.drawable.round_corner_layout_with_main));
             paymentOption = 1;

         });

        btn2.setOnClickListener(v->{
            text1.setTextColor(this.getResources().getColor(R.color.text_black));
            btn1.setBackground(this.getResources().getDrawable(R.drawable.round_corner_layout));
            text2.setTextColor(this.getResources().getColor(R.color.white));
            btn2.setBackground(this.getResources().getDrawable(R.drawable.round_corner_layout_with_main));
            paymentOption = 2;
        });

        view.findViewById(R.id.payBtn).setOnClickListener(v->{
            if(paymentOption==2){
                payUsingUpi("New Pizza Num", "9044982994@okbizaxis", "Paying for order: " + OrderId,payingPayment);
            }else{
                setOrderPayed("");
            }
        });


        bsd.setContentView(view);
        bsd.setCancelable(false);
        bsd.show();

        myD.dismissLoadingDialog(100);
    }

    @Override
    public void onBackPressed() {
        if(getIntent().getStringExtra("orderId")!=null){
            Toast.makeText(this, "Payment Completed", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, UserOrderActivity.class));
        }else{
            Toast.makeText(this, "Your Order Has Been Placed.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, HomeActivity.class));
        }
    }
}