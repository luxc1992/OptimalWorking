package com.yst.onecity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.App;
import com.yst.basic.framework.view.roundedimageview.RoundedImageView;
import com.yst.onecity.R;
import com.yst.onecity.bean.InteractionBean;
import com.yst.onecity.view.MyGridView;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yst.onecity.Constant.COMMA;
import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO2;

/**
 * 互动列表多条目适配器
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/5/17
 */

public class InteractionAdapter extends BaseAdapter {

    /**
     * 3种类型item
     * 0  1  2 服务  资讯 商品
     */
    private final int TYPE_0 = NO0;
    private final int TYPE_1 = NO1;
    private final int TYPE_2 = NO2;

    private List<InteractionBean.ContentBean> mList;
    private Context mContext;

    public InteractionAdapter(List<InteractionBean.ContentBean> list, Context context) {
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
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        int type = mList.get(position).getIdType();
        if (type == NO0) {
            return TYPE_0;
        } else if (type == NO1) {
            return TYPE_1;
        } else {
            return TYPE_2;
        }
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ShareProduct mShareProduct;
        ProjectHolder mProjectHolder;
        ProductHolder mProductHolder;
        int type = getItemViewType(position);
        switch (type) {
            // 服务
            case TYPE_0:
                if (convertView == null) {
                    convertView = View.inflate(mContext, R.layout.item_hudong_comment, null);
                    mProjectHolder = new ProjectHolder(convertView);
                    convertView.setTag(mProjectHolder);
                } else {
                    mProjectHolder = (ProjectHolder) convertView.getTag();

                 }
                Glide.with(App.getInstance()).load(mList.get(position).getHead_img()).into(mProjectHolder.itemIvProjectIcon);
                mProjectHolder.itemTvProjectUsername.setText(mList.get(position).getNickname());
                mProjectHolder.itemTvProjectTime.setText(mList.get(position).getCreated_time());
                mProjectHolder.itemTxtComment.setText(mList.get(position).getContent());
                Glide.with(App.getInstance()).load(mList.get(position).getImgAddress()).into(mProjectHolder.itemIvProjectBg);
                mProjectHolder.itemTvProjectName.setText(mList.get(position).getServiceTitle());
                mProjectHolder.itemTvProjectPrice.setText("¥" + mList.get(position).getPrice());
                mProjectHolder.itemPrice.setGravity(View.VISIBLE);
                if (mList.get(position).getReplyContent() != null) {
                    mProjectHolder.llApplyComment.setVisibility(View.VISIBLE);
                    mProjectHolder.tvReplySearch.setVisibility(View.GONE);
                    mProjectHolder.itemTvProjectCommentUser.setText(mList.get(position).getUserName());
                    mProjectHolder.itemTvProjectCommentContent.setText(mList.get(position).getReplyContent());
                } else {
                    mProjectHolder.llApplyComment.setVisibility(View.GONE);
                    mProjectHolder.tvReplySearch.setVisibility(View.VISIBLE);
                }
                mProjectHolder.tvReplySearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mReplyCommentCallBack != null) {
                            mReplyCommentCallBack.onReplyCommentCallBack(position);
                        }
                    }
                });
                break;
            //咨询
            case TYPE_1:

                if (convertView == null) {
                    convertView = View.inflate(mContext, R.layout.item_hudong, null);
                    mShareProduct = new ShareProduct(convertView);
                    convertView.setTag(mShareProduct);
                } else {
                    mShareProduct = (ShareProduct) convertView.getTag();
                }
                if (mList.get(position).getConsultationImgUrl() != null && mList.get(position).getConsultationImgUrl().contains(COMMA)) {
                    String[] imgList = mList.get(position).getConsultationImgUrl().split(COMMA);
                    HuDongGridAdapter mHuDongGridAdapter = new HuDongGridAdapter(Arrays.asList(imgList), mContext);
                    mShareProduct.mygridView.setAdapter(mHuDongGridAdapter);
                    mShareProduct.mygridView.setVisibility(View.VISIBLE);
                    mShareProduct.irvProjectBg.setVisibility(View.GONE);
                } else {
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mShareProduct.irvProjectBg.getLayoutParams();
                    layoutParams.height = App.screenWidth * 4 / 9;
                    mShareProduct.irvProjectBg.setLayoutParams(layoutParams);
                    Glide.with(App.getInstance()).load(mList.get(position).getConsultationImgUrl()).into(mShareProduct.irvProjectBg);
                    mShareProduct.mygridView.setVisibility(View.GONE);
                    mShareProduct.irvProjectBg.setVisibility(View.VISIBLE);
                }
                Glide.with(App.getInstance()).load(mList.get(position).getHead_img()).into(mShareProduct.itemProjectHead);
                mShareProduct.itemTxtProjectUserName.setText(mList.get(position).getNickname());
                mShareProduct.itemTxtProjectTime.setText(mList.get(position).getCreated_time());
                mShareProduct.itemTxtProjectTitle.setText(mList.get(position).getContent());
                mShareProduct.itemTxtProjectContentTitle.setText(mList.get(position).getConsultationTitle());
                mShareProduct.itemTxtProjectContent.setText(mList.get(position).getConsultationDescription());
                mShareProduct.itemPrice.setGravity(View.GONE);
                if (mList.get(position).getReplyContent() != null) {
                    mShareProduct.tvZixunReply.setVisibility(View.GONE);
                } else {
                    mShareProduct.tvZixunReply.setVisibility(View.VISIBLE);
                    mShareProduct.tvZixunReply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mReplyCommentCallBack != null) {
                                mReplyCommentCallBack.onReplyCommentCallBack(position);
                            }
                        }
                    });
                }
                break;
            //商品
            case TYPE_2:
                if (convertView == null) {
                    convertView = View.inflate(mContext, R.layout.item_hudong_product, null);
                    mProductHolder = new ProductHolder(convertView);
                    convertView.setTag(mProductHolder);
                } else {
                    mProductHolder = (ProductHolder) convertView.getTag();
                }
                Glide.with(App.getInstance()).load(mList.get(position).getHead_img()).into(mProductHolder.ivItemProductHead);
                Glide.with(App.getInstance()).load(mList.get(position).getProductAttachment()).into(mProductHolder.ivHudongProduct);
                mProductHolder.itemProductUserName.setText(mList.get(position).getNickname());
                mProductHolder.itemProductUserTime.setText(mList.get(position).getCreated_time());
                mProductHolder.itemProductTitle.setText(mList.get(position).getContent());
                mProductHolder.itemHudongProductName.setText(mList.get(position).getProductName());
                mProductHolder.itemHudongProductPrice.setText(mList.get(position).getProductMaxprice());
                mProductHolder.itemHudongProductPro.setText(mList.get(position).getProductTitle());
                mProductHolder.itemPrice.setGravity(View.VISIBLE);
                if (mList.get(position).getReplyContent() != null) {
                    mProductHolder.tvProductReply.setVisibility(View.GONE);
                } else {
                    mProductHolder.tvProductReply.setVisibility(View.VISIBLE);
                    mProductHolder.tvProductReply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mReplyCommentCallBack != null) {
                                mReplyCommentCallBack.onReplyCommentCallBack(position);
                            }
                        }
                    });
                }
                break;
            default:
                break;
        }
        return convertView;
    }


    static class ProjectHolder {
        @BindView(R.id.item_iv_project_icon)
        RoundedImageView itemIvProjectIcon;
        @BindView(R.id.item_tv_project_username)
        TextView itemTvProjectUsername;
        @BindView(R.id.item_tv_project_time)
        TextView itemTvProjectTime;
        @BindView(R.id.ll_item_no)
        LinearLayout llItemNo;
        @BindView(R.id.item_txt_comment)
        TextView itemTxtComment;
        @BindView(R.id.item_iv_project_bg)
        RoundedImageView itemIvProjectBg;
        @BindView(R.id.item_tv_project_name)
        TextView itemTvProjectName;
        @BindView(R.id.item_tv_project_price)
        TextView itemTvProjectPrice;
        @BindView(R.id.rel_hudong_img_bg)
        RelativeLayout relHudongImgBg;
        @BindView(R.id.item_tv_project_comment_user)
        TextView itemTvProjectCommentUser;
        @BindView(R.id.item_tv_project_comment_content)
        TextView itemTvProjectCommentContent;
        @BindView(R.id.ll_apply_comment)
        LinearLayout llApplyComment;
        @BindView(R.id.tv_reply_search)
        TextView tvReplySearch;
        @BindView(R.id.item_price)
        LinearLayout itemPrice;
        ProjectHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    static class ShareProduct {
        @BindView(R.id.item_project_head)
        RoundedImageView itemProjectHead;
        @BindView(R.id.item_txt_project_user_name)
        TextView itemTxtProjectUserName;
        @BindView(R.id.item_txt_project_time)
        TextView itemTxtProjectTime;
        @BindView(R.id.ll_item_no)
        LinearLayout llItemNo;
        @BindView(R.id.item_txt_project_title)
        TextView itemTxtProjectTitle;
        @BindView(R.id.mygrid_view)
        MyGridView mygridView;
        @BindView(R.id.irv_project_bg)
        RoundedImageView irvProjectBg;
        @BindView(R.id.item_txt_project_content_title)
        TextView itemTxtProjectContentTitle;
        @BindView(R.id.item_txt_project_content)
        TextView itemTxtProjectContent;
        @BindView(R.id.rel_hudong_img_bg)
        LinearLayout relHudongImgBg;
        @BindView(R.id.item_price)
        LinearLayout itemPrice;
        @BindView(R.id.tv_zixun_reply)
        TextView tvZixunReply;

        ShareProduct(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ProductHolder {
        @BindView(R.id.iv_item_product_head)
        RoundedImageView ivItemProductHead;
        @BindView(R.id.item_product_user_name)
        TextView itemProductUserName;
        @BindView(R.id.item_product_user_time)
        TextView itemProductUserTime;
        @BindView(R.id.ll_item_no)
        LinearLayout llItemNo;
        @BindView(R.id.item_product_title)
        TextView itemProductTitle;
        @BindView(R.id.iv_hudong_product)
        RoundedImageView ivHudongProduct;
        @BindView(R.id.item_hudong_product_name)
        TextView itemHudongProductName;
        @BindView(R.id.item_hudong_product_pro)
        TextView itemHudongProductPro;
        @BindView(R.id.item_hudong_product_price)
        TextView itemHudongProductPrice;
        @BindView(R.id.rel_hudong_img_bg)
        RelativeLayout relHudongImgBg;
        @BindView(R.id.tv_product_reply)
        TextView tvProductReply;
        @BindView(R.id.item_price)
        LinearLayout itemPrice;

        ProductHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private ReplyCommentCallBack mReplyCommentCallBack;

    public interface ReplyCommentCallBack {
        /**
         * 刪除好友
         *
         * @param position
         */
        void onReplyCommentCallBack(int position);
    }

    public void setReplyCommentCallBack(ReplyCommentCallBack mReplyCommentCallBack) {
        this.mReplyCommentCallBack = mReplyCommentCallBack;
    }
}
