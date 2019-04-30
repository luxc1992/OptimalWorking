package com.yst.im.imchatlibrary.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 查询我创建的群聊
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/4/12.
 */
public class MyCreatGroupEntity implements Serializable{

    /**
     * code : 0
     * msg : 查询我创建的群聊成功
     * content : [{"id":8,"createTime":1523502957000,"updateTime":null,"userId":25,"groupNumberByMax":500,"groupNumberByCurrent":null,"groupType":null,"groupName":"葫芦娃","descrbe":"回家睡不好","topic":null,"imageUrl":"[\"https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180411/20180411140048050425910.jpeg\"]","groupUserType":null,"prohibitUserSpeak":null,"requestSourceSystem":"im","groups":[],"applyUser":[]}]
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
