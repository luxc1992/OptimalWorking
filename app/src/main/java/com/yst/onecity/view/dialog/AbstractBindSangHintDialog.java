package com.yst.onecity.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yst.basic.framework.utils.WindowUtils;
import com.yst.onecity.R;

import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO2;
import static com.yst.onecity.Constant.NO3;

/**
 * 三方登录提示框
 *
 * @author chejianqi
 * @version 1.0.1
 * @date 2018/3/7
 */

public abstract class AbstractBindSangHintDialog {
    private Dialog dialog;
    private Activity activity;
    private TextView title, qqTvName, weChatTvName;
    private Button sure;
    private String qqName;
    private String weChatName;
    private LinearLayout qqLy, weChatLy;
    private int type;

    public AbstractBindSangHintDialog(Activity activity, String qqName, String weChatName, int type) {
        this.activity = activity;
        this.qqName = qqName;
        this.weChatName = weChatName;
        this.type = type;
        if (dialog == null) {
            getDialog(activity);
        }
    }

    private void getDialog(Activity activity) {
        View view = activity.getLayoutInflater().inflate(R.layout.dialog_sanfanghint, null);
        dialog = new Dialog(activity, R.style.prompt_progress_dailog_dimenabled);
        dialog.setCancelable(false);
        dialog.setContentView(view, new LinearLayout.LayoutParams
                (WindowUtils.dip2px(activity, 265), WindowUtils.dip2px(activity, 165)));
        title = view.findViewById(R.id.title);
        sure = view.findViewById(R.id.sure);
        qqTvName = view.findViewById(R.id.qq_name);
        weChatTvName = view.findViewById(R.id.wechat_name);
        qqLy = view.findViewById(R.id.qq_ly);
        weChatLy = view.findViewById(R.id.wechat_ly);
        Window dialogWindow = dialog.getWindow();
        if (dialogWindow != null) {
            android.view.WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            dialogWindow.setGravity(Gravity.CENTER);
            dialogWindow.setAttributes(lp);
        }
        sure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                sureClick();
            }
        });

        if (type == NO1) {
            qqTvName.setText(qqName);
            weChatLy.setVisibility(View.GONE);
            qqLy.setVisibility(View.VISIBLE);
        } else if (type == NO2) {
            qqLy.setVisibility(View.GONE);
            weChatLy.setVisibility(View.VISIBLE);
            weChatTvName.setText(weChatName);
        } else if (type == NO3) {
            qqLy.setVisibility(View.VISIBLE);
            weChatLy.setVisibility(View.VISIBLE);
            qqTvName.setText(qqName);
            weChatTvName.setText(weChatName);
        }
    }

    /**
     * 确认按钮
     */
    public abstract void sureClick();

    /**
     * 设置弹出框中的文字
     *
     * @param s1 标题
     * @param s2 确认
     */
    public void setText(String s1, String s2) {
        title.setText(s1);
        sure.setText(s2);

    }

    public void showDialog() {
        dialog.show();
    }

    public void dismissDialog() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }
}
