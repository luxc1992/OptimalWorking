package com.yst.onecity.wheelview;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.App;
import com.yst.onecity.R;
import com.yst.onecity.adapter.CityWheelAdapter;
import com.yst.onecity.adapter.ProvinceWheelAdapter;
import com.yst.onecity.bean.CityAdressBean;
import com.yst.onecity.bean.ProvinceAdressBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.WheelUtils;
import com.yst.onecity.wheelview.listener.MyOnWheelChangedListener;
import com.yst.onecity.wheelview.listener.MyOnWheelClickedListener;
import com.yst.onecity.wheelview.listener.OnAddressChangeListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * (个人信息)服务弹框
 *
 * @author LianJinXue
 * @version 1.0.1
 * @date 2018/2/22
 */
public class ChooseAddressWheel implements MyOnWheelChangedListener,MyOnWheelClickedListener {

    @BindView(R.id.province_wheel)
    MyWheelView provinceWheel;
    @BindView(R.id.city_wheel)
    MyWheelView cityWheel;
    @BindView(R.id.tv_myinfo_title)
    TextView tvMyInfoTitle;
    private Activity context;
    private View parentView;
    private PopupWindow popupWindow = null;
    private WindowManager.LayoutParams layoutParams = null;
    private LayoutInflater layoutInflater = null;
    private List<ProvinceAdressBean.ContentBean> province = null;
    private List<CityAdressBean.ContentBean> citys = null;
    private OnAddressChangeListener onAddressChangeListener = null;
    public ChooseAddressWheel(Activity context) {
        this.context = context;
        init();
    }

    private void init() {
        layoutParams = context.getWindow().getAttributes();
        layoutInflater = context.getLayoutInflater();
        initView();
        initPopupWindow();
    }

    private void initView() {
        parentView = layoutInflater.inflate(R.layout.dialog_myinfo_changebirth, null);
        ButterKnife.bind(this, parentView);
        provinceWheel.setVisibleItems(8);
        cityWheel.setVisibleItems(8);
        provinceWheel.addChangingListener(this);
        cityWheel.addChangingListener(this);

    }

    private void initPopupWindow() {
        popupWindow = new PopupWindow(parentView, WindowManager.LayoutParams.MATCH_PARENT, (int) (WheelUtils.getScreenHeight(context) * (2.0 / 5)));
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.setAnimationStyle(R.style.anim_push_bottom);
        popupWindow.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.WHITE));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        tvMyInfoTitle.setText("请选择区域");
        tvMyInfoTitle.setTextColor(Color.parseColor("#333333"));
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                layoutParams.alpha = 1.0f;
                context.getWindow().setAttributes(layoutParams);
                popupWindow.dismiss();
            }
        });
    }

    /**
     * 加载数据
     *
     * @version 1.0.1
     */
    private void bindData() {
        provinceWheel.setViewAdapter(new ProvinceWheelAdapter(context, province));
        updateCity(province.get(0).getId());
    }

    /**
     * 省份改变后城市和地区联动
     *
     * @version 1.0.1
     */
    @Override
    public void onChanged(MyWheelView wheel, int oldValue, int newValue) {
        if (wheel == provinceWheel) {
            updateCity(province.get(newValue).getId());
        }
    }

    /**
     * 加载市
     *
     * @version 1.0.1
     */
    private void updateCity(final int id) {
        if(cityWheel != null){
            cityWheel.setCurrentItem(0);
        }
        RequestApi.myCityAdress(App.manager.getPhoneNum(), App.manager.getUuid(), "1", String.valueOf(id), new AbstractNetWorkCallback<CityAdressBean>() {
            @Override
            public void onSuccess(CityAdressBean cityAdressBean) {
                if (cityAdressBean.getCode() == 1) {
                    if (cityAdressBean.getContent() != null) {
                        cityWheel.setViewAdapter(new CityWheelAdapter(context, cityAdressBean.getContent()));
                        if (citys != null){
                            citys.clear();
                        }
                        citys = cityAdressBean.getContent();
                    }else {
                        ToastUtils.show(cityAdressBean.getMsg());
                    }
                }else {
                    ToastUtils.show(cityAdressBean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    public void show(View v) {
        layoutParams.alpha = 0.6f;
        context.getWindow().setAttributes(layoutParams);
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 加载省
     *
     * @version 1.0.1
     */
    public void setProvince(List<ProvinceAdressBean.ContentBean> province) {
        this.province = province;
        bindData();
    }

  /**
   * 确定
   *
   * @version 1.0.1
   */
    @OnClick(R.id.confirm_txtview)
    public void confirm() {
        if (onAddressChangeListener != null) {
            int provinceIndex = provinceWheel.getCurrentItem();
            int cityIndex = cityWheel.getCurrentItem();
            String provinceName = "", cityName = "", areaName = "";
            if (province != null && province.size() > provinceIndex) {
                ProvinceAdressBean.ContentBean provinceEntity = province.get(provinceIndex);
                provinceName = provinceEntity.getName();
            }
            if (citys != null && citys.size() > cityIndex) {
                CityAdressBean.ContentBean cityEntity = citys.get(cityIndex);
                cityName = cityEntity.getName();
            }
            onAddressChangeListener.onAddressChange(provinceName, cityName, areaName);

            cancel();
        }
    }


    @OnClick(R.id.btn_myinfo_cancel)
    public void cancel() {
        popupWindow.dismiss();
    }

    public void setOnAddressChangeListener(OnAddressChangeListener onAddressChangeListener) {
        this.onAddressChangeListener = onAddressChangeListener;
    }

    @Override
    public void onItemClicked(MyWheelView wheel, int itemIndex){
    }
}