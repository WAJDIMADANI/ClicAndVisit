package com.clickandvisit.ui.shared.dialog

import android.content.Context
import android.view.LayoutInflater
import com.clickandvisit.databinding.ActionSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog


class SimpleImgPickerDialog(
    private val myContext: Context,
    private val takePicActionBlock: (() -> Unit)? = null,
    private val pickPicActionBlock: (() -> Unit)? = null,
    private val dismissActionBlock: (() -> Unit)? = null
) : BottomSheetDialog(myContext) {

    init {
        createDialog()
    }

    private fun createDialog() {
        val inflater = LayoutInflater.from(myContext)
        val binding = ActionSheetBinding.inflate(inflater)

        binding.pickPicture.setOnClickListener {
            pickPicActionBlock?.invoke()
            dismiss()
        }

        binding.takePicture.setOnClickListener {
            takePicActionBlock?.invoke()
            dismiss()
        }

        setOnDismissListener {
            dismissActionBlock?.invoke()
        }

        binding.executePendingBindings()
        setContentView(binding.root)

    }

}