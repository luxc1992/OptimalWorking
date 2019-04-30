package com.yst.basic.framework.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yst.basic.framework.Const;
import com.yst.basic.framework.R;
import com.yst.basic.framework.adapter.KeyBoardAdapter;
import com.yst.basic.framework.listener.OnPasswordInputFinish;
import com.yst.basic.framework.view.dialog.AbstractPopEnterPassword;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 弹框里面的View
 *
 * @author lixiangchao
 * @date 2017/12/5
 * @version 1.0.1
 */
public class PasswordView extends RelativeLayout {

    Context mContext;

    private VirtualKeyboardView virtualKeyboardView;

    /**
     * 用数组保存6个TextView，为什么用数组？
     */
    private TextView[] tvList;

    /**
     * 用数组保存6个TextView，为什么用数组？
     */
    private ImageView[] imgList;

    private GridView gridView;

    private ImageView imgCancel;

    private TextView tvTitle;

    private ArrayList<Map<String, String>> valueList;

    /**
     * 用于记录当前输入密码格位置
     */
    private int currentIndex = -1;

    private boolean isHide = false;

    public PasswordView(Context context) {
        this(context, null);
    }

    public PasswordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        View view = View.inflate(context, R.layout.layout_popup_bottom, null);

        virtualKeyboardView = view.findViewById(R.id.virtualKeyboardView);
        imgCancel = view.findViewById(R.id.img_cancel);
        tvTitle = view.findViewById(R.id.tv_title);
        gridView = virtualKeyboardView.getGridView();

        initValueList();

        initView(view);

        setupView();

        addView(view);
    }

    private void initView(View view) {
        tvList = new TextView[6];
        imgList = new ImageView[6];

        tvList[0] = view.findViewById(R.id.tv_pass1);
        tvList[1] = view.findViewById(R.id.tv_pass2);
        tvList[2] = view.findViewById(R.id.tv_pass3);
        tvList[3] = view.findViewById(R.id.tv_pass4);
        tvList[4] = view.findViewById(R.id.tv_pass5);
        tvList[5] = view.findViewById(R.id.tv_pass6);

        imgList[0] = view.findViewById(R.id.img_pass1);
        imgList[1] = view.findViewById(R.id.img_pass2);
        imgList[2] = view.findViewById(R.id.img_pass3);
        imgList[3] = view.findViewById(R.id.img_pass4);
        imgList[4] = view.findViewById(R.id.img_pass5);
        imgList[5] = view.findViewById(R.id.img_pass6);
    }

    /**
     * 这里，我们没有使用默认的数字键盘，因为第10个数字不显示.而是空白
     */
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

    /**
     * 设置title
     *
     * @param title
     */
    public void setTitleText(String title, String type) {
        String titleText;
        if (type.equals(AbstractPopEnterPassword.CASH_PAY_TYPE)) {
            if (null == title) {
                titleText = "请输入支付密码";
            } else {
                titleText = title;
            }
            isHide = true;
            for (TextView mView : tvList) {
                mView.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        } else {
            if (null == title) {
                titleText = "";
            } else {
                titleText = "请输入验证码";
            }
            isHide = false;
            for (TextView mView : tvList) {
                mView.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
        }
        tvTitle.setText(titleText);
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
                    StringBuffer stringBuffer = new StringBuffer();
                    for (int i = 0; i < Const.SIX; i++) {
                        stringBuffer.append(tvList[i].getText().toString().trim());
                    }
                    System.out.println("strPassword :" + stringBuffer.toString());
                    //接口中要实现的方法，完成密码输入完成后的响应逻辑
                    pass.inputFinish(stringBuffer.toString());
                }
            }
        });
    }

    public VirtualKeyboardView getVirtualKeyboardView() {
        return virtualKeyboardView;
    }

    public ImageView getImgCancel() {
        return imgCancel;
    }
}
