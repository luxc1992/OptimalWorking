package com.yst.onecity.utils;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

/**
 * 解析工具类
 *
 * @author LianJinXue
 * @version 1.0.1
 * @date 2018/2/23
 */
public class JsonUtil {
	private static Gson gson = new Gson();

	@SuppressWarnings("hiding")
	public static <T> T parseJson(String response, Class<T> clazz) {
		try {
			return gson.fromJson(response, clazz);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static <T> T parseJson(String response, Type type) {
		try {
			return gson.fromJson(response, type);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String toJson(Object object) {
		try {
			return gson.toJson(object);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static String stringToJson(Map<String,Object> params){

        JSONObject jsonObject =new JSONObject();
        if (params != null && params.size() > 0) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                String value = (String) params.get(key);
                try {
                    jsonObject.put(key,value);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        return jsonObject.toString();
	}
}