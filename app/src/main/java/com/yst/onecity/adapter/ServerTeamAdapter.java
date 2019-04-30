package com.yst.onecity.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.App;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.WindowUtils;
import com.yst.im.imchatlibrary.bean.IntentChatEntity;
import com.yst.im.imchatlibrary.ui.activity.ChatScreenActivity;
import com.yst.im.imsdk.ChatType;
import com.yst.onecity.H5Const;
import com.yst.onecity.R;
import com.yst.onecity.activity.h5detail.ServerTeamPageDetailActivity;
import com.yst.onecity.activity.login.LoginActivity;
import com.yst.onecity.bean.ServerTeamListBean;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.view.RatingBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yst.onecity.Constant.NO2;
import static com.yst.onecity.Constant.NO3;

/**
 * 服务团队适配器
 *
 * @author chenjiadi
 * @version 1.1.0
 * @date 2018/5/16.
 */

public class ServerTeamAdapter extends BaseAdapter {
    private Activity mContext;
    private List<ServerTeamListBean.ContentBean> goodsBeanList;
    private Animation mShowAction;

    public ServerTeamAdapter(Activity mContext, List<ServerTeamListBean.ContentBean> goodsBeanList) {
        this.mContext = mContext;
        this.goodsBeanList = goodsBeanList;
        initAnimationsOne();
    }

    public void setServerTeamList(List<ServerTeamListBean.ContentBean> goodsBeanList) {
        if (goodsBeanList != null) {
            this.goodsBeanList = goodsBeanList;
            notifyDataSetChanged();
        }
        initAnimationsOne();
    }

