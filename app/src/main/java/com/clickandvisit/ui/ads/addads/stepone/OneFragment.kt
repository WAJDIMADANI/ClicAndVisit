package com.clickandvisit.ui.ads.addads.stepone


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.clickandvisit.R
import com.clickandvisit.base.BaseFragment
import com.clickandvisit.data.model.property.Property
import com.clickandvisit.data.model.property.add.PropertyAdd
import com.clickandvisit.databinding.OneFragmentBinding
import com.clickandvisit.global.helper.Navigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OneFragment(val property: Property?) : BaseFragment() {

    val viewModel: OneViewModel by viewModels()

    private lateinit var binding: OneFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.one_fragment, container, false)
        binding = OneFragmentBinding.bind(view)

        binding.viewModel = viewModel

        if (property != null) {
            viewModel.onEditProperty(property)
        }

        binding.lifecycleOwner = viewLifecycleOwner

        val dpeValues = listOf("A", "B", "C", "D", "E", "F", "G") // Energy performance values

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerBaseObserver(viewModel)



        binding.tvA.setOnClickListener {
            binding.tvA.layoutParams.width = 140
            binding.tvA.layoutParams.height = 120
            binding.tvA.text = "A"
            binding.tvA.requestLayout()
            viewModel.onDPEAClicked(1)

            binding.tvB.layoutParams.width = 115
            binding.tvB.layoutParams.height = 55
            binding.tvB.text = ""
            binding.tvB.requestLayout()

            binding.tvC.layoutParams.width = 115
            binding.tvC.layoutParams.height = 55
            binding.tvC.text = ""
            binding.tvC.requestLayout()

            binding.tvD.layoutParams.width = 115
            binding.tvD.layoutParams.height = 55
            binding.tvD.text = ""
            binding.tvD.requestLayout()

            binding.tvE.layoutParams.width = 115
            binding.tvE.layoutParams.height = 55
            binding.tvE.text = ""
            binding.tvE.requestLayout()

            binding.tvF.layoutParams.width = 115
            binding.tvF.layoutParams.height = 55
            binding.tvF.text = ""
            binding.tvF.requestLayout()

            binding.tvG.layoutParams.width = 115
            binding.tvG.layoutParams.height = 55
            binding.tvG.text = ""
            binding.tvG.requestLayout()
        }

        binding.tvB.setOnClickListener {
            binding.tvA.layoutParams.width = 115
            binding.tvA.layoutParams.height = 55
            binding.tvA.text = ""
            binding.tvA.requestLayout()
            viewModel.onDPEAClicked(2)

            binding.tvB.layoutParams.width = 140
            binding.tvB.layoutParams.height = 120
            binding.tvB.text = "B"
            binding.tvB.requestLayout()

            binding.tvC.layoutParams.width = 115
            binding.tvC.layoutParams.height = 55
            binding.tvC.text = ""
            binding.tvC.requestLayout()

            binding.tvD.layoutParams.width = 115
            binding.tvD.layoutParams.height = 55
            binding.tvD.text = ""
            binding.tvD.requestLayout()

            binding.tvE.layoutParams.width = 115
            binding.tvE.layoutParams.height = 55
            binding.tvE.text = ""
            binding.tvE.requestLayout()

            binding.tvF.layoutParams.width = 115
            binding.tvF.layoutParams.height = 55
            binding.tvF.text = ""
            binding.tvF.requestLayout()

            binding.tvG.layoutParams.width = 115
            binding.tvG.layoutParams.height = 55
            binding.tvG.text = ""
            binding.tvG.requestLayout()
        }

        binding.tvC.setOnClickListener {
            binding.tvA.layoutParams.width = 115
            binding.tvA.layoutParams.height = 55
            binding.tvA.text = ""
            binding.tvA.requestLayout()
            viewModel.onDPEAClicked(3)

            binding.tvB.layoutParams.width = 115
            binding.tvB.layoutParams.height = 55
            binding.tvB.text = ""
            binding.tvB.requestLayout()

            binding.tvC.layoutParams.width = 140
            binding.tvC.layoutParams.height = 120
            binding.tvC.text = "C"
            binding.tvC.requestLayout()

            binding.tvD.layoutParams.width = 115
            binding.tvD.layoutParams.height = 55
            binding.tvD.text = ""
            binding.tvD.requestLayout()

            binding.tvE.layoutParams.width = 115
            binding.tvE.layoutParams.height = 55
            binding.tvE.text = ""
            binding.tvE.requestLayout()

            binding.tvF.layoutParams.width = 115
            binding.tvF.layoutParams.height = 55
            binding.tvF.text = ""
            binding.tvF.requestLayout()

            binding.tvG.layoutParams.width = 115
            binding.tvG.layoutParams.height = 55
            binding.tvG.text = ""
            binding.tvG.requestLayout()
        }

        binding.tvD.setOnClickListener {
            binding.tvA.layoutParams.width = 115
            binding.tvA.layoutParams.height = 55
            binding.tvA.text = ""
            binding.tvA.requestLayout()
            viewModel.onDPEAClicked(4)

            binding.tvB.layoutParams.width = 115
            binding.tvB.layoutParams.height = 55
            binding.tvB.text = ""
            binding.tvB.requestLayout()

            binding.tvC.layoutParams.width = 115
            binding.tvC.layoutParams.height = 55
            binding.tvC.text = ""
            binding.tvC.requestLayout()

            binding.tvD.layoutParams.width = 140
            binding.tvD.layoutParams.height = 120
            binding.tvD.text = "D"
            binding.tvD.requestLayout()

            binding.tvE.layoutParams.width = 115
            binding.tvE.layoutParams.height = 55
            binding.tvE.text = ""
            binding.tvE.requestLayout()

            binding.tvF.layoutParams.width = 115
            binding.tvF.layoutParams.height = 55
            binding.tvF.text = ""
            binding.tvF.requestLayout()

            binding.tvG.layoutParams.width = 115
            binding.tvG.layoutParams.height = 55
            binding.tvG.text = ""
            binding.tvG.requestLayout()
        }

        binding.tvE.setOnClickListener {
            binding.tvA.layoutParams.width = 115
            binding.tvA.layoutParams.height = 55
            binding.tvA.text = ""
            binding.tvA.requestLayout()
            viewModel.onDPEAClicked(5)

            binding.tvB.layoutParams.width = 115
            binding.tvB.layoutParams.height = 55
            binding.tvB.text = ""
            binding.tvB.requestLayout()

            binding.tvC.layoutParams.width = 115
            binding.tvC.layoutParams.height = 55
            binding.tvC.text = ""
            binding.tvC.requestLayout()

            binding.tvD.layoutParams.width = 115
            binding.tvD.layoutParams.height = 55
            binding.tvD.text = ""
            binding.tvD.requestLayout()

            binding.tvE.layoutParams.width = 140
            binding.tvE.layoutParams.height = 120
            binding.tvE.text = "E"
            binding.tvE.requestLayout()

            binding.tvF.layoutParams.width = 115
            binding.tvF.layoutParams.height = 55
            binding.tvF.text = ""
            binding.tvF.requestLayout()

            binding.tvG.layoutParams.width = 115
            binding.tvG.layoutParams.height = 55
            binding.tvG.text = ""
            binding.tvG.requestLayout()
        }

        binding.tvF.setOnClickListener {
            binding.tvA.layoutParams.width = 115
            binding.tvA.layoutParams.height = 55
            binding.tvA.text = ""
            binding.tvA.requestLayout()
            viewModel.onDPEAClicked(6)

            binding.tvB.layoutParams.width = 115
            binding.tvB.layoutParams.height = 55
            binding.tvB.text = ""
            binding.tvB.requestLayout()

            binding.tvC.layoutParams.width = 115
            binding.tvC.layoutParams.height = 55
            binding.tvC.text = ""
            binding.tvC.requestLayout()

            binding.tvD.layoutParams.width = 115
            binding.tvD.layoutParams.height = 55
            binding.tvD.text = ""
            binding.tvD.requestLayout()

            binding.tvE.layoutParams.width = 115
            binding.tvE.layoutParams.height = 55
            binding.tvE.text = ""
            binding.tvE.requestLayout()

            binding.tvF.layoutParams.width = 140
            binding.tvF.layoutParams.height = 120
            binding.tvF.text = "F"
            binding.tvF.requestLayout()

            binding.tvG.layoutParams.width = 115
            binding.tvG.layoutParams.height = 55
            binding.tvG.text = ""
            binding.tvG.requestLayout()
        }

        binding.tvG.setOnClickListener {
            binding.tvA.layoutParams.width = 115
            binding.tvA.layoutParams.height = 55
            binding.tvA.text = ""
            binding.tvA.requestLayout()
            viewModel.onDPEAClicked(7)

            binding.tvB.layoutParams.width = 115
            binding.tvB.layoutParams.height = 55
            binding.tvB.text = ""
            binding.tvB.requestLayout()

            binding.tvC.layoutParams.width = 115
            binding.tvC.layoutParams.height = 55
            binding.tvC.text = ""
            binding.tvC.requestLayout()

            binding.tvD.layoutParams.width = 115
            binding.tvD.layoutParams.height = 55
            binding.tvD.text = ""
            binding.tvD.requestLayout()

            binding.tvE.layoutParams.width = 115
            binding.tvE.layoutParams.height = 55
            binding.tvE.text = ""
            binding.tvE.requestLayout()

            binding.tvF.layoutParams.width = 115
            binding.tvF.layoutParams.height = 55
            binding.tvF.text = ""
            binding.tvF.requestLayout()

            binding.tvG.layoutParams.width = 140
            binding.tvG.layoutParams.height = 120
            binding.tvG.text = "G"
            binding.tvG.requestLayout()
        }






        binding.tvGESA.setOnClickListener {
            binding.tvGESA.layoutParams.width = 140
            binding.tvGESA.layoutParams.height = 120
            binding.tvGESA.text = "A"
            binding.tvGESA.requestLayout()
            viewModel.onDPEAClicked(1)

            binding.tvGESB.layoutParams.width = 115
            binding.tvGESB.layoutParams.height = 55
            binding.tvGESB.text = ""
            binding.tvGESB.requestLayout()

            binding.tvGESC.layoutParams.width = 115
            binding.tvGESC.layoutParams.height = 55
            binding.tvGESC.text = ""
            binding.tvGESC.requestLayout()

            binding.tvGESD.layoutParams.width = 115
            binding.tvGESD.layoutParams.height = 55
            binding.tvGESD.text = ""
            binding.tvGESD.requestLayout()

            binding.tvGESE.layoutParams.width = 115
            binding.tvGESE.layoutParams.height = 55
            binding.tvGESE.text = ""
            binding.tvGESE.requestLayout()

            binding.tvGESF.layoutParams.width = 115
            binding.tvGESF.layoutParams.height = 55
            binding.tvGESF.text = ""
            binding.tvGESF.requestLayout()

            binding.tvGESG.layoutParams.width = 115
            binding.tvGESG.layoutParams.height = 55
            binding.tvGESG.text = ""
            binding.tvGESG.requestLayout()
        }

        binding.tvGESB.setOnClickListener {
            binding.tvGESA.layoutParams.width = 115
            binding.tvGESA.layoutParams.height = 55
            binding.tvGESA.text = ""
            binding.tvGESA.requestLayout()
            viewModel.onDPEAClicked(2)

            binding.tvGESB.layoutParams.width = 140
            binding.tvGESB.layoutParams.height = 120
            binding.tvGESB.text = "B"
            binding.tvGESB.requestLayout()

            binding.tvGESC.layoutParams.width = 115
            binding.tvGESC.layoutParams.height = 55
            binding.tvGESC.text = ""
            binding.tvGESC.requestLayout()

            binding.tvGESD.layoutParams.width = 115
            binding.tvGESD.layoutParams.height = 55
            binding.tvGESD.text = ""
            binding.tvGESD.requestLayout()

            binding.tvGESE.layoutParams.width = 115
            binding.tvGESE.layoutParams.height = 55
            binding.tvGESE.text = ""
            binding.tvGESE.requestLayout()

            binding.tvGESF.layoutParams.width = 115
            binding.tvGESF.layoutParams.height = 55
            binding.tvGESF.text = ""
            binding.tvGESF.requestLayout()

            binding.tvGESG.layoutParams.width = 115
            binding.tvGESG.layoutParams.height = 55
            binding.tvGESG.text = ""
            binding.tvGESG.requestLayout()
        }

        binding.tvGESC.setOnClickListener {
            binding.tvGESA.layoutParams.width = 115
            binding.tvGESA.layoutParams.height = 55
            binding.tvGESA.text = ""
            binding.tvGESA.requestLayout()
            viewModel.onDPEAClicked(3)

            binding.tvGESB.layoutParams.width = 115
            binding.tvGESB.layoutParams.height = 55
            binding.tvGESB.text = ""
            binding.tvGESB.requestLayout()

            binding.tvGESC.layoutParams.width = 140
            binding.tvGESC.layoutParams.height = 120
            binding.tvGESC.text = "C"
            binding.tvGESC.requestLayout()

            binding.tvGESD.layoutParams.width = 115
            binding.tvGESD.layoutParams.height = 55
            binding.tvGESD.text = ""
            binding.tvGESD.requestLayout()

            binding.tvGESE.layoutParams.width = 115
            binding.tvGESE.layoutParams.height = 55
            binding.tvGESE.text = ""
            binding.tvGESE.requestLayout()

            binding.tvGESF.layoutParams.width = 115
            binding.tvGESF.layoutParams.height = 55
            binding.tvGESF.text = ""
            binding.tvGESF.requestLayout()

            binding.tvGESG.layoutParams.width = 115
            binding.tvGESG.layoutParams.height = 55
            binding.tvGESG.text = ""
            binding.tvGESG.requestLayout()
        }

        binding.tvGESD.setOnClickListener {
            binding.tvGESA.layoutParams.width = 115
            binding.tvGESA.layoutParams.height = 55
            binding.tvGESA.text = ""
            binding.tvGESA.requestLayout()
            viewModel.onDPEAClicked(4)

            binding.tvGESB.layoutParams.width = 115
            binding.tvGESB.layoutParams.height = 55
            binding.tvGESB.text = ""
            binding.tvGESB.requestLayout()

            binding.tvGESC.layoutParams.width = 115
            binding.tvGESC.layoutParams.height = 55
            binding.tvGESC.text = ""
            binding.tvGESC.requestLayout()

            binding.tvGESD.layoutParams.width = 140
            binding.tvGESD.layoutParams.height = 120
            binding.tvGESD.text = "D"
            binding.tvGESD.requestLayout()

            binding.tvGESE.layoutParams.width = 115
            binding.tvGESE.layoutParams.height = 55
            binding.tvGESE.text = ""
            binding.tvGESE.requestLayout()

            binding.tvGESF.layoutParams.width = 115
            binding.tvGESF.layoutParams.height = 55
            binding.tvGESF.text = ""
            binding.tvGESF.requestLayout()

            binding.tvGESG.layoutParams.width = 115
            binding.tvGESG.layoutParams.height = 55
            binding.tvGESG.text = ""
            binding.tvGESG.requestLayout()
        }

        binding.tvGESE.setOnClickListener {
            binding.tvGESA.layoutParams.width = 115
            binding.tvGESA.layoutParams.height = 55
            binding.tvGESA.text = ""
            binding.tvGESA.requestLayout()
            viewModel.onDPEAClicked(5)

            binding.tvGESB.layoutParams.width = 115
            binding.tvGESB.layoutParams.height = 55
            binding.tvGESB.text = ""
            binding.tvGESB.requestLayout()

            binding.tvGESC.layoutParams.width = 115
            binding.tvGESC.layoutParams.height = 55
            binding.tvGESC.text = ""
            binding.tvGESC.requestLayout()

            binding.tvGESD.layoutParams.width = 115
            binding.tvGESD.layoutParams.height = 55
            binding.tvGESD.text = ""
            binding.tvGESD.requestLayout()

            binding.tvGESE.layoutParams.width = 140
            binding.tvGESE.layoutParams.height = 120
            binding.tvGESE.text = "E"
            binding.tvGESE.requestLayout()

            binding.tvGESF.layoutParams.width = 115
            binding.tvGESF.layoutParams.height = 55
            binding.tvGESF.text = ""
            binding.tvGESF.requestLayout()

            binding.tvGESG.layoutParams.width = 115
            binding.tvGESG.layoutParams.height = 55
            binding.tvGESG.text = ""
            binding.tvGESG.requestLayout()
        }

        binding.tvGESF.setOnClickListener {
            binding.tvGESA.layoutParams.width = 115
            binding.tvGESA.layoutParams.height = 55
            binding.tvGESA.text = ""
            binding.tvGESA.requestLayout()
            viewModel.onDPEAClicked(6)

            binding.tvGESB.layoutParams.width = 115
            binding.tvGESB.layoutParams.height = 55
            binding.tvGESB.text = ""
            binding.tvGESB.requestLayout()

            binding.tvGESC.layoutParams.width = 115
            binding.tvGESC.layoutParams.height = 55
            binding.tvGESC.text = ""
            binding.tvGESC.requestLayout()

            binding.tvGESD.layoutParams.width = 115
            binding.tvGESD.layoutParams.height = 55
            binding.tvGESD.text = ""
            binding.tvGESD.requestLayout()

            binding.tvGESE.layoutParams.width = 115
            binding.tvGESE.layoutParams.height = 55
            binding.tvGESE.text = ""
            binding.tvGESE.requestLayout()

            binding.tvGESF.layoutParams.width = 140
            binding.tvGESF.layoutParams.height = 120
            binding.tvGESF.text = "F"
            binding.tvGESF.requestLayout()

            binding.tvGESG.layoutParams.width = 115
            binding.tvGESG.layoutParams.height = 55
            binding.tvGESG.text = ""
            binding.tvGESG.requestLayout()
        }

        binding.tvGESG.setOnClickListener {
            binding.tvGESA.layoutParams.width = 115
            binding.tvGESA.layoutParams.height = 55
            binding.tvGESA.text = ""
            binding.tvGESA.requestLayout()
            viewModel.onDPEAClicked(7)

            binding.tvGESB.layoutParams.width = 115
            binding.tvGESB.layoutParams.height = 55
            binding.tvGESB.text = ""
            binding.tvGESB.requestLayout()

            binding.tvGESC.layoutParams.width = 115
            binding.tvGESC.layoutParams.height = 55
            binding.tvGESC.text = ""
            binding.tvGESC.requestLayout()

            binding.tvGESD.layoutParams.width = 115
            binding.tvGESD.layoutParams.height = 55
            binding.tvGESD.text = ""
            binding.tvGESD.requestLayout()

            binding.tvGESE.layoutParams.width = 115
            binding.tvGESE.layoutParams.height = 55
            binding.tvGESE.text = ""
            binding.tvGESE.requestLayout()

            binding.tvGESF.layoutParams.width = 115
            binding.tvGESF.layoutParams.height = 55
            binding.tvGESF.text = ""
            binding.tvGESF.requestLayout()

            binding.tvGESG.layoutParams.width = 140
            binding.tvGESG.layoutParams.height = 120
            binding.tvGESG.text = "G"
            binding.tvGESG.requestLayout()
        }

    }

    /**
     * handling navigation event
     */
    override fun navigate(navigationTo: Navigation) {

    }

}