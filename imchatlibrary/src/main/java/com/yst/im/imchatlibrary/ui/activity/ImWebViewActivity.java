package com.yst.im.imchatlibrary.ui.activity;

import android.content.Intent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.base.BaseActivity;
import com.yst.im.imchatlibrary.bean.WebViewShareEntity;


/**
 * 分享
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/4.
 */
public class ImWebViewActivity extends BaseActivity {
    private ImageView mImgTitlePic;
    private TextView mTxtTitleCenter;
    private TextView mTxtTitleRight;
    private WebView mWvWebHtml;
    private TextView mTvWebTitle;
    private TextView mTvWebContent;
    private ImageView mImgWebPic;
    private WebViewShareEntity webViewShareEntity;

    @Override
    protected int getLayout() {
        return R.layout.activity_im_web_view;
    }

    @Override
    protected int getStatusColor() {
        return 0;
    }

    @Override
    protected void initView() {
        mImgTitlePic = (ImageView) findViewById(R.id.img_title_pic);
        mTxtTitleCenter = (TextView) findViewById(R.id.txt_title_center);
        mTxtTitleRight = (TextView) findViewById(R.id.txt_title_right);
        mWvWebHtml = (WebView) findViewById(R.id.wv_web_html);
        mTvWebTitle = (TextView) findViewById(R.id.tv_web_title);
        mTvWebContent = (TextView) findViewById(R.id.tv_web_content);
        mImgWebPic = (ImageView) findViewById(R.id.img_web_pic);

    }

    @Override
    protected boolean getStatus() {
        return false;
    }

    @Override
    protected void initData() {
        webViewShareEntity = new WebViewShareEntity();
        webViewShareEntity.setContent("这是只是一个测试的数据，用来测试模板消息内容");
        webViewShareEntity.setImgUrl("https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180202/20180202104925107797088.jpg");
        webViewShareEntity.setTitle("这是一个标题");

        mTvWebContent.setText(webViewShareEntity.getContent());
        mTvWebTitle.setText(webViewShareEntity.getTitle());
        Glide.with(this).load(webViewShareEntity.getImgUrl()).into(mImgWebPic);
        mImgTitlePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTxtTitleCenter.setText(webViewShareEntity.getTitle());
        mTxtTitleRight.setText("确定");
        mTxtTitleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent();
                mIntent.putExtra("webViewShareEntity", webViewShareEntity);
                // 设置结果，并进行传送
                setResult(RESULT_OK, mIntent);
                finish();
            }
        });

    }
}
