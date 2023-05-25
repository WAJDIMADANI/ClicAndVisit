package com.clickandvisit.global.helper

import android.content.Context
import android.text.TextUtils
import com.clickandvisit.BuildConfig
import com.clickandvisit.data.model.user.User
import com.clickandvisit.global.utils.*
import com.securepreferences.SecurePreferences
import com.squareup.moshi.Moshi

private const val FILE_NAME_FLAG = "bingo_file_flag"


private const val TOKEN_FLAG = "1"
private const val USER_FLAG = "2"
private const val INTRO_FLAG = "3"
private const val AIRPORT_FLAG = "airport_flag"


class SharedPreferences(context: Context, val moshi: Moshi) {

    private val sharedPreferences: android.content.SharedPreferences

    init {
        DebugLog.d(TAG, "SharedPreferences init")
        sharedPreferences = SecurePreferences(context, getPassKeyStore(context), FILE_NAME_FLAG)
    }

    fun isConnected(): Boolean {
        return !TextUtils.isEmpty(getToken())
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
        setToken(user.token)
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


    fun setIntro(isShowed: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(INTRO_FLAG, isShowed)
        editor.apply()
    }

    fun isIntro(): Boolean {
        return sharedPreferences.getBoolean(INTRO_FLAG, false)
    }


    private fun getPassKeyStore(context: Context): String {
        val alias = context.applicationContext.packageName
        var pass: String? = null

        try {
            KeyStoreHelper.createKeys(context, alias)
            pass = KeyStoreHelper.getSigningKey(alias)
        } catch (e: Throwable) {
            //Crashlytics.logException(e) TODO
        }

        if (TextUtils.isEmpty(pass)) {
            try {
                pass = context.getAndroidId()
                pass = pass.bitShiftEntireString()
            } catch (e: Throwable) {
                //Crashlytics.logException(e) TODO
            }
        }

        if (TextUtils.isEmpty(pass)) {
            pass = BuildConfig.APPLICATION_ID
            pass = pass.bitShiftEntireString()
        }

        return pass ?: ""
    }
}