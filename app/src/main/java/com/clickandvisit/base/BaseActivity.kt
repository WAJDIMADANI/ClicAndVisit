package com.clickandvisit.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.NonNull
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.observe
import com.clickandvisit.R
import com.clickandvisit.data.model.property.Property
import com.clickandvisit.global.helper.DetachableClickHelper
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.listener.OnFilterClickedListener
import com.clickandvisit.global.listener.OnMapsClickedListener
import com.clickandvisit.global.listener.OnSendClickedListener
import com.clickandvisit.global.utils.DebugLog
import com.clickandvisit.global.utils.observeOnlyNotNull
import com.clickandvisit.ui.shared.bottomsheet.filter.CustomFilterBottomSheet
import com.clickandvisit.ui.shared.bottomsheet.maps.CustomMapsBottomSheet
import com.clickandvisit.ui.shared.bottomsheet.meet.CustomMeetBottomSheet
import com.clickandvisit.ui.shared.dialog.CustomProgressDialog
import com.clickandvisit.ui.shared.view.CustomSnackBar
import com.squareup.picasso.Picasso
import dagger.Lazy
import javax.inject.Inject
import kotlin.reflect.KClass


private val TAG = BaseActivity::class.java.simpleName

abstract class BaseActivity : AppCompatActivity() {

    @Inject
    protected lateinit var picassoLazy: Lazy<Picasso>

