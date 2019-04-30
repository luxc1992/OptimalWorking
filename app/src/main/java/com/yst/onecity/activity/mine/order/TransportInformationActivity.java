package com.yst.onecity.activity.mine.order;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.Utils;
import com.yst.basic.framework.utils.WindowUtils;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.LogisticsCompanyBean;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.view.SpinnerPopWindow;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;

/**
 * 物流单号页面
 *
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/02/22
 */
public class TransportInformationActivity extends BaseActivity implements AdapterView.OnItemClickListener, PopupWindow.OnDismissListener {
    @BindView(R.id.tv_right)
    TextView mTvRight;
    @BindView(R.id.et_trans_code)
    EditText mEtTransCode;
    @BindView(R.id.txt_trans_commit)
    TextView mTxtTransCommit;
    @BindView(R.id.txt_trans_company)
    TextView mTxtTransCompany;
    @BindView(R.id.iv_trans_triangle)
    ImageView mIvTransTriangle;
    @BindView(R.id.rel_trans_company)
    RelativeLayout mRelTransCompany;

    private SpinnerPopWindow mSpinnerPopWindow;
    private List<LogisticsCompanyBean.ContentBean> mData = new ArrayList<>();
    private String logisticsCompanyId = "";
    /**
     * 1 无需发货 0 需要发货
     */
    private int flag = 0;
    private String orderNo;

    @Override
    public int getLayoutId() {
        return R.layout.activity_transport_infomation;
    }

    @Override
    public void initData() {
        initTitleBar("运单信息");
        setRightText("无需寄件");
        mSpinnerPopWindow = new SpinnerPopWindow(this, mData, this);
        mSpinnerPopWindow.setOnDismissListener(this);
        getCompanyList();
        if (getIntent().getExtras() != null) {
            orderNo = getIntent().getExtras().getString("orderNo");
        }
    }

    /**
     * PopupWindow显示的ListView的item点击事件
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mSpinnerPopWindow.dismiss();
        logisticsCompanyId = mData.get(position).getId();
        mTxtTransCompany.setText(mData.get(position).getCompanyName());
    }

    /**
     * 监听PopupWindow消失
     */
    @Override
    public void onDismiss() {
        mRelTransCompany.setBackgroundResource(R.drawable.shape_trans_copmany);
        mIvTransTriangle.setImageResource(R.mipmap.triangle_open);
    }

    @OnClick({R.id.txt_trans_commit, R.id.rel_trans_company, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                flag = 1;
                if (Utils.isClickable()) {
                    confirmSend("");
                }
                break;
            //确认提交
            case R.id.txt_trans_commit:
                flag = 0;
                String no = mEtTransCode.getText().toString().trim();
                if (TextUtils.isEmpty(logisticsCompanyId)) {
                    ToastUtils.show("请选择物流公司");
                    return;
                }
                if (TextUtils.isEmpty(no)) {
                    ToastUtils.show("请输入物流单号");
                    return;
                }
                if (Utils.isClickable()) {
                    confirmSend(no);
                }
                break;
            //选择物流公司
            case R.id.rel_trans_company:
                mSpinnerPopWindow.setWidth(mRelTransCompany.getWidth());
                mSpinnerPopWindow.setHeight(WindowUtils.getScreenHeight(this)*2 / 5);
                mRelTransCompany.setBackgroundResource(R.drawable.shape_trans_copmany2);
                mSpinnerPopWindow.showAsDropDown(mRelTransCompany);
                mIvTransTriangle.setImageResource(R.mipmap.triangle_close);
                break;
            default:
                break;
        }
    }

    /**
     * 确认发货
     *
     * @param logisticsNo 订单号
     */
    private void confirmSend(String logisticsNo) {
        //1 无需发货 0 需要发货, 主订单号
        RequestApi.confirmSend(App.manager.getPhoneNum(), App.manager.getUuid(), flag, orderNo, logisticsCompanyId, logisticsNo, new AbstractNetWorkCallback<MsgBean>() {
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

            @Override
            public void onSuccess(MsgBean msgBean) {
                if (msgBean.getCode() == Constant.NO1) {
                    EventBus.getDefault().post(new EventBean(Constant.NO1));
                    TransportInformationActivity.this.finish();
                }
                ToastUtils.show(msgBean.getMsg());
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    /**
     * 获取物流公司列表
     */
    private void getCompanyList() {
        RequestApi.getLogisticsCompanyList(App.manager.getUuid(), App.manager.getPhoneNum(), new AbstractNetWorkCallback<LogisticsCompanyBean>() {

            @Override
            public void onSuccess(LogisticsCompanyBean bean) {
                if (bean.getCode() == NO1) {
                    if (bean.getContent().size() == NO0) {
                        ToastUtils.show("暂无物流公司数据");
                    } else {
                        mData.addAll(bean.getContent());
                    }
                } else {
                    ToastUtils.show(bean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }
}
