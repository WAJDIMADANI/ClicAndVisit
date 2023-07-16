package com.clickandvisit

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.os.StrictMode
import androidx.multidex.MultiDexApplication
import com.clickandvisit.global.helper.BackgroundManager
import com.squareup.leakcanary.LeakCanary
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ClickVisitApplication : MultiDexApplication(), Application.ActivityLifecycleCallbacks {

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
        private lateinit var instance: ClickVisitApplication
        fun getInstance(): ClickVisitApplication = instance
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

    }

    override fun onActivityStarted(activity: Activity) {
        BackgroundManager.getInstance().onStart()
    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {
        BackgroundManager.getInstance().onStop()
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }

}
