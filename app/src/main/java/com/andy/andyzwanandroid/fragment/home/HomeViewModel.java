package com.andy.andyzwanandroid.fragment.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.andy.andyzwanandroid.fragment.home.bean.HomeBannerBean;
import com.andy.andyzwanandroid.fragment.home.bean.HomeViewBean;

import java.util.List;

public class HomeViewModel extends ViewModel {

    //首页文章列表数据
    private MutableLiveData<HomeViewBean> homeViewData;

    //banner列表数据
    private MutableLiveData<List<HomeBannerBean>> homeBannerData;

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

    //更新首页文章数据
    public void setData(HomeViewBean.HomeNewsData data) {
        HomeViewBean temp = homeViewData.getValue();
        if (temp == null) {
            temp = new HomeViewBean();
        }
        temp.setData(data);
        homeViewData.setValue(temp);
    }

    //更新banner数据
    public void setBannerData(List<HomeBannerBean> data) {
        homeBannerData.setValue(data);
    }



    public HomeViewModel() {
        homeViewData = new MutableLiveData<>();
        homeViewData.setValue(new HomeViewBean());
    }




}