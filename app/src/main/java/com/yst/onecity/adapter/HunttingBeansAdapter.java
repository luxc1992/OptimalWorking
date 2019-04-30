package com.yst.onecity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.bean.HuntBean;
import com.yst.onecity.utils.ConstUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的猎豆明细列表适配器
 *
 * @author chenjiadi
 * @version 1.0.1
 * @date 2018/2/11.
 */

public class HunttingBeansAdapter extends BaseAdapter {
    private Context mContext;
    private List<HuntBean.ContentBean.ListBean> goodsBeanList;

    public HunttingBeansAdapter(Context mContext, List<HuntBean.ContentBean.ListBean> goodsBeanList) {
        this.mContext = mContext;
        this.goodsBeanList = goodsBeanList;
    }
    public void setFansList(List<HuntBean.ContentBean.ListBean> goodsBeanList) {
        if (goodsBeanList != null) {
            this.goodsBeanList = goodsBeanList;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return goodsBeanList.size();
    }

    @Override
    public Object getItem(int i) {
        return goodsBeanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_huntting_beans_layout, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ConstUtils.setTextString(String.valueOf(goodsBeanList.get(i).getNum()), viewHolder.tvHunttingBeanNum);
        ConstUtils.setTextString(goodsBeanList.get(i).getCreatedTime(),viewHolder.tvHunttingBeanTime);
        int type = goodsBeanList.get(i).getType();
        //类型 0签到 1注册 2分享咨询 3阅读 4发布咨询 5文章被阅读 6收徒奖励 7徒弟文章被阅读奖励 8徒弟分享奖励 9徒弟阅读文章奖励
        switch (type){
            case 0:
                viewHolder.tvHunttingBeanTitle.setText("签到");
                break;
            case 1:
                viewHolder.tvHunttingBeanTitle.setText("注册");
                break;
            case 2:
                viewHolder.tvHunttingBeanTitle.setText("分享资讯");
                break;
            case 3:
                viewHolder.tvHunttingBeanTitle.setText("阅读");
                break;
            case 4:
                viewHolder.tvHunttingBeanTitle.setText("发布资讯");
                break;
            case 5:
                viewHolder.tvHunttingBeanTitle.setText("文章被阅读");
                break;
            case 6:
                viewHolder.tvHunttingBeanTitle.setText("收徒奖励");
                break;
            case 7:
                viewHolder.tvHunttingBeanTitle.setText("徒弟文章被阅读奖励");
                break;
            case 8:
                viewHolder.tvHunttingBeanTitle.setText("徒弟分享奖励");
                break;
            case 9:
                viewHolder.tvHunttingBeanTitle.setText("徒弟阅读文章奖励");
                break;
            case 10:
                viewHolder.tvHunttingBeanTitle.setText("设置头像");
                break;
            case 11:
                viewHolder.tvHunttingBeanTitle.setText("设置昵称");
                break;
            case 12:
                viewHolder.tvHunttingBeanTitle.setText("设置昵称+头像");
                break;
            case 13:
                viewHolder.tvHunttingBeanTitle.setText(R.string.bean_conversion);
                break;
            case 14:
                viewHolder.tvHunttingBeanTitle.setText("购物奖励");
                break;
            default:
                break;
        }
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_huntting_bean_title)
        TextView tvHunttingBeanTitle;
        @BindView(R.id.tv_huntting_bean_num)
        TextView tvHunttingBeanNum;
        @BindView(R.id.tv_huntting_bean_time)
        TextView tvHunttingBeanTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
