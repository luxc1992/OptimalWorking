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
import com.yst.onecity.utils.MyLog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * (我的)卡包-无效新人卡适配器
 *
 * @author liumanqing
 * @version 1.1.0
 * @date 2018/6/1
 */
public class MyCouponFalseAdapter extends BaseAdapter{
    private Context context;
    private List<MyCardBagListBean.ContentBean.FalseMapBean.MyCouponFalseJsonArrayBean> list;

    public MyCouponFalseAdapter(Context context, List<MyCardBagListBean.ContentBean.FalseMapBean.MyCouponFalseJsonArrayBean> list) {
        this.context = context;
        this.list = list;
    }

    public void setData(List<MyCardBagListBean.ContentBean.FalseMapBean.MyCouponFalseJsonArrayBean> list) {
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
        MyCouponFalseViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_validcard, null);
            vh = new MyCouponFalseViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (MyCouponFalseViewHolder) convertView.getTag();
        }
        vh.layout.setBackgroundResource(R.drawable.shape_cardbaglist_invalid_bg);
        //新人卡
        if (list.size() > 0) {
            vh.tvCardName.setText(list.get(position).getName());
            vh.relativeLayout.setVisibility(View.VISIBLE);
            vh.tvCardDate.setVisibility(View.GONE);
            vh.price.setText(list.get(position).getMoney());
            vh.tvCardDay.setText("(代金券)");
            vh.tvCardExplain.setText("注：仅限本人使用，仅限健身课题");
        }

        return convertView;
    }

    class MyCouponFalseViewHolder {
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

        MyCouponFalseViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
