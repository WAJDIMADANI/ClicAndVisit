package com.clickandvisit.ui.menu.payment

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.model.Card
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.listener.SchedulerProvider
import com.clickandvisit.global.listener.ToolBarListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class PaymentViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    private val userRepository: UserRepository
) : BaseAndroidViewModel(application, schedulerProvider), ToolBarListener, OnEditClickListener,
    OnAddCardClickListener {

    val cardNumber = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val cvvCode = MutableLiveData<String>()
    val expDate = MutableLiveData<String>()

    val cardNumberError: MutableLiveData<Boolean> = MutableLiveData(false)
    val nameError: MutableLiveData<Boolean> = MutableLiveData(false)
    val cvvCodeError: MutableLiveData<Boolean> = MutableLiveData(false)
    val expDateError: MutableLiveData<Boolean> = MutableLiveData(false)


    val cardList: MutableLiveData<List<Card>> = MutableLiveData(arrayListOf())

    init {
        val card0 = Card("0101010101", "Nicolas Martin", "522", "11/25")
        val card1 = Card("1212121212", "Louis Bernard", "512", "04/24")
        val card2 = Card("2323232323", "Michel Robert", "456", "12/26")
        cardList.value = arrayListOf(card0, card1, card2)
    }

    fun onBackClick() {
        navigate(Navigation.Back)
    }

    override fun onItemClickListener(card: Card) {
        showAddCardBottomSheet(card)
    }

    override fun onAddCardClickListener() {
        showAddCardBottomSheet(null)
    }

}
