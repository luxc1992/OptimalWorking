package com.yst.onecity.bean;

/**
 * 标题图片实体
 *
 * @author chenjiadi
 * @version 1.0.1
 * @date 2018/2/27
 */
public class ConsultItemBean {
    /**
     * 需要上传的图片ID
     */
    public int attachment_id;
    public String content;
    public int impose;
    public String address;
    public int types;

    /**
     * 0是文本，2是图片
     */
    public int type;
    /**
     * 默认不加载本地图片
     */
    public boolean isLocalImg;
    /**
     * 资讯模块选中图片ID
     */
    public int contentId;

    /**
     * zhaiyanwu   判断是否是视频
     * @return
     */
    public int getTypes() {
        return types;
    }

    public void setTypes(int types) {
        this.types = types;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public int getImpose() {
        return impose;
    }

    public void setImpose(int impose) {
        this.impose = impose;
    }

    public boolean isLocalImg() {
        return isLocalImg;
    }

    public void setLocalImg(boolean localImg) {
        isLocalImg = localImg;
    }

    public int getId() {
        return attachment_id;
    }

    public void setId(int id) {
        this.attachment_id = id;
    }

    public String getmPhotoPath() {
        return address;
    }

    public void setmPhotoPath(String mPhotoPath) {
        this.address = mPhotoPath;
    }

    public String getmTextContent() {
        return content;
    }

    public void setmTextContent(String mTextContent) {
        this.content = mTextContent;
    }

    public int getmType() {
        return type;
    }

    public void setmType(int mType) {
        this.type = mType;
    }

}
