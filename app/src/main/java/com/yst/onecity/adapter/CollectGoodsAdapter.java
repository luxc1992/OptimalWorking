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
import com.yst.onecity.bean.mine.MyCollectGoodBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的收藏 商品item adapter
 *
 * @author liumanqing
 * @version 1.0.1
 * @date 2018/2/26
 */
public class CollectGoodsAdapter extends BaseAdapter {
    private List<MyCollectGoodBean.ContentBean> list;
    private Context context;
    private View.OnClickListener onClickListener;

    public CollectGoodsAdapter(List<MyCollectGoodBean.ContentBean> list, Context context, View.OnClickListener onClickListener) {
        this.list = list;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    public void setData(List<MyCollectGoodBean.ContentBean> list) {
        if (list != null) {
            this.list = list;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
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
        CollectGoodsViewHolder vh;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_collect_goods, null);
            vh = new CollectGoodsViewHolder(view);
            view.setTag(vh);
        } else {
            vh = (CollectGoodsViewHolder) view.getTag();
        }
        vh.tvAddress.setText(list.get(i).getLocation());
        vh.tvGoodsName.setText(list.get(i).getTitle());
        vh.tvGoodsIntroduce.setText(list.get(i).getContent());
        //设置商品的价格
        if (list.get(i).getMinPrice() == list.get(i).getMaxPrice()) {
            vh.tvGoodsPrice.setText("￥" + list.get(i).getMinPrice());
        } else {
            vh.tvGoodsPrice.setText("￥" + list.get(i).getMinPrice() + "-" + list.get(i).getMaxPrice());
        }
        Glide.with(context).load(list.get(i).getAddress()).error(R.mipmap.default_product_icon).into(vh.ivGoods);
        //取消收藏的监听回调
        vh.lineCollect.setTag(list.get(i).getProductId() + "");
        vh.lineCollect.setOnClickListener(onClickListener);

        return view;
    }

    class CollectGoodsViewHolder {
        @BindView(R.id.img_collect_goods)
        ImageView ivGoods;
        @BindView(R.id.tv_collect_goods_name)
        TextView tvGoodsName;
        @BindView(R.id.tv_collect_goods_introduce)
        TextView tvGoodsIntroduce;
        @BindView(R.id.tv_collect_goods_price)
        TextView tvGoodsPrice;
        @BindView(R.id.tv_collect_address)
        TextView tvAddress;
        @BindView(R.id.line_collect)
        LinearLayout lineCollect;

        CollectGoodsViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
