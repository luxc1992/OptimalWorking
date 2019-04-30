package com.yst.onecity.fragment.share;

import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.App;
import com.yst.basic.framework.adapter.AbstractCommonAdapter;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.entity.holder.CommonViewHolder;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.view.roundedimageview.RoundedImageView;
import com.yst.onecity.R;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.WatchAllReplyBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.view.MyListView;
import com.yst.onecity.view.dialog.SendCommentDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yst.onecity.Constant.NO1;

/**
 * 查看全部回复
 *
 * @author luxuchang
 * @version 1.1.0
 * @date 2018/5/26
 */
public class WatchReplayListActivity extends BaseActivity {
    @BindView(R.id.iv_comment_person_avatar)
    RoundedImageView ivCommentPersonAvatar;
    @BindView(R.id.tv_nick_name)
    TextView tvNickName;
    @BindView(R.id.tv_comment_total_num)
    TextView tvCommentTotalNum;
    @BindView(R.id.tv_comment_content)
    TextView tvCommentContent;
    @BindView(R.id.tv_comment_publish_time)
    TextView tvCommentPublishTime;
    @BindView(R.id.btn_reply)
    TextView btnReply;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.tv_like_num)
    TextView tvLikeNum;
    @BindView(R.id.lv_all_reply)
    MyListView lvAllReply;
    @BindView(R.id.end)
    TextView end;
    @BindView(R.id.icon)
    RoundedImageView icon;
    @BindView(R.id.ll_bottom_view)
    RelativeLayout llBottomView;

    private AbstractCommonAdapter<WatchAllReplyBean.ReplyListBean> adapter;
    private List<WatchAllReplyBean.ReplyListBean> list = new ArrayList<>();
    private String commentId;
    private String consultationId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_watch_reply_list;
    }

    @Override
    public void initData() {

        if (getIntent() != null){
            commentId = getIntent().getStringExtra("commentId");
            consultationId = getIntent().getStringExtra("consultationId");
        }

        initTitleBar("评论区");
        requestData();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        adapter = new AbstractCommonAdapter<WatchAllReplyBean.ReplyListBean>(WatchReplayListActivity.this, list, R.layout.item_comment_layout) {
            @Override
            public void convert(CommonViewHolder holder, int position, WatchAllReplyBean.ReplyListBean item) {
                holder.setText(R.id.tv_comment_name, item.getNickName());
                holder.setText(R.id.tv_comment_content, item.getContent());
                holder.setText(R.id.tv_comment_time, item.getCreateTime());
                holder.setText(R.id.tv_comment_count, item.getFabulousNum());
                Glide.with(context).load(item.getLogoAddress()).error(R.mipmap.default_head).into((ImageView) holder.getView(R.id.iv_comment_head));
            }
        };

        lvAllReply.setAdapter(adapter);
    }

    /**
     * 请求数据
     */
    private void requestData() {
        list.clear();
        RequestApi.getConsultationReplyCommentMessage(App.manager.getPhoneNum(), App.manager.getUuid(), commentId, consultationId, new AbstractNetWorkCallback<WatchAllReplyBean>() {
            @Override
            public void onSuccess(WatchAllReplyBean watchAllReplyBean) {
                if (watchAllReplyBean.getCode() == NO1) {
                    Glide.with(context).load(watchAllReplyBean.getLogoAddress()).error(R.mipmap.default_head).into(ivCommentPersonAvatar);
                    Glide.with(context).load(watchAllReplyBean.getLogoAddress()).error(R.mipmap.default_head).into(icon);
                    tvNickName.setText(watchAllReplyBean.getNickName());
                    tvCommentTotalNum.setText(String.format(getString(R.string.comment_num), String.valueOf(watchAllReplyBean.getBCount())));
                    tvCommentContent.setText(ConstUtils.getStringNoEmpty(watchAllReplyBean.getContent()));
                    tvCommentPublishTime.setText(ConstUtils.getStringNoEmpty(watchAllReplyBean.getCreateTime()));
                    tvLikeNum.setText(ConstUtils.changeEmptyStringToZero(String.valueOf(watchAllReplyBean.getFabulousNum())));

                    if (watchAllReplyBean.getReplyList() != null) {
                        list.addAll(watchAllReplyBean.getReplyList());
                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }


    @OnClick({R.id.btn_reply, R.id.btn_publish_reply})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_reply:
            case R.id.btn_publish_reply:
                SendCommentDialog pDialog = new SendCommentDialog(this, "发布");
                pDialog.commentListener(new SendCommentDialog.SendCommentListener() {
                    @Override
                    public void addComment(String content) {
                        if (TextUtils.isEmpty(content)) {
                            ToastUtils.show("请输入回复内容");
                        }
                        RequestApi.replyConsultationComment(App.manager.getPhoneNum(), App.manager.getUuid(),commentId, content,  new AbstractNetWorkCallback<MsgBean>() {
                            @Override
                            public void onBefore() {
                                super.onBefore();
                                showInfoProgressDialog();
                            }

                            @Override
                            public void onAfter() {
                                super.onAfter();
                                dismissInfoProgressDialog();
                            }

                            @Override
                            public void onSuccess(MsgBean msgBean) {
                                if (msgBean.getCode() == NO1) {
                                    requestData();
                                }
                            }

                            @Override
                            public void onError(String errorMsg) {
                                ToastUtils.show(errorMsg);
                            }
                        });
                    }
                });
                pDialog.show();
                break;
            default:
                break;
        }
    }
}
