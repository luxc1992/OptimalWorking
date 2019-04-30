package com.yst.onecity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.utils.MyLog;
import com.yst.basic.framework.view.roundedimageview.RoundedImageView;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.bean.servermanage.ServerManageBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 服务批量管理的适配器
 *
 * @author wuxiaofang
 * @version 1.1.0
 * @date 2018/5/26
 */

public class ServiceBulkManageAdapter extends BaseAdapter {
    private Context context;
    private List<ServerManageBean.ContentBean> serviceList = new ArrayList<>();
    private int type;
    private boolean isEdit;
    private ArrayList<String> poList = new ArrayList<>();
    private onPoClickInter onPoClickInter;

    public ServiceBulkManageAdapter(Context context, int type, onPoClickInter onPoClickInter) {
        this.context = context;
        this.type = type;
        this.onPoClickInter = onPoClickInter;
    }

    /**
     * 为集合添加数据
     *
     * @param serviceList 集合
     */
    public void addData(List<ServerManageBean.ContentBean> serviceList) {
        if (null != serviceList) {
            this.serviceList.clear();
            this.serviceList.addAll(serviceList);
            notifyDataSetChanged();
        }
    }

    /**
     * 是否可编辑
     *
     * @param isEdit 是否可编辑
     */
    public void isEdit(boolean isEdit) {
        this.isEdit = isEdit;
        notifyDataSetChanged();
    }

    /**
     * 清除选中的id集合
     */
    public void clearList() {
        poList.clear();
    }

    @Override
    public int getCount() {
        return serviceList.size();
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
            view = View.inflate(context, R.layout.item_bulk_manage, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        if (isEdit) {
            viewHolder.checkBulk.setVisibility(View.VISIBLE);
        } else {
            viewHolder.checkBulk.setVisibility(View.GONE);
        }
        Glide.with(context).load(serviceList.get(position).getLog()).error(R.mipmap.serbackground).into(viewHolder.ivSerBackground);
        viewHolder.tvServerName.setText(serviceList.get(position).getTitle());
        viewHolder.tvServerPrice.setText(String.format(context.getResources().getString(R.string.string_money), String.valueOf(serviceList.get(position).getPrice())));
        switch (type) {
            case 1:
                viewHolder.ivLogo.setVisibility(View.GONE);
                break;
            case 2:
                int examineStates = Integer.parseInt(serviceList.get(position).getExamineStatus());
                viewHolder.ivLogo.setVisibility(View.VISIBLE);
                if (examineStates== Constant.NO0) {
                    viewHolder.ivLogo.setImageResource(R.mipmap.shenheshibai);
                }else {
                    viewHolder.ivLogo.setImageResource(R.mipmap.shenhezhong);
                }
                break;
            case 3:
                viewHolder.ivLogo.setVisibility(View.VISIBLE);
                viewHolder.ivLogo.setImageResource(R.mipmap.yixiajia);
                break;
            default:
                break;
        }
        boolean ckeck = serviceList.get(position).isClick();
        if (ckeck) {
            viewHolder.checkBulk.setImageResource(R.mipmap.yixuanzhong);
        } else {
            viewHolder.checkBulk.setImageResource(R.mipmap.weixuanzhong);
        }

        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.checkBulk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean ckeck = serviceList.get(position).isClick();
                if (ckeck) {
                    finalViewHolder.checkBulk.setImageResource(R.mipmap.weixuanzhong);
                    serviceList.get(position).setClick(false);
                    poList.remove(String.valueOf(serviceList.get(position).getId()));
                } else {
                    finalViewHolder.checkBulk.setImageResource(R.mipmap.yixuanzhong);
                    serviceList.get(position).setClick(true);
                    poList.add(String.valueOf(serviceList.get(position).getId()));
                }
                    MyLog.e("sss","--polist: "+poList.size());
                onPoClickInter.onPo(poList);
            }
        });
        return view;
    }


    public interface onPoClickInter {
        /**
         * 接口回调选中的id
         *
         * @param list id集合
         */
        void onPo(ArrayList<String> list);
    }

    static class ViewHolder {
        @BindView(R.id.check_bulk)
        ImageView checkBulk;
        @BindView(R.id.iv_ser_background)
        RoundedImageView ivSerBackground;
        @BindView(R.id.iv_logo)
        ImageView ivLogo;
        @BindView(R.id.tv_server_name)
        TextView tvServerName;
        @BindView(R.id.tv_server_price)
        TextView tvServerPrice;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
