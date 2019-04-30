package com.yst.onecity.activity.mine.order;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.utils.Utils;
import com.yst.im.imchatlibrary.bean.IntentChatEntity;
import com.yst.im.imchatlibrary.ui.activity.ChatScreenActivity;
import com.yst.im.imsdk.ChatType;
import com.yst.onecity.R;
import com.yst.onecity.adapter.ServiceEvaluateImageAdapter;
import com.yst.onecity.bean.ConsultItemBean;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.order.OrderGroupBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.AddImageOrVideo;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.view.RatingBar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

import static com.yst.onecity.Constant.IMG_FORMAT;
import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO100;
import static com.yst.onecity.Constant.NO200;
import static com.yst.onecity.Constant.NO6;

/**
 * 订单-服务订单-评价(买家)
 *
 * @author songbinbin
 * @version 1.1.0
 * @date 2018/5/21
 */
public class ServiceOrderEvaluateActivity extends BaseActivity {

    @BindView(R.id.service_order_evaluate_iv_head)
    ImageView ivHead;
    @BindView(R.id.service_order_evaluate_tv_service_name)
    TextView tvName;
    @BindView(R.id.service_order_evaluate_tv_price)
    TextView tvPrice;
    @BindView(R.id.service_order_evaluate_tv_number)
    TextView tvNumber;
    @BindView(R.id.service_order_evaluate_ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.service_order_evaluate_et_comment)
    EditText etComment;
    @BindView(R.id.service_order_evaluate_griView)
    GridView gridView;
    @BindView(R.id.service_order_evaluate_cb_anonymity)
    CheckBox cbAnonymity;
    @BindView(R.id.service_order_evaluate_tv_commit)
    TextView tvCommit;

    /**
     * 评价星级
     */
    private int ratingBarSize = 0;
    private ServiceEvaluateImageAdapter serviceEvaluateImageAdapter;
    /**
     * 评论图片
     */
    ArrayList<ConsultItemBean> imgList = new ArrayList<>();
    /**
     * 替换图片位置
     */
    int replacePosition = -1;
    /**
     * true：替换 false：添加 图片
     */
    boolean reOrAdd = false;
    private OrderGroupBean groupBean;
    private String orderId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_service_order_evaluate;
    }

    @Override
    public void initData() {
        initTitleBar(getString(R.string.evaluate));
        setRightText(getString(R.string.contact_teams));
        setRightTextViewClickable(true);
        initAdapter();
        ratingBar.setStar(0);
        Intent intent = getIntent();
        groupBean = (OrderGroupBean) intent.getSerializableExtra("groupBean");
        orderId = groupBean.getId();
        setData();
    }

    /**
     * 设置数据
     */
    public void setData(){
        Glide.with(context).load(groupBean.getSon().get(0).getAddress()).placeholder(R.mipmap.ic_logo).error(R.mipmap.ic_logo).into(ivHead);

        tvName.setText(groupBean.getSon().get(0).getName());
        tvPrice.setText("￥" + groupBean.getSon().get(0).getSPrice());
        String num = Integer.toString(groupBean.getSon().get(0).getNum());
        tvNumber.setText("x" + num);
    }

    /**
     * 初始化评价图片适配器
     */
    public void initAdapter() {
        serviceEvaluateImageAdapter = new ServiceEvaluateImageAdapter(imgList, ServiceOrderEvaluateActivity.this);
        gridView.setAdapter(serviceEvaluateImageAdapter);
        if (serviceEvaluateImageAdapter != null) {
            serviceEvaluateImageAdapter.setIScrollPosition(new ServiceEvaluateImageAdapter.IGetScrollPosition() {
                @Override
                public void click(int i, int event) {
                    //删除
                    if(event == NO0) {
                        imgList.remove(i);
                        //替换图片
                    }else if(event == NO1){
                        if (!Utils.isClickable()) {
                            return;
                        }
                        replacePosition = i;
                        int num;
                        if(replacePosition <= imgList.size()-1){
                            num = NO1;
                            reOrAdd = true;
                        }else {
                            num = NO6 - imgList.size();
                            reOrAdd = false;
                        }
                        //权限判断
                        if (ContextCompat.checkSelfPermission(ServiceOrderEvaluateActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            //申请READ_EXTERNAL_STORAGE权限
                            ActivityCompat.requestPermissions(ServiceOrderEvaluateActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, NO200);
                        } else {
                            MultiImageSelector
                                    .create()
                                    .multi()
                                    .count(num)
                                    .start(ServiceOrderEvaluateActivity.this, NO100);
                        }
                    }
                    serviceEvaluateImageAdapter.notifyDataSetChanged();
                }
            });
        }
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
                ChatScreenActivity.getJumpChatSource(ServiceOrderEvaluateActivity.this, intentChatEntity);
            }
        });

        ratingBar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float ratingCount) {
                ratingBarSize = (int) ratingCount;
                checkTextEnable();
            }
        });

        tvCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commitEvaluate();
            }
        });
    }

    /**
     * 提交评价
     */
    public void commitEvaluate(){
        String content = etComment.getText().toString().trim();
        String anonymity;
        if(cbAnonymity.isChecked()){
            anonymity = "0";
        }else{
            anonymity = "1";
        }
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i < imgList.size(); i++) {
            if (i > 0){
                sb.append(",");
            }
            sb.append(imgList.get(i).getAddress());
        }
        String imgAddress = sb.toString();
        RequestApi.evaluateServiceOrder(App.manager.getUuid(),App.manager.getPhoneNum(),App.manager.getId(), orderId, content
                ,Integer.toString(ratingBarSize), anonymity, imgAddress, new AbstractNetWorkCallback<MsgBean>() {

                    @Override
                    public void onSuccess(MsgBean msgBean) {
                        ToastUtils.show(msgBean.getMsg());
                        if(msgBean.getCode() == NO1){
                            EventBus.getDefault().post(new EventBean("MyServiceOrderFragment"));
                            ServiceOrderEvaluateActivity.this.finish();
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

    /**
     * 上传图片
     */
    public void uploadPic(List<String> imgPathList){
        for (int i=0;i<imgPathList.size();i++) {
            AddImageOrVideo.upLoadImg(ServiceOrderEvaluateActivity.this, imgPathList.get(i), new AddImageOrVideo.UploadImgListener() {
                @Override
                public void uploadImgListener(String msg) {
                    if (msg.endsWith(IMG_FORMAT)) {
                        ConsultItemBean consultItemBean = new ConsultItemBean();
                        consultItemBean.setmPhotoPath(msg);
                        //替换
                        if(reOrAdd) {
                            if(replacePosition < 0){
                                return;
                            }
                            imgList.set(replacePosition,consultItemBean);
                            //添加
                        }else{
                            imgList.add(consultItemBean);
                        }
                        serviceEvaluateImageAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtils.show(msg);
                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        //封面图
        if (requestCode == NO100) {
            // 获取返回的图片列表(存放的是图片路径)
            ArrayList<String> stringArrayListExtra = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
            uploadPic(stringArrayListExtra);
        }
    }
}
