package com.yst.onecity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.onecity.R;
import com.yst.onecity.bean.agent.PublishCourseBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO2;
import static com.yst.onecity.Constant.NO3;

/**
 * 发布课题里的适配器
 *
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/5/18
 */

public class CourseShareAdapter extends BaseAdapter {

    /**
     * 一张图
     */
    private final int ONE_PIC = NO0;
    /**
     * 三张图
     */
    private final int THREE_PIC = NO1;

    private List<PublishCourseBean.ContentBean> mList;
    private Context mContext;

    public CourseShareAdapter(List<PublishCourseBean.ContentBean> list, Context context) {
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
        if (mList.get(position).getImages().size() == NO1) {
            //一张图片
            return ONE_PIC;
        } else if (mList.get(position).getImages().size() == NO3) {
            //三张图片
            return THREE_PIC;
        }
        return ONE_PIC;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        switch (getItemViewType(position)) {
            case ONE_PIC:
                OneViewHolder holder;
                if (convertView == null) {
                    convertView = View.inflate(mContext, R.layout.item_course_one, null);
                    holder = new OneViewHolder(convertView);
                    convertView.setTag(holder);
                } else {
                    holder = (OneViewHolder) convertView.getTag();
                }

                holder.mTxtOneTitle.setText(mList.get(position).getTitle());
                holder.mTxtOneContent.setText(mList.get(position).getDescription());
                if (mList.get(position).getImages().size() > NO0 && mList.get(position).getImages() != null) {
                    Glide.with(mContext).load(mList.get(position).getImages().get(0)).into(holder.mIvOnePic);
                }
                holder.mIvOneDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mList.remove(position);
                        notifyDataSetChanged();
                    }
                });
                break;
            case THREE_PIC:
                ThreeViewHolder holder3;
                if (convertView == null) {
                    convertView = View.inflate(mContext, R.layout.item_course_three, null);
                    holder3 = new ThreeViewHolder(convertView);
                    convertView.setTag(holder3);
                } else {
                    holder3 = (ThreeViewHolder) convertView.getTag();
                }

                holder3.mTxtShareTitle.setText(mList.get(position).getTitle());
                holder3.mTxtShareContent.setText(mList.get(position).getDescription());
                if (mList.get(position).getImages().size() > NO0 && mList.get(position).getImages() != null) {
                    Glide.with(mContext).load(mList.get(position).getImages().get(0)).into(holder3.mIvSharePic1);
                    Glide.with(mContext).load(mList.get(position).getImages().get(1)).into(holder3.mIvSharePic2);
                    Glide.with(mContext).load(mList.get(position).getImages().get(2)).into(holder3.mIvSharePic3);
                }
                holder3.mIvShareDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mList.remove(position);
                        notifyDataSetChanged();
                    }
                });
                break;
            default:
                break;
        }
        return convertView;
    }

    class OneViewHolder {
        @BindView(R.id.iv_one_delete)
        ImageView mIvOneDelete;
        @BindView(R.id.txt_one_title)
        TextView mTxtOneTitle;
        @BindView(R.id.txt_one_content)
        TextView mTxtOneContent;
        @BindView(R.id.iv_one_pic)
        ImageView mIvOnePic;

        OneViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class ThreeViewHolder {
        @BindView(R.id.iv_share_delete)
        ImageView mIvShareDelete;
        @BindView(R.id.txt_share_title)
        TextView mTxtShareTitle;
        @BindView(R.id.txt_share_content)
        TextView mTxtShareContent;
        @BindView(R.id.iv_share_pic1)
        ImageView mIvSharePic1;
        @BindView(R.id.iv_share_pic2)
        ImageView mIvSharePic2;
        @BindView(R.id.iv_share_pic3)
        ImageView mIvSharePic3;

        ThreeViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
