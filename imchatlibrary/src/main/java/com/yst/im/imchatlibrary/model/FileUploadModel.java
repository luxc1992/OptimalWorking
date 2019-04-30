package com.yst.im.imchatlibrary.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yst.im.imchatlibrary.bean.FileUpLoadEntity;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;

import okhttp3.Call;

import static com.yst.im.imsdk.MessageConstant.NUM_0;

/**
 * 文件上传
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */

public class FileUploadModel {
    private FileUploadListenerCallBack mFileUploadListenerCallBack;
    private Context context;

    public FileUploadModel(Context context) {
        this.context = context;
    }

    /**
     * 上传
     *
     * @param file 文件路径
     * @throws Exception 泛异常
     */
    public void getUpLoadPicture(File file, final int type, final int messageType) throws Exception {
        OkHttpUtils.post()
                .url(Constants.URL_FILE_UPLOAD)
                .addFile("urlFile", file.getName(), file)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d("xcc", "上传成功" + response);
                        Gson gson = new Gson();
                        FileUpLoadEntity fileUpLoadEntity = gson.fromJson(response, FileUpLoadEntity.class);
                        if (fileUpLoadEntity.getCode() == NUM_0) {
                            fileUpLoadEntity.setType(type);
                            fileUpLoadEntity.setMessageType(messageType);
                            mFileUploadListenerCallBack.onFileUpload(fileUpLoadEntity);
                        }
                    }
                });
    }

    public interface FileUploadListenerCallBack {
        /**
         * 审核用户加入群回调
         *
         * @param fileUpLoadEntity
         */
        void onFileUpload(FileUpLoadEntity fileUpLoadEntity);
    }

    public void setFileUploadListenerCallBack(FileUploadListenerCallBack mFileUploadListenerCallBack) {
        this.mFileUploadListenerCallBack = mFileUploadListenerCallBack;
    }

}
