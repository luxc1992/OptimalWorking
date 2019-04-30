package com.yst.onecity.bean.mine;

import java.util.List;

/**
 * 我的收藏-资讯
 *
 * @author liumanqing
 * @version 1.0.1
 * @date 2018/03/05
 */
public class MyCollectInformationBean {

    /**
     * code : 1
     * msg : null
     * content : [{"id":5,"commentNum":0,"modelType":0,"consultationClassifyId":1,"reprintName":null,"fabulousNum":0,"imgNum":null,"phone":"17210000000","shareNum":null,"title":"测试视频资讯002 **","memberId":2,"browserAmount":0,"plantId":1,"name":"俊杰","type":1,"status":1,"productPlan":null,"currencyAttachmentVos":[{"id":18,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/video/20180209/20180209105431879702921.mp4","consultationId":null,"videoCoverAddress":null},{"id":38,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180209/20180209105527864239043.jpg","consultationId":null,"videoCoverAddress":null}]},{"id":15,"commentNum":0,"modelType":3,"consultationClassifyId":1,"reprintName":null,"fabulousNum":0,"imgNum":null,"phone":"17210000000","shareNum":null,"title":"测试图文资讯001***","memberId":2,"browserAmount":0,"plantId":2,"name":"俊杰","type":0,"status":1,"productPlan":null,"currencyAttachmentVos":[{"id":19,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180209/20180209105527864239043.jpg","consultationId":null,"videoCoverAddress":null},{"id":20,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180209/20180209105527864239043.jpg","consultationId":null,"videoCoverAddress":null},{"id":21,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180209/20180209105527864239043.jpg","consultationId":null,"videoCoverAddress":null}]},{"id":16,"commentNum":0,"modelType":3,"consultationClassifyId":1,"reprintName":null,"fabulousNum":0,"imgNum":null,"phone":"17210000000","shareNum":null,"title":"测试图文资讯001***","memberId":2,"browserAmount":0,"plantId":1,"name":"俊杰","type":0,"status":1,"productPlan":null,"currencyAttachmentVos":[{"id":22,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180209/20180209105527864239043.jpg","consultationId":null,"videoCoverAddress":null},{"id":23,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180209/20180209105527864239043.jpg","consultationId":null,"videoCoverAddress":null},{"id":24,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180209/20180209105527864239043.jpg","consultationId":null,"videoCoverAddress":null}]},{"id":17,"commentNum":0,"modelType":1,"consultationClassifyId":null,"reprintName":null,"fabulousNum":0,"imgNum":null,"phone":"13718003599","shareNum":null,"title":"斯蒂芬","memberId":1,"browserAmount":0,"plantId":2,"name":"俊杰","type":0,"status":1,"productPlan":null,"currencyAttachmentVos":[{"id":60,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180208/20180208134635883392347.png","consultationId":null,"videoCoverAddress":null}]},{"id":18,"commentNum":0,"modelType":1,"consultationClassifyId":null,"reprintName":null,"fabulousNum":0,"imgNum":null,"phone":"18","shareNum":null,"title":"A","memberId":1,"browserAmount":0,"plantId":3,"name":"俊杰","type":0,"status":1,"productPlan":null,"currencyAttachmentVos":[{"id":61,"address":"A","consultationId":null,"videoCoverAddress":null}]}]
     */

    private int code;
    private Object msg;
    private List<ContentBean> content;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
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
         * id : 5
         * commentNum : 0
         * modelType : 0
         * consultationClassifyId : 1
         * reprintName : null
         * fabulousNum : 0
         * imgNum : null
         * phone : 17210000000
         * shareNum : null
         * title : 测试视频资讯002 **
         * memberId : 2
         * browserAmount : 0
         * plantId : 1
         * name : 俊杰
         * type : 1
         * status : 1
         * productPlan : null
         * currencyAttachmentVos : [{"id":18,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/video/20180209/20180209105431879702921.mp4","consultationId":null,"videoCoverAddress":null},{"id":38,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180209/20180209105527864239043.jpg","consultationId":null,"videoCoverAddress":null}]
         */

        private int id;
        private int commentNum;
        private int modelType;
        private int consultationClassifyId;
        private Object reprintName;
        private int fabulousNum;
        private Object imgNum;
        private String phone;
        private Object shareNum;
        private String title;
        private int memberId;
        private int browserAmount;
        private int plantId;
        private String name;
        private int type;
        private int status;
        private ProductPlanBean productPlan;
        private List<CurrencyAttachmentVosBean> currencyAttachmentVos;
        public ProductPlanBean getProductPlan() {
            return productPlan;
        }

