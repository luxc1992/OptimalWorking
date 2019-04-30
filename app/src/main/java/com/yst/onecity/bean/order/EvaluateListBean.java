package com.yst.onecity.bean.order;

import com.yst.onecity.bean.MsgBean;

import java.util.List;

/**
 * 评价列表实体类
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/3/1
 */
public class EvaluateListBean extends MsgBean {
    private List<OrderChildBean> content;

    public List<OrderChildBean> getContent() {
        return content;
    }
}
