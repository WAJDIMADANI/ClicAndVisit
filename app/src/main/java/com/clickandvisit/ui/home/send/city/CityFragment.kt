package com.clickandvisit.ui.home.send.city

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.clickandvisit.R
import com.clickandvisit.base.BaseFragment
import com.clickandvisit.databinding.CityFragmentBinding
import com.clickandvisit.global.helper.Navigation
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CityFragment : BaseFragment() {


    private val viewModel: CityViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.city_fragment, container, false)
        val bind = CityFragmentBinding.bind(view)
        bind.viewModel = viewModel
        bind.lifecycleOwner = viewLifecycleOwner
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
        //TODO
    }

    /**
     * handling navigation event
     */
    override fun navigate(navigationTo: Navigation) {
        when (navigationTo) {

        }
    }

}