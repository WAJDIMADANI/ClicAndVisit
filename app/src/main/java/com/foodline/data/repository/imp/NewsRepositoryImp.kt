package com.foodline.data.repository.imp

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.foodline.base.BaseRepository
import com.foodline.data.db.Database
import com.foodline.data.model.news.News
import com.foodline.data.repository.abs.NewsRepository
import com.foodline.data.retrofit.APIClient
import com.foodline.global.helper.SharedPreferences
import com.foodline.ui.home.news.FIRST_PAGE
import javax.inject.Inject

class NewsRepositoryImp @Inject constructor(
    apiClient: APIClient,
    sharedPreferences: SharedPreferences,
    database: Database
) : BaseRepository(apiClient, sharedPreferences, database), NewsRepository {

    @WorkerThread
    override suspend fun deleteAll() {
        database.newsDao().deleteAll()
    }


    @WorkerThread
    override suspend fun getNewsAndCache(page: Int, loadSize: Int): List<News> {
        val news = apiClient.getNews(page, loadSize)
        if (page == FIRST_PAGE) database.newsDao().deleteAll()
        if (news.isNotEmpty()) database.newsDao().insertAll(*news.toTypedArray())
        return news
    }

    @WorkerThread
    override suspend fun getCount(): Int {
        return database.newsDao().getCount()
    }


    override fun getAllNews(): LiveData<List<News>> {
        return database.newsDao().getAllNews()
    }


    @WorkerThread
    override suspend fun insertAll(vararg news: News) {
        database.newsDao().insertAll(*news)
    }

    @WorkerThread
    override suspend fun delete(news: News) {
        database.newsDao().delete(news)
    }
}