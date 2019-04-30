package com.yst.onecity.bean;

import java.util.List;

/**
 * 描述
 *
 * @author luxuchang
 * @version 1.1.0
 * @date 2018/5/24
 */
public class RecommendedBean {

    /**
     * code : 1
     * msg : 查询成功
     * content : [{"id":2188,"commentNum":3,"modelType":0,"consultationClassifyId":124,"reprintName":null,"fabulousNum":2,"imgNum":null,"phone":"13126519519","shareNum":0,"title":"桃花潭水深千尺","description":"5656","memberId":512,"browserAmount":12,"plantId":22792,"name":"娜露","type":0,"status":null,"productPlan":null,"currencyAttachmentVos":[{"id":47347,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180418/20180418115008651171353.jpg","consultationId":null,"videoCoverAddress":null},{"id":47348,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180418/20180418115008662075144.jpg","consultationId":null,"videoCoverAddress":null},{"id":47349,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180418/20180418115008859550019.jpg","consultationId":null,"videoCoverAddress":null}]},{"id":2187,"commentNum":1,"modelType":0,"consultationClassifyId":123,"reprintName":null,"fabulousNum":1,"imgNum":null,"phone":"18500902107","shareNum":0,"title":"美味可口","description":"5656","memberId":501,"browserAmount":15,"plantId":22792,"name":"2107","type":1,"status":null,"productPlan":null,"currencyAttachmentVos":[{"id":47336,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/video/20180418/20180418112912875747428.mp4","consultationId":null,"videoCoverAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180418/20180418112914863090771.png"}]},{"id":2184,"commentNum":0,"modelType":0,"consultationClassifyId":125,"reprintName":null,"fabulousNum":0,"imgNum":null,"phone":"13717847870","shareNum":0,"title":"咨询","description":"5656","memberId":492,"browserAmount":34,"plantId":22790,"name":"小猴子","type":0,"status":null,"productPlan":null,"currencyAttachmentVos":[{"id":47286,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180418/20180418100948608675658.jpg","consultationId":null,"videoCoverAddress":null},{"id":47287,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180418/20180418100959882829848.jpg","consultationId":null,"videoCoverAddress":null},{"id":47288,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180418/20180418100959989784910.jpg","consultationId":null,"videoCoverAddress":null}]},{"id":2164,"commentNum":0,"modelType":0,"consultationClassifyId":125,"reprintName":null,"fabulousNum":0,"imgNum":null,"phone":"13717847870","shareNum":0,"title":"咨询01","description":"5656","memberId":492,"browserAmount":0,"plantId":22724,"name":"小猴子","type":0,"status":null,"productPlan":null,"currencyAttachmentVos":[{"id":47013,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417110351874670215.jpg","consultationId":null,"videoCoverAddress":null},{"id":47014,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417110357669601094.jpg","consultationId":null,"videoCoverAddress":null},{"id":47015,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417110357875141432.jpg","consultationId":null,"videoCoverAddress":null},{"id":47016,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417110358216148299.jpg","consultationId":null,"videoCoverAddress":null}]},{"id":2163,"commentNum":7,"modelType":0,"consultationClassifyId":124,"reprintName":null,"fabulousNum":0,"imgNum":null,"phone":"18811332346","shareNum":0,"title":"图文咨询1","description":"5656","memberId":505,"browserAmount":43,"plantId":null,"name":"songbinbin","type":0,"status":null,"productPlan":null,"currencyAttachmentVos":[{"id":47000,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417105730111361288.jpg","consultationId":null,"videoCoverAddress":null}]},{"id":2162,"commentNum":0,"modelType":0,"consultationClassifyId":126,"reprintName":null,"fabulousNum":0,"imgNum":null,"phone":"15070925726","shareNum":1,"title":"图文资讯","description":"5656","memberId":1,"browserAmount":7,"plantId":22722,"name":"7121","type":0,"status":null,"productPlan":null,"currencyAttachmentVos":[{"id":46997,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417105419612916113.png","consultationId":null,"videoCoverAddress":null}]}]
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
         * id : 2188
         * commentNum : 3
         * modelType : 0
         * consultationClassifyId : 124
         * reprintName : null
         * fabulousNum : 2
         * imgNum : null
         * phone : 13126519519
         * shareNum : 0
         * title : 桃花潭水深千尺
         * description : 5656
         * memberId : 512
         * browserAmount : 12
         * plantId : 22792
         * name : 娜露
         * type : 0
         * status : null
         * productPlan : null
         * currencyAttachmentVos : [{"id":47347,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180418/20180418115008651171353.jpg","consultationId":null,"videoCoverAddress":null},{"id":47348,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180418/20180418115008662075144.jpg","consultationId":null,"videoCoverAddress":null},{"id":47349,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180418/20180418115008859550019.jpg","consultationId":null,"videoCoverAddress":null}]
         */

        private int id;
        private int commentNum;
        private int modelType;
        private int consultationClassifyId;
        private Object reprintName;
        private int fabulousNum;
        private Object imgNum;
        private String phone;
        private int shareNum;
        private String title;
        private String description;
        private int memberId;
        private int browserAmount;
        private int plantId;
        private String name;
        private int type;
        private Object status;
        private Object productPlan;
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

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public Object getProductPlan() {
            return productPlan;
        }

        public void setProductPlan(Object productPlan) {
            this.productPlan = productPlan;
        }

        public List<CurrencyAttachmentVosBean> getCurrencyAttachmentVos() {
            return currencyAttachmentVos;
        }

        public void setCurrencyAttachmentVos(List<CurrencyAttachmentVosBean> currencyAttachmentVos) {
            this.currencyAttachmentVos = currencyAttachmentVos;
        }

        public static class CurrencyAttachmentVosBean {
            /**
             * id : 47347
             * address : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180418/20180418115008651171353.jpg
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
