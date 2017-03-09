package com.app.common.util;

import android.os.Debug;
import android.util.Log;

import com.app.common.BuildConfig;

/**
 * Created by zxk on 17-3-9.
 */

public class LoggerUtil {
    private static final String TAG = "demo";

    public static void e(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, msg);
        }
    }

    public static void e(String msg) {
        e(TAG, msg);
    }

    public static void d(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void d(String msg) {
        d(TAG, msg);
    }
}
