package com.yst.onecity.activity.teammanage;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.MyLog;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.utils.Utils;
import com.yst.basic.framework.view.roundedimageview.RoundedImageView;
import com.yst.im.imchatlibrary.model.SetLogoModel;
import com.yst.im.imchatlibrary.model.SetNameModel;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.activity.SaleClassActivity;
import com.yst.onecity.activity.UploadZizhiActivity;
import com.yst.onecity.activity.mine.setting.PersonDetailActivity;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.AddImageOrVideo;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.MyTextWatcher;
import com.yst.onecity.utils.PhotoUtil;
import com.yst.onecity.view.AbstractTakePhotoDialog;
import com.yst.onecity.view.dialog.AbstractDeleteDialog;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * 创建团队
 *
 * @author wuxiaofang
 * @version 1.1.0
 * @date 2018/5/23
 */

public class CreateTeamActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_add_head)
    TextView tvAddHead;
    @BindView(R.id.tv_team_name)
    TextView tvTeamName;
    @BindView(R.id.et_team_intro)
    EditText etTeamIntro;
    @BindView(R.id.tv_team_address)
    TextView tvTeamAddress;
    @BindView(R.id.tv_jingying_class)
    TextView tvJingyingClass;
    @BindView(R.id.tv_upload_zizhi)
    TextView tvUploadZizhi;
    @BindView(R.id.iv_head)
    RoundedImageView ivHead;
    @BindView(R.id.tv_no_input)
    TextView tvNoInput;
    private AbstractTakePhotoDialog dialog;
    private String cameraPath;
    private String cropPath;
    private String headUrl = "";
    private String saleClassId = "";
    private String id01Url = "";
    private String id02Url = "";
    private String team = "";
    private String zhizhao = "";
    private String provinceId = "";
    private String cityId = "";
    private String districtId = "";
    private String longtitue = "";
    private String lantitue = "";
    private String detailsAddress = "";
    private File cropFile;
    private String provinceName;
    private String cityName;
    private String districtName;

    @Override
    public int getLayoutId() {
        return R.layout.activity_creats_team;
    }

    @Override
    public void initData() {
        tvMainTitle.setText("创建团队");
        setRightText("完成");
        setRightTextVisibility(View.VISIBLE);
        setRightTextColor(ContextCompat.getColor(this, R.color.gray_line));
        setRightTextViewClickable(true);
        MyTextWatcher watcher = new MyTextWatcher(Constant.NO100, etTeamIntro, this, tvNoInput);
        watcher.setIsNoInput(true);
        etTeamIntro.addTextChangedListener(watcher);
        setRightTextViewOnClickListener(this);
    }

    @OnClick({R.id.iv_head, R.id.tv_team_address, R.id.tv_jingying_class, R.id.tv_upload_zizhi})
    public void onViewClick(View view) {
        if (!Utils.isClickable()) {
            return;
        }
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            //点击添加头像
            case R.id.iv_head:
                String cameraPermission = Manifest.permission.CAMERA;
                String read = Manifest.permission.READ_EXTERNAL_STORAGE;
                if (EasyPermissions.hasPermissions(CreateTeamActivity.this, cameraPermission, read)) {
                    addHead();
                } else {
                    EasyPermissions.requestPermissions(this, "请打开照相机和读写权限", 100, cameraPermission, read);
                }
                break;
            //团队地址
            case R.id.tv_team_address:
                bundle.putString("provinceName",provinceName);
                bundle.putString("cityName",cityName);
                bundle.putString("districtName",districtName);
                bundle.putString("detailAddress",detailsAddress);
                JumpIntent.jump(this, TeamAddressActivity.class,bundle, Constant.TEAM_ADDRESS);
                break;
            //经营品类
            case R.id.tv_jingying_class:
                JumpIntent.jump(this, SaleClassActivity.class, Constant.NO18);
                break;
            //上传资质
            case R.id.tv_upload_zizhi:
                bundle.putString("id01Url",id01Url);
                bundle.putString("id02Url",id02Url);
                bundle.putString("team",team);
                bundle.putString("zhizhao",zhizhao);
                JumpIntent.jump(this, UploadZizhiActivity.class, bundle,Constant.UPLOAD_ZIZHI);
                break;
            default:
                break;
        }
    }

    /**
     * 添加头像
     */
    private void addHead() {
        dialog = new AbstractTakePhotoDialog(this) {
            @Override
            public void takePhoto() {
                cameraPath = System.currentTimeMillis() + ".png";
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                        Environment.getExternalStorageDirectory(), cameraPath)));
                startActivityForResult(intent, Constant.REQUESTCAMERA);
            }

            @Override
            public void takePicture() {
                Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
                albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(albumIntent, Constant.REQUESTALBUMN);
            }
        };
        dialog.showDialog();
    }

    @Override
    public void onClick(View view) {
        if (!Utils.isClickable()) {
            return;
        }
        createTamCompleteNet();
    }

    @OnClick(R.id.ll_back)
    public void onViewBack() {
        if (!Utils.isClickable()) {
            return;
        }
        isSureNoEdit();
    }

    /**
     * 放弃编辑的弹框
     */
    private void isSureNoEdit() {
        AbstractDeleteDialog dialog = new AbstractDeleteDialog(this) {
            @Override
            public void sureClick() {
                finish();
            }
        };
        dialog.setTitleisVisible(false);
        dialog.setText("是否放弃创建团队", "放弃创建", "再看看");
        dialog.setButtonColor(ContextCompat.getColor(this, R.color.color_FF4852), ContextCompat.getColor(this, R.color.black));
        dialog.showDialog();
    }

    /**
     * 创建团队完成
     */
    private void createTamCompleteNet() {
        String teamIntro = etTeamIntro.getText().toString();
        final String teamName = tvTeamName.getText().toString();
        if (TextUtils.isEmpty(teamName)) {
            ToastUtils.show("请添加团队名称");
            return;
        }
        if (TextUtils.isEmpty(teamIntro)) {
            ToastUtils.show("请填写团队简介");
            return;
        }
        if (TextUtils.isEmpty(provinceId) || TextUtils.isEmpty(cityId) || TextUtils.isEmpty(districtId)) {
            ToastUtils.show("请选择地址");
            return;
        }
        if (TextUtils.isEmpty(detailsAddress)) {
            ToastUtils.show("请填写详细地址");
            return;
        }
        if (TextUtils.isEmpty(lantitue) || TextUtils.isEmpty(longtitue)) {
            ToastUtils.show("未获取经纬度");
            return;
        }
        if (TextUtils.isEmpty(saleClassId)) {
            ToastUtils.show("请选择经营品类");
            return;
        }
        if (TextUtils.isEmpty(id01Url)) {
            ToastUtils.show("请上传身份证正面照片");
            return;
        }
        if (TextUtils.isEmpty(id02Url)) {
            ToastUtils.show("请上传身份证反面照片");
            return;
        }

        RequestApi.completeCreateTeam(String.valueOf(App.manager.getId()),
                teamName, teamIntro, headUrl, saleClassId,
                detailsAddress, id01Url, id02Url, team, zhizhao,
                cityId, provinceId, districtId, longtitue, lantitue,
                new AbstractNetWorkCallback<MsgBean>() {
                    @Override
                    public void onSuccess(MsgBean msgBean) {
                        if (null != msgBean) {
                            if (msgBean.getCode() == Constant.NO1) {
                                SetLogoModel setLogoModel = new SetLogoModel(CreateTeamActivity.this);
                                setLogoModel.getSetLogo(headUrl);
                                SetNameModel setNameModel = new SetNameModel(CreateTeamActivity.this);
                                setNameModel.getSetName(teamName);
                                ToastUtils.show(msgBean.getMsg());
                                finish();
                            } else {
                                ToastUtils.show(msgBean.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(String errorMsg) {
                        ToastUtils.show(errorMsg);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constant.REQUESTCAMERA:
                if (!TextUtils.isEmpty(cameraPath)) {
                    Uri photoUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), cameraPath));
                    startPhotoZoom(photoUri);
                }
                break;
            case Constant.REQUESTALBUMN:
                if (null != data) {
                    Uri uri = data.getData();
                    startPhotoZoom(uri);
                }
                break;
            case Constant.REQUESTCROP:
                MyLog.e("sss", "--crop: " + cropFile);
                if (cropFile.exists()) {
                    MyLog.e("sss", "--crop: " + cropFile);
                    Uri clipPhotoUri = Uri.fromFile(new File(cropFile.getAbsolutePath()));
                    File imagePath = PhotoUtil.getFileFromMediaUri(this, clipPhotoUri);
                    AddImageOrVideo.upLoadImg(this, imagePath.toString(), new AddImageOrVideo.UploadImgListener() {
                        @Override
                        public void uploadImgListener(String msg) {
                            Glide.with(CreateTeamActivity.this).load(msg).into(ivHead);
                            headUrl = msg;
                        }
                    });
                }
                break;
            case Constant.NO18:
                MyLog.e("sss", "--saale:" + data);
                if (resultCode == Constant.NO0) {
                    if (null != data) {
                        String saleId = data.getStringExtra("saleId");
                        String sortName = data.getStringExtra("sortName");
                        tvJingyingClass.setText(ConstUtils.getStringNoEmpty(sortName));
                        if (!TextUtils.isEmpty(saleId)) {
                            saleClassId = saleId;
                        }
                    }
                }
            case Constant.UPLOAD_ZIZHI:
                MyLog.e("sss", "--zizhi:" + data);
                if (resultCode == Constant.UPLOAD_ZIZHI) {
                    if (null != data) {
                        id01Url = data.getStringExtra("id01");
                        id02Url = data.getStringExtra("id02");
                        team = data.getStringExtra("team");
                        zhizhao = data.getStringExtra("zhizhao");
                    }
                }
                break;
            case Constant.TEAM_ADDRESS:
                MyLog.e("sss", "--dataaddress:" + data);
                if (resultCode == Constant.TEAM_ADDRESS) {
                    if (null != data) {
                        provinceName = data.getStringExtra("provinceName");
                        cityName = data.getStringExtra("cityName");
                        districtName = data.getStringExtra("districtName");
                        provinceId = data.getStringExtra("provinceId");
                        districtId = data.getStringExtra("districtId");
                        cityId = data.getStringExtra("cityId");
                        longtitue = data.getStringExtra("longitude");
                        lantitue = data.getStringExtra("latitude");
                        detailsAddress = data.getStringExtra("detailAddress");
                        MyLog.e("sss", "--provinceId--:" + provinceId);
                        MyLog.e("sss", "--cityId--:" + cityId);
                        MyLog.e("sss", "--districtId--:" + districtId);
                        MyLog.e("sss", "--detailsAddress--:" + detailsAddress);
                        MyLog.e("sss", "--longtitue--:" + longtitue);
                        MyLog.e("sss", "--lantitue--:" + lantitue);

                        tvTeamAddress.setText(ConstUtils.getStringNoEmpty(detailsAddress));
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 对图片进行裁剪
     *
     * @param uri uri
     */
    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 100);
        intent.putExtra("outputY", 100);
        cropPath = System.currentTimeMillis() + ".png";
        cropFile = new File(Environment.getExternalStorageDirectory(), cropPath);
        intent.putExtra("output", Uri.fromFile(cropFile));
        intent.putExtra("outputFormat", "JPEG");
        startActivityForResult(intent, Constant.REQUESTCROP);
    }
}
