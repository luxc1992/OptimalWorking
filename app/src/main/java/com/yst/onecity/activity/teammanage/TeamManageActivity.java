package com.yst.onecity.activity.teammanage;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.utils.Utils;
import com.yst.basic.framework.view.roundedimageview.RoundedImageView;
import com.yst.im.imchatlibrary.model.SetLogoModel;
import com.yst.im.imchatlibrary.model.SetNameModel;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.TeamInfoBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.AddImageOrVideo;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.PhotoUtil;
import com.yst.onecity.view.AbstractTakePhotoDialog;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

import static com.yst.onecity.Constant.NO2;
import static com.yst.onecity.R.id.tv_server_range;


/**
 * 团队管理
 *
 * @author wuxiaofang
 * @version 1.1.0
 * @date 2018/5/18
 */

public class TeamManageActivity extends BaseActivity {

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
    @BindView(R.id.iv_head_team)
    RoundedImageView ivHeadTeam;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_base_info)
    TextView tvBaseInfo;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.ll_sala_state)
    LinearLayout llSalaState;
    @BindView(R.id.tv_map)
    TextView tvMap;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(tv_server_range)
    TextView tvServerRange;
    @BindView(R.id.tv_sale_class)
    TextView tvSaleClass;
    @BindView(R.id.ll_sale_class)
    LinearLayout llSaleClass;
    String head = "";
    private AbstractTakePhotoDialog dialog;
    private String cameraPath;
    private String cropPath;
    private String detailAddress;
    private String phone;
    private String teamIntro;
    private String cityId;
    private String provinceId;
    private String cityName;
    private String districtId;
    private String nickName;
    private String provinceName;
    private String districtName;
    private String advisorId;
    private File cropFile;
    private String headUrl;
    private int saleStatus;

    @Override
    public int getLayoutId() {
        return R.layout.activity_team_manage;
    }

    @Override
    public void initData() {
        initTitleBar(getString(R.string.team_manage));
        tvServerRange.setVisibility(View.GONE);
        if (null != getIntent()) {
            advisorId = String.valueOf(getIntent().getIntExtra("advisorId", 0));
        }

        xiugaiHead();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getTeamInfo();
    }

    /**
     * 获取团队信息
     */
    private void getTeamInfo() {
        RequestApi.getTeamInfo(advisorId, App.manager.getPhoneNum(), App.manager.getUuid(), new AbstractNetWorkCallback<TeamInfoBean>() {
            @Override
            public void onSuccess(TeamInfoBean teamInfoBean) {
                if (null != teamInfoBean) {
                    if (teamInfoBean.getCode() == Constant.NO1 && teamInfoBean.getContent() != null) {
                        TeamInfoBean.ContentBean content = teamInfoBean.getContent();
                        detailAddress = content.getAddress();
                        tvMap.setText(ConstUtils.getStringNoEmpty(detailAddress));
                        cityId = String.valueOf(content.getCityId());
                        String categoryName = content.getCategoryName();
                        provinceId = String.valueOf(content.getProvinceId());
                        teamIntro = content.getContent();
                        headUrl = content.getImgUrl();
                        cityName = content.getCityName();
                        phone = content.getTelPhone();
                        districtId = String.valueOf(content.getCountyId());
                        nickName = content.getNikeName();
                        provinceName = content.getProvinceName();
                        districtName = content.getCountyName();
                        saleStatus = content.getStatus();
                        tvName.setText(nickName);
                        Glide.with(TeamManageActivity.this).load(headUrl).error(R.mipmap.default_head).into(ivHeadTeam);
                        tvLocation.setText(provinceName + "-" + cityName + "-" + districtName);
                        tvType.setText(categoryName);
                        tvSaleClass.setText(categoryName);
                        switch (saleStatus) {
                            case Constant.NO0:
                                tvState.setText("营业中");
                                break;
                            case Constant.NO1:
                                tvState.setText("暂停营业");
                                break;
                            case NO2:
                                tvState.setText("已关门");
                                break;
                            default:
                                break;
                        }
                    } else {
                        ToastUtils.show(teamInfoBean.getMsg());
                    }
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    /**
     * 修改头像
     */
    private void xiugaiHead() {
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
    }


    @OnClick({R.id.iv_head_team, R.id.tv_base_info, R.id.ll_sala_state, R.id.ll_address, tv_server_range, R.id.ll_sale_class})
    public void click(View view) {
        if (!Utils.isClickable()) {
            return;
        }
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            //头像
            case R.id.iv_head_team:
                String cameraPermission = Manifest.permission.CAMERA;
                String read = Manifest.permission.READ_EXTERNAL_STORAGE;
                if (EasyPermissions.hasPermissions(TeamManageActivity.this, cameraPermission, read)) {
                    dialog.showDialog();
                } else {
                    EasyPermissions.requestPermissions(this, "请打开照相机和读写权限", 100, cameraPermission, read);
                }
                break;
            //基本信息
            case R.id.tv_base_info:
                int stateTpe = 0;
                String saleState = tvState.getText().toString();
                /*
                 * 0、营业 1、停止营业 2、关闭
                 */
                switch (saleState) {
                    case Constant.ON_SALE:
                        stateTpe = Constant.NO0;
                        break;
                    case Constant.PAUSE_SALE:
                        stateTpe = Constant.NO1;
                        break;
                    case Constant.CLOSE_DOOR:
                        stateTpe = NO2;
                        break;
                    default:
                        break;
                }
                bundle.putString("advisorId", advisorId);
                bundle.putString("headUrl", headUrl);
                bundle.putString("teamName", nickName);
                bundle.putString("teamAddress", detailAddress);
                bundle.putString("teamPhone", phone);
                bundle.putString("teamIntro", teamIntro);
                bundle.putInt("saleState", stateTpe);
                bundle.putString("provinceId", provinceId);
                bundle.putString("cityId", cityId);
                bundle.putString("districtId", districtId);
                JumpIntent.jump(this, TeamInfoActivity.class, bundle);
                break;
            //营业状态
            case R.id.ll_sala_state:
                Bundle bundle1 = new Bundle();
                bundle1.putString("type", tvState.getText().toString());
                JumpIntent.jump(this, OnSaleStateActvity.class, bundle1, Constant.NO0);
                break;
            //团队地址
            case R.id.ll_address:
                bundle.putBoolean("isChangeAddress",true);
                bundle.putString("provinceName", provinceName);
                bundle.putString("provinceId", provinceId);
                bundle.putString("cityName", cityName);
                bundle.putString("cityId", cityId);
                bundle.putString("districtId", districtId);
                bundle.putString("detailAddress", detailAddress);
                bundle.putString("districtName", districtName);
                bundle.putString("districtName", districtName);
                JumpIntent.jump(this, TeamAddressActivity.class, bundle, Constant.NO2000 * NO2);
                break;
            //经营品类
            case R.id.ll_sale_class:
                break;
            default:
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constant.REQUESTCAMERA:
                Uri photoUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), cameraPath));
                startPhotoZoom(photoUri);
                break;
            case Constant.REQUESTALBUMN:
                if (null != data) {
                    Uri uri = data.getData();
                    startPhotoZoom(uri);
                }
                break;
            case Constant.REQUESTCROP:
                if (cropFile.exists()) {
                    Uri clipPhotoUri = Uri.fromFile(new File(cropFile.getAbsolutePath()));
                    File imagePath = PhotoUtil.getFileFromMediaUri(this, clipPhotoUri);
                    MyLog.e("sss",headUrl+"      "+imagePath);
                    AddImageOrVideo.upLoadImg(this, imagePath.toString(), new AddImageOrVideo.UploadImgListener() {
                        @Override
                        public void uploadImgListener(String msg) {
                            headUrl = msg;
                            comminSaleStatus(2, "", msg,0,provinceId,cityId,districtId);
                        }
                    });
                }
                break;
            //修改营业状态
            case Constant.NO0:
                if (resultCode == Constant.NO1) {
                    int type = data.getIntExtra("type", 0);
                    comminSaleStatus(0, "","", type,provinceId,cityId,districtId);
                }
                break;
            //修改地址
            case Constant.NO2000 * NO2:
                if (resultCode == Constant.TEAM_ADDRESS) {
                    String address = data.getStringExtra("detailAddress");
                    String provinceId = data.getStringExtra("provinceId");
                    String cityId = data.getStringExtra("cityId");
                    String districtId = data.getStringExtra("districtId");
                    comminSaleStatus(1, address, "",0,provinceId,cityId,districtId);
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


    /**
     * 提交信息
     *
     * @param type    0 修改营业状态  1 修改地址 2 修改头像
     * @param address 地址
     * @param status  营业状态
     */
    private void comminSaleStatus(int type, final String address, final String head, final int status,String provinceId,String cityId,String districtId) {
        if (type == 0) {
            RequestApi.updateTeamInfo(advisorId, App.manager.getUuid(), headUrl, nickName, detailAddress, App.manager.getPhoneNum(),phone, teamIntro, String.valueOf(status), cityId, provinceId, districtId, new AbstractNetWorkCallback<MsgBean>() {
                @Override
                public void onSuccess(MsgBean teamInfoBean) {
                    if (null != teamInfoBean) {
                        if (teamInfoBean.getCode() == Constant.NO1) {
                            switch (status) {
                                case 0:
                                    tvState.setText(getString(R.string.onsale));
                                    break;
                                case 1:
                                    tvState.setText(getString(R.string.pause_sale));
                                    break;
                                case 2:
                                    tvState.setText(getString(R.string.close_door));
                                    break;
                                default:
                                    break;
                            }
                            getTeamInfo();
                        } else {
                            ToastUtils.show(teamInfoBean.getMsg());
                        }
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    ToastUtils.show(errorMsg);
                }
            });
        } else if(type == 1){
            RequestApi.updateTeamInfo(advisorId, App.manager.getUuid(), headUrl, nickName, address, App.manager.getPhoneNum(),phone, teamIntro, String.valueOf(saleStatus), cityId, provinceId, districtId, new AbstractNetWorkCallback<MsgBean>() {
                @Override
                public void onSuccess(MsgBean teamInfoBean) {
                    if (null != teamInfoBean) {
                        if (teamInfoBean.getCode() == Constant.NO1) {
                            tvMap.setText(ConstUtils.getStringNoEmpty(address));
                            getTeamInfo();
                        } else {
                            ToastUtils.show(teamInfoBean.getMsg());
                        }
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    ToastUtils.show(errorMsg);
                }
            });
        }else{
            RequestApi.updateTeamInfo(advisorId, App.manager.getUuid(), head, nickName, detailAddress, App.manager.getPhoneNum(),phone, teamIntro, String.valueOf(saleStatus), cityId, provinceId, districtId, new AbstractNetWorkCallback<MsgBean>() {
                @Override
                public void onSuccess(MsgBean teamInfoBean) {
                    if (null != teamInfoBean) {
                        if (teamInfoBean.getCode() == Constant.NO1) {
                            SetLogoModel setLogoModel = new SetLogoModel(TeamManageActivity.this);
                            setLogoModel.getSetLogo(head);
                            Glide.with(TeamManageActivity.this).load(head).into(ivHeadTeam);
                            getTeamInfo();
                        } else {
                            ToastUtils.show(teamInfoBean.getMsg());
                        }
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    ToastUtils.show(errorMsg);
                }
            });
        }
    }
}
