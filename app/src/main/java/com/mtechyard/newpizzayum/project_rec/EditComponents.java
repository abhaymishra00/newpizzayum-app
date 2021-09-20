package com.mtechyard.newpizzayum.project_rec;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class EditComponents {
    private final Activity myActivity;

    public EditComponents(Activity activity) {
        this.myActivity = activity;
    }


    public void setText(int ViewID, String Text) {
        TextView v = myActivity.findViewById(ViewID);
        v.setText(Text);
    }

    public void setTextViewVisibility(int ViewID, int Visibility) {
        TextView v = myActivity.findViewById(ViewID);
        v.setVisibility(Visibility);
    }

    public void setLinearLayoutVisibility(int ViewID, int Visibility) {
        LinearLayout v = myActivity.findViewById(ViewID);
        v.setVisibility(Visibility);
    }

    public void setButtonVisibility(int ViewID, int Visibility) {
        Button v = myActivity.findViewById(ViewID);
        v.setVisibility(Visibility);
    }
    public void setButtonText(int ViewID, String text) {
        Button v = myActivity.findViewById(ViewID);
        v.setText(text);
    }



    public static void setLinearLayoutVisibility(Activity activity ,int ViewID, int Visibility) {
        LinearLayout v = activity.findViewById(ViewID);
        v.setVisibility(Visibility);
    }

    public static void setConstraintLayoutVisibility(Activity activity ,int ViewID, int Visibility) {
        ConstraintLayout v = activity.findViewById(ViewID);
        v.setVisibility(Visibility);
    }

    public static void setLinearLayoutVisibility(View v, int ViewID, int Visibility) {
        LinearLayout ll = v.findViewById(ViewID);
        ll.setVisibility(Visibility);
    }


    public static void setTextViewText(View v, int ViewID, String text) {
        TextView tv = v.findViewById(ViewID);
        tv.setText(text);
    }

    public void setTextViewText(int ViewID, String text) {
        TextView tv = myActivity.findViewById(ViewID);
        tv.setText(text);
    }
}
