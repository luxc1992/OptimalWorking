package com.yst.im.imsdk;

import java.nio.ByteBuffer;

/**
 * 读取缓冲流封装方法
 *
 * @author Lierpeng
 * @version 1.0.2
 * @date 2018/3/15.
 */
public class SelectAttachObj {

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

    Integer alreadyLength = 0;
    Integer objLength = 0;
    Integer curState = 0;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    String content = "";


    public void setCurState(Integer curState) {
        this.curState = curState;
    }
}