package com.clickandvisit.ui.user.chat.conv

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.model.GlobalResponse
import com.clickandvisit.data.model.chat.Message
import com.clickandvisit.data.model.chat.MessagesResponse
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.listener.SchedulerProvider
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

    val msg = MutableLiveData<String>()
    val discId = MutableLiveData<Int>()

    init {
        discId.value =
            savedStateHandle.getLiveData<Int>(ExtraKeys.ConvActivity.DISC_ID_EXTRA_KEY).value
        getMessages()
    }

    private fun getMessages() {
        showBlockProgressBar()
        viewModelScope.launch {
            tryCatch({
                val response = withContext(schedulerProvider.dispatchersIO()) {
                    userRepository.getMessages(discId.value!!)
                }
                onGetDiscussionSuccess(response)
            }, { error ->
                onGetDiscussionError(error)
            })
        }
    }

    private fun onGetDiscussionSuccess(response: MessagesResponse) {
        hideBlockProgressBar()
        list.value = response.discussions as ArrayList
    }

    private fun onGetDiscussionError(throwable: Throwable) {
        hideBlockProgressBar()
        handleThrowable(throwable)
    }

    fun onSendClicked() {
        if (validateFields()) {
            showBlockProgressBar()
            viewModelScope.launch {
                tryCatch({
                    val response = withContext(schedulerProvider.dispatchersIO()) {
                        userRepository.sendMessage(discId.value!!, msg.value!!)
                    }
                    onSendMessageSuccess(response)
                }, { error ->
                    onSendMessageError(error)
                })
            }
        }
    }

    private fun onSendMessageSuccess(response: GlobalResponse) {
        getMessages()
        msg.value = ""
    }

    private fun onSendMessageError(throwable: Throwable) {
        showBlockProgressBar()
        handleThrowable(throwable)
    }

    private fun validateFields(): Boolean {
        return msg.value.isNullOrEmpty().not()
    }

    fun onBackClick() {
        navigate(Navigation.Back)
    }

    fun getCurrentUserId(): Int {
        return userRepository.getCurrentUserId()
    }

}