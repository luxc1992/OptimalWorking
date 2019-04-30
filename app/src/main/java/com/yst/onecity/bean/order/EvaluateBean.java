package com.yst.onecity.bean.order;

import com.yst.onecity.bean.ImageBean;

import java.util.List;

/**
 * 查看评价内容
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/3/1
 */
public class EvaluateBean {

    /**
     * isOne : 0
     * productId : 1
     * content : 这商品真是嘎嘎好啊,快点买吧
     * parentId : null
     * productSpecificationId : 1
     * fabulousNum : 0
     * replyNum : 0
     * startNum : 1
     * createdTime : 2018-3-01 02:50:23
     * anonymity : 1
     * id : 62
     */
    private int isOne;
    private String productId;
    private String content;
    private int productSpecificationId;
    private int fabulousNum;
    private int replyNum;
    private int startNum;
    private String createdTime;
    private int anonymity;
    private String id;
    private String nickName;
    private ProductBean product;
    private List<ImageBean> img;

    public int getIsOne() {
        return isOne;
    }

    public String getProductId() {
        return productId;
    }

    public String getContent() {
        return content;
    }

    public int getProductSpecificationId() {
        return productSpecificationId;
    }

    public int getFabulousNum() {
        return fabulousNum;
    }

    public int getReplyNum() {
        return replyNum;
    }

    public int getStartNum() {
        return startNum;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public int getAnonymity() {
        return anonymity;
    }

    public String getId() {
        return id;
    }

    public String getNickName() {
        return nickName;
    }

    public ProductBean getProduct() {
        return product;
    }

    public List<ImageBean> getImg() {
        return img;
    }
}
