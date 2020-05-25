package com.andy.andyzwanandroid.httpUtils;

import com.andy.andyzwanandroid.config.Constants;

import java.io.File;
import java.util.HashMap;

public class HttpParams {

    //请求类型常量
    private static final String METHOD_GET = "get";
    private static final String METHOD_POST = "post";

    private HashMap<String, String> urlParams = new HashMap<>();
    private HashMap<String, File> fileParams = new HashMap<>();
    private HashMap<String, String> headers = new HashMap<>();

    private String baseUrl;
    private String api;
    private String method;

    public HttpParams() {
        urlParams = new HashMap<String, String>();
        fileParams = new HashMap<String, File>();
        headers = new HashMap<String, String>();
        setBaseUrl(Constants.BASE_URL);
        setMethod(METHOD_POST);

        //统一添加请求头
        headers.put("Accept", "application/json");
    }

    public void put(String key, String value) {
        urlParams.put(key, value);
    }

    public void put(String key, int value) {
        urlParams.put(key, String.valueOf(value));
    }

    public void put(String key, double value) {
        urlParams.put(key, String.valueOf(value));
    }

    public void put(String key, float value) {
        urlParams.put(key, String.valueOf(value));
    }

    public HashMap<String, String> getUrlParams() {
        return urlParams;
    }

    public void setUrlParams(HashMap<String, String> urlParams) {
        this.urlParams = urlParams;
    }

    public HashMap<String, File> getFileParams() {
        return fileParams;
    }

    public void setFileParams(HashMap<String, File> fileParams) {
        this.fileParams = fileParams;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return baseUrl + api;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
