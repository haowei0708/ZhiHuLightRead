package com.example.kevin.zhihulightread.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

/**
 * 作者：Created by Kevin on 2016/1/24.
 * 邮箱：haowei0708@163.com
 * 描述：定义一个全局的盒子，放置的属性和方法都是全局可以调用
 */
public class BaseApplication extends Application {

    private static Context context;
    private static Thread mainThread;
    private static int mainThreadId;
    private static Looper mainLooper;
    private static Handler handler;


    @Override
    public void onCreate() {//程序的入口
        //常用的属性
        context = getApplicationContext();

        //主线程
        mainThread = Thread.currentThread();

        //主线程ID
        mainThreadId = android.os.Process.myPid();

        mainLooper = getMainLooper();

        handler = new Handler();

        super.onCreate();
    }

    public static Context getContext() {
        return context;
    }

    public static Thread getMainThread() {
        return mainThread;
    }

    public static int getMainThreadId() {
        return mainThreadId;
    }

    public static Looper getMainThreadLooper() {
        return mainLooper;
    }

    public static Handler getHandler() {
        return handler;
    }


}
