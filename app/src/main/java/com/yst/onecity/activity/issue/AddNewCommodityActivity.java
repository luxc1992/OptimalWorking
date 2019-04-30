package com.yst.onecity.activity.issue;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.MyLog;
import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.activity.home.PublishInfoVideoActivity;
import com.yst.onecity.adapter.ChooseImageSpecificationAdapter;
import com.yst.onecity.adapter.FreightTemplateAdapter;
import com.yst.onecity.adapter.issue.SpecificationListAdapter;
import com.yst.onecity.bean.CommoditySpecificationBean;
import com.yst.onecity.bean.ConsultItemBean;
import com.yst.onecity.bean.FreightRuleBean;
import com.yst.onecity.bean.issue.AddCommodityBean;
import com.yst.onecity.bean.issue.CommodityTwoClassifyBean;
import com.yst.onecity.bean.issue.IssueCommodityNewsBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.AddImageOrVideo;
import com.yst.onecity.utils.BitmapUtil;
import com.yst.onecity.view.MyListView;
import com.yst.onecity.view.editor.CommoditySortRichEditor;
import com.yst.onecity.view.editor.SEditorData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import pub.devrel.easypermissions.EasyPermissions;

import static com.yst.onecity.Constant.ISGOODMANAGER;
import static com.yst.onecity.Constant.ISHUNTERADD;
import static com.yst.onecity.Constant.NO100;
import static com.yst.onecity.Constant.NO1024;

/**
 * 添加新商品界面
 *
 * @author zhaiyanwu
 * @version 1.0.1
 * @date 2018/02/06
 */
