package com.yst.onecity.activity.home;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.MyLog;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.PublishClassifyBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.AddImageOrVideo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.yst.onecity.Constant.NO1;


/**
 * 首页-编辑视频
 *
 * @author songbinbin
 * @version 1.0.1
 * @date 2018/2/25
 */
public class EditVideoActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.editVideo_rl_add_product_plan)
    RelativeLayout isAddProduct;
    @BindView(R.id.editVideo_rl_add_product_plan2)
    RelativeLayout productShow;
    @BindView(R.id.edit_video_frameLayoutVideo)
    FrameLayout flVideo;
    @BindView(R.id.edit_video_imageViewVideo)
    ImageView imgViewVideo;
    @BindView(R.id.editVideo_title)
    EditText etTitle;
    @BindView(R.id.editVideo_tv_product_text)
    TextView tvProductTitle;

    /**
     * 视频路径
     */
    private String videoPath;
    /**
     * 视频封面路径
     */
    private String framePicPath;
    /**
     * 添加产品计划请求码
     */
    private final int REQUEST_CODE = 1;
    /**
     * 选择分类dialog
     */
    private Dialog dialog;
    /**
     * 选择的分类
     */
    private List<PublishClassifyBean.ContentBean> classifyList = new ArrayList<>();
    /**
     * 分类
     */
    private List<PublishClassifyBean.ContentBean> mListText;
    SimpleAdapter simpleAdapter;

    /**
     * 产品计划ID
     */
    private String productPlanId = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_editvideo;
    }

    @Override
    public void initData() {
        initTitleBar("编辑视频");
        setRightText("发布");
        setRightTextVisibility(View.VISIBLE);
        setRightTextViewClickable(true);

        //视频路径
        videoPath = getIntent().getStringExtra("videoPath");
        //视频封面路径
        framePicPath = getIntent().getStringExtra("framePicPath");
        //设置视频封面图
        Glide.with(EditVideoActivity.this).load(framePicPath).error(R.mipmap.jc_play_normal).into(imgViewVideo);

    }

    public void upVideoImg(final String vPath){
        AddImageOrVideo.upLoadImg(EditVideoActivity.this, framePicPath, new AddImageOrVideo.UploadImgListener() {
            @Override
            public void uploadImgListener(String msg) {
                //上传成功后发布
                publish(vPath,msg);
            }
        });
    }

    /**
     * 发布
     */
    public void publish(String vPath,String iPath){
        String title = etTitle.getText().toString().trim();
        //分类
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < classifyList.size(); i++) {
            if(i>0){
                sb.append(",");
                sb.append(classifyList.get(i).getId());
            }else {
                sb.append(classifyList.get(i).getId());
            }
        }
        String classifyId = sb.toString();
        RequestApi.publishVideo(App.manager.getImId()+"",title, App.manager.getUuid(), vPath, iPath, App.manager.getPhoneNum(), productPlanId, classifyId, new AbstractNetWorkCallback<MsgBean>() {
            @Override
            public void onSuccess(MsgBean msgBean) {
                ToastUtils.show(msgBean.getMsg());
                if(msgBean.getCode() == 1) {
                    finish();
                }
            }
            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
                MyLog.e("publishVideo:onError",errorMsg);
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

    @Override
    public void setListener() {
        //添加产品计划/回显
        isAddProduct.setOnClickListener(this);
        productShow.setOnClickListener(this);
        //右上角文字点击监听
        setRightTextViewOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Utils.isClickable()) {
                    return;
                }
                //获取分类列表
                getPublishClassify();
            }
        });

        //图文资讯标题输入监听
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
                    etTitle.setText(editable);
                    //设置光标在最后
                    etTitle.setSelection(num);
                }
            }
        });

        //点击跳转到播放页
        flVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(videoPath)) {
                    Intent intent = new Intent(EditVideoActivity.this, WidthMatchVideoActivity.class);
                    intent.putExtra("videoPath", videoPath);
                    startActivity(intent);
                } else {
                    ToastUtils.show("视频路径无效");
                }
            }
        });
    }



    /**
     * 获取指定文件大小
     *
     * @param file 文件
     * @return 文件大小
     * @throws IOException io异常
     */
    private static long getFileSize(File file) throws IOException {
        long size = 0;
        FileInputStream fis = null;
        try {
            if (file.exists()) {
                fis = new FileInputStream(file);
                size = fis.available();
                fis.close();
            } else {
                Log.e("获取文件大小", "文件不存在!");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(null != fis){
                fis.close();
            }
        }

        return size;
    }

    /**
     * 上传视频删除本地资源
     *
     * @param keyCode code
     * @param event 事件
     * @return boolean
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            //退出时删除录制的视频和图片
            if (!TextUtils.isEmpty(videoPath)) {
                File file = new File(videoPath);
                file.delete();
            }
            if (!TextUtils.isEmpty(framePicPath)) {
                File file = new File(framePicPath);
                file.delete();
            }

            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //添加产品计划回显
        if (REQUEST_CODE == requestCode) {
            if(resultCode == RESULT_OK) {
                //产品计划id
                productPlanId = data.getStringExtra("id");
                //产品计划标题
                String productPlanTitle = data.getStringExtra("title");
                if (TextUtils.isEmpty(productPlanId)) {
                    ToastUtils.show("未选择服务项目");
                } else {
                    isAddProduct.setVisibility(View.GONE);
                    productShow.setVisibility(View.VISIBLE);
                    tvProductTitle.setText(productPlanTitle);
                }
            }else {
                ToastUtils.show("添加服务项目失败");
            }
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            default:
                break;
            case R.id.editVideo_rl_add_product_plan:
            case R.id.editVideo_rl_add_product_plan2:
                ToastUtils.show("添加计划");
                JumpIntent.jump(this, PublishInfoAddProductPlanActivity.class,REQUEST_CODE);
                break;
            //确定（选择分类弹框）
            case R.id.dialog_multiple_choice_tv_sure:

                //执行发布
                if (TextUtils.isEmpty(videoPath) || TextUtils.isEmpty(framePicPath)) {
                    ToastUtils.show("录制视频失败，请重新录制");
                }else if(TextUtils.isEmpty(etTitle.getText().toString().trim())){
                    ToastUtils.show("标题不能为空");
                }else if(classifyList == null || classifyList.size() <= 0){
                    ToastUtils.show("请选择分类");
                    return;
                }else {
                    dialog.dismiss();
                    //上传视频
                    AddImageOrVideo.upLoadVideo(EditVideoActivity.this, videoPath, new AddImageOrVideo.UploadVideoListener() {
                        @Override
                        public void uploadVideoListener(String msg) {
                            if(msg.endsWith(Constant.VIDEO_FORMAT)){
                                upVideoImg(msg);
                            }else {
                                ToastUtils.show(msg);
                            }
                        }
                    });
                }

                break;
            //取消（选择分类弹框）
            case R.id.dialog_multiple_choice_tv_cancel:
                dialog.dismiss();
                break;
        }
    }

    /**
     * 分类弹窗（多选）
     *
     * @param content 实体
     */
    public void classifyAlert(List<PublishClassifyBean.ContentBean> content) {
        // 动态加载gridView的布局文件进来
        View view = LayoutInflater.from(EditVideoActivity.this).inflate(R.layout.dialog_multiple_choice, null);
        GridView gridView = view.findViewById(R.id.dialog_multiple_choice_gridView);
        TextView tvSure = view.findViewById(R.id.dialog_multiple_choice_tv_sure);
        TextView tvCancel = view.findViewById(R.id.dialog_multiple_choice_tv_cancel);
        tvSure.setOnClickListener(this);
        tvCancel.setOnClickListener(this);

        //模拟数据
        mListText = content;
        ArrayList<Map<String, Object>> mData = new ArrayList<>();
        int length = mListText.size();
        for (int i = 0; i < length; i++) {
            Map<String, Object> item = new HashMap<>(16);
            item.put("text", mListText.get(i).getDescription_name());
            mData.add(item);
        }

        gridView.setAdapter(simpleAdapter = new SimpleAdapter(this, mData, R.layout.item_dialog_multiple_choice_grid, new String[]{"text"},
                new int[]{R.id.item_dialog_multiple_choice_tv_classify}));
        gridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);
        gridView.setOnItemClickListener(new ItemOnClick());

        dialog = new Dialog(this, R.style.prompt_progress_dailog_dimenabled);
        //必须用setContentView后面设置属性才生效 不能用 setView（）
        dialog.setContentView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        dialog.show();
        //设置dialog属性必 须先 show 在设置否则空指针
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //设置dialog 在布局中的位置
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        //设置dialog的背景颜色为透明色,就可以显示圆角了!!
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    /**
     * 获取发布资讯选择发布分类
     */
    private void getPublishClassify() {
        RequestApi.getPublishClassify(new AbstractNetWorkCallback<PublishClassifyBean>() {
            @Override
            public void onSuccess(PublishClassifyBean publishClassifyBean) {
                if (publishClassifyBean != null && publishClassifyBean.getCode() == NO1) {
                    if (publishClassifyBean.getContent() != null) {
                        List<PublishClassifyBean.ContentBean> content = publishClassifyBean.getContent();
                        //选择分类弹框
                        classifyAlert(content);
                    }
                }
            }
            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
                MyLog.e("getClassify:onError",errorMsg);
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
     * 分类弹框item点击监听
     */
    class ItemOnClick implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
            CheckBox cBox = view.findViewById(R.id.item_dialog_multiple_choice_cb);
            if (cBox.isChecked()) {
                cBox.setChecked(false);
                //取消选择时，从被选择分类的集合中移除
                for (int i = 0; i < classifyList.size(); i++) {
                    if (mListText.get(position).getDescription_name().equals(classifyList.get(i).getDescription_name())) {
                        classifyList.remove(i);
                    }
                }
            } else {
                cBox.setChecked(true);
                //被选中时添加到集合
                classifyList.add(mListText.get(position));
            }
        }
    }

}
