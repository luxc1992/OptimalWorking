package com.yst.onecity.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 商品管理列表实体
 *
 * @author chenjiadi
 * @version 1.0.1
 * @date 2018/2/28.
 */

public class GoodsManageListBean implements Serializable {

    /**
     * code : 1
     * msg : 查询成功
     * content : [{"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180208/20180208134635883392347.png","minPrice":0.01,"location":null,"maxPrice":0.01,"title":"11111","content":"砍价商品测试","status":1},{"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180208/20180208134635883392347.png","minPrice":0.22,"location":"北京","maxPrice":0.22,"title":"砍价商品测试2","content":"砍价商品测试","status":1},{"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180208/20180208134635883392347.png","minPrice":2,"location":null,"maxPrice":2,"title":"砍价商品测试3","content":"砍价商品测试","status":1}]
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
         * address : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180208/20180208134635883392347.png
         * minPrice : 0.01
         * location : null
         * maxPrice : 0.01
         * title : 11111
         * content : 砍价商品测试
         * status : 1
         * id:1
         */

        private String address;
        private double minPrice;
        private String location;
        private double maxPrice;
        private String title;
        private String content;
        private int status;
        private boolean isChecked;

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        private String productId;
        /**
         * 点击批量管理
         */
        private boolean isclicked;
        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        public boolean isIsclicked() {
            return isclicked;
        }

        public void setIsclicked(boolean isclicked) {
            this.isclicked = isclicked;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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

        @Override
        public String toString() {
            return "ContentBean{" +
                    "address='" + address + '\'' +
                    ", minPrice=" + minPrice +
                    ", location='" + location + '\'' +
                    ", maxPrice=" + maxPrice +
                    ", title='" + title + '\'' +
                    ", content='" + content + '\'' +
                    ", status=" + status +
                    ", isChecked=" + isChecked +
                    ", productId='" + productId + '\'' +
                    ", isclicked=" + isclicked +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GoodsManageListBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", content=" + content +
                '}';
    }
}
