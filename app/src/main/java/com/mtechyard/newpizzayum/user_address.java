package com.mtechyard.newpizzayum;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mtechyard.newpizzayum.adapters.addressAdepter;
import com.mtechyard.newpizzayum.project_rec.AddressList;
import com.mtechyard.newpizzayum.project_rec.TinyDB;

import java.util.ArrayList;
import java.util.List;

public class user_address extends AppCompatActivity {

    private static RecyclerView recyclerView;
    @SuppressLint("StaticFieldLeak")
    private static Activity thisActivity;
    private static boolean option;

    public static void deleteAddress(int position) {
        List<AddressList> list  = new TinyDB(thisActivity).getAddressList();
        list.remove(position);
        Gson gson = new Gson();
        String newAddressList = gson.toJson(list, ArrayList.class);
        new TinyDB(thisActivity).StoreString("UserAddressList", newAddressList);
        recyclerView.setAdapter(new addressAdepter(thisActivity,list, option));

    }

    public static void setDefaultAddress(int position) {
        new TinyDB(thisActivity).changeDefaultAddress(position);
        if (thisActivity.getIntent().getIntExtra("requestCode",-1) == 1){

            Intent resultIntent = new Intent();
            thisActivity.setResult(RESULT_OK, resultIntent);
            thisActivity.finish();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_address);

        thisActivity = this;
        option = false;
        ImageView bBtn  = findViewById(R.id.backButton);
        bBtn.setOnClickListener(v -> {


            if (thisActivity.getIntent().getIntExtra("requestCode",-1) == 1){

                Intent resultIntent = new Intent();
                thisActivity.setResult(RESULT_OK, resultIntent);
                thisActivity.finish();

            }else{
                startActivity(new Intent(user_address.this,home.class));
            }

        });


        findViewById(R.id.changeAddress).setOnClickListener(v->{

            if (thisActivity.getIntent().getIntExtra("requestCode",-1) == 1){
                Intent intent = new Intent(this, user_details_form.class);
                intent.putExtra("requestCode",2);
                startActivityForResult(intent, 2);
            }else{
                startActivity(new Intent(user_address.this,user_details_form.class));
            }
        });

        recyclerView = findViewById(R.id.address_list);
        if (thisActivity.getIntent().getIntExtra("requestCode",-1) == 1){
            option = true;
        }
        recyclerView.setAdapter(new addressAdepter(this,new TinyDB(this).getAddressList(), option));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                recyclerView.setAdapter(new addressAdepter(this,new TinyDB(this).getAddressList(), option));
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (thisActivity.getIntent().getIntExtra("requestCode",-1) == 1){

            Intent resultIntent = new Intent();
            thisActivity.setResult(RESULT_OK, resultIntent);
            thisActivity.finish();

        }else{
            startActivity(new Intent(user_address.this,home.class));
        }
    }
}