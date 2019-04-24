package com.example.liangwanandroid.model.common;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.liangwanandroid.model.common.bean.SearchHistoryBean;

import java.util.List;

@Dao
public interface SearchHistoryDao {

    /*====================== update ==========================*/
    @Query("SELECT * FROM taghistory WHERE tag = :tag")
    SearchHistoryBean queryByTag(String tag);

    @Query("SELECT * FROM taghistory")
    List<SearchHistoryBean> queryAll();

    /*====================== update ==========================*/

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(SearchHistoryBean historyBean);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(SearchHistoryBean... historyBeans);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(List<SearchHistoryBean> searchHistoryBeans);

    /*====================== delete ==========================*/

    @Delete
    void delete(SearchHistoryBean searchHistoryBean);

    @Delete
    void delete(SearchHistoryBean... searchHistoryBeans);

    @Delete
    void delete(List<SearchHistoryBean> searchHistoryBeans);

    @Query("DELETE FROM taghistory")
    void clear();
    /*====================== insert ==========================*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SearchHistoryBean searchHistoryBean);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SearchHistoryBean... searchHistoryBeans);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<SearchHistoryBean> searchHistoryBeans);
}
