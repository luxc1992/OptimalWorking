package com.yst.onecity.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.utils.WindowUtils;
import com.yst.onecity.R;
import com.yst.onecity.bean.ConsultItemBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO6;

/**
 * 发布图文咨询上传标题图片适配器
 *
 * @author songbinbin
 * @version 1.1.0
 * @date 2018/5/21
 */
public class ServiceEvaluateImageAdapter extends BaseAdapter {

    private List<ConsultItemBean> strings = null;
    private Activity context;

    public ServiceEvaluateImageAdapter(List<ConsultItemBean> urls, Activity context) {
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
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.service_evaluate_gv_image_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        if (strings != null && i < strings.size()) {
            Glide.with(context)
                    .load(strings.get(i).getmPhotoPath())
                    .error(R.mipmap.camera)
                    .into(viewHolder.imgItem);
            viewHolder.ivChoose.setVisibility(View.VISIBLE);
        } else {
            Glide.with(context)
                    .load(R.mipmap.camera)
                    .into(viewHolder.imgItem);
            viewHolder.ivChoose.setVisibility(View.GONE);
        }
        int width = WindowUtils.getScreenWidth(context);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(new ViewGroup.LayoutParams((width -WindowUtils.dip2px(context,50))/ 3, (width -WindowUtils.dip2px(context,50))/ 4));
        lp.setMargins(0,0,0,5);
        viewHolder.imgItem.setLayoutParams(lp);
        viewHolder.imgItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iGetScrollPosition != null) {
                    iGetScrollPosition.click(i, NO1);
                }
            }
        });
        viewHolder.ivChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iGetScrollPosition != null) {
                    iGetScrollPosition.click(i, NO0);
                }
            }
        });
        return view;
    }
    private IGetScrollPosition iGetScrollPosition;

    public void setIScrollPosition(IGetScrollPosition iGetScrollPosition) {
        this.iGetScrollPosition = iGetScrollPosition;
    }

    public interface IGetScrollPosition {
        /**
         * 点击删除回掉
         * @param i position
         * @param event 事件（0：删除图片点击删除，1：替换图片）
         */
        void click(int i, int event);

    }

    static class ViewHolder {
        @BindView(R.id.service_evaluate_img_item)
        ImageView imgItem;
        @BindView(R.id.service_evaluate_iv_choosh)
        ImageView ivChoose;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
