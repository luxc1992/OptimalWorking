package com.yst.onecity.bean.agent;

import java.util.List;

/**
 * 发布课题回显列表实体类
 *
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/5/28
 */

public class PublishCourseBean {

    /**
     * code : 1
     * msg : 操作成功
     * content : [{"id":2299,"title":"标题","description":"5656","images":["https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180514/20180514103418485265049.jpg"],"consulationIds":null},{"id":2313,"title":"我是来自北方的狼","description":"房贷首付","images":["https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180524/20180524155950488902094.png"],"consulationIds":null},{"id":2336,"title":"范德萨范德萨","description":"发射点发射点","images":["https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180524/20180524161852640046842.png"],"consulationIds":null},{"id":2338,"title":"订单","description":"房贷首付","images":["https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180524/20180524173045775402844.png"],"consulationIds":null}]
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
         * id : 2299
         * title : 标题
         * description : 5656
         * images : ["https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180514/20180514103418485265049.jpg"]
         * consulationIds : null
         */

        private int id;
        private String title;
        private String description;
        private Object consulationIds;
        private List<String> images;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Object getConsulationIds() {
            return consulationIds;
        }

        public void setConsulationIds(Object consulationIds) {
            this.consulationIds = consulationIds;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
