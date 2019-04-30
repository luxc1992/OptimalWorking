package com.yst.onecity.activity;

import android.view.View;
import android.widget.Button;

import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.onecity.R;
import com.yst.onecity.activity.mine.MyZxingActivity;

import butterknife.BindView;

/**
 * 申请猎头
 *
 * @author liumanqing
 * @version 1.0.1
 * @date 2018/2/24
 */
public class ApplyHunterActivity extends BaseActivity {
    @BindView(R.id.btn_send)
    Button btnSend;

    @Override
    public int getLayoutId() {
        return R.layout.activity_apply_hunter;
    }

    @Override
    public void initData() {
        initTitleBar(getString(R.string.apply_service_team));
        //设置标题字体加粗
        setTitleBold();
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JumpIntent.jump(ApplyHunterActivity.this, MyZxingActivity.class);
            }
        });
    }

}
