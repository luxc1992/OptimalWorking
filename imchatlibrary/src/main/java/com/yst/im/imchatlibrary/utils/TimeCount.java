package com.yst.im.imchatlibrary.utils;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * 获取验证码
 *
 * @author lierpeng
 * @date 2018/04/08
 * @version 1.0.0
 */
public class TimeCount extends CountDownTimer {
    private TextView mTextView;

    public TimeCount(long millisInFuture, long countDownInterval,TextView mTextView) {
        super(millisInFuture, countDownInterval);
        this.mTextView=mTextView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mTextView.setClickable(false);
        mTextView.setText(String.valueOf(millisUntilFinished / 1000 + " 秒后可重获"));
    }

    @Override
    public void onFinish() {
        mTextView.setText("重新获取");
        mTextView.setClickable(true);
    }
}