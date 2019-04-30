package com.yst.onecity.bean;

import java.util.List;

/**
 * 描述
 *
 * @author luxuchang
 * @version 1.1.0
 * @date 2018/6/5
 */
public class CardUnionDetailBean {

    /**
     * code : 1
     * msg : 操作成功！
     * content : {"CODE":"74257759","payNum":0,"teamMsgList":[{"phone":"15701574039","nikeName":"种去","imgAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180601/20180601181704748574440.jpg","merchantUUID":"ae26edfad2694e87bd5f66d592c77d02","serviceAdd":null,"starNum":0}],"beginTime":"2018年06月05日","endTime":"2018年06月22日","punchMsgList":[],"TYPE":null,"allNum":null,"serviceOrderId":459}
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
         * CODE : 74257759
         * payNum : 0
         * teamMsgList : [{"phone":"15701574039","nikeName":"种去","imgAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180601/20180601181704748574440.jpg","merchantUUID":"ae26edfad2694e87bd5f66d592c77d02","serviceAdd":null,"starNum":0}]
         * beginTime : 2018年06月05日
         * endTime : 2018年06月22日
         * punchMsgList : []
         * TYPE : null
         * allNum : null
         * serviceOrderId : 459
         */

        private String CODE;
        private int payNum;
        private String beginTime;
        private String endTime;
        private Object TYPE;
        private Object allNum;
        private int serviceOrderId;
        private List<TeamMsgListBean> teamMsgList;
        private List<?> punchMsgList;

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

        public Object getTYPE() {
            return TYPE;
        }

        public void setTYPE(Object TYPE) {
            this.TYPE = TYPE;
        }

        public Object getAllNum() {
            return allNum;
        }

        public void setAllNum(Object allNum) {
            this.allNum = allNum;
        }

        public int getServiceOrderId() {
            return serviceOrderId;
        }

        public void setServiceOrderId(int serviceOrderId) {
            this.serviceOrderId = serviceOrderId;
        }

        public List<TeamMsgListBean> getTeamMsgList() {
            return teamMsgList;
        }

        public void setTeamMsgList(List<TeamMsgListBean> teamMsgList) {
            this.teamMsgList = teamMsgList;
        }

        public List<?> getPunchMsgList() {
            return punchMsgList;
        }

        public void setPunchMsgList(List<?> punchMsgList) {
            this.punchMsgList = punchMsgList;
        }

        public static class TeamMsgListBean {
            /**
             * phone : 15701574039
             * nikeName : 种去
             * imgAddress : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180601/20180601181704748574440.jpg
             * merchantUUID : ae26edfad2694e87bd5f66d592c77d02
             * serviceAdd : null
             * starNum : 0
             */

            private String phone;
            private String nikeName;
            private String imgAddress;
            private String merchantUUID;
            private Object serviceAdd;
            private int starNum;

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

            public String getMerchantUUID() {
                return merchantUUID;
            }

            public void setMerchantUUID(String merchantUUID) {
                this.merchantUUID = merchantUUID;
            }

            public Object getServiceAdd() {
                return serviceAdd;
            }

            public void setServiceAdd(Object serviceAdd) {
                this.serviceAdd = serviceAdd;
            }

            public int getStarNum() {
                return starNum;
            }

            public void setStarNum(int starNum) {
                this.starNum = starNum;
            }
        }
    }
}
