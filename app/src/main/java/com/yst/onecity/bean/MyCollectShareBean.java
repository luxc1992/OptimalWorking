package com.yst.onecity.bean;

import java.util.List;

/**
 * 我的收藏分享实体类
 *
 * @author liumanqing
 * @version 1.1.0
 * @date 2018/5/29
 */
public class MyCollectShareBean {

    /**
     * code : 1
     * msg : null
     * content : [{"id":2162,"commentNum":-1,"modelType":1,"consultationClassifyId":126,"reprintName":null,"fabulousNum":1,"imgNum":null,"phone":"15070925726","shareNum":1,"title":"图文资讯","description":null,"memberId":1,"browserAmount":257,"plantId":22722,"name":"7121","type":0,"status":null,"productPlan":{"id":22722,"title":"奶茶1","describes":"627373744","commentNum":4,"likeNum":0,"shareNum":0,"status":1,"feedback":null,"createTime":null,"modelType":0,"address":"北京市","projectPlanId":null,"projectPlan":null,"consulationVos":null,"currencyAttachmentVos":null,"productS":null,"productPlanDailyVo":null},"currencyAttachmentVos":[{"id":46997,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417105419612916113.png","consultationId":2162,"videoCoverAddress":null}]},{"id":2184,"commentNum":0,"modelType":3,"consultationClassifyId":125,"reprintName":null,"fabulousNum":0,"imgNum":null,"phone":"13717847870","shareNum":null,"title":"咨询","description":null,"memberId":492,"browserAmount":34,"plantId":22790,"name":"小猴子","type":0,"status":null,"productPlan":null,"currencyAttachmentVos":[{"id":47286,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180418/20180418100948608675658.jpg","consultationId":2184,"videoCoverAddress":null},{"id":47287,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180418/20180418100959882829848.jpg","consultationId":2184,"videoCoverAddress":null},{"id":47288,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180418/20180418100959989784910.jpg","consultationId":2184,"videoCoverAddress":null}]},{"id":2213,"commentNum":0,"modelType":0,"consultationClassifyId":128,"reprintName":null,"fabulousNum":1,"imgNum":null,"phone":"18612926230","shareNum":null,"title":"666666","description":null,"memberId":522,"browserAmount":19,"plantId":null,"name":"合作","type":1,"status":null,"productPlan":null,"currencyAttachmentVos":[{"id":47836,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/video/20180420/20180420165431256032078.mp4","consultationId":2213,"videoCoverAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180420/20180420165431377144928.jpg"}]},{"id":2242,"commentNum":0,"modelType":3,"consultationClassifyId":128,"reprintName":"娜露","fabulousNum":0,"imgNum":null,"phone":"13126519519","shareNum":0,"title":"外国人","description":null,"memberId":507,"browserAmount":5,"plantId":22925,"name":"一休哥","type":0,"status":null,"productPlan":null,"currencyAttachmentVos":[{"id":48172,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180423/20180423170319569166784.jpg","consultationId":2242,"videoCoverAddress":null},{"id":48173,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180423/20180423170320023639695.jpg","consultationId":2242,"videoCoverAddress":null},{"id":48174,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180423/20180423170320497095260.jpg","consultationId":2242,"videoCoverAddress":null}]},{"id":2260,"commentNum":12,"modelType":1,"consultationClassifyId":123,"reprintName":null,"fabulousNum":1,"imgNum":null,"phone":"18811486281","shareNum":null,"title":"jhj","description":null,"memberId":507,"browserAmount":32,"plantId":null,"name":"一休哥","type":0,"status":null,"productPlan":null,"currencyAttachmentVos":[{"id":48368,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180425/20180425110027717108178.jpg","consultationId":2260,"videoCoverAddress":null}]},{"id":2263,"commentNum":0,"modelType":0,"consultationClassifyId":128,"reprintName":null,"fabulousNum":0,"imgNum":null,"phone":"18811332346","shareNum":null,"title":"测试优化","description":null,"memberId":505,"browserAmount":10,"plantId":null,"name":"songbinbin","type":1,"status":null,"productPlan":null,"currencyAttachmentVos":[{"id":48774,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/video/20180426/20180426092003622894352.mp4","consultationId":2263,"videoCoverAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180426/20180426092004777555994.jpg"}]}]
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
         * id : 2162
         * commentNum : -1
         * modelType : 1
         * consultationClassifyId : 126
         * reprintName : null
         * fabulousNum : 1
         * imgNum : null
         * phone : 15070925726
         * shareNum : 1
         * title : 图文资讯
         * description : null
         * memberId : 1
         * browserAmount : 257
         * plantId : 22722
         * name : 7121
         * type : 0
         * status : null
         * productPlan : {"id":22722,"title":"奶茶1","describes":"627373744","commentNum":4,"likeNum":0,"shareNum":0,"status":1,"feedback":null,"createTime":null,"modelType":0,"address":"北京市","projectPlanId":null,"projectPlan":null,"consulationVos":null,"currencyAttachmentVos":null,"productS":null,"productPlanDailyVo":null}
         * currencyAttachmentVos : [{"id":46997,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417105419612916113.png","consultationId":2162,"videoCoverAddress":null}]
         */

