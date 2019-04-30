package com.yst.onecity.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 描述
 *
 * @author luxuchang
 * @version 1.1.0
 * @date 2018/5/28
 */
public class CardUnionUsedBean {


    /**
     * code : 1
     * msg : 查询成功
     * content : [{"createTime":"2018-05-25","serviceOrderVOList":[{"serviceOrderId":152,"clubCardId":null,"orderNo":null,"type":4,"num":1,"status":null,"price":null,"memberId":498,"nickName":"0867","logoAttachmentAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180423/20180423171301419578383.png","phone":"18518350867","detailAdd":"河北省石家庄市鹿泉市嘉里大通","usedNum":null,"nikeName":null,"nikeAddress":null,"detailTime":"2018-05-25 12:00:01","advisorName":"博洛尼装修2","provinceName":"河北省","cityName":"石家庄市","countyName":"鹿泉市","createdTime":"2018-05-25"}]}]
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
         * createTime : 2018-05-25
         * serviceOrderVOList : [{"serviceOrderId":152,"clubCardId":null,"orderNo":null,"type":4,"num":1,"status":null,"price":null,"memberId":498,"nickName":"0867","logoAttachmentAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180423/20180423171301419578383.png","phone":"18518350867","detailAdd":"河北省石家庄市鹿泉市嘉里大通","usedNum":null,"nikeName":null,"nikeAddress":null,"detailTime":"2018-05-25 12:00:01","advisorName":"博洛尼装修2","provinceName":"河北省","cityName":"石家庄市","countyName":"鹿泉市","createdTime":"2018-05-25"}]
         */

        private String createTime;
        private List<ServiceOrderVOListBean> serviceOrderVOList;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public List<ServiceOrderVOListBean> getServiceOrderVOList() {
            return serviceOrderVOList;
        }

        public void setServiceOrderVOList(List<ServiceOrderVOListBean> serviceOrderVOList) {
            this.serviceOrderVOList = serviceOrderVOList;
        }

        public static class ServiceOrderVOListBean implements Serializable{
            /**
             * serviceOrderId : 152
             * clubCardId : null
             * orderNo : null
             * type : 4
             * num : 1
             * status : null
             * price : null
             * memberId : 498
             * nickName : 0867
             * logoAttachmentAddress : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180423/20180423171301419578383.png
             * phone : 18518350867
             * detailAdd : 河北省石家庄市鹿泉市嘉里大通
             * usedNum : null
             * nikeName : null
             * nikeAddress : null
             * detailTime : 2018-05-25 12:00:01
             * advisorName : 博洛尼装修2
             * provinceName : 河北省
             * cityName : 石家庄市
             * countyName : 鹿泉市
             * createdTime : 2018-05-25
             */

            private int serviceOrderId;
            private Object clubCardId;
            private Object orderNo;
            private int type;
            private int num;
            private Object status;
            private Object price;
            private int memberId;
            private String nickName;
            private String logoAttachmentAddress;
            private String phone;
            private String detailAdd;
            private int usedNum;
            private Object nikeName;
            private Object nikeAddress;
            private String detailTime;
            private String advisorName;
            private String provinceName;
            private String cityName;
            private String countyName;
            private String createdTime;

            public int getServiceOrderId() {
                return serviceOrderId;
            }

            public void setServiceOrderId(int serviceOrderId) {
                this.serviceOrderId = serviceOrderId;
            }

            public Object getClubCardId() {
                return clubCardId;
            }

            public void setClubCardId(Object clubCardId) {
                this.clubCardId = clubCardId;
            }

            public Object getOrderNo() {
                return orderNo;
            }

            public void setOrderNo(Object orderNo) {
                this.orderNo = orderNo;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public Object getStatus() {
                return status;
            }

            public void setStatus(Object status) {
                this.status = status;
            }

            public Object getPrice() {
                return price;
            }

            public void setPrice(Object price) {
                this.price = price;
            }

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getLogoAttachmentAddress() {
                return logoAttachmentAddress;
            }

            public void setLogoAttachmentAddress(String logoAttachmentAddress) {
                this.logoAttachmentAddress = logoAttachmentAddress;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getDetailAdd() {
                return detailAdd;
            }

            public void setDetailAdd(String detailAdd) {
                this.detailAdd = detailAdd;
            }

            public int getUsedNum() {
                return usedNum;
            }

            public void setUsedNum(int usedNum) {
                this.usedNum = usedNum;
            }

            public Object getNikeName() {
                return nikeName;
            }

            public void setNikeName(Object nikeName) {
                this.nikeName = nikeName;
            }

            public Object getNikeAddress() {
                return nikeAddress;
            }

            public void setNikeAddress(Object nikeAddress) {
                this.nikeAddress = nikeAddress;
            }

            public String getDetailTime() {
                return detailTime;
            }

            public void setDetailTime(String detailTime) {
                this.detailTime = detailTime;
            }

            public String getAdvisorName() {
                return advisorName;
            }

            public void setAdvisorName(String advisorName) {
                this.advisorName = advisorName;
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

            public String getCreatedTime() {
                return createdTime;
            }

            public void setCreatedTime(String createdTime) {
                this.createdTime = createdTime;
            }
        }
    }
}
