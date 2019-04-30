package com.yst.onecity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.bean.FreightRuleBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 运费模板列表adapter
 *
 * @author zhaiyanwu
 * @version v3.0.1
 * @date 2018/3/2.
 */
public class FreightTemplateAdapter extends BaseAdapter {
    private int id;
    Context context;
    List<FreightRuleBean.ContentBean> list;
    private final LayoutInflater from;
    private int defItem;

    public FreightTemplateAdapter(Context context, List<FreightRuleBean.ContentBean> content) {
        this.list = content;
        this.context = context;
        from = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public FreightRuleBean.ContentBean getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = from.inflate(R.layout.item_lv_freight_template, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        FreightRuleBean.ContentBean item = getItem(i);
        viewHolder.name.setText(item.getName());

        if (defItem == list.get(i).getId()) {
            viewHolder.ferightSelect.setVisibility(View.VISIBLE);
        } else {
            viewHolder.ferightSelect.setVisibility(View.INVISIBLE);
        }


        viewHolder.centerVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iGetScrollPosition.click(i);
            }
        });
        viewHolder.linearName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iGetScrollPosition.confirm(i);
            }
        });
        return view;
    }


    public void setDefSelect(int position) {
        this.defItem = position;
        notifyDataSetChanged();
    }

    private IGetScrollPosition iGetScrollPosition;

    public void setIScrollPositon(IGetScrollPosition iGetScrollPosition) {
        this.iGetScrollPosition = iGetScrollPosition;
    }

    public interface IGetScrollPosition {
        /**
         * 回调
         *
         * @param i 索引
         */
        void click(int i);

        /**
         * 选择的状态
         *
         * @param i id
         */
        void confirm(int i);

    }

    class ViewHolder {
        @BindView(R.id.tev_name)
        TextView name;
        @BindView(R.id.customer_center_version_tv)
        TextView centerVersion;
        @BindView(R.id.customer_center_update_app_tv0)
        LinearLayout linearName;
        @BindView(R.id.img_feright_select)
        ImageView ferightSelect;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
