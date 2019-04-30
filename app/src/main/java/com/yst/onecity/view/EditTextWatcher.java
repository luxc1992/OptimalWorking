package com.yst.onecity.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.yst.basic.framework.utils.ToastUtils;
import com.yst.onecity.utils.MyLog;

/**
 * EditText监听类
 *
 * @version 1.0.1
 * @author jiaofan
 * @date 2017/12/22
 */

public class EditTextWatcher implements TextWatcher {
    /**
     * 字符个数限制
     */
    private int limit;
    
    /**
     * 编辑框控件
     */
    private EditText text;
    
    /**
     * 计数控件
     */
    private TextView txt;
    
    /**
     * 上下文对象
     */
    private Context context;

    /**
     * 用来记录输入字符的时候光标的位置
     */
    int cursor = 0;
    
    /**
     * 用来标注输入某一内容之前的编辑框中的内容的长度
     */
    int beforeLength;

    public EditTextWatcher(EditText text, TextView txt, int limit, Context context) {
        this.limit = limit;
        this.text = text;
        this.txt = txt;
        this.context = context;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {
        beforeLength = s.length();
    }

    @Override
    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
        cursor = i;
    }

    @Override
    public void afterTextChanged(Editable s) {
        MyLog.e("此时你已经输入了", "" + s.length());
        // 输入内容后编辑框所有内容的总长度
        int afterLength = s.length();
        txt.setText(String.valueOf(afterLength));
        // 如果字符添加后超过了限制的长度，那么就移除后面添加的那一部分，这个很关键
        if (afterLength > limit) {
            txt.setText(String.valueOf(limit));
            // 比限制的最大数超出了多少字
            int dValue = afterLength - limit;
            // 这时候从手机输入的字的个数
            int dNum = afterLength - beforeLength;
            // 需要删除的超出部分的开始位置
            int st = cursor + (dNum - dValue);
            // 需要删除的超出部分的末尾位置
            int en = cursor + dNum;
            // 调用delete()方法将编辑框中超出部分的内容去掉
            Editable sNew = s.delete(st, en);
            // 给编辑框重新设置文本
            text.setText(sNew.toString());
            // 设置光标最后显示的位置为超出部分的开始位置，优化体验
            text.setSelection(st);
            // 弹出信息提示已超出字数限制
            ToastUtils.show("已超出最大字数限制");
        }
    }
}
