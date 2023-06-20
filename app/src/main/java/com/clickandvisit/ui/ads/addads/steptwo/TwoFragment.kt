package com.clickandvisit.ui.ads.addads.steptwo

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import com.clickandvisit.R
import com.clickandvisit.base.BaseFragment
import com.clickandvisit.databinding.TwoFragmentBinding
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.utils.DebugLog
import com.clickandvisit.global.utils.TAG
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TwoFragment : BaseFragment() {

    val viewModel: TwoViewModel by viewModels()

    private lateinit var binding: TwoFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.two_fragment, container, false)
        binding = TwoFragmentBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        //(requireActivity() as IntroActivity?)?.stepTwo()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerBaseObserver(viewModel)
        showInputDialog()
    }

    private fun showInputDialog() {
        val alert: AlertDialog.Builder = AlertDialog.Builder(activity)

        val container = FrameLayout(requireActivity())

        alert.setTitle("Veuillez saisir le nombre de ...")

        val input = EditText(activity)
        input.background = requireActivity().getDrawable(R.drawable.ic_item_border)
        input.hint = "Nombre de ..."
        val params = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.topMargin = 50
        params.marginStart = 50
        params.marginEnd = 50

        input.layoutParams = params
        container.addView(input)

        alert.setView(container)

        alert.setPositiveButton("Saisir") { dialog, _ ->
            DebugLog.i(TAG, input.text.toString())
            dialog.dismiss()
            // Do something with value!
        }

        alert.setNegativeButton(
            "Annuler"
        ) { dialog, _ ->
            dialog.dismiss()
        }

        alert.show()
    }

    /**
     * handling navigation event
     */
    override fun navigate(navigationTo: Navigation) {

    }
}