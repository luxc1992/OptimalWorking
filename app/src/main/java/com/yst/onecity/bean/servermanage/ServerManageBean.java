package com.yst.onecity.bean.servermanage;

import java.util.List;

/**
 * 服务管理的bean类
 *
 * @author wuxiaofang
 * @version 1.0.1
 * @date 2018/5/17
 */

public class ServerManageBean {

    /**
     * code : 1
     * content : [{"id":37,"log":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417114522153867251.jpg","price":500,"status":1,"title":"去北京看看"},{"id":38,"log":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417114522153867251.jpg","price":500,"status":1,"title":"去北京看看"},{"id":39,"log":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417114522153867251.jpg","price":500,"status":1,"title":"去北京看看"}]
     * msg : 查询成功
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
         * id : 37
         * log : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417114522153867251.jpg
         * price : 500.0
         * status : 1
         * title : 去北京看看
         */

        private int id;
        private String log;
        private double price;
        private int status;
        private String title;
        private String examineStatus;
        private boolean isClick;

        public boolean isClick() {
            return isClick;
        }

        public void setClick(boolean click) {
            isClick = click;
        }

        public String getExamineStatus() {
            return examineStatus;
        }

        public void setExamineStatus(String examineStatus) {
            this.examineStatus = examineStatus;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLog() {
            return log;
        }

        public void setLog(String log) {
            this.log = log;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
