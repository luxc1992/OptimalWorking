package com.yst.im.imchatlibrary.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.adapter.CityListAdapter;
import com.yst.im.imchatlibrary.base.BaseActivity;
import com.yst.im.imchatlibrary.base.baseview.AbstractTitleView;
import com.yst.im.imchatlibrary.bean.CityEntity;
import com.yst.im.imchatlibrary.model.AddressInfoModel;
import com.yst.im.imchatlibrary.utils.JumpIntent;

import java.util.ArrayList;
import java.util.List;

/**
 * 一级城市
 *
 * @author Lierpeng
 * @date 2018/4/4.
 * @version 1.0.0
 */
public class TierOneCitiesActivity extends BaseActivity implements AddressInfoModel.AddressInfoListenerCallBack {
    private ListView mLvOneCitiesList;
    private List<CityEntity.ContentBean> mCityList;
    private CityListAdapter mCityListAdapter;
    private TextView mTxtOneCitiesSelect;

    @Override
    protected int getLayout() {
        return R.layout.activity_tier_one_cities;
    }

    @Override
    protected int getStatusColor() {
        return 0;
    }

    @Override
    protected void initView() {
        AbstractTitleView mTitleViewOneCitiesTitle = (AbstractTitleView) findViewById(R.id.titleView_one_cities_title);
        mTxtOneCitiesSelect = (TextView) findViewById(R.id.txt_one_cities_select);
        mLvOneCitiesList = (ListView) findViewById(R.id.lv_one_cities_list);
        mTitleViewOneCitiesTitle.setTitleText(MyApp.getInstance().getString(R.string.personal_setting_city));
        mTitleViewOneCitiesTitle.getLeftBackImageTv().setOnClickListener(new View.OnClickListener() {
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
        String address = getIntent().getStringExtra("address");
        if (!TextUtils.isEmpty(address)) {
            mTxtOneCitiesSelect.setText(address);
        }
        mCityList = new ArrayList<>();
        AddressInfoModel mAddressInfoModel = new AddressInfoModel(this);
        mAddressInfoModel.setAddressInfoListenerCallBack(this);
        mAddressInfoModel.getAddressInfo();
        mCityListAdapter = new CityListAdapter(this, mCityList);
        mLvOneCitiesList.setAdapter(mCityListAdapter);
        mLvOneCitiesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("city", mCityList.get(position));
                JumpIntent.jump(TierOneCitiesActivity.this, SecondaryCitiesActivity.class,
                        bundle);
            }
        });
    }

    @Override
    public void onAddressInfo(List<CityEntity.ContentBean> content) {
        mCityList.addAll(content);
        mCityListAdapter.notifyDataSetChanged();
    }
}
