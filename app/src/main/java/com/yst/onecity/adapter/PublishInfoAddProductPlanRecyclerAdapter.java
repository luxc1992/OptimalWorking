package com.yst.onecity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.bean.home.PublishInfoAddProductPlanBean;

import java.util.List;

/**
 * 发布资讯-添加产品计划RecyclerView适配器
 *
 * @author songbinbin
 * @version 1.0.1
 * @date 2018/2/27
 */
public class PublishInfoAddProductPlanRecyclerAdapter extends RecyclerView.Adapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<PublishInfoAddProductPlanBean.ContentBean> list;

    private static final int IMG1 = 1;
    private static final int IMG2 = 2;
    private static final int IMG3 = 3;
    private static final int VIDEO = 4;

    public PublishInfoAddProductPlanRecyclerAdapter(Context context, List<PublishInfoAddProductPlanBean.ContentBean> list) {
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    /**
     * 设置适配器数据
     *
     * @param isAdd
     * @param bean
     */
    public void setDate(boolean isAdd, List<PublishInfoAddProductPlanBean.ContentBean> bean) {
        if (isAdd) {
            list.addAll(bean);
        } else {
            if (this.list != null) {
                this.list.clear();
            }
            this.list = bean;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int num = list.get(viewType).getCommentNum();
        switch (num) {

            case IMG1:
                HolderOne one = new HolderOne(layoutInflater.inflate(R.layout.item_publish_info_add_product_one, parent, false));
                return one;
            case IMG2:
                HolderTwo two = new HolderTwo(layoutInflater.inflate(R.layout.item_publish_info_add_product_two, parent, false));
                return two;
            case IMG3:
                HolderThree three = new HolderThree(layoutInflater.inflate(R.layout.item_publish_info_add_product_three, parent, false));
                return three;
            case VIDEO:
                HolderFour four = new HolderFour(layoutInflater.inflate(R.layout.item_publish_info_add_product_four, parent, false));
                return four;
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    /**
     * 单图
     */
    class HolderOne extends RecyclerView.ViewHolder implements View.OnClickListener {
        /**
         * 编号，标题，时间，地址，描述，分享，评论，点赞
         */
        TextView tvNumber, tvTitle, tvTime, tvAddress, tvDescribe, tvShare, tvComment, tvLike;
        /**
         * 选中,封面
         */
        ImageView ivImg;
        CheckBox ivCheck;

        public HolderOne(View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.item_one_publish_add_product_tv_number);
            tvTitle = itemView.findViewById(R.id.item_one_publish_add_product_tv_title);
            tvTime = itemView.findViewById(R.id.item_one_publish_add_product_tv_time);
            tvAddress = itemView.findViewById(R.id.item_one_publish_add_product_tv_address);
            tvDescribe = itemView.findViewById(R.id.item_one_publish_add_product_tv_describe);
            tvShare = itemView.findViewById(R.id.item_one_publish_add_product_tv_share_num);
            tvComment = itemView.findViewById(R.id.item_one_publish_add_product_tv_comment_num);
            tvLike = itemView.findViewById(R.id.item_one_publish_add_product_tv_like_num);
            ivCheck = itemView.findViewById(R.id.item_one_publish_add_product_cb_check);
            ivImg = itemView.findViewById(R.id.item_one_publish_add_product_iv_img);

            /*注册条目点击事件*/
            ivCheck.setOnClickListener(HolderOne.this);
        }

        @Override
        public void onClick(View v) {
            //注意这里使用getAdapterPosition方法获取数据
            int position = getAdapterPosition();
            if (mOnItemClickListener != null) {
                if (R.id.item_one_publish_add_product_cb_check == v.getId()) {
                    mOnItemClickListener.onClick(ivCheck, position);
                }
            }
        }
    }

    /**
     * 单图布局数据配置
     *
     * @param holder
     * @param position
     */
  /**  private void ONE(HolderOne holder, int position) throws IOException {
        holder.tvNumber.setText(list.get(position).getLikeNum() + "");
        holder.tvTitle.setText(list.get(position).getTitle());
        holder.tvTime.setText(list.get(position).getUpdateTime());
        holder.tvAddress.setText(list.get(position).getAddress());
        holder.tvDescribe.setText(list.get(position).getDescription());
        holder.tvComment.setText(list.get(position).getCommentNum() + "");
        holder.tvLike.setText(list.get(position).getLikeNum() + "");
        holder.tvShare.setText(list.get(position).getShareNum() + "");
        holder.ivCheck.setChecked(list.get(position).isChecked);
        Glide.with(context).load(list.get(0).getImg().get(0).getImg()).into(holder.ivImg);
    }
*/
    /**
     * 双图
     */
    class HolderTwo extends RecyclerView.ViewHolder implements View.OnClickListener {
        /**
         * 编号，标题，时间，地址，描述，分享，评论，点赞
         */
        TextView tvNumber, tvTitle, tvTime, tvAddress, tvDescribe, tvShare, tvComment, tvLike;
        /**
         * 选中,封面
         */
        ImageView ivImg, ivImg2;
        CheckBox ivCheck;

        public HolderTwo(View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.item_two_publish_add_product_tv_number);
            tvTitle = itemView.findViewById(R.id.item_two_publish_add_product_tv_title);
            tvTime = itemView.findViewById(R.id.item_two_publish_add_product_tv_time);
            tvAddress = itemView.findViewById(R.id.item_two_publish_add_product_tv_address);
            tvDescribe = itemView.findViewById(R.id.item_two_publish_add_product_tv_describe);
            tvShare = itemView.findViewById(R.id.item_two_publish_add_product_tv_share_num);
            tvComment = itemView.findViewById(R.id.item_two_publish_add_product_tv_comment_num);
            tvLike = itemView.findViewById(R.id.item_two_publish_add_product_tv_like_num);
            ivCheck = itemView.findViewById(R.id.item_two_publish_add_product_cb_check);
            ivImg = itemView.findViewById(R.id.item_two_publish_add_product_iv_img1);
            ivImg2 = itemView.findViewById(R.id.item_two_publish_add_product_iv_img2);

            /*注册条目点击事件*/
            ivCheck.setOnClickListener(HolderTwo.this);
        }

        @Override
        public void onClick(View v) {
            //注意这里使用getAdapterPosition方法获取数据
            int position = getAdapterPosition();
            if (mOnItemClickListener != null) {
                if (R.id.item_two_publish_add_product_cb_check == v.getId()) {
                    mOnItemClickListener.onClick(ivCheck, position);
                }
            }
        }
    }

    /**
     * 双图布局数据配置
     *
     * @param holder
     * @param position
     */
  /**  private void TWO(HolderTwo holder, int position) throws IOException {
        holder.tvNumber.setText(list.get(position).getLikeNum() + "");
        holder.tvTitle.setText(list.get(position).getTitle());
        holder.tvTime.setText(list.get(position).getUpdateTime());
        holder.tvAddress.setText(list.get(position).getAddress());
        holder.tvDescribe.setText(list.get(position).getDescription());
        holder.tvComment.setText(list.get(position).getCommentNum() + "");
        holder.tvLike.setText(list.get(position).getLikeNum() + "");
        holder.tvShare.setText(list.get(position).getShareNum() + "");
        holder.ivCheck.setChecked(list.get(position).isChecked);
        Glide.with(context).load(list.get(0).getImg().get(0).getImg()).into(holder.ivImg);
        Glide.with(context).load(list.get(0).getImg().get(1).getImg()).into(holder.ivImg2);
    }
*/
    /**
     * 三图
     */
    class HolderThree extends RecyclerView.ViewHolder implements View.OnClickListener {
        /**
         * 编号，标题，时间，地址，描述，分享，评论，点赞
         */
        TextView tvNumber, tvTitle, tvTime, tvAddress, tvDescribe, tvShare, tvComment, tvLike;
        /**
         * 选中,封面
         */
        ImageView ivImg, ivImg2, ivImg3;
        CheckBox ivCheck;
        public HolderThree(View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.item_three_publish_add_product_tv_number);
            tvTitle = itemView.findViewById(R.id.item_three_publish_add_product_tv_title);
            tvTime = itemView.findViewById(R.id.item_three_publish_add_product_tv_time);
            tvAddress = itemView.findViewById(R.id.item_three_publish_add_product_tv_address);
            tvDescribe = itemView.findViewById(R.id.item_three_publish_add_product_tv_describe);
            tvShare = itemView.findViewById(R.id.item_three_publish_add_product_tv_share_num);
            tvComment = itemView.findViewById(R.id.item_three_publish_add_product_tv_comment_num);
            tvLike = itemView.findViewById(R.id.item_three_publish_add_product_tv_like_num);
            ivCheck = itemView.findViewById(R.id.item_three_publish_add_product_cb_check);
            ivImg = itemView.findViewById(R.id.item_three_publish_add_product_iv_img1);
            ivImg2 = itemView.findViewById(R.id.item_three_publish_add_product_iv_img2);
            ivImg3 = itemView.findViewById(R.id.item_three_publish_add_product_iv_img3);

            /*注册条目点击事件*/
            ivCheck.setOnClickListener(HolderThree.this);
        }

        @Override
        public void onClick(View v) {
            //注意这里使用getAdapterPosition方法获取数据
            int position = getAdapterPosition();
            if (mOnItemClickListener != null) {
                if (R.id.item_three_publish_add_product_cb_check == v.getId()) {
                    mOnItemClickListener.onClick(ivCheck, position);
                }
            }
        }
    }

    /**
     * 三图布局数据配置
     *
     * @param holder
     * @param position
     */
 /**   private void THREE(HolderThree holder, int position) throws IOException {
        holder.tvNumber.setText(list.get(position).getLikeNum() + "");
        holder.tvTitle.setText(list.get(position).getTitle());
        holder.tvTime.setText(list.get(position).getUpdateTime());
        holder.tvAddress.setText(list.get(position).getAddress());
        holder.tvDescribe.setText(list.get(position).getDescription());
        holder.tvComment.setText(list.get(position).getCommentNum() + "");
        holder.tvLike.setText(list.get(position).getLikeNum() + "");
        holder.tvShare.setText(list.get(position).getShareNum() + "");
        holder.ivCheck.setChecked(list.get(position).isChecked);
        Glide.with(context).load(list.get(0).getImg().get(0).getImg()).into(holder.ivImg);
        Glide.with(context).load(list.get(0).getImg().get(1).getImg()).into(holder.ivImg2);
        Glide.with(context).load(list.get(0).getImg().get(2).getImg()).into(holder.ivImg3);
    }
*/
    /**
     * 视频
     */
    class HolderFour extends RecyclerView.ViewHolder implements View.OnClickListener {
        /**
         * 编号，标题，时间，地址，描述，分享，评论，点赞
         */
        TextView tvNumber, tvTitle, tvTime, tvAddress, tvDescribe, tvShare, tvComment, tvLike;
        /**
         * 选中,封面
         */
        CheckBox ivCheck;
        SurfaceView surfaceView;

        public HolderFour(View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.item_four_publish_add_product_tv_number);
            tvTitle = itemView.findViewById(R.id.item_four_publish_add_product_tv_title);
            tvTime = itemView.findViewById(R.id.item_four_publish_add_product_tv_time);
            tvAddress = itemView.findViewById(R.id.item_four_publish_add_product_tv_address);
            tvDescribe = itemView.findViewById(R.id.item_four_publish_add_product_tv_describe);
            tvShare = itemView.findViewById(R.id.item_four_publish_add_product_tv_share_num);
            tvComment = itemView.findViewById(R.id.item_four_publish_add_product_tv_comment_num);
            tvLike = itemView.findViewById(R.id.item_four_publish_add_product_tv_like_num);
            ivCheck = itemView.findViewById(R.id.item_four_publish_add_product_cb_check);
            surfaceView = itemView.findViewById(R.id.item_four_publish_add_product_iv_sf);

            /*注册条目点击事件*/
            ivCheck.setOnClickListener(HolderFour.this);
        }

        @Override
        public void onClick(View v) {
            //注意这里使用getAdapterPosition方法获取数据
            int position = getAdapterPosition();
            if (mOnItemClickListener != null) {
                if (R.id.item_four_publish_add_product_cb_check == v.getId()) {
                    mOnItemClickListener.onClick(ivCheck, position);
                }
            }
        }
    }

    /**
     * 视频布局数据配置
     *
     * @param holder
     * @param position
     */
   /** private void FOUR(HolderFour holder, int position) throws IOException {
        holder.tvNumber.setText(list.get(position).getLikeNum() + "");
        holder.tvTitle.setText(list.get(position).getTitle());
        holder.tvTime.setText(list.get(position).getUpdateTime());
        holder.tvAddress.setText(list.get(position).getAddress());
        holder.tvDescribe.setText(list.get(position).getDescription());
        holder.tvComment.setText(list.get(position).getCommentNum() + "");
        holder.tvLike.setText(list.get(position).getLikeNum() + "");
        holder.tvShare.setText(list.get(position).getShareNum() + "");
        holder.ivCheck.setChecked(list.get(position).isChecked);
    }
*/
    /**
     * 由于RecyclerView不再负责Item视图的布局及显示，所以RecyclerView也没有为Item开放OnItemClick等点击事件
     * 所以自定义实现item以及item内布控件的点击事件
     * <p>
     * 设置RecyclerView条目点击监听
     */
    public interface OnRecyclerViewItemClickListener {
        /**
         * RecyclerView条目点击监听OnClick
         *
         * @param view
         * @param position
         */
        void onClick(View view, int position);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
