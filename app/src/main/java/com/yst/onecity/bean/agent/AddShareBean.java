package com.yst.onecity.bean.agent;

import java.util.List;

/**
 * 添加分享fragment列表实体类
 *
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/5/26
 */

public class AddShareBean {

    /**
     * code : 1
     * msg : 成功
     * content : [{"img":[{"img":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180418/20180418100948608675658.jpg","video":null},{"img":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180418/20180418100959882829848.jpg","video":null},{"img":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180418/20180418100959989784910.jpg","video":null}],"description":"5656","browserAmount":34,"title":"咨询","userName":"小猴子","type":3,"shareNum":0,"commentNum":0,"planNum":22790,"fabulousNum":0,"reprintName":null,"ptitle":"去瞧瞧","id":2184,"memberId":492,"status":0},{"img":[{"img":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417110351874670215.jpg","video":null},{"img":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417110357669601094.jpg","video":null},{"img":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417110357875141432.jpg","video":null},{"img":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417110358216148299.jpg","video":null}],"description":"5656","browserAmount":0,"title":"咨询01","userName":"小猴子","type":4,"shareNum":0,"commentNum":0,"planNum":22724,"fabulousNum":0,"reprintName":null,"ptitle":"华为手环","id":2164,"memberId":492,"status":0},{"img":[{"img":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417105730111361288.jpg","video":null}],"description":"5656","browserAmount":44,"title":"图文咨询1","userName":"songbinbin","type":1,"shareNum":0,"commentNum":7,"planNum":null,"fabulousNum":0,"reprintName":null,"ptitle":null,"id":2163,"memberId":505,"status":0}]
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
         * img : [{"img":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180418/20180418100948608675658.jpg","video":null},{"img":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180418/20180418100959882829848.jpg","video":null},{"img":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180418/20180418100959989784910.jpg","video":null}]
         * description : 5656
         * browserAmount : 34
         * title : 咨询
         * userName : 小猴子
         * type : 3
         * shareNum : 0
         * commentNum : 0
         * planNum : 22790
         * fabulousNum : 0
         * reprintName : null
         * ptitle : 去瞧瞧
         * id : 2184
         * memberId : 492
         * status : 0
         */

        private String description;
        private int browserAmount;
        private String title;
        private String userName;
        private int type;
        private int shareNum;
        private int commentNum;
        private int planNum;
        private int fabulousNum;
        private Object reprintName;
        private String ptitle;
        private int id;
        private int memberId;
        private int status;
        private List<ImgBean> img;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getBrowserAmount() {
            return browserAmount;
        }

        public void setBrowserAmount(int browserAmount) {
            this.browserAmount = browserAmount;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getShareNum() {
            return shareNum;
        }

        public void setShareNum(int shareNum) {
            this.shareNum = shareNum;
        }

        public int getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(int commentNum) {
            this.commentNum = commentNum;
        }

        public int getPlanNum() {
            return planNum;
        }

        public void setPlanNum(int planNum) {
            this.planNum = planNum;
        }

        public int getFabulousNum() {
            return fabulousNum;
        }

        public void setFabulousNum(int fabulousNum) {
            this.fabulousNum = fabulousNum;
        }

        public Object getReprintName() {
            return reprintName;
        }

        public void setReprintName(Object reprintName) {
            this.reprintName = reprintName;
        }

        public String getPtitle() {
            return ptitle;
        }

        public void setPtitle(String ptitle) {
            this.ptitle = ptitle;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public List<ImgBean> getImg() {
            return img;
        }

        public void setImg(List<ImgBean> img) {
            this.img = img;
        }

        public static class ImgBean {
            /**
             * img : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180418/20180418100948608675658.jpg
             * video : null
             */

            private String img;
            private Object video;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public Object getVideo() {
                return video;
            }

            public void setVideo(Object video) {
                this.video = video;
            }
        }
    }
}
