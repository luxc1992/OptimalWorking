package com.yst.onecity.activity.mine.address;

import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.utils.WindowUtils;
import com.yst.basic.framework.utils.security.PreferenceUtil;
import com.yst.onecity.Const;
import com.yst.onecity.R;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.PositionBean;
import com.yst.onecity.bean.ReapRensonBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.VerifyUtil;
import com.yst.onecity.view.AddressSelector;
import com.yst.onecity.view.dialog.AbstractDeleteDialog;
import com.yst.onecity.view.viewpagerindicator.interfaces.CityInterface;
import com.yst.onecity.wheelview.listener.OnItemClickListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO2;

/**
 * 新增收货人页面
 *
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/2/24
 */
public class AddAddressActivity extends BaseActivity implements OnItemClickListener, AddressSelector.OnTabSelectedListener {

    @BindView(R.id.et_add_name)
    EditText mEtAddName;
    @BindView(R.id.et_add_phone)
    EditText mEtAddPhone;
    @BindView(R.id.txt_add_address)
    TextView mTxtAddAddress;
    @BindView(R.id.rel_add_address)
    RelativeLayout mRelAddAddress;
    @BindView(R.id.et_add_detail)
    EditText mEtAddDetail;
    @BindView(R.id.et_add_email)
    EditText mEtAddEmail;
    @BindView(R.id.cb_add_default)
    CheckBox mCbAddDefault;
    @BindView(R.id.txt_add_save)
    TextView mTxtAddSave;
    @BindView(R.id.ll_add_delete)
    LinearLayout mLlAddDelete;
    @BindView(R.id.rel_default)
    RelativeLayout relDefalt;

