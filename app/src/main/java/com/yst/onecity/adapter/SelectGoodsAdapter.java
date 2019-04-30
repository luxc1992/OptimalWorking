package com.yst.onecity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.onecity.R;
import com.yst.onecity.bean.issue.CommodityBean;
import com.yst.onecity.utils.ConstUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 选择商品列表适配器
 *
 * @author chejianqi
 * @version 1.0.1
 * @date 2018/2/9
 */

public class SelectGoodsAdapter extends BaseAdapter {
    private final String strTwo = "2";
    public List<CommodityBean.ContentBean> list;
    private Context context;
    private String goodsPrice;

    public SelectGoodsAdapter(List<CommodityBean.ContentBean> list, Context context) {
        this.list = list;
        this.context = context;
        notifyDataSetChanged();
    }

    public void setGoodsManagerList(List<CommodityBean.ContentBean> list) {
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_select_good_grid_layout, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        if (list.get(i).isChecked()) {
            viewHolder.ivCheck.setImageResource(R.mipmap.choose);
        } else {
            viewHolder.ivCheck.setImageResource(R.mipmap.unchoose);
        }

        CommodityBean.ContentBean contentBean = list.get(i);

        String maxPrice = contentBean.getMaxPrice();
        String minPrice = contentBean.getMinPrice();
        Double maxDouble = Double.valueOf(maxPrice);
        Double minDouble = Double.valueOf(minPrice);
        if (maxDouble > minDouble) {
            goodsPrice = "￥" + minPrice + "-" + maxPrice;
        }else{
            goodsPrice = "￥" + minPrice;
        }
        viewHolder.tvGoodsPrice.setText(goodsPrice);
        ConstUtils.setTextString(contentBean.getName(), viewHolder.tvGoodsName);
        ConstUtils.setTextString(contentBean.getTitle(), viewHolder.tvGoodsIntroduce);
        Glide.with(context).load(contentBean.getAttachment()).error(R.mipmap.ic_launcher).into(viewHolder.ivGoods);

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
        @BindView(R.id.iv_check)
        ImageView ivCheck;
        @BindView(R.id.linear_item_select)
        LinearLayout itemSelect;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
