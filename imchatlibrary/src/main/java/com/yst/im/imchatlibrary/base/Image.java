package com.yst.im.imchatlibrary.base;

import android.text.TextUtils;

/**
 * 图片实体
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/03/30
 */
public class Image {
    public String path;
    public String name;
    public long time;

    public Image(String path, String name, long time){
        this.path = path;
        this.name = name;
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        try {
            Image other = (Image) o;
            return TextUtils.equals(this.path, other.path);
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        return super.equals(o);
    }
}
