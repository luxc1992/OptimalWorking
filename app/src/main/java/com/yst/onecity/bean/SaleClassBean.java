package com.yst.onecity.bean;

import java.util.List;

/**
 * 经营分类的bean
 *
 * @author wuxiaofang
 * @version 1.1.0
 * @date 2018/5/26
 */

public class SaleClassBean {

    /**
     * code : 1
     * msg : 服务一级分类列表查询成功
     * content : [{"name":"装修","id":1},{"name":"职业","id":5},{"name":"队员","id":6}]
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
        public ContentBean(String name, int id) {
            this.name = name;
            this.id = id;
        }

        /**
         * name : 装修
         * id : 1
         */


        private String name;
        private int id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
