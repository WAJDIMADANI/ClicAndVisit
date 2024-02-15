package com.clickandvisit.ui.ads.addads.steptwo

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import com.clickandvisit.R
import com.clickandvisit.base.BaseFragment
import com.clickandvisit.data.model.property.Property
import com.clickandvisit.databinding.TwoFragmentBinding
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.utils.DebugLog
import com.clickandvisit.global.utils.TAG
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TwoFragment(val property: Property?) : BaseFragment() {

    val viewModel: TwoViewModel by viewModels()

    private lateinit var binding: TwoFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.two_fragment, container, false)
        binding = TwoFragmentBinding.bind(view)
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
    }

    private fun showInputDialog(
        index: Int,
        title: String,
        hint: String,
        inputType: Int,
        oldValue: String?
    ) {

        val alert: AlertDialog.Builder = AlertDialog.Builder(activity)

        val container = FrameLayout(requireActivity())

        alert.setTitle(title)

        val input = EditText(activity)
        input.background = requireActivity().getDrawable(R.drawable.ic_item_border)
        input.inputType = inputType
        input.ellipsize = TextUtils.TruncateAt.END
        input.hint = hint
        if (index == 12){
            input.maxLines = 1 // Ensures only a single line
            input.filters = arrayOf(android.text.InputFilter.LengthFilter(4)) // Limit to 4 characters
        }
        if (oldValue.isNullOrEmpty().not()) {
            input.setText(oldValue!!)
        }

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

        alert.setPositiveButton(getString(R.string.signin_submit)) { dialog, _ ->
            viewModel.getNewValueByIndex(Pair(index, input.text.toString()))
            dialog.dismiss()
        }

        alert.setNegativeButton(
            getString(R.string.ad_ads_popup_negative_button_text)
        ) { dialog, _ ->
            dialog.dismiss()
        }

        alert.show()
    }

    /**
     * handling navigation event
     */
    @SuppressLint("ResourceAsColor")
    override fun navigate(navigationTo: Navigation) {
        when (navigationTo) {
            is Navigation.PopupNavigation -> {
                showInputDialog(
                    navigationTo.index,
                    navigationTo.title,
                    navigationTo.hint,
                    navigationTo.inputType,
                    navigationTo.oldValue
                )
            }
        }
    }
}