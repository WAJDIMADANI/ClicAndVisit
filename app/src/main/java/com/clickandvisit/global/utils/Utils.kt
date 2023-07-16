package com.clickandvisit.global.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.clickandvisit.ClickVisitApplication
import com.clickandvisit.R
import com.clickandvisit.ui.user.splash.SplashActivity
import okhttp3.ResponseBody
import java.io.FileOutputStream
import java.io.InputStream
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

private const val TAG: String = "Utils"

/**
 * Open a web page of a specified URL
 *
 * @param url URL to open
 */
fun openWebPage(url: String, context: Context) {
    try {
        val webPage = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webPage)
        context.startActivity(intent)
    } catch (ex: ActivityNotFoundException) {
        DebugLog.e(TAG, "ex $ex")
    }
}

fun getDateWithPattern(date: Long): String? = run {
    val timestampCreateIntervention = Timestamp(date)
    SimpleDateFormat(
        ClickVisitApplication.getInstance().resources.getString(R.string.filter_date_pattern),
        Locale.getDefault()
    ).format(timestampCreateIntervention)
}

/**
 * from ws format to mobile format
 */
fun getWsDate(date: String?): String? {
    val inputDateFormat =
        SimpleDateFormat(ClickVisitApplication.getInstance().resources.getString(R.string.date_input_format))
    val outputDateFormat =
        SimpleDateFormat(ClickVisitApplication.getInstance().resources.getString(R.string.date_output_format))
    return try {
        outputDateFormat.format(inputDateFormat.parse(date))
    } catch (e: Exception) {
        e.printStackTrace()
        date
    }
}

fun getWsTime(date: String?): String? {
    val inputDateFormat =
        SimpleDateFormat(ClickVisitApplication.getInstance().resources.getString(R.string.date_input_format))
    val outputDateFormat =
        SimpleDateFormat(ClickVisitApplication.getInstance().resources.getString(R.string.time_output_format))
    return try {
        outputDateFormat.format(inputDateFormat.parse(date))
    } catch (e: Exception) {
        e.printStackTrace()
        date
    }
}

fun getWsNormalDateFormat(date: String?): String? {
    val inputDateFormat =
        SimpleDateFormat(ClickVisitApplication.getInstance().resources.getString(R.string.date_input_format))
    val outputDateFormat =
        SimpleDateFormat(ClickVisitApplication.getInstance().resources.getString(R.string.date_output_normal_format))
    return try {
        outputDateFormat.format(inputDateFormat.parse(date))
    } catch (e: Exception) {
        e.printStackTrace()
        date
    }
}

fun saveFile(body: ResponseBody?, pathToSaveFile: String): String {
    if (body == null)
        return ""
    var input: InputStream? = null
    try {
        input = body.byteStream()
        val fos = FileOutputStream(pathToSaveFile)
        fos.use { output ->
            val buffer = ByteArray(4 * 1024) // or other buffer size
            var read: Int
            while (input.read(buffer).also { read = it } != -1) {
                output.write(buffer, 0, read)
            }
            output.flush()
        }
        return pathToSaveFile
    } catch (e: Exception) {
        DebugLog.e("saveFile", e.toString())
    } finally {
        input?.close()
    }
    return ""
}


/**Notification**/


fun shouldCreateNowNotificationChannel(notificationManager: NotificationManager) =
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !nowNotificationChannelExists(
        notificationManager
    )

@RequiresApi(Build.VERSION_CODES.O)
private fun nowNotificationChannelExists(notificationManager: NotificationManager) =
    notificationManager.getNotificationChannel(Push.CHANNEL) != null


fun showNotification(
    context: Context,
    title: String?,
    body: String,
    data: MutableMap<String, String>
) {

    val notificationManager: NotificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    if (shouldCreateNowNotificationChannel(notificationManager)) {
        createNotificationChannel(context)
    }

    val navTypeKey = if (data.containsKey(Push.NOTIFICATION_KEY_VISIT)) {
        Push.NOTIFICATION_KEY_VISIT
    } else if (data.containsKey(Push.NOTIFICATION_KEY_MEET)) {
        Push.NOTIFICATION_KEY_MEET
    } else {
        Push.NOTIFICATION_KEY_CHAT
    }
    val navTypeValue = data[navTypeKey]

    val notificationIntent = Intent(context, SplashActivity::class.java)
    notificationIntent.putExtra(
        ExtraKeys.HomeNotificationKeys.HOME_NOTIFICATION_EXTRA_KEY,
        navTypeKey
    )
    notificationIntent.putExtra(
        ExtraKeys.HomeNotificationKeys.HOME_NOTIFICATION_EXTRA_VALUE,
        navTypeValue
    )

    val pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0)

    val notification = NotificationCompat.Builder(context, Push.CHANNEL)
        .setContentIntent(pendingIntent)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setAutoCancel(true)
        .setContentTitle(title)
        .setContentText(body)
        .setChannelId(Push.CHANNEL)
        .build()

    notificationManager.notify(Push.PAUSE_NOTIFICATION_ID, notification)
}

private fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val serviceChannel = NotificationChannel(
            Push.CHANNEL,
            context.getString(R.string.app_name),
            NotificationManager.IMPORTANCE_HIGH
        )
        val manager = context.getSystemService(NotificationManager::class.java)
        manager!!.createNotificationChannel(serviceChannel)
    }
}