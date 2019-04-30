package com.yst.onecity.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yst.onecity.R;
import com.yst.onecity.bean.ConsultItemBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yst.onecity.Constant.NO6;

/**
 * 添加图片适配器
 *
 * @author zhaiyanwu
 * @version 1.0.1
 * @date 2018/2/7
 */
public class ChooseImageSpecificationAdapter extends BaseAdapter {

    private List<ConsultItemBean> strings = null;
    private Activity context;
    public ChooseImageSpecificationAdapter(List<ConsultItemBean> urls, Activity context) {
        if (urls != null) {
            this.strings = urls;
        } else {
            this.strings = new ArrayList<>();
        }
        this.context = context;
    }

    @Override
    public int getCount() {
        int count = strings == null ? 1 : strings.size() + 1;
        if (count > NO6) {
            return strings.size();
        } else {
            return count;
        }
    }

    @Override
    public Object getItem(int i) {
        return strings.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_add_new_image, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.imgItem.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (strings != null && i < strings.size()) {
            Glide.with(context)
                    .load(strings.get(i).getmPhotoPath())
                    .error(R.mipmap.emptydata)
                    .into(viewHolder.imgItem);
            viewHolder.ivChoose.setVisibility(View.VISIBLE);
        } else {
                Glide.with(context)
                        .load(R.mipmap.tianjiatupian)
                        .into(viewHolder.imgItem);
                viewHolder.ivChoose.setVisibility(View.GONE);
        }
        viewHolder.ivChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iGetScrollPosition != null) {
                    iGetScrollPosition.click(i);
                }
            }
        });
        return view;
    }
    private IGetScrollPosition iGetScrollPosition;

    public void setIScrollPositon(IGetScrollPosition iGetScrollPosition) {
        this.iGetScrollPosition = iGetScrollPosition;
    }

    public interface IGetScrollPosition {
        /**
         * 点击删除回掉
         *
         * @param i 索引
         */
        void click(int i);
    }

    static class ViewHolder {
        @BindView(R.id.iv_add_image)
        ImageView imgItem;
        @BindView(R.id.rl_delete)
        ImageView ivChoose;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
