package com.yst.onecity.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.onecity.R;
import com.yst.onecity.bean.home.InformationBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO2;
import static com.yst.onecity.Constant.NO3;
import static com.yst.onecity.Constant.NO4;

/**
 * 我的发布-资讯item  Adapter
 *
 * @author liumanqing
 * @version 1.0.1
 * @date 2018/3/20
 */

public class MyIssueInformationAdapter extends BaseAdapter {
    private Activity context;
    private List<InformationBean.ContentBean> myIssue;
    private View.OnClickListener onClickListener;
    /**
     * 一张图片
     */
    private final int TYPE_ONEP = NO1;
    /**
     * 两张图片
     */
    private final int TYPE_TWOP = NO2;
    /**
     * 三张图片
     */
    private final int TYPE_THREEP = NO3;
    /**
     * 视频
     */
    private final int TYPE_VIDEO = NO0;

    public MyIssueInformationAdapter(Activity context, List<InformationBean.ContentBean> myIssue, View.OnClickListener onClickListener) {
        this.context = context;
        this.myIssue = myIssue;
        this.onClickListener = onClickListener;
    }

    /**
     * 设置适配器数据
     *
     * @param bean
     */
    public void setMyIssueDate(List<InformationBean.ContentBean> bean) {
        if (myIssue != null) {
            this.myIssue = bean;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return myIssue == null ? NO0 : myIssue.size();
    }

    @Override
    public Object getItem(int i) {
        return myIssue.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemViewType(int position) {
        switch (myIssue.get(position).getModelType()) {
            case TYPE_ONEP:
                return TYPE_ONEP;
            case TYPE_TWOP:
                return TYPE_TWOP;
            case TYPE_THREEP:
                return TYPE_THREEP;
            case TYPE_VIDEO:
                return TYPE_VIDEO;
            default:
                break;
        }
        return Integer.MAX_VALUE;
    }

    @Override
    public int getViewTypeCount() {
        return NO4;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        OnePicHolderView onePicHolderView = null;
        TwoPicHolderView twoPicHolderView = null;
        ThreePicHolderView threePicHolderView = null;
        FourPicHolderView fourPicHolderView = null;
        if (convertView == null) {
            switch (getItemViewType(position)) {
                case TYPE_ONEP:
                    convertView = LayoutInflater.from(context).inflate(R.layout.home_one_pic_layout, null);
                    onePicHolderView = new OnePicHolderView(convertView);
                    convertView.setTag(onePicHolderView);
                    break;
                case TYPE_TWOP:
                    convertView = LayoutInflater.from(context).inflate(R.layout.home_two_pic_layout, null);
                    twoPicHolderView = new TwoPicHolderView(convertView);
                    convertView.setTag(twoPicHolderView);
                    break;
                case TYPE_THREEP:
                    convertView = LayoutInflater.from(context).inflate(R.layout.home_three_pic_layout, null);
                    threePicHolderView = new ThreePicHolderView(convertView);
                    convertView.setTag(threePicHolderView);
                    break;
                case TYPE_VIDEO:
                    convertView = LayoutInflater.from(context).inflate(R.layout.home_four_pic_layout, null);
                    fourPicHolderView = new FourPicHolderView(convertView);
                    convertView.setTag(fourPicHolderView);
                    break;
                default:
                    break;
            }
        } else {
            switch (getItemViewType(position)) {
                case TYPE_ONEP:
                    onePicHolderView = (OnePicHolderView) convertView.getTag();
                    break;
                case TYPE_TWOP:
                    twoPicHolderView = (TwoPicHolderView) convertView.getTag();
                    break;
                case TYPE_THREEP:
                    threePicHolderView = (ThreePicHolderView) convertView.getTag();
                    break;
                case TYPE_VIDEO:
                    fourPicHolderView = (FourPicHolderView) convertView.getTag();
                    break;
                default:
                    break;
            }
        }
        switch (getItemViewType(position)) {
            case TYPE_ONEP:
                onePicHolderView.tvOneTitle.setText(myIssue.get(position).getTitle());
                onePicHolderView.tvOneCommetNum.setText(myIssue.get(position).getCommentNum() + "");
                onePicHolderView.tvOneAuthor.setText(myIssue.get(position).getName());
                //判断是否是转载
                if (myIssue.get(position).getReprintName() != null) {
                    onePicHolderView.tvOneIssueReprint.setVisibility(View.VISIBLE);
                    onePicHolderView.tvOneIssueReprint.setText("转载于# " + myIssue.get(position).getReprintName().toString());
                } else {
                    onePicHolderView.tvOneIssueReprint.setVisibility(View.GONE);
                }
                //判断是否有产品计划
                if (myIssue.get(position).getProductPlan() != null) {
                    onePicHolderView.llOnePicProductPlan.setVisibility(View.VISIBLE);
                    onePicHolderView.tvOneProductTitle.setText(myIssue.get(position).getProductPlan().getTitle());
                } else {
                    onePicHolderView.llOnePicProductPlan.setVisibility(View.GONE);
                }
                //展示单图的图片
                if (myIssue.get(position).getCurrencyAttachmentVos() != null && myIssue.get(position).getCurrencyAttachmentVos().size() > 0) {
                    Glide.with(context).load(myIssue.get(position).getCurrencyAttachmentVos().get(0).getAddress()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(onePicHolderView.ivOnePic);
                } else {
                    Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(onePicHolderView.ivOnePic);

                }
                //删除的监听回调
                onePicHolderView.btnOneDelete.setTag(myIssue.get(position).getId() + "");
                onePicHolderView.btnOneDelete.setOnClickListener(onClickListener);
                break;
            case TYPE_TWOP:
                twoPicHolderView.tvTwoTitle.setText(myIssue.get(position).getTitle());
                twoPicHolderView.tvTwoCommetNum.setText(myIssue.get(position).getCommentNum() + "");
                twoPicHolderView.tvTwoAuthor.setText(myIssue.get(position).getName());
                //判断是否是转载
                if (myIssue.get(position).getReprintName() != null) {
                    twoPicHolderView.tvTwoIssue.setVisibility(View.VISIBLE);
                    twoPicHolderView.tvTwoIssue.setText("转载于# " + myIssue.get(position).getReprintName().toString());
                } else {
                    twoPicHolderView.tvTwoIssue.setVisibility(View.GONE);
                }
                //两图的显示
                if (myIssue.get(position).getCurrencyAttachmentVos() != null && myIssue.get(position).getCurrencyAttachmentVos().size() > 0) {
                    Glide.with(context).load(myIssue.get(position).getCurrencyAttachmentVos().get(0).getAddress()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(twoPicHolderView.ivTwoPicOne);
                    Glide.with(context).load(myIssue.get(position).getCurrencyAttachmentVos().get(1).getAddress()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(twoPicHolderView.ivTwoPicTwo);
                } else {
                    Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(twoPicHolderView.ivTwoPicOne);
                    Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(twoPicHolderView.ivTwoPicTwo);
                }
                //判断是否有产品计划
                if (myIssue.get(position).getProductPlan() != null) {
                    twoPicHolderView.llTwoPicProductPlan.setVisibility(View.VISIBLE);
                    twoPicHolderView.tvTwoProductTitle.setText(myIssue.get(position).getProductPlan().getTitle());
                } else {
                    twoPicHolderView.llTwoPicProductPlan.setVisibility(View.GONE);
                }
                //删除监听回调
                twoPicHolderView.btnTwoDelete.setTag(myIssue.get(position).getId() + "");
                twoPicHolderView.btnTwoDelete.setOnClickListener(onClickListener);
                break;
            case TYPE_THREEP:
                threePicHolderView.tvThreeTitle.setText(myIssue.get(position).getTitle());
                threePicHolderView.tvThreeCommetNum.setText(myIssue.get(position).getCommentNum() + "");
                threePicHolderView.tvThreeAuthor.setText(myIssue.get(position).getName());
                //判断是否是转载
                if (myIssue.get(position).getReprintName() != null) {
                    threePicHolderView.tvThreeIssue.setVisibility(View.VISIBLE);
                    threePicHolderView.tvThreeIssue.setText("转载于# " + myIssue.get(position).getReprintName().toString());
                } else {
                    threePicHolderView.tvThreeIssue.setVisibility(View.GONE);
                }
                //三图的显示
                if (myIssue.get(position).getCurrencyAttachmentVos() != null && myIssue.get(position).getCurrencyAttachmentVos().size() > 0) {
                    Glide.with(context).load(myIssue.get(position).getCurrencyAttachmentVos().get(0).getAddress()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(threePicHolderView.ivThreePicOne);
                    Glide.with(context).load(myIssue.get(position).getCurrencyAttachmentVos().get(1).getAddress()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(threePicHolderView.ivThreePicTwo);
                    Glide.with(context).load(myIssue.get(position).getCurrencyAttachmentVos().get(2).getAddress()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(threePicHolderView.ivThreePicThree);
                } else {
                    Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(threePicHolderView.ivThreePicOne);
                    Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(threePicHolderView.ivThreePicTwo);
                    Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(threePicHolderView.ivThreePicThree);
                }
                //判断是否有产品计划
                if (myIssue.get(position).getProductPlan() != null) {
                    threePicHolderView.llThreePicProductPlan.setVisibility(View.VISIBLE);
                    threePicHolderView.tvThreeProductTitle.setText(myIssue.get(position).getProductPlan().getTitle());
                } else {
                    threePicHolderView.llThreePicProductPlan.setVisibility(View.GONE);
                }
                //删除的监听回调
                threePicHolderView.btnThreeDelete.setTag(myIssue.get(position).getId() + "");
                threePicHolderView.btnThreeDelete.setOnClickListener(onClickListener);
                break;
            case TYPE_VIDEO:
                fourPicHolderView.tvFourTitle.setText(myIssue.get(position).getTitle());
                fourPicHolderView.tvFourCommetNum.setText(myIssue.get(position).getCommentNum() + "");
                fourPicHolderView.tvFourAuthor.setText(myIssue.get(position).getName());
                //判断是否是转载
                if (myIssue.get(position).getReprintName() != null) {
                    fourPicHolderView.tvFourIssue.setVisibility(View.VISIBLE);
                    fourPicHolderView.tvFourIssue.setText("转载于# " + myIssue.get(position).getReprintName().toString());
                } else {
                    fourPicHolderView.tvFourIssue.setVisibility(View.GONE);
                }
                //判断是否有产品计划
                if (myIssue.get(position).getProductPlan() != null) {
                    fourPicHolderView.llFourPicProductPlan.setVisibility(View.VISIBLE);
                    fourPicHolderView.tvFourProductTitle.setText(myIssue.get(position).getProductPlan().getTitle());
                } else {
                    fourPicHolderView.llFourPicProductPlan.setVisibility(View.GONE);
                }
                //视频加载图片的第一帧
                if (myIssue.get(position).getCurrencyAttachmentVos() != null && myIssue.get(position).getCurrencyAttachmentVos().size() > 0) {
                    Glide.with(context).load(myIssue.get(position).getCurrencyAttachmentVos().get(0).getVideoCoverAddress()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(fourPicHolderView.videoImg);
                } else {
                    Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(fourPicHolderView.videoImg);
                }
                //删除的监听回调
                fourPicHolderView.btnFourDelete.setTag(myIssue.get(position).getId() + "");
                fourPicHolderView.btnFourDelete.setOnClickListener(onClickListener);
                break;
            default:
                break;
        }
        return convertView;
    }

    class OnePicHolderView {
        @BindView(R.id.tv_one_title)
        TextView tvOneTitle;
        @BindView(R.id.tv_one_author)
        TextView tvOneAuthor;
        @BindView(R.id.tv_one_commet_num)
        TextView tvOneCommetNum;
        @BindView(R.id.iv_one_pic)
        ImageView ivOnePic;
        @BindView(R.id.tv_one_product_title)
        TextView tvOneProductTitle;
        @BindView(R.id.ll_one_pic_product_plan)
        LinearLayout llOnePicProductPlan;
        @BindView(R.id.btn_one_product_delete)
        Button btnOneDelete;
        @BindView(R.id.line_one_collect)
        LinearLayout lineOneCollect;
        @BindView(R.id.tv_one_issue_reprint)
        TextView tvOneIssueReprint;

        OnePicHolderView(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class TwoPicHolderView {
        @BindView(R.id.tv_two_title)
        TextView tvTwoTitle;
        @BindView(R.id.iv_two_pic_one)
        ImageView ivTwoPicOne;
        @BindView(R.id.iv_two_pic_two)
        ImageView ivTwoPicTwo;
        @BindView(R.id.tv_two_author)
        TextView tvTwoAuthor;
        @BindView(R.id.tv_two_commet_num)
        TextView tvTwoCommetNum;
        @BindView(R.id.tv_two_product_title)
        TextView tvTwoProductTitle;
        @BindView(R.id.ll_two_pic_product_plan)
        LinearLayout llTwoPicProductPlan;
        @BindView(R.id.btn_two_product_delete)
        Button btnTwoDelete;
        @BindView(R.id.line_two_collect)
        LinearLayout lineTwoCollect;
        @BindView(R.id.tv_two_issue)
        TextView tvTwoIssue;

        TwoPicHolderView(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class ThreePicHolderView {
        @BindView(R.id.tv_three_title)
        TextView tvThreeTitle;
        @BindView(R.id.iv_three_pic_one)
        ImageView ivThreePicOne;
        @BindView(R.id.iv_three_pic_two)
        ImageView ivThreePicTwo;
        @BindView(R.id.iv_three_pic_three)
        ImageView ivThreePicThree;
        @BindView(R.id.tv_three_author)
        TextView tvThreeAuthor;
        @BindView(R.id.tv_three_commet_num)
        TextView tvThreeCommetNum;
        @BindView(R.id.tv_three_product_title)
        TextView tvThreeProductTitle;
        @BindView(R.id.ll_three_pic_product_plan)
        LinearLayout llThreePicProductPlan;
        @BindView(R.id.btn_three_product_delete)
        Button btnThreeDelete;
        @BindView(R.id.line_three_collect)
        LinearLayout lineThreeCollect;
        @BindView(R.id.tv_three_issue)
        TextView tvThreeIssue;

        ThreePicHolderView(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class FourPicHolderView {
        @BindView(R.id.tv_four_title)
        TextView tvFourTitle;
        @BindView(R.id.tv_four_author)
        TextView tvFourAuthor;
        @BindView(R.id.tv_four_commet_num)
        TextView tvFourCommetNum;
        @BindView(R.id.tv_four_product_title)
        TextView tvFourProductTitle;
        @BindView(R.id.ll_four_pic_product_plan)
        LinearLayout llFourPicProductPlan;
        @BindView(R.id.btn_four_product_delete)
        Button btnFourDelete;
        @BindView(R.id.line_four_collect)
        LinearLayout lineFourCollect;
        @BindView(R.id.tv_four_issue)
        TextView tvFourIssue;
        @BindView(R.id.frame_four_video)
        FrameLayout video;
        @BindView(R.id.img_video)
        ImageView videoImg;

        FourPicHolderView(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
