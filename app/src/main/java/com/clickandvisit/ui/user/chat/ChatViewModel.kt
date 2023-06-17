package com.clickandvisit.ui.user.chat

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.model.chat.Discussion
import com.clickandvisit.data.model.chat.DiscussionsResponse
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.listener.OnChatItemClickedListener
import com.clickandvisit.global.listener.SchedulerProvider
import com.clickandvisit.global.listener.ToolBarListener
import com.clickandvisit.global.utils.DebugLog
import com.clickandvisit.global.utils.TAG
import com.clickandvisit.global.utils.tryCatch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class ChatViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    private val userRepository: UserRepository
) : BaseAndroidViewModel(application, schedulerProvider), ToolBarListener,
    OnChatItemClickedListener {


    val list: MutableLiveData<List<Discussion>> = MutableLiveData(arrayListOf())

    init {
        viewModelScope.launch {
            tryCatch({
                val response = withContext(schedulerProvider.dispatchersIO()) {
                    userRepository.getDiscussions()
                }
                onGetDiscussionSuccess(response)
            }, { error ->
                onGetDiscussionError(error)
            })
        }
    }

    private fun onGetDiscussionError(throwable: Throwable) {
        handleThrowable(throwable)
    }

    private fun onGetDiscussionSuccess(response: DiscussionsResponse) {
        val res = response.discussions.reversed()
        list.value = res
    }

    fun onBackClick() {
        navigate(Navigation.Back)
    }

    override fun onItemClicked(response: Discussion) {
        navigate(Navigation.ConvActivityNavigation(response.discId.toInt()))
    }

}