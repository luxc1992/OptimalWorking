package com.yst.onecity.bean;

import java.util.List;

/**
 * 我的收藏-服务项目实体类
 *
 * @author liumanqing
 * @version 1.0.1
 * @date 2018/5/24
 */
public class ServeProjectBean {


    /**
     * code : 1
     * msg : 查询成功
     * content : [{"price":"500.00","introduce":"田园风光","imageAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180425/20180425154744299457863.png","serviceProjectId":5,"title":"我呢122"},{"price":"0.10","introduce":"田园风光","imageAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417102544771770878.jpg","serviceProjectId":2,"title":"我呢"},{"price":"500.00","introduce":"田园风光","imageAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180425/20180425154744299457863.png","serviceProjectId":4,"title":"我呢"}]
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
         * price : 500.00
         * introduce : 田园风光
         * imageAddress : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180425/20180425154744299457863.png
         * serviceProjectId : 5
         * title : 我呢122
         */

        private String price;
        private String introduce;
        private String imageAddress;
        private int serviceProjectId;
        private String title;

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getImageAddress() {
            return imageAddress;
        }

        public void setImageAddress(String imageAddress) {
            this.imageAddress = imageAddress;
        }

        public int getServiceProjectId() {
            return serviceProjectId;
        }

        public void setServiceProjectId(int serviceProjectId) {
            this.serviceProjectId = serviceProjectId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
