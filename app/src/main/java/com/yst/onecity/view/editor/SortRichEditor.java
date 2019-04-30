package com.yst.onecity.view.editor;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ViewDragHelper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.utils.WindowUtils;
import com.yst.onecity.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO2;
import static com.yst.onecity.Constant.NO200;

/**
 * 富文本编辑器
 * 1、支持图片文字添加、修改、删除
 * 2、支持图片文字混排
 * 3、支持文字中间随意插入多张图片
 * 4、支持图片文字任意排序
 *
 * @author  songbinbin
 * @date    2017/12/14
 * @version 3.0.1
 */
public class SortRichEditor extends ScrollView implements SEditorDataI {

    /**
     * 标题字数限制
     */
    private static final int TITLE_WORD_LIMIT_COUNT = 30;

    /**
     * 编辑器中可以插入的最多图片数量
     */
    private static final int MAX_IMAGE_COUNT = 20;

    /**
     * 默认ImageView高度
     */
    private final int DEFAULT_IMAGE_HEIGHT = dip2px(170);

    /**
     * 图文排序的时候，view默认缩小的高度
     */
    private final int SIZE_REDUCE_VIEW = dip2px(75);

    /**
     * 出发ScrollView滚动时，顶部与底部的偏移量
     */
    private final int SCROLL_OFFSET = (int)(SIZE_REDUCE_VIEW * .3);

    /**
     * 默认Marging
     */
    private final int DEFAULT_MARGING = dip2px(15);

    /**
     * 拖动排序的时候，当在ScrollView边界拖动时默认自滚动速度
     */
    private final int DEFAULT_SCROLL_SPEED = dip2px(15);

    /**
     * 虚线背景
     */
    private final GradientDrawable dashDrawable;

    /**
     * 因为排序状态下会修改EditText的Background，所以这里保存默认EditText
     * 的Background, 当排序完成后用于还原EditText默认的Background
     */
    private Drawable editTextBackground;

    /**
     * 布局填充器
     */
    private LayoutInflater inflater;

    /**
     * 每创建一个child，为该child赋一个ID，该ID保存在view的tag属性中
     */
    private int viewTagID = 1;

    /**
     * 因为ScrollView的子view只能有一个，并且是ViewGroup
     * 所以这里指定为所有子view的容器为parentLayout(LinearLayout)
     * 即：布局层次为：
     * ScrollView{
     *      parentLayout{
     *          titleLayout{
     *              EditText,
     *              TExtView
     *          },
     *
     *          LineView,
     *
     *         containerLayout{
     *              child1,
     *              child2,
     *              child3,
     *              ...
     *          }
     *      }
     * }
     */
    private LinearLayout parentLayout;

    /**
     * 用于放置各种文本图片内容的容器
     */
    private LinearLayout containerLayout;

    /**
     * EditText的软键盘监听器
     */
    private OnKeyListener editTextKeyListener;

    /**
     * 图片右上角删除按钮监听器
     */
    private OnClickListener deleteListener;

    /**
     * EditText和ImageView的焦点监听listener
     */
    private OnFocusChangeListener focusListener;

    /**
     * 最近获取焦点的一个EditText
     */
    private EditText lastFocusEdit;

    /**
     * 标题栏EditText
     */
    private SEDeletableEditText etTitle;

    /**
     * 添加或者删除图片View时的Transition动画
     */
    private LayoutTransition mTransitioner;

    /**
     * 图片加载器
     */
    private SEImageLoader imageLoader;

    /**
     * 用于实现拖动效果的帮助类
     */
    private ViewDragHelper viewDragHelper;

    /**
     * 因为文字长短不一（过长换行让EditText高度增大），导致EditText高度不一，
     * 所以需要一个集合存储排序之前未缩小/放大的EditText高度
     */
    private SparseArray<Integer> editTextHeightArray = new SparseArray<>();

    /**
     * 准备排序时，缩小各个child，并存放缩小的child的top作为该child的position值
     */
    private SparseArray<Integer> preSortPositionArray;

    /**
     * 排序完成后，子child位置下标
     */
    private SparseIntArray indexArray = new SparseIntArray();

    /**
     * 当前是否为排序状态
     */
    private boolean isSort;

    /**
     * 容器相对于屏幕顶部和底部的长度值，用于排序拖动Child的时候判定ScrollView是否滚动
     */
    private int containerTopVal, containerBottomVal;

    /**
     * 循环线程执行器，用于拖动view到边缘时ScrollView自动滚动功能
     */
    private ScheduledThreadPoolExecutor scheduledExecutorService;

    /**
     * 是否正在自动滚动
     */
    private boolean isAutoScroll;

    /**
     * 自动滚动速度向量
     */
    private int scrollVector;

    private float currRawY;

    private float preY;
    /**
     * 初始时设置hint
     */
    String hintStr;
    /**
     * hint文字颜色
     */
    int hintColor;

    /**
     * 文字大小
     */
    int sreTextSize;
    private int imgTrueWidth;
    private int imgTrueHeight;
    private Context context;

    public SortRichEditor(Context context) {
        this(context, null);
    }

    public SortRichEditor(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SortRichEditor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /**
         * 获取属性值
         */
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SortRichEditor);
        hintStr = typedArray.getString(R.styleable.SortRichEditor_sreHint);
        hintColor = typedArray.getColor(R.styleable.SortRichEditor_sreHintColor,ContextCompat.getColor(context,R.color.color_92969C));
        sreTextSize = typedArray.getInt(R.styleable.SortRichEditor_sreTextSize,R.dimen.textsize_14);
        typedArray.recycle();

        inflater = LayoutInflater.from(context);

        initListener();

        initParentLayout();

//        initTitleLayout();

//        initLineView();

        initContainerLayout();

        // 初始化虚线背景 Drawable
        dashDrawable = new GradientDrawable();
        dashDrawable.setStroke(dip2px(1), Color.parseColor("#4CA4E9"), dip2px(4), dip2px(3));
        dashDrawable.setColor(Color.parseColor("#ffffff"));

        // 初始化图片加载器
        imageLoader = SEImageLoader.getInstance(3, SEImageLoader.Type.LIFO);

