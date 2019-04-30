package com.yst.onecity.bean;

/**
 * 好友砍价商品列表适配器
 *
 * @author liumanqing
 * @version 1.0.1
 * @date 2018/2/7
 */

public class FriendsBargainItemBean {
    private String imgurl;
    private String goodsId;
    private String goodsName;
    private String price;
    private String num;

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
