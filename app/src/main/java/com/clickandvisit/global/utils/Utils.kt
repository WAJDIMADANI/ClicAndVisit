package com.clickandvisit.global.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.clickandvisit.ClickVisitApplication
import com.clickandvisit.R
import java.lang.Exception
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