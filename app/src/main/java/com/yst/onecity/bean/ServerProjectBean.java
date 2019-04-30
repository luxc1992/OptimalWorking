package com.yst.onecity.bean;

import java.util.List;

/**
 * 描述
 *
 * @author luxuchang
 * @version 1.1.0
 * @date 2018/5/24
 */
public class ServerProjectBean {


    /**
     * code : 1
     * msg : 根据条件查询服务项目列表
     * content : [{"advisor":{"nickName":null,"idCardImg1":null,"idCardImg2":null,"teamImg":null,"licenseImgId":null,"detailedAddress":null,"id":5,"imgUrl":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180511/20180511153452640140404.jpg","categoryName":"装修","provinceName":"河北省","cityName":"石家庄市","countyName":"鹿泉市","memberId":492,"phone":"13712341234","nikeName":"博洛尼装修2","content":"1222272455","status":0,"cityId":130100000000,"provinceId":130,"countyId":130185000000,"longitude":null,"latitude":null,"address":"三元桥","starNum":null,"examineStatus":1,"isFollow":0},"merchantId":492,"price":"0.10","introduce":"田园风光","imageAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417102544771770878.jpg","id":2,"title":"我呢"},{"advisor":{"nickName":null,"idCardImg1":null,"idCardImg2":null,"teamImg":null,"licenseImgId":null,"detailedAddress":null,"id":8,"imgUrl":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417113805125039274.jpg","categoryName":"装修","provinceName":null,"cityName":null,"countyName":null,"memberId":508,"phone":"15501193166","nikeName":"rng牛逼啊","content":"吊打韩国队","status":null,"cityId":null,"provinceId":null,"countyId":null,"longitude":null,"latitude":null,"address":"沙谷银滩进行漂流活动","starNum":null,"examineStatus":0,"isFollow":0},"merchantId":508,"price":"500.00","introduce":"田园风光","imageAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180425/20180425154744299457863.png","id":4,"title":"我呢"}]
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
         * advisor : {"nickName":null,"idCardImg1":null,"idCardImg2":null,"teamImg":null,"licenseImgId":null,"detailedAddress":null,"id":5,"imgUrl":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180511/20180511153452640140404.jpg","categoryName":"装修","provinceName":"河北省","cityName":"石家庄市","countyName":"鹿泉市","memberId":492,"phone":"13712341234","nikeName":"博洛尼装修2","content":"1222272455","status":0,"cityId":130100000000,"provinceId":130,"countyId":130185000000,"longitude":null,"latitude":null,"address":"三元桥","starNum":null,"examineStatus":1,"isFollow":0}
         * merchantId : 492
         * price : 0.10
         * introduce : 田园风光
         * imageAddress : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417102544771770878.jpg
         * id : 2
         * title : 我呢
         */

        private AdvisorBean advisor;
        private int merchantId;
        private String price;
        private String introduce;
        private String imageAddress;
        private int id;
        private String title;

        public AdvisorBean getAdvisor() {
            return advisor;
        }

        public void setAdvisor(AdvisorBean advisor) {
            this.advisor = advisor;
        }

        public int getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(int merchantId) {
            this.merchantId = merchantId;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getImageAddress() {
            return imageAddress;
        }

        public void setImageAddress(String imageAddress) {
            this.imageAddress = imageAddress;
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

        public static class AdvisorBean {
            /**
             * nickName : null
             * idCardImg1 : null
             * idCardImg2 : null
             * teamImg : null
             * licenseImgId : null
             * detailedAddress : null
             * id : 5
             * imgUrl : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180511/20180511153452640140404.jpg
             * categoryName : 装修
             * provinceName : 河北省
             * cityName : 石家庄市
             * countyName : 鹿泉市
             * memberId : 492
             * phone : 13712341234
             * nikeName : 博洛尼装修2
             * content : 1222272455
             * status : 0
             * cityId : 130100000000
             * provinceId : 130
             * countyId : 130185000000
             * longitude : null
             * latitude : null
             * address : 三元桥
             * starNum : null
             * examineStatus : 1
             * isFollow : 0
             */

            private String nickName;
            private String idCardImg1;
            private String idCardImg2;
            private String teamImg;
            private String licenseImgId;
            private String detailedAddress;
            private int id;
            private String imgUrl;
            private String categoryName;
            private String provinceName;
            private String cityName;
            private String countyName;
            private int memberId;
            private String phone;
            private String nikeName;
            private String content;
            private int status;
            private long cityId;
            private int provinceId;
            private long countyId;
            private String longitude;
            private String latitude;
            private String address;
            private String starNum;
            private int examineStatus;
            private int isFollow;

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getIdCardImg1() {
                return idCardImg1;
            }

            public void setIdCardImg1(String idCardImg1) {
                this.idCardImg1 = idCardImg1;
            }

            public String getIdCardImg2() {
                return idCardImg2;
            }

            public void setIdCardImg2(String idCardImg2) {
                this.idCardImg2 = idCardImg2;
            }

            public String getTeamImg() {
                return teamImg;
            }

            public void setTeamImg(String teamImg) {
                this.teamImg = teamImg;
            }

            public String getLicenseImgId() {
                return licenseImgId;
            }

            public void setLicenseImgId(String licenseImgId) {
                this.licenseImgId = licenseImgId;
            }

            public String getDetailedAddress() {
                return detailedAddress;
            }

            public void setDetailedAddress(String detailedAddress) {
                this.detailedAddress = detailedAddress;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getCategoryName() {
                return categoryName;
            }

            public void setCategoryName(String categoryName) {
                this.categoryName = categoryName;
            }

            public String getProvinceName() {
                return provinceName;
            }

            public void setProvinceName(String provinceName) {
                this.provinceName = provinceName;
            }

            public String getCityName() {
                return cityName;
            }

            public void setCityName(String cityName) {
                this.cityName = cityName;
            }

            public String getCountyName() {
                return countyName;
            }

            public void setCountyName(String countyName) {
                this.countyName = countyName;
            }

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getNikeName() {
                return nikeName;
            }

            public void setNikeName(String nikeName) {
                this.nikeName = nikeName;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public long getCityId() {
                return cityId;
            }

            public void setCityId(long cityId) {
                this.cityId = cityId;
            }

            public int getProvinceId() {
                return provinceId;
            }

            public void setProvinceId(int provinceId) {
                this.provinceId = provinceId;
            }

            public long getCountyId() {
                return countyId;
            }

            public void setCountyId(long countyId) {
                this.countyId = countyId;
            }

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

            public String getStarNum() {
                return starNum;
            }

            public void setStarNum(String starNum) {
                this.starNum = starNum;
            }

            public int getExamineStatus() {
                return examineStatus;
            }

            public void setExamineStatus(int examineStatus) {
                this.examineStatus = examineStatus;
            }

            public int getIsFollow() {
                return isFollow;
            }

            public void setIsFollow(int isFollow) {
                this.isFollow = isFollow;
            }
        }
    }
}
