package com.andy.andyzwanandroid.fragment.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.andy.andyzwanandroid.bean.HomeBannerBean;
import com.andy.andyzwanandroid.bean.HomeInformationBean;
import com.andy.andyzwanandroid.bean.HomeTitleData;
import com.andy.andyzwanandroid.repository.HomeRepository;
import java.util.List;

public class HomeViewModel extends ViewModel {

    //首页文章列表数据
    private MutableLiveData<HomeTitleData> homeViewData;

    //banner列表数据
    private LiveData<List<HomeBannerBean>> homeBannerData;

    //首页文章列表数据
    private LiveData<List<HomeInformationBean>> homeInformationData;

    public HomeViewModel() {
        homeViewData = new MutableLiveData<>();
        homeViewData.setValue(new HomeTitleData());
        homeInformationData = HomeRepository.getHomeInformationData();
        homeBannerData = HomeRepository.getHomeBannerData();
    }

    public MutableLiveData<HomeTitleData> getHomeViewData() {
        return homeViewData;
    }

    public void setHomeViewData(MutableLiveData<HomeTitleData> homeViewData) {
        this.homeViewData = homeViewData;
    }

    public LiveData<List<HomeBannerBean>> getHomeBannerData() {
        return homeBannerData;
    }

    public void setHomeBannerData(LiveData<List<HomeBannerBean>> homeBannerData) {
        this.homeBannerData = homeBannerData;
    }

    public LiveData<List<HomeInformationBean>> getHomeInformationData() {
        return homeInformationData;
    }

    public void setHomeInformationData(LiveData<List<HomeInformationBean>> homeInformationData) {
        this.homeInformationData = homeInformationData;
    }
}