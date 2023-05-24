package com.foodline.ui.home.transport.conf

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.foodline.R
import com.foodline.base.BaseFragment
import com.foodline.databinding.ConfFragmentBinding
import com.foodline.global.helper.Navigation
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ConfFragment : BaseFragment() {

    private val viewModel: ConfViewModel by viewModels()

    private lateinit var binding: ConfFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.conf_fragment, container, false)
        binding = ConfFragmentBinding.bind(view)
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

    }

    /**
     * handling navigation event
     */
    override fun navigate(navigationTo: Navigation) {
        when (navigationTo) {

        }
    }

}