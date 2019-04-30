package com.yst.im.imchatlibrary.bean;

/**
 * IP  端口号
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/14.
 */
public class HostAndPortEntity {
    /**
     * code : 0
     * msg : 最优服务器端口
     * content : {"port":"53531","host":"192.168.10.246"}
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
         * port : 53531
         * host : 192.168.10.246
         */
        private String port;
        private String host;

        public String getPort() {
            return port;
        }

        public void setPort(String port) {
            this.port = port;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        @Override
        public String toString() {
            return "ContentBean{" +
                    "port='" + port + '\'' +
                    ", host='" + host + '\'' +
                    '}';
        }
    }
}
