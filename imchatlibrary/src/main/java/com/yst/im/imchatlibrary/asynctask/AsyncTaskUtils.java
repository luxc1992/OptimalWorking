package com.yst.im.imchatlibrary.asynctask;

import android.os.AsyncTask;

import java.io.IOException;

/**
 * asyncTask 封装工具类
 *
 * @author Lierpeng
 * @date  2018/1/2.
 * @version  1.0.1
 */
public class AsyncTaskUtils {
    public static void doAsync(final BaseAsyncCallBack callBack) {
        if (callBack == null) {
            return;
        }
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                callBack.onPreExecute();
            }

            //子线程请求
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    callBack.doInBackground();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            //主线程更新ui
            @Override
            protected void onPostExecute(Void aVoid) {
                callBack.onPostExecute();
            }
        }.execute();
    }
}