        // 初始化ViewDragHelper
        viewDragHelper = ViewDragHelper.create(containerLayout, 1.5f, new ViewDragHelperCallBack());
    }

    /**
     * 创建父容器LinearLayout，指定为ScrollView的子View
     */
    private void initParentLayout() {
        // 因为ScrollView的子view只能有一个，并且是ViewGroup,所以先创建一个Linearlayout父容器，用来放置所有其他ViewGroup
        parentLayout = new LinearLayout(getContext());
        parentLayout.setOrientation(LinearLayout.VERTICAL);

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        parentLayout.setLayoutParams(layoutParams);

        addView(parentLayout);
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        // 初始化键盘退格监听
        // 主要用来处理点击回删按钮时，view的一些列合并操作
        editTextKeyListener = new OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                    EditText edit = (EditText) v;
                    onBackspacePress(edit);
                }
                return false;
            }
        };

        // 图片删除处理
        deleteListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                RelativeLayout parentView = (RelativeLayout) v.getParent();
                onImageDeleteClick(parentView);
            }
        };

        //焦点监听
        focusListener = new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // 图片
                if (v instanceof RelativeLayout) {
                    processSoftKeyBoard(false);
                } else if (v instanceof EditText) {
                    if (hasFocus) {
                        lastFocusEdit = (EditText) v;
                    }else {
                        //如果整个编辑器为空那么第一行的的editText失去焦点时不变为预留图标（因有默认提示性文字）
                        if(isContentEmpty()){
                            //设置hint
                            lastFocusEdit.setHint(hintStr);
                            return;
                        }
                        //当一个edittext失去焦点时如果其中无内容则创建为留用插入文字的小图片
                        if(((EditText)v).getText().toString().trim().length() <= 0){
                            int index = containerLayout.indexOfChild(v);
                            containerLayout.removeViewAt(index);
                            insertEditTextImageView(index);
                        }
                    }
                }
            }
        };
    }

    /**
     * 初始化ContainerLayout文本内容容器
     */
    private void initContainerLayout() {
        containerLayout = createContaniner();
        parentLayout.addView(containerLayout);

        EditText firstEdit = createEditText(hintStr);

        editTextHeightArray.put(Integer.parseInt(firstEdit.getTag().toString()), ViewGroup.LayoutParams.WRAP_CONTENT);
        editTextBackground = firstEdit.getBackground();
        containerLayout.addView(firstEdit);
        lastFocusEdit = firstEdit;
    }

    /**
     * 停止ScrollView的自动滚动
     */
    private void stopOverEdgeAutoScroll() {
        if (isAutoScroll) {
            scheduledExecutorService.shutdownNow();
            isAutoScroll = false;
        }
    }

    /**
     * 拖动view到或者超出边缘时，ScrollView开始自动滚动
     */
    private void startOverEdgeAutoScroll() {
        if (!isAutoScroll) {
            scheduledExecutorService = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
                @Override
                public Thread newThread(@NonNull Runnable r) {
                    return null;
                }
            });
            scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    SortRichEditor.this.scrollBy(0, scrollVector);
                }
            }, 0, 15, TimeUnit.MILLISECONDS);
            isAutoScroll = true;
        }
    }

    /**
     * 创建图片文本内容容器
     * @return
     */
        @NonNull
        private LinearLayout createContaniner() {
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        final LinearLayout containerLayout = new LinearLayout(getContext()) {
            @Override
            public boolean onInterceptTouchEvent(MotionEvent ev) {
                return viewDragHelper.shouldInterceptTouchEvent(ev);
            }

            @Override
            public boolean onTouchEvent(MotionEvent event) {
                viewDragHelper.processTouchEvent(event);
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (isSort) {
                            currRawY = event.getRawY();
                            // 内容上滚动
                            if (currRawY > containerBottomVal) {
                                scrollVector = DEFAULT_SCROLL_SPEED;
                                startOverEdgeAutoScroll();

                            // 内容下滚动
                            } else if (currRawY < containerTopVal) {
                                scrollVector = -DEFAULT_SCROLL_SPEED;
                                startOverEdgeAutoScroll();
                            }else {
                                stopOverEdgeAutoScroll();
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        stopOverEdgeAutoScroll();
                        break;
                    default:
                        break;
                }
                return true;
            }

            @Override
            public boolean dispatchTouchEvent(MotionEvent ev) {
                if (isSort) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                return super.dispatchTouchEvent(ev);
            }


        };
        containerLayout.setPadding(0, DEFAULT_MARGING, 0, DEFAULT_MARGING);
        containerLayout.setOrientation(LinearLayout.VERTICAL);
        containerLayout.setBackgroundColor(Color.WHITE);
        containerLayout.setLayoutParams(layoutParams);
        setupLayoutTransitions(containerLayout);
        return containerLayout;
    }

    /**
     * 获取排序之前子View的LayoutParams用于还原子View大小
     * @param child
     * @return
     */
    private ViewGroup.LayoutParams resetChildLayoutParams(View child) {
        ViewGroup.LayoutParams layoutParams = child.getLayoutParams();
        // 图片
        if (child instanceof RelativeLayout) {
            layoutParams.height = DEFAULT_IMAGE_HEIGHT;
        }
        // 文本编辑框
        if (child instanceof EditText) {
            child.setFocusable(true);
            child.setFocusableInTouchMode(true);
            if (child == lastFocusEdit) {
                child.requestFocus();
            }
            child.setBackground(editTextBackground);
            layoutParams.height = editTextHeightArray.get(Integer.parseInt(child.getTag().toString()));
        }
        return layoutParams;
    }

    public boolean sort() {
        isSort = !isSort;
        containerLayout.setLayoutTransition(null);
        if (isSort) {
            prepareSortUI();
            prepareSortConfig();
            processSoftKeyBoard(false);
        } else {
            endSortUI();
        }
        // 恢复transition动画
        containerLayout.setLayoutTransition(mTransitioner);
        return isSort;
    }

    public boolean isSort() {
        return isSort;
    }

    /**
     * 开始图文排序
     * 图片与文字段落高度缩小为默认高度{@link #SIZE_REDUCE_VIEW}
     * 且图片与文字可以上下拖动
     */
    private void prepareSortUI() {
        int childCount = containerLayout.getChildCount();

        if (childCount != 0) {
            if (preSortPositionArray == null) {
                preSortPositionArray = new SparseArray<>();
            } else {
                preSortPositionArray.clear();
            }
        }

        List<View> removeChildList = new ArrayList<>();

        View child;
        int pos, preIndex = 0;
        for (int i = 0; i < childCount; i++) {
            child = containerLayout.getChildAt(i);

            if (child instanceof ImageView) {
                removeChildList.add(child);
                continue;
            }

            if (child instanceof RelativeLayout) {
                ((RelativeLayout) child).getChildAt(1).setVisibility(View.GONE);
                setFocusOnView(child, false);
            }

            int tagID = Integer.parseInt(child.getTag().toString());
            ViewGroup.LayoutParams layoutParams = child.getLayoutParams();
            // 文本编辑框
            if (child instanceof EditText) {
                EditText editText = ((EditText) child);
                editTextHeightArray.put(tagID, layoutParams.height);
                editText.setFocusable(false);
                editText.setBackground(dashDrawable);
            }

            layoutParams.height = SIZE_REDUCE_VIEW;
            child.setLayoutParams(layoutParams);
            if (i == 0) {
                preIndex = tagID;
                pos = DEFAULT_MARGING;
            } else {
                pos = SIZE_REDUCE_VIEW + DEFAULT_MARGING + preSortPositionArray.get(preIndex);

                preIndex = tagID;
            }
            preSortPositionArray.put(tagID, pos);
        }

        // 移除所有的“可编辑文本”图标
        if (!removeChildList.isEmpty()) {
            for (View removeChild : removeChildList) {
                containerLayout.removeView(removeChild);
            }
        }
    }

    /**
     * 结束图文排序，图片还原为默认高度{@link #DEFAULT_IMAGE_HEIGHT}，文字还原为原本高度
     * （其文字排序前的高度值保存在{@link #editTextHeightArray}中）
     * 且图片文字不再可以上下拖动
     */
    private void endSortUI() {
        int childCount = containerLayout.getChildCount();
        View child;
        // 重新排列过
        if (indexArray.size() == childCount) {
            int sortIndex;
            View[] childArray = new View[childCount];
            // 1、先按重新排列的顺序调整子View的位置，放入数组childArray中
            for (int i = 0; i < childCount; i++) {
                if (indexArray.size() != childCount) {
                    break;
                }
                // 代表原先在i的位置上的view，换到了sortIndex位置上
                sortIndex = indexArray.get(i);
                child = containerLayout.getChildAt(i);
                childArray[sortIndex] = child;
            }

            //2、依据顺序已排列好的childArray，插入一个“将来用于编辑文字的图片”
            List<View> sortViewList = new ArrayList<>();
            View preChild = childArray[0];
            sortViewList.add(preChild);
            for (int i = 1; i < childCount; i++) {
                child = childArray[i];
                if (preChild instanceof RelativeLayout && child instanceof RelativeLayout) {
                    ImageView ivInsertEditText = createInsertEditTextImageView();
                    sortViewList.add(ivInsertEditText);
                }
                sortViewList.add(child);
                preChild = child;
            }

            // 3、依据顺序已排好并且“用于编辑文字的图片”也插入完毕的sortViewList，依次往containerLayout中添加子View
            containerLayout.removeAllViews();
            for (View sortChild : sortViewList) {
                if (sortChild instanceof RelativeLayout) {
                    ((RelativeLayout) sortChild).getChildAt(1).setVisibility(View.VISIBLE);
                    setFocusOnView(sortChild, true);
                }
                sortChild.setLayoutParams(resetChildLayoutParams(sortChild));
                containerLayout.addView(sortChild);
            }

        } else { // 没有重新排列
            View preChild = containerLayout.getChildAt(childCount - 1);
            preChild.setLayoutParams(resetChildLayoutParams(preChild));
            for (int i = childCount - NO2; i >= NO0; i--) {
                child = containerLayout.getChildAt(i);
                if (child instanceof RelativeLayout) {
                    ((RelativeLayout) child).getChildAt(1).setVisibility(View.VISIBLE);
                    setFocusOnView(child, true);
                }
                // 紧邻的两个View都是ImageView
                if (preChild instanceof RelativeLayout && child instanceof RelativeLayout) {
                    insertEditTextImageView(i + 1);
                }
                child.setLayoutParams(resetChildLayoutParams(child));
                preChild = child;
            }
        }

        // 如果最后一个View不是EditText,那么再添加一个EditText
        int lastIndex = containerLayout.getChildCount() - 1;
        View view = containerLayout.getChildAt(lastIndex);
        if (!(view instanceof EditText)) {
            insertEditTextAtIndex(lastIndex + 1, "");
        }
    }

    /**
     * 处理软键盘backSpace回退事件
     *
     * @param editTxt 光标所在的文本输入框
     */
    private void onBackspacePress(EditText editTxt) {

        int startSelection = editTxt.getSelectionStart();
        // 只有在光标已经顶到文本输入框的最前方，在判定是否删除之前的图片，或两个View合并
        if (startSelection == 0) {
            int editIndex = containerLayout.indexOfChild(editTxt);
            // 如果editIndex-1<0,
            View preView = containerLayout.getChildAt(editIndex - 1);
            // 则返回的是null
            if (null != preView) {
                if (preView instanceof RelativeLayout || preView instanceof ImageView) {
                    // 光标EditText的上一个view对应的是图片或者是一个“将来可编辑文本”的图标
                    onImageDeleteClick(preView);
                } else if (preView instanceof EditText) {
                    // 光标EditText的上一个view对应的还是文本框EditText
                    String str1 = editTxt.getText().toString();
                    EditText preEdit = (EditText) preView;
                    String str2 = preEdit.getText().toString();

                    // 合并文本view时，不需要transition动画
                    containerLayout.setLayoutTransition(null);
                    containerLayout.removeView(editTxt);
                    // 恢复transition动画
                    containerLayout.setLayoutTransition(mTransitioner);

                    // 文本合并
                    preEdit.setText(str2 + str1);
                    preEdit.requestFocus();
                    preEdit.setSelection(str2.length(), str2.length());
                    lastFocusEdit = preEdit;
                }
            }
        }
    }

    /**
     * 处理图片删除击事件
     *
     * @param view 整个image对应的relativeLayout view
     */
    private void onImageDeleteClick(View view) {
        if (!mTransitioner.isRunning()) {
            int index = containerLayout.indexOfChild(view);
            int nextIndex = index + 1;
            int lastIndex = index - 1;

            View child;
            // 删除图片位于第一个位置，只检查下一个位置的View是否为“可编辑文本”的图标
            if (index == 0) {
                child = containerLayout.getChildAt(nextIndex);
            } else {
                // 先检查上一个位置的View是否为“可编辑文本”的图标，如果不是就检查下一个位置的View
                child = containerLayout.getChildAt(lastIndex);
                if (!(child instanceof ImageView)) {
                    child = containerLayout.getChildAt(nextIndex);
                }
            }

            if (child instanceof ImageView) {
                // 如果该View是“可编辑文本”的图标，则一并删除
                containerLayout.removeView(child);
            }

            containerLayout.removeView(view);
        }
    }

    /**
     *
     * 生成一个“将来用于编辑文字的图片”ImageView
     * @return ImageView
     */
    @NonNull
    private ImageView createInsertEditTextImageView() {
        LinearLayout.LayoutParams para = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final ImageView ivInsertEditText = new ImageView(getContext());
        ivInsertEditText.setLayoutParams(para);
        ivInsertEditText.setTag(viewTagID++);
        ivInsertEditText.setImageResource(R.mipmap.icon_add_text);
        ivInsertEditText.setImageAlpha(0);
        ivInsertEditText.setScaleType(ImageView.ScaleType.FIT_START);
        ivInsertEditText.setClickable(true);
        ivInsertEditText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = containerLayout.indexOfChild(ivInsertEditText);
                containerLayout.removeView(ivInsertEditText);
                EditText editText = insertEditTextAtIndex(index, "");
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                editText.requestFocus();
                lastFocusEdit = editText;
                processSoftKeyBoard(true);
            }
        });

        // 调整ImageView的外边距
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.bottomMargin = DEFAULT_MARGING;
        ivInsertEditText.setLayoutParams(lp);

        return ivInsertEditText;
    }

    /**
     * 生成文本输入框
     */
    private EditText createEditText(String hint) {
        EditText editText = new SEDeletableEditText(getContext());
        editText.setTag(viewTagID++);
        editText.setHint(hint);
        editText.setGravity(Gravity.TOP);
        editText.setCursorVisible(true);
        editText.setBackgroundResource(android.R.color.transparent);
        editText.setTextColor(ContextCompat.getColor(getContext(),R.color.release_list_bg_color));
        editText.setHintTextColor(hintColor);
        editText.setTextSize(sreTextSize);
        editText.setOnKeyListener(editTextKeyListener);
        editText.setOnFocusChangeListener(focusListener);

        // 调整EditText的外边距
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.bottomMargin = DEFAULT_MARGING;
        lp.leftMargin = DEFAULT_MARGING;
        lp.rightMargin= DEFAULT_MARGING;
        editText.setLayoutParams(lp);

        return editText;
    }

    /**
     * 生成图片Layout
     */
    private RelativeLayout createImageLayout() {
        //动态设置高度
        int h = (int)((imgTrueHeight*1.00) / ((imgTrueWidth * 1.00) / (WindowUtils.getScreenWidth((Activity) context) * 1.00)));

        RelativeLayout.LayoutParams contentImageLp = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        contentImageLp.width = WindowUtils.getScreenWidth((Activity) context);
        contentImageLp.height = h;
        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(contentImageLp);
        imageView.setImageResource(R.mipmap.loading);

        RelativeLayout.LayoutParams closeImageLp = new RelativeLayout.LayoutParams(
                dip2px(20), dip2px(20));
        closeImageLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        closeImageLp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        closeImageLp.setMargins(0, dip2px(10), dip2px(10), 0);
        ImageView closeImage = new ImageView(getContext());
        closeImage.setScaleType(ImageView.ScaleType.FIT_XY);
        closeImage.setImageResource(R.mipmap.icon_delete);
        closeImage.setLayoutParams(closeImageLp);

        RelativeLayout layout = new RelativeLayout(getContext());
        layout.addView(imageView);
        layout.addView(closeImage);
        layout.setTag(viewTagID++);
        setFocusOnView(layout, true);

        closeImage.setTag(layout.getTag());
        closeImage.setOnClickListener(deleteListener);

        // 调整imageView的外边距
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, h);
        lp.bottomMargin = DEFAULT_MARGING;
        lp.leftMargin = DEFAULT_MARGING;
        lp.rightMargin= DEFAULT_MARGING;
        layout.setLayoutParams(lp);

        return layout;
    }

    /**
     * 生成图片Layout当图片为商品时用次布局
     */
    private RelativeLayout createImageLayoutIsGoods() {
        RelativeLayout.LayoutParams contentImageLp = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.x180));
        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(contentImageLp);
        imageView.setImageResource(R.mipmap.icon_empty_photo);

        RelativeLayout.LayoutParams closeImageLp = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        closeImageLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        closeImageLp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        closeImageLp.setMargins(0, dip2px(10), dip2px(10), 0);
        ImageView closeImage = new ImageView(getContext());
        closeImage.setScaleType(ImageView.ScaleType.FIT_XY);
        closeImage.setImageResource(R.mipmap.icon_delete);
        closeImage.setLayoutParams(closeImageLp);

        //如果是商品图片则中间多一个商品标记图片
        RelativeLayout.LayoutParams goodsTagImgLp = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        goodsTagImgLp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        goodsTagImgLp.addRule(RelativeLayout.CENTER_VERTICAL);
        goodsTagImgLp.setMargins(0, dip2px(10), dip2px(10), 0);
        ImageView goodsTagImg = new ImageView(getContext());
        goodsTagImg.setScaleType(ImageView.ScaleType.FIT_XY);
        goodsTagImg.setImageResource(R.mipmap.play);
        goodsTagImg.setLayoutParams(goodsTagImgLp);

        RelativeLayout layout = new RelativeLayout(getContext());
        layout.addView(imageView);
        layout.addView(closeImage);
        layout.addView(goodsTagImg);
        layout.setTag(viewTagID++);
        setFocusOnView(layout, true);

        closeImage.setTag(layout.getTag());
        closeImage.setOnClickListener(deleteListener);

        // 调整imageView的外边距
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, DEFAULT_IMAGE_HEIGHT);
        lp.bottomMargin = DEFAULT_MARGING;
        lp.leftMargin = DEFAULT_MARGING;
        lp.rightMargin= DEFAULT_MARGING;
        layout.setLayoutParams(lp);

        return layout;
    }

    private void setFocusOnView(View view, boolean isFocusable) {
        view.setClickable(isFocusable);
        view.setFocusable(isFocusable);
        view.setFocusableInTouchMode(isFocusable);
        if (isFocusable) {
            view.setOnFocusChangeListener(focusListener);
        } else {
            view.setOnFocusChangeListener(null);
        }
    }

    /**
     * 插入图片前的预处理
     */
    private void prepareAddImage() {
        View firstView = containerLayout.getChildAt(0);
        if (containerLayout.getChildCount() == 1 && firstView == lastFocusEdit) {
            lastFocusEdit = (EditText) firstView;
            lastFocusEdit.setHint("");
        }
    }

    /**
     * 插入一张图片
     */
    private void insertImage(String imagePath) {
        String lastEditStr = lastFocusEdit.getText().toString();
        int cursorIndex = lastFocusEdit.getSelectionStart();
        String lastStr = lastEditStr.substring(0, cursorIndex).trim();
        int lastEditIndex = containerLayout.indexOfChild(lastFocusEdit);

        if (lastEditStr.length() == 0 || lastStr.length() == 0) {
            // 如果EditText为空，或者光标已经顶在了editText的最前面，则直接插入图片，并且EditText下移即可
            insertImageViewAtIndex(lastEditIndex, imagePath, false);
            lastFocusEdit = insertEditTextAtIndex(lastEditIndex + 1, "");
            lastFocusEdit.requestFocus();
            lastFocusEdit.setSelection(0);
        } else {
            // 如果EditText非空且光标不在最顶端，则需要添加新的imageView和EditText
            lastFocusEdit.setText(lastStr);
            String editStr2 = lastEditStr.substring(cursorIndex).trim();
            if (containerLayout.getChildCount() - 1 == lastEditIndex || editStr2.length() > 0) {
                lastFocusEdit = insertEditTextAtIndex(lastEditIndex + 1, editStr2);
                lastFocusEdit.requestFocus();
                lastFocusEdit.setSelection(0);
            }
            insertImageViewAtIndex(lastEditIndex + 1, imagePath, false);
        }
        processSoftKeyBoard(false);
    }
    /**
     * 插入一张图片(选择商品-来源网络）
     */
    private void insertNetImage(String nativeImagePath, String imagePath, boolean isGoods, Context context, boolean isSet) {
        String lastEditStr = lastFocusEdit.getText().toString();
        int cursorIndex = lastFocusEdit.getSelectionStart();
        String lastStr = lastEditStr.substring(0, cursorIndex).trim();
        int lastEditIndex = containerLayout.indexOfChild(lastFocusEdit);
        if(isSet){
            if (lastEditStr.length() == 0 || lastStr.length() == 0) {
                if(lastEditIndex == 0){
                    insertEditTextImageView(lastEditIndex);
                    insertNetImageViewAtIndex(nativeImagePath,lastEditIndex+1, imagePath, isGoods, context, isSet);
                }else {
                    // 如果EditText为空，或者光标已经顶在了editText的最前面，则直接插入图片，并且EditText下移即可
                    insertNetImageViewAtIndex(nativeImagePath,lastEditIndex, imagePath, isGoods, context, isSet);
                }
            } else {
                insertNetImageViewAtIndex(nativeImagePath,lastEditIndex + 1, imagePath, isGoods, context, isSet);
            }
        }else {
            if (lastEditStr.length() == 0 || lastStr.length() == 0) {
                if(lastEditIndex == 0){
                    insertEditTextImageView(lastEditIndex);
                    insertNetImageViewAtIndex(nativeImagePath,lastEditIndex+1, imagePath, isGoods, context, isSet);
                }else {
                    // 如果EditText为空，或者光标已经顶在了editText的最前面，则直接插入图片，并且EditText下移即可
                    insertNetImageViewAtIndex(nativeImagePath,lastEditIndex, imagePath, isGoods, context, isSet);
                }
            } else {
                // 如果EditText非空且光标不在最顶端，则需要添加新的imageView和EditText
                lastFocusEdit.setText(lastStr);
                String editStr2 = lastEditStr.substring(cursorIndex).trim();
                if (containerLayout.getChildCount() - 1 == lastEditIndex || editStr2.length() > 0) {
                    lastFocusEdit = insertEditTextAtIndex(lastEditIndex + 1, editStr2);
                    lastFocusEdit.requestFocus();
                    lastFocusEdit.setSelection(editStr2.length());
                }
                insertNetImageViewAtIndex(nativeImagePath,lastEditIndex + 1, imagePath, isGoods, context, isSet);
            }
        }
        processSoftKeyBoard(false);
    }

    /**
     * 插入一张图片(选择商品-来源网络）
     */
    private void insertNetImage(String imagePath, boolean isGoods, Context context, boolean isSet) {
        String lastEditStr = lastFocusEdit.getText().toString();
        int cursorIndex = lastFocusEdit.getSelectionStart();
        String lastStr = lastEditStr.substring(0, cursorIndex).trim();
        int lastEditIndex = containerLayout.indexOfChild(lastFocusEdit);
        if(isSet){
            if (lastEditStr.length() == 0 || lastStr.length() == 0) {
                if(lastEditIndex == 0){
                    insertEditTextImageView(lastEditIndex);
                    insertNetImageViewAtIndex(lastEditIndex+1, imagePath, isGoods, context, isSet);
                }else {
                    // 如果EditText为空，或者光标已经顶在了editText的最前面，则直接插入图片，并且EditText下移即可
                    insertNetImageViewAtIndex(lastEditIndex, imagePath, isGoods, context, isSet);
                }
            } else {
                insertNetImageViewAtIndex(lastEditIndex + 1, imagePath, isGoods, context, isSet);
            }
        }else {
            if (lastEditStr.length() == 0 || lastStr.length() == 0) {
                if(lastEditIndex == 0){
                    insertEditTextImageView(lastEditIndex);
                    insertNetImageViewAtIndex(lastEditIndex+1, imagePath, isGoods, context, isSet);
                }else {
                    // 如果EditText为空，或者光标已经顶在了editText的最前面，则直接插入图片，并且EditText下移即可
                    insertNetImageViewAtIndex(lastEditIndex, imagePath, isGoods, context, isSet);
                }
            } else {
                // 如果EditText非空且光标不在最顶端，则需要添加新的imageView和EditText
                lastFocusEdit.setText(lastStr);
                String editStr2 = lastEditStr.substring(cursorIndex).trim();
                if (containerLayout.getChildCount() - 1 == lastEditIndex || editStr2.length() > 0) {
                    lastFocusEdit = insertEditTextAtIndex(lastEditIndex + 1, editStr2);
                    lastFocusEdit.requestFocus();
                    lastFocusEdit.setSelection(editStr2.length());
                }
                insertNetImageViewAtIndex(lastEditIndex + 1, imagePath, isGoods, context, isSet);
            }
        }
        processSoftKeyBoard(false);
    }

    /**
     * 批量插入一组图片
     * @param imagePathList
     */
    private void insertBatchImage(List<String> imagePathList) {
        String imagePath;
        for (int i = 0; i < imagePathList.size(); i++) {
            imagePath = imagePathList.get(i);
            insertImageViewAtIndex(containerLayout.indexOfChild(lastFocusEdit), imagePath, true);
        }
    }

    /**
     * 隐藏或者显示软键盘
     * @param isShow true:显示，false:隐藏
     */
    public void processSoftKeyBoard(boolean isShow) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (isShow) {
            imm.showSoftInput(lastFocusEdit, InputMethodManager.SHOW_FORCED);
        } else {
            imm.hideSoftInputFromWindow(lastFocusEdit.getWindowToken(), 0);
        }
    }

    /**
     * 在指定位置添加一个“将来用于编辑文字的图片”
     * @param index
     */
    private void insertEditTextImageView(int index) {
        ImageView ivInsertEditText = createInsertEditTextImageView();
        containerLayout.addView(ivInsertEditText, index);
    }

    /**
     * 在指定位置插入EditText
     *
     * @param index   位置
     * @param editStr EditText显示的文字
     */
    private EditText insertEditTextAtIndex(final int index, String editStr) {
        EditText editText = createEditText("");
        editText.setText(editStr);

        // 请注意此处，EditText添加、或删除不触动Transition动画
        containerLayout.setLayoutTransition(null);
        containerLayout.addView(editText, index);
        // add之后恢复transition动画
        containerLayout.setLayoutTransition(mTransitioner);
        return editText;
    }

    /**
     * 在指定位置添加ImageView
     */
    private void insertImageViewAtIndex(int index, String imagePath, boolean isBatch) {
        if (index > 0) {
            View currChild = containerLayout.getChildAt(index);
            // 当前index位置的child是ImageView，则在插入本ImageView的时候，多插入一个图标，用于将来可以插入EditText
            if (currChild instanceof RelativeLayout) {
                insertEditTextImageView(index);
            }

            int lastIndex = index - 1;
            View child = containerLayout.getChildAt(lastIndex);
            // index位置的上一个child是ImageView，则在插入本ImageView的时候，多插入一个图标，用于将来可以插入EditText
            if (child instanceof RelativeLayout) {
                insertEditTextImageView(index++);
            }
        }

        final RelativeLayout imageLayout = createImageLayout();
        ImageView imageView = (ImageView) imageLayout.getChildAt(0);
        PointF pointF = new PointF();
        pointF.x = getWidth() - 2 * DEFAULT_MARGING;
        pointF.y = DEFAULT_IMAGE_HEIGHT;
        imageLoader.loadImage(imagePath, imageView, pointF);
        imageView.setTag(imagePath);

        final int finalIndex = index;
        if (isBatch) {
            // 批量插入需要立即执行，否则index错误（舍弃动画效果）
            containerLayout.addView(imageLayout, finalIndex);
        } else {
            // 单张插入onActivityResult无法触发动画，此处post处理
            containerLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    containerLayout.addView(imageLayout, finalIndex);
                }
            }, 200);
        }
    }

    /**
     * 在指定位置添加ImageView(Net)
     */
    private void insertNetImageViewAtIndex(int index, String imagePath, boolean isGoods , Context context, boolean isSet) {
        if (index > 0) {
            View currChild = containerLayout.getChildAt(index);
            // 当前index位置的child是ImageView，则在插入本ImageView的时候，多插入一个图标，用于将来可以插入EditText
            if (currChild instanceof RelativeLayout) {
                insertEditTextImageView(index);
            }

            int lastIndex = index - 1;
            View child = containerLayout.getChildAt(lastIndex);
            // index位置的上一个child是ImageView，则在插入本ImageView的时候，多插入一个图标，用于将来可以插入EditText
            if (child instanceof RelativeLayout) {
                insertEditTextImageView(index++);
            }
        }

        final RelativeLayout imageLayout;
        /**
         * 如果是商品则创建一个带商品标记的ImageView
         */
        imageLayout = isGoods ? createImageLayoutIsGoods() : createImageLayout();
        ImageView imageView = (ImageView) imageLayout.getChildAt(0);

        //加载图片的时候通过强制bitmap类型获取到原图的宽高像素值
        imageLoader.loadNetImage(imagePath, imageView, context);


        getImgInfoAndSetTag(imageView, imagePath, isSet, isGoods);


        final int finalIndex = index;

        containerLayout.addView(imageLayout, finalIndex);
        //如果是设置数据时候执行
        if(isSet) {
            int childCount = containerLayout.getChildCount();
            lastFocusEdit = insertEditTextAtIndex(childCount, "");
        }
        lastFocusEdit.requestFocus();
        String s = lastFocusEdit.getText().toString().trim();
        lastFocusEdit.setSelection(s.length());
    }
    /**
     * 在指定位置添加ImageView(Net)
     */
    private void insertNetImageViewAtIndex(String nativeImagePath,int index, String imagePath, boolean isGoods , Context context, boolean isSet) {
        if (index > 0) {
            View currChild = containerLayout.getChildAt(index);
            // 当前index位置的child是ImageView，则在插入本ImageView的时候，多插入一个图标，用于将来可以插入EditText
            if (currChild instanceof RelativeLayout) {
                insertEditTextImageView(index);
            }

            int lastIndex = index - 1;
            View child = containerLayout.getChildAt(lastIndex);
            // index位置的上一个child是ImageView，则在插入本ImageView的时候，多插入一个图标，用于将来可以插入EditText
            if (child instanceof RelativeLayout) {
                insertEditTextImageView(index++);
            }
        }

        final RelativeLayout imageLayout;
        /**
         * 如果是商品则创建一个带商品标记的ImageView
         */
        imageLayout = isGoods ? createImageLayoutIsGoods() : createImageLayout();
        ImageView imageView = (ImageView) imageLayout.getChildAt(0);

        //加载图片的时候通过强制bitmap类型获取到原图的宽高像素值
        imageLoader.loadNetImage(nativeImagePath,imagePath, imageView, context);


        getImgInfoAndSetTag(imageView, imagePath, isSet, isGoods);


        final int finalIndex = index;

        containerLayout.addView(imageLayout, finalIndex);
        //如果是设置数据时候执行
        if(isSet) {
            int childCount = containerLayout.getChildCount();
            lastFocusEdit = insertEditTextAtIndex(childCount, "");
        }
        lastFocusEdit.requestFocus();
        String s = lastFocusEdit.getText().toString().trim();
        lastFocusEdit.setSelection(s.length());
    }

    /**
     * 通过只加载边框信息（不将图片加载到内存）获取图片的宽高并拼接到url后面，设置imageView 的 Tag
     *
     * @param imageView 图片控件
     * @param imagePath 图片URL
     * @param isSet 是否是编辑跳转 动态设置的
     * @param isGoods 是否是商品
     */
    private void getImgInfoAndSetTag(final ImageView imageView, final String imagePath, final boolean isSet, final boolean isGoods){
        class LoggerThreadFactory implements ThreadFactory {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {

                    }
                });
                return t;
            }
        }
        ScheduledThreadPoolExecutor scheduledExecutorService = new ScheduledThreadPoolExecutor(1,new LoggerThreadFactory());
        //Looper Three
        scheduledExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conn = null;
                try {
                    // 创建URL对象
                    URL myUri = new URL(imagePath);
                    // 创建链接
                    conn = (HttpURLConnection) myUri.openConnection();
                    // 设置链接超时
                    conn.setConnectTimeout(10000);
                    conn.setReadTimeout(5000);
                    // 设置请求方法为get
                    conn.setRequestMethod("GET");
                    conn.connect();// 开始连接
                    int responseCode = conn.getResponseCode();
                    if (responseCode == NO200) {
                        InputStream is = conn.getInputStream();
                        /**
                         * 用options 只读取图片的信息头得到图片宽高而不将图片加载到内存中
                         */
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        /**
                         * 最关键在此，把options.inJustDecodeBounds = true;
                         * 这里再decodeFile()，返回的bitmap为空，但此时调用options.outHeight时，已经包含了图片的高了
                         */
                        options.inJustDecodeBounds = true;
                        // 此时返回的bitmap为null
                        BitmapFactory.decodeFile(imagePath, options);
                        BitmapFactory.decodeStream(is, null, options);
                        /**
                         *options.outHeight为原始图片的高
                         */
                        String path2 = imagePath;
                        //将发布内容中图片的url后面拼接上图片的宽高（非商品图）
                        if (!isGoods && !isSet) {
                            path2 = imagePath + "?width=" + options.outWidth + "&height=" + options.outHeight;
                        }
                        //设置tag生成数据时用tag获取url存data
                        imageView.setTag(path2);
                    } else {
                        Log.i("获取图片信息:", "访问失败：responseCode=" + responseCode);
                        ToastUtils.show("获取图片信息:网络请求超时");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }
                }
            }
        });
    }

    /**
     * 初始化transition动画
     */
    private void setupLayoutTransitions(LinearLayout containerLayout) {
        mTransitioner = new LayoutTransition();
        containerLayout.setLayoutTransition(mTransitioner);
        mTransitioner.setDuration(300);
    }

    /**
     * dp和pixel转换
     *
     * @param dipValue dp值
     * @return 像素值
     */
    private int dip2px(float dipValue) {
        float m = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * m + 0.5f);
    }

    private void prepareSortConfig() {
        indexArray.clear();

        int[] position = new int[2];
        SortRichEditor.this.getLocationOnScreen(position);

        SortRichEditor sortRichEditor = SortRichEditor.this;
        containerTopVal = position[1] + sortRichEditor.getPaddingTop() + SCROLL_OFFSET;
        containerBottomVal = containerTopVal + sortRichEditor.getHeight() - sortRichEditor.getPaddingBottom() - SCROLL_OFFSET;

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (viewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                preY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(ev.getY() - preY) >= viewDragHelper.getTouchSlop()) {
                    processSoftKeyBoard(false);
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 重新排列Child的位置，更新{@link #indexArray} 中view的下标顺序
     */
    private void resetChildPostion() {
        indexArray.clear();
        View child;
        int tagID, sortIndex;
        int childCount = containerLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            child = containerLayout.getChildAt(i);
            tagID = Integer.parseInt(child.getTag().toString());
            sortIndex = (preSortPositionArray.get(tagID) - DEFAULT_MARGING) / (SIZE_REDUCE_VIEW + DEFAULT_MARGING);
            indexArray.put(i, sortIndex);
        }
    }

    /**
     * 根据绝对路径添加一张图片
     *
     * @param imagePath
     */
    @Override
    public void addImage(String imagePath) {
        prepareAddImage();
        insertImage(imagePath);
    }

    /**
     * 根据Url插入一张网络图片
     *
     * @param url   图片地址
     * @param isGoods   是否是商品
     * @param context
     * @param isSet     是否是动态设置值
     */
    @Override
    public void addNetImage(String url, boolean isGoods, Context context, boolean isSet) {
        prepareAddImage();
        insertNetImage(url,isGoods,context,isSet);
    }
    /**
     * 根据Url插入一张网络图片
     *
     * @param url   图片地址
     * @param isGoods   是否是商品
     * @param context
     * @param isSet     是否是动态设置值
     */
    public void addNetImage(String url, boolean isGoods, Context context, boolean isSet, int width, int height) {
        this.context = context;
        imgTrueWidth = width;
        imgTrueHeight = height;
        prepareAddImage();
        insertNetImage(url,isGoods,context,isSet);
    }
    /**
     * 根据Url插入一张网络图片
     *
     * @param url   图片地址
     * @param isGoods   是否是商品
     * @param context
     * @param isSet     是否是动态设置值
     */
    @Override
    public void addNetImage(String imagePaht, String url, boolean isGoods, Context context, boolean isSet) {
        prepareAddImage();
        insertNetImage(imagePaht,url,isGoods,context,isSet);
    }
    /**
     * 根据图片绝对路径数组批量添加一组图片
     * @param imagePaths
     */
    @Override
    public void addImageArray(String[] imagePaths) {
        if (imagePaths == null) {
            return;
        }
        addImageList(Arrays.asList(imagePaths));
    }

    /**
     * 根据图片绝对路径集合批量添加一组图片
     * @param imageList
     */
    @Override
    public void addImageList(List<String> imageList) {
        if (imageList == null || imageList.isEmpty()) {
            return;
        }
        prepareAddImage();

        String lastEditStr = lastFocusEdit.getText().toString();
        int cursorIndex = lastFocusEdit.getSelectionStart();
        String lastStr = lastEditStr.substring(0, cursorIndex).trim();
        int lastEditIndex = containerLayout.indexOfChild(lastFocusEdit);

        if (lastEditStr.length() == 0 || lastStr.length() == 0) {
            // 如果EditText为空，或者光标已经顶在了editText的最前面，则直接插入图片
            insertBatchImage(imageList);
        } else {
            // 如果EditText非空且光标不在最顶端，则添加该组图片后，最后插入一个EditText
            lastFocusEdit.setText(lastStr);
            String editStr2 = lastEditStr.substring(cursorIndex).trim();
            if (containerLayout.getChildCount() - 1 == lastEditIndex || editStr2.length() > 0) {
                lastFocusEdit = insertEditTextAtIndex(lastEditIndex + 1, editStr2);
                lastFocusEdit.requestFocus();
                lastFocusEdit.setSelection(0);
            }
            insertBatchImage(imageList);
        }
    }

    /**
     * 添加文字
     */
    @Override
    public void addText(String str) {
        insertText(str);
    }

    /**
     * 插入文字
     */
    private void insertText(String str) {
        lastFocusEdit.setText(str);
        //得到最近一个获取焦点的EditText 中的文字
        String lastEditStr = lastFocusEdit.getText().toString().trim();
        lastFocusEdit.requestFocus();
        lastFocusEdit.setSelection(lastEditStr.length());
        processSoftKeyBoard(true);
    }


    /**
     * 对外提供的接口, 生成编辑数据上传
     */
    @Override
    public List<SEditorData> buildEditData() {
        List<SEditorData> dataList = new ArrayList<>();
        int num = containerLayout.getChildCount();
        for (int index = 0; index < num; index++) {
            View itemView = containerLayout.getChildAt(index);
            SEditorData itemData = new SEditorData();
            if (itemView instanceof EditText) {
                EditText item = (EditText) itemView;
                //如果整个edittext中为空则不添加
                if(!TextUtils.isEmpty(item.getText().toString())){
                    itemData.setInputStr(item.getText().toString());
                }else {
                    continue;
                }
            } else if (itemView instanceof RelativeLayout) {
                ImageView item = (ImageView) ((RelativeLayout) itemView).getChildAt(0);
                String path = item.getTag().toString();
                if (path.endsWith(".mp4")) {
                    itemData.setVideoPath(path);
                } else {
                    itemData.setImagePath(path);
                }
            } else {
                //因编辑中有预留输入文字的小图标会造成对象中无数据所以忽略
                continue;
            }
            dataList.add(itemData);
        }
        return dataList;
    }

    /**
     * 获取当前编辑器中图片数量
     * @return
     */
    @Override
    public int getImageCount() {
        int imageCount = 0;
        int num = containerLayout.getChildCount();
        for (int index = 0; index < num; index++) {
            View child = containerLayout.getChildAt(index);
            if (child instanceof RelativeLayout) {
                imageCount++;
            }
        }
        return imageCount;
    }


    /**
     * 编辑器内容是否为空
     * @return
     */
    @Override
    public boolean isContentEmpty() {
        boolean isEmpty = true;
        int num = containerLayout.getChildCount();
        for (int index = 0; index < num; index++) {
            View itemView = containerLayout.getChildAt(index);
            if (itemView instanceof ImageView) {
                continue;
            }
            if (itemView instanceof EditText) {
                EditText item = (EditText) itemView;
                if (!TextUtils.isEmpty(item.getText().toString().trim())) {
                    isEmpty = false;
                    break;
                }
            } else if (itemView instanceof RelativeLayout) {
                ImageView item = (ImageView) ((RelativeLayout) itemView).getChildAt(index);
                if (!TextUtils.isEmpty(item.getTag().toString())) {
                    isEmpty = false;
                    break;
                }
            }
        }
        return isEmpty;
    }

    /**
     * 获取标题
     * @return
     */
    @Override
    public String getTitleData() {
        return etTitle.getText().toString().trim();
    }

    private class ViewDragHelperCallBack extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return (child instanceof RelativeLayout) && isSort;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            final int leftBound = getPaddingLeft() + DEFAULT_MARGING;
            final int rightBound = getWidth() - child.getWidth() - leftBound;
            final int newLeft = Math.min(Math.max(left, leftBound), rightBound);
            return newLeft;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return top;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return 0;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return 0;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            int releasedViewID = Integer.parseInt(releasedChild.getTag().toString());
            int releasedViewPos = preSortPositionArray.get(releasedViewID);
            viewDragHelper.settleCapturedViewAt(releasedChild.getLeft(), releasedViewPos);
            invalidate();
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {

        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            int reduceChildCount = containerLayout.getChildCount();

            View sortChild;
            int changeViewTagID, sortViewTagID, changeViewPosition, sortViewPosition;
            for (int i = 0; i < reduceChildCount; i++) {
                sortChild = containerLayout.getChildAt(i);
                if (sortChild != changedView) {
                    changeViewTagID = Integer.parseInt(changedView.getTag().toString());
                    sortViewTagID = Integer.parseInt(sortChild.getTag().toString());

                    changeViewPosition = preSortPositionArray.get(changeViewTagID);
                    sortViewPosition = preSortPositionArray.get(sortViewTagID);
                    boolean isBig = (changedView.getTop() > sortChild.getTop() && changeViewPosition < sortViewPosition);
                    boolean isLow = (changedView.getTop() < sortChild.getTop() && changeViewPosition > sortViewPosition);
                    if (isBig || isLow) {
                        sortChild.setTop(changeViewPosition);
                        sortChild.setBottom(changeViewPosition + SIZE_REDUCE_VIEW);
                        preSortPositionArray.put(sortViewTagID, changeViewPosition);
                        preSortPositionArray.put(changeViewTagID, sortViewPosition);
                        resetChildPostion();
                        break;
                    }
                }
            }
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
        }

    }
}
