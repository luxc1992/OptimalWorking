package com.yst.onecity.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.view.roundedimageview.RoundedImageView;
import com.yst.onecity.R;
import com.yst.onecity.activity.issue.AddCommodityActivity;
import com.yst.onecity.activity.issue.AddProductPlanActivity;
import com.yst.onecity.activity.mine.ProductPlanDetailActivity;
import com.yst.onecity.bean.ProductPlanBean;
import com.yst.onecity.view.SpacesItemDecoration;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO15;
import static com.yst.onecity.Constant.NO2;
import static com.yst.onecity.Constant.NO3;
import static com.yst.onecity.Constant.NO4;
import static com.yst.onecity.Constant.NO5;
import static com.yst.onecity.Constant.NO6;
import static com.yst.onecity.Constant.NO7;
import static com.yst.onecity.Constant.hunterDiary;

/**
 * 我的发布-产品计划item  Adapter
 *
 * @author liumanqing
 * @version 1.0.1
 * @date 2018/3/20
 */
public class MyIssueProductPlanAdapter extends BaseAdapter {
    private List<ProductPlanBean.ContentBean> myIssueProductPlen;
    private Activity context;
    private ProductPlanRecyclerViewAdapter adapter;
    private LinearLayoutManager layoutManager;
    private View.OnClickListener onClickListener;
    private List<ProductPlanBean.ContentBean.ProductSBean> productS;
    private boolean isAdd = true;

