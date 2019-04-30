package com.yst.im.imchatlibrary.ui.activity;

import android.os.Build;
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

import com.bumptech.glide.Glide;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.base.BaseActivity;
import com.yst.im.imchatlibrary.bean.BaseEntity;
import com.yst.im.imchatlibrary.bean.FriendInfoEntity;
import com.yst.im.imchatlibrary.bean.GroupDetailsEntity;
import com.yst.im.imchatlibrary.bean.IntentGroupEntity;
import com.yst.im.imchatlibrary.bean.IntentGroupMemberEntity;
import com.yst.im.imchatlibrary.bean.MyCreatGroupEntity;
import com.yst.im.imchatlibrary.bean.SearchGroupMemberEntity;
import com.yst.im.imchatlibrary.enumclass.GroupMemberEnum;
import com.yst.im.imchatlibrary.enumclass.IntentGroupDetail;
import com.yst.im.imchatlibrary.model.FindGroupDetailModel;
import com.yst.im.imchatlibrary.model.SearchUserInGroupModel;
import com.yst.im.imchatlibrary.model.SelectReationBetweenThePersonsModel;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.yst.im.imchatlibrary.utils.SettingNineGroupIcon;
import com.yst.im.imchatlibrary.widget.ClearEditText;
import com.yst.im.imchatlibrary.widget.NineGridImageView;
import com.yst.im.imchatlibrary.widget.NoScrollGridView;
import com.yst.im.imsdk.ChatType;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.yst.im.imchatlibrary.utils.DefendMpUtils.SPACE;
import static com.yst.im.imsdk.MessageConstant.GROUP_ADD;
import static com.yst.im.imsdk.MessageConstant.GROUP_DELETE;
import static com.yst.im.imsdk.MessageConstant.NUM_0;
import static com.yst.im.imsdk.MessageConstant.NUM_1;
import static com.yst.im.imsdk.MessageConstant.NUM_2;
import static com.yst.im.imsdk.MessageConstant.NUM_7;

/**
 * 更多群成员
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/4.
 */
