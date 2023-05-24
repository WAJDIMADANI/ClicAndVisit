package com.foodline.ui.home.send.date

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.foodline.R
import com.foodline.base.BaseFragment
import com.foodline.databinding.DateFragmentBinding
import com.foodline.global.helper.Navigation
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class DateFragment : BaseFragment() {


    private val viewModel: DateViewModel by viewModels()

    private lateinit var binding: DateFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.date_fragment, container, false)
        binding = DateFragmentBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerBaseObserver(viewModel)
        registerOneObservers()
    }

    /**
     * register UI One Observers
     */
    private fun registerOneObservers() {
        binding.customImageButtonDate.setOnClickListener {
            setSelectTime()
        }
    }

    private fun setSelectTime() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(), { _, year, monthOfYear, dayOfMonth ->

                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                viewModel.setSelectTime(calendar)
            }, year, month, day
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000

        datePickerDialog.show()
    }

    /**
     * handling navigation event
     */
    override fun navigate(navigationTo: Navigation) {
        when (navigationTo) {

        }
    }

}