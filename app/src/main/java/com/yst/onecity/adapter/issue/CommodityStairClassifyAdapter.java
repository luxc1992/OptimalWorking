package com.yst.onecity.adapter.issue;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.activity.issue.CommodityClassifyActivity;
import com.yst.onecity.bean.issue.CommodityStairClassifyBean;

import java.util.ArrayList;

/**
 * 商品的一级分类adapter
 * @author zhaiyanwu
 * @version 1.0.1
 * @date 2018/3/14.
 */
public class CommodityStairClassifyAdapter extends BaseAdapter {

    private final LayoutInflater from;
    private ArrayList<CommodityStairClassifyBean.ContentBean> lvClassifyList;
    private Context context;
    private int defItem;

    public CommodityStairClassifyAdapter(ArrayList<CommodityStairClassifyBean.ContentBean> lvClassifyList, CommodityClassifyActivity context) {
        this.lvClassifyList = lvClassifyList;
        this.context = context;
        from = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return lvClassifyList == null ? 0 : lvClassifyList.size();
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
            view = from.inflate(R.layout.item_stair_classify_list, null);
            viewHolder.text = view.findViewById(R.id.classify_name);
            viewHolder.selectorView = view.findViewById(R.id.selector_view);
            viewHolder.linearLayout = view.findViewById(R.id.item_classify_linear);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.text.setText(lvClassifyList.get(i).getClassifyName());
        if (defItem == i) {
            viewHolder.selectorView.setVisibility(View.VISIBLE);
            viewHolder.linearLayout.setBackgroundColor(Color.WHITE);
            viewHolder.text.setTextColor(ContextCompat.getColor(context, R.color.green));
            viewHolder.text.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        } else {
            viewHolder.text.setTextColor(Color.BLACK);
            viewHolder.linearLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.color_F2F2F2));
            viewHolder.text.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            viewHolder.selectorView.setVisibility(View.INVISIBLE);
        }
        return view;
    }
    public void setDefSelect(int position) {
        this.defItem = position;
        notifyDataSetChanged();
    }

    class ViewHolder {
        TextView text;
        View selectorView;
        LinearLayout linearLayout;
    }

}
