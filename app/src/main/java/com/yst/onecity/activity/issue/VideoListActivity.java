package com.yst.onecity.activity.issue;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.bean.issue.VideoInfo;
import com.yst.onecity.utils.ConstUtils;
import java.util.ArrayList;
import butterknife.BindView;

/**
 * 显示本地视频页面
 *
 * @author zhaiyanwu
 * @version 1.0.1
 * @date 2018/02/23
 */
public class VideoListActivity extends BaseActivity {

    ArrayList<VideoInfo> vList;
    private Intent lastIntent;
    @BindView(R.id.lv_video)
    ListView mListView;
    @BindView(R.id.tv_nodata)
    TextView noData;

    @Override
    public int getLayoutId() {
        return R.layout.activity_video_list;
    }

    @Override
    public void initData() {
        initTitleBar("本地视频");
        lastIntent = getIntent();
        vList = new ArrayList<VideoInfo>();

        String[] mediaColumns = new String[]{MediaStore.MediaColumns.DATA,
                BaseColumns._ID, MediaStore.MediaColumns.TITLE, MediaStore.MediaColumns.MIME_TYPE,
                MediaStore.Video.VideoColumns.DURATION, MediaStore.MediaColumns.SIZE};
        Cursor cursor = getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI, mediaColumns,
                null, null, null);
        if (cursor.moveToFirst()) {
            do {
                VideoInfo info = new VideoInfo();
                // 视频缩略图路径：MediaStore.Images.Media.DATA
                info.imagePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA));
                info.setFilePath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)));
                info.setMimeType(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.MIME_TYPE)));
                info.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.TITLE)));
                info.setTime(ConstUtils.longToHms(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DURATION))));
                info.setSize(ConstUtils.longToPoint(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.SIZE))));
                // 视频时长：MediaStore.Audio.Media.DURATION
                info.duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                // 视频大小：MediaStore.Audio.Media.SIZE
                info.videoSize = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
                int id = cursor.getInt(cursor
                        .getColumnIndexOrThrow(BaseColumns._ID));
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inDither = false;
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                info.setB(MediaStore.Video.Thumbnails.getThumbnail(getContentResolver(), id,
                        MediaStore.Images.Thumbnails.MICRO_KIND, options));
                vList.add(info);

            } while (cursor.moveToNext());
        }

        if (vList.size() == 0) {
            mListView.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        } else {
            VideoListAdapter adapter = new VideoListAdapter(VideoListActivity.this);
            mListView.setAdapter(adapter);
        }


        mListView.setOnItemClickListener(new ItemClick());

    }

    private class ItemClick implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mListView.getItemAtPosition(position);
            String filePath = vList.get(position).getFilePath();
            String imagePath = vList.get(position).imagePath;
            String duration = vList.get(position).getTime();
            int duration2 = vList.get(position).duration;
            String size = vList.get(position).getSize();
            long size2 = vList.get(position).videoSize;
            lastIntent.putExtra("path", filePath);
            lastIntent.putExtra("imagePath",imagePath);
            lastIntent.putExtra("duration",duration);
            lastIntent.putExtra("duration2",duration2);
            lastIntent.putExtra("size",size);
            lastIntent.putExtra("size2",size2);
            setResult(Constant.LOCAL_VIDEO_RESULT, lastIntent);
            finish();
        }

    }

    class VideoListAdapter extends BaseAdapter {
        private LayoutInflater inflater;

        public VideoListAdapter(Context context) {
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return vList.size();
        }

        @Override
        public Object getItem(int p) {
            return vList.get(p);
        }

        @Override
        public long getItemId(int p) {
            return p;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.item_video_list, null);
                holder.vImage = convertView.findViewById(R.id.video_img);
                holder.vTitle = convertView.findViewById(R.id.video_title);
                holder.vSize = convertView.findViewById(R.id.video_size);
                holder.vTime = convertView.findViewById(R.id.video_time);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.vImage.setImageBitmap(vList.get(position).getB());
            holder.vTitle.setText(vList.get(position).getTitle());
            holder.vSize.setText(vList.get(position).getSize());
            holder.vTime.setText(vList.get(position).getTime());

            holder.vImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    String bpath = "file://" + vList.get(position).getFilePath();
                    intent.setDataAndType(Uri.parse(bpath), "video/*");
                    startActivity(intent);
                }
            });
            return convertView;
        }

        class ViewHolder {
            ImageView vImage;
            TextView vTitle;
            TextView vSize;
            TextView vTime;
        }
    }
}
