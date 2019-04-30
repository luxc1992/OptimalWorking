package com.yst.onecity.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.yst.basic.framework.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseFragment;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.onecity.R;
import com.yst.onecity.activity.home.InformationDetailActivity;
import com.yst.onecity.adapter.MyCollectInformationAdapter;
import com.yst.onecity.adapter.MyIssueInformationAdapter;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.home.InformationBean;
import com.yst.onecity.bean.mine.MyCollectInformationBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.view.AbstractCommonDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * 首页咨询列表
 *
 * @author chenjiadi
 * @version 1.0.1
 * @date 2018/2/6
 */
public class InformationFragmrnt extends BaseFragment implements OnRefreshListener, OnLoadmoreListener {
    @BindView(R.id.lv_information)
    ListView lvInformation;
    @BindView(R.id.smartRefreshLayout_information)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.empty)
    TextView empty;

    /**
     * 判断是哪个页面加载该fragment  1我的收藏  0我的发布
     */
    private int type;
    /**
     * 加载页数
     */
    private int page = 1;
    private List<InformationBean.ContentBean> myIssue = new ArrayList<>();
    private MyIssueInformationAdapter myIssueInformationAdapter;
    private MyCollectInformationAdapter myCollectInformationAdapter;
    private List<MyCollectInformationBean.ContentBean> myCollect = new ArrayList<>();
    private AbstractCommonDialog dialog;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_information;
    }

    @Override
    public void init() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.containsKey("type") ? bundle.getInt("type") : 0;
        }
        //listview自动刷新
        smartRefreshLayout.autoRefresh(500);
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setOnLoadmoreListener(this);
        smartRefreshLayout.setEnableRefresh(true);
        smartRefreshLayout.setEnableLoadmore(true);
        //点击条目跳转到资讯详情页
        lvInformation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (myIssue != null && myIssue.size() > 0) {
                    Bundle b = new Bundle();
                    b.putString("id", myIssue.get(i).getId() + "");
                    JumpIntent.jump(getActivity(), InformationDetailActivity.class, b);
                } else if (myCollect != null && myCollect.size() > 0) {
                    Bundle b = new Bundle();
                    b.putString("id", myCollect.get(i).getId() + "");
                    JumpIntent.jump(getActivity(), InformationDetailActivity.class, b);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        page = 1;
        if (myCollect != null) {
            myCollect.clear();
        }
        if (myIssue != null) {
            myIssue.clear();
        }
        getInformation();
    }

    /**
     * 初始化数据
     */
    private void getInformation() {
        if (type == 0) {
            RequestApi.myIssueInformation("0", App.manager.getUuid(), App.manager.getPhoneNum(), page + "", 5 + "", new AbstractNetWorkCallback<InformationBean>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    dismissInfoProgressDialog();
                }

                @Override
                public void onBefore() {
                    super.onBefore();
                    showInfoProgressDialog();
                }

                @Override
                public void onSuccess(InformationBean informationBean) {
                    onLoad();
                    if (informationBean != null && informationBean.getCode() == 1) {
                        if (page == 1) {
                            myIssue = informationBean.getContent();
                        } else {
                            if (informationBean.getContent() != null) {
                                myIssue.addAll(informationBean.getContent());
                            } else {
                                ToastUtils.show("已加载全部数据");
                            }

                        }
                        setMyIssueAdapter();
                    } else {
                        if (page == 1) {
                            myIssue = new ArrayList<>();
                        } else {
                            ToastUtils.show("已加载全部数据");
                        }
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    onLoad();
                    ToastUtils.show(errorMsg);
                }
            });
        } else if (type == 1) {
            RequestApi.myCollectInformation(App.manager.getPhoneNum(), App.manager.getUuid(), page + "", 5 + "", new AbstractNetWorkCallback<MyCollectInformationBean>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    dismissInfoProgressDialog();
                }

                @Override
                public void onBefore() {
                    super.onBefore();
                    showInfoProgressDialog();
                }

                @Override
                public void onSuccess(MyCollectInformationBean informationBean) {
                    onLoad();
                    if (informationBean != null && informationBean.getCode() == 1) {
                        if (page == 1) {
                            myCollect = informationBean.getContent();
                        } else {
                            if (informationBean.getContent() != null) {
                                myCollect.addAll(informationBean.getContent());
                            } else {
                                ToastUtils.show("已加载全部数据");
                            }
                        }
                        setMyCollectAdapter();
                    } else {
                        if (page == 1) {
                            myCollect = new ArrayList<>();
                        } else {
                            ToastUtils.show("已加载全部数据");
                        }
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    onLoad();
                    ToastUtils.show(errorMsg);
                }
            });
        }


    }

    /**
     * 刷新我的发布资讯适配器
     */
    private void setMyIssueAdapter() {
        if (lvInformation == null) {
            return;
        }
        if (myIssueInformationAdapter == null) {
            myIssueInformationAdapter = new MyIssueInformationAdapter(getActivity(), myIssue, myOnClick);
            lvInformation.setAdapter(myIssueInformationAdapter);
        } else {
            myIssueInformationAdapter.setMyIssueDate(myIssue);
        }
    }

    /**
     * 刷新我的收藏资讯适配器
     */
    private void setMyCollectAdapter() {
        if (lvInformation == null) {
            return;
        }
        if (myCollectInformationAdapter == null) {
            myCollectInformationAdapter = new MyCollectInformationAdapter(getActivity(), myCollect, myOnClick);
            lvInformation.setAdapter(myCollectInformationAdapter);
        } else {
            myCollectInformationAdapter.setMyCollectDate(myCollect);
        }
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        getInformation();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page = 1;
        if (myIssue != null) {
            myIssue.clear();
            getInformation();
        }
        if (myCollect != null) {
            myCollect.clear();
            getInformation();
        }

    }

    /**
     * 停止列表刷新的
     **/
    private void onLoad() {
        smartRefreshLayout.finishRefresh(500);
        smartRefreshLayout.finishLoadmore(500);
    }

    /**
     * 取消收藏
     */
    private void unAttention(String id) {
        RequestApi.collectInformation(id, "5", App.manager.getUuid(), App.manager.getPhoneNum(), new AbstractNetWorkCallback<MsgBean>() {
            @Override
            public void onSuccess(MsgBean msgBean) {
                if (msgBean.getCode() == 1) {
                    ToastUtils.show(msgBean.getMsg());
                    page = 1;
                    getInformation();
                } else {
                    ToastUtils.show(msgBean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    /**
     * 删除资讯
     */
    private void deleteInformation(String id) {
        RequestApi.myIssueDeleteInformation(id, App.manager.getUuid(), App.manager.getPhoneNum(), new AbstractNetWorkCallback<MsgBean>() {
            @Override
            public void onSuccess(MsgBean msgBean) {
                if (msgBean.getCode() == 1) {
                    ToastUtils.show(msgBean.getMsg());
                    myIssue.clear();
                    page = 1;
                    getInformation();
                    dialog.dismissDialog();
                } else {
                    ToastUtils.show(msgBean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    /**
     * 删除资讯弹出框
     *
     * @param id 资讯id
     */
    public void showDialog(final String id) {
        dialog = new AbstractCommonDialog(getActivity()) {
            @Override
            public void sureClick() {
                deleteInformation(id);
                dialog.dismiss();
            }
        };
        dialog.setText("提示", "确定要删除资讯么？", "确定", "取消");
        dialog.showDialog();
    }

    /**
     * 创建一个资讯frament的实例
     *
     * @return 具体fragment
     */
    public static InformationFragmrnt newInstance(String id, int type) {
        Bundle args = new Bundle();
        args.putString("id", id);
        args.putInt("type", type);
        InformationFragmrnt fragment = new InformationFragmrnt();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 点击监听
     */
    private View.OnClickListener myOnClick = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                //我的发布资讯-单图删除
                case R.id.btn_one_product_delete:
                    showDialog((String) view.getTag());
                    break;
                //我的发布资讯-两图删除
                case R.id.btn_two_product_delete:
                    showDialog((String) view.getTag());
                    break;
                //我的发布资讯-三图删除
                case R.id.btn_three_product_delete:
                    showDialog((String) view.getTag());
                    break;
                //我的发布资讯-视频删除
                case R.id.btn_four_product_delete:
                    showDialog((String) view.getTag());
                    break;
                //我的收藏资讯-单图取消收藏
                case R.id.line_one_collect:
                    unAttention((String) view.getTag());
                    break;
                //我的收藏资讯-两图取消收藏
                case R.id.line_two_collect:
                    unAttention((String) view.getTag());
                    break;
                //我的收藏资讯-三图取消收藏
                case R.id.line_three_collect:
                    unAttention((String) view.getTag());
                    break;
                //我的收藏资讯-视频取消收藏
                case R.id.line_four_collect:
                    unAttention((String) view.getTag());
                    break;
                default:
                    break;
            }
        }
    };

}
