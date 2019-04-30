package com.yst.onecity.bean.order;

import com.yst.onecity.bean.MsgBean;

import java.util.List;

/**
 * 退货原因列表实体类
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/3/3
 */
public class ReturnReasonContent extends MsgBean {
    private List<ReasonBean> content;

    public List<ReasonBean> getContent() {
        return content;
    }

}
