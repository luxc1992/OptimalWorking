package com.yst.onecity.bean.agent;

import com.yst.onecity.bean.search.SearchShareBean;

import java.io.Serializable;
import java.util.List;

/**
 * 根据分类id获取数据实体类
 *
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/5/26
 */

public class ByIdBean {

    /**
     * code : 1
     * msg : 查询成功
     * content : [{"id":2338,"commentNum":0,"modelType":1,"consultationClassifyId":123,"reprintName":null,"fabulousNum":0,"imgNum":null,"phone":"15522513765","shareNum":0,"title":"订单","description":"房贷首付","memberId":1,"browserAmount":null,"plantId":null,"name":"7121","type":0,"status":1,"productPlan":null,"currencyAttachmentVos":[{"id":49464,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180524/20180524173045775402844.png","consultationId":null,"videoCoverAddress":null}]},{"id":2337,"commentNum":0,"modelType":1,"consultationClassifyId":125,"reprintName":null,"fabulousNum":0,"imgNum":null,"phone":"15522513765","shareNum":0,"title":"哈哈哈哈哈哈","description":"反倒是","memberId":1,"browserAmount":2,"plantId":null,"name":"7121","type":0,"status":1,"productPlan":null,"currencyAttachmentVos":[{"id":49463,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180524/20180524162205913415297.png","consultationId":null,"videoCoverAddress":null}]},{"id":2336,"commentNum":0,"modelType":1,"consultationClassifyId":124,"reprintName":null,"fabulousNum":0,"imgNum":null,"phone":"15522513765","shareNum":0,"title":"范德萨范德萨","description":"发射点发射点","memberId":1,"browserAmount":2,"plantId":null,"name":"7121","type":0,"status":1,"productPlan":null,"currencyAttachmentVos":[{"id":49462,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180524/20180524161852640046842.png","consultationId":null,"videoCoverAddress":null}]},{"id":2335,"commentNum":0,"modelType":1,"consultationClassifyId":124,"reprintName":null,"fabulousNum":0,"imgNum":null,"phone":"15522513765","shareNum":0,"title":"fdsds","description":"fdsfds","memberId":1,"browserAmount":2,"plantId":null,"name":"7121","type":0,"status":1,"productPlan":null,"currencyAttachmentVos":[{"id":49461,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180524/20180524161328637137325.png","consultationId":null,"videoCoverAddress":null}]},{"id":2334,"commentNum":0,"modelType":1,"consultationClassifyId":124,"reprintName":null,"fabulousNum":0,"imgNum":null,"phone":"15522513765","shareNum":0,"title":"fdsf","description":"dsfds","memberId":1,"browserAmount":2,"plantId":null,"name":"7121","type":0,"status":1,"productPlan":null,"currencyAttachmentVos":[{"id":49460,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180524/20180524161245252245948.png","consultationId":null,"videoCoverAddress":null}]}]
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

    public static class ContentBean implements Serializable {
        /**
         * id : 2338
         * commentNum : 0
         * modelType : 1
         * consultationClassifyId : 123
         * reprintName : null
         * fabulousNum : 0
         * imgNum : null
         * phone : 15522513765
         * shareNum : 0
         * title : 订单
         * description : 房贷首付
         * memberId : 1
         * browserAmount : null
         * plantId : null
         * name : 7121
         * type : 0
         * status : 1
         * productPlan : null
         * currencyAttachmentVos : [{"id":49464,"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180524/20180524173045775402844.png","consultationId":null,"videoCoverAddress":null}]
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
        private Object browserAmount;
        private Object plantId;
        private String name;
        private int type;
        private int status;
        private Object productPlan;
        private List<CurrencyAttachmentVosBean> currencyAttachmentVos;
        private boolean isClick;
        private List<SearchShareBean.ContentBean.CurrencyAttachmentVosBean> img;

        public List<SearchShareBean.ContentBean.CurrencyAttachmentVosBean> getImg() {
            return img;
        }

        public void setImg(List<SearchShareBean.ContentBean.CurrencyAttachmentVosBean> img) {
            this.img = img;
        }

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
             * id : 49464
             * address : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180524/20180524173045775402844.png
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
