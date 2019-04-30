package com.yst.onecity.activity.teammanage;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yst.basic.framework.App;
import com.yst.basic.framework.utils.MyLog;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.utils.WindowUtils;
import com.yst.basic.framework.utils.security.PreferenceUtil;
import com.yst.onecity.Const;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.adapter.ProCityAdapter;
import com.yst.onecity.bean.AddressBean;
import com.yst.onecity.bean.PositionBean;
import com.yst.onecity.utils.TabLayoutIndicatorUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import gorden.rxbus2.RxBus;
import okhttp3.Call;
import okhttp3.Request;

import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;

/**
 * 地址选择框的dialog
 *
 * @author wuxiaofang
 * @version 1.1.0
 * @date 2018/5/24
 */

public class AddressDiaFragment extends DialogFragment implements AdapterView.OnItemClickListener {

    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.tvSure)
    TextView tvSure;
    @BindView(R.id.rl_tab)
    RelativeLayout rlTab;
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.iv_empty)
    ImageView ivEmpty;
    Unbinder unbinder;
    public static List<PositionBean.ContentBean> provinceList = new ArrayList<>();
    public static List<PositionBean.ContentBean> cityList = new ArrayList<>();
    public static List<PositionBean.ContentBean> districtList = new ArrayList<>();

    private int tabCount;
    private int startTabIndex;
    private ProCityAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Window window = getDialog().getWindow();
        View view = inflater.inflate(R.layout.layout_fragment, ((ViewGroup) window.findViewById(android.R.id.content)), false);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.height = (int) (WindowUtils.getScreenHeight(getActivity()) * 0.4);
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);
        window.setWindowAnimations(R.style.add_cart_pop_anim_style);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tab.addTab(tab.newTab().setText("省份"));
        tab.addTab(tab.newTab().setText("城市"));
        tab.addTab(tab.newTab().setText("区县"));

        tabCount = tab.getTabCount();

        for (int i = 0; i < tab.getTabCount(); i++) {
            TabLayout.Tab tab1 = tab.getTabAt(i);
            if (tab1 == null) {
                return;
            }
            //这里使用到反射，拿到Tab对象后获取Class
            Class c = tab1.getClass();
            try {
                //Filed “字段、属性”的意思,c.getDeclaredField 获取私有属性。
                //"mView"是Tab的私有属性名称(可查看TabLayout源码),类型是 TabView,TabLayout私有内部类。
                Field field = c.getDeclaredField("mView");
                //值为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查。值为 false 则指示反射的对象应该实施 Java 语言访问检查。
                //如果不这样会报如下错误
                // java.lang.IllegalAccessException:
                //Class com.test.accessible.Main
                //can not access
                //a member of class com.test.accessible.AccessibleTest
                //with modifiers "private"
                field.setAccessible(true);
                final View view = (View) field.get(tab1);
                if (view == null) {
                    return;
                }
                view.setTag(i);
                view.setEnabled(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        TabLayoutIndicatorUtils.setIndicator(tab, 10, 10);
        adapter = new ProCityAdapter(getActivity());
        lv.setAdapter(adapter);
        getProvinceData();
        lv.setOnItemClickListener(this);
    }

    /**
     * 获取省份数据
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
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                MyLog.e("okhttp", "onResponse1: " + response);
                Gson gson = new Gson();
                PositionBean positionBean = gson.fromJson(response, PositionBean.class);
                if (null != positionBean) {
                    if (positionBean.getCode() == NO1 && null != positionBean.getContent()) {
                        provinceList=positionBean.getContent();
                        adapter.addData(provinceList);
                        if (provinceList.isEmpty()) {
                            lv.setVisibility(View.GONE);
                            ivEmpty.setVisibility(View.VISIBLE);
                        } else {
                            lv.setVisibility(View.VISIBLE);
                            ivEmpty.setVisibility(View.GONE);
                        }
                    } else {
                        ToastUtils.show(positionBean.getMsg());
                    }
                } else {
                    lv.setVisibility(View.GONE);
                    ivEmpty.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (startTabIndex == Constant.NO0) {
            getCity(provinceList.get(i).getId());
            PreferenceUtil.put("provinceId", provinceList.get(i).getId());
            PreferenceUtil.put("province", provinceList.get(i).getName());
        }
        if (startTabIndex == Constant.NO1) {
            getDistrict(cityList.get(i).getId());
            PreferenceUtil.put("cityId", cityList.get(i).getId());
            PreferenceUtil.put("city", cityList.get(i).getName());
        }
        if (startTabIndex >= Constant.NO2) {
            PreferenceUtil.put("districtId", districtList.get(i).getId());
            PreferenceUtil.put("district", districtList.get(i).getName());
        }
        startTabIndex++;
        if (startTabIndex < tabCount) {
            tab.getTabAt(startTabIndex).select();
        }
    }

    @OnClick(R.id.tvSure)
    public void sure() {
        String province = PreferenceUtil.getString("province", "");
        String city = PreferenceUtil.getString("city", "");
        String district = PreferenceUtil.getString("district", "");
        String provinceId = PreferenceUtil.getString("provinceId", "");
        String cityId = PreferenceUtil.getString("cityId", "");
        String districtId = PreferenceUtil.getString("districtId", "");
        RxBus.get().send(Constant.NO1, new AddressBean(province, city, district, provinceId, cityId, districtId));
        dismiss();
    }

    /**
     * 获取地区
     *
     * @param cityId 城市id
     */
    private void getDistrict(String cityId) {
        OkHttpUtils.post().url(Const.GET_POSITION_LIST)
                .addParams("phone", App.manager.getPhoneNum())
                .addParams("uuid", App.manager.getUuid())
                .addParams("type", String.valueOf(Constant.NO2))
                .addParams("id", cityId)
                .build().execute(new StringCallback() {

            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                MyLog.e("okhttp", "onResponse3: " + response);
                Gson gson = new Gson();
                PositionBean positionBean = gson.fromJson(response, PositionBean.class);
                if (null != positionBean) {
                    if (positionBean.getCode() == NO1 && null != positionBean.getContent()) {
                        districtList=positionBean.getContent();
                        adapter.addData(districtList);
                        if (districtList.isEmpty()) {
                            lv.setVisibility(View.GONE);
                            ivEmpty.setVisibility(View.VISIBLE);
                        } else {
                            lv.setVisibility(View.VISIBLE);
                            ivEmpty.setVisibility(View.GONE);
                        }
                    } else {
                        ToastUtils.show(positionBean.getMsg());
                    }
                } else {
                    lv.setVisibility(View.GONE);
                    ivEmpty.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * 获取城市列表
     */
    private void getCity(final String provinceId) {
        OkHttpUtils.post().url(Const.GET_POSITION_LIST)
                .addParams("phone", App.manager.getPhoneNum())
                .addParams("uuid", App.manager.getUuid())
                .addParams("type", String.valueOf(Constant.NO1))
                .addParams("id", provinceId)
                .build().execute(new StringCallback() {

            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                MyLog.e("okhttp", "onResponse2: " + response);
                Gson gson = new Gson();
                PositionBean positionBean = gson.fromJson(response, PositionBean.class);
                if (null != positionBean) {
                    if (positionBean.getCode() == NO1 && null != positionBean.getContent()) {
                        cityList =positionBean.getContent();
                        adapter.addData(cityList);
                        if (cityList.isEmpty()) {
                            lv.setVisibility(View.GONE);
                            ivEmpty.setVisibility(View.VISIBLE);
                        } else {
                            lv.setVisibility(View.VISIBLE);
                            ivEmpty.setVisibility(View.GONE);
                        }
                    } else {
                        ToastUtils.show(positionBean.getMsg());
                    }
                } else {
                    lv.setVisibility(View.GONE);
                    ivEmpty.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 弹框消失后的回调
     */
    public interface addressInter {
        /**
         * 存储省市区的bean
         *
         * @param bean 地址bean
         */
        void onProCityDis(AddressBean bean);
    }
}
