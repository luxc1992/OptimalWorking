package com.yst.onecity.pay;

/**
 * 微信支付服务端返回订单信息实体
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/3/6
 */
public class OrderBean {
    /**
     * 支付返回结果
     */
    private String requestURL;
    /**
     * partid : 1488438432
     * appid : wxc94befe7059e7123
     * sign : CB4418B8BEEBBC84A207CD22BC49F0A9
     * prepayid : null
     * noncestr : dKUL5LbHteAMF2X3MlDZ4VMoBKXaL8yA
     * timestamp : 1506150329
     */
    private String partid;
    private String appid;
    private String sign;
    private String prepayid;
    private String noncestr;
    private String timestamp;

    public String getRequestURL() {
        return requestURL;
    }

    public String getPartid() {
        return partid;
    }

    public void setPartid(String partid) {
        this.partid = partid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
