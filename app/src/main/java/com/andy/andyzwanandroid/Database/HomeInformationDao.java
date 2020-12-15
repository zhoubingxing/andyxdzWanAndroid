package com.andy.andyzwanandroid.Database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.andy.andyzwanandroid.bean.HomeInformationBean;

import java.util.List;

@Dao
public interface HomeInformationDao {

    @Query("SELECT * FROM t_home_information_data")
    LiveData<List<HomeInformationBean>> getInformationData();

    @Insert
    void insertInformationData(HomeInformationBean[] data);

}
