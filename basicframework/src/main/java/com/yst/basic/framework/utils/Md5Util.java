package com.yst.basic.framework.utils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具类
 *
 * @author lixiangchao
 * @date 2017/11/28
 * @version 1.0.1
 */
public class Md5Util {
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 得到参数加密后的MD5值
     *
     * @param inStr 要加密的内容
     * @return 32byte MD5 Value
     */
    public static String getMD5(String inStr) {
        byte[] inStrBytes = inStr.getBytes(Charset.forName("UTF-8"));
        try {
            MessageDigest mMessageDigest = MessageDigest.getInstance("MD5");
            mMessageDigest.update(inStrBytes);
            byte[] mdByte = mMessageDigest.digest();
            char[] str = new char[mdByte.length * 2];
            int k = 0;
            for (int i = 0; i < mdByte.length; i++) {
                byte temp = mdByte[i];
                str[k++] = HEX_DIGITS[temp >>> 4 & 0xf];
                str[k++] = HEX_DIGITS[temp & 0xf];
            }
            return new String(str).toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
