package com.yst.onecity.activity.issue;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.MyLog;
import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.activity.home.PublishInfoVideoActivity;
import com.yst.onecity.activity.home.WidthMatchVideoActivity;
import com.yst.onecity.adapter.AddProductPlanViewPagerAdapter;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.ProductPlanBean;
import com.yst.onecity.db.DbManager;
import com.yst.onecity.utils.AddImageOrVideo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import pub.devrel.easypermissions.EasyPermissions;

import static com.yst.onecity.Constant.FINISHACTIVITY;
import static com.yst.onecity.Constant.IMG_FORMAT;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO100;
import static com.yst.onecity.Constant.NO1024;
import static com.yst.onecity.Constant.NO2;
import static com.yst.onecity.Constant.NO200;
import static com.yst.onecity.Constant.NO50;

/**
 * 发布服务项目（原：添加产品计划页）
 *
 * @author songbinbin
 * @version 1.0.1
 * @date 2018/02/23
 */
public class AddProductPlanActivity extends BaseActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks {
    @BindView(R.id.add_product_plan_iv_photo)
    ImageView ivPhoto;
    @BindView(R.id.add_product_plan_tv_photo)
    TextView tvPhoto;
    @BindView(R.id.add_product_plan_btn_nextStep)
    Button nextStep;
    @BindView(R.id.add_product_plan_et_title)
    EditText etTitle;
    @BindView(R.id.add_product_plan_et_describe)
    EditText etDescribe;
    @BindView(R.id.add_product_plan_tv_describe_number)
    TextView tvDescribeNumber;
    @BindView(R.id.add_product_vp)
    ViewPager mViewPager;
    @BindView(R.id.add_product_plan_tv_addImg)
    TextView tvAddimg;
    @BindView(R.id.add_product_plan_tv_imgNumber)
    TextView tvImgNumber;
    @BindView(R.id.add_product_tv_address)
    TextView tvAddress;
    @BindView(R.id.add_product_plan_iv_delete)
    ImageView ivDelete;
    @BindView(R.id.add_product_plan_iv_play)
    ImageView ivPlay;
    @BindView(R.id.add_product_ll_refresh_location)
    LinearLayout llGetLocation;

    private PopupWindow popupWindow;
    private PopupWindow popupWindow2;
    private View inflate;
    private View inflate2;

    /**
     * 单次最大可添加图片数
     */
    private final int MAX_IMG_NUMBER = 3;
    /**
     * 选择图片requestCode
     */
    private final int IMG_REQUEST_CODE = 666;
    /**
     * 选择视频requestCode
     */
    private final int VIDEO_REQUEST_CODE = 667;
    /**
     * 选择图片requestCode(替换)
     */
    private final int REPLACE_REQUEST_CODE = 668;
    /**
     * 动态获取摄像头权限请求码
     */
    private final int GET_CAMERA_REQUEST = 333;
    /**
     * 视频格式对比
     */
    private final String VIDEO_FORMAT = ".mp4";
    /**
     * 封面图资源
     */
    List<String> listCover = new ArrayList<>();
    /**
     * 0视频，1图片
     */
    private String type = "";
    AddProductPlanViewPagerAdapter mViewPagerAdapter;
    AMapLocationClient mLocationClient;
    private String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    /**
     * 视频网址
     */
    private String mVideoPath;
    /**
     * 本地路径
     */
    private String localVideoPath;
    private double wd;
    private double jd;
    private String city;
    private final String TYPE0 = "0";

    /**
     * 是否是编辑
     */
    private boolean isEdit = false;

