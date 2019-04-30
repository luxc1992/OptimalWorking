package com.yst.onecity.activity.comment;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.MyLog;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.adapter.GoodsCommentAdapter;
import com.yst.onecity.adapter.ServiceCommentAdapter;
import com.yst.onecity.bean.GoodsCommentBean;
import com.yst.onecity.bean.ServiceCommentBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 评价管理页面
 *
 * @author wuxiaofang
 * @version 1.1.0
 * @date 2018/5/23
 */

public class CommentManageActivity extends BaseActivity implements View.OnClickListener, OnRefreshListener {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_service_comment)
    TextView tvServiceComment;
    @BindView(R.id.tv_goods_comment)
    TextView tvGoodsComment;
    @BindView(R.id.ll_comments)
    LinearLayout llComments;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.lv_comment)
    ListView lvComment;
    @BindView(R.id.iv_empty)
    ImageView ivEmpty;
    @BindView(R.id.comment_type)
    LinearLayout commentAllType;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    private boolean isService;
    private int commentType;
    private TextView tvGoodReplay;
    private TextView tvNoReplay;
    private TextView tvZhongchaReplay;
    private TextView tvTotalReplay;
    private ServiceCommentAdapter serviceCommentAdapter;
    private GoodsCommentAdapter goodsCommentAdapter;
    /**
     * 0 服务 1 商品
     */
    private int type;
    private String advisorId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_comment_manage;
    }

    @Override
    public void initData() {
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarDarkFont(false).statusBarColor(R.color.color_tab_select).init();
        tvGoodReplay = findViewById(R.id.tv_good_replay);
        tvNoReplay = findViewById(R.id.tv_no_replay);
        tvZhongchaReplay = findViewById(R.id.tv_zhongcha_replay);
        tvTotalReplay = findViewById(R.id.tv_total_replay);
        tvGoodReplay.setOnClickListener(this);
        tvNoReplay.setOnClickListener(this);
        tvZhongchaReplay.setOnClickListener(this);
        tvTotalReplay.setOnClickListener(this);
        serviceCommentAdapter = new ServiceCommentAdapter(this);
        goodsCommentAdapter = new GoodsCommentAdapter(this, advisorId);
        lvComment.setAdapter(serviceCommentAdapter);
        if (null != getIntent() && null != getIntent().getExtras()) {
            advisorId = getIntent().getExtras().getString("advisorId");
        }
        tvGoodReplay.setText("好评" + "\t" + "0");
        tvNoReplay.setText("未回复" + "\t" + "0");
        tvZhongchaReplay.setText("中差评" + "\t" + "0");
        tvTotalReplay.setText("全部" + "\t" + "0");
        getServiceList();
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setEnableLoadmore(false);
    }

    /**
     * 获取服务列表
     */
    private void getServiceList() {
        String type = String.valueOf(commentType);
        RequestApi.serviceComment(App.manager.getPhoneNum(), App.manager.getUuid(), type, new AbstractNetWorkCallback<ServiceCommentBean>() {
            @Override
            public void onBefore() {
                super.onBefore();
                showInfoProgressDialog();
            }

            @Override
            public void onSuccess(ServiceCommentBean serviceCommentBean) {
                if (null != serviceCommentBean) {
                    if (serviceCommentBean.getCode() == Constant.NO1 && null != serviceCommentBean.getContent()) {
                        ServiceCommentBean.ContentBean content = serviceCommentBean.getContent();
                        if (content.getCountAll() > 0) {
                            tvGoodReplay.setText("好评" + "\t" + content.getCountBest());
                            tvNoReplay.setText("未回复" + "\t" + content.getCountNo());
                            tvZhongchaReplay.setText("中差评" + "\t" + content.getCountLittle());
                            tvTotalReplay.setText("全部" + "\t" + content.getCountAll());
                            if (serviceCommentBean.getContent().getLista().size() > 0) {
                                setVisibleGone(true);
                                serviceCommentAdapter.addData(serviceCommentBean.getContent().getLista());
                            } else {
                                setVisibleGone(false);
                            }
                        } else {
                            tvGoodReplay.setText("好评" + "\t" + "0");
                            tvNoReplay.setText("未回复" + "\t" + "0");
                            tvZhongchaReplay.setText("中差评" + "\t" + "0");
                            tvTotalReplay.setText("全部" + "\t" + "0");
                            setVisibleGone(false);
                        }
                    } else {
                        tvGoodReplay.setText("好评" + "\t" + "0");
                        tvNoReplay.setText("未回复" + "\t" + "0");
                        tvZhongchaReplay.setText("中差评" + "\t" + "0");
                        tvTotalReplay.setText("全部" + "\t" + "0");
                        setVisibleGone(false);
                    }
                } else {
                    tvGoodReplay.setText("好评" + "\t" + "0");
                    tvNoReplay.setText("未回复" + "\t" + "0");
                    tvZhongchaReplay.setText("中差评" + "\t" + "0");
                    tvTotalReplay.setText("全部" + "\t" + "0");
                    setVisibleGone(false);
                    ToastUtils.show(serviceCommentBean.getMsg());
                }
            }

            @Override
            public void onAfter() {
                super.onAfter();
                onLoad();
                dismissInfoProgressDialog();
            }

            @Override
            public void onError(String errorMsg) {
                setVisibleGone(false);
                onLoad();
                ToastUtils.show(errorMsg);
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.tv_service_comment, R.id.tv_goods_comment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //返回
            case R.id.iv_back:
                if (!Utils.isClickable()) {
                    return;
                }
                finish();
                break;
            //服务评价和商品评价的切换
            case R.id.tv_service_comment:
                commentType = Constant.NO0;
                type = 0;
                MyLog.e("sss", "---服务：");
                llComments.setBackgroundResource(R.mipmap.left_white);
                tvServiceComment.setTextColor(ContextCompat.getColor(this, R.color.color_tab_select));
                tvGoodsComment.setTextColor(ContextCompat.getColor(this, R.color.white));
                lvComment.setAdapter(serviceCommentAdapter);
                tvTotalReplay.setBackgroundResource(R.drawable.shape_comment_select);
                tvZhongchaReplay.setBackgroundResource(R.drawable.shape_comment_normal);
                tvNoReplay.setBackgroundResource(R.drawable.shape_comment_normal);
                tvGoodReplay.setBackgroundResource(R.drawable.shape_comment_normal);
                tvTotalReplay.setTextColor(ContextCompat.getColor(this, R.color.white));
                tvGoodReplay.setTextColor(ContextCompat.getColor(this, R.color.black));
                tvNoReplay.setTextColor(ContextCompat.getColor(this, R.color.black));
                tvZhongchaReplay.setTextColor(ContextCompat.getColor(this, R.color.black));
                getServiceList();
                break;
            case R.id.tv_goods_comment:
                type = 1;
                commentType = Constant.NO0;
                tvTotalReplay.setBackgroundResource(R.drawable.shape_comment_select);
                tvZhongchaReplay.setBackgroundResource(R.drawable.shape_comment_normal);
                tvNoReplay.setBackgroundResource(R.drawable.shape_comment_normal);
                tvGoodReplay.setBackgroundResource(R.drawable.shape_comment_normal);
                tvTotalReplay.setTextColor(ContextCompat.getColor(this, R.color.white));
                tvGoodReplay.setTextColor(ContextCompat.getColor(this, R.color.black));
                tvNoReplay.setTextColor(ContextCompat.getColor(this, R.color.black));
                tvZhongchaReplay.setTextColor(ContextCompat.getColor(this, R.color.black));
                MyLog.e("sss", "---商品：");
                llComments.setBackgroundResource(R.mipmap.right_white);
                tvServiceComment.setTextColor(ContextCompat.getColor(this, R.color.white));
                tvGoodsComment.setTextColor(ContextCompat.getColor(this, R.color.color_tab_select));
                lvComment.setAdapter(goodsCommentAdapter);
                getGoodList();
                break;
            default:
                break;
        }
    }

    /**
     * 商品列表
     */
    private void getGoodList() {
        String type = String.valueOf(commentType);
        RequestApi.goodsComment(App.manager.getPhoneNum(), App.manager.getUuid(), type, new AbstractNetWorkCallback<GoodsCommentBean>() {

            @Override
            public void onBefore() {
                super.onBefore();
                showInfoProgressDialog();
            }

            @Override
            public void onSuccess(GoodsCommentBean bean) {
                if (null != bean) {
                    if (bean.getCode() == Constant.NO1 && null != bean.getContent()) {
                        GoodsCommentBean.ContentBean content = bean.getContent();
                        if (content.getCountAll() > 0) {
                            tvGoodReplay.setText("好评" + "\t" + content.getCountBest());
                            tvNoReplay.setText("未回复" + "\t" + content.getCountNo());
                            tvZhongchaReplay.setText("中差评" + "\t" + content.getCountLittle());
                            tvTotalReplay.setText("全部" + "\t" + content.getCountAll());
                            if (bean.getContent().getBigList().size() > 0) {
                                goodsCommentAdapter.addData(bean.getContent().getBigList());
                                setVisibleGone(true);
                            } else {
                                setVisibleGone(false);
                            }
                        } else {
                            tvGoodReplay.setText("好评" + "\t" + "0");
                            tvNoReplay.setText("未回复" + "\t" + "0");
                            tvZhongchaReplay.setText("中差评" + "\t" + "0");
                            tvTotalReplay.setText("全部" + "\t" + "0");
                            setVisibleGone(false);
                        }
                    } else {
                        tvGoodReplay.setText("好评" + "\t" + "0");
                        tvNoReplay.setText("未回复" + "\t" + "0");
                        tvZhongchaReplay.setText("中差评" + "\t" + "0");
                        tvTotalReplay.setText("全部" + "\t" + "0");
                        setVisibleGone(false);
                    }
                } else {
                    tvGoodReplay.setText("好评" + "\t" + "0");
                    tvNoReplay.setText("未回复" + "\t" + "0");
                    tvZhongchaReplay.setText("中差评" + "\t" + "0");
                    tvTotalReplay.setText("全部" + "\t" + "0");
                    setVisibleGone(false);
                }
            }

            @Override
            public void onAfter() {
                super.onAfter();
                onLoad();
                dismissInfoProgressDialog();
            }

            @Override
            public void onError(String errorMsg) {
                tvGoodReplay.setText("好评" + "\t" + "0");
                tvNoReplay.setText("未回复" + "\t" + "0");
                tvZhongchaReplay.setText("中差评" + "\t" + "0");
                tvTotalReplay.setText("全部" + "\t" + "0");
                ToastUtils.show(errorMsg);
                setVisibleGone(false);
                onLoad();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //未回复
            case R.id.tv_no_replay:
                commentType = Constant.NO1;
                tvNoReplay.setBackgroundResource(R.drawable.shape_comment_select);
                tvZhongchaReplay.setBackgroundResource(R.drawable.shape_comment_normal);
                tvGoodReplay.setBackgroundResource(R.drawable.shape_comment_normal);
                tvTotalReplay.setBackgroundResource(R.drawable.shape_comment_normal);
                tvNoReplay.setTextColor(ContextCompat.getColor(this, R.color.white));
                tvZhongchaReplay.setTextColor(ContextCompat.getColor(this, R.color.black));
                tvGoodReplay.setTextColor(ContextCompat.getColor(this, R.color.black));
                tvTotalReplay.setTextColor(ContextCompat.getColor(this, R.color.black));
                if (type == 0) {
                    getServiceList();
                } else {
                    getGoodList();
                }
                break;
            //中差评
            case R.id.tv_zhongcha_replay:
                commentType = Constant.NO2;
                tvZhongchaReplay.setBackgroundResource(R.drawable.shape_comment_select);
                tvNoReplay.setBackgroundResource(R.drawable.shape_comment_normal);
                tvGoodReplay.setBackgroundResource(R.drawable.shape_comment_normal);
                tvTotalReplay.setBackgroundResource(R.drawable.shape_comment_normal);
                tvZhongchaReplay.setTextColor(ContextCompat.getColor(this, R.color.white));
                tvNoReplay.setTextColor(ContextCompat.getColor(this, R.color.black));
                tvGoodReplay.setTextColor(ContextCompat.getColor(this, R.color.black));
                tvTotalReplay.setTextColor(ContextCompat.getColor(this, R.color.black));
                if (type == 0) {
                    getServiceList();
                } else {
                    getGoodList();
                }
                break;
            //好评
            case R.id.tv_good_replay:
                commentType = Constant.NO3;
                tvGoodReplay.setBackgroundResource(R.drawable.shape_comment_select);
                tvZhongchaReplay.setBackgroundResource(R.drawable.shape_comment_normal);
                tvNoReplay.setBackgroundResource(R.drawable.shape_comment_normal);
                tvTotalReplay.setBackgroundResource(R.drawable.shape_comment_normal);
                tvGoodReplay.setTextColor(ContextCompat.getColor(this, R.color.white));
                tvNoReplay.setTextColor(ContextCompat.getColor(this, R.color.black));
                tvZhongchaReplay.setTextColor(ContextCompat.getColor(this, R.color.black));
                tvTotalReplay.setTextColor(ContextCompat.getColor(this, R.color.black));
                if (type == 0) {
                    getServiceList();
                } else {
                    getGoodList();
                }
                break;
            //全部
            case R.id.tv_total_replay:
                commentType = Constant.NO0;
                tvTotalReplay.setBackgroundResource(R.drawable.shape_comment_select);
                tvZhongchaReplay.setBackgroundResource(R.drawable.shape_comment_normal);
                tvNoReplay.setBackgroundResource(R.drawable.shape_comment_normal);
                tvGoodReplay.setBackgroundResource(R.drawable.shape_comment_normal);
                tvTotalReplay.setTextColor(ContextCompat.getColor(this, R.color.white));
                tvNoReplay.setTextColor(ContextCompat.getColor(this, R.color.black));
                tvZhongchaReplay.setTextColor(ContextCompat.getColor(this, R.color.black));
                tvGoodReplay.setTextColor(ContextCompat.getColor(this, R.color.black));
                if (type == 0) {
                    getServiceList();
                } else {
                    getGoodList();
                }
                break;
            default:
                break;
        }
    }

    public void setVisibleGone(boolean flag) {
        if (flag) {
            lvComment.setVisibility(View.VISIBLE);
            ivEmpty.setVisibility(View.GONE);
        } else {
            lvComment.setVisibility(View.GONE);
            ivEmpty.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        if (type == 0) {
            getServiceList();
        } else {
            getGoodList();
        }
    }

    /**
     * 停止列表刷新的
     **/
    private void onLoad() {
        smartRefreshLayout.finishRefresh(500);
        smartRefreshLayout.finishLoadmore(500);
    }
}
