package com.mulakat.huseyin.util;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;

import com.mulakat.huseyin.mulakat.MainActivity;

/**
 * Created by huseyin on 1.11.2017.
 */

public class utility {


    public static boolean checkInternetConnection() {
        ConnectivityManager conMgr = (ConnectivityManager) statics.getMainActivity().getSystemService (Context.CONNECTIVITY_SERVICE);
        // ARE WE CONNECTED TO THE NET
        if (conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNumeric(String str) {
        return str.matches("^(?:(?:\\-{1})?\\d+(?:\\.{1}\\d+)?)$");
    }
}
