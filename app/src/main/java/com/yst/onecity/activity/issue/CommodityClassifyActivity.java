package com.yst.onecity.activity.issue;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.adapter.issue.CommodityStairClassifyAdapter;
import com.yst.onecity.adapter.issue.CommodityTwoClassifyAdapter;
import com.yst.onecity.bean.issue.CommodityStairClassifyBean;
import com.yst.onecity.bean.issue.CommodityTwoClassifyBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * 商品的分类页面
 *
 * @author zhaiyanwu
 * @version 1.0.1
 * @date 2018/3/14.
 */
public class CommodityClassifyActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.lv_classify)
    ListView lvClassify;
    @BindView(R.id.lv_child_classify)
    ListView lvChildClassify;

    private CommodityStairClassifyAdapter groupListAdapter;
    private ArrayList<CommodityStairClassifyBean.ContentBean> lvClassifyList = new ArrayList<>();
    private ArrayList<CommodityTwoClassifyBean.ContentBean> lvClassifyChildList = new ArrayList<>();
    private int position = 0;
    private CommodityTwoClassifyAdapter twoClassifyAdapter;
    private String classifyName;

    @Override
    public int getLayoutId() {
        return R.layout.activity_commodity_classify;
    }

    @Override
    public void initData() {
        initTitleBar("添加商品分类");
        initList();
        lvClassify.setOnItemClickListener(this);
        lvChildClassify.setOnItemClickListener(this);
    }

    /**
     * 获取一级分类数据
     */
    private void initList() {
        RequestApi.getOneList(new AbstractNetWorkCallback<CommodityStairClassifyBean>() {
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

            @Override
            public void onSuccess(CommodityStairClassifyBean commodityStairClassifyBean) {
                if (Constant.NO1 == commodityStairClassifyBean.getCode()) {
                    List<CommodityStairClassifyBean.ContentBean> content = commodityStairClassifyBean.getContent();
                    setStairClassifyData(content);
                } else {
                    ToastUtils.show(commodityStairClassifyBean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    /**
     * 设置一级分类数据
     *
     * @param content 数据
     */
    private void setStairClassifyData(List<CommodityStairClassifyBean.ContentBean> content) {
        if (content == null) {
            return;
        }
        lvClassifyList.addAll(content);
        groupListAdapter = new CommodityStairClassifyAdapter(lvClassifyList, this);
        lvClassify.setAdapter(groupListAdapter);
        for (int i = 0; i < lvClassifyList.size(); i++) {
            classifyName = lvClassifyList.get(0).getClassifyName();
        }
        //设置二级分类
        initTwoClassifyData();
    }

    /**
     * 设置二级分类数据
     */
    private void initTwoClassifyData() {
        twoClassifyAdapter = new CommodityTwoClassifyAdapter(lvClassifyChildList, this);
        lvChildClassify.setAdapter(twoClassifyAdapter);
        if (lvClassifyList!=null&&lvClassifyList.size() > 0) {
            int id = lvClassifyList.get(0).getId();
            String s = String.valueOf(id);
            //请求二级分类列表数据
            RequestApi.getTwoList(s, new AbstractNetWorkCallback<CommodityTwoClassifyBean>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    dismissInfoProgressDialog();
                }

                @Override
                public void onBefore() {
                    super.onBefore();
                    showInfoProgressDialog();
                }

                @Override
                public void onSuccess(CommodityTwoClassifyBean commodityTwoClassifyBean) {
                    if (Constant.NO1 == commodityTwoClassifyBean.getCode()) {
                        setTwoClassifyData(commodityTwoClassifyBean);
                    } else {
                        ToastUtils.show(commodityTwoClassifyBean.getMsg());
                    }
                }
                @Override
                public void onError(String errorMsg) {
                    ToastUtils.show(errorMsg);
                }
            });
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        int id = adapterView.getId();
        switch (id) {
            //一级分类
            case R.id.lv_classify:
                groupListAdapter.setDefSelect(i);
                if (position != i) {
                    lvClassifyChildList.clear();
                }
                position = i;
                classifyName = lvClassifyList.get(i).getClassifyName();
                String s = String.valueOf(lvClassifyList.get(i).getId());
                //请求二级分类列表数据
                RequestApi.getTwoList(s, new AbstractNetWorkCallback<CommodityTwoClassifyBean>() {
                    @Override
                    public void onAfter() {
                        super.onAfter();
                        dismissInfoProgressDialog();
                    }

                    @Override
                    public void onBefore() {
                        super.onBefore();
                        showInfoProgressDialog();
                    }

                    @Override
                    public void onSuccess(CommodityTwoClassifyBean commodityTwoClassifyBean) {
                        if (Constant.NO1 == commodityTwoClassifyBean.getCode()) {
                            setTwoClassifyData(commodityTwoClassifyBean);
                        } else {
                            ToastUtils.show(commodityTwoClassifyBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(String errorMsg) {
                        ToastUtils.show(errorMsg);
                    }
                });
                break;
            //二级分类
            case R.id.lv_child_classify:
                twoClassifyAdapter.setDefSelect(i);
                CommodityTwoClassifyBean.ContentBean contentBean = lvClassifyChildList.get(i);
                Bundle bundle = new Bundle();
                //数据回传发哦上一个页面
                bundle.putSerializable("data", contentBean);
                bundle.putString("classifyName", classifyName);
                Intent intent = new Intent();
                intent.putExtra("key", bundle);
                setResult(Constant.NO97, intent);
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 设置二级分类列表数据
     *
     * @param commodityTwoClassifyBean 数据
     */
    private void setTwoClassifyData(CommodityTwoClassifyBean commodityTwoClassifyBean) {
        lvClassifyChildList.clear();
        List<CommodityTwoClassifyBean.ContentBean> content = commodityTwoClassifyBean.getContent();
        lvClassifyChildList.addAll(content);
        twoClassifyAdapter.notifyDataSetChanged();
    }
}