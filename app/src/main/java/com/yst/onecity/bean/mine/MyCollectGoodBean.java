package com.yst.onecity.bean.mine;

import java.util.List;

/**
 * 我的收藏-商品
 *
 * @author liumanqing
 * @version 1.0.1
 * @date 2018/03/03
 */

public class MyCollectGoodBean {

    /**
     * code : 1
     * msg : 查询成功
     * content : [{"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180315/20180315135547465830848.jpg","productId":1970,"minPrice":0.1,"location":null,"maxPrice":0.11,"title":"花加01","content":"花加","status":1}]
     */

    private int code;
    private String msg;
    private List<ContentBean> content;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * address : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180315/20180315135547465830848.jpg
         * productId : 1970
         * minPrice : 0.1
         * location : null
         * maxPrice : 0.11
         * title : 花加01
         * content : 花加
         * status : 1
         */

        private String address;
        private int productId;
        private double minPrice;
        private String location;
        private double maxPrice;
        private String title;
        private String content;
        private int status;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public double getMinPrice() {
            return minPrice;
        }

        public void setMinPrice(double minPrice) {
            this.minPrice = minPrice;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public double getMaxPrice() {
            return maxPrice;
        }

        public void setMaxPrice(double maxPrice) {
            this.maxPrice = maxPrice;
        }

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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
