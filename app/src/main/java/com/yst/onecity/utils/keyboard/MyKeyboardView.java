package com.yst.onecity.utils.keyboard;

import android.content.Context;
import android.graphics.Canvas;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;

public class MyKeyboardView extends KeyboardView {

        private Context context;

        public MyKeyboardView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public void setContext(Context context) {
            this.context = context;
        }

        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
        }
    }