package com.clickandvisit.ui.shared.view

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText


class CustomTextInputEditText : TextInputEditText {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }

}