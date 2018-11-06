package com.yubin.wanapp.util;

import com.blankj.utilcode.util.SPUtils;

import java.util.Map;

/**
 * author : Yubin.Ying
 * time : 2018/11/1
 */
public class BasePreference {

    private static final SPUtils SP_UTILS = SPUtils.getInstance("wanapp");

    public static void putString(String key,String value) {
        SP_UTILS.put(key, value);
    }

    public static String getString(String key) {
        return SP_UTILS.getString(key);
    }

    public static void putInt(String key,int value) {
        SP_UTILS.put(key, value);
    }

    public static int getInt(String key) {
        return SP_UTILS.getInt(key);
    }

    public static void putLong(String key,long value) {
        SP_UTILS.put(key, value);
    }

    public static Long getLong(String key) {
        return SP_UTILS.getLong(key);
    }

    public static void putFloat(String key,float value) {
        SP_UTILS.put(key, value);
    }

    public static Float getFloat(String key) {
        return SP_UTILS.getFloat(key);
    }

    public static void putBoolean(String key,boolean value) {
        SP_UTILS.put(key, value);
    }

    public static boolean getBoolean(String key) {
        return SP_UTILS.getBoolean(key);
    }

    public static boolean getBoolean(String key,boolean defaultvalue) {
        return SP_UTILS.getBoolean(key,defaultvalue);
    }

    public static void clear() {
        SP_UTILS.clear();
    }

    public static String sp2String() {
        StringBuilder sb = new StringBuilder();
        Map<String, ?> map = SP_UTILS.getAll();
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            sb.append(entry.getKey())
                    .append(": ")
                    .append(entry.getValue())
                    .append("\n");
        }
        return sb.toString();
    }

}


