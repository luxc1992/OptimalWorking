package com.yst.basic.framework.entity;

/**
 * 搜索历史
 *
 * @author lixiangchao
 * @date 2017/11/28
 * @version 1.0.1
 */
public class SearchHistory {
    /**
     * 记录的id
     */
    private String id;
    /**
     * 保存的数据
     */
    private String keyword;

    private String name;

    public SearchHistory(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
