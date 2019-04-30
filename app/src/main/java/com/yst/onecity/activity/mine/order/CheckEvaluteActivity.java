package com.yst.onecity.activity.mine.order;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.adapter.ShowImageAdapter;
import com.yst.onecity.bean.ImageBean;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.order.EvaluateBean;
import com.yst.onecity.bean.order.EvaluateContentBean;
import com.yst.onecity.bean.order.OrderChildBean;
import com.yst.onecity.bean.order.ProductBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.view.RatingBar;
import com.yst.onecity.view.dialog.SendCommentDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 订单-查看评价
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/02/24
 */
public class CheckEvaluteActivity extends BaseActivity implements SendCommentDialog.SendCommentListener {

    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.griView)
    GridView griView;
    @BindView(R.id.tv_reply)
    TextView tvReply;
    @BindView(R.id.iv_product)
    ImageView ivProduct;
    @BindView(R.id.tv_product_name)
    TextView tvProductName;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.tv_publish_name)
    TextView tvPublishName;
    @BindView(R.id.ll_item_product)
    LinearLayout llItemProduct;
    private String commentId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_check_evalute;
    }

    @Override
    public void initData() {
        initTitleBar("评价");
        llItemProduct.setBackgroundColor(Color.WHITE);
        tvCount.setVisibility(View.GONE);
        ratingBar.setClickable(false);
        if (Constant.isMember) {
            tvReply.setVisibility(View.GONE);
        }
        if (getIntent().getExtras() != null) {
            OrderChildBean bean = (OrderChildBean) getIntent().getExtras().getSerializable("bean");
            if (bean != null) {
                commentId = bean.getCommentId();
                getEvaluateContent();
            }
        }
    }

    /**
     * 获取商品评价内容
     */
    private void getEvaluateContent() {
        RequestApi.getProductEvaluateContent(App.manager.getPhoneNum(), App.manager.getUuid(), commentId, new AbstractNetWorkCallback<EvaluateContentBean>() {

            @Override
            public void onBefore() {
                super.onBefore();
                showInfoProgressDialog();
            }

            @Override
            public void onAfter() {
                super.onAfter();
                dismissInfoProgressDialog();
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(EvaluateContentBean bean) {
                if (bean.getCode() == 1) {
                    if (bean.getContent() != null) {
                        scrollView.setVisibility(View.VISIBLE);
                        EvaluateBean afterSalesReasonContentBean = bean.getContent();
                        ProductBean productBean = afterSalesReasonContentBean.getProduct();
                        tvProductName.setText(ConstUtils.getStringNoEmpty(productBean.getPName()));
                        Glide.with(context).load(productBean.getPImg()).error(R.mipmap.moren).into(ivProduct);
                        ratingBar.setStar(afterSalesReasonContentBean.getStartNum());
                        tvComment.setText(ConstUtils.getStringNoEmpty(afterSalesReasonContentBean.getContent()));
                        //匿名 0是1否
                        if (afterSalesReasonContentBean.getAnonymity() == 1) {
                            tvPublishName.setText("发布人：" + afterSalesReasonContentBean.getNickName());
                        }
                        initAdapter(afterSalesReasonContentBean.getImg());
                    }
                } else {
                    ToastUtils.show(bean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    public void initAdapter(List<ImageBean> imgData) {
        griView.setAdapter(new ShowImageAdapter(imgData, this));
    }

    @OnClick(R.id.tv_reply)
    public void onViewClicked() {
        SendCommentDialog dialog = new SendCommentDialog(this);
        dialog.commentListener(this);
        dialog.show();
    }

    @Override
    public void addComment(String content) {
        replyEvaluate(content);
    }

    /**
     * 回复评价
     *
     * @param content 回复内容
     */
    private void replyEvaluate(String content) {
        RequestApi.replyEvaluate(App.manager.getPhoneNum(), App.manager.getUuid(), content, commentId, new AbstractNetWorkCallback<MsgBean>() {
            @Override
            public void onBefore() {
                super.onBefore();
                showInfoProgressDialog();
            }

            @Override
            public void onAfter() {
                super.onAfter();
                dismissInfoProgressDialog();
            }

            @Override
            public void onSuccess(MsgBean msgBean) {
                if (msgBean.getCode() == 1) {
                    CheckEvaluteActivity.this.finish();
                }
                ToastUtils.show(msgBean.getMsg());
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }
}
