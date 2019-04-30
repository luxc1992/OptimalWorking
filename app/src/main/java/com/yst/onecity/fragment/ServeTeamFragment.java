package com.yst.onecity.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseFragment;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.MyLog;
import com.yst.basic.framework.utils.NetworkUtils;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.activity.h5detail.ServerTeamPageDetailActivity;
import com.yst.onecity.adapter.ServerTeamAdapter;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.ServerTeamBannerBean;
import com.yst.onecity.bean.ServerTeamClassifyDropListBean;
import com.yst.onecity.bean.ServerTeamListBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.PreferenceUtil;
import com.yst.onecity.view.ImageCycleView;
import com.yst.onecity.view.ServerTeamClassifyPopwindow;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yst.onecity.Constant.CLEAR;
import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO2;
import static com.yst.onecity.Constant.NO3;

/**
 * 服务团队
 *
 * @author chenxiaowei
 * @version 1.1.0
 * @date 2018/5/15
 */
public class ServeTeamFragment extends BaseFragment implements AdapterView.OnItemClickListener, OnRefreshListener, OnLoadmoreListener {
    @BindView(R.id.ll_container)
    LinearLayout llContainer;
    @BindView(R.id.tv_classify)
    TextView tvClassify;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    @BindView(R.id.tv_server_range)
    TextView tvServerRange;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.lv_server_team)
    ListView lvServerTeam;
    @BindView(R.id.smartRefreshLayout_product_plan)
    SmartRefreshLayout smartRefreshLayoutProductPlan;
    @BindView(R.id.image_cycle_view)
    ImageCycleView imageCycleView;
    @BindView(R.id.ll_no_date)
    LinearLayout llNoDate;

    /**
     * 轮播图集合
     */
    private List<String> bannerList = new ArrayList<>();
    private List<ServerTeamListBean.ContentBean> list = new ArrayList<>();
    /**
     * 分类集合
     */
    List<ServerTeamClassifyDropListBean.ContentBean> classifyList = new ArrayList<>();
    /**
     * 距离集合
     */
    List<ServerTeamClassifyDropListBean.ContentBean> distanceList = new ArrayList<>();
    List<ServerTeamClassifyDropListBean.ContentBean> serverRangeList = new ArrayList<>();

    /**
     * 服务团队适配器
     */
    private ServerTeamAdapter serverTeamAdapter;
    /**
     * 分类 距离 智能筛选弹出框
     */
    private ServerTeamClassifyPopwindow serverTeamPopWindow;
    /**
     * 弹框类型
     */
    private int checkType = 0;
    private int page = 1;
    private Bundle bundle = new Bundle();
    /**
     * 分类id
     */
    private String typeId = null;
    /**
     * 距离id
     */
    private String low = null;
    /**
     * 智能筛选
     */
    private String numberType = null;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_serve_team;
    }

    @Override
    public void init() {
        initview();
        getClassify();
        getDistance();
        getServerRange();
        if (!NetworkUtils.isConnectNet(App.mContext)) {
            //无网络
            String cacheList = PreferenceUtil.getString("serverTeamList", "");
            if (!TextUtils.isEmpty(cacheList)) {
                ServerTeamListBean serverTeamListBean = new Gson().fromJson(cacheList, ServerTeamListBean.class);
                if (serverTeamListBean != null && serverTeamListBean.getCode() == NO1) {
                    list = serverTeamListBean.getContent();
                }
            }
            flushServerTeamList();
        } else {
            getServerTeamList();
        }
    }

    /**
     * 获取距离数据
     */
    private void getDistance() {
        ServerTeamClassifyDropListBean.ContentBean nearContentBean = new ServerTeamClassifyDropListBean.ContentBean();
        nearContentBean.setCheck(false);
        nearContentBean.setId(5);
        nearContentBean.setName("附近");
        distanceList.add(nearContentBean);
        ServerTeamClassifyDropListBean.ContentBean oneContentBean = new ServerTeamClassifyDropListBean.ContentBean();
        oneContentBean.setCheck(false);
        oneContentBean.setId(1);
        oneContentBean.setName("1km");
        distanceList.add(oneContentBean);
        ServerTeamClassifyDropListBean.ContentBean threeContentBean = new ServerTeamClassifyDropListBean.ContentBean();
        threeContentBean.setCheck(false);
        threeContentBean.setId(3);
        threeContentBean.setName("3km");
        distanceList.add(threeContentBean);
        ServerTeamClassifyDropListBean.ContentBean fiveContentBean = new ServerTeamClassifyDropListBean.ContentBean();
        fiveContentBean.setCheck(false);
        fiveContentBean.setId(5);
        fiveContentBean.setName("5km");
        distanceList.add(fiveContentBean);
        ServerTeamClassifyDropListBean.ContentBean tenContentBean = new ServerTeamClassifyDropListBean.ContentBean();
        tenContentBean.setCheck(false);
        tenContentBean.setId(10);
        tenContentBean.setName("10km");
        distanceList.add(tenContentBean);
    }

    /**
     * 获取智能筛选数据
     */
    private void getServerRange() {
        ServerTeamClassifyDropListBean.ContentBean intelligentContentBean = new ServerTeamClassifyDropListBean.ContentBean();
        intelligentContentBean.setCheck(false);
        intelligentContentBean.setId(1);
        intelligentContentBean.setName("离我最近");
        serverRangeList.add(intelligentContentBean);
        ServerTeamClassifyDropListBean.ContentBean goodCommentContentBean = new ServerTeamClassifyDropListBean.ContentBean();
        goodCommentContentBean.setCheck(false);
        goodCommentContentBean.setId(2);
        goodCommentContentBean.setName("好评优先");
        serverRangeList.add(goodCommentContentBean);
    }

    /**
     * 获取分类数据
     */
    private void getClassify() {
        RequestApi.getServerTeamClassifyDropList(new AbstractNetWorkCallback<ServerTeamClassifyDropListBean>() {
            @Override
            public void onSuccess(ServerTeamClassifyDropListBean serverTeamClassifyDropListBean) {
                if (serverTeamClassifyDropListBean.getCode() == NO1) {
                    if (serverTeamClassifyDropListBean.getContent() != null) {
                        classifyList = serverTeamClassifyDropListBean.getContent();
                        for (int i = 0; i < serverTeamClassifyDropListBean.getContent().size(); i++) {
                            serverTeamClassifyDropListBean.getContent().get(i).setCheck(false);
                        }
                    }
                }
            }

            @Override
            public void onError(String errorMsg) {
            }
        });

    }

    @OnClick({R.id.tv_classify, R.id.tv_distance, R.id.tv_server_range})
    public void onViewClicked(View view) {
        if (!Utils.isClickable()) {
            return;
        }
        switch (view.getId()) {
            case R.id.tv_classify:
                checkType = 1;
                serverTeamPopWindow = new ServerTeamClassifyPopwindow(getActivity(), classifyList, this, true);
                serverTeamPopWindow.showPopupWindow(line);
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 0.5f;
                getActivity().getWindow().setAttributes(lp);
                serverTeamPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                        lp.alpha = 1f;
                        getActivity().getWindow().setAttributes(lp);
                    }
                });
                break;
            case R.id.tv_distance:
                checkType = 2;
                serverTeamPopWindow = new ServerTeamClassifyPopwindow(getActivity(), distanceList, this, false);
                serverTeamPopWindow.showPopupWindow(line);
                WindowManager.LayoutParams lps = getActivity().getWindow().getAttributes();
                lps.alpha = 0.5f;
                getActivity().getWindow().setAttributes(lps);
                serverTeamPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                        lp.alpha = 1f;
                        getActivity().getWindow().setAttributes(lp);
                    }
                });

                break;
            case R.id.tv_server_range:
                checkType = 3;
                serverTeamPopWindow = new ServerTeamClassifyPopwindow(getActivity(), serverRangeList, this, false);
                serverTeamPopWindow.showPopupWindow(line);
                WindowManager.LayoutParams lpss = getActivity().getWindow().getAttributes();
                lpss.alpha = 0.5f;
                getActivity().getWindow().setAttributes(lpss);
                serverTeamPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                        lp.alpha = 1f;
                        getActivity().getWindow().setAttributes(lp);
                    }
                });
                break;
            default:
                break;
        }
    }

    /**
     * 初始化轮播图控件
     */
    private void initview() {
        smartRefreshLayoutProductPlan.setOnRefreshListener(this);
        smartRefreshLayoutProductPlan.setOnLoadmoreListener(this);
        smartRefreshLayoutProductPlan.setEnableRefresh(true);
        smartRefreshLayoutProductPlan.setEnableLoadmore(true);
        imageCycleView.setmGroupGravity(Gravity.CENTER_HORIZONTAL);
        getServerBanner();
    }

    /**
     * 获取轮播图地址
     */
    private void getServerBanner() {
        RequestApi.getServerTeamBanner(new AbstractNetWorkCallback<ServerTeamBannerBean>() {
            @Override
            public void onSuccess(final ServerTeamBannerBean serverTeamBannerBean) {
                if (serverTeamBannerBean.getCode() == NO1) {
                    if (serverTeamBannerBean.getContent() != null) {
                        for (int i = 0; i < serverTeamBannerBean.getContent().size(); i++) {
                            bannerList.add(serverTeamBannerBean.getContent().get(i).getAdvertisementImage());
                        }
                    }
                    imageCycleView.setImageResources(bannerList, false);
                    imageCycleView.setImageCycleViewListener(new ImageCycleView.ImageCycleViewListener() {
                        @Override
                        public void onImageClick(int position, View imageView) {
                            if (serverTeamBannerBean.getContent().get(position).getAdvertisementLink() != null) {
                                bundle.putString("url", serverTeamBannerBean.getContent().get(position).getAdvertisementLink());
                                JumpIntent.jump(getActivity(), ServerTeamPageDetailActivity.class, bundle);
                            }
                        }
                    });
                }
            }

            @Override
            public void onError(String errorMsg) {

            }
        });
    }

    /**
     * 获取服务团队列表
     */
    private void getServerTeamList() {
        MyLog.e("ssssssssaaaaaaaaaa", "longitude----------" + PreferenceUtil.getString(Constant.LONGITUDE, "") + "-------latitude----------" + PreferenceUtil.getString(Constant.LATITUDE, ""));
        String longitude = PreferenceUtil.getString(Constant.LONGITUDE, "");
        String latitude = PreferenceUtil.getString(Constant.LATITUDE, "");
        MyLog.e("aaaaaaaa", "typeid----" + typeId + "\n" + low + "\n" + numberType);
        RequestApi.getServerTeamList(typeId, low, numberType, TextUtils.isEmpty(longitude) ? "39" : longitude, TextUtils.isEmpty(latitude) ? "116" : latitude, page, 10, new AbstractNetWorkCallback<ServerTeamListBean>() {
            @Override
            public void onSuccess(ServerTeamListBean serverTeamListBean) {
                if (serverTeamListBean.getCode() == NO1 && serverTeamListBean.getContent().size() != NO0) {
                    if (page == 1) {
                        list = serverTeamListBean.getContent();
                    } else {
                        list.addAll(serverTeamListBean.getContent());
                    }

                } else {
                    if (page == 1) {
                        list = new ArrayList<>();
                    } else {
                        ToastUtils.show("已加载全部数据");
                    }
                }
                flushServerTeamList();
            }

            @Override
            public void onSuccessString(String jsonData) {
                super.onSuccessString(jsonData);
                if (page == NO1) {
                    PreferenceUtil.put("serverTeamList", jsonData);
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
                flushServerTeamList();
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
                onLoad();
            }
        });
    }

    /**
     * 刷新适配器
     */
    private void flushServerTeamList() {
        if (null == lvServerTeam) {
            return;
        }
        MyLog.e("aaasdsfdsfds", "ssssssssssssssssss---------" + list.size());
        if (list.size() == 0) {
            llNoDate.setVisibility(View.VISIBLE);
        } else {
            llNoDate.setVisibility(View.GONE);
        }
        if (serverTeamAdapter == null) {
            serverTeamAdapter = new ServerTeamAdapter(getActivity(), list);
            lvServerTeam.setAdapter(serverTeamAdapter);
        } else {
            serverTeamAdapter.setServerTeamList(list);
        }
    }

    /**
     * pop item点击事件
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (checkType) {
            case NO1:
                for (int i = 0; i < classifyList.size(); i++) {
                    classifyList.get(i).setCheck(false);
                }
                classifyList.get(position).setCheck(true);
                if (classifyList.get(position).getId() == NO0) {
                    typeId = null;
                } else {
                    typeId = String.valueOf(classifyList.get(position).getId());
                }
                list.clear();
                page = 1;
                getServerTeamList();
                break;
            case NO2:
                for (int i = 0; i < distanceList.size(); i++) {
                    distanceList.get(i).setCheck(false);
                }
                distanceList.get(position).setCheck(true);
                low = String.valueOf(distanceList.get(position).getId());
                list.clear();
                page = 1;
                getServerTeamList();
                break;
            case NO3:
                for (int i = 0; i < serverRangeList.size(); i++) {
                    serverRangeList.get(i).setCheck(false);
                }
                serverRangeList.get(position).setCheck(true);
                numberType = String.valueOf(serverRangeList.get(position).getId());
                list.clear();
                page = 1;
                getServerTeamList();
                break;
            default:
                break;
        }


        serverTeamPopWindow.dismiss();

    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page = 1;
        getServerTeamList();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        getServerTeamList();
    }

    /**
     * 停止列表刷新的
     **/
    private void onLoad() {
        smartRefreshLayoutProductPlan.finishRefresh(500);
        smartRefreshLayoutProductPlan.finishLoadmore(500);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            typeId = null;
            low = null;
            numberType = null;
            page = 1;
            getServerTeamList();

        }

    }
}
