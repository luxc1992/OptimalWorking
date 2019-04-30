package com.yst.im.imchatlibrary.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

import com.yst.im.imchatlibrary.R;

import static com.yst.im.imsdk.MessageConstant.NUM_7;
import static com.yst.im.imsdk.MessageConstant.NUM_8;

/**
 * 表情加载类,可自己添加多种表情，分别建立不同的map存放和不同的标志符即可
 *
 * @author Lierpeng
 * @date 2018/3/29.
 * @version 1.0.0
 */
public class LoadingView extends View {
    private Paint mPaint=new Paint();
    ValueAnimator scaleAnim;
    private int colorMain;
    private int startIndex = 0;
    public LoadingView(Context context) {
        this(context, null);
    }
    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        colorMain = context.getResources().getColor(R.color.colorPrimary);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        startAnimator();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        float radius = getWidth() / 12;
        for (int i = 0; i < NUM_8; i++) {
        Point point = circleAt(getWidth(), getHeight(), getWidth() / 2 - radius, i * (Math.PI / 4));
            if (startIndex < 5) {
                if (i >= startIndex && i < startIndex + 4) {
                    mPaint.setColor(Color.GRAY);
                } else {
                    mPaint.setColor(colorMain);
                }
            }else {
                if (i >= startIndex || i <=  startIndex -5 ) {
                    mPaint.setColor(Color.GRAY);
                } else {
                    mPaint.setColor(colorMain);
                }
            }
            canvas.drawCircle(point.x, point.y, radius, mPaint);
        }

    }


    /**
     * 圆O的圆心为(a,b),半径为R,点A与到X轴的为角α.
     *则点A的坐标为(a+R*cosα,b+R*sinα)
     * @param width
     * @param height
     * @param radius
     * @param angle
     * @return
     */
    Point circleAt(int width,int height,float radius,double angle){
        float x = (float) (width / 2 + radius * (Math.cos(angle)));
        float y = (float) (height / 2 + radius * (Math.sin(angle)));
        return new Point(x,y);
    }

    private void startAnimator(){
         scaleAnim= ValueAnimator.ofFloat(1,0.4f,1);
            scaleAnim.setDuration(100);
            scaleAnim.setRepeatCount(-1);
            scaleAnim.start();
        scaleAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                if (startIndex != NUM_7){
                    startIndex += 1;
                }else {
                    startIndex = 0;

                }
                invalidate();
            }
        });
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        scaleAnim.end();
    }
    final class Point{
        public float x;
        public float y;

        public Point(float x, float y){
            this.x=x;
            this.y=y;
        }
    }
}
