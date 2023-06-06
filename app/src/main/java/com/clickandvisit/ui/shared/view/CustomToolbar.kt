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


    private fun initialize(context: Context, attrs: AttributeSet?) {
        LayoutInflater.from(getContext()).inflate(R.layout.custom_toolbar, this, true)
        binding = CustomToolbarBinding.inflate(LayoutInflater.from(getContext()), this, true)
        binding.ivNav.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.ivNav -> toolBarListener!!.onBMenuClicked()
            R.id.ivSearch -> toolBarListener!!.onSearchClicked()
            R.id.ivChat -> toolBarListener!!.onChatClicked()
            R.id.ivProfile -> toolBarListener!!.onProfileClicked()
        }
    }

    fun setToolBarListener(toolBarListener: ToolBarListener) {
        this.toolBarListener = toolBarListener
    }
}
