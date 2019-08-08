package cn.pai.common.utils;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * description：金额工具
 * author：pany
 * on 2018/1/17 16:17
 */
public class AmtUtils {

    /**
     * 格式化 元
     * 保留两位小数
     *
     * @param amt
     * @return
     */
    public static String formatY(String amt) {
        if (amt == null || TextUtils.isEmpty(amt)) {
            return null;
        }
        return formatY(new BigDecimal(amt));
    }

    /**
     * 格式化 元
     * 保留两位小数
     *
     * @param amt
     * @return
     */
    public static String formatY(BigDecimal amt) {
        if (amt == null) {
            return null;
        }
        return new DecimalFormat("0.00").format(amt);
    }

    /**
     * 格式化 分
     * 去掉小数点后面
     *
     * @param amt
     * @return
     */
    public static String formatF(String amt) {
        if (amt == null || TextUtils.isEmpty(amt)) {
            return null;
        }
        return formatF(new BigDecimal(amt));
    }

    /**
     * 格式化 分
     * 去掉小数点后面
     *
     * @param amt
     * @return
     */
    public static String formatF(BigDecimal amt) {
        if (amt == null) {
            return null;
        }
        return new DecimalFormat("0").format(amt);
    }

    /**
     * 将分为单位的转换为元 （除100）
     * 且保留两位小数
     *
     * @param amt
     * @return
     */
    public static String changeF2Y(String amt) {
        if (!TextUtils.isEmpty(amt) && !amt.equalsIgnoreCase("null") && !amt.contains(".")) {
            return formatY(new BigDecimal(amt).divide(new BigDecimal(100)));
        }
        return amt;
    }

    /**
     * 将分为单位的转换为元 （除100）
     * 且保留两位小数
     *
     * @param amt
     * @return
     */
    public static String changeF2Y(BigDecimal amt) {
        if (amt != null) {
            return formatY(amt.divide(new BigDecimal(100)));
        }
        return null;
    }

    /**
     * 将元为单位的转换为分 （乘100）
     * 且去掉小数点
     *
     * @param amt
     * @return
     */
    public static String changeY2F(String amt) {
        if (!TextUtils.isEmpty(amt) && !amt.equalsIgnoreCase("null")) {
            return formatF(new BigDecimal(amt).multiply(new BigDecimal(100)));
        }
        return amt;
    }

    /**
     * 将元为单位的转换为分 （乘100）
     * 且去掉小数点
     *
     * @param amt
     * @return
     */
    public static String changeY2F(BigDecimal amt) {
        if (amt != null) {
            return formatF(amt.multiply(new BigDecimal(100)));
        }
        return null;
    }

    /**
     * 12为分为单位
     *
     * @param amount
     * @return
     */
    public static String changeF12(String amount) {
        if (TextUtils.isEmpty(amount)) {
            return amount;
        }
        String[] part = amount.split("\\.");
        switch (part.length) {
            case 1:
                return ("0000000000" + amount + "00").substring(amount.length());
            case 2:
                String digit = part[1].length() >= 2 ? part[1].substring(0, 2)
                        : (part[1] + "00").substring(0, 2);
                amount = "000000000000" + part[0] + digit;
                return amount.substring(amount.length() - 12);
        }
        return amount;
    }

    /**
     * 转成负数，并保留两位小数据
     *
     * @return
     */
    public static String formatNegY(String amt) {
        if (!TextUtils.isEmpty(amt)) {
            return formatY(new BigDecimal(-1).multiply(new BigDecimal(amt)));
        }
        return amt;
    }

    /**
     * 转成负数，并保留两位小数据
     *
     * @return
     */
    public static String formatNegY(BigDecimal amt) {
        if (amt != null) {
            return formatY(new BigDecimal(-1).multiply(amt));
        }
        return null;
    }

    /**
     * 将分为单位的转换为元 （除100）
     * 且保留两位小数
     * 转为负数
     * @param amt
     * @return
     */
    public static String changeNegF2Y(String amt) {
        if (!TextUtils.isEmpty(amt) && !amt.equalsIgnoreCase("null") && !amt.contains(".")) {
            return formatY(new BigDecimal(amt).divide(new BigDecimal(-100)));
        }
        return amt;
    }

    /**
     * 将分为单位的转换为元 （除100）
     * 且保留两位小数
     * 转为负数
     * @param amt
     * @return
     */
    public static String changeNegF2Y(BigDecimal amt) {
        if (amt != null) {
            return formatY(amt.divide(new BigDecimal(-100)));
        }
        return null;
    }
}
