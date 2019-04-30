package com.yst.onecity.bean;

import java.util.List;

/**
 * 案例管理列表的bean
 *
 * @author wuxiaofang
 * @version 1.0.1
 * @date 2018/5/17
 */

public class CaseBean {

    /**
     * code : 1
     * msg : 获取案例列表成功！
     * content : [{"id":4,"createUser":"admin","createdTime":"2018-05-23 14:55:30","updateTime":"2018-05-23 17:02:20","createdIp":"127.0.0.1","title":"案例标题1","imageAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417113921552972464.jpg","content":"<p>\r\n\t<img src=\"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180522/20180522202719599843040.jpg\" alt=\"\" /><img src=\"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180522/20180522202719599843040.jpg\" alt=\"\" /><img src=\"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180522/20180522202719599843040.jpg\" alt=\"\" />\r\n<\/p>\r\n<p>\r\n\t哈哈哈哈哈哈哈哈哈哈\r\n<\/p>","serviceProjectId":null,"readNum":null,"serviceId":null,"serviceTitle":null,"price":null,"serviceImageAddress":null,"productVOList":null}]
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
         * id : 4
         * createUser : admin
         * createdTime : 2018-05-23 14:55:30
         * updateTime : 2018-05-23 17:02:20
         * createdIp : 127.0.0.1
         * title : 案例标题1
         * imageAddress : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417113921552972464.jpg
         * content : <p>
         * <img src="https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180522/20180522202719599843040.jpg" alt="" /><img src="https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180522/20180522202719599843040.jpg" alt="" /><img src="https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180522/20180522202719599843040.jpg" alt="" />
         * </p>
         * <p>
         * 哈哈哈哈哈哈哈哈哈哈
         * </p>
         * serviceProjectId : null
         * readNum : null
         * serviceId : null
         * serviceTitle : null
         * price : null
         * serviceImageAddress : null
         * productVOList : null
         */

        private int id;
        private String createUser;
        private String createdTime;
        private String updateTime;
        private String createdIp;
        private String title;
        private String imageAddress;
        private String content;
        private Object serviceProjectId;
        private Object readNum;
        private Object serviceId;
        private Object serviceTitle;
        private Object price;
        private Object serviceImageAddress;
        private Object productVOList;
        private boolean isClick;

        public boolean isClick() {
            return isClick;
        }

        public void setClick(boolean click) {
            isClick = click;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCreateUser() {
            return createUser;
        }

        public void setCreateUser(String createUser) {
            this.createUser = createUser;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getCreatedIp() {
            return createdIp;
        }

        public void setCreatedIp(String createdIp) {
            this.createdIp = createdIp;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImageAddress() {
            return imageAddress;
        }

        public void setImageAddress(String imageAddress) {
            this.imageAddress = imageAddress;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Object getServiceProjectId() {
            return serviceProjectId;
        }

        public void setServiceProjectId(Object serviceProjectId) {
            this.serviceProjectId = serviceProjectId;
        }

        public Object getReadNum() {
            return readNum;
        }

        public void setReadNum(Object readNum) {
            this.readNum = readNum;
        }

        public Object getServiceId() {
            return serviceId;
        }

        public void setServiceId(Object serviceId) {
            this.serviceId = serviceId;
        }

        public Object getServiceTitle() {
            return serviceTitle;
        }

        public void setServiceTitle(Object serviceTitle) {
            this.serviceTitle = serviceTitle;
        }

        public Object getPrice() {
            return price;
        }

        public void setPrice(Object price) {
            this.price = price;
        }

        public Object getServiceImageAddress() {
            return serviceImageAddress;
        }

        public void setServiceImageAddress(Object serviceImageAddress) {
            this.serviceImageAddress = serviceImageAddress;
        }

        public Object getProductVOList() {
            return productVOList;
        }

        public void setProductVOList(Object productVOList) {
            this.productVOList = productVOList;
        }
    }
}
