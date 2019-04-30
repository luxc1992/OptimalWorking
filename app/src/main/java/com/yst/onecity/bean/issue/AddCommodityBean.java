package com.yst.onecity.bean.issue;

import java.io.Serializable;

/**
 * @author zhaiyanwu
 * @version v3.0.1
 * @date 2018/3/12.
 */
public class AddCommodityBean implements Serializable{

    private int code;
    private String msg;
    private ContentBean content;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "AddCommodityBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", content=" + content +
                '}';
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public class ContentBean implements Serializable{
        private int productId;
        private String attachment;
        private String name;
        private String title;
        private String price;
        private String address;
        private String minPrice;
        private String maxPrice;

        public String getMinPrice() {
            return minPrice;
        }

        public void setMinPrice(String minPrice) {
            this.minPrice = minPrice;
        }

        public String getMaxPrice() {
            return maxPrice;
        }

        public void setMaxPrice(String maxPrice) {
            this.maxPrice = maxPrice;
        }

        @Override
        public String toString() {
            return "ContentBean{" +
                    "productId=" + productId +
                    ", attachment='" + attachment + '\'' +
                    ", name='" + name + '\'' +
                    ", title='" + title + '\'' +
                    ", price=" + price +
                    ", address='" + address + '\'' +
                    '}';
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public String getAttachment() {
            return attachment;
        }

        public void setAttachment(String attachment) {
            this.attachment = attachment;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }


}
