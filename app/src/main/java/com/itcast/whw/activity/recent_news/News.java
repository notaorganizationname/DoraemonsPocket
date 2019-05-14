package com.itcast.whw.activity.recent_news;

/**
 * 新闻实体类
 */
public class News {
    private String news_title;
    private String news_imgUrl;
    private String news_content;
    private int forward_count;
    private int like_count;

    public String getNews_title() {
        return news_title;
    }

    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }

    public String getNews_imgUrl() {
        return news_imgUrl;
    }

    public void setNews_imgUrl(String news_imgUrl) {
        this.news_imgUrl = news_imgUrl;
    }

    public String getNews_content() {
        return news_content;
    }

    public void setNews_content(String news_content) {
        this.news_content = news_content;
    }

    public int getForward_count() {
        return forward_count;
    }

    public void setForward_count(int forward_count) {
        this.forward_count = forward_count;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }
}
