package com.yst.onecity.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.MyLog;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.utils.AddImageOrVideo;
import com.yst.onecity.utils.PhotoUtil;
import com.yst.onecity.view.AbstractTakePhotoDialog;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 上传资质页面
 *
 * @author wuxiaofang
 * @version 1.1.0
 * @date 2018/5/21
 */

public class UploadZizhiActivity extends BaseActivity implements View.OnClickListener {
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
    @BindView(R.id.tv_id)
    TextView tvId;
    @BindView(R.id.iv_id01)
    ImageView ivId01;
    @BindView(R.id.iv_id02)
    ImageView ivId02;
    @BindView(R.id.iv_team)
    ImageView ivTeam;
    @BindView(R.id.iv_yingye_zhizhao)
    ImageView ivYingyeZhizhao;
    private AbstractTakePhotoDialog dialog;
    private String cameraPath;
    private int type;
    private String id01Uri = "";
    private String id02Uri = "";
    private String teamUri = "";
    private String zhizhaoUri = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_upload_zizhi;
    }

    @Override
    public void initData() {
        initTitleBar("上传资质");
        setRightText("完成");
        id01Uri = getIntent().getStringExtra("id01Url");
        id02Uri = getIntent().getStringExtra("id02Url");
        teamUri = getIntent().getStringExtra("team");
        zhizhaoUri = getIntent().getStringExtra("zhizhao");

        if (!TextUtils.isEmpty(id01Uri)){
            Glide.with(UploadZizhiActivity.this).load(id01Uri).error(R.mipmap.idzhengmian).into(ivId01);
        }
        if (!TextUtils.isEmpty(id02Uri)){
            Glide.with(UploadZizhiActivity.this).load(id02Uri).error(R.mipmap.idfanmian).into(ivId02);
        }
        if (!TextUtils.isEmpty(teamUri)){
            Glide.with(UploadZizhiActivity.this).load(teamUri).error(R.mipmap.team).into(ivTeam);
        }
        if (!TextUtils.isEmpty(zhizhaoUri)){
            Glide.with(UploadZizhiActivity.this).load(zhizhaoUri).error(R.mipmap.zhizhao).into(ivYingyeZhizhao);
        }

        EasyPermissions.requestPermissions(this, "请打开照相机,和读写权限", 100, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
        setRightTextColor(ContextCompat.getColor(this, R.color.color_FF9C9C9C));
        setRightTextViewOnClickListener(this);
    }

    @OnClick({R.id.iv_id01, R.id.iv_id02, R.id.iv_team, R.id.iv_yingye_zhizhao})
    public void onPhotoClick(View view) {
        if (!Utils.isClickable()) {
            return;
        }
        switch (view.getId()) {
            case R.id.iv_id01:
                type = Constant.NO0;
                getPhoto();
                break;
            case R.id.iv_id02:
                type = Constant.NO1;
                getPhoto();
                break;
            case R.id.iv_team:
                type = Constant.NO2;
                getPhoto();
                break;
            case R.id.iv_yingye_zhizhao:
                type = Constant.NO3;
                getPhoto();
                break;
            default:
                break;
        }
    }

    /**
     * 获取图片
     */
    private void getPhoto() {
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
                AddImageOrVideo.localImg(UploadZizhiActivity.this, Constant.REQUESTALBUMN, 1);
            }
        };
        dialog.showDialog();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constant.REQUESTCAMERA:
                Uri clipPhotoUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), cameraPath));
                File imagePath = PhotoUtil.getFileFromMediaUri(this, clipPhotoUri);
                switch (type) {
                    case Constant.NO0:
                        AddImageOrVideo.upLoadImg(this, imagePath.toString(), new AddImageOrVideo.UploadImgListener() {
                            @Override
                            public void uploadImgListener(String msg) {
                                Glide.with(UploadZizhiActivity.this).load(msg).error(R.mipmap.idzhengmian).into(ivId01);
                                id01Uri = msg;
                            }
                        });
                        break;
                    case Constant.NO1:
                        AddImageOrVideo.upLoadImg(this, imagePath.toString(), new AddImageOrVideo.UploadImgListener() {
                            @Override
                            public void uploadImgListener(String msg) {
                                Glide.with(UploadZizhiActivity.this).load(msg).error(R.mipmap.idfanmian).into(ivId02);
                                id02Uri = msg;
                            }
                        });
                        break;
                    case Constant.NO2:
                        AddImageOrVideo.upLoadImg(this, imagePath.toString(), new AddImageOrVideo.UploadImgListener() {
                            @Override
                            public void uploadImgListener(String msg) {
                                Glide.with(UploadZizhiActivity.this).load(msg).error(R.mipmap.team).into(ivTeam);
                                teamUri = msg;
                            }
                        });
                        break;
                    case Constant.NO3:
                        AddImageOrVideo.upLoadImg(this, imagePath.toString(), new AddImageOrVideo.UploadImgListener() {
                            @Override
                            public void uploadImgListener(String msg) {
                                Glide.with(UploadZizhiActivity.this).load(msg).error(R.mipmap.zhizhao).into(ivYingyeZhizhao);
                                zhizhaoUri = msg;
                            }
                        });

                        break;
                    default:
                        break;
                }
                break;
            case Constant.REQUESTALBUMN:
                if (null != data) {
                    List<String> list = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    if (list.size() > 0) {
                        File imagePath1 = PhotoUtil.getFileFromMediaUri(this, Uri.fromFile(new File(list.get(0))));
                        MyLog.e("sss11", "--imagePath1: " + String.valueOf(imagePath1));
                        switch (type) {
                            case Constant.NO0:
                                AddImageOrVideo.upLoadImg(this, String.valueOf(imagePath1), new AddImageOrVideo.UploadImgListener() {
                                    @Override
                                    public void uploadImgListener(String msg) {
                                        MyLog.e("sss","ivId01-----"+msg);
                                        Glide.with(UploadZizhiActivity.this).load(msg).error(R.mipmap.idzhengmian).into(ivId01);
                                        id01Uri = msg;
                                    }
                                });
                                break;
                            case Constant.NO1:
                                AddImageOrVideo.upLoadImg(this, String.valueOf(imagePath1), new AddImageOrVideo.UploadImgListener() {
                                    @Override
                                    public void uploadImgListener(String msg) {
                                        MyLog.e("sss","ivId02-----"+msg);
                                        Glide.with(UploadZizhiActivity.this).load(msg).error(R.mipmap.idfanmian).into(ivId02);
                                        id02Uri = msg;
                                    }
                                });
                                break;
                            case Constant.NO2:
                                AddImageOrVideo.upLoadImg(this, String.valueOf(imagePath1), new AddImageOrVideo.UploadImgListener() {
                                    @Override
                                    public void uploadImgListener(String msg) {
                                        MyLog.e("sss","ivTeam-----"+msg);
                                        Glide.with(UploadZizhiActivity.this).load(msg).error(R.mipmap.team).into(ivTeam);
                                        teamUri = msg;
                                    }
                                });
                                break;
                            case Constant.NO3:
                                AddImageOrVideo.upLoadImg(this, String.valueOf(imagePath1), new AddImageOrVideo.UploadImgListener() {
                                    @Override
                                    public void uploadImgListener(String msg) {
                                        MyLog.e("sss","ivYingyeZhizhao-----"+msg);
                                        Glide.with(UploadZizhiActivity.this).load(msg).error(R.mipmap.zhizhao).into(ivYingyeZhizhao);
                                        zhizhaoUri = msg;
                                    }
                                });
                                break;
                            default:
                                break;
                        }
                    }
                    break;
                }
            default:
                break;
        }
    }

    /**
     * 上传图片
     *
     * @param imgPath
     */
    private void uploadImage(final String imgPath, final ImageView imageView) {
        AddImageOrVideo.upLoadImg(this, imgPath, new AddImageOrVideo.UploadImgListener() {
            @Override
            public void uploadImgListener(String msg) {
                Glide.with(UploadZizhiActivity.this).load(imgPath).into(imageView);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (TextUtils.isEmpty(id01Uri)) {
            ToastUtils.show("请上传身份证正面照片");
            return;
        }
        if (TextUtils.isEmpty(id02Uri)) {
            ToastUtils.show("请上传身份证反面照片");
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("id01", id01Uri);
        intent.putExtra("id02", id02Uri);
        intent.putExtra("team", teamUri);
        intent.putExtra("zhizhao", zhizhaoUri);
        setResult(Constant.UPLOAD_ZIZHI, intent);
        finish();
    }
}
