package com.yst.onecity.bean.issue;

import java.util.List;

/**供应商
 * @author zhaiyanwu
 * @version v1.0.1
 * @date 2018/3/1.
 */
public class TextViewBean {

    /**
     * code : 1
     * msg : 获取成功
     * content : [{"id":1,"supplier_name":"测试供应商"},{"id":2,"supplier_name":"ceshi"}]
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
         * id : 1
         * supplier_name : 测试供应商
         */

        private int id;
        private String supplier_name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSupplier_name() {
            return supplier_name;
        }

        public void setSupplier_name(String supplier_name) {
            this.supplier_name = supplier_name;
        }
    }
}
