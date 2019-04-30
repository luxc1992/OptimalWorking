package com.yst.im.imchatlibrary.bean;

import java.util.List;

/**
 * 搜索好友列表或者群列表
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/13
 */

public class SearchFigureEntity {

    /**
     * code : 0
     * msg : 查询成功
     */

    private int code;
    private String msg;
    private List<SearchResultEntity> content;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<SearchResultEntity> getContent() {
        return content;
    }

    public void setContent(List<SearchResultEntity> content) {
        this.content = content;
    }

}
