package com.yst.im.imchatlibrary.widget;

import android.app.Activity;
import android.app.Dialog;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.utils.CommonsUtils;

/**
 * 通用dialog 弹窗
 *
 * @author luxuchang
 * @version 1.0.1
 * @date 2018/1/3.
 */

public abstract class AbstractIMCommonDialog {
    protected Dialog dialog;
    private TextView title, content;
    private static EditText editText;
    private TextView sure;
    private TextView cancle;

    public AbstractIMCommonDialog(Activity activity) {
        if (dialog == null) {
            getDialog(activity);
        }
    }


    /**
     * 初始化  dialog
     *
     * @param activity 上下文
     * @return dialog
     */
    private Dialog getDialog(Activity activity) {
        View view = activity.getLayoutInflater().inflate(R.layout.im_common_dialog, null);
        dialog = new Dialog(activity, R.style.prompt_progress_dailog_dimenabled);
        dialog.setContentView(view, new LinearLayout.LayoutParams
                (CommonsUtils.dip2px(activity, 280), CommonsUtils.dip2px(activity, 155)));
        title = view.findViewById(R.id.title);
        content = view.findViewById(R.id.content);
        editText = (ImContainsEmojiEditText) view.findViewById(R.id.edit_text);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        sure = view.findViewById(R.id.sure);
        cancle = view.findViewById(R.id.cancle);

        Window dialogWindow = dialog.getWindow();
        android.view.WindowManager.LayoutParams lp = dialogWindow
                .getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setAttributes(lp);

        sure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sureClick();
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return dialog;
    }

    /**
     * 获取输入框内容
     *
     * @return 内容
     */
    public static String getEditTextContent() {
        if (editText == null) {
            return "";
        }
        return editText.getText().toString().trim();
    }

    /**
     * 确认
     */
    public abstract void sureClick();


    /**
     * 设置文本
     *
     * @param strTitle   dialog 标题
     * @param strContent dialog 提示内容  为 null 时 不显示提示内容  显示输入框
     * @param strSure    确定按钮文字
     * @param strCancel  取消按钮文字
     */
    public void setText(String strTitle, String strContent, String etContent, String strSure, String strCancel) {
        title.setText(strTitle);
        if (strContent == null) {
            content.setVisibility(View.GONE);
            editText.setVisibility(View.VISIBLE);
            editText.setText(etContent);
            editText.setSelection(etContent.length());
        } else {
            editText.setVisibility(View.GONE);
            content.setText(strContent);
        }
        sure.setText(strSure);
        cancle.setText(strCancel);
    }


    /**
     * 设置 dialog 弹窗 确定按钮 和 取消按钮   颜色
     *
     * @param sureColor   确定按钮颜色
     * @param cancelColor 取消按钮颜色
     */
    public void setButtonColor(int sureColor, int cancelColor) {
        sure.setTextColor(sureColor);
        cancle.setTextColor(cancelColor);
    }

    /**
     * 显示dialog 弹窗
     */
    public void showDialog() {
        dialog.show();
    }

    /**
     * 隐藏dialog 弹窗
     */
    public void dismissDialog() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }
}
