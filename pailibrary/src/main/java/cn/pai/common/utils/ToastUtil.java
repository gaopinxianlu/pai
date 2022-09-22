package cn.pai.common.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @author pany
 * @description
 * @date 2017年4月7日上午11:41:51
 */
public class ToastUtil {

    public static void show(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void show(Context context, String text, int duration) {
        Toast.makeText(context, text, duration).show();
    }
}
