package com.example.kevin.zhihulightread.global;


import com.example.kevin.zhihulightread.utils.LogUtils;

/**
 * 作者：Created by Kevin on 2016/1/24.
 * 邮箱：haowei0708@163.com
 * 描述：全局变量
 */
public class Constants {
    public static final int DEBUGLEVEL = LogUtils.LEVEL_ALL;
    public static final long PROTOCOLTIMEOUT =  5 * 60 * 1000*60*60;;
    public static final int PAGESIZE = 20;

    public static final class URLS{
        public static String BASEURL = "http://10.0.3.2:8080/GooglePlayServer/";
        public static String ICON = "http://10.0.3.2:8080/GooglePlayServer/image?name=";
    }

    public static final class PAY{

    }
}
