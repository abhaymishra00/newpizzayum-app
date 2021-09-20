package com.mtechyard.newpizzayum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

public class offers_and_discounts extends AppCompatActivity {
    private int openCode;
    private int totalAmount;
    private int backResult;
    private String backResultCoupon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers_and_discounts);

        //INIT
        backResult = 0;
        totalAmount = getIntent().getIntExtra("totalAmount", -1);
        if ( totalAmount != -1) {
            openCode = 1;
        } else {
            openCode = 0;
        }

        //DO NOT REMOVE THIS CODE FROM HERE
        ImageView bBtn = findViewById(R.id.backButton);
        bBtn.setOnClickListener(v -> {
            GoBack();
        });

        // coupon code apply btn click handel
        findViewById(R.id.couponCodeBtn).setOnClickListener(v -> {

            backResult = 10;
            backResultCoupon = "NEW50";
            GoBack();
        });

    }


    private void GoBack(){
        if (openCode == 1) {
            setResult(RESULT_OK, new Intent().putExtra("result", backResult));
            setResult(RESULT_OK, new Intent().putExtra("discountCoupon", backResultCoupon));
            finish();
        } else {
            startActivity(new Intent(this, home.class));
        }
    }

    @Override
    public void onBackPressed() {
        GoBack();
    }

}