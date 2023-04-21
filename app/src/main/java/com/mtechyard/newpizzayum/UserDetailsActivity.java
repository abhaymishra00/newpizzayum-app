package com.mtechyard.newpizzayum;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.mtechyard.newpizzayum.api.AddressList;
import com.mtechyard.newpizzayum.app.GlobalFunctions;
import com.mtechyard.newpizzayum.app.MyDialog;
import com.mtechyard.newpizzayum.api.RequestResponse;
import com.mtechyard.newpizzayum.app_src.AppDB;
import com.mtechyard.newpizzayum.api.Url;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class UserDetailsActivity extends AppCompatActivity {
    EditText name, mobile, locality, address, pinCode;
    TextView error_1, error_2, error_3, error_4, error_5;
    CheckBox defaultAddress;
    GlobalFunctions gf;
    String name_v, locality_v, fullAddress, mobile_v, pinCode_v;

    MyDialog dialog;
    Spinner spinner;
    String[] spinnerData ;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details_form);

        ImageView bBtn = findViewById(R.id.backButton);
        bBtn.setOnClickListener(v -> {
            if (getIntent().getIntExtra("requestCode", -1) == 2) {

                dialog.dismissLoadingDialog(1);
                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);
                finish();

            } else {
                dialog.dismissLoadingDialog(1);
                startActivity(new Intent(this, AddressActivity.class));
            }

        });


        dialog = new MyDialog(this);
        name = findViewById(R.id.a_full_name);
        mobile = findViewById(R.id.a_mobile_no);
        locality = findViewById(R.id.a_locality);
        address = findViewById(R.id.a_full_address);
        pinCode = findViewById(R.id.a_pinCode);
        defaultAddress = findViewById(R.id.a_checkbox);
        spinner = findViewById(R.id.select_locality);


        // Selection of the spinner
        String url = "https://mtechyard.com/newpizzayum-app/locality.php";
        StringRequest localityRequest = new StringRequest(Request.Method.POST,url,response -> {
            if (GlobalFunctions.isJSONValid(response)){
                Gson gson = new Gson();
                String[] data = gson.fromJson(response,String[].class);
                spinnerData = data;
                // Application of the Array to the Spinner
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, data);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                spinner.setAdapter(spinnerArrayAdapter);
            }

        },error -> {

        });

        RequestQueue re = Volley.newRequestQueue(this);
        re.add(localityRequest);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                locality.setText(spinnerData[position]);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        error_1 = findViewById(R.id.a_error_1);
        error_2 = findViewById(R.id.a_error_2);
        error_3 = findViewById(R.id.a_error_3);
        error_4 = findViewById(R.id.a_error_4);
        error_5 = findViewById(R.id.a_error_5);

        gf = new GlobalFunctions(UserDetailsActivity.this);

        findViewById(R.id.a_add_btn).setOnClickListener(v -> {

            name_v = this.name.getEditableText().toString().trim();
            locality_v = this.locality.getEditableText().toString().trim();
            fullAddress = this.address.getEditableText().toString().trim();
            mobile_v = this.mobile.getEditableText().toString().trim();
            pinCode_v = this.pinCode.getEditableText().toString().trim();

            if (name_v.length() < 3) {
                error_1.setText("Enter a valid name like Shivam Mishra");
                error_1.setVisibility(View.VISIBLE);
            } else {
                error_1.setVisibility(View.GONE);
                if (mobile_v.isEmpty()) {
                    error_2.setVisibility(View.VISIBLE);
                    error_2.setText("Enter your mobile number");
                } else {

                    String mEx = mobile_v.substring(0, 1);
                    String gMobile;
                    if (mEx.equals("+")) {
                        int mLength = mobile_v.length();
                        gMobile = mobile_v.substring(3, mLength);
                    } else {
                        gMobile = mobile_v;
                    }

                    if (gMobile.length() != 10) {
                        error_2.setVisibility(View.VISIBLE);
                        error_2.setText("Enter a valid mobile number");
                    } else {
                        error_2.setVisibility(View.GONE);
                        if (locality_v.isEmpty()) {
                            error_3.setVisibility(View.VISIBLE);
                            error_3.setText("Enter your locality");
                        } else {
                            checkLocality(locality_v);
                        }
                    }

                }
            }


        });

    }

    private void saveUserAddress() {
        dialog.showLoadingDialog();

        if (defaultAddress.isChecked()) {
            new AppDB(this).removeDefaultAddress();
            new AppDB(this).addNewAddress(new AddressList(
                    name_v,
                    mobile_v,
                    pinCode_v,
                    fullAddress + " " + pinCode_v,
                    locality_v,
                    true
            ));
        } else {
            new AppDB(this).addNewAddress(new AddressList(
                    name_v,
                    mobile_v,
                    pinCode_v,
                    fullAddress + " " + pinCode_v,
                    locality_v,
                    false
            ));
        }


        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Toast.makeText(this, "New Address Created", Toast.LENGTH_SHORT).show();
            if (getIntent().getIntExtra("requestCode", -1) == 2) {

                dialog.dismissLoadingDialog(1);
                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);
                finish();

            } else {
                dialog.dismissLoadingDialog(1);
                startActivity(new Intent(this, AddressActivity.class));
            }
        }, 1000);
    }


    private void checkLocality(String UserLocality) {
        StringRequest localityCheckRequest = new StringRequest(Request.Method.POST, Url.AREA_CHECK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                Gson gson = new Gson();
                RequestResponse data = gson.fromJson(response, RequestResponse.class);

                if (data.getResult().equals("success")) {

                    check_v(data.getLocalityCheckResponse());

                } else {
                    Log.e("localityCheck", "Check Response Comes Failed" + data.getResult());
                }

            }
        }, error -> Log.e("localityCheck", "Locality check request failed because => " + error.getMessage())) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("locality", UserLocality);
                return params;
            }

        };

        RequestQueue loadUiRequest = Volley.newRequestQueue(UserDetailsActivity.this);
        loadUiRequest.add(localityCheckRequest);

    }

    @SuppressLint("SetTextI18n")
    private void check_v(boolean localityCheckResponse) {
        if (localityCheckResponse) {
            if (fullAddress.isEmpty()) {
                error_3.setVisibility(View.GONE);
                error_4.setVisibility(View.VISIBLE);
                error_4.setText("Enter your full address");

            } else {
                error_4.setVisibility(View.GONE);
                if (pinCode.length() < 6) {
                    error_5.setVisibility(View.VISIBLE);
                    error_5.setText("Enter your valid area pin code");
                } else {
                    saveUserAddress();
                }
            }
        } else {
            error_3.setVisibility(View.VISIBLE);
            error_3.setText("Sorry! we are currently not served in your entered locality");
        }
    }


    @Override
    public void onBackPressed() {
        if (getIntent().getIntExtra("requestCode", -1) == 2) {
            dialog.dismissLoadingDialog(1);
            Intent resultIntent = new Intent();
            setResult(RESULT_OK, resultIntent);
            finish();

        } else {
            dialog.dismissLoadingDialog(1);
            startActivity(new Intent(this, AddressActivity.class));
        }
    }


}