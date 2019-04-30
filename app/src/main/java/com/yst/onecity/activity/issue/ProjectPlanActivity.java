package com.yst.onecity.activity.issue;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.MyLog;
import com.yst.onecity.R;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.ProjectPlanBean;
import com.yst.onecity.bean.issue.UpdatePlanBean;
import com.yst.onecity.db.DbManager;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.AddImageOrVideo;
import com.yst.onecity.utils.PermissionHelper;
import com.yst.onecity.view.viewpagerindicator.interfaces.PermissionInterface;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

import static com.yst.onecity.Constant.IMG_FORMAT;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO200;
import static com.yst.onecity.Constant.NO30;
import static com.yst.onecity.Constant.NO6;

/**
 * 申请项目计划界面
 *
 * @author zhaiyanwu
 * @version 1.0.1
 * @date 2018/02/07
 */
public class ProjectPlanActivity extends BaseActivity implements PermissionInterface {
    private static final int REQUEST_IMAGE = 1;
    @BindView(R.id.et_project_title)
    EditText mEtProjectTitle;
    @BindView(R.id.et_project_content)
    EditText mEtProjectContent;
    @BindView(R.id.txt_project_position)
    TextView mTxtProjectPosition;
    @BindView(R.id.iv_project_cover)
    ImageView mIvProjectCover;
    @BindView(R.id.ll_project_addcover)
    LinearLayout mLlProjectAddcover;
    @BindView(R.id.btn_project_ok)
    Button mBtnProjectOk;
    private AMapLocationClient mLocationClient;
    private double wd;
    private double jd;
    private String imgUrl = "";
    private String city;

