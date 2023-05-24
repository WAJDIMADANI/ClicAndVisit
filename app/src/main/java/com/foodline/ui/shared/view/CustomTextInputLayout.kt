package com.foodline.ui.shared.view

import android.content.Context
import android.util.AttributeSet
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import com.google.android.material.textfield.TextInputLayout
import com.foodline.R


@BindingMethods(
    BindingMethod(
        type = CustomTextInputLayout::class,
        attribute = "app:errorBackground",
        method = "setErrorBackground"
    )
)
class CustomTextInputLayout : TextInputLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }

    fun setErrorBackground(isError: Boolean) {
        if (isError) {
            error = getStringErrorMessage()
            errorIconDrawable = null
        } else {
            error = null
        }
    }

    private fun getStringErrorMessage(): String {
        return context.getString(R.string.global_empty)
    }

}