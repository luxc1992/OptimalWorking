package com.yst.onecity.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yst.basic.framework.utils.MyLog;
import com.yst.basic.framework.utils.security.PreferenceUtil;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.bean.SaleClassBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import gorden.rxbus2.RxBus;

/**
 * 城市列表的适配器
 *
 * @author wuxiaofang
 * @version 1.1.0
 * @date 2018/5/26
 */

public class SaleClassAdapter extends BaseAdapter {
    private Context context;
    private List<SaleClassBean.ContentBean> classList = new ArrayList<>();
    private int checkPosition;

    public SaleClassAdapter(Context context) {
        this.context = context;
    }

    /**
     * 为集合添加数据
     *
     * @param classList 地址集合
     */
    public void addData(List<SaleClassBean.ContentBean> classList) {
        if (null != classList) {
            this.classList.clear();
            this.classList.addAll(classList);
            notifyDataSetChanged();
        }
    }

    /**
     * 改变颜色
     */
    public void setState(int position) {
        this.checkPosition = position;
    }

    @Override
    public int getCount() {
        MyLog.e("sss", "===" + classList.size());
        return classList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (null == view) {
            view = View.inflate(context, R.layout.item_sale_class, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (checkPosition == i) {
            viewHolder.ivDui.setVisibility(View.VISIBLE);
            viewHolder.tvClassName.setTextColor(ContextCompat.getColor(context, R.color.color_tab_select));
        } else {
            viewHolder.ivDui.setVisibility(View.GONE);
            viewHolder.tvClassName.setTextColor(ContextCompat.getColor(context, R.color.gray_line));
        }
        viewHolder.tvClassName.setText(classList.get(i).getName());
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.itemRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferenceUtil.put("position", i);
                PreferenceUtil.put("check", true);
                finalViewHolder.ivDui.setVisibility(View.VISIBLE);
                finalViewHolder.tvClassName.setTextColor(ContextCompat.getColor(context, R.color.color_tab_select));
                RxBus.get().send(Constant.NO9,new SaleClassBean.ContentBean(classList.get(i).getName(),classList.get(i).getId()));
            }
        });
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_class_name)
        TextView tvClassName;
        @BindView(R.id.iv_dui)
        ImageView ivDui;
        @BindView(R.id.item_rl)
        RelativeLayout itemRl;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
