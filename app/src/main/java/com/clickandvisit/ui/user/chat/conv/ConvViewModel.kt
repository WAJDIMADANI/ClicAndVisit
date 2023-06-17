package com.clickandvisit.ui.user.chat.conv

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.model.chat.Discussion
import com.clickandvisit.data.model.chat.DiscussionsResponse
import com.clickandvisit.data.model.chat.Message
import com.clickandvisit.data.model.chat.MessagesResponse
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.listener.SchedulerProvider
import com.clickandvisit.global.listener.ToolBarListener
import com.clickandvisit.global.utils.ExtraKeys
import com.clickandvisit.global.utils.tryCatch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class ConvViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
) : BaseAndroidViewModel(application, schedulerProvider) {


    val list: MutableLiveData<List<Message>> = MutableLiveData(arrayListOf())

    init {
        val discId =
            savedStateHandle.getLiveData<Int>(ExtraKeys.ConvActivity.DISC_ID_EXTRA_KEY).value

        viewModelScope.launch {
            tryCatch({
                val response = withContext(schedulerProvider.dispatchersIO()) {
                    userRepository.getMessages(discId!!)
                }
                onGetDiscussionSuccess(response)
            }, { error ->
                onGetDiscussionError(error)
            })
        }
    }

    private fun onGetDiscussionSuccess(response: MessagesResponse) {
        val res = response.discussions.reversed()
        list.value = res
    }

    private fun onGetDiscussionError(throwable: Throwable) {
        handleThrowable(throwable)
    }

    fun onBackClick() {
        navigate(Navigation.Back)
    }

    fun getCurrentUserId(): Int {
        return userRepository.getCurrentUserId()
    }

}