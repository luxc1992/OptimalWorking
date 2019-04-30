package com.yst.onecity.bean.issue;

import me.yokeyword.indexablerv.IndexableEntity;

/**
 * 城市实体类
 *
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/2/24
 */
public class CityBean implements IndexableEntity {
    private String name;
    private String pinyin;
    private int id;

    public CityBean(String name, String pinyin, int id) {
        this.name = name;
        this.pinyin = pinyin;
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getFieldIndexBy() {
        return name;
    }

    @Override
    public void setFieldIndexBy(String indexField) {
        this.name = indexField;
    }

    @Override
    public void setFieldPinyinIndexBy(String pinyin) {
        // 需要用到拼音时(比如:搜索), 可增添pinyin字段 this.pinyin  = pinyin
        // 见 CityEntity
    }
}
