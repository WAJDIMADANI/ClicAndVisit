package com.clickandvisit.global.utils

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.Uri
import android.os.SystemClock
import android.provider.Settings
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.clickandvisit.R
import com.clickandvisit.global.listener.DataAdapterListener
import com.google.android.material.slider.Slider
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CancellationException
import java.io.File
import java.lang.NumberFormatException


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


/**
 * set spinner entries
 */
fun Spinner.setSpinnerEntries(entries: List<String>?) {
    if (entries != null) {
        val arrayAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, entries)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapter = arrayAdapter
    }
}

/**
 * set spinner value
 */
fun Spinner.setSpinnerValue(value: String?) {
    if (adapter != null && ((adapter is ArrayAdapter<*>))) {
        val position = (adapter as ArrayAdapter<String>).getPosition(value)
        setSelection(position, false)
        tag = position
    }
}

/**
 * set spinner onItemSelectedListener listener
 */
fun Spinner.setSpinnerInverseBindingListener(listener: InverseBindingListener?) {
    onItemSelectedListener = if (listener == null) {
        null
    } else {
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                if (tag != position) {
                    listener.onChange()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }
}

/**
 * get spinner value
 */
fun Spinner.getSpinnerValue(): Any? {
    return selectedItem
}


@BindingAdapter(value = ["imageUrl", "placeholder", "picasso"], requireAll = true)
fun setImageUrl(imageView: ImageView, imageUrl: String, placeHolder: Drawable, picasso: Picasso) {
    if (TextUtils.isEmpty(imageUrl)) {
        imageView.setImageDrawable(placeHolder)
    } else {
        when (imageView.scaleType) {
            ImageView.ScaleType.CENTER_CROP -> picasso.load(imageUrl).fit().centerCrop()
                .placeholder(
                    placeHolder
                ).into(imageView)

            ImageView.ScaleType.CENTER_INSIDE -> picasso.load(imageUrl).fit().centerInside()
                .placeholder(
                    placeHolder
                ).into(imageView)

            else -> picasso.load(imageUrl).placeholder(R.mipmap.ic_launcher_foreground)
                .into(imageView)
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


@BindingAdapter("recyclerViewData")
fun <T> setRecyclerViewProperties(recyclerView: RecyclerView, recyclerViewData: T?) {
    recyclerViewData?.let {
        if (recyclerView.adapter is DataAdapterListener<*>) {
            (recyclerView.adapter as DataAdapterListener<T>).setData(it)
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

fun String?.toMediaUrl(): String {
    return if (this.isNullOrEmpty()) {
        "https://"
    } else {
        this
    }
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun String.getPriceNBR(): String {
    if (this.isEmpty()) {
        return ""
    } else {
        return try {
            if (this.length >= 3) {
                "${
                    this.replaceRange(
                        this.length - 3,
                        this.length,
                        " " + this.subSequence(this.length - 3, this.length)
                    )
                } €"
            } else {
                "$this €"
            }

        } catch (e: NumberFormatException) {
            ""
        }
    }
}
