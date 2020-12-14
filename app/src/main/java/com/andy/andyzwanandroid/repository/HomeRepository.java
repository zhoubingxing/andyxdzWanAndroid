package com.andy.andyzwanandroid.repository;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.andy.andyzwanandroid.Database.WanAndroidDataBase;
import com.andy.andyzwanandroid.application.WanAndroidApplication;
import com.andy.andyzwanandroid.fragment.home.bean.HomeBannerBean;
import com.andy.andyzwanandroid.fragment.home.bean.HomeRecyclerBean;
import com.andy.andyzwanandroid.httpUtils.HttpCallBack;
import com.andy.andyzwanandroid.httpUtils.HttpManager;
import com.andy.andyzwanandroid.httpUtils.HttpParams;
import com.andy.andyzwanandroid.fragment.home.bean.HomeViewBean;
import com.andy.andyzwanandroid.utils.WanCallback;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;

public class HomeRepository {

//    static public LiveData<HomeViewBean.HomeInformationData> getHomeInformationData (int page) {
//        MutableLiveData<HomeViewBean.HomeInformationData>
//        HomeViewBean.HomeInformationData result = new HomeViewBean.HomeInformationData();
//        result.setCurPage(page);
//    }


    //获取首页文章列表
    static public void loadHomeInformationData(WanCallback callback) {
        HttpManager.getInstance(WanAndroidApplication.getInstance()).getHttpRequest("https://www.wanandroid.com/article/list/0/json", new HttpParams(), new HttpCallBack() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(String response) {
                try{
                    HomeViewBean.HomeInformationData informationData  = new Gson().fromJson(response, HomeViewBean.class).getData();
//                    callback.callback(data);

                    for (HomeRecyclerBean homeRecyclerBean : informationData.getDatas()) {
                        homeRecyclerBean.setCurPage(informationData.getCurPage());
                    }
                    WanAndroidDataBase.getDatabase(WanAndroidApplication.getInstance())
                            .homeInformationDao().insertInformationData(informationData.getDatas());
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure() {
                Log.d("OKhttp","onFailure");
            }

            @Override
            public void onSocketTimeout() {
                Log.d("OKhttp","onSocketTimeout");
            }
        });
    }

    static public void loadBannerData(WanCallback callback) {
        HttpManager.getInstance(WanAndroidApplication.getInstance()).getHttpRequest("https://www.wanandroid.com/banner/json", new HttpParams(), new HttpCallBack() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(String response) {
                try{
                    HomeViewBean.HomeBannerData data  = new Gson().fromJson(response, HomeViewBean.HomeBannerData.class);
                    ArrayList<HomeBannerBean> dataList = new ArrayList<HomeBannerBean>(data.getData().length);
                    Collections.addAll(dataList,data.getData());
                    callback.callback(dataList);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure() {
                Log.d("OKhttp","onFailure");
            }

            @Override
            public void onSocketTimeout() {
                Log.d("OKhttp","onSocketTimeout");
            }
        });
    }
}
