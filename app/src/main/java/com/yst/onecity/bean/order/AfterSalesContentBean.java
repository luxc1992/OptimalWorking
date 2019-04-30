package com.yst.onecity.bean.order;

import com.yst.onecity.bean.MsgBean;

import java.util.List;

/**
 * 售后列表实体类
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/3/1
 */
public class AfterSalesContentBean extends MsgBean {
    private List<AfterSalesProductBean> content;

    public List<AfterSalesProductBean> getContent() {
        return content;
    }
}
