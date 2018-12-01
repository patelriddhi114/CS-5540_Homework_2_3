package com.example.rkjc.news_app_2.HW2;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.rkjc.news_app_2.HW2.NewsItem;

import java.util.List;

@Dao
public interface NewsItemDao
{
    //insert(list<newsitems>)
    @Insert
    void insert(List<NewsItem> newsItem);
//clearall
    @Query("DELETE FROM news_item")
    void deleteAll();
//loadallnewsitems
    @Query("SELECT * FROM news_item ORDER BY id ASC")
    LiveData<List<NewsItem>> getAllNewsItems();
}