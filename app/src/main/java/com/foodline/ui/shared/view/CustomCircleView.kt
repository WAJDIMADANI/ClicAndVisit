package com.foodline.ui.shared.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.foodline.R

/**
 * Created by SAzouzi on 06/02/2020
 */

class CustomCircleView : View {

    private var color = ""

    constructor(context: Context?) : super(context)

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
        context.obtainStyledAttributes(attrs, R.styleable.CustomCircleView).apply {
            color = getString(R.styleable.CustomCircleView_bg_circle_color) ?: "#151515"//FIXME
            recycle()
        }
    }

    override fun draw(canvas: Canvas) {
        val circlePaint = Paint()
        circlePaint.color = Color.parseColor(color)
        circlePaint.flags = Paint.ANTI_ALIAS_FLAG
        val h = height
        val w = width
        val diameter = if (h > w) h else w
        val radius = diameter / 2
        canvas.drawCircle(
            diameter / 2.toFloat(),
            diameter / 2.toFloat(),
            radius.toFloat(),
            circlePaint
        )
        super.draw(canvas)
    }

    fun setColor(color: String?) {
        color?.let {
            this.color = color
        }
    }

}