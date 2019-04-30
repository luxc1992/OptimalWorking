package com.yst.onecity.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseFragment;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.NetworkUtils;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.Constant;
import com.yst.onecity.H5Const;
import com.yst.onecity.R;
import com.yst.onecity.activity.h5detail.ServerTeamPageDetailActivity;
import com.yst.onecity.activity.home.ChooseServeAddressActivity;
import com.yst.onecity.activity.home.PlatformInformActivity;
import com.yst.onecity.activity.home.RedPackageRulActivity;
import com.yst.onecity.activity.home.SearchHomeActivity;
import com.yst.onecity.activity.login.LoginActivity;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.home.BannerBean;
import com.yst.onecity.bean.home.BannerContentBean;
import com.yst.onecity.bean.home.CouponStateBean;
import com.yst.onecity.bean.home.ProjectClassifyBean;
import com.yst.onecity.bean.home.ProjectClassifyContentBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.PreferenceUtil;
import com.yst.onecity.view.BannerView;
import com.yst.onecity.view.CustomViewPager;
import com.yst.onecity.view.dialog.AbstractRedPackageDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;
import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;

/**
 * 首页
 *
 * @author chenxiaowei
 * @version 1.1.0
 * @date 2018/5/15
 */
public class HomePageFragment extends BaseFragment implements AMapLocationListener, EasyPermissions.PermissionCallbacks, TabLayout.OnTabSelectedListener, OnRefreshListener, OnLoadmoreListener {

