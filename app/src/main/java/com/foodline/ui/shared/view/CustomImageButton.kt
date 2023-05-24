package com.foodline.ui.shared.view

import android.content.Context
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageButton
import com.foodline.global.utils.CLICK_OPACITY


class CustomImageButton : AppCompatImageButton {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val rectF = RectF(0f, 0f, width.toFloat(), height.toFloat())
        val x = event.x
        val y = event.y
        alpha = if (rectF.contains(
                x,
                y
            ) && (event.action == MotionEvent.ACTION_DOWN || event.action == MotionEvent.ACTION_MOVE) && isEnabled
        ) {
            CLICK_OPACITY
        } else {
            1f
        }
        return super.onTouchEvent(event)
    }

}