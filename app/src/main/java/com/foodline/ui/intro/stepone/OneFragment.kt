package com.foodline.ui.intro.stepone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.foodline.R
import com.foodline.base.BaseFragment
import com.foodline.databinding.OneFragmentBinding
import com.foodline.global.helper.Navigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OneFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.one_fragment, container, false)
        val bind = OneFragmentBinding.bind(view)
        bind.lifecycleOwner = viewLifecycleOwner

        //(requireActivity() as IntroActivity?)?.stepOne()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerOneObservers()
    }

    /**
     * register UI One Observers
     */
    private fun registerOneObservers() {

    }

    /**
     * handling navigation event
     */
    override fun navigate(navigationTo: Navigation) {

    }

}