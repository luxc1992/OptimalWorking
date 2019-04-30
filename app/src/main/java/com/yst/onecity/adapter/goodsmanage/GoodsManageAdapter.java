package com.yst.onecity.adapter.goodsmanage;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.App;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.MyLog;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.activity.issue.AddNewCommodityActivity;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.goodsmanage.GoodsBean;
import com.yst.onecity.bean.issue.IssueCommodityNewsBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.inter.GoodsManageInter;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.view.dialog.AbstractGoodsDeleteDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yst.onecity.Constant.NO1;

/**
 * 商品管理的适配器
 *
 * @author wuxiaofang
 * @version 1.0.1
 * @date 2018/5/19
 */

public class GoodsManageAdapter extends BaseAdapter {
    private Context context;
    private List<GoodsBean.ContentBean> goodsList = new ArrayList<>();
    private int tabPosition;
    private String productId;
    private int type;
    private String content = "";
    private GoodsManageInter goodsManageInter;
    private AbstractGoodsDeleteDialog dialog;
    private boolean isEdit;
    private boolean isDelete;
    private boolean isShangJia;

    public GoodsManageAdapter(Context context, int tabPosition, GoodsManageInter goodsManageInter) {
        this.context = context;
        this.tabPosition = tabPosition;
        this.goodsManageInter = goodsManageInter;
    }

