package com.clickandvisit.ui.shared.bottomsheet.meet

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.clickandvisit.R
import com.clickandvisit.databinding.BottomSheetMeetBinding
import com.clickandvisit.global.listener.OnSendClickedListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog


class CustomMeetBottomSheet(
    private val myContext: Context,
    private val title: String,
    private val hint: String,
    private val onSendClickedListener: OnSendClickedListener,
    private val actionBlock: (() -> Unit)? = null,
    private val dismissActionBlock: (() -> Unit)? = null
) : BottomSheetDialog(myContext) {

    private lateinit var binding: BottomSheetMeetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding =
            DataBindingUtil.inflate(
                LayoutInflater.from(myContext),
                R.layout.bottom_sheet_meet,
                null,
                true
            )

        binding.bottomsheet = this
        binding.tvTitle.text = title
        binding.chatMessage.hint = hint

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

        binding.ivHide.setOnClickListener {
            actionBlock?.invoke()
            dismiss()
        }

        binding.cbSend.setOnClickListener {
            onSendClickedListener.onSendClicked(binding.chatMessage.text.toString())
        }

    }

    override fun onStart() {
        super.onStart()
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}