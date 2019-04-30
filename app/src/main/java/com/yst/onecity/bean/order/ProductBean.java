package com.yst.onecity.bean.order;

import java.io.Serializable;

/**
 * 商品实体类
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/3/1
 */
public class ProductBean implements Serializable {

    /**
     * pName : 11111
     * psName : 红色/M
     * pImg : https://ph-images.oss-cn-shenzhen.aliyuncs.com/hunter/20171020/20171020151947691523990.jpg
     */

    private String pName;
    private String psName;
    private String pImg;

    /**
     * productid : 11111
     * price : 1
     * num : 1
     * pImg : https://ph-images.oss-cn-shenzhen.aliyuncs.com/hunter/20171020/20171020151947691523990.jpg
     * productName : 11111
     * spec : 红色/M
     */
    private String productid;
    private String orderid;
    private String price;
    private int num;
    private String productAddress;
    private String productName;
    private String spec;

    public String getPName() {
        return pName;
    }

    public String getPsName() {
        return psName;
    }

    public String getPImg() {
        return pImg;
    }

    public String getpName() {
        return pName;
    }

    public String getpImg() {
        return pImg;
    }

    public String getProductid() {
        return productid;
    }

    public String getOrderid() {
        return orderid;
    }

    public String getPrice() {
        return price;
    }

    public int getNum() {
        return num;
    }

    public String getProductAddress() {
        return productAddress;
    }

    public String getProductName() {
        return productName;
    }

    public String getSpec() {
        return spec;
    }
}
