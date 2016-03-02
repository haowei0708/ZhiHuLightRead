package com.example.kevin.zhihulightread.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 作者：Created by Kevin on 2016/1/18.
 * 邮箱：haowei0708@163.com
 * 描述：sharedPreference的工具类
 */
public class PreferenceUtil {

    public static String getStringPref(Context context, String key, String defValue) {
        SharedPreferences preferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        preferences.getString(key, defValue);
        return null;
    }

    public static void setStringPref(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        preferences.edit().putString(key, value);
    }
}
