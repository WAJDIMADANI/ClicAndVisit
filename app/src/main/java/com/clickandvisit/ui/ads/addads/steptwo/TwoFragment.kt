package com.clickandvisit.ui.ads.addads.steptwo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.clickandvisit.R
import com.clickandvisit.base.BaseFragment
import com.clickandvisit.databinding.TwoFragmentBinding
import com.clickandvisit.global.helper.Navigation

class TwoFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.two_fragment, container, false)
        val bind = TwoFragmentBinding.bind(view)
        bind.lifecycleOwner = viewLifecycleOwner
        //(requireActivity() as IntroActivity?)?.stepTwo()
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