package com.yst.onecity.activity.mine.setting;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.Utils;
import com.yst.basic.framework.view.roundedimageview.RoundedImageView;
import com.yst.im.imchatlibrary.model.SetLogoModel;
import com.yst.im.imchatlibrary.model.SetNameModel;
import com.yst.onecity.Const;
import com.yst.onecity.R;
import com.yst.onecity.activity.teammanage.TeamManageActivity;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.PersonBean;
import com.yst.onecity.bean.PersondetailBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.PhotoUtil;
import com.yst.onecity.view.AbstractTakePhotoDialog;
import com.yst.onecity.view.EditTextWatcher;
import com.yst.onecity.view.ExamineTextWatcher;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import pub.devrel.easypermissions.EasyPermissions;

import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO100;

/**
 * (我的)个人信息
 *
 * @author liumanqing
 * @version 1.0.1
 * @date 2018/2/22.
 */
public class PersonDetailActivity extends BaseActivity {
    public static final int REQUEST_CAMERA = 10001;
    public static final int REQUEST_IMAGE = 10002;
    public static final int PHOTO_REQUEST_CUT = 10003;
    private final int MIN_NUMBER = 100;
    @BindView(R.id.image_head)
    RoundedImageView imageHead;
    @BindView(R.id.tv_name)
    EditText tvName;
    @BindView(R.id.tv_phone)
    EditText tvPhone;
    @BindView(R.id.et_company)
    EditText etCompany;
    @BindView(R.id.txt_idea_num_company)
    TextView mTxtIdeaNum;
    @BindView(R.id.txt_head_txxtview)
    TextView tvHead;
    private AbstractTakePhotoDialog dialog;
    private String tmpPATH;
    private String cipPATH;
    private String logoAddress;
    private String personId;
    private int type;

    @Override
    public int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    public void initData() {
        initTitleBar("个人信息");
        setRightText("保存");
        setRightTextViewClickable(true);
        setRightTextColor(R.color.color_666666);
        takePhotoDialog();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            type = bundle.getInt("type");
            personId = bundle.getString("personId");

            //type  0 代表不可点击展示会员信息
            if (type == 0) {
                setRightTextVisibility(View.GONE);
                tvHead.setVisibility(View.GONE);
                tvName.setEnabled(false);
                tvName.setFocusable(false);
                tvName.setKeyListener(null);
                etCompany.setEnabled(false);
                etCompany.setFocusable(false);
                etCompany.setKeyListener(null);
                imageHead.setClickable(false);
                getPersonInfo();
            } else {
                getInfo();
            }
        }

        //昵称输入监听
        tvName.addTextChangedListener(new ExamineTextWatcher(ExamineTextWatcher.TYPE_TEXT_NUMBER_LETTER, tvName, 16));

        setRightTextViewOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Utils.isClickable()) {
                    return;
                }
                setPersonInfo();
            }
        });
        etCompany.addTextChangedListener(new EditTextWatcher(etCompany, mTxtIdeaNum, NO100, this));

    }


    @OnClick({R.id.image_head})
    public void onClick(View view) {
        switch (view.getId()) {
            //头像
            case R.id.image_head:
                String cameraPermission = Manifest.permission.CAMERA;
                String read = Manifest.permission.READ_EXTERNAL_STORAGE;
                if (EasyPermissions.hasPermissions(PersonDetailActivity.this, cameraPermission, read)) {
                    dialog.showDialog();
                } else {
                    EasyPermissions.requestPermissions(this, "请打开照相机,和读写权限", 100, cameraPermission, read);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            //相册
            case REQUEST_IMAGE:
                if (null != data) {
                    Uri uri = data.getData();
                    startPhotoZoom(uri);
                }
                break;
            //照相
            case REQUEST_CAMERA:
                Uri photoUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), tmpPATH));
                startPhotoZoom(photoUri);
                break;
            //上传头像
            case PHOTO_REQUEST_CUT:
                Uri clipPhotoUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), cipPATH));
                File imagePath = PhotoUtil.getFileFromMediaUri(this, clipPhotoUri);
                loadImage(imagePath);
                break;
            default:
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (type != 0) {
            getInfo();
        }

    }

    /**
     * 保存个人信息
     *
     * @version 1.0.1
     */
    private void setPersonInfo() {
        final String etName = this.tvName.getText().toString().trim();
        if (TextUtils.isEmpty(etName)) {
            ToastUtils.show("请输入昵称");
            return;
        }
        String company = etCompany.getText().toString().trim();
        RequestApi.setPersoninfo(String.valueOf(App.manager.getId()), App.manager.getUuid(), App.manager.getPhoneNum(), ConstUtils.getStringNoEmpty(logoAddress), ConstUtils.getStringNoEmpty(etName), ConstUtils.getStringNoEmpty(company), new AbstractNetWorkCallback<MsgBean>() {
            @Override
            public void onSuccess(MsgBean msgBean) {
                if (msgBean.getCode() == 1) {
                    SetLogoModel setLogoModel = new SetLogoModel(PersonDetailActivity.this);
                    setLogoModel.getSetLogo(logoAddress);
                    SetNameModel setNameModel = new SetNameModel(PersonDetailActivity.this);
                    setNameModel.getSetName(ConstUtils.getStringNoEmpty(etName));
                    finish();
                    ToastUtils.show(msgBean.getMsg());
                } else {
                    ToastUtils.show(msgBean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });

    }

    /**
     * (设置)获取个人信息
     *
     * @version 1.0.1
     */
    private void getInfo() {
        RequestApi.getPersoninfo(App.manager.getUuid(), App.manager.getPhoneNum(), new AbstractNetWorkCallback<PersondetailBean>() {

            @Override
            public void onSuccess(PersondetailBean msgBean) {
                if (msgBean.getCode() == NO1) {
                    tvName.setText(ConstUtils.getStringNoEmpty(ConstUtils.getStringNoEmpty(msgBean.getContent().getNickname())));
                    etCompany.setText(ConstUtils.getStringNoEmpty(ConstUtils.getStringNoEmpty(msgBean.getContent().getContent())));
                    tvPhone.setText(ConstUtils.getStringNoEmpty(ConstUtils.getStringNoEmpty(msgBean.getContent().getPhone())));
                    Glide.with(App.getInstance())
                            .load(msgBean.getContent().getLogo_attachment_address())
                            .error(R.mipmap.default_head)
                            .into(imageHead);
                } else {
                    ToastUtils.show(msgBean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);

            }
        });

    }

    /**
     * 会员信息回显
     *
     * @version 1.0.1
     */
    private void getPersonInfo() {
        RequestApi.myPersonInfro(personId, new AbstractNetWorkCallback<PersonBean>() {
            @Override
            public void onSuccess(PersonBean personBean) {
                if (personBean.getCode() == NO1) {
                    if (personBean.getContent() != null) {
                        Glide.with(App.getInstance())
                                .load(ConstUtils.getStringNoEmpty(personBean.getContent().getLogo_attachment_address()))
                                .error(R.mipmap.default_nor_avatar)
                                .into(imageHead);
                        tvName.setText(ConstUtils.getStringNoEmpty(personBean.getContent().getNickname()));
                        etCompany.setText(ConstUtils.getStringNoEmpty(personBean.getContent().getContent()));
                        tvPhone.setText(ConstUtils.getStringNoEmpty(personBean.getContent().getPhone()));
                    } else {
                        ToastUtils.show(personBean.getMsg());
                    }
                } else {
                    ToastUtils.show(personBean.getMsg());
                }

            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }


    /**
     * 上传头像
     *
     * @version 1.0.1
     */
    private void loadImage(File imagePath) {
        OkHttpUtils.post()
                .url(Const.UPLOAD_IMG)
                .addFile("imageFile", imagePath.getName(), imagePath)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.show("请求失败");
                    }

                    @Override
                    public void onResponse(String s, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            logoAddress = jsonObject.getString("content");
                            Glide.with(App.getInstance())
                                    .load(logoAddress)
                                    .error(R.mipmap.default_head)
                                    .into(imageHead);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 对选择的图片进行裁剪后展示
     *
     * @param uri uri
     */
    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 100);
        intent.putExtra("outputY", 100);
        cipPATH = System.currentTimeMillis() + ".png";
        intent.putExtra("output", Uri.fromFile(new File(Environment.getExternalStorageDirectory(), cipPATH)));
        intent.putExtra("outputFormat", "JPEG");
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    /**
     * 拍照
     *
     * @version 1.0.1
     */
    private void takePhotoDialog() {
        dialog = new AbstractTakePhotoDialog(this) {
            @Override
            public void takePhoto() {
                tmpPATH = System.currentTimeMillis() + ".png";
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                        Environment.getExternalStorageDirectory(), tmpPATH)));
                startActivityForResult(intent, REQUEST_CAMERA);
            }

            /**
             * 选择相册
             * @version 1.0.1
             */
            @Override
            public void takePicture() {
                Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
                albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(albumIntent, REQUEST_IMAGE);
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.dismissDialog();
    }

}