    /**
     * 编辑产品计划 回传（包含：产品计划页，商品页，日记页所有信息）
     */
    private ProductPlanBean.ContentBean editContent;
    private String projectId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_product_plan;
    }

    @Override
    public void initData() {
        initTitleBar(getString(R.string.issue_services_available));
        EventBus.getDefault().register(this);
        //设置封面图适配器
        mViewPagerAdapter = new AddProductPlanViewPagerAdapter(AddProductPlanActivity.this, listCover);
        mViewPager.setAdapter(mViewPagerAdapter);

        //编辑
        Intent intent = getIntent();
        editContent = (ProductPlanBean.ContentBean) intent.getSerializableExtra("productPlanBean");
        //猎头 h5 项目计划详情 我要加入 回传项目计划id
        projectId = intent.getStringExtra("planId");
        if (null != editContent) {
            isEdit = true;
            //如果是编辑跳转，设置数据
            setEditContent(editContent);
        }

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        inflate = View.inflate(getApplicationContext(), R.layout.popup_window_view, null);
        initPopWindow(inflate);
        inflate2 = View.inflate(getApplicationContext(), R.layout.popup_window_view2, null);
        initPopWindow2(inflate2);

        //创建城市表
        DbManager dbManager = new DbManager(this);
        dbManager.copyDBFile();
        initLocation();
    }

    /**
     * 获取当前地理位置
     */
    private void initLocation() {
        if(null == mLocationClient) {
            mLocationClient = new AMapLocationClient(this);
        }
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setOnceLocation(true);
        mLocationClient.setLocationOption(option);
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        city = aMapLocation.getCity();
                        //得到纬度
                        wd = aMapLocation.getLatitude();
                        //得到经度
                        jd = aMapLocation.getLongitude();
                        tvAddress.setText(city);
                        //下划线
                        tvAddress.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
                    } else {
                        EasyPermissions.requestPermissions(AddProductPlanActivity.this, "需要定位权限", NO100, permission);
                        //定位失败
                        MyLog.e("response", "失败码===" + aMapLocation.getErrorCode());

                    }
                }
            }
        });
        mLocationClient.startLocation();
    }

    @Override
    public void setListener() {
        //点击获取定位
        llGetLocation.setOnClickListener(this);

        mViewPagerAdapter.setOnItemClickListener(new AddProductPlanViewPagerAdapter.OnVpItemClickListener() {
            @Override
            public void onClick(ImageView imageView) {
                //替换封面图
                if (!type.equals(TYPE0)) {
                    AddImageOrVideo.localImg(AddProductPlanActivity.this, REPLACE_REQUEST_CODE, NO1);
                }
            }
        });
        ivPlay.setOnClickListener(this);
        ivDelete.setOnClickListener(this);
        ivPhoto.setOnClickListener(this);
        nextStep.setOnClickListener(this);
        tvAddimg.setOnClickListener(this);
        //产品标题输入监听
        etTitle.addTextChangedListener(new TextWatcher() {
            //记录输入的字数
            private CharSequence wordNum;
            private int selectionStart;
            private int selectionEnd;
            private int num = 30;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //实时记录输入的字数
                wordNum = charSequence;
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //自动删除超出文字限制的部分
                selectionStart = etTitle.getSelectionStart();
                selectionEnd = etTitle.getSelectionEnd();
                if (wordNum.length() > num) {
                    //删除多余输入的字（不会显示出来）
                    editable.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    etTitle.setText(editable);
                    //设置光标在最后
                    etTitle.setSelection(tempSelection);
                }
            }
        });
        //产品描述输入监听
        etDescribe.addTextChangedListener(new TextWatcher() {
            //记录输入的字数
            private CharSequence wordNum;
            private int num = 200;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //实时记录输入的字数
                wordNum = charSequence;
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = wordNum.length() + "/" + num + " 字";
                tvDescribeNumber.setText(str);
                if (wordNum.length() > num) {
                    tvDescribeNumber.setTextColor(ContextCompat.getColor(AddProductPlanActivity.this, R.color.color_red));
                } else {
                    tvDescribeNumber.setTextColor(ContextCompat.getColor(AddProductPlanActivity.this, R.color.color_2E2D2D));
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_product_ll_refresh_location:
                initLocation();
                break;
            //播放视频
            case R.id.add_product_plan_iv_play:
                ToastUtils.show("播放");
                if (!TextUtils.isEmpty(localVideoPath)) {
                    Intent intent = new Intent(AddProductPlanActivity.this, WidthMatchVideoActivity.class);
                    intent.putExtra("videoPath", localVideoPath);
                    startActivity(intent);
                } else {
                    Toast.makeText(AddProductPlanActivity.this, "视频路径无效", Toast.LENGTH_SHORT).show();
                }
                break;
            //删除
            case R.id.add_product_plan_iv_delete:
                if (type.equals(TYPE0)) {
                    listCover.clear();
                    mViewPagerAdapter.setDatas(listCover);
                    setVideoHide();
                } else {
                    listCover.remove(mViewPager.getCurrentItem());
                    mViewPagerAdapter.setDatas(listCover);
                    setImgHide();
                }
                break;
            //点击添加图片/视频弹窗
            case R.id.add_product_plan_iv_photo:
                showPopWindow();
                break;
            //弹窗取消
            case R.id.popup_window_tv_cancel:
                popupWindow.dismiss();
                break;
            //添加图片
            case R.id.popup_window_view_picture:
                popupWindow.dismiss();
            case R.id.add_product_plan_tv_addImg:
                //选择图片
                AddImageOrVideo.localImg(AddProductPlanActivity.this, IMG_REQUEST_CODE, (MAX_IMG_NUMBER - getListCover()));
                break;
            //添加视频
            case R.id.popup_window_view_video:
                popupWindow.dismiss();
                //底部弹窗 选择本地/拍摄
                showPopWindow2();
                break;
            //拍摄视频
            case R.id.popup_window_view_picture2:
                popupWindow2.dismiss();
                //6.0以上
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //检查相机权限
                    int checkPermissionCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
                    int checkPermissionAudio = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
                    if (checkPermissionCamera != PackageManager.PERMISSION_GRANTED || checkPermissionAudio != PackageManager.PERMISSION_GRANTED) {
                        //如果权限返回不为 GRANTED 则申请权限 GET_CAMERA_REQUEST 为请求码
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}, GET_CAMERA_REQUEST);
                    } else {
                        //已有权限
                        Bundle from = new Bundle();
                        from.putString("from", "1");
                        JumpIntent.jump(AddProductPlanActivity.this, PublishInfoVideoActivity.class, from, NO50);
                    }
                } else {
                    //6.0以下 无需动态获取
                    Bundle from = new Bundle();
                    from.putString("from", "1");
                    JumpIntent.jump(AddProductPlanActivity.this, PublishInfoVideoActivity.class, from, NO50);
                }
                break;
            //本地视频
            case R.id.popup_window_view_video2:
                popupWindow2.dismiss();
                AddImageOrVideo.localVideo(this, VIDEO_REQUEST_CODE);
                break;
            //取消 添加视频方式 dialog
            case R.id.popup_window_tv_cancel2:
                popupWindow2.dismiss();
                break;
            //下一步
            case R.id.add_product_plan_btn_nextStep:
                if (!Utils.isClickable()) {
                    return;
                }
                if (getListCover() < NO1) {
                    ToastUtils.show("封面图至少添加一个图片或视频");
                    break;
                } else if (etTitle.getText().toString().trim().length() < NO1) {
                    ToastUtils.show("标题不能少于一个字");
                    break;
                } else if (etDescribe.getText().toString().trim().length() < NO1) {
                    ToastUtils.show("描述不能少于一个字");
                    break;
                } else if (etDescribe.getText().toString().trim().length() > NO200) {
                    ToastUtils.show("描述不能超过200字");
                    break;
                }else if(TextUtils.isEmpty(city)){
                    ToastUtils.show("定位失败，请重新定位!");
                    break;
                }
                Bundle bundle = new Bundle();
                bundle.putString("title", etTitle.getText().toString().trim());
                bundle.putString("describe", etDescribe.getText().toString().trim());
                bundle.putStringArrayList("img", (ArrayList<String>) listCover);
                bundle.putString("type", type);
                bundle.putString("videoPath", mVideoPath);
                bundle.putString("address", city);
                bundle.putDouble("jd", jd);
                bundle.putDouble("wd", wd);
                bundle.putString("projectId", projectId);
                if (isEdit) {
                    bundle.putSerializable("editContent", editContent);
                    bundle.putInt("from", 1);
                } else {
                    bundle.putInt("from", 2);
                }
                JumpIntent.jump(this, AddCommodityActivity.class, bundle);
                break;
            default:
                break;
        }
    }

    /**
     * 显示popwindow
     */
    private void showPopWindow() {
        popupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        popupWindow.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xf0fbfbfb);
        popupWindow.setBackgroundDrawable(dw);
        // 设置popWindow的显示和消失动画
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        popupWindow.showAtLocation(this.findViewById(R.id.add_product_plan_btn_nextStep), Gravity.BOTTOM, 0, 0);
        //设置背景半透明
        backgroundAlpha(0.9f);
        //关闭事件
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popupWindow.dismiss();
                backgroundAlpha(1f);
            }
        });
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
        popupWindow2.showAtLocation(this.findViewById(R.id.add_product_plan_btn_nextStep), Gravity.BOTTOM, 0, 0);
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
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha 透明度
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        //0.0-1.0
        lp.alpha = bgAlpha;
        this.getWindow().setAttributes(lp);
    }

    /**
     * 上传视频
     *
     * @param imgPath   缩略图
     * @param videoPath 视频路径
     */
    public void uploadVideo(final String imgPath, String videoPath) {
        AddImageOrVideo.upLoadVideo(AddProductPlanActivity.this, videoPath, new AddImageOrVideo.UploadVideoListener() {
            @Override
            public void uploadVideoListener(String msg) {
                if (msg.endsWith(VIDEO_FORMAT)) {
                    ToastUtils.show("上传成功");
                    //成功
                    videoOk(imgPath, msg);
                } else {
                    ToastUtils.show(msg);
                }
            }
        });
    }

    /**
     * 视频上传成功
     *
     * @param imgPath   缩略图
     * @param videoPath 视频路径
     */
    public void videoOk(String imgPath, final String videoPath) {
        AddImageOrVideo.upLoadImg(AddProductPlanActivity.this, imgPath, new AddImageOrVideo.UploadImgListener() {
            @Override
            public void uploadImgListener(String msg) {
                if (null == msg) {
                    msg = "上传失败";
                }
                if (msg.endsWith(IMG_FORMAT)) {
                    //添加路径到集合
                    listCover.add(msg);
                    mViewPagerAdapter.setDatas(listCover);
                    mVideoPath = videoPath;
                    type = "0";
                    setVideoHide();
                } else {
                    ToastUtils.show(msg);
                }
            }
        });
    }

    /**
     * 上传图片
     */
    public void uploadPic(List<String> imgPathList, final int todo) {
        for (int i = 0; i < imgPathList.size(); i++) {
            AddImageOrVideo.upLoadImg(AddProductPlanActivity.this, imgPathList.get(i), new AddImageOrVideo.UploadImgListener() {
                @Override
                public void uploadImgListener(String msg) {
                    if (null == msg) {
                        msg = "上传失败";
                    }
                    if (msg.endsWith(IMG_FORMAT)) {
                        imgOk(msg, todo);
                    } else {
                        ToastUtils.show(msg);
                    }
                }
            });
        }
    }

    /**
     * 图片上传成功后
     *
     * @param url 网络路径
     */
    public void imgOk(String url, int todo) {
        //添加路径到集合
        if (todo == NO1) {
            listCover.add(url);
        } else if (todo == NO2) {
            listCover.set(mViewPager.getCurrentItem(), url);
        }
        mViewPagerAdapter.setDatas(listCover);
        //设置类型
        type = "1";
        //控制传图片显示隐藏
        setImgHide();
        //设置监听，主要是设置点点的背景
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == NO2) {
                    String str = (mViewPager.getCurrentItem() + 1) + "/" + getListCover();
                    //设置图片数
                    tvImgNumber.setText(str);
                }
            }
        });
    }

    /**
     * 上传视频设置隐藏
     */
    public void setVideoHide() {
        if (null != listCover && getListCover() > 0) {
            //隐藏拍照图片/文字
            ivPhoto.setVisibility(View.INVISIBLE);
            tvPhoto.setVisibility(View.INVISIBLE);
            ivDelete.setVisibility(View.VISIBLE);
            //显示vp
            mViewPager.setVisibility(View.VISIBLE);
            //显示播放按钮
            ivPlay.setVisibility(View.VISIBLE);
        } else {
            //显示拍照图片/文字
            ivPhoto.setVisibility(View.VISIBLE);
            tvPhoto.setVisibility(View.VISIBLE);
            ivDelete.setVisibility(View.INVISIBLE);
            //隐藏vp
            mViewPager.setVisibility(View.INVISIBLE);
            //隐藏播放按钮
            ivPlay.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 上传图片成功设置隐藏
     */
    public void setImgHide() {
        //大于0 小于3  显示添加
        if (getListCover() > 0) {
            //隐藏拍照图片/文字
            ivPhoto.setVisibility(View.INVISIBLE);
            tvPhoto.setVisibility(View.INVISIBLE);
            //显示图片数
            tvImgNumber.setVisibility(View.VISIBLE);
            //显示vp
            mViewPager.setVisibility(View.VISIBLE);
            //删除按钮
            ivDelete.setVisibility(View.VISIBLE);
            //设置页码
            if (null != listCover) {
                String str = (mViewPager.getCurrentItem() + 1) + "/" + getListCover();
                tvImgNumber.setText(str);
            }
        } else {
            //显示拍照图片/文字
            ivPhoto.setVisibility(View.VISIBLE);
            tvPhoto.setVisibility(View.VISIBLE);
            //隐藏图片数
            tvImgNumber.setVisibility(View.INVISIBLE);
            //隐藏vp
            mViewPager.setVisibility(View.INVISIBLE);
            ivDelete.setVisibility(View.INVISIBLE);
        }

        //当前添加的图片 大于 0 小于3 时显示 添加图片  0或3时隐藏
        if (MAX_IMG_NUMBER == getListCover() || Constant.NO0 == getListCover()) {
            tvAddimg.setVisibility(View.INVISIBLE);
        } else {
            tvAddimg.setVisibility(View.VISIBLE);
        }

    }

    /**
     * 获取当前添加的图片数量listCover.size()
     */
    public int getListCover() {
        if (null != listCover) {
            return listCover.size();
        }
        return 0;
    }

    /**
     * 初始化PopWindow
     *
     * @param inflate 布局
     */
    private void initPopWindow(View inflate) {
        ImageView imagePicture = inflate.findViewById(R.id.popup_window_view_picture);
        ImageView imageVideo = inflate.findViewById(R.id.popup_window_view_video);
        TextView tvCancel = inflate.findViewById(R.id.popup_window_tv_cancel);
        imagePicture.setOnClickListener(this);
        imageVideo.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }

    /**
     * 初始化PopWindow
     *
     * @param inflate 布局
     */
    private void initPopWindow2(View inflate) {
        ImageView imagePicture2 = inflate.findViewById(R.id.popup_window_view_picture2);
        ImageView imageVideo2 = inflate.findViewById(R.id.popup_window_view_video2);
        TextView tvCancel2 = inflate.findViewById(R.id.popup_window_tv_cancel2);
        imagePicture2.setOnClickListener(this);
        imageVideo2.setOnClickListener(this);
        tvCancel2.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //拍摄视频
        if (NO50 == requestCode) {
            if (resultCode == RESULT_OK) {
                String videoPath = data.getStringExtra("videoPath");
                String framePicPath = data.getStringExtra("framePicPath");
                int videoSize = data.getIntExtra("videoSize", 0);
                //视频最大支持100M
                if (videoSize / NO1024 / NO1024 > NO100) {
                    ToastUtils.show("视频不能超过100M");
                    return;
                }
                if (videoPath == null) {
                    ToastUtils.show("视频获取失败，请重新拍摄");
                    return;
                }
                localVideoPath = videoPath;
                //上传视频
                uploadVideo(framePicPath, videoPath);
            }
        }

        //替换图片
        if (REPLACE_REQUEST_CODE == requestCode) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> imgPathList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                //上传图片
                uploadPic(imgPathList, NO2);
            }
        }

        //图片
        if (IMG_REQUEST_CODE == requestCode) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> imgPathList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                //上传图片
                uploadPic(imgPathList, NO1);
            }
        }

        //视频
        if (VIDEO_REQUEST_CODE == requestCode) {
            if (resultCode == Constant.LOCAL_VIDEO_RESULT) {
                Bundle extras = data.getExtras();
                String videoPath = "";
                long size = 0;
                if(null != extras) {
                    // 视频路径 ：MediaStore.Audio.Media.DATA
                    videoPath = extras.getString("path");
                    //视频大小 （/M）
                    size = extras.getLong("size2");
                }
                //如果大小超过100M
                if (size / NO1024 / NO1024 > NO100) {
                    ToastUtils.show("视频不能超过100M");
                    return;
                }
                MediaMetadataRetriever media = new MediaMetadataRetriever();
                // videoPath 本地视频的路径
                media.setDataSource(videoPath);
                Bitmap bitmap = media.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
                if (bitmap == null) {
                    ToastUtils.show("视频无效,重新选择");
                    return;
                }
                localVideoPath = videoPath;
                //视频第一帧图片路径
                String imagePath = AddImageOrVideo.saveBitmap(this, bitmap);
                //上传视频
                uploadVideo(imagePath, videoPath);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //选择图片
        if (requestCode == IMG_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //进入选择页
                AddImageOrVideo.localImg(AddProductPlanActivity.this, IMG_REQUEST_CODE, (MAX_IMG_NUMBER - getListCover()));
            }
        } else if (GET_CAMERA_REQUEST == requestCode) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Bundle from = new Bundle();
                from.putString("from", "1");
                JumpIntent.jump(AddProductPlanActivity.this, PublishInfoVideoActivity.class, from, NO50);
            }
        }

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);

    }

    /**
     * 定位权限成功
     *
     * @param requestCode 请求码
     * @param perms 权限
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        initLocation();
    }

    /**
     * 定位权限失败
     *
     * @param requestCode 请求码
     * @param perms 权限
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        ToastUtils.show("请求权限失败");
    }

    public void setEditContent(ProductPlanBean.ContentBean editContent) {
        //标题
        etTitle.setText(editContent.getTitle());
        //描述
        etDescribe.setText(editContent.getDescription());
        int modelType = editContent.getModelType();
        type = Integer.toString(modelType);
        //封面图
        List<ProductPlanBean.ContentBean.CurrencyAttachmentVos> list = editContent.getCurrencyAttachmentVos();
        if (null == list || list.size() <= 0) {
            return;
        }
        //视频
        if (editContent.getCurrencyAttachmentVos() != null && editContent.getCurrencyAttachmentVos().get(0).getVideoCoverAddress() != null) {
            mVideoPath = list.get(0).getAddress();
            String videoImgPath = list.get(0).getVideoCoverAddress();
            listCover.add(videoImgPath);
            mViewPagerAdapter.setDatas(listCover);
            setVideoHide();
            localVideoPath = mVideoPath;
            //图片
        } else {
            for (int i = 0; i < list.size(); i++) {
                listCover.add(list.get(i).getAddress());
                mViewPagerAdapter.setDatas(listCover);
                setImgHide();
            }
        }
    }

    @Subscribe
    public void onShareEventMain(EventBean event) {
        if (FINISHACTIVITY.equals(event.getMsg())) {
            finish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if(null != mLocationClient){
            mLocationClient.stopLocation();
        }
    }
}
