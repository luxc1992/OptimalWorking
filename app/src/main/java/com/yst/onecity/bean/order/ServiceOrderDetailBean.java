package com.yst.onecity.bean.order;

import java.util.List;

/**
 * 服务订单详情
 *
 * @author songbinbin
 * @version 1.1.0
 * @date 2018/5/24
 */
public class ServiceOrderDetailBean {

    /**
     * code : 1
     * msg : 查询订单成功
     * content : [{"serviceType":0,"code":"0873679906","advisorId":21,"distance":"12242.26/km","payTime":null,"projectImage":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180602/20180602140741850451848.jpg","num":1,"title":"服务2","type":0,"content":"六一快了～哈哈哈","cityName":"市辖区","price":"200.00","payPrice":"200.00","createdTime":1527933282,"id":393,"countyName":"朝阳区","orderNo":"PJFW20180602175735098203487","address":"嘉里大通物流(霄云路)","serviceTime":"2018-06-01 00:00:00","linkphone":"15701574039","imgUrl":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180601/20180601102539721521007.jpg","phone":"13126519519","serviceProjectId":101,"provinceName":"北京市","projectName":"六一儿童团","status":7}]
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
         * serviceType : 0
         * code : 0873679906
         * advisorId : 21
         * distance : 12242.26/km
         * payTime : null
         * projectImage : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180602/20180602140741850451848.jpg
         * num : 1
         * title : 服务2
         * type : 0
         * content : 六一快了～哈哈哈
         * cityName : 市辖区
         * price : 200.00
         * payPrice : 200.00
         * createdTime : 1527933282
         * id : 393
         * countyName : 朝阳区
         * orderNo : PJFW20180602175735098203487
         * address : 嘉里大通物流(霄云路)
         * serviceTime : 2018-06-01 00:00:00
         * memberPhone : 15701574039
         * imgUrl : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180601/20180601102539721521007.jpg
         * phone : 13126519519
         * serviceProjectId : 101
         * provinceName : 北京市
         * projectName : 六一儿童团
         * status : 7
         * serviceAdd:请求卖家详情时返回与买家时不一致 地址改用此字段
         * detailAdd:地址详细信息
         */

        private int serviceType;
        private String code;
        private long advisorId;
        private String distance;
        private String payTime;
        private String projectImage;
        private int num;
        private String title;
        private int type;
        private String content;
        private String cityName;
        private String price;
        private String payPrice;
        private long createdTime;
        private int id;
        private String countyName;
        private String orderNo;
        private String address;
        private String serviceTime;
        private String linkphone;
        private String imgUrl;
        private String phone;
        private long serviceProjectId;
        private String provinceName;
        private String projectName;
        private int status;
        private String serviceAdd;
        private String detailAdd;
        private String linkman;

        public void setLinkman(String linkman) {
            this.linkman = linkman;
        }

        public String getLinkman() {

            return linkman;
        }

        public void setDetailAdd(String detailAdd) {
            this.detailAdd = detailAdd;
        }

        public String getDetailAdd() {

            return detailAdd;
        }

        public void setServiceAdd(String serviceAdd) {
            this.serviceAdd = serviceAdd;
        }

        public String getServiceAdd() {

            return serviceAdd;
        }

        public int getServiceType() {
            return serviceType;
        }

        public void setServiceType(int serviceType) {
            this.serviceType = serviceType;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public long getAdvisorId() {
            return advisorId;
        }

        public void setAdvisorId(long advisorId) {
            this.advisorId = advisorId;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getPayTime() {
            return payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }

        public String getProjectImage() {
            return projectImage;
        }

        public void setProjectImage(String projectImage) {
            this.projectImage = projectImage;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPayPrice() {
            return payPrice;
        }

        public void setPayPrice(String payPrice) {
            this.payPrice = payPrice;
        }

        public long getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(long createdTime) {
            this.createdTime = createdTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCountyName() {
            return countyName;
        }

        public void setCountyName(String countyName) {
            this.countyName = countyName;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getServiceTime() {
            return serviceTime;
        }

        public void setServiceTime(String serviceTime) {
            this.serviceTime = serviceTime;
        }

        public String getLinkphone() {
            return linkphone;
        }

        public void setLinkphone(String linkphone) {
            this.linkphone = linkphone;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public long getServiceProjectId() {
            return serviceProjectId;
        }

        public void setServiceProjectId(long serviceProjectId) {
            this.serviceProjectId = serviceProjectId;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
