package com.yst.onecity.bean;

/**
 * 我的  版本检测
 *
 * @author LianJinXue
 * @version 1.0.1
 * @date 2018/3/3
 */

public class CheckVersionBean {


    /**
     * code : 2
     * msg : 您当前不是最新版本，请更新到最新版本后使用
     * content : {"forcedUpdate":1,"updateDetias":"1、48小时退款，30天无忧退货/n2、邀请好友，领走猎豆/n3、商品标签，一眼Get商品制造商/n4、评论显示优化，商品口碑一目了然/n","time":"2018-01-01","newVersion":"3.2.3"}
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
         * forcedUpdate : 1    （2是强更   1可以不更）
         * updateDetias : 1、48小时退款，30天无忧退货/n2、邀请好友，领走猎豆/n3、商品标签，一眼Get商品制造商/n4、评论显示优化，商品口碑一目了然/n
         * time : 2018-01-01
         * newVersion : 3.2.3
         */
        private String url;
        private int forcedUpdate;
        private String updateDetias;
        private String time;
        private String newVersion;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getForcedUpdate() {
            return forcedUpdate;
        }

        public void setForcedUpdate(int forcedUpdate) {
            this.forcedUpdate = forcedUpdate;
        }

        public String getUpdateDetias() {
            return updateDetias;
        }

        public void setUpdateDetias(String updateDetias) {
            this.updateDetias = updateDetias;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getNewVersion() {
            return newVersion;
        }

        public void setNewVersion(String newVersion) {
            this.newVersion = newVersion;
        }
    }
}
