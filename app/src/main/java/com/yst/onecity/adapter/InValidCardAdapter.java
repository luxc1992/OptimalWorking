package com.yst.onecity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.bean.mine.MyCardBagListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO2;
import static com.yst.onecity.Constant.NO3;

/**
 * (我的)卡包-无效卡券适配器
 *
 * @author liumanqing
 * @version 1.1.0
 * @date 2018/5/18
 */
public class InValidCardAdapter extends BaseAdapter {
    private List<MyCardBagListBean.ContentBean.FalseMapBean.FalseArrayBean> list;
    private Context context;

    public InValidCardAdapter(List<MyCardBagListBean.ContentBean.FalseMapBean.FalseArrayBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setData(List<MyCardBagListBean.ContentBean.FalseMapBean.FalseArrayBean> list) {
        if (list != null) {
            this.list = list;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        InValidCardViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_validcard, null);
            vh = new InValidCardViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (InValidCardViewHolder) convertView.getTag();
        }
        vh.layout.setBackgroundResource(R.drawable.shape_cardbaglist_invalid_bg);
        vh.tvCardName.setText(list.get(position).getCardName());
        vh.relativeLayout.setVisibility(View.GONE);
        vh.tvCardDate.setVisibility(View.VISIBLE);
        vh.tvCardDate.setText("有效日期：" + list.get(position).getBeginTime() + "-" + list.get(position).getEndTime());
        vh.tvCardDay.setText("(" + list.get(position).getUsedNum() + "/" + list.get(position).getNum() + ")");
        //周期卡
        if (list.get(position).getType() == NO0 || list.get(position).getType() == NO1) {
            vh.tvCardExplain.setText("注：仅限本人使用，每天限用一位商家");
        }
        //次数卡
        if (list.get(position).getType() == NO2 || list.get(position).getType() == NO3) {
            vh.tvCardExplain.setText("注：仅限本人使用，每天不限商家");
        }

        return convertView;
    }

    class InValidCardViewHolder {
        @BindView(R.id.item_validcard)
        RelativeLayout layout;
        @BindView(R.id.card_name)
        TextView tvCardName;
        @BindView(R.id.card_date)
        TextView tvCardDate;
        @BindView(R.id.card_day)
        TextView tvCardDay;
        @BindView(R.id.card_explain)
        TextView tvCardExplain;
        @BindView(R.id.rel_price)
        RelativeLayout relativeLayout;
        @BindView(R.id.tv_price_validcard)
        TextView price;

        InValidCardViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
