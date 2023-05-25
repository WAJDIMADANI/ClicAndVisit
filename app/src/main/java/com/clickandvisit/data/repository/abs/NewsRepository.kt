package com.clickandvisit.data.repository.abs

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.clickandvisit.data.model.news.News

interface NewsRepository {

    fun getAllNews(): LiveData<List<News>>

    @WorkerThread
    suspend fun getNewsAndCache(page: Int, loadSize: Int): List<News>

    @WorkerThread
    suspend fun getCount(): Int

    @WorkerThread
    suspend fun insertAll(vararg news: News)

    @WorkerThread
    suspend fun delete(news: News)

    @WorkerThread
    suspend fun deleteAll()
}