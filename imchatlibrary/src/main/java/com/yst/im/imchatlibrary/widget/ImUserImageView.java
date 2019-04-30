package com.yst.im.imchatlibrary.widget;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * imageView 处理回收试 异常奔溃
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/5/17
 */

@SuppressLint("AppCompatCustomView")
public class ImUserImageView extends ImageView {


    public ImUserImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        try {
            super.onDraw(canvas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
