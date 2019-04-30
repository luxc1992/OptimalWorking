package com.yst.onecity.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * 验证类工具类
 *
 * @author luxuchang
 * @version 1.0.1
 * @date 2018/2/9.
 */

public class VerifyUtil {

    /**
     * 判断交易密码，不能是连续相同的数字
     *
     * @param pwd 密码
     * @return true 匹配成功 不符合要求  false  匹配失败  符合要求
     */
    public static boolean checkTradePwd(String pwd) {
        String regX = "^(?:([0-9])\\1{5})$";
        return pwd.matches(regX);
    }

    /**
     * 判断是不是手机号
     *
     * @param mobiles 手机号
     * @return b
     */
    public static boolean isMobileNO(String mobiles) {
        final Pattern pattern = compile("^((1[3-9][0-9])|(14[5,7])|(17[0,3,6,7,8]))\\d{8}$");
        Matcher m = pattern.matcher(mobiles);
        return m.matches();
    }
    /**
     * 判断是不是手机号
     *
     * @param loginPwd 登录密码
     * @return b
     */
    public static boolean checkLoginPwd(String loginPwd) {
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";
        final Pattern pattern = compile(regex);
        Matcher m = pattern.matcher(loginPwd);
        return m.matches();
    }

    /**
     * 判断邮箱是否合法
     * @param email 邮箱
     * @return b
     */
    public static boolean isEmail(String email){
        if (null==email || "".equals(email)) {
            return false;
        }
        Pattern p = compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 验证是否是连续的数字
     *
     * @param s str
     * @return true 是  false 不是
     */
    public static boolean isContinuityCharacter(String s) {
        boolean up = true;
        boolean down = true;
        char[] data = s.toCharArray();
        for (int i = 0; i < data.length - 1; i++) {
            int a = Integer.parseInt(String.valueOf(data[i]));
            int b = Integer.parseInt(String.valueOf(data[i + 1]));
            up = up && (a + 1 == b);
            down = down && (a - 1 == b);
        }
        return up || down;
    }
}
