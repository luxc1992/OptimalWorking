package com.yst.im.imchatlibrary.base;

import android.text.TextUtils;

import java.util.List;

/**
 * 文件夹
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/03/27
 */
public class Folder {
    public String name;
    public String path;
    public Image cover;
    public List<Image> images;

    @Override
    public boolean equals(Object o) {
        try {
            Folder other = (Folder) o;
            return TextUtils.equals(other.path, path);
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        return super.equals(o);
    }
}
