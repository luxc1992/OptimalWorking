package com.yst.onecity.pay;

import com.yst.onecity.bean.MsgBean;

/**
 * 支付结果实体类
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/3/7
 */
public class PayResultBean extends MsgBean {
    private OrderBean content;

    public OrderBean getContent() {
        return content;
    }
}
