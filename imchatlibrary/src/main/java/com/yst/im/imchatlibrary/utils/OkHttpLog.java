package com.yst.im.imchatlibrary.utils;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

import static com.yst.im.imchatlibrary.utils.DefendMpUtils.HTML;
import static com.yst.im.imchatlibrary.utils.DefendMpUtils.JSON;
import static com.yst.im.imchatlibrary.utils.DefendMpUtils.TEXT;
import static com.yst.im.imchatlibrary.utils.DefendMpUtils.WEB_HTML;
import static com.yst.im.imchatlibrary.utils.DefendMpUtils.XML;

/**
 * okhttp 请求接口日志
 *
 * @author Qinchaoshuai
 * @date 2018/04/10
 * @version 1.0.0
 */

public class OkHttpLog implements Interceptor {
    public static final String TAG = "OkHttpUtils";
    private String tag;
    private boolean showResponse;

    public OkHttpLog(String tag, boolean showResponse)
    {
        if (TextUtils.isEmpty(tag))
        {
            tag = TAG;
        }
        this.showResponse = showResponse;
        this.tag = tag;
    }

    public OkHttpLog(String tag)
    {
        this(tag, false);
    }

    @Override
    public Response intercept(Chain chain) throws IOException
    {
        Request request = chain.request();
        logForRequest(request);
        Response response = chain.proceed(request);
        return logForResponse(response);
    }

    private Response logForResponse(Response response)
    {
        try
        {
            //===>response log
            Log.e(tag, "========response'log=======");
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
            Log.e(tag, "url : " + clone.request().url());
            Log.e(tag, "code : " + clone.code());
            Log.e(tag, "protocol : " + clone.protocol());
            if (!TextUtils.isEmpty(clone.message())) {
                Log.e(tag, "message : " + clone.message());
            }

            if (showResponse)
            {
                ResponseBody body = clone.body();
                if (body != null)
                {
                    MediaType mediaType = body.contentType();
                    if (mediaType != null)
                    {
                        Log.e(tag, "responseBody's contentType : " + mediaType.toString());
                        if (isText(mediaType))
                        {
                            String resp = body.string();
                            Log.e(tag, "responseBody's content : " + resp);

                            body = ResponseBody.create(mediaType, resp);
                            return response.newBuilder().body(body).build();
                        } else
                        {
                            Log.e(tag, "responseBody's content : " + " maybe [file part] , too large too print , ignored!");
                        }
                    }
                }
            }

            Log.e(tag, "========response'log=======end");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    private void logForRequest(Request request)
    {
        try
        {
            String url = request.url().toString();
            Headers headers = request.headers();

            Log.e(tag, "========request'log=======");
            Log.e(tag, "method : " + request.method());
            Log.e(tag, "url : " + url);
            if (headers != null && headers.size() > 0)
            {
                Log.e(tag, "headers : " + headers.toString());
            }
            RequestBody requestBody = request.body();
            if (requestBody != null)
            {
                Log.d(this.tag, "tag:" + request.tag() + " request : " + url + "?" + this.bodyToString(request));
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null)
                {
                    Log.e(tag, "requestBody's contentType : " + mediaType.toString());
                    if (isText(mediaType))
                    {
                        Log.e(tag, "requestBody's content : " + bodyToString(request));
                    } else
                    {
                        Log.e(tag, "requestBody's content : " + " maybe [file part] , too large too print , ignored!");
                    }
                }
            }
            Log.e(tag, "========request'log=======end");
        } catch (Exception e)
        {
//            e.printStackTrace();
        }
    }

    private boolean isText(MediaType mediaType)
    {
        if (mediaType.type() != null && TEXT.equals(mediaType.type()))
        {
            return true;
        }
        if (mediaType.subtype() != null)
        {
            if (JSON.equals(mediaType.subtype()) ||
                    XML.equals(mediaType.subtype()) ||
                    HTML.equals(mediaType.subtype()) ||
                    WEB_HTML.equals(mediaType.subtype())
                    ) {
                return true;
            }
        }
        return false;
    }

    private String bodyToString(final Request request)
    {
        try
        {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e)
        {
            return "something error when show requestBody.";
        }
    }
}
