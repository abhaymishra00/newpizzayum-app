package com.mtechyard.newpizzayum.project_rec;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;

import com.mtechyard.newpizzayum.R;

import java.util.Objects;

public class MyDialog {
    Context context;

    Activity myActivity;
    AlertDialog alertDialog;
    ProgressDialog progressDialog;

    public MyDialog(Activity activity){
        myActivity = activity;
        this.context = activity.getApplicationContext();
        progressDialog = new ProgressDialog(myActivity);
    }

    public void showLoadingDialog(){
        progressDialog = new ProgressDialog(myActivity);
        progressDialog.setCancelable(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.loading_dialog);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
    }
    public void dismissLoadingDialog(int mSec){
        Handler handler = new Handler();
        handler.postDelayed(() -> progressDialog.dismiss(),mSec);
    }
}
