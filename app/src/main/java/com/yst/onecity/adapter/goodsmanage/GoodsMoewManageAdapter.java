package com.yst.onecity.adapter.goodsmanage;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.bean.goodsmanage.GoodsBean;
import com.yst.onecity.inter.ServiceManageInter;
import com.yst.onecity.utils.ConstUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商品批量管理的适配器
 *
 * @author wuxiaofang
 * @version 1.1.0
 * @date 2018/5/26
 */

public class GoodsMoewManageAdapter extends BaseAdapter {
    private Context context;
    private List<GoodsBean.ContentBean> goodsList = new ArrayList<>();
    private ArrayList<String> proList = new ArrayList<>();
    private ServiceManageInter goodsInter;
    private int typ;

    public GoodsMoewManageAdapter(Context context, ServiceManageInter goodsInter, int type) {
        this.context = context;
        this.goodsInter = goodsInter;
        this.typ = type;
        proList.clear();
    }

    /**
     * 危机和添加数据
     *
     * @param goodsList 有数据的集合
     */
    public void addData(List<GoodsBean.ContentBean> goodsList) {
        if (null != goodsList) {
            this.goodsList.clear();
            this.goodsList.addAll(goodsList);
            proList.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return goodsList.size();
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
    public View getView(final int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (null == view) {
            view = View.inflate(context, R.layout.item_goods, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        String goodsPrice;
        if (goodsList.get(position).getMinPrice().equals(goodsList.get(position).getMaxPrice())) {
            goodsPrice = String.format(context.getResources().getString(R.string.string_money), String.valueOf(goodsList.get(position).getMinPrice()));
        } else {
            goodsPrice = String.format(context.getResources().getString(R.string.string_money), String.valueOf(goodsList.get(position).getMinPrice())) + "-" + String.format(context.getResources().getString(R.string.string_money), String.valueOf(goodsList.get(position).getMaxPrice()));
        }
        viewHolder.tvPrice.setText(goodsPrice);
        Glide.with(context).load(goodsList.get(position).getAddress()).error(R.mipmap.fyj).into(viewHolder.ivGoods);
        ConstUtils.setTextInVisibleString(goodsList.get(position).getTitle(), viewHolder.tvName);
        ConstUtils.setTextInVisibleString(goodsList.get(position).getLocation(), viewHolder.tvIntro);
        viewHolder.framSahngjiaEdDe.setVisibility(View.GONE);
        viewHolder.ivCheck.setVisibility(View.VISIBLE);
        boolean checked = goodsList.get(position).isClick();
        if (checked) {
            viewHolder.ivCheck.setImageResource(R.mipmap.yixuanzhong);
        } else {
            viewHolder.ivCheck.setImageResource(R.mipmap.weixuanzhong);
        }
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.ivCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = goodsList.get(position).isClick();
                if (checked) {
                    finalViewHolder.ivCheck.setImageResource(R.mipmap.weixuanzhong);
                    proList.remove(String.valueOf(goodsList.get(position).getPId()));
                } else {
                    finalViewHolder.ivCheck.setImageResource(R.mipmap.yixuanzhong);
                    proList.add(String.valueOf(goodsList.get(position).getPId()));
                }
                goodsList.get(position).setClick(!checked);
                StringBuilder builder = new StringBuilder("");
                for (int i = 0; i < proList.size(); i++) {
                    builder.append(proList.get(i));
                    if (i != proList.size() - 1) {
                        builder.append(",");
                    }
                }
                switch (typ) {
                    case Constant.NO0:
                        goodsInter.shangJia(builder.toString());
                        break;
                    case Constant.NO1:
                        goodsInter.xiaJia(builder.toString());
                        break;
                    case Constant.NO2:
                        goodsInter.delete(builder.toString());
                        break;
                    default:
                        break;
                }
            }
        });
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.iv_goods)
        ImageView ivGoods;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_intro)
        TextView tvIntro;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_shangjia)
        TextView tvShangjia;
        @BindView(R.id.tv_bianji)
        TextView tvBianji;
        @BindView(R.id.tv_delete)
        TextView tvDelete;
        @BindView(R.id.fram_sahngjia_ed_de)
        FrameLayout framSahngjiaEdDe;
        @BindView(R.id.tv_xiajia)
        TextView tvXiajia;
        @BindView(R.id.fram_xiajia)
        FrameLayout framXiajia;
        @BindView(R.id.tv_bianji2)
        TextView tvBianji2;
        @BindView(R.id.tv_delete2)
        TextView tvDelete2;
        @BindView(R.id.fram_edit_delete)
        FrameLayout framEditDelete;
        @BindView(R.id.iv_logo)
        ImageView ivLogo;
        @BindView(R.id.iv_check)
        ImageView ivCheck;
        @BindView(R.id.view_line)
        View viewLine;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
