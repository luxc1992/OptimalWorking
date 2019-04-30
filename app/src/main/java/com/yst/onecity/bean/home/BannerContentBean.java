package com.yst.onecity.bean.home;

import com.yst.onecity.bean.MsgBean;

import java.util.List;

/**
 * 首页轮播图
 *
 * @author chenxiaowei
 * @version 1.1.0
 * @date 2018/5/24
 */
public class BannerContentBean extends MsgBean {
    private List<BannerBean> content;


    public List<BannerBean> getContent() {
        return content;
    }

    public void setContent(List<BannerBean> content) {
        this.content = content;
    }
}
