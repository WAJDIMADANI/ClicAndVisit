package com.clickandvisit.ui.ads.adsdetails

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.clickandvisit.R
import com.clickandvisit.base.BaseActivity
import com.clickandvisit.data.model.property.Property
import com.clickandvisit.databinding.ActivityAdsDetailsBinding
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.utils.observeOnlyNotNull
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import dagger.hilt.android.AndroidEntryPoint
import me.jlurena.revolvingweekview.DayTime
import me.jlurena.revolvingweekview.WeekView
import me.jlurena.revolvingweekview.WeekViewEvent
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.format.TextStyle
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


@AndroidEntryPoint
class AdsDetailsActivity : BaseActivity(), WeekView.WeekViewLoader,
    WeekView.EmptyViewClickListener {

    private val viewModel: AdsDetailsViewModel by viewModels()

    private lateinit var binding: ActivityAdsDetailsBinding

    @Inject
    lateinit var adapter: RoomAdapter


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



        binding.weekview.setLimitTime(7, 22)
        binding.weekview.minTime = 7
        binding.weekview.maxTime = 22
        // Set up a date time interpreter to interpret how the date and time will be formatted in
        // the week view. This is optional.
        setupDateTimeInterpreter()
        binding.weekview.weekViewLoader = this
        binding.weekview.emptyViewClickListener = this



 /*       viewModel.availableHours.observeOnlyNotNull(this) { list ->
            list.forEach {

                val now = LocalDateTime.now().with(LocalTime.of(it.subSequence(0, 2).toString().toInt(), 0))
                val startTime = DayTime(now)

                val endTime = DayTime(startTime)
                endTime.addMinutes(60)

                val event = WeekViewEvent("ID", "", startTime, endTime)
                event.color = Color.argb(255, 78, 177, 97)

                events.add(event)
            }

        }
*/

    }

    /**
     * Set up a date time interpreter which will show short date values when in week view and long date values otherwise.
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

    override fun onEmptyViewClicked(day: DayTime?) {
        val endTime = DayTime(day)
        endTime.addMinutes(60)
        val events: MutableList<WeekViewEvent> = java.util.ArrayList()
        val event = WeekViewEvent("ID", "", day, endTime)
        event.color = Color.argb(255, 78, 177, 97)
        events.add(event)
        //binding.weekview.cacheAndSortEvents(events)
        //binding.weekview.calculateHeaderHeight()
    }

    override fun onWeekViewLoad(): MutableList<out WeekViewEvent> {
        val events: MutableList<WeekViewEvent> = java.util.ArrayList()

        viewModel.availableHours.value?.forEach {

            val now = LocalDateTime.now().with(LocalTime.of(it.subSequence(0, 2).toString().toInt(), 0))
            val startTime = DayTime(now)

            val endTime = DayTime(startTime)
            endTime.addMinutes(60)

            val event = WeekViewEvent("ID", "", startTime, endTime)
            event.isAllDay = false
            event.name = "\n   "+it.subSequence(0, 2).toString()+ "h"
            event.color = Color.argb(255, 78, 177, 97)

            events.add(event)
        }

        return events
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
                        binding.tvGESA.layoutParams.height = 120
                        binding.tvGESA.text = "A"
                        binding.tvGESA.requestLayout()
                    }
                    "B" -> {
                        binding.tvGESB.layoutParams.width = 140
                        binding.tvGESB.layoutParams.height = 120
                        binding.tvGESB.text = "B"
                        binding.tvGESB.requestLayout()
                    }
                    "C" -> {
                        binding.tvGESC.layoutParams.width = 140
                        binding.tvGESC.layoutParams.height = 120
                        binding.tvGESC.text = "C"
                        binding.tvGESC.requestLayout()
                    }
                    "D" -> {
                        binding.tvGESD.layoutParams.width = 140
                        binding.tvGESD.layoutParams.height = 120
                        binding.tvGESD.text = "D"
                        binding.tvGESD.requestLayout()
                    }
                    "E" -> {
                        binding.tvGESE.layoutParams.width = 140
                        binding.tvGESE.layoutParams.height = 120
                        binding.tvGESE.text = "E"
                        binding.tvGESE.requestLayout()
                    }
                    "F" -> {
                        binding.tvGESF.layoutParams.width = 140
                        binding.tvGESF.layoutParams.height = 120
                        binding.tvGESF.text = "F"
                        binding.tvGESF.requestLayout()
                    }
                    "G" -> {
                        binding.tvGESG.layoutParams.width = 140
                        binding.tvGESG.layoutParams.height = 120
                        binding.tvGESG.text = "G"
                        binding.tvGESG.requestLayout()
                    }
                    else -> {
                        binding.tvGESG.layoutParams.width = 140
                        binding.tvGESG.layoutParams.height = 120
                        binding.tvGESG.text = "G"
                        binding.tvGESG.requestLayout()
                    }
                }
            }

        }
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