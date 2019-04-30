package com.yst.onecity.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 服务团队列表实体类
 *
 * @author chenjiadi
 * @version 1.1.0
 * @date 2018/5/24.
 */

public class ServerTeamListBean implements Serializable {


    /**
     * code : 1
     * msg : 查询服务团队列表成功
     * content : [{"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180511/20180511153452640140404.jpg","distance":"5008.84/km","phone":"13712341234","latitude":"1.222222","nikename":"博洛尼装修2","star_num":null,"id":5,"content":"1222272455","memberId":492,"longitude":"121.020022","caseList":[{"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180522/20180522202719599843040.jpg","caseId":5,"advisorName":"博洛尼装修2","title":"案例","content":"<p>\n\t<img src=\"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180522/20180522202719599843040.jpg\" alt=\"\" /><img src=\"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180522/20180522202719599843040.jpg\" alt=\"\" /><img src=\"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180522/20180522202719599843040.jpg\" alt=\"\" />\n<\/p>\n<p>\n\t哈哈哈哈哈哈哈哈哈哈\n<\/p>"}]}]
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
         * address : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180511/20180511153452640140404.jpg
         * distance : 5008.84/km
         * phone : 13712341234
         * latitude : 1.222222
         * nikename : 博洛尼装修2
         * star_num : null
         * uuid:"474944e3993d428e9a147f7fb4de8d10",
         * id : 5
         * content : 1222272455
         * memberId : 492
         * longitude : 121.020022
         * caseList : [{"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180522/20180522202719599843040.jpg","caseId":5,"advisorName":"博洛尼装修2","title":"案例","content":"<p>\n\t<img src=\"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180522/20180522202719599843040.jpg\" alt=\"\" /><img src=\"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180522/20180522202719599843040.jpg\" alt=\"\" /><img src=\"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180522/20180522202719599843040.jpg\" alt=\"\" />\n<\/p>\n<p>\n\t哈哈哈哈哈哈哈哈哈哈\n<\/p>"}]
         */

        private String address;
        private String distance;
        private String phone;
        private String latitude;
        private String nikename;
        private float star_num;
        private int id;
        private String content;
        private int memberId;
        private String longitude;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        private String uuid;
        private List<CaseListBean> caseList;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getNikename() {
            return nikename;
        }

        public void setNikename(String nikename) {
            this.nikename = nikename;
        }

        public float getStar_num() {
            return star_num;
        }

        public void setStar_num(float star_num) {
            this.star_num = star_num;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public List<CaseListBean> getCaseList() {
            return caseList;
        }

        public void setCaseList(List<CaseListBean> caseList) {
            this.caseList = caseList;
        }

        public static class CaseListBean {
            /**
             * address : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180522/20180522202719599843040.jpg
             * caseId : 5
             * advisorName : 博洛尼装修2
             * title : 案例
             * content : <p>
             * <img src="https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180522/20180522202719599843040.jpg" alt="" /><img src="https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180522/20180522202719599843040.jpg" alt="" /><img src="https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180522/20180522202719599843040.jpg" alt="" />
             * </p>
             * <p>
             * 哈哈哈哈哈哈哈哈哈哈
             * </p>
             */

            private String address;
            private int caseId;
            private String advisorName;
            private String title;
            private String content;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getCaseId() {
                return caseId;
            }

            public void setCaseId(int caseId) {
                this.caseId = caseId;
            }

            public String getAdvisorName() {
                return advisorName;
            }

            public void setAdvisorName(String advisorName) {
                this.advisorName = advisorName;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
