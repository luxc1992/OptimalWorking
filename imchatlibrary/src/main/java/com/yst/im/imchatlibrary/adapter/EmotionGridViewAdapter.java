package com.yst.im.imchatlibrary.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.utils.EmotionUtils;

import java.util.List;

/**
 * 表情GVAdapter
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/04/11.
 */
public class EmotionGridViewAdapter extends BaseAdapter {

    private Context context;
    private List<String> emotionNames;
    private int itemWidth;

    public EmotionGridViewAdapter(Context context, List<String> emotionNames, int itemWidth) {
        this.context = context;
        this.emotionNames = emotionNames;
        this.itemWidth = itemWidth;
    }

    @Override
    public int getCount() {
        // +1 最后一个为删除按钮
        return null == emotionNames ? 0 : emotionNames.size() + 1;
    }

    @Override
    public String getItem(int position) {
        return emotionNames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView ivEmotion = new ImageView(context);
        // 设置内边距
        ivEmotion.setPadding(itemWidth / 8, itemWidth / 8, itemWidth / 8, itemWidth / 8);
        LayoutParams params = new LayoutParams(itemWidth, itemWidth);
        ivEmotion.setLayoutParams(params);

        //判断是否为最后一个item
        if (position == getCount() - 1) {
            ivEmotion.setImageResource(R.drawable.compose_emotion_delete);
        } else {
            String emotionName = emotionNames.get(position);
            ivEmotion.setImageResource(EmotionUtils.EMOTION_STATIC_MAP.get(emotionName));
        }

        return ivEmotion;
    }

}
