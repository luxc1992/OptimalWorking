package com.yst.onecity.utils;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yst.onecity.Constant;

/**
 * 对输入框的输入字数监听
 *
 * @author wuxiaofang
 * @version 1.1.0
 * @date 2018/05/18
 */
public class MyTextWatcher implements TextWatcher {
    /**
     * 字符个数限制
     */
    private int limit;
    /**
     * 编辑框控件
     */
    private EditText text;
    /**
     * 上下文对象
     */
    private Context context;
    /**
     * 用来记录输入字符的时候光标的位置
     */
    private int cursor = 0;
    /**
     * 用来标注输入某一内容之前的编辑框中的内容的长度
     */
    private int beforeLength;
    private TextView tvNum;
    private boolean isNoPut;

    public MyTextWatcher(int limit, EditText text, Context context, TextView tvNum) {
        this.limit = limit;
        this.text = text;
        this.context = context;
        this.tvNum = tvNum;
    }

    public void setIsNoInput(boolean is) {
        this.isNoPut = is;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        beforeLength = s.length();
    }

    /**
     * s 编辑框中全部的内容 、start 编辑框中光标所在的位置（从0开始计算）、count 从手机的输入法中输入的字符个数
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        cursor = start;
    }

    @Override
    public void afterTextChanged(Editable s) {
        /*
         *这里可以知道你已经输入的字数，大家可以自己根据需求来自定义文本控件实时的显示已经输入的字符个数
         */
        int afterLength = s.length();
        /*
         * 如果字符添加后超过了限制的长度，那么就移除后面添加的那一部分，这个很关键
         */
        if (isNoPut) {
            if (afterLength <= Constant.NO100) {
                afterLength = Constant.NO100 - afterLength;
            }
        }
        tvNum.setText(String.valueOf(afterLength));
        if (afterLength > limit) {
            /*
             * 比限制的最大数超出了多少字
             */
            int dValue = afterLength - limit;
            /*
             * 这时候从手机输入的字的个数
             */
            int dNum = afterLength - beforeLength;
            /*
             * 需要删除的超出部分的开始位置
             */
            int st = cursor + (dNum - dValue);
            /*
             * 需要删除的超出部分的末尾位置
             */
            int en = cursor + dNum;
            /*
             * 调用delete()方法将编辑框中超出部分的内容去掉
             */
            Editable sNew = s.delete(st, en);
            /*
             * 给编辑框重新设置文本
             */
            text.setText(sNew.toString());
            /*
             * 设置光标最后显示的位置为超出部分的开始位置，优化体验
             */
            text.setSelection(st);
            /*
             * 弹出信息提示已超出字数限制
             */
            Toast.makeText(context, "已超出最大字数限制", Toast.LENGTH_SHORT).show();
        }
    }
}