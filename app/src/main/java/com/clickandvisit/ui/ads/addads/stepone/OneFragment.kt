package com.clickandvisit.ui.ads.addads.stepone


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import com.clickandvisit.R
import com.clickandvisit.base.BaseFragment
import com.clickandvisit.data.model.property.Property
import com.clickandvisit.databinding.OneFragmentBinding
import com.clickandvisit.global.helper.Navigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OneFragment(val property: Property?) : BaseFragment() {

    val viewModel: OneViewModel by viewModels()
    private lateinit var binding: OneFragmentBinding


    private var isClickedDPEA = false
    private var isClickedDPEB = false
    private var isClickedDPEC = false
    private var isClickedDPED = false
    private var isClickedDPEE = false
    private var isClickedDPEF = false
    private var isClickedDPEG = false

    private var isClickedGESA = false
    private var isClickedGESB = false
    private var isClickedGESC = false
    private var isClickedGESD = false
    private var isClickedGESE = false
    private var isClickedGESF = false
    private var isClickedGESG = false

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
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerBaseObserver(viewModel)

        binding.etPrice.addTextChangedListener(SpaceTextWatcher(binding.etPrice))

        binding.tvA.setOnClickListener {
            if (isClickedDPEA) {
                isClickedDPEA = false
                viewModel.dpe.value = ""
                binding.tvA.layoutParams.width = 125
                binding.tvA.layoutParams.height = 55
                binding.tvA.text = ""
                binding.tvA.requestLayout()
            } else {
                isClickedDPEA = true
                isClickedDPEB = false
                isClickedDPEC = false
                isClickedDPED = false
                isClickedDPEE = false
                isClickedDPEF = false
                isClickedDPEG = false
                viewModel.dpe.value = "A"
                binding.tvA.layoutParams.width = 140
                binding.tvA.layoutParams.height = 125
                binding.tvA.text = "A"
                binding.tvA.requestLayout()
                viewModel.onDPEAClicked(1)
            }


            binding.tvB.layoutParams.width = 125
            binding.tvB.layoutParams.height = 55
            binding.tvB.text = ""
            binding.tvB.requestLayout()

            binding.tvC.layoutParams.width = 125
            binding.tvC.layoutParams.height = 55
            binding.tvC.text = ""
            binding.tvC.requestLayout()

            binding.tvD.layoutParams.width = 125
            binding.tvD.layoutParams.height = 55
            binding.tvD.text = ""
            binding.tvD.requestLayout()

            binding.tvE.layoutParams.width = 125
            binding.tvE.layoutParams.height = 55
            binding.tvE.text = ""
            binding.tvE.requestLayout()

            binding.tvF.layoutParams.width = 125
            binding.tvF.layoutParams.height = 55
            binding.tvF.text = ""
            binding.tvF.requestLayout()

            binding.tvG.layoutParams.width = 125
            binding.tvG.layoutParams.height = 55
            binding.tvG.text = ""
            binding.tvG.requestLayout()
        }

        binding.tvB.setOnClickListener {


            binding.tvA.layoutParams.width = 125
            binding.tvA.layoutParams.height = 55
            binding.tvA.text = ""
            binding.tvA.requestLayout()
            viewModel.onDPEAClicked(2)

            if (isClickedDPEB) {
                isClickedDPEB = false
                viewModel.dpe.value = ""
                binding.tvB.layoutParams.width = 125
                binding.tvB.layoutParams.height = 55
                binding.tvB.text = ""
                binding.tvB.requestLayout()
            } else {
                isClickedDPEA = false
                isClickedDPEB = true
                isClickedDPEC = false
                isClickedDPED = false
                isClickedDPEE = false
                isClickedDPEF = false
                isClickedDPEG = false
                viewModel.dpe.value = "B"
                binding.tvB.layoutParams.width = 140
                binding.tvB.layoutParams.height = 125
                binding.tvB.text = "B"
                binding.tvB.requestLayout()
            }


            binding.tvC.layoutParams.width = 125
            binding.tvC.layoutParams.height = 55
            binding.tvC.text = ""
            binding.tvC.requestLayout()

            binding.tvD.layoutParams.width = 125
            binding.tvD.layoutParams.height = 55
            binding.tvD.text = ""
            binding.tvD.requestLayout()

            binding.tvE.layoutParams.width = 125
            binding.tvE.layoutParams.height = 55
            binding.tvE.text = ""
            binding.tvE.requestLayout()

            binding.tvF.layoutParams.width = 125
            binding.tvF.layoutParams.height = 55
            binding.tvF.text = ""
            binding.tvF.requestLayout()

            binding.tvG.layoutParams.width = 125
            binding.tvG.layoutParams.height = 55
            binding.tvG.text = ""
            binding.tvG.requestLayout()
        }

        binding.tvC.setOnClickListener {

            binding.tvA.layoutParams.width = 125
            binding.tvA.layoutParams.height = 55
            binding.tvA.text = ""
            binding.tvA.requestLayout()
            viewModel.onDPEAClicked(3)

            binding.tvB.layoutParams.width = 125
            binding.tvB.layoutParams.height = 55
            binding.tvB.text = ""
            binding.tvB.requestLayout()

            if (isClickedDPEC) {
                isClickedDPEC = false
                viewModel.dpe.value = ""
                binding.tvC.layoutParams.width = 125
                binding.tvC.layoutParams.height = 55
                binding.tvC.text = ""
                binding.tvC.requestLayout()
            } else {
                isClickedDPEA = false
                isClickedDPEB = false
                isClickedDPEC = true
                isClickedDPED = false
                isClickedDPEE = false
                isClickedDPEF = false
                isClickedDPEG = false
                viewModel.dpe.value = "C"
                binding.tvC.layoutParams.width = 140
                binding.tvC.layoutParams.height = 125
                binding.tvC.text = "C"
                binding.tvC.requestLayout()
            }

            binding.tvD.layoutParams.width = 125
            binding.tvD.layoutParams.height = 55
            binding.tvD.text = ""
            binding.tvD.requestLayout()

            binding.tvE.layoutParams.width = 125
            binding.tvE.layoutParams.height = 55
            binding.tvE.text = ""
            binding.tvE.requestLayout()

            binding.tvF.layoutParams.width = 125
            binding.tvF.layoutParams.height = 55
            binding.tvF.text = ""
            binding.tvF.requestLayout()

            binding.tvG.layoutParams.width = 125
            binding.tvG.layoutParams.height = 55
            binding.tvG.text = ""
            binding.tvG.requestLayout()
        }

        binding.tvD.setOnClickListener {

            binding.tvA.layoutParams.width = 125
            binding.tvA.layoutParams.height = 55
            binding.tvA.text = ""
            binding.tvA.requestLayout()
            viewModel.onDPEAClicked(4)

            binding.tvB.layoutParams.width = 125
            binding.tvB.layoutParams.height = 55
            binding.tvB.text = ""
            binding.tvB.requestLayout()

            binding.tvC.layoutParams.width = 125
            binding.tvC.layoutParams.height = 55
            binding.tvC.text = ""
            binding.tvC.requestLayout()

            if (isClickedDPED) {
                isClickedDPED = false
                viewModel.dpe.value = ""
                binding.tvD.layoutParams.width = 125
                binding.tvD.layoutParams.height = 55
                binding.tvD.text = ""
                binding.tvD.requestLayout()
            } else {
                isClickedDPEA = false
                isClickedDPEB = false
                isClickedDPEC = false
                isClickedDPED = true
                isClickedDPEE = false
                isClickedDPEF = false
                isClickedDPEG = false
                viewModel.dpe.value = "D"
                binding.tvD.layoutParams.width = 140
                binding.tvD.layoutParams.height = 125
                binding.tvD.text = "D"
                binding.tvD.requestLayout()
            }

            binding.tvE.layoutParams.width = 125
            binding.tvE.layoutParams.height = 55
            binding.tvE.text = ""
            binding.tvE.requestLayout()

            binding.tvF.layoutParams.width = 125
            binding.tvF.layoutParams.height = 55
            binding.tvF.text = ""
            binding.tvF.requestLayout()

            binding.tvG.layoutParams.width = 125
            binding.tvG.layoutParams.height = 55
            binding.tvG.text = ""
            binding.tvG.requestLayout()
        }

        binding.tvE.setOnClickListener {
            binding.tvA.layoutParams.width = 125
            binding.tvA.layoutParams.height = 55
            binding.tvA.text = ""
            binding.tvA.requestLayout()
            viewModel.onDPEAClicked(5)

            binding.tvB.layoutParams.width = 125
            binding.tvB.layoutParams.height = 55
            binding.tvB.text = ""
            binding.tvB.requestLayout()

            binding.tvC.layoutParams.width = 125
            binding.tvC.layoutParams.height = 55
            binding.tvC.text = ""
            binding.tvC.requestLayout()

            binding.tvD.layoutParams.width = 125
            binding.tvD.layoutParams.height = 55
            binding.tvD.text = ""
            binding.tvD.requestLayout()

            if (isClickedDPEE) {
                isClickedDPEE = false
                viewModel.dpe.value = ""
                binding.tvE.layoutParams.width = 125
                binding.tvE.layoutParams.height = 55
                binding.tvE.text = ""
                binding.tvE.requestLayout()
            } else {
                isClickedDPEA = false
                isClickedDPEB = false
                isClickedDPEC = false
                isClickedDPED = false
                isClickedDPEE = true
                isClickedDPEF = false
                isClickedDPEG = false
                viewModel.dpe.value = "E"
                binding.tvE.layoutParams.width = 140
                binding.tvE.layoutParams.height = 125
                binding.tvE.text = "E"
                binding.tvE.requestLayout()
            }

            binding.tvF.layoutParams.width = 125
            binding.tvF.layoutParams.height = 55
            binding.tvF.text = ""
            binding.tvF.requestLayout()

            binding.tvG.layoutParams.width = 125
            binding.tvG.layoutParams.height = 55
            binding.tvG.text = ""
            binding.tvG.requestLayout()
        }

        binding.tvF.setOnClickListener {
            viewModel.dpe.value = "F"
            binding.tvA.layoutParams.width = 125
            binding.tvA.layoutParams.height = 55
            binding.tvA.text = ""
            binding.tvA.requestLayout()
            viewModel.onDPEAClicked(6)

            binding.tvB.layoutParams.width = 125
            binding.tvB.layoutParams.height = 55
            binding.tvB.text = ""
            binding.tvB.requestLayout()

            binding.tvC.layoutParams.width = 125
            binding.tvC.layoutParams.height = 55
            binding.tvC.text = ""
            binding.tvC.requestLayout()

            binding.tvD.layoutParams.width = 125
            binding.tvD.layoutParams.height = 55
            binding.tvD.text = ""
            binding.tvD.requestLayout()

            binding.tvE.layoutParams.width = 125
            binding.tvE.layoutParams.height = 55
            binding.tvE.text = ""
            binding.tvE.requestLayout()

            if (isClickedDPEF) {
                isClickedDPEF = false
                viewModel.dpe.value = ""
                binding.tvF.layoutParams.width = 125
                binding.tvF.layoutParams.height = 55
                binding.tvF.text = ""
                binding.tvF.requestLayout()
            } else {
                isClickedDPEA = false
                isClickedDPEB = false
                isClickedDPEC = false
                isClickedDPED = false
                isClickedDPEE = false
                isClickedDPEF = true
                isClickedDPEG = false
                viewModel.dpe.value = "F"
                binding.tvF.layoutParams.width = 140
                binding.tvF.layoutParams.height = 125
                binding.tvF.text = "F"
                binding.tvF.requestLayout()
            }

            binding.tvG.layoutParams.width = 125
            binding.tvG.layoutParams.height = 55
            binding.tvG.text = ""
            binding.tvG.requestLayout()
        }

        binding.tvG.setOnClickListener {
            viewModel.dpe.value = "G"
            binding.tvA.layoutParams.width = 125
            binding.tvA.layoutParams.height = 55
            binding.tvA.text = ""
            binding.tvA.requestLayout()
            viewModel.onDPEAClicked(7)

            binding.tvB.layoutParams.width = 125
            binding.tvB.layoutParams.height = 55
            binding.tvB.text = ""
            binding.tvB.requestLayout()

            binding.tvC.layoutParams.width = 125
            binding.tvC.layoutParams.height = 55
            binding.tvC.text = ""
            binding.tvC.requestLayout()

            binding.tvD.layoutParams.width = 125
            binding.tvD.layoutParams.height = 55
            binding.tvD.text = ""
            binding.tvD.requestLayout()

            binding.tvE.layoutParams.width = 125
            binding.tvE.layoutParams.height = 55
            binding.tvE.text = ""
            binding.tvE.requestLayout()

            binding.tvF.layoutParams.width = 125
            binding.tvF.layoutParams.height = 55
            binding.tvF.text = ""
            binding.tvF.requestLayout()

            if (isClickedDPEG) {
                isClickedDPEG = false
                viewModel.dpe.value = ""
                binding.tvG.layoutParams.width = 125
                binding.tvG.layoutParams.height = 55
                binding.tvG.text = ""
                binding.tvG.requestLayout()
            } else {
                isClickedDPEA = false
                isClickedDPEB = false
                isClickedDPEC = false
                isClickedDPED = false
                isClickedDPEE = false
                isClickedDPEF = false
                isClickedDPEG = true
                viewModel.dpe.value = "G"
                binding.tvG.layoutParams.width = 140
                binding.tvG.layoutParams.height = 125
                binding.tvG.text = "G"
                binding.tvG.requestLayout()
            }

        }


        binding.tvGESA.setOnClickListener {

            if (isClickedGESA) {
                isClickedGESA = false
                viewModel.ges.value = ""
                binding.tvGESA.layoutParams.width = 125
                binding.tvGESA.layoutParams.height = 55
                binding.tvGESA.text = ""
                binding.tvGESA.requestLayout()
            } else {
                isClickedGESA = true
                isClickedGESB = false
                isClickedGESC = false
                isClickedGESD = false
                isClickedGESE = false
                isClickedGESF = false
                isClickedGESG = false
                viewModel.ges.value = "A"
                binding.tvGESA.layoutParams.width = 140
                binding.tvGESA.layoutParams.height = 71
                binding.tvGESA.text = "A"
                binding.tvGESA.requestLayout()
                viewModel.onDPEAClicked(1)
            }

            binding.tvGESB.layoutParams.width = 125
            binding.tvGESB.layoutParams.height = 55
            binding.tvGESB.text = ""
            binding.tvGESB.requestLayout()

            binding.tvGESC.layoutParams.width = 125
            binding.tvGESC.layoutParams.height = 55
            binding.tvGESC.text = ""
            binding.tvGESC.requestLayout()

            binding.tvGESD.layoutParams.width = 125
            binding.tvGESD.layoutParams.height = 55
            binding.tvGESD.text = ""
            binding.tvGESD.requestLayout()

            binding.tvGESE.layoutParams.width = 125
            binding.tvGESE.layoutParams.height = 55
            binding.tvGESE.text = ""
            binding.tvGESE.requestLayout()

            binding.tvGESF.layoutParams.width = 125
            binding.tvGESF.layoutParams.height = 55
            binding.tvGESF.text = ""
            binding.tvGESF.requestLayout()

            binding.tvGESG.layoutParams.width = 125
            binding.tvGESG.layoutParams.height = 55
            binding.tvGESG.text = ""
            binding.tvGESG.requestLayout()
        }

        binding.tvGESB.setOnClickListener {
            binding.tvGESA.layoutParams.width = 125
            binding.tvGESA.layoutParams.height = 55
            binding.tvGESA.text = ""
            binding.tvGESA.requestLayout()

            if (isClickedGESB) {
                isClickedGESB = false
                viewModel.ges.value = ""
                binding.tvGESB.layoutParams.width = 125
                binding.tvGESB.layoutParams.height = 55
                binding.tvGESB.text = ""
                binding.tvGESB.requestLayout()
            } else {
                isClickedGESA = false
                isClickedGESB = true
                isClickedGESC = false
                isClickedGESD = false
                isClickedGESE = false
                isClickedGESF = false
                isClickedGESG = false
                viewModel.ges.value = "B"
                binding.tvGESB.layoutParams.width = 140
                binding.tvGESB.layoutParams.height = 71
                binding.tvGESB.text = "B"
                binding.tvGESB.requestLayout()
                viewModel.onDPEAClicked(2)
            }

            binding.tvGESC.layoutParams.width = 125
            binding.tvGESC.layoutParams.height = 55
            binding.tvGESC.text = ""
            binding.tvGESC.requestLayout()

            binding.tvGESD.layoutParams.width = 125
            binding.tvGESD.layoutParams.height = 55
            binding.tvGESD.text = ""
            binding.tvGESD.requestLayout()

            binding.tvGESE.layoutParams.width = 125
            binding.tvGESE.layoutParams.height = 55
            binding.tvGESE.text = ""
            binding.tvGESE.requestLayout()

            binding.tvGESF.layoutParams.width = 125
            binding.tvGESF.layoutParams.height = 55
            binding.tvGESF.text = ""
            binding.tvGESF.requestLayout()

            binding.tvGESG.layoutParams.width = 125
            binding.tvGESG.layoutParams.height = 55
            binding.tvGESG.text = ""
            binding.tvGESG.requestLayout()
        }

        binding.tvGESC.setOnClickListener {
            viewModel.ges.value = "C"
            binding.tvGESA.layoutParams.width = 125
            binding.tvGESA.layoutParams.height = 55
            binding.tvGESA.text = ""
            binding.tvGESA.requestLayout()

            binding.tvGESB.layoutParams.width = 125
            binding.tvGESB.layoutParams.height = 55
            binding.tvGESB.text = ""
            binding.tvGESB.requestLayout()

            binding.tvGESC.layoutParams.width = 140
            binding.tvGESC.layoutParams.height = 71
            binding.tvGESC.text = "C"
            binding.tvGESC.requestLayout()

            if (isClickedGESC) {
                isClickedGESC = false
                viewModel.ges.value = ""
                binding.tvGESC.layoutParams.width = 125
                binding.tvGESC.layoutParams.height = 55
                binding.tvGESC.text = ""
                binding.tvGESC.requestLayout()
            } else {
                isClickedGESA = false
                isClickedGESB = false
                isClickedGESC = true
                isClickedGESD = false
                isClickedGESE = false
                isClickedGESF = false
                isClickedGESG = false
                viewModel.ges.value = "C"
                binding.tvGESC.layoutParams.width = 140
                binding.tvGESC.layoutParams.height = 71
                binding.tvGESC.text = "C"
                binding.tvGESC.requestLayout()
                viewModel.onDPEAClicked(3)
            }

            binding.tvGESD.layoutParams.width = 125
            binding.tvGESD.layoutParams.height = 55
            binding.tvGESD.text = ""
            binding.tvGESD.requestLayout()

            binding.tvGESE.layoutParams.width = 125
            binding.tvGESE.layoutParams.height = 55
            binding.tvGESE.text = ""
            binding.tvGESE.requestLayout()

            binding.tvGESF.layoutParams.width = 125
            binding.tvGESF.layoutParams.height = 55
            binding.tvGESF.text = ""
            binding.tvGESF.requestLayout()

            binding.tvGESG.layoutParams.width = 125
            binding.tvGESG.layoutParams.height = 55
            binding.tvGESG.text = ""
            binding.tvGESG.requestLayout()
        }

        binding.tvGESD.setOnClickListener {
            viewModel.ges.value = "D"
            binding.tvGESA.layoutParams.width = 125
            binding.tvGESA.layoutParams.height = 55
            binding.tvGESA.text = ""
            binding.tvGESA.requestLayout()

            binding.tvGESB.layoutParams.width = 125
            binding.tvGESB.layoutParams.height = 55
            binding.tvGESB.text = ""
            binding.tvGESB.requestLayout()

            binding.tvGESC.layoutParams.width = 125
            binding.tvGESC.layoutParams.height = 55
            binding.tvGESC.text = ""
            binding.tvGESC.requestLayout()


            if (isClickedGESD) {
                isClickedGESD = false
                viewModel.ges.value = ""
                binding.tvGESD.layoutParams.width = 125
                binding.tvGESD.layoutParams.height = 55
                binding.tvGESD.text = ""
                binding.tvGESD.requestLayout()
            } else {
                isClickedGESA = false
                isClickedGESB = false
                isClickedGESC = false
                isClickedGESD = true
                isClickedGESE = false
                isClickedGESF = false
                isClickedGESG = false
                viewModel.ges.value = "D"
                binding.tvGESD.layoutParams.width = 140
                binding.tvGESD.layoutParams.height = 71
                binding.tvGESD.text = "D"
                binding.tvGESD.requestLayout()
                viewModel.onDPEAClicked(4)
            }


            binding.tvGESE.layoutParams.width = 125
            binding.tvGESE.layoutParams.height = 55
            binding.tvGESE.text = ""
            binding.tvGESE.requestLayout()

            binding.tvGESF.layoutParams.width = 125
            binding.tvGESF.layoutParams.height = 55
            binding.tvGESF.text = ""
            binding.tvGESF.requestLayout()

            binding.tvGESG.layoutParams.width = 125
            binding.tvGESG.layoutParams.height = 55
            binding.tvGESG.text = ""
            binding.tvGESG.requestLayout()
        }

        binding.tvGESE.setOnClickListener {
            viewModel.ges.value = "E"
            binding.tvGESA.layoutParams.width = 125
            binding.tvGESA.layoutParams.height = 55
            binding.tvGESA.text = ""
            binding.tvGESA.requestLayout()

            binding.tvGESB.layoutParams.width = 125
            binding.tvGESB.layoutParams.height = 55
            binding.tvGESB.text = ""
            binding.tvGESB.requestLayout()

            binding.tvGESC.layoutParams.width = 125
            binding.tvGESC.layoutParams.height = 55
            binding.tvGESC.text = ""
            binding.tvGESC.requestLayout()

            binding.tvGESD.layoutParams.width = 125
            binding.tvGESD.layoutParams.height = 55
            binding.tvGESD.text = ""
            binding.tvGESD.requestLayout()


            if (isClickedGESE) {
                isClickedGESE = false
                viewModel.ges.value = ""
                binding.tvGESE.layoutParams.width = 125
                binding.tvGESE.layoutParams.height = 55
                binding.tvGESE.text = ""
                binding.tvGESE.requestLayout()
            } else {
                isClickedGESA = false
                isClickedGESB = false
                isClickedGESC = false
                isClickedGESD = false
                isClickedGESE = true
                isClickedGESF = false
                isClickedGESG = false
                viewModel.ges.value = "E"
                binding.tvGESE.layoutParams.width = 140
                binding.tvGESE.layoutParams.height = 71
                binding.tvGESE.text = "E"
                binding.tvGESE.requestLayout()
                viewModel.onDPEAClicked(5)
            }


            binding.tvGESF.layoutParams.width = 125
            binding.tvGESF.layoutParams.height = 55
            binding.tvGESF.text = ""
            binding.tvGESF.requestLayout()

            binding.tvGESG.layoutParams.width = 125
            binding.tvGESG.layoutParams.height = 55
            binding.tvGESG.text = ""
            binding.tvGESG.requestLayout()
        }

        binding.tvGESF.setOnClickListener {
            viewModel.ges.value = "F"
            binding.tvGESA.layoutParams.width = 125
            binding.tvGESA.layoutParams.height = 55
            binding.tvGESA.text = ""
            binding.tvGESA.requestLayout()

            binding.tvGESB.layoutParams.width = 125
            binding.tvGESB.layoutParams.height = 55
            binding.tvGESB.text = ""
            binding.tvGESB.requestLayout()

            binding.tvGESC.layoutParams.width = 125
            binding.tvGESC.layoutParams.height = 55
            binding.tvGESC.text = ""
            binding.tvGESC.requestLayout()

            binding.tvGESD.layoutParams.width = 125
            binding.tvGESD.layoutParams.height = 55
            binding.tvGESD.text = ""
            binding.tvGESD.requestLayout()

            binding.tvGESE.layoutParams.width = 125
            binding.tvGESE.layoutParams.height = 55
            binding.tvGESE.text = ""
            binding.tvGESE.requestLayout()

            binding.tvGESF.layoutParams.width = 140
            binding.tvGESF.layoutParams.height = 71
            binding.tvGESF.text = "F"
            binding.tvGESF.requestLayout()


            if (isClickedGESF) {
                isClickedGESF = false
                viewModel.ges.value = ""
                binding.tvGESF.layoutParams.width = 125
                binding.tvGESF.layoutParams.height = 55
                binding.tvGESF.text = ""
                binding.tvGESF.requestLayout()
            } else {
                isClickedGESA = false
                isClickedGESB = false
                isClickedGESC = false
                isClickedGESD = false
                isClickedGESE = false
                isClickedGESF = true
                isClickedGESG = false
                viewModel.ges.value = "F"
                binding.tvGESF.layoutParams.width = 140
                binding.tvGESF.layoutParams.height = 71
                binding.tvGESF.text = "F"
                binding.tvGESF.requestLayout()
                viewModel.onDPEAClicked(6)
            }


            binding.tvGESG.layoutParams.width = 125
            binding.tvGESG.layoutParams.height = 55
            binding.tvGESG.text = ""
            binding.tvGESG.requestLayout()
        }

        binding.tvGESG.setOnClickListener {
            viewModel.ges.value = "G"
            binding.tvGESA.layoutParams.width = 125
            binding.tvGESA.layoutParams.height = 55
            binding.tvGESA.text = ""
            binding.tvGESA.requestLayout()

            binding.tvGESB.layoutParams.width = 125
            binding.tvGESB.layoutParams.height = 55
            binding.tvGESB.text = ""
            binding.tvGESB.requestLayout()

            binding.tvGESC.layoutParams.width = 125
            binding.tvGESC.layoutParams.height = 55
            binding.tvGESC.text = ""
            binding.tvGESC.requestLayout()

            binding.tvGESD.layoutParams.width = 125
            binding.tvGESD.layoutParams.height = 55
            binding.tvGESD.text = ""
            binding.tvGESD.requestLayout()

            binding.tvGESE.layoutParams.width = 125
            binding.tvGESE.layoutParams.height = 55
            binding.tvGESE.text = ""
            binding.tvGESE.requestLayout()

            binding.tvGESF.layoutParams.width = 125
            binding.tvGESF.layoutParams.height = 55
            binding.tvGESF.text = ""
            binding.tvGESF.requestLayout()

            if (isClickedGESG) {
                isClickedGESG = false
                viewModel.ges.value = ""
                binding.tvGESG.layoutParams.width = 125
                binding.tvGESG.layoutParams.height = 55
                binding.tvGESG.text = ""
                binding.tvGESG.requestLayout()
            } else {
                isClickedGESA = false
                isClickedGESB = false
                isClickedGESC = false
                isClickedGESD = false
                isClickedGESE = false
                isClickedGESF = false
                isClickedGESG = true
                viewModel.ges.value = "G"
                binding.tvGESG.layoutParams.width = 140
                binding.tvGESG.layoutParams.height = 71
                binding.tvGESG.text = "G"
                binding.tvGESG.requestLayout()
                viewModel.onDPEAClicked(7)
            }

        }

    }

    /**
     * handling navigation event
     */
    override fun navigate(navigationTo: Navigation) {
        when (navigationTo) {

            is Navigation.DPENavigation -> {
                when (navigationTo.energy) {
                    "A" -> {
                        binding.tvA.layoutParams.width = 140
                        binding.tvA.layoutParams.height = 125
                        binding.tvA.text = "A"
                        binding.tvA.requestLayout()
                    }

                    "B" -> {
                        binding.tvB.layoutParams.width = 140
                        binding.tvB.layoutParams.height = 125
                        binding.tvB.text = "B"
                        binding.tvB.requestLayout()
                    }

                    "C" -> {
                        binding.tvC.layoutParams.width = 140
                        binding.tvC.layoutParams.height = 125
                        binding.tvC.text = "C"
                        binding.tvC.requestLayout()
                    }

                    "D" -> {
                        binding.tvD.layoutParams.width = 140
                        binding.tvD.layoutParams.height = 125
                        binding.tvD.text = "D"
                        binding.tvD.requestLayout()
                    }

                    "E" -> {
                        binding.tvE.layoutParams.width = 140
                        binding.tvE.layoutParams.height = 125
                        binding.tvE.text = "E"
                        binding.tvE.requestLayout()
                    }

                    "F" -> {
                        binding.tvF.layoutParams.width = 140
                        binding.tvF.layoutParams.height = 125
                        binding.tvF.text = "F"
                        binding.tvF.requestLayout()
                    }

                    "G" -> {
                        binding.tvG.layoutParams.width = 140
                        binding.tvG.layoutParams.height = 125
                        binding.tvG.text = "G"
                        binding.tvG.requestLayout()
                    }

                    else -> {
                        binding.tvG.layoutParams.width = 140
                        binding.tvG.layoutParams.height = 125
                        binding.tvG.text = "G"
                        binding.tvG.requestLayout()
                    }
                }
            }

            is Navigation.GESNavigation -> {
                when (navigationTo.ges) {
                    "A" -> {
                        binding.tvGESA.layoutParams.width = 140
                        binding.tvGESA.layoutParams.height = 125
                        binding.tvGESA.text = "A"
                        binding.tvGESA.requestLayout()
                    }

                    "B" -> {
                        binding.tvGESB.layoutParams.width = 140
                        binding.tvGESB.layoutParams.height = 125
                        binding.tvGESB.text = "B"
                        binding.tvGESB.requestLayout()
                    }

                    "C" -> {
                        binding.tvGESC.layoutParams.width = 140
                        binding.tvGESC.layoutParams.height = 125
                        binding.tvGESC.text = "C"
                        binding.tvGESC.requestLayout()
                    }

                    "D" -> {
                        binding.tvGESD.layoutParams.width = 140
                        binding.tvGESD.layoutParams.height = 125
                        binding.tvGESD.text = "D"
                        binding.tvGESD.requestLayout()
                    }

                    "E" -> {
                        binding.tvGESE.layoutParams.width = 140
                        binding.tvGESE.layoutParams.height = 125
                        binding.tvGESE.text = "E"
                        binding.tvGESE.requestLayout()
                    }

                    "F" -> {
                        binding.tvGESF.layoutParams.width = 140
                        binding.tvGESF.layoutParams.height = 125
                        binding.tvGESF.text = "F"
                        binding.tvGESF.requestLayout()
                    }

                    "G" -> {
                        binding.tvGESG.layoutParams.width = 140
                        binding.tvGESG.layoutParams.height = 125
                        binding.tvGESG.text = "G"
                        binding.tvGESG.requestLayout()
                    }

                    else -> {
                        binding.tvGESG.layoutParams.width = 140
                        binding.tvGESG.layoutParams.height = 125
                        binding.tvGESG.text = "G"
                        binding.tvGESG.requestLayout()
                    }
                }
            }

        }
    }

}

class SpaceTextWatcher(private val editText: EditText) : TextWatcher {
    private var isEditing = false
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    override fun afterTextChanged(s: Editable?) {
        if (!isEditing) {
            isEditing = true
            val originalText = s.toString()
            val cleanString = originalText.replace("[^\\d]".toRegex(), "")
            val formattedText = formatPrice(cleanString)
            editText.setText(formattedText)
            editText.setSelection(formattedText.length)
            isEditing = false
        }
    }
    private fun formatPrice(price: String): String {
        val builder = StringBuilder()
        var count = 0
        for (i in price.length - 1 downTo 0) {
            builder.insert(0, price[i])
            count++
            if (count == 3 && i != 0) {
                builder.insert(0, " ")
                count = 0
            }
        }
        return builder.toString()
    }
}