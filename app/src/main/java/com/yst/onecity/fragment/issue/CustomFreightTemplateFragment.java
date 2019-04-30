package com.yst.onecity.fragment.issue;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseFragment;
import com.yst.basic.framework.utils.MyLog;
import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.R;
import com.yst.onecity.bean.issue.TemplateBean;
import com.yst.onecity.bean.issue.FreightRuleContentBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

import static com.yst.onecity.Constant.NO1;

/**
 * 自定义承担运费
 * 运费模板页面
 *
 * @author zhaiyanwu
 * @version 1.0.1
 * @date 2018/2/25
 */
public class CustomFreightTemplateFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.btn_affirm)
    Button affirm;
    @BindView(R.id.edt_import_piece)
    EditText edtImportPiece;
    @BindView(R.id.edt_import_money)
    EditText edtImportMoney;
    @BindView(R.id.edt_import_piece_s)
    EditText edtImportPieceS;
    @BindView(R.id.edt_import_money_s)
    EditText edtImportMoneyS;
    /**
     * 运费规则
     */
    private String freightRuleJson;
    private FreightRuleContentBean.ContentBean.FerighListBean ferighListBean;
    private EditText etName;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_custom_freight_template;
    }

    @Override
    public void init() {
        etName = (EditText) getActivity().findViewById(R.id.et_name);

        Bundle arguments = getArguments();
        if (arguments != null) {
            ferighListBean = (FreightRuleContentBean.ContentBean.FerighListBean) arguments.getSerializable("content");
            if (ferighListBean != null) {
                //首价
                int firstPrice = ferighListBean.getFirstPrice();
                //增加的运费
                int increaseFreight = ferighListBean.getIncreaseFreight();
                //运费计算方式
                int firstProduct = ferighListBean.getFirstProduct();
                //超过后的运费计数方式
                int nextProduct = ferighListBean.getNextProduct();
                edtImportPiece.setText(firstProduct + "");
                edtImportMoneyS.setText(increaseFreight + "");
                edtImportMoney.setText(firstPrice + "");
                edtImportPieceS.setText(nextProduct + "");
                etName.setFocusable(false);
                edtImportPiece.setFocusable(false);
                edtImportMoneyS.setFocusable(false);
                edtImportMoney.setFocusable(false);
                edtImportPieceS.setFocusable(false);
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
                if (ferighListBean != null) {
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
                if (TextUtils.isEmpty(edtImportPiece.getText().toString())) {
                    ToastUtils.show("请输入默认运费");
                    return;
                }
                if (TextUtils.isEmpty(edtImportMoney.getText().toString())) {
                    ToastUtils.show("请输入默认价格");
                    return;
                }
                if (TextUtils.isEmpty(edtImportPieceS.getText().toString())) {
                    ToastUtils.show("请输入每增加的件数");
                    return;
                }
                if (TextUtils.isEmpty(edtImportMoneyS.getText().toString())) {
                    ToastUtils.show("请输入增加运费金额");
                    return;
                }
                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("firstProduct", edtImportPiece.getText().toString());
                    jsonObject.put("firstPrice", edtImportMoney.getText().toString());
                    jsonObject.put("nextProduct", edtImportPieceS.getText().toString());
                    jsonObject.put("increaseFreight", edtImportMoneyS.getText().toString());
                    jsonArray.put(jsonObject);
                    freightRuleJson = jsonArray.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (TextUtils.isEmpty(freightRuleJson)) {
                    return;
                }
                MyLog.e("guize", "guize------" + freightRuleJson);
                RequestApi.addEstablishFreight(etName.getText().toString(), "0", 0, freightRuleJson, App.manager.getPhoneNum(), App.manager.getUuid(), new AbstractNetWorkCallback<TemplateBean>() {
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
