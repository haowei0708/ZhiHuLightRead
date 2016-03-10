package com.example.kevin.zhihulightread.model;


import java.util.List;

/**
 * 作者：Created by Kevin on 2016/2/20.
 * 邮箱：haowei0708@163.com
 * 描述：新闻详情页的bean,
 */
public class NewsDetailBean {
    private String body;
    private List<String> css;//   Array
    private String ga_prefix;//   050615
    private String id;//          3892357
    private String image;//        http://p4.zhimg.com/30/59/30594279d368534c6c2f91b2c00c7806.jpg
    private String image_source;// 应该是图片来源 Angel Abril Ruiz / CC BY
    private String share_url;//    我靠，还有分享的链接  http://daily.zhihu.com/story/3892357
    private String title;//        标题：卖衣服的新手段：把耐用品变成「不停买新的」
    private String type;//          0

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<String> getCss() {
        return css;
    }

    public void setCss(List<String> css) {
        this.css = css;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
