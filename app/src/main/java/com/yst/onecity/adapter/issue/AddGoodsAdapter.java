package com.yst.onecity.adapter.issue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.bean.issue.CommodityBean;
import com.yst.onecity.utils.ConstUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 添加商品列表适配器
 *
 * @author zhaiyanwu
 * @version 1.0.1
 * @date 2018/2/9
 */

public class AddGoodsAdapter extends BaseAdapter {
    public List<CommodityBean.ContentBean> list;
    private Context context;
    private int type;
    private String goodsPrice;
    private final String strTwo = "2";

    public AddGoodsAdapter(List<CommodityBean.ContentBean> list, Context context, int type) {
        this.list = list;
        this.context = context;
        this.type = type;
        notifyDataSetChanged();
    }

    public void setGoodsManagerList(List<CommodityBean.ContentBean> list, int type) {
        if (list != null) {
            this.list = list;
            this.type = type;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_add_good_grid_layout, null);
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
        if (type == Constant.NO1) {
            viewHolder.type1.setVisibility(View.VISIBLE);
        } else {
            viewHolder.type1.setVisibility(View.GONE);
        }
        if (type == Constant.NO2) {
            viewHolder.ivCheck.setVisibility(View.GONE);
            viewHolder.type2.setVisibility(View.VISIBLE);
            String type = list.get(i).getType();
            if (strTwo.equals(type)) {
                viewHolder.editCommodity.setVisibility(View.GONE);
            } else {
                viewHolder.editCommodity.setVisibility(View.VISIBLE);
            }
        } else {
            viewHolder.ivCheck.setVisibility(View.VISIBLE);
            viewHolder.type2.setVisibility(View.GONE);
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
        ConstUtils.setTextString(contentBean.getAddress(), viewHolder.tvAddress);
        Glide.with(context).load(contentBean.getAttachment()).error(R.mipmap.ic_launcher).into(viewHolder.ivGoods);
        //删除
        viewHolder.deleteCommodity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iGetScrollPosition != null) {
                    iGetScrollPosition.click(i);
                }
            }
        });
        //编辑
        viewHolder.editCommodity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iGetScrollPosition != null) {
                    iGetScrollPosition.compile(i);
                }
            }
        });
        return view;
    }

    private IGetScrollPosition iGetScrollPosition;

    public void setIScrollPositon(IGetScrollPosition iGetScrollPosition) {
        this.iGetScrollPosition = iGetScrollPosition;
    }

    public interface IGetScrollPosition {
        /**
         * 点击删除回掉
         *
         * @param i 角标
         */
        void click(int i);

        /**
         * 编辑
         *
         * @param i 角标
         */
        void compile(int i);

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
        @BindView(R.id.linear_item_select)
        LinearLayout itemSelect;
        @BindView(R.id.linear_edit_commodity)
        LinearLayout editCommodity;
        @BindView(R.id.linear_delete_commodity)
        LinearLayout deleteCommodity;
        @BindView(R.id.linear_type_1)
        LinearLayout type1;
        @BindView(R.id.linear_type_2)
        LinearLayout type2;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
