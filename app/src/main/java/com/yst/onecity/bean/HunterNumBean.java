package com.yst.onecity.bean;

/**
 * 徒弟数量
 *
 * @author LianJinXue
 * @version 1.0.1
 * @date 2018/3/7
 */

public class HunterNumBean {
    /**
     * code : 1
     * msg : 查询成功
     * content : {"groupId":null,"count":0}
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
         * groupId : null
         * count : 0
         */

        private String groupId;
        private int count;

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
