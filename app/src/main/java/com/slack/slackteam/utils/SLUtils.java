package com.slack.slackteam.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.slack.slackteam.R;

/**
 * Created by jacobkoikkara on 8/11/15.
 */
public class SLUtils {


    public static final boolean ENABLE_TOAST = true;
    public static final boolean ENABLE_LOG = true;

    public static final String TAG_SL = "SLACK";

    public static Toast mToast = null;


    public static final boolean hasNetworkSupport(Context ctx) {
        PackageManager packageManager = ctx.getPackageManager();
        return (packageManager
                .hasSystemFeature(PackageManager.FEATURE_LOCATION_NETWORK));
    }

    /**
     * Detects the availability of a working network connection to open a
     * http/https connection.
     *
     * @param context The context of the activity who is trying to check for the
     *                status of the network.
     * @return true if network is available, false otherwise.
     */

    public static boolean isNetworkAvailable(Context context) {
        if (context == null)
            return false;

        ConnectivityManager conMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMgr != null) {
            NetworkInfo[] info = conMgr.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i] != null
                            && (info[i].getState() == NetworkInfo.State.CONNECTED))
                        return true;
                }
            }
        }
        return false;
    }

    /**
     * Detects the availability of a working Wifi network connection to open a
     * http/https connection.
     *
     * @param context The context of the activity who is trying to check for the
     *                status of the network.
     * @return true if Wifi network is available, false otherwise.
     */
    public static boolean isWiFiAvailable(Context context) {
        if (context == null)
            return false;

        ConnectivityManager conMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetwork = conMgr
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Check Network provider is enabled for location update
     *
     * @param context
     * @return if network provider enabled
     */
    public static boolean isNetworkProviderEnabled(Context context) {
        final LocationManager manager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);

        return manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    /**
     * Simple network connection check.
     *
     * @param context
     */
    public static boolean isNetworkConnected(Context context) {
        final ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnectedOrConnecting()) {
            return false;
        }
        return true;
    }

    /**
     * Method for showing toast messages.
     *
     * @param context
     * @param message
     */
    public static void showToast(Context context, String message) {


        if (ENABLE_TOAST) {
            if (mToast == null) {
                mToast = Toast.makeText(context, "", Toast.LENGTH_LONG);
            }
            mToast.setText(message);
            mToast.show();
        }

    }

    /**
     * Method for showing toast messages.
     *
     * @param context
     * @param resId
     */
    public static void showToast(Context context, int resId) {


        if (ENABLE_TOAST) {
            if (mToast == null) {
                mToast = Toast.makeText(context, "", Toast.LENGTH_LONG);
            }
            mToast.setText(context.getResources().getText(resId));
            mToast.show();
        }

    }

    /**
     * Method for showing Log messages.
     *
     * @param message
     */
    public static void showLog(String tag, Object message) {


        if (ENABLE_LOG) {
            if (tag == null || tag.isEmpty()) {
                Log.d(TAG_SL, String.valueOf(message));
            } else {
                Log.d(tag, String.valueOf(message));
            }
        }

    }

    /**
     * Method for showing Log messages.
     *
     * @param message
     */
    public static void showLog(String message) {

        showLog(TAG_SL, message);

    }

    /**
     * Method for showing Log messages.
     *
     * @param message
     */
    public static void showLog(Object message) {

        showLog(TAG_SL, String.valueOf(message));

    }


    public static boolean hasFroyo() {
        // Can use static final constants like FROYO, declared in later versions
        // of the OS since they are inlined at compile time. This is guaranteed behavior.
        return Build.VERSION.SDK_INT >= VERSION_CODES.FROYO;
    }

    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD;
    }

    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB;
    }

    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB_MR1;
    }

    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN;
    }

    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= VERSION_CODES.KITKAT;
    }

}