    private static final int ADDRESS_CODE = 1;
    public static SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.banner_view)
    BannerView bannerView;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.tv_location_city)
    TextView tvLocationCity;
    @BindView(R.id.tv_red_point)
    TextView tvRedPoint;
    @BindView(R.id.viewPager)
    CustomViewPager viewPager;
    private AMapLocationClient mLocationClient;
    private List<ProjectClassifyBean> labels = new ArrayList<>();
    private Bundle bundle = new Bundle();
    private AbstractRedPackageDialog mRedPackageDialog;
    private String chooseName = "";
    private int pageIndex = 1;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_homepage;
    }

    @Override
    public void init() {
        smartRefreshLayout = view.findViewById(R.id.smart_refresh_layout);
        initBanner();
        if (!NetworkUtils.isConnectNet(App.mContext)) {
            String labelJson = PreferenceUtil.getString("homeLabel", "");
            ProjectClassifyContentBean projectClassifyContentBean = new Gson().fromJson(labelJson, ProjectClassifyContentBean.class);
            if (projectClassifyContentBean != null && projectClassifyContentBean.getCode() == NO1) {
                labels = projectClassifyContentBean.getContent();
                addTab();
            }
        } else {
            initLabel();
        }
        initRedDialog();
        if (!App.manager.getLoginState()) {
            mRedPackageDialog.showDialog();
        } else {
            getMemberIsReceiveCoupon();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (TextUtils.isEmpty(chooseName)) {
            initLocation();
            chooseName = "";
        }
        if (App.manager.getLoginState()) {
            getMessageState();
        }
    }

    /**
     * 轮播图
     */
    private void initBanner() {
        final List<String> imageList = new ArrayList<>();
        RequestApi.getBanner(new AbstractNetWorkCallback<BannerContentBean>() {
            @Override
            public void onSuccess(BannerContentBean bannerContentBean) {
                if (bannerContentBean.getCode() == Constant.NO1) {
                    final List<BannerBean> bannerList = bannerContentBean.getContent();
                    if (bannerList != null) {
                        if (bannerList.size() > 0) {
                            for (int i = 0; i < bannerList.size(); i++) {
                                imageList.add(bannerList.get(i).getAdvertisementImage());
                            }

                            bannerView.setInternetImageResources(imageList, true);
                            bannerView.setImageCycleViewListener(new BannerView.ImageCycleViewListener() {
                                @Override
                                public void onImageClick(int position) {
                                    if (Utils.isClickable()) {
                                        bundle.putString("url", bannerList.get(position).getAdvertisementLink());
                                        JumpIntent.jump(getActivity(), ServerTeamPageDetailActivity.class, bundle);
                                    }
                                }
                            });
                        }
                    }
                } else {
                    ToastUtils.show(bannerContentBean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });

    }

    /**
     * 课题分类
     */
    private void initLabel() {
        RequestApi.getHomeClassify(new AbstractNetWorkCallback<ProjectClassifyContentBean>() {
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
            public void onSuccess(ProjectClassifyContentBean projectClassifyContentBean) {
                if (projectClassifyContentBean.getCode() == Constant.NO1) {
                    labels = projectClassifyContentBean.getContent();
                    if (labels != null) {
                        if (labels.size() != 0) {
                            addTab();
                        }
                    }
                } else {
                    ToastUtils.show(projectClassifyContentBean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }

            @Override
            public void onSuccessString(String jsonData) {
                super.onSuccessString(jsonData);
                PreferenceUtil.put("homeLabel", jsonData);
            }
        });
    }

    @SuppressLint("InflateParams")
    private void addTab() {
        int size = labels.size();
        DissertationAssembleAdapter mAdapter = new DissertationAssembleAdapter(getChildFragmentManager());
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(this);
        //设置欲缓存页
        viewPager.setOffscreenPageLimit(size);
        for (int i = 0; i < size; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                //给每一个tab设置view
                tab.setCustomView(R.layout.item_homepage_type);
                TextView textView = tab.getCustomView().findViewById(R.id.tv_label);
                textView.setText(labels.get(i).getDescriptionName());
                if (i == 0) {
                    textView.setTextColor(0xFFED5452);
                }
            }
        }
    }

    /**
     * 是否领取过新人红包
     */
    private void getMemberIsReceiveCoupon() {
        RequestApi.getMemberIsReceiveCoupon(App.manager.getUuid(), App.manager.getPhoneNum(), new AbstractNetWorkCallback<CouponStateBean>() {
            @Override
            public void onSuccess(CouponStateBean mCouponStateBean) {
                if (mCouponStateBean.getCode() == NO1) {
                    if (mCouponStateBean.getContent().equals(String.valueOf(NO0)) || TextUtils.isEmpty(mCouponStateBean.getContent())) {
                        mRedPackageDialog.showDialog();
                    }
                }
            }

            @Override
            public void onError(String errorMsg) {
            }
        });
    }

    @Override
    public void initCtrl() {

    }

    /**
     * 初始化红包弹窗
     */
    private void initRedDialog() {
        mRedPackageDialog = new AbstractRedPackageDialog(getActivity()) {
            @Override
            public void openClick() {
                mRedPackageDialog.dismissDialog();
                RedPackageRulActivity.getJumpActivity(getActivity());
            }

            @Override
            public void closeClick() {
                mRedPackageDialog.dismissDialog();
            }
        };
    }

    @OnClick({R.id.iv_search, R.id.tv_location_city, R.id.iv_notice})
    public void onViewClicked(View view) {
        if (!Utils.isClickable()) {
            return;
        }
        switch (view.getId()) {
            case R.id.iv_search:
                JumpIntent.jump(getActivity(), SearchHomeActivity.class);
                break;
            case R.id.tv_location_city:
                Intent intent = new Intent(getActivity(), ChooseServeAddressActivity.class);
                startActivityForResult(intent, ADDRESS_CODE);
                break;
            case R.id.iv_notice:
                if (App.manager.getLoginState()) {
                    PlatformInformActivity.getJumpApplyAffirmActivity(getActivity());
                } else {
                    JumpIntent.jump(getActivity(), LoginActivity.class);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void setListener() {
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setOnLoadmoreListener(this);
        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putString("url", H5Const.H5_BANNER1);
                JumpIntent.jump(getActivity(), ServerTeamPageDetailActivity.class, bundle);
            }
        });
        viewPager.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPager.resetHeight(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case ADDRESS_CODE:
                chooseName = data.getStringExtra("address");
                tvLocationCity.setText(chooseName);
                PreferenceUtil.put(Constant.LATITUDE, data.getStringExtra(Constant.LATITUDE));
                PreferenceUtil.put(Constant.LONGITUDE, data.getStringExtra(Constant.LONGITUDE));
                break;
            default:
                break;
        }
    }

    /**
     * 定位
     */
    private void initLocation() {
        if (mLocationClient == null) {
            //初始化定位
            mLocationClient = new AMapLocationClient(getContext());
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
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                PreferenceUtil.put(Constant.LATITUDE, String.valueOf(aMapLocation.getLatitude()));
                PreferenceUtil.put(Constant.LONGITUDE, String.valueOf(aMapLocation.getLongitude()));
                tvLocationCity.setText(ConstUtils.getStringNoEmpty(aMapLocation.getPoiName()));
            } else {
                if (aMapLocation.getErrorCode() == Constant.NO12) {
                    EasyPermissions.requestPermissions(this, "请打开定位权限", Constant.NO100, Manifest.permission.ACCESS_COARSE_LOCATION);
                }
            }
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        initLocation();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        tvLocationCity.setText("定位失败");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initBanner();
            if (!NetworkUtils.isConnectNet(App.mContext)) {
                String labelJson = PreferenceUtil.getString("homeLabel", "");
                ProjectClassifyContentBean projectClassifyContentBean = new Gson().fromJson(labelJson, ProjectClassifyContentBean.class);
                if (projectClassifyContentBean != null && projectClassifyContentBean.getCode() == NO1) {
                    labels = projectClassifyContentBean.getContent();
                }
            } else {
                initLabel();
            }
            if (App.manager.getLoginState()) {
                getMessageState();
            }
        }
    }

    /**
     * 获取消息阅读状态
     */
    private void getMessageState() {
        RequestApi.getMessage(App.manager.getPhoneNum(), App.manager.getUuid(), new AbstractNetWorkCallback<MsgBean>() {
            @Override
            public void onSuccess(MsgBean msgBean) {
                if (msgBean.getCode() == NO1) {
                    if (Constant.HAVE.equals(msgBean.getMsg())) {
                        tvRedPoint.setVisibility(View.VISIBLE);
                    } else {
                        tvRedPoint.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (tab.getCustomView() == null) {
            return;
        }
        TextView textView = tab.getCustomView().findViewById(R.id.tv_label);
        textView.setTextColor(0xFFED5452);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        if (tab.getCustomView() == null) {
            return;
        }
        TextView textView = tab.getCustomView().findViewById(R.id.tv_label);
        textView.setTextColor(0xFF333333);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageIndex = 1;
        EventBus.getDefault().post(new EventBean("page", pageIndex));
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        pageIndex++;
        EventBus.getDefault().post(new EventBean("page", pageIndex));
    }

    class DissertationAssembleAdapter extends FragmentPagerAdapter {

        private DissertationAssembleAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return HomeProjectFragment.newInstance(labels.get(position).getId());
        }

        @Override
        public int getCount() {
            return labels.size();
        }
    }

}
