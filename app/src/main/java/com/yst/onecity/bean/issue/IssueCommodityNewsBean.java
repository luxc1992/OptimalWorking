package com.yst.onecity.bean.issue;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhaiyanwu
 * @version v3.0.1
 * @date 2018/3/14.
 */
public class IssueCommodityNewsBean implements Serializable{

    /**
     * code : 1
     * msg : 获取成功
     * content : {"imageJSON":[{"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180316/20180316170351146162325.jpg","productId":2122,"id":674,"sort":1}],"productJson":{"id":2122,"createUser":null,"createdTime":"2018-03-16 17:04:49","updateTime":"2018-03-16 17:04:49","createdIp":null,"badCommentNum":null,"barCode":"1521191088702","commonCommentNum":null,"name":"q","niceCommentNum":null,"saleNum":null,"status":0,"type":1,"imageAttachmentAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180316/20180316170351146162325.jpg","merchantId":11,"productTypeId":99,"supplierId":null,"description":"[{\"type\":0,\"content\":\"vcv\",\"viewImg\":\"\",\"sort\":1}]","examineStatus":2,"isDeleted":0,"title":"s","productWight":null,"productVolume":null,"isBargain":0,"isExplosion":0,"freightRuleMainId":43,"isInitiate":null,"visitors":null,"address":"","unit":null},"specificationAndValJson":[{"specificationValue":["vv"],"specificationName":"vv"}],"towProductTypeName":{"classify_name":"分类21","onename":"分类二","id":99},"description":[{"view_img":"","sort":1,"type":0,"content":"vcv"}],"productSkuJSON":[{"address":"","salePrice":0,"price":null,"num":0,"id":1581,"content":"vv"}],"firstProductTypeName":"分类二"}
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

    public static class ContentBean  implements Serializable{
        /**
         * imageJSON : [{"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180316/20180316170351146162325.jpg","productId":2122,"id":674,"sort":1}]
         * productJson : {"id":2122,"createUser":null,"createdTime":"2018-03-16 17:04:49","updateTime":"2018-03-16 17:04:49","createdIp":null,"badCommentNum":null,"barCode":"1521191088702","commonCommentNum":null,"name":"q","niceCommentNum":null,"saleNum":null,"status":0,"type":1,"imageAttachmentAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180316/20180316170351146162325.jpg","merchantId":11,"productTypeId":99,"supplierId":null,"description":"[{\"type\":0,\"content\":\"vcv\",\"viewImg\":\"\",\"sort\":1}]","examineStatus":2,"isDeleted":0,"title":"s","productWight":null,"productVolume":null,"isBargain":0,"isExplosion":0,"freightRuleMainId":43,"isInitiate":null,"visitors":null,"address":"","unit":null}
         * specificationAndValJson : [{"specificationValue":["vv"],"specificationName":"vv"}]
         * towProductTypeName : {"classify_name":"分类21","onename":"分类二","id":99}
         * description : [{"view_img":"","sort":1,"type":0,"content":"vcv"}]
         * productSkuJSON : [{"address":"","salePrice":0,"price":null,"num":0,"id":1581,"content":"vv"}]
         * firstProductTypeName : 分类二
         */

        private ProductJsonBean productJson;
        private TowProductTypeNameBean towProductTypeName;
        private String firstProductTypeName;
        private List<ImageJSONBean> imageJSON;
        private List<SpecificationAndValJsonBean> specificationAndValJson;
        private List<DescriptionBean> description;
        private List<ProductSkuJSONBean> productSkuJSON;

        public ProductJsonBean getProductJson() {
            return productJson;
        }

        public void setProductJson(ProductJsonBean productJson) {
            this.productJson = productJson;
        }

        public TowProductTypeNameBean getTowProductTypeName() {
            return towProductTypeName;
        }

        public void setTowProductTypeName(TowProductTypeNameBean towProductTypeName) {
            this.towProductTypeName = towProductTypeName;
        }

        public String getFirstProductTypeName() {
            return firstProductTypeName;
        }

        public void setFirstProductTypeName(String firstProductTypeName) {
            this.firstProductTypeName = firstProductTypeName;
        }

        public List<ImageJSONBean> getImageJSON() {
            return imageJSON;
        }

        public void setImageJSON(List<ImageJSONBean> imageJSON) {
            this.imageJSON = imageJSON;
        }

        public List<SpecificationAndValJsonBean> getSpecificationAndValJson() {
            return specificationAndValJson;
        }

        public void setSpecificationAndValJson(List<SpecificationAndValJsonBean> specificationAndValJson) {
            this.specificationAndValJson = specificationAndValJson;
        }

        public List<DescriptionBean> getDescription() {
            return description;
        }

        public void setDescription(List<DescriptionBean> description) {
            this.description = description;
        }

        public List<ProductSkuJSONBean> getProductSkuJSON() {
            return productSkuJSON;
        }

        public void setProductSkuJSON(List<ProductSkuJSONBean> productSkuJSON) {
            this.productSkuJSON = productSkuJSON;
        }

        public static class ProductJsonBean  implements Serializable{
            /**
             * id : 2122
             * createUser : null
             * createdTime : 2018-03-16 17:04:49
             * updateTime : 2018-03-16 17:04:49
             * createdIp : null
             * badCommentNum : null
             * barCode : 1521191088702
             * commonCommentNum : null
             * name : q
             * niceCommentNum : null
             * saleNum : null
             * status : 0
             * type : 1
             * imageAttachmentAddress : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180316/20180316170351146162325.jpg
             * merchantId : 11
             * productTypeId : 99
             * supplierId : null
             * description : [{"type":0,"content":"vcv","viewImg":"","sort":1}]
             * examineStatus : 2
             * isDeleted : 0
             * title : s
             * productWight : null
             * productVolume : null
             * isBargain : 0
             * isExplosion : 0
             * freightRuleMainId : 43
             * isInitiate : null
             * visitors : null
             * address :
             * unit : null
             */

