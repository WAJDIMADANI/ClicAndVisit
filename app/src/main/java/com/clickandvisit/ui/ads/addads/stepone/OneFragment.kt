package com.clickandvisit.ui.ads.addads.stepone


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.clickandvisit.R
import com.clickandvisit.base.BaseFragment
import com.clickandvisit.databinding.OneFragmentBinding
import com.clickandvisit.global.helper.Navigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OneFragment : BaseFragment() {

    val viewModel: OneViewModel by viewModels()

    private lateinit var binding: OneFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.one_fragment, container, false)
        binding = OneFragmentBinding.bind(view)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        //val dpeValues = listOf("A", "B", "C", "D", "E", "F", "G") // Energy performance values

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerBaseObserver(viewModel)

        binding.tvA.setOnClickListener {
            binding.tvA.layoutParams.width = 25
            binding.tvA.layoutParams.height = 50
            binding.tvA.text = "A"
            binding.tvA.invalidate()
            viewModel.onDPEAClicked()
        }
    }

    /**
     * handling navigation event
     */
    override fun navigate(navigationTo: Navigation) {

    }

}