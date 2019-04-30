package com.yst.im.imchatlibrary.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.R;
import com.yst.im.imsdk.bean.ContactsEntity;
import com.yst.im.imsdk.utils.BaseUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统通知 adapter
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/05/07.
 */
public class SystemNotificationAdapter extends BaseAdapter {
    private Context context;
    private String dissolve = "解散";
    private String quit = "退出群聊";
    private String remove = "移出群聊";
    private List<ContactsEntity> friendList;

    public SystemNotificationAdapter(Context context, List<ContactsEntity> friendList) {
        this.context = context;
        this.friendList = friendList;
    }

    @Override
    public int getCount() {
        return null == friendList ? 0 : friendList.size();
    }

    @Override
    public Object getItem(int position) {
        return friendList.get(position);
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
            convertView = View.inflate(context, R.layout.item_system_list, null);
            holder.mImgSystemListIcon = (ImageView) convertView.findViewById(R.id.img_system_list_icon);
            holder.mTxtSystemListUserName = (TextView) convertView.findViewById(R.id.txt_system_list_user_name);
            holder.mTxtSystemListTime = (TextView) convertView.findViewById(R.id.txt_system_list_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTxtSystemListTime.setText(BaseUtils.stampToYearAndDate(friendList.get(position).getOccureTime()));
        Glide.with(MyApp.getInstance())
                .load(R.drawable.icon_notice)
                .into(holder.mImgSystemListIcon);
        String content = friendList.get(position).getContent();
        if (!TextUtils.isEmpty(content)) {
            int index;
            if (content.contains(dissolve)) {
                index = content.indexOf(dissolve);
                content = content.substring(0, index + 2) + " " + "<font color=\"#DF9253\">" + content.substring(index + 2, content.length()) + "</font>";
            } else if (content.contains(quit)) {
                index = content.indexOf(quit);
                content = content.substring(0, index + 4) + " " + "<font color=\"#DF9253\">" + content.substring(index + 4, content.length()) + "</font>";
            } else if (content.contains(remove)) {
                index = content.indexOf(remove);
                content = content.substring(0, index + 4) + " " + "<font color=\"#DF9253\">" + content.substring(index + 4, content.length()) + "</font>";
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                holder.mTxtSystemListUserName.setText(Html.fromHtml(content, Html.FROM_HTML_MODE_COMPACT));
            } else {
                holder.mTxtSystemListUserName.setText(Html.fromHtml(content));
            }
        }
        return convertView;
    }

    /**
     * 好友列表ViewHolder
     */
    private class ViewHolder {
        private ImageView mImgSystemListIcon;
        private TextView mTxtSystemListUserName;
        private TextView mTxtSystemListTime;
    }
}
