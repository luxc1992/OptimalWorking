package com.yst.onecity.bean;

import java.util.List;

/**
 * 描述
 *
 * @author luxuchang
 * @version 1.1.0
 * @date 2018/5/28
 */
public class CardUnionUsedDetailBean {

    /**
     * code : 1
     * msg : 操作成功！
     * content : {"CODE":"6462223802","payNum":1,"teamMsgList":[{"phone":"13717847870","nikeName":"博洛尼装修2","imgAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180511/20180511153452640140404.jpg","serviceAdd":"北京市朝阳区","starNum":null}],"beginTime":null,"endTime":null,"punchMsgList":[],"TYPE":null,"allNum":null}
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
         * CODE : 6462223802
         * payNum : 1
         * teamMsgList : [{"phone":"13717847870","nikeName":"博洛尼装修2","imgAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180511/20180511153452640140404.jpg","serviceAdd":"北京市朝阳区","starNum":null}]
         * beginTime : null
         * endTime : null
         * punchMsgList : []
         * TYPE : null
         * allNum : null
         */

        private String CODE;
        private int payNum;
        private Object beginTime;
        private Object endTime;
        private Object TYPE;
        private Object allNum;
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

        public Object getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(Object beginTime) {
            this.beginTime = beginTime;
        }

        public Object getEndTime() {
            return endTime;
        }

        public void setEndTime(Object endTime) {
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
             * phone : 13717847870
             * nikeName : 博洛尼装修2
             * imgAddress : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180511/20180511153452640140404.jpg
             * serviceAdd : 北京市朝阳区
             * starNum : null
             */

            private String phone;
            private String nikeName;
            private String imgAddress;
            private String serviceAdd;
            private Object starNum;

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

            public Object getStarNum() {
                return starNum;
            }

            public void setStarNum(Object starNum) {
                this.starNum = starNum;
            }
        }
    }
}
