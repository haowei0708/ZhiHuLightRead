package com.example.kevin.zhihulightread.global;


import com.example.kevin.zhihulightread.utils.LogUtils;

/**
 * 作者：Created by Kevin on 2016/1/24.
 * 邮箱：haowei0708@163.com
 * 描述：全局变量
 */
public class Constants {
    public static final int DEBUGLEVEL = LogUtils.LEVEL_ALL;
    public static final long PROTOCOLTIMEOUT =  1000*60*60*24*5;//保存5天
    public static final int PAGESIZE = 20;

    public static final class URLS{
        public static String BASEURL = "http://news-at.zhihu.com/api/4/";
        public static String NEWSURL = "http://news-at.zhihu.com/api/4/news/";
        public static String BEFORENEWSURL = "http://news.at.zhihu.com/api/4/news/before/";

    }

}
