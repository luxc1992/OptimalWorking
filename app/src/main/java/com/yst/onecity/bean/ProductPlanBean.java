package com.yst.onecity.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 产品计划实体类
 *
 * @author liumanqing
 * @version 1.0.1
 * @date 2018/2/23
 */
public class ProductPlanBean implements Serializable {

    /**
     * code : 1
     * content : [{"id":1,"title":"测试计划发布1","description":null,"commentNum":11,"likeNum":8,"shareNum":1,"headImg":null,"img":null,"author":null,"consulationVos":null,"currencyAttachmentVos":null,"productS":[{"id":5,"badCommentNum":11,"barCode":"1003","commonCommentNum":11,"name":"供应商测试商品","niceCommentNum":11,"saleNum":1,"imageAttachmentAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180208/20180208134635883392347.png","merchantId":3,"productTypeId":1,"supplierId":1,"description":"供应商测试商品的描述","title":"11","isBargain":1,"isExplosion":0,"visitors":null,"address":null,"price":10000},null],"productPlanDailyVo":{"id":4,"createdTime":"1519712832000","title":"1","memberId":1,"productPlanId":1,"content":"1","currencyAttachments":[{"id":null,"address":"1","header":null,"attachmentId":null,"consultationId":null,"videoCoverAddress":null}]},"status":0,"feedback":null,"createTime":null,"address":"北京"},{"browserAmount":0,"commentNum":9,"currencyAttachmentVos":[{"address":"sdfdsdf.jpg","id":196}],"fabulousNum":0,"id":40,"memberId":1,"name":"廉金雪","phone":"15011552664","plantId":1,"status":1,"title":"发的生第三方","type":0},{"browserAmount":0,"commentNum":0,"consultationClassifyId":1,"currencyAttachmentVos":[{"address":"sdfdsdf.jpg","id":202}],"fabulousNum":0,"id":43,"memberId":1,"name":"廉金雪","phone":"15011552664","plantId":1,"status":1,"title":"自定义内容区","type":0},{"browserAmount":0,"commentNum":0,"currencyAttachmentVos":[{"address":"A","id":68}],"fabulousNum":0,"id":22,"memberId":1,"name":"廉金雪","phone":"13","plantId":3,"productPlan":{"commentNum":1,"id":3,"likeNum":2,"shareNum":3,"title":"ceshijihuafabu"},"status":1,"title":"A","type":1},{"commentNum":0,"consultationClassifyId":1,"currencyAttachmentVos":[{"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180209/20180209105527864239043.jpg","id":26},{"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180209/20180209105527864239043.jpg","id":27},{"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180209/20180209105527864239043.jpg","id":29},{"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180209/20180209105527864239043.jpg","id":31},{"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180209/20180209105527864239043.jpg","id":32},{"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180209/20180209105527864239043.jpg","id":33},{"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180209/20180209105527864239043.jpg","id":35}],"id":2,"memberId":1,"name":"廉金雪","plantId":2,"shareNum":27,"status":1,"title":"错错错"}]
     * msg : 查询成功
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
        @Override
        public String toString() {
            return "ContentBean{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", describes='" + describes + '\'' +
                    ", commentNum=" + commentNum +
                    ", likeNum=" + likeNum +
                    ", shareNum=" + shareNum +
                    ", modelType=" + modelType +
                    ", headImg=" + headImg +
                    ", img=" + img +
                    ", author=" + author +
                    ", consulationVos=" + consulationVos +
                    ", currencyAttachmentVos=" + currencyAttachmentVos +
                    ", productPlanDailyVo=" + productPlanDailyVo +
                    ", status=" + status +
                    ", feedback='" + feedback + '\'' +
                    ", createTime='" + createTime + '\'' +
                    ", address='" + address + '\'' +
                    ", browserAmount=" + browserAmount +
                    ", fabulousNum=" + fabulousNum +
                    ", memberId=" + memberId +
                    ", name='" + name + '\'' +
                    ", phone='" + phone + '\'' +
                    ", plantId=" + plantId +
                    ", type=" + type +
                    ", consultationClassifyId=" + consultationClassifyId +
                    ", productPlan=" + productPlan +
                    ", productS=" + productS +
                    ", updateTime='" + updateTime + '\'' +
                    ", projectPlan='" + projectPlan + '\'' +
                    '}';
        }

        /**
         * id : 1
         * title : 测试计划发布1
         * description : null
         * commentNum : 11
         * likeNum : 8
         * shareNum : 1
         * headImg : null
         * img : null
         * author : null
         * consulationVos : null
         * currencyAttachmentVos : null
         * productS : [{"id":5,"badCommentNum":11,"barCode":"1003","commonCommentNum":11,"name":"供应商测试商品","niceCommentNum":11,"saleNum":1,"imageAttachmentAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180208/20180208134635883392347.png","merchantId":3,"productTypeId":1,"supplierId":1,"description":"供应商测试商品的描述","title":"11","isBargain":1,"isExplosion":0,"visitors":null,"address":null,"price":10000},null]
         * productPlanDailyVo : {"id":4,"createdTime":"1519712832000","title":"1","memberId":1,"productPlanId":1,"content":"1","currencyAttachments":[{"id":null,"address":"1","header":null,"attachmentId":null,"consultationId":null,"videoCoverAddress":null}]}
         * status : 0
         * feedback : null
         * createTime : null
         * address : 北京
         * browserAmount : 0
         * fabulousNum : 0
         * memberId : 1
         * name : 廉金雪
         * phone : 15011552664
         * plantId : 1
         * type : 0
         * consultationClassifyId : 1
         * productPlan : {"commentNum":1,"id":3,"likeNum":2,"shareNum":3,"title":"ceshijihuafabu"}
         */

