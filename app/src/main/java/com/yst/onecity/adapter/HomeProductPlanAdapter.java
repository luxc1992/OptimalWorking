package com.yst.onecity.adapter;

import android.app.Activity;
import android.graphics.Color;
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
import com.yst.basic.framework.view.roundedimageview.RoundedImageView;
import com.yst.onecity.R;
import com.yst.onecity.bean.home.PublishInfoAddProductPlanBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yst.onecity.Constant.NO4;
import static com.yst.onecity.Constant.NO5;
import static com.yst.onecity.Constant.NO6;
import static com.yst.onecity.Constant.NO7;
import static com.yst.onecity.Constant.hunterDiary;

/**
 * 首页-产品计划item  Adapter
 *
 * @author liumanqing
 * @version 1.0.1
 * @date 2018/3/20
 */
public class HomeProductPlanAdapter extends BaseAdapter {
    private List<PublishInfoAddProductPlanBean.ContentBean> myHomePlan;
    private Activity context;

    public HomeProductPlanAdapter(List<PublishInfoAddProductPlanBean.ContentBean> myHomePlan, Activity context) {
        this.myHomePlan = myHomePlan;
        this.context = context;
    }

    public void setHomeData(List<PublishInfoAddProductPlanBean.ContentBean> myHomePlan) {
        if (myHomePlan != null) {
            this.myHomePlan = myHomePlan;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return myHomePlan == null ? 0 : myHomePlan.size();
    }

    @Override
    public Object getItem(int i) {
        return myHomePlan.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        HomeProductPlanViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_product_plan, null);
            vh = new HomeProductPlanViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (HomeProductPlanViewHolder) convertView.getTag();
        }
        vh.lineCollect.setVisibility(View.GONE);
        vh.isCheck.setVisibility(View.VISIBLE);
        vh.lineIssue.setVisibility(View.GONE);
        vh.lineFail.setVisibility(View.GONE);
        vh.imgProductNum.setVisibility(View.VISIBLE);
        vh.lineNum.setVisibility(View.VISIBLE);
        vh.lineComplieDelete.setVisibility(View.VISIBLE);
        vh.lineTime.setVisibility(View.VISIBLE);
        vh.lineRecy.setVisibility(View.GONE);
        vh.time.setText(myHomePlan.get(position).getCreateTime());
        vh.productTitle.setText(myHomePlan.get(position).getTitle());
        if (myHomePlan.get(position).getAddress() != null && myHomePlan.get(position).getAddress() != "") {
            vh.lineAddress.setVisibility(View.VISIBLE);
            vh.location.setText(myHomePlan.get(position).getAddress());
        } else {
            vh.lineAddress.setVisibility(View.GONE);
        }
        vh.shareNum.setText(myHomePlan.get(position).getShareNum() + "");
        vh.commentNum.setText(myHomePlan.get(position).getCommentNum() + "");
        vh.zanNum.setText(myHomePlan.get(position).getLikeNum() + "");
        //判断是否有关联的项目计划
        if (myHomePlan.get(position).getProjectPlan() != null && myHomePlan.get(position).getProjectPlan() != "") {
            String str = "#" + myHomePlan.get(position).getProjectPlan() + "#   " + myHomePlan.get(position).getDescription();
            int fstart = str.indexOf("#");
            int fend = str.lastIndexOf("#") + 1;
            SpannableStringBuilder style = new SpannableStringBuilder(str);
            style.setSpan(new ForegroundColorSpan(Color.rgb(237, 84, 82)), fstart, fend, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            vh.content.setText(style);
        } else {
            vh.content.setText(myHomePlan.get(position).getDescription());
        }
        //设置当前position为CheckBox的id
        vh.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onClick(position);
            }
        });
        //状态选中
        if (myHomePlan.get(position).isChecked()) {
            vh.isCheck.setChecked(true);
        } else {
            vh.isCheck.setChecked(false);
        }

        /**
         * 判断是否有日记
         */
        if (myHomePlan.get(position).getProductPlanDailyVo() != null) {
            vh.lineCover.setVisibility(View.GONE);
            vh.lineNote.setVisibility(View.VISIBLE);
            vh.oneImage.setVisibility(View.GONE);
            vh.framVideo.setVisibility(View.GONE);
            vh.lineThreeImg.setVisibility(View.GONE);
            vh.tvNoteTitle.setText(myHomePlan.get(position).getProductPlanDailyVo().getContent());
            vh.tvNoteAuthor.setText(hunterDiary);
            vh.tvNoteTime.setText(myHomePlan.get(position).getProductPlanDailyVo().getCreatedTime());
            vh.tvNoteContent.setText(myHomePlan.get(position).getProductPlanDailyVo().getContent());
            Glide.with(context).load(myHomePlan.get(position).getProductPlanDailyVo().getUrl()).error(R.mipmap.default_nor_avatar).into(vh.imgHead);
            switch (myHomePlan.get(position).getModelType()) {
                //日记里的视频 展示第一帧
                case NO4:
                    vh.imgNoteOne.setVisibility(View.GONE);
                    vh.framVideoNote.setVisibility(View.VISIBLE);
                    vh.lineNoteImage.setVisibility(View.GONE);
                    vh.imgNoteOne.setVisibility(View.GONE);
                    if (myHomePlan.get(position).getProductPlanDailyVo().getCurrencyAttachments() != null && myHomePlan.get(position).getProductPlanDailyVo().getCurrencyAttachments().size() > 0) {
                        Glide.with(context).load(myHomePlan.get(position).getProductPlanDailyVo().getCurrencyAttachments().get(0).getVideoCoverAddress()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.videoImgNote);
                    } else {
                        Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.videoImgNote);
                    }
                    break;
                //日记里的单图
                case NO5:
                    vh.imgNoteOne.setVisibility(View.VISIBLE);
                    vh.framVideoNote.setVisibility(View.GONE);
                    vh.lineNoteImage.setVisibility(View.GONE);
                    if (myHomePlan.get(position).getProductPlanDailyVo().getCurrencyAttachments() != null && myHomePlan.get(position).getProductPlanDailyVo().getCurrencyAttachments().size() > 0) {
                        Glide.with(context).load(myHomePlan.get(position).getProductPlanDailyVo().getCurrencyAttachments().get(0).getAddress()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.imgNoteOne);
                    } else {
                        Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.imgNoteOne);
                    }
                    break;
                //日记里的两图
                case NO6:
                    vh.imgNoteOne.setVisibility(View.GONE);
                    vh.framVideoNote.setVisibility(View.GONE);
                    vh.lineNoteImage.setVisibility(View.VISIBLE);
                    vh.imgNoteThree3.setVisibility(View.INVISIBLE);
                    if (myHomePlan.get(position).getProductPlanDailyVo().getCurrencyAttachments() != null && myHomePlan.get(position).getProductPlanDailyVo().getCurrencyAttachments().size() > 0) {
                        Glide.with(context).load(myHomePlan.get(position).getProductPlanDailyVo().getCurrencyAttachments().get(0).getAddress()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.imgNoteThree1);
                        Glide.with(context).load(myHomePlan.get(position).getProductPlanDailyVo().getCurrencyAttachments().get(1).getAddress()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.imgNoteThree2);
                    } else {
                        Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.imgNoteThree1);
                        Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.imgNoteThree2);
                    }

                    break;
                //日记里的三图
                case NO7:
                    vh.imgNoteOne.setVisibility(View.GONE);
                    vh.framVideoNote.setVisibility(View.GONE);
                    vh.lineNoteImage.setVisibility(View.VISIBLE);
                    vh.imgNoteThree3.setVisibility(View.VISIBLE);
                    if (myHomePlan.get(position).getProductPlanDailyVo().getCurrencyAttachments() != null && myHomePlan.get(position).getProductPlanDailyVo().getCurrencyAttachments().size() > 0) {
                        Glide.with(context).load(myHomePlan.get(position).getProductPlanDailyVo().getCurrencyAttachments().get(0).getAddress()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.imgNoteThree1);
                        Glide.with(context).load(myHomePlan.get(position).getProductPlanDailyVo().getCurrencyAttachments().get(1).getAddress()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.imgNoteThree2);
                        Glide.with(context).load(myHomePlan.get(position).getProductPlanDailyVo().getCurrencyAttachments().get(2).getAddress()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.imgNoteThree3);
                    } else {
                        Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.imgNoteThree1);
                        Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.imgNoteThree2);
                        Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.imgNoteThree3);
                    }
                    break;
                default:
                    vh.lineNote.setVisibility(View.GONE);
                    break;
            }
        } else {
            vh.lineNote.setVisibility(View.GONE);
        }
        if (myHomePlan.get(position).getCurrencyAttachmentVos() != null && myHomePlan.get(position).getCurrencyAttachmentVos().size() > 0) {
            /**
             * 判断有几个图片
             */
            switch (myHomePlan.get(position).getModelType()) {
                //封面图视频
                case 0:
                    vh.lineCover.setVisibility(View.VISIBLE);
                    vh.oneImage.setVisibility(View.GONE);
                    vh.framVideo.setVisibility(View.VISIBLE);
                    vh.lineNote.setVisibility(View.GONE);
                    vh.lineThreeImg.setVisibility(View.GONE);
                    if (myHomePlan.get(position).getCurrencyAttachmentVos() != null && myHomePlan.get(position).getCurrencyAttachmentVos().size() > 0) {
                        Glide.with(context).load(myHomePlan.get(position).getCurrencyAttachmentVos().get(0).getVideoCoverAddress()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.videoImg);
                    } else {
                        Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.videoImg);
                    }
                    break;
                //封面图单图
                case 1:
                    vh.lineCover.setVisibility(View.VISIBLE);
                    vh.oneImage.setVisibility(View.VISIBLE);
                    vh.framVideo.setVisibility(View.GONE);
                    vh.lineThreeImg.setVisibility(View.GONE);
                    vh.lineNote.setVisibility(View.GONE);
                    if (myHomePlan.get(position).getCurrencyAttachmentVos() != null && myHomePlan.get(position).getCurrencyAttachmentVos().size() > 0) {
                        Glide.with(context).load(myHomePlan.get(position).getCurrencyAttachmentVos().get(0).getAddress()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.oneImage);
                    } else {
                        Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.oneImage);
                    }
                    break;
                //封面图两图
                case 2:
                    vh.lineCover.setVisibility(View.VISIBLE);
                    vh.oneImage.setVisibility(View.GONE);
                    vh.framVideo.setVisibility(View.GONE);
                    vh.lineThreeImg.setVisibility(View.VISIBLE);
                    vh.threeImage3.setVisibility(View.INVISIBLE);
                    vh.lineNote.setVisibility(View.GONE);
                    if (myHomePlan.get(position).getCurrencyAttachmentVos() != null && myHomePlan.get(position).getCurrencyAttachmentVos().size() > 0) {
                        Glide.with(context).load(myHomePlan.get(position).getCurrencyAttachmentVos().get(0).getAddress()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.threeImage1);
                        Glide.with(context).load(myHomePlan.get(position).getCurrencyAttachmentVos().get(1).getAddress()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.threeImage2);
                    } else {
                        Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.threeImage1);
                        Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.threeImage2);
                    }
                    break;
                //封面图三图
                case 3:
                    vh.lineCover.setVisibility(View.VISIBLE);
                    vh.oneImage.setVisibility(View.GONE);
                    vh.framVideo.setVisibility(View.GONE);
                    vh.lineThreeImg.setVisibility(View.VISIBLE);
                    vh.threeImage3.setVisibility(View.VISIBLE);
                    vh.lineNote.setVisibility(View.GONE);
                    if (myHomePlan.get(position).getCurrencyAttachmentVos() != null && myHomePlan.get(position).getCurrencyAttachmentVos().size() > 0) {
                        Glide.with(context).load(myHomePlan.get(position).getCurrencyAttachmentVos().get(0).getAddress()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.threeImage1);
                        Glide.with(context).load(myHomePlan.get(position).getCurrencyAttachmentVos().get(1).getAddress()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.threeImage2);
                        Glide.with(context).load(myHomePlan.get(position).getCurrencyAttachmentVos().get(2).getAddress()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.threeImage3);
                    } else {
                        Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.threeImage1);
                        Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.threeImage2);
                        Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.threeImage3);
                    }
                    break;
                default:
                    vh.lineCover.setVisibility(View.GONE);
                    vh.lineNote.setVisibility(View.VISIBLE);
                    break;
            }
        } else {
            vh.lineCover.setVisibility(View.GONE);
            vh.lineNote.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    class HomeProductPlanViewHolder {
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
        @BindView(R.id.line_product_plan_location)
        LinearLayout lineAddress;

        HomeProductPlanViewHolder(View view) {
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

    private HomeProductPlanAdapter.OnVpItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(HomeProductPlanAdapter.OnVpItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
