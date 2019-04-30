package com.yst.im.imchatlibrary.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.adapter.FriendListAdapter;
import com.yst.im.imchatlibrary.adapter.SearchResultAdapter;
import com.yst.im.imchatlibrary.base.BaseActivity;
import com.yst.im.imchatlibrary.bean.GroupChatEntity;
import com.yst.im.imchatlibrary.bean.IntentChatEntity;
import com.yst.im.imchatlibrary.bean.SearchEntity;
import com.yst.im.imchatlibrary.bean.SearchFigureEntity;
import com.yst.im.imchatlibrary.bean.SearchResultEntity;
import com.yst.im.imchatlibrary.bean.UserIntentChatEntity;
import com.yst.im.imchatlibrary.model.DimSearchFigureModel;
import com.yst.im.imchatlibrary.model.DimSearchModel;
import com.yst.im.imchatlibrary.utils.BaseUtils;
import com.yst.im.imchatlibrary.utils.ImLog;
import com.yst.im.imchatlibrary.utils.JumpIntent;
import com.yst.im.imchatlibrary.widget.ClearEditText;
import com.yst.im.imsdk.ChatType;
import com.yst.im.imsdk.bean.MessageBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import gorden.rxbus2.Subscribe;
import gorden.rxbus2.ThreadMode;

import static com.yst.im.imchatlibrary.utils.DefendMpUtils.SPACE;
import static com.yst.im.imsdk.MessageConstant.NUM_0;
import static com.yst.im.imsdk.MessageConstant.NUM_1;
import static com.yst.im.imsdk.MessageConstant.NUM_65;
import static com.yst.im.imsdk.MessageConstant.TYPE_DELETE_USER;
import static com.yst.im.imsdk.MessageConstant.TYPE_OUTLOGIN;

/**
 * 搜索结果 A
 *
 * @author Lierpeng
 * @version 1.0.2
 * @date 2018/03/29
 */
public class ImSearchResultActivity extends BaseActivity implements DimSearchFigureModel.DimSearchFigureListenerCallBack {
    private ClearEditText mCedtSearchResultInput;
    private LinearLayout mLilSearchResultNearest;
    private RelativeLayout relSearchNothing;
    private ListView mLvSearchResultNearestList;
    private TextView mTxtSearchResultType;
    private SearchResultAdapter mNearestAdapter;
    private ChatType chatType;
    private HashMap<String, List<SearchResultEntity>> mHashMap;
    private List<SearchResultEntity> searchList;
    private String searchText = "";
    private DimSearchFigureModel mDimSearchModel;

    @Override
    protected int getLayout() {
        return R.layout.activity_im_search_result;
    }

    @Override
    protected int getStatusColor() {
        return 0;
    }

