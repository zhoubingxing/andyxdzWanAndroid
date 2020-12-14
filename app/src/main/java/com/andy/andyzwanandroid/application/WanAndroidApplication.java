package com.andy.andyzwanandroid.application;

import android.app.Application;
import android.content.Context;

import com.andy.andyzwanandroid.httpUtils.HttpManager;
import com.andy.andyzwanandroid.utils.CrashHandler;

import leakcanary.LeakCanary;

public class WanAndroidApplication extends Application {

    public  static  WanAndroidApplication  app;

    public  static  WanAndroidApplication  getInstance(){
        return  app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        //崩溃异常捕获初始化
        CrashHandler.getInstance().init(this);
    }

}
