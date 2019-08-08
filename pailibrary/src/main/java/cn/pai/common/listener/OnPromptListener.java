package cn.pai.common.listener;

import android.content.DialogInterface;

/**
 * description：
 * author：pany
 * on 2018/6/11 10:45
 */
public class OnPromptListener {
    public String positive;
    public String negative;

    public OnPromptListener(String positive, String negative) {
        this.positive = positive;
        this.negative = negative;
    }

    public OnPromptListener(String positive) {
        this.positive = positive;
    }

    public void onPositive(DialogInterface dialog, int which) {

    }

    public void onNegative(DialogInterface dialog, int which) {

    }
}
