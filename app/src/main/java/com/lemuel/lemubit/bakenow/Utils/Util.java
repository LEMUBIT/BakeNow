package com.lemuel.lemubit.bakenow.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.lemuel.lemubit.bakenow.MainActivity;

/**
 * Created by charl on 10/11/2017.
 */

public class Util {

    //checks if activity was just run
   public static Boolean firstRun=true;

    //check if a string is empty or null
    @NonNull
    public static Boolean emptyString(String value) {
        if (value.isEmpty() || value.length() < 1 || value == null || value == "") {
            return true;//if empty or null return true
        } else {
            return false;//return false if not empty
        }

    }

    //check if a String is neither empty nor null
    @NonNull
    public static Boolean StringNotEmpty(String value) {
        if (value.isEmpty() || value.length() < 1 || value == null || value == "") {
            return false;//if empty or null return false
        } else {
            return true;//return true if not empty
        }

    }

    //check if it's a large screen >=700DP
    @NonNull
    public static Boolean isLargeScreen(Activity context) {
        //CALCULATE SCREEN SIZE, IN THIS CASE LARGE SCREENS ARE >700
        DisplayMetrics metrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;
        float scaleFactor = metrics.density;
        float widthDp = widthPixels / scaleFactor;
        float heightDp = heightPixels / scaleFactor;
        float smallestWidth = Math.min(widthDp, heightDp);
        if (smallestWidth >= 700) {
            return true;
        } else {
            return false;
        }
    }

    //check if it's a medium sized screen >=600DP
    @NonNull
    public static Boolean isMediumScreen(Activity context) {
        //CALCULATE SCREEN SIZE, IN THIS CASE MEDIUM SCREENS ARE >=600
        DisplayMetrics metrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;
        float scaleFactor = metrics.density;
        float widthDp = widthPixels / scaleFactor;
        float heightDp = heightPixels / scaleFactor;
        float smallestWidth = Math.min(widthDp, heightDp);
        if (smallestWidth >= 600) {
            return true;
        } else {
            return false;
        }
    }

    //check if it's a smaller phone screen <600
    @NonNull
    public static Boolean isSmallScreen(Activity context) {
        //CALCULATE SCREEN SIZE, IN THIS CASE MEDIUM SCREENS ARE >=600
        DisplayMetrics metrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;
        float scaleFactor = metrics.density;
        float widthDp = widthPixels / scaleFactor;
        float heightDp = heightPixels / scaleFactor;
        float smallestWidth = Math.min(widthDp, heightDp);
        if (smallestWidth < 600) {
            return true;
        } else {
            return false;
        }
    }

    //Check if a object is null
    @NonNull
    public static Boolean ObjectisNull(Object value) {
        if (value == null) {
            return true;
        } else {
            return false;
        }
    }

    //Check if a object is not null
    @NonNull
    public static Boolean ObjectisNotNull(Object value) {
        if (value == null) {
            return false;
        } else {
            return true;
        }
    }

    //Check if a Recipe ingredient is more than one
    public static String Plural(Double quantity, String measure) {
        if (quantity <= 1.0) {
            return measure;
        } else if (quantity > 1.0 && (measure.equals("CUP") || measure.equals("UNIT"))) {
            measure = measure + "s";
            return measure;
        } else {
            return measure;
        }
    }

    //Check if device is in Portrait mode
    public static Boolean isPortraitMode(int orientation)
    {
        if(orientation== Configuration.ORIENTATION_PORTRAIT)
        {
            return true;
        }
        else
        {
            return false;
        }
    }



}
