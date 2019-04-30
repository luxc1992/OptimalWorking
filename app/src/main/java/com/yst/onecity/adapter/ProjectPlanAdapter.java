package com.yst.onecity.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.onecity.R;
import com.yst.onecity.activity.issue.ProjectPlanActivity;
import com.yst.onecity.bean.ProjectPlanBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO2;

/**
 * 产品计划adapter
 *
 * @author liumanqing
 * @version 1.0.1
 * @date 2018/2/24
 */
public class ProjectPlanAdapter extends BaseAdapter {
    private List<ProjectPlanBean.ContentBean> list;
    private Activity context;
    private View.OnClickListener onClickListener;

    public ProjectPlanAdapter(List<ProjectPlanBean.ContentBean> list, Activity context, View.OnClickListener onClickListener) {
        this.list = list;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    public void setData(List<ProjectPlanBean.ContentBean> list) {
        if (list != null) {
            this.list = list;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_project_plan, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.title.setText(list.get(position).getTitle());
        vh.time.setText(list.get(position).getCreatedTime());
        vh.location.setText(list.get(position).getAddress());
        Glide.with(context).load(list.get(position).getAttachmentaddress()).into(vh.img);
        /**
         * 判断审核失败与审核中 （审核成功的不显示）2审核失败 0审核中
         */
        if (list.get(position).getStatus() == NO2) {
            vh.isPass.setText("审核失败");
            vh.lineCompileDelete.setVisibility(View.VISIBLE);
            vh.lineFail.setVisibility(View.VISIBLE);
            vh.fail.setText(list.get(position).getContent());
        } else if (list.get(position).getStatus() == NO0) {
            vh.isPass.setText("审核中");
            vh.lineCompileDelete.setVisibility(View.GONE);
            vh.lineFail.setVisibility(View.GONE);
        }
        //点击编辑跳转到申请项目计划界面
        vh.lineCompile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putSerializable("projectPlanBean", list.get(position));
                b.putInt("publishFlag", 6);
                JumpIntent.jump(context, ProjectPlanActivity.class, b);
            }
        });
        //点击删除的监听回调
        vh.lineDelete.setTag(list.get(position).getId() + "");
        vh.lineDelete.setOnClickListener(onClickListener);
        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.tv_project_plan_title)
        TextView title;
        @BindView(R.id.tv_project_plan_time)
        TextView time;
        @BindView(R.id.tv_project_plan_location)
        TextView location;
        @BindView(R.id.img_project_plan)
        ImageView img;
        @BindView(R.id.tv_project_plan_ispass)
        TextView isPass;
        @BindView(R.id.tv_project_plan_compile)
        TextView compile;
        @BindView(R.id.tv_project_plan_delete)
        TextView delete;
        @BindView(R.id.tv_project_plan_fail)
        TextView fail;
        @BindView(R.id.line_projectplan_complie_delete)
        LinearLayout lineCompileDelete;
        @BindView(R.id.line_project_plan_fail)
        LinearLayout lineFail;
        @BindView(R.id.line_delete_project_plan)
        LinearLayout lineDelete;
        @BindView(R.id.line_compile_project_plan)
        LinearLayout lineCompile;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
