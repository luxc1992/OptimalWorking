package com.yst.basic.framework.view.jazzyviewpager;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Camera;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;
import com.yst.basic.framework.Const;
import com.yst.basic.framework.R;
import com.yst.basic.framework.utils.MyLog;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 自定义viewpager
 *
 * @author lixiangchao
 * @date 2017/12/12
 * @version 1.0.1
 */
public class JazzyViewPager extends ViewPager {

    public static final String TAG = "JazzyViewPager";

    private boolean mEnabled = true;
    private boolean mFadeEnabled = false;
    private boolean mOutlineEnabled = false;
    public static int sOutlineColor = Color.WHITE;
    private TransitionEffect mEffect = TransitionEffect.Standard;

    private HashMap<Integer, Object> mObjs = new LinkedHashMap<Integer, Object>();

    private static final float SCALE_MAX = 0.5f;
    private static final float ZOOM_MAX = 0.5f;
    private static final float ROT_MAX = 15.0f;

    public enum TransitionEffect {
        /**
         * 标准
         */
        Standard,
        /**
         * 小块
         */
        Tablet,
        /**
         * 立方体进入
         */
        CubeIn,
        /**
         * 立方体外出
         */
        CubeOut,
        /**
         * 垂直弹入
         */
        FlipVertical,
        /**
         * 水平弹入
         */
        FlipHorizontal,
        /**
         * 堆叠
         */
        Stack,
        /**
         * 放大
         */
        ZoomIn,
        /**
         * 缩小
         */
        ZoomOut,
        /**
         * 上方旋转
         */
        RotateUp,
        /**
         * 下方旋转
         */
        RotateDown,
        /**
         * 手风琴
         */
        Accordion
    }

    private static final boolean API_11;

    static {
        API_11 = Build.VERSION.SDK_INT >= 11;
    }

    public JazzyViewPager(Context context) {
        this(context, null);

        initViewPagerScroll();
    }

    @SuppressWarnings("incomplete-switch")
    public JazzyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViewPagerScroll();

