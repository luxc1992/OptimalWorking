package com.yst.onecity.bean;

import java.util.List;

/**
 * 城市实体类
 *
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/2/25
 */

public class CityBean {
    private String id;
    private String name;
    private List<DiQuBean> diQus;
    public CityBean()
    {
        super();

    }
    public CityBean(String id, String name, List<DiQuBean> diQus)
    {
        super();
        this.id = id;
        this.name = name;
        this.diQus = diQus;
    }
    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public List<DiQuBean> getDiQus()
    {
        return diQus;
    }
    public void setDiQus(List<DiQuBean> diQus)
    {
        this.diQus = diQus;
    }
    @Override
    public String toString()
    {
        return name;
    }
}
