package com.yst.onecity.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 获取发布资讯选择发布分类实体
 *
 * @author chenjiadi
 * @version 1.0.1
 * @date 2018/3/1.
 */

public class PublishClassifyBean implements Serializable {

    /**
     * code : 1
     * msg : 查询成功
     * content : [{"description_name":"母婴用品","id":1},{"description_name":"一级分类","id":2},{"description_name":"分类","id":3}]
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
         * description_name : 母婴用品
         * id : 1
         */

        private String description_name;
        private int id;

        public String getDescription_name() {
            return description_name;
        }

        public void setDescription_name(String description_name) {
            this.description_name = description_name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
