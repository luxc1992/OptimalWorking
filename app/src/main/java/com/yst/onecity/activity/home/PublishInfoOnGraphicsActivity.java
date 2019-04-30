package com.yst.onecity.activity.home;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.MyLog;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.R;
import com.yst.onecity.adapter.PublishInfoTitleImageAdapter;
import com.yst.onecity.bean.ConsultItemBean;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.PublishClassifyBean;
import com.yst.onecity.bean.home.PublishInfoContentBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.AddImageOrVideo;
import com.yst.onecity.view.editor.SEditorData;
import com.yst.onecity.view.editor.SortRichEditor;

import org.greenrobot.eventbus.EventBus;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

import butterknife.BindView;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

import static com.yst.onecity.Constant.IMG_FORMAT;
import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO100;
import static com.yst.onecity.Constant.NO200;
import static com.yst.onecity.Constant.NO3;

/**
 * 首页-发布图文资讯
 *
 * @author songbinbin
 * @version 1.0.1
 * @date 2018/2/23
 */
public class PublishInfoOnGraphicsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.publish_info_graphics_et_title)
    EditText etTitle;
    @BindView(R.id.publish_info_graphics_rl_add_product_plan)
    RelativeLayout isAddProduct;
    @BindView(R.id.publish_info_graphics_rl_add_product_plan2)
    RelativeLayout productShow;
    @BindView(R.id.publish_info_graphics_et_main_body)
    SortRichEditor sreMain;
    @BindView(R.id.publish_info_graphics_tv_product_text)
    TextView tvProductTitle;
    @BindView(R.id.gv_title_img)
    GridView gvTitleImg;

    /**
     * 添加产品计划请求码
     */
    private final int REQUEST_CODE = 1;
    SimpleAdapter simpleAdapter;
    /**
     * 选择分类dialog
     */
    private Dialog dialog;
    /**
     * 选择的分类
     */
    private List<Integer> classifyList = new ArrayList<>();
    /**
     * 分类
     */
    private List<PublishClassifyBean.ContentBean> mListText;
    private PublishInfoTitleImageAdapter publishInfoTitleImageAdapter;
    /**
     * 标题图片集合
     */
    ArrayList<ConsultItemBean> titleImageList = new ArrayList<>();
    /**
     * 标题图片请求吗
     */
    private int requestCode1 = 111;
    /**
     * 请求访问外部存储
     */
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 103;

    /**
     * 产品计划ID
     */
    private String productPlanId = "";

    /**
     * 图片真实宽高
     */
    private int imgTrueWidth;
    private int imgTrueHeight;
    private String path;
    /**
     * 替换图片位置
     */
    int replacePosition = -1;
    /**
     * true：替换 false：添加 图片
     */
    boolean reOrAdd = false;

    private AddImgHandler addImgHandler = new AddImgHandler(this);

    class AddImgHandler extends Handler{
        private final WeakReference<Activity> weakReference;

        AddImgHandler(Activity activity) {
            this.weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(null == weakReference.get()){
                return;
            }
            //判断标志位
            switch (msg.what) {
                case 1:
                    sreMain.addNetImage(path, false, PublishInfoOnGraphicsActivity.this, false, imgTrueWidth, imgTrueHeight);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_publish_info_on_graphics;
    }
    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().post(new EventBean("publish"));
    }
    @Override
    public void initData() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        initTitleBar("资讯");
        setRightText("发布");
        setRightTextVisibility(View.VISIBLE);
        setRightTextViewClickable(true);
        initCtrls();
    }

    /**
     * 初始化图片标题适配器
     */
    public void initCtrls() {
        publishInfoTitleImageAdapter = new PublishInfoTitleImageAdapter(titleImageList, PublishInfoOnGraphicsActivity.this);
        gvTitleImg.setAdapter(publishInfoTitleImageAdapter);
        if (publishInfoTitleImageAdapter != null) {
            publishInfoTitleImageAdapter.setIScrollPositon(new PublishInfoTitleImageAdapter.IGetScrollPosition() {
                @Override
                public void click(int i, int event) {
                    //删除
                    if(event == NO0) {
                        titleImageList.remove(i);
                    //替换图片
                    }else if(event == NO1){
                        if (!Utils.isClickable()) {
                            return;
                        }
                        replacePosition = i;
                        int num;
                        if(replacePosition <= titleImageList.size()-1){
                            num = NO1;
                            reOrAdd = true;
                        }else {
                            num = NO3 - titleImageList.size();
                            reOrAdd = false;
                        }
                        //权限判断
                        if (ContextCompat.checkSelfPermission(PublishInfoOnGraphicsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            //申请READ_EXTERNAL_STORAGE权限
                            ActivityCompat.requestPermissions(PublishInfoOnGraphicsActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_REQUEST_CODE);
                        } else {
                            MultiImageSelector
                                    .create()
                                    .multi()
                                    .count(num)
                                    .start(PublishInfoOnGraphicsActivity.this, requestCode1);
                        }
                    }
                    publishInfoTitleImageAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    @OnClick({R.id.publish_info_graphics_rl_add_product_plan,
            R.id.publish_info_graphics_rl_add_product_plan2, R.id.publish_info_graphics_iv_bodyImg})
    public void onViewClicked(View view) {
        if (!Utils.isClickable()) {
            return;
        }
        switch (view.getId()) {
            //添加产品计划
            case R.id.publish_info_graphics_rl_add_product_plan:
            case R.id.publish_info_graphics_rl_add_product_plan2:
                JumpIntent.jump(this, PublishInfoAddProductPlanActivity.class, REQUEST_CODE);
                break;
            //添加正文图片
            case R.id.publish_info_graphics_iv_bodyImg:
                if(Utils.isClickable()){
                    AddImageOrVideo.localImg(this,NO100);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void setListener() {
        //标题右侧文字点击监听
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

            }
        });
    }

    /**
     * 分类弹窗（多选）
     *
     * @param content 实体
     */
    public void classifyAlert(List<PublishClassifyBean.ContentBean> content) {
        // 动态加载gridView的布局文件进来
        View view = LayoutInflater.from(PublishInfoOnGraphicsActivity.this).inflate(R.layout.dialog_multiple_choice, null);
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

        //监听dialog 消失时清空已选分类
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                classifyList.clear();
            }
        });

        //设置dialog属性必 须先 show 在设置否则空指针
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //设置dialog 在布局中的位置
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        //设置dialog的背景颜色为透明色,就可以显示圆角了!!
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //确定（选择分类弹框）
            case R.id.dialog_multiple_choice_tv_sure:
                //执行发布
                publish();
                break;
            //取消（选择分类弹框）
            case R.id.dialog_multiple_choice_tv_cancel:
                dialog.dismiss();
                break;
            default:
                break;
        }
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
                    Log.i("11111111111","" + mListText.get(position).getId()  + "classfiyList" +classifyList.get(i) );
                    if (mListText.get(position).getId() == classifyList.get(i)) {
                        classifyList.remove(i);
                    }
                }
            } else {
                cBox.setChecked(true);
                //被选中时添加到集合
                classifyList.add(mListText.get(position).getId());
            }
        }
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
        if (data == null) {
            return;
        }
        //封面图
        if (requestCode == requestCode1) {
            // 获取返回的图片列表(存放的是图片路径)
            ArrayList<String> stringArrayListExtra = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
            uploadPic(stringArrayListExtra);
        }

        //内容图片
        if(requestCode == NO100){
            Uri uri = data.getData();
            String imgPath = AddImageOrVideo.getRealFilePathFromUri(this,uri);
            AddImageOrVideo.upLoadImg(this, imgPath, new AddImageOrVideo.UploadImgListener() {
                @Override
                public void uploadImgListener(String msg) {
                    if (msg.endsWith(IMG_FORMAT)) {
                        path = msg;
                        getImageWidthHeight(msg, new IGetImgWidhtHeight() {
                            @Override
                            public void ballWidthHeight(String msg) {
                                if (msg.equals(NO1+"")){
                                    //从全局池中返回一个message实例，避免多次创建message（如new Message）
                                    Message m =Message.obtain();
                                    //标志消息的标志
                                    m.what=1;
                                    addImgHandler.sendMessage(m);
                                }
                            }
                        });
                    } else {
                        ToastUtils.show(msg);
                    }
                }
            });
        }
    }

    /**
     * 上传图片
     */
    public void uploadPic(List<String> imgPathList){
        for (int i=0;i<imgPathList.size();i++) {
            AddImageOrVideo.upLoadImg(PublishInfoOnGraphicsActivity.this, imgPathList.get(i), new AddImageOrVideo.UploadImgListener() {
                @Override
                public void uploadImgListener(String msg) {
                    if (msg.endsWith(IMG_FORMAT)) {
                        ConsultItemBean consultItemBean = new ConsultItemBean();
                        consultItemBean.setmPhotoPath(msg);
                        //替换
                        if(reOrAdd) {
                            if(replacePosition < 0){
                                return;
                            }
                            titleImageList.set(replacePosition,consultItemBean);
                        //添加
                        }else{
                            titleImageList.add(consultItemBean);
                        }
                        publishInfoTitleImageAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtils.show(msg);
                    }
                }
            });
        }
    }

    /**
     * 发布
     */
    public void publish(){
        //标题
        String title = etTitle.getText().toString().trim();

        //判断限制
        if(TextUtils.isEmpty(title)){
            ToastUtils.show("标题最少一个字");
            //取消分类框
            dialog.dismiss();
            return;
        }else if(null == titleImageList || titleImageList.size()<=0){
            ToastUtils.show("封面图最少添加一张");
            //取消分类框
            dialog.dismiss();
            return;
        }else if(classifyList == null || classifyList.size() <= 0){
            ToastUtils.show("请选择分类");
            return;
        }
        //取消分类框
        dialog.dismiss();

        //正文
        List<SEditorData> seDatas = sreMain.buildEditData();
        List<PublishInfoContentBean> content = new ArrayList<>();
        if(!sreMain.isContentEmpty()) {
            for (int i = 0; i < seDatas.size(); i++) {
                PublishInfoContentBean pBean = new PublishInfoContentBean();
                if(!TextUtils.isEmpty(seDatas.get(i).getInputStr())){
                    pBean.sort = "" + i;
                    pBean.type = "0";
                    pBean.detail = seDatas.get(i).getInputStr();
                }else if(!TextUtils.isEmpty(seDatas.get(i).getImagePath())){
                    pBean.sort = "" + i;
                    pBean.type = "1";
                    pBean.detail = seDatas.get(i).getImagePath();
                }
                content.add(pBean);
            }
        }
        String contentStr = "";
        if(content.size() > 0){
            Gson gson = new Gson();
            contentStr = gson.toJson(content);
        }

        //分类
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < classifyList.size(); i++) {
            if(i>0){
                sb.append(",");
                sb.append(classifyList.get(i));
            }else {
                sb.append(classifyList.get(i));
            }
        }
        String classifyId = sb.toString();

        //封面图
        StringBuilder sb2 = new StringBuilder();
        for(int i=0;i<titleImageList.size();i++){
            if(i>0){
                sb2.append(",");
                sb2.append(titleImageList.get(i).getAddress());
            }else {
                sb2.append(titleImageList.get(i).getAddress());
            }
        }
        String imgUrl = sb2.toString();

        RequestApi.publishInfoOnGraphics(title, App.manager.getUuid(), contentStr, "", imgUrl, App.manager.getPhoneNum(), productPlanId, classifyId,titleImageList.size()+"", new AbstractNetWorkCallback<MsgBean>() {

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
                MyLog.e("publishInfo:onError:",errorMsg);
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

    public interface IGetImgWidhtHeight{
        /**
         * 返回图片真实宽高
         * @param msg 信息
         */
        void ballWidthHeight(String msg);
    }
    public void getImageWidthHeight(final String imagePath, final IGetImgWidhtHeight callBack){

        class LoggerThreadFactory implements ThreadFactory {
            @Override
            public Thread newThread(@NonNull Runnable r) {
                Thread t = new Thread(r);
                t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {

                    }
                });
                return t;
            }
        }

        ScheduledThreadPoolExecutor scheduledExecutorService = new ScheduledThreadPoolExecutor(1,new LoggerThreadFactory());
        scheduledExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conn = null;
                try {
                    // 创建URL对象
                    URL myUri = new URL(imagePath);
                    // 创建链接
                    conn = (HttpURLConnection) myUri.openConnection();
                    // 设置链接超时
                    conn.setConnectTimeout(10000);
                    conn.setReadTimeout(5000);
                    // 设置请求方法为get
                    conn.setRequestMethod("GET");
                    conn.connect();// 开始连接
                    int responseCode = conn.getResponseCode();
                    if (responseCode == NO200) {
                        InputStream is = conn.getInputStream();
                        //用options 只读取图片的信息头得到图片宽高而不将图片加载到内存中
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        //最关键在此，把options.inJustDecodeBounds = true;
                        //这里再decodeFile()，返回的bitmap为空，但此时调用options.outHeight时，已经包含了图片的高了
                        options.inJustDecodeBounds = true;
                        // 此时返回的bitmap为null
                        BitmapFactory.decodeFile(imagePath, options);
                        BitmapFactory.decodeStream(is, null, options);
                        //options.outHeight为原始图片的高
                        imgTrueWidth = options.outWidth;
                        imgTrueHeight = options.outHeight;
                        callBack.ballWidthHeight("1");
                        is.close();
                    } else {
                        callBack.ballWidthHeight("0");
                        Log.i("获取图片信息:", "访问失败：responseCode=" + responseCode);
                        ToastUtils.show("获取图片信息:网络请求超时");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != addImgHandler) {
            addImgHandler.removeCallbacksAndMessages(null);
            addImgHandler = null;
        }
    }
}
