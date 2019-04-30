package com.yst.onecity.activity;


import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.yst.basic.framework.App;
import com.yst.basic.framework.adapter.AbstractCommonAdapter;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.entity.holder.CommonViewHolder;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.onecity.R;
import com.yst.onecity.bean.ServiceClassifyBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;

import java.util.ArrayList;

import butterknife.BindView;

import static com.yst.onecity.Constant.NO1;

/**
 * 选择分类
 *
 * @author chejianqi
 * @version 1.0.1
 * @date 2018/5/18
 */

public class SelectClassifyActivity extends BaseActivity {

    @BindView(R.id.select_second)
    ListView selectSecond;
    @BindView(R.id.classify_text)
    TextView classifyText;
    private int index = 0;
    private AbstractCommonAdapter secondAdapter;
    private ArrayList<ServiceClassifyBean.ContentBean.SecondListBean> list = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_classify;
    }

    @Override
    public void initData() {
        initTitleBar("选择分类");
        setRightText("确定");
        setRightTextViewOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("classify_first", classifyText.getText().toString().trim());
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).isFlag()) {
                        intent.putExtra("classify_second", list.get(i).getName());
                        intent.putExtra("classifyIndex", i);
                        intent.putExtra("classifyId", list.get(i).getClassId());
                    }
                }
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        index = getIntent().getIntExtra("classifyIndex", 0);
        initAdapter();
        getClassifyData();
    }

    private void initAdapter() {

        secondAdapter = new AbstractCommonAdapter<ServiceClassifyBean.ContentBean.SecondListBean>(this, list, R.layout.item_select_classify) {
            @Override
            public void convert(final CommonViewHolder holder, final int position, final ServiceClassifyBean.ContentBean.SecondListBean item) {
                holder.getView(R.id.select_lines).setVisibility(View.VISIBLE);
                holder.setText(R.id.item_text, item.getName());
                if (list.get(position).isFlag()) {
                    holder.setTextColor(R.id.item_text, ContextCompat.getColor(SelectClassifyActivity.this, R.color.color_F05452));
                    holder.getView(R.id.select_pigeon).setVisibility(View.VISIBLE);
                }
                holder.setLinearLayoutListener(R.id.item_classify, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = 0; i < list.size(); i++) {
                            list.get(i).setFlag(false);
                        }
                        item.setFlag(true);
                        holder.setTextColor(R.id.item_text, ContextCompat.getColor(SelectClassifyActivity.this, R.color.color_F05452));
                        holder.getView(R.id.select_pigeon).setVisibility(View.VISIBLE);
                        notifyDataSetChanged();
                    }
                });
            }
        };
        selectSecond.setAdapter(secondAdapter);
    }

    private void getClassifyData() {
        RequestApi.getServiceClassifyList(String.valueOf(App.manager.getId()), new AbstractNetWorkCallback<ServiceClassifyBean>() {
            @Override
            public void onSuccess(ServiceClassifyBean serviceClassifyBean) {
                if (serviceClassifyBean.getCode() == NO1) {
                    if (serviceClassifyBean.getContent() != null) {
                        classifyText.setText(serviceClassifyBean.getContent().getFirstName());
                        list.clear();
                        list.addAll(serviceClassifyBean.getContent().getSecondList());
                        secondAdapter.notifyDataSetChanged();
                        list.get(index).setFlag(true);
                    }
                } else {
                    ToastUtils.show(serviceClassifyBean.getMsg());
                }

            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }
}
