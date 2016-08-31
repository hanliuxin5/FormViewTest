package com.lychee.formviewtest.lychee;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by lychee on 2016/3/2.
 */
public class MyOkHttpClientManager {
    private static MyOkHttpClientManager mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;
    public static final int HTTP_CONNECT_TIMEOUT = 5 * 1000;// 建立连接的超时时间
    public static final int HTTP_SOCKET_TIMEOUT = 7 * 1000;// 输入流等待数据到达的超时时间


    public static final String TAG = "lychee";

    private MyOkHttpClientManager() {
        mOkHttpClient = new OkHttpClient();
        mOkHttpClient.setConnectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        mOkHttpClient.setReadTimeout(HTTP_SOCKET_TIMEOUT, TimeUnit.SECONDS);
        mOkHttpClient.setWriteTimeout(HTTP_SOCKET_TIMEOUT, TimeUnit.SECONDS);
        //cookie enabled
//        mOkHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        mDelivery = new Handler(Looper.getMainLooper());
    }

    public static MyOkHttpClientManager getInstance() {
        if (mInstance == null) {
            synchronized (MyOkHttpClientManager.class) {
                if (mInstance == null) {
                    mInstance = new MyOkHttpClientManager();
                }
            }
        }
        return mInstance;
    }


    /**
     * 异步的get请求
     *
     * @param url
     * @param callback
     */
    private void _getAsyn(String url, HttpCallback callback) {
        LogUtil.d("_getAsyn: " + getUrl(url, null));
        final Request request = new Request.Builder()
                .url(url)
                .build();
        deliveryResult(callback, request);
    }

    private void _getAsyn(String url, Map<String, String> params, HttpCallback callback) {
        StringBuilder sb = new StringBuilder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }

        LogUtil.d("_getAsyn: " + getUrl(url, params));
        url = url + "?" + sb;
        final Request request = new Request.Builder()
                .url(url)
                .build();
        deliveryResult(callback, request);
    }

    private void _getAsynClone(String url, HttpCallback callback) {
        LogUtil.d("_getAsyn: " + getUrl(url, null));
        final Request request = new Request.Builder()
                .url(url)
                .build();
        cloneDeliveryResult(callback, request);
    }

    /**
     * 异步的post请求
     *
     * @param url
     * @param callback
     * @param params
     */
    private void _postAsyn(String url, Map<String, String> params, HttpCallback callback) {
        LogUtil.d("_postAsyn: " + getUrl(url, params));
        Param[] paramsArr = map2Params(params);
        Request request = buildPostRequest(url, paramsArr);
        deliveryResult(callback, request);
    }


    /**
     * 异步基于post的文件上传，单文件不带参数上传
     *
     * @param url
     * @param callback
     * @param file
     * @param fileKey
     * @throws IOException
     */
    private void _postAsyn(String url, File file, String fileKey, HttpCallback callback) throws IOException {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, null);
        deliveryResult(callback, request);
    }

    /**
     * 拼接请求具体url
     *
     * @param Url
     * @param params
     * @return
     */
    private String getUrl(String Url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        return "请求地址：" + Url + "?" + sb;
    }

    //*************对外公布的方法************
    public static void getAsyn(String url, HttpCallback callback) throws IOException {
        getInstance()._getAsyn(url, callback);
    }

    public static void getAsyn(String url, Map<String, String> params, HttpCallback callback) throws IOException {
        getInstance()._getAsyn(url, params, callback);
    }

    public static void getAsynClone(String url, HttpCallback callback) throws IOException {
        getInstance()._getAsynClone(url, callback);
    }

    public static void postAsyn(String url, Map<String, String> params, HttpCallback callback) {
        getInstance()._postAsyn(url, params, callback);
    }


    public static void postAsyn(String url, File file, String fileKey, HttpCallback callback) throws IOException {
        getInstance()._postAsyn(url, file, fileKey, callback);
    }

    public void cancel() {
        mOkHttpClient.cancel(TAG);
    }
    //****************************

    private Request buildPostRequest(String url, Param[] params) {
        if (params == null) {
            params = new Param[0];
        }
        FormEncodingBuilder builder = new FormEncodingBuilder();
        for (Param param : params) {
            String value = !TextUtils.isEmpty(param.value) ? param.value : "";
            builder.add(param.key, value);
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .tag(TAG)
                .url(url)
                .post(requestBody)
                .build();
    }

    private Request buildMultipartFormRequest(String url, File[] files,
                                              String[] fileKeys, Param[] params) {
        params = validateParam(params);

        MultipartBuilder builder = new MultipartBuilder()
                .type(MultipartBuilder.FORM);

        for (Param param : params) {
            builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + param.key + "\""),
                    RequestBody.create(null, param.value));
        }
        if (files != null) {
            RequestBody fileBody = null;
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                String fileName = file.getName();
                fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
                builder.addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"" + fileKeys[i] + "\"; filename=\"" + fileName + "\""),
                        fileBody);
            }
        }

        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .tag(TAG)
                .url(url)
                .post(requestBody)
                .build();
    }

    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }


    private Param[] validateParam(Param[] params) {
        if (params == null)
            return new Param[0];
        else return params;
    }

    private Param[] map2Params(Map<String, String> params) {
        if (params == null) return new Param[0];
        int size = params.size();
        Param[] res = new Param[size];
        Set<Map.Entry<String, String>> entries = params.entrySet();
        int i = 0;
        for (Map.Entry<String, String> entry : entries) {
            res[i++] = new Param(entry.getKey(), entry.getValue());
        }
        return res;
    }

    private void deliveryResult(final HttpCallback callback, Request request) {

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(final Response response) {
                try {
                    final String string = response.body().string();
//                    LogUtil.i("deliveryResult.onResponse：" + string);
                    sendSuccessResultCallback(string, callback);
                } catch (IOException e) {
                    sendFailedStringCallback(response.request(), e, callback);
                }
            }

            @Override
            public void onFailure(final Request request, final IOException e) {
                sendFailedStringCallback(request, e, callback);
            }
        });

    }

    private void cloneDeliveryResult(final HttpCallback callback, Request request) {

        mOkHttpClient.clone().newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(final Response response) {
                try {
                    final String string = response.body().string();
//                    LogUtil.i("deliveryResult.onResponse：" + string);
                    sendSuccessResultCallback(string, callback);
                } catch (IOException e) {
                    sendFailedStringCallback(response.request(), e, callback);
                }
            }

            @Override
            public void onFailure(final Request request, final IOException e) {
                sendFailedStringCallback(request, e, callback);
            }
        });

    }

    private void sendFailedStringCallback(final Request request, final Exception e, final HttpCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null)
                    try {
                        callback.onError(e.getMessage());
                    } catch (Exception e1) {
                        //去你妹的空指针
                    }
            }
        });
    }

    private void sendSuccessResultCallback(final String object, final HttpCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    try {
                        callback.onResponse(object);
                    } catch (Exception e) {
                        //去你妹的空指针
                    }
                }
            }
        });
    }


    public static class Param {
        public Param() {
        }

        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }

        String key;
        String value;
    }


}
