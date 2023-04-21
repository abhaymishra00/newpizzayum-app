package com.mtechyard.newpizzayum;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mtechyard.newpizzayum.app.GlobalFunctions;
import com.mtechyard.newpizzayum.app.TinyDB;
import com.mtechyard.newpizzayum.app.MyDialog;
import com.mtechyard.newpizzayum.app_src.AppDB;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private MyDialog myDialog;
    private String verificationId;
    private String code;
    private PhoneAuthCredential credential;
    LinearLayout waitDialog;
    GlobalFunctions globalFunctions;
    String phoneNumber,userName;

    private final String TAG = "Firebase-auth-sms";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        FirebaseAuth.getInstance().signOut();

//        Button button = findViewById(R.id.otp_verify_btn);
//        button.setOnClickListener(v -> startActivity(new Intent(OtpActivity.this,HomeActivity.class)));


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // calling my dialogs
        myDialog = new MyDialog(this);

//        please wait dialog
        waitDialog = findViewById(R.id.please_wait_dialog);
        globalFunctions = new GlobalFunctions(this);

        // phone
        phoneNumber = " ";

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {


                PinView otp = findViewById(R.id.user_entered_otp);
                otp.setText(credential.getSmsCode());
                waitDialog.setVisibility(View.GONE);
                signInWithPhoneAuthCredential(credential);

                Log.i("firebase-sms", "firebase sms send and verify");
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e("firebase-sms-error", e.toString());
                waitDialog.setVisibility(View.GONE);

            }

            @Override
            public void onCodeSent(@NonNull String verificationCode,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                verificationId = verificationCode;
                waitDialog.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                waitDialog.setVisibility(View.GONE);
                super.onCodeAutoRetrievalTimeOut(s);
            }
        };


        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("number");
        userName = intent.getStringExtra("name");
        sendOTP(phoneNumber);


    }


    private void sendOTP(String phoneNumber) {

        if (phoneNumber != null && !phoneNumber.isEmpty()) {

            Log.i("sendOTP", "sending OtpActivity on " + phoneNumber);

            PhoneAuthOptions options =
                    PhoneAuthOptions.newBuilder(mAuth)
                            .setPhoneNumber("+91" + phoneNumber)       // Phone number to verify
                            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                            .setActivity(OtpActivity.this)                 // Activity (for callback binding)
                            .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                            .build();
            PhoneAuthProvider.verifyPhoneNumber(options);

        } else {
            Log.e("sendOTP-error", "phone number is null");
        }
    }

    public void verifyCodeFromButton(View v) {
        verifyCode();

    }


    private void verifyCode() {
        PinView otp = findViewById(R.id.user_entered_otp);
        code = otp.getEditableText().toString();

        if (CHECK_OTP(code) && !verificationId.isEmpty()) {

            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

            if (Objects.equals(credential.getSmsCode(), code)) {
                signInWithPhoneAuthCredential(credential);
            } else {
                globalFunctions.showError(R.id.user_entered_otp_error, "You entered a wrong OtpActivity");
            }
        } else {
            Log.e("VerifyOTP-error", "For OtpActivity verification need a OtpActivity and verification code");
        }

    }

    @SuppressLint("SetTextI18n")
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(OtpActivity.this, task -> {
                    if (task.isSuccessful()) {
                        nextScreen();
                    } else {
                        Toast.makeText(this, "Wrong OTP", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @SuppressLint("SetTextI18n")
    private boolean CHECK_OTP(String OTP) {
        if (OTP.isEmpty()) {
            globalFunctions.showError(R.id.user_entered_otp_error, "Please enter OTP");
            return false;
        } else {
            if (OTP.length() > 6 | OTP.length() < 6) {
                globalFunctions.showError(R.id.user_entered_otp_error, "Please enter a valid OTP");
                return false;
            } else {
                globalFunctions.hideError(R.id.user_entered_otp_error);
                return true;

            }
        }
    }

    public void nextScreen() {

        AppDB appDB = new AppDB(this);
        appDB.saveUserMobileNo(phoneNumber);
        appDB.saveUserName(userName);
        startActivity(new Intent(OtpActivity.this, HomeActivity.class));
        finish();
    }

}