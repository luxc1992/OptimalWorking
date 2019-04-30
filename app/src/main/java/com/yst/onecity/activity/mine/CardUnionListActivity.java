package com.yst.onecity.activity.mine;

import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;

import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.onecity.R;
import com.yst.onecity.adapter.CardUnionListAdapter;
import com.yst.onecity.bean.CardUnionUsedBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;

/**
 * 卡联盟列表
 *
 * @author luxuchang
 * @version 1.1.0
 * @date 2018/5/18
 */
public class CardUnionListActivity extends BaseActivity {
    @BindView(R.id.elv_card_union)
    ExpandableListView elvCardUnion;
    @BindView(R.id.rl_empty)
    RelativeLayout rlEmpty;

    private List<CardUnionUsedBean.ContentBean> list = new ArrayList<>();
    private CardUnionListAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_card_union_list;
    }

    @Override
    public void initData() {
        initTitleBar("卡联盟");

        requestData();

        adapter = new CardUnionListAdapter(CardUnionListActivity.this, list);
        elvCardUnion.setAdapter(adapter);

        elvCardUnion.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });

    }

    /**
     * 请求数据
     */
    private void requestData() {
        RequestApi.getCardTypeList(App.manager.getPhoneNum(), App.manager.getUuid(), String.valueOf(App.manager.getId()), "", new AbstractNetWorkCallback<CardUnionUsedBean>() {
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
            public void onSuccess(CardUnionUsedBean cardUnionUsedBean) {
                if (cardUnionUsedBean.getCode() == NO1) {
                    if (cardUnionUsedBean.getContent() == null || cardUnionUsedBean.getContent().size() == NO0) {
                        rlEmpty.setVisibility(View.VISIBLE);
                        return;
                    }
                    if (cardUnionUsedBean.getContent() != null || cardUnionUsedBean.getContent().size() != 0) {
                        list.addAll(cardUnionUsedBean.getContent());
                        rlEmpty.setVisibility(View.GONE);
                    }
                } else {
                    rlEmpty.setVisibility(View.VISIBLE);
                    ToastUtils.show(cardUnionUsedBean.getMsg());
                }

                adapter.notifyDataSetChanged();

                final int groupCount = elvCardUnion.getCount();
                for (int i = 0; i < groupCount; i++) {
                    elvCardUnion.expandGroup(i);
                }

            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }
}
