package com.yst.onecity.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.onecity.R;
import com.yst.onecity.view.customcalend.CalendarActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 选择服务时间
 *
 * @author chejianqi
 * @version 1.0.1
 * @date 2018/5/18
 */

public class SelectServiceTimeActivity extends BaseActivity {
    @BindView(R.id.start_tm)
    TextView startTm;
    @BindView(R.id.end_tm)
    TextView endTm;
    private String dateTime;
    private String times;

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_service_time;
    }

    @Override
    public void initData() {
        initTitleBar("选择服务时间");
        setRightText("确定");
        setRightTextViewOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                if (!TextUtils.isEmpty(startTm.getText().toString())&&!TextUtils.isEmpty(endTm.getText().toString())){
                    intent.putExtra("time",startTm.getText().toString()+" 至 "+endTm.getText().toString());
                    setResult(RESULT_OK,intent);
                }
                finish();
            }
        });
        String a="times";
        if (getIntent().hasExtra(a)){
            if (!TextUtils.isEmpty(getIntent().getStringExtra(a))){
                times = getIntent().getStringExtra("times");
                String[] s = times.split("至");
                startTm.setText(s[0].trim());
                endTm.setText(s[1].trim());
            }
        }
    }

    @OnClick({R.id.start_times, R.id.end_times})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.start_times:
                calendarAlert(true);
                break;
            case R.id.end_times:
                if (!TextUtils.isEmpty(startTm.getText().toString().trim())){
                    calendarAlert(false);
                }else {
                    ToastUtils.show("请先选择开始时间");
                }
                break;
            default:
                break;
        }
    }

    /**
     * 日历弹出框
     */

    private void calendarAlert(final boolean flag) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setCancelable(false);
        View view = LayoutInflater.from(this).inflate(R.layout.alert_item, null);
        alertDialogBuilder.setView(view);
        alertDialogBuilder.create();
        final AlertDialog alertDialog = alertDialogBuilder.show();
        CalendarActivity calendarView = (CalendarActivity) view.findViewById(R.id.calendarView);
        ImageView cancel = (ImageView) view.findViewById(R.id.cancel);
        ImageView ok = (ImageView) view.findViewById(R.id.ok);
        if (flag){
            calendarView.setNewCalenderListener(new CalendarActivity.NewCalenderListener() {
                @Override
                public void onItemListener(Date data) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat chinies = new SimpleDateFormat("yyyy年MM月dd日");
                    SimpleDateFormat mYearMonthDayformat = new SimpleDateFormat("yyyy/MM/dd");
                    //获取今天的日期
                    dateTime = sdf.format(data);
                }
            },startTm.getText().toString(),true);
        }else {
            calendarView.setNewCalenderListener(new CalendarActivity.NewCalenderListener() {
                @Override
                public void onItemListener(Date data) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat chinies = new SimpleDateFormat("yyyy年MM月dd日");
                    SimpleDateFormat mYearMonthDayformat = new SimpleDateFormat("yyyy/MM/dd");
                    //获取今天的日期
                    dateTime = sdf.format(data);
                    String selectDate = chinies.format(data);
                    String mYearMonthDay = mYearMonthDayformat.format(data);

                }
            },startTm.getText().toString()+"@@@"+endTm.getText().toString(),false);
        }

        //取消
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        //确定
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                if (flag){
                    startTm.setText(dateTime);
                    if (!TextUtils.isEmpty(endTm.getText().toString().trim())){
                        String endTms = endTm.getText().toString();

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            Date parse = sdf.parse(endTms);
                            Date parse1 = sdf.parse(startTm.getText().toString());
                            if (parse1.getTime()>parse.getTime()){
                                endTm.setText(startTm.getText().toString());
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }else {
                    endTm.setText(dateTime);
                }

            }
        });
    }
}
