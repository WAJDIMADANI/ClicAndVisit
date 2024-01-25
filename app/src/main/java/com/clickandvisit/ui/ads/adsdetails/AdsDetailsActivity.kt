package com.clickandvisit.ui.ads.adsdetails

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.clickandvisit.R
import com.clickandvisit.base.BaseActivity
import com.clickandvisit.data.model.property.Property
import com.clickandvisit.databinding.ActivityAdsDetailsBinding
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.utils.observeOnlyNotNull
import com.clickandvisit.ui.ads.adsdetails.CalendarUtils.daysInWeekArray
import com.clickandvisit.ui.ads.adsdetails.CalendarUtils.monthYearFromDate
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject
import kotlin.collections.ArrayList


@AndroidEntryPoint
class AdsDetailsActivity : BaseActivity(), CalendarAdapter.OnItemListener {

    private val viewModel: AdsDetailsViewModel by viewModels()

    private lateinit var binding: ActivityAdsDetailsBinding

    @Inject
    lateinit var adapter: RoomAdapter

    val listTV1: MutableLiveData<ArrayList<AppCompatTextView>> = MutableLiveData(arrayListOf())
    val listTV2: MutableLiveData<ArrayList<AppCompatTextView>> = MutableLiveData(arrayListOf())
    val listTV3: MutableLiveData<ArrayList<AppCompatTextView>> = MutableLiveData(arrayListOf())
    val listTV4: MutableLiveData<ArrayList<AppCompatTextView>> = MutableLiveData(arrayListOf())
    val listTV5: MutableLiveData<ArrayList<AppCompatTextView>> = MutableLiveData(arrayListOf())
    val listTV6: MutableLiveData<ArrayList<AppCompatTextView>> = MutableLiveData(arrayListOf())
    val listTV7: MutableLiveData<ArrayList<AppCompatTextView>> = MutableLiveData(arrayListOf())


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(
                this,
                R.layout.activity_ads_details
            )
        registerBindingAndBaseObservers(binding)

        viewModel.property.observeOnlyNotNull(this) {
            loadImages(it)
        }
        registerRecycler(binding)