        private int id;
        private String title;
        private String describes;
        private int commentNum;
        private int likeNum;
        private int shareNum;
        private int modelType;
        private Object headImg;
        private Object img;
        private Object author;
        private Object consulationVos;
        private List<CurrencyAttachmentVos> currencyAttachmentVos;
        private ProductPlanDailyVoBean productPlanDailyVo;
        private int status;
        private String feedback;
        private String createTime;
        private String address;
        private int browserAmount;
        private int fabulousNum;
        private int memberId;
        private String name;
        private String phone;
        private int plantId;
        private int type;
        private int consultationClassifyId;
        private ProductPlanBean productPlan;
        private List<ProductSBean> productS;
        private String updateTime;
        private String projectPlan;

        public int getModelType() {
            return modelType;
        }

        public void setModelType(int modelType) {
            this.modelType = modelType;
        }

        public String getProjectPlan() {
            return projectPlan;
        }

        public void setProjectPlan(String projectPlan) {
            this.projectPlan = projectPlan;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

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

        public String getDescription() {
            return describes;
        }

        public void setDescription(String describes) {
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

        public Object getHeadImg() {
            return headImg;
        }

        public void setHeadImg(Object headImg) {
            this.headImg = headImg;
        }

        public Object getImg() {
            return img;
        }

        public void setImg(Object img) {
            this.img = img;
        }

        public Object getAuthor() {
            return author;
        }

        public void setAuthor(Object author) {
            this.author = author;
        }

        public Object getConsulationVos() {
            return consulationVos;
        }

        public void setConsulationVos(Object consulationVos) {
            this.consulationVos = consulationVos;
        }

        public List<CurrencyAttachmentVos> getCurrencyAttachmentVos() {
            return currencyAttachmentVos;
        }

        public void setCurrencyAttachmentVos(List<CurrencyAttachmentVos> currencyAttachmentVos) {
            this.currencyAttachmentVos = currencyAttachmentVos;
        }

        public ProductPlanDailyVoBean getProductPlanDailyVo() {
            return productPlanDailyVo;
        }

        public void setProductPlanDailyVo(ProductPlanDailyVoBean productPlanDailyVo) {
            this.productPlanDailyVo = productPlanDailyVo;
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getBrowserAmount() {
            return browserAmount;
        }

        public void setBrowserAmount(int browserAmount) {
            this.browserAmount = browserAmount;
        }

        public int getFabulousNum() {
            return fabulousNum;
        }

        public void setFabulousNum(int fabulousNum) {
            this.fabulousNum = fabulousNum;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getPlantId() {
            return plantId;
        }

        public void setPlantId(int plantId) {
            this.plantId = plantId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getConsultationClassifyId() {
            return consultationClassifyId;
        }

        public void setConsultationClassifyId(int consultationClassifyId) {
            this.consultationClassifyId = consultationClassifyId;
        }

        public ProductPlanBean getProductPlan() {
            return productPlan;
        }

        public void setProductPlan(ProductPlanBean productPlan) {
            this.productPlan = productPlan;
        }

        public List<ProductSBean> getProductS() {
            return productS;
        }

        public void setProductS(List<ProductSBean> productS) {
            this.productS = productS;
        }

        public static class ProductPlanDailyVoBean implements Serializable {
            @Override
            public String toString() {
                return "ProductPlanDailyVoBean{" +
                        "id=" + id +
                        ", createdTime='" + createdTime + '\'' +
                        ", title='" + title + '\'' +
                        ", memberId=" + memberId +
                        ", productPlanId=" + productPlanId +
                        ", content='" + content + '\'' +
                        ", url='" + url + '\'' +
                        ", name='" + name + '\'' +
                        ", currencyAttachments=" + currencyAttachments +
                        '}';
            }

            /**
             * id : 4
             * createdTime : 1519712832000
             * title : 1
             * memberId : 1
             * productPlanId : 1
             * content : 1
             * currencyAttachments : [{"id":null,"address":"1","header":null,"attachmentId":null,"consultationId":null,"videoCoverAddress":null}]
             */

            private int id;
            private String createdTime;
            private String title;
            private int memberId;
            private int productPlanId;
            private String content;
            private String url;
            private String name;
            private int modelType;
            private List<CurrencyAttachmentsBean> currencyAttachments;

            public int getModelType() {
                return modelType;
            }

            public void setModelType(int modelType) {
                this.modelType = modelType;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCreatedTime() {
                return createdTime;
            }

            public void setCreatedTime(String createdTime) {
                this.createdTime = createdTime;
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

            public int getProductPlanId() {
                return productPlanId;
            }

            public void setProductPlanId(int productPlanId) {
                this.productPlanId = productPlanId;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public List<CurrencyAttachmentsBean> getCurrencyAttachments() {
                return currencyAttachments;
            }

            public void setCurrencyAttachments(List<CurrencyAttachmentsBean> currencyAttachments) {
                this.currencyAttachments = currencyAttachments;
            }

            public static class CurrencyAttachmentsBean implements Serializable {
                @Override
                public String toString() {
                    return "CurrencyAttachmentsBean{" +
                            "id=" + id +
                            ", address='" + address + '\'' +
                            ", header=" + header +
                            ", attachmentId=" + attachmentId +
                            ", consultationId=" + consultationId +
                            ", videoCoverAddress='" + videoCoverAddress + '\'' +
                            '}';
                }

                /**
                 * id : null
                 * address : 1
                 * header : null
                 * attachmentId : null
                 * consultationId : null
                 * videoCoverAddress : null
                 */

                private Object id;
                private String address;
                private Object header;
                private Object attachmentId;
                private Object consultationId;
                private String videoCoverAddress;

                public Object getId() {
                    return id;
                }

                public void setId(Object id) {
                    this.id = id;
                }

                public String getAddress() {
                    return address;
                }

                public void setAddress(String address) {
                    this.address = address;
                }

                public Object getHeader() {
                    return header;
                }

                public void setHeader(Object header) {
                    this.header = header;
                }

                public Object getAttachmentId() {
                    return attachmentId;
                }

                public void setAttachmentId(Object attachmentId) {
                    this.attachmentId = attachmentId;
                }

                public Object getConsultationId() {
                    return consultationId;
                }

                public void setConsultationId(Object consultationId) {
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

        public class ProductPlan2Bean implements Serializable {
            @Override
            public String toString() {
                return "ProductPlan2Bean{" +
                        "commentNum=" + commentNum +
                        ", id=" + id +
                        ", likeNum=" + likeNum +
                        ", shareNum=" + shareNum +
                        ", title='" + title + '\'' +
                        '}';
            }

            /**
             * commentNum : 1
             * id : 3
             * likeNum : 2
             * shareNum : 3
             * title : ceshijihuafabu
             */

            private int commentNum;
            private int id;
            private int likeNum;
            private int shareNum;
            private String title;

            public int getCommentNum() {
                return commentNum;
            }

            public void setCommentNum(int commentNum) {
                this.commentNum = commentNum;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }

        public static class CurrencyAttachmentVos implements Serializable {

            @Override
            public String toString() {
                return "CurrencyAttachmentVos{" +
                        "id=" + id +
                        ", address='" + address + '\'' +
                        ", consultationId=" + consultationId +
                        ", videoCoverAddress='" + videoCoverAddress + '\'' +
                        '}';
            }

            /**
             * id : 91
             * address : 1
             * consultationId : null
             * videoCoverAddress : 1
             */

            private int id;
            private String address;
            private Object consultationId;
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

            public Object getConsultationId() {
                return consultationId;
            }

            public void setConsultationId(Object consultationId) {
                this.consultationId = consultationId;
            }

            public String getVideoCoverAddress() {
                return videoCoverAddress;
            }

            public void setVideoCoverAddress(String videoCoverAddress) {
                this.videoCoverAddress = videoCoverAddress;
            }
        }

        public static class ProductSBean implements Serializable {
            @Override
            public String toString() {
                return "ProductSBean{" +
                        "id=" + id +
                        ", badCommentNum=" + badCommentNum +
                        ", barCode='" + barCode + '\'' +
                        ", commonCommentNum=" + commonCommentNum +
                        ", name='" + name + '\'' +
                        ", niceCommentNum=" + niceCommentNum +
                        ", saleNum=" + saleNum +
                        ", imageAttachmentAddress='" + imageAttachmentAddress + '\'' +
                        ", merchantId=" + merchantId +
                        ", productTypeId=" + productTypeId +
                        ", supplierId=" + supplierId +
                        ", description='" + description + '\'' +
                        ", title='" + title + '\'' +
                        ", isBargain=" + isBargain +
                        ", isExplosion=" + isExplosion +
                        ", visitors=" + visitors +
                        ", address='" + address + '\'' +
                        ", minPrice='" + minPrice + '\'' +
                        ", maxPrice='" + maxPrice + '\'' +
                        '}';
            }

            /**
             * id : 5
             * badCommentNum : 11
             * barCode : 1003
             * commonCommentNum : 11
             * name : 供应商测试商品
             * niceCommentNum : 11
             * saleNum : 1
             * imageAttachmentAddress : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180208/20180208134635883392347.png
             * merchantId : 3
             * productTypeId : 1
             * supplierId : 1
             * description : 供应商测试商品的描述
             * title : 11
             * isBargain : 1
             * isExplosion : 0
             * visitors : null
             * address : null
             * price : 10000
             */

            private int id;
            private int badCommentNum;
            private String barCode;
            private int commonCommentNum;
            private String name;
            private int niceCommentNum;
            private int saleNum;
            private String imageAttachmentAddress;
            private int merchantId;
            private int productTypeId;
            private int supplierId;
            private String description;
            private String title;
            private int isBargain;
            private int isExplosion;
            private Object visitors;
            private String address;
            private String minPrice;
            private String maxPrice;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getBadCommentNum() {
                return badCommentNum;
            }

            public void setBadCommentNum(int badCommentNum) {
                this.badCommentNum = badCommentNum;
            }

            public String getBarCode() {
                return barCode;
            }

            public void setBarCode(String barCode) {
                this.barCode = barCode;
            }

            public int getCommonCommentNum() {
                return commonCommentNum;
            }

            public void setCommonCommentNum(int commonCommentNum) {
                this.commonCommentNum = commonCommentNum;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getNiceCommentNum() {
                return niceCommentNum;
            }

            public void setNiceCommentNum(int niceCommentNum) {
                this.niceCommentNum = niceCommentNum;
            }

            public int getSaleNum() {
                return saleNum;
            }

            public void setSaleNum(int saleNum) {
                this.saleNum = saleNum;
            }

            public String getImageAttachmentAddress() {
                return imageAttachmentAddress;
            }

            public void setImageAttachmentAddress(String imageAttachmentAddress) {
                this.imageAttachmentAddress = imageAttachmentAddress;
            }

            public int getMerchantId() {
                return merchantId;
            }

            public void setMerchantId(int merchantId) {
                this.merchantId = merchantId;
            }

            public int getProductTypeId() {
                return productTypeId;
            }

            public void setProductTypeId(int productTypeId) {
                this.productTypeId = productTypeId;
            }

            public int getSupplierId() {
                return supplierId;
            }

            public void setSupplierId(int supplierId) {
                this.supplierId = supplierId;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getIsBargain() {
                return isBargain;
            }

            public void setIsBargain(int isBargain) {
                this.isBargain = isBargain;
            }

            public int getIsExplosion() {
                return isExplosion;
            }

            public void setIsExplosion(int isExplosion) {
                this.isExplosion = isExplosion;
            }

            public Object getVisitors() {
                return visitors;
            }

            public void setVisitors(Object visitors) {
                this.visitors = visitors;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getMinPrice() {
                return minPrice;
            }

            public void setMinPrice(String minPrice) {
                this.minPrice = minPrice;
            }

            public String getMaxPrice() {
                return maxPrice;
            }

            public void setMaxPrice(String maxPrice) {
                this.maxPrice = maxPrice;
            }
        }
    }
}
