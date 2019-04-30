package com.yst.im.imchatlibrary.widget;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yst.im.imchatlibrary.R;
import com.yst.im.imsdk.utils.BaseUtils;

/**
 * 弹框dig
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/04/11.
 */
public abstract class AbstractImSmsDialog {
    protected Dialog dialog;
    private TextView title;
    private Button sure;
    private Button cancle;

    public AbstractImSmsDialog(Activity activity, boolean isGoneCannel) {
        if (dialog == null) {
            getDialog(activity, isGoneCannel);
        }
    }

    /**
     * 设置dialog属性
     *
     * @param activity 上下文
     * @return
     */
    private Dialog getDialog(Activity activity, boolean isGoneCannel) {
        View view = activity.getLayoutInflater().inflate(R.layout.dialog_groups, null);
        dialog = new Dialog(activity, R.style.prompt_progress_dailog_dimenabled);
        dialog.setContentView(view, new LinearLayout.LayoutParams
                (BaseUtils.dip2px(activity, 280), BaseUtils.dip2px(activity, 135)));
        title = (TextView) view.findViewById(R.id.title);
        sure = (Button) view.findViewById(R.id.sure);
        cancle = (Button) view.findViewById(R.id.cancle);
        if (isGoneCannel) {
            cancle.setVisibility(View.GONE);
        }
        Window dialogWindow = dialog.getWindow();
        android.view.WindowManager.LayoutParams lp = dialogWindow
                .getAttributes();
        // 设置生效
        dialogWindow.setGravity(Gravity.TOP);
        // lp.x = 266; // 新位置X坐标
        // 新位置Y坐标
        lp.y = BaseUtils.dip2px(activity, 260);
        dialogWindow.setAttributes(lp);
        sure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                sureClick();
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                cancleClick();
            }
        });
        return dialog;
    }

    public void sureClick() {
    }

    ;

    public void cancleClick() {
    }

    ;

    public void setText(String s1, String s2, String s3) {
        title.setText(s1);
        sure.setText(s2);
        cancle.setText(s3);
    }

    public void setTextColor(int color1, int color2, int color3) {
        title.setTextColor(color1);
        sure.setTextColor(color2);
        cancle.setTextColor(color3);
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
