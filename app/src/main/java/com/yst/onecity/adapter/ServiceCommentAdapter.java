package com.yst.onecity.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.App;
import com.yst.basic.framework.utils.MyLog;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.utils.Utils;
import com.yst.basic.framework.view.roundedimageview.RoundedImageView;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.ServiceCommentBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.view.MyGridView;
import com.yst.onecity.view.RatingBar;
import com.yst.onecity.view.dialog.SendCommentDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yst.onecity.Constant.COMMA;
import static com.yst.onecity.Constant.NO0;

/**
 * 评价适配器
 *
 * @author wuxiaofang
 * @version 1.1.0
 * @date 2018/5/23
 */

public class ServiceCommentAdapter extends BaseAdapter implements SendCommentDialog.SendCommentListener {
    ViewHolder itemviewHolder = null;
    private Context context;
    private List<ServiceCommentBean.ContentBean.ListaBean> commmentList = new ArrayList<>();
    private String commentid;
    private String comentMemberId;
    private String returnContent;
    private String serviceId;
    private SendCommentDialog sendCommentDialog;
    private String content2;
    private int itemPoition;

    public ServiceCommentAdapter(Context context) {
        this.context = context;
    }

    public void addData(List<ServiceCommentBean.ContentBean.ListaBean> mList) {
        if (null != mList) {
            commmentList.clear();
            commmentList.addAll(mList);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        MyLog.e("sss", "--" + commmentList.size());
        return commmentList.size();
    }

    @Override
    public Object getItem(int i) {
        return commmentList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = View.inflate(context, R.layout.item_comment, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Glide.with(context).load(commmentList.get(i).getLogoAttachmentAddress()).error(R.mipmap.default_head).into(viewHolder.ivHead);
        viewHolder.ratingBar.setStar(commmentList.get(i).getStartNum());
        viewHolder.tvNickName.setText(commmentList.get(i).getNickname());
        viewHolder.tvCommentTime.setText(commmentList.get(i).getCommentTime());
        viewHolder.tvContent.setText(commmentList.get(i).getContent());
        viewHolder.tvName.setText(commmentList.get(i).getServiceTitle());
        viewHolder.tvGuige.setText(String.valueOf(commmentList.get(i).getServicePrice()));
        viewHolder.tvCreateTime.setText(Utils.getCustomStrTime(String.valueOf(commmentList.get(i).getPayTime()),"yyyy-MM-dd HH:mm:ss"));
        viewHolder.tvZanCount.setText(String.valueOf(commmentList.get(i).getFabulousNum()));
        viewHolder.tvLookCount.setText(String.valueOf(commmentList.get(i).getReadNum()));
        String returnContent = commmentList.get(i).getReturnContent();
        if (!TextUtils.isEmpty(returnContent)) {
            if (returnContent.contains(COMMA)) {
                String[] split = returnContent.split(COMMA);
                viewHolder.llReplay.removeAllViews();
                for (int i1 = 0; i1 < split.length; i1++) {
                    viewHolder.llReplay.setVisibility(View.VISIBLE);
                    View inflate = View.inflate(context, R.layout.item_return_content, null);
                    TextView tvReplay = inflate.findViewById(R.id.tv_replay);
                    tvReplay.setText("商家:" + split[i1]);
                    viewHolder.llReplay.addView(inflate);
                }
            } else {
                viewHolder.llReplay.setVisibility(View.VISIBLE);
                viewHolder.llReplay.removeAllViews();
                View inflate = View.inflate(context, R.layout.item_return_content, null);
                TextView tvReplay = inflate.findViewById(R.id.tv_replay);
                tvReplay.setText("商家:" + returnContent);
                viewHolder.llReplay.addView(inflate);
            }
        } else {
            viewHolder.llReplay.setVisibility(View.GONE);
        }
        int picture = commmentList.get(i).getIsPicture();
        /*
         * 是否含有图片 0没有 1 有
         */
        if (picture == NO0) {
            viewHolder.gvIv.setVisibility(View.GONE);
        } else if (picture == Constant.NO1) {
            if (TextUtils.isEmpty(commmentList.get(i).getImgUrl())) {
                commmentList.get(i).setImgUrl("");
            }
            viewHolder.gvIv.setVisibility(View.VISIBLE);
            ArrayList<String> strings = new ArrayList<>();
            strings.clear();
            if (commmentList.get(i).getImgUrl().contains(COMMA)) {
                String[] split = commmentList.get(i).getImgUrl().split(COMMA);
                for (int j = 0; j < split.length; j++) {
                    strings.add(split[j]);
                }
                HuDongGridAdapter adapter = new HuDongGridAdapter(strings, context);
                viewHolder.gvIv.setAdapter(adapter);
            } else {
                strings.add(commmentList.get(i).getImgUrl());
                HuDongGridAdapter adapter = new HuDongGridAdapter(strings, context);
                viewHolder.gvIv.setAdapter(adapter);
            }
        }
        viewHolder.tvReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemPoition = i;
                serviceId = String.valueOf(commmentList.get(i).getServiceId());
                commentid = String.valueOf(commmentList.get(i).getCommentId());
                comentMemberId = String.valueOf(commmentList.get(i).getCommentMemberId());
                if (TextUtils.isEmpty(commentid)) {
                    ToastUtils.show("评论内容为空");
                    return;
                }

                if (TextUtils.isEmpty(comentMemberId)) {
                    ToastUtils.show("用户id为空");
                    return;
                }
                content2 = commmentList.get(i).getContent();
                showSendCommentDialog();
            }
        });
        return view;
    }

    /**
     * 回复评论
     */
    private void replay(final String content) {

        RequestApi.replayComment(String.valueOf(App.manager.getId()), commentid,
                content,
                comentMemberId, content2
                , commentid, serviceId, App.manager.getPhoneNum(), App.manager.getUuid(), new AbstractNetWorkCallback<MsgBean>() {
                    @Override
                    public void onSuccess(MsgBean msgBean) {
                        if (null != msgBean) {
                            if (msgBean.getCode() == Constant.NO1) {
                                if (!TextUtils.isEmpty(content)) {
                                    StringBuffer sb = new StringBuffer();
                                    if ("".equals(commmentList.get(itemPoition).getReturnContent()) || null == commmentList.get(itemPoition).getReturnContent()) {
                                        sb.append(content);
                                    } else {
                                        sb.append(commmentList.get(itemPoition).getReturnContent() + "," + content);
                                    }
                                    commmentList.get(itemPoition).setReturnContent(sb.toString());
                                    notifyDataSetChanged();
                                }
                                ToastUtils.show(msgBean.getMsg());
                                sendCommentDialog.dismiss();
                            } else {
                                ToastUtils.show(msgBean.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(String errorMsg) {
                        ToastUtils.show(errorMsg);
                    }
                });
    }

    /**
     * 显示评论的弹框
     */
    private void showSendCommentDialog() {
        sendCommentDialog = new SendCommentDialog((Activity) context);
        sendCommentDialog.show();
        sendCommentDialog.commentListener(this);
    }

    @Override
    public void addComment(String content) {
        replay(content);
    }

    class ViewHolder {
        @BindView(R.id.iv_head)
        RoundedImageView ivHead;
        @BindView(R.id.tv_nick_name)
        TextView tvNickName;
        @BindView(R.id.tv_dafen)
        TextView tvDafen;
        @BindView(R.id.ratingBar)
        RatingBar ratingBar;
        @BindView(R.id.tv_comment_time)
        TextView tvCommentTime;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.gv_iv)
        MyGridView gvIv;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_guige)
        TextView tvGuige;
        @BindView(R.id.tv_create_time)
        TextView tvCreateTime;
        @BindView(R.id.tv_look_count)
        TextView tvLookCount;
        @BindView(R.id.tv_zan_count)
        TextView tvZanCount;
        @BindView(R.id.ll_replay)
        LinearLayout llReplay;
        @BindView(R.id.tv_replay)
        TextView tvReplay;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
