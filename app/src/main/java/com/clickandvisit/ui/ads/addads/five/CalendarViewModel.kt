package com.clickandvisit.ui.ads.addads.five

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.model.property.Property
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

    val availableHours1: MutableLiveData<ArrayList<String>?> = MutableLiveData()
    val availableHours2: MutableLiveData<ArrayList<String>?> = MutableLiveData()
    val availableHours3: MutableLiveData<ArrayList<String>?> = MutableLiveData()
    val availableHours4: MutableLiveData<ArrayList<String>?> = MutableLiveData()
    val availableHours5: MutableLiveData<ArrayList<String>?> = MutableLiveData()
    val availableHours6: MutableLiveData<ArrayList<String>?> = MutableLiveData()
    val availableHours7: MutableLiveData<ArrayList<String>?> = MutableLiveData()


    val allReservedHours: MutableLiveData<ArrayList<String>?> = MutableLiveData()

    var propertyId: Int = 0


    var datesTimes : String = ""

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
                val response = withContext(schedulerProvider.dispatchersIO()) {
                    userRepository.getAvailability(date, propertyId)
                }

                availableHours1.value = response.availableHours as ArrayList<String>
                firstDay = firstDay.plusDays(1)
                val day = firstDay
                fetchAvailability2(CalendarUtils.getWsFormattedDate(day))
            }, { error ->
                onLikeClickedError(error)
            })
        }
    }


    fun fetchAvailability2(date: String) {
        viewModelScope.launch {
            tryCatch({
                val response = withContext(schedulerProvider.dispatchersIO()) {
                    userRepository.getAvailability(date, propertyId)
                }
                availableHours2.value = response.availableHours as ArrayList<String>
                firstDay = firstDay.plusDays(1)
                val day = firstDay
                fetchAvailability3(CalendarUtils.getWsFormattedDate(day))
            }, { error ->
                onLikeClickedError(error)
            })
        }
    }


    fun fetchAvailability3(date: String) {
        viewModelScope.launch {
            tryCatch({
                val response = withContext(schedulerProvider.dispatchersIO()) {
                    userRepository.getAvailability(date, propertyId)
                }
                availableHours3.value = response.availableHours as ArrayList<String>
                firstDay = firstDay.plusDays(1)
                val day = firstDay
                fetchAvailability4(CalendarUtils.getWsFormattedDate(day))
            }, { error ->
                onLikeClickedError(error)
            })
        }
    }

    fun fetchAvailability4(date: String) {
        viewModelScope.launch {
            tryCatch({
                val response = withContext(schedulerProvider.dispatchersIO()) {
                    userRepository.getAvailability(date, propertyId)
                }
                availableHours4.value = response.availableHours as ArrayList<String>
                firstDay = firstDay.plusDays(1)
                val day = firstDay
                fetchAvailability5(CalendarUtils.getWsFormattedDate(day))
            }, { error ->
                onLikeClickedError(error)
            })
        }
    }


    fun fetchAvailability5(date: String) {
        viewModelScope.launch {
            tryCatch({
                val response = withContext(schedulerProvider.dispatchersIO()) {
                    userRepository.getAvailability(date, propertyId)
                }
                availableHours5.value = response.availableHours as ArrayList<String>
                firstDay = firstDay.plusDays(1)
                val day = firstDay
                fetchAvailability6(CalendarUtils.getWsFormattedDate(day))
            }, { error ->
                onLikeClickedError(error)
            })
        }
    }


    fun fetchAvailability6(date: String) {
        viewModelScope.launch {
            tryCatch({
                val response = withContext(schedulerProvider.dispatchersIO()) {
                    userRepository.getAvailability(date, propertyId)
                }
                availableHours6.value = response.availableHours as ArrayList<String>
                firstDay = firstDay.plusDays(1)
                val day = firstDay
                fetchAvailability7(CalendarUtils.getWsFormattedDate(day))
            }, { error ->
                onLikeClickedError(error)
            })
        }
    }

    fun fetchAvailability7(date: String) {
        viewModelScope.launch {
            tryCatch({
                val response = withContext(schedulerProvider.dispatchersIO()) {
                    userRepository.getAvailability(date, propertyId)
                }
                hideBlockProgressBar()
                availableHours7.value = response.availableHours as ArrayList<String>
            }, { error ->
                onLikeClickedError(error)
            })
        }
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
        } else if (datesTimes.contains(date).not()) {
            datesTimes = "$date,$datesTimes"
        }
    }

    private fun onSetAvailabilitySuccess(response: ResultModel) {

    }


}