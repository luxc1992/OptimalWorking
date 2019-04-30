package com.yst.onecity.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yst.onecity.R;


/**
 * 页面顶部返回栏
 *
 * @author lixiangchao
 * @version 1.0.0
 * @date 2018/4/12
 */
public class HeaderBar extends FrameLayout implements View.OnClickListener {

    private boolean isShowBack = true;
    private String titleText = "";
    private String rightText = "";

    private ImageView imgHeaderBarBack;
    public TextView txtHeaderBarTitle;
    private TextView txtHeaderBarRightContent;

    public HeaderBar(@NonNull Context context) {
        this(context, null);
    }

    public HeaderBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.HeaderBar);
        isShowBack = typedArray.getBoolean(R.styleable.HeaderBar_isShowBack, true);
        titleText = typedArray.getString(R.styleable.HeaderBar_titleText);
        rightText = typedArray.getString(R.styleable.HeaderBar_rightText);
        init();
        typedArray.recycle();
    }

    /**
     * 初始化页面控件
     */
    private void init() {
        View.inflate(getContext(), R.layout.layout_header_bar, this);
        imgHeaderBarBack = (ImageView) findViewById(R.id.img_header_back);
        txtHeaderBarTitle = (TextView) findViewById(R.id.txt_header_title);
        txtHeaderBarRightContent = (TextView) findViewById(R.id.txt_header_right_content);
        imgHeaderBarBack.setVisibility(isShowBack ? VISIBLE : GONE);
        txtHeaderBarTitle.setText(titleText != null ? titleText : "");
        if (rightText != null) {
            txtHeaderBarRightContent.setVisibility(VISIBLE);
            txtHeaderBarRightContent.setText(rightText);
        } else {
            txtHeaderBarRightContent.setVisibility(GONE);
            txtHeaderBarRightContent.setText("");
        }
        imgHeaderBarBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (getContext() instanceof Activity) {
            ((Activity) getContext()).finish();
        }
    }

    /**
     * 右侧文本控件反馈
     *
     * @return 右侧按钮
     */
    public TextView getRightTextView() {
        return txtHeaderBarRightContent;
    }
}
