package com.yst.im.imchatlibrary.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.adapter.DeleteGroupsAdapter;
import com.yst.im.imchatlibrary.base.BaseActivity;
import com.yst.im.imchatlibrary.base.baseview.AbstractTitleView;
import com.yst.im.imsdk.bean.ContactsEntity;
import com.yst.im.imchatlibrary.bean.ForWardingEntity;
import com.yst.im.imchatlibrary.bean.RecentContactEntity;
import com.yst.im.imchatlibrary.model.RecentContactsModel;

import java.util.ArrayList;
import java.util.List;

import static com.yst.im.imchatlibrary.ui.activity.ChatScreenActivity.NEWS_RECALL;
import static com.yst.im.imsdk.MessageConstant.NUM_0;
import static com.yst.im.imsdk.MessageConstant.NUM_1;
import static com.yst.im.imsdk.MessageConstant.NUM_3;
import static com.yst.im.imsdk.MessageConstant.NUM_65;
import static com.yst.im.imsdk.MessageConstant.NUM_70;
import static com.yst.im.imsdk.MessageConstant.NUM_71;
import static com.yst.im.imsdk.MessageConstant.NUM_80;
import static com.yst.im.imsdk.MessageConstant.TYPE_DELETE_USER;

/**
 * 转发 A
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/3.
 */
public class ForwardingActivity extends BaseActivity implements RecentContactsModel.RecentContactsCallBack, AdapterView.OnItemClickListener {
    private AbstractTitleView mTitleViewRecallTitle;
    private RelativeLayout mRelRecallSearch;
    private ListView mLvRecallChatList;
    private List<ContactsEntity> mReCallList;
    private DeleteGroupsAdapter mReCallContactAdapter;
    private int mSelectNum = 0;
    private String mUserIds;
    private String mEvent;

    @Override
    protected int getLayout() {
        return R.layout.activity_forwarding;
    }

    @Override
    protected int getStatusColor() {
        return 0;
    }

    @Override
    protected void initView() {
        mTitleViewRecallTitle = (AbstractTitleView) findViewById(R.id.titleView_recall_title);
        mRelRecallSearch = (RelativeLayout) findViewById(R.id.rel_recall_search);
        mLvRecallChatList = (ListView) findViewById(R.id.lv_recall_chatList);
        mTitleViewRecallTitle.setTitleText(getResources().getString(R.string.txt_group_update_transport));
        mTitleViewRecallTitle.getLeftBackImageTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleViewRecallTitle.getRightTextTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectNum > 0) {
                    mTitleViewRecallTitle.setRightTvText(String.valueOf("发送(" + mSelectNum + ")"));
                    StringBuilder stringBuilder = new StringBuilder();
                    StringBuilder sbEvent = new StringBuilder();

                    for (int i = 0; i < mReCallList.size(); i++) {

                        if (mReCallList.get(i).isChecked()) {
                            if (mReCallList.get(i).getSenderId().equals(MyApp.manager.getId())) {
                                stringBuilder.append(mReCallList.get(i).getAccepteId());
                                stringBuilder.append(",");
                                sbEvent.append(mReCallList.get(i).getEvent());
                                sbEvent.append(",");
                            } else {
                                stringBuilder.append(mReCallList.get(i).getSenderId());
                                stringBuilder.append(",");
                                sbEvent.append(mReCallList.get(i).getEvent());
                                sbEvent.append(",");
                            }
                        }
                    }
                    mUserIds = stringBuilder.toString();
                    mUserIds = mUserIds.substring(NUM_0, mUserIds.length() - NUM_1);
                    mEvent = sbEvent.toString();
                    mEvent = mEvent.substring(NUM_0, mEvent.length() - NUM_1);
                    ForWardingEntity forWardingEntity = new ForWardingEntity();
                    forWardingEntity.setAcceptId(mUserIds);
                    forWardingEntity.setEvent(String.valueOf(mEvent));
                    Intent intent = new Intent();
                    // 获得转发用户
                    intent.putExtra("forWardingEntity", forWardingEntity);
                    setResult(NEWS_RECALL, intent);
                    finish();
//

                } else if (mReCallContactAdapter.isSelectMore) {
                    mReCallContactAdapter.selectMore(false);
                    mTitleViewRecallTitle.setRightTvText(getResources().getString(R.string.txt_more_select));
                } else {
                    mReCallContactAdapter.selectMore(true);
                    mTitleViewRecallTitle.setRightTvText(getResources().getString(R.string.txt_single_select));
                }
            }
        });
    }

    @Override
    protected boolean getStatus() {
        return false;
    }

    @Override
    protected void initData() {
        RecentContactsModel recentContactsModel = new RecentContactsModel(this);
        recentContactsModel.setRecentContactsCallBack(this);
        recentContactsModel.getRecentContacts();
        mReCallList = new ArrayList<>();
        mReCallContactAdapter = new DeleteGroupsAdapter(this, mReCallList);
        mLvRecallChatList.setAdapter(mReCallContactAdapter);
        mLvRecallChatList.setOnItemClickListener(this);

    }


    @Override
    public void onRecentContacts(RecentContactEntity recentContactEntity) {
        if (recentContactEntity.getCode() == NUM_0) {

            //转发
            mReCallList.clear();
            for (int i = 0; i < recentContactEntity.getContent().size(); i++) {
                boolean isNotice = recentContactEntity.getContent().get(i).getType() == NUM_65
                        || recentContactEntity.getContent().get(i).getType() == NUM_70
                        || recentContactEntity.getContent().get(i).getType() == NUM_71
                        || recentContactEntity.getContent().get(i).getType() == TYPE_DELETE_USER;
                recentContactEntity.getContent().get(i).setReCall(true);
                if (!isNotice) {
                    mReCallList.add( recentContactEntity.getContent().get(i));
                }
            }
            mReCallContactAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mSelectNum = 0;
        if (mReCallContactAdapter.isSelectMore) {
            mReCallList.get(position).setChecked(!mReCallList.get(position).isChecked());
            mReCallContactAdapter.notifyDataSetChanged();
            for (int i = 0; i < mReCallList.size(); i++) {
                if (mReCallList.get(i).isChecked()) {
                    mSelectNum++;
                    mTitleViewRecallTitle.setRightTvText(String.valueOf("发送(" + mSelectNum + ")"));
                }
            }
        } else {
            if (mReCallList.get(position).getAccepteId().equals(MyApp.manager.getId())) {
                ForWardingEntity forWardingEntity = new ForWardingEntity();
                forWardingEntity.setAcceptId(mReCallList.get(position).getSenderId());
                forWardingEntity.setEvent(String.valueOf(mReCallList.get(position).getEvent()));
                Intent intent = new Intent();
                // 获得转发用户
                intent.putExtra("forWardingEntity", forWardingEntity);
                setResult(NEWS_RECALL, intent);
                finish();
            } else {
                ForWardingEntity forWardingEntity = new ForWardingEntity();
                forWardingEntity.setAcceptId(mReCallList.get(position).getAccepteId());
                forWardingEntity.setEvent(String.valueOf(mReCallList.get(position).getEvent()));
                Intent intent = new Intent();
                // 获得转发用户
                intent.putExtra("forWardingEntity", forWardingEntity);
                setResult(NEWS_RECALL, intent);
                finish();
            }
        }
    }
}