    private var customSnackBar: CustomSnackBar? = null
    private var progressDialog: CustomProgressDialog? = null
    private var alertDialog: AlertDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.slide_in_right, R.anim.non)
    }


    private fun registerBottomSheet(viewModel: BaseAndroidViewModel) {
        viewModel.mapsBottomSheet.observeOnlyNotNull(
            this
        ) { mapsBottomSheet ->
            showMapsBottomSheetDialog(
                mapsBottomSheet.property,
                mapsBottomSheet.onMapsClickedListener,
                mapsBottomSheet.okActionBlock,
                mapsBottomSheet.dismissActionBlock
            )
        }
        viewModel.filterBottomSheet.observeOnlyNotNull(
            this
        ) { mapsBottomSheet ->
            showCustomFilterBottomSheet(
                mapsBottomSheet.onFilterClickedListener,
                mapsBottomSheet.okActionBlock,
                mapsBottomSheet.dismissActionBlock
            )
        }
        viewModel.meetBottomSheet.observeOnlyNotNull(
            this
        ) { mapsBottomSheet ->
            showCustomMeetBottomSheet(
                mapsBottomSheet.title,
                mapsBottomSheet.hint,
                mapsBottomSheet.onSendClickedListener,
                mapsBottomSheet.okActionBlock,
                mapsBottomSheet.dismissActionBlock
            )
        }
    }

    /**
     * show signup bottom sheet dialog
     * @param okActionBlock action to do on click
     * @param dismissActionBlock action to do on dismiss optional
     */
    fun showMapsBottomSheetDialog(
        property: Property,
        onMapsClickedListener: OnMapsClickedListener,
        okActionBlock: (() -> Unit)? = null,
        dismissActionBlock: (() -> Unit)? = null
    ) {
        if (!isFinishing) {
            CustomMapsBottomSheet(
                this,
                property,
                onMapsClickedListener,
                okActionBlock,
                dismissActionBlock
            ).show()
        }
    }

    /**
     * show signup bottom sheet dialog
     * @param okActionBlock action to do on click
     * @param dismissActionBlock action to do on dismiss optional
     */
    fun showCustomMeetBottomSheet(
        title: String,
        hint: String,
        onSendClickedListener: OnSendClickedListener,
        okActionBlock: (() -> Unit)? = null,
        dismissActionBlock: (() -> Unit)? = null
    ) {
        if (!isFinishing) {
            CustomMeetBottomSheet(
                this,
                title,
                hint,
                onSendClickedListener,
                okActionBlock,
                dismissActionBlock
            ).show()
        }
    }

    /**
     * show signup bottom sheet dialog
     * @param okActionBlock action to do on click
     * @param dismissActionBlock action to do on dismiss optional
     */
    fun showCustomFilterBottomSheet(
        onFilterClickedListener: OnFilterClickedListener,
        okActionBlock: (() -> Unit)? = null,
        dismissActionBlock: (() -> Unit)? = null
    ) {
        if (!isFinishing) {
            CustomFilterBottomSheet(
                this,
                onFilterClickedListener,
                okActionBlock,
                dismissActionBlock
            ).show()
        }
    }


    /**
     * observe snackBarMessage and show snack bar
     * observe loader and show hide progress bar
     * observe dialog and show dialog
     *
     * @param viewModel BaseAndroidViewModel
     */
    protected fun registerBaseObservers(viewModel: ViewModel) {
        if (viewModel is BaseAndroidViewModel) {
            registerSnackBar(viewModel)
            registerBottomSheet(viewModel)
            registerSimpleDialog(viewModel)
            registerChoseDialog(viewModel)
            registerProgressBar(viewModel)
            registerNavigation(viewModel)
        }
    }

    private fun registerNavigation(viewModel: BaseAndroidViewModel) {
        hideKeyboard()
        viewModel.navigation.observe(this) { navigate(it) }
    }


    private fun registerSnackBar(viewModel: BaseAndroidViewModel) {
        viewModel.snackBarMessage.observe(this) { showSnackBar(it) }
    }

    private fun registerSimpleDialog(viewModel: BaseAndroidViewModel) {
        viewModel.simpleDialog.observe(
            this
        ) { simpleDialog ->
            simpleDialog?.let {
                showSimpleDialog(
                    it.title,
                    it.message,
                    it.okActionBlock,
                    it.dismissActionBlock
                )
            }
        }
    }

    private fun registerChoseDialog(viewModel: BaseAndroidViewModel) {
        viewModel.choseDialog.observe(
            this
        ) { choseDialog ->
            choseDialog?.let {
                showChoseDialog(
                    it.title,
                    it.message,
                    it.ok,
                    it.cancel,
                    it.okActionBlock,
                    it.cancelActionBlock,
                    it.dismissActionBlock
                )
            }
        }
    }

    private fun registerProgressBar(viewModel: BaseAndroidViewModel) {
        viewModel.progressBar.observe(this) {
            when {
                it -> showProgressBar()
                else -> hideProgressBar()
            }
        }
    }


    protected fun getPicasso(): Picasso {
        return picassoLazy.get()
    }


    override fun onBackPressed() {
        super.onBackPressed()
        pendingTransition()
    }


    override fun finish() {
        super.finish()
        pendingTransition()
    }


    private fun pendingTransition() {
        overridePendingTransition(R.anim.non, R.anim.slide_out_right)
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
    fun showSimpleDialog(
        @StringRes titleId: Int? = null,
        @StringRes messageId: Int,
        okActionBlock: (() -> Unit)? = null,
        dismissActionBlock: (() -> Unit)? = null
    ) {
        if (!isFinishing) {
            val title = titleId?.let { getString(it) }
            showSimpleDialog(title, getString(messageId), okActionBlock, dismissActionBlock)
        }
    }


    /**
     * show simple ok dialog
     * @param title  message string optional
     * @param message  message string
     * @param okActionBlock action to do on click
     * @param dismissActionBlock action to do on dismiss optional
     */
    fun showSimpleDialog(
        title: String? = null,
        message: String,
        okActionBlock: (() -> Unit)? = null,
        dismissActionBlock: (() -> Unit)? = null
    ) {
        if (!isFinishing) {
            val clickListener = getWrapperClick(okActionBlock, null, dismissActionBlock)
            val builder = AlertDialog.Builder(this)
            builder.setCancelable(okActionBlock == null)
                .setMessage(message)
                .setPositiveButton(R.string.global_ok, clickListener)
                .setOnDismissListener {
                    if (isChangingConfigurations.not()) dismissActionBlock?.invoke()
                }
            title?.let { builder.setTitle(it) }
            alertDialog = builder.create().apply {
                show()
            }
        }
    }


    /**
     * show simple ok dialog
     *
     * @param titleId      dialog title resources Id optional
     * @param messageId       message resources Id
     * @param okId        action button resources Id
     * @param cancelId     cancel button resources Id
     * @param okActionBlock    action to do on click ok optional
     * @param cancelActionBlock action to do on click cancel optional
     * @param dismissActionBlock action to do on dismiss optional
     */
    fun showChoseDialog(
        @StringRes titleId: Int? = null, @StringRes messageId: Int, @StringRes okId: Int,
        @StringRes cancelId: Int, okActionBlock: (() -> Unit)? = null,
        cancelActionBlock: (() -> Unit)? = null,
        dismissActionBlock: (() -> Unit)? = null
    ) {
        if (!isFinishing) {
            val title = titleId?.let { getString(it) }
            showChoseDialog(
                title,
                getString(messageId),
                getString(okId),
                getString(cancelId),
                okActionBlock,
                cancelActionBlock,
                dismissActionBlock
            );
        }
    }

    /**
     * show simple ok dialog
     *
     * @param title      dialog title optional
     * @param message       message
     * @param ok        action button
     * @param cancel     cancel button
     * @param okActionBlock     action to do on click ok optional
     * @param cancelActionBlock action to do on click cancel optional
     * @param dismissActionBlock action to do on dismiss optional
     *
     */
    fun showChoseDialog(
        title: String? = null,
        message: String,
        ok: String,
        cancel: String,
        okActionBlock: (() -> Unit)? = null,
        cancelActionBlock: (() -> Unit)? = null,
        dismissActionBlock: (() -> Unit)? = null
    ) {
        if (!isFinishing) {
            val listenerWrapper =
                getWrapperClick(okActionBlock, cancelActionBlock, dismissActionBlock)
            val builder = AlertDialog.Builder(this)
            builder
                .setCancelable(okActionBlock == null)
                .setMessage(message)
                .setPositiveButton(ok, listenerWrapper)
                .setNegativeButton(cancel, listenerWrapper)
                .setOnDismissListener(listenerWrapper)
            title?.let { builder.setTitle(it) }
            val alertDialog = builder.create()
            alertDialog.show()
        }
    }


    @NonNull
    private fun getWrapperClick(
        okActionBlock: (() -> Unit)? = null,
        cancelActionBlock: (() -> Unit)? = null,
        dismissActionBlock: (() -> Unit)? = null
    ): DetachableClickHelper {
        return DetachableClickHelper.wrapClick(okActionBlock, cancelActionBlock, dismissActionBlock)
    }


    /**
     * try to hide Keyboard from the focused screen
     */
    fun hideKeyboard() {
        try {
            val inputMethodManager =
                getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        } catch (e: Exception) {
            DebugLog.d(TAG, "Could not hide keyboard, window unreachable. " + e.toString())
        }
    }


    /**
     * This method show simple SnackBar
     *
     * @param message message dialog text
     */
    fun showSnackBar(message: String) {
        if (!isFinishing) {
            val root = window.decorView.findViewById<ViewGroup>(android.R.id.content)
            customSnackBar = CustomSnackBar.make(root, CustomSnackBar.DURATION).apply {
                setText(message)
                show()
            }
        }
    }


    /**
     * This method show simple Snackbar
     *
     * @param messageId message dialog text id
     */
    fun showSnackBar(@StringRes messageId: Int) {

        showSnackBar(getString(messageId))
    }

    /**
     * hide snackBar if it's on screen
     */
    fun hideSnackBar() {
        if (!isFinishing) {
            customSnackBar?.dismiss()
            customSnackBar = null
        }
    }


    /**
     * show unavailable Network Error
     */
    fun showErrorNetworkDialog() {
        showSimpleDialog(messageId = R.string.global_error_unavailable_network)
    }

    /**
     * show server Error
     */
    fun showErrorServerDialog() {
        showSimpleDialog(messageId = R.string.global_error_server)
    }


    /**
     * show unavailable Network Error
     */
    fun showErrorNetworkSnackBar() {
        showSnackBar(R.string.global_error_unavailable_network)
    }


    /**
     * show blocking progressBar on the root of the activity
     */
    fun showProgressBar() {
        if (!isFinishing) {
            if (progressDialog == null) {
                progressDialog = CustomProgressDialog(
                    this,
                    R.style.ProgressDialogStyle
                ).apply { setCancelable(false) }

            }
            if (progressDialog?.isShowing != true) {
                progressDialog!!.show()
            }
        }
    }


    /**
     * hide blocking progressBar
     */
    fun hideProgressBar() {
        if (progressDialog?.isShowing == true) {
            progressDialog?.dismiss()
            progressDialog = null
        }
    }


    fun hideSimperDialog() {
        if (alertDialog?.isShowing == true) {
            alertDialog?.dismiss()
            alertDialog = null
        }
    }

    /**
     * startActivity to class
     * @param kClass activity to navigate to
     * @param shouldFinish should finish current activity
     */
    fun navigateToActivity(kClass: KClass<out Activity>, shouldFinish: Boolean = false) {
        startActivity(Intent(this, kClass.java))
        if (shouldFinish) finish()
    }


    /**
     * handling navigation actions
     * @param navigationTo activity to navigate to
     */
    open fun navigate(navigationTo: Navigation) {

    }


    override fun onDestroy() {
        hideSimperDialog()
        hideProgressBar()
        super.onDestroy()
    }

/*
    fun navigateAndClearToActivity(kClass: KClass<out Activity>){
        Intent(this, kClass::class.java).let {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }
    }
*/

}