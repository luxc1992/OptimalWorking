package com.yst.onecity.activity.mine;

import android.text.TextUtils;
import android.widget.TextView;

import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.onecity.R;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.mine.GroupInfoBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.view.AbstractCommonDialog;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yst.onecity.Constant.NO1;

/**
 * 服务专员中心页面
 *
 * @author luxuchang
 * @version 1.0.1
 * @date 2018/2/24.
 */

public class ServerCenterActivity extends BaseActivity {
    @BindView(R.id.tv_my_group_chat)
    TextView tvMyGroupChat;

    @Override
    public int getLayoutId() {
        return R.layout.activity_server_center;
    }

    @Override
    public void initData() {
        initTitleBar("服务专员中心");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(App.manager.getChatGroup())) {
            tvMyGroupChat.setText(App.manager.getChatGroup());
        }
        RequestApi.checkMemberIsHunter(new AbstractNetWorkCallback<GroupInfoBean>() {
            @Override
            public void onSuccess(GroupInfoBean bean) {
                if (bean.getCode() == NO1) {
                    App.manager.setGroupId(bean.getContent());
                } else {
                    ToastUtils.show(bean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    @OnClick(R.id.tv_my_group_chat)
    public void onViewClicked() {

        if (TextUtils.isEmpty(App.manager.getChatGroup())) {
            AbstractCommonDialog dialog = new AbstractCommonDialog(this) {
                @Override
                public void sureClick() {
                    if (!TextUtils.isEmpty(getEditTextContent())) {
                        RequestApi.updateChatGroupName(getEditTextContent(), new AbstractNetWorkCallback<MsgBean>() {
                            @Override
                            public void onSuccess(MsgBean msgBean) {
                                if (msgBean.getCode() == NO1) {
                                    ToastUtils.show(msgBean.getMsg());
                                    App.manager.setChatGroup(getEditTextContent());
                                    tvMyGroupChat.setText(getEditTextContent());
                                    dismissDialog();
                                } else {
                                    ToastUtils.show(msgBean.getMsg());
                                }
                            }

                            @Override
                            public void onError(String errorMsg) {
                                ToastUtils.show(errorMsg);
                            }
                        });
                    } else {
                        ToastUtils.show("请输入群聊名称");
                    }
                }
            };
            dialog.setText("请输入群聊名称", null, "确定", "取消");
            dialog.showDialog();
        } else {
//            GroupChatEntity.ContentBean dataBean = new GroupChatEntity.ContentBean();
//            dataBean.setGroupName(TextUtils.isEmpty(App.manager.getChatGroup()) ? "" : App.manager.getChatGroup());
//            dataBean.setId(Integer.parseInt(String.valueOf(App.manager.getGroupId())));
//            dataBean.setShowRedPoint(false);
//            ChatScreenActivity.getJumpChatSource(this, ChatType.GROUP, true, null, dataBean);
        }
    }
}
