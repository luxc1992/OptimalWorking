package com.yst.basic.framework.listener;

/**
 * 自定义接口，用于给密码输入完成添加回掉事件
 *
 * @author lixiangchao
 * @date 2017/12/5
 * @version 1.0.1
 */
public interface OnPasswordInputFinish {
    /**
     * 输入最后位的监听
     * @param password 密码框
     */
    void inputFinish(String password);
}