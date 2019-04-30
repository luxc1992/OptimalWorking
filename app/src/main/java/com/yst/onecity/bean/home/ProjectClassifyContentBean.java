package com.yst.onecity.bean.home;

import com.yst.onecity.bean.MsgBean;

import java.io.Serializable;
import java.util.List;

/**
 * 首页课题分类
 *
 * @author chenxiaowei
 * @version 1.1.0
 * @date 2018/5/24
 */
public class ProjectClassifyContentBean extends MsgBean implements Serializable{
    private List<ProjectClassifyBean> content;

    public List<ProjectClassifyBean> getContent() {
        return content;
    }

    public void setContent(List<ProjectClassifyBean> content) {
        this.content = content;
    }
}
