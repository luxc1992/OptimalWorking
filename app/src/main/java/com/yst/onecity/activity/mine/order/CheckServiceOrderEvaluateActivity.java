package com.yst.onecity.activity.mine.order;

import android.content.Intent;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.im.imchatlibrary.bean.IntentChatEntity;
import com.yst.im.imchatlibrary.ui.activity.ChatScreenActivity;
import com.yst.im.imsdk.ChatType;
import com.yst.onecity.R;
import com.yst.onecity.adapter.ServiceEvaluateDetailGridAdapter;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.order.OrderGroupBean;
import com.yst.onecity.bean.order.ServiceOrderEvaluateDetailBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.view.RatingBar;
import com.yst.onecity.view.dialog.SendCommentDialog;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yst.onecity.Constant.NO1;

/**
 * 查看服务评价详情
 *
 * @author songbinbin
 * @version 1.1.0
 * @date 2018/5/23
 */
public class CheckServiceOrderEvaluateActivity extends BaseActivity implements SendCommentDialog.SendCommentListener{

    @BindView(R.id.check_service_order_evaluate_iv_head)
    ImageView ivHead;
    @BindView(R.id.check_service_order_evaluate_tv_service_name)
    TextView tvServiceName;
    @BindView(R.id.check_service_order_evaluate_tv_price)
    TextView tvPrice;
    @BindView(R.id.check_service_order_evaluate_tv_number)
    TextView tvNumber;
    @BindView(R.id.check_service_order_evaluate_ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.check_service_order_evaluate_griView)
    GridView gridView;
    @BindView(R.id.check_service_order_evaluate_tv_comment)
    TextView tvComment;
    @BindView(R.id.check_service_order_evaluate_ll_name)
    LinearLayout llName;
    @BindView(R.id.check_service_order_evaluate_tv_name)
    TextView tvName;
    @BindView(R.id.check_service_order_evaluate_tv_commit)
    TextView tvCommit;
    private String orderId;
    /**
     * 评价id
     */
    private String commentId;
    private int from;
    private OrderGroupBean groupBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_check_service_order_evaluate;
    }

    @Override
    public void initData() {
        initTitleBar(getString(R.string.evaluate));
        setRightText(getString(R.string.contact_teams));
        setRightTextViewClickable(true);
        setRightTextVisibility(View.GONE);
        Intent intent = getIntent();
        groupBean = (OrderGroupBean) intent.getSerializableExtra("groupBean");
        orderId = groupBean.getId();
        from = intent.getIntExtra("from",1);
        getEvaluateDetail();
        ratingBar.setmClickable(false);
    }

    /**
     * 获取评价详情
     */
    public void getEvaluateDetail(){
        RequestApi.getServiceOrderEvaluateDetail(App.manager.getUuid(), App.manager.getPhoneNum(), orderId, new AbstractNetWorkCallback<ServiceOrderEvaluateDetailBean>() {
            @Override
            public void onSuccess(ServiceOrderEvaluateDetailBean bean) {
                if(null != bean) {
                    if (bean.getCode() == NO1) {
                        if(null != bean.getContent()) {
                            commentId = bean.getContent().getCommentId();
                            setData(bean);
                        }else {
                            ToastUtils.show("未查询到数据");
                        }
                    } else {
                        ToastUtils.show(bean.getMsg());
                    }
                }else {
                    ToastUtils.show("查询失败");
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }

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
        });
    }

    /**
     * 设置数据
     */
    public void setData(ServiceOrderEvaluateDetailBean bean){
        if(from == 0){
            tvCommit.setVisibility(View.GONE);
            setRightTextVisibility(View.VISIBLE);
        }else {
            tvCommit.setVisibility(View.VISIBLE);
            setRightTextVisibility(View.GONE);
        }
        Glide.with(this)
                .load(bean.getContent().getAddress())
                .placeholder(R.mipmap.ic_logo)
                .error(R.mipmap.ic_logo)
                .into(ivHead);
        tvServiceName.setText(bean.getContent().getTitle());
        tvPrice.setText("￥" + bean.getContent().getPrice());
        String num = Integer.toString(bean.getContent().getSalesNum());
        tvNumber.setText("x" + num);
        ratingBar.setStar(bean.getContent().getStartNum());
        tvComment.setText(bean.getContent().getContent());
        String imgAddress = ConstUtils.getStringNoEmpty(bean.getContent().getImageAddress());
        String[] imgList = imgAddress.split(",");

        ServiceEvaluateDetailGridAdapter adapter = new ServiceEvaluateDetailGridAdapter(imgList,this);
        gridView.setAdapter(adapter);
        if(bean.getContent().getAnonymity() == 0){
            tvName.setText("匿名");
        }else {
            tvName.setText(bean.getContent().getNickName());
        }
    }

    @OnClick(R.id.check_service_order_evaluate_tv_commit)
    public void onClick(View view){
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
        RequestApi.replyServiceOrderEvaluate(App.manager.getPhoneNum(), App.manager.getUuid(), App.manager.getId(), commentId, content, new AbstractNetWorkCallback<MsgBean>() {
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
                    CheckServiceOrderEvaluateActivity.this.finish();
                }
                ToastUtils.show(msgBean.getMsg());
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    @Override
    public void setListener() {
        //联系团队
        setRightTextViewOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentChatEntity intentChatEntity = new IntentChatEntity();
                intentChatEntity.setAcceptName(groupBean.getNickName());
                intentChatEntity.setAcceptId(groupBean.getUuid());
                intentChatEntity.setChatType(ChatType.C2C);
                ChatScreenActivity.getJumpChatSource(CheckServiceOrderEvaluateActivity.this, intentChatEntity);
            }
        });
    }
}
