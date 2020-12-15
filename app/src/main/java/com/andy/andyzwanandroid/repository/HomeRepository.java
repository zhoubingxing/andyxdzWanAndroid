package com.andy.andyzwanandroid.repository;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.andy.andyzwanandroid.Database.WanAndroidDataBase;
import com.andy.andyzwanandroid.application.WanAndroidApplication;
import com.andy.andyzwanandroid.bean.HomeBannerBean;
import com.andy.andyzwanandroid.bean.HomeInformationBean;
import com.andy.andyzwanandroid.httpUtils.HttpCallBack;
import com.andy.andyzwanandroid.httpUtils.HttpManager;
import com.andy.andyzwanandroid.httpUtils.HttpParams;
import com.andy.andyzwanandroid.bean.HomeViewBean;
import com.andy.andyzwanandroid.utils.WanCallback;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeRepository {

    static public LiveData<List<HomeInformationBean>> getHomeInformationData() {
        return WanAndroidDataBase.getDatabase(WanAndroidApplication.getInstance()).homeInformationDao().getInformationData();
    }


    //获取首页文章列表
    static public void loadHomeInformationData() {
        HttpManager.getInstance(WanAndroidApplication.getInstance()).getHttpRequest("https://www.wanandroid.com/article/list/0/json", new HttpParams(), new HttpCallBack() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(String response) {
                try{
                    JSONObject obj = new JSONObject(response);
                    String dataJson = obj.getJSONObject("data").getString("datas");
                    int curPage = obj.getJSONObject("data").getInt("curPage");
                    ArrayList<HomeInformationBean> informationObjArray = new ArrayList<>();
                    informationObjArray = jsonToArrayList(dataJson, HomeInformationBean.class);

                    for (HomeInformationBean homeInformationBean : informationObjArray) {
                        homeInformationBean.setCurPage(curPage);
                    }
                    WanAndroidDataBase.getDatabase(WanAndroidApplication.getInstance())
                            .homeInformationDao().insertInformationData((HomeInformationBean[])informationObjArray.toArray());
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

    /**
     * @param json
     * @param clazz
     * @return
     */
    public static <T> ArrayList<T> jsonToArrayList(String json, Class<T> clazz)
    {
        Type type = new TypeToken<ArrayList<JsonObject>>()
        {}.getType();
        ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);

        ArrayList<T> arrayList = new ArrayList<>();
        for (JsonObject jsonObject : jsonObjects)
        {
            arrayList.add(new Gson().fromJson(jsonObject, clazz));
        }
        return arrayList;
    }
}
