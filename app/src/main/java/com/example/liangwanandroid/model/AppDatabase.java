package com.example.liangwanandroid.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.liangwanandroid.model.common.SearchHistoryDao;
import com.example.liangwanandroid.model.common.bean.SearchHistoryBean;

@Database(entities = {SearchHistoryBean.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "ADDataBase_db";

    private static volatile AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                    .allowMainThreadQueries()
                    .build();
        }

        return instance;
    }

    public abstract SearchHistoryDao searchHistoryDao();
}
