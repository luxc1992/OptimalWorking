package com.yst.onecity.bean;

/**
 * 开户行实体类
 *
 * @author LianJinXue
 * @version 1.0.1
 * @date 2018/3/1
 */

public class BandBankBean {


    /**
     * code : 1
     * msg : 查询成功
     * content : {"bankName":"农业银行·金穗通宝卡(银联卡)","bankNano":"ABC"}
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
         * bankName : 农业银行·金穗通宝卡(银联卡)
         * bankNano : ABC
         */

        private String bankName;
        private String bankNano;

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getBankNano() {
            return bankNano;
        }

        public void setBankNano(String bankNano) {
            this.bankNano = bankNano;
        }
    }
}
