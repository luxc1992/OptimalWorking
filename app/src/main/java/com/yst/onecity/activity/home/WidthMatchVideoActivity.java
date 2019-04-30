package com.yst.onecity.activity.home;

import android.content.res.Configuration;
import android.view.View;
import android.widget.LinearLayout;

import com.yst.basic.framework.base.BaseActivity;
import com.yst.onecity.R;
import com.yst.onecity.view.CommonVideoView;

import butterknife.BindView;


/**
 * 首页-播放视频
 *
 * @author songbinbin
 * @version 1.0.1
 * @date 2018/2/25
 */
public class WidthMatchVideoActivity extends BaseActivity {

    @BindView(R.id.linearlayout)
    LinearLayout linearLayout;

    private String uri;
    private CommonVideoView videoView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_widthmatchvideo;
    }

    @Override
    public void initData() {
        String videoPath = getIntent().getStringExtra("videoPath");
        int videoid = getIntent().getIntExtra("videoid", 0);

        if (videoid == 0) {
            uri = videoPath;
        }

        videoView = findViewById(R.id.common_videoView);
        videoView.start(uri);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            videoView.setFullScreen();
        } else {
            videoView.setNormalScreen();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
