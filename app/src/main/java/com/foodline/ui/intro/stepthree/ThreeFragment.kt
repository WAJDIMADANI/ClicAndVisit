package com.foodline.ui.intro.stepthree

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.foodline.R
import com.foodline.base.BaseFragment
import com.foodline.databinding.ThreeFragmentBinding
import com.foodline.global.helper.Navigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ThreeFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.three_fragment, container, false)
        val bind = ThreeFragmentBinding.bind(view)
        bind.lifecycleOwner = viewLifecycleOwner
        //(requireActivity() as IntroActivity?)?.stepThree()
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