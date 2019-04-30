package com.yst.onecity.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.utils.KeyBoardUtils;
import com.yst.onecity.R;
import com.yst.onecity.view.ContainsEmojiEditText;

/**
 * 发表回复弹出框
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/2/28
 */
public class SendCommentDialog {
    private Activity context;
    private Dialog dialog;
    private ContainsEmojiEditText etComment;
    private String send;

    public SendCommentDialog(Activity context) {
        this.context = context;
        initDialog(context);
    }

    public SendCommentDialog(Activity context, String send) {
        this.context = context;
        this.send = send;
        initDialog(context);
    }

    /**
     * 初始化Dialog
     *
     * @param context context
     */
    private void initDialog(Activity context) {
        if (dialog == null) {
            dialog = new Dialog(context);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_send_cmment);

            Window window = dialog.getWindow();
            if (window != null) {
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                window.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(context, R.color.white)));
                window.setGravity(Gravity.BOTTOM);
                window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                initView(window);
            }
        }
    }

    /**
     * 初始化控件
     *
     * @param window window
     */
    private void initView(Window window) {
        TextView tvSend = window.findViewById(R.id.tv_send);
        if (send != null) {
            tvSend.setText(send);
        }
        etComment = window.findViewById(R.id.et_comment);
        etComment.setFilters(new InputFilter[]{new InputFilter.LengthFilter(200)});
        etComment.requestFocus();
        etComment.setFocusable(true);
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInputFromInputMethod(etComment.getWindowToken(), 0);
        }
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = etComment.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    ToastUtils.show("请输入评论内容");
                    return;
                }
                sendComment.addComment(content);
                KeyBoardUtils.closeKeybord(etComment, context);
                etComment.setText("");
                dialog.dismiss();
            }
        });
    }

    public interface SendCommentListener {
        /**
         * 添加评论
         *
         * @param content 评论内容
         */
        void addComment(String content);
    }

    private SendCommentListener sendComment;

    public void commentListener(SendCommentListener sendComment) {
        this.sendComment = sendComment;
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }
}
