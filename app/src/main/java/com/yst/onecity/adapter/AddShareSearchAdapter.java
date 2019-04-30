package com.yst.onecity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.onecity.R;
import com.yst.onecity.bean.search.SearchShareBean;
import com.yst.onecity.view.CustomTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO2;
import static com.yst.onecity.Constant.NO3;

/**
 * 添加分享搜索列表适配器
 *
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/5/22
 */

public class AddShareSearchAdapter extends BaseAdapter {

    /**
     * 一张图
     */
    private final int ONE_PIC = NO0;
    /**
     * 三张图
     */
    private final int THREE_PIC = NO1;

    private Context mContext;
    private List<SearchShareBean.ContentBean> mList;
    private boolean isEdit;

    public AddShareSearchAdapter(Context context, List<SearchShareBean.ContentBean> list) {
        mContext = context;
        mList = list;
    }

    /**
     * 编辑状态赋值
     *
     * @param isEdit 编辑状态
     */
    public void isEdit(boolean isEdit) {
        this.isEdit = isEdit;
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

    @Override
    public int getViewTypeCount() {
        return NO2;
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.get(position).getCurrencyAttachmentVos().size() == NO1) {
            //一张图片
            return ONE_PIC;
        } else if (mList.get(position).getCurrencyAttachmentVos().size() == NO3) {
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
                    convertView = View.inflate(mContext, R.layout.item_share_one, null);
                    holder = new OneViewHolder(convertView);
                    convertView.setTag(holder);
                } else {
                    holder = (OneViewHolder) convertView.getTag();
                }
                holder.mTxtTitleShare.setText(mList.get(position).getTitle());
                holder.mTxtContentShare.setText(mList.get(position).getDescription());
                holder.mTxtFenxiang.setText(String.valueOf(mList.get(position).getShareNum()));
                holder.mTxtPinglun.setText(String.valueOf(mList.get(position).getCommentNum()));
                holder.mTxtDianzan.setText(String.valueOf(mList.get(position).getFabulousNum()));
                if (mList.get(position).getCurrencyAttachmentVos().size() > NO0 && mList.get(position).getCurrencyAttachmentVos() != null) {
                    Glide.with(mContext).load(mList.get(position).getCurrencyAttachmentVos().get(0).getAddress()).into(holder.mIvPicShare);
                }
                checkBoxStatus(NO1, holder, null, position, isEdit);
                break;
            case THREE_PIC:
                ThreeViewHolder holder3;
                if (convertView == null) {
                    convertView = View.inflate(mContext, R.layout.item_share_three, null);
                    holder3 = new ThreeViewHolder(convertView);
                    convertView.setTag(holder3);
                } else {
                    holder3 = (ThreeViewHolder) convertView.getTag();
                }
                holder3.mTxtItemTitle.setText(mList.get(position).getTitle());
                holder3.mTxtItemContent.setText(mList.get(position).getDescription());
                holder3.mTxtItemFenxiang.setText(String.valueOf(mList.get(position).getShareNum()));
                holder3.mTxtItemPinglun.setText(String.valueOf(mList.get(position).getCommentNum()));
                holder3.mTxtItemDianzan.setText(String.valueOf(mList.get(position).getFabulousNum()));
                if (mList.get(position).getCurrencyAttachmentVos().size() > NO0 && mList.get(position).getCurrencyAttachmentVos() != null) {
                    Glide.with(mContext).load(mList.get(position).getCurrencyAttachmentVos().get(0).getAddress()).into(holder3.mIvPic1);
                    Glide.with(mContext).load(mList.get(position).getCurrencyAttachmentVos().get(1).getAddress()).into(holder3.mIvPic2);
                    Glide.with(mContext).load(mList.get(position).getCurrencyAttachmentVos().get(2).getAddress()).into(holder3.mIvPic3);
                }
                checkBoxStatus(NO3, null, holder3, position, isEdit);
                break;
            default:
                break;
        }
        return convertView;
    }

    /**
     * 勾选框隐藏/显示状态
     *
     * @param type 条目类型
     * @param holder 单图holder
     * @param holder3 三图holder
     * @param position 条目下标
     */
    private void checkBoxStatus(int type, final OneViewHolder holder, final ThreeViewHolder holder3, final int position, boolean isEdit) {
        if (type == NO1) {
            if (mList.get(position).isClick()) {
                holder.mCbShare.setChecked(true);
            } else {
                holder.mCbShare.setChecked(false);
            }

            holder.mCbShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean click = mList.get(position).isClick();
                    if (click) {
                        holder.mCbShare.setChecked(false);
                    } else {
                        holder.mCbShare.setChecked(true);
                    }
                    mList.get(position).setClick(!click);
                }
            });
        } else if (type == NO3) {
            if (mList.get(position).isClick()) {
                holder3.mCbItemShare.setChecked(true);
            } else {
                holder3.mCbItemShare.setChecked(false);
            }

            holder3.mCbItemShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean click = mList.get(position).isClick();
                    if (click) {
                        holder3.mCbItemShare.setChecked(false);
                    } else {
                        holder3.mCbItemShare.setChecked(true);
                    }
                    mList.get(position).setClick(!click);
                }
            });
        }

    }

    static class OneViewHolder {
        @BindView(R.id.cb_share)
        CheckBox mCbShare;
        @BindView(R.id.txt_title_share)
        TextView mTxtTitleShare;
        @BindView(R.id.txt_content_share)
        TextView mTxtContentShare;
        @BindView(R.id.iv_pic_share)
        ImageView mIvPicShare;
        @BindView(R.id.txt_fenxiang)
        CustomTextView mTxtFenxiang;
        @BindView(R.id.txt_pinglun)
        CustomTextView mTxtPinglun;
        @BindView(R.id.txt_dianzan)
        CustomTextView mTxtDianzan;

        OneViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ThreeViewHolder {
        @BindView(R.id.cb_item_share)
        CheckBox mCbItemShare;
        @BindView(R.id.txt_item_title)
        TextView mTxtItemTitle;
        @BindView(R.id.txt_item_content)
        TextView mTxtItemContent;
        @BindView(R.id.iv_pic1)
        ImageView mIvPic1;
        @BindView(R.id.iv_pic2)
        ImageView mIvPic2;
        @BindView(R.id.iv_pic3)
        ImageView mIvPic3;
        @BindView(R.id.txt_item_fenxiang)
        CustomTextView mTxtItemFenxiang;
        @BindView(R.id.txt_item_pinglun)
        CustomTextView mTxtItemPinglun;
        @BindView(R.id.txt_item_dianzan)
        CustomTextView mTxtItemDianzan;

        ThreeViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
