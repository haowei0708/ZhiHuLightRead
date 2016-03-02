package com.example.kevin.zhihulightread.utils;

import android.content.Context;

/**
 * 作者：Created by Kevin on 2016/1/18.
 * 邮箱：haowei0708@163.com
 * 描述：缓存的工具类
 */
public class CacheUtil {

    /**
     * 设置缓存
     * key是url，缓存的内容是json
     */
    public static void setCache(Context context, String key, String defValue) {
        PreferenceUtil.setStringPref(context, key, defValue);
    }

    /**
     * 获取缓存信息
     */
    public static String getCache(Context context, String key) {
        return PreferenceUtil.getStringPref(context, key, null);
    }
}
