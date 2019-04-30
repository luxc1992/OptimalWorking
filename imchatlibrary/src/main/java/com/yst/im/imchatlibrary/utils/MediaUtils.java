package com.yst.im.imchatlibrary.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import com.yst.im.imsdk.MessageConstant;
import com.yst.im.imsdk.bean.MessageBean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class MediaUtils {
    public static final int MEDIA_TYPE_IMAGE = 0;
    public static final int MEDIA_TYPE_VIDEO = 1;
    public static File file;

    /**
     * Create a file Uri for saving an image or video
     */
    public static Uri getOutputMediaFileUri(Context context, int type, String imgPath) {
        //适配Android N
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", getOutputMediaFile(type, imgPath));
        } else {
            return Uri.fromFile(getOutputMediaFile(type, imgPath));
        }
    }

    /**
     * Create a File for saving an image or video
     */
    public static File getOutputMediaFile(int type, String imgPath) {
//        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "image");
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/yst/im/");
//        private static final String CACHE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/yst/img";
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + imgPath + ".jpg");
            ImLog.e("xxxxxxxxxx", "filesssss" + mediaFile.getAbsolutePath());
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + imgPath + ".mp4");
        } else {
            return null;
        }
        file = mediaFile;
        return mediaFile;
    }

    /**
     * 获取视频的第一帧图片
     */
    public static void getImageForVideo(String videoPath, OnLoadVideoImageListener listener, String imgPath, MessageBean messageType) {
        LoadVideoImageTask task = new LoadVideoImageTask(listener, imgPath, messageType);
        task.execute(videoPath);
    }

    public static class LoadVideoImageTask extends AsyncTask<String, Integer, File> {
        private OnLoadVideoImageListener listener;
        private String imgPath;
        private MessageBean messageType;

        public LoadVideoImageTask(OnLoadVideoImageListener listener, String imgPath, MessageBean messageType) {
            this.listener = listener;
            this.imgPath = imgPath;
            this.messageType = messageType;
        }

        @Override
        protected File doInBackground(String... params) {
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            File f = getOutputMediaFile(MEDIA_TYPE_IMAGE, imgPath);
            String path = params[0];
            if (path.startsWith("http")) {
                //  获取网络视频第一帧图片
                mmr.setDataSource(path, new HashMap());
            } else {
                // 本地视频
                mmr.setDataSource(path);
            }

            Bitmap bitmap = mmr.getFrameAtTime();
            //  保存图片

            if (f.exists()) {
                f.delete();
            }
            try {
                FileOutputStream out = new FileOutputStream(f);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mmr.release();
            return f;
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if (listener != null) {
            listener.onLoadImage(file);
        }
    }
}

public interface OnLoadVideoImageListener {
    void onLoadImage(File file);
}
}

