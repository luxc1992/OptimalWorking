package com.yst.im.imchatlibrary.asynctask;

import java.io.IOException;

/**
 * asyncTask 异步请求
 *
 * @author  Lierpeng
 * @date  2018/1/2.
 * @version 1.0.1
 */
public abstract class BaseAsyncCallBack {
    public void onPreExecute() {
    }

    /**
     * 主线程
     *
     * @throws IOException 流异常
     */
    public abstract void doInBackground() throws IOException;

    /**
     * 子线程
     */
    public abstract void onPostExecute();
}
