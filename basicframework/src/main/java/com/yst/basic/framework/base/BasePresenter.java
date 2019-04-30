package com.yst.basic.framework.base;

import java.lang.ref.WeakReference;

/**
 * MVP框架的Presenter层
 *
 * @author lixiangchao
 * @date 2017/12/1.
 * @version 1.0.1
 */
public abstract class BasePresenter<T extends BaseViewInter, M extends BaseModelInter> {

    private WeakReference<T> weakReference;
    protected M model;

    /**
     * 创建
     *
     * @param t
     */
    public void attach(T t) {
        weakReference = new WeakReference<>(t);
        model = getModel();
    }

    /**
     * 销毁引用
     */
    public void deAttach() {
        if (weakReference != null) {
            weakReference.clear();
            weakReference = null;
        }
    }

    /**
     * 视图是否已经被加载
     *
     * @return
     */
    public boolean isViewAttached() {
        return weakReference != null && weakReference.get() != null;
    }

    /**
     * 获取view视图
     *
     * @return
     */
    public T getView() {
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    /**
     * 获取model
     *
     * @return
     */
    protected abstract M getModel();
}
