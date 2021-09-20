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

import com.mtechyard.newpizzayum.adapters.userCartAdapter;
import com.mtechyard.newpizzayum.project_rec.TinyDB;
import com.mtechyard.newpizzayum.project_rec.UserOrderList;

import java.util.List;

public class user_cart extends AppCompatActivity {


    @SuppressLint("StaticFieldLeak")
    private static TinyDB tinyDB;
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
        tinyDB = new TinyDB(this);
        myActivity = user_cart.this;

        ll = findViewById(R.id.product_list);
        cc = findViewById(R.id.noItemInCart);

        List<UserOrderList> list = tinyDB.getOrderList();
        if (list.size()>0){
            ll.setVisibility(View.VISIBLE);
            cc.setVisibility(View.GONE);
        }else{
            ll.setVisibility(View.GONE);
            cc.setVisibility(View.VISIBLE);
        }


        recyclerView = findViewById(R.id.cart_product_recyclerView);
        recyclerView.setAdapter(new userCartAdapter(user_cart.this,tinyDB.getOrderList()));

        findViewById(R.id.cart_pBtn).setOnClickListener(v -> {
            Intent intent = new Intent(user_cart.this,order_detail.class);
            startActivity(intent);
        });

        findViewById(R.id.addItemCart).setOnClickListener(v -> {
            finish();
        });
    }


    public static void addQuantity(int position){

        tinyDB.addQuantity(position);
        List<UserOrderList> list = tinyDB.getOrderList();
        if (list.size()>0){
            ll.setVisibility(View.VISIBLE);
            cc.setVisibility(View.GONE);
            recyclerView.setAdapter(new userCartAdapter(myActivity,tinyDB.getOrderList()));
        }else{
            ll.setVisibility(View.GONE);
            cc.setVisibility(View.VISIBLE);
        }

    }
    public static void removeQuantity(int position){

        tinyDB.removeQuantity(position);
        List<UserOrderList> list = tinyDB.getOrderList();
        if (list.size()>0){
            ll.setVisibility(View.VISIBLE);
            cc.setVisibility(View.GONE);
            recyclerView.setAdapter(new userCartAdapter(myActivity,tinyDB.getOrderList()));
        }else{
            ll.setVisibility(View.GONE);
            cc.setVisibility(View.VISIBLE);
        }

    }


}