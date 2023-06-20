package com.clickandvisit.ui.ads.addads.steptwo

import android.app.AlertDialog
import android.app.Application
import android.content.DialogInterface
import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.clickandvisit.R
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.listener.SchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class TwoViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    savedStateHandle: SavedStateHandle
) : BaseAndroidViewModel(application, schedulerProvider) {


    val roomNbrApi1: MutableLiveData<String> = MutableLiveData("")
    val roomNbrApi2: MutableLiveData<String> = MutableLiveData("")
    val roomNbrApi3: MutableLiveData<String> = MutableLiveData("")
    val roomNbrApi4: MutableLiveData<String> = MutableLiveData("")
    val roomNbrApi5: MutableLiveData<String> = MutableLiveData("")
    val roomNbrApi6: MutableLiveData<String> = MutableLiveData("")
    val roomNbrApi7: MutableLiveData<String> = MutableLiveData("")
    val roomNbrApi8: MutableLiveData<String> = MutableLiveData("")
    val roomNbrApi9: MutableLiveData<String> = MutableLiveData("")
    val roomNbrApi10: MutableLiveData<String> = MutableLiveData("")

    //val roomNbrApi11: MutableLiveData<String> = MutableLiveData()
    val roomNbrApi12: MutableLiveData<String> = MutableLiveData("")
    val roomNbrApi13: MutableLiveData<String> = MutableLiveData("")
    val roomNbrApi14: MutableLiveData<String> = MutableLiveData("")
    val roomNbrApi15: MutableLiveData<String> = MutableLiveData("")
    val roomNbrApi16: MutableLiveData<String> = MutableLiveData("")
    val roomNbrApi17: MutableLiveData<String> = MutableLiveData("")
    val roomNbrApi18: MutableLiveData<String> = MutableLiveData("")
    val roomNbrApi19: MutableLiveData<String> = MutableLiveData("")
    val roomNbrApi20: MutableLiveData<String> = MutableLiveData("")


    val roomNbr1: MutableLiveData<String> =
        MutableLiveData(applicationContext.getString(R.string.ad_ads_1) + roomNbrApi1.value)
    val roomNbr2: MutableLiveData<String> =
        MutableLiveData(applicationContext.getString(R.string.ad_ads_2) + roomNbrApi2.value)
    val roomNbr3: MutableLiveData<String> =
        MutableLiveData(applicationContext.getString(R.string.ad_ads_3) + roomNbrApi3.value)
    val roomNbr4: MutableLiveData<String> =
        MutableLiveData(applicationContext.getString(R.string.ad_ads_4) + roomNbrApi4.value)
    val roomNbr5: MutableLiveData<String> =
        MutableLiveData(applicationContext.getString(R.string.ad_ads_5) + roomNbrApi5.value)
    val roomNbr6: MutableLiveData<String> =
        MutableLiveData(applicationContext.getString(R.string.ad_ads_6) + roomNbrApi6.value)
    val roomNbr7: MutableLiveData<String> =
        MutableLiveData(applicationContext.getString(R.string.ad_ads_7) + roomNbrApi7.value)
    val roomNbr8: MutableLiveData<String> =
        MutableLiveData(applicationContext.getString(R.string.ad_ads_8) + roomNbrApi8.value)
    val roomNbr9: MutableLiveData<String> =
        MutableLiveData(applicationContext.getString(R.string.ad_ads_9) + roomNbrApi9.value)
    val roomNbr10: MutableLiveData<String> =
        MutableLiveData(applicationContext.getString(R.string.ad_ads_10) + roomNbrApi10.value)

    //val roomNbr11: MutableLiveData<String> = MutableLiveData(applicationContext.getString(R.string.ad_ads_11))
    val roomNbr12: MutableLiveData<String> =
        MutableLiveData(applicationContext.getString(R.string.ad_ads_12) + roomNbrApi12.value)
    val roomNbr13: MutableLiveData<String> =
        MutableLiveData(applicationContext.getString(R.string.ad_ads_13) + roomNbrApi13.value)
    val roomNbr14: MutableLiveData<String> =
        MutableLiveData(applicationContext.getString(R.string.ad_ads_14) + roomNbrApi14.value)
    val roomNbr15: MutableLiveData<String> =
        MutableLiveData(applicationContext.getString(R.string.ad_ads_15) + roomNbrApi15.value)
    val roomNbr16: MutableLiveData<String> =
        MutableLiveData(applicationContext.getString(R.string.ad_ads_16) + roomNbrApi16.value)
    val roomNbr17: MutableLiveData<String> =
        MutableLiveData(applicationContext.getString(R.string.ad_ads_17) + roomNbrApi17.value)
    val roomNbr18: MutableLiveData<String> =
        MutableLiveData(applicationContext.getString(R.string.ad_ads_18) + roomNbrApi18.value)
    val roomNbr19: MutableLiveData<String> =
        MutableLiveData(applicationContext.getString(R.string.ad_ads_19) + roomNbrApi19.value)
    val roomNbr20: MutableLiveData<String> =
        MutableLiveData(applicationContext.getString(R.string.ad_ads_20) + roomNbrApi20.value)

    init {
       // showInputDialog()
    }


    // mrezga 950 8 ans  160 euros
    // yasmine hammamet s+2 climatisé sur mere (max 250/j) priode 15 jours

}