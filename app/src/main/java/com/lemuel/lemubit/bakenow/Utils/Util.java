package com.lemuel.lemubit.bakenow.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.lemuel.lemubit.bakenow.MainActivity;
import com.lemuel.lemubit.bakenow.R;

/**
 * Created by charl on 10/11/2017.
 */

public class Util {

    //checks if activity was just run
    public static Boolean firstRun = true;

    //check if a string is empty or null
    @NonNull
    public static Boolean emptyString(String value) {
        if (TextUtils.isEmpty(value)) {
            return true;//if empty or null return true
        } else {
            return false;//return false if not empty
        }

    }

    //check if a String is neither empty nor null
    @NonNull
    public static Boolean StringNotEmpty(String value) {
        if (TextUtils.isEmpty(value)) {
            return false;//if empty or null return false
        } else {
            return true;//return true if not empty
        }

    }

    //check if it's a large screen >=700DP
    @NonNull
    public static Boolean isLargeScreen(Activity context) {
        //CALCULATE SCREEN SIZE, IN THIS CASE LARGE SCREENS ARE >700
        Boolean aBoolean=context.getResources().getBoolean(R.bool.isLargeTablet);
        if (aBoolean) {
            return true;
        } else {
            return false;
        }
    }

    //check if it's a medium sized screen >=600DP
    @NonNull
    public static Boolean isMediumScreen(Activity context) {
        //CALCULATE SCREEN SIZE, IN THIS CASE MEDIUM SCREENS ARE DP>=600
        Boolean aBoolean=context.getResources().getBoolean(R.bool.isTablet);
        if (aBoolean) {
            return true;
        } else {
            return false;
        }
    }

    //check if it's a smaller phone screen <600
    @NonNull
    public static Boolean isSmallScreen(Activity context) {
        //CALCULATE SCREEN SIZE, IN THIS CASE MEDIUM SCREENS ARE DP>=600
        Boolean aBoolean=context.getResources().getBoolean(R.bool.isSmallScreen);
        if (aBoolean) {
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

    /*Check if a Recipe ingredient is more than one e.g if Eggs are measured in crates, it will
        output '2 crates of eggs' instead of '2 crate of eggs'
    */
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
    public static Boolean isPortraitMode(int orientation) {
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            return true;
        } else {
            return false;
        }
    }


}
