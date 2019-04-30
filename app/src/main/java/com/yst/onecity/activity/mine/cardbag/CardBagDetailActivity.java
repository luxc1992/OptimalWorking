package com.yst.onecity.activity.mine.cardbag;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.onecity.R;
import com.yst.onecity.adapter.CardDetailRecordAdapter;
import com.yst.onecity.adapter.CardDetailTeamAdapter;
import com.yst.onecity.bean.MyCardBagDetailBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.ZxingUtils;
import com.yst.onecity.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO2;
import static com.yst.onecity.Constant.NO3;
import static com.yst.onecity.Constant.NO4;
import static com.yst.onecity.Constant.NO5;

/**
 * 卡券详情
 *
 * @author liumanqing
 * @version 1.1.0
 * @date 2018/5/19
 */
public class CardBagDetailActivity extends BaseActivity {
    @BindView(R.id.rel_card)
    RelativeLayout relCard;
    @BindView(R.id.tv_card_name)
    TextView tvCardName;
    @BindView(R.id.tv_card_time)
    TextView tvCardTime;
    @BindView(R.id.tv_card_date)
    TextView tvData;
    @BindView(R.id.tv_card_code)
    TextView tvCode;
    @BindView(R.id.img_card_zxing)
    ImageView zXingImg;
    @BindView(R.id.lv_card_record)
    MyListView lvCardRecord;
    @BindView(R.id.lv_card_team)
    MyListView lvCardTeam;
    private CardDetailRecordAdapter recordAdapter;
    private CardDetailTeamAdapter teamAdapter;
    private List<MyCardBagDetailBean.ContentBean.PunchMsgListBean> punchMsgList = new ArrayList<>();
    private List<MyCardBagDetailBean.ContentBean.TeamMsgListBean> teamMsgList = new ArrayList<>();
    private List<MyCardBagDetailBean.ContentBean.PunchMsgListBean> punchMsg = new ArrayList<>();
    private List<MyCardBagDetailBean.ContentBean.TeamMsgListBean> teamMsg = new ArrayList<>();
    private int type;
    private int id;
    private StringBuffer code = new StringBuffer();

    @Override
    public int getLayoutId() {
        return R.layout.activity_card_bag_detail;
    }

    @Override
    public void initData() {
        initTitleBar("健身卡详情");
        if (getIntent().getExtras() != null) {
            id = getIntent().getExtras().getInt("id");
        }
        getData();
    }

    @OnClick({R.id.tv_record_more, R.id.tv_team_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_record_more:
                punchMsg = punchMsgList;
                flushRecordList();
                break;
            case R.id.tv_team_more:
                teamMsg = teamMsgList;
                flushTeamList();
                break;
            default:
                break;
        }
    }

    /**
     * 获得页面数据
     */
    private void getData() {
        RequestApi.getCardBagDetail(String.valueOf(id), App.manager.getUuid(), App.manager.getPhoneNum(), new AbstractNetWorkCallback<MyCardBagDetailBean>() {
            @Override
            public void onSuccess(MyCardBagDetailBean myCardBagDetailBean) {
                if (myCardBagDetailBean.getCode() == 1) {
                    type = myCardBagDetailBean.getContent().getTYPE();
                    isType();
                    punchMsgList = myCardBagDetailBean.getContent().getPunchMsgList();
                    teamMsgList = myCardBagDetailBean.getContent().getTeamMsgList();
                    if (myCardBagDetailBean.getContent().getPunchMsgList().size() > NO5) {
                        punchMsg.add(punchMsgList.get(NO0));
                        punchMsg.add(punchMsgList.get(NO1));
                        punchMsg.add(punchMsgList.get(NO2));
                        punchMsg.add(punchMsgList.get(NO3));
                        punchMsg.add(punchMsgList.get(NO4));
                    } else {
                        punchMsg = punchMsgList;
                    }
                    if (teamMsgList.size() > NO3) {
                        teamMsg.add(teamMsgList.get(NO0));
                        teamMsg.add(teamMsgList.get(NO1));
                        teamMsg.add(teamMsgList.get(NO2));
                    } else {
                        teamMsg = teamMsgList;
                    }
                    if (type == NO1 || type == NO0) {
                        tvCardTime.setText("已使用：" + myCardBagDetailBean.getContent().getPayNum() + "/" + myCardBagDetailBean.getContent().getAllNum() + "（天）");
                        code.append(2);
                    } else if (type == NO2 || type == NO3) {
                        tvCardTime.setText("已使用：" + myCardBagDetailBean.getContent().getPayNum() + "/" + myCardBagDetailBean.getContent().getAllNum() + "（次）");
                        code.append(3);
                    }
                    tvData.setText("有效期限：" + myCardBagDetailBean.getContent().getBeginTime() + "至" + myCardBagDetailBean.getContent().getEndTime());
                    tvCode.setText("验证码：" + myCardBagDetailBean.getContent().getCODE());

                    code.append("&");
                    //订单id
                    code.append(myCardBagDetailBean.getContent().getServiceOrderId());
                    code.append("&");
                    //验证码
                    code.append(myCardBagDetailBean.getContent().getCODE());
                    Log.e("liumanqing", code + "               " + code.toString());
                    zXingImg.setImageBitmap(ZxingUtils.createQRCodeBitmap(code.toString(), 500));
                    flushTeamList();
                    flushRecordList();

                } else {
                    ToastUtils.show(myCardBagDetailBean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {

            }
        });
    }

    /**
     * 判断卡的类型
     */
    private void isType() {
        if (type == NO0) {
            tvCardName.setText("周期卡（高端尊享卡）");
            relCard.setBackgroundResource(R.mipmap.beijing1);
            tvCardTime.setBackgroundResource(R.drawable.shape_cradbag_time_bg1);
        } else if (type == NO1) {
            tvCardName.setText("周期卡（中端精品卡）");
            relCard.setBackgroundResource(R.mipmap.beijing1);
            tvCardTime.setBackgroundResource(R.drawable.shape_cradbag_time_bg1);
        } else if (type == NO2) {
            tvCardName.setText("次数卡（高端尊享卡）");
            relCard.setBackgroundResource(R.mipmap.beijing2);
            tvCardTime.setBackgroundResource(R.drawable.shape_cradbag_time_bg);
        } else if (type == NO3) {
            tvCardName.setText("次数卡（中端精品卡）");
            relCard.setBackgroundResource(R.mipmap.beijing2);
            tvCardTime.setBackgroundResource(R.drawable.shape_cradbag_time_bg);
        } else {
            finish();
        }
    }

    /**
     * 刷新打卡记录适配器
     */
    private void flushRecordList() {
        if (null == lvCardRecord) {
            return;
        }
        if (recordAdapter == null) {
            recordAdapter = new CardDetailRecordAdapter(CardBagDetailActivity.this, punchMsg);
            lvCardRecord.setAdapter(recordAdapter);
        } else {
            recordAdapter.setRecordList(punchMsg);
        }
    }

    /**
     * 刷新可用团队适配器
     */
    private void flushTeamList() {
        if (null == lvCardTeam) {
            return;
        }
        if (teamAdapter == null) {
            teamAdapter = new CardDetailTeamAdapter(CardBagDetailActivity.this, teamMsg);
            lvCardTeam.setAdapter(teamAdapter);
        } else {
            teamAdapter.setTeamList(teamMsg);
        }
    }
}