public class AddNewCommodityActivity extends BaseActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks {
    @BindView(R.id.add_commodity_image_grid)
    GridView gridView;
    @BindView(R.id.image_add_commodity_image)
    ImageView imageAdd;
    @BindView(R.id.image_add_commodity_video)
    ImageView videoAdd;
    @BindView(R.id.richEditor)
    CommoditySortRichEditor srEditor;
    @BindView(R.id.linear_add_commodity_specification)
    LinearLayout addSpecification;
    @BindView(R.id.linear_father_view)
    LinearLayout fatherView;
    @BindView(R.id.lv_generate_specification)
    MyListView generateSpecification;
    @BindView(R.id.tev_generate_specification)
    TextView tevGenerateSpecification;
    @BindView(R.id.linear_establish_freight_template)
    LinearLayout linearTemplate;
    @BindView(R.id.tev_grid_hint)
    TextView gridHint;
    @BindView(R.id.lv_template)
    ListView lvTemplate;
    @BindView(R.id.btn_commit)
    TextView btnCommit;
    @BindView(R.id.edit_commodity_name)
    EditText commodityName;
    @BindView(R.id.edit_commodity_headline)
    EditText commodityHeadline;
    @BindView(R.id.tv_issue_commodity_classify)
    TextView tvClassify;
    /**
     * 视频格式对比
     */
    private final String VIDEO_FORMAT = ".mp4";
    /**
     * 请求访问外部存储
     */
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 103;
    /**
     * 内容图片
     */
    private static final int SELECT_PICTURE4 = 3;
    /**
     * 内容图片
     */
    private static final int SPECIFICATION = 5;
    /**
     * 当前选择的请求图片位置
     */
    private int currentSelectPic = -1;
    /**
     * 记录一下当前判断的权限是谁的
     */
    private int imageType = 0;
    /**
     * 存放规格值的集合
     */
    private ArrayList<ArrayList<EditText>> editSpecificationList = new ArrayList<>();
    /**
     * 存放规格名称的集合
     */
    private ArrayList<EditText> editSpecificationNameList = new ArrayList<EditText>();
    /**
     * 视频最大支持100M
     */
    private final int MAX_VIDEO_SIZE = 100;
    /**
     * 是否是从商品管理列表回显数据
     */
    private String isGoodManage;
    private int goodDetailsType = 0;
    private int goodDetailsSort = 1;
    private ArrayList<String> videoFirstPath = new ArrayList<>();
    private int position = 0;
    private SpecificationListAdapter specificationListAdapter;
    ArrayList<String> arrayListTwo = new ArrayList<>();
    private boolean isAdd = true;
    ArrayList<CommoditySpecificationBean> specificationNewData = new ArrayList<>();
    private List<FreightRuleBean.ContentBean> templateContent = new ArrayList<>();
    private FreightTemplateAdapter templateAdapter;
    private ArrayList<String> nameList;
    public final static int RICH_IMAGE_CODE = 0x33;
    String freightRuleMainId = "";
    private ChooseImageSpecificationAdapter chooseImageSpecificationAdapter;
    ArrayList<ConsultItemBean> getTitleImageList = new ArrayList<>();
    private int imageList = 0;
    private IssueCommodityNewsBean.ContentBean content;
    private ImageView imageVideo2;
    private ImageView imagePicture2;
    private View inflate2;
    private PopupWindow popupWindow2;
    private String address;
    private int imageNewsList = 0;
    private int goodsId;
    private String contentId;
    private static final int REQUEST_CODE_TYPE = 13;
    private boolean isAddSpecification = true;
    AMapLocationClient mLocationClient;
    private String isHunterAdd = "";
    private String productTypeId = "";
    private String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_new_commodity;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initData() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        initGetBundle();
        //从猎头个人主页直接添加新商品
        if (getIntent().getExtras() != null) {
            isHunterAdd = getIntent().getExtras().getString("isHunterAdd");
        }
        getFreightTemplateData();
        setLvTemplateAdapter();
        setGridViewAdapter();
        //判断是否是回显
        content = (IssueCommodityNewsBean.ContentBean) getIntent().getSerializableExtra("content");
        contentId = getIntent().getStringExtra("contentId");
        isGoodManage = getIntent().getStringExtra("isGoodManage");
        if (ISGOODMANAGER.equals(isGoodManage)) {
            initTitleBar("编辑新商品");
        } else {
            initTitleBar("添加新商品");
        }
        if (content != null) {
            setHunterProductContent(content);
        } else {
            addCommoditySpecification();
            tvClassify.setOnClickListener(this);
        }
        inflate2 = View.inflate(getApplicationContext(), R.layout.popup_window_view2, null);
        initPopWindow2(inflate2);
    }

    /**
     * 获取上一个页面传来的参数
     */
    private void initGetBundle() {
        address = getIntent().getStringExtra("address");
    }

    /**
     * 商品副标题图片adapter
     */
    private void setGridViewAdapter() {
        chooseImageSpecificationAdapter = new ChooseImageSpecificationAdapter(getTitleImageList, this);
        gridView.setAdapter(chooseImageSpecificationAdapter);
        if (chooseImageSpecificationAdapter != null) {
            chooseImageSpecificationAdapter.setIScrollPositon(new ChooseImageSpecificationAdapter.IGetScrollPosition() {
                @Override
                public void click(int i) {
                    //gridView的item点击监听 删除
                    getTitleImageList.remove(i);
                    chooseImageSpecificationAdapter.notifyDataSetChanged();
                }
            });
        }
        //商品副标题点击事件
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!Utils.isClickable()) {
                    return;
                }
                //权限判断
                if (ContextCompat.checkSelfPermission(AddNewCommodityActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    //申请READ_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(AddNewCommodityActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_REQUEST_CODE);
                } else {
                    //跳转图库
                    if (getTitleImageList.size() < Constant.NO6) {
                        MultiImageSelector
                                .create(AddNewCommodityActivity.this)
                                .multi()
                                .count(Constant.NO6 - i)
                                .start(AddNewCommodityActivity.this, REQUEST_CODE_TYPE);
                    }
                }
            }
        });
    }

    /**
     * 设置运费模板adapter
     * 点击运费模板跳转运费模板
     */
    private void setLvTemplateAdapter() {
        templateAdapter = new FreightTemplateAdapter(context, templateContent);
        lvTemplate.setAdapter(templateAdapter);
        templateAdapter.setIScrollPositon(new FreightTemplateAdapter.IGetScrollPosition() {
            //查看详情
            @Override
            public void click(int i) {
                int id = templateContent.get(i).getId();
                Bundle bundle = new Bundle();
                bundle.putLong("id", id);
                //跳转运费模板详情页面
                JumpIntent.jump(AddNewCommodityActivity.this, EstablishFreightTemplateActivity.class, bundle);
            }

            //选择模板
            @Override
            public void confirm(int i) {
                FreightRuleBean.ContentBean contentBean = templateContent.get(i);
                int id = contentBean.getId();
                freightRuleMainId = id + "";
                templateAdapter.setDefSelect(id);
                templateAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 请求模板列表接口
     */
    private void getFreightTemplateData() {
        RequestApi.getFreightRuleMainListByUserId(App.manager.getUuid(), App.manager.getPhoneNum(), new AbstractNetWorkCallback<FreightRuleBean>() {
            @Override
            public void onAfter() {
                super.onAfter();
                dismissInfoProgressDialog();
            }

            @Override
            public void onBefore() {
                super.onBefore();
                showInfoProgressDialog();
            }

            @Override
            public void onSuccess(FreightRuleBean freightRuleBean) {
                if (freightRuleBean.getCode() == Constant.NO1) {
                    List<FreightRuleBean.ContentBean> content = freightRuleBean.getContent();
                    templateContent.clear();
                    templateContent.addAll(content);
                    templateAdapter.notifyDataSetChanged();
                } else {
                    ToastUtils.show(freightRuleBean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(getResources().getString(R.string.error_hint));
            }
        });
    }

    /**
     * 从相册获取图片
     */
    private void getPicFromAlbm(final int type) {
        imageType = type;
        //权限判断
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //申请READ_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_REQUEST_CODE);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, type);
        }
    }

    /**
     * 获取图片路径 响应startActivityForResult
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (resultCode == Constant.LOCAL_VIDEO_RESULT) {
            //视频路径
            final String path = data.getExtras().getString("path");
            //视频大小
            final long size = data.getExtras().getLong("size2");
            if (size / NO1024 / NO1024 > MAX_VIDEO_SIZE) {
                ToastUtils.show("视频不能超过100M");
                return;
            }
            MediaMetadataRetriever media = new MediaMetadataRetriever();
            // videoPath 本地视频的路径
            media.setDataSource(path);
            Bitmap bitmap = media.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
            if (bitmap == null) {
                ToastUtils.show("视频无效,重新选择");
                return;
            }
            final String imagePath = AddImageOrVideo.saveBitmap(this, bitmap);
            showInfoProgressDialog();
            AddImageOrVideo.upLoadVideo(AddNewCommodityActivity.this, path, new AddImageOrVideo.UploadVideoListener() {
                @Override
                public void uploadVideoListener(final String msgs) {
                    if (msgs.endsWith(VIDEO_FORMAT)) {
                        AddImageOrVideo.upLoadImg(AddNewCommodityActivity.this, imagePath, new AddImageOrVideo.UploadImgListener() {
                            @Override
                            public void uploadImgListener(String msg) {
                                videoFirstPath.add(msg);
                                srEditor.addNetImage(msg, msgs, true, context, false);
                                ToastUtils.show("视频上传成功");
                            }
                        });
                    } else {
                        ToastUtils.show(msgs);
                    }
                    dismissInfoProgressDialog();
                }
            });
            return;
        }
        switch (requestCode) {
            //富文本选择图片
            case RICH_IMAGE_CODE:
                Uri uris = data.getData();
                String cropImagePaths = getRealFilePathFromUri(getApplicationContext(), uris);
                showInfoProgressDialog();
                File scal = BitmapUtil.scal(cropImagePaths);
                String absolutePath = scal.getAbsolutePath();
                AddImageOrVideo.upLoadImg(this, absolutePath, new AddImageOrVideo.UploadImgListener() {
                    @Override
                    public void uploadImgListener(String msg) {
                        srEditor.addNetImage(msg, false, context, false);
                        dismissInfoProgressDialog();
                    }
                });
                break;
            //规格插图
            case SPECIFICATION:
                Uri uria = data.getData();
                String cropImagePatha = getRealFilePathFromUri(getApplicationContext(), uria);
                showInfoProgressDialog();
                AddImageOrVideo.upLoadImg(this, cropImagePatha, new AddImageOrVideo.UploadImgListener() {
                    @Override
                    public void uploadImgListener(String msg) {
                        specificationNewData.get(position).setImagePath(msg);
                        specificationListAdapter.notifyDataSetChanged();
                        dismissInfoProgressDialog();
                    }
                });
                break;
            //副标题图片
            case REQUEST_CODE_TYPE:
                ArrayList<String> stringArrayListExtra = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                imageList = stringArrayListExtra.size();
                upLoadPic(stringArrayListExtra);
                break;
            //拍摄
            case Constant.NO20:
                //视频路径
                final String videoPath = data.getExtras().getString("videoPath");
                //图片路径
                final String framePicPath = data.getExtras().getString("framePicPath");
                if (TextUtils.isEmpty(videoPath) || TextUtils.isEmpty(framePicPath)) {
                    ToastUtils.show("视频获取失败,请重新拍摄");
                    return;
                }
                showInfoProgressDialog();
                AddImageOrVideo.upLoadVideo(AddNewCommodityActivity.this, videoPath, new AddImageOrVideo.UploadVideoListener() {
                    @Override
                    public void uploadVideoListener(final String msgs) {
                        if (msgs.endsWith(VIDEO_FORMAT)) {
                            AddImageOrVideo.upLoadImg(AddNewCommodityActivity.this, framePicPath, new AddImageOrVideo.UploadImgListener() {
                                @Override
                                public void uploadImgListener(String msg) {
                                    videoFirstPath.add(msg);
                                    srEditor.addNetImage(msg, msgs, true, context, false);
                                }
                            });
                        } else {
                            ToastUtils.show(msgs);
                        }
                        dismissInfoProgressDialog();
                    }
                });

                break;
            //商品分类回显设置
            case Constant.NO97:
                Bundle key = data.getExtras().getBundle("key");
                if (null != key) {
                    //一级分类名称
                    String classifyName = key.getString("classifyName");
                    CommodityTwoClassifyBean.ContentBean contentBean = (CommodityTwoClassifyBean.ContentBean) key.getSerializable("data");
                    if (contentBean != null) {
                        int id = contentBean.getId();
                        //二级分类名称
                        String classifyTwoName = contentBean.getClassifyName();
                        String name = classifyName + " > " + classifyTwoName;
                        tvClassify.setText(name);
                        tvClassify.setTextColor(Color.BLACK);
                        productTypeId = String.valueOf(id);
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 上传图片
     */
    public void upLoadPic(List<String> imgPathList) {
        imageNewsList = 0;
        showInfoProgressDialog();
        for (int i = 0; i < imgPathList.size(); i++) {
            HashMap<String, String> params = new HashMap<>(16);
            //mark 客户端标识 0 安卓 1 iOS 2 PC
            params.put("mark", "0");
            //上传图片接口
            AddImageOrVideo.upLoadImg(AddNewCommodityActivity.this, imgPathList.get(i), new AddImageOrVideo.UploadImgListener() {
                @Override
                public void uploadImgListener(String msg) {
                    if (msg.endsWith(".jpg") || msg.endsWith(".png")) {
                        ConsultItemBean consultItemBean = new ConsultItemBean();
                        consultItemBean.setId(1);
                        consultItemBean.setmPhotoPath(msg);
                        getTitleImageList.add(getTitleImageList.size(), consultItemBean);
                        //添加的图片不可以超过六张
                        if (getTitleImageList.size() >= Constant.NO6) {
                            for (int i = 0; i < getTitleImageList.size(); i++) {
                                if (i >= 6) {
                                    getTitleImageList.remove(i);
                                }
                            }
                        }
                        //刷新商品副标题的image的adapter
                        chooseImageSpecificationAdapter.notifyDataSetChanged();
                        if (getTitleImageList.size() > Constant.NO0) {
                            gridHint.setVisibility(View.GONE);
                        } else {
                            gridHint.setVisibility(View.VISIBLE);
                        }
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

    @Override
    public void setListener() {
        super.setListener();
        imageAdd.setOnClickListener(this);
        videoAdd.setOnClickListener(this);
        addSpecification.setOnClickListener(this);
        tevGenerateSpecification.setOnClickListener(this);
        linearTemplate.setOnClickListener(this);
        btnCommit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (!Utils.isClickable()) {
            return;
        }
        switch (view.getId()) {
            //添加图片
            case R.id.image_add_commodity_image:
                boolean editorDataCount = getEditorDataCount();
                if (editorDataCount) {
                    currentSelectPic = SELECT_PICTURE4;
                    //权限判断
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        //申请READ_EXTERNAL_STORAGE权限
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_REQUEST_CODE);
                    } else {
                        //跳转到调用系统图库
                        getPicFromAlbm(RICH_IMAGE_CODE);
                    }
                }
                break;
            //添加视频
            case R.id.image_add_commodity_video:
                boolean editorDataCount1 = getEditorDataCount();
                if (editorDataCount1) {
                    showPopWindow2();
                }
                break;
            //拍摄视频
            case R.id.popup_window_view_picture2:
                popupWindow2.dismiss();
                //跳转拍摄页面
                Intent intent = new Intent(AddNewCommodityActivity.this, PublishInfoVideoActivity.class);
                intent.putExtra("from", "3");
                startActivityForResult(intent, Constant.NO20);
                break;
            //本地视频
            case R.id.popup_window_view_video2:
                popupWindow2.dismiss();
                AddImageOrVideo.localVideo(this, Constant.LOCAL_VIDEO_RESULT);
                break;
            //取消 添加视频方式
            case R.id.popup_window_tv_cancel2:
                popupWindow2.dismiss();
                break;
            //添加商品规格
            case R.id.linear_add_commodity_specification:
                addCommoditySpecification();
                break;
            //生成商品规格
            case R.id.tev_generate_specification:
                //防爆点击
                boolean clickable = Utils.isClickable();
                if (clickable) {
                    isAdd = true;
                    setLvAdapter();
                }
                break;
            //创建运费模板
            case R.id.linear_establish_freight_template:
                JumpIntent.jump(this, EstablishFreightTemplateActivity.class);
                break;
            //完成按钮
            case R.id.btn_commit:
                //防爆点击
                boolean clickables = Utils.isClickable();
                if (clickables) {
                    addProductByHunter();
                }
                break;
            //分类
            case R.id.tv_issue_commodity_classify:
                //跳转商品分类页面
                Intent intents = new Intent(this, CommodityClassifyActivity.class);
                startActivityForResult(intents, Constant.NO97);
                break;
            default:
                break;
        }
    }

    /**
     * 完成添加商品
     */
    private void addProductByHunter() {
        int videoCount = 0;
        int imageCount = 0;
        //得到商品名称
        String name = commodityName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.show("请填写商品名称");
            return;
        }
        //商品标题
        String headline = commodityHeadline.getText().toString();
        if (TextUtils.isEmpty(headline)) {
            ToastUtils.show("请填写商品标题");
            return;
        }
        if (getTitleImageList.size() <= 0) {
            ToastUtils.show("请添加商品图片");
            return;
        }
        //商品分类id  productTypeId
        final JSONObject jsonObject = productJson(name, headline, productTypeId);
        if (jsonObject == null) {
            return;
        }
        //得到添加商品生成的json串,作为参数上传
        final JSONArray image = imageJSON();
        final JSONArray specification = specificationAndValJson();
        final JSONArray productSku = productSkuJSON();
        if (null == productSku) {
            return;
        }
        // 原生图文编辑
        List<SEditorData> sEditorDatas = srEditor.buildEditData();
        JSONArray jsonArray = new JSONArray();
        try {
            //生成商品详情
            for (int i = 0; i < sEditorDatas.size(); i++) {
                JSONObject jsonObject1 = new JSONObject();
                SEditorData sEditorData = sEditorDatas.get(i);
                String imagePath = sEditorData.getImagePath();
                String inputStr = sEditorData.getInputStr();
                String videoPath = sEditorData.getVideoPath();
                if (imagePath != null) {
                    //图片
                    goodDetailsType = 1;
                    jsonObject1.put("content", imagePath);
                    jsonObject1.put("sort", goodDetailsSort);
                    jsonObject1.put("type", goodDetailsType);
                    jsonObject1.put("viewImg", "");
                    imageCount++;
                    if (imageCount > Constant.NO10) {
                        ToastUtils.show("最多只能添加十张图片");
                        return;
                    }
                } else if (inputStr != null) {
                    goodDetailsType = 0;
                    //文字
                    jsonObject1.put("content", inputStr);
                    jsonObject1.put("sort", goodDetailsSort);
                    jsonObject1.put("type", goodDetailsType);
                    jsonObject1.put("viewImg", "");
                } else if (videoPath != null) {
                    goodDetailsType = 2;
                    //视频
                    String s = videoFirstPath.get(videoFirstPath.size() - 1);
                    jsonObject1.put("content", videoPath);
                    jsonObject1.put("sort", goodDetailsSort);
                    jsonObject1.put("type", goodDetailsType);
                    jsonObject1.put("viewImg", s);
                    videoCount++;
                    if (videoCount > Constant.NO1) {
                        ToastUtils.show("只能添加一个视频");
                        return;
                    }
                }
                jsonArray.put(jsonObject1);
                goodDetailsSort++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (goodDetailsSort == Constant.NO1) {
            ToastUtils.show("请填写商品详情");
            return;
        }
        if (null != content && !TextUtils.isEmpty(contentId)) {
            //编辑商品接口
            RequestApi.updateProductByHunter(jsonObject.toString(), image.toString(), jsonArray.toString(), specification.toString(), productSku.toString(), App.manager.getUuid(), App.manager.getPhoneNum(), new AbstractNetWorkCallback<AddCommodityBean>() {
                @Override
                public void onSuccess(AddCommodityBean addCommodityBean) {
                    if (addCommodityBean.getCode() == Constant.NO1) {
                        if (ISGOODMANAGER.equals(isGoodManage)) {
                            finish();
                        } else {
                            AddCommodityBean.ContentBean content = addCommodityBean.getContent();
                            Intent intent = new Intent();
                            intent.putExtra("goods", content);
                            setResult(Constant.NO50, intent);
                            finish();
                        }
                    }
                    ToastUtils.show(addCommodityBean.getMsg());
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
        } else {
            //新增商品接口
            RequestApi.addProductByHunter(jsonObject.toString(), image.toString(), jsonArray.toString(), specification.toString(), productSku.toString(), App.manager.getUuid(), App.manager.getPhoneNum(), new AbstractNetWorkCallback<AddCommodityBean>() {
                @Override
                public void onSuccess(AddCommodityBean addCommodityBean) {
                    if (addCommodityBean.getCode() == Constant.NO1) {
                        if (ISHUNTERADD.equals(isHunterAdd)) {
                            finish();
                        } else {
                            //数据回显
                            AddCommodityBean.ContentBean content = addCommodityBean.getContent();
                            Intent intent = new Intent();
                            intent.putExtra("goods", content);
                            setResult(Constant.NO50, intent);
                            finish();
                        }
                    }
                    ToastUtils.show(addCommodityBean.getMsg());
                }

                @Override
                public void onError(String errorMsg) {
                    ToastUtils.show(getResources().getString(R.string.error_hint));
                }

                @Override
                public void onAfter() {
                    super.onAfter();
                    dismissInfoProgressDialog();
                }

                @Override
                public void onBefore() {
                    super.onBefore();
                    showInfoProgressDialog();
                }
            });
        }
    }

    /**
     * 动态添加商品规格
     */
    private void addCommoditySpecification() {
        final ArrayList<EditText> editSpecificationPriceList = new ArrayList<>();
        final View sonSpecification = View.inflate(this, R.layout.item_son_add_commodity, null);
        EditText specificationName = sonSpecification.findViewById(R.id.edt_specification_name);
        ImageView imageAddEditView = sonSpecification.findViewById(R.id.image_add_edit_view);
        //商品规格item加号监听
        imageAddEditView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //初始化editText
                LinearLayout addEditView = sonSpecification.findViewById(R.id.linear_add_edit_view);
                EditText editText = new EditText(context);
                editText.setHint("例:白色");
                editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
                editText.setEllipsize(TextUtils.TruncateAt.END);
                editText.setHintTextColor(ContextCompat.getColor(context, R.color.color_DDDDDD));
                editText.setTextSize(16);
                //设置edittext只能输入数字或者价格
                editText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                editText.setGravity(Gravity.CENTER);
                LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams1.setMargins(5, 0, 5, 0);
                editText.setBackgroundResource(R.drawable.shape_commodity_bg);
                editText.setPadding(15, 7, 15, 7);
                addEditView.addView(editText, layoutParams1);
                editSpecificationPriceList.add(editText);
            }
        });
        if (isAddSpecification) {
            //生成一个editText 供用户填写
            LinearLayout addEditView = sonSpecification.findViewById(R.id.linear_add_edit_view);
            EditText editText = new EditText(context);
            editText.setHint("例:白色");
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
            editText.setEllipsize(TextUtils.TruncateAt.END);
            editText.setHintTextColor(ContextCompat.getColor(context, R.color.color_DDDDDD));
            editText.setTextSize(16);
            editText.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams1.setMargins(5, 0, 5, 0);
            editText.setBackgroundResource(R.drawable.shape_commodity_bg);
            editText.setPadding(15, 7, 15, 7);
            addEditView.addView(editText, layoutParams1);
            editSpecificationPriceList.add(editText);
            isAddSpecification = false;
        }
        //将editText放入到集合中和父view中
        fatherView.addView(sonSpecification);
        editSpecificationList.add(editSpecificationPriceList);
        editSpecificationNameList.add(specificationName);
    }

    /**
     * 动态添加商品规格  回显
     */
    private void addCommoditySpecification(IssueCommodityNewsBean.ContentBean.SpecificationAndValJsonBean specificationAndValJsonBean) {
        final View sonSpecification = View.inflate(this, R.layout.item_son_add_commodity, null);
        final ArrayList<EditText> editSpecificationPriceList = new ArrayList<>();
        EditText specificationName = sonSpecification.findViewById(R.id.edt_specification_name);
        ImageView imageAddEditView = sonSpecification.findViewById(R.id.image_add_edit_view);
        //回显后的监听   添加商品规格
        imageAddEditView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击加号生成editText
                LinearLayout addEditView = sonSpecification.findViewById(R.id.linear_add_edit_view);
                EditText editText = new EditText(context);
                editText.setHint("例:白色");
                editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
                editText.setHintTextColor(ContextCompat.getColor(context, R.color.color_DDDDDD));
                editText.setTextSize(16);
                editText.setGravity(Gravity.CENTER);
                LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams1.setMargins(5, 0, 5, 0);
                editText.setBackgroundResource(R.drawable.shape_commodity_bg);
                editText.setPadding(15, 7, 15, 7);
                addEditView.addView(editText, layoutParams1);
                editSpecificationPriceList.add(editText);
            }
        });
        String name = specificationAndValJsonBean.getSpecificationName();
        specificationName.setText(name);
        List<String> specificationValue1 = specificationAndValJsonBean.getSpecificationValue();
        LinearLayout addEditView = sonSpecification.findViewById(R.id.linear_add_edit_view);
        //遍历数据返回的editText数量,并赋值
        for (int i = 0; i < specificationValue1.size(); i++) {
            EditText editText = new EditText(context);
            //回显editText内容
            editText.setText(specificationValue1.get(i));
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
            editText.setHintTextColor(ContextCompat.getColor(context, R.color.color_DDDDDD));
            editText.setTextSize(16);
            editText.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams1.setMargins(5, 0, 5, 0);
            //设置editText背景
            editText.setBackgroundResource(R.drawable.shape_commodity_bg);
            editText.setPadding(15, 7, 15, 7);
            addEditView.addView(editText, layoutParams1);
            editSpecificationPriceList.add(editText);
        }
        fatherView.addView(sonSpecification);
        editSpecificationList.add(editSpecificationPriceList);
        editSpecificationNameList.add(specificationName);
    }

    /**
     * 设置规格值适配器
     */
    private void setLvAdapter() {
        nameList = new ArrayList<>();
        String temp = "";
        //遍历存放editText的集合得到数据
        for (int i = 0; i < editSpecificationNameList.size(); i++) {
            String trim = editSpecificationNameList.get(i).getText().toString().trim();
            nameList.add(trim);
            if (TextUtils.isEmpty(trim)) {
                ToastUtils.show("请填写规格值");
                return;
            }
        }
        //判断集合中的数据是否一致
        for (int i = 0; i < nameList.size() - 1; i++) {
            temp = nameList.get(i);
            for (int j = i + 1; j < nameList.size(); j++) {
                if (temp.equals(nameList.get(j))) {
                    ToastUtils.show("不可以填写重复的规格名称");
                    return;
                }
            }
        }
        //得到输入的规格值
        List<String[]> list = new ArrayList<>();
        for (int i = 0; i < editSpecificationList.size(); i++) {
            ArrayList<EditText> editTexts = editSpecificationList.get(i);
            String[] arr = new String[40];
            int num = 0;
            for (int j = 0; j < editTexts.size(); j++) {
                String trim = editTexts.get(j).getText().toString().trim();
                if (!TextUtils.isEmpty(trim)) {
                    arr[num] = trim + "-";
                    num++;
                }
            }
            String sre = arr[0];
            if (!TextUtils.isEmpty(sre)) {
                list.add(arr);
            }
        }
        String[] strings = null;
        if (list.size() > 0) {
            strings = list.get(0);
        }
        // 商品规格的适配器  ---------------------
        ArrayList<String> specificationData = getSpecificationData(list, strings, "");
        ArrayList<CommoditySpecificationBean> commoditySpecificationBeanArrayList = new ArrayList<>();
        for (String string : specificationData) {
            CommoditySpecificationBean bean = new CommoditySpecificationBean("", "", "", "");
            bean.setSpecification(string);
            commoditySpecificationBeanArrayList.add(bean);
        }
        if (commoditySpecificationBeanArrayList.size() - 1 < 0) {
            return;
        }
        CommoditySpecificationBean bean = commoditySpecificationBeanArrayList.get(commoditySpecificationBeanArrayList.size() - 1);
        bean.setSpecification(bean.getSpecification().substring(0, bean.getSpecification().length() - 1));
        specificationNewData.clear();
        specificationNewData.addAll(commoditySpecificationBeanArrayList);
        if (specificationListAdapter == null) {
            specificationListAdapter = new SpecificationListAdapter(this, specificationNewData, mOnClick);
            if (generateSpecification != null) {
                generateSpecification.setAdapter(specificationListAdapter);
            }
        } else {
            specificationListAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 匿名内部类监听器 商品规格
     */
    private View.OnClickListener mOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.stock_change_price:
                case R.id.image_commodity:
                    currentSelectPic = SPECIFICATION;
                    //跳转到调用系统图库
                    getPicFromAlbm(currentSelectPic);
                    position = (int) v.getTag(R.id.accordion);
                    break;
                case R.id.image_delete_specification:
                    //商品规格的删除监听
                    position = (int) v.getTag();
                    specificationListAdapter.deleteData(position);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 获得规格数据
     */
    private ArrayList<String> getSpecificationData(List<String[]> list, String[] arr, String str) {
        //判断是否重新添加
        if (isAdd) {
            arrayListTwo.clear();
        }
        //存放规格值
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            //取得当前的数组  
            if (i == list.indexOf(arr)) {
                for (String st : arr) {
                    if (TextUtils.isEmpty(st)) {
                        continue;
                    }
                    st = str + st;
                    if (i < list.size() - 1) {
                        isAdd = false;
                        getSpecificationData(list, list.get(i + 1), st);
                    } else if (i == list.size() - 1) {
                        arrayList.add(st);
                    }
                }
            }
        }
        arrayListTwo.addAll(arrayList);
        if (arrayList.size() <= 0) {
            return arrayListTwo;
        }
        return arrayList;
    }

    /**
     * 外部存储权限申请返回
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //当前申请的权限被是否被授权
        if (requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getPicFromAlbm(imageType);
            }
        }
    }

    /**
     * 根据Uri返回文件绝对路径
     * 兼容了file:///开头的 和 content://开头的情况
     */
    public static String getRealFilePathFromUri(final Context context, final Uri uri) {
        if (null == uri) {
            return null;
        }
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 生成商品标题  productJson
     *
     * @param name          商品名称
     * @param title         商品标题
     * @param productTypeId 分类id
     * @return string
     */
    private JSONObject productJson(String name, String title, String productTypeId) {

        if (null == address) {
            address = "";
        }
        if (TextUtils.isEmpty(freightRuleMainId)) {
            ToastUtils.show("请选择运费模板");
            return null;
        }
        JSONObject object = new JSONObject();
        try {
            //生成商品信息的json串
            if (content != null && !TextUtils.isEmpty(contentId)) {
                object.put("id", contentId);
            }
            //商品名称
            object.put("name", name);
            //标题
            object.put("title", title);
            if (TextUtils.isEmpty(address)) {
                initLocation();
                return null;
            }
            //产地
            object.put("address", address);
            //运费模板id
            object.put("freightRuleMainId", freightRuleMainId);
            //分类id
            object.put("productTypeId", productTypeId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    /**
     * 生成图片 imageJSON
     */
    private JSONArray imageJSON() {
        JSONArray jsonArray = new JSONArray();
        try {
            for (int i = 0; i < getTitleImageList.size(); i++) {
                JSONObject jsonObject = new JSONObject();
                //图片地址
                jsonObject.put("address", getTitleImageList.get(i).getmPhotoPath());
                //图片数量
                jsonObject.put("sort", i + 1);
                jsonArray.put(jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    /**
     * 生成商品规格 specificationAndValJson
     */
    private JSONArray specificationAndValJson() {
        JSONArray jsonArray = new JSONArray();
        try {
            int piceSize = editSpecificationList.size();
            int nameSize = editSpecificationNameList.size();
            if (piceSize == nameSize) {
                for (int i = 0; i < piceSize; i++) {
                    JSONObject jsonObject = new JSONObject();
                    String trim = editSpecificationNameList.get(i).getText().toString().trim();
                    ArrayList<EditText> editTexts = editSpecificationList.get(i);
                    StringBuilder str = new StringBuilder();
                    //生成商品规格的json串
                    for (int j = 0; j < editTexts.size(); j++) {
                        String pice = editTexts.get(j).getText().toString().trim();
                        str.append(pice + ",");
                    }
                    //商品名称
                    jsonObject.put("specificationName", trim);
                    //商品规格值
                    jsonObject.put("specificationValue", str);
                    jsonArray.put(jsonObject);
                }
            } else {
                ToastUtils.show("生成规格错误");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    /**
     * productSkuJSON  规格名称
     */
    private JSONArray productSkuJSON() {
        JSONArray jsonArray = new JSONArray();
        if (specificationNewData.size() == Constant.NO0) {
            ToastUtils.show("请填写商品规格");
            return null;
        }
        try {
            for (int i = 0; i < specificationNewData.size(); i++) {
                JSONObject jsonObject = new JSONObject();
                CommoditySpecificationBean bean = specificationNewData.get(i);
                String content = bean.getSpecification();
                String num = bean.getInventory();
                String address = bean.getImagePath();
                String salePrice = bean.getPrice();
                if (TextUtils.isEmpty(content) || TextUtils.isEmpty(num) || TextUtils.isEmpty(salePrice)) {
                    ToastUtils.show("请填写完整商品规格");
                    return null;
                }
                if (content.endsWith("-")) {
                    content = content.substring(0, content.length() - 1);
                }
                //商品规格值
                jsonObject.put("content", content);
                //商品数量
                jsonObject.put("num", num);
                //商品规格图片地址
                jsonObject.put("address", address);
                Double aDouble = Double.valueOf(salePrice);
                int v = (int) (aDouble * 10000);
                //商品价格  *10000
                jsonObject.put("salePrice", String.valueOf(v));
                jsonArray.put(jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    /**
     * 设置商品回显数据
     */
    private void setHunterProductContent(IssueCommodityNewsBean.ContentBean content) {
        if (content == null) {
            return;
        }
        //商品的信息
        IssueCommodityNewsBean.ContentBean.ProductJsonBean productJson = content.getProductJson();
        String name = productJson.getName();
        String title = productJson.getTitle();
        goodsId = productJson.getId();
        productTypeId = String.valueOf(productJson.getProductTypeId());
        //商品审核状态
        int status = productJson.getStatus();
        if (status != 1) {
            tvClassify.setOnClickListener(this);
        } else {
            tvClassify.setOnClickListener(null);
        }
        //运费模板的id
        freightRuleMainId = productJson.getFreightRuleMainId() + "";
        templateAdapter.setDefSelect(Integer.valueOf(freightRuleMainId));
        templateAdapter.notifyDataSetChanged();
        //回显商品分类
        //一级分类
        String firstProductTypeName = content.getFirstProductTypeName();
        //二级分类
        String towProductTypeName = content.getTowProductTypeName().getClassify_name();
        String names = firstProductTypeName + " > " + towProductTypeName;
        tvClassify.setText(names);
        tvClassify.setTextColor(Color.BLACK);
        //商品详情回显数据
        List<IssueCommodityNewsBean.ContentBean.DescriptionBean> description = content.getDescription();
        for (int i = 0; i < description.size(); i++) {
            IssueCommodityNewsBean.ContentBean.DescriptionBean descriptionBean = description.get(i);
            int type = descriptionBean.getType();
            switch (type) {
                case 0:
                    //文字
                    String content1 = descriptionBean.getContent();
                    srEditor.addText(content1);
                    break;
                case 1:
                    //图片
                    String content2 = descriptionBean.getContent();
                    srEditor.addNetImage(content2, false, context, false);
                    break;
                case 2:
                    //视频
                    String content3 = descriptionBean.getContent();
                    //视频第一帧
                    String viewImg = descriptionBean.getView_img();
                    videoFirstPath.add(viewImg);
                    srEditor.addNetImage(viewImg, content3, true, context, false);
                    break;
                default:
                    break;
            }
        }
        if (!TextUtils.isEmpty(name)) {
            commodityName.setText(name);
        }
        if (!TextUtils.isEmpty(title)) {
            commodityHeadline.setText(title);
        }
        //image 数组
        List<IssueCommodityNewsBean.ContentBean.ImageJSONBean> imageJSON = content.getImageJSON();
        getTitleImageList.clear();
        for (int i = 0; i < imageJSON.size(); i++) {
            IssueCommodityNewsBean.ContentBean.ImageJSONBean imageJSONBean = imageJSON.get(i);
            ConsultItemBean imageBean = new ConsultItemBean();
            imageBean.setmPhotoPath(imageJSONBean.getAddress());
            getTitleImageList.add(imageBean);
        }
        chooseImageSpecificationAdapter.notifyDataSetChanged();
        if (getTitleImageList.size() > 0) {
            gridHint.setVisibility(View.GONE);
        } else {
            gridHint.setVisibility(View.VISIBLE);
        }
        //属性值
        List<IssueCommodityNewsBean.ContentBean.SpecificationAndValJsonBean> specificationAndValJson = content.getSpecificationAndValJson();
        for (int i = 0; i < specificationAndValJson.size(); i++) {
            IssueCommodityNewsBean.ContentBean.SpecificationAndValJsonBean specificationAndValJsonBean = specificationAndValJson.get(i);
            addCommoditySpecification(specificationAndValJsonBean);
        }
        //规格回显
        List<IssueCommodityNewsBean.ContentBean.ProductSkuJSONBean> productSkuJSON = content.getProductSkuJSON();
        specificationNewData.clear();
        for (int i = 0; i < productSkuJSON.size(); i++) {
            IssueCommodityNewsBean.ContentBean.ProductSkuJSONBean productSkuJSONBean = productSkuJSON.get(i);
            //规格
            String content1 = productSkuJSONBean.getContent();
            //价格
            String salePrice = productSkuJSONBean.getSalePrice() + "";
            //库存
            String num = productSkuJSONBean.getNum() + "";
            //地址
            String address = productSkuJSONBean.getAddress();
            CommoditySpecificationBean specificationBean = new CommoditySpecificationBean(content1, salePrice, num, address);
            specificationNewData.add(specificationBean);
        }
        if (specificationListAdapter == null) {
            specificationListAdapter = new SpecificationListAdapter(this, specificationNewData, mOnClick);
            if (generateSpecification != null) {
                generateSpecification.setAdapter(specificationListAdapter);
            }
        } else {
            specificationListAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 初始化PopWindow
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
        popupWindow2.showAtLocation(this.findViewById(R.id.linear_father_view), Gravity.BOTTOM, 0, 0);
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
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        //0.0-1.0
        lp.alpha = bgAlpha;
        this.getWindow().setAttributes(lp);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getFreightTemplateData();
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
                        address = aMapLocation.getCity();
                        addProductByHunter();
                    } else {
                        EasyPermissions.requestPermissions(AddNewCommodityActivity.this, "需要定位权限", NO100, permission);
                        //定位失败
                        MyLog.e("response", "失败码===" + aMapLocation.getErrorCode());
                    }
                }
            }
        });
        mLocationClient.startLocation();
    }

    /**
     * 定位权限成功
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        initLocation();
    }

    /**
     * 定位权限失败
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        ToastUtils.show("请求权限失败");
    }

    /**
     * 点击添加图片或者添加视频的时候判断一下图片或者视频的个数
     */
    private boolean getEditorDataCount() {
        int imageCount = 0;
        int videoCount = 0;
        List<SEditorData> sEditorDatas = srEditor.buildEditData();
        //生成商品详情
        for (int i = 0; i < sEditorDatas.size(); i++) {
            SEditorData sEditorData = sEditorDatas.get(i);
            String imagePath = sEditorData.getImagePath();
            String videoPath = sEditorData.getVideoPath();
            if (imagePath != null) {
                //图片
                imageCount++;
            } else if (videoPath != null) {
                //视频
                videoCount++;
            }
        }
        if (imageCount >= Constant.NO10) {
            ToastUtils.show("添加图片不可以超过十张");
            return false;
        }
        if (videoCount >= Constant.NO1) {
            ToastUtils.show("添加视频不可以超过一个");
            return false;
        }
        return true;
    }

}
