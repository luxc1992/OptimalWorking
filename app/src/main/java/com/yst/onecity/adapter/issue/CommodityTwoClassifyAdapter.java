package com.yst.onecity.adapter.issue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.bean.issue.CommodityTwoClassifyBean;

import java.util.ArrayList;

/**
 * 商品二级分类适配器
 * @author zhaiyanwu
 * @version v3.0.1
 * @date 2018/3/14.
 */
public class CommodityTwoClassifyAdapter extends BaseAdapter {

    private Context context;
    private final LayoutInflater from;
    private ArrayList<CommodityTwoClassifyBean.ContentBean> lvClassifyChildList;
    private int defItem;

    public CommodityTwoClassifyAdapter(ArrayList<CommodityTwoClassifyBean.ContentBean> lvClassifyChildList, Context context) {
        this.lvClassifyChildList = lvClassifyChildList;
        this.context = context;
        from = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return lvClassifyChildList == null ? 0 : lvClassifyChildList.size();
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = from.inflate(R.layout.item_two_classify_list, null);
            viewHolder.tvName = view.findViewById(R.id.tv_two_classify);
            viewHolder.imgSelect = view.findViewById(R.id.img_classify_select);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        CommodityTwoClassifyBean.ContentBean contentBean = lvClassifyChildList.get(i);
        viewHolder.tvName.setText(contentBean.getClassifyName());
        if (defItem == i) {
            viewHolder.imgSelect.setVisibility(View.VISIBLE);
        } else {
            viewHolder.imgSelect.setVisibility(View.INVISIBLE);
        }
        return view;
    }

    public void setDefSelect(int position) {
        this.defItem = position;
        notifyDataSetChanged();
    }


    class ViewHolder {
        TextView tvName;
        ImageView imgSelect;
    }
}
