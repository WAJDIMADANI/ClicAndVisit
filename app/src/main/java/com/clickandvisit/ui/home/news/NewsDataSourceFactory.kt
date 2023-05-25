package com.clickandvisit.ui.home.news

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.clickandvisit.data.model.news.News
import com.clickandvisit.data.repository.abs.NewsRepository
import com.clickandvisit.global.listener.SchedulerProvider
import kotlinx.coroutines.CoroutineScope

class NewsDataSourceFactory(
    private val coroutineScope: CoroutineScope,
    private val schedulerProvider: SchedulerProvider,
    private val newsRepository: NewsRepository,
    private val firstPage: Int
) : DataSource.Factory<Int, News>() {

    private var responseFirstPage : List<News> ? =null

    val newsDataSourceLiveData = MutableLiveData<NewsDataSource>()

    override fun create(): DataSource<Int, News> {
        val newsDataSource = NewsDataSource(coroutineScope, schedulerProvider, newsRepository, firstPage,responseFirstPage)
        newsDataSourceLiveData.postValue(newsDataSource)
        responseFirstPage=null
        return newsDataSource
    }

    fun refreshData(response: List<News>) {
        responseFirstPage = response
        newsDataSourceLiveData.value?.invalidate()
    }
}