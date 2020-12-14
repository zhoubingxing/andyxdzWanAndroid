package com.andy.andyzwanandroid.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.andy.andyzwanandroid.fragment.home.bean.HomeRecyclerBean;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface HomeInformationDao {

    @Query("SELECT * FROM t_home_information_data")
    LiveData<List<HomeRecyclerBean>> getInformationData();

    @Insert
    void insertInformationData(HomeRecyclerBean[] data);

}
