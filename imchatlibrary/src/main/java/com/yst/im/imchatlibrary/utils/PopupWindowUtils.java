package com.yst.im.imchatlibrary.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yst.im.imchatlibrary.R;
import com.yst.im.imsdk.utils.*;

/**
 * @author Lierpeng
 */
public class PopupWindowUtils extends PopupWindow implements View.OnClickListener {

    private View mMenuView;

    private OnPopWindowClickListener listener;

    private Activity activity;

    private PopWindowType mPopWindowType;

    public enum PopWindowType {
        /**
         * 添加操作
         */
        AddSetting,
        /**
         * 消息操作
         */
        MsgSetting
    }

    public PopupWindowUtils(Activity activity, OnPopWindowClickListener listener, PopWindowType mPopWindowType, boolean isGoneRecall, boolean isGoneCopy) {
        this.activity = activity;
        initView(activity, listener, mPopWindowType, isGoneRecall, isGoneCopy);
        this.mPopWindowType = mPopWindowType;
    }

    public void show(int x, int y) {
        Rect rect = new Rect();
          /*
           * getWindow().getDecorView()得到的View是Window中的最顶层View，可以从Window中获取到该View，
           * 然后该View有个getWindowVisibleDisplayFrame()方法可以获取到程序显示的区域，
           * 包括标题栏，但不包括状态栏。
           */
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int winHeight = activity.getWindow().getDecorView().getHeight();
        this.showAtLocation(activity.getWindow().getDecorView(), Gravity.NO_GRAVITY, x, y);

    }

    public void show(View view, int x, int y) {
//        Rect rect = new Rect();
//          /*
//           * getWindow().getDecorView()得到的View是Window中的最顶层View，可以从Window中获取到该View，
//           * 然后该View有个getWindowVisibleDisplayFrame()方法可以获取到程序显示的区域，
//           * 包括标题栏，但不包括状态栏。
//           */
//        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
//        int winHeight = activity.getWindow().getDecorView().getHeight();
        this.showAsDropDown(view, x, y);

    }


    private void initView(Activity activity, OnPopWindowClickListener listener, PopWindowType mPopWindowType, boolean isGoneRecall, boolean isGoneCopy) {
        //设置按钮监听
        this.listener = listener;
        if (mPopWindowType.equals(PopWindowType.MsgSetting)) {
            initViewMsgSetting(activity, isGoneRecall, isGoneCopy);
        } else {
            initViewAddSetting(activity);
        }
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        if (mPopWindowType.equals(PopWindowType.MsgSetting)) {
            initViewMsgSetting(activity, isGoneRecall, isGoneCopy);
            this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            //设置SelectPicPopupWindow弹出窗体的高
            this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        } else {
            initViewAddSetting(activity);
            this.setWidth(com.yst.im.imsdk.utils.BaseUtils.dip2px(activity, 175));
            //设置SelectPicPopupWindow弹出窗体的高
            this.setHeight(com.yst.im.imsdk.utils.BaseUtils.dip2px(activity, 108));
        }

        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            @SuppressLint("ClickableViewAccessibility")
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.layout_pop).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    /**
     * 添加弹窗（添加好友、发起群聊）
     */
    private void initViewAddSetting(Activity context) {
        LinearLayout mLilNewFriend;
        LinearLayout mLilNewGroup;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.pop_window_add_operation, null);
        mLilNewFriend = (LinearLayout) mMenuView.findViewById(R.id.lil_new_friend);
        mLilNewGroup = (LinearLayout) mMenuView.findViewById(R.id.lil_new_group);
        mLilNewFriend.setOnClickListener(this);
        mLilNewGroup.setOnClickListener(this);
    }

    /**
     * popup 消息弹窗（转发、撤回、复制）
     */
    private void initViewMsgSetting(Activity context, boolean isGoneRecall, boolean isGoneCopy) {
        TextView mTxtPopWindowCopy;
        TextView mTxtPopWindowRecall;
        TextView mTxtPopWindowTransport;
        View mVCopyRecall;
        View mVRecallDelete;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.pop_window_operation, null);
        // 转发
        mTxtPopWindowCopy = (TextView) mMenuView.findViewById(R.id.txt_pop_window_copy);
        //撤回
        mTxtPopWindowRecall = (TextView) mMenuView.findViewById(R.id.txt_pop_window_recall);
        //复制
        mTxtPopWindowTransport = (TextView) mMenuView.findViewById(R.id.txt_pop_window_transport);
        mVCopyRecall = (View) mMenuView.findViewById(R.id.v_copy_recall);
        mVRecallDelete = (View) mMenuView.findViewById(R.id.v_recall_delete);

        mTxtPopWindowCopy.setOnClickListener(this);
        mTxtPopWindowRecall.setOnClickListener(this);
        mTxtPopWindowTransport.setOnClickListener(this);
        if (isGoneCopy) {
            mVCopyRecall.setVisibility(View.GONE);
            mTxtPopWindowTransport.setVisibility(View.GONE);
        }
        if (isGoneRecall) {
            mVRecallDelete.setVisibility(View.GONE);
            mTxtPopWindowRecall.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        listener.onPopWindowClickListener(v);
        dismiss();
    }

    /**
     * 接口
     */
    public interface OnPopWindowClickListener {
        void onPopWindowClickListener(View view);
    }
}