package com.yst.onecity.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.App;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.onecity.H5Const;
import com.yst.onecity.R;
import com.yst.onecity.activity.h5detail.ServerTeamPageDetailActivity;
import com.yst.onecity.bean.CaseBean;
import com.yst.onecity.utils.ConstUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 案例管理适配器
 *
 * @author chenjiadi
 * @version 1.1.0
 * @date 2018/5/24.
 */

public class CaseManagerAdapter extends BaseAdapter {
    private Activity mContext;
    private List<CaseBean.ContentBean> caseBeans;
    private List<String> idCheckList = new ArrayList<>();
    private List<CaseBean.ContentBean> checkList = new ArrayList<>();
    private boolean isEdit;
    private OnListener listener;

    public CaseManagerAdapter(Activity mContext, List<CaseBean.ContentBean> caseBeans, boolean isEdit) {
        this.mContext = mContext;
        this.caseBeans = caseBeans;
        this.isEdit = isEdit;
    }

    public void setCaseList(List<CaseBean.ContentBean> caseBeans, boolean isEdit) {
        if (caseBeans != null) {
            this.caseBeans = caseBeans;
            this.isEdit = isEdit;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return caseBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return caseBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_case, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (caseBeans.get(position).getImageAddress() != null) {
            Glide.with(App.mContext).load(caseBeans.get(position).getImageAddress()).error(R.mipmap.logo).into(viewHolder.ivCase);
        }
        ConstUtils.setTextString(caseBeans.get(position).getTitle(), viewHolder.tvCaseContent);
        if (isEdit) {
            viewHolder.check.setVisibility(View.VISIBLE);
        } else {
            viewHolder.check.setVisibility(View.GONE);
        }
        if (caseBeans.get(position).isClick()) {
            viewHolder.check.setImageResource(R.mipmap.yixuanzhong);
        } else {
            viewHolder.check.setImageResource(R.mipmap.weixuanzhong);
        }
        final ViewHolder finalViewHolder = viewHolder;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEdit) {
                    caseBeans.get(position).setClick(!caseBeans.get(position).isClick());
                    if (caseBeans.get(position).isClick()) {
                        finalViewHolder.check.setImageResource(R.mipmap.yixuanzhong);
                        idCheckList.add(caseBeans.get(position).getId() + "");
                        checkList.add(caseBeans.get(position));
                    } else {
                        finalViewHolder.check.setImageResource(R.mipmap.weixuanzhong);
                        idCheckList.remove(caseBeans.get(position).getId() + "");
                        checkList.remove(caseBeans.get(position));
                    }
                    if (listener != null) {
                        listener.onCheckListener(idCheckList);
                    }
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", caseBeans.get(position).getId()+"");
                    bundle.putString("url", H5Const.CASE_DETAIL);
                    JumpIntent.jump(mContext, ServerTeamPageDetailActivity.class, bundle);
                }
            }
        });
        return view;
    }

    public interface OnListener {
        /**
         * 批量管理操作
         *
         * @param isCheckList 选中的集合
         */
        void onCheckListener(List<String> isCheckList);
    }

    public void setListener(OnListener listener) {
        this.listener = listener;
    }

    static class ViewHolder {
        @BindView(R.id.check)
        ImageView check;
        @BindView(R.id.tv_case_content)
        TextView tvCaseContent;
        @BindView(R.id.iv_case)
        ImageView ivCase;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
