package com.yst.onecity.bean;

import java.util.List;

/**
 * 服务实体类
 *
 * @author chejianqi
 * @version 1.0.1
 * @date 2018/5/25
 */

public class ServiceDataBean {


    /**
     * code : 1
     * msg : 查询成功
     * merchantId : 123
     * log : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180525/20180525112143859010169.jpg
     * price : 48.00
     * title : 哈哈哈哈
     * examineStatus : null
     * serviceId : 222
     * status : 0
     * serviceType : 0
     * serviceTypeId : 8
     * startTime : 2018-5-25 12:00:00
     * endTime : 2018-5-25 12:00:00
     * serviceImages : [{"label":"[{\"productTagR\":716,\"productTagB\":423,\"productY\":\"32.86\",\"productX\":\"37.04\",\"productId\":\"123\",\"productTagL\":400,\"productImageY\":336,\"productName\":\"床单\",\"productTagT\":346}]","image_address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180525/20180525112124547779074.jpg"},{"label":"[{\"productTagR\":316,\"productTagB\":77,\"productY\":\"0.00\",\"productX\":\"0.00\",\"productId\":\"125\",\"productTagL\":0,\"productImageY\":458,\"productName\":\"台灯\",\"productTagT\":0},{\"productTagR\":448,\"productTagB\":504,\"productY\":\"52.72\",\"productX\":\"12.22\",\"productId\":\"126\",\"productTagL\":132,\"productImageY\":458,\"productName\":\"枕头\",\"productTagT\":427},{\"productTagR\":693,\"productTagB\":310,\"productY\":\"28.77\",\"productX\":\"34.91\",\"productId\":\"124\",\"productTagL\":377,\"productImageY\":458,\"productName\":\"被罩\",\"productTagT\":233}]","image_address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180525/20180525112152116281988.jpg"}]
     * descriptionList : [{"detail_content":"14444","sort":0,"image_address":null,"type":0},{"detail_content":null,"sort":1,"image_address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180525/20180525112224966842777.jpg?width=1152&height=864","type":1}]
     * productIds : null
     * id : null
     * servicePrice : null
     * serviceStartTime : null
     * serviceEndTime : null
     * serviceTeamId : null
     */

    private int code;
    private String msg;
    private int merchantId;
    private String log;
    private String price;
    private String title;
    private int serviceId;
    private int status;
    private int serviceType;
    private int serviceTypeId;
    private String startTime;
    private String endTime;
    private List<ServiceImagesBean> serviceImages;
    private List<DescriptionListBean> descriptionList;

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

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getServiceType() {
        return serviceType;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }

    public int getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(int serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<ServiceImagesBean> getServiceImages() {
        return serviceImages;
    }

    public void setServiceImages(List<ServiceImagesBean> serviceImages) {
        this.serviceImages = serviceImages;
    }

    public List<DescriptionListBean> getDescriptionList() {
        return descriptionList;
    }

    public void setDescriptionList(List<DescriptionListBean> descriptionList) {
        this.descriptionList = descriptionList;
    }

    public static class ServiceImagesBean {
        /**
         * label : [{"productTagR":716,"productTagB":423,"productY":"32.86","productX":"37.04","productId":"123","productTagL":400,"productImageY":336,"productName":"床单","productTagT":346}]
         * image_address : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180525/20180525112124547779074.jpg
         */

        private String label;
        private String image_address;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getImage_address() {
            return image_address;
        }

        public void setImage_address(String image_address) {
            this.image_address = image_address;
        }
    }

    public static class DescriptionListBean {
        /**
         * detail_content : 14444
         * sort : 0
         * image_address : null
         * type : 0
         */

        private String detail;
        private int sort;
        private String image_address;
        private int type;

        public String getDetail_content() {
            return detail;
        }

        public void setDetail_content(String detail_content) {
            this.detail = detail_content;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getImage_address() {
            return image_address;
        }

        public void setImage_address(String image_address) {
            this.image_address = image_address;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
