package com.yst.im.imchatlibrary.ui.fragment;

import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.LinearLayout;


import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.adapter.EmotionGridViewAdapter;
import com.yst.im.imchatlibrary.adapter.EmotionPagerAdapter;
import com.yst.im.imchatlibrary.base.BaseFragment;
import com.yst.im.imchatlibrary.utils.EmotionUtils;
import com.yst.im.imchatlibrary.utils.GlobalOnItemClickManagerUtils;
import com.yst.im.imchatlibrary.widget.IndicatorView;
import com.yst.im.imsdk.utils.BaseUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 表情
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/11.
 */
public class ChatEmotionFragment extends BaseFragment {
    private ViewPager fragmentChatVp;
    private IndicatorView fragmentChatGroup;
    private EmotionPagerAdapter emotionPagerAdapter;

    @Override
    public void initView(View mView) {
        fragmentChatVp = (ViewPager) mView.findViewById(R.id.fragment_chat_vp);
        fragmentChatGroup = (IndicatorView) mView.findViewById(R.id.fragment_chat_group);
        initWidget();
    }

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_chat_emotion;
    }

    private void initWidget() {
        fragmentChatVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int oldPagerPos = 0;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                fragmentChatGroup.playByStartPointToNext(oldPagerPos, position);
                oldPagerPos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        initEmotion();
    }

    /**
     * 初始化表情面板
     * 思路：获取表情的总数，按每行存放7个表情，动态计算出每个表情所占的宽度大小（包含间距），
     * 而每个表情的高与宽应该是相等的，这里我们约定只存放3行
     * 每个面板最多存放7*3=21个表情，再减去一个删除键，即每个面板包含20个表情
     * 根据表情总数，循环创建多个容量为20的List，存放表情，对于大小不满20进行特殊
     * 处理即可。
     */
    private void initEmotion() {
        // 获取屏幕宽度
        WindowManager manager = getActivity().getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int screenWidth = outMetrics.widthPixels;
//        int height = outMetrics.heightPixels;
        // item的间距
        int spacing = BaseUtils.dp2px(getActivity(), 2);
        // 动态计算item的宽度和高度
        int itemWidth = (screenWidth - spacing * 4) / 8;
        //动态计算gridview的总高度
//        int gvHeight = itemWidth * 3 + spacing * 6;
        int gvHeight = BaseUtils.dp2px(getActivity(), 170);

        List<GridView> emotionViews = new ArrayList<>();
        List<String> emotionNames = new ArrayList<>();
        // 遍历所有的表情的key
        for (String emojiName : EmotionUtils.EMOTION_STATIC_MAP.keySet()) {
            emotionNames.add(emojiName);
            // 每20个表情作为一组,同时添加到ViewPager对应的view集合中
            if (emotionNames.size() == 23) {
                GridView gv = createEmotionGridView(emotionNames, screenWidth, spacing, itemWidth, gvHeight);
                emotionViews.add(gv);
                // 添加完一组表情,重新创建一个表情名字集合
                emotionNames = new ArrayList<>();
            }
        }

        // 判断最后是否有不足23个表情的剩余情况
        if (emotionNames.size() > 0) {
            GridView gv = createEmotionGridView(emotionNames, screenWidth, spacing, itemWidth, gvHeight);
            emotionViews.add(gv);
        }

        //初始化指示器
        fragmentChatGroup.initIndicator(emotionViews.size());
        // 将多个GridView添加显示到ViewPager中
        emotionPagerAdapter = new EmotionPagerAdapter(emotionViews);
        fragmentChatVp.setAdapter(emotionPagerAdapter);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth, gvHeight);
        fragmentChatVp.setLayoutParams(params);


    }

    /**
     * 创建显示表情的GridView
     */
    private GridView createEmotionGridView(List<String> emotionNames, int gvWidth, int padding, int itemWidth, int gvHeight) {
        // 创建GridView
        GridView gv = new GridView(getActivity());
        //设置点击背景透明
        gv.setSelector(android.R.color.transparent);
        //设置7列
        gv.setNumColumns(8);
        gv.setHorizontalSpacing(padding);
        gv.setVerticalSpacing(padding);
        //设置GridView的宽高
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(gvWidth, gvHeight);
        gv.setLayoutParams(params);
        // 给GridView设置表情图片
        EmotionGridViewAdapter adapter = new EmotionGridViewAdapter(getActivity(), emotionNames, itemWidth);
        gv.setAdapter(adapter);
        //设置全局点击事件
        gv.setOnItemClickListener(GlobalOnItemClickManagerUtils.getInstance(getActivity()).getOnItemClickListener());
        return gv;
    }

}