package com.yst.onecity.bean.goodsmanage;

import java.util.List;

/**
 * 商品管理bean
 *
 * @author wuxiaofang
 * @version 1.0.1
 * @date 2018/5/19
 */

public class GoodsBean {

    /**
     * code : 1
     * msg : 查询成功
     * content : [{"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180423/20180423103518421870161.jpeg","minPrice":"0.10","pId":3049,"location":"北京市","maxPrice":"0.10","title":"蛋糕111","content":"vvv","status":1}]
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
         * address : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180423/20180423103518421870161.jpeg
         * minPrice : 0.10
         * pId : 3049
         * location : 北京市
         * maxPrice : 0.10
         * title : 蛋糕111
         * content : vvv
         * status : 1
         */

        private String address;
        private String minPrice;
        private int pId;
        private String location;
        private String maxPrice;
        private String title;
        private String content;
        private int status;
        private boolean isClick;
        private int examineStatus;

        public int getExamineStatus() {
            return examineStatus;
        }

        public void setExamineStatus(int examineStatus) {
            this.examineStatus = examineStatus;
        }

        public boolean isClick() {
            return isClick;
        }

        public void setClick(boolean click) {
            isClick = click;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMinPrice() {
            return minPrice;
        }

        public void setMinPrice(String minPrice) {
            this.minPrice = minPrice;
        }

        public int getPId() {
            return pId;
        }

        public void setPId(int pId) {
            this.pId = pId;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getMaxPrice() {
            return maxPrice;
        }

        public void setMaxPrice(String maxPrice) {
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
