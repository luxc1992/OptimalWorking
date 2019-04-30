package com.yst.onecity.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yst.basic.framework.utils.MyLog;
import com.yst.basic.framework.utils.WindowUtils;
import com.yst.onecity.R;

/**
 * 首页资讯收藏 举报popwindow
 *
 * @author chenjiadi
 * @version 1.0.1
 * @date 2018/2/7
 */
public class InfoCollectPopwindow extends PopupWindow implements View.OnClickListener {

    private View mPopView;
    private Activity mcontext;
    private OnListener listener;
    private TextView tvReport;
    private TextView tvCollect;
    private int popupHeight;
    private int popupWidth;

    public InfoCollectPopwindow(final Activity context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mPopView = inflater.inflate(R.layout.pop_home_collect_layout, null);
        //获取自身的长宽高
        mPopView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupHeight = mPopView.getMeasuredHeight();
        popupWidth = mPopView.getMeasuredWidth();
        this.mcontext = context;
        tvReport = mPopView.findViewById(R.id.tv_report);
        tvCollect = mPopView.findViewById(R.id.tv_collect);
        tvReport.setOnClickListener(this);
        tvCollect.setOnClickListener(this);
        this.setContentView(mPopView);
        this.setWidth(LayoutParams.WRAP_CONTENT);
        this.setHeight(LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.update();
        ColorDrawable dw = new ColorDrawable(10222222);
        this.setBackgroundDrawable(dw);
        this.setAnimationStyle(R.style.AnimationPreview);
    }

    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            this.showAsDropDown(parent, WindowUtils.dip2px(mcontext, -65), 0);
        } else {
            this.dismiss();
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void showPopupWindowBelove(View v) {
        if (!this.isShowing()) {
            int[] location = new int[2];
            v.getLocationOnScreen(location);
            //在控件上方显示
            showAtLocation(v, Gravity.NO_GRAVITY, WindowUtils.getScreenWidth(mcontext) / 2 - popupWidth / 2, location[1] - popupHeight - 60);

        } else {
            this.dismiss();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_report:
                if (listener != null) {
                    MyLog.e("sssss", "listener != null");

                    listener.onListener("report");
                    dismiss();


                }
                break;
            case R.id.tv_collect:
                if (listener != null) {
                    MyLog.e("sssss", "listener != null");

                    listener.onListener("collect");
                    dismiss();


                }
                break;
            default:
                break;
        }
    }

    public interface OnListener {
        /**
         * 举报 收藏
         *
         * @param cartflag 标识
         */
        void onListener(String cartflag);

    }

    public void setListener(OnListener listener) {
        this.listener = listener;
    }

}
