package com.yst.onecity.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.App;
import com.yst.basic.framework.view.roundedimageview.RoundedImageView;
import com.yst.onecity.R;
import com.yst.onecity.bean.FansOrAttentionsListBean;
import com.yst.onecity.utils.ConstUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;

/**
 * 粉丝列表适配器
 *
 * @author chenjiadi
 * @version 1.0.1
 * @date 2018/2/11.
 */

public class FansAdapter extends BaseAdapter {
    private Context mContext;
    private List<FansOrAttentionsListBean.ContentBean.ListBean> fansList;
    private OnListener listener;

    public FansAdapter(Context mContext, List<FansOrAttentionsListBean.ContentBean.ListBean> fansList) {
        this.mContext = mContext;
        this.fansList = fansList;
    }

    public void setFansList(List<FansOrAttentionsListBean.ContentBean.ListBean> fansList) {
        if (fansList != null) {
            this.fansList = fansList;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return fansList.size();
    }

    @Override
    public Object getItem(int position) {
        return fansList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_fans_layout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (fansList.get(position).getStatus() == NO0) {
            //单向关注
            viewHolder.tvState.setText("+关注");
            changeState(viewHolder);
        } else if (fansList.get(position).getStatus() == NO1) {
            //互相关注
            viewHolder.tvState.setText("互相关注");
            Drawable drawable = ContextCompat.getDrawable(mContext, R.mipmap.bothattention);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            viewHolder.tvState.setCompoundDrawables(drawable, null, null, null);
            viewHolder.tvState.setTextColor(ContextCompat.getColor(mContext, R.color.color_999999));
        }
        ConstUtils.setTextString(fansList.get(position).getNickName(), viewHolder.tvName);
        Glide.with(App.getInstance()).load(fansList.get(position).getImg()).error(R.mipmap.headimage).into(viewHolder.ivHead);
        viewHolder.tvState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onListener(fansList.get(position).getId() + "", position, fansList.get(position).getStatus());
                }
            }
        });
        viewHolder.ivHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onCheck(fansList.get(position).getId()+"",fansList.get(position).getIsMerchant(),fansList.get(position).getAdvisorId());
                }
            }
        });
        return convertView;
    }

    /**
     * 改变按钮状态
     *
     * @param viewHolder viewholder
     */
    private void changeState(ViewHolder viewHolder) {
        viewHolder.tvState.setTextColor(ContextCompat.getColor(mContext, R.color.color_ED5452));
        viewHolder.tvState.setCompoundDrawables(null, null, null, null);
    }

    public void setListener(OnListener listener) {
        this.listener = listener;
    }

    public interface OnListener {
        /**
         * 关注操作
         *
         * @param id       用户id
         * @param position 索引
         * @param status   关注状态
         */
        void onListener(String id, int position, int status);

        /**
         * 点击头像跳转个人信息页面
         * @param id 用户id
         * @param personType 用户身份 普通状态/猎头
         */
        void onCheck(String id,int personType,int adviserId);

    }

    class ViewHolder {
        @BindView(R.id.iv_head)
        RoundedImageView ivHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.tv_state)
        TextView tvState;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