    public MyIssueProductPlanAdapter(List<ProductPlanBean.ContentBean> myIssueProductPlen, Activity context, View.OnClickListener onClickListener) {
        this.myIssueProductPlen = myIssueProductPlen;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    public void setMyIssueData(List<ProductPlanBean.ContentBean> myIssueProductPlen) {
        if (myIssueProductPlen != null) {
            this.myIssueProductPlen = myIssueProductPlen;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return myIssueProductPlen == null ? 0 : myIssueProductPlen.size();
    }

    @Override
    public Object getItem(int i) {
        return myIssueProductPlen.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyIssueProductPlanViewHolder vh = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_product_plan, null);
            vh = new MyIssueProductPlanViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (MyIssueProductPlanViewHolder) convertView.getTag();
        }
        isIssue(vh, position);
        vh.time.setText(myIssueProductPlen.get(position).getCreateTime());
        vh.productTitle.setText(myIssueProductPlen.get(position).getTitle());
        if (myIssueProductPlen.get(position).getAddress() != null && myIssueProductPlen.get(position).getAddress() != "") {
            vh.lineLocation.setVisibility(View.VISIBLE);
            vh.location.setText(myIssueProductPlen.get(position).getAddress());
        } else {
            vh.lineLocation.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    /**
     * 判断是否审核通过 1审核通过 2审核失败 0审核中
     */
    public void isType(final int position, MyIssueProductPlanViewHolder vh) {

        if (NO1 == myIssueProductPlen.get(position).getStatus()) {
            vh.isPass.setText("审核通过");
            vh.lineFail.setVisibility(View.GONE);
            vh.imgProductNum.setVisibility(View.VISIBLE);
            vh.lineNum.setVisibility(View.VISIBLE);
            vh.lineComplieDelete.setVisibility(View.VISIBLE);
            vh.lineTime.setVisibility(View.VISIBLE);
            vh.shareNum.setText(myIssueProductPlen.get(position).getShareNum() + "");
            vh.commentNum.setText(myIssueProductPlen.get(position).getCommentNum() + "");
            vh.zanNum.setText(myIssueProductPlen.get(position).getFabulousNum() + "");
            //审核通过点击跳转到详情页
            vh.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle b = new Bundle();
                    b.putString("id", myIssueProductPlen.get(position).getId() + "");
                    JumpIntent.jump(context, ProductPlanDetailActivity.class, b);
                }
            });
            //审核通过点击编辑跳转到添加商品界面
            vh.lineCompile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("productPlanBean", (Serializable) myIssueProductPlen.get(position));
                    bundle.putInt("from", NO0);
                    bundle.putString("title", myIssueProductPlen.get(position).getTitle());
                    bundle.putString("describe", myIssueProductPlen.get(position).getDescription());
                    bundle.putString("type", myIssueProductPlen.get(position).getModelType() + "");
                    bundle.putString("address", myIssueProductPlen.get(position).getAddress());
                    bundle.putString("projectId", myIssueProductPlen.get(position).getId() + "");
                    JumpIntent.jump(context, AddCommodityActivity.class, bundle);
                }
            });
            //点击失败的监听回调
            vh.lineDelete.setTag(myIssueProductPlen.get(position).getId() + "");
            vh.lineDelete.setOnClickListener(onClickListener);
        } else if (NO0 == myIssueProductPlen.get(position).getStatus()) {
            //审核中没有编辑和删除操作
            vh.item.setOnClickListener(null);
            vh.isPass.setText("审核中");
            vh.lineFail.setVisibility(View.GONE);
            vh.lineComplieDelete.setVisibility(View.GONE);
            vh.lineNum.setVisibility(View.GONE);
            vh.imgProductNum.setVisibility(View.GONE);
            vh.lineTime.setVisibility(View.VISIBLE);
        } else if (NO2 == myIssueProductPlen.get(position).getStatus()) {
            vh.item.setOnClickListener(null);
            vh.isPass.setText("审核失败");
            vh.lineFail.setVisibility(View.VISIBLE);
            vh.lineComplieDelete.setVisibility(View.VISIBLE);
            vh.lineNum.setVisibility(View.GONE);
            vh.imgProductNum.setVisibility(View.GONE);
            vh.lineTime.setVisibility(View.VISIBLE);
            vh.fail.setText(myIssueProductPlen.get(position).getFeedback());
            //审核失败点击编辑跳转到添加产品计划界面
            vh.lineCompile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle b = new Bundle();
                    b.putSerializable("productPlanBean", (Serializable) myIssueProductPlen.get(position));
                    JumpIntent.jump(context, AddProductPlanActivity.class, b);
                }
            });
        }
        //删除点击监听回调
        vh.lineDelete.setTag(myIssueProductPlen.get(position).getId() + "");
        vh.lineDelete.setOnClickListener(onClickListener);
    }

    /**
     * 判断是否是发布
     *
     * @param vh
     * @param position
     */
    private void isIssue(MyIssueProductPlanViewHolder vh, final int position) {
        vh.lineCollect.setVisibility(View.GONE);
        isType(position, vh);
        //判断是否有关联的项目计划
        if (myIssueProductPlen.get(position).getProjectPlan() != null && myIssueProductPlen.get(position).getProjectPlan()!="") {
            String str = "#" + myIssueProductPlen.get(position).getProjectPlan() + "#   " + myIssueProductPlen.get(position).getDescription();
            int start = str.indexOf("#");
            int end = str.lastIndexOf("#") + 1;
            SpannableStringBuilder style = new SpannableStringBuilder(str);
            style.setSpan(new ForegroundColorSpan(Color.rgb(237, 84, 82)), start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            vh.content.setText(style);
        } else {
            vh.content.setText(myIssueProductPlen.get(position).getDescription());
        }
        /**
         * 判断我的发布里面是否有商品
         */
        if (myIssueProductPlen.get(position).getProductS() != null && myIssueProductPlen.get(position).getProductS().size() > 0) {
            vh.lineRecy.setVisibility(View.VISIBLE);
            productS = myIssueProductPlen.get(position).getProductS();
            adapter = new ProductPlanRecyclerViewAdapter(context, productS, null, null);
            layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            // 设置布局管理器
            vh.recyclerView.setLayoutManager(layoutManager);
            // 设置adapter
            vh.recyclerView.setAdapter(adapter);
            //设置recyclerView的分隔线
            if (isAdd) {
                vh.recyclerView.addItemDecoration(new SpacesItemDecoration(NO15, NO0));
                isAdd = false;
            } else {
                adapter.notifyDataSetChanged();
            }
            //如果已经审核通过就点击商品跳转到详情页
            if (NO1 == myIssueProductPlen.get(position).getStatus()) {
                adapter.setOnItemClickListener(new ProductPlanRecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view) {
                        Bundle b = new Bundle();
                        b.putString("id", myIssueProductPlen.get(position).getId() + "");
                        JumpIntent.jump(context, ProductPlanDetailActivity.class, b);
                    }
                });
            }
        } else {
            vh.lineRecy.setVisibility(View.GONE);
        }
        /**
         * 判断我的发布里面是否有日记
         */
        if (myIssueProductPlen.get(position).getProductPlanDailyVo() != null) {
            vh.lineCover.setVisibility(View.GONE);
            vh.lineNote.setVisibility(View.VISIBLE);
            vh.oneImage.setVisibility(View.GONE);
            vh.tvNoteTitle.setText(myIssueProductPlen.get(position).getProductPlanDailyVo().getName());
            vh.tvNoteAuthor.setText(hunterDiary);
            vh.tvNoteTime.setText(myIssueProductPlen.get(position).getProductPlanDailyVo().getCreatedTime());
            vh.tvNoteContent.setText(myIssueProductPlen.get(position).getProductPlanDailyVo().getContent());
            Glide.with(context).load(myIssueProductPlen.get(position).getProductPlanDailyVo().getUrl()).error(R.mipmap.default_nor_avatar).into(vh.imgHead);
            int modelType = myIssueProductPlen.get(position).getModelType();
            switch (modelType) {
                //日记里的视频展示第一帧
                case NO4:
                    vh.lineThreeImg.setVisibility(View.GONE);
                    vh.oneImage.setVisibility(View.GONE);
                    vh.imgNoteOne.setVisibility(View.GONE);
                    vh.framVideoNote.setVisibility(View.VISIBLE);
                    if (myIssueProductPlen.get(position).getProductPlanDailyVo().getCurrencyAttachments() != null && myIssueProductPlen.get(position).getProductPlanDailyVo().getCurrencyAttachments().size() > 0) {
                        Glide.with(context).load(myIssueProductPlen.get(position).getProductPlanDailyVo().getCurrencyAttachments().get(0).getVideoCoverAddress()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.videoImgNote);
                    } else {
                        Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.videoImgNote);
                    }
                    break;
                //日记的单图
                case NO5:
                    vh.lineThreeImg.setVisibility(View.GONE);
                    vh.oneImage.setVisibility(View.GONE);
                    vh.imgNoteOne.setVisibility(View.VISIBLE);
                    vh.framVideoNote.setVisibility(View.GONE);
                    if (myIssueProductPlen.get(position).getProductPlanDailyVo().getCurrencyAttachments() != null && myIssueProductPlen.get(position).getProductPlanDailyVo().getCurrencyAttachments().size() > 0) {
                        Glide.with(context).load(myIssueProductPlen.get(position).getProductPlanDailyVo().getCurrencyAttachments().get(0).getAddress()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.imgNoteOne);
                    } else {
                        Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.imgNoteOne);
                    }
                    break;
                //日记的两图
                case NO6:
                    vh.lineThreeImg.setVisibility(View.GONE);
                    vh.oneImage.setVisibility(View.GONE);
                    vh.imgNoteOne.setVisibility(View.GONE);
                    vh.framVideoNote.setVisibility(View.GONE);
                    vh.lineNoteImage.setVisibility(View.VISIBLE);
                    vh.imgNoteThree3.setVisibility(View.INVISIBLE);
                    if (myIssueProductPlen.get(position).getProductPlanDailyVo().getCurrencyAttachments() != null && myIssueProductPlen.get(position).getProductPlanDailyVo().getCurrencyAttachments().size() > 0) {
                        Glide.with(context).load(myIssueProductPlen.get(position).getProductPlanDailyVo().getCurrencyAttachments().get(0).getAddress()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.imgNoteThree1);
                        Glide.with(context).load(myIssueProductPlen.get(position).getProductPlanDailyVo().getCurrencyAttachments().get(1).getAddress()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.imgNoteThree2);
                    } else {
                        Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.imgNoteThree1);
                        Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.imgNoteThree2);
                    }
                    break;
                //日记的三图
                case NO7:
                    vh.lineThreeImg.setVisibility(View.GONE);
                    vh.oneImage.setVisibility(View.GONE);
                    vh.imgNoteOne.setVisibility(View.GONE);
                    vh.framVideoNote.setVisibility(View.GONE);
                    vh.lineNoteImage.setVisibility(View.VISIBLE);
                    vh.imgNoteThree3.setVisibility(View.VISIBLE);
                    if (myIssueProductPlen.get(position).getProductPlanDailyVo().getCurrencyAttachments() != null && myIssueProductPlen.get(position).getProductPlanDailyVo().getCurrencyAttachments().size() > 0) {
                        Glide.with(context).load(myIssueProductPlen.get(position).getProductPlanDailyVo().getCurrencyAttachments().get(0).getAddress()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.imgNoteThree1);
                        Glide.with(context).load(myIssueProductPlen.get(position).getProductPlanDailyVo().getCurrencyAttachments().get(1).getAddress()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.imgNoteThree2);
                        Glide.with(context).load(myIssueProductPlen.get(position).getProductPlanDailyVo().getCurrencyAttachments().get(2).getAddress()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.imgNoteThree3);
                    } else {
                        Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.imgNoteThree1);
                        Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.imgNoteThree2);
                        Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.imgNoteThree3);
                    }
                    break;
                default:
                    break;
            }
        } else {
            vh.lineNote.setVisibility(View.GONE);
        }

        /**
         * 判断有几个图片
         */
        int modelType = myIssueProductPlen.get(position).getModelType();
        switch (modelType) {
            //封面图-视频展示第一帧
            case NO0:
                vh.lineCover.setVisibility(View.VISIBLE);
                vh.oneImage.setVisibility(View.GONE);
                vh.framVideo.setVisibility(View.VISIBLE);
                vh.lineNote.setVisibility(View.GONE);
                vh.lineThreeImg.setVisibility(View.GONE);
                if (myIssueProductPlen.get(position).getCurrencyAttachmentVos() != null && myIssueProductPlen.get(position).getCurrencyAttachmentVos().size() > 0) {
                    Glide.with(context).load(myIssueProductPlen.get(position).getCurrencyAttachmentVos().get(0).getVideoCoverAddress()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.videoImg);
                } else {
                    Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.videoImg);
                }
                break;
            //封面图单图
            case NO1:
                vh.lineCover.setVisibility(View.VISIBLE);
                vh.oneImage.setVisibility(View.VISIBLE);
                vh.framVideo.setVisibility(View.GONE);
                vh.lineThreeImg.setVisibility(View.GONE);
                vh.lineNote.setVisibility(View.GONE);
                if (myIssueProductPlen.get(position).getCurrencyAttachmentVos() != null && myIssueProductPlen.get(position).getCurrencyAttachmentVos().size() > 0) {
                    Glide.with(context).load(myIssueProductPlen.get(position).getCurrencyAttachmentVos().get(0).getAddress()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.oneImage);
                } else {
                    Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.oneImage);
                }
                break;
            //封面图两图
            case NO2:
                vh.lineCover.setVisibility(View.VISIBLE);
                vh.oneImage.setVisibility(View.GONE);
                vh.framVideo.setVisibility(View.GONE);
                vh.lineThreeImg.setVisibility(View.VISIBLE);
                vh.threeImage3.setVisibility(View.INVISIBLE);
                vh.lineNote.setVisibility(View.GONE);
                if (myIssueProductPlen.get(position).getCurrencyAttachmentVos() != null && myIssueProductPlen.get(position).getCurrencyAttachmentVos().size() > 0) {
                    Glide.with(context).load(myIssueProductPlen.get(position).getCurrencyAttachmentVos().get(0).getAddress()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.threeImage1);
                    Glide.with(context).load(myIssueProductPlen.get(position).getCurrencyAttachmentVos().get(1).getAddress()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.threeImage2);
                } else {
                    Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.threeImage1);
                    Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.threeImage2);
                }
                break;
            //封面图三图
            case NO3:
                vh.lineCover.setVisibility(View.VISIBLE);
                vh.oneImage.setVisibility(View.GONE);
                vh.framVideo.setVisibility(View.GONE);
                vh.lineThreeImg.setVisibility(View.VISIBLE);
                vh.threeImage3.setVisibility(View.VISIBLE);
                vh.lineNote.setVisibility(View.GONE);
                if (myIssueProductPlen.get(position).getCurrencyAttachmentVos() != null && myIssueProductPlen.get(position).getCurrencyAttachmentVos().size() > 0) {
                    Glide.with(context).load(myIssueProductPlen.get(position).getCurrencyAttachmentVos().get(0).getAddress()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.threeImage1);
                    Glide.with(context).load(myIssueProductPlen.get(position).getCurrencyAttachmentVos().get(1).getAddress()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.threeImage2);
                    Glide.with(context).load(myIssueProductPlen.get(position).getCurrencyAttachmentVos().get(2).getAddress()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.threeImage3);
                } else {
                    Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.threeImage1);
                    Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.threeImage2);
                    Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.threeImage3);
                }
                break;
            default:
                break;
        }
    }

    class MyIssueProductPlanViewHolder {
        @BindView(R.id.line_cover)
        LinearLayout lineCover;
        @BindView(R.id.product_plan_item)
        LinearLayout item;
        @BindView(R.id.img_plannum)
        ImageView imgProductNum;
        @BindView(R.id.tv_product_plan_title)
        TextView productTitle;
        @BindView(R.id.tv_product_plan_time)
        TextView time;
        @BindView(R.id.tv_product_plan_location)
        TextView location;
        @BindView(R.id.tv_product_plan_content)
        TextView content;
        @BindView(R.id.img_product_plan_one)
        ImageView oneImage;
        @BindView(R.id.img_product_plan_three1)
        ImageView threeImage1;
        @BindView(R.id.img_product_plan_three2)
        ImageView threeImage2;
        @BindView(R.id.img_product_plan_three3)
        ImageView threeImage3;
        @BindView(R.id.fram_video)
        FrameLayout framVideo;
        @BindView(R.id.tv_product_plan_share_num)
        TextView shareNum;
        @BindView(R.id.tv_product_plan_comment_num)
        TextView commentNum;
        @BindView(R.id.tv_product_plan_zan_num)
        TextView zanNum;
        @BindView(R.id.tv_product_plan_ispass)
        TextView isPass;
        @BindView(R.id.rv_product_plan)
        RecyclerView recyclerView;
        @BindView(R.id.line_recy)
        LinearLayout lineRecy;
        @BindView(R.id.tv_product_plan_fail)
        TextView fail;
        @BindView(R.id.tv_product_plan_compile)
        TextView compile;
        @BindView(R.id.tv_product_plan_delete)
        TextView delete;
        @BindView(R.id.line_num)
        RelativeLayout lineNum;
        @BindView(R.id.line_productplan_complie_delete)
        LinearLayout lineComplieDelete;
        @BindView(R.id.line_time)
        LinearLayout lineTime;
        @BindView(R.id.line_product_issue)
        LinearLayout lineIssue;
        @BindView(R.id.line_product_collect)
        LinearLayout lineCollect;
        @BindView(R.id.img_product_plan_ischeck)
        CheckBox isCheck;
        @BindView(R.id.line_three_img)
        LinearLayout lineThreeImg;
        @BindView(R.id.line_note)
        LinearLayout lineNote;
        @BindView(R.id.img_note_head)
        RoundedImageView imgHead;
        @BindView(R.id.img_note_one)
        ImageView imgNoteOne;
        @BindView(R.id.img_note_three1)
        ImageView imgNoteThree1;
        @BindView(R.id.img_note_three2)
        ImageView imgNoteThree2;
        @BindView(R.id.img_note_three3)
        ImageView imgNoteThree3;
        @BindView(R.id.tv_note_title)
        TextView tvNoteTitle;
        @BindView(R.id.tv_note_time)
        TextView tvNoteTime;
        @BindView(R.id.tv_note_author)
        TextView tvNoteAuthor;
        @BindView(R.id.tv_note_content)
        TextView tvNoteContent;
        @BindView(R.id.fram_video_note)
        FrameLayout framVideoNote;
        @BindView(R.id.line_note_three_img)
        LinearLayout lineNoteImage;
        @BindView(R.id.product_plan_video_img)
        ImageView videoImg;
        @BindView(R.id.product_plan_video_img_note)
        ImageView videoImgNote;
        @BindView(R.id.line_fail)
        LinearLayout lineFail;
        @BindView(R.id.line_compile)
        LinearLayout lineCompile;
        @BindView(R.id.line_delete)
        LinearLayout lineDelete;
        @BindView(R.id.product_plan_video_note)
        ImageView videoImgNotePlay;
        @BindView(R.id.line_product_plan_location)
        LinearLayout lineLocation;

        MyIssueProductPlanViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 点击监听
     */
    public interface OnVpItemClickListener {
        /**
         * 条目点击监听OnClick
         *
         * @param position 索引
         */
        void onClick(int position);
    }

    private MyIssueProductPlanAdapter.OnVpItemClickListener mOnItemClickListener = null;

    /**
     * item的点击监听
     *
     * @param listener 监听
     */
    public void setOnItemClickListener(MyIssueProductPlanAdapter.OnVpItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
