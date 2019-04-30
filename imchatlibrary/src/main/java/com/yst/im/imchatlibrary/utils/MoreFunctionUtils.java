package com.yst.im.imchatlibrary.utils;

import com.yst.im.imchatlibrary.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 表情vp
 *
 * @author lierpeng
 * @date 2018/03/28
 * @version 1.0.0
 */

public class MoreFunctionUtils {
    private static int[] icon = {R.drawable.icon_news_more_photo, R.drawable.icon_news_more_shoot};
    private static  String[] iconName = {"照片", "拍摄"};




    /**
     * 初始化 群聊更多集合
     *
     * @return 更多条目集合
     */
    public static List<Map<String, Object>> getInstanceDate() {
        List<Map<String, Object>> mDataList = new ArrayList<>();
        //icon和iconName的长度是相同的，这里任选其一都可以
        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>(icon.length);
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            mDataList.add(map);
        }
        return mDataList;
    }
    public static String[] fromArray = {"image", "text"};
    public static int[] toArray = {R.id.img_news_more_pic, R.id.txt_news_more_text};
}
