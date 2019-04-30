package com.yst.onecity.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.yst.basic.framework.adapter.AbstractCommonAdapter;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.entity.holder.CommonViewHolder;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.utils.ConstUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 选择服务地址
 *
 * @author chenxiaowei
 * @version 1.1.0
 * @date 2018/5/15
 */
public class ChooseServeAddressActivity extends BaseActivity implements AMapLocationListener, PoiSearch.OnPoiSearchListener, AdapterView.OnItemClickListener, EasyPermissions.PermissionCallbacks {

    private static final int CITY_CODE = 1;
    private static final int SEARCH_CODE = 2;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_location_address)
    TextView tvLocationAddress;
    private AMapLocationClient mlocationClient;

    @BindView(R.id.listView)
    ListView listView;
    private ArrayList<PoiItem> data = new ArrayList<>();
    private String locationCity;
    private boolean isChangeCity;
    private String cityCode;
    private String nowCity;

    @Override
    public int getLayoutId() {
        return R.layout.activity_choose_serve_address;
    }

    @Override
    public void initData() {
        initTitleBar("选择服务地址");
        initLocation();
    }

    /**
     * 初始化定位
     */
    private void initLocation() {
        tvLocationAddress.setText("定位中...");
        showInfoProgressDialog();
        if (mlocationClient == null) {
            //初始化定位
            mlocationClient = new AMapLocationClient(this);
            //初始化定位参数
            AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
            //设置定位回调监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置只定位一次
            mLocationOption.setOnceLocation(true);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
        }
        //启动定位
        mlocationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                locationCity = aMapLocation.getCity();
                nowCity = locationCity;
                tvCity.setText(ConstUtils.getStringNoEmpty(aMapLocation.getCity()));
                tvLocationAddress.setText(ConstUtils.getStringNoEmpty(aMapLocation.getPoiName()));
                cityCode = aMapLocation.getCityCode();
                LatLonPoint mCurrentPoint = new LatLonPoint(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                startSearch("", mCurrentPoint);
            } else {
                dismissInfoProgressDialog();
                tvLocationAddress.setText("定位失败,点击重试");
            }
        }
    }

    /**
     * 搜索poi
     *
     * @param mCurrentPoint 中心点
     */
    private void startSearch(String city, LatLonPoint mCurrentPoint) {
        PoiSearch.Query query;
        if (TextUtils.isEmpty(city)) {
            query = new PoiSearch.Query("", "汽车服务|汽车销售|汽车维修|摩托车服务|餐饮服务|购物服务|生活服务" +
                    "|体育休闲服务|医疗保健服务|住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|" +
                    "金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施", cityCode);
        } else {
            query = new PoiSearch.Query(city, "", "");
        }
        // 设置每页最多返回多少条poiitem
        query.setPageSize(10);
        //设置查第一页
        query.setPageNum(0);
        PoiSearch poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        if (TextUtils.isEmpty(city)) {
            //设置周边搜索的中心点以及区域
            poiSearch.setBound(new PoiSearch.SearchBound(mCurrentPoint, 1000, true));
        }
        //开始搜索
        poiSearch.searchPOIAsyn();
    }


    @OnClick({R.id.tv_city, R.id.iv_refresh, R.id.tv_input_address, R.id.tv_location_address})
    public void onViewClicked(View view) {
        if (!Utils.isClickable()) {
            return;
        }
        switch (view.getId()) {
            case R.id.tv_city:
                JumpIntent.jump(ChooseServeAddressActivity.this, ChangeCityActivity.class, CITY_CODE);
                break;
            case R.id.tv_location_address:
                returnPage(tvLocationAddress.getText().toString(), data.get(0).getLatLonPoint());
                break;
            case R.id.iv_refresh:
                isChangeCity = false;
                initLocation();
                break;
            case R.id.tv_input_address:
                Bundle bundle = new Bundle();
                bundle.putString("city", nowCity);
                JumpIntent.jump(ChooseServeAddressActivity.this, SearchAddressActivity.class, bundle, SEARCH_CODE);
                break;
            default:
                break;
        }
    }

    /**
     * 返回上个页面
     *
     * @param nowAddress 已选择的地址
     */
    private void returnPage(String nowAddress, LatLonPoint mCurrentPoint) {
        Intent intent = new Intent();
        intent.putExtra("address", nowAddress);
        intent.putExtra("latitude", String.valueOf(mCurrentPoint.getLatitude()));
        intent.putExtra("longitude", String.valueOf(mCurrentPoint.getLongitude()));
        setResult(RESULT_OK, intent);
        ChooseServeAddressActivity.this.finish();
    }

    @Override
    public void setListener() {
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        if (i == Constant.NO1000) {
            data.clear();
            data = poiResult.getPois();
            if (data.size() > 0) {
                initCtrllor(data);
            }
        } else {
            ToastUtils.show("对不起，没有搜索到相关数据！");
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    public void initCtrllor(ArrayList<PoiItem> data) {
        if (isChangeCity) {
            tvLocationAddress.setText(data.get(0).getTitle());
            cityCode = data.get(0).getCityCode();
        }
        AbstractCommonAdapter<PoiItem> adapter = new AbstractCommonAdapter<PoiItem>(context, data, R.layout.item_serach_address) {
            @Override
            public void convert(CommonViewHolder holder, int position, PoiItem item) {
                holder.setText(R.id.tv_address_name, item.getTitle());
                holder.setText(R.id.tv_detail_address, item.getSnippet());
            }
        };
        listView.setAdapter(adapter);
        dismissInfoProgressDialog();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        returnPage(data.get(i).getTitle(), data.get(i).getLatLonPoint());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CITY_CODE:
                    String city = data.getStringExtra("city");
                    if (city.equals(locationCity)) {
                        initLocation();
                        isChangeCity = false;
                    } else {
                        nowCity = city;
                        isChangeCity = true;
                        tvCity.setText(city);
                        showInfoProgressDialog();
                        startSearch(city, null);
                    }
                    break;
                case SEARCH_CODE:
                    String searchAddress = data.getStringExtra("searchAddress");
                    LatLonPoint point = data.getParcelableExtra("point");
                    tvLocationAddress.setText(searchAddress);
                    showInfoProgressDialog();
                    startSearch("", point);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        initLocation();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        ChooseServeAddressActivity.this.finish();
    }
}
