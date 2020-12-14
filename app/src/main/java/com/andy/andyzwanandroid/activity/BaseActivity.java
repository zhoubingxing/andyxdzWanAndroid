package com.andy.andyzwanandroid.activity;

import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.andy.andyzwanandroid.R;
import com.andy.andyzwanandroid.application.WanAndroidApplication;
import com.andy.andyzwanandroid.utils.StatusBarUtil;
import com.google.gson.Gson;


public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //开启屏幕适配
        setCoustomDensity();
        super.onCreate(savedInstanceState);

        //设置白色状态栏
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StatusBarUtil.setLightStatusBar(this, true);//黑色文字
            StatusBarUtil.setStatusBarColor(this, R.color.white);//白色背景
        }else {
            //6.0以下默认白色文字
            StatusBarUtil.setStatusBarColor(this, R.color.black);//黑色背景
        }
        initView();
        Log.d("Activity生命周期", this.getLocalClassName() + "---------> onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Activity生命周期", this.getLocalClassName() + "---------> onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Activity生命周期", this.getLocalClassName() + "---------> onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Activity生命周期", this.getLocalClassName() + "---------> onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Activity生命周期", this.getLocalClassName() + "---------> onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Activity生命周期", this.getLocalClassName() + "---------> onDestroy");
    }

    abstract void initView();

    //今日头条屏幕适配 目标dp：360 以pixel3a为例
    void setCoustomDensity() {
        final DisplayMetrics appDisplayMetrics = WanAndroidApplication.getInstance().getApplicationContext()
                .getResources().getDisplayMetrics();
        final float targetDensity = appDisplayMetrics.widthPixels / 410;
        final int targetDensityDpi = (int)(targetDensity * 160);

        appDisplayMetrics.density =  targetDensity;
        appDisplayMetrics.scaledDensity = targetDensity * (appDisplayMetrics.scaledDensity / appDisplayMetrics.density);
        appDisplayMetrics.densityDpi = targetDensityDpi;

        final DisplayMetrics activityDisplayMetrics = this.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = activityDisplayMetrics.scaledDensity = targetDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }
}
