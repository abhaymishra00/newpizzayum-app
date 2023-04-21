package com.mtechyard.newpizzayum.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class GlobalFunctions {

    public static final String restaurant1 = "rt01";
    public static final String restaurant2 = "rt02";

    Activity myActivity;

    public GlobalFunctions(Activity activity) {
        myActivity = activity;
    }


    public void setItemVisibility(int ViewId, int Visibility) {


    }

    public final boolean PHONE_CHECK(String mobileNumber) {

        if (mobileNumber.isEmpty()) {
            return false;
        } else {
            return !(mobileNumber.length() > 10 | mobileNumber.length() < 10);
        }
    }

    @SuppressLint("SetTextI18n")
    public boolean PHONE_CHECK(String mobileNumber, int ViewID) {
        if (mobileNumber.isEmpty()) {
            showError(ViewID, "Please enter a phone no.");
            return false;
        } else {
            if (mobileNumber.length() > 10 | mobileNumber.length() < 10) {
                showError(ViewID, "Please enter a valid phone no.");
                return false;
            } else {
                hideError(ViewID);
                return true;

            }
        }
    }


    public void showError(int ViewID, String ErrorText) {
        TextView e = myActivity.findViewById(ViewID);
        e.setText(ErrorText);
        e.setVisibility(View.VISIBLE);
    }

    public void displayError(int ViewID, String errorText) {
        TextView e = myActivity.findViewById(ViewID);
        e.setText(errorText);
        e.setVisibility(View.VISIBLE);
    }

    public void hideError(int ViewID) {
        TextView et = myActivity.findViewById(ViewID);
        et.setVisibility(View.GONE);
    }




    public int clintWidth(){
        Display display = myActivity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics ();
        display.getMetrics(outMetrics);
        float density  = myActivity.getResources().getDisplayMetrics().density;
        return (int) (outMetrics.widthPixels / density);
    }

    public int clintHeight(){
        Display display = myActivity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics ();
        display.getMetrics(outMetrics);
        float density  = myActivity.getResources().getDisplayMetrics().density;
        return (int) (outMetrics.heightPixels / density);
    }


    public static void scrollRecyclerViewToPosition(Activity activity,RecyclerView recyclerView,int position){
        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(activity){
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };

        smoothScroller.setTargetPosition(position);
        Objects.requireNonNull(recyclerView.getLayoutManager()).startSmoothScroll(smoothScroller);
    }


    public static boolean isJSONValid(String jsonString) {
        try {
            new JSONObject(jsonString);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(jsonString);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    public static String upperCaseFirstLetter(String name)
    {
        char[] array = name.toCharArray();
        array[0] = Character.toUpperCase(array[0]);

        for (int i = 1; i < array.length; i++) {
            if (Character.isWhitespace(array[i - 1])) {
                array[i] = Character.toUpperCase(array[i]);
            }
        }

        return new String(array);
    }

}
