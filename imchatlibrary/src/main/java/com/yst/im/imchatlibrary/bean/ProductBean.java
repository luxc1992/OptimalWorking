package com.yst.im.imchatlibrary.bean;

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
     * content : [{"id":"1.0","productId":"2955","name":"汉服（平台）0417","title":"美女","type":"0","supplierId":null,"merchantId":null,"isBargain":"0","isExplosion":"0","attachment":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417103855063698393.jpg","price":"0.01-1.11","minPrice":"0.01","maxPrice":"1.11","address":null,"pridctTypeId":"202","productPlan":"0","createdTime":"2018-04-17 10:40:47.0"},{"id":"2.0","productId":"2956","name":"卫衣（供应商）0417","title":"男士卫衣","type":"2","supplierId":"84","merchantId":null,"isBargain":"0","isExplosion":"0","attachment":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417104336375602904.jpg","price":"1.59-2.11","minPrice":"1.59","maxPrice":"2.11","address":null,"pridctTypeId":"203","productPlan":"0","createdTime":"2018-04-17 10:44:43.0"},{"id":"3.0","productId":"2957","name":"华为手环","title":"运动手环","type":"1","supplierId":null,"merchantId":"492","isBargain":"0","isExplosion":"0","attachment":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417105615621836845.jpg","price":"0.5-1.1","minPrice":"0.5","maxPrice":"1.1","address":"北京市","pridctTypeId":"203","productPlan":"0","createdTime":"2018-04-17 10:59:50.0"},{"id":"4.0","productId":"2958","name":"蒙古袍(猎头)0417","title":"专员身份测试分润","type":"1","supplierId":null,"merchantId":"506","isBargain":"0","isExplosion":"0","attachment":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417110136893564725.jpg","price":"1.59-1.59","minPrice":"1.59","maxPrice":"1.59","address":"北京市","pridctTypeId":"202","productPlan":"0","createdTime":"2018-04-17 11:06:31.0"},{"id":"5.0","productId":"2959","name":"红豆奶茶","title":"16727373733","type":"1","supplierId":null,"merchantId":"495","isBargain":"0","isExplosion":"0","attachment":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417110619200439314.jpeg","price":"0.33-1.01","minPrice":"0.33","maxPrice":"1.01","address":"北京市","pridctTypeId":"210","productPlan":"0","createdTime":"2018-04-17 11:08:40.0"},{"id":"6.0","productId":"2960","name":"小红帽","title":"去瞧瞧","type":"1","supplierId":null,"merchantId":"502","isBargain":"0","isExplosion":"0","attachment":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417111935228460414.jpeg","price":"0.01-0.01","minPrice":"0.01","maxPrice":"0.01","address":"北京市","pridctTypeId":"206","productPlan":"0","createdTime":"2018-04-17 11:27:22.0"},{"id":"7.0","productId":"2961","name":"裙子","title":"公主裙","type":"0","supplierId":null,"merchantId":null,"isBargain":"0","isExplosion":"0","attachment":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417113624372282859.jpg","price":"0.01-0.04","minPrice":"0.01","maxPrice":"0.04","address":null,"pridctTypeId":"202","productPlan":"0","createdTime":"2018-04-17 11:37:21.0"},{"id":"9.0","productId":"2965","name":"耳坠","title":"耳环又称耳坠,是戴在耳朵的饰品,古代又称珥、珰","type":"1","supplierId":null,"merchantId":"495","isBargain":"0","isExplosion":"0","attachment":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417143136793956078.jpg","price":"0.95-1.09","minPrice":"0.95","maxPrice":"1.09","address":null,"pridctTypeId":"206","productPlan":"0","createdTime":"2018-04-17 14:32:36.0"},{"id":"12.0","productId":"2971","name":"零食","title":"零食","type":"1","supplierId":null,"merchantId":"492","isBargain":"0","isExplosion":"0","attachment":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417173604797535230.jpg","price":"0.5-0.5","minPrice":"0.5","maxPrice":"0.5","address":"北京市","pridctTypeId":"209","productPlan":"0","createdTime":"2018-04-17 17:38:17.0"},{"id":"16.0","productId":"2978","name":"xxx\n","title":"啊啊啊","type":"1","supplierId":null,"merchantId":"501","isBargain":"0","isExplosion":"0","attachment":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180418/20180418114414120585097.jpg","price":"0.1-0.1","minPrice":"0.1","maxPrice":"0.1","address":null,"pridctTypeId":"203","productPlan":"0","createdTime":"2018-04-18 11:45:11.0"}]
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
         * productId : 2955
         * name : 汉服（平台）0417
         * title : 美女
         * type : 0
         * supplierId : null
         * merchantId : null
         * isBargain : 0
         * isExplosion : 0
         * attachment : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417103855063698393.jpg
         * price : 0.01-1.11
         * minPrice : 0.01
         * maxPrice : 1.11
         * address : null
         * pridctTypeId : 202
         * productPlan : 0
         * createdTime : 2018-04-17 10:40:47.0
         */

        private String id;
        private String productId;
        private String name;
        private String title;
        private String type;
        private Object supplierId;
        private Object merchantId;
        private String isBargain;
        private String isExplosion;
        private String attachment;
        private String price;
        private String minPrice;
        private String maxPrice;
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

        public Object getSupplierId() {
            return supplierId;
        }

        public void setSupplierId(Object supplierId) {
            this.supplierId = supplierId;
        }

        public Object getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(Object merchantId) {
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
