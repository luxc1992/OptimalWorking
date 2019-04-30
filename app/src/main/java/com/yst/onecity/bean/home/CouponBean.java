package com.yst.onecity.bean.home;

/**
 * 红包优惠券
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/5/25.
 */

public class CouponBean {


    /**
     * code : 1
     * msg : 优惠券查询成功
     * content : {"canReceive":1,"usedDateNum":50,"noUsed":0,"usedDateEnd":null,"usedDateBegin":null,"usedCondition":0,"havaReceive":0,"type":0,"usedProduct":0,"platform":null,"isUsed":0,"usedDateType":1,"money":50,"totalNum":0,"isNotUsed":0,"name":"平台赠券","createdTime":1527818932000,"id":1}
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
         * canReceive : 1
         * usedDateNum : 50
         * noUsed : 0
         * usedDateEnd : null
         * usedDateBegin : null
         * usedCondition : 0
         * havaReceive : 0
         * type : 0
         * usedProduct : 0
         * platform : null
         * isUsed : 0
         * usedDateType : 1
         * money : 50.0
         * totalNum : 0
         * isNotUsed : 0
         * name : 平台赠券
         * createdTime : 1527818932000
         * id : 1
         */

        private int canReceive;
        private int usedDateNum;
        private int noUsed;
        private String usedDateEnd;
        private String usedDateBegin;
        private int usedCondition;
        private int havaReceive;
        private int type;
        private int usedProduct;
        private String platform;
        private int isUsed;
        private int usedDateType;
        private double money;
        private int totalNum;
        private int isNotUsed;
        private String name;
        private long createdTime;
        private int id;

        public int getCanReceive() {
            return canReceive;
        }

        public void setCanReceive(int canReceive) {
            this.canReceive = canReceive;
        }

        public int getUsedDateNum() {
            return usedDateNum;
        }

        public void setUsedDateNum(int usedDateNum) {
            this.usedDateNum = usedDateNum;
        }

        public int getNoUsed() {
            return noUsed;
        }

        public void setNoUsed(int noUsed) {
            this.noUsed = noUsed;
        }

        public String getUsedDateEnd() {
            return usedDateEnd;
        }

        public void setUsedDateEnd(String usedDateEnd) {
            this.usedDateEnd = usedDateEnd;
        }

        public String getUsedDateBegin() {
            return usedDateBegin;
        }

        public void setUsedDateBegin(String usedDateBegin) {
            this.usedDateBegin = usedDateBegin;
        }

        public int getUsedCondition() {
            return usedCondition;
        }

        public void setUsedCondition(int usedCondition) {
            this.usedCondition = usedCondition;
        }

        public int getHavaReceive() {
            return havaReceive;
        }

        public void setHavaReceive(int havaReceive) {
            this.havaReceive = havaReceive;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUsedProduct() {
            return usedProduct;
        }

        public void setUsedProduct(int usedProduct) {
            this.usedProduct = usedProduct;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public int getIsUsed() {
            return isUsed;
        }

        public void setIsUsed(int isUsed) {
            this.isUsed = isUsed;
        }

        public int getUsedDateType() {
            return usedDateType;
        }

        public void setUsedDateType(int usedDateType) {
            this.usedDateType = usedDateType;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public int getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
        }

        public int getIsNotUsed() {
            return isNotUsed;
        }

        public void setIsNotUsed(int isNotUsed) {
            this.isNotUsed = isNotUsed;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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
    }
}
