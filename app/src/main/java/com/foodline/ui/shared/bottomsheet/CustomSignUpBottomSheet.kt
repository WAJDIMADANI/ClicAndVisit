package com.foodline.ui.shared.bottomsheet

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.foodline.R
import com.foodline.data.model.Card
import com.foodline.databinding.ButtomSheetSignUpBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

/**
 * Created by sazouzi on 03/07/2020
 */

class CustomSignUpBottomSheet(
    private val myContext: Context,
    private val card: Card?,
    private val actionBlock: (() -> Unit)? = null,
    private val dismissActionBlock: (() -> Unit)? = null
) : BottomSheetDialog(myContext) {

    private lateinit var binding: ButtomSheetSignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding =
            DataBindingUtil.inflate(
                LayoutInflater.from(myContext),
                R.layout.buttom_sheet_sign_up,
                null,
                true
            )

       //FIXME
        binding.bottomsheet = this
        binding.name = card?.name
        binding.number = card?.name
        binding.expDate = card?.expDate
        binding.cvv = card?.cvvCode

        initializeListener()

        setCancelable(false)
        binding.executePendingBindings()
        setContentView(binding.root)
        window?.setGravity(Gravity.BOTTOM)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun initializeListener() {
        setOnDismissListener {
            dismissActionBlock?.invoke()
        }

        binding.ivBack.setOnClickListener {
            actionBlock?.invoke()
            dismiss()
        }

        binding.btnCardAdd.setOnClickListener {

        }

    }

    override fun onStart() {
        super.onStart()
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}