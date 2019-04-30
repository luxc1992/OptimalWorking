package com.yst.onecity.bean;

/**
 * 银行卡信息实体类
 *
 * @author LianJinXue
 * @version 1.0.1
 * @date 2018/3/1
 */

public class BankinfroBean {

    /**
     * code : 1
     * msg : 查询成功
     * content : {"bank":"卡类型","singleFee":"5.00","bankNo":"622908323033896788","id":19}
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
         * bank : 卡类型
         * singleFee : 5.00
         * bankNo : 622908323033896788
         * id : 19
         */

        private String bank;
        private String singleFee;
        private String bankNo;
        private int id;

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public String getSingleFee() {
            return singleFee;
        }

        public void setSingleFee(String singleFee) {
            this.singleFee = singleFee;
        }

        public String getBankNo() {
            return bankNo;
        }

        public void setBankNo(String bankNo) {
            this.bankNo = bankNo;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "ContentBean{" +
                    "bank='" + bank + '\'' +
                    ", singleFee='" + singleFee + '\'' +
                    ", bankNo='" + bankNo + '\'' +
                    ", id=" + id +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "BankinfroBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", content=" + content +
                '}';
    }
}
