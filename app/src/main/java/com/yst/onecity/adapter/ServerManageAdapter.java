package com.yst.onecity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.view.roundedimageview.RoundedImageView;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.bean.servermanage.ServerManageBean;
import com.yst.onecity.inter.ServiceManageInter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 服务管理列表的适配器
 *
 * @author wuxiaofang
 * @version 1.0.1
 * @date 2018/5/17
 */

public class ServerManageAdapter extends BaseAdapter {
    private Context context;
    private List<ServerManageBean.ContentBean> mServerList = new ArrayList<>();
    private int tabPosition;
    private ServiceManageInter serviceManageInter;

    public ServerManageAdapter(Context context, int tabPosition, ServiceManageInter serviceManageInter) {
        this.context = context;
        this.tabPosition = tabPosition;
        this.serviceManageInter = serviceManageInter;
    }

    /**
     * 为集合添加数据
     *
     * @return void
     */
    public void addData(List<ServerManageBean.ContentBean> mList) {
        if (mList != null) {
            mServerList.clear();
            mServerList.addAll(mList);
            notifyDataSetChanged();
        }

    }

    @Override
    public int getCount() {
        return mServerList.size();
    }

    @Override
    public Object getItem(int i) {
        return mServerList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = View.inflate(context, R.layout.item_server_maage, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Glide.with(context).load(mServerList.get(i).getLog()).error(R.mipmap.serbackground).into(viewHolder.ivSerBackground);
        viewHolder.tvServerName.setText(mServerList.get(i).getTitle());
        viewHolder.tvServerPrice.setText(String.format(context.getResources().getString(R.string.string_money), String.valueOf(mServerList.get(i).getPrice())));
        switch (tabPosition) {
            //上架中
            case 0:
                viewHolder.llState.setVisibility(View.VISIBLE);
                viewHolder.tvXiajia.setVisibility(View.VISIBLE);
                viewHolder.ivLogo.setVisibility(View.GONE);
                viewHolder.tvXiajia.setText("下架");
                viewHolder.tvBianji.setVisibility(View.GONE);
                viewHolder.tvDelete.setVisibility(View.GONE);
                viewHolder.tvXiajia.setOnClickListener(new MyClick(String.valueOf(mServerList.get(i).getId()), i,viewHolder));
                break;
            //待上架
            case 1:
                viewHolder.tvXiajia.setVisibility(View.GONE);
                viewHolder.ivLogo.setVisibility(View.VISIBLE);
                String examineStatus = mServerList.get(i).getExamineStatus();
                int state = Integer.parseInt(examineStatus);
                /*
                 * 0审核失败，1审核通过 2 待审核
                 */
                switch (state) {
                    case Constant.NO0:
                        Glide.with(context).load(R.mipmap.shenheshibai).into(viewHolder.ivLogo);
                        viewHolder.llState.setVisibility(View.VISIBLE);
                        viewHolder.tvBianji.setVisibility(View.VISIBLE);
                        viewHolder.tvDelete.setVisibility(View.VISIBLE);
                        viewHolder.tvBianji.setOnClickListener(new MyClick(String.valueOf(mServerList.get(i).getId()), i, viewHolder));
                        viewHolder.tvDelete.setOnClickListener(new MyClick(String.valueOf(mServerList.get(i).getId()), i, viewHolder));
                        break;
                    case Constant.NO2:
                        Glide.with(context).load(R.mipmap.shenhezhong).into(viewHolder.ivLogo);
                        viewHolder.llState.setVisibility(View.GONE);
                        viewHolder.tvBianji.setOnClickListener(new MyClick(String.valueOf(mServerList.get(i).getId()), i, viewHolder));
                        viewHolder.tvDelete.setOnClickListener(new MyClick(String.valueOf(mServerList.get(i).getId()), i, viewHolder));
                        break;
                    default:
                        break;
                }
                break;
            //已下架
            case 2:
                viewHolder.llState.setVisibility(View.VISIBLE);
                viewHolder.tvXiajia.setVisibility(View.VISIBLE);
                viewHolder.tvXiajia.setText("上架");
                viewHolder.tvBianji.setVisibility(View.VISIBLE);
                viewHolder.tvDelete.setVisibility(View.VISIBLE);
                viewHolder.ivLogo.setVisibility(View.VISIBLE);
                Glide.with(context).load(R.mipmap.yixiajia).error(R.mipmap.yixiajia).into(viewHolder.ivLogo);
                viewHolder.tvBianji.setOnClickListener(new MyClick(String.valueOf(mServerList.get(i).getId()), i, viewHolder));
                viewHolder.tvXiajia.setOnClickListener(new MyClick(String.valueOf(mServerList.get(i).getId()), i, viewHolder));
                viewHolder.tvDelete.setOnClickListener(new MyClick(String.valueOf(mServerList.get(i).getId()), i, viewHolder));
                break;
            default:
                break;
        }
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.iv_ser_background)
        RoundedImageView ivSerBackground;
        @BindView(R.id.iv_logo)
        ImageView ivLogo;
        @BindView(R.id.tv_server_name)
        TextView tvServerName;
        @BindView(R.id.tv_server_price)
        TextView tvServerPrice;
        @BindView(R.id.ll_state)
        LinearLayout llState;
        @BindView(R.id.tv_xiajia)
        TextView tvXiajia;
        @BindView(R.id.tv_bianji)
        TextView tvBianji;
        @BindView(R.id.tv_delete)
        TextView tvDelete;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public class MyClick implements View.OnClickListener {
        private String strId;
        private ViewHolder vh;
        private int position;

        public MyClick(String strId, int position, ViewHolder vh) {
            this.strId = strId;
            this.vh = vh;
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_xiajia:
                    if (vh.tvXiajia.getText().toString().equals(Constant.XIAJIA)) {
                        serviceManageInter.xiaJia(strId);
                    } else {
                        serviceManageInter.shangJia(strId);
                    }
                    break;
                case R.id.tv_bianji:
                    serviceManageInter.edit(position);
                    break;
                case R.id.tv_delete:
                    serviceManageInter.delete(strId);
                    break;
                default:
                    break;
            }
        }
    }
}
