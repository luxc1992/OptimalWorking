package com.yst.onecity.utils;

import java.util.HashMap;

import static com.yst.onecity.Constant.NO100;
import static com.yst.onecity.Constant.NO15;
import static com.yst.onecity.Constant.NO17;
import static com.yst.onecity.Constant.NO18;
import static com.yst.onecity.Constant.NO28;
import static com.yst.onecity.Constant.NO29;
import static com.yst.onecity.Constant.NO4;
import static com.yst.onecity.Constant.NO400;
import static com.yst.onecity.Constant.X;

/**
 * 功能描述
 *
 * @author luxuchang
 * @version 1.0.1
 * @date 2018/2/9.
 */

public class IsCard {
    private String codeError;

    /**
     * wi =2(n-1)(mod 11)
     */
    final int[] wi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};
    /**
     *  verify digit
     */
    final int[] vi = {1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2};
    private int[] ai = new int[18];
    private static String[] areaCode = {"11", "12", "13", "14", "15", "21", "22"
            , "23", "31", "32", "33", "34", "35", "36", "37", "41", "42", "43", "44"
            , "45", "46", "50", "51", "52", "53", "54", "61", "62", "63", "64", "65", "71", "81", "82", "91"};
    private static HashMap<String, Integer> dateMap;
    private static HashMap<String, String> areaCodeMap;

    static {
        dateMap = new HashMap<>();
        dateMap.put("01", 31);
        dateMap.put("02", null);
        dateMap.put("03", 31);
        dateMap.put("04", 30);
        dateMap.put("05", 31);
        dateMap.put("06", 30);
        dateMap.put("07", 31);
        dateMap.put("08", 31);
        dateMap.put("09", 30);
        dateMap.put("10", 31);
        dateMap.put("11", 30);
        dateMap.put("12", 31);
        areaCodeMap = new HashMap<>();
        for (String code : areaCode) {
            areaCodeMap.put(code, null);
        }
    }

    /**
     * 验证身份证位数,15位和18位身份证
     * @param code 码
     * @return boolean
     */
    public boolean verifyLength(String code) {
        int length = code.length();
        if (length == NO15 || length == NO18) {
            return true;
        } else {
            codeError = "错误：输入的身份证号不是15位和18位的";
            return false;
        }
    }

    /**
     * 判断地区码
     * @param code 码
     * @return boolean
     */
    public boolean verifyAreaCode(String code) {
        String areaCode = code.substring(0, 2);
        if (areaCodeMap.containsKey(areaCode)) {
            return true;
        } else {
            codeError = "错误：输入的身份证号的地区码(1-2位)[" + areaCode + "]不符合中国行政区划分代码规定(GB/T2260-1999)";
            return false;
        }
    }

    /**
     * 判断月份和日期
     * @param code 码
     * @return boolean
     */
    public boolean verifyBirthdayCode(String code) {
        //验证月份
        String month = code.substring(10, 12);
        boolean isEighteenCode = (18 == code.length());
        if (!dateMap.containsKey(month)) {
            codeError = "错误：输入的身份证号" + (isEighteenCode ? "(11-12位)" : "(9-10位)") + "不存在[" + month + "]月份,不符合要求(GB/T7408)";
            return false;
        }
        //验证日期
        String dayCode = code.substring(12, 14);
        Integer day = dateMap.get(month);
        String yearCode = code.substring(6, 10);
        Integer year = Integer.valueOf(yearCode);

        //非2月的情况
        if (day != null) {
            if (Integer.valueOf(dayCode) > day || Integer.valueOf(dayCode) < 1) {
                codeError = "错误：输入的身份证号" + (isEighteenCode ? "(13-14位)" : "(11-13位)") + "[" + dayCode + "]号不符合小月1-30天大月1-31天的规定(GB/T7408)";
                return false;
            }
        }
        //2月的情况
        else {
            //闰月的情况
            boolean istrue = year % NO4 == 0 && year % NO100 != 0;
            if (istrue || (year % NO400 == 0)) {
                if (Integer.valueOf(dayCode) > NO29 || Integer.valueOf(dayCode) < 1) {
                    codeError = "错误：输入的身份证号" + (isEighteenCode ? "(13-14位)" : "(11-13位)") + "[" + dayCode + "]号在" + year + "闰年的情况下未符合1-29号的规定(GB/T7408)";
                    return false;
                }
            }
            //非闰月的情况
            else {
                if (Integer.valueOf(dayCode) > NO28 || Integer.valueOf(dayCode) < 1) {
                    codeError = "错误：输入的身份证号" + (isEighteenCode ? "(13-14位)" : "(11-13位)") + "[" + dayCode + "]号在" + year + "平年的情况下未符合1-28号的规定(GB/T7408)";
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 验证身份除了最后位其他的是否包含字母
     * @param code 码
     * @return boolean
     */
    public boolean containsAllNumber(String code) {
        String str = "";
        if (code.length() == NO15) {
            str = code.substring(0, 15);
        } else if (code.length() == NO18) {
            str = code.substring(0, 17);
        }
        char[] ch = str.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            if (!(ch[i] >= '0' && ch[i] <= '9')) {
                codeError = "错误：输入的身份证号第" + (i + 1) + "位包含字母";
                return false;
            }
        }
        return true;
    }

    public String getCodeError() {
        return codeError;
    }

    /**
     * 验证身份证
     * @param idCard 身份证号码
     * @return boolean
     */
    public boolean verify(String idCard) {
        codeError = "";
        //验证身份证位数,15位和18位身份证
        if (!verifyLength(idCard)) {
            return false;
        }
        //验证身份除了最后位其他的是否包含字母
        if (!containsAllNumber(idCard)) {
            return false;
        }

        //如果是15位的就转成18位的身份证
        String fifteenCard = "";
        if (idCard.length() == NO15) {
            fifteenCard = uptoeighteen(idCard);
        } else {
            fifteenCard = idCard;
        }
        //验证身份证的地区码
        if (!verifyAreaCode(fifteenCard)) {
            return false;
        }
        //判断月份和日期
        if (!verifyBirthdayCode(fifteenCard)) {
            return false;
        }
        //验证18位校验码,校验码采用ISO 7064：1983，MOD 11-2 校验码系统
        return verifyMOD(fifteenCard);
    }

    /**
     * 验证18位校验码,校验码采用ISO 7064：1983，MOD 11-2 校验码系统
     * @param code 码
     * @return boolean
     */
    public boolean verifyMOD(String code) {
        String verify = code.substring(17, 18);
        if (X.equals(verify)) {
            code = code.replaceAll("x", "X");
            verify = "X";
        }
        String verifyIndex = getVerify(code);
        if (verify.equals(verifyIndex)) {
            return true;
        }
        codeError = "错误：输入的身份证号最末尾的数字验证码错误";
        return false;
    }

    /**
     * 获得校验位
     * @param eighteenCardId
     * @return boolean
     */
    public String getVerify(String eighteenCardId) {
        int remaining = 0;

        if (eighteenCardId.length() == NO18) {
            eighteenCardId = eighteenCardId.substring(0, 17);
        }

        if (eighteenCardId.length() == NO17) {
            int sum = 0;
            for (int i = 0; i < NO17; i++) {
                String k = eighteenCardId.substring(i, i + 1);
                ai[i] = Integer.parseInt(k);
            }

            for (int i = 0; i < NO17; i++) {
                sum = sum + wi[i] * ai[i];
            }
            remaining = sum % 11;
        }

        return remaining == 2 ? "X" : String.valueOf(vi[remaining]);
    }

    /**
     * 15位转18位身份证
     * @param fifteenCardId
     * @return boolean
     */
    public String uptoeighteen(String fifteenCardId) {
        String eighteenCardId = fifteenCardId.substring(0, 6);
        eighteenCardId = eighteenCardId + "19";
        eighteenCardId = eighteenCardId + fifteenCardId.substring(6, 15);
        eighteenCardId = eighteenCardId + getVerify(eighteenCardId);
        return eighteenCardId;
    }
}
