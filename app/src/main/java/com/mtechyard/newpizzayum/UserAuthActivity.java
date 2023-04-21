package com.mtechyard.newpizzayum;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mtechyard.newpizzayum.app.GlobalFunctions;

public class UserAuthActivity extends AppCompatActivity {

    private static final String TAG = "firebase-auth";
    Button login;
    private EditText user_input_number,user_input_name;
    GlobalFunctions globalFunction;



    TextView phone_no_input_error,usernameerror;


    @SuppressLint({"ShowToast", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_auth);

        user_input_number = findViewById(R.id.user_mobile_number);
        user_input_name = findViewById(R.id.user_name);

        //INTT GLOBAL FUNCTIONS
        globalFunction = new GlobalFunctions(this);

        //USER INPUT PHONE NO. ERROR TEXT VIEW
        phone_no_input_error = findViewById(R.id.user_mobile_number_input_error);
        usernameerror = findViewById(R.id.user_name_error);

        login = findViewById(R.id.login_button);
        login.setOnClickListener(v -> {

                if(globalFunction.PHONE_CHECK(user_input_number.getEditableText().toString(),R.id.user_mobile_number_input_error)){
                   if (!user_input_name.getEditableText().toString().isEmpty()){
                       Intent intent = new Intent(this, OtpActivity.class);
                       intent.putExtra("number",user_input_number.getEditableText().toString());
                       intent.putExtra("name",user_input_name.getEditableText().toString());
                       startActivity(intent);
                   }else{
                       usernameerror.setText("Please enter your good name.");
                       usernameerror.setVisibility(View.VISIBLE);
                   }
                }else {
                    Log.d("Phone-auth","Phone auth failed");
                }

        });

    }




}