        setClipChildren(false);
        // now style everything!
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.JazzyViewPager);
        int effect = ta.getInt(R.styleable.JazzyViewPager_style, 0);
        String[] transitions = getResources().getStringArray(R.array.jazzy_effects);
        setTransitionEffect(TransitionEffect.valueOf(transitions[effect]));
        setFadeEnabled(ta.getBoolean(R.styleable.JazzyViewPager_fadeEnabled, false));
        setOutlineEnabled(ta.getBoolean(R.styleable.JazzyViewPager_outlineEnabled, false));
        setOutlineColor(ta.getColor(R.styleable.JazzyViewPager_outlineColor, Color.WHITE));
        switch (mEffect) {
            case Stack:
            case ZoomOut:
                setFadeEnabled(true);
                break;
            default:
                break;
        }
        ta.recycle();
    }

    private void initViewPagerScroll() {
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(getContext());
            scroller.setmDuration(2000);
            mScroller.set(this, scroller);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public void setTransitionEffect(TransitionEffect effect) {
        mEffect = effect;
//		reset();
    }

    public void setPagingEnabled(boolean enabled) {
        mEnabled = enabled;
    }

    public void setFadeEnabled(boolean enabled) {
        mFadeEnabled = enabled;
    }

    public boolean getFadeEnabled() {
        return mFadeEnabled;
    }

    public void setOutlineEnabled(boolean enabled) {
        mOutlineEnabled = enabled;
        wrapWithOutlines();
    }

    public void setOutlineColor(int color) {
        sOutlineColor = color;
    }

    private void wrapWithOutlines() {
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            if (!(v instanceof OutlineContainer)) {
                removeView(v);
                super.addView(wrapChild(v), i);
            }
        }
    }

    private View wrapChild(View child) {
        if (!mOutlineEnabled || child instanceof OutlineContainer) {
            return child;
        }
        OutlineContainer out = new OutlineContainer(getContext());
        out.setLayoutParams(generateDefaultLayoutParams());
        child.setLayoutParams(new OutlineContainer.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        out.addView(child);
        return out;
    }

    @Override
    public void addView(View child) {
        super.addView(wrapChild(child));
    }

    @Override
    public void addView(View child, int index) {
        super.addView(wrapChild(child), index);
    }

    public void addView(View child, LayoutParams params) {
        super.addView(wrapChild(child), params);
    }

    @Override
    public void addView(View child, int width, int height) {
        super.addView(wrapChild(child), width, height);
    }

    public void addView(View child, int index, LayoutParams params) {
        super.addView(wrapChild(child), index, params);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return mEnabled && super.onInterceptTouchEvent(arg0);
    }

    private State mState;
    private int oldPage;

    private View mLeft;
    private View mRight;
    private float mRot;
    private float mTrans;
    private float mScale;
    /**
     * 九十度
     * 负九十度
     */
    private static final float POSITIVE_NINETY_DEGREES = 90.0f;
    private static final float NAGATIVE_NINETY_DEGREES = -90.0f;

    private enum State {
        /**
         * 闲置
         */
        IDLE,
        /**
         * 左方
         */
        GOING_LEFT,
        /**
         * 右方
         */
        GOING_RIGHT
    }


    private void logState(View v, String title) {
        MyLog.e(TAG, title + ": ROT (" + ViewHelper.getRotation(v) + ", " +
                ViewHelper.getRotationX(v) + ", " +
                ViewHelper.getRotationY(v) + "), TRANS (" +
                ViewHelper.getTranslationX(v) + ", " +
                ViewHelper.getTranslationY(v) + "), SCALE (" +
                ViewHelper.getScaleX(v) + ", " +
                ViewHelper.getScaleY(v) + "), ALPHA " +
                ViewHelper.getAlpha(v));
    }

    protected void animateScroll(int position, float positionOffset) {
        if (mState != State.IDLE) {
            mRot = (float) (1 - Math.cos(2 * Math.PI * positionOffset)) / 2 * 30.0f;
            ViewHelper.setRotationY(this, mState == State.GOING_RIGHT ? mRot : -mRot);
            ViewHelper.setPivotX(this, getMeasuredWidth() * 0.5f);
            ViewHelper.setPivotY(this, getMeasuredHeight() * 0.5f);
        }
    }

    protected void animateTablet(View left, View right, float positionOffset) {
        if (mState != State.IDLE) {
            if (left != null) {
                manageLayer(left, true);
                mRot = 30.0f * positionOffset;
                mTrans = getOffsetXForRotation(mRot, left.getMeasuredWidth(),
                        left.getMeasuredHeight());
                ViewHelper.setPivotX(left, Float.valueOf(String.valueOf(left.getMeasuredWidth() / 2)));
                ViewHelper.setPivotY(left, Float.valueOf(String.valueOf(left.getMeasuredHeight() / 2)));
                ViewHelper.setTranslationX(left, mTrans);
                ViewHelper.setRotationY(left, mRot);
                logState(left, "Left");
            }
            if (right != null) {
                manageLayer(right, true);
                mRot = -30.0f * (1 - positionOffset);
                mTrans = getOffsetXForRotation(mRot, right.getMeasuredWidth(),
                        right.getMeasuredHeight());
                ViewHelper.setPivotX(right, right.getMeasuredWidth() * 0.5f);
                ViewHelper.setPivotY(right, right.getMeasuredHeight() * 0.5f);
                ViewHelper.setTranslationX(right, mTrans);
                ViewHelper.setRotationY(right, mRot);
                logState(right, "Right");
            }
        }
    }

    private void animateCube(View left, View right, float positionOffset, boolean in) {
        if (mState != State.IDLE) {
            if (left != null) {
                manageLayer(left, true);
                mRot = (in ? POSITIVE_NINETY_DEGREES : NAGATIVE_NINETY_DEGREES) * positionOffset;
                ViewHelper.setPivotX(left, left.getMeasuredWidth());
                ViewHelper.setPivotY(left, left.getMeasuredHeight() * 0.5f);
                ViewHelper.setRotationY(left, mRot);
            }
            if (right != null) {
                manageLayer(right, true);
                mRot = -(in ? POSITIVE_NINETY_DEGREES : NAGATIVE_NINETY_DEGREES) * (Const.ONE - positionOffset);
                ViewHelper.setPivotX(right, 0);
                ViewHelper.setPivotY(right, right.getMeasuredHeight() * 0.5f);
                ViewHelper.setRotationY(right, mRot);
            }
        }
    }

    private void animateAccordion(View left, View right, float positionOffset) {
        if (mState != State.IDLE) {
            if (left != null) {
                manageLayer(left, true);
                ViewHelper.setPivotX(left, left.getMeasuredWidth());
                ViewHelper.setPivotY(left, 0);
                ViewHelper.setScaleX(left, 1 - positionOffset);
            }
            if (right != null) {
                manageLayer(right, true);
                ViewHelper.setPivotX(right, 0);
                ViewHelper.setPivotY(right, 0);
                ViewHelper.setScaleX(right, positionOffset);
            }
        }
    }

    private void animateZoom(View left, View right, float positionOffset, boolean in) {
        if (mState != State.IDLE) {
            if (left != null) {
                manageLayer(left, true);
                mScale = in ? ZOOM_MAX + (1 - ZOOM_MAX) * (1 - positionOffset) :
                        1 + ZOOM_MAX - ZOOM_MAX * (1 - positionOffset);
                ViewHelper.setPivotX(left, left.getMeasuredWidth() * 0.5f);
                ViewHelper.setPivotY(left, left.getMeasuredHeight() * 0.5f);
                ViewHelper.setScaleX(left, mScale);
                ViewHelper.setScaleY(left, mScale);
            }
            if (right != null) {
                manageLayer(right, true);
                mScale = in ? ZOOM_MAX + (1 - ZOOM_MAX) * positionOffset :
                        1 + ZOOM_MAX - ZOOM_MAX * positionOffset;
                ViewHelper.setPivotX(right, right.getMeasuredWidth() * 0.5f);
                ViewHelper.setPivotY(right, right.getMeasuredHeight() * 0.5f);
                ViewHelper.setScaleX(right, mScale);
                ViewHelper.setScaleY(right, mScale);
            }
        }
    }

    private void animateRotate(View left, View right, float positionOffset, boolean up) {
        if (mState != State.IDLE) {
            if (left != null) {
                manageLayer(left, true);
                mRot = (up ? 1 : -1) * (ROT_MAX * positionOffset);
                mTrans = (up ? -1 : 1) * (float) (getMeasuredHeight() - getMeasuredHeight() * Math.cos(mRot * Math.PI / 180.0f));
                ViewHelper.setPivotX(left, left.getMeasuredWidth() * 0.5f);
                ViewHelper.setPivotY(left, up ? 0 : left.getMeasuredHeight());
                ViewHelper.setTranslationY(left, mTrans);
                ViewHelper.setRotation(left, mRot);
            }
            if (right != null) {
                manageLayer(right, true);
                mRot = (up ? 1 : -1) * (-ROT_MAX + ROT_MAX * positionOffset);
                mTrans = (up ? -1 : 1) * (float) (getMeasuredHeight() - getMeasuredHeight() * Math.cos(mRot * Math.PI / 180.0f));
                ViewHelper.setPivotX(right, right.getMeasuredWidth() * 0.5f);
                ViewHelper.setPivotY(right, up ? 0 : right.getMeasuredHeight());
                ViewHelper.setTranslationY(right, mTrans);
                ViewHelper.setRotation(right, mRot);
            }
        }
    }

    private void animateFlipHorizontal(View left, View right, float positionOffset, int positionOffsetPixels) {
        if (mState != State.IDLE) {
            if (left != null) {
                manageLayer(left, true);
                mRot = 180.0f * positionOffset;
                if (mRot > POSITIVE_NINETY_DEGREES) {
                    left.setVisibility(View.INVISIBLE);
                } else {
                    if (left.getVisibility() == View.INVISIBLE) {
                        left.setVisibility(View.VISIBLE);
                    }
                    mTrans = positionOffsetPixels;
                    ViewHelper.setPivotX(left, left.getMeasuredWidth() * 0.5f);
                    ViewHelper.setPivotY(left, left.getMeasuredHeight() * 0.5f);
                    ViewHelper.setTranslationX(left, mTrans);
                    ViewHelper.setRotationY(left, mRot);
                }
            }
            if (right != null) {
                manageLayer(right, true);
                mRot = -180.0f * (1 - positionOffset);
                if (mRot < NAGATIVE_NINETY_DEGREES) {
                    right.setVisibility(View.INVISIBLE);
                } else {
                    if (right.getVisibility() == View.INVISIBLE) {
                        right.setVisibility(View.VISIBLE);
                    }
                    mTrans = -getWidth() - getPageMargin() + positionOffsetPixels;
                    ViewHelper.setPivotX(right, right.getMeasuredWidth() * 0.5f);
                    ViewHelper.setPivotY(right, right.getMeasuredHeight() * 0.5f);
                    ViewHelper.setTranslationX(right, mTrans);
                    ViewHelper.setRotationY(right, mRot);
                }
            }
        }
    }

    private void animateFlipVertical(View left, View right, float positionOffset, int positionOffsetPixels) {
        if (mState != State.IDLE) {
            if (left != null) {
                manageLayer(left, true);
                mRot = 180.0f * positionOffset;
                if (mRot > POSITIVE_NINETY_DEGREES) {
                    left.setVisibility(View.INVISIBLE);
                } else {
                    if (left.getVisibility() == View.INVISIBLE) {
                        left.setVisibility(View.VISIBLE);
                    }
                    mTrans = positionOffsetPixels;
                    ViewHelper.setPivotX(left, left.getMeasuredWidth() * 0.5f);
                    ViewHelper.setPivotY(left, left.getMeasuredHeight() * 0.5f);
                    ViewHelper.setTranslationX(left, mTrans);
                    ViewHelper.setRotationX(left, mRot);
                }
            }
            if (right != null) {
                manageLayer(right, true);
                mRot = -180.0f * (1 - positionOffset);
                if (mRot < NAGATIVE_NINETY_DEGREES) {
                    right.setVisibility(View.INVISIBLE);
                } else {
                    if (right.getVisibility() == View.INVISIBLE) {
                        right.setVisibility(View.VISIBLE);
                    }
                    mTrans = -getWidth() - getPageMargin() + positionOffsetPixels;
                    ViewHelper.setPivotX(right, right.getMeasuredWidth() * 0.5f);
                    ViewHelper.setPivotY(right, right.getMeasuredHeight() * 0.5f);
                    ViewHelper.setTranslationX(right, mTrans);
                    ViewHelper.setRotationX(right, mRot);
                }
            }
        }
    }

    protected void animateStack(View left, View right, float positionOffset, int positionOffsetPixels) {
        if (mState != State.IDLE) {
            if (right != null) {
                manageLayer(right, true);
                mScale = (1 - SCALE_MAX) * positionOffset + SCALE_MAX;
                mTrans = -getWidth() - getPageMargin() + positionOffsetPixels;
                ViewHelper.setScaleX(right, mScale);
                ViewHelper.setScaleY(right, mScale);
                ViewHelper.setTranslationX(right, mTrans);
            }
            if (left != null) {
                left.bringToFront();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void manageLayer(View v, boolean enableHardware) {
        if (!API_11) {
            return;
        }
        int layerType = enableHardware ? View.LAYER_TYPE_HARDWARE : View.LAYER_TYPE_NONE;
        if (layerType != v.getLayerType()) {
            v.setLayerType(layerType, null);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void disableHardwareLayer() {
        if (!API_11) {
            return;
        }
        View v;
        for (int i = 0; i < getChildCount(); i++) {
            v = getChildAt(i);
            if (v.getLayerType() != View.LAYER_TYPE_NONE) {
                v.setLayerType(View.LAYER_TYPE_NONE, null);
            }
        }
    }

    private Matrix mMatrix = new Matrix();
    private Camera mCamera = new Camera();
    private float[] mTempFloat2 = new float[2];

    protected float getOffsetXForRotation(float degrees, int width, int height) {
        mMatrix.reset();
        mCamera.save();
        mCamera.rotateY(Math.abs(degrees));
        mCamera.getMatrix(mMatrix);
        mCamera.restore();

        mMatrix.preTranslate(-width * 0.5f, -height * 0.5f);
        mMatrix.postTranslate(width * 0.5f, height * 0.5f);
        mTempFloat2[0] = width;
        mTempFloat2[1] = height;
        mMatrix.mapPoints(mTempFloat2);
        return (width - mTempFloat2[0]) * (degrees > 0.0f ? 1.0f : -1.0f);
    }

    protected void animateFade(View left, View right, float positionOffset) {
        if (left != null) {
            ViewHelper.setAlpha(left, 1 - positionOffset);
        }
        if (right != null) {
            ViewHelper.setAlpha(right, positionOffset);
        }
    }

    protected void animateOutline(View left, View right) {
        if (!(left instanceof OutlineContainer)) {
            return;
        }
        if (mState != State.IDLE) {
            if (left != null) {
                manageLayer(left, true);
                ((OutlineContainer) left).setOutlineAlpha(1.0f);
            }
            if (right != null) {
                manageLayer(right, true);
                ((OutlineContainer) right).setOutlineAlpha(1.0f);
            }
        } else {
            if (null != left) {
                ((OutlineContainer) left).start();
            }
            if (null != right) {
                ((OutlineContainer) right).start();
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mState == State.IDLE && positionOffset > 0) {
            oldPage = getCurrentItem();
            mState = position == oldPage ? State.GOING_RIGHT : State.GOING_LEFT;
        }
        boolean goingRight = position == oldPage;
        if (mState == State.GOING_RIGHT && !goingRight) {
            mState = State.GOING_LEFT;
        } else if (mState == State.GOING_LEFT && goingRight) {
            mState = State.GOING_RIGHT;
        }

        float effectOffset = isSmall(positionOffset) ? 0 : positionOffset;

//		mLeft = getChildAt(position);
//		mRight = getChildAt(position+1);
        mLeft = findViewFromObject(position);
        mRight = findViewFromObject(position + 1);

        if (mFadeEnabled) {
            animateFade(mLeft, mRight, effectOffset);
        }
        if (mOutlineEnabled) {
            animateOutline(mLeft, mRight);
        }

        switch (mEffect) {
            case Standard:
                break;
            case Tablet:
                animateTablet(mLeft, mRight, effectOffset);
                break;
            case CubeIn:
                animateCube(mLeft, mRight, effectOffset, true);
                break;
            case CubeOut:
                animateCube(mLeft, mRight, effectOffset, false);
                break;
            case FlipVertical:
                animateFlipVertical(mLeft, mRight, positionOffset, positionOffsetPixels);
                break;
            case FlipHorizontal:
                animateFlipHorizontal(mLeft, mRight, effectOffset, positionOffsetPixels);
                break;
            case Stack:
                animateStack(mLeft, mRight, effectOffset, positionOffsetPixels);
                break;
            case ZoomIn:
                animateZoom(mLeft, mRight, effectOffset, true);
                break;
            case ZoomOut:
                animateZoom(mLeft, mRight, effectOffset, false);
                break;
            case RotateUp:
                animateRotate(mLeft, mRight, effectOffset, true);
                break;
            case RotateDown:
                animateRotate(mLeft, mRight, effectOffset, false);
                break;
            case Accordion:
                animateAccordion(mLeft, mRight, effectOffset);
                break;
            default:
                break;
        }

        super.onPageScrolled(position, positionOffset, positionOffsetPixels);

        if (effectOffset == 0) {
            disableHardwareLayer();
            mState = State.IDLE;
        }

    }

    private boolean isSmall(float positionOffset) {
        return Math.abs(positionOffset) < 0.0001;
    }

    public void setObjectForPosition(Object obj, int position) {
        mObjs.put(Integer.valueOf(position), obj);
    }

    public View findViewFromObject(int position) {
        Object o = mObjs.get(Integer.valueOf(position));
        if (o == null) {
            return null;
        }
        PagerAdapter a = getAdapter();
        View v;
        for (int i = 0; i < getChildCount(); i++) {
            v = getChildAt(i);
            if (a.isViewFromObject(v, o)) {
                return v;
            }
        }
        return null;
    }

}