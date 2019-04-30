package com.yst.onecity.view;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.bean.ServerTeamClassifyDropListBean;

import java.util.List;

/**
 * 功能描述
 *
 * @author chenjiadi
 * @version 1.1.0
 * @date 2018/5/17.
 */

public class ServerTeamClassifyPopwindow extends PopupWindow{

    private View mPopView;
    private Activity context;
    private ListView mListView;
    private List<ServerTeamClassifyDropListBean.ContentBean> list;
    private MyAdapter mAdapter;
    private boolean isShow;


    public ServerTeamClassifyPopwindow(Activity context, List<ServerTeamClassifyDropListBean.ContentBean> list, AdapterView.OnItemClickListener clickListener, boolean isShow) {
        this.context = context;
        this.list = list;
        this.isShow = isShow;
        mPopView = LayoutInflater.from(context).inflate(R.layout.server_team_pop_listview, null);
        this.setContentView(mPopView);

        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.update();
        ColorDrawable dw = new ColorDrawable(10222222);
        this.setBackgroundDrawable(dw);
        mListView = mPopView.findViewById(R.id.lv);
        if (isShow) {
            //listview固定高度
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 800);
            mListView.setLayoutParams(params);
        } else {
            //listview包裹内容
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            mListView.setLayoutParams(params);
        }
        mAdapter = new MyAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(clickListener);

    }


    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            this.showAsDropDown(parent);
        } else {
            this.dismiss();
        }
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.item_server_team_list_pop, null);
                holder.tvName = convertView.findViewById(R.id.text_select);
                holder.ivIsChoose = convertView.findViewById(R.id.iv_is_choose);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvName.setText(list.get(position).getName());
            if (list.get(position).isCheck()){
                holder.ivIsChoose.setVisibility(View.VISIBLE);
            }else {
                holder.ivIsChoose.setVisibility(View.GONE);
            }
            return convertView;
        }
    }

    private class ViewHolder {
        private TextView tvName;
        private ImageView ivIsChoose;
    }

}