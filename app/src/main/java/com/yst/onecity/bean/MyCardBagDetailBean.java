package com.yst.onecity.bean;

import java.util.List;

/**
 * 我的卡包详情实体类
 *
 * @author liumanqing
 * @version 1.0.1
 * @date 2018/5/26
 */
public class MyCardBagDetailBean {

    /**
     * code : 1
     * msg : 操作成功！
     * content : {"CODE":"123456785","payNum":30,"teamMsgList":[{"phone":"13717847870","nikeName":"博洛尼装修2","imgAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180511/20180511153452640140404.jpg","serviceAdd":"127.0.0.1","starNum":null}],"beginTime":1526535199000,"endTime":1527744802000,"punchMsgList":[{"clubCardId":5,"userTime":null,"nikeName":"博洛尼装修2","imgAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180511/20180511153452640140404.jpg","serviceAdd":"北京","starNum":null,"serviceOrderId":120},{"clubCardId":5,"userTime":null,"nikeName":"博洛尼装修2","imgAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180511/20180511153452640140404.jpg","serviceAdd":"北京","starNum":null,"serviceOrderId":121},{"clubCardId":5,"userTime":null,"nikeName":"博洛尼装修2","imgAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180511/20180511153452640140404.jpg","serviceAdd":"北京","starNum":null,"serviceOrderId":122}],"TYPE":1,"allNum":30}
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
         * CODE : 123456785
         * payNum : 30
         * teamMsgList : [{"phone":"13717847870","nikeName":"博洛尼装修2","imgAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180511/20180511153452640140404.jpg","serviceAdd":"127.0.0.1","starNum":null}]
         * beginTime : 1526535199000
         * endTime : 1527744802000
         * punchMsgList : [{"clubCardId":5,"userTime":null,"nikeName":"博洛尼装修2","imgAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180511/20180511153452640140404.jpg","serviceAdd":"北京","starNum":null,"serviceOrderId":120},{"clubCardId":5,"userTime":null,"nikeName":"博洛尼装修2","imgAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180511/20180511153452640140404.jpg","serviceAdd":"北京","starNum":null,"serviceOrderId":121},{"clubCardId":5,"userTime":null,"nikeName":"博洛尼装修2","imgAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180511/20180511153452640140404.jpg","serviceAdd":"北京","starNum":null,"serviceOrderId":122}]
         * TYPE : 1
         * allNum : 30
         */

        private String CODE;
        private int payNum;
        private String beginTime;
        private String endTime;
        private int TYPE;
        private int allNum;
        private String serviceOrderId;
        private List<TeamMsgListBean> teamMsgList;
        private List<PunchMsgListBean> punchMsgList;

        public String getServiceOrderId() {
            return serviceOrderId;
        }

        public void setServiceOrderId(String serviceOrderId) {
            this.serviceOrderId = serviceOrderId;
        }

        public String getCODE() {
            return CODE;
        }

        public void setCODE(String CODE) {
            this.CODE = CODE;
        }

        public int getPayNum() {
            return payNum;
        }

        public void setPayNum(int payNum) {
            this.payNum = payNum;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getTYPE() {
            return TYPE;
        }

        public void setTYPE(int TYPE) {
            this.TYPE = TYPE;
        }

        public int getAllNum() {
            return allNum;
        }

        public void setAllNum(int allNum) {
            this.allNum = allNum;
        }

        public List<TeamMsgListBean> getTeamMsgList() {
            return teamMsgList;
        }

        public void setTeamMsgList(List<TeamMsgListBean> teamMsgList) {
            this.teamMsgList = teamMsgList;
        }

        public List<PunchMsgListBean> getPunchMsgList() {
            return punchMsgList;
        }

        public void setPunchMsgList(List<PunchMsgListBean> punchMsgList) {
            this.punchMsgList = punchMsgList;
        }

        public static class TeamMsgListBean {
            /**
             * phone : 13717847870
             * nikeName : 博洛尼装修2
             * imgAddress : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180511/20180511153452640140404.jpg
             * serviceAdd : 127.0.0.1
             * starNum : null
             */

            private String phone;
            private String nikeName;
            private String imgAddress;
            private String serviceAdd;
            private int starNum;
            private String merchantUUID;

            public String getMerchantUUID() {
                return merchantUUID;
            }

            public void setMerchantUUID(String merchantUUID) {
                this.merchantUUID = merchantUUID;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getNikeName() {
                return nikeName;
            }

            public void setNikeName(String nikeName) {
                this.nikeName = nikeName;
            }

            public String getImgAddress() {
                return imgAddress;
            }

            public void setImgAddress(String imgAddress) {
                this.imgAddress = imgAddress;
            }

            public String getServiceAdd() {
                return serviceAdd;
            }

            public void setServiceAdd(String serviceAdd) {
                this.serviceAdd = serviceAdd;
            }

            public int getStarNum() {
                return starNum;
            }

            public void setStarNum(int starNum) {
                this.starNum = starNum;
            }
        }

        public static class PunchMsgListBean {
            /**
             * clubCardId : 5
             * userTime : null
             * nikeName : 博洛尼装修2
             * imgAddress : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180511/20180511153452640140404.jpg
             * serviceAdd : 北京
             * starNum : null
             * serviceOrderId : 120
             */

            private int clubCardId;
            private String userTime;
            private String nikeName;
            private String imgAddress;
            private String serviceAdd;
            private int starNum;
            private int serviceOrderId;

            public String getUserTime() {
                return userTime;
            }

            public void setUserTime(String userTime) {
                this.userTime = userTime;
            }

            public int getStarNum() {
                return starNum;
            }

            public void setStarNum(int starNum) {
                this.starNum = starNum;
            }

            public int getClubCardId() {
                return clubCardId;
            }

            public void setClubCardId(int clubCardId) {
                this.clubCardId = clubCardId;
            }


            public String getNikeName() {
                return nikeName;
            }

            public void setNikeName(String nikeName) {
                this.nikeName = nikeName;
            }

            public String getImgAddress() {
                return imgAddress;
            }

            public void setImgAddress(String imgAddress) {
                this.imgAddress = imgAddress;
            }

            public String getServiceAdd() {
                return serviceAdd;
            }

            public void setServiceAdd(String serviceAdd) {
                this.serviceAdd = serviceAdd;
            }

            public int getServiceOrderId() {
                return serviceOrderId;
            }

            public void setServiceOrderId(int serviceOrderId) {
                this.serviceOrderId = serviceOrderId;
            }
        }
    }
}
