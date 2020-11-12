package com.andy.andyzwanandroid.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.databinding.BaseObservable;

import com.andy.andyzwanandroid.BR;
import com.andy.andyzwanandroid.R;
import com.andy.andyzwanandroid.adapter.BindingAdapter;
import com.andy.andyzwanandroid.application.WanAndroidApplication;
import com.andy.andyzwanandroid.httpUtils.HttpCallBack;
import com.andy.andyzwanandroid.httpUtils.HttpManager;
import com.andy.andyzwanandroid.httpUtils.HttpParams;
import com.andy.andyzwanandroid.utils.WanCallback;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.IntConsumer;

public class HomeViewBean extends BaseObservable implements Serializable {

    private HomeData data;
    private List<HomeRecyclerBean> list;
    private DateText dateText;

    HomeViewBean() {
        data = new HomeData();
        list = new ArrayList<>();
        dateText = new DateText();
    }

    public void load(WanCallback callback) {
        HttpManager.getInstance(WanAndroidApplication.getInstance()).getHttpRequest("https://www.wanandroid.com/article/list/1/json", new HttpParams(), new HttpCallBack() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(String response) {
                try{
                    setData(new Gson().fromJson(response,HomeViewBean.class).getData());
                    setList(new ArrayList<>(Arrays.asList(getData().getDatas())));
                    for (int i = 0; i < list.size() ; i++) {
                        switch (i % 5) {
                            case 0 :
                                list.get(i).setLayoutId(R.drawable.recycler_image1);
                                break;
                            case 1 :
                                list.get(i).setLayoutId(R.drawable.recycler_image2);
                                break;
                            case 2 :
                                list.get(i).setLayoutId(R.drawable.recycler_image3);
                                break;
                            case 3 :
                                list.get(i).setLayoutId(R.drawable.recycler_image4);
                                break;
                            case 4 :
                                list.get(i).setLayoutId(R.drawable.recycler_image5);
                                break;
                        }
                    }
                    callback.callback(null);
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

    public DateText getDateText() {
        return dateText;
    }

    public void setDateText(DateText dateText) {
        this.dateText = dateText;
    }

    public HomeData getData() {
        return data;
    }

    public void setData(HomeData data) {
        this.data = data;
        setList(new ArrayList<>(Arrays.asList(getData().getDatas())));
        for (int i = 0; i < list.size() ; i++) {
            switch (i % 5) {
                case 0 :
                    list.get(i).setLayoutId(R.drawable.recycler_image1);
                    break;
                case 1 :
                    list.get(i).setLayoutId(R.drawable.recycler_image2);
                    break;
                case 2 :
                    list.get(i).setLayoutId(R.drawable.recycler_image3);
                    break;
                case 3 :
                    list.get(i).setLayoutId(R.drawable.recycler_image4);
                    break;
                case 4 :
                    list.get(i).setLayoutId(R.drawable.recycler_image5);
                    break;
            }
        }
    }

    public List<HomeRecyclerBean> getList() {
        return list;
    }

    public void setList(List<HomeRecyclerBean> list) {
        this.list = list;
    }

    public class DateText {

        public String day;
        public String month;
        public String title;

        DateText() {
            Calendar calendar = Calendar.getInstance();
            this.month = getMonthText(calendar.get(Calendar.MONTH));
            this.day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
            this.title = getTitle(calendar.get(Calendar.HOUR_OF_DAY));
        }

        String getTitle(int hours) {
            if(hours < 7) {
                return "深夜了，请注意休息。";
            } else if (hours < 12){
                return "早上好！";
            }  else if (hours < 18){
                return "AndyWanAndroid";
            }  else {
                return "晚上好！";
            }
        }

        String getMonthText(int month) {
            switch (month + 1) {
                case 1:
                    return "一月";
                case 2:
                    return "二月";
                case 3:
                    return "三月";
                case 4:
                    return "四月";
                case 5:
                    return "五月";
                case 6:
                    return "六月";
                case 7:
                    return "七月";
                case 8:
                    return "八月";
                case 9:
                    return "九月";
                case 10:
                    return "十月";
                case 11:
                    return "十一月";
                case 12:
                    return "十二月";
            }
            return "神奇之月";




        }
    }
    public class HomeData extends BaseObservable implements Serializable {

        //页码
        int curPage;

        HomeRecyclerBean[] datas;

        public int getCurPage() {
            return curPage;
        }

        public void setCurPage(int curPage) {
            this.curPage = curPage;
        }

        public HomeRecyclerBean[] getDatas() {
            return datas;
        }

        public void setDatas(HomeRecyclerBean[] datas) {
            this.datas = datas;
        }
    }

}


