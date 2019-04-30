package com.yst.onecity.activity.mine.order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.utils.Utils;
import com.yst.im.imchatlibrary.bean.IntentChatEntity;
import com.yst.im.imchatlibrary.ui.activity.ChatScreenActivity;
import com.yst.im.imsdk.ChatType;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.adapter.ChooseImageAdapter;
import com.yst.onecity.bean.CardUnionOrderBean;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.ImageBean;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.order.OrderChildBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.AddImageOrVideo;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.view.ContainsEmojiEditText;
import com.yst.onecity.view.RatingBar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

import static com.yst.onecity.Constant.NO7;

/**
 * 订单评价商品
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/02/07
 */
public class EvaluateProductActivity extends BaseActivity implements AddImageOrVideo.UploadImgListener {
    private static final int REQUEST_IMAGE = 1;
    @BindView(R.id.ll_item_product)
    LinearLayout llItemProduct;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.et_comment)
    ContainsEmojiEditText etComment;
    @BindView(R.id.griView)
    GridView griView;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.iv_product)
    ImageView ivProduct;
    @BindView(R.id.tv_product_name)
    TextView tvProductName;
    @BindView(R.id.tv_product_price)
    TextView tvProductPrice;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.cb_anonymity)
    CheckBox cbAnonymity;
    private List<ImageBean> imgData = new ArrayList<>();
    private ChooseImageAdapter imageAdapter;
    private int ratingBarSize = 0;
    private OrderChildBean bean;
    private String userId;
    private String userName;
    private String userUrl;
    private CardUnionOrderBean.ContentBean.CardOrderVOListBean cardBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_evaluate_product;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initData() {
        initTitleBar("评价");
        if (Constant.isMember) {
            setRightText("联系团队");
        }
        etComment.setFilters(new InputFilter[]{new InputFilter.LengthFilter(120)});
        llItemProduct.setBackgroundColor(Color.WHITE);
        ratingBar.setStar(0);
        setGridViewAdapter();
        if (getIntent().getExtras() != null) {
            bean = (OrderChildBean) getIntent().getExtras().getSerializable("bean");
            cardBean = (CardUnionOrderBean.ContentBean.CardOrderVOListBean) getIntent().getExtras().getSerializable("cardBean");
            userId = getIntent().getStringExtra("userId");
            userName = getIntent().getStringExtra("userName");
            userUrl = getIntent().getStringExtra("userUrl");
            if (bean != null) {
                Glide.with(context).load(bean.getpImg()).into(ivProduct);
                tvProductName.setText(ConstUtils.getStringNoEmpty(bean.getName()));
            } else {
                Glide.with(context).load(cardBean.getImageAddress()).into(ivProduct);
                tvProductName.setText(ConstUtils.getStringNoEmpty(cardBean.getCardName()));
                tvCount.setText("x" + cardBean.getNum());
                tvProductPrice.setVisibility(View.VISIBLE);
                tvProductPrice.setText("¥" + cardBean.getPrice());
                tvDes.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * 添加图片GridView
     */
    private void setGridViewAdapter() {
        imageAdapter = new ChooseImageAdapter(imgData, context, 6);
        griView.setAdapter(imageAdapter);
        griView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (imgData.size() < NO7) {
                    ConstUtils.selectImg(EvaluateProductActivity.this, 6, imgData.size(), REQUEST_IMAGE);
                }
            }
        });
    }

    @Override
    public void setListener() {
        ratingBar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float ratingCount) {
                ratingBarSize = (int) ratingCount;
                checkTextEnable();
            }
        });
        etComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkTextEnable();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_IMAGE:
                ArrayList<String> urlList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                for (int i = 0; i < urlList.size(); i++) {
                    uploadImage(urlList.get(i));
                }
                break;
            default:
                break;
        }
    }

    private void uploadImage(String imagePath) {
        AddImageOrVideo.upLoadImg(this, imagePath, this);
    }

    @OnClick({R.id.tv_commit, R.id.tv_right})
    public void onViewClicked(View view) {
        if (!Utils.isClickable()) {
            return;
        }
        switch (view.getId()) {
            case R.id.tv_commit:
                addEvaluate();
                break;
            case R.id.tv_right:
                IntentChatEntity intentChatEntity = new IntentChatEntity();
                intentChatEntity.setAcceptName(userName);
                intentChatEntity.setAcceptId(userId);
                intentChatEntity.setChatType(ChatType.C2C);
                ChatScreenActivity.getJumpChatSource(this, intentChatEntity);
            default:
                break;
        }
    }

    /**
     * 发布评价
     */
    private void addEvaluate() {
        String content = TextUtils.isEmpty(etComment.getText().toString().trim()) ? " " : etComment.getText().toString().trim();
        // 是否匿名0是 1否
        int anonymity = cbAnonymity.isChecked() ? 0 : 1;
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < imgData.size(); i++) {
            str.append(imgData.get(i).getAddress() + ",");
        }
        String address = "";
        if (!TextUtils.isEmpty(str)) {
            address = str.substring(0, str.length() - 1);
        }
        if (bean != null) {
            RequestApi.addEvaluate(App.manager.getPhoneNum(), App.manager.getUuid(), content, ratingBarSize, bean.getoId(), anonymity, address, new AbstractNetWorkCallback<MsgBean>() {
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
                        EventBus.getDefault().post(new EventBean(Constant.NO1));
                        EvaluateProductActivity.this.finish();
                    }
                    ToastUtils.show(msgBean.getMsg());
                }

                @Override
                public void onError(String errorMsg) {
                    ToastUtils.show(errorMsg);
                }
            });
        } else {
            //卡联盟评价
            RequestApi.addCardEvaluate(App.manager.getPhoneNum(), App.manager.getUuid(), cardBean.getServiceOrderId(), String.valueOf(App.manager.getId()),
                    ratingBarSize, content, anonymity, TextUtils.isEmpty(address) ? 0 : 1, address, new AbstractNetWorkCallback<MsgBean>() {
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
                                EvaluateProductActivity.this.finish();
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

    /**
     * 判断确认按钮颜色和是否可点击
     */
    private void checkTextEnable() {
        if (ratingBarSize > 0) {
            tvCommit.setBackgroundColor(0xFFED5452);
            tvCommit.setEnabled(true);
        } else {
            tvCommit.setBackgroundColor(0xFFEE8382);
            tvCommit.setEnabled(false);
        }
    }

    @Override
    public void uploadImgListener(String msg) {
        imgData.add(new ImageBean(msg));
        imageAdapter.notifyDataSetChanged();
        checkTextEnable();
    }
}
