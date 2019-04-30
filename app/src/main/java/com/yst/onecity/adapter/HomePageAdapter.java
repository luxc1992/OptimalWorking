package com.yst.onecity.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.Utils;
import com.yst.basic.framework.view.roundedimageview.RoundedImageView;
import com.yst.onecity.H5Const;
import com.yst.onecity.R;
import com.yst.onecity.activity.h5detail.ServerTeamPageDetailActivity;
import com.yst.onecity.bean.home.HomeProjectBean;
import com.yst.onecity.bean.home.HomeProjectNewsBean;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.view.MyListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 首页适配器
 *
 * @author chenxiaowei
 * @version 1.1.0
 * @date 2018/5/17
 */
public class HomePageAdapter extends BaseAdapter {
    private Activity context;
    private List<HomeProjectBean> data;

    public HomePageAdapter(Activity context, List<HomeProjectBean> data) {
        this.context = context;
        this.data = data;
    }

    public void onRefresh(List<HomeProjectBean> mData) {
        if (mData != null) {
            this.data.clear();
            this.data.addAll(mData);
            notifyDataSetChanged();
        }
    }

    public void addData(List<HomeProjectBean> mData) {
        if (mData != null) {
            this.data.addAll(mData);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint({"SetTextI18n", "InflateParams"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_group_home, null);
            viewHolder = new MyViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) view.getTag();
        }
        final HomeProjectBean projectBean = data.get(i);
        Glide.with(context).load(projectBean.getImageAddress()).error(R.mipmap.default_product_icon).into(viewHolder.ivCover);
        viewHolder.tvGroupTitle.setText(ConstUtils.getStringNoEmpty(projectBean.getTitle()));
        viewHolder.tvGroupDes.setText(ConstUtils.getStringNoEmpty(projectBean.getContent()));
        viewHolder.tvLookCount.setText(ConstUtils.changeEmptyStringToZero(projectBean.getReadNum()));
        viewHolder.tvZanCount.setText(ConstUtils.changeEmptyStringToZero(projectBean.getPriseNum()));
        viewHolder.tvTeamCount.setText("已有" + projectBean.getServiceTeamNum() + "个服务团队");
        List<HomeProjectNewsBean> childList = data.get(i).getTopicConsultationList();
        HomeChildAdapter childAdapter = new HomeChildAdapter(context, childList);
        viewHolder.listView.setAdapter(childAdapter);
        final Bundle bundle = new Bundle();
        viewHolder.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (Utils.isClickable()) {
                    bundle.putString("id", projectBean.getTopicConsultationList().get(i).getId());
                    bundle.putString("url", H5Const.H5_CONSULTING_DETAIL);
                    JumpIntent.jump(context, ServerTeamPageDetailActivity.class, bundle);
                }
            }
        });
        viewHolder.rlProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isClickable()) {
                    bundle.putString("id", projectBean.getId());
                    bundle.putString("url", H5Const.PROJECT_DETAILS);
                    JumpIntent.jump(context, ServerTeamPageDetailActivity.class, bundle);
                }
            }
        });
        return view;
    }

    class MyViewHolder {
        @BindView(R.id.rl_project)
        RelativeLayout rlProject;
        @BindView(R.id.listView)
        MyListView listView;
        @BindView(R.id.iv_cover)
        RoundedImageView ivCover;
        @BindView(R.id.tv_team_count)
        TextView tvTeamCount;
        @BindView(R.id.tv_look_count)
        TextView tvLookCount;
        @BindView(R.id.tv_zan_count)
        TextView tvZanCount;
        @BindView(R.id.tv_group_title)
        TextView tvGroupTitle;
        @BindView(R.id.tv_group_des)
        TextView tvGroupDes;
        @BindView(R.id.dotted_line)
        View dottedLine;

        MyViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
