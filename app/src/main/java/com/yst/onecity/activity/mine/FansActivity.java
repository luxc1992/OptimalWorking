package com.yst.onecity.activity.mine;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yst.basic.framework.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.H5Const;
import com.yst.onecity.R;
import com.yst.onecity.activity.h5detail.ServerTeamPageDetailActivity;
import com.yst.onecity.activity.mine.setting.PersonDetailActivity;
import com.yst.onecity.adapter.FansAdapter;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.FansOrAttentionsListBean;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.view.ContainsEmojiEditText;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;

/**
 * 粉丝页面
 *
 * @author chenjiadi
 * @version 1.0.1
 * @date 2018/02/11
 */
public class FansActivity extends BaseActivity implements OnRefreshListener, OnLoadmoreListener {

    @BindView(R.id.et_search)
    ContainsEmojiEditText etSearch;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.tv_fans_num)
    TextView tvFansNum;
    @BindView(R.id.my_listview)
    ListView myListview;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.ll_no_date)
    LinearLayout llNoDate;


    private List<FansOrAttentionsListBean.ContentBean.ListBean> fansList = new ArrayList<>();
    private FansAdapter adapter;
    private int page = 1;
    private int searchFlag = NO0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_fans;
    }

    @Override
    public void initData() {
        smartRefreshLayout.autoRefresh(100);
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setOnLoadmoreListener(this);
        smartRefreshLayout.setEnableRefresh(true);
        smartRefreshLayout.setEnableLoadmore(true);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean isShow = actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
                if (isShow) {
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(FansActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    searchFlag = NO1;
                    searchFans(etSearch.getText().toString());
                    return true;
                }
                return false;
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    page = 1;
                    fansList.clear();
                    getFansList();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.tv_sure, R.id.ll_back})
    public void onViewClicked(View view) {
        if (!Utils.isClickable()) {
            return;
        }
        switch (view.getId()) {
            case R.id.tv_sure:
                if (TextUtils.isEmpty(etSearch.getText().toString())) {
                    ToastUtils.show("请输入搜索内容");
                    return;
                }
                searchFlag = NO1;
                searchFans(etSearch.getText().toString());
                break;
            case R.id.ll_back:
                finish();
                EventBus.getDefault().post(new EventBean("refresh"));
                break;
            default:
                break;
        }
    }

    /**
     * 搜索粉丝列表
     *
     * @param key 关键字
     */
    private void searchFans(String key) {
        RequestApi.searchFansList(App.manager.getPhoneNum(), App.manager.getUuid(), key, page, new AbstractNetWorkCallback<FansOrAttentionsListBean>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(FansOrAttentionsListBean fansOrAttentionsListBean) {
                if (fansOrAttentionsListBean.getContent() != null) {
                    if (fansOrAttentionsListBean.getContent().getList().size() != NO0 && fansOrAttentionsListBean.getCode() == 1) {
                        tvFansNum.setVisibility(View.VISIBLE);
                        tvFansNum.setText("我的粉丝 " + fansOrAttentionsListBean.getContent().getCount() + "人");
                        if (page == 1) {
                            fansList = fansOrAttentionsListBean.getContent().getList();
                        } else {
                            fansList.addAll(fansOrAttentionsListBean.getContent().getList());
                        }
                    } else {
                        if (page == 1) {
                            tvFansNum.setVisibility(View.GONE);
                            fansList = new ArrayList<>();
                            ToastUtils.show("暂无匹配用户");
                        } else {
                            ToastUtils.show("已加载全部数据");
                        }
                    }
                }
                flushFansList();
            }

            @Override
            public void onError(final String errorMsg) {
                ToastUtils.show(errorMsg);
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

    @Override
    public void onResume() {
        super.onResume();
        page = 1;
        fansList.clear();
        getFansList();
    }


    /**
     * 获取粉丝列表
     */
    private void getFansList() {
        RequestApi.getFansList(String.valueOf(App.manager.getId()),App.manager.getPhoneNum(), App.manager.getUuid(), page, new AbstractNetWorkCallback<FansOrAttentionsListBean>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(FansOrAttentionsListBean fansOrAttentionsListBean) {
                if (fansOrAttentionsListBean.getContent() != null) {
                    if (fansOrAttentionsListBean.getContent().getList().size() != NO0 && fansOrAttentionsListBean.getCode() == 1) {
                        tvFansNum.setVisibility(View.VISIBLE);
                        tvFansNum.setText("我的粉丝 " + fansOrAttentionsListBean.getContent().getCount() + "人");
                        if (page == 1) {
                            fansList = fansOrAttentionsListBean.getContent().getList();
                        } else {
                            fansList.addAll(fansOrAttentionsListBean.getContent().getList());
                        }
                    } else {
                        if (page == 1) {
                            tvFansNum.setVisibility(View.GONE);
                            fansList = new ArrayList<>();
                        } else {
                            ToastUtils.show("已加载全部数据");
                        }
                    }
                }
                flushFansList();
            }

            @Override
            public void onError(final String errorMsg) {
                ToastUtils.show(errorMsg);
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
    private void flushFansList() {
        if (null == myListview) {
            return;
        }
        if (fansList.size() == 0) {
            llNoDate.setVisibility(View.VISIBLE);
        } else {
            llNoDate.setVisibility(View.GONE);
        }
        if (adapter == null) {
            adapter = new FansAdapter(FansActivity.this, fansList);
            myListview.setAdapter(adapter);
            adapter.setListener(new FansAdapter.OnListener() {
                @Override
                public void onListener(String id, int position, int status) {
                    if (!Utils.isClickable()) {
                        return;
                    }
                    if (status == NO0) {
                        //单向关注(加关注操作)
                        operateAttention(id, "0");
                    } else {
                        //双向关注(取消关注操作)
                        operateAttention(id, "1");
                    }
                }

                @Override
                public void onCheck(String id, int personType, int adviserId) {
                    //0/null 不是猎头 1是猎头
                    if (personType == NO0) {
//                        Bundle bundle = new Bundle();
//                        bundle.putInt("type", 0);
//                        bundle.putString("personId", id);
//                        JumpIntent.jump(FansActivity.this, PersonDetailActivity.class, bundle);
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", String.valueOf(adviserId));
                        bundle.putString("url", H5Const.SERVER_TEAM_PAGE);
                        JumpIntent.jump(FansActivity.this, ServerTeamPageDetailActivity.class,bundle);
                    }
                }
            });
        } else {
            adapter.setFansList(fansList);
        }
    }

    /**
     * 取消关注
     *
     * @param id 被关注者id
     */
    private void operateAttention(String id, String type) {
        RequestApi.attention(id, type, App.manager.getPhoneNum(), App.manager.getUuid(), new AbstractNetWorkCallback<MsgBean>() {
            @Override
            public void onSuccess(MsgBean msgBean) {
                if (msgBean != null && msgBean.getCode() == NO1) {
                    getFansList();
                }
                ToastUtils.show(msgBean.getMsg());
            }

            @Override
            public void onError(final String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page = 1;
        if (searchFlag == NO1) {
            if (TextUtils.isEmpty(etSearch.getText().toString())) {
                ToastUtils.show("请输入搜索内容");
                return;
            }
            searchFans(etSearch.getText().toString());
        } else {
            getFansList();
        }
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        if (searchFlag == NO1) {
            if (TextUtils.isEmpty(etSearch.getText().toString())) {
                ToastUtils.show("请输入搜索内容");
                return;
            }
            searchFans(etSearch.getText().toString());
        } else {
            getFansList();
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
