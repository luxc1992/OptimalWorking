package com.yst.onecity.activity.issue;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.activity.home.PublishInfoVideoActivity;
import com.yst.onecity.adapter.issue.IssueSpecificationAdapter;
import com.yst.onecity.bean.ConsultItemBean;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.ProductPlanBean;
import com.yst.onecity.bean.issue.DiaryBean;
import com.yst.onecity.bean.issue.EditProductBean;
import com.yst.onecity.bean.issue.PublishProductBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.AddImageOrVideo;
import com.yst.onecity.utils.ConstUtils;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

import static com.yst.onecity.Constant.NO1024;
import static com.yst.onecity.Constant.NO200;

/**
 * 发布计划日记界面
 *
 * @author zhaiyanwu
 * @version 1.0.1
 * @date 2018/02/28
 */

public class IssueDiaryActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.image_add_commodity_image)
    ImageView addImage;
    @BindView(R.id.image_add_commodity_video)
    ImageView addVideo;
    @BindView(R.id.btn_commit)
    TextView commit;
    @BindView(R.id.tev_date)
    TextView tevDate;
    @BindView(R.id.edt_diary_main)
    EditText diaryMain;
    @BindView(R.id.add_image_grid)
    GridView imageGrid;
    private String productId = "";
    private int imageNewsList = 0;
    private int videoNum = 0;
    private int imageList = 0;
    private int type = 0;
    String video = "";
    String modelType = "";
    ArrayList<ConsultItemBean> getTitleImageList = new ArrayList<>();
    ArrayList<String> getTitleVideoList = new ArrayList<>();
    /**
     * 视频最大支持100M
     */
    private final int MAX_VIDEO_SIZE = 100;
    /**
     * 视频格式对比
     */
    private final String VIDEO_FORMAT = ".mp4";
    private IssueSpecificationAdapter chooseImageSpecificationAdapter;
    private ImageView imageVideo2;
    private ImageView imagePicture2;
    private View inflate2;
    private PopupWindow popupWindow2;
    private ProductPlanBean.ContentBean.ProductPlanDailyVoBean daily;
    private String modelType1;
    private String title;
    private String describes;
    private String address;
    private String longitude;
    private String latitude;
    private String productIds;
    private String covers;
    private String video1;
    private String cover;
    private String productId1;
    private int from;
    private String projectId;
    private ProductPlanBean.ContentBean.ProductPlanDailyVoBean productPlanDailyVo;
    private int updateDailyId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_issue_diary;
    }

    @Override
    public void initData() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        initTitleBar("发布服务案例");
        getIntegtData();
        setGridViewAdapter();
        Intent intent = getIntent();
        //产品计划id
        productId = intent.getStringExtra("productId");
        //是否是回显
        boolean isEdit = intent.getBooleanExtra("isEdit", false);
        if (isEdit) {
            //日记信息回显
            daily = (ProductPlanBean.ContentBean.ProductPlanDailyVoBean) intent.getSerializableExtra("productPlanDailyVo");
            if (daily != null) {
                int modelType = daily.getModelType();
                String createdTime = daily.getCreatedTime();
                String content = daily.getContent();
                tevDate.setText(createdTime);
                diaryMain.setText(content);
                List<ProductPlanBean.ContentBean.ProductPlanDailyVoBean.CurrencyAttachmentsBean> currencyAttachments = daily.getCurrencyAttachments();
                if (modelType == Constant.NO0) {
                    //视频地址
                    String address = currencyAttachments.get(0).getAddress();
                    //视频第一帧
                    String videoCoverAddress = currencyAttachments.get(0).getVideoCoverAddress();
                    ConsultItemBean consultItemBean = new ConsultItemBean();
                    consultItemBean.setId(1);
                    consultItemBean.setmPhotoPath(videoCoverAddress);
                    consultItemBean.setTypes(1);
                    getTitleImageList.clear();
                    getTitleImageList.add(consultItemBean);
                    getTitleVideoList.add(address);
                    video = address;
                    chooseImageSpecificationAdapter.notifyDataSetChanged();
                } else {
                    //图片
                    for (int i = 0; i < currencyAttachments.size(); i++) {
                        ConsultItemBean consultItemBean = new ConsultItemBean();
                        String address = currencyAttachments.get(i).getAddress();
                        consultItemBean.setId(1);
                        consultItemBean.setmPhotoPath(address);
                        consultItemBean.setTypes(0);
                        getTitleImageList.add(consultItemBean);
                        chooseImageSpecificationAdapter.notifyDataSetChanged();
                    }
                }
            } else {
                setTvData();
            }
        } else {
            setTvData();
        }
    }

    /**
     * 设置当前系统时间
     */
    private void setTvData() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(" yyyy - MM - dd ");
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        tevDate.setText(simpleDateFormat.format(date));
        inflate2 = View.inflate(getApplicationContext(), R.layout.popup_window_view2, null);
        initPopWindow2(inflate2);
    }

    /**
     * 获得上一个页面传过来的参数
     */
    private void getIntegtData() {
        Bundle extras = getIntent().getExtras();
        modelType1 = ConstUtils.getStringNoEmpty(extras.getString("modelType"));
        title = ConstUtils.getStringNoEmpty(extras.getString("title"));
        describes = ConstUtils.getStringNoEmpty(extras.getString("describes"));
        address = ConstUtils.getStringNoEmpty(extras.getString("address"));
        longitude = ConstUtils.getStringNoEmpty(extras.getString("longitude"));
        latitude = ConstUtils.getStringNoEmpty(extras.getString("latitude"));
        productIds = ConstUtils.getStringNoEmpty(extras.getString("productIds"));
        covers = ConstUtils.getStringNoEmpty(extras.getString("covers"));
        video1 = ConstUtils.getStringNoEmpty(extras.getString("video"));
        cover = ConstUtils.getStringNoEmpty(extras.getString("cover"));
        productId1 = ConstUtils.getStringNoEmpty(extras.getString("productId"));
        from = extras.getInt("from");
        projectId = ConstUtils.getStringNoEmpty(extras.getString("projectId"));
    }

    @Override
    public void setListener() {
        super.setListener();
        addImage.setOnClickListener(this);
        addVideo.setOnClickListener(this);
        commit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //拍照
            case R.id.image_add_commodity_image:
                if (getTitleVideoList.size() > 0) {
                    ToastUtils.show("图片和视频不可以同时添加");
                } else {
                    //最大添加三张图片
                    AddImageOrVideo.localImg(this, Constant.NO22, 3 - getTitleImageList.size());
                }
                break;
            //视频
            case R.id.image_add_commodity_video:
                showPopWindow2();
                break;
            //完成
            case R.id.btn_commit:
                boolean clickable = Utils.isClickable();
                if (clickable) {
                    String content = diaryMain.getText().toString().trim();
                    int imageSize = getTitleImageList.size();
                    int videoSize = getTitleVideoList.size();
                    boolean call = !TextUtils.isEmpty(content) && imageSize == 0 || videoSize == 0;
                    if (call) {
                        if (TextUtils.isEmpty(content) && imageSize == 0 && videoSize == 0) {
                            callConnector();
                            return;
                        }
                        if (TextUtils.isEmpty(content) || content.length() > NO200) {
                            ToastUtils.show("内容字数在 1-200 之间");
                            return;
                        }
                        if (imageSize == 0 && videoSize == 0) {
                            ToastUtils.show("请添加一张图片或者视频");
                            return;
                        }
                        if (imageSize != 0 || videoSize != 0) {
                            callConnector();
                        }
                    } else {
                        callConnector();
                    }
                }
                break;
            //拍摄视频
            case R.id.popup_window_view_picture2:
                popupWindow2.dismiss();
                Intent intent = new Intent(IssueDiaryActivity.this, PublishInfoVideoActivity.class);
                intent.putExtra("from", "2");
                startActivityForResult(intent, Constant.NO21);
                break;
            //本地视频
            case R.id.popup_window_view_video2:
                popupWindow2.dismiss();
                AddImageOrVideo.localVideo(this, Constant.LOCAL_VIDEO_RESULT);
                break;
            //取消 添加视频方式 dialog
            case R.id.popup_window_tv_cancel2:
                popupWindow2.dismiss();
                break;
            default:
                break;
        }
    }


    /**
     * 请求发布产品计划接口
     */
    private void callConnector() {
        if (Constant.NO9 == from) {
            //发布
            publishProduct();
        } else if (Constant.NO2 != from) {
            //修改
            editProduct();
        } else {
            //发布
            publishProduct();
        }
    }


    /**
     * 发布日记接口
     *
     * @param id 产品计划id
     */
    public void publishDiary(int id) {
        String content = diaryMain.getText().toString().trim();
        int imageSize = getTitleImageList.size();
        int videoSize = getTitleVideoList.size();
        //日记不填写也可以发布  如果填写日记  必须有图片或者视频
        if (TextUtils.isEmpty(content) && imageSize == Constant.NO0 && videoSize == Constant.NO0) {
            ToastUtils.show("发布成功");
            finish();
            EventBus.getDefault().post(new EventBean("finishActivity"));
            if (Constant.IS_FLUSH) {
                Constant.IS_FLUSH = false;
                EventBus.getDefault().post(new EventBean("flush"));
            }
            return;
        }
        String imagePath = "";
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < getTitleImageList.size(); i++) {
            String s = getTitleImageList.get(i).getmPhotoPath();
            if (i > 0) {
                stringBuffer.append(",");
            }
            imagePath = stringBuffer.append(s).toString();
        }
        String title = tevDate.getText().toString().trim();
        if (getTitleVideoList.size() == 0) {
            modelType = imageSize + "";
            video = "";
        } else {
            modelType = videoNum + "";
        }
        /**
         * 添加发布日记
         */
        RequestApi.publishDiary(App.manager.getPhoneNum(), App.manager.getUuid(), title, content, id + "", video, imagePath, modelType, new AbstractNetWorkCallback<DiaryBean>() {
            @Override
            public void onSuccess(DiaryBean msgBean) {
                ToastUtils.show(msgBean.getMsg());
                if (msgBean.getCode() == Constant.NO1) {
                    //发布成功之后关闭页面
                    finish();
                    EventBus.getDefault().post(new EventBean("finishActivity"));
                    if (Constant.IS_FLUSH) {
                        Constant.IS_FLUSH = false;
                        EventBus.getDefault().post(new EventBean("flush"));
                    }
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(getResources().getString(R.string.error_hint));
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        //选择本地视频
        if (resultCode == Constant.LOCAL_VIDEO_RESULT) {
            String path = data.getExtras().getString("path");
            //视频大小
            final long size = data.getExtras().getLong("size2");
            if (size / NO1024 / NO1024 > MAX_VIDEO_SIZE) {
                ToastUtils.show("视频不能超过100M");
                return;
            }
            MediaMetadataRetriever media = new MediaMetadataRetriever();
            media.setDataSource(path);
            Bitmap bitmap = media.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
            if (bitmap == null) {
                ToastUtils.show("该视频无效,请重新选择");
                return;
            }
            final String imagePath = AddImageOrVideo.saveBitmap(this, bitmap);
            showInfoProgressDialog();
            //视频上传接口
            AddImageOrVideo.upLoadVideo(IssueDiaryActivity.this, path, new AddImageOrVideo.UploadVideoListener() {
                @Override
                public void uploadVideoListener(final String msg) {
                    if (msg.endsWith(VIDEO_FORMAT)) {
                        AddImageOrVideo.upLoadImg(IssueDiaryActivity.this, imagePath, new AddImageOrVideo.UploadImgListener() {
                            @Override
                            public void uploadImgListener(String msgs) {
                                ConsultItemBean consultItemBean = new ConsultItemBean();
                                consultItemBean.setId(1);
                                consultItemBean.setmPhotoPath(msgs);
                                consultItemBean.setTypes(1);
                                getTitleImageList.clear();
                                getTitleImageList.add(consultItemBean);
                                type = 1;
                                videoNum = 0;
                                video = msg;
                                getTitleVideoList.add(msg);
                                chooseImageSpecificationAdapter.notifyDataSetChanged();
                                ToastUtils.show("视频上传成功");
                            }
                        });
                    } else {
                        ToastUtils.show(msg);
                    }
                    dismissInfoProgressDialog();
                }
            });
            return;
        }
        switch (requestCode) {
            //选择图库回传
            case Constant.NO22:
                ArrayList<String> stringArrayListExtra = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                imageList = stringArrayListExtra.size();
                upLoadPic(stringArrayListExtra);
                break;
            //拍摄视频回显
            case Constant.NO21:
                //视频路径
                String videoPath = data.getExtras().getString("videoPath");
                //图片路径
                final String framePicPath = data.getExtras().getString("framePicPath");
                if (TextUtils.isEmpty(videoPath) || TextUtils.isEmpty(framePicPath)) {
                    ToastUtils.show("视频获取失败,请重新拍摄");
                    return;
                }
                showInfoProgressDialog();
                //视频上传接口
                AddImageOrVideo.upLoadVideo(IssueDiaryActivity.this, videoPath, new AddImageOrVideo.UploadVideoListener() {
                    @Override
                    public void uploadVideoListener(final String msg) {
                        if (msg.endsWith(VIDEO_FORMAT)) {
                            AddImageOrVideo.upLoadImg(IssueDiaryActivity.this, framePicPath, new AddImageOrVideo.UploadImgListener() {
                                @Override
                                public void uploadImgListener(String msgs) {
                                    ConsultItemBean consultItemBean = new ConsultItemBean();
                                    consultItemBean.setId(1);
                                    consultItemBean.setmPhotoPath(msgs);
                                    consultItemBean.setTypes(1);
                                    getTitleImageList.clear();
                                    getTitleImageList.add(consultItemBean);
                                    type = 1;
                                    videoNum = 0;
                                    video = msg;
                                    getTitleVideoList.add(msg);
                                    chooseImageSpecificationAdapter.notifyDataSetChanged();
                                    ToastUtils.show("视频上传成功");
                                }
                            });

                        } else {
                            ToastUtils.show(msg);
                        }
                        dismissInfoProgressDialog();
                    }
                });
                break;
            default:
                break;
        }
    }

    /**
     * 上传图片
     */
    public void upLoadPic(List<String> imgPathList) {
        imageList = imgPathList.size();
        imageNewsList = 0;
        showInfoProgressDialog();
        for (int i = 0; i < imgPathList.size(); i++) {
            HashMap<String, String> params = new HashMap<>(16);
            //mark 客户端标识 0 安卓 1 iOS 2 PC
            params.put("mark", "0");
            ///图片上传接口
            AddImageOrVideo.upLoadImg(IssueDiaryActivity.this, imgPathList.get(i), new AddImageOrVideo.UploadImgListener() {
                @Override
                public void uploadImgListener(String msg) {
                    if (msg.endsWith(".jpg") || msg.endsWith(".png")) {
                        ConsultItemBean consultItemBean = new ConsultItemBean();
                        consultItemBean.setId(1);
                        consultItemBean.setmPhotoPath(msg);
                        consultItemBean.setTypes(0);
                        getTitleImageList.add(getTitleImageList.size(), consultItemBean);
                        type = 0;
                        chooseImageSpecificationAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtils.show(msg);
                    }
                    imageNewsList++;
                    if (imageNewsList == imageList) {
                        dismissInfoProgressDialog();
                    }
                }
            });
        }
    }

    /**
     * 添加日记图片adapter
     */
    private void setGridViewAdapter() {
        chooseImageSpecificationAdapter = new IssueSpecificationAdapter(getTitleImageList, this, type);
        imageGrid.setAdapter(chooseImageSpecificationAdapter);
        if (chooseImageSpecificationAdapter != null) {
            chooseImageSpecificationAdapter.setIScrollPositon(new IssueSpecificationAdapter.IGetScrollPosition() {
                @Override
                public void click(int i) {
                    if (getTitleVideoList.size() > Constant.NO0) {
                        getTitleVideoList.remove(i);
                    } else {
                        getTitleImageList.remove(i);
                    }
                    chooseImageSpecificationAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    /**
     * 显示popwindow2
     */
    private void showPopWindow2() {
        popupWindow2 = new PopupWindow(inflate2, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        popupWindow2.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xf0fbfbfb);
        popupWindow2.setBackgroundDrawable(dw);
        // 设置popWindow的显示和消失动画
        popupWindow2.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        popupWindow2.showAtLocation(this.findViewById(R.id.linear_top), Gravity.BOTTOM, 0, 0);
        //设置背景半透明
        backgroundAlpha(0.9f);
        //关闭事件
        popupWindow2.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popupWindow2.dismiss();
                backgroundAlpha(1f);
            }
        });
    }

    /**
     * 初始化PopWindow
     *
     * @param inflate 布局
     */
    private void initPopWindow2(View inflate) {
        imagePicture2 = inflate.findViewById(R.id.popup_window_view_picture2);
        imageVideo2 = inflate.findViewById(R.id.popup_window_view_video2);
        TextView tvCancel2 = inflate.findViewById(R.id.popup_window_tv_cancel2);
        imagePicture2.setOnClickListener(this);
        imageVideo2.setOnClickListener(this);
        tvCancel2.setOnClickListener(this);
    }

    /**
     * 设置添加屏幕的背景透明度
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        //0.0-1.0
        lp.alpha = bgAlpha;
        this.getWindow().setAttributes(lp);
    }

    /**
     * 发布产品计划
     */
    private void publishProduct() {
        //发布产品计划 发布成功传返回id到下一页面 (最后一个参数来源 0:自创产品计划 1:项目计划）
        RequestApi.publishProductPlan(App.manager.getUuid(), App.manager.getPhoneNum(), modelType1, title, describes, address, longitude, latitude, productIds, covers, projectId, video1, cover, "0", new AbstractNetWorkCallback<PublishProductBean>() {
            @Override
            public void onSuccess(PublishProductBean msgBean) {
                if (msgBean.code == Constant.NO1) {
                    //调用发布日记接口
                    publishDiary(msgBean.content);
                } else {
                    ToastUtils.show(msgBean.msg);
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
     * 修改产品计划
     */
    private void editProduct() {
        //修改产品计划 修改成功传返回id到下一页面
        RequestApi.editProductPlan(productId1, App.manager.getUuid(), App.manager.getPhoneNum(), modelType1, title, describes, address, longitude, latitude, productIds, covers, projectId, video1, new AbstractNetWorkCallback<EditProductBean>() {
            @Override
            public void onSuccess(EditProductBean msgBean) {
                ToastUtils.show(msgBean.getMsg());
                if (msgBean.getCode() == Constant.NO1) {
                    if (null == daily) {
                        publishDiary(Integer.valueOf(productId));
                    } else {
                        updateDailyId = daily.getId();
                        updatePublishDiary();
                    }
                } else {
                    ToastUtils.show(msgBean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
                com.yst.onecity.utils.MyLog.e("publishProduct:onError:", errorMsg);
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
     * 修改发布日记 设置参数值
     */
    public void updatePublishDiary() {
        String content = diaryMain.getText().toString().trim();
        int imageSize = getTitleImageList.size();
        int videoSize = getTitleVideoList.size();
        if (TextUtils.isEmpty(content) && imageSize == 0 && videoSize == 0) {
            ToastUtils.show("发布成功");
            finish();
            EventBus.getDefault().post(new EventBean("finishActivity"));
            if (Constant.IS_FLUSH) {
                Constant.IS_FLUSH = false;
                EventBus.getDefault().post(new EventBean("flush"));
            }
            return;
        }
        String imagePath = "";
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < getTitleImageList.size(); i++) {
            String s = getTitleImageList.get(i).getmPhotoPath();
            if (i > 0) {
                stringBuffer.append(",");
            }
            imagePath = stringBuffer.append(s).toString();
        }
        String title = tevDate.getText().toString().trim();
        if (getTitleVideoList.size() == 0) {
            modelType = imageSize + "";
            video = "";
        } else {
            modelType = videoNum + "";
        }

        /**
         * 修改发布日记
         */
        RequestApi.updataPublishDiary(App.manager.getPhoneNum(), App.manager.getUuid(), title, content, updateDailyId + "", video, imagePath, modelType, "0", new AbstractNetWorkCallback<MsgBean>() {
            @Override
            public void onSuccess(MsgBean msgBean) {
                ToastUtils.show(msgBean.getMsg());
                if (msgBean.getCode() == Constant.NO1) {
                    finish();
                    EventBus.getDefault().post(new EventBean("finishActivity"));
                    if (Constant.IS_FLUSH) {
                        Constant.IS_FLUSH = false;
                        EventBus.getDefault().post(new EventBean("flush"));
                    }
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(getResources().getString(R.string.error_hint));
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
}
