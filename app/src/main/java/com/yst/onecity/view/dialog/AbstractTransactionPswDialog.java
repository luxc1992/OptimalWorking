package com.yst.onecity.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.yst.basic.framework.Const;
import com.yst.basic.framework.adapter.KeyBoardAdapter;
import com.yst.basic.framework.listener.OnPasswordInputFinish;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.view.VirtualKeyboardView;
import com.yst.onecity.R;
import com.yst.onecity.activity.mine.accountsafe.SetLoginPwdActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 输入支付密码弹出框
 *
 * @author chejianqi
 * @version 1.0.1
 * @date 2018/2/27
 */

public abstract class AbstractTransactionPswDialog {
    public Dialog dialog;
    private VirtualKeyboardView virtualKeyboardView;
    /**
     * 用数组保存6个TextView，为什么用数组？
     */
    private TextView[] tvList;
    private Context mContext;
    /**
     * 用数组保存6个TextView，为什么用数组？
     */
    private ImageView[] imgList;

    private GridView gridView;

    private ImageView imgCancel;

    private TextView tvTitle;

    private ArrayList<Map<String, String>> valueList;

    private String money;

    private TextView transactionMoney;

    private TextView serviceCharge;

    private String serviceChargeTxt = "0.01";

    private TextView tvForget;
    /**
     * 用于记录当前输入密码格位置
     */
    private int currentIndex = -1;

    private boolean isHide = false;

    public AbstractTransactionPswDialog(Activity activity, String money ,String singleFee) {
        this.money = money;
        this.serviceChargeTxt=singleFee;
        if (dialog == null) {
            getDialog(activity);

        }
    }

    private void getDialog(Activity activity) {
        this.mContext = activity;
        View view = activity.getLayoutInflater().inflate(R.layout.transactionpsw_dialog, null);
        dialog = new Dialog(activity, R.style.transaction_psw_dailog_dimenabled);

        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();

        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);

        tvForget=view.findViewById(R.id.tv_forget);
        virtualKeyboardView = view.findViewById(R.id.virtualKeyboardView);
        imgCancel = view.findViewById(R.id.img_cancel);
        tvTitle = view.findViewById(R.id.tv_title);
        transactionMoney = view.findViewById(R.id.transaction_money);
        gridView = virtualKeyboardView.getGridView();
        serviceCharge = view.findViewById(R.id.service_charge);
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                closeClick();
            }
        });
        transactionMoney.setText("¥" + money);

        serviceCharge.setText(String.format("额外扣除¥%1$s手续费", serviceChargeTxt));
        initValueList();

        initView(view);

        setupView();

        tvForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle =new Bundle();
                bundle.putInt("type",4);
                JumpIntent.jump((Activity) mContext, SetLoginPwdActivity.class, bundle);
            }
        });
    }

    /**
     * 取消按钮
     */
    public abstract void closeClick();

    public void showDialog() {
        dialog.show();
    }
    public void dismissDialog() {
        dialog.dismiss();
    }
    private void setupView() {
        // 这里、重新为数字键盘gridView设置了Adapter
        KeyBoardAdapter keyBoardAdapter = new KeyBoardAdapter(mContext, valueList);
        gridView.setAdapter(keyBoardAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击0~9按钮
                if (position < Const.ELEVEN && position != Const.NINE) {
                    //判断输入位置————要小心数组越界
                    if (currentIndex >= -1 && currentIndex < Const.FIVE) {
                        ++currentIndex;
                        tvList[currentIndex].setText(valueList.get(position).get("name"));
                        if (isHide) {
                            tvList[currentIndex].setVisibility(View.INVISIBLE);
                            imgList[currentIndex].setVisibility(View.VISIBLE);
                        }
                    }
                } else {
                    //点击退格键
                    if (position == Const.ELEVEN) {
                        //判断是否删除完毕————要小心数组越界
                        if (currentIndex - 1 >= -1) {
                            tvList[currentIndex].setText("");
                            if (isHide) {
                                tvList[currentIndex].setVisibility(View.VISIBLE);
                                imgList[currentIndex].setVisibility(View.INVISIBLE);
                            }
                            currentIndex--;
                        }
                    }
                }
            }
        });

    }

    private void initView(View view) {
        tvList = new TextView[6];
        imgList = new ImageView[6];

        tvList[0] = view.findViewById(com.yst.basic.framework.R.id.tv_pass1);
        tvList[1] = view.findViewById(com.yst.basic.framework.R.id.tv_pass2);
        tvList[2] = view.findViewById(com.yst.basic.framework.R.id.tv_pass3);
        tvList[3] = view.findViewById(com.yst.basic.framework.R.id.tv_pass4);
        tvList[4] = view.findViewById(com.yst.basic.framework.R.id.tv_pass5);
        tvList[5] = view.findViewById(com.yst.basic.framework.R.id.tv_pass6);

        imgList[0] = view.findViewById(com.yst.basic.framework.R.id.img_pass1);
        imgList[1] = view.findViewById(com.yst.basic.framework.R.id.img_pass2);
        imgList[2] = view.findViewById(com.yst.basic.framework.R.id.img_pass3);
        imgList[3] = view.findViewById(com.yst.basic.framework.R.id.img_pass4);
        imgList[4] = view.findViewById(com.yst.basic.framework.R.id.img_pass5);
        imgList[5] = view.findViewById(com.yst.basic.framework.R.id.img_pass6);
    }

    private void initValueList() {
        valueList = new ArrayList<>();
        // 初始化按钮上应该显示的数字
        for (int i = 1; i < Const.BLACK_NUMBER; i++) {
            Map<String, String> map = new HashMap<>(16);
            if (i < 10) {
                map.put("name", String.valueOf(i));
            } else if (i == 10) {
                map.put("name", "");
            } else if (i == 11) {
                map.put("name", String.valueOf(0));
            } else if (i == 12) {
                map.put("name", "");
            }
            valueList.add(map);
        }
    }

    /**
     * 设置监听方法，在第6位输入完成后触发
     *
     * @param pass
     */
    public void setOnFinishInput(final OnPasswordInputFinish pass) {
        tvList[5].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1) {
                    //每次触发都要先将strPassword置空，再重新获取，避免由于输入删除再输入造成混乱
                    String strPassword = "";
                    for (int i = 0; i < Const.SIX; i++) {
                        strPassword += tvList[i].getText().toString().trim();
                    }
                    System.out.println("strPassword :" + strPassword);
                    //接口中要实现的方法，完成密码输入完成后的响应逻辑
                    pass.inputFinish(strPassword);
                }
            }
        });
    }

    public void cleanPasswrod(){
        tvList[0].setText("");
        tvList[1].setText("");
        tvList[2].setText("");
        tvList[3].setText("");
        tvList[4].setText("");
        tvList[5].setText("");
        currentIndex=-1;
    }
}
