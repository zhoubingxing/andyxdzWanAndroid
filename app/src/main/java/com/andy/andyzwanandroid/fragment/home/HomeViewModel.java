package com.andy.andyzwanandroid.fragment.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.andy.andyzwanandroid.Database.HomeInformationDao;
import com.andy.andyzwanandroid.Database.WanAndroidDataBase;
import com.andy.andyzwanandroid.application.WanAndroidApplication;
import com.andy.andyzwanandroid.bean.HomeBannerBean;
import com.andy.andyzwanandroid.bean.HomeInformationBean;
import com.andy.andyzwanandroid.bean.HomeViewBean;
import com.andy.andyzwanandroid.repository.HomeRepository;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    //首页文章列表数据
    private MutableLiveData<HomeViewBean> homeViewData;

    //banner列表数据
    private MutableLiveData<List<HomeBannerBean>> homeBannerData;

    private LiveData<List<HomeInformationBean>> homeInformationData;


    public HomeViewModel() {
        homeViewData = new MutableLiveData<>();
        homeViewData.setValue(new HomeViewBean());
        homeInformationData = HomeRepository.getHomeInformationData();
    }

    public LiveData<List<HomeInformationBean>> getHomeInformationData() {
        return homeInformationData;
    }

    public void setHomeInformationData(LiveData<List<HomeInformationBean>> homeInformationData) {
        this.homeInformationData = homeInformationData;
    }

    public MutableLiveData<List<HomeBannerBean>> getHomeBannerData() {
        return homeBannerData;
    }

    public void setHomeBannerData(MutableLiveData<List<HomeBannerBean>> homeBannerData) {
        this.homeBannerData = homeBannerData;
    }

    public MutableLiveData<HomeViewBean> getHomeViewData() {
        return homeViewData;
    }

    public void setHomeViewData(MutableLiveData<HomeViewBean> homeViewData) {
        this.homeViewData = homeViewData;
    }

}