            private int id;
            private Object createUser;
            private String createdTime;
            private String updateTime;
            private Object createdIp;
            private Object badCommentNum;
            private String barCode;
            private Object commonCommentNum;
            private String name;
            private Object niceCommentNum;
            private Object saleNum;
            private int status;
            private int type;
            private String imageAttachmentAddress;
            private int merchantId;
            private int productTypeId;
            private Object supplierId;
            private String description;
            private int examineStatus;
            private int isDeleted;
            private String title;
            private Object productWight;
            private Object productVolume;
            private int isBargain;
            private int isExplosion;
            private int freightRuleMainId;
            private Object isInitiate;
            private Object visitors;
            private String address;
            private Object unit;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public Object getCreateUser() {
                return createUser;
            }

            public void setCreateUser(Object createUser) {
                this.createUser = createUser;
            }

            public String getCreatedTime() {
                return createdTime;
            }

            public void setCreatedTime(String createdTime) {
                this.createdTime = createdTime;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public Object getCreatedIp() {
                return createdIp;
            }

            public void setCreatedIp(Object createdIp) {
                this.createdIp = createdIp;
            }

            public Object getBadCommentNum() {
                return badCommentNum;
            }

            public void setBadCommentNum(Object badCommentNum) {
                this.badCommentNum = badCommentNum;
            }

            public String getBarCode() {
                return barCode;
            }

            public void setBarCode(String barCode) {
                this.barCode = barCode;
            }

            public Object getCommonCommentNum() {
                return commonCommentNum;
            }

            public void setCommonCommentNum(Object commonCommentNum) {
                this.commonCommentNum = commonCommentNum;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Object getNiceCommentNum() {
                return niceCommentNum;
            }

            public void setNiceCommentNum(Object niceCommentNum) {
                this.niceCommentNum = niceCommentNum;
            }

            public Object getSaleNum() {
                return saleNum;
            }

            public void setSaleNum(Object saleNum) {
                this.saleNum = saleNum;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
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

            public Object getSupplierId() {
                return supplierId;
            }

            public void setSupplierId(Object supplierId) {
                this.supplierId = supplierId;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public int getExamineStatus() {
                return examineStatus;
            }

            public void setExamineStatus(int examineStatus) {
                this.examineStatus = examineStatus;
            }

            public int getIsDeleted() {
                return isDeleted;
            }

            public void setIsDeleted(int isDeleted) {
                this.isDeleted = isDeleted;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public Object getProductWight() {
                return productWight;
            }

            public void setProductWight(Object productWight) {
                this.productWight = productWight;
            }

            public Object getProductVolume() {
                return productVolume;
            }

            public void setProductVolume(Object productVolume) {
                this.productVolume = productVolume;
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

            public int getFreightRuleMainId() {
                return freightRuleMainId;
            }

            public void setFreightRuleMainId(int freightRuleMainId) {
                this.freightRuleMainId = freightRuleMainId;
            }

            public Object getIsInitiate() {
                return isInitiate;
            }

            public void setIsInitiate(Object isInitiate) {
                this.isInitiate = isInitiate;
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

            public Object getUnit() {
                return unit;
            }

            public void setUnit(Object unit) {
                this.unit = unit;
            }
        }

        public static class TowProductTypeNameBean  implements Serializable{
            /**
             * classify_name : 分类21
             * onename : 分类二
             * id : 99
             */

            private String classify_name;
            private String onename;
            private int id;

            public String getClassify_name() {
                return classify_name;
            }

            public void setClassify_name(String classify_name) {
                this.classify_name = classify_name;
            }

            public String getOnename() {
                return onename;
            }

            public void setOnename(String onename) {
                this.onename = onename;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }

        public static class ImageJSONBean  implements Serializable{
            /**
             * address : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180316/20180316170351146162325.jpg
             * productId : 2122
             * id : 674
             * sort : 1
             */

            private String address;
            private int productId;
            private int id;
            private int sort;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }
        }

        public static class SpecificationAndValJsonBean  implements Serializable{
            /**
             * specificationValue : ["vv"]
             * specificationName : vv
             */

            private String specificationName;
            private List<String> specificationValue;

            public String getSpecificationName() {
                return specificationName;
            }

            public void setSpecificationName(String specificationName) {
                this.specificationName = specificationName;
            }

            public List<String> getSpecificationValue() {
                return specificationValue;
            }

            public void setSpecificationValue(List<String> specificationValue) {
                this.specificationValue = specificationValue;
            }
        }

        public static class DescriptionBean  implements Serializable{
            /**
             * view_img :
             * sort : 1
             * type : 0
             * content : vcv
             */

            private String view_img;
            private int sort;
            private int type;
            private String content;

            public String getView_img() {
                return view_img;
            }

            public void setView_img(String view_img) {
                this.view_img = view_img;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }

        public static class ProductSkuJSONBean  implements Serializable{
            /**
             * address :
             * salePrice : 0.0
             * price : null
             * num : 0
             * id : 1581
             * content : vv
             */

            private String address;
            private double salePrice;
            private Object price;
            private int num;
            private int id;
            private String content;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public double getSalePrice() {
                return salePrice;
            }

            public void setSalePrice(double salePrice) {
                this.salePrice = salePrice;
            }

            public Object getPrice() {
                return price;
            }

            public void setPrice(Object price) {
                this.price = price;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
