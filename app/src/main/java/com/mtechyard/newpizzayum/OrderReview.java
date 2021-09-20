package com.mtechyard.newpizzayum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.mtechyard.newpizzayum.project_rec.GlobalFunctions;
import com.mtechyard.newpizzayum.project_rec.MyDialog;
import com.mtechyard.newpizzayum.server.OrderInfoResponse;

import java.util.HashMap;
import java.util.Map;

public class OrderReview extends AppCompatActivity {

    private MyDialog dialog;
    private TextView orderId, name, mobile, date,
            statusMassage, dName, dMobile, dAddress,
            dLocality, itemCount, subTotal, gst,
            discount, total, orderPaymentIdText, paymentId, paid;

    private CardView statusLight;
    private LinearLayout addressLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_review);

        //INIT
        dialog = new MyDialog(this);

//        ImageView bBtn = findViewById(R.id.backButton);
//        bBtn.setOnClickListener(v -> {
//            finish();
//        });


        //INIT FIND VIEW BY ID

        orderId = findViewById(R.id.orderId);
        name = findViewById(R.id.userName);
        mobile = findViewById(R.id.userMobile);
        date = findViewById(R.id.orderDate);
        statusMassage = findViewById(R.id.orderCurrentStatus);
        dName = findViewById(R.id.deliveryName);
        dMobile = findViewById(R.id.userPhoneNo);
        dAddress = findViewById(R.id.deliveryAddress);
        dLocality = findViewById(R.id.deliveryLocality);
        itemCount = findViewById(R.id.itemId20);
        subTotal = findViewById(R.id.itemId21);
        gst = findViewById(R.id.itemId23);
        discount = findViewById(R.id.itemId24);
        total = findViewById(R.id.orderTotal);
        orderPaymentIdText = findViewById(R.id.orderPaymentIdText);
        paymentId = findViewById(R.id.orderPaymentId);
        paid = findViewById(R.id.orderPaid);


        statusLight = findViewById(R.id.orderCurrentStatusLight);
        addressLayout = findViewById(R.id.addressDetailView);

        String ORDER_ID = getIntent().getStringExtra("orderId");

        Toast.makeText(this, ORDER_ID, Toast.LENGTH_SHORT).show();
        if (ORDER_ID == null || ORDER_ID.isEmpty()) {
            Toast.makeText(this, "Something Wrong to show order info, Sorry :(", Toast.LENGTH_SHORT).show();
            //finish();
        } else {
            getOrderDetails(ORDER_ID);
        }


    }

    private void getOrderDetails(String OrderId) {
        String url = "http://mtechyard.com/newpizzayum-app/orderinfo.php?orderId=" + OrderId;

        Toast.makeText(this, url + OrderId + "this is toast", Toast.LENGTH_SHORT).show();
//        @SuppressLint("SetTextI18n")
//        StringRequest orderDetailsRequest = new StringRequest(Request.Method.GET, url, response -> {
//
//            Toast.makeText(this, response+"response from api", Toast.LENGTH_SHORT).show();
//            Gson gson = new Gson();
//            if(GlobalFunctions.isJSONValid(response)){
//
//                OrderInfoResponse  data1 = gson.fromJson(response,OrderInfoResponse.class);
//                if (data1.getResult().equals("success")){
//                    name.setText(data1.getName());
//                    mobile.setText(data1.getMobile());
//                    orderId.setText("Order Id: " + data1.getOrderId());
//                    date.setText(data1.getDate());
//                    statusMassage.setText(data1.getStatusMassage());
//
//                    if (data1.getOrderFor().trim().toLowerCase().equals("delivery")){
//                        addressLayout.setVisibility(View.VISIBLE);
//                        dName.setText(data1.getdName());
//                        dAddress.setText(data1.getdAddress());
//                        dMobile.setText(data1.getdMobile());
//                        dLocality.setText(data1.getdLoaclity());
//
//                    }else{
//                        addressLayout.setVisibility(View.GONE);
//                    }
//
//                    switch (data1.getStatusLightColor()){
//                        case 1:
//                            statusMassage.setTextColor(this.getResources().getColor(R.color.preGreen));
//                            statusLight.setCardBackgroundColor(this.getResources().getColor(R.color.preGreen));
//                            break;
//
//                        case 2:
//                            statusMassage.setTextColor(this.getResources().getColor(R.color.goodYellow));
//                            statusLight.setCardBackgroundColor(this.getResources().getColor(R.color.goodYellow));
//                            break;
//
//                        case 3:
//                            statusMassage.setTextColor(this.getResources().getColor(R.color.preYellow));
//                            statusLight.setCardBackgroundColor(this.getResources().getColor(R.color.preYellow));
//                            break;
//
//                        case 4:
//                            statusMassage.setTextColor(this.getResources().getColor(R.color.darkGreen));
//                            statusLight.setCardBackgroundColor(this.getResources().getColor(R.color.darkGreen));
//                            break;
//
//                        case 5:
//                            statusMassage.setTextColor(this.getResources().getColor(R.color.red));
//                            statusLight.setCardBackgroundColor(this.getResources().getColor(R.color.red));
//                            break;
//
//
//                    }
//                }else{
//                    Toast.makeText(this, "Some thing wrong", Toast.LENGTH_SHORT).show();
//                }
//
//            }else{
//                Toast.makeText(this,    "Some internal server error occurred", Toast.LENGTH_SHORT).show();
//            }
//        }, error -> {
//            Toast.makeText(this, error.getMessage() + "this is an error", Toast.LENGTH_SHORT).show();
//        });
//
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(orderDetailsRequest);
    }
}