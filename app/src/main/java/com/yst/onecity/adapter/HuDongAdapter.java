package com.yst.onecity.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.utils.glide.GlideCircleTransform;
import com.yst.basic.framework.view.roundedimageview.RoundedImageView;
import com.yst.onecity.R;
import com.yst.onecity.bean.HuDongBean;
import com.yst.onecity.utils.ConstUtils;

import java.util.List;

import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO2;

/**
 * 互动列表多条目适配器
 *
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/2/23
 */

public class HuDongAdapter extends BaseAdapter {

    /**
     * 2种类型item
     */
    private final int TYPE_1 = NO0;
    private final int TYPE_2 = NO1;

    private List<HuDongBean.ContentBean> mList;
    private Context mContext;

    public HuDongAdapter(List<HuDongBean.ContentBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 返回条目的总数量
     */
    @Override
    public int getViewTypeCount() {
        return NO2;
    }

    @Override
    public int getItemViewType(int position) {
        int type = mList.get(position).getType();
        if (type == NO2) {
            return TYPE_1;
        } else if (type == NO1) {
            return TYPE_2;
        } else {
            return TYPE_1;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HuDongViewHolder holder;
        int type = getItemViewType(position);
        if (convertView == null) {
            holder = new HuDongViewHolder();
            switch (type) {
                case TYPE_1:
                    convertView = View.inflate(mContext, R.layout.item_hudong_question, null);
                    holder.head = convertView.findViewById(R.id.iv_item_hudong_question_head);
                    holder.name = convertView.findViewById(R.id.txt_item_hudong_question_name);
                    holder.time = convertView.findViewById(R.id.txt_item_hudong_question_time);
                    holder.title = convertView.findViewById(R.id.txt_item_hudong_question_title);
                    holder.conte = convertView.findViewById(R.id.txt_item_hudong_question_content);
                    break;
                case TYPE_2:
                    convertView = View.inflate(mContext, R.layout.item_hudong_comment, null);
                    holder.name = convertView.findViewById(R.id.item_tv_project_username);
                    holder.title = convertView.findViewById(R.id.item_tv_project_time);
                    holder.conte = convertView.findViewById(R.id.item_txt_comment);
                    break;
                default:
                    break;
            }

            convertView.setTag(holder);
        } else {
            holder = (HuDongViewHolder) convertView.getTag();
        }

        switch (type) {
            case TYPE_1:
                if (!TextUtils.isEmpty(mList.get(position).getObject_name())){
                    holder.title.setText(mList.get(position).getObject_name());
                }
                holder.name.setText(mList.get(position).getNickname());
                holder.conte.setText(mList.get(position).getContent());
                holder.time.setText(ConstUtils.getDateTime(mList.get(position).getCreated_time()));
                  Glide.with(mContext)
                            .load(ConstUtils.getStringNoEmpty(mList.get(position).getHead_img()))
                            .bitmapTransform(new GlideCircleTransform(mContext)).crossFade(1000)
                            .placeholder(R.mipmap.moren)
                            .into(holder.head);

                break;
            case TYPE_2:
                if (!TextUtils.isEmpty(mList.get(position).getObject_name())){
                    holder.title.setText(mList.get(position).getObject_name());
                }
                holder.name.setText(mList.get(position).getNickname());
                holder.conte.setText(mList.get(position).getContent());
                holder.conte.setMovementMethod(ScrollingMovementMethod.getInstance());
                holder.time.setText(ConstUtils.getDateTime(mList.get(position).getCreated_time()));
                Glide.with(mContext)
                        .load(ConstUtils.getStringNoEmpty(mList.get(position).getHead_img()))
                        .bitmapTransform(new GlideCircleTransform(mContext)).crossFade(1000)
                        .placeholder(R.mipmap.moren)
                        .into(holder.head);
                break;
            default:
                break;
        }
        return convertView;
    }

    private class HuDongViewHolder{
        RoundedImageView head;
        TextView name;
        TextView time;
        TextView title;
        TextView conte;
    }
}
