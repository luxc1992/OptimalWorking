package com.yst.onecity.bean.mine;

import java.util.List;

/**
 * 我的卡包列表实体类
 *
 * @author liumanqing
 * @version 1.0.1
 * @date 2018/5/25
 */
public class MyCardBagListBean {

    /**
     * code : 1
     * msg : 查询成功
     * content : {"trueMap":{"myCouponTrueJsonArray":[{"money":10,"name":"1","couponId":1,"isUsed":0}],"trueArray":[{"clubCardId":1,"cardName":"HAHAH","num":30,"beginTime":"2018-5-17 13:39:01","endTime":"2019-5-18 13:39:03","isUsered":0,"type":1,"serviceOrderId":17}]},"falseMap":{"myCouponFalseJsonArray":[{"money":10,"name":"1","couponId":1,"isUsed":0}],"falseArray":[{"clubCardId":1,"cardName":"HAHAH","num":30,"beginTime":"2018-5-17 13:39:01","endTime":"2019-5-18 13:39:03","isUsered":0,"type":1,"serviceOrderId":17}]}}
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
         * trueMap : {"myCouponTrueJsonArray":[{"money":100000,"name":"优","couponId":1,"isUsed":0},{"money":100000,"name":"优","couponId":1,"isUsed":0},{"money":10,"name":"1","couponId":1,"isUsed":0}],"trueArray":[{"clubCardId":1,"cardName":"HAHAH","num":30,"beginTime":"2018-5-17 13:39:01","endTime":"2019-5-18 13:39:03","isUsered":0,"type":1,"usedNum":1,"serviceOrderId":17}]}
         * falseMap : {"myCouponFalseJsonArray":[],"falseArray":[{"clubCardId":4,"cardName":"高端周期卡","num":30,"beginTime":"2018-5-21 16:43:44","endTime":"2018-5-21 16:43:47","isUsered":1,"type":0,"usedNum":0,"serviceOrderId":64}]}
         */

        private TrueMapBean trueMap;
        private FalseMapBean falseMap;

        public TrueMapBean getTrueMap() {
            return trueMap;
        }

        public void setTrueMap(TrueMapBean trueMap) {
            this.trueMap = trueMap;
        }

        public FalseMapBean getFalseMap() {
            return falseMap;
        }

        public void setFalseMap(FalseMapBean falseMap) {
            this.falseMap = falseMap;
        }

        public static class TrueMapBean {
            private List<MyCouponTrueJsonArrayBean> myCouponTrueJsonArray;
            private List<TrueArrayBean> trueArray;

            public List<MyCouponTrueJsonArrayBean> getMyCouponTrueJsonArray() {
                return myCouponTrueJsonArray;
            }

            public void setMyCouponTrueJsonArray(List<MyCouponTrueJsonArrayBean> myCouponTrueJsonArray) {
                this.myCouponTrueJsonArray = myCouponTrueJsonArray;
            }

            public List<TrueArrayBean> getTrueArray() {
                return trueArray;
            }

            public void setTrueArray(List<TrueArrayBean> trueArray) {
                this.trueArray = trueArray;
            }

            public static class MyCouponTrueJsonArrayBean {
                /**
                 * money : 10
                 * name : 1
                 * couponId : 1
                 * isUsed : 0
                 */

                private String money;
                private String name;
                private int couponId;
                private int isUsed;

                public String getMoney() {
                    return money;
                }

                public void setMoney(String money) {
                    this.money = money;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getCouponId() {
                    return couponId;
                }

                public void setCouponId(int couponId) {
                    this.couponId = couponId;
                }

                public int getIsUsed() {
                    return isUsed;
                }

                public void setIsUsed(int isUsed) {
                    this.isUsed = isUsed;
                }
            }

            public static class TrueArrayBean {
                /**
                 * clubCardId : 1
                 * cardName : HAHAH
                 * num : 30
                 * beginTime : 2018-5-17 13:39:01
                 * endTime : 2019-5-18 13:39:03
                 * isUsered : 0
                 * type : 1
                 * usedNum : 1
                 * serviceOrderId : 17
                 */

                private int clubCardId;
                private String cardName;
                private int num;
                private String beginTime;
                private String endTime;
                private int isUsered;
                private int type;
                private int serviceOrderId;
                private int usedNum;

