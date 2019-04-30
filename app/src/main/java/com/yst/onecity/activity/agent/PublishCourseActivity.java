package com.yst.onecity.activity.agent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.R;
import com.yst.onecity.adapter.CourseShareAdapter;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.agent.ByIdBean;
import com.yst.onecity.bean.agent.PublishCourseBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.AddImageOrVideo;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.PhotoUtil;
import com.yst.onecity.view.AbstractTakePhotoDialog;
import com.yst.onecity.view.AbstractUnBindDialog;
import com.yst.onecity.view.EditTextWatcher;
import com.yst.onecity.view.MyListView;
import com.yst.onecity.view.dialog.AbstractDeleteDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yst.onecity.Constant.HTTP;
import static com.yst.onecity.Constant.HTTPS;
import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO150;
import static com.yst.onecity.Constant.NO2000;
import static com.yst.onecity.activity.mine.setting.PersonDetailActivity.REQUEST_IMAGE;

/**
 * 发布课题页面
 *
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/5/17
 */
public class PublishCourseActivity extends BaseActivity {

    @BindView(R.id.tv_right)
    TextView mTvRight;
    @BindView(R.id.iv_course_cover)
    ImageView mIvCourseCover;
    @BindView(R.id.txt_course_addcover)
    TextView mTxtCourseAddcover;
    @BindView(R.id.iv_course_delete)
    ImageView mIvCourseDelete;
    @BindView(R.id.et_course_title)
    EditText mEtCourseTitle;
    @BindView(R.id.et_course_content)
    EditText mEtCourseContent;
    @BindView(R.id.txt_course_count)
    TextView mTxtCourseCount;
    @BindView(R.id.txt_course_type)
    TextView mTxtCourseType;
    @BindView(R.id.lv_course)
    MyListView mLvCourse;
    @BindView(R.id.rel_course_type)
    RelativeLayout mRelCourseType;
    @BindView(R.id.rel_course_share)
    RelativeLayout mRelCourseShare;

