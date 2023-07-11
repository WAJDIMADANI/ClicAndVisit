package com.clickandvisit.ui.ads.addads.five

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.clickandvisit.R
import com.clickandvisit.base.BaseFragment
import com.clickandvisit.data.model.property.Property
import com.clickandvisit.databinding.FragmentCalendarBinding
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.utils.observeOnlyNotNull
import com.clickandvisit.ui.ads.addads.AddAdsActivity
import com.clickandvisit.ui.ads.adsdetails.CalendarAdapter
import com.clickandvisit.ui.ads.adsdetails.CalendarUtils
import dagger.hilt.android.AndroidEntryPoint
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters


@AndroidEntryPoint
class CalendarFragment(val property: Property?) : BaseFragment(), CalendarAdapter.OnItemListener {

    val viewModel: CalendarViewModel by viewModels()

    private lateinit var binding: FragmentCalendarBinding

    val listTV1: MutableLiveData<ArrayList<AppCompatTextView>> = MutableLiveData(arrayListOf())
    val listTV2: MutableLiveData<ArrayList<AppCompatTextView>> = MutableLiveData(arrayListOf())
    val listTV3: MutableLiveData<ArrayList<AppCompatTextView>> = MutableLiveData(arrayListOf())
    val listTV4: MutableLiveData<ArrayList<AppCompatTextView>> = MutableLiveData(arrayListOf())
    val listTV5: MutableLiveData<ArrayList<AppCompatTextView>> = MutableLiveData(arrayListOf())
    val listTV6: MutableLiveData<ArrayList<AppCompatTextView>> = MutableLiveData(arrayListOf())
    val listTV7: MutableLiveData<ArrayList<AppCompatTextView>> = MutableLiveData(arrayListOf())

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)
        binding = FragmentCalendarBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        if (property != null) {
            viewModel.onEditProperty(property)
        }

        viewModel.propertyId = (activity as AddAdsActivity).getPropId()


        addTVToLists()
        itemsClickListener()

        CalendarUtils.selectedDate = LocalDate.now()
        setWeekView()
        fetchHoursResult(R.color.color_accent)

        binding.ivPreviousWeek.setOnClickListener {
            if (LocalDate.now() < CalendarUtils.selectedDate) {
                CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1)
                setWeekView()
                viewModel.firstDay = CalendarUtils.selectedDate.with(
                    TemporalAdjusters.previousOrSame(
                        DayOfWeek.SUNDAY
                    )
                )
                viewModel.fetchAvailability1(CalendarUtils.getWsFormattedDate(viewModel.firstDay))
                fetchDefaultColor()
                fetchHoursResult(R.color.color_accent)
            }
        }

        binding.ivNextWeek.setOnClickListener {
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1)
            setWeekView()
            viewModel.firstDay = CalendarUtils.selectedDate.with(
                TemporalAdjusters.previousOrSame(
                    DayOfWeek.SUNDAY
                )
            )
            viewModel.fetchAvailability1(CalendarUtils.getWsFormattedDate(viewModel.firstDay))
            fetchDefaultColor()
            fetchHoursResult(R.color.color_accent)
        }


        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun itemsClickListener() {
        itemsClickByList(
            listTV1,
            viewModel.availableHours1,
            CalendarUtils.getWsFormattedDate(viewModel.firstDay),
            true
        )

        itemsClickByList(
            listTV2,
            viewModel.availableHours2,
            CalendarUtils.getWsFormattedDate(viewModel.firstDay.plusDays(1)),
            false
        )

        itemsClickByList(
            listTV3,
            viewModel.availableHours3,
            CalendarUtils.getWsFormattedDate(viewModel.firstDay.plusDays(2)),
            true
        )

        itemsClickByList(
            listTV4,
            viewModel.availableHours4,
            CalendarUtils.getWsFormattedDate(viewModel.firstDay.plusDays(3)),
            false
        )

        itemsClickByList(
            listTV5,
            viewModel.availableHours5,
            CalendarUtils.getWsFormattedDate(viewModel.firstDay.plusDays(4)),
            true
        )

        itemsClickByList(
            listTV6,
            viewModel.availableHours6,
            CalendarUtils.getWsFormattedDate(viewModel.firstDay.plusDays(5)),
            false
        )

        itemsClickByList(
            listTV7,
            viewModel.availableHours7,
            CalendarUtils.getWsFormattedDate(viewModel.firstDay.plusDays(6)),
            true
        )


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun itemsClickByList(
        tvList: MutableLiveData<ArrayList<AppCompatTextView>>,
        hoursList: MutableLiveData<ArrayList<String>?>,
        day: String,
        isWhite: Boolean
    ) {
        tvList.value!!.forEach { tv ->
            tv.setOnClickListener {
                if (hoursList.value?.isNotEmpty() == true && hoursList.value?.contains(tv.tag) == true) {
                    if (isWhite) {
                        tv.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.white_basic
                            )
                        )
                    } else {
                        tv.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.day_gray
                            )
                        )
                    }
                    hoursList.value!!.remove(tv.tag)
                    viewModel.setAvailability("$day ${tv.tag}", "remove")
                } else {
                    tv.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.color_accent
                        )
                    )
                    hoursList.value?.add(tv.tag as String)
                    viewModel.setAvailability("$day ${tv.tag}", "add")
                }
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerObservers()
        registerBaseObserver(viewModel)
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
        binding.monthYearText.text = CalendarUtils.monthYearFromDate(CalendarUtils.selectedDate)
        val days: ArrayList<LocalDate> = CalendarUtils.daysInWeekArray(CalendarUtils.selectedDate)
        val calendarAdapter = CalendarAdapter(days, this)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(requireContext(), 7)
        binding.calendarRecyclerView.layoutManager = layoutManager
        binding.calendarRecyclerView.adapter = calendarAdapter
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun previousWeekAction(view: View?) {
        if (LocalDate.now() < CalendarUtils.selectedDate) {
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1)
            setWeekView()
            viewModel.firstDay = CalendarUtils.selectedDate.with(
                TemporalAdjusters.previousOrSame(
                    DayOfWeek.SUNDAY
                )
            )
            viewModel.fetchAvailability1(CalendarUtils.getWsFormattedDate(viewModel.firstDay))
            fetchDefaultColor()
            fetchHoursResult(R.color.color_accent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun nextWeekAction(view: View?) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1)
        setWeekView()
        viewModel.firstDay = CalendarUtils.selectedDate.with(
            TemporalAdjusters.previousOrSame(
                DayOfWeek.SUNDAY
            )
        )
        viewModel.fetchAvailability1(CalendarUtils.getWsFormattedDate(viewModel.firstDay))
        fetchDefaultColor()
        fetchHoursResult(R.color.color_accent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onItemClick(position: Int, date: LocalDate?) {
        //CalendarUtils.selectedDate = date
        //setWeekView()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchHoursResult1(colorId: Int) {
        viewModel.allReservedHours.observeOnlyNotNull(this) { dates ->
            dates?.forEach {
                listTV1.value!!.forEach { tv ->
                    if (it == "${CalendarUtils.getWsFormattedDate(viewModel.firstDay)} ${tv.tag}") {
                        tv.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.alizarin
                            )
                        )
                        tv.setOnClickListener(null)
                    }
                }
            }
        }
        viewModel.availableHours1.observeOnlyNotNull(this) { hours ->
            hours?.forEach { h ->
                listTV1.value!!.forEach { tv ->
                    if (h == tv.tag) {
                        tv.setBackgroundColor(ContextCompat.getColor(requireContext(), colorId))
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchHoursResult2(colorId: Int) {
        viewModel.allReservedHours.observeOnlyNotNull(this) { dates ->
            dates?.forEach {
                listTV2.value!!.forEach { tv ->
                    if (it == "${CalendarUtils.getWsFormattedDate(viewModel.firstDay.plusDays(1))} ${tv.tag}") {
                        tv.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.alizarin
                            )
                        )
                        tv.setOnClickListener(null)
                    }
                }
            }
        }
        viewModel.availableHours2.observeOnlyNotNull(this) { hours ->
            hours?.forEach { h ->
                listTV2.value!!.forEach { tv ->
                    if (h == tv.tag) {
                        tv.setBackgroundColor(ContextCompat.getColor(requireContext(), colorId))
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchHoursResult3(colorId: Int) {
        viewModel.allReservedHours.observeOnlyNotNull(this) { dates ->
            dates?.forEach {
                listTV3.value!!.forEach { tv ->
                    if (it == "${CalendarUtils.getWsFormattedDate(viewModel.firstDay.plusDays(2))} ${tv.tag}") {
                        tv.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.alizarin
                            )
                        )
                        tv.setOnClickListener(null)
                    }
                }
            }
        }
        viewModel.availableHours3.observeOnlyNotNull(this) { hours ->
            hours?.forEach { h ->
                listTV3.value!!.forEach { tv ->
                    if (h == tv.tag) {
                        tv.setBackgroundColor(ContextCompat.getColor(requireContext(), colorId))
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchHoursResult4(colorId: Int) {
        viewModel.allReservedHours.observeOnlyNotNull(this) { dates ->
            dates?.forEach {
                listTV4.value!!.forEach { tv ->
                    if (it == "${CalendarUtils.getWsFormattedDate(viewModel.firstDay.plusDays(3))} ${tv.tag}") {
                        tv.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.alizarin
                            )
                        )
                        tv.setOnClickListener(null)
                    }
                }
            }
        }
        viewModel.availableHours4.observeOnlyNotNull(this) { hours ->
            hours?.forEach { h ->
                listTV4.value!!.forEach { tv ->
                    if (h == tv.tag) {
                        tv.setBackgroundColor(ContextCompat.getColor(requireContext(), colorId))
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchHoursResult5(colorId: Int) {
        viewModel.allReservedHours.observeOnlyNotNull(this) { dates ->
            dates?.forEach {
                listTV5.value!!.forEach { tv ->
                    if (it == "${CalendarUtils.getWsFormattedDate(viewModel.firstDay.plusDays(4))} ${tv.tag}") {
                        tv.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.alizarin
                            )
                        )
                        tv.setOnClickListener(null)
                    }
                }
            }
        }
        viewModel.availableHours5.observeOnlyNotNull(this) { hours ->
            hours?.forEach { h ->
                listTV5.value!!.forEach { tv ->
                    if (h == tv.tag) {
                        tv.setBackgroundColor(ContextCompat.getColor(requireContext(), colorId))
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchHoursResult6(colorId: Int) {
        viewModel.allReservedHours.observeOnlyNotNull(this) { dates ->
            dates?.forEach {
                listTV6.value!!.forEach { tv ->
                    if (it == "${CalendarUtils.getWsFormattedDate(viewModel.firstDay.plusDays(5))} ${tv.tag}") {
                        tv.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.alizarin
                            )
                        )
                        tv.setOnClickListener(null)
                    }
                }
            }
        }
        viewModel.availableHours6.observeOnlyNotNull(this) { hours ->
            hours?.forEach { h ->
                listTV6.value!!.forEach { tv ->
                    if (h == tv.tag) {
                        tv.setBackgroundColor(ContextCompat.getColor(requireContext(), colorId))
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchHoursResult7(colorId: Int) {
        viewModel.allReservedHours.observeOnlyNotNull(this) { dates ->
            dates?.forEach {
                listTV7.value!!.forEach { tv ->
                    if (it == "${CalendarUtils.getWsFormattedDate(viewModel.firstDay.plusDays(6))} ${tv.tag}") {
                        tv.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.alizarin
                            )
                        )
                        tv.setOnClickListener(null)
                    }
                }
            }
        }
        viewModel.availableHours7.observeOnlyNotNull(this) { hours ->
            hours?.forEach { h ->
                listTV7.value!!.forEach { tv ->
                    if (h == tv.tag) {
                        tv.setBackgroundColor(ContextCompat.getColor(requireContext(), colorId))
                    }
                }
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun fetchDefaultColor() {

        listTV1.value!!.forEach { tv ->
            tv.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white_basic))
        }

        listTV2.value!!.forEach { tv ->
            tv.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.day_gray))
        }

        listTV3.value!!.forEach { tv ->
            tv.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white_basic))
        }

        listTV4.value!!.forEach { tv ->
            tv.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.day_gray))
        }

        listTV5.value!!.forEach { tv ->
            tv.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white_basic))
        }

        listTV6.value!!.forEach { tv ->
            tv.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.day_gray))
        }

        listTV7.value!!.forEach { tv ->
            tv.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white_basic))
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


    private fun registerObservers() {

    }

    /**
     * handling navigation event
     */
    override fun navigate(navigationTo: Navigation) {
        when (navigationTo) {

        }
    }

}