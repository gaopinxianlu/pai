package cn.pai.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * description：SharedPreferences的Dao
 * author：pany
 * on 2019/3/6 17:30
 */
public class SharePreferUtil {

    private static final String NAME = "App_SharedPreferences";

    public static SharedPreferences getSharedPreferences(Context context, String name, int mode) {
        return context.getSharedPreferences(name, mode);
    }

    public static SharedPreferences getSharedPreferences(Context context, String name) {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }


    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getEditor(Context context) {
        return getSharedPreferences(context).edit();
    }

    public static String get(Context context, String key, String defValue) {
        // Context.MODE_PRIVATE代表该文件是私有数据,只能被应用本身访问,写入的内容会覆盖原文件的内容
        return getSharedPreferences(context).getString(key, defValue);
    }

    public static int get(Context context, String key, int defValue) {
        // Context.MODE_PRIVATE代表该文件是私有数据,只能被应用本身访问,写入的内容会覆盖原文件的内容
        return getSharedPreferences(context).getInt(key, defValue);
    }

    public static long get(Context context, String key, long defValue) {
        // Context.MODE_PRIVATE代表该文件是私有数据,只能被应用本身访问,写入的内容会覆盖原文件的内容
        return getSharedPreferences(context).getLong(key, defValue);
    }

    public static float get(Context context, String key, float defValue) {
        // Context.MODE_PRIVATE代表该文件是私有数据,只能被应用本身访问,写入的内容会覆盖原文件的内容
        return getSharedPreferences(context).getFloat(key, defValue);
    }

    public static boolean get(Context context, String key, boolean defValue) {
        // Context.MODE_PRIVATE代表该文件是私有数据,只能被应用本身访问,写入的内容会覆盖原文件的内容
        return getSharedPreferences(context).getBoolean(key, defValue);
    }

    public static Map<String, ?> getAll(Context context) {
        // Context.MODE_PRIVATE代表该文件是私有数据,只能被应用本身访问,写入的内容会覆盖原文件的内容
        return getSharedPreferences(context).getAll();
    }

    public static boolean put(Context context, String key, String value) {
        // Context.MODE_PRIVATE代表该文件是私有数据,只能被应用本身访问,写入的内容会覆盖原文件的内容
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(key, value);
        return editor.commit();
    }

    public static boolean put(Context context, String key, int value) {
        // Context.MODE_PRIVATE代表该文件是私有数据,只能被应用本身访问,写入的内容会覆盖原文件的内容
        SharedPreferences.Editor editor = getEditor(context);
        editor.putInt(key, value);
        return editor.commit();
    }

    public static boolean put(Context context, String key, long value) {
        // Context.MODE_PRIVATE代表该文件是私有数据,只能被应用本身访问,写入的内容会覆盖原文件的内容
        SharedPreferences.Editor editor = getEditor(context);
        editor.putLong(key, value);
        return editor.commit();
    }

    public static boolean put(Context context, String key, float value) {
        // Context.MODE_PRIVATE代表该文件是私有数据,只能被应用本身访问,写入的内容会覆盖原文件的内容
        SharedPreferences.Editor editor = getEditor(context);
        editor.putFloat(key, value);
        return editor.commit();
    }

    public static boolean put(Context context, String key, boolean value) {
        // Context.MODE_PRIVATE代表该文件是私有数据,只能被应用本身访问,写入的内容会覆盖原文件的内容
        SharedPreferences.Editor editor = getEditor(context);
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public static boolean remove(Context context, String key) {
        // Context.MODE_PRIVATE代表该文件是私有数据,只能被应用本身访问,写入的内容会覆盖原文件的内容
        SharedPreferences.Editor editor = getEditor(context);
        editor.remove(key);
        return editor.commit();
    }
}