    private List<PublishCourseBean.ContentBean> mList = new ArrayList<>();
    public final static int TYPE_REQUEST = 1;
    public final static int SHARE_REQUEST = 2;
    private int pos = 0;
    private String tempPath;
    private String cipPath;
    private File cropFile;
    public static final int REQUEST_CAMERA = 10001;
    public static final int PHOTO_REQUEST_CUT = 10003;
    private CourseShareAdapter adapter;
    private String mTypeName;
    private int mTypeId;
    private String mImageAddress;
    private String consultationId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_publish_course;
    }

    @Override
    public void initData() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setTitleBarTitle("发布课题");
        mTvRight.setText("发布");
        mTvRight.setTextColor(ContextCompat.getColor(this, R.color.color_53A155));
        mTvRight.setVisibility(View.VISIBLE);
        mEtCourseContent.addTextChangedListener(new EditTextWatcher(mEtCourseContent, mTxtCourseCount, NO150, this));
        initAdapter();
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        adapter = new CourseShareAdapter(mList, this);
        mLvCourse.setAdapter(adapter);
    }

    @OnClick({R.id.ll_back, R.id.tv_right, R.id.txt_course_addcover, R.id.iv_course_delete, R.id.rel_course_type, R.id.rel_course_share})
    public void onViewClicked(View view) {
        if (!Utils.isClickable()) {
            return;
        }
        switch (view.getId()) {
            case R.id.ll_back:
                AbstractDeleteDialog dialog = new AbstractDeleteDialog(this) {
                    @Override
                    public void sureClick() {
                        PublishCourseActivity.this.finish();
                    }
                };
                dialog.setText("您的课题还没有发布哦，确认退出么？", "确定", "取消");
                dialog.showDialog();
                break;
            //发布
            case R.id.tv_right:
                ConstUtils.setFilter(mTvRight, NO2000);
                if (TextUtils.isEmpty(mImageAddress)) {
                    ToastUtils.show("请添加封面图");
                    return;
                }
                if (TextUtils.isEmpty(mEtCourseTitle.getText().toString().trim())) {
                    ToastUtils.show("请添加标题");
                    return;
                }
                if (TextUtils.isEmpty(mEtCourseContent.getText().toString().trim())) {
                    ToastUtils.show("请添加简介");
                    return;
                }
                if (TextUtils.isEmpty(mTypeId + "") || TextUtils.isEmpty(mTypeName)) {
                    ToastUtils.show("请添加分类");
                    return;
                }
                if (TextUtils.isEmpty(consultationId) || mList.size() == NO0) {
                    ToastUtils.show("请添加分享");
                    return;
                }
                requestPublish();
                break;
            //添加封面图
            case R.id.txt_course_addcover:
                showCameraDialog();
                break;
            //删除图片
            case R.id.iv_course_delete:
                mIvCourseCover.setImageBitmap(null);
                mImageAddress = null;
                mIvCourseDelete.setVisibility(View.GONE);
                mTxtCourseAddcover.setVisibility(View.VISIBLE);
                break;
            //分类
            case R.id.rel_course_type:
                Bundle bundle = new Bundle();
                bundle.putInt("isClick", pos);
                JumpIntent.jump(this, CourseTypeActivity.class, bundle, TYPE_REQUEST);
                break;
            //分享
            case R.id.rel_course_share:
                JumpIntent.jump(this, AddShareActivity.class, SHARE_REQUEST);
                break;
            default:
                break;
        }
    }

    /**
     * 发布课题接口
     */
    private void requestPublish() {
        RequestApi.publishCourse(
                String.valueOf(App.manager.getId()),
                mImageAddress,
                mEtCourseTitle.getText().toString().trim(),
                mEtCourseContent.getText().toString().trim(),
                String.valueOf(mTypeId),
                consultationId,
                new AbstractNetWorkCallback<MsgBean>() {
                    @Override
                    public void onSuccess(MsgBean msgBean) {
                        if (msgBean.getCode() == NO1) {
                            AbstractUnBindDialog dialog = new AbstractUnBindDialog(PublishCourseActivity.this) {
                                @Override
                                protected void sureClick() {
                                    PublishCourseActivity.this.finish();
                                }
                            };
                            dialog.setText(msgBean.getMsg());
                            dialog.showDialog();
                        } else {
                            ToastUtils.show(msgBean.getMsg());
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
     * 拍照/相册弹框
     */
    private void showCameraDialog() {
        AbstractTakePhotoDialog dialog = new AbstractTakePhotoDialog(this) {
            @Override
            public void takePhoto() {
                tempPath = System.currentTimeMillis() + ".png";
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                        Environment.getExternalStorageDirectory(), tempPath)));
                startActivityForResult(intent, REQUEST_CAMERA);
            }

            @Override
            public void takePicture() {
                Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
                albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(albumIntent, REQUEST_IMAGE);
            }
        };
        dialog.showDialog();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK && data == null) {
            return;
        }
        switch (requestCode) {
            case TYPE_REQUEST:
                mTypeName = data.getStringExtra("typeName");
                mTypeId = data.getIntExtra("typeId", 0);
                pos = data.getIntExtra("typePosition", 0);
                mTxtCourseType.setText(mTypeName);
                mTxtCourseType.setTextColor(ContextCompat.getColor(this, R.color.color_222222));
                break;
            //拍照
            case REQUEST_CAMERA:
                Uri photoUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), tempPath));
                startPhotoZoom(photoUri);
                break;
            //相册
            case REQUEST_IMAGE:
                if (null != data) {
                    Uri uri = data.getData();
                    startPhotoZoom(uri);
                }
                break;
            //裁剪
            case PHOTO_REQUEST_CUT:
                if (cropFile.exists()) {
                    Uri clipPhotoUri = Uri.fromFile(new File(cropFile.getAbsolutePath()));
                    File imagePath = PhotoUtil.getFileFromMediaUri(this, clipPhotoUri);
                    AddImageOrVideo.upLoadImg(this, imagePath.toString(), new AddImageOrVideo.UploadImgListener() {
                        @Override
                        public void uploadImgListener(String msg) {
                            if (msg.startsWith(HTTPS) || msg.startsWith(HTTP)) {
                                mImageAddress = msg;
                                Glide.with(PublishCourseActivity.this).load(msg).error(R.mipmap.list_bg).into(mIvCourseCover);
                                mIvCourseDelete.setVisibility(View.VISIBLE);
                                mTxtCourseAddcover.setVisibility(View.GONE);
                            }
                        }
                    });
                }
                break;
            case SHARE_REQUEST:
                List<ByIdBean.ContentBean> resultList = (List<ByIdBean.ContentBean>) data.getSerializableExtra("listToPublish");
                MyLog.e("tag", "resultList===" + resultList.size());
                StringBuilder str = new StringBuilder();
                for (int i = 0; i < resultList.size(); i++) {
                    str.append(resultList.get(i).getId());
                    str.append(",");
                }
                if (str != null && str.length() > 0) {
                    consultationId = str.substring(0, str.length() - 1);
                    MyLog.e("tag111", "consultationId===" + consultationId);
                    byIdRequestData(consultationId);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 根据资讯id获取回显列表数据
     *
     * @param id 资讯id
     */
    private void byIdRequestData(String id) {
        RequestApi.byIdRequestData(id, new AbstractNetWorkCallback<PublishCourseBean>() {
            @Override
            public void onSuccess(PublishCourseBean bean) {
                if (bean.getCode() == NO1) {
                    if (bean.getContent().size() > NO0) {
                        mList.addAll(bean.getContent());
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    ToastUtils.show(bean.getMsg());
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
     * 系统裁剪
     *
     * @param uri
     */
    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 100);
        intent.putExtra("outputY", 100);
        cipPath = System.currentTimeMillis() + ".png";
        cropFile = new File(Environment.getExternalStorageDirectory(), cipPath);
        intent.putExtra("output", Uri.fromFile(cropFile));
        intent.putExtra("outputFormat", "JPEG");
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }
}
