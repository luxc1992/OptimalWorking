package com.yst.onecity.bean;

/**
 * 团队信息的bean
 *
 * @author wuxiaofang
 * @version 1.1.0
 * @date 2018/05/27
 */

public class TeamInfoBean {

    /**
     * code : 1
     * msg : 服务团队信息回显成功
     * content : {"address":"大苏打","cityId":110100000000,"categoryName":"职业","provinceId":110,"content":"我就来测试的","imgUrl":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417113827151387410.jpg","cityName":"市辖区","phone":"18510617776","countyId":110101000000,"nikeName":"寒风呼呼","provinceName":"北京市","countyName":"东城区","status":0}
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

    public static class ContentBean {
        /**
         * address : 大苏打
         * cityId : 110100000000
         * categoryName : 职业
         * provinceId : 110
         * content : 我就来测试的
         * imgUrl : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417113827151387410.jpg
         * cityName : 市辖区
         * phone : 18510617776
         * countyId : 110101000000
         * nikeName : 寒风呼呼
         * provinceName : 北京市
         * countyName : 东城区
         * status : 0
         */

        private String address;
        private long cityId;
        private String categoryName;
        private int provinceId;
        private String content;
        private String imgUrl;
        private String cityName;
        private String telPhone;
        private long countyId;
        private String nikeName;
        private String provinceName;
        private String countyName;
        private int status;
        private String longitude;
        private String latitude;

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public long getCityId() {
            return cityId;
        }

        public void setCityId(long cityId) {
            this.cityId = cityId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public int getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(int provinceId) {
            this.provinceId = provinceId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getTelPhone() {
            return telPhone;
        }

        public void setTelPhone(String telPhone) {
            this.telPhone = telPhone;
        }

        public long getCountyId() {
            return countyId;
        }

        public void setCountyId(long countyId) {
            this.countyId = countyId;
        }

        public String getNikeName() {
            return nikeName;
        }

        public void setNikeName(String nikeName) {
            this.nikeName = nikeName;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public String getCountyName() {
            return countyName;
        }

        public void setCountyName(String countyName) {
            this.countyName = countyName;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