    /**
     * 为集合添加数据
     *
     * @param mList
     */
    public void addData(List<GoodsBean.ContentBean> mList) {
        if (null != mList) {
            goodsList.clear();
            goodsList.addAll(mList);
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (null == view) {
            view = View.inflate(context, R.layout.item_goods, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        String goodsPrice;
        if (goodsList.get(i).getMinPrice().equals(goodsList.get(i).getMaxPrice())) {
            goodsPrice = String.format(context.getResources().getString(R.string.string_money), String.valueOf(goodsList.get(i).getMinPrice()));
        } else {
            goodsPrice = String.format(context.getResources().getString(R.string.string_money), String.valueOf(goodsList.get(i).getMinPrice())) + "-" + String.format(context.getResources().getString(R.string.string_money), String.valueOf(goodsList.get(i).getMaxPrice()));
        }
        Glide.with(context).load(goodsList.get(i).getAddress()).error(R.mipmap.fyj).into(viewHolder.ivGoods);
        viewHolder.tvPrice.setText(goodsPrice);
        ConstUtils.setTextInVisibleString(goodsList.get(i).getTitle(), viewHolder.tvName);
        ConstUtils.setTextInVisibleString(goodsList.get(i).getLocation(), viewHolder.tvIntro);

        switch (tabPosition) {
            //上架中
            case Constant.NO0:
                viewHolder.ivLogo.setVisibility(View.GONE);
                viewHolder.viewLine.setVisibility(View.VISIBLE);
                viewHolder.framXiajia.setVisibility(View.VISIBLE);
                viewHolder.framEditDelete.setVisibility(View.GONE);
                viewHolder.framSahngjiaEdDe.setVisibility(View.GONE);
                type = Constant.NO0;
                viewHolder.framXiajia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        productId = String.valueOf(goodsList.get(i).getPId());
                        MyLog.e("pid","下架商品id--------"+productId);
                        content = "是否确定下架？";
                        shoDialog();
                    }
                });
                break;
            //待上架
            case NO1:
                viewHolder.ivLogo.setVisibility(View.VISIBLE);
                viewHolder.viewLine.setVisibility(View.GONE);
                switch (goodsList.get(i).getExamineStatus()) {
                    case Constant.NO2:
                        viewHolder.ivLogo.setImageResource(R.mipmap.shenhezhong);
                        viewHolder.framEditDelete.setVisibility(View.GONE);
                        break;
                    case Constant.NO0:
                        viewHolder.ivLogo.setImageResource(R.mipmap.shenheshibai);
                        viewHolder.framEditDelete.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
                viewHolder.framSahngjiaEdDe.setVisibility(View.GONE);
                viewHolder.framXiajia.setVisibility(View.GONE);
                viewHolder.tvBianji2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String productId = String.valueOf(goodsList.get(i).getPId());
                        getHunterProductContent(productId);
                    }
                });
                type = Constant.NO1;
                viewHolder.tvDelete2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        productId = String.valueOf(goodsList.get(i).getPId());
                        MyLog.e("pid","删除商品2id--------"+productId);
                        isDelete = true;
                        isShangJia = false;
                        isEdit = false;
                        content = "是否确定删除？";
                        shoDialog();
                    }
                });
                break;
            //已下架
            case Constant.NO2:
                viewHolder.ivLogo.setVisibility(View.GONE);
                viewHolder.viewLine.setVisibility(View.VISIBLE);
                viewHolder.framEditDelete.setVisibility(View.GONE);
                viewHolder.framSahngjiaEdDe.setVisibility(View.VISIBLE);
                viewHolder.framXiajia.setVisibility(View.GONE);
                type = Constant.NO2;
                viewHolder.tvShangjia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        productId = String.valueOf(goodsList.get(i).getPId());
                        MyLog.e("pid","上架商品id--------"+productId);
                        isDelete = false;
                        isShangJia = true;
                        isEdit = false;
                        content = "是否确定上架？";
                        shoDialog();
                    }
                });
                viewHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        productId = String.valueOf(goodsList.get(i).getPId());
                        MyLog.e("pid","删除商品id--------"+productId);
                        isDelete = true;
                        isShangJia = false;
                        isEdit = false;
                        content = "是否确定删除？";
                        shoDialog();
                    }
                });
                viewHolder.tvBianji.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String productId = String.valueOf(goodsList.get(i).getPId());
                        getHunterProductContent(productId);
                    }
                });
                break;
            default:
                break;
        }
        return view;
    }

    /**
     * 商品回显
     */
    private void getHunterProductContent(final String id) {
        RequestApi.getHunterProductContent(id, App.manager.getPhoneNum(), App.manager.getUuid(), new AbstractNetWorkCallback<IssueCommodityNewsBean>() {
            @Override
            public void onSuccess(IssueCommodityNewsBean hunterProductContentBean) {
                MyLog.e("zhaiyanwu", "下架的编辑 ==  id= " + id + "=   " + hunterProductContentBean.toString());
                if (Constant.NO1 == hunterProductContentBean.getCode()) {
                    IssueCommodityNewsBean.ContentBean content = hunterProductContentBean.getContent();
                    if (content.getProductJson() != null) {
                        String address = content.getProductJson().getAddress();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("content", content);
                        bundle.putSerializable("contentId", id);
                        bundle.putSerializable("address", address);
                        bundle.putString("isGoodManage", "isGoodManage");
                        JumpIntent.jump((Activity) context, AddNewCommodityActivity.class, bundle);
                    }
                }
                ToastUtils.show(hunterProductContentBean.getMsg());
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    private void shoDialog() {
        dialog = new AbstractGoodsDeleteDialog((Activity) context) {
            @Override
            public void sureClick() {
                if (type == Constant.NO0) {
                    xiaJia();
                }
                if (type == Constant.NO2) {
                    if (isShangJia) {
                        shangJia();
                    }
                    if (isDelete) {
                        singleDelete();
                    }
                }
                if (type == Constant.NO1) {
                    if (isDelete) {
                        singleDelete();
                    }
                }
            }

            @Override
            public void cancleClick() {
                productId = "";
            }
        };
        dialog.setText(content, "确定", "取消");
        dialog.showDialog();
    }

    /**
     * 删除
     */
    private void singleDelete() {
        if (null != productId) {
            if (TextUtils.isEmpty(productId)) {
                return;
            }
            MyLog.e("sss","--单个删除： "+productId);

            RequestApi.goodsManageBatchOperation(productId, App.manager.getPhoneNum(), App.manager.getUuid(), String.valueOf(Constant.NO2), new AbstractNetWorkCallback<MsgBean>() {
                @Override
                public void onSuccess(MsgBean msgBean) {
                    if (null != msgBean) {
                        if (msgBean.getCode() == NO1) {
                            MyLog.e("sss", "-单个删除： " + msgBean.toString());
                            goodsManageInter.isClick(true);
                            productId = "";
                            ToastUtils.show(msgBean.getMsg());
                        } else {
                            ToastUtils.show(msgBean.getMsg());
                        }
                        dialog.dismissDialog();
                    }
                }

                @Override
                public void onError(final String errorMsg) {
                    ToastUtils.show(errorMsg);
                }
            });
        }


    }

    /**
     * 上架
     */
    private void shangJia() {
        if (null != productId) {
            if (TextUtils.isEmpty(productId)) {
                return;
            }
            MyLog.e("sss","--单个上架： "+productId);
            RequestApi.goodsManageBatchOperation(productId, App.manager.getPhoneNum(), App.manager.getUuid(), String.valueOf(Constant.NO1), new AbstractNetWorkCallback<MsgBean>() {
                @Override
                public void onSuccess(MsgBean msgBean) {
                    if (null != msgBean) {
                        MyLog.e("sss", "-单个上架： " + msgBean.toString());
                        if (msgBean.getCode() == NO1) {
                            goodsManageInter.isClick(true);
                            productId = "";
                            ToastUtils.show(msgBean.getMsg());
                        } else {
                            ToastUtils.show(msgBean.getMsg());
                        }
                    }
                    dialog.dismissDialog();
                }

                @Override
                public void onError(final String errorMsg) {
                    ToastUtils.show(errorMsg);
                }
            });
        }


    }

    /**
     * 下架
     */
    private void xiaJia() {
        if (null != productId) {
            if (TextUtils.isEmpty(productId)) {
                return;
            }
            MyLog.e("sss","--单个下架： "+productId);

            RequestApi.goodsManageBatchOperation(productId, App.manager.getPhoneNum(), App.manager.getUuid(), String.valueOf(Constant.NO0), new AbstractNetWorkCallback<MsgBean>() {
                @Override
                public void onSuccess(MsgBean msgBean) {
                    if (msgBean != null) {
                        MyLog.e("sss", "-单个下架： " + msgBean.toString());
                        if (msgBean.getCode() == NO1) {
                            goodsManageInter.isClick(true);
                            productId = "";
                            ToastUtils.show(msgBean.getMsg());
                        } else {
                            ToastUtils.show(msgBean.getMsg());
                        }
                        dialog.dismissDialog();
                    }
                }

                @Override
                public void onError(final String errorMsg) {
                    ToastUtils.show(errorMsg);
                }
            });
        }


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
        @BindView(R.id.view_line)
        View viewLine;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
