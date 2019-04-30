package com.yst.im.imchatlibrary.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.adapter.SearchResultAdapter;
import com.yst.im.imchatlibrary.base.BaseActivity;
import com.yst.im.imchatlibrary.base.baseview.AbstractSortEnumType;
import com.yst.im.imchatlibrary.bean.BaseEntity;
import com.yst.im.imchatlibrary.bean.FriendListEntity;
import com.yst.im.imchatlibrary.bean.GroupChatEntity;
import com.yst.im.imchatlibrary.bean.IntentChatEntity;
import com.yst.im.imchatlibrary.bean.IntentGroupEntity;
import com.yst.im.imchatlibrary.bean.MyCreatGroupEntity;
import com.yst.im.imchatlibrary.bean.SearchEntity;
import com.yst.im.imchatlibrary.bean.SearchResultEntity;
import com.yst.im.imchatlibrary.bean.UserIntentChatEntity;
import com.yst.im.imchatlibrary.enumclass.IntentGroupDetail;
import com.yst.im.imchatlibrary.model.DimSearchModel;
import com.yst.im.imchatlibrary.model.JoinGroupChatModel;
import com.yst.im.imchatlibrary.model.QueryGroupChatModel;
import com.yst.im.imchatlibrary.ui.listener.EditChangedListener;
import com.yst.im.imchatlibrary.utils.BaseUtils;
import com.yst.im.imchatlibrary.utils.ImLog;
import com.yst.im.imchatlibrary.utils.SettingNineGroupIcon;
import com.yst.im.imchatlibrary.widget.ClearEditText;
import com.yst.im.imchatlibrary.widget.NineGridImageView;
import com.yst.im.imsdk.ChatType;
import com.yst.im.imsdk.bean.MessageBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import gorden.rxbus2.Subscribe;
import gorden.rxbus2.ThreadMode;

import static com.yst.im.imchatlibrary.utils.DefendMpUtils.LEFT_SQUARE_BRACKET;
import static com.yst.im.imchatlibrary.utils.DefendMpUtils.SPACE;
import static com.yst.im.imsdk.MessageConstant.NUM_0;
import static com.yst.im.imsdk.MessageConstant.NUM_1;
import static com.yst.im.imsdk.MessageConstant.NUM_2;
import static com.yst.im.imsdk.MessageConstant.NUM_3;
import static com.yst.im.imsdk.MessageConstant.NUM_65;
import static com.yst.im.imsdk.MessageConstant.TYPE_DELETE_USER;
import static com.yst.im.imsdk.MessageConstant.TYPE_OUTLOGIN;

/**
 * 搜索页  A
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/03/28
 */
