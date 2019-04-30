package com.yst.onecity.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.MyLog;
import com.yst.onecity.R;
import com.yst.onecity.adapter.SpeciallyPersonListAdapter;
import com.yst.onecity.bean.ChangeSpeciallyPersonBean;
import com.yst.onecity.bean.ContactsBean;
import com.yst.onecity.bean.SpeciallyPersonPhoneBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.ContactUtils;
import com.yst.onecity.view.ContainsEmojiEditText;
import com.yst.onecity.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO10;
import static com.yst.onecity.Constant.NO100;

/**
 * 专员列表页面
 *
 * @author chejianqi
 * @version 1.0.1
 * @date 2018/2/24
 */

public class SpeciallyPersonListActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.phone_list)
    MyListView phoneListView;
    @BindView(R.id.other_list)
    MyListView otherListView;
    @BindView(R.id.et_search)
    ContainsEmojiEditText etSearch;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.img_search)
    ImageView imgSearch;
    @BindView(R.id.search_list)
    ListView searchListView;
    @BindView(R.id.no_data)
    ImageView noData;
    @BindView(R.id.rl_search_list)
    RelativeLayout rlSearchList;
    @BindView(R.id.sv_list)
    ScrollView svList;
    @BindView(R.id.no_data_sv)
    ImageView noDataSv;
    private ArrayList<SpeciallyPersonPhoneBean.ContentBean> phoneList = new ArrayList<>();
    private String phoneNum = "";
    private SpeciallyPersonListAdapter phoneAdapter, otherAdapter, searchAdapter;
    private ArrayList<SpeciallyPersonPhoneBean.ContentBean> otherList = new ArrayList<>();
    private ArrayList<SpeciallyPersonPhoneBean.ContentBean> searchList = new ArrayList<>();
    private ArrayList<SpeciallyPersonPhoneBean.ContentBean> flagList = new ArrayList<>();
    @Override
    public int getLayoutId() {
        return R.layout.activity_speciallyperson_list;
    }

    @Override
    public void initData() {
        initTitleBar("专员列表");
        setRightText("确定");
        List<ContactsBean> systemContactInfo = ContactUtils.getSystemContactInfos(SpeciallyPersonListActivity.this);
        for (int i = 0; i < systemContactInfo.size(); i++) {
            ContactsBean bean = systemContactInfo.get(i);
            if (i == systemContactInfo.size() - 1) {
                phoneNum += bean.getPhoneNumber();
            } else {
                phoneNum += bean.getPhoneNumber() + ",";
            }
        }
        initAdapter();
        initClick();
        getOtherSpeciallyList();
        getContactList();
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    svList.setVisibility(View.GONE);
                    rlSearchList.setVisibility(View.VISIBLE);
                    searchPersonList(editable.toString().trim());
                    tvSearch.setVisibility(View.GONE);
                    imgSearch.setVisibility(View.GONE);
                } else {
                    svList.setVisibility(View.VISIBLE);
                    rlSearchList.setVisibility(View.GONE);
                    tvSearch.setVisibility(View.VISIBLE);
                    imgSearch.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        phoneAdapter = new SpeciallyPersonListAdapter(phoneList, this);
        otherAdapter = new SpeciallyPersonListAdapter(otherList, this);
        searchAdapter = new SpeciallyPersonListAdapter(searchList, this);
        phoneListView.setAdapter(phoneAdapter);
        otherListView.setAdapter(otherAdapter);
        searchListView.setAdapter(searchAdapter);
    }

    /**
     * 初始化点击事件
     */
    private void initClick() {
        phoneListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                for (int i = 0; i < otherList.size(); i++) {
                    otherList.get(i).setIscheck(false);
                }
                for (int i = 0; i < phoneList.size(); i++) {
                    phoneList.get(i).setIscheck(false);
                }
                phoneList.get(position).setIscheck(true);
                otherAdapter.notifyDataSetChanged();
                phoneAdapter.notifyDataSetChanged();
            }
        });
        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                for (int i = 0; i < searchList.size(); i++) {
                    searchList.get(i).setIscheck(false);
                }
                searchList.get(position).setIscheck(true);
                searchAdapter.notifyDataSetChanged();
            }
        });
        otherListView.setOnItemClickListener(this);
        setRightTextViewOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flagList.clear();
                //遍历通讯录好友
                for (int i = 0; i < phoneList.size(); i++) {
                    if (phoneList.get(i).ischeck()) {
                        flagList.add(phoneList.get(i));
                        RequestApi.changeSpeciallyPerson(App.manager.getPhoneNum(), App.manager.getUuid(), String.valueOf(phoneList.get(i).getId()), new AbstractNetWorkCallback<ChangeSpeciallyPersonBean>() {
                            @Override
                            public void onSuccess(ChangeSpeciallyPersonBean changeSpeciallyPersonBean) {
                                if (changeSpeciallyPersonBean.getCode() == NO1) {
                                    SpeciallyPersonListActivity.this.setResult(NO100 * NO10);
                                    SpeciallyPersonListActivity.this.finish();
                                }
                                ToastUtils.show(changeSpeciallyPersonBean.getMsg());
                            }

                            @Override
                            public void onError(String errorMsg) {
                                ToastUtils.show(errorMsg);
                            }
                        });

                    }
                }
                //遍历其他服务专员
                for (int i = 0; i < otherList.size(); i++) {
                    if (otherList.get(i).ischeck()) {
                        flagList.add(otherList.get(i));
                        RequestApi.changeSpeciallyPerson(App.manager.getPhoneNum(), App.manager.getUuid(), String.valueOf(otherList.get(i).getId()), new AbstractNetWorkCallback<ChangeSpeciallyPersonBean>() {
                            @Override
                            public void onSuccess(ChangeSpeciallyPersonBean changeSpeciallyPersonBean) {
                                if (changeSpeciallyPersonBean.getCode() == NO1) {
                                    SpeciallyPersonListActivity.this.finish();
                                }
                                ToastUtils.show(changeSpeciallyPersonBean.getMsg());
                            }

                            @Override
                            public void onError(String errorMsg) {
                                ToastUtils.show(errorMsg);
                            }
                        });
                    }
                }
                //遍历搜索的好友
                for (int i = 0; i < searchList.size(); i++) {
                    if (otherList.get(i).ischeck()) {
                        flagList.add(otherList.get(i));
                        RequestApi.changeSpeciallyPerson(App.manager.getPhoneNum(), App.manager.getUuid(), String.valueOf(searchList.get(i).getId()), new AbstractNetWorkCallback<ChangeSpeciallyPersonBean>() {
                            @Override
                            public void onSuccess(ChangeSpeciallyPersonBean changeSpeciallyPersonBean) {
                                if (changeSpeciallyPersonBean.getCode() == NO1) {
                                    SpeciallyPersonListActivity.this.finish();
                                }
                                ToastUtils.show(changeSpeciallyPersonBean.getMsg());
                            }

                            @Override
                            public void onError(String errorMsg) {
                                ToastUtils.show(errorMsg);
                            }
                        });
                    }
                }
                if (flagList.size()==0){
                    ToastUtils.show("请选择专员");

                }
            }
        });
    }

    /**
     * 匹配通讯录好友列表
     */
    private void getContactList() {
        phoneList.clear();
        phoneAdapter.notifyDataSetChanged();
        RequestApi.getContact(phoneNum, new AbstractNetWorkCallback<SpeciallyPersonPhoneBean>() {
            @Override
            public void onBefore() {
                super.onBefore();
                showInfoProgressDialog();
            }

            @Override
            public void onSuccess(SpeciallyPersonPhoneBean speciallyPersonPhoneBean) {
                if (speciallyPersonPhoneBean.getCode() == 1) {
                    phoneList.addAll(speciallyPersonPhoneBean.getContent());
                }
                phoneAdapter.notifyDataSetChanged();
            }

            @Override
            public void onAfter() {
                super.onAfter();
                dismissInfoProgressDialog();
            }

            @Override
            public void onError(String errorMsg) {
                MyLog.e("onError", errorMsg);
                dismissInfoProgressDialog();
            }
        });
    }

    /**
     * 其他服务专员列表
     */
    private void getOtherSpeciallyList() {
        otherList.clear();
        otherAdapter.notifyDataSetChanged();
        RequestApi.getOtherSpeciallyList(new AbstractNetWorkCallback<SpeciallyPersonPhoneBean>() {
            @Override
            public void onSuccess(SpeciallyPersonPhoneBean speciallyPersonPhoneBean) {
                if (speciallyPersonPhoneBean.getCode() == 1) {
                    otherList.addAll(speciallyPersonPhoneBean.getContent());

                }
                otherAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String errorMsg) {
                MyLog.e("onError", errorMsg);
            }
        });
    }

    /**
     * 查询专员
     *
     * @param param 手机号或昵称
     */
    private void searchPersonList(String param) {
        RequestApi.searchPerson(param, new AbstractNetWorkCallback<SpeciallyPersonPhoneBean>() {
            @Override
            public void onSuccess(SpeciallyPersonPhoneBean speciallyPersonPhoneBean) {
                if (speciallyPersonPhoneBean.getCode() == 1) {
                    searchList.clear();
                    searchList.addAll(speciallyPersonPhoneBean.getContent());
                    searchAdapter.notifyDataSetChanged();
                    if (speciallyPersonPhoneBean.getContent().size() > 0) {
                        noData.setVisibility(View.GONE);
                    } else {
                        noData.setVisibility(View.VISIBLE);
                    }
                }

            }

            @Override
            public void onError(String errorMsg) {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        for (int i = 0; i < otherList.size(); i++) {
            otherList.get(i).setIscheck(false);
        }
        for (int i = 0; i < phoneList.size(); i++) {
            phoneList.get(i).setIscheck(false);
        }
        otherList.get(position).setIscheck(true);
        otherAdapter.notifyDataSetChanged();
        phoneAdapter.notifyDataSetChanged();
    }
}
