package com.clickandvisit.ui.ads.addads.steptwo

import android.app.Application
import android.text.InputType
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.clickandvisit.R
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.model.property.Property
import com.clickandvisit.data.model.property.add.PropertyAdd
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

    fun onItemClick(item: Int) {
        when (item) {
            1 -> {
                navigate(
                    Navigation.PopupNavigation(
                        item,
                        "${applicationContext.getString(R.string.ad_ads_popup_title_1)} " +
                                "${applicationContext.getString(R.string.ad_ads_1)}",
                        "${applicationContext.getString(R.string.ad_ads_popup_input_hint_1)} " +
                                "${applicationContext.getString(R.string.ad_ads_1)}",
                        InputType.TYPE_CLASS_NUMBER,
                        roomNbrApi1.value
                    )
                )
            }
            2 -> {
                navigate(
                    Navigation.PopupNavigation(
                        item,
                        "${applicationContext.getString(R.string.ad_ads_popup_title_1)} " +
                                "${applicationContext.getString(R.string.ad_ads_2)}",
                        "${applicationContext.getString(R.string.ad_ads_popup_input_hint_1)} " +
                                "${applicationContext.getString(R.string.ad_ads_2)}",
                        InputType.TYPE_CLASS_NUMBER,
                        roomNbrApi2.value
                    )
                )
            }
            3 -> {
                navigate(
                    Navigation.PopupNavigation(
                        item,
                        "${applicationContext.getString(R.string.ad_ads_popup_title_1)} " +
                                "${applicationContext.getString(R.string.ad_ads_3)}",
                        "${applicationContext.getString(R.string.ad_ads_popup_input_hint_1)} " +
                                "${applicationContext.getString(R.string.ad_ads_3)}",
                        InputType.TYPE_CLASS_NUMBER,
                        roomNbrApi3.value
                    )
                )
            }
            4 -> {
                navigate(
                    Navigation.PopupNavigation(
                        item,
                        "${applicationContext.getString(R.string.ad_ads_popup_title_1)} " +
                                "${applicationContext.getString(R.string.ad_ads_4)}",
                        "${applicationContext.getString(R.string.ad_ads_popup_input_hint_1)} " +
                                "${applicationContext.getString(R.string.ad_ads_4)}",
                        InputType.TYPE_CLASS_NUMBER,
                        roomNbrApi4.value
                    )
                )
            }
            5 -> {
                navigate(
                    Navigation.PopupNavigation(
                        item,
                        "${applicationContext.getString(R.string.ad_ads_popup_title_1)} " +
                                "${applicationContext.getString(R.string.ad_ads_5)}",
                        "${applicationContext.getString(R.string.ad_ads_popup_input_hint_1)} " +
                                "${applicationContext.getString(R.string.ad_ads_5)}",
                        InputType.TYPE_CLASS_NUMBER,
                        roomNbrApi5.value
                    )
                )
            }
            6 -> {
                navigate(
                    Navigation.PopupNavigation(
                        item,
                        "${applicationContext.getString(R.string.ad_ads_popup_title_1)} " +
                                "${applicationContext.getString(R.string.ad_ads_6)}",
                        "${applicationContext.getString(R.string.ad_ads_popup_input_hint_1)} " +
                                "${applicationContext.getString(R.string.ad_ads_6)}",
                        InputType.TYPE_CLASS_NUMBER,
                        roomNbrApi6.value
                    )
                )
            }
            7 -> {
                navigate(
                    Navigation.PopupNavigation(
                        item,
                        "${applicationContext.getString(R.string.ad_ads_popup_title_1)} " +
                                "${applicationContext.getString(R.string.ad_ads_7)}",
                        "${applicationContext.getString(R.string.ad_ads_popup_input_hint_1)} " +
                                "${applicationContext.getString(R.string.ad_ads_7)}",
                        InputType.TYPE_CLASS_NUMBER,
                        roomNbrApi7.value
                    )
                )
            }
            8 -> {
                navigate(
                    Navigation.PopupNavigation(
                        item,
                        "${applicationContext.getString(R.string.ad_ads_popup_title_1)} " +
                                "${applicationContext.getString(R.string.ad_ads_8)}",
                        "${applicationContext.getString(R.string.ad_ads_popup_input_hint_1)} " +
                                "${applicationContext.getString(R.string.ad_ads_8)}",
                        InputType.TYPE_CLASS_NUMBER,
                        roomNbrApi8.value
                    )
                )
            }
            9 -> {
                navigate(
                    Navigation.PopupNavigation(
                        item,
                        "${applicationContext.getString(R.string.ad_ads_popup_title_2)} " +
                                "${applicationContext.getString(R.string.ad_ads_9)}",
                        "${applicationContext.getString(R.string.ad_ads_popup_input_hint_2)} " +
                                "${applicationContext.getString(R.string.ad_ads_9)}",
                        InputType.TYPE_CLASS_NUMBER,
                        roomNbrApi9.value
                    )
                )
            }
            10 -> {
                navigate(
                    Navigation.PopupNavigation(
                        item,
                        "${applicationContext.getString(R.string.ad_ads_popup_title_2)} " +
                                "${applicationContext.getString(R.string.ad_ads_10)}",
                        "${applicationContext.getString(R.string.ad_ads_popup_input_hint_2)}" +
                                "${applicationContext.getString(R.string.ad_ads_10)}",
                        InputType.TYPE_CLASS_NUMBER,
                        roomNbrApi10.value
                    )
                )
            }
            12 -> {
                navigate(
                    Navigation.PopupNavigation(
                        item,
                        "${applicationContext.getString(R.string.ad_ads_popup_title_3)}",
                        "${applicationContext.getString(R.string.ad_ads_popup_input_hint_1)} " +
                                "${applicationContext.getString(R.string.ad_ads_12)}",
                        InputType.TYPE_CLASS_NUMBER,
                        roomNbrApi12.value
                    )
                )
            }
            13 -> {
                navigate(
                    Navigation.PopupNavigation(
                        item,
                        "${applicationContext.getString(R.string.ad_ads_popup_title_1)} " +
                                "${applicationContext.getString(R.string.ad_ads_13)}",
                        "${applicationContext.getString(R.string.ad_ads_popup_input_hint_1)} " +
                                "${applicationContext.getString(R.string.ad_ads_9)}",
                        InputType.TYPE_CLASS_NUMBER,
                        roomNbrApi13.value
                    )
                )
            }
            14 -> {
                navigate(
                    Navigation.PopupNavigation(
                        item,
                        "${applicationContext.getString(R.string.ad_ads_popup_title_4)} " +
                                "${applicationContext.getString(R.string.ad_ads_14)}",
                        "",
                        InputType.TYPE_CLASS_TEXT,
                        roomNbrApi14.value
                    )
                )
            }
            15 -> {
                navigate(
                    Navigation.PopupNavigation(
                        item,
                        "${applicationContext.getString(R.string.ad_ads_popup_title_4)} " +
                                "${applicationContext.getString(R.string.ad_ads_15)}",
                        "",
                        InputType.TYPE_CLASS_TEXT,
                        roomNbrApi15.value
                    )
                )
            }
            16 -> {
                navigate(
                    Navigation.PopupNavigation(
                        item,
                        "${applicationContext.getString(R.string.ad_ads_popup_title_4)} " +
                                "${applicationContext.getString(R.string.ad_ads_18)}",
                        "",
                        InputType.TYPE_CLASS_TEXT,
                        roomNbrApi18.value
                    )
                )
            }
            17 -> {
                navigate(
                    Navigation.PopupNavigation(
                        item,
                        "${applicationContext.getString(R.string.ad_ads_popup_title_1)} " +
                                "${applicationContext.getString(R.string.ad_ads_17)}",
                        "${applicationContext.getString(R.string.ad_ads_popup_input_hint_1)} " +
                                "${applicationContext.getString(R.string.ad_ads_17)}",
                        InputType.TYPE_CLASS_NUMBER,
                        roomNbrApi17.value
                    )
                )
            }
            18 -> {
                navigate(
                    Navigation.PopupNavigation(
                        item,
                        "${applicationContext.getString(R.string.ad_ads_popup_title_4)} " +
                                "${applicationContext.getString(R.string.ad_ads_18)}",
                        "",
                        InputType.TYPE_CLASS_TEXT,
                        roomNbrApi18.value
                    )
                )
            }
            19 -> {
                navigate(
                    Navigation.PopupNavigation(
                        item,
                        "${applicationContext.getString(R.string.ad_ads_popup_title_4)} " +
                                "${applicationContext.getString(R.string.ad_ads_19)}",
                        "",
                        InputType.TYPE_CLASS_TEXT,
                        roomNbrApi19.value
                    )
                )
            }
            20 -> {
                navigate(
                    Navigation.PopupNavigation(
                        item,
                        "${applicationContext.getString(R.string.ad_ads_popup_title_4)} " +
                                "${applicationContext.getString(R.string.ad_ads_20)}",
                        "",
                        InputType.TYPE_CLASS_TEXT,
                        roomNbrApi20.value
                    )
                )
            }

        }
    }

    fun getNewValueByIndex(showInputDialog: Pair<Int, String?>) {
        when (showInputDialog.first) {
            1 -> {
                roomNbrApi1.value = showInputDialog.second
                roomNbr1.value = applicationContext.getString(R.string.ad_ads_1) + roomNbrApi1.value
            }
            2 -> {
                roomNbrApi2.value = showInputDialog.second
                roomNbr2.value = applicationContext.getString(R.string.ad_ads_2) + roomNbrApi2.value
            }
            3 -> {
                roomNbrApi3.value = showInputDialog.second
                roomNbr3.value = applicationContext.getString(R.string.ad_ads_3) + roomNbrApi3.value
            }
            4 -> {
                roomNbrApi4.value = showInputDialog.second
                roomNbr4.value = applicationContext.getString(R.string.ad_ads_4) + roomNbrApi4.value
            }
            5 -> {
                roomNbrApi5.value = showInputDialog.second
                roomNbr5.value = applicationContext.getString(R.string.ad_ads_5) + roomNbrApi5.value
            }
            6 -> {
                roomNbrApi6.value = showInputDialog.second
                roomNbr6.value = applicationContext.getString(R.string.ad_ads_6) + roomNbrApi6.value
            }
            7 -> {
                roomNbrApi7.value = showInputDialog.second
                roomNbr7.value = applicationContext.getString(R.string.ad_ads_7) + roomNbrApi7.value
            }
            8 -> {
                roomNbrApi8.value = showInputDialog.second
                roomNbr8.value = applicationContext.getString(R.string.ad_ads_8) + roomNbrApi8.value
            }
            9 -> {
                roomNbrApi9.value = showInputDialog.second
                roomNbr9.value = applicationContext.getString(R.string.ad_ads_9) + roomNbrApi9.value
            }
            10 -> {
                roomNbrApi10.value = showInputDialog.second
                roomNbr10.value =
                    applicationContext.getString(R.string.ad_ads_10) + roomNbrApi10.value
            }
            12 -> {
                roomNbrApi12.value = showInputDialog.second
                roomNbr12.value =
                    applicationContext.getString(R.string.ad_ads_12) + roomNbrApi12.value
            }
            13 -> {
                roomNbrApi13.value = showInputDialog.second
                roomNbr13.value =
                    applicationContext.getString(R.string.ad_ads_13) + roomNbrApi13.value
            }
            14 -> {
                roomNbrApi14.value = showInputDialog.second
                roomNbr14.value =
                    applicationContext.getString(R.string.ad_ads_14) + roomNbrApi14.value
            }
            15 -> {
                roomNbrApi15.value = showInputDialog.second
                roomNbr15.value =
                    applicationContext.getString(R.string.ad_ads_15) + roomNbrApi15.value
            }
            16 -> {
                roomNbrApi16.value = showInputDialog.second
                roomNbr16.value =
                    applicationContext.getString(R.string.ad_ads_16) + roomNbrApi16.value
            }
            17 -> {
                roomNbrApi17.value = showInputDialog.second
                roomNbr17.value =
                    applicationContext.getString(R.string.ad_ads_17) + roomNbrApi17.value
            }
            18 -> {
                roomNbrApi18.value = showInputDialog.second
                roomNbr18.value =
                    applicationContext.getString(R.string.ad_ads_18) + roomNbrApi18.value
            }
            19 -> {
                roomNbrApi19.value = showInputDialog.second
                roomNbr19.value =
                    applicationContext.getString(R.string.ad_ads_19) + roomNbrApi19.value
            }
            20 -> {
                roomNbrApi20.value = showInputDialog.second
                roomNbr20.value =
                    applicationContext.getString(R.string.ad_ads_20) + roomNbrApi20.value
            }

        }
    }

    fun onEditProperty(property: Property) {
        roomNbrApi1.value = property.details.chambres
        roomNbrApi2.value = property.details.suites
        roomNbrApi3.value = property.details.sallesDeBain
        roomNbrApi4.value = property.details.sallesDeau
        roomNbrApi5.value = property.details.bureaux
        roomNbrApi6.value = property.details.dressing
        roomNbrApi7.value = property.details.garages
        roomNbrApi8.value = property.details.caves
        roomNbrApi9.value = property.details.balcons
        roomNbrApi10.value = property.details.terrasse
        roomNbrApi12.value = property.details.annee
        roomNbrApi13.value = property.details.piscine
        roomNbrApi14.value = property.details.piscinable
        roomNbrApi15.value = property.details.poolHouse
        roomNbrApi16.value = property.details.sansVisAVis
        roomNbrApi17.value = property.details.ascenseur
        roomNbrApi18.value = property.details.duplex
        roomNbrApi19.value = property.details.triplex
        roomNbrApi20.value = property.details.rezDeJardin
    }

}