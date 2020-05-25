package com.andy.andyzwanandroid.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

}
