package com.yst.onecity.bean;

/**
 * 提现详情实体类
 *
 * @author chejianqi
 * @version 1.0.1
 * @date 2018/3/12
 */

public class CashDetailsBean {

    /**
     * code : 1
     * msg : 成功
     * content : {"bank":"兴业银行","money":100,"bank_num":"66666666666","fee":5,"createdTime":1520931843000,"id":44,"status":1}
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
         * bank : 兴业银行
         * money : 100
         * bank_num : 66666666666
         * fee : 5
         * createdTime : 1520931843000
         * id : 44
         * status : 1
         */

        private String bank;
        private double money;
        private String bank_num;
        private double fee;
        private long createdTime;
        private int id;
        private int status;

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public String getBank_num() {
            return bank_num;
        }

        public void setBank_num(String bank_num) {
            this.bank_num = bank_num;
        }

        public double getFee() {
            return fee;
        }

        public void setFee(double fee) {
            this.fee = fee;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