                public int getUsedNum() {
                    return usedNum;
                }

                public void setUsedNum(int usedNum) {
                    this.usedNum = usedNum;
                }

                public int getClubCardId() {
                    return clubCardId;
                }

                public void setClubCardId(int clubCardId) {
                    this.clubCardId = clubCardId;
                }

                public String getCardName() {
                    return cardName;
                }

                public void setCardName(String cardName) {
                    this.cardName = cardName;
                }

                public int getNum() {
                    return num;
                }

                public void setNum(int num) {
                    this.num = num;
                }

                public String getBeginTime() {
                    return beginTime;
                }

                public void setBeginTime(String beginTime) {
                    this.beginTime = beginTime;
                }

                public String getEndTime() {
                    return endTime;
                }

                public void setEndTime(String endTime) {
                    this.endTime = endTime;
                }

                public int getIsUsered() {
                    return isUsered;
                }

                public void setIsUsered(int isUsered) {
                    this.isUsered = isUsered;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public int getServiceOrderId() {
                    return serviceOrderId;
                }

                public void setServiceOrderId(int serviceOrderId) {
                    this.serviceOrderId = serviceOrderId;
                }
            }
        }

        public static class FalseMapBean {
            private List<MyCouponFalseJsonArrayBean> myCouponFalseJsonArray;
            private List<FalseArrayBean> falseArray;

            public List<MyCouponFalseJsonArrayBean> getMyCouponFalseJsonArray() {
                return myCouponFalseJsonArray;
            }

            public void setMyCouponFalseJsonArray(List<MyCouponFalseJsonArrayBean> myCouponFalseJsonArray) {
                this.myCouponFalseJsonArray = myCouponFalseJsonArray;
            }

            public List<FalseArrayBean> getFalseArray() {
                return falseArray;
            }

            public void setFalseArray(List<FalseArrayBean> falseArray) {
                this.falseArray = falseArray;
            }

            public static class MyCouponFalseJsonArrayBean {
                /**
                 * money : 10
                 * name : 1
                 * couponId : 1
                 * isUsed : 0
                 */

                private String money;
                private String name;
                private int couponId;
                private int isUsed;

                public String getMoney() {
                    return money;
                }

                public void setMoney(String money) {
                    this.money = money;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getCouponId() {
                    return couponId;
                }

                public void setCouponId(int couponId) {
                    this.couponId = couponId;
                }

                public int getIsUsed() {
                    return isUsed;
                }

                public void setIsUsed(int isUsed) {
                    this.isUsed = isUsed;
                }
            }

            public static class FalseArrayBean {
                /**
                 * clubCardId : 1
                 * cardName : HAHAH
                 * num : 30
                 * beginTime : 2018-5-17 13:39:01
                 * endTime : 2019-5-18 13:39:03
                 * isUsered : 0
                 * type : 1
                 * serviceOrderId : 17
                 */

                private int clubCardId;
                private String cardName;
                private int num;
                private String beginTime;
                private String endTime;
                private int isUsered;
                private int type;
                private int usedNum;
                private int serviceOrderId;

                public int getClubCardId() {
                    return clubCardId;
                }

                public void setClubCardId(int clubCardId) {
                    this.clubCardId = clubCardId;
                }

                public String getCardName() {
                    return cardName;
                }

                public void setCardName(String cardName) {
                    this.cardName = cardName;
                }

                public int getNum() {
                    return num;
                }

                public void setNum(int num) {
                    this.num = num;
                }

                public String getBeginTime() {
                    return beginTime;
                }

                public void setBeginTime(String beginTime) {
                    this.beginTime = beginTime;
                }

                public String getEndTime() {
                    return endTime;
                }

                public void setEndTime(String endTime) {
                    this.endTime = endTime;
                }

                public int getIsUsered() {
                    return isUsered;
                }

                public void setIsUsered(int isUsered) {
                    this.isUsered = isUsered;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public int getUsedNum() {
                    return usedNum;
                }

                public void setUsedNum(int usedNum) {
                    this.usedNum = usedNum;
                }

                public int getServiceOrderId() {
                    return serviceOrderId;
                }

                public void setServiceOrderId(int serviceOrderId) {
                    this.serviceOrderId = serviceOrderId;
                }
            }
        }
    }
}
