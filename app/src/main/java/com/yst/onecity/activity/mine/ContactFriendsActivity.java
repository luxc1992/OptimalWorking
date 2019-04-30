package com.yst.onecity.activity.mine;

import android.content.Intent;
import android.net.Uri;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.onecity.R;
import com.yst.onecity.adapter.SortGroupMemberAdapter;
import com.yst.onecity.bean.ContactsBean;
import com.yst.onecity.bean.GroupMemberBean;
import com.yst.onecity.utils.CharacterParser;
import com.yst.onecity.utils.ContactUtils;
import com.yst.onecity.utils.PinyinComparator;
import com.yst.onecity.view.ClearEditText;
import com.yst.onecity.view.SideBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 功能描述
 *
 * @author luxuchang
 * @version 1.0.1
 * @date 2018/2/26.
 */

public class ContactFriendsActivity extends BaseActivity {

    @BindView(R.id.filter_edit)
    ClearEditText mClearEditText;
    @BindView(R.id.lv_contact)
    ListView sortListView;
    @BindView(R.id.title_layout_no_friends)
    TextView tvNoFriends;
    @BindView(R.id.title_layout_catalog)
    TextView title;
    @BindView(R.id.title_layout)
    LinearLayout titleLayout;
    @BindView(R.id.dialog)
    TextView dialog;
    @BindView(R.id.sidebar)
    SideBar sideBar;
    private SortGroupMemberAdapter adapter;

    /**
     * 上次第一个可见元素，用于滚动时记录标识。
     */
    private int lastFirstVisibleItem = -1;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    /**
     * 联系人列表
     */
    private List<GroupMemberBean> sourceDateList;
    /**
     * 选择的数据列表
     */
    private List<GroupMemberBean> selectDateList = new ArrayList<>();
    /**
     * 搜索的数据列表
     */
    private List<GroupMemberBean> filterDateList;
    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;
    private String smsto = "smsto:";
    private String shareUrl;

    @Override
    public int getLayoutId() {
        return R.layout.activity_contact_friends;
    }

    @Override
    public void initData() {
        initTitleBar("通讯录好友");
        shareUrl = getIntent().getStringExtra("shareUrl");
        initViews();
    }