public class MoreGroupMembersActivity extends BaseActivity implements View.OnClickListener,
        SearchUserInGroupModel.SearchUserInGroupListenerCallBack,
        SelectReationBetweenThePersonsModel.selectReationBetweenThePersonsCallBack, FindGroupDetailModel.FindGroupDetailListenerCallBack {

    private ImageView mImgGroupMembersBack;
    private TextView mTxtGroupMembersName;
    private TextView mTxtGroupMembersNum;
    private NoScrollGridView mGvGroupMembersList;
    private ListView listGroupMembersList;
    private LinearLayout llMyGroupSearch;
    private RelativeLayout relGroupMoreSearch;
    private GroupDetailsEntity groupDetailsEntity;
    private TextView txtMyGroupSearchClose;
    private ClearEditText cedtSearchMyGroupInput;
    private CommonAdapter<GroupDetailsEntity.GroupsBean> commonAdapter;
    private CommonAdapter<SearchGroupMemberEntity.ContentBean> memberCommonAdapter;
    private SelectReationBetweenThePersonsModel mSelectReationBetweenThePersonsModel;
    private SearchUserInGroupModel mSearchUserInGroupModel;
    private String relation;
    private String searchName = "";
    private String userId = "";
    private String userName = "";
    private String remarkName = "";
    private List<SearchGroupMemberEntity.ContentBean> searchGroupMemberEntities;
    private FindGroupDetailModel mFindGroupDetailModel;
    private List<GroupDetailsEntity.GroupsBean> groups;

    @Override
    protected int getLayout() {
        return R.layout.activity_more_group_members;
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
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.rel_group_more_search) {
            relGroupMoreSearch.setVisibility(View.GONE);
            llMyGroupSearch.setVisibility(View.VISIBLE);
            mGvGroupMembersList.setVisibility(View.GONE);
            listGroupMembersList.setVisibility(View.VISIBLE);
        } else if (id == R.id.txt_my_group_search_close) {
            relGroupMoreSearch.setVisibility(View.VISIBLE);
            llMyGroupSearch.setVisibility(View.GONE);
            mGvGroupMembersList.setVisibility(View.VISIBLE);
            listGroupMembersList.setVisibility(View.GONE);
            cedtSearchMyGroupInput.setText("");
            searchName = "";
            searchGroupMemberEntities.clear();
            memberCommonAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void initView() {
        MyApp.addActivity(this);
        mImgGroupMembersBack = (ImageView) findViewById(R.id.img_group_members_back);
        mTxtGroupMembersName = (TextView) findViewById(R.id.txt_group_members_name);
        mTxtGroupMembersNum = (TextView) findViewById(R.id.txt_group_members_num);
        mGvGroupMembersList = (NoScrollGridView) findViewById(R.id.gv_group_members_list);
        listGroupMembersList = (ListView) findViewById(R.id.list_group_members_list);
        relGroupMoreSearch = (RelativeLayout) findViewById(R.id.rel_group_more_search);
        llMyGroupSearch = (LinearLayout) findViewById(R.id.ll_my_group_search);
        txtMyGroupSearchClose = (TextView) findViewById(R.id.txt_my_group_search_close);
        cedtSearchMyGroupInput = (ClearEditText) findViewById(R.id.cedt_search_mygroup_input);
        relGroupMoreSearch.setOnClickListener(this);
        txtMyGroupSearchClose.setOnClickListener(this);

        /**按下键盘的搜索*/
        cedtSearchMyGroupInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
                    searchName = cedtSearchMyGroupInput.getText().toString().trim();
                    mSearchUserInGroupModel.getSearchUserInGroup(String.valueOf(groupDetailsEntity.getId()), searchName);
                }
                return false;
            }
        });
        mImgGroupMembersBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFindGroupDetailModel.getFindGroupDetail(String.valueOf(groupDetailsEntity.getId()));
    }

    @Override
    protected void initData() {
        mSearchUserInGroupModel = new SearchUserInGroupModel(this);
        mSearchUserInGroupModel.setSearchUserInGroupListenerCallBack(this);
        mSelectReationBetweenThePersonsModel = new SelectReationBetweenThePersonsModel(this);
        mSelectReationBetweenThePersonsModel.setselectReationBetweenThePersonsCallBack(this);
        searchGroupMemberEntities = new ArrayList<>();
        listGroupMembersList.setAdapter(getGroupMemberListAdapter());
        groupDetailsEntity = (GroupDetailsEntity) getIntent().getSerializableExtra("groupEntity");
        mFindGroupDetailModel = new FindGroupDetailModel(this);
        mFindGroupDetailModel.setFindGroupDetailListenerCallBack(this);
        mGvGroupMembersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GroupDetailsEntity.GroupsBean contentBean = groupDetailsEntity.getGroups().get(position);
                userId = contentBean.getUserId();
                userName = contentBean.getNickName();
                remarkName = contentBean.getNickName();
                if (relation.equals(String.valueOf(NUM_0))) {
                    if (position == commonAdapter.getCount() - 1) {
                        IntentGroupMemberEntity intentGroupMemberEntity = new IntentGroupMemberEntity();
                        intentGroupMemberEntity.setGroupId(String.valueOf(groupDetailsEntity.getId()));
                        intentGroupMemberEntity.setGroupMemberEnum(GroupMemberEnum.deleted);
                        intentGroupMemberEntity.setTitleName(getResources().getString(R.string.txt_delete_groups));
                        GroupMemberActivity.getJumpGroupMember(MoreGroupMembersActivity.this, intentGroupMemberEntity);
                    } else if (position == commonAdapter.getCount() - NUM_2) {
                        IntentGroupMemberEntity intentGroupMemberEntity = new IntentGroupMemberEntity();
                        intentGroupMemberEntity.setGroupId(String.valueOf(groupDetailsEntity.getId()));
                        intentGroupMemberEntity.setGroupMemberEnum(GroupMemberEnum.Invited);
                        intentGroupMemberEntity.setTitleName(getResources().getString(R.string.txt_choose_groups));
                        GroupMemberActivity.getJumpGroupMember(MoreGroupMembersActivity.this, intentGroupMemberEntity);
                    } else {
                        if (MyApp.manager.getId().equals(contentBean.getUserId())) {
                            return;
                        }
                        mSelectReationBetweenThePersonsModel.selectReationBetweenThePersons(contentBean.getUserId());
                    }
                } else if (relation.equals(String.valueOf(NUM_1))) {
                    if (position == commonAdapter.getCount() - 1) {
                        IntentGroupMemberEntity intentGroupMemberEntity = new IntentGroupMemberEntity();
                        intentGroupMemberEntity.setGroupId(String.valueOf(groupDetailsEntity.getId()));
                        intentGroupMemberEntity.setGroupMemberEnum(GroupMemberEnum.Invited);
                        intentGroupMemberEntity.setTitleName(getResources().getString(R.string.txt_choose_groups));
                        GroupMemberActivity.getJumpGroupMember(MoreGroupMembersActivity.this, intentGroupMemberEntity);
                    } else {
                        if (MyApp.manager.getId().equals(contentBean.getUserId())) {
                            return;
                        }
                        mSelectReationBetweenThePersonsModel.selectReationBetweenThePersons(contentBean.getUserId());
                    }
                } else {
                    if (MyApp.manager.getId().equals(contentBean.getUserId())) {
                        return;
                    }
                    mSelectReationBetweenThePersonsModel.selectReationBetweenThePersons(contentBean.getUserId());
                }
            }
        });

        listGroupMembersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchGroupMemberEntity.ContentBean contentBean = searchGroupMemberEntities.get(position);
                userId = contentBean.getUserId();
                userName = contentBean.getNickName();
                if (MyApp.manager.getId().equals(contentBean.getUserId())) {
                    return;
                }
                mSelectReationBetweenThePersonsModel.selectReationBetweenThePersons(contentBean.getUserId());
            }
        });
    }

    /**
     * 获取适配器
     */
    private CommonAdapter getGroupListAdapter(final List<GroupDetailsEntity.GroupsBean> groups) {
        commonAdapter = new CommonAdapter<GroupDetailsEntity.GroupsBean>(
                MoreGroupMembersActivity.this, R.layout.item_grid_news_more, groups) {
            @Override
            protected void convert(ViewHolder viewHolder, GroupDetailsEntity.GroupsBean item, int position) {
                viewHolder.setText(R.id.txt_news_more_text, item.getNickName());
                ImageView view = viewHolder.getView(R.id.img_news_more_pic);
                Glide.with(MoreGroupMembersActivity.this)
                        .load(item.getUserIcon())
                        .error(R.mipmap.icon_default)
                        .into(view);
                if (GROUP_ADD.equals(item.getUserIcon())) {
                    Glide.with(MoreGroupMembersActivity.this)
                            .load(R.drawable.icon_group_add)
                            .error(R.mipmap.icon_default)
                            .into(view);
                }
                if (GROUP_DELETE.equals(item.getUserIcon())) {
                    Glide.with(MoreGroupMembersActivity.this)
                            .load(R.drawable.icon_group_delete)
                            .error(R.mipmap.icon_default)
                            .into(view);
                }
            }
        };
        return commonAdapter;
    }

    /**
     * 获取适配器
     */
    private CommonAdapter getGroupMemberListAdapter() {
        memberCommonAdapter = new CommonAdapter<SearchGroupMemberEntity.ContentBean>(
                MoreGroupMembersActivity.this, R.layout.item_friendlist, searchGroupMemberEntities) {
            @Override
            protected void convert(ViewHolder viewHolder, final SearchGroupMemberEntity.ContentBean item, int position) {
                TextView view = viewHolder.getView(R.id.txt_friend_list_user_name);
                String nickName = item.getNickName();
                String content = nickName;
                NineGridImageView imgIcon = viewHolder.getView(R.id.img_friend_list_user_icon);
                List<String> list = new ArrayList<>();
                list.add(item.getUserIcon());
                SettingNineGroupIcon.setGroupIcon(imgIcon, list);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    view.setText(Html.fromHtml(content, Html.FROM_HTML_MODE_COMPACT));
                } else {
                    view.setText(Html.fromHtml(content));
                }

            }
        };
        return memberCommonAdapter;
    }


    @Override
    public void onSearchUserInGroup(SearchGroupMemberEntity mSearchGroupMemberEntity) {
        searchGroupMemberEntities.clear();
        if (mSearchGroupMemberEntity.getCode() == NUM_0) {
            searchGroupMemberEntities.addAll(mSearchGroupMemberEntity.getContent());
        }
        memberCommonAdapter.notifyDataSetChanged();
    }

    @Override
    public void selectReationBetweenThePersons(BaseEntity baseEntity) {
        if (baseEntity.getCode() == NUM_0) {
            FriendInfoEntity friendInfoEntity = new FriendInfoEntity();
            if (String.valueOf(NUM_0).equals(baseEntity.getContent())) {
                friendInfoEntity.setState(baseEntity.getContent());
                friendInfoEntity.setUserId(userId);
                friendInfoEntity.setNickName(userName);
                friendInfoEntity.setRemark(remarkName);
                ApplyAffirmActivity.getJumpApplyAffirmActivity(MoreGroupMembersActivity.this, 4, friendInfoEntity);
            } else {
                friendInfoEntity.setState(baseEntity.getContent());
                friendInfoEntity.setUserId(userId);
                friendInfoEntity.setNickName(userName);
                ApplyAffirmActivity.getJumpApplyAffirmActivity(MoreGroupMembersActivity.this, 4, friendInfoEntity);
            }
        }
    }

    @Override
    public void onFindGroupDetail(GroupDetailsEntity groupDetailsEntity) {
        String groupName = groupDetailsEntity.getGroupName();
        if (groupName.length() > NUM_7) {
            String name = groupName.substring(0, 7);
            mTxtGroupMembersName.setText(String.valueOf(name + "(" + groupDetailsEntity.getGroupNumberByCurrent() + ")"));
        } else {
            mTxtGroupMembersName.setText(String.valueOf(groupName + "(" + groupDetailsEntity.getGroupNumberByCurrent() + ")"));
        }
        relation = getIntent().getStringExtra("relation");
        groups = groupDetailsEntity.getGroups();
        for (int i = 0; i < groups.size(); i++) {
            if (groupDetailsEntity.getUserId() == groups.get(i).getId()) {
                GroupDetailsEntity.GroupsBean groupManager = groups.get(i);
                groups.remove(i);
                groups.add(0, groupManager);
            }
        }

        if (!relation.equals(String.valueOf(NUM_2))) {
            GroupDetailsEntity.GroupsBean groupsBean = new GroupDetailsEntity.GroupsBean();
            groupsBean.setUserIcon("jia");
            groups.add(groupsBean);
            if (relation.equals(String.valueOf(0))) {
                for (int i = 0; i < groups.size(); i++) {
                    if (groups.get(i).getId() == groupDetailsEntity.getUserId()
                            && MyApp.manager.getId().equals(groups.get(i).getUserId())) {
                        GroupDetailsEntity.GroupsBean groupDelete = new GroupDetailsEntity.GroupsBean();
                        groupDelete.setUserIcon("jian");
                        groups.add(groupDelete);
                    }
                }
            }
        }

        mGvGroupMembersList.setAdapter(getGroupListAdapter(groups));
    }
}
