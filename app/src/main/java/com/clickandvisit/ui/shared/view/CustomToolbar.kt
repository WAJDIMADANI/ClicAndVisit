package com.clickandvisit.ui.shared.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import com.clickandvisit.R
import com.clickandvisit.databinding.CustomToolbarBinding
import com.clickandvisit.global.listener.ToolBarListener


@BindingMethods(
    BindingMethod(
        type = CustomToolbar::class,
        attribute = "app:onActionClicked",
        method = "setToolBarListener"
    )
)
class CustomToolbar : FrameLayout, View.OnClickListener {

    private var toolBarListener: ToolBarListener? = null
    private lateinit var binding: CustomToolbarBinding

    constructor(context: Context) : super(context) {
        initialize(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initialize(context, attrs)
    }


    fun onFilterExist(isExist: Boolean) {
        if (isExist) {
            binding.ivNavBM.visibility = View.INVISIBLE
            binding.ivNavBack.visibility = View.VISIBLE
        } else {
            binding.ivNavBM.visibility = View.VISIBLE
            binding.ivNavBack.visibility = View.INVISIBLE
        }
    }

    private fun initialize(context: Context, attrs: AttributeSet?) {
        LayoutInflater.from(getContext()).inflate(R.layout.custom_toolbar, this, true)
        binding = CustomToolbarBinding.inflate(LayoutInflater.from(getContext()), this, true)
        binding.ivNavBM.setOnClickListener(this)
        binding.ivNavBack.setOnClickListener(this)
        binding.ivSearch.setOnClickListener(this)
        binding.ivChat.setOnClickListener(this)
        binding.ivProfile.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.ivNavBM -> toolBarListener!!.onBMenuClicked()
            R.id.ivNavBack -> toolBarListener!!.onMenuBackClicked()
            R.id.ivSearch -> toolBarListener!!.onSearchClicked()
            R.id.ivChat -> toolBarListener!!.onChatClicked()
            R.id.ivProfile -> toolBarListener!!.onProfileClicked()
        }
    }

    fun setToolBarListener(toolBarListener: ToolBarListener) {
        this.toolBarListener = toolBarListener
    }

    fun setNotification(isNotification: Boolean) {
        if (isNotification) {
            binding.ivNotification.visibility = View.VISIBLE
        } else {
            binding.ivNotification.visibility = View.GONE
        }
    }
}
