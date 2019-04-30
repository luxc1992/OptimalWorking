package com.yst.im.imchatlibrary.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.adapter.CityListAdapter;
import com.yst.im.imchatlibrary.base.BaseActivity;
import com.yst.im.imchatlibrary.base.baseview.AbstractTitleView;
import com.yst.im.imchatlibrary.bean.BaseEntity;
import com.yst.im.imchatlibrary.bean.CityEntity;
import com.yst.im.imchatlibrary.model.SecondAddressInfoModel;
import com.yst.im.imchatlibrary.model.SetAddressModel;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.yst.im.imsdk.bean.RxBusEntity;
import com.yst.im.imsdk.utils.RxBusConstants;

import java.util.ArrayList;
import java.util.List;

import gorden.rxbus2.RxBus;

/**
 * 二级城市
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/3/28.
 */
public class SecondaryCitiesActivity extends BaseActivity implements SecondAddressInfoModel.SecondAddressInfoListenerCallBack, SetAddressModel.SetAddressListenerCallBack {
    private AbstractTitleView mTitleViewSecondCitiesTitle;
    private ListView mLvSecondCitiesList;
    private List<CityEntity.ContentBean> mCityList;
    private CityListAdapter mCityListAdapter;
    private SecondAddressInfoModel mSecondAddressInfoModel;
    private CityEntity.ContentBean city;
    private String areaName;
    private SetAddressModel mSetAddressModel;

    @Override
    protected int getLayout() {
        return R.layout.activity_secondary_cities;
    }

    @Override
    protected int getStatusColor() {
        return 0;
    }

    @Override
    protected void initView() {
        mTitleViewSecondCitiesTitle = (AbstractTitleView) findViewById(R.id.titleView_second_cities_title);
        mLvSecondCitiesList = (ListView) findViewById(R.id.lv_second_cities_list);
        mTitleViewSecondCitiesTitle.setTitleText(MyApp.getInstance().getString(R.string.personal_setting_city));
        mTitleViewSecondCitiesTitle.getRightTextTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(areaName)) {
                    ImToastUtil.showShortToast(SecondaryCitiesActivity.this, "请选择二级城市");
                    return;
                }
                if(MyApp.manager.getRegisterUser()){
                    RxBus.get().send(RxBusConstants.CONST_LOAD_CITY, (city.getAreaName() + " " + areaName));
                    MyApp.manager.setAddress(city.getAreaName() + " " + areaName);
                    MyApp.quiteActivity();
                }else {
                    mSetAddressModel.getSetAddress(city.getAreaName() + " " + areaName);
                    MyApp.manager.setAddress(city.getAreaName() + " " + areaName);
                }

            }
        });
        mTitleViewSecondCitiesTitle.getLeftBackImageTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        MyApp.addActivity(this);
    }

    @Override
    protected boolean getStatus() {
        return false;
    }

    @Override
    protected void initData() {
        city = (CityEntity.ContentBean) getIntent().getSerializableExtra("city");
        mCityList = new ArrayList<>();
        mSetAddressModel = new SetAddressModel(this);
        mSetAddressModel.setSetAddressListenerCallBack(this);
        mSecondAddressInfoModel = new SecondAddressInfoModel(this);
        mSecondAddressInfoModel.setSecondAddressInfoListenerCallBack(this);
        mSecondAddressInfoModel.getSecondAddressInfo(String.valueOf(city.getId()));
        mCityListAdapter = new CityListAdapter(this, mCityList);
        mLvSecondCitiesList.setAdapter(mCityListAdapter);
        mLvSecondCitiesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                areaName = mCityList.get(position).getAreaName();
                ImToastUtil.showShortToast(SecondaryCitiesActivity.this, areaName);
            }
        });

    }

    @Override
    public void onSecondAddressInfo(List<CityEntity.ContentBean> contentBeans) {
        for (int i = 0; i < contentBeans.size(); i++) {
            contentBeans.get(i).setSecond(true);
        }
        mCityList.addAll(contentBeans);
        mCityListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSetAddress(BaseEntity baseEntity) {
        RxBus.get().send(RxBusConstants.CONST_LOAD_CITY, (city.getAreaName() + " " + areaName));
        MyApp.quiteActivity();
    }
}
