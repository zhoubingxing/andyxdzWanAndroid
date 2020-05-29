package com.andy.andyzwanandroid.application;

import android.app.Application;
import android.content.Context;

import com.andy.andyzwanandroid.httpUtils.HttpManager;

public class WanAndroidApplication extends Application {

    public  static  WanAndroidApplication  app;

    public  static  WanAndroidApplication  getInstance(){
        return  app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

}
