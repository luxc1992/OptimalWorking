package com.yst.onecity.bean.order;

import com.yst.onecity.bean.MsgBean;

import java.util.List;

/**
 * 选择售后列表实体类
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/3/5
 */
public class ChooseAfterContentBean extends MsgBean {
    private List<OrderChildBean> content;

    public List<OrderChildBean> getContent() {
        return content;
    }
}