    @Override
    protected void initView() {
        BaseUtils.setStatusTextColor(true, ImSearchResultActivity.this);
        mCedtSearchResultInput = (ClearEditText) findViewById(R.id.cedt_search_result_input);
        mLilSearchResultNearest = (LinearLayout) findViewById(R.id.lil_search_result_nearest);
        relSearchNothing = (RelativeLayout) findViewById(R.id.rel_search_nothing);
        mLvSearchResultNearestList = (ListView) findViewById(R.id.lv_search_result_nearest_list);
        mTxtSearchResultType = (TextView) findViewById(R.id.txt_search_result_type);
        ImageView mImgSearchResultBack = (ImageView) findViewById(R.id.img_search_result_back);
        TextView mTxtSearchResultCancle = (TextView) findViewById(R.id.txt_search_result_cancle);

        chatType = (ChatType) getIntent().getSerializableExtra("chatType");
        searchText = getIntent().getStringExtra("searchText");
        mCedtSearchResultInput.setText(searchText);
        mCedtSearchResultInput.setSelection(searchText.length());
        //私聊
        if (chatType == ChatType.C2C) {
            mTxtSearchResultType.setText(getResources().getString(R.string.txt_search_nearest));
            //群聊
        } else if (chatType == ChatType.GROUP) {
            mTxtSearchResultType.setText(getResources().getString(R.string.txt_search_group));
        }
        mImgSearchResultBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTxtSearchResultCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //按下键盘的搜索
        mCedtSearchResultInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
                    searchText = mCedtSearchResultInput.getText().toString().trim();
                    //私聊
                    if (chatType == ChatType.C2C) {
                        mDimSearchModel.getDimSearch(searchText, "2");
                        //群聊
                    } else if (chatType == ChatType.GROUP) {
                        mDimSearchModel.getDimSearch(searchText, "1");
                        mTxtSearchResultType.setText(getResources().getString(R.string.txt_search_group));
                    }
                }
                return false;
            }
        });
    }

    /**
     * 页面跳转
     *
     * @param context  上下文
     * @param chatType 跳转来源类型
     */
    public static void getJumpChatSource(Context context, ChatType chatType, List<SearchResultEntity> searchList, String searchText) {
        Intent intent = new Intent(context, ImSearchResultActivity.class);
        HashMap<String, List<SearchResultEntity>> stringListHashMap = new HashMap<>(16);
        stringListHashMap.put("searchList", searchList);
        intent.putExtra("chatType", chatType);
        intent.putExtra("searchText", searchText);
        intent.putExtra("searchResultEntity", stringListHashMap);
        context.startActivity(intent);
    }

    @Override
    protected boolean getStatus() {
        return true;
    }

    @Override
    protected void initData() {
        searchList = new ArrayList<>();
        mDimSearchModel = new DimSearchFigureModel(this);
        mDimSearchModel.setDimSearchFigureListenerCallBack(this);
        ImLog.e("ImLog", "initData - searchList = " + searchList.size());
        mNearestAdapter = new SearchResultAdapter(this, searchList, chatType);
        mLvSearchResultNearestList.setAdapter(mNearestAdapter);
        if (!TextUtils.isEmpty(searchText)) {
            mNearestAdapter.setSearchName(searchText);
        }
        mLvSearchResultNearestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //私聊
                if (chatType == ChatType.C2C) {
                    IntentChatEntity intentChatEntity=new IntentChatEntity();
                    intentChatEntity.setChatType(ChatType.C2C);
                    intentChatEntity.setAcceptId(searchList.get(position).getUserId());
                    if ((null!=searchList.get(position).getRemark())) {
                        intentChatEntity.setAcceptName(searchList.get(position).getRemark());
                    }else {
                        intentChatEntity.setAcceptName(searchList.get(position).getNickName());
                    }
                    ChatScreenActivity.getJumpChatSource(ImSearchResultActivity.this,intentChatEntity);
                    //群聊
                } else if (chatType == ChatType.GROUP) {
                    IntentChatEntity intentChatEntity=new IntentChatEntity();
                    intentChatEntity.setChatType(ChatType.GROUP);
                    intentChatEntity.setGroupNum(searchList.get(position).getGroupNumberByCurrent());
                    intentChatEntity.setAcceptId(searchList.get(position).getId());
                    intentChatEntity.setAcceptName(searchList.get(position).getGroupName());
                    ChatScreenActivity.getJumpChatSource(ImSearchResultActivity.this,intentChatEntity);
                }
            }
        });
        //私聊
        if (chatType == ChatType.C2C) {
            mDimSearchModel.getDimSearch(searchText, "2");
            //群聊
        } else if (chatType == ChatType.GROUP) {
            mDimSearchModel.getDimSearch(searchText, "1");
            mTxtSearchResultType.setText(getResources().getString(R.string.txt_search_group));
        }
    }

    @Override
    public void onDimSearch(SearchFigureEntity searchEntity) {
        searchList.clear();
        mLilSearchResultNearest.setVisibility(View.VISIBLE);
        relSearchNothing.setVisibility(View.GONE);
        if (searchEntity.getCode() == NUM_0) {
            this.searchList.addAll(searchEntity.getContent());
        } else {
            mLilSearchResultNearest.setVisibility(View.GONE);
            relSearchNothing.setVisibility(View.VISIBLE);
        }
        mNearestAdapter.setSearchName(searchText);
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
                if (chatType == ChatType.C2C) {
                    mDimSearchModel.getDimSearch(searchText, "2");
                    //群聊
                } else if (chatType == ChatType.GROUP) {
                    mDimSearchModel.getDimSearch(searchText, "1");
                    mTxtSearchResultType.setText(getResources().getString(R.string.txt_search_group));
                }
                break;
            default:
                break;
        }
    }
}
