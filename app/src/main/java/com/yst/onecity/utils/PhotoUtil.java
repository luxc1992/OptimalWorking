package com.yst.onecity.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

import com.yst.basic.framework.App;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 图片工具类
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/02/07
 */
public class PhotoUtil {

    /**
     * 图片压缩
     */
    private static Bitmap compressImageBitmap(String photoPath, int ivWidth, int ivHeight) {

        //根据图片路径，获取bitmap的宽和高
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, options);
        int photoWidth = options.outWidth;
        int photoHeight = options.outHeight;

        //获取缩放比例
        int inSampleSize = 1;
        if (photoWidth > ivWidth || photoHeight > ivHeight) {
            int widthRatio = Math.round((float) photoWidth / ivWidth);
            int heightRatio = Math.round((float) photoHeight / ivHeight);
            inSampleSize = Math.max(widthRatio, heightRatio);
        }

        //使用现在的options获取Bitmap
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(photoPath, options);
    }

    /**
     * 图片压缩并返回路径
     */
    public static String compressBitmap(String photoPath, int ivWidth, int ivHeight) {
        return savePngImage(compressImageBitmap(photoPath, ivWidth, ivHeight), System.currentTimeMillis() + ".png");
    }

    /**
     * 通过Uri获取文件
     *
     * @param ac
     * @param uri
     * @return
     */
    public static File getFileFromMediaUri(Context ac, Uri uri) {
        String content = "content";
        String file = "file";
        if (uri.getScheme().toString().compareTo(content) == 0) {
            ContentResolver cr = ac.getContentResolver();
            // 根据Uri从数据库中找
            Cursor cursor = cr.query(uri, new String[]{"_data"}, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                // 获取图片路径
                String filePath = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
                cursor.close();
                if (filePath != null) {
                    return new File(filePath);
                }
            }
        } else if (uri.getScheme().toString().compareTo(file) == 0) {
            return new File(uri.toString().replace("file://", ""));
        }
        return null;
    }

    /**
     * bitmap 保存png
     */
    public static String savePngImage(Bitmap bitmap, String name) {
        File file = new File(App.getInstance().getExternalFilesDir(Environment.DIRECTORY_PICTURES), name);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        FileOutputStream out = null;
        try {
            int options = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, os);
            //大于200KB就一直压缩
            int size = 1024;
            int maxSize = 200;
            while (os.toByteArray().length / size > maxSize) {
                os.reset();
                options -= 10;
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, os);
            }
            out = new FileOutputStream(file);
            out.write(os.toByteArray());
            out.flush();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    os.close();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
