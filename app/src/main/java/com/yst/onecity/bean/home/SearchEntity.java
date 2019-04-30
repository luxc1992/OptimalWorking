package com.yst.onecity.bean.home;

import com.yst.basic.framework.entity.SearchHistory;
import com.yst.onecity.bean.MsgBean;

import java.util.List;

/**
 * 热门搜索
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/5/24.
 */

public class SearchEntity extends MsgBean {

    private List<SearchHistory> data;

    public List<SearchHistory> getData() {
        return data;
    }

    public void setData(List<SearchHistory> data) {
        this.data = data;
    }
}
