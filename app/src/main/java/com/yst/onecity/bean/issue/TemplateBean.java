package com.yst.onecity.bean.issue;

/**
 * @author zhaiyanwu
 * @version v3.0.1
 * @date 2018/4/12.
 */
public class TemplateBean {

    /**
     * code : 1
     * msg : 新增运费模板成功
     * content : {"freightId":1821,"name":"1"}
     */

    private int code;
    private String msg;
    private ContentBean content;

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

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * freightId : 1821
         * name : 1
         */

        private int freightId;
        private String name;

        public int getFreightId() {
            return freightId;
        }

        public void setFreightId(int freightId) {
            this.freightId = freightId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
