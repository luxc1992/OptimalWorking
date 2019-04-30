package com.yst.onecity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yst.onecity.R;
import com.yst.onecity.bean.ReapBean;

import java.util.ArrayList;
import java.util.List;

import static com.umeng.commonsdk.stateless.UMSLEnvelopeBuild.mContext;

/**
 * 我的   收获地址适配
 *
 * @author LianJinXue
 * @version 1.0.1
 * @date 2018/3/5
 */

public class ReapAdapter extends BaseAdapter{
private List<ReapBean>mlist=new ArrayList<>();
    private Context context;

    public ReapAdapter(List<ReapBean> mlist, Context context) {
        this.mlist = mlist;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mlist==null? 0: mlist.size();
    }

    @Override
    public Object getItem(int i) {
        return mlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
      HunttingBeansAdapter.ViewHolder viewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_am, null);
            viewHolder = new HunttingBeansAdapter.ViewHolder(view);
            view.setTag(viewHolder);
        }
        return null;
    }
}
