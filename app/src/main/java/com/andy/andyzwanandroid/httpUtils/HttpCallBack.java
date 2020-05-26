package com.andy.andyzwanandroid.httpUtils;

import okhttp3.Response;

public interface HttpCallBack  {

    //请求成功的回调
    void onSuccess(String response);

    //请求失败的回调
    void onFailure();

    //请求超时的回调
    void onSocketTimeout();
}
