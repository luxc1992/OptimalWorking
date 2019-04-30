package com.yst.onecity.adapter.issue;

import android.app.Activity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.onecity.R;
import com.yst.onecity.bean.CommoditySpecificationBean;
import com.yst.onecity.utils.CashierInputFilter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 设置规格值适配器
 *
 * @author zhaiyanwu
 * @version v1.0.1
 * @date 2018/2/24.
 */
public class SpecificationListAdapter extends BaseAdapter {
    Activity context;
    ArrayList<CommoditySpecificationBean> specificationData;
    private final LayoutInflater from;
    private View.OnClickListener listener;
    private final String sLines = "-";

    public SpecificationListAdapter(Activity context, ArrayList specificationData, View.OnClickListener listener) {
        this.context = context;
        this.specificationData = specificationData;
        this.listener = listener;
        from = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return specificationData == null ? 0 : specificationData.size();
    }

    @Override
    public CommoditySpecificationBean getItem(int i) {
        return specificationData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int item, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = from.inflate(R.layout.specification_list_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (item == 0) {
            viewHolder.specification.setVisibility(View.VISIBLE);
        } else {
            viewHolder.specification.setVisibility(View.GONE);
        }
        String imagePath = getItem(item).getImagePath();
        if (!TextUtils.isEmpty(imagePath)) {
            Glide.with(context)
                    .load(imagePath)
                    .error(R.mipmap.emptydata)
                    .into(viewHolder.commodity);
            viewHolder.commodity.setVisibility(View.VISIBLE);
            viewHolder.changePrice.setVisibility(View.GONE);
        } else {
            viewHolder.commodity.setVisibility(View.GONE);
            viewHolder.changePrice.setVisibility(View.VISIBLE);
        }

        viewHolder.commodity.setTag(R.id.accordion, item);
        viewHolder.changePrice.setTag(R.id.accordion, item);
        viewHolder.deleteSpecification.setTag(item);
        viewHolder.commodity.setOnClickListener(listener);
        viewHolder.deleteSpecification.setOnClickListener(listener);
        viewHolder.changePrice.setOnClickListener(listener);
        final CommoditySpecificationBean specificationBean = getItem(item);
        String specification = specificationBean.getSpecification();
        specificationBean.getPrice();
        if (specification.endsWith(sLines)) {
            specification = specification.substring(0, specification.length() - 1);
        }
        viewHolder.tvSpecification.setText(specification);
        if (viewHolder.editInventory.getTag() instanceof TextWatcher) {
            viewHolder.editInventory.removeTextChangedListener((TextWatcher) viewHolder.editInventory.getTag());
        }
        viewHolder.editInventory.setText(specificationBean.getInventory());
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    specificationBean.setInventory("");
                } else {
                    specificationBean.setInventory(s.toString());
                }
            }
        };
        viewHolder.editInventory.addTextChangedListener(watcher);
        viewHolder.editInventory.setTag(watcher);
//---------------------------分割线-----------------------------------分割线-------------------------------
        if (viewHolder.editPice.getTag() instanceof TextWatcher) {
            viewHolder.editPice.removeTextChangedListener((TextWatcher) viewHolder.editPice.getTag());
        }
        viewHolder.editPice.setText(specificationBean.getPrice());
        TextWatcher watchers = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    specificationBean.setPrice("");
                } else {
                    specificationBean.setPrice(s.toString());
                }
            }
        };
        viewHolder.editPice.addTextChangedListener(watchers);
        viewHolder.editPice.setTag(watchers);
        //设置输入的是价格（不能以小数点开头并且小数点后只能输入两位数字）
        viewHolder.editPice.setFilters(new InputFilter[]{new CashierInputFilter()});
        viewHolder.editPice.setEllipsize(TextUtils.TruncateAt.END);
        return view;
    }

    public void deleteData(int i) {
        specificationData.remove(i);
        notifyDataSetChanged();
    }


    public static class ViewHolder {

        @BindView(R.id.specification_code)
        TextView tvSpecification;
        @BindView(R.id.stock_change_price)
        TextView changePrice;
        @BindView(R.id.image_commodity)
        ImageView commodity;
        @BindView(R.id.relative_specification)
        RelativeLayout specification;
        @BindView(R.id.image_delete_specification)
        ImageView deleteSpecification;
        @BindView(R.id.edit_inventory)
        public EditText editInventory;
        @BindView(R.id.edit_pice)
        public EditText editPice;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }

}
