package com.foodline.base

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.foodline.R
import com.foodline.data.model.Card
import com.foodline.data.retrofit.EndpointInterceptor
import com.foodline.global.helper.Navigation
import com.foodline.global.helper.SingleLiveEvent
import com.foodline.global.helper.dialog.ChoseDialog
import com.foodline.global.helper.dialog.SimpleDialog
import com.foodline.global.listener.SchedulerProvider
import com.foodline.ui.shared.bottomsheet.SignUpBottomSheet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import retrofit2.HttpException


abstract class BaseAndroidViewModel(
    application: Application,
    protected val schedulerProvider: SchedulerProvider
) : AndroidViewModel(application) {


    private val job = SupervisorJob()
    protected val viewModelScope = CoroutineScope(job + schedulerProvider.dispatchersUI())


    //application context for resource access only
    protected val applicationContext = application.applicationContext!!;

    //for blocking progress bar
    private val _progressBar: MutableLiveData<Boolean> = MutableLiveData()
    val progressBar: LiveData<Boolean>
        get() = _progressBar

    //for displaying simple dialog
    private val _simpleDialog: MutableLiveData<SimpleDialog?> = MutableLiveData()
    val simpleDialog: LiveData<SimpleDialog?>
        get() = _simpleDialog

    //for displaying chose dialog
    private val _choseDialog: MutableLiveData<ChoseDialog?> = MutableLiveData()
    val choseDialog: LiveData<ChoseDialog?>
        get() = _choseDialog


    //for displaying global snack bar
    private val _snackBarMessage: SingleLiveEvent<String> = SingleLiveEvent()
    val snackBarMessage: LiveData<String>
        get() = _snackBarMessage

    //for navigation events
    private val _navigation: SingleLiveEvent<Navigation> = SingleLiveEvent()
    val navigation: LiveData<Navigation>
        get() = _navigation


    //for displaying signUp BottomSheet dialog
    var signUpBottomSheet: MutableLiveData<SignUpBottomSheet> = MutableLiveData()

    /**
     * show chose dialog
     *
     * @param titleId       title resources Id
     * @param bodyId       body resources Id
     * @param okActionBlock    action to do on click ok
     * @param dismissActionBlock action to do on dismiss optional
     *
     */
    fun showAddCardBottomSheet(
        card: Card?,
        okActionBlock: (() -> Unit)? = null,
        dismissActionBlock: (() -> Unit)? = null
    ) {
        signUpBottomSheet.value =
            SignUpBottomSheet.build(
                context = applicationContext,
                card = card,
                okActionBlock = okActionBlock,
                dismissActionBlock = dismissSignUpBottomSheet(dismissActionBlock)
            )
    }

    private fun dismissSignUpBottomSheet(dismissActionBlock: (() -> Unit)? = null): () -> Unit {
        return {
            dismissActionBlock?.invoke()
            signUpBottomSheet.value = null
        }
    }


    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }


    protected fun shownSnackBarMessage(message: String) {
        _snackBarMessage.value = message
    }


    protected fun shownSnackBarMessage(@StringRes messageResourceId: Int) {
        shownSnackBarMessage(applicationContext.getString(messageResourceId))
    }


    private fun setShownBlockProgress(show: Boolean) {
        _progressBar.value = show
    }


    protected fun showBlockProgressBar() {
        setShownBlockProgress(true)
    }


    protected fun hideBlockProgressBar() {
        setShownBlockProgress(false)
    }

    /**
     * show simple ok dialog
     *
     * @param titleId  resources id optional
     * @param messageId  resources id
     * @param okActionBlock action to do on click
     * @param dismissActionBlock action to do on dismiss optional
     *
     */
    fun shownSimpleDialog(
        @StringRes titleId: Int? = null, @StringRes messageId: Int, okActionBlock: (() -> Unit)? = null,
        dismissActionBlock: (() -> Unit)? = null
    ) {
        _simpleDialog.value =
            SimpleDialog.build(
                applicationContext,
                titleId,
                messageId,
                okActionBlock,
                dismissSimpleBuild(dismissActionBlock)
            )
    }


    /**
     * show simple ok dialog
     * @param title  message string optional
     * @param message  message string
     * @param okActionBlock action to do on click
     * @param dismissActionBlock action to do on dismiss optional
     *
     */
    fun shownSimpleDialog(
        title: String? = null,
        message: String,
        okActionBlock: (() -> Unit)? = null,
        dismissActionBlock: (() -> Unit)? = null
    ) {
        _simpleDialog.value = SimpleDialog.build(title, message, okActionBlock, dismissSimpleBuild(dismissActionBlock))
    }


    private fun dismissSimpleBuild(dismissActionBlock: (() -> Unit)? = null): () -> Unit {
        return {
            dismissActionBlock?.invoke()
            _simpleDialog.value = null
        }
    }


    /**
     * show simple ok dialog
     *
     * @param titleId      dialog title resources Id
     * @param messageId       message resources Id
     * @param okId        action button resources Id
     * @param cancelId     cancel button resources Id
     * @param okActionBlock    action to do on click ok
     * @param cancelActionBlock action to do on click cancel
     * @param dismissActionBlock action to do on dismiss optional
     *
     */
    fun shownChoseDialog(
        @StringRes titleId: Int? = null, @StringRes messageId: Int, @StringRes okId: Int,
        @StringRes cancelId: Int, okActionBlock: (() -> Unit)? = null,
        cancelActionBlock: (() -> Unit)? = null,
        dismissActionBlock: (() -> Unit)? = null
    ) {

        _choseDialog.value =
            ChoseDialog.build(
                applicationContext,
                titleId,
                messageId,
                okId,
                cancelId,
                okActionBlock,
                cancelActionBlock,
                dismissChoseBuild(dismissActionBlock)
            )
    }


    /**
     * show simple ok dialog
     *
     * @param title      dialog title
     * @param message       message
     * @param ok        action button
     * @param cancel     cancel button
     * @param okActionBlock     action to do on click ok
     * @param cancelActionBlock action to do on click cancel
     * @param dismissActionBlock action to do on dismiss optional
     *
     */
    fun shownChoseDialog(
        title: String? = null,
        message: String,
        ok: String,
        cancel: String,
        okActionBlock: (() -> Unit)? = null,
        cancelActionBlock: (() -> Unit)? = null,
        dismissActionBlock: (() -> Unit)? = null
    ) {
        _choseDialog.value =
            ChoseDialog.build(
                title,
                message,
                ok,
                cancel,
                okActionBlock,
                cancelActionBlock,
                dismissChoseBuild(dismissActionBlock)
            )
    }


    private fun dismissChoseBuild(dismissActionBlock: (() -> Unit)? = null): () -> Unit {
        return {
            dismissActionBlock?.invoke()
            _choseDialog.value = null
        }
    }


    /**
     * show simple error server ok dialog
     *
     */
    protected fun shownServerErrorSimpleDialog() {
        shownSimpleDialog(messageId = R.string.global_error_server)
    }


    /**
     * show simple error ok dialog
     * @param throwable error
     *
     */
    protected fun handleThrowable(throwable: Throwable) {
        if (throwable is EndpointInterceptor.NetworkNotFoundException) {
            shownSimpleDialog(messageId = R.string.global_error_unavailable_network)
        } else if (throwable is HttpException) {
            when (throwable.code()) {
                //other default handler
                else -> shownServerErrorSimpleDialog()
            }
        } else {
            shownServerErrorSimpleDialog()
        }
    }


    /**
     * used for navigation events
     * @param navigationTo  destination
     *
     */
    fun navigate(navigationTo: Navigation) {
        _navigation.value = navigationTo
    }
}
