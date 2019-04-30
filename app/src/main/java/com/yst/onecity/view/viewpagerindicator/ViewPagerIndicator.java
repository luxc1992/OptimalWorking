package com.yst.onecity.view.viewpagerindicator;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import com.yst.onecity.view.viewpagerindicator.indicatorview.IPagerIndicator;
import com.yst.onecity.view.viewpagerindicator.indicatorview.LinePagerIndicator;
import com.yst.onecity.view.viewpagerindicator.interfaces.IPagerNavigator;
import com.yst.onecity.view.viewpagerindicator.titleview.IPagerTitleView;
import com.yst.onecity.view.viewpagerindicator.titleview.ScaleTransitionPagerTitleView;
import com.yst.onecity.view.viewpagerindicator.titleview.SimplePagerTitleView;


/**
 * 整个框架的入口，核心
 *
 * @author chenjiadi
 * @version 1.0.1
 * @date 2018/2/6
 */
public class ViewPagerIndicator extends FrameLayout {
    private IPagerNavigator mNavigator;
    private CommonNavigator commonNavigator;
    private PagerAdapter mViewPagerAdapter;
    /**
     * 是否显示indicator 游标
     */
    private boolean mIsShowIndicator = true;
    private OnIndicatorItemClick listener;
    public static int type = 0;

    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPagerIndicator(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        commonNavigator = new CommonNavigator(context);
    }

    /**
     * 关联viewpager
     *
     * @param viewPager
     * @param mAdjustMode true 平分充满，flase自适应
     */
    public void bindViewPager(ViewPager viewPager, boolean mAdjustMode) {
        bindViewPager(viewPager, mAdjustMode, true);
    }

    /**
     * 关联viewpager
     *
     * @param viewPager
     * @param mAdjustMode     true 平分充满，flase自适应
     * @param isShowIndicator true 默认 显示底部游标，flase 隐藏底部游标
     */
    public void bindViewPager(ViewPager viewPager, boolean mAdjustMode, boolean isShowIndicator) {
        commonNavigator.setAdjustMode(mAdjustMode);
        mViewPagerAdapter = viewPager.getAdapter();
        mIsShowIndicator = isShowIndicator;
        initNavigator(viewPager);
        initPagerListener(viewPager);
    }

    public void bindArrData(String[] titleArr, OnIndicatorItemClick listener) {
        this.listener = listener;
        commonNavigator.setAdjustMode(true);
        initNavigator(titleArr);
    }

    /**
     * viewpager添加页面改变监听
     *
     * @param viewPager
     */
    private void initPagerListener(ViewPager viewPager) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                onVPageScrolled(position, positionOffset, positionOffsetPixels);

            }

            @Override
            public void onPageSelected(int position) {
                onVPageSelected(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                onVPageScrollStateChanged(state);

            }
        });
    }

    /**
     * 没有绑定viewpager的初始化
     *
     * @param titleArr
     */
    private void initNavigator(final String[] titleArr) {
        commonNavigator.setAdapter(new AbstractCommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return titleArr.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(titleArr[index]);
                initTitle(simplePagerTitleView);
                //点击事件
                simplePagerTitleView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onVPageSelected(index);
                        listener.onIndicatorClick(index);
                        notifyDataSetChanged();
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                if (!mIsShowIndicator) {
                    return null;
                } else {
                    LinePagerIndicator indicator = new LinePagerIndicator(context);
                    initIndicator(indicator, context);
                    return indicator;
                }
            }
        });
        setNavigator(commonNavigator);
    }

    /**
     * 绑定viewpager的初始化
     *
     * @param viewPager
     */
    private void initNavigator(final ViewPager viewPager) {
        commonNavigator.setAdapter(new AbstractCommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mViewPagerAdapter.getCount();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(mViewPagerAdapter.getPageTitle(index));
                if (type == 0) {
                    initTitle(simplePagerTitleView);
                } else {
                    setTitleColor(simplePagerTitleView);
                }

                //点击事件
                simplePagerTitleView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index, false);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                if (!mIsShowIndicator) {
                    return null;
                } else {
                    LinePagerIndicator indicator = new LinePagerIndicator(context);
                    initIndicator(indicator, context);
                    return indicator;
                }
            }
        });
        setNavigator(commonNavigator);
    }

    /**
     * 初始化标题样式
     *
     * @param titleView
     */
    private void initTitle(SimplePagerTitleView titleView) {
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        titleView.setNormalColor(Color.parseColor("#333333"));
        titleView.setSelectedColor(Color.parseColor("#ED5553"));
    }

    /**
     * 初始化标题样式
     *
     * @param titleView
     */
    private void setTitleColor(SimplePagerTitleView titleView) {
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        titleView.setNormalColor(Color.parseColor("#333333"));
        titleView.setSelectedColor(Color.parseColor("#368D2F"));
    }

    /**
     * 初始化指示器样式
     *
     * @param indicator
     */
    private void initIndicator(LinePagerIndicator indicator, Context context) {
        indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
        indicator.setLineHeight(UiUtil.dip2px(context, 2));
        indicator.setLineWidth(UiUtil.dip2px(context, 22));
        indicator.setRoundRadius(UiUtil.dip2px(context, 3));
        indicator.setStartInterpolator(new AccelerateInterpolator());
        indicator.setEndInterpolator(new DecelerateInterpolator(1.0f));
        if (type==0) {
            indicator.setColors(Color.parseColor("#ED5452"));
        }else{
            indicator.setColors(Color.parseColor("#368D2F"));
        }
    }

    public void onVPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mNavigator != null) {
            mNavigator.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    public void onVPageSelected(int position) {
        if (mNavigator != null) {
            mNavigator.onPageSelected(position);
        }
    }

    public void onVPageScrollStateChanged(int state) {
        if (mNavigator != null) {
            mNavigator.onPageScrollStateChanged(state);
        }
    }

    public IPagerNavigator getNavigator() {
        return mNavigator;
    }

    public void setNavigator(IPagerNavigator navigator) {
        if (mNavigator == navigator) {
            return;
        }
        if (mNavigator != null) {
            mNavigator.onDetachFromMagicIndicator();
        }
        mNavigator = navigator;
        removeAllViews();
        if (mNavigator instanceof View) {
            LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            addView((View) mNavigator, lp);
            mNavigator.onAttachToMagicIndicator();
        }
    }

    public interface OnIndicatorItemClick {
        /**
         * item单击事件
         *
         * @param index 索引
         */
        void onIndicatorClick(int index);
    }
}

