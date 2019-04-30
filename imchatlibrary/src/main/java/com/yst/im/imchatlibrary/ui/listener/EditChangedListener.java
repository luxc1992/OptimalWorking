package com.yst.im.imchatlibrary.ui.listener;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yst.im.imchatlibrary.base.baseview.AbstractSortEnumType;
import com.yst.im.imchatlibrary.utils.ImLog;
import com.yst.im.imchatlibrary.utils.ImToastUtil;

/**
 * EditText 监听输入文字
 *
 * @author Lierpeng
 * @date 2018/03/28
 * @version 1.0.0
 */
public class EditChangedListener implements TextWatcher {
    /**
     * 光标结束位置
     */
    private int editStart;
    /**
     * 光标开始位置
     */
    private int editEnd;
    private int charMaxNum = 150;

    private EditText editText;
    private TextView textView;
    private CharSequence temp;
    private Context context;
    private boolean isToast = true;
    private AbstractSortEnumType abstractSortEnumType;

    public EditChangedListener(Context context, EditText editText, TextView textView, int charMaxNum, AbstractSortEnumType abstractSortEnumType) {
        this.editText = editText;
        this.textView = textView;
        this.charMaxNum = charMaxNum;
        this.context = context;
        this.abstractSortEnumType=abstractSortEnumType;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        temp = s;
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (textView != null) {
            if (temp == null) {
                textView.setText(String.valueOf(0));
            } else {
                if(abstractSortEnumType.equals(AbstractSortEnumType.reverseOrder)){
                    textView.setText(String.valueOf("还可以输入"+(charMaxNum-temp.length())+"字"));
                }else {
                    textView.setText(String.valueOf(temp.length()));
                }
            }
        }
        //得到光标开始和结束位置 ,超过最大数后记录刚超出的数字索引进行控制
        if (editText != null && temp != null && temp.length() != 0) {
            editStart = editText.getSelectionStart();
            editEnd = editText.getSelectionEnd();
            ImLog.e("ImLog", "isToast = " + isToast);
            if (temp.length() < charMaxNum) {
                isToast = true;
            }
            if (temp.length() > charMaxNum) {
                if (isToast) {
                    Toast.makeText(context.getApplicationContext(), "你输入的字数已经超过了限制！", Toast.LENGTH_SHORT).show();
                    isToast = false;
                }
                s.delete(editStart - 1, editEnd);
                int tempSelection = editStart;
                editText.setText(s);
                editText.setSelection(tempSelection);
            }
        }
    }

    public int getInputSize() {
        if (temp == null || temp.length() == 0) {
            return 0;
        }
        int size = temp.length();
        return size;
    }
}