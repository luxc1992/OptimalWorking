package com.yst.im.imchatlibrary.ui.activity;

import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.base.BaseActivity;
import com.yst.im.imchatlibrary.base.baseview.AbstractTitleView;
import com.yst.im.imchatlibrary.bean.IntentChatEntity;
import com.yst.im.imchatlibrary.bean.MyCreatGroupEntity;
import com.yst.im.imchatlibrary.bean.SearchFigureEntity;
import com.yst.im.imchatlibrary.bean.SearchResultEntity;
import com.yst.im.imchatlibrary.model.DimSearchFigureModel;
import com.yst.im.imchatlibrary.model.QueryCreateGroupChatModel;
import com.yst.im.imchatlibrary.model.QueryJoinGroupChatModel;
import com.yst.im.imchatlibrary.utils.BaseUtils;
import com.yst.im.imchatlibrary.utils.ImLog;
import com.yst.im.imchatlibrary.utils.SettingNineGroupIcon;
import com.yst.im.imchatlibrary.widget.ClearEditText;
import com.yst.im.imchatlibrary.widget.NineGridImageView;
import com.yst.im.imsdk.ChatType;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import static com.yst.im.imchatlibrary.utils.DefendMpUtils.LEFT_SQUARE_BRACKET;
import static com.yst.im.imchatlibrary.utils.DefendMpUtils.SPACE;
import static com.yst.im.imsdk.MessageConstant.NUM_0;
import static com.yst.im.imsdk.MessageConstant.NUM_1;


/**
 * 我的群聊页面
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/4/2.
 */

