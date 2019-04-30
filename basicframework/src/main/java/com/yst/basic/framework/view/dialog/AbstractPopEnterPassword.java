package com.yst.basic.framework.view.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yst.basic.framework.R;
import com.yst.basic.framework.listener.OnPasswordInputFinish;
import com.yst.basic.framework.utils.MyLog;
import com.yst.basic.framework.view.PasswordView;

/**
 * 输入支付密码
 *
 * @author lixiangchao
 * @date 2017/11/30
 * @version 1.0.1
 */
public abstract class AbstractPopEnterPassword extends PopupWindow {

    private PasswordView pwdView;

    /**
     * 是否是密码
     */
    public static final String CASH_PAY_TYPE = "Password";

    /**
     * 数字展示
     */
    public static final String CASH_PAY_TYPE_OTHER = "Number";

    private View mMenuView;

    private Activity mContext;

    public AbstractPopEnterPassword(final Activity context, String title, String payType) {

        super(context);

        this.mContext = context;

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mMenuView = inflater.inflate(R.layout.pop_enter_password, null);

        pwdView = mMenuView.findViewById(R.id.pwd_view);
        FrameLayout popwindow = mMenuView.findViewById(R.id.popwindow);

        TextView tv = mMenuView.findViewById(R.id.txt_password_top);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        //添加密码输入完成的响应
        pwdView.setOnFinishInput(new OnPasswordInputFinish() {
            @Override
            public void inputFinish(String password) {
                payFinish(password);
            }
        });
        pwdView.setTitleText(title, payType);
        // 监听X关闭按钮
        pwdView.getImgCancel().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        // 监听键盘上方的返回
        pwdView.getVirtualKeyboardView().getLayoutBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        popwindow.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                MyLog.e("sss", "?tttttt === ");
                return false;
            }
        });

        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.pop_add_ainm);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x66000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
    }

    /**
     * 支付完成回调方法
     *
     * @param password 密码
     */
    public abstract void payFinish(String password);

    /**
     * 关闭弹框
     */
    public void dismissPop() {
        dismiss();
    }
}
