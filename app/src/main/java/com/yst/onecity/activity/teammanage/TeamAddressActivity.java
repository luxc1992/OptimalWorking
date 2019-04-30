package com.yst.onecity.activity.teammanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.yst.basic.framework.adapter.AbstractCommonAdapter;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.entity.holder.CommonViewHolder;
import com.yst.basic.framework.utils.MyLog;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.bean.AddressBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import gorden.rxbus2.RxBus;
import gorden.rxbus2.Subscribe;
import gorden.rxbus2.ThreadMode;


/**
 * 团队地址页面
 *
 * @author wuxiaofang
 * @version 1.1.0
 * @date 2018/5/21
 */

public class TeamAddressActivity extends BaseActivity implements View.OnClickListener, Inputtips.InputtipsListener, AdapterView.OnItemClickListener {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.iv_warning)
    ImageView ivWarning;
    @BindView(R.id.tv_tishi)
    TextView tvTishi;
    @BindView(R.id.tv_exapmle)
    TextView tvExapmle;
    @BindView(R.id.ll_province)
    LinearLayout llProvince;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.et_detail_address)
    EditText etDetailAddress;
    @BindView(R.id.ll_detail_address)
    LinearLayout llDetailAddress;
    @BindView(R.id.tv_province)
    TextView tvProvince;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_district)
    TextView tvDistrict;
    @BindView(R.id.ll_list)
    LinearLayout llList;
    @BindView(R.id.lv_address)
    ListView listView;

    private List<Tip> mData = new ArrayList<>();
    private AbstractCommonAdapter<Tip> adapter;
    private String provinceId = "";
    private String cityId = "";
    private String districtId = "";
    private String longitude = "";
    private String latitude = "";
    private String province;
    private String city;
    private String district;
    private boolean changeAddress = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_team_address;
    }

    @Override
    public void initData() {
        RxBus.get().register(this);
        tvMainTitle.setText("团队地址");
        tvRight.setText("确定");
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setTextColor(ContextCompat.getColor(this, R.color.color_FF9C9C9C));
        tvRight.setOnClickListener(this);
        if (null != getIntent() && null != getIntent().getExtras()) {
            Bundle bundle = getIntent().getExtras();
            changeAddress = bundle.getBoolean("isChangeAddress",false);
            String provinceName = bundle.getString("provinceName");
            String cityName = bundle.getString("cityName");
            String districtName = bundle.getString("districtName");
            String detailAddress = bundle.getString("detailAddress");
            province = provinceName;
            city = cityName;
            district = districtName;

            tvProvince.setText(provinceName);
            tvCity.setText(cityName);
            tvDistrict.setText(districtName);
            etDetailAddress.setText(detailAddress);
        }
        listView.setOnItemClickListener(this);
        etDetailAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString())) {
                    autoSearch(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void initCtrlor() {
        adapter = new AbstractCommonAdapter<Tip>(context, mData, R.layout.item_serach_address) {
            @Override
            public void convert(CommonViewHolder holder, int position, Tip item) {
                holder.setText(R.id.tv_address_name, item.getName());
                holder.setText(R.id.tv_detail_address, item.getAddress());
            }
        };
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        String detailAddress = etDetailAddress.getText().toString();
        if (TextUtils.isEmpty(tvProvince.getText().toString()) || TextUtils.isEmpty(tvCity.getText().toString()) || TextUtils.isEmpty(tvDistrict.getText().toString())) {
            ToastUtils.show("请选择省市区");
            return;
        }
        if (TextUtils.isEmpty(detailAddress)) {
            ToastUtils.show("请填写详细地址");
            return;
        }
        //修改地址不需要判断
        if (!changeAddress){
            if (TextUtils.isEmpty(longitude) || TextUtils.isEmpty(latitude)) {
                ToastUtils.show("经纬度未获取");
                return;
            }
        }
        Intent intent = new Intent();
        intent.putExtra("provinceName", province);
        intent.putExtra("cityName", city);
        intent.putExtra("districtName", district);
        intent.putExtra("provinceId", provinceId);
        intent.putExtra("cityId", cityId);
        intent.putExtra("districtId", districtId);
        intent.putExtra("longitude", longitude);
        intent.putExtra("latitude", latitude);
        intent.putExtra("detailAddress", detailAddress);
        setResult(Constant.TEAM_ADDRESS, intent);
        finish();
    }

    @OnClick({R.id.ll_province, R.id.ll_back})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.ll_province:
                if (!Utils.isClickable()) {
                    return;
                }
                AddressDiaFragment editNameDialog = new AddressDiaFragment();
                editNameDialog.show(getSupportFragmentManager(), "");
                break;
            case R.id.ll_back:
                if (!Utils.isClickable()) {
                    return;
                }
                finish();
                break;
            default:
                break;
        }
    }

    @Subscribe(code = Constant.NO1, threadMode = ThreadMode.MAIN)
    public void getData(AddressBean bean) {
        province = bean.getProvince();
        city = bean.getCity();
        district = bean.getDistrict();
        tvProvince.setText(bean.getProvince());
        tvCity.setText(bean.getCity());
        tvDistrict.setText(bean.getDistrict());
        provinceId = bean.getProId();
        cityId = bean.getCityId();
        districtId = bean.getDistrictId();

        MyLog.e("sss", bean.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unRegister(this);
    }

    /**
     * 检索地址
     *
     * @param address
     */
    private void autoSearch(String address) {
        //第二个参数传入null或者“”代表在全国进行检索，否则按照传入的city进行检索
        InputtipsQuery inputQuery = null;
        String str1 = "市辖区";
        String str2 = "市辖县";
        if (!TextUtils.isEmpty(city)){
            if (!city.equals(str1) || !city.equals(str2)) {
                inputQuery = new InputtipsQuery(address, city);
            } else {
                inputQuery = new InputtipsQuery(address, province);
            }
        }else{
            ToastUtils.show("请先选择省市区");
            etDetailAddress.setText("");
            return;
        }

        llList.setVisibility(View.VISIBLE);
        //限制在当前城市
        inputQuery.setCityLimit(true);
        Inputtips inputTips = new Inputtips(TeamAddressActivity.this, inputQuery);
        inputTips.setInputtipsListener(this);
        inputTips.requestInputtipsAsyn();
    }

    @Override
    public void onGetInputtips(List<Tip> list, int i) {
        if (i == Constant.NO1000) {
            if (list != null) {
                mData = list;
                initCtrlor();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        etDetailAddress.setText(mData.get(position).getName());
        LatLonPoint point = mData.get(position).getPoint();
        longitude = String.valueOf(point.getLongitude());
        latitude = String.valueOf(point.getLatitude());
        MyLog.e("sss", "longitude==" + longitude + "        latitude==" + latitude);
        llList.setVisibility(View.GONE);
    }

}
