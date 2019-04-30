package com.yst.onecity.activity.mine;

import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.view.roundedimageview.RoundedImageView;
import com.yst.onecity.R;
import com.yst.onecity.adapter.HunttingBeansAdapter;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.HuntBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.ConstUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.yst.onecity.Constant.NO0;

/**
 * 猎豆页面
 *
 * @author chenjiadi
 * @version 1.0.1
 * @date 2018/02/11
 */
public class HuntingBeansActivity extends BaseActivity {

    @BindView(R.id.headView)
    RoundedImageView headView;
    @BindView(R.id.tv_beans_number)
    TextView tvBeansNumber;
    @BindView(R.id.my_listview)
    ListView myListView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.ll_no_date)
    LinearLayout llNoDate;
    private HunttingBeansAdapter adapter;
    private int page = 1;
    private List<HuntBean.ContentBean.ListBean> list = new ArrayList<>();


    @Override
    public int getLayoutId() {
        return R.layout.activity_hunting_beans;
    }

    @Override
    public void initData() {
        smartRefreshLayout.autoRefresh(100);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page=1;
                getHunttingBean();
            }
        });
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                getHunttingBean();
            }
        });
        smartRefreshLayout.setEnableRefresh(true);
        smartRefreshLayout.setEnableLoadmore(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        page = 1;
        list.clear();
        getHunttingBean();


    }
    /**
     * 获取猎豆明细
     */
    private void getHunttingBean() {
        RequestApi.myhunt(App.manager.getUuid(), App.manager.getPhoneNum(), page, new AbstractNetWorkCallback<HuntBean>() {
            @Override
            public void onSuccess(HuntBean huntBean) {
                if (huntBean.getContent() != null) {
                    if (huntBean.getContent().getList().size() != NO0 && huntBean.getCode() == 1) {
                        tvBeansNumber.setText(ConstUtils.getStringNoEmpty(String.valueOf(huntBean.getContent().getScore())));
                        if (page == 1) {
                            list = huntBean.getContent().getList();
                        } else {
                            list.addAll(huntBean.getContent().getList());
                        }
                    } else {
                        if (page == 1) {
                            list = new ArrayList<>();
                        } else {
                            ToastUtils.show("已加载全部数据");
                        }
                    }
                }
                flushBeansList();
            }

            @Override
            public void onAfter() {
                super.onAfter();
                dismissInfoProgressDialog();
                onLoad();
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });

    }

    /**
     * 刷新适配器
     */
    private void flushBeansList() {
        if (null == myListView) {
            return;
        }
        if (list.size() == 0) {
            llNoDate.setVisibility(View.VISIBLE);
        } else {
            llNoDate.setVisibility(View.GONE);
        }
        if (adapter == null) {
            adapter = new HunttingBeansAdapter(HuntingBeansActivity.this, list);
            myListView.setAdapter(adapter);
        } else {
            adapter.setFansList(list);
        }
    }

    /**
     * 停止列表刷新的
     **/
    private void onLoad() {
        smartRefreshLayout.finishRefresh(500);
        smartRefreshLayout.finishLoadmore(500);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            EventBus.getDefault().post(new EventBean("refresh"));
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
