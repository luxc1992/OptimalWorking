package com.yst.onecity.bean.issue;

import java.io.Serializable;
import java.util.List;

/**
 * 商品的实体类
 *
 * @author zhaiyanwu
 * @version v1.0.1
 * @date 2018/3/1.
 */
public class CommodityBean implements Serializable {

    @Override
    public String toString() {
        return "CommodityBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", content=" + content +
                '}';
    }

    /**
     * code : 1
     * msg : 查询成功
     * content : [{"id":"7.0","productId":"7","name":"明日之子商品001","title":"11","type":"1","supplierId":"1","merchantId":"3","isBargain":"0","isExplosion":"0","attachment":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180208/20180208134635883392347.png","price":"2.0000","address":"null","pridctTypeId":"2","productPlan":"0","createdTime":"2018-02-07 15:17:50.0"}]
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

    public static class ContentBean implements Serializable {
        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        /**
         * id : 7.0
         * productId : 7
         * name : 明日之子商品001
         * title : 11
         * type : 1
         * supplierId : 1
         * merchantId : 3
         * isBargain : 0
         * isExplosion : 0
         * attachment : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180208/20180208134635883392347.png
         * price : 2.0000
         * address : null
         * pridctTypeId : 2
         * productPlan : 0
         * createdTime : 2018-02-07 15:17:50.0
         */

        private boolean isChecked;
        private String id;
        private String productId;
        private String name;
        private String title;
        private String type;
        private String supplierId;
        private String merchantId;
        private String isBargain;
        private String isExplosion;
        private String attachment;
        private String price;
        private String address;
        private String pridctTypeId;
        private String productPlan;
        private String createdTime;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSupplierId() {
            return supplierId;
        }

        public void setSupplierId(String supplierId) {
            this.supplierId = supplierId;
        }

        public String getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }

        public String getIsBargain() {
            return isBargain;
        }

        public void setIsBargain(String isBargain) {
            this.isBargain = isBargain;
        }

        public String getIsExplosion() {
            return isExplosion;
        }

        public void setIsExplosion(String isExplosion) {
            this.isExplosion = isExplosion;
        }

        public String getAttachment() {
            return attachment;
        }

        public void setAttachment(String attachment) {
            this.attachment = attachment;
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

        public String getPridctTypeId() {
            return pridctTypeId;
        }

        public void setPridctTypeId(String pridctTypeId) {
            this.pridctTypeId = pridctTypeId;
        }

        public String getProductPlan() {
            return productPlan;
        }

        public void setProductPlan(String productPlan) {
            this.productPlan = productPlan;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }


        @Override
        public String toString() {
            return "ContentBean{" +
                    "isChecked=" + isChecked +
                    ", id='" + id + '\'' +
                    ", productId='" + productId + '\'' +
                    ", name='" + name + '\'' +
                    ", title='" + title + '\'' +
                    ", type='" + type + '\'' +
                    ", supplierId='" + supplierId + '\'' +
                    ", merchantId='" + merchantId + '\'' +
                    ", isBargain='" + isBargain + '\'' +
                    ", isExplosion='" + isExplosion + '\'' +
                    ", attachment='" + attachment + '\'' +
                    ", price='" + price + '\'' +
                    ", address='" + address + '\'' +
                    ", pridctTypeId='" + pridctTypeId + '\'' +
                    ", productPlan='" + productPlan + '\'' +
                    ", createdTime='" + createdTime + '\'' +
                    ", minPrice='" + minPrice + '\'' +
                    ", maxPrice='" + maxPrice + '\'' +
                    '}';
        }
    }
}
