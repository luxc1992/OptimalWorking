package com.yst.onecity.bean;

import java.util.List;

/**
 * 功能描述
 *
 * @author luxuchang
 * @version 1.0.1
 * @date 2018/3/8.
 */

public class ProductBean {

    /**
     * code : 1
     * msg : 获取成功
     * content : [{"id":"1.0","productId":"1","name":"333333311111111111","title":"好","type":"0","supplierId":"1","merchantId":"36","isBargain":"1","isExplosion":"1","attachment":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180208/20180208134635883392347.png","price":"0.2424","address":"null","pridctTypeId":"16","productPlan":"0","createdTime":"2018-02-07 10:35:58.0"},{"id":"2.0","productId":"2","name":"333333333322222222","title":"11","type":"0","supplierId":"1","merchantId":"36","isBargain":"1","isExplosion":"1","attachment":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180208/20180208134635883392347.png","price":"0.2222","address":"null","pridctTypeId":"1","productPlan":"0","createdTime":"2018-02-07 10:39:33.0"},{"id":"3.0","productId":"3","name":"砍价商品测试3","title":"11","type":"0","supplierId":"1","merchantId":"36","isBargain":"1","isExplosion":"1","attachment":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180208/20180208134635883392347.png","price":"2.0000","address":"null","pridctTypeId":"1","productPlan":"0","createdTime":"2018-02-07 10:40:38.0"},{"id":"4.0","productId":"4","name":"444444444444444","title":"1","type":"1","supplierId":"0","merchantId":"3","isBargain":"1","isExplosion":"0","attachment":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180208/20180208134635883392347.png","price":"2.0000","address":"null","pridctTypeId":"1","productPlan":"0","createdTime":"2018-02-07 15:11:50.0"},{"id":"7.0","productId":"16","name":"1234","title":"策二人","type":"1","supplierId":null,"merchantId":"36","isBargain":"1","isExplosion":null,"attachment":"1234","price":"2.0000","address":"null","pridctTypeId":"1234","productPlan":"0","createdTime":"2018-02-28 17:59:14.0"},{"id":"8.0","productId":"67","name":"名称","title":"标题","type":"0","supplierId":null,"merchantId":null,"isBargain":null,"isExplosion":null,"attachment":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180307/20180307125250724553774.jpg","price":"0.0108","address":"null","pridctTypeId":"26","productPlan":"0","createdTime":"2018-03-07 12:53:15.0"},{"id":"9.0","productId":"5","name":"2222222222","title":"11","type":"0","supplierId":"1","merchantId":"1","isBargain":"1","isExplosion":"0","attachment":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180208/20180208134635883392347.png","price":"2.0000","address":"上海","pridctTypeId":"1","productPlan":"3","createdTime":"2018-02-07 15:17:47.0"},{"id":"10.0","productId":"1","name":"333333311111111111","title":"好","type":"0","supplierId":"1","merchantId":"1","isBargain":"1","isExplosion":"1","attachment":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180208/20180208134635883392347.png","price":"0.2424","address":"dfgfd ","pridctTypeId":"16","productPlan":"16","createdTime":"2018-02-07 10:35:58.0"},{"id":"11.0","productId":"2","name":"333333333322222222","title":"11","type":"0","supplierId":"1","merchantId":"1","isBargain":"1","isExplosion":"1","attachment":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180208/20180208134635883392347.png","price":"0.2222","address":"dfgfd ","pridctTypeId":"1","productPlan":"16","createdTime":"2018-02-07 10:39:33.0"},{"id":"12.0","productId":"3","name":"砍价商品测试3","title":"11","type":"0","supplierId":"1","merchantId":"1","isBargain":"1","isExplosion":"1","attachment":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180208/20180208134635883392347.png","price":"2.0000","address":"dfgfd ","pridctTypeId":"1","productPlan":"16","createdTime":"2018-02-07 10:40:38.0"}]
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
         * id : 1.0
         * productId : 1
         * name : 333333311111111111
         * title : 好
         * type : 0
         * supplierId : 1
         * merchantId : 36
         * isBargain : 1
         * isExplosion : 1
         * attachment : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180208/20180208134635883392347.png
         * price : 0.2424
         * address : null
         * pridctTypeId : 16
         * productPlan : 0
         * createdTime : 2018-02-07 10:35:58.0
         */

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
        private boolean isChecked;

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
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
    }
}
