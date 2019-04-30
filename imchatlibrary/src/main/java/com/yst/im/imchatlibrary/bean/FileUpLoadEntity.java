package com.yst.im.imchatlibrary.bean;

/**
 * 聊天语音  图片请求实体类
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/6.
 */
public class FileUpLoadEntity {
    /**
     * code : 0
     * msg : 音频上传成功！
     * content : https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/amr/20180106/20180106135109349857011.amr
     */
    private int code;
    private String msg;
    private String content;
    private int type;
    private int messageType;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "FileUpLoadEntity{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
