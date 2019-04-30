package com.yst.onecity.activity.issue;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.onecity.R;
import com.yst.onecity.bean.issue.FreightRuleContentBean;
import com.yst.onecity.fragment.issue.CustomFreightTemplateFragment;
import com.yst.onecity.fragment.issue.SellerAssumeFreightFragment;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;

import java.util.List;

import butterknife.BindView;

/**
 * 运费模板页面
 *
 * @author zhaiyanwu
 * @version 1.0.1
 * @date 2018/02/24
 */
public class EstablishFreightTemplateActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.frame_freight_template)
    FrameLayout freightTemplate;
    @BindView(R.id.radio_is_confirm)
    RadioGroup isConfirm;
    @BindView(R.id.linear_freight_type)
    LinearLayout freightType;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.radio_but_custom)
    RadioButton butCustom;
    @BindView(R.id.radio_but_standard)
    RadioButton butStandard;
    private SellerAssumeFreightFragment assumeFreightFragment;
    private CustomFreightTemplateFragment customFreightTemplateFragment;
    private Bundle extras;

    @Override
    public int getLayoutId() {
        return R.layout.activity_establish_freight_template;
    }

    @Override
    public void initData() {
        initTitleBar("运费模板");
        extras = getIntent().getExtras();
        if (extras != null) {
            Long id = extras.getLong("id", 0);
            getFreightRuleContent(id);
        } else {
            assumeFreightFragment = new SellerAssumeFreightFragment();
            customFreightTemplateFragment = new CustomFreightTemplateFragment();
            setFragment(assumeFreightFragment);
        }
    }

    /**
     * 运费模板详情接口
     */
    private void getFreightRuleContent(Long id) {
        RequestApi.getFreightRuleContent(id + "", App.manager.getPhoneNum(), App.manager.getUuid(), new AbstractNetWorkCallback<FreightRuleContentBean>() {
            @Override
            public void onSuccess(FreightRuleContentBean freightRuleContentBean) {
                if (1 == freightRuleContentBean.getCode()) {
                    FreightRuleContentBean.ContentBean content = freightRuleContentBean.getContent();
                    setFreightRuleContent(content);
                } else {
                    ToastUtils.show(freightRuleContentBean.getMsg());
                }
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
    }

    /**
     * 设置运费模板回显
     *
     * @param content 数据bean
     */
    private void setFreightRuleContent(FreightRuleContentBean.ContentBean content) {
        //设置RadioGroup不可以点击
        for (int i = 0; i < isConfirm.getChildCount(); i++) {
            isConfirm.getChildAt(i).setEnabled(false);
        }
            String name = content.getName();
        if (!TextUtils.isEmpty(name)) {
            etName.setText(name);
        }
        List<FreightRuleContentBean.ContentBean.FerighListBean> ferighList = content.getFerighList();
        if (ferighList == null) {
            Bundle bundle = new Bundle();
            bundle.putInt("content", 1);
            assumeFreightFragment = new SellerAssumeFreightFragment();
            customFreightTemplateFragment = new CustomFreightTemplateFragment();
            assumeFreightFragment.setArguments(bundle);
            setFragment(assumeFreightFragment);
            freightType.setVisibility(View.GONE);
        } else {
            for (int i = 0; i < ferighList.size(); i++) {
                final FreightRuleContentBean.ContentBean.FerighListBean ferighListBean = ferighList.get(i);
                //是否包邮
                int isFreeShipping = ferighListBean.getIsFreeShipping();
                switch (isFreeShipping) {
                    case 0:
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("content", ferighListBean);
                        assumeFreightFragment = new SellerAssumeFreightFragment();
                        customFreightTemplateFragment = new CustomFreightTemplateFragment();
                        customFreightTemplateFragment.setArguments(bundle);
                        butCustom.setChecked(true);
                        setFragment(customFreightTemplateFragment);
                        freightType.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public void setListener() {
        super.setListener();
        isConfirm.setOnCheckedChangeListener(this);
    }

    /**
     * FragmentTransaction
     */
    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_freight_template, fragment);
        fragmentTransaction.commit();
    }

    /**
     * 监听
     *
     * @param radioGroup
     * @param i
     */
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            //自定义承担运费
            case R.id.radio_but_custom:
                setFragment(customFreightTemplateFragment);
                freightType.setVisibility(View.VISIBLE);
                break;
            //卖家承担运费
            case R.id.radio_but_standard:
                setFragment(assumeFreightFragment);
                freightType.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

}
