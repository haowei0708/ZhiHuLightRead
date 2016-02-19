package com.example.kevin.zhihulightread.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;

import com.example.kevin.zhihulightread.base.BaseApplication;


/**
 * 作者：Created by Kevin on 2016/1/24.
 * 邮箱：haowei0708@163.com
 * 描述：UI相关的工具类
 */
public class UIUtils {

    public static String getPackageName() {
        return getContext().getPackageName();
    }

    /**
     * 得到上下文
     * @return
     */
    public static Context getContext() {
        return BaseApplication.getContext();
    }

    /**
     * 得到Resources对象
     * @return
     */
    public static Resources getResoure(){
        return getContext().getResources();
    }

    /**
     * 得到String.xml中的字符串
     * @param resId
     * @return
     */
    public static String getString(int resId) {
        return getResoure().getString(resId);
    }

    /**
     * 得到String.xml中的字符串带占位符
     * @param resId
     * @return
     */
    public static String getString(int resId, Object... formatArgs) {
        return getResoure().getString(resId,formatArgs);
    }

    /**
     * 得到String.xml中的字符串数组
     * @param resId
     * @return
     */
    public static String[] getStringArr(int resId) {
        return getResoure().getStringArray(resId);
    }

    /**
     * 得到Color.xml中的颜色
     * @param colorId
     * @return
     */
    public static int getColor(int colorId) {
        return getResoure().getColor(colorId);
    }

    /**
     * 得到当前主线程ID
     * @return
     */
    public static long getMainThreadId(){
        return BaseApplication.getMainThreadId();
    }

    /**
     * 得到主线程的Handler
     * @return
     */
    public static Handler getMainThreadHandler(){
        return BaseApplication.getHandler();
    }

    /**
     * 安全的执行一个任务
     * @param task
     */
    public static void postSafely(Runnable task){
        //得到当前线程的id
        int curThreadId = android.os.Process.myTid();
        //如果当前线程为主线程
        if (curThreadId == getMainThreadId()){
            task.run();
        }else {//如果当前线程不是主线程
            getMainThreadHandler().post(task);
        }
    }

    /**
     * 安全的执行一个延时任务
     * @param task
     */
    public static void postSafelyDelayed(Runnable task,int delayMillis){
        getMainThreadHandler().postDelayed(task, delayMillis);
    }

    public static void removeTast(Runnable task){
        getMainThreadHandler().removeCallbacks(task);
    }

}
