package com.foodline.global.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.Uri
import android.os.SystemClock
import android.provider.Settings
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.observe
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.foodline.R
import com.foodline.global.helper.PaginationState
import com.foodline.global.listener.DataAdapterListener
import com.foodline.global.listener.PaginationStateListener
import com.google.android.material.slider.Slider
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CancellationException
import java.io.File


/**
 * give default value for the live data
 *
 */
fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }


/**
 * ic_status_validated if user is connected to the Internet
 *
 * @param context Context to get resources and device specific display metrics
 * @return A boolean value
 */
fun Context?.isNetworkAvailable(): Boolean {
    if (this == null) return false
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = cm.activeNetworkInfo
    return netInfo != null && netInfo.isConnected
}


/**
 * check if the target is a valid mail
 *
 * @return A boolean value
 */
fun CharSequence?.isValidEmail(): Boolean {
    return if (TextUtils.isEmpty(this)) {
        false
    } else {
        android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
}


/**
 * BitShift the entire string to obfuscate it further
 * and make it harder to guess the password.
 */
fun String?.bitShiftEntireString(): String {
    return if (TextUtils.isEmpty(this)) {
        ""
    } else {
        val msg = StringBuilder(this ?: "")
        for (i in 0 until msg.length) {
            msg.setCharAt(i, msg[i] + SERIAL_LENGTH)
        }
        msg.toString()
    }
}


/**
 * Gets the hardware Android_ID.
 *
 * @return  Settings.Secure.ANDROID_ID
 * Credit: SecurePreferences for Android
 */
fun Context?.getAndroidId(): String {
    return if (this == null) {
        ""
    } else {
        return Settings.Secure.getString(
            contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }
}


/**
 * property TAG extension for Loging
 *
 */
val Any.TAG: String
    get() {
        val tag = javaClass.simpleName
        return if (tag.length <= 23) tag else tag.substring(0, 23)
    }


@BindingAdapter(value = ["imageUrl", "placeholder", "picasso"], requireAll = true)
fun setImageUrl(imageView: ImageView, imageUrl: String, placeHolder: Drawable, picasso: Picasso) {
    if (TextUtils.isEmpty(imageUrl)) {
        imageView.setImageDrawable(placeHolder)
    } else {
        when (imageView.scaleType) {
            ImageView.ScaleType.CENTER_CROP -> picasso.load(imageUrl).fit().centerCrop().placeholder(
                placeHolder
            ).into(imageView)
            ImageView.ScaleType.CENTER_INSIDE -> picasso.load(imageUrl).fit().centerInside().placeholder(
                placeHolder
            ).into(imageView)
            else -> picasso.load(imageUrl).placeholder(R.mipmap.ic_launcher).into(imageView)
        }
    }
}

@BindingAdapter(value = ["imageUri", "picasso"], requireAll = true)
fun setImageUri(imageView: ImageView, imageUri: Uri?, picasso: Picasso) {
    imageUri?.let {
        picasso.load(imageUri).fit().centerCrop()
            .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(imageView)
    }
}


@BindingAdapter("data")
fun <T> setRecyclerViewProperties(recyclerView: RecyclerView, data: T?) {
    data?.let {
        if (recyclerView.adapter is DataAdapterListener<*>) {
            (recyclerView.adapter as DataAdapterListener<T>).setData(it)
        }
    }
}


@BindingAdapter("pagedListAdapterData")
fun <T> setRecyclerViewProperties(recyclerView: RecyclerView, data: PagedList<T>?) {
    data?.let {
        if (recyclerView.adapter is PagedListAdapter<*, *>) {
            (recyclerView.adapter as PagedListAdapter<T, *>).submitList(it)
        }
    }
}


@BindingAdapter("pagedListAdapterState")
fun setRecyclerViewProperties(recyclerView: RecyclerView, paginationState: PaginationState?) {
    paginationState?.let {
        if (recyclerView.adapter is PaginationStateListener) {
            (recyclerView.adapter as PaginationStateListener).setState(paginationState)
        }
    }
}


@BindingAdapter("onClickWithDebounce")
fun onClickWithDebounce(view: View, listener: View.OnClickListener) {
    view.setClickWithDebounce {
        listener.onClick(view)
    }
}


@BindingAdapter(value = ["onValueChangeListener"])
fun setOnValueChangeListener(slider: Slider, listener: OnValueChangeListener) {
    slider.addOnChangeListener { _: Slider?, value: Float, _: Boolean ->
        listener.onValueChanged(value)
    }
}


interface OnValueChangeListener {
    fun onValueChanged(value: Float)
}

object LastClickTimeSingleton {
    var lastClickTime: Long = 0
}

fun View.setClickWithDebounce(action: () -> Unit) {
    setOnClickListener(object : View.OnClickListener {

        override fun onClick(v: View) {
            if (Math.abs(SystemClock.elapsedRealtime() - LastClickTimeSingleton.lastClickTime) < 300L) return
            else action()
            LastClickTimeSingleton.lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}


/**
 * used for suspend tryCatch
 * @param tryBlock  try block to execute
 * @param catchBlock  catch block to execute
 * @param handleCancellationExceptionManually cancellation exception will manually handled
 *
 */
suspend fun tryCatch(
    tryBlock: suspend () -> Unit,
    catchBlock: suspend (Throwable) -> Unit,
    handleCancellationExceptionManually: Boolean = false
) {
    try {
        tryBlock()
    } catch (e: Throwable) {
        if (e !is CancellationException ||
            handleCancellationExceptionManually
        ) {
            catchBlock(e)
        } else {
            throw e
        }
    }
}


/**
 * used for suspend tryCatchFinally
 * @param tryBlock  try block to execute
 * @param catchBlock  catch block to execute
 * @param finallyBlock  catch block to execute
 * @param handleCancellationExceptionManually cancellation exception will manually handled
 *
 */
suspend fun tryCatchFinally(
    tryBlock: () -> Unit,
    catchBlock: (Throwable) -> Unit,
    finallyBlock: () -> Unit,
    handleCancellationExceptionManually: Boolean = false
) {

    var caughtThrowable: Throwable? = null

    try {
        tryBlock()
    } catch (e: Throwable) {
        if (e !is CancellationException ||
            handleCancellationExceptionManually
        ) {
            catchBlock(e)
        } else {
            caughtThrowable = e
        }
    } finally {
        if (caughtThrowable is CancellationException &&
            !handleCancellationExceptionManually
        ) {
            throw caughtThrowable
        } else {
            finallyBlock()
        }
    }
}


/**
 * used for suspend tryFinally
 * @param tryBlock  try block to execute
 * @param finallyBlock  catch block to execute
 * @param suppressCancellationException cancellation exception should be suppressed
 *
 */
suspend fun tryFinally(
    tryBlock: suspend () -> Unit,
    finallyBlock: suspend () -> Unit,
    suppressCancellationException: Boolean = false
) {

    var caughtThrowable: Throwable? = null

    try {
        tryBlock()
    } catch (e: CancellationException) {
        if (!suppressCancellationException) {
            caughtThrowable = e
        }
    } finally {
        if (caughtThrowable is CancellationException &&
            !suppressCancellationException
        ) {
            throw caughtThrowable
        } else {
            finallyBlock()
        }
    }
}

/**
 * check if the target is a sequence of white spaces
 *
 * @return A boolean value
 */
fun CharSequence?.isWhiteSpaces(): Boolean {
    return if (TextUtils.isEmpty(this)) {
        true
    } else {
        this?.matches(WHITE_SPACE.toRegex()) ?: true
    }
}


infix fun <T> Boolean.then(param: T): T? = if (this) param else null

/**
 * Created by sazouzi on 24/06/2020
 */

/**
 * observe non null live data update
 *
 * @param owner
 * @param observer
 *
 */
fun <T> LiveData<T>.observeOnlyNotNull(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(owner) {
        it?.let(observer)
    }
}

fun File.deleteDirectory(): Boolean {
    return if (exists()) {
        listFiles()?.forEach {
            if (it.isDirectory) {
                it.deleteDirectory()
            } else {
                it.delete()
            }
        }
        delete()
    } else false
}