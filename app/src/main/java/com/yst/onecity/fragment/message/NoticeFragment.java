package com.yst.onecity.fragment.message;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yst.basic.framework.App;
import com.yst.basic.framework.adapter.AbstractCommonAdapter;
import com.yst.basic.framework.base.BaseFragment;
import com.yst.basic.framework.entity.holder.CommonViewHolder;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.MyLog;
import com.yst.im.imchatlibrary.bean.BaseEntity;
import com.yst.im.imchatlibrary.bean.GroupDetailsEntity;
import com.yst.im.imchatlibrary.bean.RecentContactEntity;
import com.yst.im.imchatlibrary.model.FindGroupDetailModel;
import com.yst.im.imchatlibrary.model.RecentContactsModel;
import com.yst.im.imchatlibrary.model.UpdateMessageStateModel;
import com.yst.onecity.R;
import com.yst.onecity.activity.H5OrderDetailsActivity;
import com.yst.onecity.activity.mine.order.MyServiceOrderDetailActivity;
import com.yst.onecity.bean.NoticeBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.ConstUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO10;
import static com.yst.onecity.Constant.NO2;

/**
 * 通知fragment页面
 *
 * @author qinchaoshuai
 * @version 1.0.1
 * @date 2018/2/23
 */
public class NoticeFragment extends BaseFragment implements OnRefreshListener,
        OnLoadmoreListener,
        AdapterView.OnItemClickListener,
        RecentContactsModel.RecentContactsCallBack,
        UpdateMessageStateModel.UpdateMessageStateListenerCallBack,
        FindGroupDetailModel.FindGroupDetailListenerCallBack {

    @BindView(R.id.lv_notice)
    ListView mLvNotice;
    @BindView(R.id.refreshLayout_notice)
    SmartRefreshLayout mRefreshLayoutNotice;
    @BindView(R.id.rel_notice_nothing)
    RelativeLayout mRelNoticeNothing;
    int page = 1;
    private List<NoticeBean.ContentBean> mList = new ArrayList<>();
    private boolean isRefresh = true;
    private AbstractCommonAdapter<NoticeBean.ContentBean> mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_notice;
    }

    @Override
    public void init() {
        mRefreshLayoutNotice.setOnRefreshListener(this);
        mRefreshLayoutNotice.setOnLoadmoreListener(this);
        mAdapter = new AbstractCommonAdapter<NoticeBean.ContentBean>(getActivity(), mList, R.layout.item_notice) {
            @Override
            public void convert(CommonViewHolder holder, int position, NoticeBean.ContentBean item) {
                holder.setText(R.id.txt_item_notice_time, ConstUtils.getDateTime(item.getCreatedTime()));
                holder.setText(R.id.txt_item_notice_title, item.getTitle());
                holder.setText(R.id.txt_item_notice_content, item.getComment());
                ImageView iv = holder.getView(R.id.iv_item_notice_image);
                //type=2 交易成功才显示图片
                Glide.with(getActivity())
                        .load(ConstUtils.getStringNoEmpty(item.getImgUrl()))
                        .error(R.mipmap.moren)
                        .into(iv);
            }
        };
        mLvNotice.setAdapter(mAdapter);
        mLvNotice.setOnItemClickListener(this);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        isRefresh = true;
        page = 1;
        mList.clear();
        mAdapter.notifyDataSetChanged();
        getData();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        isRefresh = false;
        page++;
        getData();
    }

    @Override
    public void onResume() {
        super.onResume();
        mRefreshLayoutNotice.autoRefresh(50);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mList.get(position).getOrderId()==null){
            return;
        }
        String itemType = mList.get(position).getOrderId().substring(0, 4);
        String sp="PJSP";
        String fw="PJFW";
        if (itemType.equals(sp)){
            if (mList.get(position).getType() == NO2) {
                Bundle bundle = new Bundle();
                bundle.putString("type", mList.get(position).getOrderState() + "");
                bundle.putString("orderNo", mList.get(position).getOrderId() + "");
                JumpIntent.jump(getActivity(), H5OrderDetailsActivity.class, bundle);
            }
        }else if (itemType.equals(fw)){
            if (mList.get(position).getType() == NO2) {
                Bundle bundle = new Bundle();
                bundle.putString("status", mList.get(position).getOrderState() + "");
                bundle.putString("from", mList.get(position).getMsgType());
                bundle.putString("orderId", mList.get(position).getServiceOrderId() + "");
                JumpIntent.jump(getActivity(), MyServiceOrderDetailActivity.class, bundle);
            }
        }
    }

    /**
     * 获取系统通知列表
     */
    private void getData() {
        RequestApi.getSystemMsgList(
                App.manager.getPhoneNum(),
                String.valueOf(App.manager.getId()),
                App.manager.getUuid(),
                String.valueOf(page),
                String.valueOf(NO10),
                new AbstractNetWorkCallback<NoticeBean>() {
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
                            mRefreshLayoutNotice.finishRefresh(1000);
                        } else {
                            mRefreshLayoutNotice.finishLoadmore(1000);
                        }
                    }

                    @Override
                    public void onSuccess(NoticeBean bean) {
                        if (bean.getCode() == NO1) {
                            if (bean.getContent() != null) {
                                mList.addAll(bean.getContent());
                                if (mList.size() == NO0) {
                                    mRelNoticeNothing.setVisibility(View.VISIBLE);
                                } else {
                                    mAdapter.notifyDataSetChanged();
                                }
                            } else {
                                mRelNoticeNothing.setVisibility(View.VISIBLE);
                            }

                        } else {
                            mRelNoticeNothing.setVisibility(View.VISIBLE);
                            ToastUtils.show(bean.getMsg());
                        }
                    }

                    @Override
                    public void onError(String errorMsg) {
                        mRelNoticeNothing.setVisibility(View.VISIBLE);
                        MyLog.e("okhttp", "errorMsg===" + errorMsg);
                    }
                }
        );
    }

    @Override
    public void onFindGroupDetail(GroupDetailsEntity groupDetailsEntity) {

    }

    @Override
    public void onRecentContacts(RecentContactEntity recentContactEntity) {

    }

    @Override
    public void onUpdateMessageState(BaseEntity baseEntity) {

    }
}