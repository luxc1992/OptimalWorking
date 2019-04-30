package com.yst.onecity.bean;

/**
 * 图片实体类
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/2/7
 */
public class ImageBean {
    private String imageId;
    private String address;

    public ImageBean(String address) {
        this.address = address;
    }

    public String getImageId() {
        return imageId;
    }

    public String getAddress() {
        return address;
    }
}
