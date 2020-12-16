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

    //
    private HomeRepository homeRepository;

    //当前最后文章列表页数
    private int currentPage = 0;

    /**
     * 初始化各数据
     */
    public HomeViewModel() {
        homeRepository = new HomeRepository();
        homeViewData = new MutableLiveData<>();
        homeViewData.setValue(new HomeTitleData());
        homeInformationData = homeRepository.getHomeInformationData();
        homeBannerData = homeRepository.getHomeBannerData();
    }

    /**
     * 首次进入Home画面请求数据
     */
    public void getServiceData() {
        //http请求banner数据
        homeRepository.loadBannerData();
        //http请求首页文章数据
        homeRepository.loadHomeInformationData(currentPage);
    }

    /**
     * 点击刷新按钮,刷新page为0的数据
     */
    public void refreshHomeInformation() {
        homeRepository.loadHomeInformationData(0);
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