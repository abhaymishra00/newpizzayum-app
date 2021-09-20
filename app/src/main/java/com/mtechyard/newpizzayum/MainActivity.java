package com.mtechyard.newpizzayum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.mtechyard.newpizzayum.project_rec.TinyDB;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        startActivity(new Intent(MainActivity.this, home.class));
//        finish();


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                TinyDB tinyDB = new TinyDB(MainActivity.this);
                String userMobileNo = tinyDB.getUserMobileNo();

                if (userMobileNo.trim().isEmpty()) {
                    startActivity(new Intent(MainActivity.this, user_auth.class));
                    finish();
                } else {
                    startActivity(new Intent(MainActivity.this, home.class));
                    finish();
                }
            }
        }, 1000);
    }
}