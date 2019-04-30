package com.yst.basic.framework.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yst.basic.framework.Const;
import com.yst.basic.framework.R;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.utils.Utils;

import java.util.List;

/**
 * 多标签页面展示
 *
 * @author lixiangchao
 * @date 2017/12/21.
 * @version 1.0.1
 */
public abstract class BaseBottomTabActivity extends BaseActivity {

    /**
     * 底部中间按钮
     */
    private LinearLayout llayoutMainBottomMiddle;
    private ImageView imgMainBottomMiddle;
    private TextView txtMainBottomMiddle;
    private View viewMainMiddle;

    private FrameLayout frameRootLayout;

    private int position = 0;

    /**
     * 底部视图
     *
     * @return
     */
    private LinearLayout llayoutBottomGroup;
    private LinearLayout llayout1;
    private LinearLayout llayout2;
    private LinearLayout llayout3;
    private LinearLayout llayout4;
    private LinearLayout llayoutMiddle;

    private ImageView imgView1;
    private ImageView imgView2;
    private ImageView imgView3;
    private ImageView imgView4;
    private ImageView imgMiddle;

    private TextView txtView1;
    private TextView txtView2;
    private TextView txtView3;
    private TextView txtView4;
    private TextView txtMiddle;

    /**
     * 页面跳转控制器
     */
    private FragmentManager fMgr;
    /**
     * Fragment1
     */
    private Fragment fragment1;
    /**
     * Fragment2
     */
    private Fragment fragment2;
    /**
     * Fragment3
     */
    private Fragment fragment3;
    /**
     * Fragment4
     */
    private Fragment fragment4;
    /**
     * FragmentMiddle
     */
    private Fragment fragmentMiddle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_base_bottom_tab;
    }

    @Override
    public void initData() {
        initView();
        config();
        if (getTabFragments() == null || getTabFragments().size() < 1) {
            ToastUtils.show("写点什么吧");
            frameRootLayout.setVisibility(View.GONE);
            llayoutBottomGroup.setVisibility(View.GONE);
        } else if (getTabFragments().size() == Const.TAB_COUNT + 1) {
            showRootBottomView();
        } else {
            showRootBottomView();
        }
        fMgr = getSupportFragmentManager();
        position = getShowPosition();
        if ((position > getTabFragments().size() - 1) || position < 0) {
            ToastUtils.show("超出我的能力范围了，给你重置成0了哦");
            position = 0;
        }
        setTabSelection(position);
    }

    /**
     * 显示内容和底部导航栏
     */
    private void showRootBottomView() {
        frameRootLayout.setVisibility(View.VISIBLE);
        llayoutBottomGroup.setVisibility(View.VISIBLE);
    }

    /**
     * 初始化控件，因为是依赖所以只能用原始的声明控件方法
     */
    private void initView() {
        viewMainMiddle = findViewById(R.id.view_main_middle);
        llayoutMainBottomMiddle = findViewById(R.id.llayout_main_bottom_menu);
        imgMainBottomMiddle = findViewById(R.id.img_main_publish_menu);
        txtMainBottomMiddle = findViewById(R.id.txt_main_bottom_middle_view);
        txtMainBottomMiddle = findViewById(R.id.txt_main_bottom_middle_view);
        txtMainBottomMiddle.setText(null == getMiddleBtnText() ? "" : getMiddleBtnText());

        frameRootLayout = findViewById(R.id.frame_root_layout);
        llayoutBottomGroup = findViewById(R.id.llayout_main_bottom_group);

        llayout1 = findViewById(R.id.main_activity_home_linear_layout);
        llayout2 = findViewById(R.id.main_activity_classification_linear_layout);
        llayout3 = findViewById(R.id.main_activity_rank_linear_layout);
        llayout4 = findViewById(R.id.main_activity_mine_linear_layout);
        llayoutMiddle = findViewById(R.id.main_activity_middle_linear_layout);
        llayout1.setOnClickListener(new MyClickListener());
        llayout2.setOnClickListener(new MyClickListener());
        llayout3.setOnClickListener(new MyClickListener());
        llayout4.setOnClickListener(new MyClickListener());
        llayoutMiddle.setOnClickListener(new MyClickListener());

        imgView1 = findViewById(R.id.main_activity_home_image_view);
        imgView2 = findViewById(R.id.main_activity_classification_image_view);
        imgView3 = findViewById(R.id.main_activity_rank_image_view);
        imgView4 = findViewById(R.id.main_activity_mine_image_view);
        imgMiddle = findViewById(R.id.main_activity_middle_image_view);

        txtView1 = findViewById(R.id.main_activity_home_text_view);
        txtView2 = findViewById(R.id.main_activity_classification_text_view);
        txtView3 = findViewById(R.id.main_activity_rank_text_view);
        txtView4 = findViewById(R.id.main_activity_mine_text_view);
        txtMiddle = findViewById(R.id.main_activity_middle_text_view);

        llayoutMainBottomMiddle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isClickable()) {
                    setBtnClickListener();
                }
            }
        });
        if (isShowMiddle()) {
            viewMainMiddle.setVisibility(View.VISIBLE);
            llayoutMainBottomMiddle.setVisibility(View.VISIBLE);
        } else if (getTabFragments().size() == Const.TAB_COUNT){
            viewMainMiddle.setVisibility(View.GONE);
            llayoutMainBottomMiddle.setVisibility(View.GONE);
        } else if (getTabFragments().size() == Const.FIVE) {
            llayoutMiddle.setVisibility(View.VISIBLE);
            viewMainMiddle.setVisibility(View.GONE);
            llayoutMainBottomMiddle.setVisibility(View.GONE);
            txtMiddle.setText(getTabContent()[4] == null ? "" : getTabContent()[4]);
        }

        txtView1.setText(getTabContent()[0] == null ? "" : getTabContent()[0]);
        txtView2.setText(getTabContent()[1] == null ? "" : getTabContent()[1]);
        txtView3.setText(getTabContent()[2] == null ? "" : getTabContent()[2]);
        txtView4.setText(getTabContent()[3] == null ? "" : getTabContent()[3]);
    }

    /**
     * 是否展示中间的按钮
     *
     * @return
     */
    public abstract boolean isShowMiddle();

    /**
     * 是否是回调展示业务工程中的页面
     *
     * @return
     */
    public abstract boolean isMiddleCallback();

    /**
     * 中间按钮的点击回调
     */
    protected void setBtnClickListener() {
    }

    /**
     * 设置中间按钮的文本
     *
     * @return 获取返回值
     */
    protected abstract String getMiddleBtnText();

    /**
     * 获取展示的内容
     *
     * @return
     */
    public abstract List<Fragment> getTabFragments();

    /**
     * 获取按钮未选中的图标样式
     *
     * @return
     */
    public abstract int[] getTabNormalIcons();

    /**
     * 获取按钮选中的样式
     *
     * @return
     */
    public abstract int[] getTabSelectedIcons();

    /**
     * 获取标签展示内容
     *
     * @return
     */
    public abstract String[] getTabContent();

    /**
     * 获取文字内容的展示样式 第一个为未选中颜色 第二个为选中的颜色
     *
     * @return
     */
    public abstract String[] getTabColors();

    /**
     * 获取第几个tab展示 下标从0开始 默认为0
     *
     * @return
     */
    public abstract int getShowPosition();

    /**
     * 这里是用做一些自己的操作的
     *
     * @return
     */
    public abstract void config();

    /**
     * 重新写一个页面监听
     */
    class MyClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.main_activity_home_linear_layout) {
                setTabSelection(0);
            } else if (view.getId() == R.id.main_activity_classification_linear_layout) {
                setTabSelection(1);
            } else if (view.getId() == R.id.main_activity_rank_linear_layout) {
                setTabSelection(2);
            } else if (view.getId() == R.id.main_activity_mine_linear_layout) {
                setTabSelection(3);
            } else if (view.getId() == R.id.main_activity_middle_linear_layout) {
                setTabSelection(4);
            }
        }
    }

    /**
     * 重置底部标签
     */
    private void resetBottomViewStatus() {
        //TODO reset bottom views status
        imgView1.setImageResource(getTabNormalIcons()[0]);
        txtView1.setTextColor(Color.parseColor(getTabColors()[0]));
        imgView2.setImageResource(getTabNormalIcons()[1]);
        txtView2.setTextColor(Color.parseColor(getTabColors()[0]));
        imgView3.setImageResource(getTabNormalIcons()[2]);
        txtView3.setTextColor(Color.parseColor(getTabColors()[0]));
        imgView4.setImageResource(getTabNormalIcons()[3]);
        txtView4.setTextColor(Color.parseColor(getTabColors()[0]));
        if (getTabNormalIcons().length > Const.TAB_COUNT) {
            imgMiddle.setImageResource(getTabNormalIcons()[4]);
            txtMiddle.setTextColor(Color.parseColor(getTabColors()[0]));
        }
    }

    /**
     * 当fragment已被实例化，就隐藏起来
     *
     * @param ftn 事物
     */
    private void hideFragments(FragmentTransaction ftn) {
        // TODO Auto-generated method stub
        if (fragment1 != null) {
            ftn.hide(fragment1);
        }
        if (fragment2 != null) {
            ftn.hide(fragment2);
        }
        if (fragment3 != null) {
            ftn.hide(fragment3);
        }
        if (fragment4 != null) {
            ftn.hide(fragment4);
        }
        if (fragmentMiddle != null) {
            ftn.hide(fragmentMiddle);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // 记录当前的position
        outState.putInt("position", position);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onRestoreInstanceState(savedInstanceState);
        // 针对用户点击了清除内存的加速球的解决方法----需要saveInstance方法
        if (null == fMgr) {
            fMgr = getSupportFragmentManager();
        }
        position = savedInstanceState.getInt("position");
        setTabSelection(position);
    }

    /**
     * 设置当前选中的标签状态和对应的内容展示
     *
     * @param position 当前选中的图标
     */
    private void setTabSelection(int position) {
        // TODO Auto-generated method stub
        this.position = position;
        resetBottomViewStatus();
        // 更改底部导航栏按钮状态
        FragmentTransaction ftn = fMgr.beginTransaction();
        // 想要显示一个fragment,先隐藏所有fragment，防止重叠
        hideFragments(ftn);
        switch (position) {
            case 0:
                imgView1.setImageResource(getTabSelectedIcons()[0]);
                txtView1.setTextColor(Color.parseColor(getTabColors()[1]));
                if (fragment1 == null) {
                    fragment1 = getTabFragments().get(0);
                    ftn.add(R.id.frame_root_layout, fragment1, "fragment1");
                } else {
                    ftn.show(fragment1);
                }
                break;
            case 1:
                imgView2.setImageResource(getTabSelectedIcons()[1]);
                txtView2.setTextColor(Color.parseColor(getTabColors()[1]));
                if (fragment2 == null) {
                    fragment2 = getTabFragments().get(1);
                    ftn.add(R.id.frame_root_layout, fragment2, "fragment2");
                } else {
                    ftn.show(fragment2);
                }
                break;
            case 2:
                imgView3.setImageResource(getTabSelectedIcons()[2]);
                txtView3.setTextColor(Color.parseColor(getTabColors()[1]));
                if (fragment3 == null) {
                    fragment3 = getTabFragments().get(2);
                    ftn.add(R.id.frame_root_layout, fragment3, "fragment3");
                } else {
                    ftn.show(fragment3);
                }
                break;
            case 3:
                imgView4.setImageResource(getTabSelectedIcons()[3]);
                txtView4.setTextColor(Color.parseColor(getTabColors()[1]));
                if (fragment4 == null) {
                    fragment4 = getTabFragments().get(3);
                    ftn.add(R.id.frame_root_layout, fragment4, "fragment4");
                } else {
                    ftn.show(fragment4);
                }
                break;
            case 4:
                if (isMiddleCallback()) {
                    setBtnClickListener();
                    break;
                } else {
                    imgMiddle.setImageResource(getTabSelectedIcons()[4]);
                    txtMiddle.setTextColor(Color.parseColor(getTabColors()[1]));
                    if (fragmentMiddle == null) {
                        fragmentMiddle = getTabFragments().get(4);
                        ftn.add(R.id.frame_root_layout, fragmentMiddle, "fragmentMiddle");
                    } else {
                        ftn.show(fragmentMiddle);
                    }
                }
                break;
            default:
                break;
        }
        ftn.commitAllowingStateLoss();
    }
}
