package com.yst.onecity.fragment.message;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseFragment;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.onecity.R;
import com.yst.onecity.activity.login.LoginActivity;
import com.yst.onecity.adapter.NewAttentionAdapter;
import com.yst.onecity.bean.FansOrAttentionsListBean;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;

/**
 * 新的关注
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/5/17.
 */

public class NewAttentionFragment extends BaseFragment implements OnRefreshListener, OnLoadmoreListener {
    @BindView(R.id.lv_attention)
    ListView lvAttention;
    @BindView(R.id.refreshLayout_attention)
    SmartRefreshLayout refreshLayoutAttention;
    @BindView(R.id.rel_attention_nothing)
    RelativeLayout relAttentionNothing;
    int page = 1;
    private NewAttentionAdapter mNewAttentionAdapter;
    private List<FansOrAttentionsListBean.ContentBean.ListBean> fansList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_new_attention;
    }

    @Override
    public void init() {
        refreshLayoutAttention.setEnableLoadmore(true);
        mNewAttentionAdapter = new NewAttentionAdapter(getActivity(), fansList);
        lvAttention.setAdapter(mNewAttentionAdapter);
        mNewAttentionAdapter.setAttentionCallBack(new NewAttentionAdapter.AttentionCallBack() {
            @Override
            public void mAttentionCallBack(int position) {
                if (!App.manager.getLoginState()) {
                    JumpIntent.jump(getActivity(), LoginActivity.class);
                    return;
                }
                FansOrAttentionsListBean.ContentBean.ListBean listBean = fansList.get(position);
                attention(String.valueOf(listBean.getId()), String.valueOf(listBean.getStatus()));
            }
        });
    }

    @Override
    protected void setListener() {
        refreshLayoutAttention.setOnRefreshListener(this);
        refreshLayoutAttention.setOnLoadmoreListener(this);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page ++;
        fansList.clear();
        mNewAttentionAdapter.notifyDataSetChanged();
        getFansList();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        refreshFansList();
    }

    private void refreshFansList() {
        page = 1;
        fansList.clear();
        mNewAttentionAdapter.notifyDataSetChanged();
        getFansList();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshFansList();
    }

    /**
     * 获取粉丝列表
     */
    private void getFansList() {
        RequestApi.getFansList(
                String.valueOf(App.manager.getId()),
                App.manager.getPhoneNum(),
                App.manager.getUuid(),
                page,
                new AbstractNetWorkCallback<FansOrAttentionsListBean>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(FansOrAttentionsListBean fansOrAttentionsListBean) {
                        if (fansOrAttentionsListBean.getContent() != null) {
                            if (fansOrAttentionsListBean.getContent().getList().size() != NO0 && fansOrAttentionsListBean.getCode() == NO1) {
                                fansList.addAll(fansOrAttentionsListBean.getContent().getList());
                            } else {
                                if (page == 1) {
                                    fansList = new ArrayList<>();
                                } else {
                                    ToastUtils.show("已加载全部数据");
                                }
                            }
                            mNewAttentionAdapter.notifyDataSetChanged();
                            if (fansList.size() == NO0 && page == NO1) {
                                relAttentionNothing.setVisibility(View.VISIBLE);
                                lvAttention.setVisibility(View.GONE);
                            } else {
                                relAttentionNothing.setVisibility(View.GONE);
                                lvAttention.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                    @Override
                    public void onError(final String errorMsg) {
                        ToastUtils.show(errorMsg);
                    }

                    @Override
                    public void onBefore() {
                        super.onBefore();
                    }

                    @Override
                    public void onAfter() {
                        super.onAfter();
                        onLoad();
                    }
                });
    }

    /**
     * 关注(取消)
     *
     * @param id 被关注者id
     */
    private void attention(String id, String type) {
        RequestApi.attention(id, type, App.manager.getPhoneNum(), App.manager.getUuid(), new AbstractNetWorkCallback<MsgBean>() {
            @Override
            public void onSuccess(MsgBean msgBean) {
                if (msgBean != null && msgBean.getCode() == NO1) {
                    refreshFansList();
                ToastUtils.show(msgBean.getMsg());
                }
            }

            @Override
            public void onError(final String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    /**
     * 停止列表刷新的
     **/
    private void onLoad() {
        refreshLayoutAttention.finishRefresh(500);
        refreshLayoutAttention.finishLoadmore(500);
    }
}
