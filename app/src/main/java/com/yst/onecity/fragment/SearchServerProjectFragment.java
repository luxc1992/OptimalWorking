package com.yst.onecity.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yst.basic.framework.App;
import com.yst.basic.framework.adapter.AbstractCommonAdapter;
import com.yst.basic.framework.base.BaseFragment;
import com.yst.basic.framework.entity.holder.CommonViewHolder;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.view.roundedimageview.RoundedImageView;
import com.yst.onecity.H5Const;
import com.yst.onecity.R;
import com.yst.onecity.activity.h5detail.ServerTeamPageDetailActivity;
import com.yst.onecity.activity.login.LoginActivity;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.ServerProjectBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;

/**
 * 搜索服务项目列表
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/5/18.
 */

public class SearchServerProjectFragment extends BaseFragment implements OnRefreshListener, OnLoadmoreListener {

    @BindView(R.id.smartRefreshLayout_server_project)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.lv_server_project)
    ListView listView;
    @BindView(R.id.rl_empty)
    RelativeLayout rlEmpty;

    private AbstractCommonAdapter<ServerProjectBean.ContentBean> adapter;
    private List<ServerProjectBean.ContentBean> mList = new ArrayList<>();
    public static String searchName = "searchName";
    private int page = 1;
    /**
     * 搜索的关键词
     */
    private String keyWord = "";
    /**
     * 关注成功的条目
     */
    private int mPosition = -1;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_server_project;
    }

    @Override
    public void init() {
        if (null != getArguments()) {
            keyWord = getArguments().getString(searchName);
        }
        smartRefreshLayout.setEnableLoadmore(true);
        smartRefreshLayout.setOnLoadmoreListener(this);
        smartRefreshLayout.setOnRefreshListener(this);
        adapter = new AbstractCommonAdapter<ServerProjectBean.ContentBean>(
                getContext(), mList, R.layout.item_search_service) {
            @Override
            public void convert(CommonViewHolder holder, final int position, final ServerProjectBean.ContentBean item) {
                TextView tvAttention = holder.getView(R.id.txt_item_new_attention);
                RoundedImageView ivBg = holder.getView(R.id.iv_search_home);
                RoundedImageView ivIcon = holder.getView(R.id.iv_item_search_icon);
                RelativeLayout relComment = holder.getView(R.id.rel_comment);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ivIcon.getLayoutParams();
                layoutParams.height = App.screenWidth * 4 / 9;
                ivIcon.setLayoutParams(layoutParams);
                Glide.with(getActivity())
                        .load(String.valueOf(item.getImageAddress())).error(R.mipmap.default_product_icon).into(ivBg);
                relComment.setVisibility(View.GONE);
                if (null != item.getAdvisor() && null != item.getAdvisor().getImgUrl()) {
                relComment.setVisibility(View.VISIBLE);
                    Glide.with(getActivity())
                            .load(item.getAdvisor().getImgUrl()).error(R.mipmap.default_product_icon).into(ivIcon);
                    holder.setText(R.id.tv_service_team_name, String.valueOf(item.getAdvisor().getNikeName()));
                    holder.setText(R.id.tv_service_team_desc, String.valueOf(item.getAdvisor().getContent()));
                    if (item.getAdvisor().getIsFollow() == NO0) {
                        tvAttention.setText("+ 关注 ");
                    } else {
                        tvAttention.setText(" 已关注 ");
                    }
                    tvAttention.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!App.manager.getLoginState()) {
                                JumpIntent.jump(getActivity(), LoginActivity.class);
                                return;
                            }
                            if (item.getAdvisor().getIsFollow() == NO0) {
                                mPosition = position;
                                attention(String.valueOf(item.getAdvisor().getMemberId()), String.valueOf(item.getAdvisor().getIsFollow()));
                            }
                        }
                    });
                }
                holder.setText(R.id.tv_sever_project_name, String.valueOf(item.getTitle()));
                holder.setText(R.id.tv_sever_project_price, String.valueOf("¥ " + item.getPrice()));
            }
        };
        listView.setAdapter(adapter);
        requestServerProjectByKeyWords(page, keyWord);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle2 = new Bundle();
                //服务项目id
                bundle2.putString("id", String.valueOf(mList.get(position).getId()));
                bundle2.putString("url", H5Const.SERVE_TEM_DETAILS);
                JumpIntent.jump(getActivity(), ServerTeamPageDetailActivity.class, bundle2);

            }
        });
    }

    /**
     * 通过关键字 获取服务项目
     *
     * @param page
     * @param title 搜索内容
     */
    public void requestServerProjectByKeyWords(final int page, String title) {
        if (page == NO1) {
            mList.clear();
            if (null != adapter) {
                adapter.notifyDataSetChanged();
            }
        }
        RequestApi.getServerProjectBySearch("2", String.valueOf(App.manager.getId()), title, page, new AbstractNetWorkCallback<ServerProjectBean>() {
            @Override
            public void onAfter() {
                super.onAfter();
                dismissInfoProgressDialog();
                loadFinish();
            }

            @Override
            public void onBefore() {
                super.onBefore();
                showInfoProgressDialog();
            }

            @Override
            public void onSuccess(ServerProjectBean recommendedBean) {
                boolean b = page == NO1 && (recommendedBean.getContent() == null || recommendedBean.getContent().size() == 0);
                if (b) {
                    rlEmpty.setVisibility(View.VISIBLE);
                } else {
                    rlEmpty.setVisibility(View.GONE);
                    if (page != NO1 && recommendedBean.getContent().size() == NO0) {
                        ToastUtils.show("没有更多数据了");
                    }
                    mList.addAll(recommendedBean.getContent());
                    if (null != adapter) {
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
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
                    mList.get(mPosition).getAdvisor().setIsFollow(NO1);
                    if (null != adapter) {
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    mPosition = -1;
                }
                ToastUtils.show(msgBean.getMsg());
            }

            @Override
            public void onError(final String errorMsg) {
                mPosition = -1;
                ToastUtils.show(errorMsg);
            }
        });
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        requestServerProjectByKeyWords(page, keyWord);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page = 1;
        requestServerProjectByKeyWords(page, keyWord);
    }

    private void loadFinish() {
        smartRefreshLayout.finishRefresh(500);
        smartRefreshLayout.finishLoadmore(500);
    }
}