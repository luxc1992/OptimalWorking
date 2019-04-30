package com.yst.onecity.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.view.ContainsEmojiEditText;

/**
 * 输入框监听
 *
 * @author luxuchang
 * @version 1.0.1
 * @date 2018/2/22.
 */

public class MyEditTextChangeListener implements TextWatcher {

    private ContainsEmojiEditText emojiEditText1, emojiEditText2, emojiEditText3;
    private TextView btnCommit;


    public MyEditTextChangeListener(ContainsEmojiEditText emojiEditText1, TextView btnCommit) {
        this.emojiEditText1 = emojiEditText1;
        this.btnCommit = btnCommit;
    }

    public MyEditTextChangeListener(ContainsEmojiEditText emojiEditText1, ContainsEmojiEditText emojiEditText2, TextView btnCommit) {
        this.emojiEditText1 = emojiEditText1;
        this.emojiEditText2 = emojiEditText2;
        this.btnCommit = btnCommit;
    }

    public MyEditTextChangeListener(ContainsEmojiEditText emojiEditText1, ContainsEmojiEditText emojiEditText2, ContainsEmojiEditText emojiEditText3, TextView btnCommit) {
        this.emojiEditText1 = emojiEditText1;
        this.emojiEditText2 = emojiEditText2;
        this.emojiEditText3 = emojiEditText3;
        this.btnCommit = btnCommit;
    }

    /**
     * 编辑框的内容发生改变之前的回调方法
     */
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    /**
     * 编辑框的内容正在发生改变时的回调方法 >>用户正在输入
     * 我们可以在这里实时地 通过搜索匹配用户的输入
     */
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    /**
     * 编辑框的内容改变以后,用户没有继续输入时 的回调方法
     */
    @Override
    public void afterTextChanged(Editable editable) {
        if (emojiEditText1 != null && emojiEditText2 != null && emojiEditText3 != null) {
            if (emojiEditText1.getText().toString().trim().length() > 0 && emojiEditText2.getText().toString().trim().length() > 0 && emojiEditText3.getText().toString().trim().length() > 0) {
                btnCommit.setEnabled(true);
                btnCommit.setBackgroundResource(R.drawable.shape_btn_bg);
            } else {
                btnCommit.setEnabled(false);
                btnCommit.setBackgroundResource(R.drawable.shape_btn_pink_red_bg);
            }
        } else if (emojiEditText3 == null) {
            if (emojiEditText1.getText().toString().trim().length() > 0 && emojiEditText2.getText().toString().trim().length() > 0) {
                btnCommit.setEnabled(true);
                btnCommit.setBackgroundResource(R.drawable.shape_btn_bg);
            } else {
                btnCommit.setEnabled(false);
                btnCommit.setBackgroundResource(R.drawable.shape_btn_pink_red_bg);
            }
        } else if (emojiEditText2 == null && emojiEditText3 == null) {
            if (emojiEditText1.getText().toString().trim().length() > 0) {
                btnCommit.setEnabled(true);
                btnCommit.setBackgroundResource(R.drawable.shape_btn_bg);
            } else {
                btnCommit.setEnabled(false);
                btnCommit.setBackgroundResource(R.drawable.shape_btn_pink_red_bg);
            }
        }
    }
}
