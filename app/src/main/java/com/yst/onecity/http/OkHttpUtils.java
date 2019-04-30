package com.yst.onecity.http;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.umeng.commonsdk.statistics.common.MLog;
import com.yst.basic.framework.utils.MyLog;
import com.yst.basic.framework.utils.OkHttpLog;
import com.yst.basic.framework.utils.SignUtils;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.yst.onecity.Constant.FAILEDCONNECT;
import static com.yst.onecity.Constant.NETWORKUNREACHABLE;
import static com.yst.onecity.Constant.NO10;
import static com.yst.onecity.Constant.NO20;

/**
 * 网络请求工具类
 *
 * @author chejianqi
 * @version 1.0.1
 * @date 2018/2/28
 */

public class OkHttpUtils {
    private static OkHttpUtils okHttpUtils;
    private OkHttpClient okHttpClient;
    private Platform mPlatform;

    private OkHttpUtils() {
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new OkHttpLog("OneCity", true))
                .connectTimeout(20000L, TimeUnit.MILLISECONDS)
                .readTimeout(20000L, TimeUnit.MILLISECONDS).build();
        mPlatform = Platform.get();
    }

    public static OkHttpUtils getInstace() {
        if (okHttpUtils == null) {
            synchronized (OkHttpUtils.class) {
                if (okHttpUtils == null) {
                    okHttpUtils = new OkHttpUtils();
                }
            }
        }
        return okHttpUtils;
    }

    public <T> void post(final String url, final Map<String, Object> params, final AbstractNetWorkCallback<T> callback) {

        FormBody.Builder builder = new FormBody.Builder();
        if (params != null && params.size() > 0) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                String value = (String) params.get(key);
                builder.add(key, value);
            }
            //mark 客户端标识 0 安卓 1 iOS 2 PC
            builder.add("mark", "0");
        }
        Request request = new Request.Builder().url(url).post(builder.build()).build();
        callback.onBefore();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mPlatform.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (e instanceof SocketTimeoutException) {
                            callback.onError("连接超时");
                        } else if (e instanceof ConnectException) {
                            if (e.getMessage().contains(NETWORKUNREACHABLE)) {
                                callback.onError("无网络");
                            } else if (e.getMessage().contains(FAILEDCONNECT)) {
                                callback.onError("服务器开小差啦");
                            } else {
                                callback.onError("网络不给力啊");
                            }
                        } else {
                            callback.onError("服务器访问失败");
                        }
                        callback.onAfter();
                        MyLog.e("okHttpUtils ", "\n请求地址 " + url + "?" + SignUtils.mapObjcetLinkString(params));
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String jsonData = response.body().string();
                if (null != params) {
                    params.put("mark", "0");
                }
                MyLog.e("okHttpUtils ", "\n请求地址\n " + url + "?" + SignUtils.mapObjcetLinkString(params) + "\n 返回数据 \n" + jsonData);
                mPlatform.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (response.code() == NO20 * NO10) {
                            try {
                                callback.onSuccess(getGeneric(jsonData, callback));
                                callback.onSuccessString(jsonData);
                            } catch (JsonSyntaxException e) {
                                MLog.e("Json解析异常\n", "返回数据跟实体类不匹配");
                                callback.onError("数据异常");
                            } finally {
                                callback.onAfter();
                            }
                        } else {
                            callback.onError("服务器异常");
                            callback.onAfter();
                        }
                    }
                });
            }
        });
    }

    /**
     * 自动解析json至回调中的JavaBean
     *
     * @param jsonData 返回的json串
     * @param callBack 回调
     * @param <T>      实体类泛型
     * @return 返回实体类
     */
    private <T> T getGeneric(String jsonData, AbstractNetWorkCallback<T> callBack) {
        Gson gson = new Gson();
        //通过反射获取泛型的实例
        //得到这个类所实现的所有接口的集合
        Type types = callBack.getClass().getGenericSuperclass();
        //获取该接口中所有的参数
        Type[] actualTypeArguments = ((ParameterizedType) types).getActualTypeArguments();
        //取第一个参数，就是对应JavaBean
        Type type = actualTypeArguments[0];
        //通过gson转到对应的JavaBean
        T t = gson.fromJson(jsonData, type);

        return t;
    }
}
