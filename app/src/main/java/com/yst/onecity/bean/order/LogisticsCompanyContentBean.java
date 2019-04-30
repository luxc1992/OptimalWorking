package com.yst.onecity.bean.order;

import com.yst.onecity.bean.MsgBean;

import java.util.List;

/**
 * 物流公司列表实体类
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/3/1
 */
public class LogisticsCompanyContentBean extends MsgBean {
    private List<LogisticsCompanyBean> content;

    public List<LogisticsCompanyBean> getContent() {
        return content;
    }
}
