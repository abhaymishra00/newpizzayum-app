package com.mtechyard.newpizzayum;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.mtechyard.newpizzayum.adapters.ordersAdapter;
import com.mtechyard.newpizzayum.api.Url;
import com.mtechyard.newpizzayum.api.UserOrderData;
import com.mtechyard.newpizzayum.app.MyDialog;
import com.mtechyard.newpizzayum.app_src.AppDB;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserOrderActivity extends AppCompatActivity{

    List<UserOrderData> dataList;
    RecyclerView recyclerView;

    LinearLayout linearLayout;
    ConstraintLayout constraintLayout;
    MyDialog myDialog;

    @SuppressLint("StaticFieldLeak")
    private static Activity myActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order);

        // INIT
        dataList = new ArrayList<>();
        recyclerView = findViewById(R.id.order__);
        myDialog = new MyDialog(this);
        myDialog.showLoadingDialog();
        myActivity = this;


        linearLayout = findViewById(R.id.userOrderList);
        constraintLayout= findViewById(R.id.noItemInOrder);


        ImageView bBtn = findViewById(R.id.backButton);
        bBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, HomeActivity.class));

        });



        StringRequest request = new StringRequest(Request.Method.POST, Url.GET_USERS_ORDERS, response -> {
           // Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
            if ((response.length() > 0)) {

                linearLayout.setVisibility(View.VISIBLE);
                constraintLayout.setVisibility(View.GONE);

                Gson gson = new Gson();

                UserOrderData[] dataList = gson.fromJson(response,UserOrderData[].class);
                recyclerView.setAdapter(new ordersAdapter(this, dataList));

            }else{
                linearLayout.setVisibility(View.GONE);
                constraintLayout.setVisibility(View.VISIBLE);
            }

            myDialog.dismissLoadingDialog(500);
        }, error -> {
            Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
            myDialog.dismissLoadingDialog(500);
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("primaryMobile", new AppDB(UserOrderActivity.this).getUserMobileNo());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }


    public void openHome(View view) {
        startActivity(new Intent(this, HomeActivity.class));
    }


    public static void showPayment (String OrderId,String Payment){
        Intent intent = new Intent(myActivity, OrderDetailsActivity.class);
        intent.putExtra("orderId",OrderId);
        intent.putExtra("payingPayment",Payment);
        myActivity.startActivity(intent);
    }




}