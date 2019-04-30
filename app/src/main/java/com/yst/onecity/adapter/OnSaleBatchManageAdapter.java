package com.yst.onecity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.App;
import com.yst.onecity.R;
import com.yst.onecity.bean.GoodsManageListBean;
import com.yst.onecity.utils.ConstUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yst.onecity.Constant.NO0;

/**
 * 商品批量管理适配器
 *
 * @author chejianqi
 * @version 1.0.1
 * @date 2018/2/9
 */

public class OnSaleBatchManageAdapter extends BaseAdapter {
    private List<GoodsManageListBean.ContentBean> list;
    private Context context;
    private String goodsPrice;

    public OnSaleBatchManageAdapter(List<GoodsManageListBean.ContentBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setGoodsManagerList(List<GoodsManageListBean.ContentBean> list) {
        if (list != null) {
            this.list = list;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_good_list_layout, null);
            viewHolder = new ViewHolder(view);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.ivCheck.setVisibility(View.VISIBLE);
        if (list.get(i).isChecked()) {
            viewHolder.ivCheck.setImageResource(R.mipmap.choose);
        } else {
            viewHolder.ivCheck.setImageResource(R.mipmap.unchoose);
        }
        if (list.get(i).getStatus() == NO0) {
            //已下架状态
            viewHolder.ivOffShelf.setVisibility(View.VISIBLE);
        } else {
            //上架状态
            viewHolder.ivOffShelf.setVisibility(View.GONE);
        }
        if (list.get(i).getMinPrice() == list.get(i).getMaxPrice()) {
            goodsPrice = "￥" + list.get(i).getMinPrice();
        } else {
            goodsPrice = "￥" + list.get(i).getMinPrice() + "-" + list.get(i).getMaxPrice();
        }
        viewHolder.tvGoodsPrice.setText(goodsPrice);
        ConstUtils.setTextString(list.get(i).getTitle(), viewHolder.tvGoodsName);
        ConstUtils.setTextString(list.get(i).getContent(), viewHolder.tvGoodsIntroduce);
        ConstUtils.setTextString(list.get(i).getLocation(), viewHolder.tvAddress);
        Glide.with(App.getInstance()).load(list.get(i).getAddress()).error(R.mipmap.ic_launcher).into(viewHolder.ivGoods);
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.iv_goods)
        ImageView ivGoods;
        @BindView(R.id.iv_off_shelf)
        ImageView ivOffShelf;
        @BindView(R.id.tv_goods_name)
        TextView tvGoodsName;
        @BindView(R.id.tv_goods_introduce)
        TextView tvGoodsIntroduce;
        @BindView(R.id.tv_goods_price)
        TextView tvGoodsPrice;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.iv_check)
        ImageView ivCheck;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
