package com.yst.onecity.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

/**
 * Gson解析封装类
 *
 * @author jiaofan
 * @date 2017/11/22
 * @version 1.0.1
 */

public class GsonUtil {

    private static Gson gson = null;
    static {
        if (gson == null) {
            gson = new Gson();
        }
    }

    private GsonUtil() {
    }

    /**
     * 转成Json
     *
     * @param object 对象
     * @return jsonString
     */
    public static String beanToJson(Object object) {
        String jsonString = null;
        if (gson != null) {
            jsonString = gson.toJson(object);
        }
        return jsonString;
    }

    /**
     * 转成bean
     *
     * @param jsonString json串
     * @param cls 类名
     * @return t
     */
    public static <T> T jsonToBean(String jsonString, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(jsonString, cls);
        }
        return t;
    }

    /**
     * 转成list
     *
     * @param jsonString json串
     * @param cls 类名
     * @return list
     */
    public static <T> List<T> jsonToList(String jsonString, Class<T> cls) {
        List<T> list = null;
        if (gson != null) {
            list = gson.fromJson(jsonString, new TypeToken<List<T>>() {}.getType());
        }
        return list;
    }

    /**
     * 转成map
     *
     * @param jsonString json串
     * @return map
     */
    public static <T> Map<String, T> jsonToMaps(String jsonString) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(jsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }

    /**
     * 转成list中有map的
     *
     * @param jsonString json串
     * @return list
     */
    public static <T> List<Map<String, T>> jsonToListMaps(String jsonString) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(jsonString, new TypeToken<List<Map<String, T>>>() {}.getType());
        }
        return list;
    }
}
