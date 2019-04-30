package com.yst.onecity.bean;

import java.io.Serializable;

/**
 * 首页咨询分享 h5返回分享内容实体
 *
 * @author chenjiadi
 * @version 1.0.1
 * @date 2018/3/3.
 */

public class HomeInfoShareBean implements Serializable {
    /**
     * type：类型  0 是右上角三个点  1是下边转发按钮
     * url:,路径
     * newsId:资讯id
     * title:资讯标题
     * publisher:,发布人id
     * memberId:分享人id
     * content：资讯内容
     * isCollect：0 0是未收藏，1是已收藏
     */
    private String type;
    private String url;
    private String newsId;
    private String title;
    private String publisher;
    private String memberId;
    private String content;

    public String getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(String isCollect) {
        this.isCollect = isCollect;
    }

    private String isCollect;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
