package com.mtechyard.newpizzayum;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.mtechyard.newpizzayum.adapters.userCartAdapter;
import com.mtechyard.newpizzayum.app.TinyDB;
import com.mtechyard.newpizzayum.api.UserOrderList;
import com.mtechyard.newpizzayum.app_src.AppDB;

import java.util.List;

public class CartActivity extends AppCompatActivity {


    @SuppressLint("StaticFieldLeak")
    private static AppDB appDB;
    private static RecyclerView recyclerView;
    @SuppressLint("StaticFieldLeak")
    private static Activity myActivity;
    @SuppressLint("StaticFieldLeak")
    private static LinearLayout ll;
    private static ConstraintLayout cc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cart);
        ImageView bBtn  = findViewById(R.id.backButton);
        bBtn.setOnClickListener(v -> {
            finish();
        });
        appDB = new AppDB(this);
        myActivity = CartActivity.this;

        ll = findViewById(R.id.product_list);
        cc = findViewById(R.id.noItemInCart);

        List<UserOrderList> list = appDB.getOrderList();
        if (list.size()>0){
            ll.setVisibility(View.VISIBLE);
            cc.setVisibility(View.GONE);
        }else{
            ll.setVisibility(View.GONE);
            cc.setVisibility(View.VISIBLE);
        }


        recyclerView = findViewById(R.id.cart_product_recyclerView);
        recyclerView.setAdapter(new userCartAdapter(CartActivity.this, appDB.getOrderList()));

        findViewById(R.id.cart_pBtn).setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, OrderDetailsActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.addItemCart).setOnClickListener(v -> {
            finish();
        });
    }


    public static void addQuantity(int position){

        appDB.addQuantity(position);
        List<UserOrderList> list = appDB.getOrderList();
        if (list.size()>0){
            ll.setVisibility(View.VISIBLE);
            cc.setVisibility(View.GONE);
            recyclerView.setAdapter(new userCartAdapter(myActivity, appDB.getOrderList()));
        }else{
            ll.setVisibility(View.GONE);
            cc.setVisibility(View.VISIBLE);
        }

    }
    public static void removeQuantity(int position){

        appDB.removeQuantity(position);
        List<UserOrderList> list = appDB.getOrderList();
        if (list.size()>0){
            ll.setVisibility(View.VISIBLE);
            cc.setVisibility(View.GONE);
            recyclerView.setAdapter(new userCartAdapter(myActivity, appDB.getOrderList()));
        }else{
            ll.setVisibility(View.GONE);
            cc.setVisibility(View.VISIBLE);
        }

    }


}