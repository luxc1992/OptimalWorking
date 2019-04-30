package com.yst.onecity.bean.mine;

/**
 * 我的二维码实体类
 *
 * @author liumanqing
 * @version 1.0.1
 * @date 2018/3/1
 */
public class MyZxingBean {

    /**
     * code : 1
     * content : {"headImg":"http://img.zcool.cn/community/01690955496f930000019ae92f3a4e.jpg@2o.jpg","id":1,"url":"15011552664","username":"廉金雪"}
     * msg : 获取成功
     */

    private int code;
    private ContentBean content;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class ContentBean {
        /**
         * headImg : http://img.zcool.cn/community/01690955496f930000019ae92f3a4e.jpg@2o.jpg
         * id : 1
         * url : 15011552664
         * username : 廉金雪
         */

        private String headImg;
        private int id;
        private String url;
        private String username;

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
