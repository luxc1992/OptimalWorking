package com.yst.onecity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.yst.onecity.R;
import com.yst.onecity.bean.TagListBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 添加上传图片适配器
 *
 * @author chejianqi
 * @version 1.0.1
 * @date 2018/5/18
 */

public class GridViewAddImagesAdapter extends BaseAdapter {
    private ArrayList<TagListBean> datas;
    private Context context;
    private LayoutInflater inflater;
    /**
     * 可以动态设置最多上传几张，之后就不显示+号了，用户也无法上传了
     * 默认9张
     */
    private int maxImages = 7;
    private GridViewDeleteListener gridViewDeleteListener;
    public GridViewAddImagesAdapter(ArrayList<TagListBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setGridViewDeleteListener(GridViewDeleteListener gridViewDeleteListener){
        this.gridViewDeleteListener=gridViewDeleteListener;
    }

    /**
     * 获取最大上传张数
     *
     * @return
     */
    public int getMaxImages() {
        return maxImages;
    }

    /**
     * 设置最大上传张数
     *
     * @param maxImages
     */
    public void setMaxImages(int maxImages) {
        this.maxImages = maxImages;
    }

    /**
     * 让GridView中的数据数目加1最后一个显示+号
     * 当到达最大张数时不再显示+号
     *
     * @return 返回GridView中的数量
     */
    @Override
    public int getCount() {
        int count = datas == null ? 1 : datas.size() + 1;
        if (count >= maxImages) {
            return datas.size();
        } else {
            return count;
        }
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void notifyDataSetChanged(ArrayList<TagListBean> datas) {
        this.datas = datas;
        this.notifyDataSetChanged();

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_published_grida, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /**代表+号之前的需要正常显示图片**/
        if (datas != null && position < datas.size()) {
            Glide.with(context)
                    .load(datas.get(position).getName())
                    .priority(Priority.HIGH)
                    .centerCrop()
                    .into(viewHolder.ivimage);
            viewHolder.btdel.setVisibility(View.VISIBLE);
            viewHolder.btdel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gridViewDeleteListener.deleteListener(position);
                }
            });
        } else {
            /**代表+号的需要+号图片显示图片**/
            Glide.with(context)
                    .load(R.mipmap.service_add)
                    .into(viewHolder.ivimage);
            viewHolder.ivimage.setScaleType(ImageView.ScaleType.FIT_XY);
            viewHolder.btdel.setVisibility(View.GONE);
            notifyDataSetChanged();
        }

        return convertView;

    }

    public interface GridViewDeleteListener {
        /**
         * 删除
         *
         * @param position 下标
         */
        void deleteListener(int position);
    }

    static class ViewHolder {
        @BindView(R.id.iv_image)
        ImageView ivimage;
        @BindView(R.id.bt_del)
        TextView btdel;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}