package com.andy.andyzwanandroid.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.andy.andyzwanandroid.bean.HomeInformationBean;

/**
 * @description:
 * @author: zhouchaoliang
 * @date :   12/14/20 10:12 AM
 */
@Database(entities = {HomeInformationBean.class}, version = 1)//申明Entity和数据库的版本号
public abstract class WanAndroidDataBase extends RoomDatabase {

    public abstract HomeInformationDao homeInformationDao();//创建DAO的抽象类

    private static WanAndroidDataBase INSTANCE;//创建单例

    public static WanAndroidDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WanAndroidDataBase.class) {
                if (INSTANCE == null)    {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            WanAndroidDataBase.class, "wan_android_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;}

}
