package com.yst.im.imchatlibrary.bean;

import java.io.Serializable;

/**
 * 分享模板
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/2/8
 */

public class WebViewShareEntity implements Serializable{
    private String title;
    private String content;
    private String imgUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "WebViewShareEntity{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
