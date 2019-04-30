package com.yst.im.imchatlibrary.widget;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yst.im.imchatlibrary.R;
import com.yst.im.imsdk.utils.BaseUtils;


/**
 * 收费弹框
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/04/11.
 */
public abstract class AbstractImChargeDialog {
    protected Dialog dialog;
    private TextView title;
    private TextView yesNo;
    private EditText edtPwdVerify;
    private View viewLineCancle;
    private Button sure;
    private Button cancle;
    private boolean isEdteVisb = false;

    public AbstractImChargeDialog(Activity activity) {
        if (dialog == null) {
            getDialog(activity);
        }
    }

    /**
     * 设置dialog属性
     *
     * @param activity
     * @return
     */
    private Dialog getDialog(Activity activity) {
        View view = activity.getLayoutInflater().inflate(R.layout.dialog_tips, null);
        dialog = new Dialog(activity, R.style.prompt_progress_dailog_dimenabled);
        dialog.setContentView(view, new LinearLayout.LayoutParams
                (BaseUtils.dip2px(activity, 250), LinearLayout.LayoutParams.WRAP_CONTENT));
        title = view.findViewById(R.id.title);
        yesNo = view.findViewById(R.id.yesNo);
        sure = view.findViewById(R.id.sure);
        cancle = view.findViewById(R.id.cancle);
        edtPwdVerify = view.findViewById(R.id.edt_pwd_verify);
        viewLineCancle = view.findViewById(R.id.view_line_cancle);
        Window dialogWindow = dialog.getWindow();
        android.view.WindowManager.LayoutParams lp = dialogWindow
                .getAttributes();
        // 设置生效
        dialogWindow.setGravity(Gravity.CENTER);
        // lp.x = 266; // 新位置X坐标
        lp.width = BaseUtils.dip2px(activity, 250);
        lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);

        sure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (!isEdteVisb) {
                    dialog.dismiss();
                }
                sureClick();
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                closeClick();
            }
        });
        return dialog;
    }

    public void sureClick() {
    }

    ;

    public void closeClick() {
    }

    ;

    public void setText(String title, String yesNo) {
        this.title.setText(title);
        this.yesNo.setText(yesNo);
    }

    public EditText getEditText() {
        return this.edtPwdVerify;
    }

    public void setIsVisiable(boolean isTitle, boolean isCancleBtn) {
        if (!isTitle) {
            title.setVisibility(View.GONE);
        } else {
            title.setVisibility(View.VISIBLE);
        }
        if (!isCancleBtn) {
            cancle.setVisibility(View.GONE);
            viewLineCancle.setVisibility(View.GONE);
        } else {
            cancle.setVisibility(View.VISIBLE);
            viewLineCancle.setVisibility(View.VISIBLE);
        }
    }

    public void setIsVisiable(boolean isTitle, boolean isCancleBtn, boolean isEdteVisb) {
        if (!isTitle) {
            title.setVisibility(View.GONE);
        } else {
            title.setVisibility(View.VISIBLE);
        }
        if (!isCancleBtn) {
            cancle.setVisibility(View.GONE);
            viewLineCancle.setVisibility(View.GONE);
        } else {
            cancle.setVisibility(View.VISIBLE);
            viewLineCancle.setVisibility(View.VISIBLE);
        }
        this.isEdteVisb = isEdteVisb;
        if (!isEdteVisb) {
            yesNo.setVisibility(View.VISIBLE);
            edtPwdVerify.setVisibility(View.GONE);
        } else {
            yesNo.setVisibility(View.INVISIBLE);
            edtPwdVerify.setVisibility(View.VISIBLE);
        }
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
