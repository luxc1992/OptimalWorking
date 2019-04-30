package com.yst.onecity.bean;

/**
 * (我的)卡包-实体类
 *
 * @author liumanqing
 * @version 1.1.0
 * @date 2018/5/18
 */
public class CardBagBean {
    private String name;
    private String time;
    private String explain;
    private String date;
    private String price;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public CardBagBean(String name, String time, String explain, String date, String price) {
        this.name = name;
        this.time = time;
        this.explain = explain;
        this.date = date;
        this.price = price;
    }
}
