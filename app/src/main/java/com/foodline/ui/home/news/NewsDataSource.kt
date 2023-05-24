package com.foodline.ui.home.news

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.foodline.data.model.news.News
import com.foodline.data.repository.abs.NewsRepository
import com.foodline.global.helper.PaginationState
import com.foodline.global.listener.SchedulerProvider
import com.foodline.global.utils.tryCatch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class NewsDataSource(
    private val viewModelScope: CoroutineScope,
    private val schedulerProvider: SchedulerProvider,
    private val newsRepository: NewsRepository,
    private val firstPage: Int,
    private var responseFirstPage: List<News>?
) : PageKeyedDataSource<Int, News>() {

    var paginationState: MutableLiveData<PaginationState> = MutableLiveData(
        PaginationState.LoadingInitial)

    private var retryBlock: (() -> Unit)? = null

    private var job: Job? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, News>
    ) {
        responseFirstPage?.let {
            fetchRefresh(callback, it);
        } ?: run {
            fetchLoadInitial(params, callback)
        }
    }

    private fun fetchLoadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, News>
    ) {
        viewModelScope.launch {
            updateState(PaginationState.LoadingInitial)
            tryCatch({
                val response = withContext(schedulerProvider.dispatchersIO()) {
                    newsRepository.getNewsAndCache(firstPage, params.requestedLoadSize)
                }
                callback.onResult(
                    response,
                    null,
                    firstPage + 1
                )
                updateState(PaginationState.LoadingInitialDone(response.isEmpty()))
            }, {
                setRetry { loadInitial(params, callback) }
                updateState(PaginationState.LoadingInitialError(it))
            })
        }
    }

    private fun fetchRefresh(
        callback: LoadInitialCallback<Int, News>,
        it: List<News>
    ) {

        viewModelScope.launch {
            job?.cancel()
            updateState(PaginationState.LoadingDone)
            callback.onResult(
                it,
                null,
                firstPage + 1
            )
            updateState(PaginationState.LoadingInitialDone(it.isEmpty()))
            responseFirstPage = null
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {
        job = viewModelScope.launch {
            updateState(PaginationState.Loading)
            tryCatch({
                val response = withContext(schedulerProvider.dispatchersIO()) {
                    newsRepository.getNewsAndCache(params.key, params.requestedLoadSize)
                }
                callback.onResult(
                    response,
                    params.key + 1
                )
                updateState(PaginationState.LoadingDone)
            }, {
                setRetry { loadAfter(params, callback) }
                updateState(PaginationState.LoadingError(it))
            })
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {
    }

    private fun updateState(newPaginationState: PaginationState) {
        paginationState.value = newPaginationState
    }


    fun retry() {
        retryBlock?.let {
            it()
        }
    }

    private fun setRetry(block: (() -> Unit)? = null) {
        retryBlock = block
    }
}