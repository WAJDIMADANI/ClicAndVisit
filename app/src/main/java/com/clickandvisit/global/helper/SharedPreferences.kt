package com.clickandvisit.global.helper

import android.content.Context
import android.text.TextUtils
import com.clickandvisit.BuildConfig
import com.clickandvisit.data.model.user.User
import com.clickandvisit.global.utils.*
import com.securepreferences.SecurePreferences
import com.squareup.moshi.Moshi

private const val FILE_NAME_FLAG = "clickandvisit_file_flag"


private const val TOKEN_FLAG = "1"
private const val USER_FLAG = "2"

private const val PUSH_KEY_FLAG = "subject"
private const val CHAT_FLAG = "chat"
private const val VISITS_FLAG = "visit"
private const val MEET_FLAG = "meet"


class SharedPreferences(context: Context, val moshi: Moshi) {

    private val sharedPreferences: android.content.SharedPreferences

    init {
        DebugLog.d(TAG, "SharedPreferences init")
        sharedPreferences = SecurePreferences(context, getPassKeyStore(context), FILE_NAME_FLAG)
    }

    fun isConnected(): Boolean {
        return !TextUtils.isEmpty(getToken())
    }

    fun setPushValue(value: String?) {
        val editor = sharedPreferences.edit()
        editor.putString(PUSH_KEY_FLAG, value)
        editor.apply()
    }

    fun getPushValue(): String? {
        return sharedPreferences.getString(PUSH_KEY_FLAG, null)
    }

    fun setChat(isNotification: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(CHAT_FLAG, isNotification)
        editor.apply()
    }

    fun getChat(): Boolean {
        return sharedPreferences.getBoolean(CHAT_FLAG, false)
    }

    private fun deleteChat() {
        val editor = sharedPreferences.edit()
        editor.remove(CHAT_FLAG)
        editor.apply()
    }

    fun setVisits(isNotification: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(VISITS_FLAG, isNotification)
        editor.apply()
    }

    fun getVisits(): Boolean {
        return sharedPreferences.getBoolean(VISITS_FLAG, false)
    }

    private fun deleteVisits() {
        val editor = sharedPreferences.edit()
        editor.remove(VISITS_FLAG)
        editor.apply()
    }

    fun setMeet(isNotification: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(MEET_FLAG, isNotification)
        editor.apply()
    }

    fun getMeet(): Boolean {
        return sharedPreferences.getBoolean(MEET_FLAG, false)
    }

    private fun deleteMeet() {
        val editor = sharedPreferences.edit()
        editor.remove(MEET_FLAG)
        editor.apply()
    }

    private fun setToken(token: String?) {
        val editor = sharedPreferences.edit()
        editor.putString(TOKEN_FLAG, token)
        editor.apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString(TOKEN_FLAG, null)
    }


    fun saveUser(user: User) {
        setToken(user.id)
        val jsonAdapter = moshi.adapter(User::class.java)
        val editor = sharedPreferences.edit()
        editor.putString(USER_FLAG, jsonAdapter.toJson(user))
        editor.apply()
    }

    fun getUser(): User {
        val json = sharedPreferences.getString(USER_FLAG, null)
        val adapter = moshi.adapter(User::class.java)
        return adapter.fromJson(json) as User
    }

    private fun deleteUser() {
        val editor = sharedPreferences.edit()
        editor.remove(USER_FLAG)
        editor.apply()
    }

    private fun deleteToken() {
        val editor = sharedPreferences.edit()
        editor.remove(TOKEN_FLAG)
        editor.apply()
    }

    fun clearCache() {
        deleteToken()
        deleteUser()
        deleteChat()
        deleteVisits()
        deleteMeet()
    }

    private fun getPassKeyStore(context: Context): String {
        val alias = context.applicationContext.packageName
        var pass: String? = null

        try {
            KeyStoreHelper.createKeys(context, alias)
            pass = KeyStoreHelper.getSigningKey(alias)
        } catch (e: Throwable) {
            //Crashlytics.logException(e)
        }

        if (TextUtils.isEmpty(pass)) {
            try {
                pass = context.getAndroidId()
                pass = pass.bitShiftEntireString()
            } catch (e: Throwable) {
                //Crashlytics.logException(e)
            }
        }

        if (TextUtils.isEmpty(pass)) {
            pass = BuildConfig.APPLICATION_ID
            pass = pass.bitShiftEntireString()
        }

        return pass ?: ""
    }
}