package com.yst.onecity.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.MyLog;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.utils.security.PreferenceUtil;
import com.yst.onecity.Const;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.adapter.SaleClassAdapter;
import com.yst.onecity.bean.SaleClassBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import gorden.rxbus2.RxBus;
import gorden.rxbus2.Subscribe;
import gorden.rxbus2.ThreadMode;
import okhttp3.Call;
import okhttp3.Request;

/**
 * 经营分类页面
 *
 * @author wuxiaofang
 * @version 1.1.0
 * @date 2018/5/26
 */

public class SaleClassActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.lv_sale_class)
    ListView lvSaleClass;
    @BindView(R.id.tv_choice)
    TextView tvChoice;
    @BindView(R.id.iv_empty)
    ImageView ivEmpty;
    List<SaleClassBean.ContentBean> classList = new ArrayList<>();
    private SaleClassAdapter adapter;
    private int position;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sale_class;
    }

    @Override
    public void initData() {
        initTitleBar("经营分类");
        RxBus.get().register(this);
        boolean check = PreferenceUtil.getBoolean("check", false);
        if (check) {
            position = PreferenceUtil.getInt("position", 0);
        }
        adapter = new SaleClassAdapter(this);
        lvSaleClass.setAdapter(adapter);
        adapter.setState(position);
        getList();
    }

    /**
     * 获取列表
     */
    private void getList() {
        OkHttpUtils.post().url(Const.GET_SERVICE_CLASS)
                .build().execute(new StringCallback() {

            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                showInfoProgressDialog();
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                dismissInfoProgressDialog();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                MyLog.e("sss", "经营分类： " + response);
                if (null != response) {
                    Gson gson = new Gson();
                    SaleClassBean bean = gson.fromJson(response, SaleClassBean.class);
                    if (null != bean && bean.getCode() == Constant.NO1 && null != bean.getContent()) {
                        classList.addAll(bean.getContent());
                        adapter.addData(classList);
                        if (classList.isEmpty()) {
                            lvSaleClass.setVisibility(View.GONE);
                            ivEmpty.setVisibility(View.VISIBLE);
                        } else {
                            lvSaleClass.setVisibility(View.VISIBLE);
                            ivEmpty.setVisibility(View.GONE);
                        }
                    }
                } else {
                    lvSaleClass.setVisibility(View.GONE);
                    ivEmpty.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Subscribe(code = Constant.NO9, threadMode = ThreadMode.MAIN)
    public void diss(SaleClassBean.ContentBean bean) {
        Intent intent = new Intent();
        intent.putExtra("saleId",String.valueOf(bean.getId()));
        intent.putExtra("sortName",bean.getName());
        MyLog.e("sss","-----id: "+bean.getId());
        setResult(Constant.NO0,intent);
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unRegister(this);
    }
}