    private void initViews() {
        // 实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        List<ContactsBean> contactInfo = ContactUtils.getSystemContactInfos(ContactFriendsActivity.this);

        if (contactInfo.size() == 0) {
            tvNoFriends.setVisibility(View.VISIBLE);
            tvNoFriends.setText("没有联系人");
            sideBar.setVisibility(View.GONE);
        } else {
            tvNoFriends.setVisibility(View.GONE);
            sideBar.setVisibility(View.VISIBLE);
        }
        pinyinComparator = new PinyinComparator();

        sideBar.setTextView(dialog);

        // 设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }
            }
        });

        sortListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 这里要利用adapter.getItem(position)来获取当前position所对应的对象
                if (TextUtils.isEmpty(mClearEditText.getText().toString().trim())) {
                    if (sourceDateList.get(position).isChecked()) {
                        sourceDateList.get(position).setChecked(false);
                    } else {
                        sourceDateList.get(position).setChecked(true);
                    }
                } else {
                    if (filterDateList.get(position).isChecked()) {
                        filterDateList.get(position).setChecked(false);
                    } else {
                        filterDateList.get(position).setChecked(true);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        sourceDateList = filledData(contactInfo);

        // 根据a-z进行排序源数据
        Collections.sort(sourceDateList, pinyinComparator);
        adapter = new SortGroupMemberAdapter(this, sourceDateList);
        sortListView.setAdapter(adapter);
        sortListView.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (sourceDateList.size() == 0) {
                    return;
                }
                int section = getSectionForPosition(firstVisibleItem);
                int nextSection = getSectionForPosition(firstVisibleItem + 1);
                int nextSecPosition = getPositionForSection(+nextSection);
                if (firstVisibleItem != lastFirstVisibleItem) {
                    MarginLayoutParams params = (MarginLayoutParams) titleLayout.getLayoutParams();
                    params.topMargin = 0;
                    titleLayout.setLayoutParams(params);
                    title.setText(sourceDateList.get(getPositionForSection(section)).getSortLetters());
                }
                if (nextSecPosition == firstVisibleItem + 1) {
                    View childView = view.getChildAt(0);
                    if (childView != null) {
                        int titleHeight = titleLayout.getHeight();
                        int bottom = childView.getBottom();
                        MarginLayoutParams params = (MarginLayoutParams) titleLayout.getLayoutParams();
                        if (bottom < titleHeight) {
                            float pushedDistance = bottom - titleHeight;
                            params.topMargin = (int) pushedDistance;
                            titleLayout.setLayoutParams(params);
                        } else {
                            if (params.topMargin != 0) {
                                params.topMargin = 0;
                                titleLayout.setLayoutParams(params);
                            }
                        }
                    }
                }
                lastFirstVisibleItem = firstVisibleItem;
            }
        });

        // 根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 这个时候不需要挤压效果 就把他隐藏掉
                titleLayout.setVisibility(View.GONE);
                // 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mClearEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean b = actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
                if (b) {
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(ContactFriendsActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 为ListView填充数据
     *
     * @param data 手机联系人数据
     * @return 返回
     */
    private List<GroupMemberBean> filledData(List<ContactsBean> data) {
        List<GroupMemberBean> mSortList = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            GroupMemberBean sortModel = new GroupMemberBean();
            sortModel.setName(data.get(i).getContactName());
            sortModel.setPhone(data.get(i).getPhoneNumber());
            // 汉字转换成拼音
            String pinyin = characterParser.getSelling(TextUtils.isEmpty(data.get(i).getContactName())?"#":data.get(i).getContactName());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr 关键字搜索
     */

    private void filterData(String filterStr) {
        filterDateList = new ArrayList<>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = sourceDateList;
            tvNoFriends.setVisibility(View.GONE);
        } else {
            filterDateList.clear();
            for (GroupMemberBean sortModel : sourceDateList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
        if (filterDateList.size() == 0) {
            tvNoFriends.setVisibility(View.VISIBLE);
            tvNoFriends.setText("没有匹配的联系人");
        }
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        if (sourceDateList != null && sourceDateList.size() > 0) {
            return sourceDateList.get(position).getSortLetters().charAt(0);
        }
        return 0;
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        if (sourceDateList != null && sourceDateList.size() > 0) {
            for (int i = 0; i < sourceDateList.size(); i++) {
                String sortStr = sourceDateList.get(i).getSortLetters();
                char firstChar = sortStr.toUpperCase().charAt(0);
                if (firstChar == section) {
                    return i;
                }
            }
        }
        return 0;
    }

    @OnClick(R.id.btn_sure)
    public void onViewClicked() {
        selectDateList.clear();
        smsto = "smsto:";
        for (int i = 0; i < sourceDateList.size(); i++) {
            if (sourceDateList.get(i).isChecked()) {
                selectDateList.add(sourceDateList.get(i));
            }
        }
        if (selectDateList.size() < 1) {
            ToastUtils.show("请选择联系人");
            return;
        }

        for (int i = 0; i < selectDateList.size(); i++) {
            if (i == selectDateList.size() - 1) {
                smsto += selectDateList.get(i).getPhone();
            } else {
                smsto += selectDateList.get(i).getPhone() + ";";
            }
        }
        Uri uri = Uri.parse(smsto);
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        String smsContent = "我在【优工连】赚零花钱，快来一起关注吧，打开链接"+shareUrl+"输入手机号确定师徒关系，并下载注册【优工连APP】，登陆后赶紧去赚取积分吧，积分可变现呦！【链接地址】";
        it.putExtra("sms_body", smsContent);
        startActivity(it);
    }

}
