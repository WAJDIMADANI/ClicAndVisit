package com.foodline

import android.os.StrictMode
import androidx.multidex.MultiDexApplication
import com.squareup.leakcanary.LeakCanary
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FoodLineApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        instance = this
        LeakCanary.install(this)
        initStrictMode()
    }

    /**
     * You should not leave this code enabled in a production application.
     * It is designed for pre-production use only
     * Instead, a flag can be used to conditionally turn on StrictMode or off.
     */

    private fun initStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build()
            )
            StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build())
        }
    }

    companion object {
        private lateinit var instance: FoodLineApplication
        fun getInstance(): FoodLineApplication = instance
    }

}
