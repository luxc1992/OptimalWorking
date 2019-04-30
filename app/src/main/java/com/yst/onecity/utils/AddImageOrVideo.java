package com.yst.onecity.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.google.gson.Gson;
import com.yst.basic.framework.utils.NetworkUtils;
import com.yst.basic.framework.view.RequestProcessDialog;
import com.yst.onecity.Const;
import com.yst.onecity.Constant;
import com.yst.onecity.activity.issue.VideoListActivity;
import com.yst.onecity.bean.ImgUploadBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.UUID;

import me.nereo.multi_image_selector.MultiImageSelector;
import okhttp3.Call;
import okhttp3.Request;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 添加 图片/视频（本地图片/视频 拍摄图片/视频）
 *
 * @author songbinbin
 * @version 1.0.1
 * @date 2018/2/23
 */
public class AddImageOrVideo {

    private static final String SD_PATH = "/sdcard/dskqxt/pic/";
    private static final String IN_PATH = "/dskqxt/pic/";

    /**
     * 显示界面等待条
     */
    private static RequestProcessDialog mInfoProgressDialog;

    /**
     * 选择本地图片(多选)
     *
     * @param activity  activity
     * @param imgCount  最大可选图片数
     * @param imageCode 请求码
     */
    public static void localImg(Activity activity, int imageCode, int imgCount) {
        if (EasyPermissions.hasPermissions(activity, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            MultiImageSelector.create().count(imgCount).showCamera(true).start(activity, imageCode);
        } else {
            EasyPermissions.requestPermissions(activity, "请打开拍照和读取照片权限", imageCode, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    /**
     * 选择本地图片(单选)
     *
     * @param activity  当前页
     * @param imageRequestCode 请求码
     */
    public static void localImg(Activity activity, int imageRequestCode){
        //权限判断
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //申请READ_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, imageRequestCode);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activity.startActivityForResult(intent,imageRequestCode);
        }
    }

    /**
     * 选择本地视频
     *
     * @param activity  当前页
     * @param videoRequestCode  请求码
     */
    public static void localVideo(Activity activity, int videoRequestCode) {
        boolean write = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
            boolean camera = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED;
            //权限判断
            if (write && camera) {
                //申请权限
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, videoRequestCode);
        } else {
            Intent intent = new Intent(activity, VideoListActivity.class);
            activity.startActivityForResult(intent,videoRequestCode);
        }
    }

    /**
     * 拍摄图片
     *
     * @param activity 当前类
     * @param requestCode 请求码
     */
    public static void shootImg(Activity activity,int requestCode) {
        File outputImage = new File(Environment.getExternalStorageDirectory(), "tempImage" + ".jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
            Uri imageUri = Uri.fromFile(outputImage);
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            activity.startActivityForResult(intent, requestCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 拍摄视频
     */
    public static void shootVideo() {
//        PublishInfoVideoActivity.class
    }

    /**
     * 上传图片到服务器
     *
     * @param imagePath 图片路径
     * @param activity  当前文
     * @return  图片地址
     */
    public static void upLoadImg(final Activity activity, String imagePath, final UploadImgListener listener){
        //无网络
        if(NetworkUtils.NETWORN_NONE == NetworkUtils.getNetworkState(activity)){
            listener.uploadImgListener("无网络");
            return;
        }
        //上传的图片(BitmapUtil.scal()压缩图片）
        try {
            imagePath = URLDecoder.decode(imagePath, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        final File file= com.yst.onecity.utils.BitmapUtil.scal(imagePath);
        //当前时间戳
        String dateFormat = com.yst.onecity.utils.ConstUtils.getCurrentDateFormat();
        //上传图片接口
        String url = Const.UPLOAD_IMG;
        HashMap<String, String> params = new HashMap<>(16);
        //mark 客户端标识 0 安卓 1 iOS 2 PC
        params.put("mark", "0");
        //上传图片 得到图片地址

        OkHttpUtils.post().addFile("imageFile", dateFormat + ".jpg", file).url(url).params(params).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                listener.uploadImgListener("上传失败");
            }
            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                ImgUploadBean imgUpload = gson.fromJson(response,ImgUploadBean.class);
                if(null == imgUpload || null == imgUpload.content){
                    listener.uploadImgListener("上传失败");
                    return;
                }
                if(Constant.NO1 == imgUpload.code){
                    listener.uploadImgListener(imgUpload.content);
                }else {
                    listener.uploadImgListener(imgUpload.msg);
                }
            }

            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if (mInfoProgressDialog == null) {
                    mInfoProgressDialog = new RequestProcessDialog(activity);
                }
                mInfoProgressDialog.setMessage("加载中");
                mInfoProgressDialog.setCancelable(false);
                if (!activity.isFinishing()) {
                    try {
                        mInfoProgressDialog.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if (mInfoProgressDialog != null) {
                    mInfoProgressDialog.dismiss();
                }
            }
        });
    }

    /**
     * 上传视频到服务器
     *
     * @param videoPath 视频路径
     * @param activity  当前文
     * @return  视频地址
     */
    public static void upLoadVideo(final Activity activity, String videoPath, final UploadVideoListener listener){

        //无网络
        if(NetworkUtils.NETWORN_NONE == NetworkUtils.getNetworkState(activity)){
            listener.uploadVideoListener("无网络");
            return;
        }
        //上传的视频
        final File file= new File(videoPath);
        //当前时间戳
        String dateFormat = com.yst.onecity.utils.ConstUtils.getCurrentDateFormat();
        //上传视频接口
        String url = Const.UPLOAD_VIDEO;
        HashMap<String, String> params = new HashMap<>(16);
        //mark 客户端标识 0 安卓 1 iOS 2 PC
        params.put("mark", "0");
        //上传视频 得到视频地址
        OkHttpUtils.post().addFile("urlFile", dateFormat + ".mp4", file).url(url).params(params).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                listener.uploadVideoListener("上传失败");
            }
            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                ImgUploadBean imgUpload = gson.fromJson(response,ImgUploadBean.class);
                if(Constant.NO1 == imgUpload.code){
                    listener.uploadVideoListener(imgUpload.content);
                }else {
                    listener.uploadVideoListener(imgUpload.msg);
                }
            }

            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if (mInfoProgressDialog == null) {
                    mInfoProgressDialog = new RequestProcessDialog(activity);
                }
                mInfoProgressDialog.setMessage("加载中");
                mInfoProgressDialog.setCancelable(false);
                if (!activity.isFinishing()) {
                    try {
                        mInfoProgressDialog.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if (mInfoProgressDialog != null) {
                    mInfoProgressDialog.dismiss();
                }
            }
        });
    }

    /**
     * 根据Uri返回文件绝对路径
     * 兼容了file:///开头的 和 content://开头的情况
     *
     * @param context
     * @param uri
     * @return the file path or null
     */
    public static String getRealFilePathFromUri(Context context, Uri uri) {
        if (null == uri){
            return null;
        }
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 上传图片接口
     */
    public interface UploadImgListener{
        /**
         * 回调上传图片消息
         * @param msg
         */
        void uploadImgListener(String msg);
    }

    /**
     * 上传视频接口
     */
    public interface UploadVideoListener{
        /**
         * 回调上传视频消息
         * @param msg
         */
        void uploadVideoListener(String msg);
    }

    /**
     * 保存bitmap到本地
     *
     * @param context
     * @param mBitmap
     * @return
     */
    public static String saveBitmap(Context context, Bitmap mBitmap) {
        String savePath;
        File filePic;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            savePath = SD_PATH;
        } else {
            savePath = context.getApplicationContext().getFilesDir().getAbsolutePath() + IN_PATH;
        }
        try {
            filePic = new File(savePath + generateFileName() + ".jpg");
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return filePic.getAbsolutePath();
    }

    /**
     * 随机生产文件名
     *
     * @return
     */
    private static String generateFileName() {
        return UUID.randomUUID().toString();
    }

}
