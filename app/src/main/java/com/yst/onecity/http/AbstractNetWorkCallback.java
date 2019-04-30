package com.yst.onecity.http;

/**
 * 网络请求回调
 *
 * @author chejianqi
 * @version 1.0.1
 * @date 2018/2/28
 */
public abstract class AbstractNetWorkCallback<T> {

    /**
     * 成功
     *
     * @param t 泛型
     */
    public abstract void onSuccess(T t);

    /**
     * 失败
     *
     * @param errorMsg 错误信息
     */
    public abstract void onError(String errorMsg);

    /**
     * onBefore
     */
    public void onBefore() {
    }

    /**
     * onAfter
     */
    public void onAfter() {
    }

    /**
     * 缓存
     *
     * @param jsonData json数据
     */
    public void onSuccessString(String jsonData){

    }
}
