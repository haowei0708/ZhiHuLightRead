package com.example.kevin.zhihulightread.utils;

import android.view.View;

import com.lidroid.xutils.BitmapUtils;

/**
 * 作者：Created by Kevin on 2016/1/30.
 * 邮箱：haowei0708@163.com
 * 描述：快速构造bitmap
 */
public class BitmapHelper {
    public static BitmapUtils mBitmapUtils;

    static {
        mBitmapUtils  = new BitmapUtils(UIUtils.getContext());
    }

    public static <T extends View> void display(T container, String uri) {
        mBitmapUtils.display(container,uri);
    }
}
