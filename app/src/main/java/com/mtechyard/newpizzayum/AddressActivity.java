package com.mtechyard.newpizzayum;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.mtechyard.newpizzayum.adapters.addressAdepter;
import com.mtechyard.newpizzayum.api.AddressList;
import com.mtechyard.newpizzayum.app_src.AppDB;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends AppCompatActivity {

    private static RecyclerView recyclerView;
    @SuppressLint("StaticFieldLeak")
    private static Activity thisActivity;
    private static boolean option;

    public static void deleteAddress(int position) {
        List<AddressList> list  = new AppDB(thisActivity).getAddressList();
        list.remove(position);
        Gson gson = new Gson();
        String newAddressList = gson.toJson(list, ArrayList.class);
        new AppDB(thisActivity).save("UserAddressList", newAddressList);
        recyclerView.setAdapter(new addressAdepter(thisActivity,list, option));

    }

    public static void setDefaultAddress(int position) {
        new AppDB(thisActivity).changeDefaultAddress(position);
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
                startActivity(new Intent(AddressActivity.this, HomeActivity.class));
            }

        });


        findViewById(R.id.changeAddress).setOnClickListener(v->{

            if (thisActivity.getIntent().getIntExtra("requestCode",-1) == 1){
                Intent intent = new Intent(this, UserDetailsActivity.class);
                intent.putExtra("requestCode",2);
                startActivityForResult(intent, 2);
            }else{
                startActivity(new Intent(AddressActivity.this, UserDetailsActivity.class));
            }
        });

        recyclerView = findViewById(R.id.address_list);
        if (thisActivity.getIntent().getIntExtra("requestCode",-1) == 1){
            option = true;
        }
        recyclerView.setAdapter(new addressAdepter(this,new AppDB(this).getAddressList(), option));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                recyclerView.setAdapter(new addressAdepter(this,new AppDB(this).getAddressList(), option));
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
            startActivity(new Intent(AddressActivity.this, HomeActivity.class));
        }
    }
}