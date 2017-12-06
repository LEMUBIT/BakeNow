/*
 *
 *   BakeNow application
 *
 *   @author Lemuel Ogbunude
 *   Copyright (C) 2017 Lemuel Ogbunude (lemuelcco@gmail.com)
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *
 */

package com.lemuel.lemubit.bakenow.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.lemuel.lemubit.bakenow.R;


public class Util {

    //track activity first run
    public static Boolean firstRun = true;

    //string is empty or null
    @NonNull
    public static Boolean emptyString(String value) {
        return TextUtils.isEmpty(value) ? true : false;
    }

    //string is neither empty nor null
    @NonNull
    public static Boolean StringNotEmpty(String value) {
        return TextUtils.isEmpty(value) ? false : true;
    }

    // >=700DP
    @NonNull
    public static Boolean isLargeScreen(Activity context) {
        return context.getResources().getBoolean(R.bool.isLargeTablet);
    }

    //>=600DP
    @NonNull
    public static Boolean isMediumScreen(Activity context) {
        return context.getResources().getBoolean(R.bool.isTablet);
    }

    //<600
    @NonNull
    public static Boolean isSmallScreen(Activity context) {
        return context.getResources().getBoolean(R.bool.isSmallScreen);
    }

    //object is null
    @NonNull
    public static Boolean ObjectisNull(Object value) {
        return value == null ? true : false;
    }

    //object is not null
    @NonNull
    public static Boolean ObjectisNotNull(Object value) {
        return value == null ? false : true;
    }


    /*Check if a Recipe ingredient is more than one e.g if Eggs are measured in crates, it will
    *    output '2 crates of eggs' instead of '2 crate of eggs'
    */
    public static String Plural(Double quantity, String measure) {

        if (quantity <= 1.0) {
            return measure;
        } else if (quantity > 1.0 && (measure.equals("CUP") || measure.equals("UNIT"))) {
            //If it is measured in either CUPs or UNITs
            measure = measure + "s";
            return measure;
        } else {
            return measure;
        }
    }

    //in Portrait mode
    public static Boolean isPortraitMode(int orientation) {
        return orientation == Configuration.ORIENTATION_PORTRAIT ? true : false;
    }

    //Check if device is currently connected
    //code snippet from www.androidhive.info
    public static boolean isConnected(Activity context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}
