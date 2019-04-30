package com.yst.onecity.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 描述
 *
 * @author luxuchang
 * @version 1.1.0
 * @date 2018/5/24
 */
public class CardUnionOrderBean {

    /**
     * code : 1
     * msg : 查询成功
     * content : {"cardOrderVOList":[{"serviceOrderId":134,"clubCardId":4,"orderNo":"PJGK20180524164516118145837","type":0,"num":30,"status":7,"price":"20.00","memberId":null,"nickName":null,"logoAttachmentAddress":null,"phone":null,"detailAdd":null,"usedNum":null,"nikeName":null,"nikeAddress":null,"createdTime":null},{"serviceOrderId":48,"clubCardId":5,"orderNo":"PJGK20180523111556725651545","type":2,"num":7,"status":7,"price":"10.00","memberId":null,"nickName":null,"logoAttachmentAddress":null,"phone":null,"detailAdd":null,"usedNum":null,"nikeName":null,"nikeAddress":null,"createdTime":null},{"serviceOrderId":35,"clubCardId":4,"orderNo":"PJGK20180522204637406161033","type":0,"num":30,"status":7,"price":"20.00","memberId":null,"nickName":null,"logoAttachmentAddress":null,"phone":null,"detailAdd":null,"usedNum":null,"nikeName":null,"nikeAddress":null,"createdTime":null},{"serviceOrderId":28,"clubCardId":4,"orderNo":"PJGK20180522165514217395596","type":0,"num":30,"status":7,"price":"20.00","memberId":null,"nickName":null,"logoAttachmentAddress":null,"phone":null,"detailAdd":null,"usedNum":null,"nikeName":null,"nikeAddress":null,"createdTime":null},{"serviceOrderId":27,"clubCardId":4,"orderNo":"PJGK20180522165504743931016","type":0,"num":30,"status":7,"price":"20.00","memberId":null,"nickName":null,"logoAttachmentAddress":null,"phone":null,"detailAdd":null,"usedNum":null,"nikeName":null,"nikeAddress":null,"createdTime":null},{"serviceOrderId":21,"clubCardId":4,"orderNo":"20180521172230785507934","type":0,"num":30,"status":7,"price":"20.00","memberId":null,"nickName":null,"logoAttachmentAddress":null,"phone":null,"detailAdd":null,"usedNum":null,"nikeName":null,"nikeAddress":null,"createdTime":null},{"serviceOrderId":13,"clubCardId":1,"orderNo":"20180519140659635896540","type":1,"num":30,"status":3,"price":"1.00","memberId":null,"nickName":null,"logoAttachmentAddress":null,"phone":null,"detailAdd":null,"usedNum":null,"nikeName":null,"nikeAddress":null,"createdTime":null}]}
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
        private List<CardOrderVOListBean> cardOrderVOList;

        public List<CardOrderVOListBean> getCardOrderVOList() {
            return cardOrderVOList;
        }

        public void setCardOrderVOList(List<CardOrderVOListBean> cardOrderVOList) {
            this.cardOrderVOList = cardOrderVOList;
        }

        public static class CardOrderVOListBean implements Serializable{

            /**
             * clubCardId : 4
             * orderNo : PJGK20180530154134400992723
             * cardName : 高端周期卡
             * price : 30
             * num : 30
             * imageAddress : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417113805125039274.jpg
             * type : 0
             * serviceOrderId : 193
             * status : 7
             */

            private String clubCardId;
            private String orderNo;
            private String cardName;
            private String price;
            private String payPrice;
            private int num;
            private String imageAddress;
            private int type;
            private String serviceOrderId;
            private String createdTime;
            private int status;

            public String getPayPrice() {
                return payPrice;
            }

            public void setPayPrice(String payPrice) {
                this.payPrice = payPrice;
            }

            public String getCreatedTime() {
                return createdTime;
            }

            public void setCreatedTime(String createdTime) {
                this.createdTime = createdTime;
            }

            public String getClubCardId() {
                return clubCardId;
            }

            public void setClubCardId(String clubCardId) {
                this.clubCardId = clubCardId;
            }

            public String getOrderNo() {
                return orderNo;
            }

            public void setOrderNo(String orderNo) {
                this.orderNo = orderNo;
            }

            public String getCardName() {
                return cardName;
            }

            public void setCardName(String cardName) {
                this.cardName = cardName;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public String getImageAddress() {
                return imageAddress;
            }

            public void setImageAddress(String imageAddress) {
                this.imageAddress = imageAddress;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getServiceOrderId() {
                return serviceOrderId;
            }

            public void setServiceOrderId(String serviceOrderId) {
                this.serviceOrderId = serviceOrderId;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}
