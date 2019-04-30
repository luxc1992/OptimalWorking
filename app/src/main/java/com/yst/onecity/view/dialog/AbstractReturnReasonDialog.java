package com.yst.onecity.view.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.yst.basic.framework.adapter.AbstractCommonAdapter;
import com.yst.basic.framework.entity.holder.CommonViewHolder;
import com.yst.onecity.R;
import com.yst.onecity.bean.order.ReasonBean;
import com.yst.onecity.view.MyListView;

import java.util.List;

/**
 * 申请退款 退货原因选择弹出框
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/2/24
 */
public abstract class AbstractReturnReasonDialog {
    protected Dialog dialog;
    private List<ReasonBean> data;
    private Context context;

    public AbstractReturnReasonDialog(Activity activity, List<ReasonBean> data) {
        this.data = data;
        this.context = activity;
        if (dialog == null) {
            getDialog(activity);
        }
    }

    @SuppressWarnings("deprecation")
    private void getDialog(Activity activity) {
        @SuppressLint("InflateParams") View view = activity.getLayoutInflater().inflate(R.layout.dialog_return_reason, null);
        //默认样式
        dialog = new Dialog(activity, R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        MyListView listView = view.findViewById(R.id.listView);
        TextView tvClose = view.findViewById(R.id.tv_close);
        final ImageView ivChoose = view.findViewById(R.id.iv_choose);
        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams wl = window.getAttributes();
            wl.x = 0;
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            wl.y = dm.heightPixels;
            // 以下这两句是为了保证按钮可以水平满屏
            wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
            wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            // 设置显示位置
            dialog.onWindowAttributesChanged(wl);
        }
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
        final AbstractCommonAdapter<ReasonBean> adapter = new AbstractCommonAdapter<ReasonBean>(context, data, R.layout.item_return_reason) {
            @Override
            public void convert(CommonViewHolder holder, int position, ReasonBean item) {
                holder.setText(R.id.tv_return_reason, item.getReasonTypeName());
                ImageView ivChoose = holder.getView(R.id.iv_choose);
                if (data.get(position).isChoose()) {
                    ivChoose.setImageResource(R.mipmap.checked);
                } else {
                    ivChoose.setImageResource(R.mipmap.circle);
                }
                holder.setText(R.id.tv_return_reason, item.getReasonTypeName());
            }
        };
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                for (int i = 0; i < data.size(); i++) {
                    data.get(i).setChoose(false);
                }
                data.get(position).setChoose(true);
                adapter.notifyDataSetChanged();
                onItemClickListener(data, data.get(position).getReasonTypeName(), data.get(position).getId());
                dialog.dismiss();
            }
        });

        tvClose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    /**
     * 条目点击监听
     *
     * @param reason         退货原因
     * @param returnReasonId 退货原因id
     * @param reasonData     退货原因数据源
     */
    public abstract void onItemClickListener(List<ReasonBean> reasonData, String reason, String returnReasonId);

    public void showDialog() {
        dialog.show();
    }

    public void dismissDialog() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }
}
