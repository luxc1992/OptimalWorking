package com.yst.onecity.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.R;
import com.yst.onecity.bean.TagMsg;
import com.yst.onecity.bean.issue.CommodityBean;
import com.yst.onecity.view.DragView;
import com.yst.onecity.view.dialog.AbstractSelectProductDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 编辑照片
 *
 * @author chejianqi
 * @version 1.0.1
 * @date 2018/5/21
 */

public class EditPhotoActivity extends BaseActivity implements View.OnClickListener, View.OnLongClickListener {
    @BindView(R.id.edit_photo)
    ImageView editPhoto;
    @BindView(R.id.relative)
    RelativeLayout relative;
    List<CommodityBean.ContentBean> productList = new ArrayList<>();
    private String imgTv = "img_path";
    private String imgPath = "img_path";
    private String listName = "tagList";
    private AbstractSelectProductDialog dialog;
    private ArrayList<DragView> viewList = new ArrayList<>();
    private ArrayList<TagMsg> tagList = new ArrayList<>();
    private ArrayList<TagMsg> requestList = new ArrayList<>();
    private ArrayList<DragView> requestView = new ArrayList<>();

    @Override
    public int getLayoutId() {

        return R.layout.activity_edit_photo;
    }

    @Override
    public void initData() {
        initTitleBar("编辑图片");
        setRightText("继续");
        setRightTextViewOnClickListener(this);
        if (getIntent().hasExtra(imgTv)) {
            if (!TextUtils.isEmpty(getIntent().getStringExtra(imgTv))) {
                imgPath = getIntent().getStringExtra("img_path");
                Glide.with(this)
                        .load(imgPath)
                        .asBitmap()//强制Glide返回一个Bitmap对象
                        .listener(new RequestListener<String, Bitmap>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Bitmap bitmap, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                int height = bitmap.getHeight();
                                if (getIntent().hasExtra(listName)) {
                                    requestList.addAll((ArrayList<TagMsg>) getIntent().getSerializableExtra(listName));
                                }
                                for (int i = 0; i < requestList.size(); i++) {
                                    final DragView dragView = new DragView(EditPhotoActivity.this, editPhoto, requestList.get(i), height);
                                    relative.addView(dragView);
                                    requestView.add(dragView);
                                }
                                return false;
                            }
                        }).into(editPhoto);

            }
        }
        dialog = new AbstractSelectProductDialog(this) {
            @Override
            public void sureClick() {
                JumpIntent.jump(EditPhotoActivity.this, SelectProductActivity.class, 10001);
            }
        };
        editPhoto.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //之前添加的
        for (int i = 0; i < requestList.size(); i++) {
            TagMsg tagMsg = new TagMsg();
            tagMsg.setProductName(requestList.get(i).getProductName());
            tagMsg.setProductId(requestList.get(i).getProductId());
            tagMsg.setLabelY(Utils.doubleToString(((double) requestView.get(i).getTop() / (double) editPhoto.getHeight()) * 100));
            tagMsg.setLabelX(Utils.doubleToString(((double) requestView.get(i).getLeft() / (double) editPhoto.getWidth()) * 100));
            tagList.add(tagMsg);
        }
        //新添加的
        for (int i = 0; i < viewList.size(); i++) {
            TagMsg tagMsg = new TagMsg();
            tagMsg.setProductName(productList.get(i).getName());
            tagMsg.setProductId(productList.get(i).getProductId());
            tagMsg.setLabelY(Utils.doubleToString(((double) viewList.get(i).getTop() / (double) editPhoto.getHeight()) * 100));
            tagMsg.setLabelX(Utils.doubleToString(((double) viewList.get(i).getLeft() / (double) editPhoto.getWidth()) * 100));
            tagList.add(tagMsg);
        }

        Intent intent = new Intent();
        intent.putExtra("tagList", tagList);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        requestList.clear();
    }

    @Override
    public boolean onLongClick(View v) {
        dialog.showDialog();
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        dialog.dismissDialog();
        if (resultCode != RESULT_OK) {
            return;
        }
        List<CommodityBean.ContentBean> contentBeansList = (List<CommodityBean.ContentBean>) data.getSerializableExtra("productList");
        productList.addAll(contentBeansList);
        for (int i = 0; i < contentBeansList.size(); i++) {
            TagMsg tagMsg = new TagMsg();
            tagMsg.setLabelX("0");
            tagMsg.setLabelY("0");
            tagMsg.setProductName(contentBeansList.get(i).getName());
            tagMsg.setProductName(contentBeansList.get(i).getName());
            tagMsg.setProductId(contentBeansList.get(i).getProductId());
            final DragView dragView = new DragView(EditPhotoActivity.this, editPhoto, tagMsg, editPhoto.getHeight());
            relative.addView(dragView);
            viewList.add(dragView);
        }
    }
}
