package com.yst.im.imchatlibrary.ui.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.base.BaseActivity;
import com.yst.im.imchatlibrary.base.baseview.AbstractTitleView;
import com.yst.im.imchatlibrary.bean.BaseEntity;
import com.yst.im.imchatlibrary.model.SetSexModel;
import com.yst.im.imchatlibrary.utils.ImToastUtil;

import static com.yst.im.imsdk.MessageConstant.MAN;
import static com.yst.im.imsdk.MessageConstant.NUM_0;

/**
 * 设置性别
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/4/11.
 */
public class SettingUserSexActivity extends BaseActivity implements View.OnClickListener, SetSexModel.SetSexListenerCallBack{

    private AbstractTitleView titleViewSetSex;
    private RelativeLayout relSettingSexMan;
    private RelativeLayout relSettingSexWoman;
    private ImageView imgNan;
    private ImageView imgNv;
    private SetSexModel setSexModel;

    /**
     *  男，  女
     */
    private String sex;

    @Override
    protected int getLayout() {
        return R.layout.activity_setting_sex;
    }

    @Override
    protected int getStatusColor() {
        return 0;
    }

    @Override
    protected void initView() {
        titleViewSetSex = (AbstractTitleView) findViewById(R.id.titleview_set_sex);
        relSettingSexMan = (RelativeLayout) findViewById(R.id.rel_setting_sex_man);
        relSettingSexWoman = (RelativeLayout) findViewById(R.id.rel_setting_sex_woman);
        imgNan = (ImageView) findViewById(R.id.img_nan);
        imgNv = (ImageView) findViewById(R.id.img_nv);

        titleViewSetSex.getLeftBackTextTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        titleViewSetSex.getRightTextTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSexModel.setSex(sex);
            }
        });
        relSettingSexMan.setOnClickListener(this);
        relSettingSexWoman.setOnClickListener(this);
    }

    @Override
    protected boolean getStatus() {
        return true;
    }

    @Override
    protected void initData() {
        sex = MyApp.manager.getSex();
        if (MAN.equals(sex)) {
            imgNan.setSelected(true);
            imgNv.setSelected(false);
        }else {
            imgNan.setSelected(false);
            imgNv.setSelected(true);
        }
        setSexModel = new SetSexModel(this);
        setSexModel.setSetSexListenerCallBack(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.rel_setting_sex_man) {
            imgNan.setSelected(true);
            imgNv.setSelected(false);
            sex = "男";
        }else if (view.getId() == R.id.rel_setting_sex_woman) {
            imgNan.setSelected(false);
            imgNv.setSelected(true);
            sex = "女";
        }
    }

    @Override
    public void onFindUser(BaseEntity baseEntity) {
        if (baseEntity.getCode() == NUM_0) {
            MyApp.manager.setSex(sex);
            finish();
        }
        ImToastUtil.showShortToast(this, baseEntity.getMsg());
    }
}
