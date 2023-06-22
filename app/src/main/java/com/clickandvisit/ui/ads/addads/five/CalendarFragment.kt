package com.clickandvisit.ui.ads.addads.five

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.clickandvisit.R
import com.clickandvisit.base.BaseFragment
import com.clickandvisit.databinding.FragmentCalendarBinding
import com.clickandvisit.global.helper.Navigation
import dagger.hilt.android.AndroidEntryPoint
import me.jlurena.revolvingweekview.DayTime
import me.jlurena.revolvingweekview.WeekView
import me.jlurena.revolvingweekview.WeekView.WeekViewLoader
import me.jlurena.revolvingweekview.WeekViewEvent
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.format.TextStyle
import java.util.*


@AndroidEntryPoint
class CalendarFragment : BaseFragment(), WeekViewLoader {

    val viewModel: CalendarViewModel by viewModels()

    private lateinit var binding: FragmentCalendarBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)
        binding = FragmentCalendarBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.weekview.weekViewLoader = this
        binding.weekview.setLimitTime(7, 22)
        binding.weekview.minTime = 7
        binding.weekview.maxTime = 22

        // Set up a date time interpreter to interpret how the date and time will be formatted in
        // the week view. This is optional.
        setupDateTimeInterpreter()
        return view
    }


    /**
     * Set up a date time interpreter which will show short date values when in week view and long
     * date values otherwise.
     */
    fun setupDateTimeInterpreter() {
        binding.weekview.dayTimeInterpreter = object : WeekView.DayTimeInterpreter {
            override fun interpretDay(date: Int): String {
                return DayOfWeek.of(date).getDisplayName(TextStyle.SHORT, Locale.FRANCE)
                    .toUpperCase(
                        Locale.ROOT
                    )
            }

            override fun interpretTime(hour: Int, minutes: Int): String {
                val res = String.format(Locale.getDefault(), "%02d", hour)
                return res + "h"
            }
        }
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

    override fun onWeekViewLoad(): MutableList<out WeekViewEvent> {
        // Populate the week view with some events.
        val events: MutableList<WeekViewEvent> = ArrayList()
        val random = Random()
        val startTime = DayTime(
            LocalDateTime.now().with(LocalTime.of(7, 0)).plusDays(
                random.nextInt(2).toLong()
            ).plusHours(2)
        )
        val endTime = DayTime(startTime)
        endTime.addMinutes(60)

        val event = WeekViewEvent("ID", "", startTime, endTime)
        event.color = Color.argb(255, 78, 177, 97)
        events.add(event)

/*        for (int i = 0; i < 10; i++) {
            LocalDateTime now = LocalDateTime.now().with(LocalTime.of(7, 0));
            DayTime startTime = new DayTime(now.plusDays(random.nextInt(7)).plusHours(i * (random.nextInt(3) + 1)));
            DayTime endTime = new DayTime(startTime);
            endTime.addMinutes(60);
            WeekViewEvent event = new WeekViewEvent("ID" + i, "", startTime, endTime);
            event.setColor(Color.argb(255, 78, 177, 97));
            events.add(event);
        }*/
        return events
    }

}