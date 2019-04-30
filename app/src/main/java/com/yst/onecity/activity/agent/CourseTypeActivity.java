package com.yst.onecity.activity.agent;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yst.basic.framework.adapter.AbstractCommonAdapter;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.entity.holder.CommonViewHolder;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.onecity.R;
import com.yst.onecity.bean.agent.ClassifyBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;

/**
 * 课题分类页面
 *
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/5/17
 */
public class CourseTypeActivity extends BaseActivity {

    @BindView(R.id.lv_type)
    ListView mLvType;

    private List<ClassifyBean.ContentBean> mList = new ArrayList<>();
    private AbstractCommonAdapter<ClassifyBean.ContentBean> mAdapter;
    private int mClick;

    @Override
    public int getLayoutId() {
        return R.layout.activity_course_type;
    }

    @Override
    public void initData() {
        initTitleBar("课题分类");
        getClassify();
        initAdapter();
        if (getIntent().getExtras() != null) {
            mClick = getIntent().getExtras().getInt("isClick");
        }
    }

    /**
     * 获取课题分类
     */
    private void getClassify() {
        RequestApi.getClassify(new AbstractNetWorkCallback<ClassifyBean>() {
            @Override
            public void onSuccess(ClassifyBean bean) {
                if (bean.getCode() == NO1) {
                    if (bean.getContent().size() > NO0) {
                        mList.addAll(bean.getContent());
                        mList.get(mClick).setClick(true);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtils.show(bean.getMsg());
                    }
                } else {
                    ToastUtils.show(bean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        mAdapter = new AbstractCommonAdapter<ClassifyBean.ContentBean>(this, mList, R.layout.item_type) {
            @Override
            public void convert(CommonViewHolder holder, int position, ClassifyBean.ContentBean item) {
                holder.setText(R.id.txt_type_name, item.getDescriptionName());
                if (item.isClick()) {
                    holder.setTextColor(R.id.txt_type_name, ContextCompat.getColor(context, R.color.color_ED5452));
                    holder.setImgVisiable(R.id.iv_type_yes, View.VISIBLE);
                } else {
                    holder.setImgVisiable(R.id.iv_type_yes, View.GONE);
                }
            }
        };
        mLvType.setAdapter(mAdapter);

        mLvType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("typeName", mList.get(position).getDescriptionName());
                intent.putExtra("typeId", mList.get(position).getId());
                intent.putExtra("typePosition", position);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
