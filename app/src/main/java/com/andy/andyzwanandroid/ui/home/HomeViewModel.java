package com.andy.andyzwanandroid.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.andy.andyzwanandroid.R;
import com.andy.andyzwanandroid.adapter.BindingAdapter;
import com.andy.andyzwanandroid.databinding.FragmentHomeBinding;
import com.andy.andyzwanandroid.httpUtils.HttpCallBack;
import com.andy.andyzwanandroid.httpUtils.HttpManager;
import com.andy.andyzwanandroid.httpUtils.HttpParams;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.IntConsumer;

public class HomeViewModel extends ViewModel {

    private Activity activity;
    private FragmentHomeBinding binding;

    private HomeViewBean homeViewBean;

    public HomeViewModel(Activity activity, FragmentHomeBinding binding) {
        this.activity = activity;
        this.binding = binding;
        homeViewBean = new HomeViewBean();
        homeViewBean.load();
    }

    //liveData样式
    private MutableLiveData<String> mText;
    public LiveData<String> getText() {
        return mText;
    }

    public void setText(String mText) {
        this.mText.setValue(mText);
    }
    //end

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }




}