        addTVToLists()
        CalendarUtils.selectedDate = LocalDate.now()
        setWeekView()
        fetchHoursResult(R.color.color_accent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun fetchHoursResult(colorId: Int) {
        fetchHoursResult1(colorId)
        fetchHoursResult2(colorId)
        fetchHoursResult3(colorId)
        fetchHoursResult4(colorId)
        fetchHoursResult5(colorId)
        fetchHoursResult6(colorId)
        fetchHoursResult7(colorId)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun setWeekView() {
        binding.monthYearText.text = monthYearFromDate(CalendarUtils.selectedDate)
        val days: ArrayList<LocalDate> = daysInWeekArray(CalendarUtils.selectedDate)
        val calendarAdapter = CalendarAdapter(days, this)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 7)
        binding.calendarRecyclerView.layoutManager = layoutManager
        binding.calendarRecyclerView.adapter = calendarAdapter
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun previousWeekAction(view: View?) {
        if (LocalDate.now() < CalendarUtils.selectedDate) {
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1)
            setWeekView()
            viewModel.firstDay =
                CalendarUtils.selectedDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
            viewModel.fetchAvailability1(CalendarUtils.getWsFormattedDate(viewModel.firstDay))
            fetchDefaultColor()
            fetchHoursResult(R.color.color_accent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun nextWeekAction(view: View?) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1)
        setWeekView()
        viewModel.firstDay =
            CalendarUtils.selectedDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
        viewModel.fetchAvailability1(CalendarUtils.getWsFormattedDate(viewModel.firstDay))
        fetchDefaultColor()
        fetchHoursResult(R.color.color_accent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onItemClick(position: Int, date: LocalDate?) {
        //CalendarUtils.selectedDate = date
        //setWeekView()
    }


    private fun registerRecycler(binding: ActivityAdsDetailsBinding) {
        binding.rvSearch.layoutManager = GridLayoutManager(this, 3)
        binding.rvSearch.adapter = adapter
    }

    private fun loadImages(value: Property) {
        val imageList = ArrayList<SlideModel>()
        if (value.mainPhoto.isNullOrEmpty().not()) {
            imageList.add(SlideModel(imageUrl = value.mainPhoto))
        }

        value.album?.forEach {
            imageList.add(SlideModel(imageUrl = it))
        }
        binding.imageSlider.setImageList(imageList, ScaleTypes.CENTER_CROP)
    }

    /**
     * handling navigation event
     */
    override fun navigate(navigationTo: Navigation) {
        when (navigationTo) {
            is Navigation.Back -> finish()
            is Navigation.Scroll -> {
                binding.svHeader.smoothScrollTo(0, binding.svHeader.bottom - 420)
            }

            is Navigation.ShareNavigation -> {
                Intent().let {
                    it.action = Intent.ACTION_SEND
                    it.type = "text/plain"
                    it.putExtra(Intent.EXTRA_TEXT, navigationTo.property.toString())
                    startActivity(
                        Intent.createChooser(
                            it,
                            getString(R.string.global_recommend_app)
                        )
                    )
                }
            }

            is Navigation.DPENavigation -> {
                when (navigationTo.energy) {
                    "A" -> {
                        binding.tvA.layoutParams.width = 140
                        binding.tvA.layoutParams.height = 120
                        binding.tvA.text = "A"
                        binding.tvA.requestLayout()
                    }
                    "B" -> {
                        binding.tvB.layoutParams.width = 140
                        binding.tvB.layoutParams.height = 120
                        binding.tvB.text = "B"
                        binding.tvB.requestLayout()
                    }
                    "C" -> {
                        binding.tvC.layoutParams.width = 140
                        binding.tvC.layoutParams.height = 120
                        binding.tvC.text = "C"
                        binding.tvC.requestLayout()
                    }
                    "D" -> {
                        binding.tvD.layoutParams.width = 140
                        binding.tvD.layoutParams.height = 120
                        binding.tvD.text = "D"
                        binding.tvD.requestLayout()
                    }
                    "E" -> {
                        binding.tvE.layoutParams.width = 140
                        binding.tvE.layoutParams.height = 120
                        binding.tvE.text = "E"
                        binding.tvE.requestLayout()
                    }
                    "F" -> {
                        binding.tvF.layoutParams.width = 140
                        binding.tvF.layoutParams.height = 120
                        binding.tvF.text = "F"
                        binding.tvF.requestLayout()
                    }
                    "G" -> {
                        binding.tvG.layoutParams.width = 140
                        binding.tvG.layoutParams.height = 120
                        binding.tvG.text = "G"
                        binding.tvG.requestLayout()
                    }
                    else -> {
                        binding.tvG.layoutParams.width = 140
                        binding.tvG.layoutParams.height = 120
                        binding.tvG.text = "G"
                        binding.tvG.requestLayout()
                    }
                }
            }

            is Navigation.GESNavigation -> {
                when (navigationTo.ges) {
                    "A" -> {
                        binding.tvGESA.layoutParams.width = 140
                        binding.tvGESA.layoutParams.height = 71
                        binding.tvGESA.text = "A"
                        binding.tvGESA.requestLayout()
                    }
                    "B" -> {
                        binding.tvGESB.layoutParams.width = 140
                        binding.tvGESB.layoutParams.height = 71
                        binding.tvGESB.text = "B"
                        binding.tvGESB.requestLayout()
                    }
                    "C" -> {
                        binding.tvGESC.layoutParams.width = 140
                        binding.tvGESC.layoutParams.height = 71
                        binding.tvGESC.text = "C"
                        binding.tvGESC.requestLayout()
                    }
                    "D" -> {
                        binding.tvGESD.layoutParams.width = 140
                        binding.tvGESD.layoutParams.height = 71
                        binding.tvGESD.text = "D"
                        binding.tvGESD.requestLayout()
                    }
                    "E" -> {
                        binding.tvGESE.layoutParams.width = 140
                        binding.tvGESE.layoutParams.height = 71
                        binding.tvGESE.text = "E"
                        binding.tvGESE.requestLayout()
                    }
                    "F" -> {
                        binding.tvGESF.layoutParams.width = 140
                        binding.tvGESF.layoutParams.height = 71
                        binding.tvGESF.text = "F"
                        binding.tvGESF.requestLayout()
                    }
                    "G" -> {
                        binding.tvGESG.layoutParams.width = 140
                        binding.tvGESG.layoutParams.height = 71
                        binding.tvGESG.text = "G"
                        binding.tvGESG.requestLayout()
                    }
                    else -> {
                        binding.tvGESG.layoutParams.width = 140
                        binding.tvGESG.layoutParams.height = 71
                        binding.tvGESG.text = "G"
                        binding.tvGESG.requestLayout()
                    }
                }
            }

        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchHoursResult1(colorId: Int) {
        viewModel.availableHours1.observeOnlyNotNull(this) { hours ->
            hours?.forEach { h ->
                listTV1.value!!.forEach { tv ->
                    if (h == tv.tag) {
                        tv.setBackgroundColor(getColor(colorId))
                        tv.setOnClickListener {
                            if (viewModel.isCurrentUser().not()) {
                                viewModel.reserve(
                                    CalendarUtils.getWsFormattedDate(
                                        CalendarUtils.selectedDate.with(
                                            TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)
                                        )
                                    ),
                                    "${tv.tag}"
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchHoursResult2(colorId: Int) {
        viewModel.availableHours2.observeOnlyNotNull(this) { hours ->
            hours?.forEach { h ->
                listTV2.value!!.forEach { tv ->
                    if (h == tv.tag) {
                        tv.setBackgroundColor(getColor(colorId))
                        tv.setOnClickListener {
                            if (viewModel.isCurrentUser().not()) {
                                viewModel.reserve(
                                    CalendarUtils.getWsFormattedDate(
                                        CalendarUtils.selectedDate.with(
                                            TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)
                                        ).plusDays(1)
                                    ),
                                    "${tv.tag}"
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchHoursResult3(colorId: Int) {
        viewModel.availableHours3.observeOnlyNotNull(this) { hours ->
            hours?.forEach { h ->
                listTV3.value!!.forEach { tv ->
                    if (h == tv.tag) {
                        tv.setBackgroundColor(getColor(colorId))
                        tv.setOnClickListener {
                            if (viewModel.isCurrentUser().not()) {
                                viewModel.reserve(
                                    CalendarUtils.getWsFormattedDate(
                                        CalendarUtils.selectedDate.with(
                                            TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)
                                        ).plusDays(2)
                                    ),
                                    "${tv.tag}"
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchHoursResult4(colorId: Int) {
        viewModel.availableHours4.observeOnlyNotNull(this) { hours ->
            hours?.forEach { h ->
                listTV4.value!!.forEach { tv ->
                    if (h == tv.tag) {
                        tv.setBackgroundColor(getColor(colorId))
                        tv.setOnClickListener {
                            if (viewModel.isCurrentUser().not()) {
                                viewModel.reserve(
                                    CalendarUtils.getWsFormattedDate(
                                        CalendarUtils.selectedDate.with(
                                            TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)
                                        ).plusDays(3)
                                    ),
                                    "${tv.tag}"
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchHoursResult5(colorId: Int) {
        viewModel.availableHours5.observeOnlyNotNull(this) { hours ->
            hours?.forEach { h ->
                listTV5.value!!.forEach { tv ->
                    if (h == tv.tag) {
                        tv.setBackgroundColor(getColor(colorId))
                        tv.setOnClickListener {
                            if (viewModel.isCurrentUser().not()) {
                                viewModel.reserve(
                                    CalendarUtils.getWsFormattedDate(
                                        CalendarUtils.selectedDate.with(
                                            TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)
                                        ).plusDays(4)
                                    ),
                                    "${tv.tag}"
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchHoursResult6(colorId: Int) {
        viewModel.availableHours6.observeOnlyNotNull(this) { hours ->
            hours?.forEach { h ->
                listTV6.value!!.forEach { tv ->
                    if (h == tv.tag) {
                        tv.setBackgroundColor(getColor(colorId))
                        tv.setOnClickListener {
                            if (viewModel.isCurrentUser().not()) {
                                viewModel.reserve(
                                    CalendarUtils.getWsFormattedDate(
                                        CalendarUtils.selectedDate.with(
                                            TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)
                                        ).plusDays(5)
                                    ),
                                    "${tv.tag}"
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchHoursResult7(colorId: Int) {
        viewModel.availableHours7.observeOnlyNotNull(this) { hours ->
            hours?.forEach { h ->
                listTV7.value!!.forEach { tv ->
                    if (h == tv.tag) {
                        tv.setBackgroundColor(getColor(colorId))
                        tv.setOnClickListener {
                            if (viewModel.isCurrentUser().not()) {
                                viewModel.reserve(
                                    CalendarUtils.getWsFormattedDate(
                                        CalendarUtils.selectedDate.with(
                                            TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)
                                        ).plusDays(6)
                                    ),
                                    "${tv.tag}"
                                )
                            }
                        }
                    }
                }
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun fetchDefaultColor() {

        listTV1.value!!.forEach { tv ->
            tv.setBackgroundColor(getColor(R.color.white_basic))
        }

        listTV2.value!!.forEach { tv ->
            tv.setBackgroundColor(getColor(R.color.day_gray))
        }

        listTV3.value!!.forEach { tv ->
            tv.setBackgroundColor(getColor(R.color.white_basic))
        }

        listTV4.value!!.forEach { tv ->
            tv.setBackgroundColor(getColor(R.color.day_gray))
        }

        listTV5.value!!.forEach { tv ->
            tv.setBackgroundColor(getColor(R.color.white_basic))
        }

        listTV6.value!!.forEach { tv ->
            tv.setBackgroundColor(getColor(R.color.day_gray))
        }

        listTV7.value!!.forEach { tv ->
            tv.setBackgroundColor(getColor(R.color.white_basic))
        }

    }


    private fun addTVToLists() {
        listTV1.value?.add(binding.tvh17)
        listTV1.value?.add(binding.tvh18)
        listTV1.value?.add(binding.tvh19)
        listTV1.value?.add(binding.tvh110)
        listTV1.value?.add(binding.tvh111)
        listTV1.value?.add(binding.tvh112)
        listTV1.value?.add(binding.tvh113)
        listTV1.value?.add(binding.tvh114)
        listTV1.value?.add(binding.tvh115)
        listTV1.value?.add(binding.tvh116)
        listTV1.value?.add(binding.tvh117)
        listTV1.value?.add(binding.tvh118)
        listTV1.value?.add(binding.tvh119)
        listTV1.value?.add(binding.tvh120)
        listTV1.value?.add(binding.tvh121)


        listTV2.value?.add(binding.tvh27)
        listTV2.value?.add(binding.tvh28)
        listTV2.value?.add(binding.tvh29)
        listTV2.value?.add(binding.tvh210)
        listTV2.value?.add(binding.tvh211)
        listTV2.value?.add(binding.tvh212)
        listTV2.value?.add(binding.tvh213)
        listTV2.value?.add(binding.tvh214)
        listTV2.value?.add(binding.tvh215)
        listTV2.value?.add(binding.tvh216)
        listTV2.value?.add(binding.tvh217)
        listTV2.value?.add(binding.tvh218)
        listTV2.value?.add(binding.tvh219)
        listTV2.value?.add(binding.tvh220)
        listTV2.value?.add(binding.tvh221)


        listTV3.value?.add(binding.tvh37)
        listTV3.value?.add(binding.tvh38)
        listTV3.value?.add(binding.tvh39)
        listTV3.value?.add(binding.tvh310)
        listTV3.value?.add(binding.tvh311)
        listTV3.value?.add(binding.tvh312)
        listTV3.value?.add(binding.tvh313)
        listTV3.value?.add(binding.tvh314)
        listTV3.value?.add(binding.tvh315)
        listTV3.value?.add(binding.tvh316)
        listTV3.value?.add(binding.tvh317)
        listTV3.value?.add(binding.tvh318)
        listTV3.value?.add(binding.tvh319)
        listTV3.value?.add(binding.tvh320)
        listTV3.value?.add(binding.tvh321)


        listTV4.value?.add(binding.tvh47)
        listTV4.value?.add(binding.tvh48)
        listTV4.value?.add(binding.tvh49)
        listTV4.value?.add(binding.tvh410)
        listTV4.value?.add(binding.tvh411)
        listTV4.value?.add(binding.tvh412)
        listTV4.value?.add(binding.tvh413)
        listTV4.value?.add(binding.tvh414)
        listTV4.value?.add(binding.tvh415)
        listTV4.value?.add(binding.tvh416)
        listTV4.value?.add(binding.tvh417)
        listTV4.value?.add(binding.tvh418)
        listTV4.value?.add(binding.tvh419)
        listTV4.value?.add(binding.tvh420)
        listTV4.value?.add(binding.tvh421)


        listTV5.value?.add(binding.tvh57)
        listTV5.value?.add(binding.tvh58)
        listTV5.value?.add(binding.tvh59)
        listTV5.value?.add(binding.tvh510)
        listTV5.value?.add(binding.tvh511)
        listTV5.value?.add(binding.tvh512)
        listTV5.value?.add(binding.tvh513)
        listTV5.value?.add(binding.tvh514)
        listTV5.value?.add(binding.tvh515)
        listTV5.value?.add(binding.tvh516)
        listTV5.value?.add(binding.tvh517)
        listTV5.value?.add(binding.tvh518)
        listTV5.value?.add(binding.tvh519)
        listTV5.value?.add(binding.tvh520)
        listTV5.value?.add(binding.tvh521)


        listTV6.value?.add(binding.tvh67)
        listTV6.value?.add(binding.tvh68)
        listTV6.value?.add(binding.tvh69)
        listTV6.value?.add(binding.tvh610)
        listTV6.value?.add(binding.tvh611)
        listTV6.value?.add(binding.tvh612)
        listTV6.value?.add(binding.tvh613)
        listTV6.value?.add(binding.tvh614)
        listTV6.value?.add(binding.tvh615)
        listTV6.value?.add(binding.tvh616)
        listTV6.value?.add(binding.tvh617)
        listTV6.value?.add(binding.tvh618)
        listTV6.value?.add(binding.tvh619)
        listTV6.value?.add(binding.tvh620)
        listTV6.value?.add(binding.tvh621)


        listTV7.value?.add(binding.tvh77)
        listTV7.value?.add(binding.tvh78)
        listTV7.value?.add(binding.tvh79)
        listTV7.value?.add(binding.tvh710)
        listTV7.value?.add(binding.tvh711)
        listTV7.value?.add(binding.tvh712)
        listTV7.value?.add(binding.tvh713)
        listTV7.value?.add(binding.tvh714)
        listTV7.value?.add(binding.tvh715)
        listTV7.value?.add(binding.tvh716)
        listTV7.value?.add(binding.tvh717)
        listTV7.value?.add(binding.tvh718)
        listTV7.value?.add(binding.tvh719)
        listTV7.value?.add(binding.tvh720)
        listTV7.value?.add(binding.tvh721)
    }


    /**
     * Register the UI for XMLBinding
     * Register the activity for base observers
     */
    private fun registerBindingAndBaseObservers(binding: ActivityAdsDetailsBinding) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        registerBaseObservers(viewModel)
    }

}