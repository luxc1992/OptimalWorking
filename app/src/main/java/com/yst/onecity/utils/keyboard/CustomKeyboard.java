package com.yst.onecity.utils.keyboard;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import com.yst.basic.framework.utils.ToastUtils;
import com.yst.onecity.R;

/**
 * 自定义键盘
 *
 * @author wuxiaofang
 * @version 1.1.0
 * @date 2018/5/21
 */
public class CustomKeyboard {

    private final Context context;
    private EditText mEdittext;
    private MyKeyboardView mKeyboardView;
    private Keyboard mKeyboard;

    public CustomKeyboard(Context context, MyKeyboardView keyboardView, EditText editText) {
        this.context = context;
        this.mEdittext = editText;
        mKeyboard = new Keyboard(context, R.xml.layout_keyboard);
        mKeyboardView = keyboardView;
        mKeyboardView.setContext(context);
        mKeyboardView.setKeyboard(mKeyboard);
        mKeyboardView.setPreviewEnabled(false);
        mKeyboardView.setOnKeyboardActionListener(actionListener);
    }

    private KeyboardView.OnKeyboardActionListener actionListener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onPress(int primaryCode) {
        }

        @Override
        public void onRelease(int primaryCode) {

        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            Editable editable = mEdittext.getText();
            int index = mEdittext.getSelectionStart();
            switch (primaryCode) {
                case 9995:
                    ToastUtils.show("验证");
                    break;
                default:
                    editable.insert(index, Character.toString((char) primaryCode));
                    break;
            }
        }

        @Override
        public void onText(CharSequence text) {

        }

        @Override
        public void swipeLeft() {

        }

        @Override
        public void swipeRight() {

        }

        @Override
        public void swipeDown() {

        }

        @Override
        public void swipeUp() {

        }
    };

    public void showKeyboard() {
        if (mKeyboardView.getVisibility() != View.VISIBLE) {
            mKeyboardView.setVisibility(View.VISIBLE);
        }
    }

    public void hideKeyboard() {
        if (mKeyboardView.getVisibility() == View.VISIBLE) {
            mKeyboardView.setVisibility(View.GONE);
        }
    }

    public boolean isShowKeyboard() {
        return mKeyboardView.getVisibility() == View.VISIBLE;
    }
}