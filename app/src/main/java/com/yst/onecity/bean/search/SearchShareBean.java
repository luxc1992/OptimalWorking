package com.yst.onecity.bean.search;

import com.yst.onecity.bean.MsgBean;

import java.util.List;

/**
 * 搜索分享
 *
 * @author qinchaoshuai
 * @version 1.0.1
 * @date 2018/5/26.
 */

public class SearchShareBean extends MsgBean{


    private List<ContentBean> content;

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * id : 2348
         * commentNum : null
         * modelType : 3
         * consultationClassifyId : null
         * reprintName : null
         * fabulousNum : 0
         * imgNum : null
         * phone : null
         * shareNum : 0
         * title : 我是谁
         * description : 范德萨范德萨
         * memberId : 1
         * browserAmount : null
         * plantId : null
         * name : null
         * type : null
         * status : 0
         * productPlan : null
         * currencyAttachmentVos : [{"id":49497,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180531/20180531113734784797184.jpg","consultationId":null,"videoCoverAddress":null},{"id":49498,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417113805125039274.jpg","consultationId":null,"videoCoverAddress":null},{"id":49499,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417113818012152925.jpg","consultationId":null,"videoCoverAddress":null}]
         */

        private int id;
        private Object commentNum;
        private int modelType;
        private Object consultationClassifyId;
        private Object reprintName;
        private int fabulousNum;
        private Object imgNum;
        private Object phone;
        private int shareNum;
        private String title;
        private String description;
        private int memberId;
        private Object browserAmount;
        private Object plantId;
        private Object name;
        private Object type;
        private int status;
        private Object productPlan;
        private List<CurrencyAttachmentVosBean> currencyAttachmentVos;
        private boolean isClick;

        public boolean isClick() {
            return isClick;
        }

        public void setClick(boolean click) {
            isClick = click;
        }
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(Object commentNum) {
            this.commentNum = commentNum;
        }

        public int getModelType() {
            return modelType;
        }

        public void setModelType(int modelType) {
            this.modelType = modelType;
        }

        public Object getConsultationClassifyId() {
            return consultationClassifyId;
        }

        public void setConsultationClassifyId(Object consultationClassifyId) {
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

        public Object getPhone() {
            return phone;
        }

        public void setPhone(Object phone) {
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

        public Object getBrowserAmount() {
            return browserAmount;
        }

        public void setBrowserAmount(Object browserAmount) {
            this.browserAmount = browserAmount;
        }

        public Object getPlantId() {
            return plantId;
        }

        public void setPlantId(Object plantId) {
            this.plantId = plantId;
        }

        public Object getName() {
            return name;
        }

        public void setName(Object name) {
            this.name = name;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
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
             * id : 49497
             * address : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180531/20180531113734784797184.jpg
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
