package com.clickandvisit.ui.ads.addads.five

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.clickandvisit.R
import com.clickandvisit.base.BaseFragment
import com.clickandvisit.data.model.property.Property
import com.clickandvisit.databinding.FragmentCalendarBinding
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.ui.ads.adsdetails.CalendarAdapter
import com.clickandvisit.ui.ads.adsdetails.CalendarUtils
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate


@AndroidEntryPoint
class CalendarFragment(val property: Property?) : BaseFragment(), CalendarAdapter.OnItemListener  {

    val viewModel: CalendarViewModel by viewModels()

    private lateinit var binding: FragmentCalendarBinding


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

        CalendarUtils.selectedDate = LocalDate.now()
        setWeekView()

        binding.ivPreviousWeek.setOnClickListener {
            if (LocalDate.now() < CalendarUtils.selectedDate) {
                CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1)
                setWeekView()
            }
        }

        binding.ivNextWeek.setOnClickListener {
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1)
            setWeekView()
        }

        return view
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
    override fun onItemClick(position: Int, date: LocalDate?) {
        CalendarUtils.selectedDate = date
        setWeekView()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerObservers()
        registerBaseObserver(viewModel)
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