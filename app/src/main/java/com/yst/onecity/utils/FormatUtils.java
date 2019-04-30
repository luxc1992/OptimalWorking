package com.yst.onecity.utils;

import android.text.TextUtils;

import static com.yst.onecity.Constant.NO0;

/**
 * 格式化 工具类
 *
 * @author luxuchang
 * @version 1.0.1
 * @date 2018/2/9.
 */

public class FormatUtils {
    private static final int INDEX3 = 3;
    private static final int INDEX4 = 3;

    /**
     * 姓名用*显示
     *
     * @param name 名字
     * @return s 名字
     */
    public static String formatName(String name) {
        if (name.length() > NO0) {
            return "*" + name.substring(1);
        }else{
            return "";
        }
//        else if (name.length() >= NO3) {
//            return "**" + name.substring(name.length() - 1, name.length());
//        } else {
//            return "";
//        }
    }

    /**
     * 银行卡部分用*显示
     *
     * @param str s
     * @return s
     */
    public static String formatCardId(String str) {
        if (str == null) {
            return "";
        }
        String subTop;
        String subCenter;
        String subBottom;
        try {
            subTop = str.substring(0, INDEX3);
            subCenter = str.substring(INDEX3, str.length() - INDEX4);
            subBottom = str.substring(str.length() - INDEX4, str.length());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < subCenter.length(); i++) {
                sb = sb.append("*");
            }
            str = "";
            str += subTop;
            str += sb.toString();
            str += subBottom;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 规范手机号
     *
     * @param phone 手机号
     * @return s
     */
    public static String formatPhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return "";
        }
        return phone.substring(0, 3) + "****" + phone.substring(7);
    }

    /**
     * 规范手机号
     *
     * @param phone 手机号
     * @return s
     */
    public static String formaPhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return "";
        }
        return phone.substring(0, 3) + "****" + phone.substring(7);
    }
    /**
     * 规范身份证
     * @param idCard 身份证号
     * @return s
     */
    public static String formatIDCard(String idCard) {
        if (TextUtils.isEmpty(idCard)) {
            return "";
        }
        return idCard.substring(0, 3) + "**************" + idCard.substring(idCard.length()-1);
    }

    /**
     * 银行卡
     * @param bankNo 银行卡号
     * @return s
     */
    public static String formatBankNo(String bankNo) {
        if (TextUtils.isEmpty(bankNo)) {
            return null;
        }
        return "**** **** **** " + bankNo.substring(bankNo.length() - 4);
    }
}
