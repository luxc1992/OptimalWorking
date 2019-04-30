package com.yst.im.imchatlibrary.manager;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yst.im.imchatlibrary.R;

import static com.yst.im.imsdk.MessageConstant.NUM_0;
import static com.yst.im.imsdk.MessageConstant.NUM_6;

/**
 * 以下为dialog的初始化控件，包括其中的布局文件
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/2.
 */
public class DialogManager {
    private Dialog mDialog;
    private RelativeLayout imgBg;
    private TextView tipsTxt;
    private RelativeLayout imgBg2;
    private TextView tipsTxt2;
    private Context mContext;

    public DialogManager(Context context) {
        mContext = context;
    }

    public void showRecordingDialog() {
        mDialog = new Dialog(mContext, R.style.Theme_audioDialog);
        // 用layoutinflater来引用布局
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.dialog_manager, null);
        mDialog.setContentView(view);
        imgBg = (RelativeLayout) view.findViewById(R.id.dm_rl_bg);
        tipsTxt = (TextView) view.findViewById(R.id.dm_tv_txt);
        imgBg2 = (RelativeLayout) view.findViewById(R.id.dm_rl_bg2);
        tipsTxt2 = (TextView) view.findViewById(R.id.dm_tv_txt2);
        mDialog.show();
    }

    /**
     * 设置正在录音时的dialog界面
     */
    public void recording() {
        if (mDialog != null && mDialog.isShowing()) {
            imgBg.setVisibility(View.VISIBLE);
            tipsTxt.setVisibility(View.VISIBLE);
            imgBg2.setVisibility(View.GONE);
            tipsTxt2.setVisibility(View.GONE);
            imgBg.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.yuyin_voice_1));
            tipsTxt.setText(R.string.up_for_cancel);
        }
    }

    /**
     * 取消界面
     */
    public void wantToCancel() {
        if (mDialog != null && mDialog.isShowing()) {
            imgBg.setVisibility(View.GONE);
            tipsTxt.setVisibility(View.GONE);
            imgBg2.setVisibility(View.VISIBLE);
            tipsTxt2.setVisibility(View.VISIBLE);
            imgBg2.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.yuyin_cancel));
            tipsTxt2.setText(R.string.want_to_cancle);
            tipsTxt2.setBackgroundColor(mContext.getResources().getColor(R.color.colorRed));
        }
    }

    /**
     *     时间过短
     */
    public void tooShort() {
        if (mDialog != null && mDialog.isShowing()) {
            imgBg2.setVisibility(View.VISIBLE);
            tipsTxt2.setVisibility(View.VISIBLE);
            imgBg.setVisibility(View.GONE);
            tipsTxt.setVisibility(View.GONE);
            imgBg2.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.yuyin_gantanhao));
            tipsTxt2.setText(R.string.time_too_short);
        }

    }

    /**
     *     隐藏dialog
     */
    public void dimissDialog() {

        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }

    }

    public void updateVoiceLevel(int level) {
        if (level > NUM_0 && level < NUM_6) {

        } else {
            level = 5;
        }
        if (mDialog != null && mDialog.isShowing()) {

            //通过level来找到图片的id，也可以用switch来寻址，但是代码可能会比较长
            int resId = mContext.getResources().getIdentifier("yuyin_voice_" + level,
                    "drawable", mContext.getPackageName());
            imgBg.setBackgroundResource(resId);
        }

    }

    public TextView getTipsTxt() {
        return tipsTxt;
    }

    public void setTipsTxt(TextView tipsTxt) {
        this.tipsTxt = tipsTxt;
    }
}
