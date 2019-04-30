package com.yst.onecity.activity.mine.setting;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.R;

import butterknife.BindView;

/**
 * 关于我们
 *
 * @author LianJinXue
 * @version 1.0.1
 * @date 2018/2/23
 */

public class MyActivity extends BaseActivity {

    @BindView(R.id.txt_telphone)
    TextView tvTel;
    @BindView(R.id.txt_my_txxtview)
    TextView tvVerCode;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my;
    }

    @Override
    public void initData() {
        initTitleBar(getString(R.string.about_us));
        getData();
        tvTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call();
            }
        });

    }

    /**
     * 调用拨号界面
     * Intent.ACTION_CALL
     */
    private void call() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + getString(R.string.about_us_tel)));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * 获取版本号
     */
    private void getData() {
        String str = "版本号:"+ Utils.getVerCode(this);
        tvVerCode.setText(str);
    }

}
