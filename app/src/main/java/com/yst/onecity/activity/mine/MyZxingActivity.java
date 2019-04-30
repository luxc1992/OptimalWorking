package com.yst.onecity.activity.mine;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lxc.sharelibrary.ShareActivity;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.onecity.R;
import com.yst.onecity.bean.mine.MyZxingBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.ZxingUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yst.onecity.Constant.shareContent;
import static com.yst.onecity.Constant.shareTitle;

/**
 * 我的二维码
 *
 * @author liumanqing
 * @version 1.0.1
 * @date 2018/2/22
 */
public class MyZxingActivity extends BaseActivity {
    @BindView(R.id.zxing_head)
    ImageView head;
    @BindView(R.id.zxing_name)
    TextView name;
    @BindView(R.id.zxing_share)
    Button share;
    @BindView(R.id.zxing_img)
    ImageView zXingImg;
    Bitmap bitmap;
    /**
     * 分享的地址
     */
    private String shareUrl;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_zxing;
    }

    @Override
    public void initData() {
        initTitleBar(getString(R.string.myzxing));
        //开启一个新的线程
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        requestData();
    }

    /**
     * 获取用户信息：头像，昵称，二维码
     */
    private void requestData() {
        RequestApi.myZxing(String.valueOf(App.manager.getId()),App.manager.getUuid(), App.manager.getPhoneNum(), new AbstractNetWorkCallback<MyZxingBean>() {
            @Override
            public void onSuccess(MyZxingBean myZxingBean) {
                int code = myZxingBean.getCode();
                if (code == 1) {
                    if (myZxingBean.getContent().getHeadImg() != null) {
                        bitmap = netPicToBmp(myZxingBean.getContent().getHeadImg());
                        zXingImg.setImageBitmap(ZxingUtils.createQRCodeBitmap(myZxingBean.getContent().getUrl(), 800, bitmap, 2));
                        Glide.with(MyZxingActivity.this).load(myZxingBean.getContent().getHeadImg()).error(R.mipmap.default_nor_avatar).into(head);
                    } else {
                        bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.default_nor_avatar);
                        zXingImg.setImageBitmap(ZxingUtils.createQRCodeBitmap(myZxingBean.getContent().getUrl(), 800, bitmap, 2));
                        Glide.with(MyZxingActivity.this).load(myZxingBean.getContent().getHeadImg()).error(R.mipmap.default_nor_avatar).into(head);
                    }
                    name.setText(myZxingBean.getContent().getUsername());
                    shareUrl = myZxingBean.getContent().getUrl();
                } else {
                    ToastUtils.show(myZxingBean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    @OnClick({R.id.zxing_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.zxing_share:
                ShareActivity shareActivity = ShareActivity.getInstance();
                shareActivity.setNoCircleOfFriends(true);
                shareActivity.popShare(MyZxingActivity.this, shareTitle, shareContent, shareUrl + "&token=" + App.manager.getToken(), R.drawable.logo_icon);
                break;
            default:
                break;
        }
    }

    /**
     * 网络图片转换为Bitmap
     */
    public static Bitmap netPicToBmp(String src) {
        try {
            Log.d("FileUtil", src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            //设置固定大小
            //需要的大小
            float newWidth = 200f;
            float newHeigth = 200f;

            //图片大小
            int width = myBitmap.getWidth();
            int height = myBitmap.getHeight();

            //缩放比例
            float scaleWidth = newWidth / width;
            float scaleHeigth = newHeigth / height;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeigth);

            return Bitmap.createBitmap(myBitmap, 0, 0, width, height, matrix, true);
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }
}