    private PermissionHelper mPermissionHelper;
    private int mPublishFlag;
    private String mTitle;
    private String mDescribes;
    private String mAddress;
    private String mCover;
    private int mId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_project_plan;
    }

    @Override
    public void initData() {
        DbManager dbManager = new DbManager(this);
        dbManager.copyDBFile();
        initTitleBar("课题");
        easyPermission();
        if (getIntent() != null) {
            mPublishFlag = getIntent().getIntExtra("publishFlag", 0);
            MyLog.e("bundle", "publishFlag==="+ mPublishFlag);
            if (mPublishFlag == NO6) {
                ProjectPlanBean.ContentBean bean = (ProjectPlanBean.ContentBean) getIntent().getSerializableExtra("projectPlanBean");
                mTitle = bean.getTitle();
                mDescribes = bean.getDescribes();
                mAddress = bean.getAddress();
                mCover = bean.getAttachmentaddress();
                mId = bean.getId();

                mEtProjectTitle.setText(mTitle);
                mEtProjectContent.setText(mDescribes);
                mTxtProjectPosition.setText(mAddress);
                Glide.with(this)
                        .load(mCover)
                        .placeholder(R.mipmap.loading)
                        .error(R.mipmap.loading)
                        .into(mIvProjectCover);

                MyLog.e("bundle", "id==="+ mId);
                MyLog.e("bundle", "Title==="+mTitle);
                MyLog.e("bundle", "Describes==="+mDescribes);
                MyLog.e("bundle", "Address==="+mAddress);
                MyLog.e("bundle", "Attachmentaddress==="+mCover);
            }
        } else {
            ToastUtils.show("暂无数据");
        }
    }

    /**
     * 获取当前地理位置
     */
    private void initLocation() {
        mLocationClient = new AMapLocationClient(this);
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setOnceLocation(true);
        mLocationClient.setLocationOption(option);
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        //得到纬度
                        wd = aMapLocation.getLatitude();
                        //得到经度
                        jd = aMapLocation.getLongitude();
                        city = aMapLocation.getCity();
                        mTxtProjectPosition.setText(city);
                        DbManager dbManager = new DbManager(ProjectPlanActivity.this);
                        int cityId = dbManager.findIdByName(city);
                        MyLog.e("response", "cityId===" + cityId);
                        MyLog.e("response", "city===" + city);
                        MyLog.e("response", "wd===" + wd);
                        MyLog.e("response", "jd===" + jd);
                    } else {
                        ToastUtils.show("定位失败，请打开定位权限");
                        //定位失败
                        MyLog.e("response", "失败码===" + aMapLocation.getErrorCode());
                    }
                }
            }
        });
        mLocationClient.startLocation();
    }

    @OnClick({R.id.ll_project_addcover, R.id.btn_project_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_project_addcover:
                AddImageOrVideo.localImg(ProjectPlanActivity.this,REQUEST_IMAGE,NO1);
                break;
            case R.id.btn_project_ok:
                if (mPublishFlag == NO6) {
                    //修改项目计划
                    updateProjectPlan();
                } else {
                    publishProject();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 修改项目计划
     */
    private void updateProjectPlan() {
        String title = mEtProjectTitle.getText().toString().trim();
        String content = mEtProjectContent.getText().toString().trim();
        if (TextUtils.isEmpty(title) || title.length() > NO30) {
            ToastUtils.show("标题字数在 1-30 之间");
            return;
        }
        if (TextUtils.isEmpty(content) || content.length() > NO200) {
            ToastUtils.show("内容字数在 1-200 之间");
            return;
        }
        if (TextUtils.isEmpty(mCover)) {
            ToastUtils.show("请添加封面图");
            return;
        }
        if (TextUtils.isEmpty(mAddress)) {
            ToastUtils.show("请获取位置");
            return;
        }
        RequestApi.updateProjectPlan(
                App.manager.getPhoneNum(),
                App.manager.getUuid(),
                TextUtils.isEmpty(imgUrl) ? mCover : imgUrl,
                title,
                content,
                mAddress,
                TextUtils.isEmpty(Double.toString(jd)) ? "116.465603" : Double.toString(jd),
                TextUtils.isEmpty(Double.toString(wd)) ? "39.960029" : Double.toString(wd),
                String.valueOf(mId),
                "",
                new AbstractNetWorkCallback<UpdatePlanBean>() {
                    @Override
                    public void onSuccess(UpdatePlanBean bean) {
                        if (bean.getCode() == NO1) {
                            ToastUtils.show(bean.getMsg());
                            finish();
                        } else {
                            ToastUtils.show(bean.getMsg());
                        }
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

                    @Override
                    public void onError(String errorMsg) {

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
                    String path = urlList.get(i);
                    AddImageOrVideo.upLoadImg(ProjectPlanActivity.this, path, new AddImageOrVideo.UploadImgListener() {
                        @Override
                        public void uploadImgListener(String msg) {
                            if(msg.endsWith(IMG_FORMAT)){
                                imgUrl = msg;
                                Glide.with(context)
                                        .load(msg)
                                        .placeholder(R.mipmap.loading)
                                        .error(R.mipmap.loading)
                                        .into(mIvProjectCover);
                            }else {
                                ToastUtils.show(msg);
                            }
                        }
                    });
                }
                break;
            default:
                break;
        }
    }

    /**
     * 申请项目计划
     */
    public void publishProject(){

        String title = "";
        title = mEtProjectTitle.getText().toString().trim();
        String describes = "";
        describes = mEtProjectContent.getText().toString().trim();
        String address = "";
        address = city;
        if(TextUtils.isEmpty(title) || title.length() > NO30){
            ToastUtils.show("标题字数在 1-30 之间");
            return;
        }else if(TextUtils.isEmpty(describes) || describes.length() > NO200){
            ToastUtils.show("内容字数在 1-200 之间");
            return;
        }else if(TextUtils.isEmpty(address)){
            ToastUtils.show("请获取位置");
            return;
        }else if(TextUtils.isEmpty(imgUrl)){
            ToastUtils.show("请添加封面图");
            return;
        }
        RequestApi.publishProject(App.manager.getPhoneNum(), imgUrl, title, describes, address, Double.toString(jd), Double.toString(wd), App.manager.getUuid(),"", new AbstractNetWorkCallback<MsgBean>() {
            @Override
            public void onSuccess(MsgBean msgBean) {
                ToastUtils.show(msgBean.getMsg());
                if(msgBean.getCode() == 1){
                    finish();
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
                MyLog.e("publishProject:onError:",errorMsg);
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
     * 申请定位权限
     */
    private void easyPermission() {
        mPermissionHelper = new PermissionHelper(this, this);
        mPermissionHelper.requestPermissions();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(mPermissionHelper.requestPermissionsResult(requestCode, permissions, grantResults)){
            //权限请求结果，并已经处理了该回调
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public int getPermissionsRequestCode() {
        return 10000;
    }

    @Override
    public String[] getPermissions() {
        //设置该界面所需的全部权限
        return new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
    }

    @Override
    public void requestPermissionsSuccess() {
        //权限请求用户已经全部允许
        initLocation();
    }

    @Override
    public void requestPermissionsFail() {
        //权限请求被用户拒绝
        ToastUtils.show("您已拒绝，如需定位，请打开定位权限");
    }
}