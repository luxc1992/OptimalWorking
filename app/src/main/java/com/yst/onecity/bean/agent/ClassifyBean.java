package com.yst.onecity.bean.agent;

import java.util.List;

/**
 * 课题分类实体类
 *
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/5/26
 */

public class ClassifyBean {

    /**
     * code : 1
     * msg : 课题分类查询成功
     * content : [{"descriptionName":"健身燃脂","icon":null,"id":1},{"descriptionName":"增肌","icon":null,"id":2}]
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
         * descriptionName : 健身燃脂
         * icon : null
         * id : 1
         */

        private String descriptionName;
        private Object icon;
        private int id;
        private boolean isClick;

        public boolean isClick() {
            return isClick;
        }

        public void setClick(boolean click) {
            isClick = click;
        }

        public String getDescriptionName() {
            return descriptionName;
        }

        public void setDescriptionName(String descriptionName) {
            this.descriptionName = descriptionName;
        }

        public Object getIcon() {
            return icon;
        }

        public void setIcon(Object icon) {
            this.icon = icon;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