        public void setProductPlan(ProductPlanBean productPlan) {
            this.productPlan = productPlan;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(int commentNum) {
            this.commentNum = commentNum;
        }

        public int getModelType() {
            return modelType;
        }

        public void setModelType(int modelType) {
            this.modelType = modelType;
        }

        public int getConsultationClassifyId() {
            return consultationClassifyId;
        }

        public void setConsultationClassifyId(int consultationClassifyId) {
            this.consultationClassifyId = consultationClassifyId;
        }

        public Object getReprintName() {
            return reprintName;
        }

        public void setReprintName(Object reprintName) {
            this.reprintName = reprintName;
        }

        public int getFabulousNum() {
            return fabulousNum;
        }

        public void setFabulousNum(int fabulousNum) {
            this.fabulousNum = fabulousNum;
        }

        public Object getImgNum() {
            return imgNum;
        }

        public void setImgNum(Object imgNum) {
            this.imgNum = imgNum;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Object getShareNum() {
            return shareNum;
        }

        public void setShareNum(Object shareNum) {
            this.shareNum = shareNum;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public int getBrowserAmount() {
            return browserAmount;
        }

        public void setBrowserAmount(int browserAmount) {
            this.browserAmount = browserAmount;
        }

        public int getPlantId() {
            return plantId;
        }

        public void setPlantId(int plantId) {
            this.plantId = plantId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public List<CurrencyAttachmentVosBean> getCurrencyAttachmentVos() {
            return currencyAttachmentVos;
        }

        public void setCurrencyAttachmentVos(List<CurrencyAttachmentVosBean> currencyAttachmentVos) {
            this.currencyAttachmentVos = currencyAttachmentVos;
        }

        public static class ProductPlanBean {
            /**
             * id : 1
             * title : 测试计划发布1
             * description : null
             * commentNum : 14
             * likeNum : 8
             * shareNum : 1
             * status : 1
             * feedback : null
             * createTime : null
             * modelType : 0
             * address : 北京
             * projectPlanId : null
             * projectPlan : null
             * consulationVos : null
             * currencyAttachmentVos : null
             * productS : null
             * productPlanDailyVo : null
             */

            private int id;
            private String title;
            private Object description;
            private int commentNum;
            private int likeNum;
            private int shareNum;
            private int status;
            private Object feedback;
            private Object createTime;
            private int modelType;
            private String address;
            private Object projectPlanId;
            private Object projectPlan;
            private Object consulationVos;
            private Object currencyAttachmentVos;
            private Object productS;
            private Object productPlanDailyVo;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public Object getDescription() {
                return description;
            }

            public void setDescription(Object description) {
                this.description = description;
            }

            public int getCommentNum() {
                return commentNum;
            }

            public void setCommentNum(int commentNum) {
                this.commentNum = commentNum;
            }

            public int getLikeNum() {
                return likeNum;
            }

            public void setLikeNum(int likeNum) {
                this.likeNum = likeNum;
            }

            public int getShareNum() {
                return shareNum;
            }

            public void setShareNum(int shareNum) {
                this.shareNum = shareNum;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public Object getFeedback() {
                return feedback;
            }

            public void setFeedback(Object feedback) {
                this.feedback = feedback;
            }

            public Object getCreateTime() {
                return createTime;
            }

            public void setCreateTime(Object createTime) {
                this.createTime = createTime;
            }

            public int getModelType() {
                return modelType;
            }

            public void setModelType(int modelType) {
                this.modelType = modelType;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public Object getProjectPlanId() {
                return projectPlanId;
            }

            public void setProjectPlanId(Object projectPlanId) {
                this.projectPlanId = projectPlanId;
            }

            public Object getProjectPlan() {
                return projectPlan;
            }

            public void setProjectPlan(Object projectPlan) {
                this.projectPlan = projectPlan;
            }

            public Object getConsulationVos() {
                return consulationVos;
            }

            public void setConsulationVos(Object consulationVos) {
                this.consulationVos = consulationVos;
            }

            public Object getCurrencyAttachmentVos() {
                return currencyAttachmentVos;
            }

            public void setCurrencyAttachmentVos(Object currencyAttachmentVos) {
                this.currencyAttachmentVos = currencyAttachmentVos;
            }

            public Object getProductS() {
                return productS;
            }

            public void setProductS(Object productS) {
                this.productS = productS;
            }

            public Object getProductPlanDailyVo() {
                return productPlanDailyVo;
            }

            public void setProductPlanDailyVo(Object productPlanDailyVo) {
                this.productPlanDailyVo = productPlanDailyVo;
            }
        }
        public static class CurrencyAttachmentVosBean {
            /**
             * id : 18
             * address : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/video/20180209/20180209105431879702921.mp4
             * consultationId : null
             * videoCoverAddress : null
             */

            private int id;
            private String address;
            private Object consultationId;
            private Object videoCoverAddress;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public Object getConsultationId() {
                return consultationId;
            }

            public void setConsultationId(Object consultationId) {
                this.consultationId = consultationId;
            }

            public Object getVideoCoverAddress() {
                return videoCoverAddress;
            }

            public void setVideoCoverAddress(Object videoCoverAddress) {
                this.videoCoverAddress = videoCoverAddress;
            }
        }
    }
}
