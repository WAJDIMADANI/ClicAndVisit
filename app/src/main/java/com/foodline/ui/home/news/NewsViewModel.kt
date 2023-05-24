package com.foodline.ui.home.news

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.foodline.R
import com.foodline.base.BaseAndroidViewModel
import com.foodline.data.model.news.News
import com.foodline.data.repository.abs.NewsRepository
import com.foodline.data.retrofit.EndpointInterceptor
import com.foodline.global.helper.Navigation
import com.foodline.global.helper.PaginationState
import com.foodline.global.helper.RefreshState
import com.foodline.global.listener.OnItemClickedListener
import com.foodline.global.listener.RetryListener
import com.foodline.global.listener.SchedulerProvider
import com.foodline.global.listener.ToolBarListener
import com.foodline.global.utils.ExtraKeys
import com.foodline.global.utils.tryCatch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

const val NEWS_PAGE_SIZE = 10
const val FIRST_PAGE = 0;


@HiltViewModel
class NewsViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider, private val newsRepository: NewsRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseAndroidViewModel(application, schedulerProvider), ToolBarListener, RetryListener,
    OnItemClickedListener {

    private val arg1 = savedStateHandle.get<String>(ExtraKeys.NewsActivity.NEWS_EXTRA_VAR_KEY)!!
    var newsList: LiveData<PagedList<News>>

    private var refreshState: MutableLiveData<RefreshState> = MutableLiveData()


    private val newsDataSourceFactory: NewsDataSourceFactory =
        NewsDataSourceFactory(viewModelScope, schedulerProvider, newsRepository, FIRST_PAGE)

    init {

        val config = PagedList.Config.Builder().setPageSize(NEWS_PAGE_SIZE)
            .setInitialLoadSizeHint(NEWS_PAGE_SIZE * 1).setEnablePlaceholders(false).build()

        newsList = LivePagedListBuilder(newsDataSourceFactory, config).build()

        shownSnackBarMessage(arg1)
    }

    var state = newsDataSourceFactory.newsDataSourceLiveData.switchMap {
        it.paginationState
    }

    var isEmpty = state.map {
        if (it is PaginationState.LoadingInitialDone) {
            it.isEmpty
        } else {
            false
        }
    }

    var isLoading = state.map {
        it == PaginationState.LoadingInitial
    }

    var errorMessage = state.map {
        if (it is PaginationState.LoadingInitialError) {
            when (it.throwable) {
                is EndpointInterceptor.NetworkNotFoundException -> {
                    applicationContext.getString(R.string.global_error_unavailable_network)
                }
                is HttpException -> {
                    when (it.throwable.code()) {
                        //other default handler
                        else -> applicationContext.getString(R.string.global_error_server)
                    }
                }
                else -> {
                    applicationContext.getString(R.string.global_error_server)
                }
            }
        } else {
            ""
        }
    }

    var isError = errorMessage.map {
        it.isBlank().not()
    }

    var isRefreshing = refreshState.map {
        it == RefreshState.Refreshing
    }

    override fun onItemClicked(value: String) {
        shownSnackBarMessage(value)
    }

    override fun onRetry() {
        newsDataSourceFactory.newsDataSourceLiveData.value?.retry()
    }

    override fun onStartClicked() {
        navigate(Navigation.Back)
    }


    fun onStart() {
        viewModelScope.launch {
            onRefresh()
        }
    }


    fun onRefresh() {
        val state =
            newsDataSourceFactory.newsDataSourceLiveData.value?.paginationState?.value
        if (refreshState.value == RefreshState.Refreshing || state == null || (state == PaginationState.LoadingInitial)) {
            return
        }
        updateState(RefreshState.Refreshing)
        viewModelScope.launch {
            tryCatch({
                val response = withContext(schedulerProvider.dispatchersIO()) {
                    newsRepository.getNewsAndCache(FIRST_PAGE, NEWS_PAGE_SIZE)
                }
                updateState(RefreshState.RefreshingDone)
                newsDataSourceFactory.refreshData(response)
            }, {
                updateState(RefreshState.RefreshingError(it))
                shownSnackBarMessage(R.string.global_error_refresh_fail)
            })
        }
    }


    private fun updateState(refreshState: RefreshState) {
        this.refreshState.value = refreshState
    }
}