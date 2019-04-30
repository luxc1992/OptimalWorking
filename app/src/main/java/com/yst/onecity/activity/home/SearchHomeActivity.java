package com.yst.onecity.activity.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.entity.SearchHistory;
import com.yst.basic.framework.utils.KeyBoardUtils;
import com.yst.basic.framework.view.FlowLayout;
import com.yst.onecity.R;
import com.yst.onecity.adapter.CommomFragmentPagerAdapter;
import com.yst.onecity.bean.home.SearchEntity;
import com.yst.onecity.fragment.SearchServerProjectFragment;
import com.yst.onecity.fragment.share.SearchShareFragment;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.view.ContainsEmojiEditText;
import com.yst.onecity.view.viewpagerindicator.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;

/**
 * 服务搜索
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/5/18.
 */

public class SearchHomeActivity extends BaseActivity {

    @BindView(R.id.ll_search_back)
    LinearLayout llSearchBack;
    @BindView(R.id.img_home_search)
    ImageView imgHomeSearch;
    @BindView(R.id.tv_search_right)
    TextView tvSearchRight;
    @BindView(R.id.indicator_search)
    ViewPagerIndicator indicatorSearch;
    @BindView(R.id.viewpager_search)
    ViewPager viewpagerSearch;
    @BindView(R.id.flv_search_history)
    FlowLayout flvSearchHistory;
    @BindView(R.id.flv_search_hot)
    FlowLayout flvSearchHot;
    @BindView(R.id.edt_search)
    ContainsEmojiEditText edtSearch;
    @BindView(R.id.ll_search_history)
    LinearLayout llSearchHistory;
    @BindView(R.id.rel_search_result)
    RelativeLayout relSearchResult;
    @BindView(R.id.tv_clear_history)
    TextView tvClearHistory;

    private ArrayList<SearchHistory> hot = new ArrayList<>();
    private List<SearchHistory> searchHistoryList;
    private ArrayList<String> sortList = new ArrayList<>();
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private CommomFragmentPagerAdapter adapter;
    private String searchName = "";
    private SearchServerProjectFragment mSearchServerProjectFragment;
    private SearchShareFragment mSearchShareFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_home;
    }

    @Override
    public void initData() {
        getSearchHistory();
        getHotSeachKeyWord();
        edtSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        // 按下键盘的搜索
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchName = edtSearch.getText().toString().trim();
                    if (null == mSearchServerProjectFragment) {
                        setFragment();
                        llSearchHistory.setVisibility(View.GONE);
                        relSearchResult.setVisibility(View.VISIBLE);
                        viewpagerSearch.setVisibility(View.VISIBLE);
                    } else {
                        mSearchServerProjectFragment.requestServerProjectByKeyWords(NO1, searchName);
                        mSearchShareFragment.requestShareData(NO1, searchName);
                    }
                    App.getInstance().saveSearchHistory(searchName);
                }
                return false;
            }
        });
    }

    /**
     * 设置两个fragment
     */
    private void setFragment() {
        sortList.add("服务项目");
        sortList.add("分享");
        Bundle mBundle = new Bundle();
        mBundle.putString(SearchServerProjectFragment.searchName, searchName);
        mSearchServerProjectFragment = new SearchServerProjectFragment();
        mSearchServerProjectFragment.setArguments(mBundle);
        mSearchShareFragment = new SearchShareFragment();
        mSearchShareFragment.setArguments(mBundle);
        fragments.add(mSearchServerProjectFragment);
        fragments.add(mSearchShareFragment);
        adapter = new CommomFragmentPagerAdapter(getSupportFragmentManager(), fragments, sortList);
        viewpagerSearch.setAdapter(adapter);
        indicatorSearch.bindViewPager(viewpagerSearch, true);
    }

    /**
     * 获取历史搜索记录
     */
    private void getSearchHistory() {
        searchHistoryList = App.getInstance().getSearchHistory();
        if (searchHistoryList.size() == NO0) {
            flvSearchHistory.setVisibility(View.GONE);
        } else {
            flvSearchHistory.setVisibility(View.VISIBLE);
            flvSearchHistory.removeAllViews();
            for (int i = 0; i < searchHistoryList.size(); i++) {
                final String searchName = searchHistoryList.get(i).getKeyword();
                TextView tv = (TextView) View.inflate(this, R.layout.item_search_lable, null);
                tv.setText(searchName);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        edtSearch.setText(searchName);
                        edtSearch.setSelection(searchName.length());
                        llSearchHistory.setVisibility(View.GONE);
                        relSearchResult.setVisibility(View.VISIBLE);
                        viewpagerSearch.setVisibility(View.VISIBLE);
                        SearchHomeActivity.this.searchName = searchName;
                        setFragment();
                    }
                });
                flvSearchHistory.addView(tv);
            }
        }
    }

    /**
     * 设置热门关键字
     */
    private void setHotKeyWord() {
        flvSearchHot.removeAllViews();
        for (int i = 0; i < hot.size(); i++) {
            TextView tv = (TextView) View.inflate(this, R.layout.item_search_lable, null);
            final String searchName = hot.get(i).getName();
            tv.setText(searchName);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edtSearch.setText(searchName);
                    edtSearch.setSelection(searchName.length());
                    llSearchHistory.setVisibility(View.GONE);
                    relSearchResult.setVisibility(View.VISIBLE);
                    viewpagerSearch.setVisibility(View.VISIBLE);
                    SearchHomeActivity.this.searchName = searchName;
                    setFragment();
                }
            });
            flvSearchHot.addView(tv);
        }
    }

    /**
     * 热们搜索
     */
    private void getHotSeachKeyWord() {
        RequestApi.getHotSeachKeyWord(new AbstractNetWorkCallback<SearchEntity>() {
            @Override
            public void onSuccess(SearchEntity mSearchEntity) {
                if (mSearchEntity.getData() != null) {
                    if (mSearchEntity.getData().size() != NO0 && mSearchEntity.getCode() == NO1) {
                        hot.addAll(mSearchEntity.getData());
                    }
                    setHotKeyWord();
                }
            }

            @Override
            public void onError(String errorMsg) {

            }

            @Override
            public void onBefore() {
                super.onBefore();
            }

            @Override
            public void onAfter() {
                super.onAfter();
            }
        });
    }


    @OnClick({R.id.ll_search_back, R.id.tv_search_right, R.id.tv_clear_history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_search_back:
            case R.id.tv_search_right:
                finish();
                break;
            case R.id.tv_clear_history:
                App.getInstance().clearSearchHistory();
                getSearchHistory();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KeyBoardUtils.closeKeybord(edtSearch, this);
    }
}