public class MineGroupActivity extends BaseActivity implements View.OnClickListener,
        QueryCreateGroupChatModel.QueryCreateGroupChatCallBack,
        QueryJoinGroupChatModel.QueryJoinGroupChatCallBack,
        DimSearchFigureModel.DimSearchFigureListenerCallBack {

    private AbstractTitleView titleviewMyGroup;
    private View viewLineStatus;
    private RelativeLayout relMyGroupSearch;
    private RelativeLayout relSearchResult;
    private TextView txtSearchResult;
    private LinearLayout llMyGroupSearch;
    private LinearLayout llSelectGroupType;
    private TextView txtMyGroupSearchClose;
    private TextView txtMyCreatGroup;
    private TextView txtMyAddGroup;
    private List<SearchResultEntity> applyEntityList;
    private ListView listViewMyGroup;
    private ClearEditText cedtSearchMyGroupInput;
    private SmartRefreshLayout refMyGroupRefresh;
    private LinearLayout llNoGroupData;
    private TextView txtGroupCount;

    private String searchName = "";
    private CommonAdapter<SearchResultEntity> commonAdapter;
    private QueryCreateGroupChatModel queryCreateGroupChatModel;
    private QueryJoinGroupChatModel queryJoinGroupChatModel;
    private DimSearchFigureModel mDimSearchFigureModel;
    /**
     * 0 ,我创建的 1， 我加入的
     */
    private int searchGroup = 0;
    private boolean isSearch = false;

    @Override
    protected int getLayout() {
        return R.layout.activity_my_group;
    }

    @Override
    protected void initView() {
        BaseUtils.setStatusTextColor(true, MineGroupActivity.this);
        titleviewMyGroup = (AbstractTitleView) findViewById(R.id.titleview_my_group);
        viewLineStatus = findViewById(R.id.view_line_status);
        relMyGroupSearch = (RelativeLayout) findViewById(R.id.rel_my_group_search);
        llMyGroupSearch = (LinearLayout) findViewById(R.id.ll_my_group_search);
        llSelectGroupType = (LinearLayout) findViewById(R.id.ll_select_group_type);
        txtMyGroupSearchClose = (TextView) findViewById(R.id.txt_my_group_search_close);
        relSearchResult = (RelativeLayout) findViewById(R.id.rel_search_result);
        txtSearchResult = (TextView) findViewById(R.id.txt_search_result);
        txtMyCreatGroup = (TextView) findViewById(R.id.txt_my_creat_group);
        txtMyAddGroup = (TextView) findViewById(R.id.txt_my_add_group);
        listViewMyGroup = (ListView) findViewById(R.id.listView_my_group);
        cedtSearchMyGroupInput = (ClearEditText) findViewById(R.id.cedt_search_mygroup_input);
        refMyGroupRefresh = (SmartRefreshLayout) findViewById(R.id.ref_my_group_refresh);
        llNoGroupData = (LinearLayout) findViewById(R.id.ll_no_group_data);
        txtGroupCount = (TextView) findViewById(R.id.txt_group_count);
        txtMyCreatGroup.setSelected(true);
        titleviewMyGroup.getLeftBackImageTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        titleviewMyGroup.setTitleText("我的群聊");
        relMyGroupSearch.setOnClickListener(this);
        txtMyGroupSearchClose.setOnClickListener(this);
        txtMyCreatGroup.setOnClickListener(this);
        txtMyAddGroup.setOnClickListener(this);
        refMyGroupRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {

            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

            }
        });
        refMyGroupRefresh.setEnableRefresh(false);
        refMyGroupRefresh.setEnableLoadmore(false);
        listViewMyGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                IntentChatEntity intentChatEntity = new IntentChatEntity();
                intentChatEntity.setAcceptName(applyEntityList.get(i).getGroupName());
                intentChatEntity.setAcceptId(String.valueOf(applyEntityList.get(i).getId()));
                intentChatEntity.setChatType(ChatType.GROUP);
                intentChatEntity.setGroupNum(applyEntityList.get(i).getGroupNumberByCurrent() + "");
                ChatScreenActivity.getJumpChatSource(MineGroupActivity.this, intentChatEntity);
            }
        });
    }

    @Override
    protected int getStatusColor() {
        return 0;
    }

    @Override
    protected boolean getStatus() {
        return false;
    }

    @Override
    protected void initData() {
        BaseUtils.setStatusTextColor(false, this);
        queryCreateGroupChatModel = new QueryCreateGroupChatModel(this);
        queryCreateGroupChatModel.setQueryCreateGroupChatCallBack(this);
        queryJoinGroupChatModel = new QueryJoinGroupChatModel(this);
        queryJoinGroupChatModel.setQueryJoinGroupChatCallBack(this);
        mDimSearchFigureModel = new DimSearchFigureModel(this);
        mDimSearchFigureModel.setDimSearchFigureListenerCallBack(this);
        applyEntityList = new ArrayList<>();
        commonAdapter = getGroupListAdapter();
        listViewMyGroup.setAdapter(commonAdapter);
        /**按下键盘的搜索*/

        cedtSearchMyGroupInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String edit = v.getText().toString();
                if ("".equals(edit = edit.replaceAll(SPACE, ""))) {
                    return false;
                }
                llNoGroupData.setVisibility(View.GONE);
                relSearchResult.setVisibility(View.VISIBLE);
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_NONE
                        || actionId == EditorInfo.IME_ACTION_GO
                        || actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    searchName = cedtSearchMyGroupInput.getText().toString().trim();
                    mDimSearchFigureModel.getDimSearch(searchName, String.valueOf(NUM_1));
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.rel_my_group_search) {
            isSearch = true;
            viewLineStatus.setBackgroundResource(R.drawable.shape_address_book_title);
            relSearchResult.setBackgroundColor(ContextCompat.getColor(this, R.color.colorffebebeb));
            relMyGroupSearch.setVisibility(View.GONE);
            titleviewMyGroup.setVisibility(View.GONE);
            llSelectGroupType.setVisibility(View.GONE);
            llMyGroupSearch.setVisibility(View.VISIBLE);
        } else if (id == R.id.txt_my_group_search_close) {
            isSearch = false;
            relMyGroupSearch.setVisibility(View.VISIBLE);
            titleviewMyGroup.setVisibility(View.VISIBLE);
            llSelectGroupType.setVisibility(View.VISIBLE);
            llMyGroupSearch.setVisibility(View.GONE);
            viewLineStatus.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWrite));
            relSearchResult.setBackgroundColor(ContextCompat.getColor(this, R.color.colorffe9f4ff));
            searchName = "";
            cedtSearchMyGroupInput.setText("");
            if (searchGroup == NUM_0) {
                queryCreateGroupChatModel.queryCreateGroupChat();
            } else {
                queryJoinGroupChatModel.queryJoinGroupChat();
            }
        } else if (id == R.id.txt_my_creat_group) {
            txtMyCreatGroup.setSelected(true);
            txtMyAddGroup.setSelected(false);
            txtSearchResult.setText("我创建的");
            searchGroup = NUM_0;
            applyEntityList.clear();
            commonAdapter.notifyDataSetChanged();
            txtGroupCount.setText("");
            if (searchGroup == NUM_0) {
                queryCreateGroupChatModel.queryCreateGroupChat();
            }
        } else if (id == R.id.txt_my_add_group) {
            txtSearchResult.setText("我加入的");
            txtMyCreatGroup.setSelected(false);
            txtMyAddGroup.setSelected(true);
            searchGroup = NUM_1;
            applyEntityList.clear();
            commonAdapter.notifyDataSetChanged();
            txtGroupCount.setText("");
            if (searchGroup == NUM_1) {
                queryJoinGroupChatModel.queryJoinGroupChat();
            }
        }
    }

    /**
     * 获取适配器
     */
    private CommonAdapter getGroupListAdapter() {
        CommonAdapter<SearchResultEntity> commonAdapter = new CommonAdapter<SearchResultEntity>(MineGroupActivity.this, R.layout.item_friendlist, applyEntityList) {
            @Override
            protected void convert(ViewHolder viewHolder, SearchResultEntity item, int position) {
                TextView txtFirendGroupNum = viewHolder.getView(R.id.txt_friend_list_group_num);
                txtFirendGroupNum.setTextColor(ContextCompat.getColor(MineGroupActivity.this, R.color.colorBlack222));
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
                    NineGridImageView imgIcon = viewHolder.getView(R.id.img_friend_list_user_icon);
                    SettingNineGroupIcon.setGroupIcon(imgIcon, list);
                }
                TextView view = viewHolder.getView(R.id.txt_friend_list_user_name);
                viewHolder.setText(R.id.txt_friend_list_group_num, "(" + item.getGroupNumberByCurrent() + ")");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    view.setText(Html.fromHtml(content, Html.FROM_HTML_MODE_COMPACT));
                } else {
                    view.setText(Html.fromHtml(content));
                }
            }
        };
        return commonAdapter;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (searchGroup == NUM_0) {
            queryCreateGroupChatModel.queryCreateGroupChat();
        } else {
            queryJoinGroupChatModel.queryJoinGroupChat();
        }
    }

    @Override
    public void queryCreateGroupChat(MyCreatGroupEntity baseEntity) {
        applyEntityList.clear();
        txtSearchResult.setText("我创建的");
        if (baseEntity.getCode() == NUM_0) {
            if (baseEntity.getContent() != null) {
                applyEntityList.addAll(baseEntity.getContent());
                commonAdapter.notifyDataSetChanged();
            }
        }
        if (applyEntityList.size() == 0) {
            llNoGroupData.setVisibility(View.VISIBLE);
            relSearchResult.setVisibility(View.GONE);
        } else {
            llNoGroupData.setVisibility(View.GONE);
            relSearchResult.setVisibility(View.VISIBLE);
            txtGroupCount.setText(String.valueOf(applyEntityList.size()));
        }
    }

    @Override
    public void queryJoinGroupChat(MyCreatGroupEntity baseEntity) {
        applyEntityList.clear();
        txtSearchResult.setText("我加入的");
        if (baseEntity.getCode() == NUM_0) {
            if (baseEntity.getContent() != null) {
                applyEntityList.addAll(baseEntity.getContent());
            }
        }
        if (applyEntityList.size() == 0) {
            llNoGroupData.setVisibility(View.VISIBLE);
            relSearchResult.setVisibility(View.GONE);
        } else {
            llNoGroupData.setVisibility(View.GONE);
            relSearchResult.setVisibility(View.VISIBLE);
            txtGroupCount.setText(String.valueOf(applyEntityList.size()));
        }
        commonAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDimSearch(SearchFigureEntity searchEntity) {
        applyEntityList.clear();
        txtSearchResult.setText("搜索结果");
        if (searchEntity.getCode() == NUM_0) {
            txtGroupCount.setText(String.valueOf(searchEntity.getContent().size()));
            if (searchEntity.getContent() != null) {
                applyEntityList.addAll(searchEntity.getContent());
            }
        }else {
            txtGroupCount.setText(String.valueOf("0"));
        }
        commonAdapter.notifyDataSetChanged();
    }

    /**
     * 设置退出提示： 对退出的设置时间监听
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ImLog.e("ImLog", "isSearch = " + isSearch);
            if (isSearch) {
                isSearch = false;
                relMyGroupSearch.setVisibility(View.VISIBLE);
                titleviewMyGroup.setVisibility(View.VISIBLE);
                llSelectGroupType.setVisibility(View.VISIBLE);
                llMyGroupSearch.setVisibility(View.GONE);
                viewLineStatus.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWrite));
                relSearchResult.setBackgroundColor(ContextCompat.getColor(this, R.color.colorffe9f4ff));
                searchName = "";
                cedtSearchMyGroupInput.setText("");
                if (searchGroup == NUM_0) {
                    queryCreateGroupChatModel.queryCreateGroupChat();
                } else {
                    queryJoinGroupChatModel.queryJoinGroupChat();
                }
                return true;
            } else {
                finish();
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
