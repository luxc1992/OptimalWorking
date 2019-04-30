package com.yst.onecity.adapter.issue;

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

/**
 * 添加图片适配器
 *
 * @author zhaiyanwu
 * @version 1.0.1
 * @date 2018/2/7
 */
public class IssueSpecificationAdapter extends BaseAdapter {

    private List<ConsultItemBean> strings = null;
    private Activity context;
    int type;

    public IssueSpecificationAdapter(List<ConsultItemBean> urls, Activity context, int type) {
        if (urls != null) {
            this.strings = urls;
        } else {
            this.strings = new ArrayList<>();
        }
        this.context = context;
        this.type = type;

    }

    @Override
    public int getCount() {
        return strings.size();
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
        String s = strings.get(i).getmPhotoPath();

        Glide.with(context)
                .load(s)
                .error(R.mipmap.emptydata)
                .into(viewHolder.imgItem);
        viewHolder.ivChoose.setVisibility(View.VISIBLE);
        int type = strings.get(i).getTypes();
        if (type == 0) {
            viewHolder.imagePlay.setVisibility(View.GONE);
        } else {
            viewHolder.imagePlay.setVisibility(View.VISIBLE);
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
         *  删除回调
         * @param i 角标
         */
        void click(int i);
    }

    static class ViewHolder {
        @BindView(R.id.iv_add_image)
        ImageView imgItem;
        @BindView(R.id.rl_delete)
        ImageView ivChoose;
        @BindView(R.id.image_play)
        ImageView imagePlay;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
