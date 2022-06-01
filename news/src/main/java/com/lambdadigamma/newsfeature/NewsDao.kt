package com.lambdadigamma.newsfeature

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(news: List<RssItem>): List<Long>

    @Query("SELECT * FROM newsItems")
    fun getNews(): LiveData<List<RssItem>>

}