    private AddressSelector mAddressSelector;
    private List<PositionBean.ContentBean> mList1 = new ArrayList<>();
    private List<PositionBean.ContentBean> mList2 = new ArrayList<>();
    private List<PositionBean.ContentBean> mList3 = new ArrayList<>();
    private PopupWindow mPop;
    private ImageView mClose;
    private String name;
    private String phone;
    private String address;
    private String code;
    private int aid;
    private int editflags;
    private int isDefault;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_address;
    }

    @Override
    public void initData() {
        initTitleBar("新增收货人");
        String editFlag = "editFlag";
        if (getIntent() != null && getIntent().hasExtra(editFlag)) {
            editflags = getIntent().getIntExtra("editFlag", 0);
            aid = getIntent().getIntExtra("aid", 0);
            //editFlag为2是编辑收货人
            if (editflags == NO2) {
                initTitleBar("编辑收货人");
                mLlAddDelete.setVisibility(View.GONE);
                //编辑收货人回显
                getAddressInfo();
            } else {
                initTitleBar("新增收货人");
            }
        }
    }

    @OnClick({R.id.rel_add_address, R.id.txt_add_save, R.id.ll_add_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //省区
            case R.id.rel_add_address:
                showAddressSelector();
                break;
            //保存
            case R.id.txt_add_save:
                name = mEtAddName.getText().toString().trim();
                phone = mEtAddPhone.getText().toString().trim();
                address = mEtAddDetail.getText().toString().trim();
                code = mEtAddEmail.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    ToastUtils.show("请输入您的姓名");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    ToastUtils.show("请输入您的手机号");
                    return;
                }
                if (!VerifyUtil.isMobileNO(phone)) {
                    ToastUtils.show("手机号格式不正确");
                    return;
                }
                if (TextUtils.isEmpty(address)) {
                    ToastUtils.show("请输入地址");
                    return;
                }
                if (editflags == NO2) {
                    //编辑收货人
                    setEditAddressInfo();
                } else {
                    //新增收货人
                    requestAddAddress();
                }
                break;
            //删除
            case R.id.ll_add_delete:
                showDeleteDialog();
                break;
            default:
                break;
        }
    }

    /**
     * 新增收货人
     *
     * @version 1.0.1
     */
    public void requestAddAddress() {
        RequestApi.addAddress(String.valueOf(App.manager.getId()),
                App.manager.getPhoneNum(),
                App.manager.getUuid(),
                !mCbAddDefault.isChecked() ? String.valueOf(NO0) : String.valueOf(NO1),
                ConstUtils.getStringNoEmpty(name),
                ConstUtils.getStringNoEmpty(phone),
                PreferenceUtil.getString("provinceId", ""),
                PreferenceUtil.getString("districtId", ""),
                PreferenceUtil.getString("cityId", ""),
                address,
                TextUtils.isEmpty(code) ? "" : code,
                new AbstractNetWorkCallback<MsgBean>() {
                    @Override
                    public void onSuccess(MsgBean msgBean) {
                        if (msgBean.getCode() == NO1) {
                            ToastUtils.show(msgBean.getMsg());
                            finish();
                        } else {
                            ToastUtils.show(msgBean.getMsg());
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
     * 编辑收货人
     *
     * @version 1.0.1
     */
    private void setEditAddressInfo() {
        RequestApi.updateAddress(
                App.manager.getPhoneNum(),
                App.manager.getUuid(),
                !mCbAddDefault.isChecked() ? String.valueOf(NO0) : String.valueOf(NO1),
                ConstUtils.getStringNoEmpty(name),
                ConstUtils.getStringNoEmpty(phone),
                PreferenceUtil.getString("provinceId", ""),
                PreferenceUtil.getString("districtId", ""),
                PreferenceUtil.getString("cityId", ""),
                ConstUtils.getStringNoEmpty(address),
                code,
                String.valueOf(aid),
                new AbstractNetWorkCallback<MsgBean>() {
                    @Override
                    public void onSuccess(MsgBean msgBean) {
                        if (msgBean.getCode() == NO1) {
                            ToastUtils.show(msgBean.getMsg());
                            finish();
                        } else {
                            ToastUtils.show(msgBean.getMsg());
                        }
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

                    @Override
                    public void onError(String errorMsg) {
                        ToastUtils.show(errorMsg);
                    }
                });
    }

    /**
     * 编辑收货人 回显
     *
     * @version 1.0.1
     */
    public void getAddressInfo() {
        RequestApi.getAddressInfo(
                App.manager.getPhoneNum(),
                App.manager.getUuid(),
                String.valueOf(aid),
                new AbstractNetWorkCallback<ReapRensonBean>() {
                    @Override
                    public void onSuccess(ReapRensonBean reapRensonBean) {
                        if (reapRensonBean.getCode() == NO1) {
                            if (reapRensonBean.getContent() != null) {
                                PreferenceUtil.put("provinceId", reapRensonBean.getContent().getPro_area_id());
                                PreferenceUtil.put("provinceName", reapRensonBean.getContent().getProvinceName());
                                PreferenceUtil.put("cityId", reapRensonBean.getContent().getCity_area_id());
                                PreferenceUtil.put("cityName", reapRensonBean.getContent().getCityName());
                                PreferenceUtil.put("districtId", reapRensonBean.getContent().getDistrict_area_id());
                                PreferenceUtil.put("districtName", reapRensonBean.getContent().getCountyName());

                                mEtAddName.setText(ConstUtils.getStringNoEmpty(reapRensonBean.getContent().getUser_name()));
                                mEtAddPhone.setText(ConstUtils.getStringNoEmpty(reapRensonBean.getContent().getMobile()));
                                mTxtAddAddress.setText(ConstUtils.getStringNoEmpty(reapRensonBean.getContent().getProvinceName() + reapRensonBean.getContent().getCityName() + reapRensonBean.getContent().getCountyName()));
                                mTxtAddAddress.setTextColor(ContextCompat.getColor(context, R.color.color_333333));
                                mEtAddDetail.setText(ConstUtils.getStringNoEmpty(reapRensonBean.getContent().getDetail_address()));
                                mEtAddEmail.setText(ConstUtils.getStringNoEmpty(reapRensonBean.getContent().getCode()));
                                isDefault = reapRensonBean.getContent().getIs_default();
                                if (isDefault == NO1) {
                                    relDefalt.setVisibility(View.INVISIBLE);
                                } else {
                                    relDefalt.setVisibility(View.VISIBLE);
                                    mCbAddDefault.setChecked(false);
                                }
                            } else {
                                ToastUtils.show(reapRensonBean.getMsg());
                            }
                        } else {
                            ToastUtils.show(reapRensonBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(String errorMsg) {
                        ToastUtils.show(errorMsg);
                    }
                });
    }

    /**
     * 删除弹框
     *
     * @version 1.0.1
     */
    private void showDeleteDialog() {
        AbstractDeleteDialog dialog = new AbstractDeleteDialog(AddAddressActivity.this) {
            @Override
            public void sureClick() {
                deleteAddress();
            }
        };
        dialog.setText("确认删除该地址么？", "确认", "取消");
        dialog.showDialog();
    }

    /**
     * 删除收货地址
     *
     * @version 1.0.1
     */
    private void deleteAddress() {
        RequestApi.deleteAddress(
                App.manager.getPhoneNum(),
                App.manager.getUuid(),
                String.valueOf(aid),
                new AbstractNetWorkCallback<MsgBean>() {
                    @Override
                    public void onSuccess(MsgBean msgBean) {
                        if (msgBean.getCode() == NO1) {
                            ToastUtils.show(msgBean.getMsg());
                            finish();
                        } else {
                            ToastUtils.show(msgBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(String errorMsg) {
                        ToastUtils.show(errorMsg);
                    }
                });
    }

    /**
     * 显示地址选择器
     *
     * @version 1.0.1
     */
    private void showAddressSelector() {
        View rootView = LayoutInflater.from(this).inflate(R.layout.pop_choose_address, null, false);
        mPop = new PopupWindow(rootView, ViewGroup.LayoutParams.MATCH_PARENT, (int) (WindowUtils.getScreenHeight(this) * 0.6), false);
        mAddressSelector = rootView.findViewById(R.id.addressSelector);
        mClose = rootView.findViewById(R.id.iv_pop_close);
        setPopAttribute(rootView);
        getProvinceData();
        mAddressSelector.setOnItemClickListener(this);
        mAddressSelector.setOnTabSelectedListener(this);
    }

    @Override
    public void itemClick(AddressSelector addressSelector, CityInterface city, int tabPosition) {
        switch (tabPosition) {
            case 0:
                addressSelector.setCities(mList2);
                PreferenceUtil.put("provinceId", city.getCityid());
                PreferenceUtil.put("provinceName", city.getCityName());
                getCityData(city.getCityid());
                break;
            case 1:
                addressSelector.setCities(mList3);
                PreferenceUtil.put("cityId", city.getCityid());
                PreferenceUtil.put("cityName", city.getCityName());
                getDistrictData(city.getCityid());
                break;
            case 2:
                String provinceId = PreferenceUtil.getString("provinceId", "");
                String provinceName = PreferenceUtil.getString("provinceName", "");
                String cityId = PreferenceUtil.getString("cityId", "");
                String cityName = PreferenceUtil.getString("cityName", "");
                String districtId = city.getCityid();
                String districtName = city.getCityName();
                PreferenceUtil.put("districtId", city.getCityid());
                PreferenceUtil.put("districtName", city.getCityName());
                MyLog.e("all", "所在地区===" + provinceId + provinceName + ", " + cityId + cityName + ", " + districtId + districtName);
                setPopDismiss();
                mTxtAddAddress.setText(provinceName + cityName + districtName);
                mTxtAddAddress.setTextColor(ContextCompat.getColor(this, R.color.color_333333));
                break;
            default:
                break;
        }
    }

    @Override
    public void onTabSelected(AddressSelector addressSelector, AddressSelector.Tab tab) {
        switch (tab.getIndex()) {
            case 0:
                addressSelector.setCities(mList1);
                break;
            case 1:
                addressSelector.setCities(mList2);
                break;
            case 2:
                addressSelector.setCities(mList3);
                break;
            default:
                break;
        }
    }

    @Override
    public void onTabReselected(AddressSelector addressSelector, AddressSelector.Tab tab) {

    }

    /**
     * 改变背景颜色
     *
     * @version 1.0.1
     */
    private void changeBackground(Float backgroundColor) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = backgroundColor;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
    }

    /**
     * 设置pop属性
     *
     * @param rootView 父布局
     * @version 1.0.1
     */
    private void setPopAttribute(View rootView) {
        mAddressSelector.setTabAmount(3);
        mAddressSelector.setListTextSelectedColor(ContextCompat.getColor(this, R.color.color_ED5452));
        mAddressSelector.setTextSelectedColor(ContextCompat.getColor(this, R.color.color_ED5452));
        mAddressSelector.setLineColor(ContextCompat.getColor(this, R.color.color_ED5452));
        changeBackground(0.5f);
        rootView.setFocusable(true);
        rootView.setFocusableInTouchMode(true);
        mPop.setOutsideTouchable(false);
        mPop.setFocusable(true);
        mPop.setAnimationStyle(R.style.mypopwindow_anim_style);
        mPop.setContentView(rootView);
        mPop.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);

        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPopDismiss();
            }
        });
        mPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setPopDismiss();
            }
        });
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    setPopDismiss();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * pop消失时操作
     *
     * @version 1.0.1
     */
    private void setPopDismiss() {
        changeBackground(1f);
        mPop.dismiss();
        mPop = null;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (mPop != null && mPop.isShowing()) {
            return false;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    /**
     * 获取区县数据
     *
     * @version 1.0.1
     */
    private void getDistrictData(String cityId) {
        OkHttpUtils.post().url(Const.GET_POSITION_LIST)
                .addParams("phone", App.manager.getPhoneNum())
                .addParams("uuid", App.manager.getUuid())
                .addParams("type", "2")
                .addParams("id", cityId)
                .build().execute(new StringCallback() {

            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                showInfoProgressDialog();
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                dismissInfoProgressDialog();
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                MyLog.e("okhttp", "onResponse3: " + response);
                Gson gson = new Gson();
                PositionBean bean = gson.fromJson(response, PositionBean.class);
                if (bean.getCode() == NO1) {
                    mList3 = bean.getContent();
                    mAddressSelector.setCities(mList3);
                }
            }
        });
    }

    /**
     * 获取城市数据
     *
     * @version 1.0.1
     */
    private void getCityData(String provinceId) {
        OkHttpUtils.post().url(Const.GET_POSITION_LIST)
                .addParams("phone", App.manager.getPhoneNum())
                .addParams("uuid", App.manager.getUuid())
                .addParams("type", "1")
                .addParams("id", provinceId)
                .build().execute(new StringCallback() {

            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                showInfoProgressDialog();
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                dismissInfoProgressDialog();
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                MyLog.e("okhttp", "onResponse2: " + response);
                Gson gson = new Gson();
                PositionBean bean = gson.fromJson(response, PositionBean.class);
                if (bean.getCode() == NO1) {
                    mList2 = bean.getContent();
                    mAddressSelector.setCities(mList2);
                }
            }
        });
    }

    /**
     * 获取省份数据
     *
     * @version 1.0.1
     */
    private void getProvinceData() {
        OkHttpUtils.post().url(Const.GET_POSITION_LIST)
                .addParams("phone", App.manager.getPhoneNum())
                .addParams("uuid", App.manager.getUuid())
                .addParams("type", String.valueOf(NO0))
                .build().execute(new StringCallback() {

            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                showInfoProgressDialog();
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                dismissInfoProgressDialog();
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                MyLog.e("okhttp", "onResponse1: " + response);
                Gson gson = new Gson();
                PositionBean bean = gson.fromJson(response, PositionBean.class);
                if (bean.getCode() == NO1) {
                    mList1 = bean.getContent();
                    mAddressSelector.setCities(mList1);
                }
            }
        });
    }
}