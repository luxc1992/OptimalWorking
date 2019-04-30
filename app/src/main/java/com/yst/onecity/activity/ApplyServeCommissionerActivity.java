package com.yst.onecity.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.onecity.R;
import com.yst.onecity.activity.mine.ServerCenterActivity;
import com.yst.onecity.bean.HunterNumBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;

import butterknife.BindView;

import static com.yst.onecity.Constant.NO2;


/**
 * 申请服务专员
 *
 * @author liumanqing
 * @version 1.0.1
 * @date 2018/2/24
 */
public class ApplyServeCommissionerActivity extends BaseActivity {
    @BindView(R.id.tv_serve_num)
    TextView tvNum;
    @BindView(R.id.btn_apprentice)
    Button btnApprentice;
    @BindView(R.id.tv_serve_already)
    TextView tvAlready;

    @Override
    public int getLayoutId() {
        return R.layout.activity_apply_serve_commissioner;
    }

    @Override
    public void initData() {
        initTitleBar(getString(R.string.apply_task));
        getServerData();
        setTitleBold();

    }

    /**
     * 徒弟数量
     *
     * @version 1.0.1
     */
    private void getServerData() {
        RequestApi.getHunterNum(App.manager.getPhoneNum(), App.manager.getUuid(), new AbstractNetWorkCallback<HunterNumBean>() {
            @Override
            public void onSuccess(HunterNumBean hunterNumBean) {
                if (hunterNumBean.getContent() != null && hunterNumBean.getCode() == 1) {
                    if (hunterNumBean.getContent().getGroupId() != null) {
                        App.manager.setGroupId(hunterNumBean.getContent().getGroupId());
                    }
                    int content = hunterNumBean.getContent().getCount();
                    if (content < NO2) {
                        tvNum.setText(content + "/2");
                        btnApprentice.setText(getString(R.string.now_apprentice));
                        tvAlready.setVisibility(View.INVISIBLE);
                        btnApprentice.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                JumpIntent.jump(ApplyServeCommissionerActivity.this, RecruitActivity.class);
                            }
                        });
                    } else {
                        tvNum.setText(content + "/2");
                        btnApprentice.setText(getString(R.string.in_task));
                        btnApprentice.setBackgroundResource(R.drawable.shape_btn_bg);
                        tvAlready.setVisibility(View.VISIBLE);
                        btnApprentice.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                JumpIntent.jump(ApplyServeCommissionerActivity.this, ServerCenterActivity.class);
                            }
                        });
                    }
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });

    }
}
