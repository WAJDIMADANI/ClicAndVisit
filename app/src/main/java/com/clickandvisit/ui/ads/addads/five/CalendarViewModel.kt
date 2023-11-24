package com.clickandvisit.ui.ads.addads.five

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.model.property.Property
import com.clickandvisit.data.model.reservation.AvailabilityResponse
import com.clickandvisit.data.model.reservation.ResultModel
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.global.listener.SchedulerProvider
import com.clickandvisit.global.utils.tryCatch
import com.clickandvisit.ui.ads.adsdetails.CalendarUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject


@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class CalendarViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository
) : BaseAndroidViewModel(application, schedulerProvider) {

    val availableHours1: MutableLiveData<ArrayList<String?>?> = MutableLiveData()
    val availableHours2: MutableLiveData<ArrayList<String?>?> = MutableLiveData()
    val availableHours3: MutableLiveData<ArrayList<String?>?> = MutableLiveData()
    val availableHours4: MutableLiveData<ArrayList<String?>?> = MutableLiveData()
    val availableHours5: MutableLiveData<ArrayList<String?>?> = MutableLiveData()
    val availableHours6: MutableLiveData<ArrayList<String?>?> = MutableLiveData()
    val availableHours7: MutableLiveData<ArrayList<String?>?> = MutableLiveData()


    val allReservedHours: MutableLiveData<ArrayList<String>?> = MutableLiveData()

    var propertyId: Int = 0


    var datesTimes: String = ""

    private var isEdit = false


    @RequiresApi(Build.VERSION_CODES.O)
    var firstDay = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))

    init {
        // fetchAvailability1(CalendarUtils.getWsFormattedDate(firstDay))
    }

    fun onEditProperty(property: Property) {
        fetchAvailability1(CalendarUtils.getWsFormattedDate(firstDay))
        isEdit = true
        viewModelScope.launch {
            tryCatch({
                val response = withContext(schedulerProvider.dispatchersIO()) {
                    userRepository.getAllReserved(property.id)
                }
                allReservedHours.value = response.availableHours as ArrayList
            }, { error ->
                onLikeClickedError(error)
            })
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchAvailability1(date: String) {
        availableHours1.value = arrayListOf()
        availableHours2.value = arrayListOf()
        availableHours3.value = arrayListOf()
        availableHours4.value = arrayListOf()
        availableHours5.value = arrayListOf()
        availableHours6.value = arrayListOf()
        availableHours7.value = arrayListOf()
        showBlockProgressBar()
        viewModelScope.launch {
            tryCatch({

                var allWeekDate = (",").plus(date.plus(firstDay.plusDays(1)).plus(","))
                allWeekDate = allWeekDate.plus(firstDay.plusDays(1)).plus(",")
                allWeekDate = allWeekDate.plus(firstDay.plusDays(1)).plus(",")
                allWeekDate = allWeekDate.plus(firstDay.plusDays(1)).plus(",")
                allWeekDate = allWeekDate.plus(firstDay.plusDays(1)).plus(",")
                allWeekDate = allWeekDate.plus(firstDay.plusDays(1)).plus(",")


                val response = withContext(schedulerProvider.dispatchersIO()) {
                    userRepository.getAvailability(allWeekDate, propertyId)
                }

                getAvailabilitySuccess(response)
            }, { error ->
                onLikeClickedError(error)
            })
        }
    }

    private fun getAvailabilitySuccess(response: AvailabilityResponse) {
        hideBlockProgressBar()
        availableHours1.value = response.availableHours[0] as ArrayList<String?>
        availableHours2.value = response.availableHours[1] as ArrayList<String?>
        availableHours3.value = response.availableHours[2] as ArrayList<String?>
        availableHours4.value = response.availableHours[3] as ArrayList<String?>
        availableHours5.value = response.availableHours[4] as ArrayList<String?>
        availableHours6.value = response.availableHours[5] as ArrayList<String?>
        availableHours7.value = response.availableHours[6] as ArrayList<String?>
    }


    private fun onLikeClickedError(throwable: Throwable) {
        hideBlockProgressBar()
        handleThrowable(throwable)
    }


    fun setAvailability(date: String, accept: String) {
        if (isEdit) {
            viewModelScope.launch {
                tryCatch({
                    val response = withContext(schedulerProvider.dispatchersIO()) {
                        userRepository.setAvailability(
                            propertyId,
                            date,
                            accept
                        )
                    }
                    onSetAvailabilitySuccess(response)
                }, { error ->
                    onLikeClickedError(error)
                })
            }
        } /*else if (datesTimes.contains(date).not()) {
            datesTimes = "$date,$datesTimes"
        }*/
    }

    private fun onSetAvailabilitySuccess(response: ResultModel) {

    }


}