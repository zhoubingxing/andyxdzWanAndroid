package com.andy.andyzwanandroid.repository;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.andy.andyzwanandroid.Database.WanAndroidDataBase;
import com.andy.andyzwanandroid.R;
import com.andy.andyzwanandroid.application.WanAndroidApplication;
import com.andy.andyzwanandroid.bean.HomeBannerBean;
import com.andy.andyzwanandroid.bean.HomeInformationBean;
import com.andy.andyzwanandroid.httpUtils.HttpCallBack;
import com.andy.andyzwanandroid.httpUtils.HttpManager;
import com.andy.andyzwanandroid.httpUtils.HttpParams;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HomeRepository {

     public LiveData<List<HomeInformationBean>> getHomeInformationData() {
        return WanAndroidDataBase.getDatabase(WanAndroidApplication.getInstance()).homeInformationDao().getInformationData();
    }

     public LiveData<List<HomeBannerBean>> getHomeBannerData() {
        return WanAndroidDataBase.getDatabase(WanAndroidApplication.getInstance()).homeInformationDao().getBannerData();
    }

    /**
     * 获取主页文章
     * @param curPage 请求的页码
     */
     public void loadHomeInformationData(int curPage) {
        HttpManager.getInstance(WanAndroidApplication.getInstance()).getHttpRequest(getRequestInformationUrl(curPage), new HttpParams(), new HttpCallBack() {
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
                    for (int i = 0; i < informationObjArray.size() ; i++) {
                        switch (i % 5) {
                            case 0 :
                                informationObjArray.get(i).setLayoutId(R.drawable.recycler_image1);
                                break;
                            case 1 :
                                informationObjArray.get(i).setLayoutId(R.drawable.recycler_image2);
                                break;
                            case 2 :
                                informationObjArray.get(i).setLayoutId(R.drawable.recycler_image3);
                                break;
                            case 3 :
                                informationObjArray.get(i).setLayoutId(R.drawable.recycler_image4);
                                break;
                            case 4 :
                                informationObjArray.get(i).setLayoutId(R.drawable.recycler_image5);
                                break;
                        }
                    }
                    WanAndroidDataBase.getDatabase(WanAndroidApplication.getInstance())
                            .homeInformationDao().deleteInformationData(curPage);
                    WanAndroidDataBase.getDatabase(WanAndroidApplication.getInstance())
                            .homeInformationDao().insertInformationData(informationObjArray);
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
     * 请求首页Banner数据
     */
     public void loadBannerData() {
        HttpManager.getInstance(WanAndroidApplication.getInstance()).getHttpRequest("https://www.wanandroid.com/banner/json", new HttpParams(), new HttpCallBack() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(String response) {
                try{
                    JSONObject obj = new JSONObject(response);
                    String dataJson = obj.getString("data");
                    ArrayList<HomeBannerBean> bannerObjArray = new ArrayList<>();
                    bannerObjArray = jsonToArrayList(dataJson, HomeBannerBean.class);
                    WanAndroidDataBase.getDatabase(WanAndroidApplication.getInstance())
                            .homeInformationDao().insertBannerData(bannerObjArray);
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
    public  <T> ArrayList<T> jsonToArrayList(String json, Class<T> clazz)
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

    private String getRequestInformationUrl(int curPage) {
        return "https://www.wanandroid.com/article/list/" + curPage + "/json";
    }
}
