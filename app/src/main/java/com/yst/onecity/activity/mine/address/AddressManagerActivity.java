package com.yst.onecity.activity.mine.address;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.onecity.R;
import com.yst.onecity.adapter.AbstractCommonAdapter;
import com.yst.onecity.adapter.MyViewHolder;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.ReapBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.view.AbstractCommonDialog;
import com.yst.onecity.view.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO10;
import static com.yst.onecity.Constant.NO2;

/**
 * 地址管理列表页面
 *
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/2/24
 */
public class AddressManagerActivity extends BaseActivity {
    @BindView(R.id.rlv_am)
    RecyclerView mRlvAm;
    @BindView(R.id.txt_am_new)
    TextView mTxtAmNew;
    private AbstractCommonAdapter adapter;
    private List<ReapBean.ContentBean> mData = new ArrayList<>();


    @Override
    public int getLayoutId() {
        return R.layout.activity_address_manager;
    }

    @Override
    public void initData() {
        initTitleBar("地址管理");
        getReapData(mData);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getReapList();
    }

    /**
     * 删除弹框
     *
     * @param aid 地址id
     */
    private void showDeleteDialog(final int aid) {
        AbstractCommonDialog dialog = new AbstractCommonDialog(AddressManagerActivity.this) {
            @Override
            public void sureClick() {
                deleteAddress(aid);
                dismissDialog();
            }
        };
        dialog.setText("提示", "是否确定删除？", "确定", "取消");
        dialog.showDialog();
    }

    @OnClick(R.id.txt_am_new)
    public void onViewClicked() {
        //跳转新增收货人页面
        JumpIntent.jump(this, AddAddressActivity.class);
    }

    /**
     * 删除收货地址
     *
     * @param aid 地址id
     * @version 1.0.1
     */
    private void deleteAddress(int aid) {
        RequestApi.deleteAddress(
                App.manager.getPhoneNum(),
                App.manager.getUuid(),
                String.valueOf(aid),
                new AbstractNetWorkCallback<MsgBean>() {
            @Override
            public void onSuccess(MsgBean msgBean) {
                if (msgBean.getCode() == NO1) {
                    getReapList();
                    ToastUtils.show(msgBean.getMsg());
                } else {
                    ToastUtils.show(msgBean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    /**
     * 收货人列表
     *
     * @version 1.0.1
     */
    public void getReapList() {
        RequestApi.myAddressList(String.valueOf(App.manager.getId()),App.manager.getPhoneNum(), App.manager.getUuid(), new AbstractNetWorkCallback<ReapBean>() {
            @Override
            public void onSuccess(ReapBean reapBean) {
                if (reapBean.getCode() == NO1) {
                    if (reapBean.getContent().size() != NO0) {
                        mData.clear();
                        mData.addAll(reapBean.getContent());
                        adapter.notifyDataSetChanged();
                    } else {
                        mData.clear();
                        adapter.notifyDataSetChanged();
                        ToastUtils.show("没有收货地址了~");
                    }
                } else {
                    ToastUtils.show(reapBean.getMsg());
                }
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
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });

    }

    /**
     * 设置适配器
     *
     * @param mData 集合
     *
     * @version 1.0.1
     */
    private void getReapData(final List<ReapBean.ContentBean> mData) {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRlvAm.setLayoutManager(new LinearLayoutManager(this));
        mRlvAm.addItemDecoration(new SpacesItemDecoration(NO0, NO10));
        adapter = new AbstractCommonAdapter<ReapBean.ContentBean>(AddressManagerActivity.this, R.layout.item_am, mData) {
            @Override
            public void convert(MyViewHolder holder, ReapBean.ContentBean item) {
                holder.setText(R.id.txt_item_am_name, ConstUtils.getStringNoEmpty(item.getUser_name()));
                int isDefault = item.getIs_default();
                CheckBox checkbox = holder.getView(R.id.cb_default);
                //1为默认地址
                if (isDefault == NO1) {
                    holder.setViewVisibility(R.id.iv_item_am_default, View.VISIBLE);
                    holder.setTextColor(R.id.txt_item_am_default, ContextCompat.getColor(context, R.color.color_ED5452));
                    checkbox.setChecked(true);
                } else {
                    holder.setViewVisibility(R.id.iv_item_am_default, View.GONE);
                    holder.setTextColor(R.id.txt_item_am_default, ContextCompat.getColor(context, R.color.color_979797));
                    checkbox.setChecked(false);
                }
                holder.setText(R.id.txt_item_am_phone, ConstUtils.getStringNoEmpty(item.getMobile()));
                holder.setText(
                        R.id.txt_item_am_address,
                        ConstUtils.getStringNoEmpty(item.getProvince_name()+item.getCity_name()+item.getCounty_name()+item.getDetail_address())
                );
            }

            @Override
            public void bindClick(final MyViewHolder holder, final ReapBean.ContentBean item) {
                holder.setOnInClickListener(R.id.ll_item_am_edit, item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //跳转到编辑收货人页面
                        Bundle bundle = new Bundle();
                        bundle.putInt("editFlag", NO2);
                        bundle.putInt("aid", item.getId());
                        JumpIntent.jump(AddressManagerActivity.this, AddAddressActivity.class, bundle);
                    }
                });

                holder.setOnInClickListener(R.id.ll_item_am_delete, item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //删除地址弹框
                        showDeleteDialog(item.getId());
                    }
                });

                holder.setOnInClickListener(R.id.cb_default, item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //设置默认地址
                        requestSetDefault(item.getId());
                    }
                });
            }
        };
        mRlvAm.setAdapter(adapter);
    }

    /**
     * 设置默认地址
     *
     * @param id 地址id
     */
    private void requestSetDefault(int id) {
        RequestApi.setDefaultAddress(
                App.manager.getPhoneNum(),
                App.manager.getUuid(),
                String.valueOf(id),
                new AbstractNetWorkCallback<MsgBean>() {
                    @Override
                    public void onSuccess(MsgBean msgBean) {
                        if (msgBean.getCode() == NO1) {
                            getReapList();
                        } else {
                            ToastUtils.show(msgBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(String errorMsg) {
                        ToastUtils.show(errorMsg);
                    }
                });
    }
}