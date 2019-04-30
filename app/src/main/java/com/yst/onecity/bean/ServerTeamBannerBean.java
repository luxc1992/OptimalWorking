package com.yst.onecity.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 服务团队轮播图实体类
 *
 * @author chenjiadi
 * @version 1.1.0
 * @date 2018/5/24.
 */

public class ServerTeamBannerBean implements Serializable{

    /**
     * code : 1
     * msg : 服务团队轮播图查询成功
     * content : [{"advertisementName":"服务团队广告","advertisementLink":"http://www.baidu.com","advertisementImage":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180425/20180425160020481030583.jpg"},{"advertisementName":"服务团队广告2","advertisementLink":"http://www.baidu.com","advertisementImage":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180425/20180425160020481030583.jpg"}]
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
         * advertisementName : 服务团队广告
         * advertisementLink : http://www.baidu.com
         * advertisementImage : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180425/20180425160020481030583.jpg
         */

        private String advertisementName;
        private String advertisementLink;
        private String advertisementImage;

        public String getAdvertisementName() {
            return advertisementName;
        }

        public void setAdvertisementName(String advertisementName) {
            this.advertisementName = advertisementName;
        }

        public String getAdvertisementLink() {
            return advertisementLink;
        }

        public void setAdvertisementLink(String advertisementLink) {
            this.advertisementLink = advertisementLink;
        }

        public String getAdvertisementImage() {
            return advertisementImage;
        }

        public void setAdvertisementImage(String advertisementImage) {
            this.advertisementImage = advertisementImage;
        }
    }
}
