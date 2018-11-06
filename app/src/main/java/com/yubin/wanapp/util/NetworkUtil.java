package com.yubin.wanapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * author : Yubin.Ying
 * time : 2018/11/2
 */
public class NetworkUtil {
    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager manager = (ConnectivityManager) context
                .getApplicationContext().getSystemService(
                        Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        if (networkinfo == null || !networkinfo.isAvailable()) {
            return false;
        }
        return true;
    }
}
