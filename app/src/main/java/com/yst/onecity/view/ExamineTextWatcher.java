package com.yst.onecity.view;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import com.yst.onecity.utils.ValidateUtil;

/**
 * 用户输入验证器
 *
 * @author songbinbin
 * @version 1.0.1
 * @date 2018/4/8
 */
public class ExamineTextWatcher implements TextWatcher {
    private static final String TAG = "ExamineTextWatcher";

    /**
     * 最大字符数
     */
    private int maxLen = 20;

    /**
     * 帐号
     */
    public static final int TYPE_ACCOUNT = 1;

    /**
     * 金额
     */
    public static final int TYPE_MONEY = 2;

    /**
     * 字母，数字，汉字
     */
    public static final int TYPE_TEXT_NUMBER_LETTER = 3;

    /**
     * 输入框
     */
    private EditText mEditText;

    /**
     * 验证类型
     */
    private int examineType;

    /**
     * 输入前的文本内容
     */
    private String beforeText;

    /**
     * 构造器
     *
     * @param type     验证类型
     * @param editText 输入框
     */
    public ExamineTextWatcher(int type, EditText editText, int maxLen) {
        this.examineType = type;
        this.mEditText = editText;
        this.maxLen = maxLen;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // 输入前的字符
        beforeText = s.toString();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // 输入后的字符
        String afterText = s.toString();

        boolean isValid = true;
        if (!TextUtils.isEmpty(afterText)) {
            switch (examineType) {
                case TYPE_ACCOUNT:
                    isValid = ValidateUtil.isAccount(afterText);
                    break;
                case TYPE_MONEY:
                    isValid = ValidateUtil.isMoney(afterText);
                    break;
                case TYPE_TEXT_NUMBER_LETTER:
                    isValid = ValidateUtil.isTextNumberLetter(afterText);
                    break;
                default:
                    break;
            }
            if (!isValid) {
                // 用户现在输入的字符数减去之前输入的字符数，等于新增的字符数
                int differ = afterText.length() - beforeText.length();
                // 如果用户的输入不符合规范，则显示之前输入的文本
                mEditText.setText(beforeText);
                // 光标移动到文本末尾
                mEditText.setSelection(afterText.length() - differ);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        int charSequenceCount = countChineseChar(s);
        if (s.length() + charSequenceCount > maxLen) {
            mEditText.setText(s.subSequence(0, s.length() - 1));
            mEditText.setSelection(mEditText.getText().length());
            return;
        }
    }

    /**
     * 计算中文字符
     *
     * @param sequence
     * @return
     */
    private int countChineseChar(CharSequence sequence) {

        if (TextUtils.isEmpty(sequence)) {
            return 0;
        }
        int charNum = 0;
        for (int i = 0; i < sequence.length(); i++) {
            char word = sequence.charAt(i);
            //中文
            if (isChineseChar(word)) {
                charNum++;
            }
        }
        return charNum;
    }

    /**

     * 判断是否是中文
     * @param c 字符
     * @return true = 是  false = 不是
     */
    public static boolean isChineseChar(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }
}
