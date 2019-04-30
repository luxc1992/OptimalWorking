package com.yst.im.imchatlibrary.bean;

import android.graphics.Point;

/**
 * 模板消息实体类   （type = 51）
 *
 * @author Lierpeng
 * @version 1.0.1
 * @date 2018/2/6.
 */
public class TemplateMessageEntity {
    /**
     * 分享内容图片地址
     */
    private String imgUrl;
    /**
     *(0,0)左上角坐标
     */
    private Point point;
    /**
     *top   bottom  left  right 模板消息类型
     * 1  2  3  4      5  6  7  8
     */
    private String templateType;
    /**
     *模板消息标题
     */
    private String templateTitle;
    /**
     *模板消息内容
     */
    private String templateContent;

    /**
     *分享来源 -应用名
     */
    private String shareSource;

    /**
     *分享来源图片-应用图片
     */
    private String shareSourceImgUrl;
    /**
     * 模块
     */
    private String moduleSource;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public String getTemplateTitle() {
        return templateTitle;
    }

    public void setTemplateTitle(String templateTitle) {
        this.templateTitle = templateTitle;
    }

    public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent;
    }

    public String getShareSource() {
        return shareSource;
    }

    public void setShareSource(String shareSource) {
        this.shareSource = shareSource;
    }

    public String getShareSourceImgUrl() {
        return shareSourceImgUrl;
    }

    public void setShareSourceImgUrl(String shareSourceImgUrl) {
        this.shareSourceImgUrl = shareSourceImgUrl;
    }

    public String getModuleSource() {
        return moduleSource;
    }

    public void setModuleSource(String moduleSource) {
        this.moduleSource = moduleSource;
    }

    @Override
    public String toString() {
        return "TemplateMessageEntity{" +
                "imgUrl='" + imgUrl + '\'' +
                ", point=" + point +
                ", templateType='" + templateType + '\'' +
                ", templateTitle='" + templateTitle + '\'' +
                ", templateContent='" + templateContent + '\'' +
                ", shareSource='" + shareSource + '\'' +
                ", shareSourceImgUrl='" + shareSourceImgUrl + '\'' +
                '}';
    }
}
