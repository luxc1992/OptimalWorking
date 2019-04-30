package com.yst.onecity.fragment.message;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseFragment;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.activity.login.LoginActivity;
import com.yst.onecity.adapter.InteractionAdapter;
import com.yst.onecity.bean.InteractionBean;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.view.dialog.SendCommentDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO10;

/**
 * 互动fragment页面
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/5/31
 */

public class HuDongFragment extends BaseFragment implements OnRefreshListener, OnLoadmoreListener, AdapterView.OnItemClickListener {
    @BindView(R.id.lv_hudong)
    ListView mLvHudong;
    @BindView(R.id.refreshLayout_hudong)
    SmartRefreshLayout mRefreshLayoutHudong;
    @BindView(R.id.rel_hudong_nothing)
    RelativeLayout mRelHudongNothing;
    int page = 1;
    private List<InteractionBean.ContentBean> mList = new ArrayList<>();
    private InteractionAdapter mInteractionAdapter;
    private boolean isRefresh = true;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_hudong;
    }

    @Override
    public void init() {
        mRefreshLayoutHudong.setOnRefreshListener(this);
        mRefreshLayoutHudong.setOnLoadmoreListener(this);
        mInteractionAdapter = new InteractionAdapter(mList, getActivity());
        mLvHudong.setAdapter(mInteractionAdapter);
        mLvHudong.setOnItemClickListener(this);
        mInteractionAdapter.setReplyCommentCallBack(new InteractionAdapter.ReplyCommentCallBack() {
            @Override
            public void onReplyCommentCallBack(final int position) {
                SendCommentDialog pDialog = new SendCommentDialog(getActivity(), "发布");
                pDialog.commentListener(new SendCommentDialog.SendCommentListener() {
                    @Override
                    public void addComment(String content) {
                        if (!App.manager.getLoginState()) {
                            JumpIntent.jump(getActivity(), LoginActivity.class);
                            return;
                        }
                        if (TextUtils.isEmpty(content)) {
                            ToastUtils.show("请输入回复内容");
                        }
                        if (mList.get(position).getIdType() == NO1) {
                            zixunReply(content, position);
                        } else if (mList.get(position).getIdType() == NO0){
                            replyServerComment(content, position);
                        } else {
                            replyProductComment(content, position);
                        }
                    }
                });
                pDialog.show();
            }
        });
    }

    /**
     * 回复咨询评论
     */
    private void zixunReply(String content, int position) {
        RequestApi.replyConsultationComment(
                App.manager.getPhoneNum(),
                App.manager.getUuid(),
                String.valueOf(mList.get(position).getOperation_object_id()),
                content,
                new AbstractNetWorkCallback<MsgBean>() {
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
                    public void onSuccess(MsgBean msgBean) {
                        if (msgBean.getCode() == NO1) {
                            isRefresh = true;
                            page = 1;
                            mList.clear();
                            mInteractionAdapter.notifyDataSetChanged();
                            getData();
                        }
                    }

                    @Override
                    public void onError(String errorMsg) {
                        ToastUtils.show(errorMsg);
                    }
                });
    }

    /**
     * 回复服务评论
     */
    private void replyServerComment(String content, int position) {
        RequestApi.replayComment(
                String.valueOf(App.manager.getId()),
                String.valueOf(mList.get(position).getOperation_object_id()),
                content,
                String.valueOf(mList.get(position).getMember_id()),
                String.valueOf(mList.get(position).getObject_name()),
                String.valueOf(mList.get(position).getOperation_object_id()),
                String.valueOf(mList.get(position).getServiceProjectId()),
                App.manager.getPhoneNum(),
                App.manager.getUuid(),
                new AbstractNetWorkCallback<MsgBean>() {
                    @Override
                    public void onSuccess(MsgBean msgBean) {
                        if (null != msgBean) {
                            if (msgBean.getCode() == Constant.NO1) {
                                isRefresh = true;
                                page = 1;
                                mList.clear();
                                mInteractionAdapter.notifyDataSetChanged();
                                getData();
                            } else {
                                ToastUtils.show(msgBean.getMsg());
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
     * 回复商品评论
     */
    private void replyProductComment(String content, int position) {
        RequestApi.replayComment(
                String.valueOf(App.manager.getId()),
                String.valueOf(mList.get(position).getOperation_object_id()),
                content,
                String.valueOf(mList.get(position).getMember_id()),
                String.valueOf(mList.get(position).getObject_name()),
                String.valueOf(mList.get(position).getOperation_object_id()),
                "",
                App.manager.getPhoneNum(),
                App.manager.getUuid(),
                new AbstractNetWorkCallback<MsgBean>() {
                    @Override
                    public void onSuccess(MsgBean msgBean) {
                        if (null != msgBean) {
                            if (msgBean.getCode() == Constant.NO1) {
                                isRefresh = true;
                                page = 1;
                                mList.clear();
                                mInteractionAdapter.notifyDataSetChanged();
                                getData();
                            } else {
                                ToastUtils.show(msgBean.getMsg());
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
    public void onRefresh(RefreshLayout refreshlayout) {
        isRefresh = true;
        page = 1;
        mList.clear();
        mInteractionAdapter.notifyDataSetChanged();
        getData();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        isRefresh = false;
        page++;
        getData();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onResume() {
        super.onResume();
        isRefresh = true;
        page = 1;
        mList.clear();
        mInteractionAdapter.notifyDataSetChanged();
        getData();
    }

    /**
     * 获取互动列表
     */
    private void getData() {
        RequestApi.getHuDongMsgList(
                App.manager.getPhoneNum(),
                String.valueOf(App.manager.getId()),
                App.manager.getUuid(),
                String.valueOf(page),
                String.valueOf(NO10),
                new AbstractNetWorkCallback<InteractionBean>() {
                    @Override
                    public void onBefore() {
                        super.onBefore();
                        showInfoProgressDialog();
                    }

                    @Override
                    public void onAfter() {
                        super.onAfter();
                        dismissInfoProgressDialog();
                        if (isRefresh) {
                            mRefreshLayoutHudong.finishRefresh(1000);
                        } else {
                            mRefreshLayoutHudong.finishLoadmore(1000);
                        }
                        if (mList.size() == NO0) {
                            mRelHudongNothing.setVisibility(View.VISIBLE);
                        } else {
                            mRelHudongNothing.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onSuccess(InteractionBean bean) {
                        if (bean.getCode() == NO1) {
                            if (bean.getContent() != null && bean.getContent().size() > NO0) {
                                mList.addAll(bean.getContent());
                                mInteractionAdapter.notifyDataSetChanged();
                            } else {
                                if (mList.size() > NO0 && page > NO1) {
                                    ToastUtils.show("已加载全部数据");
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(String errorMsg) {
                        ToastUtils.show(errorMsg);
                    }
                }
        );
    }
}