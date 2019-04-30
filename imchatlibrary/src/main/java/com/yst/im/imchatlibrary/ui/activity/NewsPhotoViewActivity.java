package com.yst.im.imchatlibrary.ui.activity;

import android.view.View;

import com.bumptech.glide.Glide;
import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.base.BaseActivity;

import uk.co.senab.photoview.PhotoView;

/**
 * photoView 放大
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/4.
 */
public class NewsPhotoViewActivity extends BaseActivity {
    private PhotoView mPhvNewsPhoto;
    private String picUrl;
    @Override
    protected int getLayout() {
        return R.layout.activity_news_photo_view;
    }

    @Override
    protected int getStatusColor() {
        return 0;
    }

    @Override
    protected void initView() {
        mPhvNewsPhoto = (PhotoView) findViewById(R.id.phv_news_photo);
        picUrl=getIntent().getStringExtra("picUrl");
        mPhvNewsPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected boolean getStatus() {
        return false;
    }

    @Override
    protected void initData() {
        Glide.with(this).load(picUrl)
                .into(mPhvNewsPhoto);
    }
}
