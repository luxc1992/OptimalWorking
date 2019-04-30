package com.yst.onecity.activity.home;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.Gson;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.adapter.CityAdapter;
import com.yst.onecity.adapter.CityHeaderAdapter;
import com.yst.onecity.bean.issue.CityBean;
import com.yst.onecity.db.DbManager;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.PreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.indexablerv.IndexableAdapter;
import me.yokeyword.indexablerv.IndexableLayout;

/**
 * 选择服务地址
 *
 * @author chenxiaowei
 * @version 1.1.0
 * @date 2018/5/17
 */
public class ChangeCityActivity extends BaseActivity implements AMapLocationListener, CityHeaderAdapter.OnHotCityListener {

    @BindView(R.id.tv_location_address)
    TextView tvLocationAddress;
    private AMapLocationClient mLocationClient;
    @BindView(R.id.indexableLayout)
    IndexableLayout indexableLayout;
    private List<String> hotCities = new ArrayList<>();
    private List<CityBean> allCities;
    private CityAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_city;
    }

    @Override
    public void initData() {
        initTitleBar("切换城市");
        setBackResouce(R.mipmap.close);
        initLocation();
        initHotCity();
        indexableLayout.setLayoutManager(new LinearLayoutManager(this));
        DbManager dbManager = new DbManager(context);
        dbManager.copyDBFile();
        allCities = dbManager.getAllCities();
        initCtrllor();
    }

    /**
     * 热门城市
     */
    private void initHotCity() {
        String jsonCity = PreferenceUtil.getString("jsonCity", "");
        if (TextUtils.isEmpty(jsonCity)) {
            hotCities.add("北京市");
            hotCities.add("上海市");
            hotCities.add("广州市");
            hotCities.add("深圳市");
        } else {
            try {
                JSONArray jsonArray = new JSONArray(jsonCity);
                for (int i = 0; i < jsonArray.length(); i++) {
                    hotCities.add((String) jsonArray.get(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void initCtrllor() {
        mAdapter = new CityAdapter(this);
        indexableLayout.setAdapter(mAdapter);
        indexableLayout.setOverlayStyle_Center();
        mAdapter.setDatas(allCities);
        // 全字母排序,排序规则设置为：每个字母都会进行比较排序；速度较慢
        indexableLayout.setCompareMode(IndexableLayout.MODE_FAST);
        List<String> bannerList = new ArrayList<>();
        bannerList.add("");
        CityHeaderAdapter cityHeaderAdapter = new CityHeaderAdapter("A", null, bannerList, hotCities, context, this);
        indexableLayout.addHeaderAdapter(cityHeaderAdapter);
    }

    @Override
    public void setListener() {
        mAdapter.setOnItemContentClickListener(new IndexableAdapter.OnItemContentClickListener<CityBean>() {
            @Override
            public void onItemClick(View v, int originalPosition, int currentPosition, CityBean entity) {
                if (originalPosition >= 0) {
                    if (hotCities.size() == Constant.NO4) {
                        hotCities.remove(3);
                        hotCities.add(0, entity.getName());
                    } else {
                        hotCities.add(0, entity.getName());
                    }
                    //本地保存热门城市
                    PreferenceUtil.put("jsonCity", new Gson().toJson(hotCities));
                    Intent intent = new Intent();
                    intent.putExtra("city", entity.getName());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    /**
     * 初始化定位
     */
    private void initLocation() {
        tvLocationAddress.setText("定位中...");
        showInfoProgressDialog();
        if (mLocationClient == null) {
            //初始化定位
            mLocationClient = new AMapLocationClient(this);
            //初始化定位参数
            AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
            //设置定位回调监听
            mLocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置只定位一次
            mLocationOption.setOnceLocation(true);
            //设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
        }
        //启动定位
        mLocationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        dismissInfoProgressDialog();
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                tvLocationAddress.setText(ConstUtils.getStringNoEmpty(aMapLocation.getPoiName()));
            } else {
                tvLocationAddress.setText("定位失败,点击重试");
            }
        }
    }

    @OnClick(R.id.iv_refresh)
    public void onViewClicked() {
        initLocation();
    }

    @Override
    public void onCityListener(String hotCity) {
        if (hotCities.size() == Constant.NO4) {
            hotCities.remove(0);
            hotCities.add(0, hotCity);
        } else {
            hotCities.add(0, hotCity);
        }
        //本地保存热门城市
        PreferenceUtil.put("jsonCity", new Gson().toJson(hotCities));
        Intent intent = new Intent();
        intent.putExtra("city", hotCity);
        setResult(RESULT_OK, intent);
        finish();
    }
}
