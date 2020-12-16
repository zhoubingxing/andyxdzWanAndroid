package com.andy.andyzwanandroid.Database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.andy.andyzwanandroid.bean.HomeBannerBean;
import com.andy.andyzwanandroid.bean.HomeInformationBean;

import java.util.List;

@Dao
public interface HomeInformationDao {

    @Query("SELECT * FROM t_home_information_data")
    LiveData<List<HomeInformationBean>> getInformationData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertInformationData(List<HomeInformationBean> data);

    @Query("DELETE  FROM t_home_information_data WHERE curPage=:curPage")
    void deleteInformationData(int curPage);

    @Query("SELECT * FROM t_home_banner_data")
    LiveData<List<HomeBannerBean>> getBannerData();

    @Insert
    void insertBannerData(List<HomeBannerBean> data);
}