    @Override
    public int getCount() {
        return goodsBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return goodsBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_server_team_layout, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (goodsBeanList.get(position).getAddress() != null) {
            Glide.with(App.mContext).load(goodsBeanList.get(position).getAddress()).error(R.mipmap.default_product_icon).into(viewHolder.ivServerName);
        }
        viewHolder.ratingBar.setClickable(false);
        ConstUtils.setTextString(goodsBeanList.get(position).getNikename(), viewHolder.tvName);
        ConstUtils.setTextString("智能筛选 " + goodsBeanList.get(position).getDistance(), viewHolder.tvServerDistance);
        viewHolder.ratingBar.setStar(goodsBeanList.get(position).getStar_num());
        ConstUtils.setTextString(goodsBeanList.get(position).getContent(), viewHolder.tvDesc);
        int type = goodsBeanList.get(position).getCaseList().size();
        switch (type) {
            case 0:
                viewHolder.llFirstServerCase.setVisibility(View.GONE);
                viewHolder.llSecondServerCase.setVisibility(View.GONE);
                viewHolder.llThirdServerCase.setVisibility(View.GONE);
                viewHolder.tvCheckMoreCase.setVisibility(View.GONE);
                break;
            //一个案例
            case 1:
                viewHolder.tvCheckMoreCase.setVisibility(View.VISIBLE);
                viewHolder.llFirstServerCase.setVisibility(View.VISIBLE);
                viewHolder.llSecondServerCase.setVisibility(View.GONE);
                viewHolder.llThirdServerCase.setVisibility(View.GONE);

                if (goodsBeanList.get(position).getCaseList().size() == 1 && goodsBeanList.get(position).getCaseList().get(0) != null) {
                    final ServerTeamListBean.ContentBean.CaseListBean caseListBean = goodsBeanList.get(position).getCaseList().get(0);
                    viewHolder.llFirstServerCase.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //案例详情
                            Bundle bundle = new Bundle();
                            bundle.putString("id", String.valueOf(caseListBean.getCaseId()));
                            bundle.putString("url", H5Const.CASE_DETAIL);
                            JumpIntent.jump(mContext, ServerTeamPageDetailActivity.class, bundle);
                        }
                    });
                    ConstUtils.setTextString(caseListBean.getTitle(), viewHolder.tvFirstServerCaseName);
                    if (caseListBean.getAddress() != null) {
                        Glide.with(App.mContext).load(caseListBean.getAddress()).error(R.mipmap.default_product_icon).into(viewHolder.ivFirstServerCase);
                    }
                }
                break;
            //两个案例
            case 2:
                viewHolder.tvCheckMoreCase.setVisibility(View.VISIBLE);
                viewHolder.llFirstServerCase.setVisibility(View.VISIBLE);
                viewHolder.llSecondServerCase.setVisibility(View.VISIBLE);
                viewHolder.llThirdServerCase.setVisibility(View.GONE);

                final ViewHolder finalViewHolder = viewHolder;
                viewHolder.ivSecondDown.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(finalViewHolder.llSecondServerCase.getLayoutParams());
                        lp.setMargins(0, WindowUtils.dip2px(mContext, 140), 0, 0);
                        finalViewHolder.llSecondServerCase.setLayoutParams(lp);
                        finalViewHolder.llSecondServerCase.startAnimation(mShowAction);
                        finalViewHolder.ivSecondDown.setVisibility(View.GONE);
                        finalViewHolder.ivSecondUp.setVisibility(View.VISIBLE);
                        finalViewHolder.ivThirdDown.setVisibility(View.GONE);
                        finalViewHolder.ivThirdUp.setVisibility(View.GONE);
                    }
                });
                viewHolder.ivSecondUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(finalViewHolder.llSecondServerCase.getLayoutParams());
                        lp.setMargins(0, WindowUtils.dip2px(mContext, 75), 0, 0);
                        finalViewHolder.llSecondServerCase.setLayoutParams(lp);
                        finalViewHolder.ivSecondDown.setVisibility(View.VISIBLE);
                        finalViewHolder.ivSecondUp.setVisibility(View.GONE);
                        finalViewHolder.ivThirdDown.setVisibility(View.GONE);
                        finalViewHolder.ivThirdUp.setVisibility(View.GONE);
                    }
                });
                //第一个案例
                if (goodsBeanList.get(position).getCaseList().size() == NO2 && goodsBeanList.get(position).getCaseList().get(0) != null) {
                    final ServerTeamListBean.ContentBean.CaseListBean caseListBean = goodsBeanList.get(position).getCaseList().get(0);
                    viewHolder.llFirstServerCase.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //案例详情
                            Bundle bundle = new Bundle();
                            bundle.putString("id", String.valueOf(caseListBean.getCaseId()));
                            bundle.putString("url", H5Const.CASE_DETAIL);
                            JumpIntent.jump(mContext, ServerTeamPageDetailActivity.class, bundle);
                        }
                    });
                    ConstUtils.setTextString(caseListBean.getTitle(), viewHolder.tvFirstServerCaseName);
                    if (caseListBean.getAddress() != null) {
                        Glide.with(App.mContext).load(caseListBean.getAddress()).error(R.mipmap.default_product_icon).into(viewHolder.ivFirstServerCase);
                    }
                }
                //第二个案例
                if (goodsBeanList.get(position).getCaseList().size() == NO2 && goodsBeanList.get(position).getCaseList().get(1) != null) {
                    final ServerTeamListBean.ContentBean.CaseListBean caseListBean = goodsBeanList.get(position).getCaseList().get(1);
                    viewHolder.llSecondServerCase.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //案例详情
                            Bundle bundle = new Bundle();
                            bundle.putString("id", String.valueOf(caseListBean.getCaseId()));
                            bundle.putString("url", H5Const.CASE_DETAIL);
                            JumpIntent.jump(mContext, ServerTeamPageDetailActivity.class, bundle);
                        }
                    });
                    ConstUtils.setTextString(caseListBean.getTitle(), viewHolder.tvSecondServerCaseName);
                    if (caseListBean.getAddress() != null) {
                        Glide.with(App.mContext).load(caseListBean.getAddress()).error(R.mipmap.default_product_icon).into(viewHolder.ivSecondServerCase);
                    }
                }

                break;
            //三个案例
            case 3:
                viewHolder.tvCheckMoreCase.setVisibility(View.VISIBLE);
                viewHolder.llFirstServerCase.setVisibility(View.VISIBLE);
                viewHolder.llSecondServerCase.setVisibility(View.VISIBLE);
                viewHolder.llThirdServerCase.setVisibility(View.VISIBLE);
                final ViewHolder finalViewHolder1 = viewHolder;
                viewHolder.ivThirdDown.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(finalViewHolder1.llSecondServerCase.getLayoutParams());
                        lp.setMargins(0, WindowUtils.dip2px(mContext, 140), 0, 0);
                        finalViewHolder1.llSecondServerCase.setLayoutParams(lp);
                        finalViewHolder1.llSecondServerCase.startAnimation(mShowAction);
                        finalViewHolder1.ivSecondDown.setVisibility(View.GONE);
                        finalViewHolder1.ivSecondUp.setVisibility(View.GONE);
                        RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(finalViewHolder1.llThirdServerCase.getLayoutParams());
                        lps.setMargins(0, WindowUtils.dip2px(mContext, 280), 0, 0);
                        finalViewHolder1.llThirdServerCase.setLayoutParams(lps);
                        finalViewHolder1.llThirdServerCase.startAnimation(mShowAction);
                        finalViewHolder1.ivThirdDown.setVisibility(View.GONE);
                        finalViewHolder1.ivThirdUp.setVisibility(View.VISIBLE);
                    }
                });
                viewHolder.ivThirdUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(finalViewHolder1.llSecondServerCase.getLayoutParams());
                        lp.setMargins(0, WindowUtils.dip2px(mContext, 75), 0, 0);
                        finalViewHolder1.llSecondServerCase.setLayoutParams(lp);
                        finalViewHolder1.ivSecondDown.setVisibility(View.GONE);
                        finalViewHolder1.ivSecondUp.setVisibility(View.GONE);
                        RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(finalViewHolder1.llThirdServerCase.getLayoutParams());
                        lps.setMargins(0, WindowUtils.dip2px(mContext, 140), 0, 0);
                        finalViewHolder1.llThirdServerCase.setLayoutParams(lps);
                        finalViewHolder1.ivThirdDown.setVisibility(View.VISIBLE);
                        finalViewHolder1.ivThirdUp.setVisibility(View.GONE);
                    }
                });
                //第一个案例
                if (goodsBeanList.get(position).getCaseList().size() == NO3 && goodsBeanList.get(position).getCaseList().get(0) != null) {
                    final ServerTeamListBean.ContentBean.CaseListBean caseListBean = goodsBeanList.get(position).getCaseList().get(0);
                    viewHolder.llFirstServerCase.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //案例详情
                            Bundle bundle = new Bundle();
                            bundle.putString("id", String.valueOf(caseListBean.getCaseId()));
                            bundle.putString("url", H5Const.CASE_DETAIL);
                            JumpIntent.jump(mContext, ServerTeamPageDetailActivity.class, bundle);
                        }
                    });
                    ConstUtils.setTextString(caseListBean.getTitle(), viewHolder.tvFirstServerCaseName);
                    if (caseListBean.getAddress() != null) {
                        Glide.with(App.mContext).load(caseListBean.getAddress()).error(R.mipmap.default_product_icon).into(viewHolder.ivFirstServerCase);
                    }
                }
                //第二个案例
                if (goodsBeanList.get(position).getCaseList().size() == NO3 && goodsBeanList.get(position).getCaseList().get(1) != null) {
                    final ServerTeamListBean.ContentBean.CaseListBean caseListBean = goodsBeanList.get(position).getCaseList().get(1);
                    viewHolder.llSecondServerCase.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //案例详情
                            Bundle bundle = new Bundle();
                            bundle.putString("id", String.valueOf(caseListBean.getCaseId()));
                            bundle.putString("url", H5Const.CASE_DETAIL);
                            JumpIntent.jump(mContext, ServerTeamPageDetailActivity.class, bundle);
                        }
                    });
                    ConstUtils.setTextString(caseListBean.getTitle(), viewHolder.tvSecondServerCaseName);
                    if (caseListBean.getAddress() != null) {
                        Glide.with(App.mContext).load(caseListBean.getAddress()).error(R.mipmap.default_product_icon).into(viewHolder.ivSecondServerCase);
                    }
                }
                //第三个案例
                if (goodsBeanList.get(position).getCaseList().size() == NO3 && goodsBeanList.get(position).getCaseList().get(NO2) != null) {
                    final ServerTeamListBean.ContentBean.CaseListBean caseListBean = goodsBeanList.get(position).getCaseList().get(2);
                    viewHolder.llThirdServerCase.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //案例详情
                            Bundle bundle = new Bundle();
                            bundle.putString("id", String.valueOf(caseListBean.getCaseId()));
                            bundle.putString("url", H5Const.CASE_DETAIL);
                            JumpIntent.jump(mContext, ServerTeamPageDetailActivity.class, bundle);
                        }
                    });
                    ConstUtils.setTextString(caseListBean.getTitle(), viewHolder.tvThirdServerCaseName);
                    if (caseListBean.getAddress() != null) {
                        Glide.with(App.mContext).load(caseListBean.getAddress()).error(R.mipmap.default_product_icon).into(viewHolder.ivThirdServerCase);
                    }
                }

                break;
            default:
                break;
        }
        //跳转服务团队主页h5
        viewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(goodsBeanList.get(position).getId()));
                bundle.putString("url", H5Const.SERVER_TEAM_PAGE);
                JumpIntent.jump(mContext, ServerTeamPageDetailActivity.class, bundle);
            }
        });
        //查看更服务案例按钮
        viewHolder.tvCheckMoreCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(goodsBeanList.get(position).getId()));
                bundle.putString("url", H5Const.SERVER_TEAM_PAGE);
                bundle.putString("isAnli", "isAnli");
                JumpIntent.jump(mContext, ServerTeamPageDetailActivity.class, bundle);
            }
        });
        //如果是自己就不显示联系团队 否则显示
        if (App.manager.getId() == goodsBeanList.get(position).getMemberId()) {
            viewHolder.tvContactTeam.setVisibility(View.GONE);
        } else {
            viewHolder.tvContactTeam.setVisibility(View.VISIBLE);
        }
        //联系团队
        viewHolder.tvContactTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (App.manager.getLoginState()) {
                    IntentChatEntity intentChatEntity = new IntentChatEntity();
                    intentChatEntity.setAcceptName(goodsBeanList.get(position).getNikename());
                    intentChatEntity.setAcceptId(goodsBeanList.get(position).getUuid());
                    intentChatEntity.setChatType(ChatType.C2C);
                    ChatScreenActivity.getJumpChatSource(mContext, intentChatEntity);
                } else {
                    JumpIntent.jump(mContext, LoginActivity.class);
                }
            }
        });
        return view;
    }

    private void initAnimationsOne() {
        mShowAction = AnimationUtils.loadAnimation(mContext, R.anim.push_up_in);
    }

    static class ViewHolder {
        @BindView(R.id.cl_container)
        ConstraintLayout constraintLayout;
        @BindView(R.id.iv_server_team)
        ImageView ivServerName;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_server_distance)
        TextView tvServerDistance;
        @BindView(R.id.ratingBar)
        RatingBar ratingBar;
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.tv_contact_team)
        TextView tvContactTeam;
        @BindView(R.id.tv_server_case)
        TextView tvServerCase;
        @BindView(R.id.tv_first_server_case_name)
        TextView tvFirstServerCaseName;
        @BindView(R.id.iv_first_server_case)
        ImageView ivFirstServerCase;
        @BindView(R.id.tv_first_case_detail)
        TextView tvFirstCaseDetail;
        @BindView(R.id.ll_first_server_case)
        LinearLayout llFirstServerCase;
        @BindView(R.id.tv_second_server_case_name)
        TextView tvSecondServerCaseName;
        @BindView(R.id.iv_second_server_case)
        ImageView ivSecondServerCase;
        @BindView(R.id.tv_second_case_detail)
        TextView tvSecondCaseDetail;
        @BindView(R.id.iv_second_down)
        ImageView ivSecondDown;
        @BindView(R.id.ll_second_server_case)
        LinearLayout llSecondServerCase;
        @BindView(R.id.tv_third_server_case_name)
        TextView tvThirdServerCaseName;
        @BindView(R.id.iv_third_server_case)
        ImageView ivThirdServerCase;
        @BindView(R.id.tv_third_case_detail)
        TextView tvThirdCaseDetail;
        @BindView(R.id.iv_third_down)
        ImageView ivThirdDown;
        @BindView(R.id.ll_third_server_case)
        LinearLayout llThirdServerCase;
        @BindView(R.id.tv_check_more_case)
        TextView tvCheckMoreCase;
        @BindView(R.id.iv_second_up)
        ImageView ivSecondUp;
        @BindView(R.id.iv_third_up)
        ImageView ivThirdUp;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