public class ImSearchActivity extends BaseActivity implements View.OnClickListener, DimSearchModel.DimSearchListenerCallBack,
        QueryGroupChatModel.QueryGroupChatListenerCallBack {
    private ClearEditText mCedtSearchInput;
    private ListView mLvSearchNearestList;
    private RelativeLayout mRelSearchMoreNearest;
    private ListView mLvSearchGroupList;
    private LinearLayout mLilSearchResult;
    private RelativeLayout mRelSearchMoreGroup;
    private RelativeLayout mRelSearchNothing;
    private TextView mTxtSearchNearest;
    private RelativeLayout mRelSearchNearestLayout;
    private TextView mTxtSearchNearestNum;
    private TextView mTxtSearchCancle;
    private LinearLayout mLilSearchFriendResult;
    private LinearLayout mLilSearchGroupResult;
    private TextView mTxtSearchGroup;
    private SearchResultAdapter mNearestAdapter;
    private SearchResultAdapter mGroupAdapter;
    private List<SearchResultEntity> mGroupList;
    private List<SearchResultEntity> mNearestList;
    private JumpIntentFrom mJumpIntentFrom;
    private DimSearchModel mDimSearchModel;
    private String searchName = "";
    private List<SearchResultEntity> applyEntityList = new ArrayList<>();
    CommonAdapter<SearchResultEntity> commonAdapter;
    private QueryGroupChatModel mQueryGroupChatModel;

    public enum JumpIntentFrom {

        /**
         * 添加好友/群
         */
        Add,
        /**
         * 其他
         */
        other
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_im_search;
    }

    @Override
    protected int getStatusColor() {
        return 0;
    }

    @Override
    protected boolean getStatus() {
        return false;
    }

    public static void getJumpFrom(Context context, JumpIntentFrom mJumpIntentFrom) {
        Intent intent = new Intent(context, ImSearchActivity.class);
        intent.putExtra("jumpIntentFrom", mJumpIntentFrom);
        context.startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.rel_search_more_nearest) {
            ImLog.e("ImLog", "mNearestList = " + mNearestList.size());
            ImSearchResultActivity.getJumpChatSource(ImSearchActivity.this, ChatType.C2C, mNearestList, searchName);
        } else if (i == R.id.rel_search_more_group) {
            ImLog.e("ImLog", "mGroupList = " + mGroupList.size());
            ImSearchResultActivity.getJumpChatSource(ImSearchActivity.this, ChatType.GROUP, mGroupList, searchName);
        } else if (i == R.id.txt_search_cancle) {
            finish();
        }
    }

    @Override
    protected void initView() {
        MyApp.addActivity(this);
        BaseUtils.setStatusTextColor(false, ImSearchActivity.this);
        mCedtSearchInput = (ClearEditText) findViewById(R.id.cedt_search_input);
        mTxtSearchCancle = (TextView) findViewById(R.id.txt_search_cancle);
        mLvSearchNearestList = (ListView) findViewById(R.id.lv_search_nearest_list);
        mRelSearchMoreNearest = (RelativeLayout) findViewById(R.id.rel_search_more_nearest);
        mLvSearchGroupList = (ListView) findViewById(R.id.lv_search_group_list);
        mRelSearchMoreGroup = (RelativeLayout) findViewById(R.id.rel_search_more_group);
        mRelSearchNothing = (RelativeLayout) findViewById(R.id.rel_search_nothing);
        mLilSearchResult = (LinearLayout) findViewById(R.id.lil_search_result);
        mLilSearchGroupResult = (LinearLayout) findViewById(R.id.lil_search_group_result);
        mLilSearchFriendResult = (LinearLayout) findViewById(R.id.lil_search_friend_result);
        mTxtSearchNearest = (TextView) findViewById(R.id.txt_search_nearest);
        mTxtSearchGroup = (TextView) findViewById(R.id.txt_search_group);
        mRelSearchNearestLayout = (RelativeLayout) findViewById(R.id.rel_search_nearest_layout);
        mTxtSearchNearestNum = (TextView) findViewById(R.id.txt_search_nearest_num);
        mRelSearchMoreNearest.setOnClickListener(this);
        mRelSearchMoreGroup.setOnClickListener(this);
        mTxtSearchCancle.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mJumpIntentFrom = (JumpIntentFrom) getIntent().getSerializableExtra("jumpIntentFrom");
        mDimSearchModel = new DimSearchModel(this);
        mDimSearchModel.setDimSearchListenerCallBack(this);
        mQueryGroupChatModel = new QueryGroupChatModel(this);
        mQueryGroupChatModel.setQueryGroupChatListenerCallBack(this);
        mLilSearchResult.setVisibility(View.GONE);
        mRelSearchNothing.setVisibility(View.VISIBLE);
        mGroupList = new ArrayList<>();
        mNearestList = new ArrayList<>();
        /**按下键盘的搜索*/
        mCedtSearchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String edit = v.getText().toString();
                if ("".equals(edit = edit.replaceAll(SPACE, ""))) {
                    return false;
                }
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_NONE
                        || actionId == EditorInfo.IME_ACTION_GO
                        || actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    searchName = mCedtSearchInput.getText().toString().trim();
                    if (mJumpIntentFrom == JumpIntentFrom.Add) {
                        mQueryGroupChatModel.getQueryGroupChat(searchName);
                        mTxtSearchNearest.setText("搜索结果");
                        mRelSearchMoreGroup.setVisibility(View.GONE);
                        mRelSearchNearestLayout.setBackgroundColor(ContextCompat.getColor(ImSearchActivity.this, R.color.colorBlackEB));
                        mTxtSearchNearestNum.setVisibility(View.VISIBLE);
                        mRelSearchMoreNearest.setVisibility(View.GONE);
                        mLilSearchGroupResult.setVisibility(View.GONE);
                    } else {
                        mRelSearchNothing.setVisibility(View.GONE);
                        mLilSearchResult.setVisibility(View.VISIBLE);
                        mRelSearchMoreGroup.setVisibility(View.VISIBLE);
                        mRelSearchMoreNearest.setVisibility(View.VISIBLE);
                        mDimSearchModel.getDimSearch(searchName, "");
                        mTxtSearchNearestNum.setVisibility(View.GONE);
                    }
                }
                return false;
            }
        });
        if (mJumpIntentFrom == JumpIntentFrom.Add) {
            searchName = AddActivity.searchName;
            mCedtSearchInput.setText(searchName);
            mTxtSearchNearest.setText("搜索结果");
            mCedtSearchInput.setSelection(searchName.length());
            mRelSearchMoreNearest.setVisibility(View.GONE);
            mRelSearchMoreGroup.setVisibility(View.GONE);
            mLilSearchGroupResult.setVisibility(View.GONE);
            mQueryGroupChatModel.getQueryGroupChat(searchName);

        } else {
            mNearestAdapter = new SearchResultAdapter(this, mNearestList, ChatType.C2C);
            mLvSearchNearestList.setAdapter(mNearestAdapter);
            mGroupAdapter = new SearchResultAdapter(this, mGroupList, ChatType.GROUP);
            mLvSearchGroupList.setAdapter(mGroupAdapter);
        }

        mLvSearchNearestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mJumpIntentFrom != JumpIntentFrom.Add) {
                    IntentChatEntity intentChatEntity = new IntentChatEntity();
                    if ((null != mNearestList.get(position).getRemark())) {
                        intentChatEntity.setAcceptName(mNearestList.get(position).getRemark());
                    } else {
                        intentChatEntity.setAcceptName(mNearestList.get(position).getNickName());
                    }
                    intentChatEntity.setAcceptId(mNearestList.get(position).getUserId());
                    intentChatEntity.setChatType(ChatType.C2C);
                    ChatScreenActivity.getJumpChatSource(ImSearchActivity.this, intentChatEntity);
                }
            }
        });

        mLvSearchGroupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mJumpIntentFrom != JumpIntentFrom.Add) {
                    IntentChatEntity intentChatEntity = new IntentChatEntity();
                    intentChatEntity.setAcceptName(mGroupList.get(position).getGroupName());
                    intentChatEntity.setAcceptId(mGroupList.get(position).getId());
                    intentChatEntity.setChatType(ChatType.GROUP);
                    intentChatEntity.setGroupNum(mGroupList.get(position).getGroupNumberByCurrent());
                    ChatScreenActivity.getJumpChatSource(ImSearchActivity.this, intentChatEntity);
                }
            }
        });
    }

    /**
     * 获取适配器
     */
    private CommonAdapter getGroupListAdapter() {
        commonAdapter = new CommonAdapter<SearchResultEntity>(
                ImSearchActivity.this, R.layout.item_delete_groups, applyEntityList) {
            @Override
            protected void convert(ViewHolder viewHolder, final SearchResultEntity item, int position) {
                TextView applyGroup = viewHolder.getView(R.id.txt_delete_groups_group_apply);
                TextView view = viewHolder.getView(R.id.txt_delete_groups_user_name);
                String nickName = item.getGroupName();
                String content = nickName;
                if (searchName.length() > 0 && nickName.contains(searchName)) {
                    if (nickName.indexOf(searchName) == 0 && searchName.length() == nickName.length()) {
                        content = nickName;
                        content = "<font color=\"#FF943E\">" + content + "</font>";
                    } else if (nickName.indexOf(searchName) == 0) {
                        String substring = nickName.substring(0, searchName.length());
                        String substring1 = nickName.substring(searchName.length(), nickName.length());
                        content = "<font color=\"#FF943E\">" + substring + "</font>" + substring1;
                    } else if (nickName.indexOf(searchName) + searchName.length() == nickName.length()) {
                        String substring = nickName.substring(0, nickName.indexOf(searchName));
                        String substring1 = nickName.substring(nickName.indexOf(searchName), nickName.length());
                        content = substring + "<font color=\"#FF943E\">" + substring1 + "</font>";
                    } else {
                        String substring = nickName.substring(0, nickName.indexOf(searchName));
                        String substring1 = nickName.substring(nickName.indexOf(searchName), nickName.indexOf(searchName) + searchName.length());
                        String substring2 = nickName.substring(nickName.indexOf(searchName) + searchName.length(), nickName.length());
                        content = substring + "<font color=\"#FF943E\">" + substring1 + "</font>" + substring2;
                    }
                }
                if (!TextUtils.isEmpty(item.getImageUrl()) && item.getImageUrl().contains(LEFT_SQUARE_BRACKET)) {
                    JSONArray objects = JSONObject.parseArray(item.getImageUrl());
                    List<String> list = new ArrayList<>();
                    for (int i = 0; i < objects.size(); i++) {
                        list.add(objects.get(i).toString());
                    }
                    NineGridImageView imgIcon = viewHolder.getView(R.id.img_delete_groups_user_icon);
                    SettingNineGroupIcon.setGroupIcon(imgIcon, list);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    view.setText(Html.fromHtml(content, Html.FROM_HTML_MODE_COMPACT));
                } else {
                    view.setText(Html.fromHtml(content));
                }
                applyGroup.setVisibility(View.VISIBLE);
                if (String.valueOf(NUM_2).equals(item.getState())) {
                    applyGroup.setText("已申请");
                    applyGroup.setBackgroundResource(R.drawable.btn_shape_width_gray);
                    applyGroup.setEnabled(false);
                } else if (String.valueOf(NUM_0).equals(item.getState())) {
                    applyGroup.setText("发消息");
                    applyGroup.setBackgroundResource(R.drawable.btn_shape_width_blue);
                    applyGroup.setEnabled(true);
                } else {
                    applyGroup.setText("加入");
                    applyGroup.setBackgroundResource(R.drawable.btn_shape_width_blue);
                    applyGroup.setEnabled(true);
                }
                viewHolder.setOnClickListener(R.id.txt_delete_groups_group_apply, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (String.valueOf(NUM_0).equals(item.getState())) {
                            IntentChatEntity intentChatEntity = new IntentChatEntity();
                            intentChatEntity.setAcceptName(item.getGroupName());
                            intentChatEntity.setAcceptId(String.valueOf(item.getId()));
                            intentChatEntity.setChatType(ChatType.GROUP);
                            intentChatEntity.setGroupNum(item.getGroupNumberByCurrent());
                            ChatScreenActivity.getJumpChatSource(ImSearchActivity.this, intentChatEntity);
                        } else {
                            IntentGroupEntity intentGroupEntity = new IntentGroupEntity();
                            intentGroupEntity.setGroupId(String.valueOf(item.getId()));
                            intentGroupEntity.setIntentGroupDetail(IntentGroupDetail.OutGroup);
                            GroupDetailsActivity.getJumpGroup(ImSearchActivity.this, intentGroupEntity);
                        }
                    }
                });
            }
        };
        return commonAdapter;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mJumpIntentFrom == JumpIntentFrom.Add) {
            mQueryGroupChatModel.getQueryGroupChat(searchName);
        }
    }

    @Override
    public void onDimSearch(SearchEntity searchEntity) {
        if (null == searchEntity || searchEntity.getCode() == NUM_1) {
            mLilSearchResult.setVisibility(View.GONE);
            mRelSearchNothing.setVisibility(View.VISIBLE);
            return;
        }
        mNearestList.clear();
        mGroupList.clear();
        SearchEntity.ContentBean contentBeanList = searchEntity.getContent();
        if (null == contentBeanList.getGroupChat() || contentBeanList.getGroupChat().size() == 0) {
            mLilSearchGroupResult.setVisibility(View.GONE);
        } else {
            for (int i = 0; i < contentBeanList.getGroupChat().size(); i++) {
                contentBeanList.getGroupChat().get(i).setGroup(true);
            }
            mGroupList.addAll(contentBeanList.getGroupChat());
            List<SearchResultEntity> mSearchResultEntity = new ArrayList<>();
            if (contentBeanList.getGroupChat().size() > NUM_3) {
                for (int i = 0; i < NUM_3; i++) {
                    mSearchResultEntity.add(mGroupList.get(i));
                }
            } else {
                mSearchResultEntity.addAll(mGroupList);
            }
            ImLog.e("ImLog", "mGroupList = " + mGroupList.size());
            if (mGroupAdapter != null) {
                mGroupAdapter.setSearchName(searchName, mSearchResultEntity);
            }
            ImLog.e("ImLog", "mGroupList = " + mGroupList.size());
        }
        if (null == contentBeanList.getUserChat() || contentBeanList.getUserChat().size() == 0) {
            mLilSearchFriendResult.setVisibility(View.GONE);
        } else {
            mNearestList.addAll(contentBeanList.getUserChat());
            List<SearchResultEntity> mSearchResultUserEntity = new ArrayList<>();
            if (contentBeanList.getUserChat().size() > NUM_3) {
                for (int i = 0; i < NUM_3; i++) {
                    mSearchResultUserEntity.add(mNearestList.get(i));
                }
            } else {
                mSearchResultUserEntity.addAll(mNearestList);
            }
            ImLog.e("ImLog", "mNearestList = " + mNearestList.size());
            if (mNearestAdapter != null) {
                mNearestAdapter.setSearchName(searchName, mSearchResultUserEntity);
            }
            ImLog.e("ImLog", "mNearestList = " + mNearestList.size());
        }
    }


    @Override
    public void onQueryGroupChat(MyCreatGroupEntity baseEntity) {
        if (baseEntity.getCode() == NUM_1 || null == baseEntity.getContent() || baseEntity.getContent().size() == 0) {
            mRelSearchNothing.setVisibility(View.VISIBLE);
            mLilSearchResult.setVisibility(View.GONE);
        } else {
            mRelSearchNothing.setVisibility(View.GONE);
            mLilSearchResult.setVisibility(View.VISIBLE);
            applyEntityList.clear();
            applyEntityList.addAll(baseEntity.getContent());
            mTxtSearchNearestNum.setText(String.valueOf(baseEntity.getContent().size()));
            if (null == commonAdapter) {
                commonAdapter = getGroupListAdapter();
                mLvSearchNearestList.setAdapter(commonAdapter);
            } else {
                commonAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 消息回调
     *
     * @param msgInfo 实体类
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageReceive(MessageBean msgInfo) {
        switch (msgInfo.getType()) {
            case NUM_65:
            case TYPE_DELETE_USER:
            case TYPE_OUTLOGIN:
                if (mJumpIntentFrom == JumpIntentFrom.Add) {
                    mQueryGroupChatModel.getQueryGroupChat(searchName);
                } else {
                    mDimSearchModel.getDimSearch(searchName, "");
                }
                break;
            default:
                break;
        }
    }
}
