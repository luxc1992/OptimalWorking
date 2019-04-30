package com.yst.onecity.bean;

/**
 * EventBus接受事件
 *
 * @author LianJinXue
 * @version 1.0.1
 * @date 2018/3/6
 */

public class MessageEvent {
    private String message;

    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
