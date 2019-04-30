package com.yst.onecity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.yst.onecity.R;
import com.yst.onecity.bean.ImageBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 添加图片适配器
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/2/7
 */
public class ChooseImageAdapter extends BaseAdapter {
    private List<ImageBean> datas = new ArrayList<>();
    private Context mContext;
    private LayoutInflater inflater;
    private int maxCount;

    public ChooseImageAdapter(List<ImageBean> list, Context context, int maxCount) {
        datas = list;
        mContext = context;
        inflater = LayoutInflater.from(context);
        this.maxCount = maxCount;
    }

    @Override
    public int getCount() {
        int count = datas == null ? 1 : datas.size() + 1;
        //可以动态设置最多上传几张，之后就不显示+号了，用户也无法上传了
        if (count >= maxCount + 1) {
            return datas.size();
        } else {
            return count;
        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_add_image, viewGroup, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (datas != null && position < datas.size()) {
            final File file = new File(datas.get(position).getAddress());
            //加载图片
            Glide.with(mContext).load(datas.get(position).getAddress()).into(holder.ivAddImage);
            holder.rlDelete.setVisibility(View.VISIBLE);
            holder.rlDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (file.exists()) {
                        file.delete();
                    }
                    datas.remove(position);
                    notifyDataSetChanged();
                }
            });
        } else {
            Glide.with(mContext).load(R.mipmap.camera).into(holder.ivAddImage);
            holder.rlDelete.setVisibility(View.GONE);
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_add_image)
        ImageView ivAddImage;
        @BindView(R.id.rl_delete)
        RelativeLayout rlDelete;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
