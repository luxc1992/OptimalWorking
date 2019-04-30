package com.yst.onecity.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yst.onecity.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索记录流布局
 * 新增加单选模式
 *
 * @author chenjiadi
 * @version 1.0.1
 * @date 2018/2/6
 */

public class FlowCheckLayout extends ViewGroup {
    private final int MODE_DEFAULT = 0;
    private final int MODE_RADIO = 1;
    private final int MODE_MORE_CHECK = 2;


    private int checkMode;
    private int curCheckPos = -1;

    private OnItemClickListener listener;
    /**
     * 存储所有的View
     */
    private List<List<View>> mAllViews = new ArrayList<List<View>>();
    private List<TextView> mAllTetxtViews = new ArrayList<TextView>();
    /**
     * 正常的文字颜色
     */
    private int colorNormal;
    /**
     * 选中的文字颜色
     */
    private int colorCheck;
    /**
     * 正常的背景
     */
    private Drawable bgResNormal;
    /**
     * 选中的背景
     */
    private Drawable bgResCheck;

    public void setOnItemListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public FlowCheckLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlowCheckLayout);
        colorNormal = typedArray.getColor(R.styleable.FlowCheckLayout_color_normal, ContextCompat.getColor(context,R.color.color_80000000));
        colorCheck = typedArray.getColor(R.styleable.FlowCheckLayout_color_check, ContextCompat.getColor(context,R.color.color_80000000));
        bgResNormal = typedArray.getDrawable(R.styleable.FlowCheckLayout_bg_normal) == null ? ContextCompat.getDrawable(context,R.drawable.shape_main_btm_no_click) : typedArray.getDrawable(R.styleable.FlowCheckLayout_bg_normal);
        bgResCheck = typedArray.getDrawable(R.styleable.FlowCheckLayout_bg_check) == null ? ContextCompat.getDrawable(context,R.drawable.shape_main_btm_clickble) : typedArray.getDrawable(R.styleable.FlowCheckLayout_bg_check);
        checkMode = typedArray.getInteger(R.styleable.FlowCheckLayout_check_mode, 0);
        typedArray.recycle();
    }

    public FlowCheckLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowCheckLayout(Context context) {
        this(context, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        // 如果是warp_content情况下，记录宽和高
        int width = 0;
        int height = 0;

        // 记录每一行的宽度与高度
        int lineWidth = 0;
        int lineHeight = 0;

        // 得到内部元素的个数
        int cCount = getChildCount();

        for (int i = 0; i < cCount; i++) {
            // 通过索引拿到每一个子view
            View child = getChildAt(i);
            // 测量子View的宽和高,系统提供的measureChild
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            // 得到LayoutParams
            MarginLayoutParams lp = (MarginLayoutParams) child
                    .getLayoutParams();

            // 子View占据的宽度
            int childWidth = child.getMeasuredWidth() + lp.leftMargin
                    + lp.rightMargin;
            // 子View占据的高度
            int childHeight = child.getMeasuredHeight() + lp.topMargin
                    + lp.bottomMargin;

            // 换行 判断 当前的宽度大于 开辟新行
            if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                // 对比得到最大的宽度
                width = Math.max(width, lineWidth);
                // 重置lineWidth
                lineWidth = childWidth;
                // 记录行高
                height += lineHeight;
                lineHeight = childHeight;
            } else
            // 未换行
            {
                // 叠加行宽
                lineWidth += childWidth;
                // 得到当前行最大的高度
                lineHeight = Math.max(lineHeight, childHeight);
            }
            // 特殊情况,最后一个控件
            if (i == cCount - 1) {
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }
        }
        setMeasuredDimension(
                modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingLeft() + getPaddingRight(),
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height + getPaddingTop() + getPaddingBottom()
        );

    }


    /**
     * 每一行的高度
     */
    private List<Integer> mLineHeight = new ArrayList<Integer>();

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllViews.clear();
        mLineHeight.clear();

        // 当前ViewGroup的宽度
        int width = getWidth();

        int lineWidth = 0;
        int lineHeight = 0;

        // 存放每一行的子view
        List<View> lineViews = new ArrayList<View>();

        int cCount = getChildCount();

        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child
                    .getLayoutParams();

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            // 如果需要换行
            if (childWidth + lineWidth + lp.leftMargin + lp.rightMargin > width - getPaddingLeft() - getPaddingRight()) {
                // 记录LineHeight
                mLineHeight.add(lineHeight);
                // 记录当前行的Views
                mAllViews.add(lineViews);

                // 重置我们的行宽和行高
                lineWidth = 0;
                lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
                // 重置我们的View集合
                lineViews = new ArrayList<View>();
            }
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin
                    + lp.bottomMargin);
            lineViews.add(child);

        }// for end
        // 处理最后一行
        mLineHeight.add(lineHeight);
        mAllViews.add(lineViews);

        // 设置子View的位置

        int left = getPaddingLeft();
        int top = getPaddingTop();

        // 行数
        int lineNum = mAllViews.size();

        for (int i = 0; i < lineNum; i++) {
            // 当前行的所有的View
            lineViews = mAllViews.get(i);
            lineHeight = mLineHeight.get(i);

            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);
                // 判断child的状态
                if (child.getVisibility() == View.GONE) {
                    continue;
                }

                MarginLayoutParams lp = (MarginLayoutParams) child
                        .getLayoutParams();

                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();

                // 为子View进行布局
                child.layout(lc, tc, rc, bc);

                left += child.getMeasuredWidth() + lp.leftMargin
                        + lp.rightMargin;
            }
            left = getPaddingLeft();
            top += lineHeight;
        }

    }

    /**
     * 与当前ViewGroup对应的LayoutParams
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    public void addDatas(ArrayList<String> datas) {
        if (datas != null && datas.size() == 0) {
            return;
        }
        String[] dataArr = new String[datas.size()];
        for (int i = 0; i < datas.size(); i++) {
            dataArr[i] = datas.get(i);
        }
        addDatas(dataArr);
    }

    /**
     * 添加views
     *
     * @param datas textview文字
     */
    public void addDatas(final String[] datas) {
        removeAllViews();
        curCheckPos = -1;
        mAllTetxtViews.clear();
        for (int i = 0; i < datas.length; i++) {
            TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.item_seach_record, this, false);
            mAllTetxtViews.add(tv);
            final int finalI = i;
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onItemClick(datas[finalI], finalI);
                    }
                    switch (checkMode) {
                        case MODE_DEFAULT:
                            //默认模式不做处理
                            break;
                        case MODE_RADIO:
                            //单选模式
                            setCurView(finalI);
                            break;
                        case MODE_MORE_CHECK:
                            //多选模式，未完成
                            break;
                        default:
                            break;
                    }
                }
            });
            tv.setText(datas[i]);
            addView(tv);
        }
    }

    /**
     * 单选模式下设置被选中的view
     *
     * @param pos
     */
    public void setCurView(int pos) {
        //不是单选模式。直接返回
        if (checkMode != MODE_RADIO) {
            return;
        }
        //不等
        if (pos != curCheckPos) {
            setTextCheck(pos);
            setTextNornal(curCheckPos);
            curCheckPos = pos;
        } else { //点击的就是当前

        }
    }

    private void setTextCheck(int pos) {
        TextView textView = mAllTetxtViews.get(pos);
        textView.setTextColor(colorCheck);
        ViewCompat.setBackground(textView, bgResCheck);
    }

    private void setTextNornal(int pos) {
        if (pos == -1) {
            return;
        }
        TextView textView = mAllTetxtViews.get(pos);
        textView.setTextColor(colorNormal);
        ViewCompat.setBackground(textView, bgResNormal);
    }

    public interface OnItemClickListener {
        /**
         * 点击事件
         * @param str string
         * @param pos position
         */
        void onItemClick(String str, int pos);
    }
}
