package com.yst.onecity.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.utils.WindowUtils;
import com.yst.onecity.R;
import com.yst.onecity.adapter.GridViewAddImagesAdapter;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.ServiceClassifyBean;
import com.yst.onecity.bean.ServiceDataBean;
import com.yst.onecity.bean.TagListBean;
import com.yst.onecity.bean.TagMsg;
import com.yst.onecity.bean.home.PublishInfoContentBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.AddImageOrVideo;
import com.yst.onecity.utils.CommonUtils;
import com.yst.onecity.view.editor.CommoditySortRichEditor;
import com.yst.onecity.view.editor.SEditorData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

import static android.view.ViewGroup.FOCUS_BEFORE_DESCENDANTS;
import static android.view.ViewGroup.FOCUS_BLOCK_DESCENDANTS;
import static com.yst.onecity.Constant.DOT;
import static com.yst.onecity.Constant.IMG_FORMAT;
import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO2;
import static com.yst.onecity.Constant.NO3;
import static com.yst.onecity.Constant.NO6;

/**
 * 创建服务
 *
 * @author chejianqi
 * @version 1.0.1
 * @date 2018/5/16
 */

public class CreateServeActivity extends BaseActivity implements AdapterView.OnItemClickListener, GridViewAddImagesAdapter.GridViewDeleteListener, View.OnTouchListener {
    public static final int REQUEST_IMAGE_1 = 10011;
    public static final int REQUEST_IMAGE_2 = 10022;
    public static final int REQUEST_IMAGE_3 = 10033;
    public static final int PHOTO_REQUEST_CUT = 10003;
    public static final int TIMES_REQUEST = 1001;
    public static final int CLASSIFY_REQUEST = 1002;
    public static final int EDITPHOTO_REQUEST = 1003;
    public static final int MAX_IMG_NUMBER = 6;
    @BindView(R.id.select_photo)
    ImageView selectPhoto;
    @BindView(R.id.select_photo_add)
    ImageView selectPhotoAdd;
    @BindView(R.id.service_name)
    EditText serviceName;
    @BindView(R.id.product_price)
    EditText productPrice;
    @BindView(R.id.service_time)
    TextView serviceTime;
    @BindView(R.id.is_put_away)
    ImageView isPutAway;
    @BindView(R.id.service_msg_photo)
    ImageView serviceMsgPhoto;
    @BindView(R.id.set_service_img)
    ImageView setServiceImg;
    @BindView(R.id.linear_1)
    LinearLayout linear1;
    @BindView(R.id.select_photo_grid)
    GridView selectPhotoGrid;
    @BindView(R.id.select_come_shop_img)
    ImageView selectComeShopImg;
    @BindView(R.id.select_come_home_img)
    ImageView selectComeHomeImg;
    @BindView(R.id.service_msg_editText)
    CommoditySortRichEditor sreMain;
    @BindView(R.id.select_fen_lei)
    TextView selectClassifyTv;
    @BindView(R.id.top_relative)
    RelativeLayout topRelative;
    @BindView(R.id.bt_del)
    TextView btDel;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    @BindView(R.id.linear_view)
    LinearLayout linearView;
    private String cipPATH;
    private GridViewAddImagesAdapter gridViewAddImagesAdapter;
    private ArrayList<TagListBean> list = new ArrayList<>();
    private int serviceType = 0;
    private boolean putsWay = false;
    private int index;
    private String headImageUrl;
    private int classifyIndex = 0;
    private int classifyId;
    private int type = 1;
    private String serviceId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_create_serve;
    }

    @Override
    public void initData() {
        String t = "type";
        serviceName.clearFocus();
        productPrice.clearFocus();
        sreMain.clearFocus();
        if (getIntent().hasExtra(t)) {
            type = getIntent().getIntExtra("type", 1);
        }


        if (type == NO1) {
            initTitleBar("创建服务");
        } else if (type == NO2) {
            initTitleBar("编辑服务");
            String sId = "serviceId";
            if (getIntent().hasExtra(sId)) {
                serviceId = String.valueOf(getIntent().getIntExtra("serviceId", 0));
            }
            linearView.setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
            getServiceData();
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        gridViewAddImagesAdapter = new GridViewAddImagesAdapter(list, this);
        selectPhotoGrid.setAdapter(gridViewAddImagesAdapter);
        gridViewAddImagesAdapter.notifyDataSetChanged();
        selectPhotoGrid.setOnItemClickListener(this);
        gridViewAddImagesAdapter.setGridViewDeleteListener(this);
        btDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topRelative.setVisibility(View.GONE);
                linear1.setVisibility(View.VISIBLE);
            }
        });
        serviceName.setOnTouchListener(this);
        productPrice.setOnTouchListener(this);
        sreMain.setOnTouchListener(this);
        CommonUtils.setEditTextInputLength(productPrice, 8, true);
        CommonUtils.setEditTextInputLength(serviceName, 15, true);

        productPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

                if (s.toString().contains(DOT)) {
                    if (s.length() - 1 - s.toString().indexOf(DOT) > NO2) {
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + NO3);
                        productPrice.setText(s);
                        productPrice.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(DOT)) {
                    s = "0" + s;
                    productPrice.setText(s);
                    productPrice.setSelection(2);
                }
                if (s.toString().startsWith(String.valueOf(NO0)) && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(NO1, NO2).equals(DOT)) {
                        productPrice.setText(s.subSequence(0, 1));
                        productPrice.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    /**
     * 获取服务数据
     */
    private void getServiceData() {
        RequestApi.getServiceData(serviceId, new AbstractNetWorkCallback<ServiceDataBean>() {
            @Override
            public void onSuccess(ServiceDataBean serviceDataBean) {
                TagListBean tagListBean = null;
                if (serviceDataBean.getCode() == NO1) {
                    topRelative.setVisibility(View.VISIBLE);
                    linear1.setVisibility(View.GONE);
                    headImageUrl = serviceDataBean.getLog();
                    getClassifyData(serviceDataBean.getServiceTypeId());
                    Glide.with(CreateServeActivity.this).load(serviceDataBean.getLog()).centerCrop().into(setServiceImg);
                    if (serviceDataBean.getStatus() == NO0) {
                        putsWay = false;
                        isPutAway.setBackgroundResource(R.mipmap.service_guan);
                    } else {
                        putsWay = true;
                        isPutAway.setBackgroundResource(R.mipmap.service_kai);
                    }
                    if (serviceDataBean.getServiceType() == NO0) {
                        selectComeShopImg.setBackgroundResource(R.mipmap.service_select_true);
                        selectComeHomeImg.setBackgroundResource(R.mipmap.service_select_false);
                    } else {
                        selectComeShopImg.setBackgroundResource(R.mipmap.service_select_false);
                        selectComeHomeImg.setBackgroundResource(R.mipmap.service_select_true);
                    }
                    productPrice.setText(serviceDataBean.getPrice());
                    serviceName.setText(serviceDataBean.getTitle());
                    serviceTime.setText(serviceDataBean.getStartTime() + "至" + serviceDataBean.getEndTime());
                    if (serviceDataBean.getServiceImages() != null) {
                        List<ServiceDataBean.ServiceImagesBean> serviceImages = serviceDataBean.getServiceImages();
                        for (int i = 0; i < serviceImages.size(); i++) {
                            tagListBean = new TagListBean();
                            tagListBean.setName(serviceImages.get(i).getImage_address());
                            if (!TextUtils.isEmpty(serviceImages.get(i).getLabel())) {
                                try {
                                    JSONArray jsonArray = new JSONArray(serviceImages.get(i).getLabel());
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        JSONObject jsonObject = (JSONObject) jsonArray.get(j);
                                        TagMsg tagMsg = new TagMsg();
                                        tagMsg.setProductName(jsonObject.getString("productName"));
                                        tagMsg.setProductId(jsonObject.getString("productId"));
                                        tagMsg.setLabelX(jsonObject.getString("productX"));
                                        tagMsg.setLabelY(jsonObject.getString("productY"));
                                        tagListBean.list.add(tagMsg);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            list.add(tagListBean);
                            Log.e("tag  - size", list.size() + "");
                        }
                    }

                    gridViewAddImagesAdapter.notifyDataSetChanged();
                    if (serviceDataBean.getDescriptionList()!=null){
                        for (int i = 0; i < serviceDataBean.getDescriptionList().size(); i++) {
                            if (serviceDataBean.getDescriptionList().get(i).getType() == NO0) {
                                sreMain.addText(serviceDataBean.getDescriptionList().get(i).getDetail_content());
                            } else if (serviceDataBean.getDescriptionList().get(i).getType() == NO1) {
                                sreMain.addNetImage(serviceDataBean.getDescriptionList().get(i).getDetail_content(), false, CreateServeActivity.this, false);
                            }
                        }
                    }

                    linearView.setDescendantFocusability(FOCUS_BEFORE_DESCENDANTS);
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }


    /**
     * 获取数据 转json
     */
    private void saveData() {
        if (TextUtils.isEmpty(headImageUrl)) {
            ToastUtils.show("请添加封面图");
            return;
        }
        if (TextUtils.isEmpty(selectClassifyTv.getText().toString().trim())) {
            ToastUtils.show("请选择分类");
            return;
        }
        if (TextUtils.isEmpty(productPrice.getText().toString().trim())) {
            ToastUtils.show("请添加项目价格");
            return;
        }
        if (TextUtils.isEmpty(serviceTime.getText().toString().trim())) {
            ToastUtils.show("请选择服务时间");
            return;
        }
        if (sreMain.isContentEmpty()) {
            ToastUtils.show("请填写服务描述");
            return;
        }
        StringBuffer productId = new StringBuffer();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("ImageAddress", list.get(i).getName());
                JSONArray jsonArray1 = new JSONArray();
                for (int j = 0; j < list.get(i).getList().size(); j++) {
                    JSONObject tagJsonObject = new JSONObject();
                    productId.append(list.get(i).getList().get(j).getProductId());
                    productId.append(",");
                    tagJsonObject.put("productName", list.get(i).getList().get(j).getProductName());
                    tagJsonObject.put("productId", list.get(i).getList().get(j).getProductId());
                    tagJsonObject.put("productX", list.get(i).getList().get(j).getLabelX());
                    tagJsonObject.put("productY", list.get(i).getList().get(j).getLabelY());
                    jsonArray1.put(tagJsonObject);
                }
                jsonObject.put("productList", jsonArray1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(jsonObject);
        }
        String substring = "";
        if (!TextUtils.isEmpty(productId.toString())) {
            substring = productId.toString().substring(0, productId.toString().length() - 1);
        }
        List<SEditorData> setData = sreMain.buildEditData();
        List<PublishInfoContentBean> content = new ArrayList<>();
        if (!sreMain.isContentEmpty()) {
            for (int i = 0; i < setData.size(); i++) {
                PublishInfoContentBean pBean = new PublishInfoContentBean();
                if (!TextUtils.isEmpty(setData.get(i).getInputStr())) {
                    pBean.sort = "" + i;
                    pBean.type = "0";
                    pBean.detail = setData.get(i).getInputStr();
                } else if (!TextUtils.isEmpty(setData.get(i).getImagePath())) {
                    pBean.sort = "" + i;
                    pBean.type = "1";
                    pBean.detail = setData.get(i).getImagePath();
                }
                content.add(pBean);
            }
        }
        String contentStr = "";
        if (content.size() > 0) {
            Gson gson = new Gson();
            contentStr = gson.toJson(content);
        }
        String isPutWay;
        if (putsWay) {
            isPutWay = "1";
        } else {
            isPutWay = "0";
        }
        String[] times = serviceTime.getText().toString().split("至");

        RequestApi.toCreateService(headImageUrl, jsonArray.toString(), String.valueOf(serviceType), String.valueOf(classifyId),
                serviceName.getText().toString(), productPrice.getText().toString(), isPutWay, times[0], times[1], String.valueOf(App.manager.getId()), contentStr, substring, type, serviceId, new AbstractNetWorkCallback<MsgBean>() {
                    @Override
                    public void onSuccess(MsgBean msgBean) {
                        ToastUtils.show(msgBean.getMsg());
                        finish();
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
                        ToastUtils.show(errorMsg);
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
        intent.putExtra("aspectX", 4);
        intent.putExtra("aspectY", 3);
        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", WindowUtils.getScreenWidth(this));
        intent.putExtra("outputY", WindowUtils.dip2px(this, 220));
        cipPATH = System.currentTimeMillis() + ".png";
        intent.putExtra("output", Uri.fromFile(new File(Environment.getExternalStorageDirectory(), cipPATH)));
        intent.putExtra("outputFormat", "JPEG");
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (position == list.size() && list.size() < NO6) {
            AddImageOrVideo.localImg(CreateServeActivity.this, REQUEST_IMAGE_2, MAX_IMG_NUMBER - list.size());
        } else {
            index = position;
            Bundle bundle = new Bundle();
            bundle.putString("img_path", list.get(position).getName());
            bundle.putSerializable("tagList", list.get(index).getList());
            JumpIntent.jump(this, EditPhotoActivity.class, bundle, EDITPHOTO_REQUEST);
        }

    }


    public void setEditText(int type) {
        serviceName.clearFocus();
        productPrice.clearFocus();
        sreMain.clearFocus();
        if (type == NO1) {
            serviceName.requestFocus();
        } else if (type == NO2) {
            productPrice.requestFocus();
        } else {
            sreMain.requestFocus();
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.service_name:
                setEditText(1);
                break;
            case R.id.product_price:
                setEditText(2);
                break;
            case R.id.service_msg_editText:
                setEditText(3);
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void deleteListener(int position) {
        list.remove(position);
        gridViewAddImagesAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            //相册
            case REQUEST_IMAGE_1:
                final List<String> list1 = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                if (list1.size() > 0) {
                    startPhotoZoom(Uri.fromFile(new File(list1.get(0))));
                }
                break;
            case REQUEST_IMAGE_2:
                final List<String> list2 = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                for (int i = 0; i < list2.size(); i++) {
                    final TagListBean tagListBean = new TagListBean();
                    AddImageOrVideo.upLoadImg(this, list2.get(i), new AddImageOrVideo.UploadImgListener() {
                        @Override
                        public void uploadImgListener(String msg) {
                            if (msg.endsWith(IMG_FORMAT)) {
                                tagListBean.setName(msg);
                                list.add(tagListBean);
                                gridViewAddImagesAdapter.notifyDataSetChanged();
                            } else {
                                ToastUtils.show(msg);
                            }
                        }
                    });
                }
                break;
            case REQUEST_IMAGE_3:
                List<String> list3 = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                if (list3.size() > 0) {
                    Uri uri = Uri.fromFile(new File(list3.get(0)));
                    String imgPath = AddImageOrVideo.getRealFilePathFromUri(this, uri);
                    AddImageOrVideo.upLoadImg(this, imgPath, new AddImageOrVideo.UploadImgListener() {
                        @Override
                        public void uploadImgListener(String msg) {
                            if (msg.endsWith(IMG_FORMAT)) {
                                sreMain.addNetImage(msg, false, CreateServeActivity.this, false);
                            } else {
                                ToastUtils.show(msg);
                            }
                        }
                    });
                }
                break;
            //裁剪
            case PHOTO_REQUEST_CUT:
                Uri clipPhotoUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), cipPATH));
                String url = AddImageOrVideo.getRealFilePathFromUri(this, clipPhotoUri);
                AddImageOrVideo.upLoadImg(this, url, new AddImageOrVideo.UploadImgListener() {
                    @Override
                    public void uploadImgListener(String msg) {
                        if (msg.endsWith(IMG_FORMAT)) {
                            headImageUrl = msg;
                            Glide.with(CreateServeActivity.this).load(msg).into(setServiceImg);
                        } else {
                            ToastUtils.show(msg);
                        }
                    }
                });

                if (url != null) {
                    topRelative.setVisibility(View.VISIBLE);
                    linear1.setVisibility(View.GONE);
                }
                break;
            //选择时间
            case TIMES_REQUEST:
                serviceTime.setText(data.getStringExtra("time"));
                break;
            //选择分类
            case CLASSIFY_REQUEST:
                classifyId = data.getIntExtra("classifyId", 0);
                classifyIndex = data.getIntExtra("classifyIndex", 0);
                selectClassifyTv.setText(data.getStringExtra("classify_first") + "-" + data.getStringExtra("classify_second"));
                break;
            //编辑图片（添加标签）
            case EDITPHOTO_REQUEST:
                list.get(index).setList((ArrayList<TagMsg>) data.getSerializableExtra("tagList"));
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.select_photo, R.id.save_service, R.id.service_time, R.id.select_fen_lei, R.id.select_photo_add, R.id.select_come_shop, R.id.select_come_home, R.id.classify_select, R.id.select_service_time, R.id.is_put_away, R.id.service_msg_photo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.select_photo:
                AddImageOrVideo.localImg(CreateServeActivity.this, REQUEST_IMAGE_1, NO1);
                break;
            case R.id.select_come_shop:
                serviceType = 0;
                selectComeShopImg.setBackgroundResource(R.mipmap.service_select_true);
                selectComeHomeImg.setBackgroundResource(R.mipmap.service_select_false);
                break;
            case R.id.select_come_home:
                serviceType = 1;
                selectComeShopImg.setBackgroundResource(R.mipmap.service_select_false);
                selectComeHomeImg.setBackgroundResource(R.mipmap.service_select_true);
                break;
            case R.id.classify_select:
            case R.id.select_fen_lei:
                Bundle bundleClassify = new Bundle();
                bundleClassify.putInt("classifyIndex", classifyIndex);
                JumpIntent.jump(this, SelectClassifyActivity.class, bundleClassify, CLASSIFY_REQUEST);
                break;
            case R.id.select_service_time:
            case R.id.service_time:
                Bundle bundle = new Bundle();
                if (!TextUtils.isEmpty(serviceTime.getText().toString())) {
                    bundle.putString("times", serviceTime.getText().toString());
                }
                JumpIntent.jump(this, SelectServiceTimeActivity.class, bundle, TIMES_REQUEST);
                break;
            case R.id.is_put_away:
                putsWay = !putsWay;
                if (putsWay) {
                    isPutAway.setBackgroundResource(R.mipmap.service_kai);
                } else {
                    isPutAway.setBackgroundResource(R.mipmap.service_guan);
                }
                break;
            case R.id.service_msg_photo:
                AddImageOrVideo.localImg(CreateServeActivity.this, REQUEST_IMAGE_3, NO1);
                break;
            case R.id.save_service:
                saveData();
                break;
            default:
                break;
        }
    }

    private void getClassifyData(final int serviceTypeId) {
        RequestApi.getServiceClassifyList(String.valueOf(App.manager.getId()), new AbstractNetWorkCallback<ServiceClassifyBean>() {
            @Override
            public void onSuccess(ServiceClassifyBean serviceClassifyBean) {
                if (serviceClassifyBean.getCode() == NO1) {
                    if (serviceClassifyBean.getContent() != null) {
                        for (int i = 0; i < serviceClassifyBean.getContent().getSecondList().size(); i++) {
                            if (serviceTypeId == serviceClassifyBean.getContent().getSecondList().get(i).getClassId()) {
                                classifyId = serviceClassifyBean.getContent().getSecondList().get(i).getClassId();
                                classifyIndex=i;
                                Log.e("tag", serviceTypeId + "==" + serviceClassifyBean.getContent().getSecondList().get(i).getClassId());
                                selectClassifyTv.setText(serviceClassifyBean.getContent().getFirstName() + "-" + serviceClassifyBean.getContent().getSecondList().get(i).getName());
                            }
                        }
                    }
                } else {
                    ToastUtils.show(serviceClassifyBean.getMsg());
                }

            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }
}
