
package com.yst.im.imchatlibrary.bean;

import java.io.Serializable;

/**
 * 定义消息头部
 *
 * @author Lierpeng
 * @date 2018/03/27.
 * @version 1.0.0
 */
public class BaseHeader implements Serializable {

    private Long id;

    private static final long serialVersionUID = 1L;
    /**
     * 定义消息类型 0json 1xml 2 图片 3视频 4文件 5文件夹  6语音
     */
    private int type = -1;
    /**
     * 0登录 1发送内容 2心跳包 3群聊 4添加好友 5删除好友 6加入群聊 7退出群聊 8解散群聊
     */
    private int event = -1;
    /**
     * 消息唯一的流水号；每次消息切记不要重复
     */
    private String version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
