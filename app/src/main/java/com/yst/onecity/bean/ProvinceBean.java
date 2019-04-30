package com.yst.onecity.bean;

import java.util.List;

/**
 * 省份实体类
 *
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/2/25
 */

public class ProvinceBean {
    private String id;
    private String name;
    private List<CityBean> citys;
    public ProvinceBean()
    {
        super();

    }
    public ProvinceBean(String id, String name, List<CityBean> citys)
    {
        super();
        this.id = id;
        this.name = name;
        this.citys = citys;
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
    public List<CityBean> getCitys()
    {
        return citys;
    }
    public void setCitys(List<CityBean> citys)
    {
        this.citys = citys;
    }
    @Override
    public String toString()
    {
        return name;
    }
}
