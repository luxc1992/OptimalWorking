package com.yst.onecity.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Point;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.yst.basic.framework.utils.ToastUtils;
import com.yst.onecity.R;
import com.yst.onecity.bean.CityBean;
import com.yst.onecity.bean.DiQuBean;
import com.yst.onecity.bean.ProvinceBean;
import com.yst.onecity.utils.ParserTool;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 地址弹出框
 *
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/2/24
 */
public abstract class AbstractAddressDialog {
    protected Dialog dialog;
    private EditText mName;
    private EditText mDetail;
    private EditText mPhone;
    private TextView mQueding;
    private TextView mQixiao;
    private Spinner mSp1;
    private Spinner mSp2;
    private Spinner mSp3;

    private List<ProvinceBean> provs;
    private int proPosition;

    private ArrayAdapter<ProvinceBean> proAdapter;
    private ArrayAdapter<CityBean> cityAdapter;
    private ArrayAdapter<DiQuBean> diquAdapter;

    protected AbstractAddressDialog(Activity activity) {
        if (dialog == null) {
            getDialog(activity);
        }
    }

    private Dialog getDialog(Activity activity) {
        View view = activity.getLayoutInflater().inflate(R.layout.dialog_address, null);
        dialog = new Dialog(activity, R.style.prompt_progress_dailog_dimenabled);
        dialog.setContentView(view,
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        mName = view.findViewById(R.id.et_dialog_name);
        mDetail = view.findViewById(R.id.et_dialog_detail);
        mPhone = view.findViewById(R.id.et_dialog_phone);
        mQueding = view.findViewById(R.id.txt_dialog_queding);
        mQixiao = view.findViewById(R.id.txt_dialog_quxiao);
        mSp1 = view.findViewById(R.id.sp1_dialog_address);
        mSp2 = view.findViewById(R.id.sp2_dialog_address);
        mSp3 = view.findViewById(R.id.sp3_dialog_address);

        Window dialogWindow = dialog.getWindow();
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        Point outSize = new Point();
        display.getSize(outSize);
        //得到屏幕的宽度
        int screenWidth = outSize.x;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        //设置宽度
        lp.width = (int) (screenWidth * 0.9);
        dialogWindow.setGravity(Gravity.CENTER);
        dialog.getWindow().setAttributes(lp);
        //实现spinner下拉框效果
        setSpinner(activity);

        mQueding.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = mName.getText().toString().trim();
                String detail = mDetail.getText().toString().trim();
                String phone = mPhone.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    ToastUtils.show("请输入收货人姓名");
                    return;
                }
                if (TextUtils.isEmpty(detail)) {
                    ToastUtils.show("请输入详细地址");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    ToastUtils.show("请输入联系电话");
                    return;
                }
                dialog.dismiss();
                sureClick(name, detail, phone);
            }
        });

        mQixiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return dialog;
    }

    private void setSpinner(final Activity activity) {
        //得到解析后的数据
        InputStream in = activity.getResources().openRawResource(R.raw.city);
        try {
            provs = ParserTool.parserProvince(in);
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        proAdapter = new ArrayAdapter<ProvinceBean>(activity, R.layout.custom_spinner_txt, provs);
        mSp1.setAdapter(proAdapter);
        mSp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                proPosition = position;
                //设置市的适配器
                cityAdapter = new ArrayAdapter<CityBean>(activity, R.layout.custom_spinner_txt, provs.get(position).getCitys());
                mSp2.setAdapter(cityAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                diquAdapter = new ArrayAdapter<DiQuBean>(activity, R.layout.custom_spinner_txt, provs.get(proPosition).getCitys().get(position).getDiQus());
                mSp3.setAdapter(diquAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * 确认按钮
     *
     * @param name   收货人姓名
     * @param detail 详细地址
     * @param phone  联系电话
     */
    public abstract void sureClick(String name, String detail, String phone);

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
