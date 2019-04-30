package com.yst.onecity.utils;

import com.yst.onecity.bean.GroupMemberBean;

import java.util.Comparator;

import static com.yst.onecity.Constant.AT;
import static com.yst.onecity.Constant.JING;

/**
 * 对手机通讯录的数据 根据拼音进行排序
 *
 * @author luxuchang
 * @version 1.0.1
 * @date 2018/2/26.
 */

public class PinyinComparator implements Comparator<GroupMemberBean> {

    @Override
    public int compare(GroupMemberBean o1, GroupMemberBean o2) {
        if (AT.equals(o1.getSortLetters())
                || JING.equals(o2.getSortLetters())) {
            return -1;
        } else if (JING.equals(o1.getSortLetters())
                || AT.equals(o2.getSortLetters())) {
            return 1;
        } else {
            return o1.getSortLetters().compareTo(o2.getSortLetters());
        }
    }

}