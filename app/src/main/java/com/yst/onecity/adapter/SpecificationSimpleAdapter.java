package com.yst.onecity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yst.onecity.R;

import java.util.ArrayList;

/** 规格值删除键适配器
 *
 * @author zhaiyanwu
 * @version v3.0.1
 * @date 2018/2/24.
 */
public class SpecificationSimpleAdapter extends BaseAdapter {
    private final LayoutInflater from;
    Context context;
    ArrayList list;

    public SpecificationSimpleAdapter(Context context, ArrayList list) {
        this.context = context;
        this.list = list;
        from = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void setDate(ArrayList bean) {
        list.addAll(bean);
        notifyDataSetChanged();
    }
    public void deleteData(int i ){
        list.remove(i);
        notifyDataSetChanged();
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view  =  from.inflate(R.layout.item_listview_delete_specification,null);
        }
        return view;
    }

    class ViewHolder{

        View view;

    }

}
