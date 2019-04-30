package com.yst.im.imsdk.bean;

/**
 * 读取缓冲流封装方法
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/11.
 */
public class SelectAttachObj {

    private String content = "";

    private Integer alreadyLength = 0;

    private Integer objLength = 0;

    private Integer curState = 0;

    public Integer getCurState() {
        return curState;
    }

    public Integer getObjLength() {
        return objLength;
    }

    public void setObjLength(Integer objLength) {
        this.objLength = objLength;
    }

    public Integer getAlreadyLength() {
        return alreadyLength;
    }

    public void setAlreadyLength(Integer alreadyLength) {
        this.alreadyLength = alreadyLength;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCurState(Integer curState) {
        this.curState = curState;
    }
}