        private int id;
        private int commentNum;
        private int modelType;
        private int consultationClassifyId;
        private String reprintName;
        private int fabulousNum;
        private int imgNum;
        private String phone;
        private int shareNum;
        private String title;
        private String description;
        private int memberId;
        private int browserAmount;
        private int plantId;
        private String name;
        private int type;
        private String status;
        private ProductPlanBean productPlan;
        private List<CurrencyAttachmentVosBean> currencyAttachmentVos;

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

        public String getReprintName() {
            return reprintName;
        }

        public void setReprintName(String reprintName) {
            this.reprintName = reprintName;
        }

        public int getFabulousNum() {
            return fabulousNum;
        }

        public void setFabulousNum(int fabulousNum) {
            this.fabulousNum = fabulousNum;
        }

        public int getImgNum() {
            return imgNum;
        }

        public void setImgNum(int imgNum) {
            this.imgNum = imgNum;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getShareNum() {
            return shareNum;
        }

        public void setShareNum(int shareNum) {
            this.shareNum = shareNum;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public ProductPlanBean getProductPlan() {
            return productPlan;
        }

        public void setProductPlan(ProductPlanBean productPlan) {
            this.productPlan = productPlan;
        }

        public List<CurrencyAttachmentVosBean> getCurrencyAttachmentVos() {
            return currencyAttachmentVos;
        }

        public void setCurrencyAttachmentVos(List<CurrencyAttachmentVosBean> currencyAttachmentVos) {
            this.currencyAttachmentVos = currencyAttachmentVos;
        }

        public static class ProductPlanBean {
            /**
             * id : 22722
             * title : 奶茶1
             * describes : 627373744
             * commentNum : 4
             * likeNum : 0
             * shareNum : 0
             * status : 1
             * feedback : null
             * createTime : null
             * modelType : 0
             * address : 北京市
             * projectPlanId : null
             * projectPlan : null
             * consulationVos : null
             * currencyAttachmentVos : null
             * productS : null
             * productPlanDailyVo : null
             */

            private int id;
            private String title;
            private String describes;
            private int commentNum;
            private int likeNum;
            private int shareNum;
            private int status;
            private String feedback;
            private String createTime;
            private int modelType;
            private String address;
            private int projectPlanId;
            private String projectPlan;
            private String consulationVos;
            private String currencyAttachmentVos;
            private String productS;
            private String productPlanDailyVo;

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

            public String getDescribes() {
                return describes;
            }

            public void setDescribes(String describes) {
                this.describes = describes;
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

            public String getFeedback() {
                return feedback;
            }

            public void setFeedback(String feedback) {
                this.feedback = feedback;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
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

            public int getProjectPlanId() {
                return projectPlanId;
            }

            public void setProjectPlanId(int projectPlanId) {
                this.projectPlanId = projectPlanId;
            }

            public String getProjectPlan() {
                return projectPlan;
            }

            public void setProjectPlan(String projectPlan) {
                this.projectPlan = projectPlan;
            }

            public String getConsulationVos() {
                return consulationVos;
            }

            public void setConsulationVos(String consulationVos) {
                this.consulationVos = consulationVos;
            }

            public String getCurrencyAttachmentVos() {
                return currencyAttachmentVos;
            }

            public void setCurrencyAttachmentVos(String currencyAttachmentVos) {
                this.currencyAttachmentVos = currencyAttachmentVos;
            }

            public String getProductS() {
                return productS;
            }

            public void setProductS(String productS) {
                this.productS = productS;
            }

            public String getProductPlanDailyVo() {
                return productPlanDailyVo;
            }

            public void setProductPlanDailyVo(String productPlanDailyVo) {
                this.productPlanDailyVo = productPlanDailyVo;
            }
        }

        public static class CurrencyAttachmentVosBean {
            /**
             * id : 46997
             * address : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417105419612916113.png
             * consultationId : 2162
             * videoCoverAddress : null
             */

            private int id;
            private String address;
            private int consultationId;
            private String videoCoverAddress;

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

            public int getConsultationId() {
                return consultationId;
            }

            public void setConsultationId(int consultationId) {
                this.consultationId = consultationId;
            }

            public String getVideoCoverAddress() {
                return videoCoverAddress;
            }

            public void setVideoCoverAddress(String videoCoverAddress) {
                this.videoCoverAddress = videoCoverAddress;
            }
        }
    }
}
