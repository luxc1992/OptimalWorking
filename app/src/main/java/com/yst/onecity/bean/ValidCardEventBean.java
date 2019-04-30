package com.yst.onecity.bean;


import com.yst.onecity.bean.mine.MyCardBagListBean;

import java.util.List;

/**
 * (我的)卡包-有效卡
 *
 * @author liumanqing
 * @version 1.1.0
 * @date 2018/5/28
 */
public class ValidCardEventBean {
    List<MyCardBagListBean.ContentBean.TrueMapBean.TrueArrayBean> trueMapCardBeen;
    List<MyCardBagListBean.ContentBean.TrueMapBean.MyCouponTrueJsonArrayBean> trueMapCouponBeen;
    List<MyCardBagListBean.ContentBean.FalseMapBean.FalseArrayBean> falseMapCardBeen;
    List<MyCardBagListBean.ContentBean.FalseMapBean.MyCouponFalseJsonArrayBean> falseMapCouponBeen;

    public ValidCardEventBean(List<MyCardBagListBean.ContentBean.TrueMapBean.TrueArrayBean> trueMapCardBeen, List<MyCardBagListBean.ContentBean.TrueMapBean.MyCouponTrueJsonArrayBean> trueMapCouponBeen, List<MyCardBagListBean.ContentBean.FalseMapBean.FalseArrayBean> falseMapCardBeen, List<MyCardBagListBean.ContentBean.FalseMapBean.MyCouponFalseJsonArrayBean> falseMapCouponBeen) {
        this.trueMapCardBeen = trueMapCardBeen;
        this.trueMapCouponBeen = trueMapCouponBeen;
        this.falseMapCardBeen = falseMapCardBeen;
        this.falseMapCouponBeen = falseMapCouponBeen;
    }

    public List<MyCardBagListBean.ContentBean.TrueMapBean.TrueArrayBean> getTrueMapCardBeen() {
        return trueMapCardBeen;
    }

    public void setTrueMapCardBeen(List<MyCardBagListBean.ContentBean.TrueMapBean.TrueArrayBean> trueMapCardBeen) {
        this.trueMapCardBeen = trueMapCardBeen;
    }

    public List<MyCardBagListBean.ContentBean.TrueMapBean.MyCouponTrueJsonArrayBean> getTrueMapCouponBeen() {
        return trueMapCouponBeen;
    }

    public void setTrueMapCouponBeen(List<MyCardBagListBean.ContentBean.TrueMapBean.MyCouponTrueJsonArrayBean> trueMapCouponBeen) {
        this.trueMapCouponBeen = trueMapCouponBeen;
    }

    public List<MyCardBagListBean.ContentBean.FalseMapBean.FalseArrayBean> getFalseMapCardBeen() {
        return falseMapCardBeen;
    }

    public void setFalseMapCardBeen(List<MyCardBagListBean.ContentBean.FalseMapBean.FalseArrayBean> falseMapCardBeen) {
        this.falseMapCardBeen = falseMapCardBeen;
    }

    public List<MyCardBagListBean.ContentBean.FalseMapBean.MyCouponFalseJsonArrayBean> getFalseMapCouponBeen() {
        return falseMapCouponBeen;
    }

    public void setFalseMapCouponBeen(List<MyCardBagListBean.ContentBean.FalseMapBean.MyCouponFalseJsonArrayBean> falseMapCouponBeen) {
        this.falseMapCouponBeen = falseMapCouponBeen;
    }

    @Override
    public String toString() {
        return "ValidCardEventBean{" +
                "trueMapCardBeen=" + trueMapCardBeen +
                ", trueMapCouponBeen=" + trueMapCouponBeen +
                ", falseMapCardBeen=" + falseMapCardBeen +
                ", falseMapCouponBeen=" + falseMapCouponBeen +
                '}';
    }
}
