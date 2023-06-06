package com.clickandvisit.ui.addads.stepfour


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.clickandvisit.R
import com.clickandvisit.base.BaseFragment
import com.clickandvisit.databinding.FragmentFourBinding
import com.clickandvisit.global.helper.Navigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FourFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_four, container, false)
        val bind = FragmentFourBinding.bind(view)
        bind.lifecycleOwner = viewLifecycleOwner
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