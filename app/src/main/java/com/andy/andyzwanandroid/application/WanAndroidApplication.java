package com.andy.andyzwanandroid.application;

import android.app.Application;
import android.content.Context;

import com.andy.andyzwanandroid.httpUtils.HttpManager;
import com.andy.andyzwanandroid.utils.CrashHandler;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class WanAndroidApplication extends Application {

    public  static  WanAndroidApplication  app;

    private static RefWatcher sRefWatcher;

    public  static  WanAndroidApplication  getInstance(){
        return  app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        //leakCanary内存泄漏检测初始化
        sRefWatcher = LeakCanary.install(this);

        //崩溃异常捕获初始化
        CrashHandler.getInstance().init(this);
    }

    public static RefWatcher getRefWatcher() {
        return sRefWatcher;
    }

}
