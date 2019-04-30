package com.yst.onecity.fragment.issue;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseFragment;
import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.R;
import com.yst.onecity.bean.issue.TemplateBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;

import butterknife.BindView;

import static com.yst.onecity.Constant.NO1;

/**
 * 卖家承担运费
 * 运费模板页面
 *
 * @author zhaiyanwu
 * @version 1.0.1
 * @date 2018/2/23
 */

public class SellerAssumeFreightFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.btn_affirm)
    Button affirm;
    private int content;
    private EditText etName;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_seller_assume_freight;
    }

    @Override
    public void init() {
        etName = (EditText) getActivity().findViewById(R.id.et_name);

        Bundle arguments = getArguments();
        if (arguments != null) {
            content = arguments.getInt("content", 0);
            if (content!=0){
                etName.setFocusable(false);
            }
        }
    }

    @Override
    protected void setListener() {
        super.setListener();
        affirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_affirm:
                if (content != 0) {
                    getActivity().finish();
                    return;
                }
                if (!Utils.isClickable()) {
                    return;
                }
                if (TextUtils.isEmpty(etName.getText().toString())) {
                    ToastUtils.show("请输入运费模板名称");
                    return;
                }
                RequestApi.addFreeEstablishFreight(etName.getText().toString(), "1", 0, App.manager.getPhoneNum(), App.manager.getUuid(), new AbstractNetWorkCallback<TemplateBean>() {
                    @Override
                    public void onSuccess(TemplateBean msgBean) {
                        if (msgBean != null && msgBean.getCode() == NO1) {
                            getActivity().finish();
                        }
                        assert msgBean != null;
                        ToastUtils.show(msgBean.getMsg());
                    }

                    @Override
                    public void onError(String errorMsg) {
                        ToastUtils.show(errorMsg);
                    }

                    @Override
                    public void onBefore() {
                        super.onBefore();
                        showInfoProgressDialog();
                    }

                    @Override
                    public void onAfter() {
                        super.onAfter();
                        dismissInfoProgressDialog();
                    }
                });
                break;
            default:
                break;
        }
    }
}
