package com.andy.andyzwanandroid.httpUtils;



import android.content.Context;
import android.os.Handler;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpManager {


    private volatile static HttpManager httpManager;

    private OkHttpClient okHttpClient;

    private Handler handler;

    //volatile + 双重检查
    public static HttpManager getInstance(Context context) {
        if(httpManager == null) {
            synchronized (HttpManager.class) {
                if(httpManager == null) {
                    httpManager = new HttpManager(context);
                }
            }
        }
        return httpManager;
    }

    private HttpManager(Context context) {
        File sdcache = context.getExternalCacheDir();
        int cacheSize = 10 * 1024 * 1024;
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .cache(new Cache(sdcache.getAbsoluteFile(), cacheSize));
        okHttpClient = builder.build();
        handler = new Handler();
    }

    //get异步请求
    public void getHttpRequest(String url, HttpParams params, HttpCallBack callBack) {
        if (params!=null){
            StringBuilder stringBuilder = new StringBuilder(url).append("?");
            for (Map.Entry<String, String> entry : params.getUrlParams().entrySet()){
                stringBuilder.append(entry.getKey())
                        .append("=")
                        .append(entry.getValue())
                        .append("&");
            }
            // 去除最后的&
            url = stringBuilder.substring(0,stringBuilder.length()-1);
        }
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.post(()->{
                    callBack.onFailure();
                    Log.e(HttpManager.class.toString(),e.toString());
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                handler.post(()->{
                    callBack.onSuccess(response.body().toString());
                });
            }
        });
    }

    //post异步请求
    public void postHttpRequest(String url, HttpParams params, HttpCallBack callBack) {
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        //添加参数
        if(params != null) {
            for (Map.Entry<String,String> entry: params.getUrlParams().entrySet()){
                bodyBuilder.add(entry.getKey(),entry.getValue());
            }
        }
        //构造请求体
        FormBody formBody = bodyBuilder.build();

        final Request request = new Request.Builder()
                .post(formBody)
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.post(()->{
                    callBack.onFailure();
                    Log.e(HttpManager.class.toString(),e.toString());
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                handler.post(()->{
                    callBack.onSuccess(response.body().toString());
                });
            }
        });
    }

}