package com.mtechyard.newpizzayum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.mtechyard.newpizzayum.app.TinyDB;
import com.mtechyard.newpizzayum.app_src.AppDB;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            AppDB tinyDB = new AppDB(MainActivity.this);
            String userMobileNo = tinyDB.getUserMobileNo();

            if (userMobileNo.trim().isEmpty()) {
                startActivity(new Intent(MainActivity.this, UserAuthActivity.class));
                finish();
            } else {
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                finish();
            }
        }, 5000);
    }
}