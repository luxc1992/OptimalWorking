package com.yst.onecity.activity.mine.address;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.onecity.R;
import com.yst.onecity.adapter.AbstractCommonAdapter;
import com.yst.onecity.adapter.MyViewHolder;
import com.yst.onecity.bean.ReapBean;
import com.yst.onecity.bean.ReapRensonBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.view.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO10;
import static com.yst.onecity.Constant.NO2;

/**
 * 选择收货人
 *
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/2/24
 */

public class ChooseAddressActivity extends BaseActivity {
    @BindView(R.id.txt_am_new)
    TextView mTxtAmNew;
    @BindView(R.id.rlv_am)
    RecyclerView mRlvAm;
    private List< ReapRensonBean.ContentBean>mist=new ArrayList<>();


    @Override
    public int getLayoutId() {
        return R.layout.activity_address_manager;
    }

    @Override
    public void initData() {
        initTitleBar("选择收货人");
        getReapList();

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
                if (reapBean.getCode() == 1) {
                    if (reapBean.getContent() != null) {
                        getReapData(reapBean.getContent());
                        ToastUtils.show(reapBean.getMsg());
                    }

                } else {
                    ToastUtils.show("收货列表为空");
                }

            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });

    }


    /**
     * 收货数据源
     *
     * @version 1.0.1
     */
    private void getReapData(final List<ReapBean.ContentBean> mData) {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRlvAm.setLayoutManager(new LinearLayoutManager(this));
        mRlvAm.addItemDecoration(new SpacesItemDecoration(NO0, NO10));
        AbstractCommonAdapter adapter = new AbstractCommonAdapter<ReapBean.ContentBean>(this, R.layout.item_choose_address, mData) {
            @Override
            public void convert(MyViewHolder holder, ReapBean.ContentBean item) {
                ImageView imagechoose = holder.getView(R.id.iv_item_choose_default);
                CheckBox checkbox = holder.getView(R.id.cb_item_choose);
                int isDefault = item.getIs_default();
                if (isDefault==1){
                    imagechoose.setVisibility(View.VISIBLE);
                    checkbox.setChecked(true);
                }else {
                    imagechoose.setVisibility(View.GONE);
                    checkbox.setChecked(false);
                }
                holder.setText(R.id.txt_item_choose_name, ConstUtils.getStringNoEmpty(item.getUser_name()));
                holder.setText(R.id.txt_item_choose_phone,ConstUtils.getStringNoEmpty(item.getMobile()));
                holder.setText(R.id.txt_item_choose_detail,ConstUtils.getStringNoEmpty(item.getDetail_address()));
            }

            @Override
            public void bindClick(MyViewHolder holder, ReapBean.ContentBean item) {
                holder.setOnInClickListener(R.id.iv_item_choose_edit, item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("editflag", NO2);
                        JumpIntent.jump(ChooseAddressActivity.this, AddAddressActivity.class, bundle);
                    }
                });
            }
        };
        mRlvAm.setAdapter(adapter);
    }


    @OnClick(R.id.txt_am_new)
    public void onViewClicked() {
        JumpIntent.jump(this, AddAddressActivity.class);
    }
}
