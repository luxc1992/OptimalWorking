package com.yst.im.imchatlibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SP保存数据
 *
 * @author lierpeng
 * @date 2018/04/08
 * @version 1.0.0
 */
public class SpUtils {

    public static void saveSPDates(Context context, String keyName, String value) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(keyName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(keyName, value);
        editor.commit();
    }

    public static String getSPValues(Context context, String keyName) {
        SharedPreferences sp = context.getSharedPreferences(keyName, Context.MODE_PRIVATE);
        String values = sp.getString(keyName, "");
        return values;
    }


    public static  void saveSPIntDates(Context context, String keyName, int value) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(keyName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(keyName, value);
        editor.commit();
    }

    public static int getSPIntValues(Context context, String keyName) {
        SharedPreferences sp = context.getSharedPreferences(keyName, Context.MODE_PRIVATE);
        int values = sp.getInt(keyName, 0);
        return values;
    }

    public static void saveSPBooleanDates(Context context, String keyName, boolean value) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(keyName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(keyName, value);
        editor.commit();
    }

    public static boolean getSPBooleanValues(Context context, String keyName) {
        SharedPreferences sp = context.getSharedPreferences(keyName, Context.MODE_PRIVATE);
        boolean values = sp.getBoolean(keyName, false);
        return values;
    }

   public static String getSPStringValues(Context context, String keyName,String normalTxt) {
        SharedPreferences sp = context.getSharedPreferences(keyName, Context.MODE_PRIVATE);
        String values = sp.getString(keyName, normalTxt);
        return values;
    }
}
