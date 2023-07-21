package com.clickandvisit.ui.shared.bottomsheet.filter

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.clickandvisit.R
import com.clickandvisit.databinding.BottomSheetFilterBinding
import com.clickandvisit.global.listener.OnFilterClickedListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog


class CustomFilterBottomSheet(
    private val myContext: Context,
    private val onFilterClickedListener: OnFilterClickedListener,
    private val actionBlock: (() -> Unit)? = null,
    private val dismissActionBlock: (() -> Unit)? = null
) : BottomSheetDialog(myContext) {

    private lateinit var binding: BottomSheetFilterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding =
            DataBindingUtil.inflate(
                LayoutInflater.from(myContext),
                R.layout.bottom_sheet_filter,
                null,
                true
            )

        binding.bottomsheet = this

        initializeListener()

        setCancelable(true)
        binding.executePendingBindings()
        setContentView(binding.root)
        window?.setGravity(Gravity.BOTTOM)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun initializeListener() {
        setOnDismissListener {
            dismissActionBlock?.invoke()
        }

        binding.tvCancel.setOnClickListener {
            actionBlock?.invoke()
            dismiss()
        }

        binding.tvDate.setOnClickListener {
            onFilterClickedListener.onDateClicked()
            dismiss()
        }

        binding.tvPriceAsc.setOnClickListener {
            onFilterClickedListener.onPriceAscClicked()
            dismiss()
        }

        binding.tvPriceDesc.setOnClickListener {
            onFilterClickedListener.onPriceDescClicked()
            dismiss()
        }

        binding.tvSurfaceAsc.setOnClickListener {
            onFilterClickedListener.onSurfaceAscClicked()
            dismiss()
        }

        binding.tvSurfaceDesc.setOnClickListener {
            onFilterClickedListener.onSurfaceDescClicked()
            dismiss()
        }

    }

    override fun onStart() {
        super.onStart()
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}