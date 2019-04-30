package com.yst.onecity.bean.issue;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhaiyanwu
 * @version v3.0.1
 * @date 2018/3/5.
 */
public class FreightRuleContentBean {

    /**
     * code : 1
     * msg : 获取成功
     * content : {"ferighList":[{"id":1,"isFreeShipping":0,"costType":0,"sendType":0,"firstPrice":10,"increaseFreight":5,"firstProduct":1,"nextProduct":1,"firstWeight":null,"nextWeight":null,"firstVolume":null,"nextVolume":null,"isDeleted":null,"mainId":null,"totalPrice":null},{"id":2,"isFreeShipping":0,"costType":0,"sendType":1,"firstPrice":8,"increaseFreight":7,"firstProduct":1,"nextProduct":1,"firstWeight":null,"nextWeight":null,"firstVolume":null,"nextVolume":null,"isDeleted":null,"mainId":null,"totalPrice":null},{"id":3,"isFreeShipping":0,"costType":0,"sendType":2,"firstPrice":5,"increaseFreight":2,"firstProduct":1,"nextProduct":1,"firstWeight":null,"nextWeight":null,"firstVolume":null,"nextVolume":null,"isDeleted":null,"mainId":null,"totalPrice":null}],"costType":0,"name":"普通通用模板"}
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
         * ferighList : [{"id":1,"isFreeShipping":0,"costType":0,"sendType":0,"firstPrice":10,"increaseFreight":5,"firstProduct":1,"nextProduct":1,"firstWeight":null,"nextWeight":null,"firstVolume":null,"nextVolume":null,"isDeleted":null,"mainId":null,"totalPrice":null},{"id":2,"isFreeShipping":0,"costType":0,"sendType":1,"firstPrice":8,"increaseFreight":7,"firstProduct":1,"nextProduct":1,"firstWeight":null,"nextWeight":null,"firstVolume":null,"nextVolume":null,"isDeleted":null,"mainId":null,"totalPrice":null},{"id":3,"isFreeShipping":0,"costType":0,"sendType":2,"firstPrice":5,"increaseFreight":2,"firstProduct":1,"nextProduct":1,"firstWeight":null,"nextWeight":null,"firstVolume":null,"nextVolume":null,"isDeleted":null,"mainId":null,"totalPrice":null}]
         * costType : 0
         * name : 普通通用模板
         */

        private int costType;
        private String name;
        private List<FerighListBean> ferighList;

        public int getCostType() {
            return costType;
        }

        public void setCostType(int costType) {
            this.costType = costType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<FerighListBean> getFerighList() {
            return ferighList;
        }

        public void setFerighList(List<FerighListBean> ferighList) {
            this.ferighList = ferighList;
        }

        public static class FerighListBean implements Serializable{
            @Override
            public String toString() {
                return "FerighListBean{" +
                        "id=" + id +
                        ", isFreeShipping=" + isFreeShipping +
                        ", costType=" + costType +
                        ", sendType=" + sendType +
                        ", firstPrice=" + firstPrice +
                        ", increaseFreight=" + increaseFreight +
                        ", firstProduct=" + firstProduct +
                        ", nextProduct=" + nextProduct +
                        ", firstWeight=" + firstWeight +
                        ", nextWeight=" + nextWeight +
                        ", firstVolume=" + firstVolume +
                        ", nextVolume=" + nextVolume +
                        ", isDeleted=" + isDeleted +
                        ", mainId=" + mainId +
                        ", totalPrice=" + totalPrice +
                        '}';
            }

            /**
             * id : 1
             * isFreeShipping : 0
             * costType : 0
             * sendType : 0
             * firstPrice : 10
             * increaseFreight : 5
             * firstProduct : 1
             * nextProduct : 1
             * firstWeight : null
             * nextWeight : null
             * firstVolume : null
             * nextVolume : null
             * isDeleted : null
             * mainId : null
             * totalPrice : null
             */

            private int id;
            private int isFreeShipping;
            private int costType;
            private int sendType;
            private int firstPrice;
            private int increaseFreight;
            private int firstProduct;
            private int nextProduct;
            private Object firstWeight;
            private Object nextWeight;
            private Object firstVolume;
            private Object nextVolume;
            private Object isDeleted;
            private Object mainId;
            private Object totalPrice;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getIsFreeShipping() {
                return isFreeShipping;
            }

            public void setIsFreeShipping(int isFreeShipping) {
                this.isFreeShipping = isFreeShipping;
            }

            public int getCostType() {
                return costType;
            }

            public void setCostType(int costType) {
                this.costType = costType;
            }

            public int getSendType() {
                return sendType;
            }

            public void setSendType(int sendType) {
                this.sendType = sendType;
            }

            public int getFirstPrice() {
                return firstPrice;
            }

            public void setFirstPrice(int firstPrice) {
                this.firstPrice = firstPrice;
            }

            public int getIncreaseFreight() {
                return increaseFreight;
            }

            public void setIncreaseFreight(int increaseFreight) {
                this.increaseFreight = increaseFreight;
            }

            public int getFirstProduct() {
                return firstProduct;
            }

            public void setFirstProduct(int firstProduct) {
                this.firstProduct = firstProduct;
            }

            public int getNextProduct() {
                return nextProduct;
            }

            public void setNextProduct(int nextProduct) {
                this.nextProduct = nextProduct;
            }

            public Object getFirstWeight() {
                return firstWeight;
            }

            public void setFirstWeight(Object firstWeight) {
                this.firstWeight = firstWeight;
            }

            public Object getNextWeight() {
                return nextWeight;
            }

            public void setNextWeight(Object nextWeight) {
                this.nextWeight = nextWeight;
            }

            public Object getFirstVolume() {
                return firstVolume;
            }

            public void setFirstVolume(Object firstVolume) {
                this.firstVolume = firstVolume;
            }

            public Object getNextVolume() {
                return nextVolume;
            }

            public void setNextVolume(Object nextVolume) {
                this.nextVolume = nextVolume;
            }

            public Object getIsDeleted() {
                return isDeleted;
            }

            public void setIsDeleted(Object isDeleted) {
                this.isDeleted = isDeleted;
            }

            public Object getMainId() {
                return mainId;
            }

            public void setMainId(Object mainId) {
                this.mainId = mainId;
            }

            public Object getTotalPrice() {
                return totalPrice;
            }

            public void setTotalPrice(Object totalPrice) {
                this.totalPrice = totalPrice;
            }
        }
    }
}
