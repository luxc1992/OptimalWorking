package com.yst.onecity.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yst.basic.framework.utils.WindowUtils;
import com.yst.onecity.R;
import com.yst.onecity.bean.TagMsg;

import java.io.Serializable;

import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO100;
import static com.yst.onecity.Constant.NO2;
import static com.yst.onecity.Constant.NO50;

/**
 * 随意拖动的view
 *
 * @author chejianqi
 * @version 1.0.1
 * @date 2018/5/21
 */

public class DragView extends LinearLayout implements Serializable {
    private Context context;
    private float downX;
    private float downY;
    private View view;
    private ImageView imageViewLeft, imageViewRight, initImageView;
    private TextView productName;
    private int initWidth, initHeight;
    private TagMsg tagMsg;
    private int imgHeight;

    public DragView(Context context) {
        super(context);
        this.context = context;
        initView(context);
    }

    public DragView(Context context, ImageView imageView, TagMsg tagMsg, int imgHeight) {
        super(context);
        this.context = context;
        this.initImageView = imageView;
        this.tagMsg = tagMsg;
        this.imgHeight = imgHeight;
        initView(context);
    }


    public DragView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public DragView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.item_drag_view, null);
        imageViewLeft = view.findViewById(R.id.round_local_left);
        imageViewRight = view.findViewById(R.id.round_local_right);
        productName = view.findViewById(R.id.product_name);
        productName.setText(tagMsg.getProductName());
        addView(view);

        //height == 屏幕的高度减去状态栏高度和标题栏的高度。
        int height = WindowUtils.getScreenHeight((Activity) context) - WindowUtils.getStatusBarHeight(context.getResources()) - WindowUtils.dip2px(context, 50);
        if (imgHeight>height){
            imgHeight=height;
        }
        //设置标签的Y为图片的Y
        setY((height - imgHeight) / 2);
        RelativeLayout.LayoutParams lpFeedback = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lpFeedback.leftMargin = (int) (Double.parseDouble(tagMsg.getLabelX())/100 * WindowUtils.getScreenWidth((Activity) context));
        lpFeedback.topMargin = (int) (Double.parseDouble(tagMsg.getLabelY())/100 * imgHeight);
        setLayoutParams(lpFeedback);
        //设置标签圆点的位置。
        if ((Double.parseDouble(tagMsg.getLabelX())/NO100)*WindowUtils.getScreenWidth((Activity) context) > WindowUtils.getScreenWidth((Activity) context) / NO2) {
            imageViewLeft.setVisibility(INVISIBLE);
            imageViewRight.setVisibility(VISIBLE);
            productName.setBackgroundResource(R.drawable.shape_product_item_left);
        } else {
            imageViewLeft.setVisibility(VISIBLE);
            imageViewRight.setVisibility(INVISIBLE);
            productName.setBackgroundResource(R.drawable.shape_product_item);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if (this.isEnabled()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downX = event.getX();
                    downY = event.getY();
                    initWidth = getWidth();
                    initHeight = getHeight();
                    break;
                case MotionEvent.ACTION_MOVE:
                    //把标签固定在图片内
                    int l, r, t, b;
                    final float xDistance = event.getX() - downX;
                    final float yDistance = event.getY() - downY;
                    l = (int) (getLeft() + xDistance);
                    r = l + getWidth();
                    t = (int) (getTop() + yDistance);
                    b = t + getHeight();

                    if (l > WindowUtils.getScreenWidth((Activity) context) / NO2) {
                        imageViewLeft.setVisibility(INVISIBLE);
                        imageViewRight.setVisibility(VISIBLE);
                        productName.setBackgroundResource(R.drawable.shape_product_item_left);
                    } else {
                        imageViewLeft.setVisibility(VISIBLE);
                        imageViewRight.setVisibility(INVISIBLE);
                        productName.setBackgroundResource(R.drawable.shape_product_item);
                    }
                    this.layout(l, t, r, b);
                    if (l >= 0 && t >= 0 && r <= WindowUtils.getScreenWidth((Activity) context) && b <= initImageView.getHeight()) {
                        this.layout(l, t, r, b);
                    } else {
                        if (l < 0) {
                            this.layout(NO0, t, initWidth, b);
                            if (t < 0) {
                                this.layout(NO0, NO0, initWidth, initHeight);
                            }
                            if (b > initImageView.getHeight()) {
                                this.layout(NO0, initImageView.getHeight() - initHeight, initWidth, initImageView.getHeight());
                            }
                        } else if (t < 0) {
                            this.layout(l, NO0, r, initHeight);
                            if (r > WindowUtils.getScreenWidth((Activity) context)) {
                                this.layout(WindowUtils.getScreenWidth((Activity) context) - initWidth, NO0, WindowUtils.getScreenWidth((Activity) context), initHeight);
                            }
                        } else if (r > WindowUtils.getScreenWidth((Activity) context)) {
                            this.layout(WindowUtils.getScreenWidth((Activity) context) - initWidth, t, WindowUtils.getScreenWidth((Activity) context), b);
                            if (b > initImageView.getHeight()) {
                                this.layout(WindowUtils.getScreenWidth((Activity) context) - initWidth, initImageView.getHeight() - initHeight, WindowUtils.getScreenWidth((Activity) context), initImageView.getHeight());
                            }
                        } else if (b > initImageView.getHeight()) {
                            this.layout(l, initImageView.getHeight() - initHeight, r, initImageView.getHeight());
                            if (r > WindowUtils.getScreenWidth((Activity) context)) {
                                this.layout(WindowUtils.getScreenWidth((Activity) context) - initWidth, initImageView.getHeight() - WindowUtils.dip2px(context, NO50) - initHeight, WindowUtils.getScreenWidth((Activity) context), initImageView.getHeight());
                            }
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    RelativeLayout.LayoutParams lpFeedback = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    lpFeedback.leftMargin = getLeft();
                    lpFeedback.topMargin = getTop();
                    lpFeedback.setMargins(getLeft(), getTop(), 0, 0);
                    setLayoutParams(lpFeedback);
                    break;
                default:
                    break;
            }
        }
        return true;
    }
}
