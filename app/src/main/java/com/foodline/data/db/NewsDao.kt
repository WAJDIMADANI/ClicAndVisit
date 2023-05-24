package com.foodline.data.db


import androidx.lifecycle.LiveData
import androidx.room.*
import com.foodline.data.model.news.News


@Dao
interface NewsDao {

    @Query("SELECT count(*) FROM news")
    suspend fun getCount(): Int

    @Query("SELECT * from news")
    fun getAllNews(): LiveData<List<News>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg news: News)

    @Delete
    suspend fun delete(news: News)

    @Query("DELETE from news")
    suspend fun deleteAll()

}
