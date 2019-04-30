package com.yst.basic.framework.utils;

import android.util.Base64;

import com.yst.basic.framework.Const;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 修改项目工程名称
 *
 * @author lixiangchao
 * @date 2017/07/19
 * @version 1.0.1
 */
public class SignUtils {

    /**
     * 十六进制数组
     */
    private static final String[] HEX_STRINGS;

    static {
        HEX_STRINGS = new String[Const.NUMBER256];
        for (int i = 0; i < Const.NUMBER256; i++) {
            StringBuilder d = new StringBuilder(2);
            char ch = Character.forDigit(((byte) i >> 4) & 0x0F, 16);
            d.append(Character.toUpperCase(ch));
            ch = Character.forDigit((byte) i & 0x0F, 16);
            d.append(Character.toUpperCase(ch));
            HEX_STRINGS[i] = d.toString();
        }
    }

    private static String[] IGNORE = new String[]{
            "sign_type", "sign_data", "encrypt_type", "encrypt_data", "salt", "signature", "sign"
    };

    /**
     * 计算签名
     * @param map 有key和value的map，使用=和&拼接所有参数，
     *            "sign_type", "sign_data", "encrypt_type", "encrypt_data"不参加计算
     * @param algorithm 签名算法 MD5, SHA-1, SHA-256
     * @param salt 签名密钥
     * @param charset 字符串编码
     * @return 签名
     */
   /* public static String sign(Map<String, String> map, String algorithm, String salt, String charset) throws UnsupportedEncodingException {
        String linkString = map2LinkString(map);
        String data = linkString + salt;
        System.out.println("signData = " + data);
        return digestHex(algorithm, data, charset);
    }*/

    /**
     * 验证签名正确性。
     * @param sign 签名数据
     * @param map 数据
     * @param algorithm 签名算法 MD5, SHA-1, SHA-256
     * @param salt 签名密钥
     * @param charset 字符串
     * @return 验证结果
     */
    /*public static boolean verify(String sign, Map<String, String> map, String algorithm, String salt,
                                 String charset) throws UnsupportedEncodingException {
        if (sign==null || "".equals(sign.trim()) || map.size()==0) return false;
        String newSign = sign(map, algorithm, salt, charset);
        return newSign.equals(sign);
    }*/

    /**
     * 将MAP数据用=和&拼接成String
     *
     * @param map 数据
     * @return 字符串
     */
    public static String map2LinkString(Map<String, String> map) {
        StringBuilder link = new StringBuilder();
        try {
            ArrayList<String> mapKeys = new ArrayList<String>(map.keySet());
            Collections.sort(mapKeys);
            boolean first = true;
            for_map_keys:
            for (String key : mapKeys) {
                String value = map.get(key);
//                if (value==null || "".equals(value.trim())) continue;
                if (value == null) {
                    continue;
                }
                for (String i : IGNORE) {
                    if (i.equalsIgnoreCase(key)) {
                        continue for_map_keys;
                    }
                }

                if (!first) {
                    link.append("&");
                }
                link.append(key).append("=").append(value);
                if (first) {
                    first = false;
                }
            }
        } catch (Exception e) {
        }
        return link.toString();
    }

    /**
     * 将MAP数据用=和&拼接成String
     *
     * @param map 数据
     * @return 字符串
     */
    public static String mapObjcetLinkString(Map<String, Object> map) {
        StringBuilder link = new StringBuilder();
        try {
            ArrayList<String> mapKeys = new ArrayList<String>(map.keySet());
            Collections.sort(mapKeys);
            boolean first = true;
            for_map_keys:
            for (String key : mapKeys) {
                Object value = map.get(key);
//                if (value==null || "".equals(value.trim())) continue;
                if (value == null) {
                    continue;
                }
                for (String i : IGNORE) {
                    if (i.equalsIgnoreCase(key)) {
                        continue for_map_keys;
                    }
                }

                if (!first) {
                    link.append("&");
                }
                link.append(key).append("=").append(value);
                if (first) {
                    first = false;
                }
            }
        } catch (Exception e) {
        }
        return link.toString();
    }

    /**
     * 将MAP数据用=和&拼接成String
     *
     * @param map 数据
     * @return 字符串
     */
    public static String mapToLinkString(Map<String, Object> map) {
        ArrayList<String> mapKeys = new ArrayList<String>(map.keySet());
        Collections.sort(mapKeys);
        StringBuilder link = new StringBuilder();
        boolean first = true;
        for_map_keys:
        for (String key : mapKeys) {
            Object obj = map.get(key);
            String value = "";
            if (obj != null) {
                value = ((String[]) obj)[0];
            }
            if ("".equals(value.trim())) {
                continue;
            }
            for (String i : IGNORE) {
                if (i.equalsIgnoreCase(key)) {
                    continue for_map_keys;
                }
            }
            if (!first) {
                link.append("&");
            }
            link.append(key).append("=").append(value);
            if (first) {
                first = false;
            }
        }
        return link.toString();
    }

    /**
     * 集合排序拼接
     *
     * @param map 集合
     * @return 返回拼接后的内容
     */
    public static String mapToLinkString2(Map<String, String[]> map) {
        ArrayList<String> mapKeys = new ArrayList<String>(map.keySet());
        Collections.sort(mapKeys);
        StringBuilder link = new StringBuilder();
        boolean first = true;
        for_map_keys:
        for (String key : mapKeys) {
            Object obj = map.get(key);
            String value = "";
            if (obj != null) {
                value = ((String[]) obj)[0];
            }
            if ("".equals(value.trim())) {
                continue;
            }
            for (String i : IGNORE) {
                if (i.equalsIgnoreCase(key)) {
                    continue for_map_keys;
                }
            }
            if (!first) {
                link.append("&");
            }
            link.append(key).append("=").append(value);
            if (first) {
                first = false;
            }
        }
        return link.toString();
    }

    /**
     * 将字节数组转换成HEX String
     *
     * @param b
     * @return HEX String
     */
    public static String hexString(byte[] b) {
        StringBuilder d = new StringBuilder(b.length * 2);
        for (byte aB : b) {
            d.append(HEX_STRINGS[(int) aB & 0xFF]);
        }
        System.out.println("sign=" + d.toString());
        return d.toString();
    }

    /**
     * map集合排序
     *
     * @param map map集合
     * @return 返回加签后的内容
     */
    public static String mapToSign(Map<String, Object> map) {
        if (null == map) {
            return "";
        }
        String sign = "";
        try {
            String signData = SignUtils.mapObjcetLinkString(map);
            String md5 = Md5Util.getMD5(signData);
            byte[] bytes = (md5 + "YGL" + "A").getBytes("utf-8");
            if (bytes != null) {
                sign = Base64.encodeToString(bytes, Base64.DEFAULT).replaceAll("\r\n", "");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sign;
    }

    /**
     * 获取时间戳 一秒级别
     *
     * @return
     */
    public static String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    /**
     * 接口验签，需要要验签的key和value
     *
     * @param strings 以键值对的方式进行验证
     * @return 返回加签后的内容
     */
    public static String getSign(String... strings) {
        if (null == strings || strings.length < 1 || strings.length % Const.TWO != 0) {
            return "";
        }
        Map<String, Object> map = new HashMap<>(16);
        for (int i = 0; i < strings.length; i += Const.TWO) {
            map.put(strings[i], strings[i + 1]);
        }
        map.put("client_type", "A");
        MyLog.e("SignUtils", mapObjcetLinkString(map));
        return SignUtils.mapToSign(map);
    }
}
