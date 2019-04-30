package com.yst.onecity.activity.agent;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.R;
import com.yst.onecity.adapter.ShareViewPagerAdapter;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.PublishClassifyBean;
import com.yst.onecity.bean.agent.ByIdBean;
import com.yst.onecity.fragment.AddShareFragment;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.view.ContainsEmojiEditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yst.onecity.Constant.GONE;
import static com.yst.onecity.Constant.LIST;
import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO6;

/**
 * 添加分享页面
 *
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/5/17
 */
public class AddShareActivity extends BaseActivity implements TextWatcher {

    @BindView(R.id.tv_right)
    TextView mTvRight;
    @BindView(R.id.et_share_search)
    ContainsEmojiEditText mEtShareSearch;
    @BindView(R.id.tab_share)
    XTabLayout mTabShare;
    @BindView(R.id.vp_share)
    ViewPager mVpShare;

    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> mStrings = new ArrayList<>();
    private List<ByIdBean.ContentBean> list = new ArrayList<>();
    private boolean isEdit;
    private int count = 0;
    private List<PublishClassifyBean.ContentBean> mTabList = new ArrayList<>();
    private ShareViewPagerAdapter viewPagerAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_share;
    }

    @Override
    public void initData() {
        EventBus.getDefault().register(this);
        initTitleBar("添加分享");
        mTvRight.setText("完成");
        mTvRight.setTextSize(13);
        mTvRight.setTextColor(ContextCompat.getColor(this, R.color.color_ED5452));
        mTvRight.setVisibility(View.VISIBLE);
        mTabShare.setBackgroundColor(Color.WHITE);
        mVpShare.setOffscreenPageLimit(2);
        getPublishClassify();
        viewPagerAdapter = new ShareViewPagerAdapter(getSupportFragmentManager(), mFragments, mStrings);
        mEtShareSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        mEtShareSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    mVpShare.setCurrentItem(NO0);
                    EventBus.getDefault().post(new EventBean(mEtShareSearch.getText().toString().trim(), NO1));
                }
                return false;
            }
        });
    }

    /**
     * 获取添加分享导航栏
     */
    private void getPublishClassify() {
        RequestApi.getPublishClassify(new AbstractNetWorkCallback<PublishClassifyBean>() {
            @Override
            public void onSuccess(PublishClassifyBean bean) {
                if (bean.getCode() == NO1) {
                    if (bean.getContent().size() > NO0) {
                        mTabList.clear();
                        mTabList.addAll(bean.getContent());
                        PublishClassifyBean.ContentBean contentBean = new PublishClassifyBean.ContentBean();
                        contentBean.setDescription_name("全部");
                        contentBean.setId(NO0);
                        mTabList.add(NO0, contentBean);
                        for (PublishClassifyBean.ContentBean forBean : mTabList) {
                            mStrings.add(forBean.getDescription_name());
                            mFragments.add(AddShareFragment.newInstance(String.valueOf(forBean.getId())));
                        }
                        viewPagerAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtils.show(bean.getMsg());
                    }
                    setData();
                } else {
                    ToastUtils.show(bean.getMsg());
                    mVpShare.setVisibility(View.GONE);
                    mTabShare.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(String errorMsg) {
                mVpShare.setVisibility(View.GONE);
                mTabShare.setVisibility(View.GONE);
                ToastUtils.show(errorMsg);
            }
        });
    }

    /**
     * 设置导航栏适配器
     */
    private void setData() {
        viewPagerAdapter.setData(mFragments);
        mVpShare.setOffscreenPageLimit(NO6);
        mVpShare.setAdapter(viewPagerAdapter);
        mTabShare.setupWithViewPager(mVpShare);
    }

    @OnClick(R.id.tv_right)
    public void onViewClicked() {
        if (!Utils.isClickable()) {
            return;
        }
            isEdit = false;
            EventBus.getDefault().post(new EventBean(GONE, isEdit));
    }

    /**
     * 点击完成后fragment发到activity的通知结果
     *
     * @param event 回调标识以及数据
     */
    @Subscribe
    public void onEventMainThread(EventBean event) {
        if (LIST.equals(event.getMsg())) {
            count++;
            List<ByIdBean.ContentBean> eventList = event.getList();
            list.addAll(eventList);
            MyLog.e("tag", "count===" + count);
            MyLog.e("tag", "list222222===" + list.size());
            MyLog.e("tag", "mFragments===" + mFragments.size());

            if (count == mFragments.size()) {
                Intent intent = new Intent();
                intent.setClass(AddShareActivity.this, PublishCourseActivity.class);
                intent.putExtra("listToPublish", (Serializable) list);
                setResult(RESULT_OK, intent);
                AddShareActivity.this.finish();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
