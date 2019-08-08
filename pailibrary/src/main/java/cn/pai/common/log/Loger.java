package cn.pai.common.log;

import android.util.Log;

/**
 * 日志
 * author：pany
 * on 2017/10/25 00:16
 */
public class Loger {

    public static boolean debug = true;
    public static final String TAG = "pai";

    public static void p(String msg) {
        if (debug){
            Log.i(TAG, msg);
        }
    }

    public static void i(String msg) {
        if (debug){
            Log.i(TAG, msg);
        }
    }

    public static void d(String msg) {
        if (debug){
            Log.d(TAG, msg);
        }
    }

    public static void e(String msg) {
        if (debug){
            Log.e(TAG, msg);
        }
    }
}
