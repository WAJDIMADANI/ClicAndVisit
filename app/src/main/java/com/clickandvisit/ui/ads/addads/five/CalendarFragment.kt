package com.clickandvisit.ui.ads.addads.five

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.clickandvisit.R
import com.clickandvisit.base.BaseFragment
import com.clickandvisit.data.model.property.Property
import com.clickandvisit.databinding.FragmentCalendarBinding
import com.clickandvisit.global.helper.Navigation
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CalendarFragment(val property: Property?) : BaseFragment() {

    val viewModel: CalendarViewModel by viewModels()

    private lateinit var binding: FragmentCalendarBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)
        binding = FragmentCalendarBinding.bind(view)
        binding.viewModel = viewModel
        if (property != null) {
            viewModel.onEditProperty(property)
        }

        binding.lifecycleOwner = viewLifecycleOwner

        return view
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