package com.yst.onecity.bean.order;

import com.yst.onecity.bean.MsgBean;

import java.util.List;

/**
 * 猎头订单列表
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/2/28
 */
public class HunterOrderListBean extends MsgBean {
    private List<OrderGroupBean> content;

    public List<OrderGroupBean> getContent() {
        return content;
    }

    public void setContent(List<OrderGroupBean> content) {
        this.content = content;
    }
}
