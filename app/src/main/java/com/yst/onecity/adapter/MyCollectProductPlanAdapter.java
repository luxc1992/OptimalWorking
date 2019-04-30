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
import com.yst.onecity.activity.mine.ProductPlanDetailActivity;
import com.yst.onecity.bean.mine.MyCollectProductPlan;
import com.yst.onecity.view.SpacesItemDecoration;

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
 * 我的收藏-产品计划item  Adapter
 *
 * @author liumanqing
 * @version 1.0.1
 * @date 2018/3/20
 */

public class MyCollectProductPlanAdapter extends BaseAdapter {
    private List<MyCollectProductPlan.ContentBean> myCollectProductPlen;
    private List<MyCollectProductPlan.ContentBean.ProductVOListBean> myCollectGood;
    private View.OnClickListener onClickListener;
    private Activity context;
    private ProductPlanRecyclerViewAdapter adapter;
    private LinearLayoutManager layoutManager;
    private boolean isAdd = true;

    public MyCollectProductPlanAdapter(List<MyCollectProductPlan.ContentBean> myCollectProductPlen, View.OnClickListener onClickListener, Activity context) {
        this.myCollectProductPlen = myCollectProductPlen;
        this.onClickListener = onClickListener;
        this.context = context;
    }

    public void setMyCollectData(List<MyCollectProductPlan.ContentBean> myCollectProductPlen) {
        if (myCollectProductPlen != null) {
            this.myCollectProductPlen = myCollectProductPlen;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return myCollectProductPlen == null ? 0 : myCollectProductPlen.size();
    }

    @Override
    public Object getItem(int i) {
        return myCollectProductPlen.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyCollectProductPlanViewHolder vh = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_product_plan, null);
            vh = new MyCollectProductPlanViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (MyCollectProductPlanViewHolder) convertView.getTag();
        }
        vh.lineCollect.setVisibility(View.VISIBLE);
        vh.lineIssue.setVisibility(View.GONE);
        vh.lineFail.setVisibility(View.GONE);
        vh.imgProductNum.setVisibility(View.VISIBLE);
        vh.lineNum.setVisibility(View.VISIBLE);
        vh.lineComplieDelete.setVisibility(View.VISIBLE);
        vh.lineTime.setVisibility(View.VISIBLE);
        vh.lineRecy.setVisibility(View.GONE);
        vh.time.setText(myCollectProductPlen.get(position).getUpdateTime());
        vh.productTitle.setText(myCollectProductPlen.get(position).getTitle());
        //展示项目计划
        if (myCollectProductPlen.get(position).getProjectName() != null&&myCollectProductPlen.get(position).getProjectName()!="") {
            String str = "#" + myCollectProductPlen.get(position).getProjectName() + "#   " + myCollectProductPlen.get(position).getDescription();
            int fstart = str.indexOf("#");
            int fend = str.lastIndexOf("#") + 1;
            SpannableStringBuilder style = new SpannableStringBuilder(str);
            style.setSpan(new ForegroundColorSpan(Color.rgb(237, 84, 82)), fstart, fend, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            vh.content.setText(style);
        } else {
            vh.content.setText(myCollectProductPlen.get(position).getDescription());
        }
        //设置值
        if (myCollectProductPlen.get(position).getAddress() != null && myCollectProductPlen.get(position).getAddress() != "") {
            vh.lineLocation.setVisibility(View.VISIBLE);
            vh.location.setText(myCollectProductPlen.get(position).getAddress());
        } else {
            vh.lineLocation.setVisibility(View.INVISIBLE);
        }
        vh.shareNum.setText(myCollectProductPlen.get(position).getShareNum() + "");
        vh.commentNum.setText(myCollectProductPlen.get(position).getCommentNum() + "");
        vh.zanNum.setText(myCollectProductPlen.get(position).getLikeNum() + "");
        //点击条目跳转到产品计划详情
        vh.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("id", myCollectProductPlen.get(position).getId() + "");
                JumpIntent.jump(context, ProductPlanDetailActivity.class, b);
            }
        });

        /**
         * 判断我的收藏里是否有商品
         */
        if (myCollectProductPlen.get(position) != null && myCollectProductPlen.get(position).getProductVOList() != null && myCollectProductPlen.get(position).getProductVOList().size() > 0) {
            vh.lineRecy.setVisibility(View.VISIBLE);
            myCollectGood = myCollectProductPlen.get(position).getProductVOList();
            adapter = new ProductPlanRecyclerViewAdapter(context, null, myCollectGood, null);
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
            //点击商品跳转到产品计划详情
            adapter.setOnItemClickListener(new ProductPlanRecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view) {
                    Bundle b = new Bundle();
                    b.putString("id", myCollectProductPlen.get(position).getId() + "");
                    JumpIntent.jump(context, ProductPlanDetailActivity.class, b);
                }
            });
        } else {
            vh.lineRecy.setVisibility(View.GONE);
        }
        /**
         * 判断我的收藏里是否有日记
         */
        if (myCollectProductPlen.get(position).getDaily() != null) {
            vh.lineCover.setVisibility(View.GONE);
            vh.lineNote.setVisibility(View.VISIBLE);
            vh.oneImage.setVisibility(View.GONE);
            vh.framVideo.setVisibility(View.GONE);
            vh.lineThreeImg.setVisibility(View.GONE);
            vh.tvNoteTitle.setText(myCollectProductPlen.get(position).getDaily().getNickname());
            vh.tvNoteAuthor.setText(hunterDiary);
            vh.tvNoteTime.setText(myCollectProductPlen.get(position).getDaily().getCreatedTime());
            vh.tvNoteContent.setText(myCollectProductPlen.get(position).getDaily().getContent());
            Glide.with(context).load(myCollectProductPlen.get(position).getDaily().getHeadImg()).error(R.mipmap.default_nor_avatar).into(vh.imgHead);
            int modelType = myCollectProductPlen.get(position).getType();
            switch (modelType) {
                //日记里的视频
                case NO4:
                    vh.imgNoteOne.setVisibility(View.GONE);
                    vh.framVideoNote.setVisibility(View.VISIBLE);
                    if (myCollectProductPlen.get(position).getDaily().getImages() != null && myCollectProductPlen.get(position).getDaily().getImages().size() > 0) {
                        Glide.with(context).load(myCollectProductPlen.get(position).getDaily().getImages().get(0).getVideo()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.videoImgNote);
                    } else {
                        Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.videoImgNote);
                    }
                    break;
                //日记里的单图
                case NO5:
                    vh.imgNoteOne.setVisibility(View.VISIBLE);
                    vh.framVideoNote.setVisibility(View.GONE);
                    if (myCollectProductPlen.get(position).getDaily().getImages() != null && myCollectProductPlen.get(position).getDaily().getImages().size() > 0) {
                        Glide.with(context).load(myCollectProductPlen.get(position).getDaily().getImages().get(0).getImg()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.imgNoteOne);
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
                    if (myCollectProductPlen.get(position).getDaily().getImages() != null && myCollectProductPlen.get(position).getDaily().getImages().size() > 0) {
                        Glide.with(context).load(myCollectProductPlen.get(position).getDaily().getImages().get(0).getImg()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.imgNoteThree1);
                        Glide.with(context).load(myCollectProductPlen.get(position).getDaily().getImages().get(1).getImg()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.imgNoteThree2);
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
                    if (myCollectProductPlen.get(position).getDaily().getImages() != null && myCollectProductPlen.get(position).getDaily().getImages().size() > 0) {
                        Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.imgNoteThree1);
                        Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.imgNoteThree2);
                        Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.imgNoteThree3);
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
            //隐藏日记
            vh.lineNote.setVisibility(View.GONE);
        }
        /**
         * 判断有几个图片
         */
        int modelType = myCollectProductPlen.get(position).getType();
        switch (modelType) {
            //封面图-视频
            case NO0:
                vh.lineCover.setVisibility(View.VISIBLE);
                vh.oneImage.setVisibility(View.GONE);
                vh.framVideo.setVisibility(View.VISIBLE);
                vh.lineNote.setVisibility(View.GONE);
                vh.lineThreeImg.setVisibility(View.GONE);
                if (myCollectProductPlen.get(position).getImg() != null && myCollectProductPlen.get(position).getImg().size() > 0) {
                    Glide.with(context).load(myCollectProductPlen.get(position).getImg().get(0).getVideo()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.videoImg);
                } else {
                    Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.videoImg);
                }
                break;
            //封面图-单图
            case NO1:
                vh.lineCover.setVisibility(View.VISIBLE);
                vh.oneImage.setVisibility(View.VISIBLE);
                vh.framVideo.setVisibility(View.GONE);
                vh.lineThreeImg.setVisibility(View.GONE);
                vh.lineNote.setVisibility(View.GONE);
                if (myCollectProductPlen.get(position).getImg() != null && myCollectProductPlen.get(position).getImg().size() > 0) {
                    Glide.with(context).load(myCollectProductPlen.get(position).getImg().get(0).getImg()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.oneImage);
                } else {
                    Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.oneImage);
                }
                break;
            //封面图-两图
            case NO2:
                vh.lineCover.setVisibility(View.VISIBLE);
                vh.oneImage.setVisibility(View.GONE);
                vh.framVideo.setVisibility(View.GONE);
                vh.lineThreeImg.setVisibility(View.VISIBLE);
                vh.threeImage3.setVisibility(View.INVISIBLE);
                vh.lineNote.setVisibility(View.GONE);
                if (myCollectProductPlen.get(position).getImg() != null && myCollectProductPlen.get(position).getImg().size() > 0) {
                    Glide.with(context).load(myCollectProductPlen.get(position).getImg().get(0).getImg()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.threeImage1);
                    Glide.with(context).load(myCollectProductPlen.get(position).getImg().get(1).getImg()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.threeImage2);
                } else {
                    Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.threeImage1);
                    Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.threeImage2);
                }
                break;
            //封面图-三图
            case NO3:
                vh.lineCover.setVisibility(View.VISIBLE);
                vh.oneImage.setVisibility(View.GONE);
                vh.framVideo.setVisibility(View.GONE);
                vh.lineThreeImg.setVisibility(View.VISIBLE);
                vh.threeImage3.setVisibility(View.VISIBLE);
                vh.lineNote.setVisibility(View.GONE);
                if (myCollectProductPlen.get(position).getImg() != null && myCollectProductPlen.get(position).getImg().size() > 0) {
                    Glide.with(context).load(myCollectProductPlen.get(position).getImg().get(0).getImg()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.threeImage1);
                    Glide.with(context).load(myCollectProductPlen.get(position).getImg().get(1).getImg()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.threeImage2);
                    Glide.with(context).load(myCollectProductPlen.get(position).getImg().get(2).getImg()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.threeImage3);
                } else {
                    Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.threeImage1);
                    Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.threeImage2);
                    Glide.with(context).load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(vh.threeImage3);
                }
                break;
            default:
                break;
        }
        //取消收藏的点击回调
        vh.lineCollect.setTag(myCollectProductPlen.get(position).getId() + "");
        vh.lineCollect.setOnClickListener(onClickListener);
        return convertView;
    }

    class MyCollectProductPlanViewHolder {
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
        LinearLayout lineLocation;

        MyCollectProductPlanViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
