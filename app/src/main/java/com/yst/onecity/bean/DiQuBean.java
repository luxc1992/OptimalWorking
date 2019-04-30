package com.yst.onecity.bean;

/**
 * 区县实体类
 *
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/2/25
 */

public class DiQuBean {
    private String id;
    private String name;
    public DiQuBean()
    {
        super();

    }
    public DiQuBean(String id, String name)
    {
        super();
        this.id = id;
        this.name = name;
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
    @Override
    public String toString()
    {
        return name;
    }
}
