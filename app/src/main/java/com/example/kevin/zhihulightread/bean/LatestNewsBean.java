package com.example.kevin.zhihulightread.bean;

import java.util.List;

/**
 * 作者：Created by Kevin on 2016/2/19.
 * 邮箱：haowei0708@163.com
 * 描述：最新新闻的bean
 */
public class LatestNewsBean {

    public List<Stories> stories;
    public List<Top_stories> top_stories;

    public class Stories {
        public String ga_prefix;//	021922
        public String id;// 7873017
        public List<String> images;//	Array
        public String title;// 标题，深夜惊奇
        public String type;// 0   ，乎日报可能将某个主题日报的站外文章推送至知乎日报首页时type为1
    }



    public class Top_stories{
       public String  ga_prefix	;//  021920
       public String  id	    ;//  7894651
       public String  image	    ;// http://pic4.zhimg.com/f4f827d17bed5677436a6c5c2413ee2f.jpg
       public String  title	    ;// 这么多叫《XX 挑战》的综艺节目霸屏，到底算不算抄袭？
       public String  type	    ;//  0
    }
}
